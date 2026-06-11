package net.labymod.core.flint.marketplace;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintUser.class */
public class FlintUser {
    private UUID uuid;

    @SerializedName("user_name")
    private String userName;

    public String getUserName() {
        return this.userName;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Icon icon() {
        return Icon.head(this.uuid);
    }
}
