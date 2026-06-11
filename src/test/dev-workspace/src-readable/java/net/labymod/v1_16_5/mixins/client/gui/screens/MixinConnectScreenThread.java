package net.labymod.v1_16_5.mixins.client.gui.screens;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/screens/MixinConnectScreenThread.class */
@Mixin(targets = {"net/minecraft/client/gui/screens/ConnectScreen$1"})
public class MixinConnectScreenThread {
    @ModifyArg(method = {"lambda$run$1()V", "lambda$run$2(Ljava/lang/String;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public nr labyMod$fireServerKickEvent(nr reason) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(djz.C().E());
        if (serverData == null) {
            return reason;
        }
        return ((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component) reason, ServerKickEvent.Context.PRE_LOGIN))).reason();
    }
}
