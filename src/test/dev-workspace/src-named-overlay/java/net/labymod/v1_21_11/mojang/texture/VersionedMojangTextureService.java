package net.labymod.v1_21_11.mojang.texture;

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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mojang/texture/VersionedMojangTextureService.class */
@Singleton
@Implements(MojangTextureService.class)
public class VersionedMojangTextureService extends DefaultMojangTextureService {
    public VersionedMojangTextureService(LabyAPI labyAPI) {
        super(labyAPI);
    }

    public ResourceLocation getDefaultTexture(UUID profileId, MojangTextureType type) {
        ResourceLocation location;
        PlayerSkin playerSkin = DefaultPlayerSkin.get(profileId);
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$mojang$texture$MojangTextureType[type.ordinal()]) {
            case 1:
                location = (ResourceLocation) CastUtil.cast(playerSkin.body().texturePath());
                break;
            case 2:
                ClientAsset.Texture cape = playerSkin.cape();
                location = cape == null ? null : (ResourceLocation) CastUtil.cast(cape.texturePath());
                break;
            case 3:
                ClientAsset.Texture elytra = playerSkin.elytra();
                location = elytra == null ? null : (ResourceLocation) CastUtil.cast(elytra.texturePath());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
        return location;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mojang.texture.VersionedMojangTextureService$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mojang/texture/VersionedMojangTextureService$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$mojang$texture$MojangTextureType = new int[MojangTextureType.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$mojang$texture$MojangTextureType[MojangTextureType.SKIN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$mojang$texture$MojangTextureType[MojangTextureType.CAPE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$mojang$texture$MojangTextureType[MojangTextureType.ELYTRA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
