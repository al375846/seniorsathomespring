package seniorsathome.seniorsathomespring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.UserDao;
import seniorsathome.seniorsathomespring.dao.VolunteerDao;
import seniorsathome.seniorsathomespring.model.Correo;
import seniorsathome.seniorsathomespring.model.User;
import seniorsathome.seniorsathomespring.model.Volunteer;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {
    private VolunteerDao volunteerDao;
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setVolunteerDao(VolunteerDao a) {
        this.volunteerDao = a;
    }

    /*Listar todos los voluntarios*/
    @RequestMapping("/list")
    public String listVolunteers(Model model) {
        model.addAttribute("volunteers", volunteerDao.getVolunteers());
        return "volunteer/list";
    }

    /*Listar todos los voluntarios que están a la espera de que se les acepte o rechace su solicitud*/
    @RequestMapping("/listunsolved")
    public String listVolunteersUnsolved(Model model) {
        model.addAttribute("volunteers", volunteerDao.getVolunteersUnsolved());
        return "volunteer/listunsolved";
    }

    /*Añadir voluntario*/
    @RequestMapping(value="/add")
    public String addVolunteer(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer/add";
    }


    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer,
                                   BindingResult bindingResult) {
        VolunteerValidator volValidator = new VolunteerValidator(userDao.listAllUsersName());
        volValidator.validate(volunteer, bindingResult);
        //si hay algún error en los campos introducidos, vuelve a solicitar introducir la información
        if (bindingResult.hasErrors())
            return "volunteer/add";
        volunteerDao.addVolunteer(volunteer);
        //mensaje de respuesta con el resultado del registro
        Correo.enviarMensajeSah(volunteer.getEmail(), "Register", "You have applied correctly");
        return "/home";
    }

    /*Actualizar voluntario. Si no se ha introducido información en el campo de nueva contraseña, se queda la anterior contraseña*/
    @RequestMapping(value="/update/{idNumber}", method = RequestMethod.GET)
    public String editVolunteer(Model model, @PathVariable String idNumber) {
        model.addAttribute("volunteer", volunteerDao.getVolunteer(idNumber));
        return "volunteer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(HttpSession session,
                                      @ModelAttribute("volunteer") Volunteer v, String newPassword,
                                      BindingResult bindingResult) {
        User user = (User)session.getAttribute("user");
        Volunteer vol = volunteerDao.getVolunteer(v.getIdNumber());
        Boolean changePass= true;
        if ( newPassword == "" ){
            v.setPassword(vol.getPassword());
            changePass = false;
        }
        else {
            v.setPassword(newPassword);
        }
        VolunteerValidator volValidator = new VolunteerValidator(userDao.listAllUsersName());
        volValidator.validate(v, bindingResult);

        v.setRequestDate(vol.getRequestDate());
        v.setApprovalDate(vol.getApprovalDate());
        v.setStatus(vol.getStatus());
        //si hay algún error en los campos introducidos, vuelve a solicitar introducir la información
        if (bindingResult.hasErrors())
            return "volunteer/update";
        //actualizar voluntario con nueva contraseña
        if(changePass)
            volunteerDao.updateVolunteer(v);
        //actualizar voluntario con la misma contraseña
        else
            volunteerDao.updateVolunteerWithoutEncription(v);
        vol = volunteerDao.getVolunteer(v.getIdNumber());
        if (vol.getUserName() != user.getUsername() ){
            user.setUsername(vol.getUserName());
            user.setPassword(vol.getPassword());
            session.setAttribute("user",user);
        }
        //Mensaje con el resultado de la operación
        Correo.enviarMensajeSah(v.getEmail(), "Update", "Your register has been updated");
        return "redirect:/profile/volunteer";
    }

    /*Eliminar voluntario*/
    @RequestMapping(value = "/delete/{idNumber}")
    public String processDeleteVolunteer(@PathVariable String idNumber) {
        volunteerDao.deleteVolunteer(idNumber);
        return "redirect:../list";
    }

    /*Aceptar voluntario*/
    @RequestMapping(value = "/accept/{idNumber}")
    public String acceptVolunteer(@PathVariable String idNumber) {
        Volunteer v = volunteerDao.getVolunteer(idNumber);
        v.setStatus("APPROVED");
        v.setApprovalDate(LocalDate.now());
        volunteerDao.updateVolunteerWithoutEncription(v);
        //Mensaje con el resultado de la operación, en este caso aceptado
        Correo.enviarMensajeSah(v.getEmail(), "Register accepted", "Your register has been accepted");
        return "redirect:/volunteer/listunsolved";
    }

    /*Rechazar voluntario*/
    @RequestMapping(value = "/reject/{idNumber}")
    public String rejectVolunteer(@PathVariable String idNumber) {
        Volunteer v = volunteerDao.getVolunteer(idNumber);
        v.setStatus("REJECTED");
        volunteerDao.updateVolunteerWithoutEncription(v);
        //Mensaje con el resultado de la operación, en este caso denegado
        Correo.enviarMensajeSah(v.getEmail(), "Register denied", "Your register has been denied");
        return "redirect:/volunteer/listunsolved";
    }
}
