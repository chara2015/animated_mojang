package net.labymod.v1_20_6.mixins.client.gui.screens;

import java.net.InetSocketAddress;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.core.client.network.server.connect.ConnectAddressResolver;
import net.labymod.v1_20_6.client.multiplayer.server.VersionedServerController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/screens/MixinConnectScreenThread.class */
@Mixin(targets = {"net/minecraft/client/gui/screens/ConnectScreen$1"})
public class MixinConnectScreenThread {

    @Shadow
    @Final
    fly e;

    @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/resolver/ServerNameResolver;resolveAddress(Lnet/minecraft/client/multiplayer/resolver/ServerAddress;)Ljava/util/Optional;"))
    private Optional<fzn> labyMod$resolveAddress(fzq resolver, fzo address) {
        InetSocketAddress resolved = ConnectAddressResolver.resolve(address.a(), address.b(), this.e);
        return resolved == null ? Optional.empty() : Optional.of(fzn.a(resolved));
    }

    @ModifyArg(method = {"lambda$run$0(Lnet/minecraft/client/Minecraft;)V", "lambda$run$1(Lnet/minecraft/client/Minecraft;Ljava/lang/String;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public xp labyMod$fireServerKickEvent(xp reason) {
        LabyAPI labyAPI = Laby.labyAPI();
        ServerData data = ((VersionedServerController) labyAPI.serverController()).getConnectingServerData();
        if (data == null) {
            return reason;
        }
        ComponentMapper componentMapper = labyAPI.minecraft().componentMapper();
        return (xp) componentMapper.toMinecraftComponent(((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(data), componentMapper.fromMinecraftComponent(reason), ServerKickEvent.Context.PRE_LOGIN))).reason());
    }
}
