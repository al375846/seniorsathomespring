package seniorsathome.seniorsathomespring.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seniorsathome.seniorsathomespring.model.Invoice;

public class InvoiceValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Invoice.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Invoice invoice = (Invoice)obj;

        if (invoice.getReleaseDate() == null)
            errors.rejectValue("releaseDate", "Required", "You must enter a release date");

        if (invoice.getStartDate() == null)
            errors.rejectValue("startDate", "Required", "You must enter a start date");

        if (invoice.getFinalDate() == null)
            errors.rejectValue("finalDate", "Required", "You must enter a final date");

        if (invoice.getPrice() == 0 || String.valueOf(invoice.getPrice()).trim().equals(""))
            errors.rejectValue("price", "Required", "You must enter a price");

        if (invoice.getBeneficiaryID().trim().equals(""))
            errors.rejectValue("beneficiaryID", "Required", "You must enter a beneficiaryID");
    }
}
