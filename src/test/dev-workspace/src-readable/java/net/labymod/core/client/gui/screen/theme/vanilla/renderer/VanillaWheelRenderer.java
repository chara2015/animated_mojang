package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.util.ColorUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaWheelRenderer.class */
public class VanillaWheelRenderer extends ThemeRenderer<WheelWidget> {
    public VanillaWheelRenderer() {
        super("Wheel");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(WheelWidget widget, ScreenContext context) {
        float radius = widget.bounds().getWidth(BoundsType.INNER) / 2.0f;
        for (AbstractWidget<?> child : widget.getChildren()) {
            if (child instanceof WheelWidget.Segment) {
                WheelWidget.Segment segment = (WheelWidget.Segment) child;
                renderSegmentBackground(widget, context, segment, widget.bounds().getCenterX(), widget.bounds().getCenterY(), radius);
            }
        }
    }

    private void renderSegmentBackground(WheelWidget wheelWidget, ScreenContext context, WheelWidget.Segment segment, float centerX, float centerY, float radius) {
        int color;
        float startingAngle = segment.getStartingAngle();
        float endingAngle = segment.getEndingAngle();
        float innerRadius = wheelWidget.innerRadius().get().floatValue();
        double segmentDistanceRadians = (float) Math.toRadians(wheelWidget.segmentDistanceDegrees().get().floatValue() / 2.0f);
        ScreenCanvas renderState = context.canvas();
        if (segment.isVisible()) {
            if (segment.isHighlighted()) {
                color = wheelWidget.segmentHighlightColor().get().intValue();
            } else if (segment.isSegmentSelected() && segment.isSelectable()) {
                color = wheelWidget.segmentSelectedColor().get().intValue();
            } else {
                color = wheelWidget.segmentBackgroundColor().get().intValue();
            }
            LssProperty<Integer> segmentColorProperty = segment.segmentColor();
            segmentColorProperty.set(Integer.valueOf(color));
            renderState.submitTrapezoid(centerX + (((float) Math.sin(startingAngle)) * radius), centerY + (((float) Math.cos(startingAngle)) * radius), centerX + (((float) Math.sin(endingAngle)) * radius), centerY + (((float) Math.cos(endingAngle)) * radius), centerX + (((float) Math.sin(endingAngle)) * innerRadius), centerY + (((float) Math.cos(endingAngle)) * innerRadius), centerX + (((float) Math.sin(startingAngle)) * innerRadius), centerY + (((float) Math.cos(startingAngle)) * innerRadius), ColorUtil.lerpedColor(wheelWidget.backgroundColorTransitionDuration().get().longValue(), context.getTickDelta(), segmentColorProperty));
        }
        renderState.submitTriangle(centerX + (((float) Math.sin(((double) startingAngle) - segmentDistanceRadians)) * (innerRadius - 1.0f)), centerY + (((float) Math.cos(((double) startingAngle) - segmentDistanceRadians)) * (innerRadius - 1.0f)), centerX + (((float) Math.sin(((double) endingAngle) + segmentDistanceRadians)) * (innerRadius - 1.0f)), centerY + (((float) Math.cos(((double) endingAngle) + segmentDistanceRadians)) * (innerRadius - 1.0f)), centerX, centerY, wheelWidget.innerBackgroundColor().get().intValue());
    }
}
