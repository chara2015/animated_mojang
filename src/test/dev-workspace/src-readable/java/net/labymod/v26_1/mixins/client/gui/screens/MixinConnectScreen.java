package net.labymod.v26_1.mixins.client.gui.screens;

import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/gui/screens/MixinConnectScreen.class */
@Mixin({ConnectScreen.class})
public abstract class MixinConnectScreen implements AddressResolveCallback {

    @Shadow
    volatile boolean aborted;

    @Shadow
    protected abstract void shadow$updateStatus(Component component);

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void updateStatus(net.labymod.api.client.component.Component status) {
        ComponentMapper mapper = Laby.references().componentMapper();
        shadow$updateStatus((Component) mapper.toMinecraftComponent(status));
    }

    @Override // net.labymod.core.client.network.server.connect.AddressResolveCallback
    public void abort() {
        this.aborted = true;
    }

    @Inject(method = {"startConnecting"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$fireServerLoginEvent(Screen previousScreen, Minecraft minecraft, ServerAddress address, ServerData data, boolean quickPlay, @Nullable TransferState transferState, CallbackInfo ci) {
        net.labymod.api.client.network.server.ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new net.labymod.api.client.network.server.ServerAddress(address.getHost(), address.getPort())).build();
        }
        CookieStorage cookieStorage = MinecraftUtil.fromMinecraft(transferState);
        ServerLoginEvent event = (ServerLoginEvent) Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData), cookieStorage));
        net.labymod.api.client.component.Component disconnectReason = event.getDisconnectReason();
        if (disconnectReason != null) {
            Minecraft.getInstance().setScreen(new DisconnectedScreen(previousScreen, CommonComponents.CONNECT_FAILED, (Component) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }

    @ModifyVariable(method = {"startConnecting"}, at = @At("HEAD"), index = 3, argsOnly = true)
    private static ServerData labymod$transferRewrite(ServerData value) {
        if (value.ip.contains(Worldsharing.CLOUD_DOMAIN_PATTERN)) {
            value = new ServerData(value.name, value.name, value.type());
        }
        return value;
    }
}
