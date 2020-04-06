package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Request;


public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Request.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Request request = (Request) obj;
        if (request.getNumber_id().trim().equals(""))
            errors.rejectValue("number_id", "Required", "You must enter an identification number");
        if (request.getStatus().trim().equals(""))
            errors.rejectValue("status", "Required", "You must enter the status of the request");
        if (request.getType().trim().equals(""))
            errors.rejectValue("type", "Required", "You must enter the type of service");
        if (request.getStart_date() == null)
            errors.rejectValue("startDate", "Required", "You must enter a start date");
        if (request.getFinal_date() == null)
            errors.rejectValue("finalDate", "Required", "You must enter a final date");
        if (request.getFinal_date() != null && request.getStart_date() != null && request.getFinal_date().isBefore(request.getStart_date()))
            errors.rejectValue("finalDate", "Incorrect Value", "The final date must be after the start date");
        if (request.getBeneficiary_id() == null)
            errors.rejectValue("beneficiary_id", "Required", "You must enter a beneficiary id");
        if (request.getContract_id() == null)
            errors.rejectValue("contract_id", "Required", "You must enter a contract id");
        if (request.getComments().length()>255)
            errors.rejectValue("userName", "Incorrect Value", "The comment cannot be longer than 255 characters");
    }

}
