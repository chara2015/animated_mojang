package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import java.util.Locale;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.exception.ForwardException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/BorderRadiusForwarder.class */
public class BorderRadiusForwarder implements Forwarder {
    private static final String LOWER_EDGE_SOFTNESS = "lowerEdgeSoftness";
    private static final String UPPER_EDGE_SOFTNESS = "upperEdgeSoftness";
    private static final String BORDER_SOFTNESS = "borderSoftness";
    private static final String BORDER_RADIUS = "borderRadius";
    private static final String BORDER_TOP_LEFT_RADIUS = "borderTopLeftRadius";
    private static final String BORDER_TOP_RIGHT_RADIUS = "borderTopRightRadius";
    private static final String BORDER_BOTTOM_LEFT_RADIUS = "borderBottomLeftRadius";
    private static final String BORDER_BOTTOM_RIGHT_RADIUS = "borderBottomRightRadius";
    private static final String BORDER_THICKNESS = "borderThickness";
    private static final String BORDER_BACKGROUND = "borderBackground";

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals(BORDER_RADIUS) || key.equals(BORDER_TOP_LEFT_RADIUS) || key.equals(BORDER_TOP_RIGHT_RADIUS) || key.equals(BORDER_BOTTOM_LEFT_RADIUS) || key.equals(BORDER_BOTTOM_RIGHT_RADIUS) || key.equals(LOWER_EDGE_SOFTNESS) || key.equals(UPPER_EDGE_SOFTNESS) || key.equals(BORDER_SOFTNESS) || key.equals(BORDER_THICKNESS) || key.equals(BORDER_BACKGROUND);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) throws Exception {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        AbstractWidget<?> abstractWidget = (AbstractWidget) widget;
        if (abstractWidget.getBorderRadius() == null) {
            abstractWidget.setBorderRadius(new BorderRadius());
        }
        Object value = object.value();
        if (value instanceof String) {
            String str = (String) value;
            handleRadius(abstractWidget, str);
            return;
        }
        if (value instanceof Float) {
            Float f = (Float) value;
            if (key.equals(BORDER_TOP_LEFT_RADIUS) || key.equals(BORDER_TOP_RIGHT_RADIUS) || key.equals(BORDER_BOTTOM_RIGHT_RADIUS) || key.equals(BORDER_BOTTOM_LEFT_RADIUS)) {
                handleRadius(abstractWidget, key, f.floatValue());
            } else {
                handleBorderProperties(abstractWidget, key, f.floatValue());
            }
        }
        if (value instanceof Integer) {
            Integer i = (Integer) value;
            abstractWidget.getBorderRadius().setBorderBackground(i.intValue());
        }
    }

    private void handleBorderProperties(AbstractWidget<?> widget, String key, float value) {
        BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius == null) {
        }
        switch (key) {
            case "lowerEdgeSoftness":
                borderRadius.setLowerEdgeSoftness(value);
                break;
            case "upperEdgeSoftness":
                borderRadius.setUpperEdgeSoftness(value);
                break;
            case "borderThickness":
                borderRadius.setThickness(value);
                break;
            case "borderSoftness":
                borderRadius.setBorderSoftness(value);
                break;
        }
    }

    private void handleRadius(AbstractWidget<?> widget, String key, float value) {
        BorderRadius borderRadius;
        borderRadius = widget.getBorderRadius();
        if (borderRadius == null) {
            borderRadius = new BorderRadius();
            widget.setBorderRadius(borderRadius);
        }
        switch (key) {
            case "borderTopLeftRadius":
                borderRadius.setLeftTop(value);
                return;
            case "borderTopRightRadius":
                borderRadius.setRightTop(value);
                return;
            case "borderBottomRightRadius":
                borderRadius.setRightBottom(value);
                return;
            case "borderBottomLeftRadius":
                borderRadius.setLeftBottom(value);
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    private void handleRadius(AbstractWidget<?> widget, String value) throws ForwardException {
        float leftTop;
        float rightTop;
        float leftBottom;
        float rightBottom;
        String[] values = value.split(" ");
        if (values.length > 4) {
            throw new ForwardException(String.format(Locale.ROOT, "Invalid \"%s\" property. Too many values. (Max: 4, Current: %s)", "border-radius", Integer.valueOf(values.length)));
        }
        if (values.length == 4) {
            leftTop = Float.parseFloat(values[0]);
            rightTop = Float.parseFloat(values[1]);
            rightBottom = Float.parseFloat(values[2]);
            leftBottom = Float.parseFloat(values[3]);
        } else if (values.length == 3) {
            leftTop = Float.parseFloat(values[0]);
            rightTop = Float.parseFloat(values[1]);
            leftBottom = Float.parseFloat(values[1]);
            rightBottom = Float.parseFloat(values[2]);
        } else if (values.length == 2) {
            leftTop = Float.parseFloat(values[0]);
            rightTop = Float.parseFloat(values[1]);
            leftBottom = Float.parseFloat(values[1]);
            rightBottom = Float.parseFloat(values[0]);
        } else {
            float radius = Float.parseFloat(values[0]);
            leftTop = radius;
            rightTop = radius;
            leftBottom = radius;
            rightBottom = radius;
        }
        BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius == null) {
            widget.setBorderRadius(new BorderRadius(leftTop, rightTop, leftBottom, rightBottom));
        } else {
            borderRadius.setRadius(leftTop, rightTop, leftBottom, rightBottom);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void reset(Widget widget, String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        AbstractWidget<?> abstractWidget = (AbstractWidget) widget;
        BorderRadius borderRadius = abstractWidget.getBorderRadius();
        if (borderRadius != null) {
            switch (key) {
                case "borderSoftness":
                    borderRadius.setBorderSoftness(0.0f);
                    break;
                case "borderRadius":
                    borderRadius.setRadius(0.0f);
                    break;
                case "borderTopLeftRadius":
                    borderRadius.setLeftTop(0.0f);
                    break;
                case "borderTopRightRadius":
                    borderRadius.setRightTop(0.0f);
                    break;
                case "borderBottomRightRadius":
                    borderRadius.setRightBottom(0.0f);
                    break;
                case "borderBottomLeftRadius":
                    borderRadius.setLeftBottom(0.0f);
                    break;
                case "borderBackground":
                    borderRadius.setBorderBackground(0);
                    break;
                case "borderThickness":
                    borderRadius.setThickness(0.0f);
                    break;
                case "upperEdgeSoftness":
                    borderRadius.setUpperEdgeSoftness(0.0f);
                    break;
                case "lowerEdgeSoftness":
                    borderRadius.setLowerEdgeSoftness(0.0f);
                    break;
            }
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        if (key.equals(BORDER_RADIUS)) {
            return String.class;
        }
        return Float.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 0;
    }
}
