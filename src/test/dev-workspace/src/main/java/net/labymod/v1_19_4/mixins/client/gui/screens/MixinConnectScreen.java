package net.labymod.v1_19_4.mixins.client.gui.screens;

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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/screens/MixinConnectScreen.class */
@Mixin({erz.class})
public abstract class MixinConnectScreen implements AddressResolveCallback {

    @Shadow
    volatile boolean q;

    @Shadow
    protected abstract void shadow$a(tj tjVar);

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void updateStatus(Component status) {
        ComponentMapper mapper = Laby.references().componentMapper();
        shadow$a((tj) mapper.toMinecraftComponent(status));
    }

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void abort() {
        this.q = true;
    }

    @Inject(method = {"startConnecting"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$fireServerLoginEvent(etd previousScreen, emh minecraft, fen address, fdq data, CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        Component disconnectReason = ((ServerLoginEvent) Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData)))).getDisconnectReason();
        if (disconnectReason != null) {
            emh.N().a(new esh(previousScreen, ti.m, (tj) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }

    @ModifyVariable(method = {"startConnecting"}, at = @At("HEAD"), index = 3, argsOnly = true)
    private static fdq labymod$transferRewrite(fdq value) {
        if (value.b.contains(Worldsharing.CLOUD_DOMAIN_PATTERN)) {
            value = new fdq(value.a, value.a, value.d());
        }
        return value;
    }
}
