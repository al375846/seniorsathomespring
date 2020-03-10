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
import seniorsathome.seniorsathomespring.model.Company;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private CompanyDao companyDao;

    @Autowired
    public void setCopanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @RequestMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyDao.getCompanies());
        return "company/list";
    }

    @RequestMapping(value="/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("company") Company company,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "company/add";
        companyDao.addCompany(company);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{fiscalNumber}", method = RequestMethod.GET)
    public String editCompany(Model model, @PathVariable String fiscalNumber) {
        model.addAttribute("company", companyDao.getCompany(fiscalNumber));
        return "company/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("company") Company company,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "company/update";
        companyDao.updateCompany(company);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{fiscalNumber}")
    public String processDeleteCompany(@PathVariable String fiscalNumber) {
        companyDao.deleteCompany(fiscalNumber);
        return "redirect:../list";
    }
}

