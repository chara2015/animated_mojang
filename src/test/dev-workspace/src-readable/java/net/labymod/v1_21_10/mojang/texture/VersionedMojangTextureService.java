package net.labymod.v1_21_10.mojang.texture;

import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.mojang.texture.DefaultMojangTextureService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mojang/texture/VersionedMojangTextureService.class */
@Singleton
@Implements(MojangTextureService.class)
public class VersionedMojangTextureService extends DefaultMojangTextureService {
    public VersionedMojangTextureService(LabyAPI labyAPI) {
        super(labyAPI);
    }

    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public ResourceLocation getDefaultTexture(UUID profileId, MojangTextureType type) {
        ResourceLocation location;
        czp playerSkin = idg.a(profileId);
        switch (type) {
            case SKIN:
                location = (ResourceLocation) CastUtil.cast(playerSkin.a().b());
                break;
            case CAPE:
                c cape = playerSkin.b();
                location = cape == null ? null : (ResourceLocation) CastUtil.cast(cape.b());
                break;
            case ELYTRA:
                c elytra = playerSkin.c();
                location = elytra == null ? null : (ResourceLocation) CastUtil.cast(elytra.b());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
        return location;
    }
}
