package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.Border;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/BorderWidthForwarder.class */
public class BorderWidthForwarder implements Forwarder {
    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals("borderWidth");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) throws Exception {
        if (widget instanceof AbstractWidget) {
            AbstractWidget<?> abstractWidget = (AbstractWidget) widget;
            float width = ((Float) object.value()).floatValue();
            if (!abstractWidget.hasBorder()) {
                abstractWidget.border = new Border();
            }
            abstractWidget.border.setWidth(width);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void reset(Widget widget, String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        AbstractWidget abstractWidget = (AbstractWidget) widget;
        abstractWidget.border = null;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        return Float.TYPE;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 1;
    }
}
