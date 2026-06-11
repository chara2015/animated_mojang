package net.labymod.core.client.gui.screen.widget.widgets.store;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.color.GradientDirection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/GradientWidget.class */
@AutoWidget
public class GradientWidget extends SimpleWidget {
    private final LssProperty<Direction> direction = new LssProperty<>(null);
    private final LssProperty<Integer> colorStart = new LssProperty<>(0);
    private final LssProperty<Integer> colorEnd = new LssProperty<>(0);

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        ScreenCanvas screenCanvas = context.canvas();
        RoundedData roundedData = null;
        BorderRadius borderRadius = getBorderRadius();
        if (borderRadius != null) {
            roundedData = RoundedData.builder().applyBorderRadius(borderRadius).build();
        }
        int argb0 = this.colorStart.get().intValue();
        int argb1 = this.colorEnd.get().intValue();
        Direction direction = this.direction.get();
        screenCanvas.submitGuiRect(bounds(), GuiRectangleRenderState.RectConfig.builder().setArgb(argb0).setRoundedData(roundedData).setGradient(direction == null ? null : direction.toGradientDirection(), argb0, argb1).build());
    }

    public LssProperty<Direction> direction() {
        return this.direction;
    }

    public LssProperty<Integer> colorStart() {
        return this.colorStart;
    }

    public LssProperty<Integer> colorEnd() {
        return this.colorEnd;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/GradientWidget$Direction.class */
    public enum Direction {
        VERTICAL_TOP_BOTTOM,
        VERTICAL_BOTTOM_TOP,
        HORIZONTAL_LEFT_RIGHT,
        HORIZONTAL_RIGHT_LEFT;

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public GradientDirection toGradientDirection() throws MatchException {
            switch (this) {
                case VERTICAL_TOP_BOTTOM:
                    return GradientDirection.TOP_TO_BOTTOM;
                case VERTICAL_BOTTOM_TOP:
                    return GradientDirection.BOTTOM_TO_TOP;
                case HORIZONTAL_LEFT_RIGHT:
                    return GradientDirection.LEFT_TO_RIGHT;
                case HORIZONTAL_RIGHT_LEFT:
                    return GradientDirection.RIGHT_TO_LEFT;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
    }
}
