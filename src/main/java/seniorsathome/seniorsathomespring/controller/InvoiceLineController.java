package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.InvoiceDao;
import seniorsathome.seniorsathomespring.dao.InvoiceLineDao;
import seniorsathome.seniorsathomespring.model.Invoice;
import seniorsathome.seniorsathomespring.model.InvoiceLine;

@Controller
@RequestMapping("/invoiceLine")
public class InvoiceLineController {

    private InvoiceLineDao invoiceLineDao;

    @Autowired
    public void setInvoiceDao(InvoiceLineDao invoiceLineDao) {
        this.invoiceLineDao=invoiceLineDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar

    @RequestMapping("/list")
    public String listInvoiceLines(Model model) {
        model.addAttribute("invoiceLines", invoiceLineDao.getInvoiceLines());
        return "invoiceLine/list";
    }

    @RequestMapping(value="/add")
    public String addInvoiceLine(Model model) {
        model.addAttribute("invoiceLine", new InvoiceLine());
        return "invoiceLine/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("invoiceLine") InvoiceLine invoiceLine,
                                   BindingResult bindingResult) {
        InvoiceLineValidator invoiceLineValidator = new InvoiceLineValidator();
        invoiceLineValidator.validate(invoiceLine, bindingResult);
        if (bindingResult.hasErrors())
            return "invoiceLine/add";
        invoiceLineDao.addInvoiceLine(invoiceLine);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{number_id}", method = RequestMethod.GET)
    public String editInvoiceLine(Model model, @PathVariable String number_id) {
        model.addAttribute("invoiceLine", invoiceLineDao.getInvoiceLine(number_id));
        return "invoiceLine/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("invoiceLine") InvoiceLine invoiceLine,
            BindingResult bindingResult) {
        InvoiceLineValidator invoiceLineValidator = new InvoiceLineValidator();
        invoiceLineValidator.validate(invoiceLine, bindingResult);
        if (bindingResult.hasErrors())
            return "invoiceLine/update";
        invoiceLineDao.updateInvoiceLine(invoiceLine);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{number_id}")
    public String processDeleteCompany(@PathVariable String number_id) {
        invoiceLineDao.deleteInvoiceLine(number_id);
        return "redirect:../list";
    }
}
