package net.labymod.api.client.gui.screen.activity.overlay;

import net.labymod.api.client.gui.screen.activity.overlay.initializer.InstanceScreenInitializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/overlay/RegisteredReplacement.class */
public class RegisteredReplacement {
    private final Class<?> clazz;
    private final InstanceScreenInitializer initializer;
    private boolean enabled = true;

    public RegisteredReplacement(Class<?> clazz, InstanceScreenInitializer initializer) {
        this.clazz = clazz;
        this.initializer = initializer;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public InstanceScreenInitializer getInitializer() {
        return this.initializer;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
