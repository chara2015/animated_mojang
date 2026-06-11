package net.minecraft.client.resources;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/DefaultPlayerSkin.class */
public class DefaultPlayerSkin {
    private static final PlayerSkin[] DEFAULT_SKINS = {create("entity/player/slim/alex", PlayerModelType.SLIM), create("entity/player/slim/ari", PlayerModelType.SLIM), create("entity/player/slim/efe", PlayerModelType.SLIM), create("entity/player/slim/kai", PlayerModelType.SLIM), create("entity/player/slim/makena", PlayerModelType.SLIM), create("entity/player/slim/noor", PlayerModelType.SLIM), create("entity/player/slim/steve", PlayerModelType.SLIM), create("entity/player/slim/sunny", PlayerModelType.SLIM), create("entity/player/slim/zuri", PlayerModelType.SLIM), create("entity/player/wide/alex", PlayerModelType.WIDE), create("entity/player/wide/ari", PlayerModelType.WIDE), create("entity/player/wide/efe", PlayerModelType.WIDE), create("entity/player/wide/kai", PlayerModelType.WIDE), create("entity/player/wide/makena", PlayerModelType.WIDE), create("entity/player/wide/noor", PlayerModelType.WIDE), create("entity/player/wide/steve", PlayerModelType.WIDE), create("entity/player/wide/sunny", PlayerModelType.WIDE), create("entity/player/wide/zuri", PlayerModelType.WIDE)};

    public static Identifier getDefaultTexture() {
        return getDefaultSkin().body().texturePath();
    }

    public static PlayerSkin getDefaultSkin() {
        return DEFAULT_SKINS[6];
    }

    public static PlayerSkin get(UUID $$0) {
        return DEFAULT_SKINS[Math.floorMod($$0.hashCode(), DEFAULT_SKINS.length)];
    }

    public static PlayerSkin get(GameProfile $$0) {
        return get($$0.id());
    }

    private static PlayerSkin create(String $$0, PlayerModelType $$1) {
        return new PlayerSkin(new ClientAsset.ResourceTexture(Identifier.withDefaultNamespace($$0)), null, null, $$1, true);
    }
}
