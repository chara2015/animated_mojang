package net.labymod.v1_21_5.mojang.texture;

import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.core.client.mojang.texture.DefaultMojangTextureService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mojang/texture/VersionedMojangTextureService.class */
@Singleton
@Implements(MojangTextureService.class)
public class VersionedMojangTextureService extends DefaultMojangTextureService {
    public VersionedMojangTextureService(LabyAPI labyAPI) {
        super(labyAPI);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mojang.texture.MojangTextureService
    public ResourceLocation getDefaultTexture(UUID profileId, MojangTextureType type) throws MatchException {
        hls playerSkin = hli.a(profileId);
        switch (type) {
            case SKIN:
                return playerSkin.a();
            case CAPE:
                return playerSkin.c();
            case ELYTRA:
                return playerSkin.d();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
