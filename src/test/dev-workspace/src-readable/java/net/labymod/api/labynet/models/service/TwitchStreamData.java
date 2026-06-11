package net.labymod.api.labynet.models.service;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/service/TwitchStreamData.class */
public class TwitchStreamData {
    private String title;

    @SerializedName("game_name")
    private String gameName;
    private String type;

    @SerializedName("viewer_count")
    private int viewerCount;

    public String getGameName() {
        return this.gameName;
    }

    public String getType() {
        return this.type;
    }

    public int getViewerCount() {
        return this.viewerCount;
    }
}
