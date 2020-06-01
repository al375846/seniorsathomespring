package seniorsathome.seniorsathomespring.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private static final double Catering = 4.5; //price per day
    private static final double Nursing = 5; //price per visit
    private static final double Cleaning = 4; //price per hour //3 hour always


    private String numberID;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
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

    public static List<InvoiceLine> emitirFactura(Beneficiary beneficiary, List<Request> requests, int invoiceId, int comienzo) {
        List<InvoiceLine> invoicelines = new ArrayList<InvoiceLine>();
        for(Request request: requests) {
            InvoiceLine invoiceLine = new InvoiceLine();
            invoiceLine.setInvoiceID("I" + invoiceId);
            invoiceLine.setNumberID("IL" + comienzo);
            comienzo++;
            invoiceLine.setRequestID(request.getNumber_id());
            System.out.println(request.toString());
            if(request.getType().equals("CATERING")) {
                invoiceLine.setPrice(Catering * request.getDays().length() * 4);
            }
            if(request.getType().equals("NURSING")) {
                invoiceLine.setPrice(Nursing * request.getDays().length() * 4);
            }
            if(request.getType().equals("CLEANING")) {
                invoiceLine.setPrice(Cleaning * request.getDays().length() * 4);
            }
            invoicelines.add(invoiceLine);
        }
        return invoicelines;
    }
}
