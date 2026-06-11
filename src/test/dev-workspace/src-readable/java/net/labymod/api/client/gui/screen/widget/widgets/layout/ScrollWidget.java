package net.labymod.api.client.gui.screen.widget.widgets.layout;

import java.util.Objects;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/ScrollWidget.class */
@AutoWidget
public class ScrollWidget extends SimpleWidget {
    private static final ModifyReason CONTENT_BOUNDS = ModifyReason.of("contentBounds");
    private static final ModifyReason SCROLLBAR_AUTO_BOUNDS = ModifyReason.of("scrollbarAutoBounds");
    private final ListSession<?> session;
    private final Widget contentWidget;
    private final ScrollbarWidget scrollbarWidget;
    private final LssProperty<VerticalAlignment> childAlign;
    private final LssProperty<Boolean> enableScrollContent;
    private final LssProperty<Float> scrollSpeed;
    private final LssProperty<Boolean> scrollAlwaysVisible;
    private final LssProperty<Boolean> moveDirtBackground;
    private final LssProperty<VerticalAlignment> fixedPosition;
    private final LssProperty<Boolean> autoScroll;
    private final LssProperty<Boolean> modifyContentWidth;
    private float pressedScrollPositionY;
    private boolean dragging;
    private boolean rendered;

    public ScrollWidget(SessionedListWidget<?> listWidget) {
        this(listWidget, listWidget.listSession());
    }

    public ScrollWidget(VerticalListWidget<?> listWidget) {
        this(listWidget, listWidget.listSession());
    }

    public ScrollWidget(Widget widget, ListSession<?> session) {
        this.childAlign = new LssProperty<>(VerticalAlignment.TOP);
        this.enableScrollContent = new LssProperty<>(false);
        this.scrollSpeed = new LssProperty<>(Float.valueOf(-1.0f));
        this.scrollAlwaysVisible = new LssProperty<>(false);
        this.moveDirtBackground = new LssProperty<>(true);
        this.fixedPosition = new LssProperty<>(null);
        this.autoScroll = new LssProperty<>(false);
        this.modifyContentWidth = new LssProperty<>(true);
        this.contentWidget = widget;
        this.session = session;
        this.scrollbarWidget = new ScrollbarWidget(this);
        updateBounds();
    }

    private void scrollBackground(float delta) {
        Parent parent;
        String scrollBackgroundRenderer = (String) this.labyAPI.themeService().currentTheme().metadata().get(DefaultThemeVariables.SCROLL_BACKGROUND);
        if (this.moveDirtBackground.get().booleanValue() && scrollBackgroundRenderer != null) {
            Parent parent2 = getParent();
            if (Objects.isNull(parent2)) {
                return;
            }
            do {
                if (parent2 instanceof AbstractWidget) {
                    AbstractWidget<?> w = (AbstractWidget) parent2;
                    if (w.renderer().get().getName().equals(scrollBackgroundRenderer)) {
                        LssProperty<Integer> backgroundDirtShift = w.backgroundDirtShift();
                        backgroundDirtShift.set(Integer.valueOf((int) this.session.getScrollPositionY()));
                        return;
                    }
                }
                parent = parent2.getParent();
                parent2 = parent;
            } while (parent != null);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.contentWidget);
        addChild(this.scrollbarWidget);
        this.rendered = false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        updateScrollbarBounds();
        updateScrollContentOffset();
        updateContentBounds();
        super.renderWidget(context);
        this.rendered = true;
    }

