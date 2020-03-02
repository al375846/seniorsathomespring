package seniorsathome.seniorsathomespring.model;

import java.util.Date;

public class Contract {

    private String numberID;
    private int quantity;
    private Date startDate;
    private Date finalDate;
    private double price;
    private String companyID;
    private ServiceType serviceType;

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "numberID='" + numberID + '\'' +
                ", quantity=" + quantity +
                ", startDate=" + startDate +
                ", finalDate=" + finalDate +
                ", price=" + price +
                ", companyID='" + companyID + '\'' +
                ", serviceType=" + serviceType +
                '}';
    }
}
