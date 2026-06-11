package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.RectangleState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/bounds/Bounds.class */
public interface Bounds extends ReasonableMutableRectangle {
    void checkForChanges();

    void setOuterWidth(float f, ModifyReason modifyReason);

    void setOuterHeight(float f, ModifyReason modifyReason);

    void setMiddleWidth(float f, ModifyReason modifyReason);

    void setMiddleHeight(float f, ModifyReason modifyReason);

    void setOuterX(float f, ModifyReason modifyReason);

    void setOuterY(float f, ModifyReason modifyReason);

    void setOuterLeft(float f, ModifyReason modifyReason);

    void setOuterTop(float f, ModifyReason modifyReason);

    void setOuterRight(float f, ModifyReason modifyReason);

    void setOuterBottom(float f, ModifyReason modifyReason);

    void setOuterPosition(float f, float f2, ModifyReason modifyReason);

    void setOuterSize(float f, float f2, ModifyReason modifyReason);

    void setMiddleLeft(float f, ModifyReason modifyReason);

    void setMiddleTop(float f, ModifyReason modifyReason);

    void setMiddleRight(float f, ModifyReason modifyReason);

    void setMiddleBottom(float f, ModifyReason modifyReason);

    void setX(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setY(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setRightX(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setBottomY(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setLeft(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setTop(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setBottom(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setRight(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setPosition(float f, float f2, BoundsType boundsType, ModifyReason modifyReason);

    void setSize(float f, float f2, BoundsType boundsType, ModifyReason modifyReason);

    void setWidth(float f, BoundsType boundsType, ModifyReason modifyReason);

    void setHeight(float f, BoundsType boundsType, ModifyReason modifyReason);

    float getX(BoundsType boundsType);

    float getY(BoundsType boundsType);

    float getWidth(BoundsType boundsType);

    float getHeight(BoundsType boundsType);

    float getLeft(BoundsType boundsType);

    float getTop(BoundsType boundsType);

    float getRight(BoundsType boundsType);

    float getBottom(BoundsType boundsType);

    float getMaxX(BoundsType boundsType);

    float getMaxY(BoundsType boundsType);

    float getCenterX(BoundsType boundsType);

    float getCenterY(BoundsType boundsType);

    float getOffset(BoundsType boundsType, OffsetSide offsetSide);

    float getOffset(BoundsType boundsType, RectangleState rectangleState);

    float getHorizontalOffset(BoundsType boundsType);

    float getVerticalOffset(BoundsType boundsType);

    boolean isInRectangle(BoundsType boundsType, Point point);

    boolean isInRectangle(BoundsType boundsType, float f, float f2);

    boolean isXInRectangle(BoundsType boundsType, float f);

    boolean isYInRectangle(BoundsType boundsType, float f);

    ReasonableMutableRectangle rectangle(BoundsType boundsType);

    MutableRectangle copy(BoundsType boundsType);

    MutableRectangle copy(BoundsType boundsType, MutableRectangle mutableRectangle);

    WidgetStyleSheetUpdater getUpdater();

    Rectangle prevRectangle();

    boolean wasUpdatedThisFrame();

    static MutableRectangle absoluteBounds(Parent parent) {
        return absoluteBounds(parent, BoundsType.INNER);
    }

    static MutableRectangle absoluteBounds(Parent parent, BoundsType boundsType) {
        MutableRectangle rectangle = parent.bounds().rectangle(boundsType).copy();
        while (true) {
            Parent parent2 = parent.getParent();
            parent = parent2;
            if (parent2 != null) {
                if (parent instanceof ScreenRendererWidget) {
                    rectangle.setPosition(rectangle.getX() + parent.bounds().getX(BoundsType.INNER), rectangle.getY() + parent.bounds().getY(BoundsType.INNER));
                }
                if (parent instanceof Widget) {
                    rectangle.add(((Widget) parent).getTranslateX(), ((Widget) parent).getTranslateY());
                }
            } else {
                return rectangle;
            }
        }
    }
}
