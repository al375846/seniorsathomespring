package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Contract;


public class ContractValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Contract.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contract contract = (Contract)obj;
        if (contract.getNumberID().trim().equals(""))
            errors.rejectValue("numberID", "Required", "You must enter an id");
        if (contract.getServiceType().trim().equals(""))
            errors.rejectValue("serviceType", "Required", "You must enter a service type");
        if (contract.getQuantity() <= 0)
            errors.rejectValue("quantity", "Incorrect Value", "The company must offer 1 service");
        if (contract.getPrice() < 0)
            errors.rejectValue("price", "Incorrect Value", "The price of service must be greater than 0");
        if (contract.getStartDate() == null)
            errors.rejectValue("startDate", "Required", "You must enter a start date");
        if (contract.getFinalDate() == null)
            errors.rejectValue("finalDate", "Required", "You must enter a final date");
        if (contract.getFinalDate() != null && contract.getStartDate() != null && contract.getFinalDate().isBefore(contract.getStartDate()))
            errors.rejectValue("finalDate", "Incorrect Value", "The final date must be after the start date");
    }
}
