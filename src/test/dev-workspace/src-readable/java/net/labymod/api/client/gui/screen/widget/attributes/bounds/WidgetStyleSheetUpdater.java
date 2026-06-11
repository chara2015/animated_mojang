package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/bounds/WidgetStyleSheetUpdater.class */
public interface WidgetStyleSheetUpdater {
    void onBoundsChanged(Rectangle rectangle, Rectangle rectangle2);

    Float getPadding(OffsetSide offsetSide);

    Float getBorder(OffsetSide offsetSide);

    Float getMargin(OffsetSide offsetSide);

    boolean isUsingFloatingPointPosition();
}
