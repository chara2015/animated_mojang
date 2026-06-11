package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/FoldingWidget.class */
@AutoWidget
public class FoldingWidget extends ListWidget<Widget> {
    private static final ModifyReason LIST_CONTENT = ModifyReason.of("listContent");
    private final LssProperty<Float> contentOffset;
    private final LssProperty<Boolean> previewExpand;
    private final Widget previewWidget;
    private final Widget contentWidget;
    private IconWidget unfoldWidget;
    private FlexibleContentWidget previewWrapper;
    private boolean expanded;
    private boolean alwaysWrapPreview;

    @Deprecated
    public FoldingWidget(Widget previewWidget, Widget contentWidget, boolean expanded) {
        this.contentOffset = new LssProperty<>(Float.valueOf(0.0f));
        this.previewExpand = new LssProperty<>(false);
        this.alwaysWrapPreview = false;
        this.previewWidget = previewWidget;
        this.contentWidget = contentWidget;
        this.expanded = expanded;
        this.previewWidget.addId("folding-preview");
        this.contentWidget.addId("folding-content");
        translateY().addChangeListener((property, oldValue, newValue) -> {
            if (this.parent == null || (this.parent instanceof ListWidget)) {
                return;
            }
            updateVisibility(this, this.parent);
        });
    }

    @Deprecated
    public FoldingWidget(Widget previewWidget, Widget contentWidget) {
        this(previewWidget, contentWidget, false);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget previewWrapper = new FlexibleContentWidget();
        previewWrapper.addId("preview-wrapper");
        previewWrapper.addFlexibleContent(this.previewWidget);
        DivWidget unfoldWrapper = new DivWidget();
        unfoldWrapper.addId("unfold-wrapper");
        unfoldWrapper.setPressable(() -> {
            this.labyAPI.minecraft().sounds().playButtonPress();
            setExpanded(!this.expanded);
        });
        this.unfoldWidget = new IconWidget(unfoldIcon());
        this.unfoldWidget.addId("unfold-widget");
        this.unfoldWidget.setAttributeState(AttributeState.ENABLED, true);
        unfoldWrapper.addChild(this.unfoldWidget);
        previewWrapper.addContent(unfoldWrapper);
        this.previewWrapper = previewWrapper;
        addChild(this.previewWrapper);
        if (this.expanded) {
            addChild(this.contentWidget);
        }
        setAttributeState(AttributeState.ACTIVE, this.expanded);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.contentWidget.setVisible(this.expanded);
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton != MouseButton.LEFT || !this.previewExpand.get().booleanValue() || !this.previewWrapper.bounds().isInRectangle(BoundsType.BORDER, mouse)) {
            return false;
        }
        this.labyAPI.minecraft().sounds().playButtonPress();
        setExpanded(!this.expanded);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        update();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return bounds().getWidth(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        float height = 0.0f;
        if (this.previewWrapper != null) {
            height = 0.0f + this.previewWrapper.bounds().getHeight(type);
        }
        if (this.expanded) {
            height += this.contentWidget.bounds().getHeight(BoundsType.OUTER);
        }
        return height + bounds().getVerticalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        if (type == AutoAlignType.HEIGHT) {
            return false;
        }
        return child == this.previewWrapper || child == this.contentWidget;
    }

    protected Icon unfoldIcon() {
        return this.expanded ? Textures.SpriteCommon.SMALL_UP_SHADOW : Textures.SpriteCommon.SMALL_DOWN_SHADOW;
    }

    protected void update() {
        if (this.previewWrapper == null) {
            return;
        }
        Bounds bounds = bounds();
        float x = bounds.getX();
        float y = bounds.getY();
        float width = bounds.getWidth();
        ReasonableMutableRectangle preview = this.previewWrapper.bounds().rectangle(BoundsType.OUTER);
        preview.setPosition(x, y, LIST_CONTENT);
        preview.setWidth(width, LIST_CONTENT);
        float height = preview.getHeight();
        if (this.expanded) {
            float contentOffset = this.contentOffset.get().floatValue();
            ReasonableMutableRectangle content = this.contentWidget.bounds().rectangle(BoundsType.OUTER);
            content.setPosition(x + contentOffset, y + height, LIST_CONTENT);
            content.setWidth(width - contentOffset, LIST_CONTENT);
            height += content.getHeight();
        }
        bounds.setHeight(height, LIST_CONTENT);
    }

    public LssProperty<Float> contentOffset() {
        return this.contentOffset;
    }

    public LssProperty<Boolean> previewExpand() {
        return this.previewExpand;
    }

    public Widget contentWidget() {
        return this.contentWidget;
    }

    public Widget previewWidget() {
        return this.previewWidget;
    }

    public FoldingWidget alwaysWrapPreview(boolean alwaysWrapPreview) {
        this.alwaysWrapPreview = alwaysWrapPreview;
        return this;
    }

    public boolean isAlwaysWrappingPreview() {
        return this.alwaysWrapPreview;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        if (expanded) {
            Widget firstChildIf = findFirstChildIf(widget -> {
                return widget == this.contentWidget;
            });
            if (firstChildIf == null) {
                addChildInitialized(this.contentWidget);
                this.contentWidget.setTranslateY(getTranslateY());
            }
        }
        this.expanded = expanded;
        setAttributeState(AttributeState.ACTIVE, expanded);
        if (this.unfoldWidget != null) {
            this.unfoldWidget.icon().set(unfoldIcon());
        }
        update();
        ((Widget) this.parent).updateBounds();
        ((Widget) this.parent.getParent()).updateBounds();
        if (this.parent instanceof ListWidget) {
            ListWidget<?> listWidget = (ListWidget) this.parent;
            listWidget.updateVisibility(listWidget, listWidget.getParent());
        }
    }
}
