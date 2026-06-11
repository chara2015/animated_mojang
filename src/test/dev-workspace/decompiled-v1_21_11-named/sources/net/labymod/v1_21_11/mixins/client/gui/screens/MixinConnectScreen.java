package net.labymod.v1_21_11.mixins.client.gui.screens;

import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/screens/MixinConnectScreen.class */
@Mixin({ConnectScreen.class})
public abstract class MixinConnectScreen implements AddressResolveCallback {

    @Shadow
    volatile boolean u;

    @Shadow
    protected abstract void shadow$a(Component component);

    public void updateStatus(net.labymod.api.client.component.Component status) {
        ComponentMapper mapper = Laby.references().componentMapper();
        shadow$a((Component) mapper.toMinecraftComponent(status));
    }

    public void abort() {
        this.u = true;
    }

    @Inject(method = {"startConnecting"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$fireServerLoginEvent(Screen previousScreen, Minecraft minecraft, ServerAddress address, ServerData data, boolean quickPlay, @Nullable TransferState transferState, CallbackInfo ci) {
        ConnectableServerData connectableServerDataCreateServerData = Laby.labyAPI().serverController().createServerData(data);
        if (connectableServerDataCreateServerData == null) {
            connectableServerDataCreateServerData = ConnectableServerData.builder().address(new net.labymod.api.client.network.server.ServerAddress(address.getHost(), address.getPort())).build();
        }
        CookieStorage cookieStorage = MinecraftUtil.fromMinecraft(transferState);
        ServerLoginEvent event = Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(connectableServerDataCreateServerData), cookieStorage));
        net.labymod.api.client.component.Component disconnectReason = event.getDisconnectReason();
        if (disconnectReason != null) {
            Minecraft.getInstance().setScreen(new DisconnectedScreen(previousScreen, CommonComponents.CONNECT_FAILED, (Component) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }

    @ModifyVariable(method = {"startConnecting"}, at = @At("HEAD"), index = 3, argsOnly = true)
    private static ServerData labymod$transferRewrite(ServerData value) {
        if (value.ip.contains(".laby.cloud:")) {
            value = new ServerData(value.name, value.name, value.type());
        }
        return value;
    }
}
