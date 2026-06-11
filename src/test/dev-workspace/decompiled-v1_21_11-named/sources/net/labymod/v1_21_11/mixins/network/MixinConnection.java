package net.labymod.v1_21_11.mixins.network;

import io.netty.channel.Channel;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.core.client.multiplayer.NettyConnection;
import net.labymod.v1_21_11.client.multiplayer.server.VersionedServerController;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/network/MixinConnection.class */
@Mixin({Connection.class})
@Implements({@Interface(iface = NettyConnection.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinConnection implements NettyConnection {

    @Shadow
    private Channel k;

    @Intrinsic
    public Channel labyMod$getChannel() {
        return this.k;
    }

    @ModifyVariable(method = {"disconnect"}, at = @At("HEAD"), argsOnly = true)
    public Component labyMod4$modifyReason(Component component) {
        ConnectableServerData serverData;
        VersionedServerController versionedServerControllerServerController = Laby.labyAPI().serverController();
        ConnectableServerData currentStorageServerData = versionedServerControllerServerController.getCurrentStorageServerData();
        if (currentStorageServerData != null) {
            serverData = currentStorageServerData;
        } else if (versionedServerControllerServerController.getCurrentServerData() != null) {
            serverData = ConnectableServerData.from(versionedServerControllerServerController.getCurrentServerData());
        } else {
            ServerData data = versionedServerControllerServerController.getConnectingServerData();
            if (data == null) {
                return component;
            }
            serverData = ConnectableServerData.from(data);
        }
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        return (Component) componentMapper.toMinecraftComponent(Laby.fireEvent(new ServerKickEvent(serverData, componentMapper.fromMinecraftComponent(component), ServerKickEvent.Context.PLAY)).reason());
    }
}
