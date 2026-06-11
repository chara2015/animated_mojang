package net.labymod.core.client.gui.screen.widget.widgets;

import java.util.List;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.collection.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/NewWindowWidget.class */
@AutoWidget
public abstract class NewWindowWidget extends InterpolateWidget {
    private static final ModifyReason DRAGGING = ModifyReason.of("dragging");
    private static final ModifyReason DRAGGING_RESET = ModifyReason.of("draggingReset");
    private static final ModifyReason RESCALING = ModifyReason.of("rescaling");
    private static final ModifyReason RESCALING_RESET = ModifyReason.of("rescalingReset");
    private static final ModifyReason MIN_SIZE = ModifyReason.of("minSize");
    private static final ModifyReason SHIFT_TO_BOUNDS = ModifyReason.of("shiftToBounds");

    @Nullable
    private Widget titleBarWidget;

    @NotNull
    private Widget contentWidget;
    private final Key draggingButton;
    private final DraggingType draggingType;
    private float dragOffsetX;
    private float dragOffsetY;
    private final Key rescaleButton;
    private final RescaleType rescaleType;
    protected BoundsType boundsType;
    private boolean dragging = false;
    private boolean draggingUpdated = false;
    private boolean rescaling = false;
    private Edge rescalingEdge = null;
    private boolean rescalingUpdated = false;
    private boolean hoverEdge = false;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/NewWindowWidget$DraggingType.class */
    public enum DraggingType {
        TITLE_BAR,
        CONTENT,
        NONE
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/NewWindowWidget$Edge.class */
    public enum Edge {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM;

        public static final Edge[] VALUES = values();
    }

    protected abstract Widget createTitleBar();

