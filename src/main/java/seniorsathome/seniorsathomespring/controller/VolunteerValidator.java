package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Volunteer;

import javax.validation.constraints.Null;


public class VolunteerValidator implements Validator{
    @Override
    public boolean supports(Class<?> cls) {
        return Volunteer.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Volunteer vol = (Volunteer)obj;
        if (vol.getName().trim().equals(""))
            errors.rejectValue("name", "Required",
                    "You must enter a name");
        if (vol.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required",
                    "You must enter a phone number");
        if (vol.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required",
                    "You must enter an email");
        if (vol.getAddress().trim().equals(""))
            errors.rejectValue("address", "Required",
                    "You must enter an address");
        if (vol.getUserName().trim().equals(""))
            errors.rejectValue("userName", "Required",
                    "You must enter a username");
        if (vol.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required",
                    "You must enter a password");

        if ((vol.getPassword().trim().equals("") == false) && (vol.getPassword().length() <= 4))
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
        CharSequence charSequence = "@";

        if ((!vol.getEmail().trim().contains(charSequence)) && (vol.getEmail().trim().equals("") == false))
            errors.rejectValue("email", "Incorrect Value", "It should be an email (contains @)");


        if (vol.getName().length()>50)
            errors.rejectValue("name", "Incorrect Value", "The name cannot be longer than 50 characters");
        if (vol.getPhoneNumber().length()>50)
            errors.rejectValue("phoneNumber", "Incorrect Value", "The phone number connot be longer than 10 characters");
        if (vol.getEmail().length()>50)
            errors.rejectValue("email", "Incorrect Value", "The mail cannot be longer than 50 characters");
        if (vol.getAddress().length()>50)
            errors.rejectValue("address", "Incorrect Value", "The address cannot be longer than 50 characters");
        if (vol.getUserName().length()>25)
            errors.rejectValue("userName", "Incorrect Value", "The username cannot be longer than 25 characters");
        if (vol.getPassword().length()>25)
            errors.rejectValue("password", "Incorrect Value", "The password cannot be longer than 25 characters");

        // Afegeix ací la validació per a Edat > 15 anys
    }
}
