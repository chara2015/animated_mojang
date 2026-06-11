package net.labymod.core.client.gui.lss.style.modifier.forwarder.priority;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.PropertyValueAccessorForwarder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/priority/AbstractPriorityForwarder.class */
public abstract class AbstractPriorityForwarder extends PropertyValueAccessorForwarder {
    private final String[] keys = getKeys();

    abstract String[] getKeys();

    @Override // net.labymod.core.client.gui.lss.style.modifier.forwarder.PropertyValueAccessorForwarder, net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        for (String priorityKey : this.keys) {
            if (key.equals(priorityKey)) {
                return true;
            }
        }
        return false;
    }
}
