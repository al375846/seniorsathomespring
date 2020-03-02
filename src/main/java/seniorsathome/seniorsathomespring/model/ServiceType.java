package seniorsathome.seniorsathomespring.model;

public enum ServiceType {
    CATERING("catering"),
    PHISIOTERAPY("phisioterapy"),
    NURSING("nursing"),
    LAUNDRY("laundry"),
    CLEANING("cleaning");

    private String serviceName;

    ServiceType(String service) {
        this.serviceName = service;
    }

    public String serviceName() {
        return serviceName;
    }
}
