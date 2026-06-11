package net.labymod.core.main.listener;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.core.localization.InternationalizationReloader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/TranslationReloadListener.class */
public class TranslationReloadListener {
    @Subscribe
    public void resourceReload(ResourceReloadEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        InternationalizationReloader.reload();
    }
}
