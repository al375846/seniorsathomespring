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

        /*Comprobaciones para asegurarnos de que se introducen todos los datos necesarios*/

        if (invoiceLine.getNumberID().trim().equals(""))
            errors.rejectValue("numberID", "Required", "You must enter an identification number");
        if (invoiceLine.getPrice() == 0 || String.valueOf(invoiceLine.getPrice()).trim().equals(""))
            errors.rejectValue("price", "Required", "You must enter a price");
        if (invoiceLine.getRequestID().trim().equals(""))
            errors.rejectValue("requestID", "Required", "You must enter a request identification");
        if (invoiceLine.getInvoiceID().trim().equals(""))
            errors.rejectValue("invoiceID", "Required", "You must enter an invoice identification");
    }
}
