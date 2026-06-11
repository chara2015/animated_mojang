package net.labymod.api.mojang;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.mojang.texture.MojangTextureType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mojang/GameProfile.class */
public interface GameProfile extends MetadataExtension {
    UUID getUniqueId();

    String getUsername();

    @NotNull
    Map<String, Collection<Property>> getProperties();

    GameProfile copy();

    @Deprecated
    UUID getProfileId();

    @Deprecated
    JsonObject getTexturesJson();

    default boolean hasProperty(@NotNull String key) {
        return getProperties().containsKey(key);
    }

    default CompletableResourceLocation getTexture(MojangTextureType type) {
        return Laby.references().mojangTextureService().getTexture(getUniqueId(), type);
    }
}
