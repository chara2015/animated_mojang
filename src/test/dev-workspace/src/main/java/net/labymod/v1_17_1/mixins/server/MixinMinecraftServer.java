package net.labymod.v1_17_1.mixins.server;

import net.labymod.v1_17_1.server.MinecraftServerAccessor;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/server/MixinMinecraftServer.class */
@Mixin({MinecraftServer.class})
public class MixinMinecraftServer implements MinecraftServerAccessor {

    @Shadow
    @Final
    protected a j;

    @Override // net.labymod.v1_17_1.server.MinecraftServerAccessor
    public a getStorageSource() {
        return this.j;
    }
}
