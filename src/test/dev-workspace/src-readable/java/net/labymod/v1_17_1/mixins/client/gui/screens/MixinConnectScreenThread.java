package net.labymod.v1_17_1.mixins.client.gui.screens;

import java.net.InetSocketAddress;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.core.client.network.server.connect.ConnectAddressResolver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/gui/screens/MixinConnectScreenThread.class */
@Mixin(targets = {"net/minecraft/client/gui/screens/ConnectScreen$1"})
public class MixinConnectScreenThread {

    @Shadow
    @Final
    dzq c;

    @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/resolver/ServerNameResolver;resolveAddress(Lnet/minecraft/client/multiplayer/resolver/ServerAddress;)Ljava/util/Optional;"))
    private Optional<ejs> labyMod$resolveAddress(ejv resolver, ejt address) {
        InetSocketAddress resolved = ConnectAddressResolver.resolve(address.a(), address.b(), this.c);
        return resolved == null ? Optional.empty() : Optional.of(ejs.a(resolved));
    }

    @ModifyArg(method = {"lambda$run$0(Lnet/minecraft/client/Minecraft;)V", "lambda$run$1(Lnet/minecraft/client/Minecraft;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public os labyMod$fireServerKickEvent(os reason) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(dvp.C().E());
        if (serverData == null) {
            return reason;
        }
        return ((ServerKickEvent) Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component) reason, ServerKickEvent.Context.PRE_LOGIN))).reason();
    }
}
