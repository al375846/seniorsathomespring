package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.CompanyDao;
import seniorsathome.seniorsathomespring.dao.ContractDao;
import seniorsathome.seniorsathomespring.model.Company;
import seniorsathome.seniorsathomespring.model.User;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

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

    @RequestMapping("/committee")
    public String loginCommittee(Model model) {
        return "profile/committee";
    }

    @RequestMapping("/beneficiary")
    public String loginBeneficiary(Model model) {
        return "profile/beneficiary";
    }

    @RequestMapping("/company")
    public String loginCompany(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            model.addAttribute("contracts", contractDao.getContracts());
        }else{
            String nombre = user.getUsername();
            Company com = companyDao.getCompanyByUserName(nombre);
            model.addAttribute("usuario",com.getName());
            model.addAttribute("contracts",contractDao.inicioSesion(com.getFiscalNumber()));
        }
        return "profile/company";
    }

    @RequestMapping("/socialworker")
    public String loginSocialWorker(Model model) {
        return "profile/socialworker";
    }

    @RequestMapping("/volunteer")
    public String loginVolunteer(Model model) {
        return "profile/volunteer";
    }
}
