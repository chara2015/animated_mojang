package net.labymod.api.client.session.model;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/model/MojangTextureChangedResponse.class */
public class MojangTextureChangedResponse {

    @SerializedName("id")
    private UUID uniqueId;

    @SerializedName("name")
    private String username;
    private MojangTexture[] skins;
    private MojangTexture[] capes;

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public String getUsername() {
        return this.username;
    }

    @Nullable
    public MojangTexture[] getSkins() {
        return this.skins;
    }

    @Nullable
    public MojangTexture[] getCapes() {
        return this.capes;
    }

    @Nullable
    public MojangTexture getActiveCape() {
        for (MojangTexture cape : this.capes) {
            if (cape.isActive()) {
                return cape;
            }
        }
        return null;
    }

    public void filterActiveTextures() {
        this.skins = filterActiveTextures(this.skins);
        this.capes = filterActiveTextures(this.capes);
    }

    private MojangTexture[] filterActiveTextures(MojangTexture[] textures) {
        if (textures == null) {
            return null;
        }
        int activeTextures = 0;
        for (MojangTexture mojangTexture : textures) {
            if (mojangTexture.getState() == MojangTextureState.ACTIVE) {
                activeTextures++;
            }
        }
        MojangTexture[] activeTexturesArray = new MojangTexture[activeTextures];
        int activeTexturesIndex = 0;
        for (MojangTexture texture : textures) {
            if (texture.getState() == MojangTextureState.ACTIVE) {
                int i = activeTexturesIndex;
                activeTexturesIndex++;
                activeTexturesArray[i] = texture;
            }
        }
        return activeTexturesArray;
    }
}
