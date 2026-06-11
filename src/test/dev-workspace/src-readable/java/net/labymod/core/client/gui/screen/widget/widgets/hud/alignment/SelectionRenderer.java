package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/alignment/SelectionRenderer.class */
public class SelectionRenderer {
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 1.5f;
    private static final int HANDLE_OUTLINE_COLOR = -1;
    private static final int SELECTION_LINE_COLOR = -16777216;
    private static final int BACKGROUND_OUTLINE_COLOR_INNER = -1;
    private static final int BACKGROUND_OUTLINE_COLOR_OUTER = -16777216;
    private final HudWidgetInteractionWidget interactionWidget;
    private HandleType hoveredHandle;
    private HandleType draggingHandle;
    private boolean dragging;
    private float mouseClickedX;
    private float mouseClickedY;
    private static final int HANDLE_COLOR = ColorFormat.ARGB32.pack(0, 0, 0, 120);
    private static final int HANDLE_HOVER_COLOR = ColorFormat.ARGB32.pack(240, 240, 240, 200);
    private static final int BACKGROUND_COLOR = ColorFormat.ARGB32.pack(120, 120, 120, 30);
    private final List<HudWidget<?>> selected = new ArrayList();
    private final List<Session> sessions = new ArrayList();
    private HudWidget<?> lastSelectedHudWidget = null;
    private final EditButton editButton = new EditButton(this);
    private final LabyAPI labyAPI = Laby.labyAPI();

    public SelectionRenderer(HudWidgetInteractionWidget interactionWidget) {
        this.interactionWidget = interactionWidget;
    }

    public void render(ScreenContext context) {
        MutableRectangle entireRectangle;
        float right;
        if (this.selected.isEmpty() || isMoving() || (entireRectangle = getEntireRectangle(1)) == null) {
            return;
        }
        renderSelectionBox(context, (int) entireRectangle.getLeft(), (int) entireRectangle.getTop(), (int) entireRectangle.getRight(), (int) entireRectangle.getBottom());
        if (isSingleSelection()) {
            if (this.dragging) {
                if (this.draggingHandle.isCorner()) {
                    HudWidgetWidget widget = this.interactionWidget.getWidget(this.lastSelectedHudWidget);
                    boolean isOnRightSideOfScreen = entireRectangle.getCenterX() > this.interactionWidget.bounds().getCenterX() + 5.0f;
                    float snappingScale = getSnappingScale();
                    float scale = widget.size().getScale();
                    String scaleString = String.format(Locale.ROOT, "%.2fx", Float.valueOf(scale));
                    boolean ignoreSnapping = this.interactionWidget.shouldIgnoreAlignment();
                    int color = NamedTextColor.YELLOW.getValue();
                    if (scale == snappingScale && !ignoreSnapping) {
                        color = NamedTextColor.AQUA.getValue();
                    }
                    if (scale == 1.0f && !ignoreSnapping) {
                        color = NamedTextColor.GREEN.getValue();
                    }
                    if (scale == 0.5f || scale == MAX_SCALE) {
                        color = NamedTextColor.RED.getValue();
                    }
                    RenderableComponent renderableComponent = RenderableComponent.of(Component.text(scaleString));
                    ScreenCanvas screenCanvasCanvas = context.canvas();
                    if (isOnRightSideOfScreen) {
                        right = (entireRectangle.getLeft() - (renderableComponent.getWidth() * 0.67f)) - 1.0f;
                    } else {
                        right = entireRectangle.getRight() + 1.0f;
                    }
                    screenCanvasCanvas.submitRenderableComponent(renderableComponent, right, entireRectangle.getCenterY() - (0.67f * 4.0f), color, 0.67f, 1);
                    return;
                }
                return;
            }
            this.editButton.render(context);
        }
    }

