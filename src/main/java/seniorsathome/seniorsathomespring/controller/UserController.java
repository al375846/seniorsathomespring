package seniorsathome.seniorsathomespring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import seniorsathome.seniorsathomespring.dao.UserDao;
import seniorsathome.seniorsathomespring.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserDao userDao;

    @Autowired
    public void setSociDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/list")
    public String listSocis(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new User());
            session.setAttribute("nextUrl","/user/list");
            return "login";
        }
        model.addAttribute("users", userDao.listAllUsers());
        return "user/list";
    }
}
