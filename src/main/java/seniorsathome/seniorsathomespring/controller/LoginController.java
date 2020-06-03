package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.UserDao;
import seniorsathome.seniorsathomespring.model.User;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    /*Logea un usuario*/
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
        // Comprueba que el login sea correcto
        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        // Guardamos los datos del usuario autenticado en la sesión
        session.setAttribute("user", user);

        String type = userDao.userType(user);
        if (!type.equals("")) {
            session.setAttribute("type", type);
            return "redirect:/profile/" + type;
        }

        if (session.getAttribute("nextUrl") != null) {
            String url = session.getAttribute("nextUrl").toString();
            session.removeAttribute("nextUrl");
            return "redirect:" + url;
        }

        // Vuelve a la página principal
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

        String type = userDao.userType(user);
        if (!type.equals("")) {
            session.setAttribute("type", type);
            return "redirect:/profile/" + type;
        }

        if (session.getAttribute("nextUrl") != null) {
            String url = session.getAttribute("nextUrl").toString();
            session.removeAttribute("nextUrl");
            return "redirect:" + url;
        }

        return "redirect:/";

    }

    /*Salir de la sesión*/
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
