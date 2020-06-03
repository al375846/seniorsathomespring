package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.BeneficiaryDao;
import seniorsathome.seniorsathomespring.dao.CompanyDao;
import seniorsathome.seniorsathomespring.dao.ContractDao;
import seniorsathome.seniorsathomespring.dao.VolunteerDao;
import seniorsathome.seniorsathomespring.model.Beneficiary;
import seniorsathome.seniorsathomespring.model.Company;
import seniorsathome.seniorsathomespring.model.User;
import seniorsathome.seniorsathomespring.model.Volunteer;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private ContractDao contractDao;
    private CompanyDao companyDao;
    private VolunteerDao volunteerDao;
    private BeneficiaryDao beneficiaryDao;

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao = beneficiaryDao;
    }

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao) {
        this.volunteerDao = volunteerDao;
    }

    /*Redirige al perfil del CAS Comité*/
    @RequestMapping("/committee")
    public String loginCommittee(Model model) {
        return "profile/committee";
    }

    /*Redirige al perfil del CAS Manager*/
    @RequestMapping("/casmanager")
    public String loginCasmanager(Model model) {
        return "profile/casmanager";
    }

    /*Redirige al perfil del CAS de los Voluntarios*/
    @RequestMapping("/casvolunteer")
    public String loginCasvolunteer(Model model) {
        return "profile/casvolunteer";
    }

    /*Redirige al perfil de los beneficiarios*/
    @RequestMapping("/beneficiary")
    public String loginBeneficiary(HttpSession session,Model model) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            return "redirect:/login";
        }else{
            String nombre = user.getUsername();
            Beneficiary bene = beneficiaryDao.getBeneficiaryByUsername(nombre);
            model.addAttribute("beneficiary",bene);
        }
        return "profile/beneficiary";
    }

    /*Redirige al perfil de las Compañías*/
    @RequestMapping("/company")
    public String loginCompany(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            model.addAttribute("contracts", contractDao.getContracts());
        }else{
            String name = user.getUsername();
            Company com = companyDao.getCompanyByUserName(name);
            model.addAttribute("company",com);
            model.addAttribute("usuario",com.getName());
            model.addAttribute("contracts",contractDao.getContractsCompany(com.getFiscalNumber()));
        }
        return "profile/company";
    }

    /*Redirige al perfil de los Trabajadores Sociales*/
    @RequestMapping("/socialworker")
    public String loginSocialWorker(Model model) {
        return "profile/socialworker";
    }

    /*Redirige al perfil de los Voluntarios*/
    @RequestMapping("/volunteer")
    public String loginVolunteer(HttpSession session,Model model) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            return "redirect:/login";
        }else{
            String nombre = user.getUsername();
            Volunteer vol = volunteerDao.getVolunteerByUsername(nombre);
            model.addAttribute("volunteer",vol);
            return "profile/volunteer";
        }
    }

    /*Redirige al perfil que este logeado desde cualquier otra página, o a la pagina de Log in si no hay ninguna sesión abierta*/
    @RequestMapping("/enter")
    public String loginSocialWorker(HttpSession session, Model model) {
        if (session.getAttribute("type") == null) {
            return "redirect:/login";
        }
        String profile =  (String) session.getAttribute("type");
        return "redirect:/profile/" + profile;
    }
}
