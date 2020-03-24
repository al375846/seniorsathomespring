package seniorsathome.seniorsathomespring.model;

import java.util.Date;

public class Request {

    private String numberID;
    private Date startDate;
    private Date finalDate;
    private Date approvalDate;
    private Date rejectDate;
    private String comments;
    private String beneficiaryID;
    private String contractID;
    private String status;
    private String serviceType;

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(String beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "numberID='" + numberID + '\'' +
                ", startDate=" + startDate +
                ", finalDate=" + finalDate +
                ", approvalDate=" + approvalDate +
                ", rejectDate=" + rejectDate +
                ", comments='" + comments + '\'' +
                ", beneficiaryID='" + beneficiaryID + '\'' +
                ", contractID='" + contractID + '\'' +
                ", status=" + status +
                ", serviceType=" + serviceType +
                '}';
    }
}
