package net.labymod.core.client.entity;

import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.user.permission.PermissionRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/MouseDelayFix.class */
public class MouseDelayFix {
    public static final PermissionRegistry REGISTRY = Laby.references().permissionRegistry();
    public static final ConfigProperty<Boolean> PROPERTY = Laby.labyAPI().config().ingame().mouseDelayFix();

    public static boolean isEnabled() {
        return PROPERTY.get().booleanValue() && REGISTRY.isPermissionEnabled("crosshair_sync");
    }
}
