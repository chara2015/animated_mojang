package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border;

import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/alignment/border/HorizontalAlignmentLine.class */
public class HorizontalAlignmentLine extends RangeAlignmentLine {
    private final Supplier<HorizontalCoordinates> coordinates;
    private final boolean center;

    private HorizontalAlignmentLine(boolean center, float range, Supplier<HorizontalCoordinates> coordinates) {
        super(range);
        this.center = center;
        this.coordinates = coordinates;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.AlignmentLine
    public void render(ScreenContext context, Rectangle chain) {
        HorizontalCoordinates coordinates = this.coordinates.get();
        float opacity = getOpacity(context.getTickDelta());
        context.canvas().submitRelativeRect(coordinates.x1, coordinates.y - (this.center ? 1 : 0), Math.abs(coordinates.x2 - coordinates.x1) + 1.0f, 1.0f, ColorFormat.ARGB32.pack(255, 255, 255, (int) (opacity * 255.0f)));
        super.render(context, chain);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border.RangeAlignmentLine
    protected float getDistance(Rectangle rectangle) {
        HorizontalCoordinates coordinates = this.coordinates.get();
        if (rectangle.getTop() < coordinates.y && rectangle.getBottom() > coordinates.y) {
            return 0.0f;
        }
        return Math.min(Math.abs(rectangle.getTop() - coordinates.y), Math.abs(rectangle.getBottom() - coordinates.y));
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border.RangeAlignmentLine
    public void align(MutableRectangle rectangle) {
        HorizontalCoordinates coordinates = this.coordinates.get();
        if (this.center) {
            if (coordinates.y > rectangle.getTop() - this.range && coordinates.y < rectangle.getBottom() + this.range) {
                rectangle.setY(coordinates.y - (rectangle.getHeight() / 2.0f));
                return;
            }
            return;
        }
        if (coordinates.y > rectangle.getTop() - this.range && coordinates.y < rectangle.getTop() + this.range) {
            rectangle.setY(coordinates.y);
        }
        if (coordinates.y > rectangle.getBottom() - this.range && coordinates.y < rectangle.getBottom() + this.range) {
            rectangle.setY(coordinates.y - rectangle.getHeight());
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/alignment/border/HorizontalAlignmentLine$HorizontalCoordinates.class */
    public static class HorizontalCoordinates {
        private final float y;
        private final float x1;
        private final float x2;

        private HorizontalCoordinates(float y, float x1, float x2) {
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
        }

        public static HorizontalCoordinates of(float y, float x1, float x2) {
            return new HorizontalCoordinates(y, x1, x2);
        }
    }

    public static HorizontalAlignmentLine border(float range, Supplier<HorizontalCoordinates> coordinates) {
        return new HorizontalAlignmentLine(false, range, coordinates);
    }

    public static HorizontalAlignmentLine center(float range, Supplier<HorizontalCoordinates> coordinates) {
        return new HorizontalAlignmentLine(true, range, coordinates);
    }
}
