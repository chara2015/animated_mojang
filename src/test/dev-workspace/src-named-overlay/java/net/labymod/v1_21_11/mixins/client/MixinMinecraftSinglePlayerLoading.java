package net.labymod.v1_21_11.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({Minecraft.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, boolean $$4, CallbackInfo ci) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            level.disconnect(ClientLevel.DEFAULT_QUIT_MESSAGE);
        }
    }
}