    private void updateScrollContentOffset() {
        float scrollOffset = this.session.getRenderScrollPositionY();
        if (!isUsingFloatingPointPosition()) {
            scrollOffset = (float) Math.ceil(scrollOffset);
        }
        this.contentWidget.setTranslateY(-scrollOffset);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        float width;
        if (this.scrollAlwaysVisible.get().booleanValue() || isScrollbarRequired() || !this.modifyContentWidth.get().booleanValue()) {
            width = this.scrollbarWidget.bounds().getWidth(BoundsType.OUTER);
        } else {
            width = 0.0f;
        }
        float scrollBarWidth = width;
        Bounds contentBounds = this.contentWidget.bounds();
        Bounds scrollbarBounds = this.scrollbarWidget.bounds();
        Bounds bounds = bounds();
        contentBounds.setOuterPosition(bounds.getLeft(BoundsType.INNER), bounds.getTop(BoundsType.INNER), CONTENT_BOUNDS);
        if (!hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
            contentBounds.setOuterWidth(bounds.getWidth(BoundsType.INNER) - scrollBarWidth, CONTENT_BOUNDS);
        }
        if (!this.scrollbarWidget.useLssPosition().get().booleanValue()) {
            scrollbarBounds.setOuterX(bounds.getRight(BoundsType.INNER) - scrollBarWidth, SCROLLBAR_AUTO_BOUNDS);
            scrollbarBounds.setOuterY(bounds.getTop(BoundsType.INNER), SCROLLBAR_AUTO_BOUNDS);
            scrollbarBounds.setOuterRight(bounds.getRight(BoundsType.INNER), SCROLLBAR_AUTO_BOUNDS);
            scrollbarBounds.setOuterBottom(bounds.getBottom(BoundsType.INNER), SCROLLBAR_AUTO_BOUNDS);
        }
        super.updateContentBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.rendered && !didBoundsChangeThisFrame()) {
            updateScrollbarBounds();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        float left;
        float right;
        if (this.scrollbarWidget.useLssPosition().get().booleanValue()) {
            left = Math.min(this.contentWidget.bounds().getLeft(BoundsType.OUTER), isScrollbarRequired() ? this.scrollbarWidget.bounds().getLeft(BoundsType.OUTER) : 2.1474836E9f);
            right = Math.max(this.contentWidget.bounds().getRight(BoundsType.OUTER), isScrollbarRequired() ? this.scrollbarWidget.bounds().getRight(BoundsType.OUTER) : -2.1474836E9f);
        } else {
            left = this.contentWidget.bounds().getLeft(BoundsType.OUTER);
            right = this.contentWidget.bounds().getRight(BoundsType.OUTER) + this.scrollbarWidget.bounds().getWidth(BoundsType.OUTER);
        }
        return (right - left) + bounds().getHorizontalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return this.contentWidget.bounds().getHeight(BoundsType.OUTER) + bounds().getVerticalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        this.pressedScrollPositionY = this.session.getScrollPositionY() + mouse.getY();
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (!this.enableScrollContent.get().booleanValue() || !this.contentWidget.bounds().isInRectangle(mouse)) {
            return super.mouseDragged(mouse, button, deltaX, deltaY);
        }
        if (super.mouseDragged(mouse, button, deltaX, deltaY)) {
            return true;
        }
        Bounds bounds = bounds();
        if (isScrollbarRequired()) {
            if (bounds.isInRectangle(mouse.getX(), mouse.getY()) || this.dragging) {
                float previousPosition = this.session.getScrollPositionY();
                this.session.setScrollPositionY(this.pressedScrollPositionY - mouse.getY());
                updateScrollbarBounds();
                scrolled(previousPosition, this.session.getScrollPositionY());
                this.dragging = true;
                return true;
            }
            return false;
        }
        return false;
    }

