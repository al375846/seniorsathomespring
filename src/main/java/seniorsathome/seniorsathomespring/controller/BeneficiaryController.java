package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.*;
import seniorsathome.seniorsathomespring.model.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    BeneficiaryDao beneficiaryDao;
    SocialWorkerDao socialWorkerDao;
    RaterDao raterDao;
    ScheduleDao scheduleDao;
    UserDao userDao;
    ValorationDao valorationDao;

    @Autowired
    public void setValorationDao(ValorationDao valorationDao) {
        this.valorationDao = valorationDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao = beneficiaryDao;
    }

    @Autowired
    public void setSocialWorkerDao(SocialWorkerDao socialWorkerDao) {
        this.socialWorkerDao = socialWorkerDao;
    }

    @Autowired
    public void setRaterDao(RaterDao raterDao) {
        this.raterDao = raterDao;
    }

    @Autowired
    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    /*Lista todos los beneficairios*/
    @RequestMapping("/list")
    public String listBeneficiaries(Model model) {
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiaries());
        return "beneficiary/list";
    }

    /*Lista todos los beneficairios por trabajador social*/
    @RequestMapping("/listbysocialworker")
    public String listBeneficiariesBySocialworker(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        SocialWorker sw =socialWorkerDao.getSocialWorkerByUserName(user.getUsername());
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiariesBySocialWorker(sw.getNumberid()));
        return "beneficiary/listbysocialworker";
    }

    /*Lista todos los beneficairios sin trabajador social*/
    @RequestMapping("/listnosocialworker")
    public String listBeneficiariesNoSocialworker(Model model, HttpSession session) {
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiariesNoSocialWorker());
        return "beneficiary/listnosocialworker";
    }

    /*Lista todos los horarios de voluntarios*/
    @RequestMapping("/listschedules")
    public String listSchedules(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("schedules", scheduleDao.getInactiveSchedulesByDateStandar());
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiaryByUsername(user.getUsername()));
        return "beneficiary/listschedules";
    }

    /*Lista todos los horarios de voluntarios buscando por fecha*/
    @RequestMapping(value="/listschedulessearch",  method = RequestMethod.GET)
    public String listSchedulesByDate(Model model, HttpSession session, String search) {
        User user = (User) session.getAttribute("user");
        LocalDate date = LocalDate.parse(search, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("schedules", scheduleDao.getInactiveSchedulesByDate(date));
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiaryByUsername(user.getUsername()));
        return "beneficiary/listschedules";
    }

    /*Añade un beneficiario*/
    @RequestMapping(value="/add")
    public String addBeneficiary(Model model) {
        model.addAttribute("beneficiary", new Beneficiary());
        return "beneficiary/add";
    }

    /*Guarda el beneficiario añadido*/
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("beneficiary") Beneficiary beneficiary,
                                   BindingResult bindingResult, HttpSession session) {
        BeneficiaryValidator beneficiaryValidator = new BeneficiaryValidator(userDao.listAllUsersName());
        beneficiaryValidator.validate(beneficiary, bindingResult);
        if (bindingResult.hasErrors())
            return "beneficiary/add";

        String type = (String) session.getAttribute("type");
        User user = (User) session.getAttribute("user");
        if (type!=null && type.equals("socialworker")){
            SocialWorker sw = socialWorkerDao.getSocialWorkerByUserName(user.getUsername());
            beneficiary.setSocialWorkerID(sw.getNumberid());
            beneficiaryDao.addBeneficiary(beneficiary);
            Correo.enviarMensajeSah(beneficiary.getEmail(), "Register", "Your register has been received");
            Correo.enviarMensajeSah(sw.getEmail(), "Beneficiary Register", "The register of the beneficiary has been received");
            return "redirect:/profile/socialworker";
        }
        beneficiary.setSocialWorkerID("");
        beneficiaryDao.addBeneficiary(beneficiary);
        Correo.enviarMensajeSah(beneficiary.getEmail(), "Register", "Your register has been received");
        return "redirect:/";
    }

    /*Asigna trabajador social a un beneficiario*/
    @RequestMapping(value="/assignsocialworker/{identificationNumber}", method = RequestMethod.GET)
    public String overviewRequest(Model model, @PathVariable String identificationNumber) {
        Beneficiary beneficiary = beneficiaryDao.getBeneficiary(identificationNumber);
        model.addAttribute("beneficiary", beneficiary);
        model.addAttribute("socialworkers", socialWorkerDao.getSocialWorkers());
        return "beneficiary/assignsocialworker";
    }

    /*Guarda los cambios de asignar trabajador social a un beneficiario*/
    @RequestMapping(value="/assign/{identificationNumber}/{numberid}")
    public String accept(
             @PathVariable String identificationNumber, @PathVariable String numberid) {
        Beneficiary beneficiaryAccept = beneficiaryDao.getBeneficiary(identificationNumber);
        beneficiaryAccept.setSocialWorkerID(numberid);
        SocialWorker sw = socialWorkerDao.getSocialWorker(numberid);
        beneficiaryDao.updateBeneficiaryWithoutEncryption(beneficiaryAccept);
        Correo.enviarMensajeSah(sw.getEmail(), "Beneficiary Assign", "A new beneficiary has been assigned to you");
        Correo.enviarMensajeSah(beneficiaryAccept.getEmail(), "Socialworker Assign", "A new socialworker has been assigned to you");
        return "redirect:/beneficiary/listnosocialworker";
    }

    /*Actualiza un beneficiario*/
    @RequestMapping(value="/update/{identificationNumber}", method = RequestMethod.GET)
    public String editBeneficiary(Model model, @PathVariable String identificationNumber) {
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiary(identificationNumber));
        return "beneficiary/update";
    }

    /*Gaurda la actualizacion un beneficiario*/
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(HttpSession session,
            @ModelAttribute("beneficiary") Beneficiary beneficiary,String newPassword,
            BindingResult bindingResult) {
        User user = (User)session.getAttribute("user");
        Beneficiary bene = beneficiaryDao.getBeneficiary(beneficiary.getIdentificationNumber());
        Boolean changePass= true;
        if ( newPassword == "" ){
            beneficiary.setPassword(bene.getPassword());
            changePass= false;
        }
        else {
            beneficiary.setPassword(newPassword);
        }
        BeneficiaryValidator beneficiaryValidator = new BeneficiaryValidator(userDao.listAllUsersName());
        beneficiaryValidator.validate(beneficiary, bindingResult);

        if (bindingResult.hasErrors())
            return "beneficiary/update";
        if(changePass)
            beneficiaryDao.updateBeneficiary(beneficiary);
        else
            beneficiaryDao.updateBeneficiaryWithoutEncryption(beneficiary);
        bene = beneficiaryDao.getBeneficiary(beneficiary.getIdentificationNumber());
        if (beneficiary.getUserName() != user.getUsername() ){
            user.setUsername(bene.getUserName());
            user.setPassword(bene.getPassword());
            session.setAttribute("user",user);
        }
        Correo.enviarMensajeSah(beneficiary.getEmail(), "Update", "Your register has been updated");
        return "redirect:/profile/beneficiary";
    }

    /*Lista hoarios por un beneficiario*/
    @RequestMapping(value = "/schedules/{identificationNumber}")
    public String listSchedulesBeneficiaries(Model model, @PathVariable String identificationNumber){
        model.addAttribute("schedules", beneficiaryDao.listSchedules(identificationNumber));
        return "beneficiary/schedules";
    }

    /*Lista peticiones y hoarios por un beneficiario*/
    @RequestMapping(value = "/requests/{identificationNumber}")
    public String listRequestBeneficiaries(Model model, @PathVariable String identificationNumber) {
        model.addAttribute("requests", beneficiaryDao.listRequests(identificationNumber));
        model.addAttribute("schedules", scheduleDao.getActiveSchedulesByBeneficiary(identificationNumber));
        model.addAttribute("beneficiary", identificationNumber);
        return "beneficiary/requests";
    }

    /*Aplica por una peticio un beneficiario*/
    @RequestMapping(value="/servicesForm/{identificationNumber}", method= RequestMethod.GET)
    public String newService(Model model,  @PathVariable String identificationNumber) {
        model.addAttribute("service", new Request());
        model.addAttribute("id", identificationNumber);
        model.addAttribute("actives", beneficiaryDao.activeServices(identificationNumber));
        return "beneficiary/servicesForm";
    }

    /*Guarda la aplicacion*/
    @RequestMapping(value="/servicesForm", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("service") Request request, Model model, BindingResult bindingResult) {

        ServiceValidator serviceValidator = new ServiceValidator();
        serviceValidator.validate(request, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("id", request.getBeneficiary_id());
            model.addAttribute("actives", beneficiaryDao.activeServices(request.getBeneficiary_id()));
            return "beneficiary/servicesForm";
        }

        beneficiaryDao.addRequest(request);
        String identificationNumber = request.getBeneficiary_id();
        Beneficiary bene = beneficiaryDao.getBeneficiary(identificationNumber);
        Correo.enviarMensajeSah(bene.getEmail(), "Request", "Your request has been applied");
        return "redirect:./requests/" + identificationNumber;
    }

    /*Borra un beneficiario*/
    @RequestMapping(value = "/delete/{identificationNumber}")
    public String processDeleteBeneficiary(@PathVariable String identificationNumber) {
        beneficiaryDao.deleteBeneficiary(identificationNumber);
        return "redirect:../list";
    }

    /*Lista las posibilidades de valoraciones*/
    @RequestMapping(value = "/rate/{identificationNumber}")
    public String rateRequest(@PathVariable String identificationNumber, Model model) {
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiary(identificationNumber));
        List<Rater> todos = raterDao.listRaters(identificationNumber);
        List<String> val_realizacas = valorationDao.getValorationsByBeneficiary(identificationNumber);
        List<Rater> val_por_hacer = new ArrayList<>();
        for (Rater rat: todos) {
            if(!val_realizacas.contains(rat.getRaterid()))
                val_por_hacer.add(rat);
        }
        model.addAttribute("raters", val_por_hacer);
        return "beneficiary/listraters";
    }


}
