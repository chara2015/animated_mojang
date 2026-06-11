package net.labymod.v26_1_2.mixins.client.server;

import net.labymod.api.Laby;
import net.labymod.api.event.client.network.server.IntegratedServerStoppingEvent;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/server/MixinIntegratedServer.class */
@Mixin({IntegratedServer.class})
public class MixinIntegratedServer {

    @Shadow
    public static int MAX_PLAYERS;

    @Inject(method = {"stopServer"}, at = {@At("TAIL")})
    public void labymod$stopServer(CallbackInfo ci) {
        Laby.fireEvent(new IntegratedServerStoppingEvent());
    }

    @Inject(method = {"getMaxPlayers"}, at = {@At("RETURN")}, cancellable = true)
    private void labymod$getMaxPlayers(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Integer.valueOf(MAX_PLAYERS));
    }
}
