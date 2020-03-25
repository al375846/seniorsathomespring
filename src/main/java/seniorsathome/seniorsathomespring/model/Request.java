package seniorsathome.seniorsathomespring.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Request {

    private String number_id;
    private String status;
    private String type;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date start_date;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date final_date;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date approval_date;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date reject_date;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getFinal_date() {
        return final_date;
    }

    public void setFinal_date(Date final_date) {
        this.final_date = final_date;
    }

    public Date getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(Date approval_date) {
        this.approval_date = approval_date;
    }

    public Date getReject_date() {
        return reject_date;
    }

    public void setReject_date(Date reject_date) {
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
