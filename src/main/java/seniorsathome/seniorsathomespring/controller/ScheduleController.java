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
import seniorsathome.seniorsathomespring.model.Schedule;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

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

    @RequestMapping(value="/add")
    public String addSchedule(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "schedule/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("schedule") Schedule schedule,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "schedule/add";
        scheduleDao.addSchedule(schedule);
        return "redirect:list";
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
