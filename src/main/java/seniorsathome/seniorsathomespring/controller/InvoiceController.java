package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.BeneficiaryDao;
import seniorsathome.seniorsathomespring.dao.InvoiceDao;
import seniorsathome.seniorsathomespring.dao.InvoiceLineDao;
import seniorsathome.seniorsathomespring.model.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceDao invoiceDao;
    private BeneficiaryDao beneficiaryDao;
    private InvoiceLineDao invoiceLineDao;

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao=invoiceDao;
    }

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao=beneficiaryDao;
    }

    @Autowired
    public void setInvoiceLineDao(InvoiceLineDao invoiceLineDao) {
        this.invoiceLineDao = invoiceLineDao;
    }


// Operacions: Crear, llistar, actualitzar, esborrar

    @RequestMapping("/list")
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceDao.getInvoices());
        return "invoice/list";
    }

    @RequestMapping(value="/add")
    public String addInvoice(Model model) {
        model.addAttribute("invoice", new Invoice());
        return "invoice/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("invoice") Invoice invoice,
                                   BindingResult bindingResult) {
        InvoiceValidator invoiceValidator = new InvoiceValidator();
        invoiceValidator.validate(invoice, bindingResult);
        if (bindingResult.hasErrors())
            return "invoice/add";
        invoiceDao.addInvoice(invoice);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{number_id}", method = RequestMethod.GET)
    public String editInvoice(Model model, @PathVariable String number_id) {
        model.addAttribute("invoice", invoiceDao.getInvoice(number_id));
        return "invoice/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("invoice") Invoice invoice,
            BindingResult bindingResult) {
        InvoiceValidator invoiceValidator = new InvoiceValidator();
        invoiceValidator.validate(invoice, bindingResult);
        if (bindingResult.hasErrors())
            return "invoice/update";
        invoiceDao.updateInvoice(invoice);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{number_id}")
    public String processDeleteCompany(@PathVariable String number_id) {
        invoiceDao.deleteInvoice(number_id);
        return "redirect:../list";
    }

    @RequestMapping(value = "/emit")
    public String generateBills() {
        List<Beneficiary> beneficiarios = beneficiaryDao.getBeneficiaries();
        for (Beneficiary beneficiary: beneficiarios) {
            List<Request> requests = beneficiaryDao.listActiveRequests(beneficiary.getIdentificationNumber());
            if (requests.size() > 0) {
                List<InvoiceLine> invoiceLines = Invoice.emitirFactura(beneficiary, requests, invoiceDao.getInvoices().size() + 1, invoiceLineDao.getInvoiceLines().size() + 1);
                double sumaTotal = 0;
                for (InvoiceLine invoiceLine : invoiceLines) {
                    sumaTotal += invoiceLine.getPrice();
                }
                Invoice invoice = new Invoice();
                invoice.setBeneficiaryID(beneficiary.getIdentificationNumber());
                invoice.setNumberID("I" + (invoiceDao.getInvoices().size() + 1));
                invoice.setReleaseDate(LocalDate.now());
                invoice.setPrice(sumaTotal);
                LocalDate dia = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
                invoice.setStartDate(dia);
                if (LocalDate.now().getMonthValue() == 12) {
                    dia = LocalDate.of(LocalDate.now().getYear() + 1, 1, 1);
                } else {
                    dia = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue() + 1, 1);
                }
                invoice.setFinalDate(dia);
                invoiceDao.addInvoice(invoice);
                for (InvoiceLine invoiceLine : invoiceLines) {
                    invoiceLineDao.addInvoiceLine(invoiceLine);
                }
                Correo.enviarMensajeSah(beneficiary.getEmail(), "Bill", "Your bill has been created and payed");
            }
        }
        return "redirect:list";
    }
}
