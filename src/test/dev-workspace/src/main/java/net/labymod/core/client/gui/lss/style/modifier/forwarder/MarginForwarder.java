package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/MarginForwarder.class */
public class MarginForwarder implements Forwarder {
    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals("margin");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) {
        if (widget instanceof AbstractWidget) {
            AbstractWidget<?> abstractWidget = (AbstractWidget) widget;
            Object value = object.value();
            if (value instanceof String) {
                String string = (String) value;
                String[] sides = string.split(" ");
                if (sides.length > 0) {
                    abstractWidget.marginTop().set(Float.valueOf(Float.parseFloat(sides[0])));
                }
                if (sides.length > 1) {
                    abstractWidget.marginRight().set(Float.valueOf(Float.parseFloat(sides[1])));
                }
                if (sides.length > 2) {
                    abstractWidget.marginBottom().set(Float.valueOf(Float.parseFloat(sides[2])));
                }
                if (sides.length > 3) {
                    abstractWidget.marginLeft().set(Float.valueOf(Float.parseFloat(sides[3])));
                    return;
                }
                return;
            }
            float padding = ((Float) value).floatValue();
            abstractWidget.marginLeft().set(Float.valueOf(padding));
            abstractWidget.marginTop().set(Float.valueOf(padding));
            abstractWidget.marginRight().set(Float.valueOf(padding));
            abstractWidget.marginBottom().set(Float.valueOf(padding));
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void reset(Widget widget, String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        AbstractWidget abstractWidget = (AbstractWidget) widget;
        abstractWidget.marginLeft().reset();
        abstractWidget.marginTop().reset();
        abstractWidget.marginRight().reset();
        abstractWidget.marginBottom().reset();
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        return value.contains(" ") ? String.class : Float.TYPE;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 1;
    }
}
