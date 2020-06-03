package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Schedule;

import java.time.LocalDate;


public class ScheduleValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Schedule.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Schedule sch = (Schedule)obj;

        /*Comprobaciones para asegurarnos de que se introducen todos los datos necesarios*/

        if (sch.getDay() == null)
            errors.rejectValue("day", "Required", "You must enter a day");
        if (sch.getStarthour() == null)
            errors.rejectValue("starthour", "Required", "You must enter a start hour");
        if (sch.getFinalhour() == null)
            errors.rejectValue("finalhour", "Required", "You must enter a final hour");

        /*Comprobaciones para asegurarnos de que los datos introducidos tienen valores adecuados*/

        if (sch.getStarthour() != null &&  sch.getDay().isBefore(LocalDate.now().plusDays(1)))
            errors.rejectValue("day", "Incorrect Value", "The date cannot be before tomorrow");
        if (sch.getStarthour() != null && sch.getFinalhour() != null) {
            if (!(sch.getStarthour().isBefore(sch.getFinalhour())))
                errors.rejectValue("finalhour", "Incorrect Value","The final hour is before start hour");
        }
    }

}
