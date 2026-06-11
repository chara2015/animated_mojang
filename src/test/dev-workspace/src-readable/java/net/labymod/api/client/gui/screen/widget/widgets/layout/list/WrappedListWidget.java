package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/list/WrappedListWidget.class */
@AutoWidget
public class WrappedListWidget<T extends Widget> extends ListWidget<T> {
    private static final ModifyReason MODIFY_REASON = ModifyReason.of("ListBounds");
    private final LssProperty<Float> spaceBetweenEntries = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> verticalSpaceBetweenEntries = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> horizontalSpaceBetweenEntries = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Integer> maxLines = new LssProperty<>(0);

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        updateChildren();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        return type == AutoAlignType.POSITION;
    }

    @Deprecated
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }

    public LssProperty<Float> verticalSpaceBetweenEntries() {
        return this.verticalSpaceBetweenEntries;
    }

    public LssProperty<Float> horizontalSpaceBetweenEntries() {
        return this.horizontalSpaceBetweenEntries;
    }

    public LssProperty<Integer> maxLines() {
        return this.maxLines;
    }

    protected void updateChildren() {
        int maxLines = this.maxLines.get().intValue();
        float spaceBetweenEntries = this.spaceBetweenEntries.get().floatValue();
        float verticalSpaceBetweenEntries = this.verticalSpaceBetweenEntries.get().floatValue();
        float horizontalSpaceBetweenEntries = this.horizontalSpaceBetweenEntries.get().floatValue();
        Bounds bounds = bounds();
        float maxX = bounds.getMaxX();
        float startX = bounds.getX();
        float startY = bounds.getY();
        float x = startX;
        float y = startY;
        float rowHeight = 0.0f;
        boolean overflow = false;
        for (T child : this.children) {
            ReasonableMutableRectangle childBounds = child.bounds().rectangle(BoundsType.OUTER);
            float childWidth = childBounds.getWidth();
            if (!overflow && (x + childWidth > maxX || forceNewLine(child))) {
                x = startX;
                float offsetY = rowHeight + spaceBetweenEntries + verticalSpaceBetweenEntries;
                y += offsetY;
                float nextY = MathHelper.ceil(y - startY);
                float lineOffset = MathHelper.ceil(maxLines * offsetY);
                if (maxLines > 0 && nextY >= lineOffset) {
                    y -= offsetY;
                    overflow = true;
                }
            }
            child.setVisible(!overflow);
            if (!overflow) {
                childBounds.setPosition(x, y, MODIFY_REASON);
                rowHeight = Math.max(childBounds.getHeight(), rowHeight);
                x += childWidth + spaceBetweenEntries + horizontalSpaceBetweenEntries;
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        float spaceBetweenEntries = this.spaceBetweenEntries.get().floatValue();
        float verticalSpaceBetweenEntries = this.verticalSpaceBetweenEntries.get().floatValue();
        Bounds bounds = bounds();
        float maxX = bounds.getMaxX();
        float startX = bounds.getX();
        float startY = bounds.getY();
        float x = startX;
        float y = startY;
        float rowHeight = 0.0f;
        for (T child : this.children) {
            if (child.isVisible()) {
                ReasonableMutableRectangle childBounds = child.bounds().rectangle(BoundsType.OUTER);
                float childWidth = childBounds.getWidth();
                if (x + childWidth > maxX || forceNewLine(child)) {
                    x = startX;
                    y += rowHeight + spaceBetweenEntries + verticalSpaceBetweenEntries;
                }
                rowHeight = Math.max(childBounds.getHeight(), rowHeight);
                x += childWidth + spaceBetweenEntries + verticalSpaceBetweenEntries;
            }
        }
        return (y - startY) + rowHeight + bounds.getVerticalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return bounds().getWidth(type);
    }

    protected boolean forceNewLine(Widget child) {
        return false;
    }
}
