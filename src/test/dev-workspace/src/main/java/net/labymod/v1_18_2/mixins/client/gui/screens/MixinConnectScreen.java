package net.labymod.v1_18_2.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;
import net.labymod.core.client.worldsharing.Worldsharing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/screens/MixinConnectScreen.class */
@Mixin({ecv.class})
public abstract class MixinConnectScreen implements AddressResolveCallback {

    @Shadow
    volatile boolean p;

    @Shadow
    protected abstract void shadow$a(qk qkVar);

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void updateStatus(Component status) {
        ComponentMapper mapper = Laby.references().componentMapper();
        shadow$a((qk) mapper.toMinecraftComponent(status));
    }

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void abort() {
        this.p = true;
    }

    @Inject(method = {"startConnecting"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$fireServerLoginEvent(edw previousScreen, dyr minecraft, end address, emx data, CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        qk disconnectReason = ((ServerLoginEvent) Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData)))).getDisconnectReason();
        if (disconnectReason != null) {
            dyr.D().a(new edc(previousScreen, qj.i, disconnectReason));
            ci.cancel();
        }
    }

    @WrapOperation(method = {"startConnecting"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setCurrentServer(Lnet/minecraft/client/multiplayer/ServerData;)V")})
    private static void transferRewrite(dyr instance, emx serverData, Operation<Void> original) {
        if (serverData.b.contains(Worldsharing.CLOUD_DOMAIN_PATTERN)) {
            serverData = new emx(serverData.a, serverData.a, false);
        }
        original.call(new Object[]{instance, serverData});
    }
}
