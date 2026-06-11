package net.labymod.api.client.session.model;

import java.util.UUID;
import net.labymod.api.client.session.MinecraftServices;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/model/MojangTexture.class */
public class MojangTexture {
    private UUID id;
    private MojangTextureState state;
    private String url;
    private MinecraftServices.SkinVariant variant;
    private String alias;

    public UUID getId() {
        return this.id;
    }

    public MojangTextureState getState() {
        return this.state;
    }

    public boolean isActive() {
        return this.state == MojangTextureState.ACTIVE;
    }

    public String getUrl() {
        return this.url;
    }

    public String getTextureHash() {
        return this.url.substring(this.url.lastIndexOf("/") + 1);
    }

    public MinecraftServices.SkinVariant getVariant() {
        return this.variant;
    }

    public String getAlias() {
        return this.alias;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MojangTexture that = (MojangTexture) o;
        return this.id.equals(that.id);
    }

    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        return 0;
    }
}
