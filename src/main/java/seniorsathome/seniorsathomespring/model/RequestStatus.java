package seniorsathome.seniorsathomespring.model;

public enum RequestStatus {
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    UNSOLVED("UNSOLVED");

    private RequestStatus statusName;

    RequestStatus(String status) {
        this.statusName = RequestStatus.valueOf(status);
    }

    public RequestStatus statusName() {
        return statusName;
    }
}
