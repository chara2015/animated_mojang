package net.labymod.v1_12_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.core.client.worldsharing.Worldsharing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiConnecting.class */
@Mixin({bkr.class})
public class MixinGuiConnecting {

    @Shadow
    private boolean h;

    @Inject(method = {"connect"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireServerLoginEvent(String host, int port, CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(bib.z().C());
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(host, port)).build();
        }
        hh disconnectReason = ((ServerLoginEvent) Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData)))).getDisconnectReason();
        if (disconnectReason != null) {
            bib.z().a(new bky(bib.z().m, "connect.failed", disconnectReason));
            this.h = true;
            ci.cancel();
        }
    }

    @WrapOperation(method = {"<init>(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/ServerData;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setServerData(Lnet/minecraft/client/multiplayer/ServerData;)V")})
    public void transferRewrite(bib instance, bse serverData, Operation<Void> original) {
        if (serverData.b.contains(Worldsharing.CLOUD_DOMAIN_PATTERN)) {
            serverData = new bse(serverData.a, serverData.a, false);
        }
        instance.a(serverData);
    }
}
