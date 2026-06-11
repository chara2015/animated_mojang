package net.labymod.core.client.gui.hud;

import java.util.HashSet;
import java.util.Set;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.core.event.addon.lifecycle.AddonStateChangeEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/HudWidgetHolder.class */
public class HudWidgetHolder {
    private final String namespace;
    private final Set<HudWidget<?>> hudWidgets = new HashSet();
    private AddonStateChangeEvent.State state;

    public HudWidgetHolder(String namespace, boolean enabled) {
        this.namespace = namespace;
        this.state = enabled ? AddonStateChangeEvent.State.ENABLED : AddonStateChangeEvent.State.DISABLED;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void register(HudWidget<?> hudWidget) {
        this.hudWidgets.add(hudWidget);
    }

    public Set<HudWidget<?>> getHudWidgets() {
        return this.hudWidgets;
    }

    public void updateState(AddonStateChangeEvent.State state) {
        this.state = state;
    }

    public AddonStateChangeEvent.State state() {
        return this.state;
    }
}
