package net.labymod.api.configuration.settings.type;

import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.user.permission.ClientPermission;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/SettingPermissionHolder.class */
public class SettingPermissionHolder {

    @Nullable
    private ClientPermission permission;

    public SettingPermissionHolder() {
    }

    public SettingPermissionHolder(PermissionRequired permissionRequired) {
        this(permissionRequired.value());
    }

    public SettingPermissionHolder(String permissionId) {
        set(permissionId);
    }

    @Nullable
    public String getPermissionId() {
        if (this.permission == null) {
            return null;
        }
        return this.permission.getIdentifier();
    }

    @Nullable
    public ClientPermission getPermission() {
        return this.permission;
    }

    public void set(@Nullable String permissionId) {
        if (permissionId == null) {
            this.permission = null;
        } else {
            this.permission = Laby.labyAPI().permissionRegistry().getPermission(permissionId);
        }
    }

    public void set(@Nullable SettingPermissionHolder other) {
        set(other == null ? null : other.getPermissionId());
    }
}
