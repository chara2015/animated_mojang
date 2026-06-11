package net.labymod.core.client.gui.lss.style.modifier.forwarder.priority;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/priority/HighPriorityForwarder.class */
public class HighPriorityForwarder extends AbstractPriorityForwarder {
    @Override // net.labymod.core.client.gui.lss.style.modifier.forwarder.priority.AbstractPriorityForwarder
    String[] getKeys() {
        return new String[]{"marginLeft", "marginTop", "marginRight", "marginBottom", "paddingLeft", "paddingTop", "paddingRight", "paddingBottom"};
    }

    @Override // net.labymod.core.client.gui.lss.style.modifier.forwarder.PropertyValueAccessorForwarder, net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 1;
    }
}
