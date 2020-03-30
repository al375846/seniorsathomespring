package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.InvoiceLine;

public class InvoiceLineValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return InvoiceLine.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        InvoiceLine invoiceLine = (InvoiceLine)obj;

        if (invoiceLine.getPrice() == 0 || String.valueOf(invoiceLine.getPrice()).trim().equals(""))
            errors.rejectValue("price", "Required", "You must enter a price");

    }
}
