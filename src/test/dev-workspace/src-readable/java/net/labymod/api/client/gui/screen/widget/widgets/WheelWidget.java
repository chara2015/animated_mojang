package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/WheelWidget.class */
@AutoWidget
public class WheelWidget extends AbstractWidget<AbstractWidget<?>> {
    private static final ModifyReason CENTER_SEGMENT = ModifyReason.of("centerSegment");
    private final LssProperty<Boolean> selectable = new LssProperty<>(true);
    private final LssProperty<Float> innerRadius = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> segmentDistanceDegrees = (LssProperty) new LssProperty(Float.valueOf(2.0f)).addChangeListener((type, oldValue, newValue) -> {
        calculateAngles();
    });
    private final LssProperty<Integer> segmentBackgroundColor = new LssProperty<>(-2145773030);
    private final LssProperty<Integer> segmentHighlightColor = new LssProperty<>(-1702854528);
    private final LssProperty<Integer> segmentSelectedColor = new LssProperty<>(-1702854528);
    private final LssProperty<Integer> segmentBorderColor = new LssProperty<>(Integer.MIN_VALUE);
    private final LssProperty<Integer> innerBackgroundColor = new LssProperty<>(-2142417587);
    private final LssProperty<Integer> innerBorderColor = new LssProperty<>(-432786380);

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void applyStyleSheet(StyleSheet styleSheet) {
        super.applyStyleSheet(styleSheet);
        calculateAngles();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        MutableMouse mouse = context.mouse();
        Bounds bounds = bounds();
        float centerX = bounds.getCenterX();
        float centerY = bounds.getCenterY();
        float radius = bounds.getWidth(BoundsType.OUTER) / 2.0f;
        float centerDistanceSquared = MathHelper.square(((float) mouse.getXDouble()) - centerX) + MathHelper.square(((float) mouse.getYDouble()) - centerY);
        boolean segmentsHovered = this.selectable.get().booleanValue() && centerDistanceSquared >= MathHelper.square(this.innerRadius.get().floatValue()) && centerDistanceSquared <= MathHelper.square(radius);
        double angle = MathHelper.wrapRadians((float) Math.atan2(mouse.getXDouble() - ((double) centerX), mouse.getYDouble() - ((double) centerY)));
        Segment hoveredSegment = null;
        for (T child : this.children) {
            if (child instanceof Segment) {
                Segment segment = (Segment) child;
                if (hoveredSegment == null && segmentsHovered && MathHelper.isAngleBetween(angle, MathHelper.wrapRadians(segment.getStartingAngle()), MathHelper.wrapRadians(segment.getEndingAngle()), 6.283185307179586d)) {
                    hoveredSegment = segment;
                } else {
                    segment.setSegmentSelected(false);
                }
            }
        }
        for (T child2 : this.children) {
            if (child2 instanceof Segment) {
                Segment segment2 = (Segment) child2;
                float startingAngle = segment2.getStartingAngle();
                float endingAngle = segment2.getEndingAngle();
                if (segment2 == hoveredSegment) {
                    segment2.setSegmentSelected(true);
                }
                float middleAngle = (startingAngle + endingAngle) / 2.0f;
                float middleRadius = (this.innerRadius.get().floatValue() + radius) / 2.0f;
                float middleX = (float) (((double) centerX) + (Math.sin(middleAngle) * ((double) middleRadius)));
                float middleY = (float) (((double) centerY) + (Math.cos(middleAngle) * ((double) middleRadius)));
                segment2.bounds().setOuterPosition(middleX - (segment2.bounds().getWidth(BoundsType.OUTER) / 2.0f), middleY - (segment2.bounds().getHeight(BoundsType.OUTER) / 2.0f), CENTER_SEGMENT);
            }
        }
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return getContentDiameter() + bounds().getHorizontalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return getContentDiameter() + bounds().getVerticalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        for (AbstractWidget<?> child : this.children) {
            if (child instanceof Segment) {
                Segment segment = (Segment) child;
                if (segment.isSegmentSelected() && segment.onPress()) {
                    return true;
                }
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    private float getContentDiameter() {
        if (this.children.isEmpty()) {
            return 0.0f;
        }
        Bounds bounds = bounds();
        float centerX = bounds.getCenterX();
        float centerY = bounds.getCenterY();
        float radius = bounds.getWidth(BoundsType.OUTER) / 2.0f;
        float width = Float.MIN_VALUE;
        float height = Float.MIN_VALUE;
        for (T child : this.children) {
            width = Math.max(width, child.bounds().getWidth(BoundsType.OUTER));
            height = Math.max(height, child.bounds().getHeight(BoundsType.OUTER));
        }
        float middleRadius = (this.innerRadius.get().floatValue() + radius) / 2.0f;
        float left = (centerX - middleRadius) - (width / 2.0f);
        float top = (centerY - middleRadius) - (height / 2.0f);
        float right = centerX + middleRadius + (width / 2.0f);
        float bottom = centerY + middleRadius + (height / 2.0f);
        return Math.max(top - bottom, right - left);
    }

    private void calculateAngles() {
        float segmentSize = 360.0f / getSegmentCount();
        float halfSegmentDistance = segmentDistanceDegrees().get().floatValue() / 2.0f;
        float shift = (segmentSize / 2.0f) * (getSegmentShift() + 1);
        int index = 0;
        for (T child : this.children) {
            if (child instanceof Segment) {
                Segment segment = (Segment) child;
                segment.setStartingAngle((float) Math.toRadians(shift + (segmentSize * index) + halfSegmentDistance));
                segment.setEndingAngle((float) Math.toRadians(shift + (((segmentSize * index) + segmentSize) - halfSegmentDistance)));
                index++;
            }
        }
    }

    public void addSegment(Segment segment) {
        addChild(segment);
    }

    public void addSegmentInitialized(Segment segment) {
        addChildInitialized(segment);
        calculateAngles();
    }

    public LssProperty<Boolean> selectable() {
        return this.selectable;
    }

    public LssProperty<Float> innerRadius() {
        return this.innerRadius;
    }

    public LssProperty<Float> segmentDistanceDegrees() {
        return this.segmentDistanceDegrees;
    }

    public LssProperty<Integer> segmentBackgroundColor() {
        return this.segmentBackgroundColor;
    }

    public LssProperty<Integer> segmentHighlightColor() {
        return this.segmentHighlightColor;
    }

    public LssProperty<Integer> segmentSelectedColor() {
        return this.segmentSelectedColor;
    }

    public LssProperty<Integer> segmentBorderColor() {
        return this.segmentBorderColor;
    }

    public LssProperty<Integer> innerBackgroundColor() {
        return this.innerBackgroundColor;
    }

    public LssProperty<Integer> innerBorderColor() {
        return this.innerBorderColor;
    }

    public int getSegmentCount() {
        int count = 0;
        for (T child : this.children) {
            if (child instanceof Segment) {
                count++;
            }
        }
        return count;
    }

    public int getSegmentShift() {
        return getSegmentCount();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        if (type != AutoAlignType.POSITION) {
            return false;
        }
        return child instanceof Segment;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/WheelWidget$Segment.class */
    @AutoWidget
    public static class Segment extends AbstractWidget<Widget> {
        protected float startingAngle;
        protected float endingAngle;
        protected boolean segmentSelected;
        protected long highlightedAt;
        protected boolean selectable = true;
        private final LssProperty<Integer> segmentColor = new LssProperty<>(0);

        public float getStartingAngle() {
            return this.startingAngle;
        }

        public void setStartingAngle(float startingAngle) {
            this.startingAngle = startingAngle;
        }

        public float getEndingAngle() {
            return this.endingAngle;
        }

        public void setEndingAngle(float endingAngle) {
            this.endingAngle = endingAngle;
        }

        public boolean isSelectable() {
            return this.selectable;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }

        public boolean isSegmentSelected() {
            return this.segmentSelected;
        }

        public void highlight() {
            this.highlightedAt = TimeUtil.getMillis();
        }

        public void highlightAt(long millis) {
            this.highlightedAt = millis;
        }

        public void unhighlight() {
            this.highlightedAt = 0L;
        }

        public boolean isHighlighted() {
            return this.highlightedAt != 0 && TimeUtil.getMillis() - this.highlightedAt < 1000;
        }

        public void setSegmentSelected(boolean selected) {
            if (this.segmentSelected == selected) {
                return;
            }
            this.segmentSelected = selected;
            onSelectionChanged();
        }

        protected void onSelectionChanged() {
        }

        @Deprecated
        public LssProperty<Integer> segmentColor() {
            return this.segmentColor;
        }
    }
}
