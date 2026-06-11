package net.labymod.core.main.user.permission;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.models.Implements;
import net.labymod.api.user.permission.ClientPermission;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import net.labymod.core.main.animation.old.animations.SneakingOldAnimation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/permission/DefaultPermissionRegistry.class */
@Singleton
@Implements(PermissionRegistry.class)
public class DefaultPermissionRegistry implements PermissionRegistry {
    private final Map<String, ClientPermission> permissions = new HashMap();

    @Inject
    public DefaultPermissionRegistry() {
    }

    @Override // net.labymod.api.user.permission.PermissionRegistry
    public ClientPermission register(@NotNull String permissionId, boolean defaultEnabled, boolean canForceEnable) {
        Class<?> callerClass = Reflection.getCallerClass();
        ClassLoader classLoader = callerClass.getClassLoader();
        String addonId = "labymod";
        if (classLoader instanceof AddonClassLoader) {
            addonId = ((AddonClassLoader) classLoader).getAddonInfo().getNamespace();
        }
        ClientPermission permission = new ClientPermission(permissionId, String.format(Locale.ROOT, "%s.permissions.%s.name", addonId, permissionId.replace("_", "-")), defaultEnabled, canForceEnable);
        this.permissions.put(permissionId, permission);
        return permission;
    }

    @Override // net.labymod.api.user.permission.PermissionRegistry
    @Nullable
    public ClientPermission getPermission(String permissionId) {
        return this.permissions.get(permissionId);
    }

    @Override // net.labymod.api.user.permission.PermissionRegistry
    public boolean isPermissionEnabled(String permissionId) {
        ClientPermission permission = getPermission(permissionId);
        return permission != null && (permission.isEnabled() || !Laby.labyAPI().serverController().isConnected());
    }

    @Override // net.labymod.api.user.permission.PermissionRegistry
    public boolean isPermissionEnabled(String permissionId, boolean condition) {
        return isPermissionEnabled(permissionId) && condition;
    }

    public void registerDefaultPermissions() {
        register("improved_lava", false);
        register("crosshair_sync", false, true);
        register("refill_fix", false);
        register(RangeOldAnimation.NAME, false);
        register(SlowdownOldAnimation.NAME, false);
        register("entity_marker", false);
        register("danger_warner", false);
        register("gui_all", true);
        register("gui_potion_effects", true);
        register("gui_armor_hud", true);
        register("gui_item_hud", true);
        register("chat_autotext", true);
        register("blockbuild", true, true);
        register(SneakingOldAnimation.NAME, true);
        register("sneaking_visual", true);
        register(FlintUrls.QUERY_TAGS_PARAM, true);
        register("chat", true);
        register("animations", true);
        register("saturation_bar", true);
    }

    public void disableAllPermissions() {
        for (ClientPermission value : this.permissions.values()) {
            value.setEnabled(false);
        }
    }

    public void resetPermissions() {
        for (ClientPermission clientPermission : this.permissions.values()) {
            clientPermission.setDefaultEnabled();
        }
    }

    public void setPermission(String id, boolean enabled) {
        ClientPermission permission = getPermission(id);
        if (permission != null) {
            permission.setEnabled(enabled);
        }
    }
}
