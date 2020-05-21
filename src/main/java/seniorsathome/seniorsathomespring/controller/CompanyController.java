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
import seniorsathome.seniorsathomespring.dao.CompanyDao;
import seniorsathome.seniorsathomespring.dao.ContractDao;
import seniorsathome.seniorsathomespring.dao.RequestDao;
import seniorsathome.seniorsathomespring.model.*;

import javax.websocket.server.PathParam;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private RequestDao requestDao;
    private CompanyDao companyDao;
    private BeneficiaryDao beneficiaryDao;
    private ContractDao contractDao;

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao = beneficiaryDao;
    }

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @RequestMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyDao.getCompanies());
        return "company/list";
    }

    @RequestMapping(value = "/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("company") Company company,
                                   BindingResult bindingResult) {
        CompanyValidator companyValidator = new CompanyValidator();
        companyValidator.validate(company, bindingResult);
        if (bindingResult.hasErrors())
            return "company/add";
        companyDao.addCompany(company);
        Correo.enviarMensajeSah(company.getEmail(), "Register", "Your are now registerd. Username: " + company.getUserName() + " Password: " + company.getPassword());
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{fiscalNumber}", method = RequestMethod.GET)
    public String editCompany(Model model, @PathVariable String fiscalNumber) {
        model.addAttribute("company", companyDao.getCompany(fiscalNumber));
        return "company/update";
    }

    @RequestMapping(value = "/listRequest/{contractID}", method = RequestMethod.GET)
    public String listRequest(Model model, @PathVariable String contractID) {
        model.addAttribute("listRequests", requestDao.listRequestByContractId(contractID));
        return "company/listRequest";
    }

    @RequestMapping(value = "/updateRequest/{requestID}", method = RequestMethod.GET)
    public String updateRequest(Model model, @PathVariable String requestID) {
        model.addAttribute("updateRequest", requestDao.getRequest(requestID));
        return "company/updateRequest";
    }
    @RequestMapping(value = "/updateRequest", method = RequestMethod.POST)
    public String processUpdateRequestSubmit(
            @ModelAttribute("company") Request request,String monday,String tuesday,String wednesday,String thursday,String friday,String saturday,String sunday,
            BindingResult bindingResult,Model model) {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("updateRequest", requestDao.getRequest(request.getNumber_id()));
            return "company/updateRequest";}
        request.setDays(monday+tuesday+wednesday+thursday+friday+saturday+sunday);
        requestDao.updateRequest(request);
        model.addAttribute("listRequests", requestDao.listRequestByContractId(request.getContract_id()));
        Beneficiary beneficiary = beneficiaryDao.getBeneficiary(request.getBeneficiary_id());
        Contract contract = contractDao.getContract(request.getContract_id());
        Company company = companyDao.getCompany(contract.getCompanyID());
        Correo.enviarMensajeSah(company.getEmail(), beneficiary.getEmail(), "Days anh hour of request", "Your request is now accepted and we have assigned days and hour");
        return "company/listRequest";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("company") Company company,
            BindingResult bindingResult) {
        CompanyValidator companyValidator = new CompanyValidator();
        companyValidator.validate(company, bindingResult);
        if (bindingResult.hasErrors())
            return "company/update";
        companyDao.updateCompany(company);
        Correo.enviarMensajeSah(company.getEmail(), "Update", "Your profile has been updated. Username: " + company.getUserName() + " Password: " + company.getPassword());
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{fiscalNumber}")
    public String processDeleteCompany(@PathVariable String fiscalNumber) {
        companyDao.deleteCompany(fiscalNumber);
        return "redirect:../list";
    }
    @RequestMapping("/hour/{requestID}")
    public String Hour(@PathVariable String requestID , Time hora, Model model){
        Request req = requestDao.getRequest(requestID);
        LocalTime time = hora.toLocalTime();
        req.setStarthour(time);
        requestDao.updateRequest(req);
        model.addAttribute("listRequests", requestDao.listRequestByContractId(req.getContract_id()));
        return"company/listRequest";
    }

    @RequestMapping(value = "/monday/{requestID}")
    public String monday(@PathVariable String requestID, Model model) {
        Request req = requestDao.getRequest(requestID);
        String days = req.getDays();
        if (days == null){
            req.setDays("M");
            requestDao.updateRequest(req);
            model.addAttribute("listRequests", requestDao.listRequestByContractId(req.getContract_id()));
            return "company/listRequest";
        }
        if (days.contains("M")){
            days = days.replace("M","");
        }else {
            days = añadir(days,"M");
        }
        req.setDays(days);
        requestDao.updateRequest(req);
        model.addAttribute("listRequests", requestDao.listRequestByContractId(req.getContract_id()));
        return "company/listRequest";
    }
    public String añadir(String days , String letra){
        return days+letra;
    }
}

