package net.labymod.v1_18_2.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/MixinClientHandshakePacketListener.class */
@Mixin({emr.class})
public class MixinClientHandshakePacketListener {
    @ModifyArg(method = {"onDisconnect"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public qk labyMod$fireServerKickEvent(qk reason) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(dyr.D().F());
        if (serverData == null) {
            return reason;
        }
        return ((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component) reason, ServerKickEvent.Context.LOGIN))).reason();
    }
}
