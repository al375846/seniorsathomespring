package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Schedule;

public class ScheduleValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Schedule.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Schedule sch = (Schedule)obj;
        if (sch.getNumberid().trim().equals(""))
            errors.rejectValue("numberid", "Required",
                    "You must enter a number id");
        if (sch.getDay() == null)
            errors.rejectValue("day", "Required",
                    "You must enter a day");
        if (sch.getStarthour() == null)
            errors.rejectValue("starthour", "Required",
                    "You must enter a start hour");
        if (sch.getFinalhour() == null)
            errors.rejectValue("finalhour", "Required",
                    "You must enter a final hour");
        if (sch.getStatus() == null)
            errors.rejectValue("status", "Required",
                    "You must enter a status");
        if (sch.getVolunteerid().trim().equals(""))
            errors.rejectValue("volunteerid", "Required",
                    "You must enter a volunteer id");
        if (sch.getStarthour() != null && sch.getFinalhour() != null) {
            if (!(sch.getStarthour().isBefore(sch.getFinalhour())))
                errors.rejectValue("finalhour", "Incorrect Value",
                        "The final hour is before start hour");
        }
        if (sch.getNumberid().length()>10)
            errors.rejectValue("numberid", "Incorrect Value", "The number id cannot be longer than 10 characters");
        if (sch.getBeneficiaryid().length()>10)
            errors.rejectValue("beneficiaryid", "Incorrect Value", "The beneficiary id cannot be longer than 10 characters");
        if (sch.getVolunteerid().length()>10)
            errors.rejectValue("volunteerid", "Incorrect Value", "The volunteer id cannot be longer than 10 characters");
    }
}