package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.Notification;
import net.labymod.api.user.permission.ClientPermission;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.notification.DefaultNotificationController;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.moderation.Permission;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/moderation/PermissionPacketHandler.class */
public class PermissionPacketHandler implements PacketHandler<PermissionPacket> {
    private final PermissionRegistry permissionRegistry = Laby.references().permissionRegistry();
    private final DefaultNotificationController defaultNotificationController = (DefaultNotificationController) Laby.references().notificationController();

    public void handle(@NotNull UUID uuid, @NotNull PermissionPacket packet) {
        Collection<ClientPermission> enabledPermissions = new HashSet<>();
        Collection<ClientPermission> disabledPermissions = new HashSet<>();
        for (Permission.StatedPermission permission : packet.getPermissions()) {
            ClientPermission clientPermission = this.permissionRegistry.getPermission(permission.permission().getIdentifier());
            if (clientPermission != null) {
                boolean enabled = permission.allowed();
                boolean permissionEnabled = this.permissionRegistry.isPermissionEnabled(clientPermission.getIdentifier());
                if (enabled != permissionEnabled) {
                    (enabled ? enabledPermissions : disabledPermissions).add(clientPermission);
                    clientPermission.setEnabled(enabled);
                }
            }
        }
        this.defaultNotificationController.push(buildNotification(enabledPermissions, true));
        this.defaultNotificationController.push(buildNotification(disabledPermissions, false));
    }

    private Notification buildNotification(Collection<ClientPermission> permissions, boolean enabled) {
        if (permissions.isEmpty()) {
            return null;
        }
        TextColor color = enabled ? NamedTextColor.DARK_GREEN : NamedTextColor.DARK_RED;
        TextComponent.Builder builder = Component.text();
        int index = 0;
        int size = permissions.size();
        TextComponent.Builder displayNameBuilder = Component.text();
        for (ClientPermission permission : permissions) {
            displayNameBuilder.append(permission.displayName());
            if (index == size - 1) {
                break;
            }
            if (index == size - 2) {
                displayNameBuilder.append(Component.text(" ")).append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.text(" "));
                index++;
            } else {
                displayNameBuilder.append(Component.text(",")).append(Component.text(" "));
                index++;
            }
        }
        if (index == 1) {
            Component[] componentArr = new Component[2];
            componentArr[0] = displayNameBuilder.build();
            componentArr[1] = Component.translatable("labymod.misc." + (enabled ? "enabled" : "disabled"), color);
            builder.append(Component.translatable("labymod.notification.permission", componentArr));
        } else {
            Component[] componentArr2 = new Component[2];
            componentArr2[0] = displayNameBuilder.build();
            componentArr2[1] = Component.translatable("labymod.misc." + (enabled ? "enabled" : "disabled"), color);
            builder.append(Component.translatable("labymod.notification.permissions", componentArr2));
        }
        long duration = 4000 + (((long) index) * 750);
        return Notification.builder().title(Component.text("Permissions")).title(Component.text("Permissions")).text(builder.build()).duration(duration).type(Notification.Type.SYSTEM).icon(Icon.currentServer()).build();
    }
}
