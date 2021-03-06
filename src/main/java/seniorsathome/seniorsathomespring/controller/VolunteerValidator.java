package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Volunteer;

import javax.validation.constraints.Null;
import java.util.List;


public class VolunteerValidator implements Validator{

    List<String> users;
    VolunteerValidator(List<String> todos) {
        users = todos;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Volunteer.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Volunteer vol = (Volunteer)obj;

        /*Comprobaciones para asegurarnos de que se introducen todos los datos necesarios*/

        if (vol.getName().trim().equals(""))
            errors.rejectValue("name", "Required","You must enter a name");
        if (vol.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required", "You must enter a phone number");
        if (vol.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required", "You must enter an email");
        if (vol.getAddress().trim().equals(""))
            errors.rejectValue("address", "Required", "You must enter an address");
        if (vol.getUserName().trim().equals(""))
            errors.rejectValue("userName", "Required", "You must enter a username");
        if (vol.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required","You must enter a password");

        /*Comprobaciones para asegurarnos de que los datos introducidos tienen valores y longitudes adecuadas*/

        if ((vol.getPassword().length() <= 4) && (vol.getPassword().trim().equals("") == false))
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
        CharSequence charSequence = "@";
        if ((!vol.getEmail().trim().contains(charSequence)) && (vol.getEmail().trim().equals("") == false))
            errors.rejectValue("email", "Incorrect Value", "It should be an email (contains @)");
        if (vol.getName().length()>50)
            errors.rejectValue("name", "Incorrect Value", "The name cannot be longer than 50 characters");
        if (vol.getPhoneNumber().length()>10)
            errors.rejectValue("phoneNumber", "Incorrect Value", "The phone number cannot be longer than 10 characters");
        if (vol.getEmail().length()>50)
            errors.rejectValue("email", "Incorrect Value", "The mail cannot be longer than 50 characters");
        if (vol.getAddress().length()>50)
            errors.rejectValue("address", "Incorrect Value", "The address cannot be longer than 50 characters");
        if (vol.getUserName().length()>25)
            errors.rejectValue("userName", "Incorrect Value", "The username cannot be longer than 25 characters");
        if (users.contains(vol.getUserName().trim()))
            errors.rejectValue("userName", "Incorrect Value", "Username already taken");
        if ((vol.getPassword().length()>40) && (vol.getPassword().trim().equals("") == false))
            errors.rejectValue("password", "Incorrect Value", "The password cannot be longer than 40 characters");
    }
}
