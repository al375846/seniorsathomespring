package seniorsathome.seniorsathomespring.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private String numberid;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate day;
    @DateTimeFormat(iso=DateTimeFormat.ISO.TIME)
    private LocalTime starthour;
    @DateTimeFormat(iso=DateTimeFormat.ISO.TIME)
    private LocalTime finalhour;
    private Boolean status;
    private String beneficiaryid;
    private String volunteerid;

    public String getNumberid() {
        return numberid;
    }

    public void setNumberid(String numberid) {
        this.numberid = numberid;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getStarthour() {
        return starthour;
    }

    public void setStarthour(LocalTime starthour) {
        this.starthour = starthour;
    }

    public LocalTime getFinalhour() {
        return finalhour;
    }

    public void setFinalhour(LocalTime finalhour) {
        this.finalhour = finalhour;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBeneficiaryid() {
        return beneficiaryid;
    }

    public void setBeneficiaryid(String beneficiaryid) {
        this.beneficiaryid = beneficiaryid;
    }

    public String getVolunteerid() {
        return volunteerid;
    }

    public void setVolunteerid(String volunteerid) {
        this.volunteerid = volunteerid;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "numberid='" + numberid + '\'' +
                ", day=" + day +
                ", starthour=" + starthour +
                ", finalhour=" + finalhour +
                ", status='" + status + '\'' +
                ", beneficiaryid='" + beneficiaryid + '\'' +
                ", volunteerid='" + volunteerid + '\'' +
                '}';
    }
}
