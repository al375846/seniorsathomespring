package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Request;

public class ServiceValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Request.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Request request = (Request) obj;
        if (request.getType().trim().equals(""))
            errors.rejectValue("type", "Required", "You must enter the type of service");
        if (request.getStart_date() == null)
            errors.rejectValue("start_date", "Required", "You must enter a start date");

        if (request.getFinal_date() == null) {
            errors.rejectValue("final_date", "Required", "You must enter a final date");
        } else if (request.getFinal_date().compareTo(request.getStart_date()) < 0){
            errors.rejectValue("final_date", "Incorrect Value", "Start date must be previous than the final date");
        }

        if (request.getComments().length()>255)
            errors.rejectValue("comments", "Incorrect Value", "The comment cannot be longer than 255 characters");
    }

}