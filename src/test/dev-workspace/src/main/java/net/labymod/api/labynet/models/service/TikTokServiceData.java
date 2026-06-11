package net.labymod.api.labynet.models.service;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/service/TikTokServiceData.class */
public class TikTokServiceData extends ServiceData {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("follower_count")
    private int followerCount;

    @SerializedName("likes_count")
    private int likesCount;

    public String getDisplayName() {
        return this.displayName;
    }

    public int getFollowerCount() {
        return this.followerCount;
    }

    public int getLikesCount() {
        return this.likesCount;
    }
}
