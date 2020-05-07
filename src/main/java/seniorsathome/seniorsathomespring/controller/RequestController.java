package seniorsathome.seniorsathomespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.dao.ContractDao;
import seniorsathome.seniorsathomespring.dao.RequestDao;
import seniorsathome.seniorsathomespring.model.Request;

import java.time.LocalDate;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;

    private ContractDao contractDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
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
        return "redirect:../listunsolved";
    }

    @RequestMapping(value="/overview/{number_id}", method = RequestMethod.GET)
    public String overviewRequest(Model model, @PathVariable String number_id) {
        Request request = requestDao.getRequest(number_id);
        model.addAttribute("request", request);
        model.addAttribute("contracts", contractDao.getContractsByService(request.getType()));
        return "request/overview";
    }

    @RequestMapping(value="/accept", method = RequestMethod.POST)
    public String accept(
            @ModelAttribute("request") Request request, String contID) {
        Request requestAccept = requestDao.getRequest(request.getNumber_id());
        requestAccept.setApproval_date(LocalDate.now());
        requestAccept.setStatus("APPROVED");
        requestAccept.setContract_id(contID);
        requestDao.updateRequest(requestAccept);
        return "redirect:listunsolved";
    }
}
