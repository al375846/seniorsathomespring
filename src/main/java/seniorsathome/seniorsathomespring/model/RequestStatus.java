package seniorsathome.seniorsathomespring.model;

public enum RequestStatus {
    APPROVED("approved"),
    REJECTED("rejected"),
    UNSOLVED("unsolved");

    private String statusName;

    RequestStatus(String status) {
        this.statusName = status;
    }

    public String statusName() {
        return statusName;
    }
}
