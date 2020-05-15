package seniorsathome.seniorsathomespring.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Rater {

    private String raterid;
    private String name;
    private String serviceType;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public String getRaterid() {
        return raterid;
    }

    public void setRaterid(String raterid) {
        this.raterid = raterid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rater{" +
                "name='" + name + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", date=" + date +
                '}';
    }
}
