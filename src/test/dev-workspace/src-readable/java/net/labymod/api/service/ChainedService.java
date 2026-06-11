package net.labymod.api.service;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/ChainedService.class */
public class ChainedService extends Service {
    private final List<Service> services = new ArrayList();

    protected <T extends Service> T registerService(T service) {
        this.services.add(service);
        return service;
    }

    @Override // net.labymod.api.service.Service
    @MustBeInvokedByOverriders
    public void onServiceCompleted() {
        long start = System.currentTimeMillis();
        for (Service service : this.services) {
            service.prepareSynchronously();
        }
        long duration = System.currentTimeMillis() - start;
        LOGGER.info("ChainedService completed in {}ms", Long.valueOf(duration));
    }

    @Override // net.labymod.api.service.Service
    @MustBeInvokedByOverriders
    public void onServiceUnload() {
        for (Service service : this.services) {
            service.unload();
        }
    }
}