    private float getScrollSpeed() {
        if (Objects.equals(this.scrollSpeed.get(), this.scrollSpeed.defaultValue())) {
            return this.contentWidget.getContentHeight(BoundsType.OUTER) / this.contentWidget.getChildren().size();
        }
        return this.scrollSpeed.get().floatValue();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (isScrollbarRequired()) {
            float previousPosition = this.session.getScrollPositionY();
            float scrollSpeed = getScrollSpeed();
            float delta = ((float) (-scrollDelta)) * scrollSpeed * (this.childAlign.get() == VerticalAlignment.BOTTOM ? -1 : 1);
            this.session.scroll(delta);
            updateScrollbarBounds();
            scrolled(previousPosition, this.session.getScrollPositionY());
            if (isHovered()) {
                return true;
            }
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        this.dragging = false;
        return super.mouseReleased(mouse, mouseButton);
    }

    public void updateScrollbarBounds() {
        float prevMaxY = this.session.maxScrollPositionY();
        boolean prevBottom = this.session.isScrolledToBottom();
        boolean prevRequired = this.session.isScrollbarRequired();
        float contentHeight = this.contentWidget.getContentHeight(BoundsType.OUTER);
        float overflow = Math.max(contentHeight - bounds().getHeight(BoundsType.INNER), 0.0f);
        this.session.updateBounds(contentHeight, overflow, this.fixedPosition.get());
        if (this.autoScroll.get().booleanValue()) {
            if ((prevBottom || !prevRequired) && this.session.maxScrollPositionY() != prevMaxY) {
                this.session.scrollToBottom();
                this.session.skipAnimation();
            }
        }
    }

    public void scrolled(float from, float to) {
        float delta = to - from;
        if (delta != 0.0f) {
            updateScrollContentOffset();
            scrollBackground(delta);
        }
    }

    public float getOverflowHeight() {
        float overflow = this.session.getContentHeight() - bounds().getHeight(BoundsType.INNER);
        return Math.max(overflow, 0.0f);
    }

    public boolean isScrollbarRequired() {
        return this.scrollbarWidget.isVisible() && getOverflowHeight() > 0.0f;
    }

    public float getTopLeftSpace() {
        return getOverflowHeight() - this.session.getScrollPositionY();
    }

    public void scrollToBottom() {
        scrollTo(getOverflowHeight());
    }

    public void scrollToTop() {
        scrollTo(0.0f);
    }

    public void scrollToSelectedChild() {
        Widget selected = (Widget) this.session.getSelectedEntry();
        if (selected == null) {
            return;
        }
        scrollTo(selected.bounds().getTop() - this.contentWidget.bounds().getTop());
    }

    private void scrollTo(float targetPosition) {
        float previousPosition = this.session.getScrollPositionY();
        this.session.setScrollPositionY(targetPosition);
        scrolled(previousPosition, this.session.getScrollPositionY());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean isVisibleForContentBounds(Widget child) {
        if (this.scrollbarWidget == child && !this.scrollbarWidget.useLssPosition().get().booleanValue()) {
            return false;
        }
        return super.isVisibleForContentBounds(child);
    }

    public float getBottomLeftSpace() {
        return this.session.getScrollPositionY();
    }

    public ListSession<?> session() {
        return this.session;
    }

    public Widget contentWidget() {
        return this.contentWidget;
    }

    public LssProperty<VerticalAlignment> childAlign() {
        return this.childAlign;
    }

    public LssProperty<Boolean> enableScrollContent() {
        return this.enableScrollContent;
    }

    public LssProperty<Float> scrollSpeed() {
        return this.scrollSpeed;
    }

    public LssProperty<Boolean> scrollAlwaysVisible() {
        return this.scrollAlwaysVisible;
    }

    public LssProperty<Boolean> moveDirtBackground() {
        return this.moveDirtBackground;
    }

    public LssProperty<VerticalAlignment> fixedPosition() {
        return this.fixedPosition;
    }

    public LssProperty<Boolean> autoScroll() {
        return this.autoScroll;
    }

    public LssProperty<Boolean> modifyContentWidth() {
        return this.modifyContentWidth;
    }

    public ScrollbarWidget scrollbar() {
        return this.scrollbarWidget;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        if (type != AutoAlignType.HEIGHT || !hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) {
            if (type == AutoAlignType.WIDTH && hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
                return false;
            }
            return type == AutoAlignType.WIDTH ? child != this.scrollbarWidget : type == AutoAlignType.POSITION && (child == this.contentWidget || (child == this.scrollbarWidget && !this.scrollbarWidget.useLssPosition().get().booleanValue()));
        }
        return false;
    }
}
