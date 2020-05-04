package seniorsathome.seniorsathomespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavController {


    @RequestMapping("/others/services")
    public String showServices(Model model) {
        return "/others/services";
    }
    @RequestMapping("/others/mission")
    public String showMission(Model model) {
        return "/others/mission";
    }
    @RequestMapping("/others/objetives")
    public String showObjetives(Model model) {
        return "/others/objetives";
    }
    @RequestMapping("/others/history")
    public String showHistory(Model model) {
        return "/others/history";
    }
    @RequestMapping("/others/contact")
    public String showContact(Model model) {
        return "/others/contact";
    }
}
