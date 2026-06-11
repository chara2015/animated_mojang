package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/emotes/VariableListWidget.class */
@AutoWidget
public class VariableListWidget extends ListWidget<AbstractWidget<?>> {
    private static final ModifyReason ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    private AbstractWidget<?> flexible;

    public void setFlexibleChild(AbstractWidget<?> child) {
        this.flexible = child;
        super.addChild(child);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        float fMin;
        Bounds bounds = bounds();
        float x = bounds.getX();
        float y = bounds.getY();
        float width = bounds.getWidth();
        float height = bounds.getHeight();
        float centerX = x + (width / 2.0f);
        float flexibleHeight = height;
        for (T child : this.children) {
            if (this.flexible != child) {
                Bounds childBounds = child.bounds();
                flexibleHeight -= childBounds.getHeight(BoundsType.OUTER);
            }
        }
        for (T child2 : this.children) {
            ReasonableMutableRectangle rectangle = child2.bounds().rectangle(BoundsType.OUTER);
            if (this.flexible == child2) {
                WidgetSize maxSize = child2.sizes().getSize(SizeType.MAX, WidgetSide.WIDTH);
                float size = maxSize == null ? 0.0f : maxSize.value();
                if (size == 0.0f) {
                    fMin = flexibleHeight;
                } else {
                    fMin = Math.min(size, flexibleHeight);
                }
                float size2 = fMin;
                rectangle.setSize(size2, ENTRY_BOUNDS);
                rectangle.setPosition(centerX - (size2 / 2.0f), y, ENTRY_BOUNDS);
                y += size2;
            } else {
                float childX = centerX - (rectangle.getWidth() / 2.0f);
                rectangle.setPosition(childX, y, ENTRY_BOUNDS);
                y += rectangle.getHeight();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        return type == AutoAlignType.POSITION || child == this.flexible;
    }
}
