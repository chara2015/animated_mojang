package net.labymod.v1_20_4.mixins.server;

import net.labymod.v1_20_4.server.MinecraftServerAccessor;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/server/MixinMinecraftServer.class */
@Mixin({MinecraftServer.class})
public class MixinMinecraftServer implements MinecraftServerAccessor {

    @Shadow
    @Final
    protected c g;

    @Override // net.labymod.v1_20_4.server.MinecraftServerAccessor
    public c getStorageSource() {
        return this.g;
    }
}
