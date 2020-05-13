package seniorsathome.seniorsathomespring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.ScheduleDao;
import seniorsathome.seniorsathomespring.dao.VolunteerDao;
import seniorsathome.seniorsathomespring.model.Schedule;
import seniorsathome.seniorsathomespring.model.User;
import seniorsathome.seniorsathomespring.model.Volunteer;

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

    @RequestMapping("/list")
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleDao.getSchedules());
        return "schedule/list";
    }
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
        if (bindingResult.hasErrors())
            return "schedule/add";
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            return "redirect:/login";
        }else {
            Volunteer nombre = volunteerDao.getVolunteerByUsername(user.getUsername());
            schedule.setVolunteerid(nombre.getIdNumber());
            scheduleDao.addSchedule(schedule);
            return "redirect:listByUser";
        }
    }

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
        if (bindingResult.hasErrors())
            return "schedule/update";
        scheduleDao.updateSchedule(schedule);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numberid}")
    public String processDeleteSchedule(@PathVariable String numberid) {
        scheduleDao.deleteSchedule(numberid);
        return "redirect:../list";
    }
}
