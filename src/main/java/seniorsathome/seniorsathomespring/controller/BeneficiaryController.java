package seniorsathome.seniorsathomespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.BeneficiaryDao;
import seniorsathome.seniorsathomespring.model.Beneficiary;

@Controller
public class BeneficiaryController {

    BeneficiaryDao beneficiaryDao;

    @RequestMapping("/list")
    public String listBeneficiaries(Model model) {
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiaries());
        return "beneficiary/list";
    }

    @RequestMapping(value="/add")
    public String addBeneficiary(Model model) {
        model.addAttribute("beneficiary", new Beneficiary());
        return "beneficiary/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("benefiaciary") Beneficiary beneficiary,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiary/add";
        beneficiaryDao.addBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{identificationNumber}", method = RequestMethod.GET)
    public String editBeneficiary(Model model, @PathVariable String identificationNumber) {
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiary(identificationNumber));
        return "beneficiary/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("beneficiary") Beneficiary beneficiary,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiary/update";
        beneficiaryDao.updateBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{identificationNumber}")
    public String processDeleteBeneficiary(@PathVariable String identificationNumber) {
        beneficiaryDao.deleteBeneficiary(identificationNumber);
        return "redirect:../../list";
    }

}
