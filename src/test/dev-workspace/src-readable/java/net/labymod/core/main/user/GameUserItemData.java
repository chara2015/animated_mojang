package net.labymod.core.main.user;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/GameUserItemData.class */
public class GameUserItemData {

    @SerializedName("i")
    private int identifier;

    @SerializedName("d")
    private String[] options;

    public GameUserItemData() {
    }

    public GameUserItemData(int identifier, String[] options) {
        this.identifier = identifier;
        this.options = options;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public String[] getOptions() {
        return this.options;
    }
}
