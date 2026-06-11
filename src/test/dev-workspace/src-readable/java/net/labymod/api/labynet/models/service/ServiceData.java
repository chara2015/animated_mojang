package net.labymod.api.labynet.models.service;

import net.labymod.api.labynet.models.service.ServiceStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/service/ServiceData.class */
public class ServiceData extends ServiceStatus {
    private String id;
    private String name;

    public ServiceData() {
        super(ServiceStatus.Status.OK);
    }
}
