package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Valoration;

public class ValorationValidator implements Validator{
    @Override
    public boolean supports(Class<?> cls) {
        return Valoration.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Valoration val = (Valoration) obj;
        if (val.getComment().trim().equals(""))
            errors.rejectValue("comment", "Required",
                    "You must enter a comment");

        if (val.getRate()<= 0 && val.getRate()>5)
            errors.rejectValue("rate", "Incorrect Value",
                    "The value should be between 1 to 5");
        if (val.getComment().length()>255)
            errors.rejectValue("rate", "Incorrect Value",
                    "The comment cannot be longer than 255 characters");

    }
}