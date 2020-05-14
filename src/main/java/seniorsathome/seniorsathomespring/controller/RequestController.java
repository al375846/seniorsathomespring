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

import java.time.LocalDate;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;
    private BeneficiaryDao beneficiaryDao;
    private ContractDao contractDao;
    private CompanyDao companyDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao = beneficiaryDao;
    }

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @RequestMapping("/list")
    public String listRequests(Model model) {
        model.addAttribute("requests", requestDao.getRequests());
        return "request/list";
    }

    @RequestMapping("/listunsolved")
    public String listUnsolvedRequests(Model model) {
        model.addAttribute("requests", requestDao.listUnsolvedRequests());
        return "request/listunsolved";
    }

    @RequestMapping(value="/add")
    public String addRequest(Model model) {
        model.addAttribute("request", new Request());
        return "request/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request,
                                   BindingResult bindingResult) {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "request/add";
        requestDao.addRequest(request);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{number_id}", method = RequestMethod.GET)
    public String editRequest(Model model, @PathVariable String number_id) {
        model.addAttribute("request", requestDao.getRequest(number_id));
        return "request/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("request") Request request,
            BindingResult bindingResult) {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "request/update";
        requestDao.updateRequest(request);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{number_id}")
    public String processDeleteRequest(@PathVariable String number_id) {
        requestDao.deleteRequest(number_id);
        return "redirect:../list";
    }

    @RequestMapping("/reject/{number_id}")
    public String reject(@PathVariable String number_id) {
        Request request = requestDao.getRequest(number_id);
        request.setStatus("REJECTED");
        request.setReject_date(LocalDate.now());
        requestDao.updateRequest(request);
        Beneficiary bene = beneficiaryDao.getBeneficiary(request.getBeneficiary_id());
        Correo.enviarMensajeSah(bene.getEmail(), "Update", "Your register has been updated");
        return "redirect:../listunsolved";
    }

    @RequestMapping(value="/overview/{number_id}", method = RequestMethod.GET)
    public String overviewRequest(Model model, @PathVariable String number_id) {
        Request request = requestDao.getRequest(number_id);
        model.addAttribute("request", request);
        model.addAttribute("contracts", contractDao.getContractsByService(request.getType()));
        return "request/overview";
    }

    @RequestMapping(value="/accept/{number_id}/{numberID}")
    public String accept(
            @PathVariable String number_id, @PathVariable String numberID) {
        Request request = requestDao.getRequest(number_id);
        request.setApproval_date(LocalDate.now());
        request.setStatus("APPROVED");
        request.setContract_id(numberID);
        Contract contract = contractDao.getContract(numberID);
        System.out.println(numberID);
        System.out.println(number_id);
        contract.setQuantity(contract.getQuantity() - 1);
        requestDao.updateRequest(request);
        contractDao.updateContract(contract);
        Company company = companyDao.getCompany(contract.getCompanyID());
        Correo.enviarMensajeSah(company.getEmail(), "Request assigned", "A new request has been assigned");
        return "redirect:/request/listunsolved";
    }
}
