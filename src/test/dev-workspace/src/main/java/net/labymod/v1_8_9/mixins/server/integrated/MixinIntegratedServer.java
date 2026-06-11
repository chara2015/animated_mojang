package net.labymod.v1_8_9.mixins.server.integrated;

import net.labymod.api.Laby;
import net.labymod.api.event.client.network.server.IntegratedServerStoppingEvent;
import net.labymod.core.client.worldsharing.model.PortStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/server/integrated/MixinIntegratedServer.class */
@Mixin({bpo.class})
public class MixinIntegratedServer implements PortStorage {

    @Unique
    private int labymod$port = -1;

    @Inject(method = {"stopServer"}, at = {@At("TAIL")})
    private void labymod$stopServer(CallbackInfo ci) {
        Laby.fireEvent(new IntegratedServerStoppingEvent());
    }

    @ModifyVariable(method = {"shareToLAN"}, at = @At(value = "LOAD", ordinal = 0))
    private int labymod$modifyPort(int port) {
        if (this.labymod$port > 0) {
            port = this.labymod$port;
            this.labymod$port = -1;
        }
        return port;
    }

    @Override // net.labymod.core.client.worldsharing.model.PortStorage
    public void labymod$setPort(int value) {
        this.labymod$port = value;
    }
}
