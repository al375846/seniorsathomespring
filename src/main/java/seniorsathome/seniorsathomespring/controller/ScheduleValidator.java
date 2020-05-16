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
        if (sch.getDay() == null)
            errors.rejectValue("day", "Required",
                    "You must enter a day");
        if (sch.getStarthour() == null)
            errors.rejectValue("starthour", "Required",
                    "You must enter a start hour");
        if (sch.getFinalhour() == null)
            errors.rejectValue("finalhour", "Required",
                    "You must enter a final hour");
        if (sch.getStarthour() != null && sch.getFinalhour() != null) {
            if (!(sch.getStarthour().isBefore(sch.getFinalhour())))
                errors.rejectValue("finalhour", "Incorrect Value",
                        "The final hour is before start hour");
        }
    }
}