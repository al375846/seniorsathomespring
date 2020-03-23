package seniorsathome.seniorsathomespring.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Invoice {

    private String numberID;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate releaseDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate finalDate;

    private double price;
    private String beneficiaryID;

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
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
