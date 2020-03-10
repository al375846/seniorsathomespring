package seniorsathome.seniorsathomespring.model;

public enum ServiceType {
    CATERING("CATERING"),
    PHISIOTERAPY("PHISIOTERAPY"),
    NURSING("NURSING"),
    LAUNDRY("LAUNDRY"),
    CLEANING("CLEANING");

    private ServiceType serviceName;

    ServiceType(String service) {
        this.serviceName = ServiceType.valueOf(service);
    }

    public ServiceType serviceName() {
        return serviceName;
    }
}
