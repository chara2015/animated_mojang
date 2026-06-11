package net.labymod.api.util.math;

import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/LssMath.class */
public class LssMath {
    public static float min(Widget widget, String key, ProcessedObject[] values) {
        float min = 2.1474836E9f;
        for (ProcessedObject value : values) {
            min = Math.min(min, parseInt(widget, key, value.value()));
        }
        return min;
    }

    public static float max(Widget widget, String key, ProcessedObject[] values) {
        float max = -2.1474836E9f;
        for (ProcessedObject value : values) {
            max = Math.max(max, parseInt(widget, key, value.value()));
        }
        return max;
    }

    public static float parseInt(Widget widget, String key, Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (value instanceof String) {
            String s = (String) value;
            if (s.endsWith("%")) {
                return getPercentageValue(widget, key, Float.parseFloat(s.substring(0, s.length() - 1)));
            }
            return Float.parseFloat(s);
        }
        throw new IllegalArgumentException("Invalid numeric value: '" + String.valueOf(value) + "' (" + value.getClass().getSimpleName() + ")");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static float calc(net.labymod.api.client.gui.screen.widget.Widget r5, java.lang.String r6, java.lang.String r7) {
        /*
            Method dump skipped, instruction units count: 367
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.util.math.LssMath.calc(net.labymod.api.client.gui.screen.widget.Widget, java.lang.String, java.lang.String):float");
    }

    public static float getPercentageValue(Parent parent, String key, float value) {
        Float size;
        float max;
        Float size2;
        if (parent == null || value == 0.0f) {
            return 0.0f;
        }
        boolean vertical = false;
        int heightIndex = key.indexOf("eight");
        if (heightIndex > 0) {
            char c = key.charAt(heightIndex - 1);
            if (Character.toLowerCase(c) == 'h') {
                vertical = true;
            }
        }
        boolean horizontal = false;
        int widthIndex = key.indexOf("idth");
        if (widthIndex > 0) {
            char c2 = key.charAt(widthIndex - 1);
            if (Character.toLowerCase(c2) == 'w') {
                horizontal = true;
            }
        }
        boolean vertical2 = vertical | (key.equals("top") || key.equals("bottom") || key.equals("maxHeight") || key.equals("minHeight"));
        boolean horizontal2 = horizontal | (key.equals("left") || key.equals("right") || key.equals("maxWidth") || key.equals("minWidth") || key.equals("innerRadius"));
        Bounds parentBounds = parent.bounds();
        if (vertical2) {
            if (parent instanceof Widget) {
                Widget widget = (Widget) parent;
                size2 = widget.getSize(SizeType.MAX, WidgetSide.HEIGHT);
            } else {
                size2 = null;
            }
            Float maxHeight = size2;
            if (maxHeight != null && ((Widget) parent).hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) {
                max = maxHeight.floatValue() - parentBounds.getVerticalOffset(BoundsType.OUTER);
            } else {
                max = parentBounds.getHeight(BoundsType.INNER);
            }
        } else if (horizontal2) {
            if (parent instanceof Widget) {
                Widget widget2 = (Widget) parent;
                size = widget2.getSize(SizeType.MAX, WidgetSide.WIDTH);
            } else {
                size = null;
            }
            Float maxWidth = size;
            if (maxWidth != null && ((Widget) parent).hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
                max = maxWidth.floatValue() - parentBounds.getHorizontalOffset(BoundsType.OUTER);
            } else {
                max = parentBounds.getWidth(BoundsType.INNER);
            }
        } else {
            throw new IllegalArgumentException("Percentage cannot be calculated for key: '" + key + "' (requires a width or height defining key)");
        }
        return (max * value) / 100.0f;
    }

    private static float applyOperator(String text, char operator, float n1, float n2) {
        switch (operator) {
            case ImGuiFlags.StyleColors.PlotHistogram /* 42 */:
                return n1 * n2;
            case ImGuiFlags.StyleColors.PlotHistogramHovered /* 43 */:
                return n1 + n2;
            case ',':
            case ImGuiFlags.StyleColors.TableBorderLight /* 46 */:
            default:
                throw new IllegalArgumentException("Invalid calc expression: Didn't expect " + operator + " (\"" + text + "\")");
            case ImGuiFlags.StyleColors.TableBorderStrong /* 45 */:
                return n1 - n2;
            case ImGuiFlags.StyleColors.TableRowBg /* 47 */:
                return n1 / n2;
        }
    }
}
