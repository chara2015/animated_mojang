package net.labymod.core.labyconnect.session.advertisement;

import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.labyconnect.session.advertisement.AdvertisementController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/advertisement/AdServerData.class */
public class AdServerData extends StorageServerData {
    private final AdvertisementController controller;
    private final int id;

    public AdServerData(AdvertisementController controller, int id, String name, String address) {
        super(name, ServerAddress.parse(address));
        this.controller = controller;
        this.id = id;
    }

    public int getAdId() {
        return this.id;
    }

    @Override // net.labymod.api.client.network.server.storage.StorageServerData, net.labymod.api.client.network.server.ConnectableServerData, net.labymod.api.client.network.server.ServerData
    public boolean equals(Object o) {
        if (o instanceof AdServerData) {
            return ((AdServerData) o).id == this.id;
        }
        return super.equals(o);
    }

    public void sendEvent(AdvertisementController.Event event) {
        this.controller.sendEvent(event, this);
    }

    public AdvertisementController getController() {
        return this.controller;
    }
}
