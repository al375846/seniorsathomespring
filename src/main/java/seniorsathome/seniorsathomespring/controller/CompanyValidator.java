package seniorsathome.seniorsathomespring.controller;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Company;


public class CompanyValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Company.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Company company = (Company)obj;
        if (company.getFiscalNumber().trim().equals(""))
            errors.rejectValue("fiscalNumber", "Required", "You must enter a fiscal number");
        if (company.getResponsibleName().trim().equals(""))
            errors.rejectValue("responsibleName", "Required", "You must enter a responsible name");
        if (company.getPassword().length() <= 4)
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
    }
}
