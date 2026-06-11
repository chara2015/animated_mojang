package net.labymod.core.client.gui.lss.style.modifier.processors;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/ColorPostProcessor.class */
public class ColorPostProcessor implements PostProcessor {
    private static final Map<String, TextColor> COLORS = new HashMap();

    static {
        for (TextColor color : ColorUtil.DEFAULT_COLORS) {
            COLORS.put(color.toString(), color);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        if (element instanceof Function) {
            Function function = (Function) element;
            String name = function.getName();
            return name.equals(ItemMetadata.RGB_KEY) || name.equals("rgba");
        }
        String rawValue = element.getRawValue();
        return rawValue.startsWith("#") || COLORS.get(rawValue) != null;
    }

    public static int parse(Function function, ProcessedObject[] values) {
        boolean hasAlpha = function.getName().equals("rgba");
        if (hasAlpha && values.length == 2) {
            int value = ((Integer) values[0].value()).intValue();
            return ColorFormat.ARGB32.pack(value, getAlpha(true, values[1]));
        }
        int red = ((Integer) values[0].value()).intValue();
        int green = ((Integer) values[1].value()).intValue();
        int blue = ((Integer) values[2].value()).intValue();
        return ColorFormat.ARGB32.pack(red, green, blue, getAlpha(hasAlpha, hasAlpha ? values[3] : null));
    }

    private static int getAlpha(boolean hasAlpha, ProcessedObject object) {
        float alpha = (!hasAlpha || object == null) ? 255.0f : ((Float) object.value()).floatValue();
        boolean isAlphaFloat = hasAlpha && object != null && object.rawValue().contains(".");
        return (int) (isAlphaFloat ? alpha * 255.0f : alpha);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        int color;
        if (element instanceof FixedElement) {
            String raw = element.getRawValue();
            if (raw.startsWith("#")) {
                String hex = raw.substring(1);
                if (hex.length() == 3) {
                    hex = hex.charAt(0) + hex.charAt(0) + hex.charAt(1) + hex.charAt(1) + hex.charAt(2) + hex.charAt(2);
                } else if (hex.length() == 4) {
                    hex = hex.charAt(0) + hex.charAt(0) + hex.charAt(1) + hex.charAt(1) + hex.charAt(2) + hex.charAt(2) + hex.charAt(3) + hex.charAt(3);
                }
                if (hex.length() == 6) {
                    color = Integer.parseInt(hex, 16) | (-16777216);
                } else if (hex.length() == 8) {
                    color = (int) Long.parseLong(hex, 16);
                } else {
                    throw new IllegalArgumentException("Unknown color: " + raw);
                }
                return Integer.valueOf(color);
            }
            TextColor textColor = COLORS.get(raw);
            if (textColor != null) {
                return Integer.valueOf(textColor.getValue() | (-16777216));
            }
            throw new IllegalArgumentException("Unknown color: " + raw);
        }
        if (element instanceof Function) {
            Function function = (Function) element;
            return Integer.valueOf(parse(function, function.firstValues(widget, key)));
        }
        return null;
    }
}
