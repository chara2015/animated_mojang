package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/WidthHeightForwarder.class */
public class WidthHeightForwarder implements Forwarder {
    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals("width") || key.equals("height");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) {
        WidgetSide side = key.equals("width") ? WidgetSide.WIDTH : WidgetSide.HEIGHT;
        WidgetSize size = null;
        Object value = object.value();
        if (value instanceof Float) {
            size = object.postProcessor() instanceof PercentagePostProcessor ? WidgetSize.percentage(((Float) value).floatValue()) : WidgetSize.fixed(((Float) value).floatValue());
        } else if (value instanceof String) {
            String mode = (String) value;
            if (mode.equals("fit-content")) {
                size = WidgetSize.fitContent();
            } else {
                if (mode.equals(side == WidgetSide.WIDTH ? "height" : "width")) {
                    size = WidgetSize.maintainOther();
                }
            }
        }
        if (size != null) {
            widget.setSize(SizeType.ACTUAL, side, size);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        if (value.equals("fit-content") || (!key.equals("width") ? value.equals("width") : value.equals("height"))) {
            return String.class;
        }
        return Float.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 2;
    }
}
