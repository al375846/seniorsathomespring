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
import seniorsathome.seniorsathomespring.model.Invoice;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceDao invoiceDao;

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao=invoiceDao;
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
        /*if (bindingResult.hasErrors())
            return "invoice/add";*/
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
        if (bindingResult.hasErrors())
            return "invoice/update";
        invoiceDao.updateInvoice(invoice);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{number_id}")
    public String processDeleteCompany(@PathVariable String number_id) {
        invoiceDao.deleteInvoice(number_id);
        return "redirect:../../list";
    }
}
