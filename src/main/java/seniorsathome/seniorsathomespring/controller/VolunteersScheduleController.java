package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.VolunteersScheduleDao;
import seniorsathome.seniorsathomespring.model.VolunteersSchedule;

@Controller
@RequestMapping("/volunteersSchedule")
public class VolunteersScheduleController {

    private VolunteersScheduleDao volunteerScheduleDao;

    @Autowired
    public void setVolunteersScheduleDao(VolunteersScheduleDao volunteerScheduleDao) {
        this.volunteerScheduleDao = volunteerScheduleDao;
    }

    @RequestMapping("/list")
    public String listVolunteersSchedule(Model model) {
        model.addAttribute("volunteersSchedules", volunteerScheduleDao.getVolunteersSchedules());
        return "volunteerSchedule/list";
    }

    @RequestMapping(value="/add")
    public String addVolunteersSchedule(Model model) {
        model.addAttribute("volunteersSchedule", new VolunteersSchedule());
        return "volunteerSchedule/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteersSchedule") VolunteersSchedule volunteer,
                                   BindingResult bindingResult) {
        volunteerScheduleDao.addVolunteersSchedule(volunteer);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{numberID}/", method = RequestMethod.GET)
    public String editVolunteersSchedule(Model model, @PathVariable String numberID) {
        model.addAttribute("volunteersSchedule", volunteerScheduleDao.getVolunteersSchedule(numberID));
        return "volunteerSchedule/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("volunteersSchedule") VolunteersSchedule v,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "volunteerSchedule/update";
        volunteerScheduleDao.updateVolunteersSchedule(v);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numberID}")
    public String processDeleteVolunteersSchedule(@PathVariable String numberID) {
        volunteerScheduleDao.deleteVolunteersSchedule(numberID);
        return "redirect:../list";
    }
}
