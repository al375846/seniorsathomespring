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
        if (company.getResponsiblePhoneNumber().trim().equals(""))
            errors.rejectValue("responsiblePhoneNumber", "Required", "You must enter a responsible phone number");
        if (company.getResponsibleAddress().trim().equals(""))
            errors.rejectValue("responsibleAddress", "Required", "You must enter a responsible address");
        if (company.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required", "You must enter a phone number");
        if (company.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required", "You must enter a password");
        if (company.getName().trim().equals(""))
            errors.rejectValue("name", "Required", "You must enter a name");
        if (company.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required", "You must enter an email");
        if (company.getUserName().trim().equals(""))
            errors.rejectValue("userName", "Required", "You must enter a user name");

        if (company.getPassword().length() <= 4 && company.getPassword().trim().equals("") == false)
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");

        CharSequence charSequence = "@";
        if ((!company.getEmail().trim().contains(charSequence)) && (company.getEmail().trim().equals("") == false))
            errors.rejectValue("email", "Incorrect Value", "It should be an email (contains @)");
        if (company.getName().length()>50)
            errors.rejectValue("name", "Incorrect Value", "The name cannot be longer than 50 characters");
        if (company.getResponsibleName().length()>50)
            errors.rejectValue("responsibleName", "Incorrect Value", "The name cannot be longer than 50 characters");
        if (company.getPhoneNumber().length()>10)
            errors.rejectValue("phoneNumber", "Incorrect Value", "The phone number cannot be longer than 10 characters");
        if (company.getEmail().length()>50)
            errors.rejectValue("email", "Incorrect Value", "The mail cannot be longer than 50 characters");
        if (company.getResponsibleAddress().length()>50)
            errors.rejectValue("address", "Incorrect Value", "The address cannot be longer than 50 characters");
        if (company.getUserName().length()>25)
            errors.rejectValue("userName", "Incorrect Value", "The username cannot be longer than 25 characters");
        if (company.getPassword().length()>40)
            errors.rejectValue("password", "Incorrect Value", "The password cannot be longer than 40 characters");

    }
}
