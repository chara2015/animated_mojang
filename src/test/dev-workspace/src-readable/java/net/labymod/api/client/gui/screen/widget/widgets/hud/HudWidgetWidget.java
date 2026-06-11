package net.labymod.api.client.gui.screen.widget.widgets.hud;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.ChainAlignmentSide;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.chain.ChainAttachingLine;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/hud/HudWidgetWidget.class */
@AutoWidget
public class HudWidgetWidget extends InterpolateWidget {
    private static final ModifyReason HUD_WIDGET_SELF = ModifyReason.of("hudWidgetSelf");
    private final HudWidget<?> hudWidget;
    private final HudWidgetRendererAccessor accessor;
    private final ScaledRectangle scaledRectangle;
    private final HudSize size;
    private final ChainAttachingLine chainAttachingLineTop;
    private final ChainAttachingLine chainAttachingLineBottom;
    private ChainAlignmentSide alignmentVisibility;
    private float prevWidth;
    private float prevHeight;
    private boolean isOverlapping;
    private Runnable doubleClickListener;
    private long lastClickedTime;
    private HudWidgetAnchor initializedAnchor;

    public HudWidgetWidget(@NotNull HudWidget<?> hudWidget, HudWidgetRendererAccessor accessor) {
        super(50.0f);
        this.scaledRectangle = new ScaledRectangle(this);
        this.chainAttachingLineTop = new ChainAttachingLine(ChainAlignmentSide.TOP);
        this.chainAttachingLineBottom = new ChainAttachingLine(ChainAlignmentSide.BOTTOM);
        this.isOverlapping = false;
        this.doubleClickListener = null;
        this.lastClickedTime = 0L;
        this.initializedAnchor = null;
        Objects.requireNonNull(hudWidget, "Hud Widget cannot be null");
        this.handleStyleSheet = false;
        this.accessor = accessor;
        this.hudWidget = hudWidget;
        this.size = new HudSize(this.hudWidget.getConfig());
        visible().set(false);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        draggable().set(true);
        hoverBoxDelay().set(Integer.valueOf(SubmissionOrders.DEBUG));
        this.hudWidget.initialize(this);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void postInitialize() {
        super.postInitialize();
        this.hudWidget.postInitialize(this);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.hudWidget.onBoundsChanged(this, previousRect, newRect);
        Collection<HudWidget<?>> hudWidgets = this.accessor.getHudWidgetsInArea(scaledBounds());
        this.isOverlapping = hudWidgets.size() > 1;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (!this.accessor.isEditor() && this.hudWidget.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        long timePassedSinceLastClick = TimeUtil.getMillis() - this.lastClickedTime;
        if (timePassedSinceLastClick < 250) {
            if (this.doubleClickListener != null) {
                this.doubleClickListener.run();
            }
            if (this.accessor.isEditor()) {
                this.accessor.openSettings(this.hudWidget);
            }
        }
        this.lastClickedTime = TimeUtil.getMillis();
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        boolean editorContext = this.accessor.isEditor();
        if (this.hudWidget.isHolderEnabled()) {
            if (!editorContext && this.accessor.isDebugEnabled() && !this.hudWidget.renderInDebug()) {
                return;
            }
            if (this.hudWidget.isEnabled()) {
                this.hudWidget.updateSize(this, editorContext, this.size);
            }
            boolean visibility = ((this.hudWidget.isVisibleInGame() && this.hudWidget.isAllowed()) || editorContext) && this.hudWidget.isEnabled();
            if (visible().get().booleanValue() != visibility) {
                visible().set(Boolean.valueOf(visibility));
                this.accessor.onVisibilityChanged(this.hudWidget);
            }
            if (this.prevWidth != this.size.getActualWidth() || this.prevHeight != this.size.getActualHeight()) {
                this.prevWidth = this.size.getActualWidth();
                this.prevHeight = this.size.getActualHeight();
                this.accessor.onSizeChanged(this.hudWidget);
            }
            if (this.isOverlapping) {
                context.canvas().nextLayer();
            }
            super.render(context);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        try {
            context.pushStack();
            ScaledRectangle bounds = scaledBounds();
            boolean floatingPointPosition = this.accessor.isEditor();
            context.stack().translate(MathHelper.applyFloatingPointPosition(floatingPointPosition, bounds.getX()), MathHelper.applyFloatingPointPosition(floatingPointPosition, bounds.getY()), 0.0f);
            context.mouse().transform(bounds, () -> {
                try {
                    context.pushStack();
                    renderHudWidget(context);
                } finally {
                    context.popStack();
                }
            });
            context.popStack();
        } catch (Throwable th) {
            context.popStack();
            throw th;
        }
    }

    private void renderHudWidget(ScreenContext context) {
        float scale = this.size.getScale();
        if (scale != 1.0f && !this.hudWidget.handlesScaling()) {
            context.stack().scale(scale, scale, 1.0f);
        }
        this.hudWidget.renderWidget(context, this.accessor.isEditor(), this.size);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderOverlay(ScreenContext context) {
        super.renderOverlay(context);
        Stack stack = context.stack();
        stack.push();
        ScaledRectangle bounds = scaledBounds();
        stack.translate(bounds.getX(), bounds.getY(), 0.0f);
        this.chainAttachingLineTop.render(context, bounds);
        this.chainAttachingLineBottom.render(context, bounds);
        stack.pop();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        FrameProfiler.push((Supplier<String>) () -> {
            return "postTick(" + getTypeName() + ")";
        });
        this.chainAttachingLineTop.tick(this.alignmentVisibility == ChainAlignmentSide.TOP);
        this.chainAttachingLineBottom.tick(this.alignmentVisibility == ChainAlignmentSide.BOTTOM);
        boolean editor = this.accessor.isEditor();
        if (this.hudWidget.isEnabled() && (editor || this.labyAPI.minecraft().isIngame())) {
            FrameProfiler.push((Supplier<String>) () -> {
                return "tick(" + this.hudWidget.getId() + ")";
            });
            this.hudWidget.tick(editor);
            FrameProfiler.pop();
        }
        FrameProfiler.pop();
    }

    public void setDoubleClickListener(Runnable doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    @NotNull
    public HudWidget<?> hudWidget() {
        return this.hudWidget;
    }

    @Deprecated
    public HudWidget<?> getHudWidget() {
        return hudWidget();
    }

    public void setAlignmentVisibility(ChainAlignmentSide visibility) {
        this.alignmentVisibility = visibility;
    }

    public ChainAlignmentSide getAlignmentVisibility() {
        return this.alignmentVisibility;
    }

    public void setInitializedAnchor(HudWidgetAnchor initializedAnchor) {
        this.initializedAnchor = initializedAnchor;
    }

    public HudWidgetAnchor getInitializedAnchor() {
        return this.initializedAnchor;
    }

    public HudWidgetRendererAccessor accessor() {
        return this.accessor;
    }

    public void update(String reason) {
        boolean update = true;
        Iterator it = this.children.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Widget child = (Widget) it.next();
            if (!(child instanceof HudWidget.Updatable)) {
                update = false;
                break;
            }
        }
        if (!update) {
            reInitialize();
            return;
        }
        for (T child2 : this.children) {
            ((HudWidget.Updatable) child2).update(reason);
        }
    }

    public ScaledRectangle scaledBounds() {
        return this.scaledRectangle;
    }

    public HudSize size() {
        return this.size;
    }

    public void updateSizeOfWidget() {
        ScaledRectangle rectangle = scaledBounds();
        rectangle.setSize(this.size.getScaledWidth(), this.size.getScaledHeight(), HUD_WIDGET_SELF);
        rectangle.checkForChanges();
        if (!this.accessor.isEditor() && !this.labyAPI.hudWidgetRegistry().globalHudWidgetConfig().smoothMovement().get().booleanValue()) {
            skipInterpolation();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.Parent
    @Deprecated
    public Bounds bounds() {
        return super.bounds();
    }
}
