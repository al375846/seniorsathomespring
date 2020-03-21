package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.model.Company;
import seniorsathome.seniorsathomespring.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {

    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

        Company company = new Company();

        company.setFiscalNumber(rs.getString("fiscalNumber"));
        company.setName(rs.getString("name"));
        company.setEmail(rs.getString("email"));
        company.setResponsibleName(rs.getString("responsibleName"));
        company.setResponsiblePhoneNumber(rs.getString("responsiblePhoneNumber"));
        company.setResponsibleAddress(rs.getString("responsibleAddress"));
        company.setPhoneNumber(rs.getString("phoneNumber"));
        company.setUserName(rs.getString("userName"));
        company.setPassword(rs.getString("password"));

        return company;
    }

    @Controller
    @RequestMapping("/invoice")
    public static class InvoiceController {

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

        @RequestMapping(value="/add")
        public String addInvoice(Model model) {
            model.addAttribute("invoice", new Invoice());
            return "invoice/add";
        }

        @RequestMapping(value="/add", method= RequestMethod.POST)
        public String processAddSubmit(@ModelAttribute("invoice") Invoice invoice,
                                       BindingResult bindingResult) {
            if (bindingResult.hasErrors())
                return "invoice/add";
            invoiceDao.addInvoice(invoice);
            return "redirect:list";
        }

        @RequestMapping(value="/update/{numberId}", method = RequestMethod.GET)
        public String editInvoice(Model model, @PathVariable String numberId) {
            model.addAttribute("invoice", invoiceDao.getInvoice(numberId));
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

        @RequestMapping(value = "/delete/{numberID}")
        public String processDeleteCompany(@PathVariable String numberID) {
            invoiceDao.deleteInvoice(numberID);
            return "redirect:../../list";
        }
    }
}
