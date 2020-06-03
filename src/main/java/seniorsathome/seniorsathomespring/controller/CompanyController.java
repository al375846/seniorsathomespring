package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import seniorsathome.seniorsathomespring.dao.*;
import seniorsathome.seniorsathomespring.model.*;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.time.LocalTime;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private RequestDao requestDao;
    private CompanyDao companyDao;
    private BeneficiaryDao beneficiaryDao;
    private ContractDao contractDao;
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

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

    //Lista las compañias
    @RequestMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyDao.getCompanies());
        return "company/list";
    }

    //sirve para cuando añades una compañia saber cual compañia
    @RequestMapping(value = "/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company/add";
    }
    //añade una compñia
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("company") Company company,
                                   BindingResult bindingResult) {
        CompanyValidator companyValidator = new CompanyValidator(userDao.listAllUsersName());
        companyValidator.validate(company, bindingResult);
        if (bindingResult.hasErrors())
            return "company/add";
        companyDao.addCompany(company);
        Correo.enviarMensajeSah(company.getEmail(), "Register", "Your are now registered. Username: " + company.getUserName() + " Password: " + company.getPassword());
        return "redirect:list";
    }

    //sirve para sacar la informacion de la compañia para su modificación
    @RequestMapping(value = "/update/{fiscalNumber}", method = RequestMethod.GET)
    public String editCompany(Model model, @PathVariable String fiscalNumber) {
        model.addAttribute("company", companyDao.getCompany(fiscalNumber));
        return "company/update";
    }

    //lista todas sus peticiones asignadas a el contrato
    @RequestMapping(value = "/listRequest/{contractID}", method = RequestMethod.GET)
    public String listRequest(Model model, @PathVariable String contractID) {
        model.addAttribute("listRequests", requestDao.listRequestByContractId(contractID));
        return "company/listRequest";
    }

    //lista todos sus contratos
    @RequestMapping(value = "/listContracts", method = RequestMethod.GET)
    public String listContracts(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null){
            model.addAttribute("contracts", contractDao.getContracts());
        }else{
            String name = user.getUsername();
            Company company = companyDao.getCompanyByUserName(name);
            model.addAttribute("contracts",contractDao.getContractsCompany(company.getFiscalNumber()));
        }
        return "company/listContracts";
    }

    //sirve para sacar los datos de la peticion a modificar
    @RequestMapping(value = "/updateRequest/{requestID}", method = RequestMethod.GET)
    public String updateRequest(Model model, @PathVariable String requestID) {
        model.addAttribute("updateRequest", requestDao.getRequest(requestID));
        return "company/updateRequest";
    }
    //sirve para modificar una peticion en cuanto a los dias y las horas
    @RequestMapping(value = "/updateRequest", method = RequestMethod.POST)
    public String processUpdateRequestSubmit(
            @ModelAttribute("company") Request request,String monday,String tuesday,String wednesday,String thursday,String friday,String saturday,String sunday,
            BindingResult bindingResult,Model model) {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("updateRequest", requestDao.getRequest(request.getNumber_id()));
            return "company/updateRequest";}
        String dias = "";
        dias = (monday != null) ? dias + monday: dias;
        dias = (tuesday != null) ? dias + tuesday: dias;
        dias = (wednesday != null) ? dias + wednesday: dias;
        dias = (thursday != null) ? dias + thursday: dias;
        dias = (friday != null) ? dias + friday: dias;
        dias = (saturday != null) ? dias + saturday: dias;
        dias = (sunday != null) ? dias + sunday: dias;
        request.setDays(dias);
        requestDao.updateRequest(request);
        model.addAttribute("listRequests", requestDao.listRequestByContractId(request.getContract_id()));
        Beneficiary beneficiary = beneficiaryDao.getBeneficiary(request.getBeneficiary_id());
        Contract contract = contractDao.getContract(request.getContract_id());
        Company company = companyDao.getCompany(contract.getCompanyID());
        Correo.enviarMensajeSah(beneficiary.getAddress(),"Schedule of a service ",dias + request.getStarthour() + "days and hour that " + company.getName() + "will do the job");
        Correo.enviarMensajeSah(company.getEmail(), beneficiary.getEmail(), "Days anh hour of request", "Your request is now accepted and we have assigned days and hour");
        return "company/listRequest";
    }

    //sirve para modificar una compañia
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("company") Company company,
            BindingResult bindingResult) {
        CompanyValidator companyValidator = new CompanyValidator(userDao.listAllUsersName());
        companyValidator.validate(company, bindingResult);
        if (bindingResult.hasErrors())
            return "company/update";
        companyDao.updateCompany(company);
        Correo.enviarMensajeSah(company.getEmail(), "Update", "Your profile has been updated. Username: " + company.getUserName() + " Password: " + company.getPassword());
        return "redirect:list";
    }

    //para borrar una compañia por su numero fiscal
    @RequestMapping(value = "/delete/{fiscalNumber}")
    public String processDeleteCompany(@PathVariable String fiscalNumber) {
        companyDao.deleteCompany(fiscalNumber);
        return "redirect:../list";
    }

    //Le añade la hora a una peticion
    @RequestMapping("/hour/{requestID}")
    public String Hour(@PathVariable String requestID , Time hora, Model model){
        Request req = requestDao.getRequest(requestID);
        LocalTime time = hora.toLocalTime();
        req.setStarthour(time);
        requestDao.updateRequest(req);
        model.addAttribute("listRequests", requestDao.listRequestByContractId(req.getContract_id()));
        return"company/listRequest";
    }

}

