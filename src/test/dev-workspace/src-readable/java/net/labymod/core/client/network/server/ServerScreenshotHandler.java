package net.labymod.core.client.network.server;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/ServerScreenshotHandler.class */
@Singleton
public class ServerScreenshotHandler {
    private final ServerController serverController = Laby.references().serverController();

    @Inject
    public ServerScreenshotHandler() {
    }

    @Subscribe(-128)
    public void storeLastScreenshot(WriteScreenshotEvent event) {
        StorageServerData serverData;
        if (event.isCancelled() || event.getPhase() != Phase.POST || (serverData = this.serverController.getCurrentStorageServerData()) == null) {
            return;
        }
        serverData.metadata().put(StorageServerData.LAST_SCREENSHOT_KEY, event.getDestination().getAbsolutePath());
        this.serverController.serverList().saveAsync();
    }
}
