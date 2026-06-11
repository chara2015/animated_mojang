package net.labymod.api.client.gui.screen.widget.size;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.RectangleState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/size/WidgetSize.class */
public class WidgetSize {
    private static final WidgetSize FIT_CONTENT = new WidgetSize(Type.FIT_CONTENT, -1.0f, false);
    private static final WidgetSize MAINTAIN_OTHER = new WidgetSize(Type.MAINTAIN_OTHER, -1.0f, false);
    private static final WidgetSize ZERO = fixed(0.0f);
    private final Type type;
    private final float value;
    private final boolean percentage;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/size/WidgetSize$Type.class */
    public enum Type {
        FIT_CONTENT,
        FIXED,
        MAINTAIN_OTHER
    }

    private WidgetSize(Type type, float value, boolean percentage) {
        this.type = type;
        this.value = value;
        this.percentage = percentage;
    }

    public static WidgetSize fitContent() {
        return FIT_CONTENT;
    }

    public static WidgetSize maintainOther() {
        return MAINTAIN_OTHER;
    }

    public static WidgetSize fixed(float value) {
        return new WidgetSize(Type.FIXED, value, false);
    }

    public static WidgetSize percentage(float value) {
        return new WidgetSize(Type.FIXED, value, true);
    }

    public static WidgetSize zero() {
        return ZERO;
    }

    public Type type() {
        return this.type;
    }

    public float value() {
        return this.value;
    }

    public boolean percentage() {
        return this.percentage;
    }

    public float computeValue(AbstractWidget<?> widget, WidgetSide side) {
        switch (this.type) {
            case FIT_CONTENT:
                return widget.getContentSize(widget.fitOuter().get().booleanValue() ? BoundsType.OUTER : BoundsType.INNER, side);
            case FIXED:
                RectangleState state = side == WidgetSide.WIDTH ? RectangleState.WIDTH : RectangleState.HEIGHT;
                if (this.percentage) {
                    return this.value - widget.bounds().getOffset(BoundsType.OUTER, state);
                }
                switch (widget.boxSizing().get()) {
                    case BORDER_BOX:
                        return this.value - widget.bounds().getOffset(BoundsType.BORDER, state);
                    case CONTENT_BOX:
                        return this.value;
                    default:
                        throw new IllegalStateException("Unexpected value: " + String.valueOf(widget.boxSizing().get()));
                }
            case MAINTAIN_OTHER:
                if (side == WidgetSide.WIDTH) {
                    return widget.bounds().getHeight();
                }
                return widget.bounds().getWidth();
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.type));
        }
    }
}
