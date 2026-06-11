package net.labymod.v1_21_4.mixins.client.gui.screens;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/screens/MixinConnectScreen.class */
@Mixin({ftl.class})
public abstract class MixinConnectScreen implements AddressResolveCallback {

    @Shadow
    volatile boolean w;

    @Shadow
    protected abstract void shadow$a(wp wpVar);

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void updateStatus(Component status) {
        ComponentMapper mapper = Laby.references().componentMapper();
        shadow$a((wp) mapper.toMinecraftComponent(status));
    }

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void abort() {
        this.w = true;
    }

    @Inject(method = {"startConnecting"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$fireServerLoginEvent(fum previousScreen, flk minecraft, ghs address, ggp data, boolean quickPlay, @Nullable ggt transferState, CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        CookieStorage cookieStorage = MinecraftUtil.fromMinecraft(transferState);
        ServerLoginEvent event = (ServerLoginEvent) Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData), cookieStorage));
        Component disconnectReason = event.getDisconnectReason();
        if (disconnectReason != null) {
            flk.Q().a(new ftt(previousScreen, wo.r, (wp) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }

    @ModifyVariable(method = {"startConnecting"}, at = @At("HEAD"), index = 3, argsOnly = true)
    private static ggp labymod$transferRewrite(ggp value) {
        if (value.b.contains(Worldsharing.CLOUD_DOMAIN_PATTERN)) {
            value = new ggp(value.a, value.a, value.f());
        }
        return value;
    }
}
