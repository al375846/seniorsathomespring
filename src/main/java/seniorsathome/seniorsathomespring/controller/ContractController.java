package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import seniorsathome.seniorsathomespring.dao.CompanyDao;
import seniorsathome.seniorsathomespring.dao.ContractDao;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.ServiceType;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractDao contractDao;
    private CompanyDao companyDao;

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @RequestMapping("/list")
    public String listContracts(Model model) {
        model.addAttribute("contracts", contractDao.getContracts());
        return "contract/list";
    }

    @RequestMapping(value="/add")
    public String addContract(Model model) {
        model.addAttribute("contract", new Contract());
        model.addAttribute("companies", companyDao.getCompanies());
        return "contract/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contract") Contract contract,
                                   Model model,
                                   BindingResult bindingResult) {
        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("companies", companyDao.getCompanies());
            return "contract/add";
        }
        contractDao.addContract(contract);
        return "redirect:popup";
    }
    @RequestMapping("/popup")
    public String contractPopup(Model model) {
        return "contract/popup";
    }

    @RequestMapping(value="/update/{numberID}", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable String numberID) {
        model.addAttribute("contract", contractDao.getContract(numberID));
        model.addAttribute("companies", companyDao.getCompanies());
        return "contract/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("contract") Contract contract,
            Model model, BindingResult bindingResult) {
        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("companies", companyDao.getCompanies());
            return "contract/update";
        }
        contractDao.updateContract(contract);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numberID}")
    public String processDeleteContract(@PathVariable String numberID) {
        contractDao.deleteContract(numberID);
        return "redirect:../list";
    }
}
