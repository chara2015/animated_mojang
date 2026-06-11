package net.labymod.v1_21_11.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinClientHandshakePacketListener.class */
@Mixin({ClientHandshakePacketListenerImpl.class})
public class MixinClientHandshakePacketListener {
    @ModifyArg(method = {"onDisconnect"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/DisconnectionDetails;)V"), index = 2)
    public DisconnectionDetails labyMod$fireServerKickEvent(DisconnectionDetails details) {
        LabyAPI labyAPI = Laby.labyAPI();
        ServerData data = labyAPI.serverController().getConnectingServerData();
        if (data == null) {
            return details;
        }
        ComponentMapper componentMapper = labyAPI.minecraft().componentMapper();
        Component newReason = (Component) componentMapper.toMinecraftComponent(Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(data), componentMapper.fromMinecraftComponent(details.reason()), ServerKickEvent.Context.LOGIN)).reason());
        return new DisconnectionDetails(newReason, details.report(), details.bugReportLink());
    }
}
