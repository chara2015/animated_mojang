package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.TileWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/TilesGridWidget.class */
@AutoWidget
public class TilesGridWidget<T extends Widget> extends ListWidget<TileWidget<T>> {
    private static final ModifyReason ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    private final LssProperty<Float> spaceBetweenEntries = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Integer> tilesPerLine = new LssProperty<>(3);
    private final LssProperty<Float> tileHeight = new LssProperty<>(Float.valueOf(-1.0f));

    public TilesGridWidget() {
        translateX().addChangeListener((type, oldValue, newValue) -> {
            updateVisibility();
        });
        translateY().addChangeListener((type2, oldValue2, newValue2) -> {
            updateVisibility();
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        updateTiles();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        int tilesPerLine = this.tilesPerLine.get().intValue();
        int lines = MathHelper.ceil(this.children.size() / tilesPerLine);
        return (getTileHeight() * lines) + (this.spaceBetweenEntries.get().floatValue() * (lines - 1));
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget
    public void updateVisibility(ListWidget<?> list, Parent parent) {
        super.updateVisibility(list, parent);
        if (this.parent == null) {
            return;
        }
        ReasonableMutableRectangle parentBounds = parent.bounds().rectangle(BoundsType.MIDDLE);
        float translateY = list.getTranslateY();
        for (T tile : this.children) {
            if (tile.isDragging()) {
                tile.setVisible(true);
            } else {
                Bounds tileBounds = tile.bounds();
                boolean isInRectangle = parentBounds.isYInRectangle(tileBounds.getY() + translateY) || parentBounds.isYInRectangle((tileBounds.getY() + tileBounds.getHeight()) + translateY);
                tile.setVisible(isInRectangle);
            }
        }
    }

    private void updateVisibility() {
        Parent parent = this.parent;
        ListWidget<?> list = this;
        Widget closestContentWidget = getClosestContentWidget();
        if (closestContentWidget instanceof ScrollWidget) {
            ScrollWidget scroll = (ScrollWidget) closestContentWidget;
            parent = closestContentWidget;
            Widget widgetContentWidget = scroll.contentWidget();
            if (widgetContentWidget instanceof ListWidget) {
                ListWidget<?> content = (ListWidget) widgetContentWidget;
                list = content;
            }
        }
        updateVisibility(list, parent);
    }

    public void updateTiles() {
        boolean useFloatingPoint = useFloatingPointPosition().get().booleanValue();
        float gridTileWidth = transformFloatingPoint(useFloatingPoint, getTileWidth());
        float gridTileHeight = transformFloatingPoint(useFloatingPoint, getTileHeight());
        int indexX = 0;
        int indexY = 0;
        float x = transformFloatingPoint(useFloatingPoint, bounds().getX());
        float y = transformFloatingPoint(useFloatingPoint, bounds().getY());
        for (T tile : this.children) {
            Bounds bounds = tile.bounds();
            float horizontalOffset = transformFloatingPoint(useFloatingPoint, bounds.getHorizontalOffset(BoundsType.BORDER));
            float verticalOffset = transformFloatingPoint(useFloatingPoint, bounds.getVerticalOffset(BoundsType.BORDER));
            float spaceBetweenEntries = transformFloatingPoint(useFloatingPoint, this.spaceBetweenEntries.get().floatValue());
            bounds.setOuterPosition(x + (indexX * (gridTileWidth + spaceBetweenEntries)), y + (indexY * (gridTileHeight + spaceBetweenEntries)), ENTRY_BOUNDS);
            bounds.setOuterSize(gridTileWidth - horizontalOffset, gridTileHeight - verticalOffset, ENTRY_BOUNDS);
            indexX++;
            if (indexX >= this.tilesPerLine.get().intValue()) {
                indexX = 0;
                indexY++;
            }
        }
        updateVisibility();
    }

    public float getSpaceAvailable() {
        float spaceBetween = this.spaceBetweenEntries.get().floatValue();
        Bounds bounds = bounds();
        if (spaceBetween == 0.0f) {
            return bounds.getWidth();
        }
        float width = bounds.getWidth(BoundsType.INNER);
        return width - (spaceBetween * (this.tilesPerLine.get().intValue() - 1));
    }

    public float getTileWidth() {
        return getSpaceAvailable() / this.tilesPerLine.get().intValue();
    }

    public float getTileHeight() {
        return this.tileHeight.get().floatValue() < 0.0f ? getTileWidth() : this.tileHeight.get().floatValue();
    }

    public void addTile(T widget) {
        addChild(new TileWidget(widget, 1, 1), false);
    }

    public void addTileInitialized(T widget) {
        addChildInitialized(new TileWidget(widget, 1, 1), false);
    }

    @Deprecated
    public void addUnsortedTile(T widget) {
        addChild(new TileWidget(widget, 1, 1), false);
    }

    public void addSortedTile(T widget) {
        addChild(new TileWidget(widget, 1, 1), true);
    }

    public void addSortedTileInitialized(T widget) {
        addChildInitialized(new TileWidget(widget, 1, 1), true);
    }

    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }

    public LssProperty<Integer> tilesPerLine() {
        return this.tilesPerLine;
    }

    public LssProperty<Float> tileHeight() {
        return this.tileHeight;
    }
}
