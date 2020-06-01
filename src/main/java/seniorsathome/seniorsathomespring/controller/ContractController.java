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
import seniorsathome.seniorsathomespring.model.Company;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.Correo;
import seniorsathome.seniorsathomespring.model.ServiceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @RequestMapping(value = "/add")
    public String addContract(Model model) {
        model.addAttribute("contract", new Contract());
        model.addAttribute("companies", companyDao.getCompanies());
        return "contract/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contract") Contract contract,
                                   Model model,
                                   BindingResult bindingResult) {
        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("companies", companyDao.getCompanies());
            return "contract/add";
        }
        Company company = companyDao.getCompanyByName(contract.getCompanyID());
        contract.setCompanyID(company.getFiscalNumber());
        contractDao.addContract(contract);
        Correo.enviarMensajeSah(company.getEmail(), "Contract added", "A contract has been added to your company");
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{numberID}", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable String numberID) {
        Contract contract = contractDao.getContract(numberID);
        List<String> servicios = new ArrayList<String>();
        servicios.add("CATERING");
        servicios.add("CLEANING");
        servicios.add("NURSING");
        model.addAttribute("contract", contract);
        model.addAttribute("actualcompany", companyDao.getCompany(contract.getCompanyID()));
        model.addAttribute("actualservice", contract.getServiceType());
        model.addAttribute("companies", companyDao.getCompanies());
        model.addAttribute("services", servicios);
        return "contract/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("contract") Contract contract,
            Model model, BindingResult bindingResult) {
        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("companies", companyDao.getCompanies());
            return "contract/update";
        }
        Company company = companyDao.getCompanyByName(contract.getCompanyID());
        contract.setCompanyID(company.getFiscalNumber());
        System.out.println(contract.getCompanyID());
        Correo.enviarMensajeSah(company.getEmail(), "Contract updated", "Your contact has been updated");
        contractDao.updateContract(contract);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numberID}")
    public String processDeleteContract(@PathVariable String numberID) {
        contractDao.deleteContract(numberID);
        return "redirect:../list";
    }
}
