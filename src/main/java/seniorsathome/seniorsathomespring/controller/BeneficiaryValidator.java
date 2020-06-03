package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Beneficiary;

import java.util.List;

public class BeneficiaryValidator implements Validator {

    List<String> usuarios;

    BeneficiaryValidator(List<String> lista) {
        this.usuarios = lista;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Beneficiary.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        Beneficiary beneficiary = (Beneficiary) obj;
        if (beneficiary.getName().trim().equals(""))
            errors.rejectValue("name", "Required", "You must enter a name");
        if (beneficiary.getSurnames().trim().equals(""))
            errors.rejectValue("surnames", "Required", "You must enter a surname");
        if (beneficiary.getPhoneNumber().trim().equals(""))
            errors.rejectValue("phoneNumber", "Required", "You must enter a phone number");
        if (beneficiary.getEmail().trim().equals(""))
            errors.rejectValue("email", "Required", "You must enter an email");
        if (beneficiary.getAccount().trim().equals(""))
            errors.rejectValue("account", "Required", "You must enter an account");


        CharSequence charSequence = "@";
        if ((!beneficiary.getEmail().trim().contains(charSequence)) && (beneficiary.getEmail().trim().equals("") == false))
            errors.rejectValue("email", "Incorrect Value", "It should be an email (contains @)");
        if (beneficiary.getAddress().trim().equals(""))
            errors.rejectValue("address", "Required", "You must enter an address");
        if (beneficiary.getUserName().trim().equals(""))
            errors.rejectValue("userName", "Required", "You must enter a username");
        if (usuarios.contains(beneficiary.getUserName().trim()))
            errors.rejectValue("userName", "Incorrect value", "Username already taken");
        if (beneficiary.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required", "You must enter a password");
        if ((beneficiary.getPassword().length() <= 4) && (beneficiary.getPassword().trim().equals("") == false))
            errors.rejectValue("password", "Incorrect Value", "The password must have more than 4 characters");
        if (beneficiary.getName().length()>50)
            errors.rejectValue("name", "Incorrect Value", "The name cannot be longer than 50 characters");
        if (beneficiary.getSurnames().length()>50)
            errors.rejectValue("surnames", "Incorrect Value", "The surnames cannot be longer than 50 characters");
        if (beneficiary.getPhoneNumber().length()>10)
            errors.rejectValue("phoneNumber", "Incorrect Value", "The phone number cannot be longer than 10 characters");
        if (beneficiary.getEmail().length()>50)
            errors.rejectValue("email", "Incorrect Value", "The mail cannot be longer than 50 characters");
        if (beneficiary.getAddress().length()>50)
            errors.rejectValue("address", "Incorrect Value", "The address cannot be longer than 50 characters");
        if (beneficiary.getUserName().length()>25)
            errors.rejectValue("userName", "Incorrect Value", "The username cannot be longer than 25 characters");
        if ((beneficiary.getPassword().length()>40) && (beneficiary.getPassword().trim().equals("") == false))
            errors.rejectValue("password", "Incorrect Value", "The password cannot be longer than 40 characters");
        if ((beneficiary.getAccount().length()!=24) && (beneficiary.getAccount().trim().equals("") == false))
            errors.rejectValue("account", "Incorrect Value", "The account cannot be different from 24 characters");
    }
}
