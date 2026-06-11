package net.labymod.api.user.permission;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent;
import net.labymod.serverapi.core.model.moderation.Permission;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/permission/ClientPermission.class */
public class ClientPermission extends Permission {
    private static final String SERVER_FEATURES_TRANSLATION_KEY = "labymod.ui.settings.serverFeatures.";
    private final Component displayName;
    private final boolean defaultEnabled;
    private final boolean canForceEnable;
    private boolean enabled;
    private boolean stateChangeInProgress;
    private SettingOverlayInfo overlayInfo;

    public ClientPermission(String permissionId, String translatableKey, boolean enabled, boolean canForceEnable) {
        super(permissionId);
        this.stateChangeInProgress = true;
        this.enabled = enabled;
        this.displayName = Component.translatable(translatableKey, new Component[0]);
        this.defaultEnabled = enabled;
        this.canForceEnable = canForceEnable;
    }

    public Component displayName() {
        return this.displayName;
    }

    public boolean isDefaultEnabled() {
        return this.defaultEnabled;
    }

    public void setDefaultEnabled() {
        setEnabled(this.defaultEnabled);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isCanForceEnable() {
        return this.canForceEnable;
    }

    public void setEnabled(boolean enabled) {
        if (isEnabled() == enabled) {
            return;
        }
        if (!this.stateChangeInProgress) {
            throw new IllegalStateException("You cannot enable/disable this permission in the event context");
        }
        this.stateChangeInProgress = false;
        this.enabled = enabled;
        Laby.fireEvent(new PermissionStateChangeEvent(this, enabled ? PermissionStateChangeEvent.State.ALLOWED : PermissionStateChangeEvent.State.DISALLOWED));
        this.stateChangeInProgress = true;
    }

    public SettingOverlayInfo overlayInfo() {
        String str;
        String str2;
        if (this.overlayInfo == null) {
            if (isDefaultEnabled()) {
                str2 = "labymod.ui.settings.serverFeatures.disabled";
            } else {
                if (this.canForceEnable) {
                    str = "disabledDefaultForceEnable";
                } else {
                    str = "disabledDefault";
                }
                str2 = "labymod.ui.settings.serverFeatures." + str;
            }
            String translation = str2;
            this.overlayInfo = new SettingOverlayInfo(() -> {
                return Laby.labyAPI().serverController().isConnected() || !isEnabled();
            }, Component.translatable(translation, NamedTextColor.RED), true);
        }
        return this.overlayInfo;
    }

    public boolean hasCurrentlyPermission() {
        LabyAPI labyAPI = Laby.labyAPI();
        boolean permissionEnabled = !labyAPI.serverController().isConnected() || isEnabled();
        return (isDefaultEnabled() || labyAPI.minecraft().isIngame()) && permissionEnabled;
    }
}
