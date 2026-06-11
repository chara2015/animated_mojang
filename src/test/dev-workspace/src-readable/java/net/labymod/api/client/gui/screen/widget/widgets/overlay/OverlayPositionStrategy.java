package net.labymod.api.client.gui.screen.widget.widgets.overlay;

import java.util.Collections;
import java.util.List;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.util.bounds.Rectangle;
import org.spongepowered.include.com.google.common.collect.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/overlay/OverlayPositionStrategy.class */
public enum OverlayPositionStrategy {
    CUSTOM(Orientation.NONE, WidgetAlignment.CENTER, (ignored, bounds) -> {
        return 0.0f;
    }),
    X_X(Orientation.HORIZONTAL, WidgetAlignment.LEFT, (width, widgetBounds) -> {
        return widgetBounds.getX();
    }),
    MAXY_Y(Orientation.VERTICAL, WidgetAlignment.BOTTOM, (height, widgetBounds2) -> {
        return widgetBounds2.getMaxY();
    }),
    Y_MAXY(Orientation.VERTICAL, WidgetAlignment.TOP, (height2, widgetBounds3) -> {
        return widgetBounds3.getY() - height2;
    }),
    MAXX_MAXX(Orientation.HORIZONTAL, WidgetAlignment.RIGHT, (width2, widgetBounds4) -> {
        return widgetBounds4.getMaxX() - width2;
    });

    private static final List<OverlayPositionStrategy> HORIZONTAL;
    private static final List<OverlayPositionStrategy> VERTICAL;
    private final Orientation orientation;
    private final PositionConsumer positionConsumer;
    private final WidgetAlignment alignment;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/overlay/OverlayPositionStrategy$PositionConsumer.class */
    public interface PositionConsumer {
        float get(float f, Rectangle rectangle);
    }

    static {
        List<OverlayPositionStrategy> horizontal = Lists.newArrayList();
        List<OverlayPositionStrategy> vertical = Lists.newArrayList();
        OverlayPositionStrategy[] values = values();
        for (OverlayPositionStrategy value : values) {
            if (value.orientation == Orientation.HORIZONTAL) {
                horizontal.add(value);
            } else if (value.orientation == Orientation.VERTICAL) {
                vertical.add(value);
            }
        }
        HORIZONTAL = Collections.unmodifiableList(horizontal);
        VERTICAL = Collections.unmodifiableList(vertical);
    }

    OverlayPositionStrategy(Orientation orientation, WidgetAlignment alignment, PositionConsumer positionConsumer) {
        this.orientation = orientation;
        this.alignment = alignment;
        this.positionConsumer = positionConsumer;
    }

    public Orientation orientation() {
        return this.orientation;
    }

    public PositionConsumer position() {
        return this.positionConsumer;
    }

    public WidgetAlignment alignment() {
        return this.alignment;
    }

    public static List<OverlayPositionStrategy> horizontalValues() {
        return HORIZONTAL;
    }

    public static List<OverlayPositionStrategy> verticalValues() {
        return VERTICAL;
    }
}
