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


    /*Lista las valoraciones*/
    @RequestMapping("/list")
    public String listValorations(Model model) {
        model.addAttribute("valorations", valorationDao.getValorations());
        return "valoration/list";
    }

    /*Lista las valoraciones de un voluntario o compañia*/
    @RequestMapping("/listbyrater/{numberid}")
    public String listValorations(Model model, @PathVariable String numberid) {
        if(numberid.charAt(0) == 'V') {
            model.addAttribute("valorations", valorationDao.getVolunteerValorations(numberid));
            model.addAttribute("average", valorationDao.getVolunteerAverage(numberid));
        }
        else{
            model.addAttribute("valorations", valorationDao.getCompanyValorations(numberid));
            model.addAttribute("average", valorationDao.getCompanyAverage(numberid));
        }

        return "valoration/listbyrater";
    }

    /*Añade una valoracion*/
    @RequestMapping(value="/add")
    public String addValoration(Model model) {
        model.addAttribute("valoration", new Valoration());
        return "valoration/add";
    }

    /*Añade una valoracion y la guarda*/
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("valoration") Valoration valoration, String estrellas,
                                   BindingResult bindingResult) {
        if (estrellas == null)
            estrellas = "0";
        valoration.setRate(Integer.parseInt(estrellas));
        ValorationValidator val = new ValorationValidator();
        val.validate(valoration,bindingResult);
        if (bindingResult.hasErrors())
            return "valoration/add";
        valorationDao.addValoration(valoration);
        return "redirect:/beneficiary/rate/" + valoration.getIdBeneficiary();
    }

    /*Actualiza una valoracion*/
    @RequestMapping(value="/update/{idValoration}", method = RequestMethod.GET)
    public String editValoration(Model model, @PathVariable String idValoration) {
        model.addAttribute("valoration", valorationDao.getValoration(idValoration));
        return "valoration/update";
    }

    /*Actualiza una valoracion y la guarda*/
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

    /*Borra una valoracion*/
    @RequestMapping(value = "/delete/{idValoration}")
    public String processDeleteValoration(@PathVariable String idValoration) {
        valorationDao.deleteValoration(idValoration);
        return "redirect:../list";
    }

    /*Guarda el resultado de una valoracion*/
    @RequestMapping(value = "/rate/{identificationNumber}/{raterid}")
    public String rateRater(@PathVariable String identificationNumber, @PathVariable String raterid, Model model) {
        Valoration val = new Valoration();
        val.setIdBeneficiary(identificationNumber);
        if (raterid.charAt(0) != 'V') {
            val.setIdCompany(raterid);
        }
        else {
            val.setIdVolunteer(raterid);
        }
        model.addAttribute("valoration", val);
        return "valoration/add";
    }
}
