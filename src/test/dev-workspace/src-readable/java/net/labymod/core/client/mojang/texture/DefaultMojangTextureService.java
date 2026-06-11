package net.labymod.core.client.mojang.texture;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.mojang.texture.SkinPolicy;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/mojang/texture/DefaultMojangTextureService.class */
public abstract class DefaultMojangTextureService implements MojangTextureService {
    private final Map<MojangTextureType, TextureCache> textureCache = new HashMap();
    protected final LabyAPI labyAPI;

    public DefaultMojangTextureService(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        for (MojangTextureType type : MojangTextureType.VALUES) {
            this.textureCache.put(type, new TextureCache(type, this::getDefaultTexture));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public CompletableResourceLocation getTexture(UUID profileId, MojangTextureType type) throws MatchException {
        CompletableResourceLocation location = obtainLocationFromPlayerInfo(getPlayerInfo(profileId), type);
        return location == null ? getOrLoad(profileId, type) : location;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public CompletableResourceLocation getTexture(String name, MojangTextureType type) throws MatchException {
        CompletableResourceLocation location = obtainLocationFromPlayerInfo(getPlayerInfo(name), type);
        return location == null ? getOrLoad(name, type) : location;
    }

    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public MinecraftServices.SkinVariant getVariant(ResourceLocation location) {
        return MinecraftServices.SkinVariant.of((String) location.metadata().get("variant", MinecraftServices.SkinVariant.CLASSIC.getId()));
    }

    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public final void applySkinTexture(UUID profileId, MinecraftServices.SkinVariant variant, String url) {
        applyTexture(profileId, MojangTextureType.SKIN, url, texture -> {
            NetworkPlayerInfo playerInfo = getPlayerInfo(profileId);
            if (playerInfo == null) {
                return;
            }
            playerInfo.getSkin().setSkinVariant(variant);
        });
    }

    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public final void applyTexture(UUID profileId, MojangTextureType type, String url) {
        applyTexture(profileId, type, url, texture -> {
            NetworkPlayerInfo playerInfo;
            if (type != MojangTextureType.SKIN || (playerInfo = getPlayerInfo(profileId)) == null) {
                return;
            }
            GameImage image = texture.getImage();
            MinecraftServices.SkinVariant variant = SkinPolicy.guessVariant(image);
            playerInfo.getSkin().setSkinVariant(variant);
        });
    }

    private void applyTexture(UUID profileId, MojangTextureType type, String url, Consumer<SimpleTexture> callback) {
        PlayerSkin skin;
        NetworkPlayerInfo playerInfo = getPlayerInfo(profileId);
        if (playerInfo != null) {
            skin = playerInfo.getSkin();
            skin.setTexture(type, null);
        } else {
            skin = null;
        }
        TextureCache cache = this.textureCache.get(type);
        PlayerSkin playerSkin = skin;
        cache.refreshTexture(profileId, url, texture -> {
            if (texture == null) {
                return;
            }
            SimpleTexture gameImageTexture = (SimpleTexture) texture;
            ResourceLocation location = gameImageTexture.getTextureLocation();
            if (playerSkin != null) {
                playerSkin.setTexture(type, location);
            }
            callback.accept(gameImageTexture);
        });
    }

    @Nullable
    private NetworkPlayerInfo getPlayerInfo(UUID profileId) {
        ClientPacketListener packetListener = this.labyAPI.minecraft().getClientPacketListener();
        if (packetListener == null) {
            return null;
        }
        return packetListener.getNetworkPlayerInfo(profileId);
    }

    @Nullable
    private NetworkPlayerInfo getPlayerInfo(String username) {
        ClientPacketListener packetListener = this.labyAPI.minecraft().getClientPacketListener();
        if (packetListener == null) {
            return null;
        }
        return packetListener.getNetworkPlayerInfo(username);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Nullable
    private CompletableResourceLocation obtainLocationFromPlayerInfo(@Nullable NetworkPlayerInfo playerInfo, MojangTextureType type) throws MatchException {
        if (playerInfo == null) {
            return null;
        }
        PlayerSkin skin = playerInfo.getSkin();
        CompletableResourceLocation completable = skin.getCompletableTexture(type);
        completable.addCompletableListener(() -> {
            ResourceLocation location = completable.getCompleted();
            if (location != null) {
                location.metadata().set("variant", skin.getSkinVariant().getId());
            }
        });
        return completable;
    }

    private CompletableResourceLocation getOrLoad(UUID profileId, MojangTextureType type) {
        TextureCache cache = this.textureCache.get(type);
        return cache.getOrLoad(profileId, texture -> {
        });
    }

    private CompletableResourceLocation getOrLoad(String username, MojangTextureType type) {
        TextureCache cache = this.textureCache.get(type);
        return cache.getOrLoad(username, texture -> {
        });
    }
}
