package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectPosition;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/ObjectPositionForwarder.class */
public class ObjectPositionForwarder implements Forwarder {
    private final WidgetModifier widgetModifier;

    public ObjectPositionForwarder(WidgetModifier widgetModifier) {
        this.widgetModifier = widgetModifier;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return key.equals("objectPosition");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) throws Exception {
        Supplier<Float> offset;
        if (widget instanceof IconWidget) {
            IconWidget icon = (IconWidget) widget;
            ObjectPosition position = icon.objectPosition();
            String s = (String) object.value();
            String[] parts = s.split(" ");
            Collection<PositionEntry> entries = new ArrayList<>();
            int i = 0;
            while (i < parts.length) {
                Object value = parsePart(icon, null, parts[i]);
                Object addition = null;
                if ((value instanceof HorizontalAlignment) || (value instanceof VerticalAlignment)) {
                    if ((value == HorizontalAlignment.CENTER || value == VerticalAlignment.CENTER) && contains(entries, value.getClass())) {
                        value = value instanceof HorizontalAlignment ? VerticalAlignment.CENTER : HorizontalAlignment.CENTER;
                    }
                    if (contains(entries, value.getClass())) {
                        throw new IllegalArgumentException("Duplicate horizontal/vertical alignment (" + String.valueOf(value) + ") found in object-position");
                    }
                    if (parts.length > 1 && i < parts.length - 1) {
                        addition = parsePart(icon, value, parts[i + 1]);
                    }
                }
                if (value instanceof Supplier) {
                    offset = (Supplier) value;
                    value = i == 0 ? HorizontalAlignment.CENTER : VerticalAlignment.CENTER;
                } else if (addition instanceof Float) {
                    offset = (Supplier) addition;
                    i++;
                } else {
                    offset = () -> {
                        return Float.valueOf(0.0f);
                    };
                }
                if (parts.length == 1) {
                    if (value instanceof HorizontalAlignment) {
                        entries.add(new PositionEntry(this, VerticalAlignment.CENTER, () -> {
                            return Float.valueOf(0.0f);
                        }));
                    }
                    if (value instanceof VerticalAlignment) {
                        entries.add(new PositionEntry(this, HorizontalAlignment.CENTER, () -> {
                            return Float.valueOf(0.0f);
                        }));
                    }
                }
                entries.add(new PositionEntry(this, value, offset));
                i++;
            }
            for (PositionEntry entry : entries) {
                applyValue(position, entry.alignment, entry.offset);
            }
        }
    }

    private Object parsePart(IconWidget widget, Object value, String part) throws Exception {
        String uppercasePart = StringUtil.toUppercase(part);
        HorizontalAlignment horizontalAlignment = HorizontalAlignment.of(uppercasePart);
        if (horizontalAlignment != null) {
            return horizontalAlignment;
        }
        VerticalAlignment verticalAlignment = VerticalAlignment.of(uppercasePart);
        if (verticalAlignment != null) {
            return value;
        }
        if (!part.endsWith("%")) {
            float parsed = Float.parseFloat(part);
            return () -> {
                return Float.valueOf(parsed);
            };
        }
        float percentage = Float.parseFloat(part.substring(0, part.length() - 1)) / 100.0f;
        boolean horizontal = value instanceof HorizontalAlignment;
        return () -> {
            float size = horizontal ? widget.iconBounds().getWidth() : widget.iconBounds().getHeight();
            return Float.valueOf(size * percentage);
        };
    }

    private void applyValue(ObjectPosition position, Object alignment, Supplier<Float> offset) {
        if (alignment instanceof HorizontalAlignment) {
            position.setHorizontalAlignment((HorizontalAlignment) alignment);
            position.setHorizontalOffset(offset);
        }
        if (alignment instanceof VerticalAlignment) {
            position.setVerticalAlignment((VerticalAlignment) alignment);
            position.setVerticalOffset(offset);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        return String.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return -1;
    }

    private boolean contains(Collection<PositionEntry> entries, Class<?> alignmentClass) {
        for (PositionEntry entry : entries) {
            if (entry.alignment.getClass() == alignmentClass) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/ObjectPositionForwarder$PositionEntry.class */
    private class PositionEntry {
        private final Object alignment;
        private final Supplier<Float> offset;

        public PositionEntry(ObjectPositionForwarder objectPositionForwarder, Object alignment, Supplier<Float> offset) {
            this.alignment = alignment;
            this.offset = offset;
        }
    }
}
