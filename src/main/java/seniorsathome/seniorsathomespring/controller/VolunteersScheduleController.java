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
@RequestMapping("/volunteersSchedelure")
public class VolunteersScheduleController {

    private VolunteersScheduleDao volunteerDao;

    @Autowired
    public void setVolunteersScheduleDao(VolunteersScheduleDao a) {
        this.volunteerDao = a;
    }

    @RequestMapping("/list")
    public String listVolunteersSchedelures(Model model) {
        model.addAttribute("volunteersSchedelures", volunteerDao.getVolunteersSchedules());
        return "volunteersSchedelure/list";
    }

    @RequestMapping(value="/add")
    public String addVolunteersSchedelure(Model model) {
        model.addAttribute("volunteersSchedelure", new VolunteersSchedule());
        return "volunteersSchedelure/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteersSchedelure") VolunteersSchedule volunteer,
                                   BindingResult bindingResult) {
        volunteerDao.addVolunteersSchedule(volunteer);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{numberID}/", method = RequestMethod.GET)
    public String editVolunteersSchedelure(Model model, @PathVariable String numberID) {
        model.addAttribute("volunteer", volunteerDao.getVolunteersSchedule(numberID));
        return "volunteersSchedelure/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("volunteersSchedelure") VolunteersSchedule v,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "volunteer/update";
        volunteerDao.updateVolunteersSchedule(v);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numberID}")
    public String processDeleteVolunteersSchedelure(@PathVariable String numberID) {
        volunteerDao.deleteVolunteersSchedule(numberID);
        return "redirect:../list";
    }
}
