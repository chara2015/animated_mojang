package net.labymod.api.client.network;

import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.mojang.texture.MojangTextureType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/PlayerSkin.class */
public interface PlayerSkin {
    void setSkinTexture(@Nullable ResourceLocation resourceLocation);

    void setCapeTexture(@Nullable ResourceLocation resourceLocation);

    void setElytraTexture(@Nullable ResourceLocation resourceLocation);

    void setSkinVariant(@NotNull MinecraftServices.SkinVariant skinVariant);

    @NotNull
    CompletableResourceLocation getCompletableSkinTexture();

    @NotNull
    CompletableResourceLocation getCompletableCapeTexture();

    @NotNull
    CompletableResourceLocation getCompletableElytraTexture();

    @NotNull
    MinecraftServices.SkinVariant getSkinVariant();

    default void setTexture(MojangTextureType type, ResourceLocation texture) {
        switch (type) {
            case SKIN:
                setSkinTexture(texture);
                break;
            case CAPE:
                setCapeTexture(texture);
                break;
            case ELYTRA:
                setElytraTexture(texture);
                break;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @NotNull
    default CompletableResourceLocation getCompletableTexture(MojangTextureType type) throws MatchException {
        switch (type) {
            case SKIN:
                return getCompletableSkinTexture();
            case CAPE:
                return getCompletableCapeTexture();
            case ELYTRA:
                return getCompletableElytraTexture();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Deprecated(since = "4.2.14", forRemoval = true)
    @Nullable
    default ResourceLocation getTexture(MojangTextureType type) {
        return getCompletableTexture(type).getCompleted();
    }

    @Deprecated(since = "4.2.14", forRemoval = true)
    @NotNull
    default ResourceLocation getSkinLocation() {
        return getCompletableSkinTexture().getCompleted();
    }

    @Deprecated(since = "4.2.14", forRemoval = true)
    @Nullable
    default ResourceLocation getCapeLocation() {
        return getCompletableCapeTexture().getCompleted();
    }

    @Deprecated(since = "4.2.14", forRemoval = true)
    @Nullable
    default ResourceLocation getElytraLocation() {
        return getCompletableElytraTexture().getCompleted();
    }
}
