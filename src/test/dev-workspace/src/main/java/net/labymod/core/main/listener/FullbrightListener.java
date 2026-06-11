package net.labymod.core.main.listener;

import net.labymod.api.LabyAPI;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/FullbrightListener.class */
public class FullbrightListener {
    private final LabyAPI labyAPI;

    public FullbrightListener(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Subscribe
    public void onUpdateLightmapTexture(UpdateLightmapTextureEvent event) {
        if (this.labyAPI.config().ingame().enableFullbright().get().booleanValue()) {
            event.setCancelled(true);
        }
    }
}
