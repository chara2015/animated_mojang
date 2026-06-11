package net.labymod.v26_1.mixins.server;

import net.labymod.v26_1.server.MinecraftServerAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/server/MixinMinecraftServer.class */
@Mixin({MinecraftServer.class})
public class MixinMinecraftServer implements MinecraftServerAccessor {

    @Shadow
    @Final
    protected LevelStorageSource.LevelStorageAccess storageSource;

    @Override // net.labymod.v26_1.server.MinecraftServerAccessor
    public LevelStorageSource.LevelStorageAccess getStorageSource() {
        return this.storageSource;
    }
}
