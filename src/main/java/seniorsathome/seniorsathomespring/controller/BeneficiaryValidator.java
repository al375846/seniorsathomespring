package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Beneficiary;

public class BeneficiaryValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Beneficiary.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        Beneficiary beneficiary = (Beneficiary) obj;
        if (beneficiary.getIdentificationNumber().trim().equals(""))
            errors.rejectValue("identificationNumber", "Required", "You must enter an identification number");
        if (beneficiary.getName().trim().equals(""))
            errors.rejectValue("name", "Required", "You must enter a name");
        if (beneficiary.getSurnames().trim().equals(""))
            errors.rejectValue("surnames", "Required", "You mus enter surnames");
        if (beneficiary.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required", "You must enter a phone number");
        if (beneficiary.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required", "You must enter an email");

        CharSequence charSequence = "@";
        if (!beneficiary.getEmail().trim().contains(charSequence))
            errors.rejectValue("email", "Incorrect Value", "Should be a Email (contains @)");
        if (beneficiary.getAddress().trim().equals(""))
            errors.rejectValue("address", "Required", "You must enter an address");
        if (beneficiary.getUserName().trim().equals(""))
            errors.rejectValue("userName", "Required", "You must enter a username");
        if (beneficiary.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required", "You must enter a password");
        if (beneficiary.getPassword().length() <= 4)
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
    }
}
