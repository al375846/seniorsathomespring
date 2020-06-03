package seniorsathome.seniorsathomespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavController {

    /*Redirige a la página Servicios*/
    @RequestMapping("/others/services")
    public String showServices(Model model) {
        return "/others/services";
    }

    /*Redirige a la página Nuestra misión*/
    @RequestMapping("/others/mission")
    public String showMission(Model model) {
        return "/others/mission";
    }

    /*Redirige a la página Nuestra historia*/
    @RequestMapping("/others/history")
    public String showHistory(Model model) {
        return "/others/history";
    }

    /*Redirige a la página Contactanos*/
    @RequestMapping("/others/contact")
    public String showContact(Model model) {
        return "/others/contact";
    }
}