    private void renderSelectionBox(ScreenContext context, float left, float top, float right, float bottom) {
        ScreenCanvas renderState = context.canvas();
        MutableMouse mouse = context.mouse();
        float progress = (TimeUtil.getMillis() % 1000) / 250.0f;
        renderState.submitAbsoluteRect(left, top, right, bottom, BACKGROUND_COLOR);
        renderState.submitAbsoluteOutlineRect(left, top, right, bottom, 0.25f, -1, -16777216);
        renderSelectionLine(renderState, top, bottom, left, false, 1.0f - progress);
        renderSelectionLine(renderState, left, right, top, true, progress + 0.5f);
        renderSelectionLine(renderState, top, bottom, right, false, progress + 0.5f);
        renderSelectionLine(renderState, left, right, bottom, true, 1.0f - progress);
        this.hoveredHandle = null;
        if (isSingleSelection()) {
            renderHandle(renderState, mouse, HandleType.TOP_LEFT, left, top);
            renderHandle(renderState, mouse, HandleType.TOP_RIGHT, right, top);
            renderHandle(renderState, mouse, HandleType.BOTTOM_LEFT, left, bottom);
            renderHandle(renderState, mouse, HandleType.BOTTOM_RIGHT, right, bottom);
            if (this.lastSelectedHudWidget.isResizeable()) {
                renderHandle(renderState, mouse, HandleType.LEFT, left, top + ((bottom - top) / 2.0f));
                renderHandle(renderState, mouse, HandleType.RIGHT, right, top + ((bottom - top) / 2.0f));
                renderHandle(renderState, mouse, HandleType.TOP, left + ((right - left) / 2.0f), top);
                renderHandle(renderState, mouse, HandleType.BOTTOM, left + ((right - left) / 2.0f), bottom);
            }
        }
    }

    private void renderHandle(ScreenCanvas renderState, MutableMouse mouse, HandleType type, float x, float y) {
        if (!this.interactionWidget.bounds().isInRectangle(x, y)) {
            return;
        }
        if (type == HandleType.TOP || type == HandleType.BOTTOM) {
            HudWidget<?> hudWidget = getLastSelectedHudWidget();
            HudWidget<?> target = type == HandleType.TOP ? hudWidget.getParent() : hudWidget.getChild();
            if (target != null) {
                return;
            }
        }
        boolean hover = mouse.isInside((double) (x - 2.0f), (double) (y - 2.0f), 4.0d, 4.0d) || (this.dragging && this.draggingHandle == type);
        renderState.submitAbsoluteRect(x - 1.0f, y - 1.0f, x + 1.0f, y + 1.0f, hover ? HANDLE_HOVER_COLOR : HANDLE_COLOR);
        renderState.submitAbsoluteOutlineRect(x - 1.0f, y - 1.0f, x + 1.0f, y + 1.0f, 0.25f, -1, -1);
        if (hover) {
            this.hoveredHandle = type;
        }
    }

