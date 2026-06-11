package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/MinMaxSizeForwarder.class */
public class MinMaxSizeForwarder implements Forwarder {
    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals("minWidth") || key.equals("maxWidth") || key.equals("minHeight") || key.equals("maxHeight");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) {
        WidgetSize size = (WidgetSize) object.value();
        setSize(widget, key, size);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void reset(Widget widget, String key) {
        setSize(widget, key, null);
    }

    private void setSize(Widget widget, String key, WidgetSize size) {
        switch (key) {
            case "minWidth":
                widget.setSize(SizeType.MIN, WidgetSide.WIDTH, size);
                break;
            case "maxWidth":
                widget.setSize(SizeType.MAX, WidgetSide.WIDTH, size);
                break;
            case "minHeight":
                widget.setSize(SizeType.MIN, WidgetSide.HEIGHT, size);
                break;
            case "maxHeight":
                widget.setSize(SizeType.MAX, WidgetSide.HEIGHT, size);
                break;
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        return WidgetSize.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 1;
    }
}
