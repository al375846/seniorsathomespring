package seniorsathome.seniorsathomespring.model;

public class InvoiceLine {

    private String numberID;
    private double price;
    private String requestID;
    private String invoiceID;

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" +
                "numberID='" + numberID + '\'' +
                ", price=" + price +
                ", requestID='" + requestID + '\'' +
                ", invoiceID='" + invoiceID + '\'' +
                '}';
    }
}
