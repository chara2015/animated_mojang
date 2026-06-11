package net.labymod.api.util;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ComponentUtil.class */
public class ComponentUtil {
    public static final Component COMMA = Component.text(", ");
    public static final Component AND = Component.space().append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.space());

    public static Component join(List<Component> elements) {
        return join(COMMA, AND, elements);
    }

    public static Component join(Component delimiter, List<Component> elements) {
        return join(delimiter, delimiter, elements);
    }

    public static Component join(Component delimiter, Component lastDelimiter, List<Component> elements) {
        if (elements.isEmpty()) {
            return Component.empty();
        }
        if (elements.size() == 1) {
            return elements.get(0);
        }
        Component joined = Component.empty();
        for (int i = 0; i < elements.size(); i++) {
            if (i != 0) {
                if (i == elements.size() - 1) {
                    joined.append(lastDelimiter);
                } else {
                    joined.append(delimiter);
                }
            }
            joined.append(elements.get(i));
        }
        return joined;
    }

    public static int getTextColor(Style style, int argb) {
        TextColor textColor = style.getColor();
        int color = textColor == null ? argb : textColor.getValue();
        if (color != argb) {
            color = ColorFormat.ARGB32.pack(color, ColorFormat.ARGB32.alpha(argb));
        }
        return color;
    }
}
