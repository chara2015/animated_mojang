package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.api.util.ListOrder;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/FlexibleContentWidget.class */
@AutoWidget
public class FlexibleContentWidget extends AbstractWidget<FlexibleContentEntry> {
    private static final ModifyReason FLEX_ENTRY_POSITION = ModifyReason.of("flexEntryPosition");
    private static final ModifyReason FLEX_BOUNDS_VERTICAL = ModifyReason.of("flexBoundsVertical");
    private static final ModifyReason FLEX_BOUNDS_HORIZONTAL = ModifyReason.of("flexBoundsHorizontal");
    private final LssProperty<FlexibleContentOrientation> orientation = new LssProperty<>(FlexibleContentOrientation.VERTICAL);
    private final LssProperty<Integer> spaceBetweenEntries = new LssProperty<>(0);
    private final LssProperty<ListOrder> listOrder = new LssProperty<>(ListOrder.NORMAL);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/FlexibleContentWidget$FlexibleContentOrientation.class */
    public enum FlexibleContentOrientation {
        VERTICAL,
        HORIZONTAL
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void applyStyleSheet(StyleSheet styleSheet) {
        for (int i = 0; i < 2; i++) {
            super.applyStyleSheet(styleSheet);
        }
    }

    public FlexibleContentEntry addFlexibleContent(Widget widget) {
        return addContent(widget, true);
    }

    public FlexibleContentEntry addContent(Widget widget) {
        return addContent(widget, false);
    }

    private FlexibleContentEntry addContent(Widget widget, boolean flexible) {
        return addChild(new FlexibleContentEntry(widget, flexible));
    }

    public FlexibleContentEntry addFlexibleContentInitialized(Widget widget) {
        return addContentInitialized(widget, true);
    }

    public FlexibleContentEntry addContentInitialized(Widget widget) {
        return addContentInitialized(widget, false);
    }

    private FlexibleContentEntry addContentInitialized(Widget widget, boolean flexible) {
        return addChildInitialized(new FlexibleContentEntry(widget, flexible));
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01df  */
    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void updateContentBounds() {
        /*
            Method dump skipped, instruction units count: 765
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget.updateContentBounds():void");
    }

    private void updateChildren(Widget widget) {
        for (Widget child : widget.getChildren()) {
            child.bounds().checkForChanges();
            updateChildren(child);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        Bounds bounds = bounds();
        if (this.orientation.get() == FlexibleContentOrientation.VERTICAL) {
            float maxWidth = 0.0f;
            for (T child : this.children) {
                if (!child.ignoreBounds().get().booleanValue()) {
                    Float childWidth = getFitWidth(child);
                    maxWidth = Math.max(maxWidth, childWidth != null ? childWidth.floatValue() : 0.0f);
                }
            }
            return maxWidth + bounds.getHorizontalOffset(type);
        }
        float width = 0.0f;
        int spaceBetweenEntries = this.spaceBetweenEntries.get().intValue();
        for (T child2 : this.children) {
            if (!child2.ignoreBounds().get().booleanValue()) {
                Float childWidth2 = getFitWidth(child2);
                width += (childWidth2 != null ? childWidth2.floatValue() : 0.0f) + spaceBetweenEntries;
            }
        }
        if (width != 0.0f) {
            width -= spaceBetweenEntries;
        }
        return width + bounds.getHorizontalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        if (this.orientation.get() != FlexibleContentOrientation.VERTICAL) {
            float maxHeight = 0.0f;
            for (T child : this.children) {
                if (!child.ignoreBounds().get().booleanValue()) {
                    Float childHeight = getFitHeight(child);
                    maxHeight = Math.max(maxHeight, childHeight != null ? childHeight.floatValue() : 0.0f);
                }
            }
            return Math.max(maxHeight, getDefaultContentHeight(type));
        }
        float height = 0.0f;
        int spaceBetweenEntries = this.spaceBetweenEntries.get().intValue();
        for (T child2 : this.children) {
            if (!child2.ignoreBounds().get().booleanValue()) {
                Float childHeight2 = getFitHeight(child2);
                height += (childHeight2 != null ? childHeight2.floatValue() : 0.0f) + spaceBetweenEntries;
            }
        }
        return height - spaceBetweenEntries;
    }

    public LssProperty<Integer> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }

    public LssProperty<FlexibleContentOrientation> orientation() {
        return this.orientation;
    }

    public LssProperty<ListOrder> listOrder() {
        return this.listOrder;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        if (type != AutoAlignType.WIDTH && type != AutoAlignType.HEIGHT) {
            return !(type == AutoAlignType.POSITION && (child instanceof FlexibleContentEntry) && ((FlexibleContentEntry) child).ignoreBounds().get().booleanValue()) && type == AutoAlignType.POSITION;
        }
        if ((child instanceof FlexibleContentEntry) && ((FlexibleContentEntry) child).isFlexible() && !((FlexibleContentEntry) child).ignoreBounds().get().booleanValue()) {
            return (type == AutoAlignType.WIDTH && !hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) || (type == AutoAlignType.HEIGHT && !hasSize(WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT));
        }
        return false;
    }
}
