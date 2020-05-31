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
        if (company.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required", "You must enter a password");
        if (company.getName().trim().equals(""))
            errors.rejectValue("name", "Required", "You must enter a name");
        if (company.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required", "You must enter an email");
        if (company.getResponsiblePhoneNumber().trim().equals(""))
            errors.rejectValue("responsiblePhoneNumber", "Required", "You must enter a phone number from the responsible");
        if (company.getResponsibleAddress().trim().equals(""))
            errors.rejectValue("responsibleAddress", "Required", "You must enter an address");
        if (company.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required", "You must enter a phone number");

        if (company.getPassword().length() <= 4)
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
        if (company.getPassword().trim().length() > 40)
            errors.rejectValue("password", "Incorrect Value", "You must enter a password with less than 40 characters");
    }
}
