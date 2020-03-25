package seniorsathome.seniorsathomespring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.ValorationDao;
import seniorsathome.seniorsathomespring.model.Valoration;

@Controller
@RequestMapping("/valoration")
public class ValorationController {

    private ValorationDao valorationDao;

    @Autowired
    public void setValorationDao(ValorationDao valorationDao) {
        this.valorationDao = valorationDao;
    }

    @RequestMapping("/list")
    public String listValorations(Model model) {
        model.addAttribute("valorations", valorationDao.getValorations());
        return "valoration/list";
    }

    @RequestMapping(value="/add")
    public String addValoration(Model model) {
        model.addAttribute("valoration", new Valoration());
        return "valoration/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("valoration") Valoration valoration,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "valoration/add";
        valorationDao.addValoration(valoration);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idValoration}", method = RequestMethod.GET)
    public String editValoration(Model model, @PathVariable String idValoration) {
        model.addAttribute("valoration", valorationDao.getValoration(idValoration));
        return "valoration/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("valoration") Valoration valoration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "valoration/update";
        valorationDao.updateValoration(valoration);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{idValoration}")
    public String processDeleteValoration(@PathVariable String idValoration) {
        valorationDao.deleteValoration(idValoration);
        return "redirect:../list";
    }
}