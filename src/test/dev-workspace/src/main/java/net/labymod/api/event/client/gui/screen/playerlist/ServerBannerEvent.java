package net.labymod.api.event.client.gui.screen.playerlist;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/playerlist/ServerBannerEvent.class */
public class ServerBannerEvent implements Event {
    private String url;
    private String hash;

    public ServerBannerEvent(String url, String hash) {
        this.url = url;
        this.hash = hash;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
