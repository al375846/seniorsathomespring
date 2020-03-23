package seniorsathome.seniorsathomespring.model;

public class Valoration {
    private String idValoration;
    private double rate;
    private String comment;
    private String idVolunteer;
    private String idCompany;
    private String idBeneficiary;

    public String getIdValoration() {
        return idValoration;
    }

    public void setIdValoration(String idValoration) {
        this.idValoration = idValoration;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIdVolunteer() {
        return idVolunteer;
    }

    public void setIdVolunteer(String idVolunteer) {
        this.idVolunteer = idVolunteer;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getIdBeneficiary() {
        return idBeneficiary;
    }

    public void setIdBeneficiary(String idBeneficiary) {
        this.idBeneficiary = idBeneficiary;
    }

    @Override
    public String toString() {
        return "Valoration{" +
                "idValoration='" + idValoration + '\'' +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", idVolunteer='" + idVolunteer + '\'' +
                ", idCompany='" + idCompany + '\'' +
                ", idBeneficiary='" + idBeneficiary + '\'' +
                '}';
    }
}
