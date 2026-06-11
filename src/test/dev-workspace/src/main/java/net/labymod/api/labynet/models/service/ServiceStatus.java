package net.labymod.api.labynet.models.service;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/service/ServiceStatus.class */
public class ServiceStatus {
    private final Status status;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/service/ServiceStatus$Status.class */
    public enum Status {
        NOT_CONNECTED,
        UNAUTHORIZED,
        NOT_LINKED,
        NEEDS_RELINK,
        OK,
        LOADING,
        ERROR
    }

    ServiceStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public static ServiceStatus of(Status status) {
        return new ServiceStatus(status);
    }
}
