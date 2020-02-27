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
    private enum status{APPROVED, REJECTED, UNSOLVED}
    private enum serviceType{CATERING, PHISIOTERAPY, NURSING, LAUNDRY, CLEANING}

}
