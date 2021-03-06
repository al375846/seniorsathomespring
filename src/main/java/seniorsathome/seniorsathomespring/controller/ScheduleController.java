package seniorsathome.seniorsathomespring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.BeneficiaryDao;
import seniorsathome.seniorsathomespring.dao.ScheduleDao;
import seniorsathome.seniorsathomespring.dao.VolunteerDao;
import seniorsathome.seniorsathomespring.model.Correo;
import seniorsathome.seniorsathomespring.model.Schedule;
import seniorsathome.seniorsathomespring.model.User;
import seniorsathome.seniorsathomespring.model.Volunteer;
import seniorsathome.seniorsathomespring.model.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private VolunteerDao volunteerDao;

    @Autowired
    public void setVolunteerDao(VolunteerDao a) {
        this.volunteerDao = a;
    }
    private ScheduleDao scheduleDao;

    @Autowired
    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    BeneficiaryDao beneficiaryDao;

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao = beneficiaryDao;
    }

    /*Listar todos los horarios*/
    @RequestMapping("/list")
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleDao.getSchedules());
        return "schedule/list";
    }

    /*Listar todos los horarios de un usuario*/
    @RequestMapping("/listByUser")
    public String loginCompany(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            model.addAttribute("schedules", scheduleDao.getSchedules());
        }else{
            String username = user.getUsername();
            Volunteer nombre = volunteerDao.getVolunteerByUsername(username);
            model.addAttribute("schedules",scheduleDao.getScheduleByIdVolunteer(nombre.getIdNumber()));
        }
        return  "schedule/listByUser";
    }

    /*Mostrar los horarios activos de un usuario*/
    @RequestMapping("/listactive")
    public String listActiveSchedules(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            model.addAttribute("schedules", scheduleDao.getSchedules());
        }else{
            String username = user.getUsername();
            Volunteer nombre = volunteerDao.getVolunteerByUsername(username);
            model.addAttribute("schedules",scheduleDao.getActiveSchedulesByVolunteer(nombre.getIdNumber()));
        }
        return "schedule/listactive";
    }

    /*Añadir horario*/
    @RequestMapping(value="/add")
    public String addSchedule(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "schedule/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(HttpSession session,@ModelAttribute("schedule") Schedule schedule,
                                   BindingResult bindingResult) {
        ScheduleValidator val = new ScheduleValidator();
        val.validate(schedule,bindingResult);
        //si hay errores en algún campo vuelve a solicitar los datos
        if (bindingResult.hasErrors())
            return "schedule/add";
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            return "redirect:/login";
        }else {
            Volunteer volunteer = volunteerDao.getVolunteerByUsername(user.getUsername());
            schedule.setVolunteerid(volunteer.getIdNumber());
            scheduleDao.addSchedule(schedule);
            //correo con la respuesta de la operación, en este caso informa de que se ha añadido un nuevo horario
            Correo.enviarMensajeSah(volunteer.getEmail(), "New schedule", "A new schedule has been added");
            return "redirect:listByUser";
        }
    }

    /*Editar horario*/
    @RequestMapping(value="/update/{numberid}", method = RequestMethod.GET)
    public String editSchedule(Model model, @PathVariable String numberid) {
        model.addAttribute("schedule", scheduleDao.getSchedule(numberid));
        return "schedule/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("schedule") Schedule schedule,
            BindingResult bindingResult) {
        ScheduleValidator val = new ScheduleValidator();
        val.validate(schedule,bindingResult);
        //si hay errores en algún campo vuelve a solicitar los datos
        if (bindingResult.hasErrors())
            return "schedule/update";
        scheduleDao.updateSchedule(schedule);
        return "redirect:list";
    }

    /*Eliminar horario*/
    @RequestMapping(value = "/delete/{numberid}")
    public String processDeleteSchedule(@PathVariable String numberid) {
        scheduleDao.deleteSchedule(numberid);
        return "redirect:../listByUser";
    }

    /*Mostrar los datos de quien ha reservado un horario*/
    @RequestMapping(value="/overview/{numberid}", method = RequestMethod.GET)
    public String overviwSchedule(Model model, @PathVariable String numberid) {
        Schedule s = scheduleDao.getSchedule(numberid);
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiary(s.getBeneficiaryid()));
        return "schedule/overview";
    }

    /*Reservar, por parte de un beneficiario, el horario libre de un voluntario*/
    @RequestMapping(value="/reserve/{beneficiaryid}/{scheduleid}")
    public String overviwSchedule(Model model, @PathVariable String beneficiaryid, @PathVariable String scheduleid) {
        Schedule s = scheduleDao.getSchedule(scheduleid);
        s.setBeneficiaryid(beneficiaryid);
        s.setStatus(true);
        scheduleDao.updateSchedule(s);
        Volunteer v = volunteerDao.getVolunteer(s.getVolunteerid());
        Beneficiary b = beneficiaryDao.getBeneficiary(beneficiaryid);
        Correo.enviarMensajeSah(v.getEmail(), "Schedule reserved", b.getName() + " has reserved your schedule, with phone number " + b.getPhoneNumber());
        return "redirect:/profile/beneficiary";
    }
}
