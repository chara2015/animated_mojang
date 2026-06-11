package net.labymod.v1_21_8.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.v1_21_8.client.multiplayer.server.VersionedServerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/MixinClientHandshakePacketListener.class */
@Mixin({grj.class})
public class MixinClientHandshakePacketListener {
    @ModifyArg(method = {"onDisconnect"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/DisconnectionDetails;)V"), index = 2)
    public wf labyMod$fireServerKickEvent(wf details) {
        LabyAPI labyAPI = Laby.labyAPI();
        ServerData data = ((VersionedServerController) labyAPI.serverController()).getConnectingServerData();
        if (data == null) {
            return details;
        }
        ComponentMapper componentMapper = labyAPI.minecraft().componentMapper();
        xo newReason = (xo) componentMapper.toMinecraftComponent(((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(data), componentMapper.fromMinecraftComponent(details.a()), ServerKickEvent.Context.LOGIN))).reason());
        return new wf(newReason, details.b(), details.c());
    }
}
