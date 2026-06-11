package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.screenshot.ScreenshotSection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotScrollbarWidget.class */
@AutoWidget
public class ScreenshotScrollbarWidget extends SimpleWidget {
    private static final int POINTER_COLOR = -1;
    private static final int POSITION_INDICATOR_COLOR = -1;
    private final ScreenshotTimelineWidget timelineWidget;
    private long lastAttentionTime = -1;
    private int previousAttentionAmount = 0;
    private int attentionAmount = 0;
    private static final int TICK_COLOR = ColorFormat.ARGB32.pack(180, 180, 180, 80);
    private static final int TIME_BACKGROUND_COLOR = ColorFormat.ARGB32.pack(0, 0, 0, 120);

    public ScreenshotScrollbarWidget(ScreenshotTimelineWidget timelineWidget) {
        this.timelineWidget = timelineWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        draggable().set(true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        float attentionStrength = MathHelper.lerp(this.attentionAmount, this.previousAttentionAmount, context.getTickDelta());
        opacity().set(Float.valueOf(attentionStrength / 7.0f));
        if (isHovered()) {
            updateAttention();
        }
        super.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        renderSectionTicks(context);
        renderIndicator(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        this.previousAttentionAmount = this.attentionAmount;
        long attentionDuration = TimeUtil.getMillis() - this.lastAttentionTime;
        if (attentionDuration < 1000) {
            this.attentionAmount++;
            if (this.attentionAmount > 7) {
                this.attentionAmount = 7;
                return;
            }
            return;
        }
        this.attentionAmount--;
        if (this.attentionAmount < 0) {
            this.attentionAmount = 0;
        }
    }

    private void renderSectionTicks(ScreenContext context) {
        ScreenCanvas renderState = context.canvas();
        ScreenshotContainerWidget container = this.timelineWidget.getContainer();
        Bounds bounds = bounds();
        ScreenshotSection[] sections = (ScreenshotSection[]) this.timelineWidget.holder().browser().getSections().toArray(new ScreenshotSection[0]);
        float previousTimeY = 0.0f;
        float previousY = 0.0f;
        for (ScreenshotSection section : sections) {
            float containerOffset = container.getOffsetOfSection(section);
            float scrollbarOffset = container2ScrollbarOffset(containerOffset, 10.0f);
            float y = bounds.getY() + scrollbarOffset;
            if (section.isBeginningOfYear() && y - previousTimeY > 10.0f) {
                renderState.submitText(String.valueOf(section.getYear()), (int) bounds.getCenterX(), (int) y, NamedTextColor.GRAY.getValue() | (-16777216), 0.67f, 3);
                previousTimeY = y;
            } else if (y - previousY > 2.0f && y - previousTimeY > 10.0f) {
                renderState.submitRelativeRect(((int) bounds.getRight()) - 2, (int) y, 1.0f, 1.0f, TICK_COLOR);
            }
            previousY = y;
        }
    }

    private void renderIndicator(ScreenContext context) {
        float fScrollbar2ContainerOffset;
        ScreenCanvas renderState = context.canvas();
        ScreenshotContainerWidget container = this.timelineWidget.getContainer();
        Bounds bounds = bounds();
        boolean attachedToMouse = isHovered() || isDragging();
        float scrollOffset = this.timelineWidget.getScrollOffset();
        float scrollbarOffset = MathHelper.clamp(context.mouse().getY() - bounds.getY(), 0.0f, bounds.getHeight());
        if (attachedToMouse) {
            fScrollbar2ContainerOffset = scrollbar2ContainerOffset(scrollbarOffset);
        } else {
            fScrollbar2ContainerOffset = scrollOffset;
        }
        float containerOffset = fScrollbar2ContainerOffset;
        ScreenshotSectionWidget sectionWidget = container.getSectionAtOffset(containerOffset);
        if (sectionWidget == null) {
            return;
        }
        float targetScrollbarOffset = container2ScrollbarOffset(containerOffset, 1.0f);
        float y = bounds.getY() + targetScrollbarOffset;
        float width = sectionWidget.renderableTitleComponent().getWidth();
        renderState.submitRelativeRect((int) ((bounds.getRight() - width) - 1.0f), (int) y, width, 1.0f, -1);
        renderState.submitRelativeRect((int) ((bounds.getRight() - width) - 1.0f), (int) (y - 10.0f), width, 10.0f, TIME_BACKGROUND_COLOR);
        renderState.submitRenderableComponent(sectionWidget.renderableTitleComponent(), (int) (bounds.getRight() - width), (int) (y - 10.0f), -1, 1);
        if (isHovered() && !isDragging()) {
            renderActualIndicator(context);
        }
    }

    private void renderActualIndicator(ScreenContext context) {
        ScreenCanvas renderState = context.canvas();
        Bounds bounds = bounds();
        float containerOffset = this.timelineWidget.getScrollOffset();
        float scrollbarOffset = container2ScrollbarOffset(containerOffset, 1.0f);
        renderState.submitRelativeRect(bounds.getX(), bounds.getY() + scrollbarOffset, bounds.getWidth(), 1.0f, -1);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        float mouseOffsetY = mouse.getY() - bounds().getY();
        this.timelineWidget.setScrollOffset(scrollbar2ContainerOffset(mouseOffsetY));
        updateAttention();
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (isDragging()) {
            float mouseOffsetY = mouse.getY() - bounds().getY();
            this.timelineWidget.setScrollOffset(scrollbar2ContainerOffset(mouseOffsetY));
        }
        updateAttention();
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    public void updateAttention() {
        float maxScrollOffset = this.timelineWidget.getMaxScrollOffset();
        if (maxScrollOffset > 0.0f) {
            this.lastAttentionTime = TimeUtil.getMillis();
        }
    }

    private float container2ScrollbarOffset(float offset) {
        return container2ScrollbarOffset(offset, 0.0f);
    }

    private float scrollbar2ContainerOffset(float offset) {
        return scrollbar2ContainerOffset(offset, 0.0f);
    }

    private float container2ScrollbarOffset(float offset, float elementHeight) {
        float maxScrollOffset = this.timelineWidget.getMaxScrollOffset();
        float percentage = maxScrollOffset == 0.0f ? 0.0f : offset / maxScrollOffset;
        return percentage * (bounds().getHeight() - elementHeight);
    }

    private float scrollbar2ContainerOffset(float offset, float elementHeight) {
        float percentage = offset / (bounds().getHeight() - elementHeight);
        return this.timelineWidget.getMaxScrollOffset() * percentage;
    }
}
