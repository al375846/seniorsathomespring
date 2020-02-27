package seniorsathome.seniorsathomespring.model;

import java.util.Date;

public class Invoice {

    private String numberID;
    private Date releaseDate;
    private Date startDate;
    private Date finalDate;
    private double price;
    private String beneficiaryID;

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(String beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "numberID='" + numberID + '\'' +
                ", releaseDate=" + releaseDate +
                ", startDate=" + startDate +
                ", finalDate=" + finalDate +
                ", price=" + price +
                ", beneficiaryID='" + beneficiaryID + '\'' +
                '}';
    }
}