    private void renderSelectionLine(ScreenCanvas renderState, float from, float to, float level, boolean horizontal, float progress) {
        float offset = (progress % 1.0f) * 2 * 2;
        float f = (from + offset) - 2;
        while (true) {
            float position = f;
            if (position < to) {
                float clamped1 = MathHelper.clamp(position, from, to);
                float clamped2 = MathHelper.clamp(position + 2, from, to);
                if (horizontal) {
                    renderState.submitAbsoluteRect(clamped1, level - 0.25f, clamped2, level + 0.25f, -16777216);
                } else {
                    renderState.submitAbsoluteRect(level - 0.25f, clamped1, level + 0.25f, clamped2, -16777216);
                }
                f = position + (2 * 2);
            } else {
                return;
            }
        }
    }

    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.hoveredHandle != null) {
            this.dragging = true;
            this.draggingHandle = this.hoveredHandle;
            this.mouseClickedX = mouse.getX();
            this.mouseClickedY = mouse.getY();
            this.sessions.clear();
            for (HudWidget<?> hudWidget : this.selected) {
                this.sessions.add(new Session(this.interactionWidget.getWidget(hudWidget)));
            }
            return true;
        }
        if (this.editButton.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        return false;
    }

    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        float f;
        if (this.dragging) {
            float mouseOffsetX = mouse.getX() - this.mouseClickedX;
            float mouseOffsetY = mouse.getY() - this.mouseClickedY;
            if (this.draggingHandle.isCorner()) {
                float snappingScale = getSnappingScale();
                for (Session session : this.sessions) {
                    session.setScaleFromOffset(this.interactionWidget, this.draggingHandle, mouseOffsetX, snappingScale);
                }
            } else {
                if (this.draggingHandle == HandleType.TOP || this.draggingHandle == HandleType.BOTTOM) {
                    f = mouseOffsetY;
                } else {
                    f = mouseOffsetX;
                }
                float mouseOffset = f;
                for (Session session2 : this.sessions) {
                    session2.setSizeFromOffset(this.interactionWidget, this.draggingHandle, mouseOffset);
                }
            }
            this.interactionWidget.updateHudWidgetsInDropzone();
            return true;
        }
        return false;
    }

    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (this.dragging) {
            this.dragging = false;
            this.hoveredHandle = null;
            for (Session session : this.sessions) {
                session.dispose();
                this.interactionWidget.saveHudWidget(session.hudWidget());
            }
            this.sessions.clear();
            return true;
        }
        return false;
    }

    public void onTick() {
        this.editButton.onTick();
    }

    public void select(HudWidget<?> hudWidget) {
        if (this.selected.contains(hudWidget)) {
            return;
        }
        this.selected.add(hudWidget);
        this.lastSelectedHudWidget = hudWidget;
    }

    public void unselect(HudWidget<?> hudWidget) {
        this.selected.remove(hudWidget);
    }

    public void unselectAll() {
        this.selected.clear();
    }

    public boolean isSelected(HudWidget<?> hudWidget) {
        return this.selected.contains(hudWidget);
    }

    public boolean isSingleSelection() {
        return this.selected.size() == 1;
    }

    public List<HudWidget<?>> getSelected() {
        return this.selected;
    }

    private boolean isMoving() {
        return this.interactionWidget.getDraggingHudWidget() != null;
    }

    public HudWidget<?> getLastSelectedHudWidget() {
        return this.lastSelectedHudWidget;
    }

    public HudWidgetInteractionWidget interactionWidget() {
        return this.interactionWidget;
    }

    MutableRectangle getEntireRectangle(int padding) {
        DefaultRectangle entireRectangle = null;
        for (HudWidget<?> hudWidget : this.selected) {
            HudWidgetWidget widget = this.interactionWidget.getWidget(hudWidget);
            if (entireRectangle == null) {
                entireRectangle = new DefaultRectangle(widget.scaledBounds());
            } else {
                entireRectangle.extend(widget.scaledBounds());
            }
        }
        if (entireRectangle == null) {
            return null;
        }
        return entireRectangle.expand(padding);
    }

    private float getSnappingScale() {
        if (this.sessions.size() != 1) {
            return 1.0f;
        }
        Session session = this.sessions.get(0);
        HudWidget<?> hudWidget = session.hudWidget();
        HudWidget<?> parent = hudWidget.getParent();
        if (parent != null) {
            HudWidgetWidget parentWidget = this.interactionWidget.getWidget(parent);
            return parentWidget.size().getScale();
        }
        HudWidget<?> child = hudWidget.getChild();
        if (child != null) {
            HudWidgetWidget childWidget = this.interactionWidget.getWidget(child);
            return childWidget.size().getScale();
        }
        return 1.0f;
    }

    public boolean canUpdateHudWidget(HudWidget<?> hudWidget) {
        for (Session session : this.sessions) {
            if (session.hudWidget() == hudWidget && this.draggingHandle.isEdge()) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/alignment/SelectionRenderer$HandleType.class */
    enum HandleType {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        RIGHT,
        BOTTOM,
        LEFT;

        public boolean isLeftCorner() {
            return this == TOP_LEFT || this == BOTTOM_LEFT;
        }

        public boolean isRightCorner() {
            return this == TOP_RIGHT || this == BOTTOM_RIGHT;
        }

        public boolean isTopCorner() {
            return this == TOP_LEFT || this == TOP_RIGHT;
        }

        public boolean isBottomCorner() {
            return this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
        }

        public boolean isCorner() {
            return this == TOP_LEFT || this == TOP_RIGHT || this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
        }

        public boolean isEdge() {
            return this == TOP || this == RIGHT || this == BOTTOM || this == LEFT;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/alignment/SelectionRenderer$Session.class */
    static class Session {
        private final HudWidget<?> hudWidget;
        private final Rectangle previousRectangle;
        private final float previousScale;
        private final HudWidgetAnchor previousAnchor;

        public Session(HudWidgetWidget widget) {
            this.hudWidget = widget.hudWidget();
            this.previousScale = widget.size().getScale();
            this.previousRectangle = widget.scaledBounds().copy();
            this.previousAnchor = this.hudWidget.anchor();
        }

        public void setScaleFromOffset(HudWidgetInteractionWidget interactionWidget, HandleType type, float mouseOffset, float snappingScale) {
            float f;
            float actualWidth;
            float actualHeight;
            float fromLeft = this.previousRectangle.getLeft();
            float fromTop = this.previousRectangle.getTop();
            float fromRight = this.previousRectangle.getRight();
            float fromBottom = this.previousRectangle.getBottom();
            if (type.isLeftCorner()) {
                f = ((fromRight - (fromLeft + mouseOffset)) / (fromRight - fromLeft)) * this.previousScale;
            } else {
                f = (((fromRight + mouseOffset) - fromLeft) / (fromRight - fromLeft)) * this.previousScale;
            }
            float scale = f;
            boolean ignoreSnapping = interactionWidget.shouldIgnoreAlignment();
            float differenceToOther = Math.abs(scale - snappingScale);
            if (differenceToOther < 0.04f && !ignoreSnapping) {
                scale = snappingScale;
            }
            float differenceToDefault = Math.abs(scale - 1.0f);
            if (differenceToDefault < 0.04f && !ignoreSnapping) {
                scale = 1.0f;
            }
            float scale2 = MathHelper.clamp(scale, 0.5f, SelectionRenderer.MAX_SCALE);
            HudWidgetWidget widget = interactionWidget.getWidget(this.hudWidget);
            HudSize size = widget.size();
            HudWidgetDropzone dropzone = this.hudWidget.getAttachedDropzone();
            if (dropzone == null) {
                this.hudWidget.getConfig().scale().set(Float.valueOf(scale2));
                boolean isCentered = this.previousAnchor.isCenter();
                HudWidget<?> hudWidget = this.hudWidget;
                if (type.isRightCorner() && !isCentered) {
                    actualWidth = fromLeft;
                } else {
                    actualWidth = fromLeft + (((fromRight - fromLeft) - (size.getActualWidth() * scale2)) * (isCentered ? 0.5f : 1.0f));
                }
                float fRound = Math.round(actualWidth);
                if (type.isBottomCorner()) {
                    actualHeight = fromTop;
                } else {
                    actualHeight = (fromTop + (fromBottom - fromTop)) - (size.getActualHeight() * scale2);
                }
                interactionWidget.updatePosition(hudWidget, fRound, Math.round(actualHeight));
            } else {
                for (HudWidgetDropzone otherZone : this.hudWidget.getDropzones()) {
                    HudWidget<?> hudWidget2 = interactionWidget.getHudWidgetInDropzone(otherZone);
                    if (hudWidget2 != null) {
                        hudWidget2.getConfig().scale().set(Float.valueOf(scale2));
                        interactionWidget.updateHudWidget(hudWidget2);
                    }
                }
            }
            if (this.hudWidget.getParent() != null) {
                interactionWidget.updateHudWidget(this.hudWidget.firstWidget());
            }
            widget.updateSizeOfWidget();
            widget.skipInterpolation();
        }

        public void setSizeFromOffset(HudWidgetInteractionWidget interactionWidget, HandleType type, float mouseOffset) {
            if (!this.hudWidget.isResizeable()) {
                return;
            }
            HudWidgetConfig config = this.hudWidget.getConfig();
            ResizeableHudWidget.ResizeableHudWidgetConfig resizeable = (ResizeableHudWidget.ResizeableHudWidgetConfig) config;
            HudWidgetWidget widget = interactionWidget.getWidget(this.hudWidget);
            float scale = config.scale().get().floatValue();
            float width = resizeable.width().get().floatValue() * scale;
            float height = resizeable.height().get().floatValue() * scale;
            float previousWidth = this.previousRectangle.getWidth();
            float previousHeight = this.previousRectangle.getHeight();
            boolean isCentered = this.previousAnchor.isCenter();
            float minWidth = resizeable.getMinWidth() * scale;
            float minHeight = resizeable.getMinHeight() * scale;
            float maxWidth = resizeable.getMaxWidth() * scale;
            float maxHeight = resizeable.getMaxHeight() * scale;
            switch (type.ordinal()) {
                case 4:
                    if (previousHeight - mouseOffset < minHeight) {
                        mouseOffset = previousHeight - minHeight;
                    }
                    if (previousHeight - mouseOffset > maxHeight) {
                        mouseOffset = previousHeight - maxHeight;
                    }
                    height = previousHeight - mouseOffset;
                    interactionWidget.updatePosition(this.hudWidget, this.previousRectangle.getLeft(), this.previousRectangle.getTop() + mouseOffset);
                    break;
                case 5:
                    if (previousWidth + (mouseOffset * (isCentered ? 2 : 1)) < minWidth) {
                        mouseOffset = (minWidth - previousWidth) / (isCentered ? 2 : 1);
                    }
                    if (previousWidth + (mouseOffset * (isCentered ? 2 : 1)) > maxWidth) {
                        mouseOffset = (maxWidth - previousWidth) / (isCentered ? 2 : 1);
                    }
                    width = previousWidth + (mouseOffset * (isCentered ? 2 : 1));
                    if (isCentered) {
                        interactionWidget.updatePosition(this.hudWidget, this.previousRectangle.getLeft() - mouseOffset, this.previousRectangle.getTop());
                    }
                    break;
                case 6:
                    if (previousHeight + mouseOffset < minHeight) {
                        mouseOffset = minHeight - previousHeight;
                    }
                    if (previousHeight + mouseOffset > maxHeight) {
                        mouseOffset = maxHeight - previousHeight;
                    }
                    height = previousHeight + mouseOffset;
                    break;
                case 7:
                    if (previousWidth - (mouseOffset * (isCentered ? 2 : 1)) < minWidth) {
                        mouseOffset = (previousWidth - minWidth) / (isCentered ? 2 : 1);
                    }
                    if (previousWidth - (mouseOffset * (isCentered ? 2 : 1)) > maxWidth) {
                        mouseOffset = (previousWidth - maxWidth) / (isCentered ? 2 : 1);
                    }
                    width = previousWidth - (mouseOffset * (isCentered ? 2 : 1));
                    interactionWidget.updatePosition(this.hudWidget, this.previousRectangle.getLeft() + mouseOffset, this.previousRectangle.getTop());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
            }
            float width2 = width / scale;
            float height2 = height / scale;
            widget.size().set(width2, height2);
            ((ResizeableHudWidget) this.hudWidget).setSize(width2, height2);
            widget.updateSizeOfWidget();
            widget.skipInterpolation();
            if (this.hudWidget.getParent() != null) {
                interactionWidget.updateHudWidget(this.hudWidget.firstWidget());
            }
        }

        public void dispose() {
        }

        public HudWidget<?> hudWidget() {
            return this.hudWidget;
        }
    }
}
