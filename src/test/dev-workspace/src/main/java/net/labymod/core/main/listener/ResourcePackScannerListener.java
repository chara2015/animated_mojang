package net.labymod.core.main.listener;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/ResourcePackScannerListener.class */
public class ResourcePackScannerListener {
    private final ResourcePackScanner resourcePackScanner = Laby.references().resourcePackScanner();

    @Subscribe
    public void onResourceReload(ResourceReloadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.resourcePackScanner.scan();
    }
}
