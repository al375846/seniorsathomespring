package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.User;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return User.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {

        User user = (User)obj;

        /*Comprobaciones para asegurarnos de que se introducen todos los datos necesarios*/

        if (user.getUsername().trim().equals(""))
            errors.rejectValue("username", "Required", "You must enter a username");
        if (user.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required", "You must enter a password");

        /*Comprobaciones para asegurarnos de que los datos introducidos tienen las longitudes adecuadas*/

        if (user.getUsername().trim().length() > 25)
            errors.rejectValue("username", "Incorrect Value", "You must enter a username with less than 25 characters");
        if (user.getPassword().trim().length() > 40)
            errors.rejectValue("password", "Incorrect Value", "You must enter a password with less than 40 characters");
    }
}
