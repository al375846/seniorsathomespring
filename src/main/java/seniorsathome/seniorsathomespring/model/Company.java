package seniorsathome.seniorsathomespring.model;

public class Company {

    private String name;
    private String email;
    private String responsibleName;
    private String responsiblePhoneNumber;
    private String responsibleAddress;
    private String fiscalNumber;
    private String phoneNumber;
    private String userName;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getResponsiblePhoneNumber() {
        return responsiblePhoneNumber;
    }

    public void setResponsiblePhoneNumber(String responsiblePhoneNumber) {
        this.responsiblePhoneNumber = responsiblePhoneNumber;
    }

    public String getResponsibleAddress() {
        return responsibleAddress;
    }

    public void setResponsibleAddress(String responsibleAddress) {
        this.responsibleAddress = responsibleAddress;
    }

    public String getFiscalNumber() {
        return fiscalNumber;
    }

    public void setFiscalNumber(String fiscalNumber) {
        this.fiscalNumber = fiscalNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", responsibleName='" + responsibleName + '\'' +
                ", responsiblePhoneNumber='" + responsiblePhoneNumber + '\'' +
                ", responsibleAddress='" + responsibleAddress + '\'' +
                ", fiscalNumber='" + fiscalNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
