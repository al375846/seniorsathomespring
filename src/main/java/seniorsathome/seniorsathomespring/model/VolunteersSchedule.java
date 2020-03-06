package seniorsathome.seniorsathomespring.model;

import java.sql.Date;
import java.sql.Time;

public class VolunteersSchedule {

    private String numberID;
    private Time startHour;
    private Time finalHour;
    private byte status;
    private String beneficiaryID;
    private String volunteerID;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    private Date day;

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Time getFinalHour() {
        return finalHour;
    }

    public void setFinalHour(Time finalHour) {
        this.finalHour = finalHour;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(String beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public String getVolunteerID() {
        return volunteerID;
    }

    public void setVolunteerID(String volunteerID) {
        this.volunteerID = volunteerID;
    }

    @Override
    public String toString() {
        return "VolunteersSchedule{" +
                "numberID='" + numberID + '\'' +
                ", startHour=" + startHour +
                ", finalHour=" + finalHour +
                ", status=" + status +
                ", beneficiaryID='" + beneficiaryID + '\'' +
                ", volunteerID='" + volunteerID + '\'' +
                '}';
    }
}
