package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import seniorsathome.seniorsathomespring.dao.UserDao;
import seniorsathome.seniorsathomespring.model.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") User user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user);

        if (session.getAttribute("nextUrl") != null) {
            String url = session.getAttribute("nextUrl").toString();
            session.removeAttribute("nextUrl");
            return "redirect:" + url;
        }

        // Torna a la pàgina principal
        return "redirect:/";
    }

    @RequestMapping(value="/loginpopup", method= RequestMethod.POST)
    public String checkPopupLogin(User user, HttpSession session){
        if (user.getPassword().trim().equals("") || user.getUsername().trim().equals("")) {
            return "redirect:/login";
        }
        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            return "redirect:/login";
        }

        session.setAttribute("user", user);

        if (session.getAttribute("nextUrl") != null) {
            String url = session.getAttribute("nextUrl").toString();
            session.removeAttribute("nextUrl");
            return "redirect:" + url;
        }

        return "redirect:/";

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
