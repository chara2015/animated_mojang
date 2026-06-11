package net.labymod.api.user.permission;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/permission/PermissionRegistry.class */
@Referenceable
public interface PermissionRegistry {
    ClientPermission register(@NotNull String str, boolean z, boolean z2);

    @Nullable
    ClientPermission getPermission(String str);

    boolean isPermissionEnabled(String str);

    boolean isPermissionEnabled(String str, boolean z);

    default ClientPermission register(String permissionId) {
        return register(permissionId, false);
    }

    default ClientPermission register(String permissionId, boolean defaultEnabled) {
        return register(permissionId, defaultEnabled, false);
    }

    default boolean isPermissionEnabled(String permissionId, ConfigProperty<Boolean> property) {
        return isPermissionEnabled(permissionId, property.getOrDefault().booleanValue());
    }
}
