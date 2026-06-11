package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentUtil;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.notification.Notification;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.addon.ServerAPIAddonService;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonDisablePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/AddonDisablePacketHandler.class */
public class AddonDisablePacketHandler implements PacketHandler<AddonDisablePacket> {
    public static void pushDisableNotification(String... addonNames) {
        List<Component> components = new ArrayList<>();
        for (String addonName : addonNames) {
            components.add(Component.text(addonName, NamedTextColor.WHITE));
        }
        pushNotificationInternal(components, false);
    }

    private static void pushNotificationInternal(List<Component> handledAddons, boolean revert) {
        String str;
        if (handledAddons.isEmpty()) {
            return;
        }
        int size = handledAddons.size();
        if (size == 1) {
            str = "labymod.notification.addons.one";
        } else {
            str = "labymod.notification.addons.multiple";
        }
        String key = str;
        TextColor textColor = NamedTextColor.GRAY;
        Component[] componentArr = new Component[2];
        componentArr[0] = ComponentUtil.join(handledAddons);
        componentArr[1] = Component.translatable(revert ? "labymod.misc.enabled" : "labymod.misc.disabled", revert ? NamedTextColor.DARK_GREEN : NamedTextColor.DARK_RED);
        Component component = Component.translatable(key, textColor, componentArr);
        Laby.references().notificationController().push(Notification.builder().title(Component.text("Addons")).text(component).duration(5000 + (((long) (size - 1)) * 750)).type(Notification.Type.SYSTEM).icon(Icon.currentServer()).build());
    }

    public void handle(@NotNull UUID uuid, @NotNull AddonDisablePacket packet) {
        boolean handled;
        InstalledAddonInfo addonInfo;
        DefaultAddonService addonService = DefaultAddonService.getInstance();
        ServerAPIAddonService serverAPI = addonService.serverAPI();
        boolean revert = packet.action() == AddonDisablePacket.Action.REVERT;
        List<Component> handledAddons = new ArrayList<>();
        for (String namespace : packet.getAddonsToDisable()) {
            if (revert) {
                handled = serverAPI.revertForceDisable(namespace);
            } else {
                handled = serverAPI.forceDisable(namespace);
            }
            if (handled && (addonInfo = addonService.getAddonInfo(namespace)) != null) {
                handledAddons.add(Component.text(addonInfo.getDisplayName(), NamedTextColor.WHITE));
            }
        }
        pushNotificationInternal(handledAddons, revert);
    }
}
