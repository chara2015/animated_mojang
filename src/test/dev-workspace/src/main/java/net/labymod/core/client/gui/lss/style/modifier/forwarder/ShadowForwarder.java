package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import java.util.Locale;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/ShadowForwarder.class */
public class ShadowForwarder implements Forwarder {
    private final WidgetModifier widgetModifier;

    public ShadowForwarder(WidgetModifier widgetModifier) {
        this.widgetModifier = widgetModifier;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals("boxShadow");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) throws Exception {
        Shadow shadow;
        if (widget instanceof AbstractWidget) {
            AbstractWidget<?> abstractWidget = (AbstractWidget) widget;
            Object value = object.value();
            if (value instanceof String) {
                String string = (String) value;
                String lowerCase = string.toLowerCase(Locale.ROOT);
                if (lowerCase.startsWith("classic ") || lowerCase.startsWith("classic-background")) {
                    boolean left = false;
                    boolean top = false;
                    boolean right = false;
                    boolean bottom = false;
                    boolean classicIsBackground = lowerCase.startsWith("classic-background");
                    for (String argument : string.split(" ")) {
                        switch (argument.toLowerCase(Locale.ROOT)) {
                            case "left":
                                left = true;
                                break;
                            case "top":
                                top = true;
                                break;
                            case "right":
                                right = true;
                                break;
                            case "bottom":
                                bottom = true;
                                break;
                        }
                    }
                    shadow = new Shadow(true, left, top, right, bottom, classicIsBackground);
                } else if (string.equals("none")) {
                    shadow = null;
                } else {
                    String[] values = string.split(" ");
                    int index = 0;
                    boolean inset = values[0].equals("inset");
                    if (inset) {
                        index = 0 + 1;
                    }
                    float offsetX = Float.parseFloat(values[index]);
                    float offsetY = Float.parseFloat(values[index + 1]);
                    float blur = Float.parseFloat(values[index + 2]);
                    float spread = Float.parseFloat(values[index + 3]);
                    StringBuilder colorString = new StringBuilder();
                    for (int i = index + 4; i < values.length; i++) {
                        colorString.append(values[i]);
                    }
                    ProcessedObject[] processValue = this.widgetModifier.processValue(widget, Integer.TYPE, key, colorString.toString());
                    int color = ((Integer) processValue[0].value()).intValue();
                    shadow = new Shadow(inset, offsetX, offsetY, spread, blur, color);
                }
                abstractWidget.shadow = shadow;
            }
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void reset(Widget widget, String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        AbstractWidget abstractWidget = (AbstractWidget) widget;
        abstractWidget.shadow = null;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        return String.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 1;
    }
}
