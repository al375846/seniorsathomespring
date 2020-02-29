package seniorsathome.seniorsathomespring.model;

public class Beneficiary {

    private String identificationNumber;
    private String name;
    private String surnames;
    private String phoneNumber;
    private String email;
    private String address;
    private String userName;
    private String password;
    private String socialWorkerID;

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSocialWorkerID() {
        return socialWorkerID;
    }

    public void setSocialWorkerID(String socialWorkerID) {
        this.socialWorkerID = socialWorkerID;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "identificationNumber='" + identificationNumber + '\'' +
                ", name='" + name + '\'' +
                ", surnames='" + surnames + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", socialWorkerID='" + socialWorkerID + '\'' +
                '}';
    }
}
