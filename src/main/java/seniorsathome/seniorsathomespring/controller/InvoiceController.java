package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
            return "invoices/list";
        }

}
