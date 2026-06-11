package net.labymod.core.client.entity;

import net.labymod.api.LabyAPI;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/NickHiderListener.class */
public class NickHiderListener {
    private final LabyAPI labyAPI;

    public NickHiderListener(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Subscribe
    public void onNameTagRender(PlayerNameTagRenderEvent event) {
        if (this.labyAPI.config().ingame().hideNametag().get().booleanValue()) {
            event.setCancelled(true);
        }
    }
}
