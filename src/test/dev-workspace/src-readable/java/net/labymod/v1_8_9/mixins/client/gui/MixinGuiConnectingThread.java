package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiConnectingThread.class */
@Mixin(targets = {"net/minecraft/client/multiplayer/GuiConnecting$1"})
public class MixinGuiConnectingThread {
    @ModifyArg(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiDisconnected;<init>(Lnet/minecraft/client/gui/GuiScreen;Ljava/lang/String;Lnet/minecraft/util/IChatComponent;)V"), index = 2)
    public eu labyMod$fireServerKickEvent(eu reason) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(ave.A().D());
        if (serverData == null) {
            return reason;
        }
        return ((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component) reason, ServerKickEvent.Context.PRE_LOGIN))).reason();
    }
}
