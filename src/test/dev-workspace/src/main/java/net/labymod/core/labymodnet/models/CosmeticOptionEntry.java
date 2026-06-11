package net.labymod.core.labymodnet.models;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/CosmeticOptionEntry.class */
public class CosmeticOptionEntry {

    @SerializedName("custom_key")
    private final String customKey;
    private final String name;
    private final String data;
    private transient String customName;

    public CosmeticOptionEntry(String customKey, String name, String data) {
        this.customKey = customKey;
        this.name = name;
        this.data = data;
    }

    public String getCustomKey() {
        return this.customKey;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String toString() {
        if (this.customName != null) {
            return this.customName;
        }
        if (getName() != null) {
            return getName();
        }
        return getData();
    }
}
