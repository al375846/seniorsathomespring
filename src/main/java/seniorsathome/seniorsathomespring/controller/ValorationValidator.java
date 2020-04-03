package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Valoration;

public class ValorationValidator implements Validator{
    @Override
    public boolean supports(Class<?> cls) {
        return Valoration.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Valoration val = (Valoration) obj;
        if (val.getIdValoration().trim().equals(""))
            errors.rejectValue("idValoration", "Required",
                    "You must enter a valoration id");
        if (val.getComment().trim().equals(""))
            errors.rejectValue("comment", "Required",
                    "You must enter a comment");
        if (val.getIdBeneficiary().trim().equals(""))
            errors.rejectValue("idBeneficiary", "Required",
                    "You must enter a beneficiary id");

        if (val.getIdVolunteer().trim().equals("")&&val.getIdCompany().trim().equals("")){
            errors.rejectValue("idVolunteer", "Required",
                    "You must enter a company or a volunteer");
        }
        if (!(val.getIdVolunteer().trim().equals(""))&&!(val.getIdCompany().trim().equals(""))){
            errors.rejectValue("idVolunteer", "Required",
                    "You must enter a company or a volunteer ONLY");
        }
        //Solo pongan un voluntario o una compañia

        if (val.getRate()<= 0 && val.getRate()>5)
            errors.rejectValue("rate", "Incorrect Value",
                    "The value should be between 1 to 5");
        if (val.getComment().length()>255)
            errors.rejectValue("rate", "Incorrect Value",
                    "The comment cannot be longer than 255 characters");

        if (val.getIdValoration().length()>10)
            errors.rejectValue("idValoration", "Incorrect Value", "The valoration id cannot be longer than 10 characters");
        if(!(val.getIdVolunteer().trim().equals(""))||!(val.getIdCompany().trim().equals(""))){
            if (val.getIdVolunteer().length()>10)
                errors.rejectValue("idVolunteer", "Incorrect Value", "The volunteer id cannot be longer than 10 characters");
            if (val.getIdCompany().length()>10)
                errors.rejectValue("idCompany", "Incorrect Value", "The company id cannot be longer than 10 characters");
        }
        if (val.getIdBeneficiary().length()>10)
            errors.rejectValue("idBeneficiary", "Incorrect Value", "The beneficiary id cannot be longer than 10 characters");
        // Afegeix ací la validació per a Edat > 15 anys
    }
}