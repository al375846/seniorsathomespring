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
        if (vol.getIdNumber().trim().equals(""))
            errors.rejectValue("idNumber", "Required",
                    "You must enter a value");
        if (vol.getName().trim().equals(""))
            errors.rejectValue("name", "Required",
                    "You must enter a value");
        if (vol.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required",
                    "You must enter a value");
        if (vol.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required",
                    "You must enter a value");
        if (vol.getAddress().trim().equals(""))
            errors.rejectValue("address", "Required",
                    "You must enter a value");
        if (vol.getUserName().trim().equals(""))
            errors.rejectValue("userName", "Required",
                    "You must enter a value");
        if (vol.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required",
                    "You must enter a value");
        if (vol.getRequestDate() == null)
            errors.rejectValue("requestDate", "Required",
                    "You must enter a value");
        if (vol.getPassword().length() <= 4)
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
        CharSequence charSequence = "@";
        if (vol.getEmail().trim().contains(charSequence))
            errors.rejectValue("password", "Incorrect Value", "Should be a Email (contains @)");

        // Afegeix ací la validació per a Edat > 15 anys
    }
}