    protected NewWindowWidget(@NotNull DraggingType draggingType, @NotNull RescaleType rescaleType, @NotNull BoundsType rescaleBoundsType, @Nullable Key rescaleButton, @Nullable Key dragButton) {
        this.draggingType = draggingType;
        this.rescaleType = rescaleType;
        this.boundsType = rescaleBoundsType;
        this.rescaleButton = rescaleButton;
        this.draggingButton = dragButton;
        draggable().set(true);
        this.contentWidget = new DivWidget();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget stencilWrapper = (DivWidget) new DivWidget().addId("stencil-wrapper");
        FlexibleContentWidget contentWrapper = new FlexibleContentWidget();
        contentWrapper.addId("window-content");
        this.titleBarWidget = createTitleBar();
        this.titleBarWidget.addId("title-bar", "window-tab");
        contentWrapper.addContent(this.titleBarWidget);
        this.contentWidget.addId("window-main");
        contentWrapper.addFlexibleContent(this.contentWidget);
        stencilWrapper.addChild(contentWrapper);
        addChild(stencilWrapper);
        resetDraggingAndScaling();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        super.render(context);
        MutableMouse mouse = context.mouse();
        if (this.rescaleType.isEdge() && isRescalingEnabled() && !mouse.isOutOfBounds()) {
            Edge hoveringEdge = this.rescaling ? this.rescalingEdge : getHoveringEdge(mouse);
            this.hoverEdge = hoveringEdge != null;
            if (this.hoverEdge) {
                renderEdge(context, mouse, hoveringEdge);
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        shiftToBounds();
        for (T child : this.children) {
            child.bounds().set(bounds(), DRAGGING);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton button) {
        boolean zIsHovered;
        Edge edge;
        if (super.mouseClicked(mouse, button)) {
            return true;
        }
        if (this.rescaleType != RescaleType.NONE && button == this.rescaleButton && !this.rescaling && isRescalingEnabled()) {
            if (this.rescaleType.isEdge() && (edge = getHoveringEdge(mouse)) != null) {
                this.rescaling = true;
                this.rescalingEdge = edge;
                this.rescalingUpdated = false;
                return true;
            }
            Rectangle corner = getCorner();
            if (this.rescaleType == RescaleType.CORNER && corner.isInRectangle(mouse)) {
                this.rescaling = true;
                this.rescalingUpdated = false;
                this.dragOffsetX = mouse.getX() - corner.getRight();
                this.dragOffsetY = mouse.getY() - corner.getBottom();
            }
        }
        if (this.draggingType != DraggingType.NONE && button == this.draggingButton && !this.dragging && isDraggingEnabled() && isInsideEdges(mouse)) {
            if (this.draggingType == DraggingType.CONTENT) {
                zIsHovered = isHovered();
            } else {
                zIsHovered = this.titleBarWidget != null && this.titleBarWidget.isHovered();
            }
            boolean hover = zIsHovered;
            if (hover) {
                this.dragging = true;
                this.draggingUpdated = false;
                Bounds bounds = bounds();
                this.dragOffsetX = mouse.getX() - bounds.getLeft(BoundsType.OUTER);
                this.dragOffsetY = mouse.getY() - bounds.getTop(BoundsType.OUTER);
                return true;
            }
            return false;
        }
        return false;
    }

    protected boolean isInsideEdges(MutableMouse mouse) {
        float left = getEdge(Edge.LEFT, Edge.LEFT);
        float top = getEdge(Edge.TOP, Edge.TOP);
        float right = getEdge(Edge.RIGHT, Edge.RIGHT);
        float bottom = getEdge(Edge.BOTTOM, Edge.BOTTOM);
        return ((float) mouse.getX()) >= left && ((float) mouse.getX()) <= right && ((float) mouse.getY()) >= top && ((float) mouse.getY()) <= bottom;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        float edge;
        if (this.rescaling) {
            Bounds bounds = bounds();
            if (this.rescaleType.isEdge()) {
                if (hasEdgeOffset()) {
                    edge = getEdge(this.rescalingEdge, this.rescalingEdge) - getActualEdge(this.rescalingEdge, this.rescalingEdge);
                } else {
                    edge = 0.0f;
                }
                float offset = edge;
                switch (this.rescalingEdge) {
                    case LEFT:
                        bounds.setMiddleLeft(Math.max(borderBounds().getLeft(), mouse.getX() - offset), RESCALING);
                        skipInterpolation();
                        break;
                    case TOP:
                        bounds.setMiddleTop(Math.max(borderBounds().getTop(), mouse.getY() - offset), RESCALING);
                        skipInterpolation();
                        break;
                    case RIGHT:
                        bounds.setMiddleRight(mouse.getX() - offset, RESCALING);
                        break;
                    case BOTTOM:
                        bounds.setMiddleBottom(mouse.getY() - offset, RESCALING);
                        break;
                }
            }
            if (this.rescaleType == RescaleType.CORNER) {
                bounds.setMiddleRight(mouse.getX() - this.dragOffsetX, RESCALING);
                bounds.setMiddleBottom(mouse.getY() - this.dragOffsetY, RESCALING);
            }
            float minWidth = getSize(SizeType.MIN, WidgetSide.WIDTH).floatValue();
            if (bounds.getWidth() < minWidth) {
                if (this.rescalingEdge == Edge.RIGHT) {
                    bounds.setWidth(minWidth, MIN_SIZE);
                } else if (this.rescalingEdge == Edge.LEFT) {
                    bounds.setLeftWidth(minWidth, MIN_SIZE);
                    skipInterpolation();
                }
            }
            float minHeight = getSize(SizeType.MIN, WidgetSide.HEIGHT).floatValue();
            if (bounds.getHeight() < minHeight) {
                if (this.rescalingEdge == Edge.BOTTOM) {
                    bounds.setHeight(minHeight, MIN_SIZE);
                } else if (this.rescalingEdge == Edge.TOP) {
                    bounds.setTopHeight(minHeight, MIN_SIZE);
                    skipInterpolation();
                }
            }
            cropToBounds();
            onWindowPositionChanged();
            this.rescalingUpdated = true;
            return true;
        }
        if (this.dragging) {
            Bounds bounds2 = bounds();
            float x = mouse.getX() - this.dragOffsetX;
            float y = mouse.getY() - this.dragOffsetY;
            dragToPosition(x, y, bounds2);
            shiftToBounds();
            onWindowPositionChanged();
            this.draggingUpdated = true;
            return true;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    protected void renderEdge(ScreenContext context, MutableMouse mouse, Edge edge) {
        boolean horizontal = edge == Edge.LEFT || edge == Edge.RIGHT;
        ScreenCanvas canvas = context.canvas();
        canvas.submitText(horizontal ? "||" : "=", mouse.getX() - (horizontal ? 1 : 2), mouse.getY() - (horizontal ? 3 : 4), -1, 0, 1);
    }

    protected void onWindowPositionChanged() {
        this.contentWidget.reInitialize();
    }

    protected boolean isDraggingEnabled() {
        return true;
    }

    protected boolean isRescalingEnabled() {
        return true;
    }

    protected boolean hasEdgeOffset() {
        return false;
    }

    public void shiftToBounds() {
        bounds().shiftToBounds(borderBounds(), SHIFT_TO_BOUNDS, false);
    }

    public void cropToBounds() {
        bounds().crop(borderBounds(), SHIFT_TO_BOUNDS);
    }

    @Nullable
    protected Edge getHoveringEdge(Point position) {
        float x = position.getX();
        float y = position.getY();
        for (Edge edge : Edge.VALUES) {
            if (this.rescaleType.hasEdge(edge)) {
                float left = getEdge(edge, Edge.LEFT);
                float top = getEdge(edge, Edge.TOP);
                float right = getEdge(edge, Edge.RIGHT);
                float bottom = getEdge(edge, Edge.BOTTOM);
                if (x >= left - 2.0f && x <= right + 2.0f && y >= top - 2.0f && y <= bottom + 2.0f) {
                    return edge;
                }
            }
        }
        return null;
    }

    protected float getEdge(Edge edge, Edge side) {
        return getActualEdge(edge, side);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:71)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private float getActualEdge(net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget.Edge r4, net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget.Edge r5) {
        /*
            Method dump skipped, instruction units count: 275
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget.getActualEdge(net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget$Edge, net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget$Edge):float");
    }

    @NotNull
    public Rectangle getCorner() {
        Bounds bounds = bounds();
        return Rectangle.relative((bounds.getRight() - 10.0f) + 0.0f, (bounds.getBottom() - 10.0f) + 0.0f, 10.0f, 10.0f);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        resetDraggingAndScaling();
        return super.mouseReleased(mouse, mouseButton);
    }

    private void resetDraggingAndScaling() {
        this.dragging = false;
        this.rescaling = false;
        this.rescalingEdge = null;
        this.rescalingUpdated = false;
        this.draggingUpdated = false;
    }

    protected void dragToPosition(float x, float y, Bounds bounds) {
        bounds.setOuterX(x, DRAGGING);
        bounds.setOuterY(y, DRAGGING);
    }

    @Nullable
    public Widget titleBarWidget() {
        return this.titleBarWidget;
    }

    @NotNull
    public Widget contentWidget() {
        return this.contentWidget;
    }

    protected Rectangle borderBounds() {
        return this.parent.bounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public boolean isDragging() {
        return this.dragging && this.draggingUpdated;
    }

    public boolean isRescaling() {
        return this.rescaling && this.rescalingUpdated;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public boolean isHovered() {
        return this.hoverEdge || super.isHovered();
    }

    public RescaleType getRescaleType() {
        return this.rescaleType;
    }

    protected void setContentWidget(@NotNull Widget widget) {
        this.contentWidget = widget;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/NewWindowWidget$RescaleType.class */
    public enum RescaleType {
        CORNER(new Edge[0]),
        EDGES(Edge.LEFT, Edge.TOP, Edge.RIGHT, Edge.BOTTOM),
        TOP_RIGHT_EDGES(Edge.TOP, Edge.RIGHT),
        TOP_RIGHT_BOTTOM_EDGES(Edge.TOP, Edge.RIGHT, Edge.BOTTOM),
        NONE(new Edge[0]);

        private final List<Edge> edges;

        RescaleType(Edge... edges) {
            this.edges = Lists.newArrayList(edges);
        }

        public boolean isEdge() {
            return !this.edges.isEmpty();
        }

        public List<Edge> getEdges() {
            return this.edges;
        }

        public boolean hasEdge(Edge edge) {
            return this.edges.contains(edge);
        }
    }
}
