package seniorsathome.seniorsathomespring.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class Request {

    private String number_id;
    private String status;
    private String type;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate start_date;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate final_date;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate approval_date;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate reject_date;
    private String comments;
    private String beneficiary_id;
    private String contract_id;

    public String getNumber_id() {
        return number_id;
    }

    public void setNumber_id(String number_id) {
        this.number_id = number_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getFinal_date() {
        return final_date;
    }

    public void setFinal_date(LocalDate final_date) {
        this.final_date = final_date;
    }

    public LocalDate getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(LocalDate approval_date) {
        this.approval_date = approval_date;
    }

    public LocalDate getReject_date() {
        return reject_date;
    }

    public void setReject_date(LocalDate reject_date) {
        this.reject_date = reject_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBeneficiary_id() {
        return beneficiary_id;
    }

    public void setBeneficiary_id(String beneficiary_id) {
        this.beneficiary_id = beneficiary_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "number_id='" + number_id + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", start_date=" + start_date +
                ", final_date=" + final_date +
                ", approval_date=" + approval_date +
                ", reject_date=" + reject_date +
                ", comments='" + comments + '\'' +
                ", beneficiary_id='" + beneficiary_id + '\'' +
                ", contract_id='" + contract_id + '\'' +
                '}';
    }
}
