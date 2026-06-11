package net.labymod.api.client.gui.hud.position;

import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/position/HudWidgetAnchor.class */
public enum HudWidgetAnchor {
    LEFT_TOP,
    CENTER_TOP,
    RIGHT_TOP,
    LEFT_BOTTOM,
    CENTER_BOTTOM,
    RIGHT_BOTTOM;

    @NotNull
    public static HudWidgetAnchor of(HudWidget<?> hudWidget) {
        HudWidgetConfig config = hudWidget.getConfig();
        return of(config.areaIdentifier(), config.horizontalAlignment().get());
    }

    @NotNull
    public static HudWidgetAnchor of(RectangleAreaPosition position, HorizontalHudWidgetAlignment horizontalAlignment) {
        if (horizontalAlignment == HorizontalHudWidgetAlignment.AUTO) {
            boolean isLocatedAtLeftArea = position == RectangleAreaPosition.TOP_LEFT || position == RectangleAreaPosition.MIDDLE_LEFT || position == RectangleAreaPosition.BOTTOM_LEFT;
            boolean isLocatedAtCenter = position == RectangleAreaPosition.TOP_CENTER || position == RectangleAreaPosition.MIDDLE_CENTER || position == RectangleAreaPosition.BOTTOM_CENTER;
            if (isLocatedAtLeftArea) {
                horizontalAlignment = HorizontalHudWidgetAlignment.LEFT;
            } else if (isLocatedAtCenter) {
                horizontalAlignment = HorizontalHudWidgetAlignment.CENTER;
            } else {
                horizontalAlignment = HorizontalHudWidgetAlignment.RIGHT;
            }
        }
        boolean isLocatedAtTopArea = position == RectangleAreaPosition.TOP_LEFT || position == RectangleAreaPosition.TOP_CENTER || position == RectangleAreaPosition.TOP_RIGHT || position == RectangleAreaPosition.MIDDLE_LEFT || position == RectangleAreaPosition.MIDDLE_CENTER || position == RectangleAreaPosition.MIDDLE_RIGHT;
        switch (horizontalAlignment) {
            case LEFT:
                return isLocatedAtTopArea ? LEFT_TOP : LEFT_BOTTOM;
            case CENTER:
                return isLocatedAtTopArea ? CENTER_TOP : CENTER_BOTTOM;
            case RIGHT:
                return isLocatedAtTopArea ? RIGHT_TOP : RIGHT_BOTTOM;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(horizontalAlignment));
        }
    }

    public float getShiftX(HudSize size) {
        return getShiftX(size.getScaledWidth());
    }

    public float getShiftY(HudSize size) {
        return getShiftY(size.getScaledHeight());
    }

    public float getShiftX(float width) {
        switch (this) {
            case LEFT_TOP:
            case LEFT_BOTTOM:
                return 0.0f;
            case CENTER_TOP:
            case CENTER_BOTTOM:
                return width / 2.0f;
            case RIGHT_TOP:
            case RIGHT_BOTTOM:
                return width;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
        }
    }

    public float getShiftY(float height) {
        switch (this) {
            case LEFT_TOP:
            case CENTER_TOP:
            case RIGHT_TOP:
                return 0.0f;
            case LEFT_BOTTOM:
            case CENTER_BOTTOM:
            case RIGHT_BOTTOM:
                return height;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
        }
    }

    public float getGapX(float outer, float inner) {
        switch (ordinal()) {
            case 1:
            case 4:
                return (outer - inner) / 2.0f;
            case 2:
            case 5:
                return outer - inner;
            case 3:
            default:
                return 0.0f;
        }
    }

    public boolean isRight() {
        return this == RIGHT_TOP || this == RIGHT_BOTTOM;
    }

    public boolean isLeft() {
        return this == LEFT_TOP || this == LEFT_BOTTOM;
    }

    public boolean isCenter() {
        return this == CENTER_TOP || this == CENTER_BOTTOM;
    }
}
