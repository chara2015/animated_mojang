package net.labymod.v1_21_11.mixins.server;

import net.labymod.v1_21_11.server.MinecraftServerAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/server/MixinMinecraftServer.class */
@Mixin({MinecraftServer.class})
public class MixinMinecraftServer implements MinecraftServerAccessor {

    @Shadow
    @Final
    protected LevelStorageSource.LevelStorageAccess g;

    @Override // net.labymod.v1_21_11.server.MinecraftServerAccessor
    public LevelStorageSource.LevelStorageAccess getStorageSource() {
        return this.g;
    }
}
