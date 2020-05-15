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

    @RequestMapping("/listbyrater/{numberid}")
    public String listValorations(Model model, @PathVariable String numberid) {
        if(numberid.charAt(0) == 'V') {
            System.out.println(valorationDao.getVolunteerAverage(numberid));
            System.out.println(valorationDao.getVolunteerAverage(numberid).getClass());
            model.addAttribute("valorations", valorationDao.getVolunteerValorations(numberid));
            model.addAttribute("average", valorationDao.getVolunteerAverage(numberid));
        }

        return "valoration/listbyrater";
    }

    @RequestMapping(value="/add")
    public String addValoration(Model model) {
        model.addAttribute("valoration", new Valoration());
        return "valoration/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("valoration") Valoration valoration, String estrellas,
                                   BindingResult bindingResult) {
        valoration.setRate(Integer.parseInt(estrellas));
        ValorationValidator val = new ValorationValidator();
        val.validate(valoration,bindingResult);
        if (bindingResult.hasErrors())
            return "valoration/add";
        valorationDao.addValoration(valoration);
        return "redirect:/beneficiary/rate/" + valoration.getIdBeneficiary();
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
        ValorationValidator val = new ValorationValidator();
        val.validate(valoration,bindingResult);
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

    @RequestMapping(value = "/rate/{identificationNumber}/{raterid}")
    public String rateRater(@PathVariable String identificationNumber, @PathVariable String raterid, Model model) {
        Valoration val = new Valoration();
        val.setIdBeneficiary(identificationNumber);
        if (raterid.charAt(0) == 'C') {
            val.setIdCompany(raterid);
        }
        else {
            val.setIdVolunteer(raterid);
        }
        model.addAttribute("valoration", val);
        return "valoration/add";
    }
}
