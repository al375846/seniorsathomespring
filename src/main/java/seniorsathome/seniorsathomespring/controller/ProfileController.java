package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping("/committee")
    public String loginCommittee(Model model) {
        return "profile/committee";
    }

    @RequestMapping("/beneficiary")
    public String loginBeneficiary(Model model) {
        return "profile/beneficiary";
    }

    @RequestMapping("/company")
    public String loginCompany(Model model) {
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
