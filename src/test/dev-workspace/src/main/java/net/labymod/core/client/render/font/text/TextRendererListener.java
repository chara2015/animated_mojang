package net.labymod.core.client.render.font.text;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/text/TextRendererListener.class */
public class TextRendererListener {
    private final DefaultTextRendererProvider provider;

    public TextRendererListener(DefaultTextRendererProvider provider) {
        this.provider = provider;
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.provider.setUseCustomFont(true);
    }
}
