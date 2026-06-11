package net.labymod.api.configuration.settings;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/SettingOverlayInfo.class */
public final class SettingOverlayInfo {
    private final BooleanSupplier activeSupplier;
    private final Component component;
    private final boolean infoAlwaysVisible;

    public SettingOverlayInfo(BooleanSupplier activeSupplier, Component component, boolean infoAlwaysVisible) {
        this.activeSupplier = activeSupplier;
        this.component = component;
        this.infoAlwaysVisible = infoAlwaysVisible;
    }

    public boolean isActive() {
        return this.activeSupplier.getAsBoolean();
    }

    public Component component() {
        return this.component;
    }

    public boolean isExclamationMarkAlwaysVisible() {
        return this.infoAlwaysVisible;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        SettingOverlayInfo that = (SettingOverlayInfo) obj;
        return Objects.equals(this.activeSupplier, that.activeSupplier) && Objects.equals(this.component, that.component) && this.infoAlwaysVisible == that.infoAlwaysVisible;
    }

    public int hashCode() {
        return Objects.hash(this.activeSupplier, this.component, Boolean.valueOf(this.infoAlwaysVisible));
    }

    public String toString() {
        return "SettingOverlayInfo[activeSupplier=" + String.valueOf(this.activeSupplier) + ", component=" + String.valueOf(this.component) + ", infoAlwaysVisible=" + this.infoAlwaysVisible + "]";
    }
}
