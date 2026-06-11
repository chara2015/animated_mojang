package net.labymod.core.main.user.shop.emote.model;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/model/DailyEmoteFlat.class */
public class DailyEmoteFlat {

    @SerializedName("e")
    private boolean hasFlat;

    public boolean hasFlat() {
        return this.hasFlat;
    }

    public void setHasFlat(boolean hasFlat) {
        this.hasFlat = hasFlat;
    }

    public DailyEmoteFlat copy() {
        DailyEmoteFlat copy = new DailyEmoteFlat();
        copy.hasFlat = this.hasFlat;
        return copy;
    }
}
