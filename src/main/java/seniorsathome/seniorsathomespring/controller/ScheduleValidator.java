package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Schedule;

public class ScheduleValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Schedule.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Schedule sch = (Schedule)obj;
        if (sch.getNumberid().trim().equals(""))
            errors.rejectValue("numberid", "Required",
                    "You must enter a value");
        if (sch.getDay() == null)
            errors.rejectValue("day", "Required",
                    "You must enter a value");
        if (sch.getStarthour() == null)
            errors.rejectValue("starthour", "Required",
                    "You must enter a value");
        if (sch.getFinalhour() == null)
            errors.rejectValue("finalhour", "Required",
                    "You must enter a value");
        if (sch.getStatus() == null)
            errors.rejectValue("status", "Required",
                    "You must enter a value");
        if (sch.getBeneficiaryid().trim().equals(""))
            errors.rejectValue("beneficiaryid", "Required",
                    "You must enter a value");
        if (sch.getVolunteerid().trim().equals(""))
            errors.rejectValue("volunteerid", "Required",
                    "You must enter a value");
        if(!(sch.getStarthour().isBefore(sch.getFinalhour())))
            errors.rejectValue("finalhour", "Incorrect Value",
                    "The final hour is before start hour");

    }
}