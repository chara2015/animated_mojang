package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/BorderPostProcessor.class */
public class BorderPostProcessor implements PostProcessor {
    private static final Border NO_BORDER = new Border(0, 0);
    private final WidgetModifier modifier;

    public BorderPostProcessor(WidgetModifier modifier) {
        this.modifier = modifier;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return key.equals("border");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        String[] sizes;
        String color;
        String rawValue = element.getRawValue();
        if (rawValue.equals("none")) {
            return NO_BORDER;
        }
        int rgbIndex = rawValue.indexOf(ItemMetadata.RGB_KEY);
        if (rgbIndex != -1) {
            sizes = rawValue.substring(0, rgbIndex - 1).split(" ");
            color = rawValue.substring(rgbIndex);
        } else {
            sizes = rawValue.split(" ");
            color = sizes[sizes.length - 1];
        }
        ProcessedObject[] objects = this.modifier.processValue(widget, Integer.TYPE, "color", color);
        int colorValue = ((Integer) objects[0].value()).intValue();
        int length = sizes.length;
        if (length <= 2) {
            return new Border(Integer.parseInt(sizes[0]), colorValue);
        }
        return new Border(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]), Integer.parseInt(sizes[2]), Integer.parseInt(sizes[3]), colorValue);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/BorderPostProcessor$Border.class */
    public static class Border {
        public final int leftWidth;
        public final int topWidth;
        public final int rightWidth;
        public final int bottomWidth;
        public final int color;

        public Border(int size, int color) {
            this.leftWidth = size;
            this.topWidth = size;
            this.rightWidth = size;
            this.bottomWidth = size;
            this.color = color;
        }

        public Border(int leftWidth, int topWidth, int rightWidth, int bottomWidth, int color) {
            this.leftWidth = leftWidth;
            this.topWidth = topWidth;
            this.rightWidth = rightWidth;
            this.bottomWidth = bottomWidth;
            this.color = color;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Border border = (Border) o;
            return this.leftWidth == border.leftWidth && this.topWidth == border.topWidth && this.rightWidth == border.rightWidth && this.bottomWidth == border.bottomWidth && this.color == border.color;
        }

        public int hashCode() {
            int result = this.leftWidth;
            return (31 * ((31 * ((31 * ((31 * result) + this.topWidth)) + this.rightWidth)) + this.bottomWidth)) + this.color;
        }
    }
}
