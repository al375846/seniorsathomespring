package seniorsathome.seniorsathomespring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.VolunteerDao;
import seniorsathome.seniorsathomespring.model.Volunteer;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {
    private VolunteerDao volunteerDao;

    @Autowired
    public void setVolunteerDao(VolunteerDao a) {
        this.volunteerDao = a;
    }

    @RequestMapping("/list")
    public String listVolunteers(Model model) {
        model.addAttribute("volunteers", volunteerDao.getVolunteers());
        return "volunteer/list";
    }

    @RequestMapping(value="/add")
    public String addVolunteer(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer,
                                   BindingResult bindingResult) {
        VolunteerValidator volValidator = new VolunteerValidator();
        volValidator.validate(volunteer, bindingResult);
        if (bindingResult.hasErrors())
            return "volunteer/add";
        volunteerDao.addVolunteer(volunteer);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idNumber}", method = RequestMethod.GET)
    public String editVolunteer(Model model, @PathVariable String idNumber) {
        model.addAttribute("volunteer", volunteerDao.getVolunteer(idNumber));
        return "volunteer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("volunteer") Volunteer v,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "volunteer/update";
        volunteerDao.updateVolunteer(v);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{idNumber}")
    public String processDeleteVolunteer(@PathVariable String idNumber) {
        volunteerDao.deleteVolunteer(idNumber);
        return "redirect:../list";
    }
}
