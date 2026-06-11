package net.labymod.v1_19_4.mixins.client.server;

import net.labymod.api.Laby;
import net.labymod.api.event.client.network.server.IntegratedServerStoppingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/server/MixinIntegratedServer.class */
@Mixin({fww.class})
public class MixinIntegratedServer {
    @Inject(method = {"stopServer"}, at = {@At("TAIL")})
    public void labymod$stopServer(CallbackInfo ci) {
        Laby.fireEvent(new IntegratedServerStoppingEvent());
    }
}
