package net.labymod.core.client.multiplayer;

import io.netty.buffer.ByteBuf;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.labymod.serverapi.ServerFeatureUpdateEvent;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.user.serverfeature.ServerFeatureService;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.core.client.network.server.DefaultAbstractServerController;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.core.main.user.shop.spray.SprayConstants;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.core.model.feature.Feature;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/multiplayer/ClientNetworkPacketListener.class */
@Referenceable
public abstract class ClientNetworkPacketListener {
    public static final int MAX_PAYLOAD_BYTES = 1048576;
    private static final ResourceLocation FANCY_FONT_NOTIFICATION = ResourceLocation.create("labymod", "fancy_font_server_resource_pack_notification");
    protected final ServerController serverController;
    protected final OldAnimationRegistry oldAnimationRegistry;
    private final DefaultServerFeatureService serverFeatureService;
    private final LabyAPI api = Laby.labyAPI();
    private final TextRendererProvider textRendererProvider = Laby.references().textRendererProvider();

    public abstract void onEntityRotate(Entity entity, float f, int i);

    public abstract <T> T getCurrentServer();

    public ClientNetworkPacketListener(ServerController serverController, OldAnimationRegistry oldAnimationRegistry, ServerFeatureService serverFeatureService) {
        this.serverController = serverController;
        this.oldAnimationRegistry = oldAnimationRegistry;
        this.serverFeatureService = (DefaultServerFeatureService) serverFeatureService;
        this.api.eventBus().registerListener(this);
    }

    public void onLoginOrServerSwitch() {
        ServerData currentServerData = this.serverController.createServerData(getCurrentServer());
        this.serverController.loginOrServerSwitch(currentServerData);
    }

    public void onLoadServerResourcePack() {
        if (!isFancyFontAllowed()) {
            disableFancyFont(SprayConstants.LABYMOD_PLUS_NEXT_SPRAY);
        }
    }

    public void onPayloadReceive(ResourceLocation location, ByteBuf buffer) {
        byte[] data = readBytes(buffer);
        if (data == null) {
            return;
        }
        ((DefaultAbstractServerController) this.serverController).payloadReceive(location.getNamespace(), location.getPath(), data);
    }

    public void onCustomPayloadReceive(@NotNull ResourceLocation channelIdentifier, @NotNull ByteBuf buffer, @NotNull InsertInfo info) {
        byte[] data = readBytes(buffer);
        if (data == null) {
            return;
        }
        PayloadChannelIdentifier identifier = PayloadChannelIdentifier.create(channelIdentifier.getNamespace(), channelIdentifier.getPath());
        boolean canHandleCustomPayload = this.serverController.handleCustomPayload(identifier, data);
        if (canHandleCustomPayload) {
            info.cancel();
        }
    }

    private byte[] readBytes(@NotNull ByteBuf buffer) {
        int readable = buffer.readableBytes();
        if (readable > 1048576) {
            return null;
        }
        byte[] data = new byte[readable];
        buffer.readBytes(data);
        return data;
    }

    @Subscribe
    public void onServerFeatureUpdate(ServerFeatureUpdateEvent event) {
        Feature.StatedFeature fancyFont = event.get(Feature.FANCY_FONT);
        if (fancyFont == null) {
            return;
        }
        if (fancyFont.isEnabled()) {
            enableFancyFont();
        } else {
            disableFancyFont(SprayConstants.LABYMOD_PLUS_NEXT_SPRAY);
        }
    }

    @Subscribe
    public void onThemeChange(ThemeChangeEvent event) {
        if (event.phase() == Phase.POST && (event.newTheme() instanceof FancyTheme)) {
            delayedFancyFontNotification(1000L);
        }
    }

    @Subscribe
    public void onThemeUpdate(ThemeUpdateEvent event) {
        if (event.reason() == FancyThemeConfig.FONT_UPDATE_REASON) {
            delayedFancyFontNotification(100L);
        }
    }

    private boolean isFancyFontAllowed() {
        return this.serverFeatureService.get().isFeatureEnabled(Feature.FANCY_FONT, false);
    }

    private boolean isFancyFontEnabledInConfig() {
        FancyThemeConfig config = (FancyThemeConfig) this.api.themeService().getThemeConfig(FancyThemeConfig.class);
        return config != null && config.fancyFont().get().booleanValue();
    }

    private void enableFancyFont() {
        if (isFancyFontEnabledInConfig()) {
            this.textRendererProvider.setUseCustomFont(true);
        }
    }

    private void disableFancyFont(long notificationDelay) {
        boolean usesFancyFont = this.textRendererProvider.useCustomFont();
        this.textRendererProvider.setUseCustomFont(false);
        if (usesFancyFont && isFancyFontEnabledInConfig()) {
            pushFancyFontServerResourcePackNotification(notificationDelay);
        }
    }

    private void delayedFancyFontNotification(long delay) {
        if (!isFancyFontAllowed() && isFancyFontEnabledInConfig() && !this.textRendererProvider.useCustomFont()) {
            pushFancyFontServerResourcePackNotification(delay);
        }
    }

    private void pushFancyFontServerResourcePackNotification(long delay) {
        this.api.notificationController().pushDelayed(FANCY_FONT_NOTIFICATION, delay, builder -> {
            builder.title(Component.translatable("labymod.notification.fancyFontServerResourcePack.title", NamedTextColor.YELLOW)).text(Component.translatable("labymod.notification.fancyFontServerResourcePack.description", new Component[0]));
        });
    }
}
