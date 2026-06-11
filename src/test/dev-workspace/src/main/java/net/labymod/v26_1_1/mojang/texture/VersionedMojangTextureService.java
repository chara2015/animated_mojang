package net.labymod.v26_1_1.mojang.texture;

import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.mojang.texture.DefaultMojangTextureService;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.player.PlayerSkin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mojang/texture/VersionedMojangTextureService.class */
@Singleton
@Implements(MojangTextureService.class)
public class VersionedMojangTextureService extends DefaultMojangTextureService {
    public VersionedMojangTextureService(LabyAPI labyAPI) {
        super(labyAPI);
    }

    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public ResourceLocation getDefaultTexture(UUID profileId, MojangTextureType type) {
        ResourceLocation location;
        PlayerSkin playerSkin = DefaultPlayerSkin.get(profileId);
        switch (type) {
            case SKIN:
                location = (ResourceLocation) CastUtil.cast(playerSkin.body().texturePath());
                break;
            case CAPE:
                ClientAsset.Texture cape = playerSkin.cape();
                location = cape == null ? null : (ResourceLocation) CastUtil.cast(cape.texturePath());
                break;
            case ELYTRA:
                ClientAsset.Texture elytra = playerSkin.elytra();
                location = elytra == null ? null : (ResourceLocation) CastUtil.cast(elytra.texturePath());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
        return location;
    }
}
