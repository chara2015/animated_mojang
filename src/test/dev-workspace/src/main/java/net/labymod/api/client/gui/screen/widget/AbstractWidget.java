package net.labymod.api.client.gui.screen.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.animation.playback.LoopMode;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MouseAction;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.util.metadata.ActivityMetadataRegistry;
import net.labymod.api.client.gui.screen.util.metadata.WidgetMetadata;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.FilterAction;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.attributes.Border;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.screen.widget.attributes.DirtBackgroundType;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.attributes.Shadow;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Animation;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AnimationBuilder;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimation;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AttributePatchAnimationAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.cursor.CursorType;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.renderer.WidgetRendererContext;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSizeList;
import net.labymod.api.client.gui.screen.widget.util.WidgetMeta;
import net.labymod.api.client.gui.screen.widget.util.WidgetTransformer;
import net.labymod.api.client.gui.screen.widget.util.WidgetUtil;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.property.ClampFloatPropertyConvention;
import net.labymod.api.property.NotNullPropertyConvention;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.collection.list.ThreadSafeArrayList;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.debug.DebugWidgetRenderer;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/AbstractWidget.class */
@AutoWidget
public abstract class AbstractWidget<T extends Widget> extends StyledWidget implements WidgetStyleSheetUpdater {
    protected static final RenderEnvironmentContext RENDER_ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
    private static final Logging LOGGER = Logging.getLogger();
    private static final FrameTimer FRAME_TIMER = Laby.references().frameTimer();
    private static final ModifyReason FIT_SIZE_INIT = ModifyReason.of("fitSizeInit");
    private static final ModifyReason FULL_PARENT_SIZE = ModifyReason.of("fullParentSize");
    private static final ModifyReason MAINTAIN_ASPECT_RATIO = ModifyReason.of("maintainAspectRatio");
    private static final ModifyReason LSS_SIZE = ModifyReason.of("lssSize");
    private static final ModifyReason ALIGNMENT = ModifyReason.of("alignment");
    private static final ModifyReason RELATIVE_BOUNDS_TOP = ModifyReason.of("relativeBoundsTop");
    private static final ModifyReason RELATIVE_BOUNDS_BOTTOM = ModifyReason.of("relativeBoundsBottom");
    private static final ModifyReason RELATIVE_BOUNDS_LEFT = ModifyReason.of("relativeBoundsLeft");
    private static final ModifyReason RELATIVE_BOUNDS_RIGHT = ModifyReason.of("relativeBoundsRight");
    private static final ModifyReason MIN_SIZE = ModifyReason.of("minSize");
    private static final ModifyReason MAX_SIZE = ModifyReason.of("maxSize");
    private static final Comparator<? super Widget> DEFAULT_WIDGET_COMPARATOR = Comparator.comparingInt((v0) -> {
        return v0.getSortingValue();
    });
    private String pendingAnimationId;
    private Runnable pendingAnimationFinishHandler;
    public Shadow shadow;
    public Border border;
    public Icon backgroundImage;
    private WidgetMetadata widgetMetadata;
    protected boolean inOverlay;
    private BorderRadius borderRadius;
    private BooleanSupplier pressListener;
    private RenderableComponent hoverComponent;
    private boolean destroyed;
    private boolean disposed;
    private long lastActionTime;
    private long lastInitialTime;
    private long lastDestroyTime;
    private long lastActiveChangedTime;
    private boolean outOfBounds;
    private ContextMenu contextMenu;
    private Consumer<ContextMenu> contextMenuFactory;
    private CursorType hoverCursorType;
    private boolean hoverCursorForceActive;
    protected final List<T> children = new ThreadSafeArrayList();
    private final PositionedBounds bounds = new PositionedBounds(this);
    protected final Animation animation = new Animation(this);
    protected final MouseAction lastMouseClick = new MouseAction();
    private final Lazy<String> uniqueId = Lazy.of(() -> {
        return UUID.randomUUID().toString();
    });
    private final List<CharSequence> ids = new ArrayList();
    private final LssProperty<ThemeRenderer> renderer = new LssProperty<>(null);
    private final LssProperty<Boolean> pressable = new LssProperty<>(true);
    private final LssProperty<Boolean> visible = new LssProperty<>(true);
    private final Map<AttributeState, AttributeStateValue> attributeStates = new HashMap();
    private final Map<AttributeState, AttributeStateValue> staticAttributeStates = new HashMap();
    private final LssProperty<Float> left = new LssProperty<>(null);
    private final LssProperty<Float> top = new LssProperty<>(null);
    private final LssProperty<Float> right = new LssProperty<>(null);
    private final LssProperty<Float> bottom = new LssProperty<>(null);
    private final LssProperty<Float> marginLeft = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> marginTop = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> marginRight = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> marginBottom = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> paddingLeft = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> paddingTop = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> paddingRight = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> paddingBottom = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> widthPrecision = new LssProperty<>(null);
    private final LssProperty<Float> heightPrecision = new LssProperty<>(null);
    private final LssProperty<BoxSizing> boxSizing = new LssProperty<>(BoxSizing.CONTENT_BOX);
    private final LssProperty<PriorityLayer> priorityLayer = new LssProperty<>(PriorityLayer.NORMAL);
    private final LssProperty<WidgetAlignment> alignmentX = new LssProperty<>(WidgetAlignment.LEFT);
    private final LssProperty<WidgetAlignment> alignmentY = new LssProperty<>(WidgetAlignment.TOP);
    private final LssProperty<Float> translateX = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> translateY = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> zIndex = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> scaleX = new LssProperty<>(Float.valueOf(1.0f));
    private final LssProperty<Float> scaleY = new LssProperty<>(Float.valueOf(1.0f));
    private final LssProperty<Float> opacity = new LssProperty<>(Float.valueOf(1.0f), new ClampFloatPropertyConvention(0.0f, 1.0f));
    private final LssProperty<Float> mouseRenderDistance = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Integer> animationDuration = new LssProperty<>(0);
    private final LssProperty<CubicBezier> animationTimingFunction = new LssProperty<>(CubicBezier.EASE_IN_OUT);
    private final LssProperty<Integer> animationIterationCount = new LssProperty<>(1);
    private final LssProperty<Boolean> animationDirectionAlternate = new LssProperty<>(false);
    private final LssProperty<Boolean> stencilX = new LssProperty<>(false);
    private final LssProperty<Boolean> stencilY = new LssProperty<>(false);
    private final LssProperty<Boolean> stencilTranslation = new LssProperty<>(false);
    private final LssProperty<Boolean> writeToStencilBuffer = new LssProperty<>(false);
    private final LssProperty<Boolean> useFloatingPointPosition = new LssProperty<>(false);
    private final LssProperty<Boolean> alwaysFocused = new LssProperty<>(false);
    private final LssProperty<Boolean> interactableOutside = new LssProperty<>(false);
    private final LssProperty<Long> destroyDelay = new LssProperty<>(0L);
    private final LssProperty<Boolean> fitOuter = new LssProperty<>(false);
    private final LssProperty<Boolean> renderChildren = new LssProperty<>(true);
    private final LssProperty<Boolean> draggable = new LssProperty<>(false);
    private final LssProperty<Boolean> interactable = new LssProperty<>(true);
    private final LssProperty<Boolean> distinct = new LssProperty<>(true);
    private final LssProperty<Boolean> cancelParentHoverComponent = new LssProperty<>(true);
    private final LssProperty<Boolean> clearDepth = new LssProperty<>(false);
    private final LssProperty<Boolean> forceVanillaFont = new LssProperty<>(false, new NotNullPropertyConvention(false));
    private final LssProperty<Filter[]> filter = new LssProperty<>(null);
    private final LssProperty<Integer> backgroundColor = new LssProperty<>(0, new NotNullPropertyConvention(0));
    private final LssProperty<Boolean> backgroundAlwaysDirt = new LssProperty<>(false);
    private final LssProperty<DirtBackgroundType> backgroundDirtType = new LssProperty<>(DirtBackgroundType.LIST);
    private final LssProperty<Integer> backgroundDirtShift = new LssProperty<>(0);
    private final LssProperty<Integer> hoverBoxDelay = new LssProperty<>(0, new NotNullPropertyConvention(0));
    private final LssProperty<Long> backgroundColorTransitionDuration = new LssProperty<>(0L);
    private final LssProperty<Float> fontWeight = new LssProperty<>(Float.valueOf(0.0f));
    private final Map<String, Runnable> actionListeners = new HashMap();
    public boolean handleStyleSheet = true;
    protected Parent parent = null;
    private Metadata metadata = Metadata.create();
    private boolean ticking = true;
    protected final long widgetCreateTimestamp = TimeUtil.getMillis();
    protected boolean initialized = false;
    protected boolean lazy = false;
    protected boolean pendingInitialize = false;
    protected boolean pressOnRelease = false;
    private Comparator<? super Widget> comparator = DEFAULT_WIDGET_COMPARATOR;
    protected float mouseClickedX = -1.0f;
    protected float mouseClickedY = -1.0f;
    protected int childBoundsUpdates = 0;
    private final WidgetSizeList sizes = new WidgetSizeList();
    private final Map<String, LssVariable> lssVariables = new HashMap();

    @ApiStatus.Experimental
    private int lastBoundsUpdateFrame = 0;
    private final WidgetMeta widgetMeta = new WidgetMeta();
    private final WidgetRendererContext widgetRendererContext = new WidgetRendererContext(this);
    private final WidgetTransformer widgetTransformer = new WidgetTransformer(this);

    @Deprecated
    private final LssProperty<Integer> backgroundDirtBrightness = new LssProperty<>(0);
    protected final LabyAPI labyAPI = Laby.labyAPI();
    private final PropertyRegistry propertyRegistry = Laby.references().propertyRegistry();

    protected AbstractWidget() {
        ActivityMetadataRegistry.collectWidgetMetadata(getClass(), metadata -> {
            this.widgetMetadata = metadata;
        });
        this.visible.addChangeListener((type, oldValue, newValue) -> {
            allowCustomFont(() -> {
                notifyParentChildBoundsChanged(this.bounds.prevRectangle(), this.bounds, false);
            });
        });
        this.forceVanillaFont.addChangeListener((type2, oldValue2, newValue2) -> {
            notifyParentChildBoundsChanged(this.bounds.prevRectangle(), this.bounds, false);
        });
        reset();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void reset() {
        dispose();
        LssPropertyResetter resetter = this.propertyRegistry.getPropertyResetter(this);
        if (resetter != null) {
            resetter.reset(this);
        }
        initializeThemeRenderer();
        this.border = null;
        this.shadow = null;
        this.borderRadius = null;
        this.childBoundsUpdates = 0;
        this.outOfBounds = false;
        this.disposed = false;
        this.destroyed = false;
        if (isDragging()) {
            setDragging(false);
        }
        for (T child : this.children) {
            child.reset();
        }
        ThreadSafe.ensureRenderThread();
        this.children.clear();
        this.sizes.reset();
        super.reset();
    }

    private void initializeThemeRenderer() {
        String rendererName = getDefaultRendererName();
        if (rendererName != null) {
            this.renderer.set(Laby.references().themeRendererParser().parse(rendererName));
        }
    }

    @Override // net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        initializeLazy(parent);
        this.initialized = true;
        this.pendingInitialize = false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public final void initializeLazy(Parent parent) {
        Laby.references().screenWindowManagement().widgetPreInitialize(this, parent);
        this.parent = parent;
        loadOverlayState(parent);
        this.lastInitialTime = TimeUtil.getMillis();
        this.pendingInitialize = true;
    }

    private void updateLazy() {
        if (this.pendingInitialize && this.parent != null && this.visible.get().booleanValue() && !isOutOfBounds()) {
            reInitialize();
        }
    }

    private void loadOverlayState(Parent parent) {
        if ((parent instanceof Activity) && !(parent instanceof ScreenOverlay)) {
            parent = parent.getParent();
        }
        this.inOverlay = parent != null && (((parent instanceof AbstractWidget) && ((AbstractWidget) parent).inOverlay) || (parent instanceof ScreenOverlay));
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void postInitialize() {
        for (T child : this.children) {
            if (child.isLazy()) {
                child.initializeLazy(this);
            } else {
                child.initialize(this);
            }
            child.postInitialize();
        }
        if (!this.styleSheets.isEmpty()) {
            reloadStyleSheets();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        for (T widget : this.children) {
            widget.postStyleSheetLoad();
        }
        handleAttributes();
        if (this.pendingAnimationId != null) {
            String id = this.pendingAnimationId;
            Runnable handler = this.pendingAnimationFinishHandler;
            this.pendingAnimationId = null;
            this.pendingAnimationFinishHandler = null;
            playAnimation(id, handler);
        }
    }

    private void applyCustomStyleSheets() {
        applyCustomStyleSheetsLocal();
        applyCustomStyleSheetsForChildren();
    }

    private void applyCustomStyleSheetsLocal() {
        Laby.references().linkMetaLoader().loadMeta(getClass(), this::applyStyleSheet);
    }

    private void applyCustomStyleSheetsForChildren() {
        Iterator<T> it = this.children.iterator();
        while (it.hasNext()) {
            Widget child = WidgetUtil.unwrapWidget(it.next());
            if (child instanceof AbstractWidget) {
                ((AbstractWidget) child).applyCustomStyleSheets();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void applyStyleSheet(StyleSheet styleSheet) {
        if (this.handleStyleSheet) {
            super.applyStyleSheet(styleSheet);
        }
        for (T widget : this.children) {
            widget.applyStyleSheet(styleSheet);
        }
        applyCustomStyleSheetsForChildren();
        if (this.handleStyleSheet) {
            sortChildren();
        }
        if (this.alwaysFocused.get().booleanValue()) {
            setFocused(true);
        }
        updateContentBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Background";
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void reInitialize(Parent parent) {
        ThreadSafe.ensureRenderThread();
        if (!this.initialized && !this.pendingInitialize) {
            return;
        }
        allowCustomFont(() -> {
            boolean wasLazy = this.pendingInitialize;
            this.pendingInitialize = false;
            StyleSheet[] styleSheets = (StyleSheet[]) this.styleSheets.toArray(new StyleSheet[0]);
            reset();
            initialize(parent);
            applyCustomStyleSheets();
            postInitialize();
            try {
                this.preventStateUpdate = true;
                for (StyleSheet styleSheet : styleSheets) {
                    applyStyleSheet(styleSheet);
                }
                postStyleSheetLoad();
                sortChildren();
                updateState(true);
                if (parent instanceof Widget) {
                    Widget parentWidget = (Widget) parent;
                    if (!wasLazy) {
                        parentWidget.sortChildren();
                    }
                    parentWidget.handleAttributes();
                    parentWidget.updateBounds();
                }
            } finally {
                this.preventStateUpdate = false;
                updateState(true);
            }
        });
    }

    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        if (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION)) {
            handleSizeAttributes();
        }
        applyMediaRules(true);
        for (T child : this.children) {
            child.patchAttributes();
            child.handleAttributes();
        }
        notifyParentChildBoundsChanged(previousRect, newRect, true);
        if (!previousRect.equalsBounds(newRect)) {
            updateContentBounds();
            this.lastBoundsUpdateFrame = FRAME_TIMER.getFrame();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void notifyParentChildBoundsChanged(Rectangle previousRect, Rectangle newRect, boolean count) {
        if (!(this.parent instanceof StyledWidget)) {
            return;
        }
        if (count) {
            int i = this.childBoundsUpdates + 1;
            this.childBoundsUpdates = i;
            if (i > 1) {
                return;
            }
        }
        ((AbstractWidget) this.parent).onChildBoundsChanged(this.actualWidget, previousRect, newRect);
    }

    protected void onChildBoundsChanged(T child, Rectangle previousRect, Rectangle newRect) {
        updateContentBounds();
        notifyParentChildBoundsChanged(this.bounds.prevRectangle(), this.bounds, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void updateState(boolean force) {
        if (force) {
            this.childBoundsUpdates = 1;
        }
        super.updateState(force);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public boolean isUsingFloatingPointPosition() {
        return this.useFloatingPointPosition.get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void unfocus() {
        for (T child : this.children) {
            child.unfocus();
        }
        setFocused(false);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        FrameProfiler.push((Supplier<String>) () -> {
            return "render(" + this.widgetMetadata.simpleName() + ")";
        });
        super.render(context);
        prepareWidgetRendering(context);
        if (isVisible()) {
            renderWithScissor(context);
        } else {
            setAttributeState(AttributeState.HOVER, false);
        }
        this.childBoundsUpdates = 0;
        FrameProfiler.pop();
    }

    private void renderWithScissor(ScreenContext context) {
        boolean scissorTranslation = this.stencilTranslation.get().booleanValue();
        if (scissorTranslation) {
            Scissor scissor = context.canvas().scissor();
            scissor.push(bounds().rectangle(BoundsType.MIDDLE), this.useFloatingPointPosition.get().booleanValue());
            try {
                renderInternal(context);
            } catch (Throwable throwable) {
                LOGGER.error("Failed to render widget while using scissor ({})", getTypeName(), throwable);
            }
            scissor.pop();
            return;
        }
        renderInternal(context);
    }

    private void renderInternal(ScreenContext context) {
        MutableMouse mouse = context.mouse();
        Bounds bounds = bounds();
        FloatVector2 pivot = getPivot();
        float x = bounds.getX(BoundsType.OUTER);
        float y = bounds.getY(BoundsType.OUTER);
        float mouseX = this.widgetTransformer.transformMouseX(mouse.getX(), x + pivot.getX());
        float mouseY = this.widgetTransformer.transformMouseY(mouse.getY(), y + pivot.getY());
        isHovered();
        boolean newHovered = isHovered(mouseX, mouseY);
        setAttributeState(AttributeState.HOVER, newHovered);
        bounds.checkForChanges();
        if (!isInRenderDistance(mouse)) {
            return;
        }
        context.pushStack();
        this.widgetTransformer.applyStackManipulation(context, x, y, pivot);
        double currentX = mouse.getXDouble();
        double currentY = mouse.getYDouble();
        mouse.set(mouseX, mouseY);
        float opacity = this.opacity.get().floatValue();
        boolean opaque = opacity > 0.0f && opacity < 1.0f;
        if (opaque) {
            this.widgetMeta.multiplyAlpha(opacity);
        }
        if (opacity > 0.0f) {
            try {
                renderWidgetTheme(context);
            } catch (Throwable throwable) {
                LOGGER.error("Failed to render widget", throwable);
            }
        }
        if (opaque) {
            this.widgetMeta.revertAlphaState();
        }
        DebugWidgetRenderer.renderDebug(context, this);
        mouse.set(currentX, currentY);
        context.popStack();
    }

    protected boolean isHovered(float mouseX, float mouseY) {
        ReasonableMutableRectangle middleRectangle = this.bounds.rectangle(BoundsType.MIDDLE);
        return (!(this.parent instanceof Widget) || ((Widget) this.parent).isHovered()) && middleRectangle.isInRectangle(mouseX, mouseY) && (this.inOverlay || !this.labyAPI.screenOverlayHandler().isOverlayHovered()) && canHover();
    }

    public FloatVector2 getPivot() {
        return this.widgetTransformer.getPivot();
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean transformMouse(MutableMouse mouse, BooleanSupplier handler) {
        FloatVector2 pivot = getPivot();
        return mouse.set(this.widgetTransformer.transformMouseX(mouse.getX(), pivot.getX()), this.widgetTransformer.transformMouseY(mouse.getY(), pivot.getY()), handler);
    }

    protected void onAttributeStateChanged(AttributeState state, boolean newState) {
        updateState(true);
    }

    protected boolean isInRenderDistance(Mouse mouse) {
        if (this.mouseRenderDistance.get().floatValue() != 0.0f) {
            float dx = bounds().getCenterX(BoundsType.OUTER) - mouse.getX();
            float dy = bounds().getCenterY(BoundsType.OUTER) - mouse.getY();
            float distanceSq = (dx * dx) + (dy * dy);
            float maxSq = this.mouseRenderDistance.get().floatValue() * this.mouseRenderDistance.get().floatValue();
            return distanceSq <= maxSq;
        }
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasTabFocus() {
        return false;
    }

    protected void renderWidgetTheme(ScreenContext context) {
        boolean useClip = this.writeToStencilBuffer.get().booleanValue() && this.borderRadius != null && this.borderRadius.isSet();
        if (useClip) {
            context.canvas().pushClipShape(ClipShape.roundedRect(bounds().rectangle(BoundsType.MIDDLE), this.borderRadius));
        }
        renderWidgetTheme(context, true);
        if (useClip) {
            context.canvas().popClipShape();
        }
    }

    private void renderWidgetTheme(ScreenContext context, boolean renderContent) {
        ThemeRenderer themeRenderer = this.renderer.get();
        this.widgetRendererContext.setThemeRenderer(themeRenderer);
        this.widgetRendererContext.renderPre(context);
        if (renderContent) {
            this.widgetRendererContext.renderWidget(context);
        }
        this.widgetRendererContext.renderPost(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (isVisible() && this.renderChildren.get().booleanValue()) {
            int size = this.children.size();
            for (int i = 0; i < size; i++) {
                try {
                    T widget = this.children.get(i);
                    if (widget != null) {
                        boolean forceVanillaFont = this.forceVanillaFont.get().booleanValue();
                        RenderAttributesStack renderAttributesStack = RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
                        RenderAttributes attributes = renderAttributesStack.pushAndGet();
                        if (forceVanillaFont) {
                            attributes.setForceVanillaFont(true);
                        }
                        attributes.setFontWeight(widget.getFontWeight());
                        attributes.apply();
                        try {
                            widget.render(context);
                        } catch (Throwable throwable) {
                            LOGGER.error("Failed to render widget ({})", getTypeName(), throwable);
                        }
                        renderAttributesStack.pop();
                    }
                } catch (Throwable throwable2) {
                    LOGGER.error("Failed to render widget", throwable2);
                }
            }
            if (0 != 0) {
                sortChildren();
            }
        }
        if (this.hoverCursorType != null) {
            handleCursor(context);
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        FrameProfiler.push((Supplier<String>) () -> {
            return "tick(" + this.widgetMetadata.simpleName() + ")";
        });
        boolean forceVanillaFont = this.forceVanillaFont.get().booleanValue();
        RenderAttributesStack stack = RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
        RenderAttributes attributes = stack.pushAndGet();
        if (forceVanillaFont) {
            attributes.setForceVanillaFont(true);
        }
        attributes.setFontWeight(getFontWeight());
        attributes.apply();
        try {
            super.tick();
        } catch (Throwable throwable) {
            LOGGER.error("Failed to tick widget ({})", getTypeName(), throwable);
        }
        stack.pop();
        FrameProfiler.pop();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderOverlay(ScreenContext context) {
        float fFloatValue;
        if (isVisible() && this.opacity.get().floatValue() > 0.0f) {
            for (T widget : this.children) {
                if (widget instanceof AbstractWidget) {
                    fFloatValue = ((AbstractWidget) widget).opacity().get().floatValue();
                } else {
                    fFloatValue = 1.0f;
                }
                float opacity = fFloatValue;
                this.widgetMeta.multiplyAlpha(opacity);
                try {
                    widget.renderOverlay(context);
                } catch (Throwable throwable) {
                    LOGGER.error("Failed to render widget overlay ({})", widget.getTypeName(), throwable);
                }
                this.widgetMeta.revertAlphaState();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderHoverComponent(ScreenContext context) {
        if (isVisible() && this.opacity.get().floatValue() > 0.0f) {
            boolean renderHover = isHoverComponentRendered();
            for (T widget : this.children) {
                widget.renderHoverComponent(context);
                if (renderHover && shouldCancelHoverComponent(widget)) {
                    AttributeStateValue attributeStateValue = this.attributeStates.get(AttributeState.HOVER);
                    if (attributeStateValue != null) {
                        attributeStateValue.lastUpdate = TimeUtil.getMillis();
                    }
                    renderHover = false;
                }
            }
            if (renderHover && this.hoverComponent != null) {
                Laby.references().tooltipService().renderTooltip(context, this, this.hoverComponent);
            } else {
                Laby.references().tooltipService().unhover(this);
            }
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        this.lastMouseClick.update(mouseButton);
        if (isHovered() && shouldFocusOnClick()) {
            setFocused(true);
        }
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if ((isHovered() || (isInteractableOutside() && this.bounds.isInRectangle(mouse))) && this.visible.get().booleanValue()) {
            if (mouseButton == MouseButton.RIGHT && hasContextMenu() && !this.labyAPI.minecraft().isMouseDown(MouseButton.LEFT)) {
                openContextMenu();
                return true;
            }
            if (mouseButton == MouseButton.LEFT) {
                this.mouseClickedX = mouse.getX();
                this.mouseClickedY = mouse.getY();
                if (!this.pressOnRelease) {
                    return onPress();
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (super.mouseReleased(mouse, mouseButton)) {
            return true;
        }
        if (this.pressOnRelease) {
            if ((isHovered() || (isInteractableOutside() && this.bounds.isInRectangle(mouse))) && this.visible.get().booleanValue() && mouseButton == MouseButton.LEFT && mouse.getX() == this.mouseClickedX && mouse.getY() == this.mouseClickedY) {
                return onPress();
            }
            return false;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        if (this.pressListener != null && this.pressable.get().booleanValue()) {
            boolean playInteractionSoundAfterHandling = playInteractionSoundAfterHandling();
            if (!playInteractionSoundAfterHandling) {
                playInteractionSoundInternal();
            }
            boolean handled = this.pressListener.getAsBoolean();
            if (playInteractionSoundAfterHandling && handled) {
                playInteractionSoundInternal();
            }
            return handled;
        }
        return false;
    }

    @ApiStatus.Internal
    public boolean playInteractionSoundInternal() {
        ThemeRenderer themeRenderer = this.renderer.get();
        if (themeRenderer == null || !themeRenderer.hasInteractionSound()) {
            SoundType interactionSound = getInteractionSound();
            if (interactionSound != null) {
                Laby.references().soundService().play(interactionSound);
            }
            return interactionSound != null;
        }
        themeRenderer.playInteractionSound(this);
        return true;
    }

    protected boolean shouldFocusOnClick() {
        return true;
    }

    protected SoundType getInteractionSound() {
        return null;
    }

    @Deprecated
    public Pressable getPressable() {
        if (this.pressListener == null) {
            return null;
        }
        return () -> {
            this.pressListener.getAsBoolean();
        };
    }

    public boolean hasPressListener() {
        return this.pressListener != null;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setPressable(Pressable pressable) {
        if (pressable == null) {
            this.pressListener = null;
        } else {
            this.pressListener = () -> {
                pressable.press();
                return true;
            };
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setPressListener(BooleanSupplier pressable) {
        this.pressListener = pressable;
    }

    protected boolean playInteractionSoundAfterHandling() {
        return false;
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isDragging() {
        return isAttributeStateEnabled(AttributeState.DRAGGING);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void setDragging(boolean dragging) {
        setAttributeState(AttributeState.DRAGGING, dragging);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isDraggable() {
        return this.draggable.get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isInteractable() {
        return this.interactable.get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public boolean isHovered() {
        return isAttributeStateEnabled(AttributeState.HOVER);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isInteractableOutside() {
        return this.interactableOutside.get().booleanValue();
    }

    protected boolean canHover() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Widget getHighestParentWidget() {
        Parent parent = getParent();
        if (parent instanceof Widget) {
            return ((Widget) parent).getHighestParentWidget();
        }
        return this;
    }

    public T getChild(String id) {
        for (T child : this.children) {
            if (child.hasId(id)) {
                return child;
            }
        }
        return null;
    }

    public Widget getChildRecursive(String id) {
        Widget recursiveChild;
        Widget child = getChild(id);
        if (child != null) {
            return child;
        }
        for (T child2 : this.children) {
            if ((child2 instanceof AbstractWidget) && (recursiveChild = ((AbstractWidget) child2).getChildRecursive(id)) != null) {
                return recursiveChild;
            }
        }
        return null;
    }

    public Widget getUnwrappedChildRecursive(String id) {
        Widget child = getChildRecursive(id);
        return WidgetUtil.unwrapWidget(child);
    }

    public T addChild(T t) {
        return (T) addChild(this.children.size(), t);
    }

    public T addChild(T t, boolean z) {
        return (T) addChild(this.children.size(), t, z);
    }

    public T addChild(int i, T t) {
        return (T) addChild(i, t, true);
    }

    public T addChild(int index, T widget, boolean shouldSort) {
        ThreadSafe.ensureRenderThread();
        Objects.requireNonNull(widget, "widget must be not null");
        if (widget == this) {
            throw new RuntimeException(this.widgetMetadata.simpleName() + " added itself as child");
        }
        if (this.distinct.get().booleanValue() && this.children.contains(widget)) {
            return widget;
        }
        this.children.add(index, widget);
        widget.onAttachedTo(this);
        if (shouldSort) {
            sortChildren();
        }
        return widget;
    }

    public T addChildBeforeId(String str, T t) {
        return (T) addChild(indexOfId(str), t);
    }

    public T addChildInitializedBeforeId(String str, T t) {
        return (T) addChildInitialized(indexOfId(str), t);
    }

    public T addChildAfterId(String str, T t) {
        return (T) addChild(indexAfterId(str), t);
    }

    public T addChildInitializedAfterId(String str, T t) {
        return (T) addChildInitialized(indexAfterId(str), t);
    }

    private int indexOfId(String id) {
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i).hasId(id)) {
                return i;
            }
        }
        return this.children.size();
    }

    private int indexAfterId(String id) {
        int index = indexOfId(id);
        return index < this.children.size() ? index + 1 : index;
    }

    public void addChildAsync(T widget) {
        this.labyAPI.minecraft().executeNextTick(() -> {
            addChildInitialized(widget);
        });
    }

    public T addChildInitialized(T t) {
        return (T) addChildInitialized(this.children.size(), t);
    }

    public T addChildInitialized(T t, boolean z) {
        return (T) addChildInitialized(this.children.size(), t, z);
    }

    public T addChildInitialized(int i, T t) {
        return (T) addChildInitialized(i, t, true);
    }

    public T addChildInitialized(int index, T widget, boolean shouldSort) {
        addChild(index, widget, shouldSort);
        initializeChild(widget);
        updateState(true);
        updateBounds();
        handleAttributes();
        widget.postStyleSheetLoad();
        updateContentBounds();
        if (this.parent instanceof AbstractWidget) {
            ((AbstractWidget) this.parent).updateState(true);
            ((AbstractWidget) this.parent).updateBounds();
            ((AbstractWidget) this.parent).handleAttributes();
        }
        return widget;
    }

    public void addChildren(Collection<T> widgets, boolean shouldSort) {
        for (T widget : widgets) {
            addChild((Widget) widget, false);
        }
        if (shouldSort) {
            sortChildren();
        }
    }

    public void addChildrenInitialized(Collection<T> widgets, boolean shouldSort) {
        for (T widget : widgets) {
            addChild((Widget) widget, false);
        }
        if (shouldSort) {
            sortChildren();
        }
        for (T widget2 : widgets) {
            initializeChild(widget2);
        }
        updateState(true);
        updateBounds();
        handleAttributes();
        for (T widget3 : widgets) {
            widget3.postStyleSheetLoad();
        }
        updateContentBounds();
    }

    private void initializeChild(T widget) {
        widget.initialize(this);
        widget.postInitialize();
        for (StyleSheet styleSheet : this.styleSheets) {
            widget.applyStyleSheet(styleSheet);
        }
        Widget unwrappedWidget = WidgetUtil.unwrapWidget(widget);
        if (unwrappedWidget instanceof AbstractWidget) {
            AbstractWidget abstractWidget = (AbstractWidget) unwrappedWidget;
            abstractWidget.applyCustomStyleSheets();
        }
        widget.updateState(true);
    }

    public boolean removeChild(String id) {
        return removeChildIf(widget -> {
            return widget.hasId(id);
        });
    }

    public void removeChild(T widget) {
        if (widget == this) {
            throw new RuntimeException(this.widgetMetadata.simpleName() + " removed itself as child");
        }
        if ((widget instanceof AbstractWidget) && ((AbstractWidget) widget).destroyDelay().get().longValue() > 0) {
            Task task = Task.builder(() -> {
                removeChildImmediately(widget);
            }).delay(((AbstractWidget) widget).destroyDelay().get().longValue(), TimeUnit.MILLISECONDS).build();
            task.executeOnRenderThread();
        } else {
            removeChildImmediately(widget);
        }
    }

    public void removeChildImmediately(T widget) {
        ThreadSafe.ensureRenderThread();
        if (this.children.remove(widget)) {
            onChildRemove();
        }
    }

    private void onChildRemove() {
        updateState(true);
        updateBounds();
        handleAttributes();
        updateContentBounds();
    }

    public void replaceChild(T widget, T replacement) {
        ThreadSafe.ensureRenderThread();
        if (widget == this || replacement == this) {
            throw new RuntimeException(this.widgetMetadata.simpleName() + " replaced itself as child");
        }
        int index = this.children.indexOf(widget);
        if (index == -1) {
            addChild(replacement);
        } else {
            this.children.set(index, replacement);
        }
    }

    public boolean removeChildIf(Predicate<T> predicate) {
        ThreadSafe.ensureRenderThread();
        boolean removed = this.children.removeIf(predicate);
        if (removed) {
            onChildRemove();
        }
        return removed;
    }

    public <K> boolean removeChildIf(Class<K> type, Predicate<K> predicate) {
        return removeChildIf(widget -> {
            if (type.isInstance(widget)) {
                return predicate.test(widget);
            }
            return false;
        });
    }

    public <K extends Widget> List<K> findChildrenIf(Predicate<T> predicate) {
        List<K> found = new ArrayList<>();
        for (T child : this.children) {
            if (predicate.test(child)) {
                found.add(child);
            }
        }
        return found;
    }

    public <K extends Widget> List<K> findChildrenIf(Class<K> type, Predicate<K> predicate) {
        List<K> found = new ArrayList<>();
        for (T child : this.children) {
            if (type.isInstance(child) && predicate.test(child)) {
                found.add(child);
            }
        }
        return found;
    }

    public <K extends Widget> K findFirstChildIf(Predicate<T> predicate) {
        for (T t : this.children) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public void reInitializeChildrenIf(Predicate<T> predicate) {
        for (K widget : findChildrenIf(predicate)) {
            widget.reInitialize();
        }
    }

    public <K extends Widget> void reInitializeChildrenIf(Class<K> type, Predicate<K> predicate) {
        for (K widget : findChildrenIf(type, predicate)) {
            widget.reInitialize();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void updateBounds() {
        bounds().checkForChanges();
        for (T child : this.children) {
            child.updateBounds();
        }
        updateContentBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void handleAttributes() {
        if (!this.handleStyleSheet) {
            return;
        }
        handleSizeAttributes();
        if (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION)) {
            if (this.parent != null) {
                handleRelativeBoundsAttributes(this.parent.bounds());
            }
            handleAlignmentAttributes();
        } else if (this.parent != null) {
            if (!this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT) && this.top.get() != null && this.bottom.get() != null && this.top.get().floatValue() == 0.0f && this.bottom.get().floatValue() == 0.0f) {
                bounds().setOuterHeight(this.parent.bounds().getHeight(BoundsType.INNER), FULL_PARENT_SIZE);
            }
            if (!this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH) && this.left.get() != null && this.right.get() != null && this.left.get().floatValue() == 0.0f && this.right.get().floatValue() == 0.0f) {
                bounds().setOuterWidth(this.parent.bounds().getWidth(BoundsType.INNER), FULL_PARENT_SIZE);
            }
        }
        handleSizeAttributes();
        applyMediaRules(false);
        updateContentBounds();
    }

    protected void handleSizeAttributes() {
        float minWidth = getSizeOr(SizeType.MIN, WidgetSide.WIDTH, 0.0f);
        float minHeight = getSizeOr(SizeType.MIN, WidgetSide.HEIGHT, 0.0f);
        Float width = getSize(SizeType.ACTUAL, WidgetSide.WIDTH);
        Float height = getSize(SizeType.ACTUAL, WidgetSide.HEIGHT);
        float maxWidth = getSizeOr(SizeType.MAX, WidgetSide.WIDTH, Float.MAX_VALUE);
        float maxHeight = getSizeOr(SizeType.MAX, WidgetSide.HEIGHT, Float.MAX_VALUE);
        if (width != null && ((this.left.get() == null || this.right.get() == null) && (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH)))) {
            bounds().setWidth(Math.max(width.floatValue(), minWidth), LSS_SIZE);
        }
        if (height != null && ((this.top.get() == null || this.bottom.get() == null) && (this.parent == null || !this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT)))) {
            bounds().setHeight(Math.max(height.floatValue(), minHeight), LSS_SIZE);
        }
        handleMinAttributes(minWidth, minHeight, MIN_SIZE);
        handleMaxAttributes(maxWidth, maxHeight, MAX_SIZE);
    }

    private void handleAlignmentAttributes() {
        float centerY;
        float centerX;
        if (this.parent == null) {
            return;
        }
        Bounds bounds = bounds();
        float thisWidth = bounds.getWidth(BoundsType.OUTER);
        float thisHeight = bounds.getHeight(BoundsType.OUTER);
        if (thisWidth != 0.0f) {
            if (alignmentX().get() == WidgetAlignment.CENTER) {
                if (this.left.get() != null) {
                    centerX = this.parent.bounds().getLeft(BoundsType.INNER) + this.left.get().floatValue();
                } else if (this.right.get() != null) {
                    centerX = this.parent.bounds().getRight(BoundsType.INNER) - this.right.get().floatValue();
                } else {
                    centerX = this.parent.bounds().getCenterX(BoundsType.INNER);
                }
                float outerX = centerX - (thisWidth / 2.0f);
                bounds.setOuterX(transformFloatingPoint(outerX), ALIGNMENT);
            }
            if (alignmentX().get() == WidgetAlignment.RIGHT) {
                float rightX = this.parent.bounds().getRight(BoundsType.INNER) - this.right.getOrDefault(Float.valueOf(0.0f)).floatValue();
                bounds.setOuterX(rightX - thisWidth, ALIGNMENT);
            }
        }
        if (thisHeight != 0.0f) {
            if (alignmentY().get() == WidgetAlignment.CENTER) {
                if (this.top.get() != null) {
                    centerY = this.parent.bounds().getTop(BoundsType.INNER) + this.top.get().floatValue();
                } else if (this.bottom.get() != null) {
                    centerY = this.parent.bounds().getBottom(BoundsType.INNER) - this.bottom.get().floatValue();
                } else {
                    centerY = this.parent.bounds().getCenterY(BoundsType.INNER);
                }
                float outerY = centerY - (thisHeight / 2.0f);
                bounds.setOuterY(transformFloatingPoint(outerY), ALIGNMENT);
            }
            if (alignmentY().get() == WidgetAlignment.BOTTOM) {
                float bottomY = this.parent.bounds().getBottom(BoundsType.INNER) - this.bottom.getOrDefault(Float.valueOf(0.0f)).floatValue();
                bounds.setOuterY(bottomY - thisHeight, ALIGNMENT);
            }
        }
    }

    protected void handleRelativeBoundsAttributes(Bounds parentBounds) {
        Bounds bounds = bounds();
        if (this.top.get() != null) {
            bounds.setOuterY(applyHeightPrecision(parentBounds.getY(BoundsType.INNER) + this.top.get().floatValue()), RELATIVE_BOUNDS_TOP);
        }
        if (this.bottom.get() != null) {
            if (this.top.get() != null) {
                bounds.setOuterHeight(applyHeightPrecision(parentBounds.getHeight(BoundsType.INNER) - (this.bottom.get().floatValue() + this.top.get().floatValue())), RELATIVE_BOUNDS_BOTTOM);
            }
            bounds.setOuterY(applyHeightPrecision((parentBounds.getBottom(BoundsType.INNER) - bounds.getHeight(BoundsType.OUTER)) - this.bottom.get().floatValue()), RELATIVE_BOUNDS_BOTTOM);
        }
        if (this.left.get() != null) {
            bounds.setOuterX(applyWidthPrecision(parentBounds.getX(BoundsType.INNER) + this.left.get().floatValue()), RELATIVE_BOUNDS_LEFT);
        }
        if (this.right.get() != null) {
            if (this.left.get() != null) {
                bounds.setOuterWidth(applyWidthPrecision(parentBounds.getWidth(BoundsType.INNER) - (this.right.get().floatValue() + this.left.get().floatValue())), RELATIVE_BOUNDS_RIGHT);
            }
            bounds.setOuterX(applyWidthPrecision((parentBounds.getRight(BoundsType.INNER) - bounds.getWidth(BoundsType.OUTER)) - this.right.get().floatValue()), RELATIVE_BOUNDS_RIGHT);
        }
    }

    private float applyWidthPrecision(float value) {
        Float precision = this.widthPrecision.get();
        if (precision == null || precision.floatValue() <= 0.0f) {
            return transformFloatingPoint(value);
        }
        return transformFloatingPoint(((int) (value / precision.floatValue())) * precision.floatValue());
    }

    private float applyHeightPrecision(float value) {
        Float precision = this.heightPrecision.get();
        if (precision == null || precision.floatValue() <= 0.0f) {
            return transformFloatingPoint(value);
        }
        return transformFloatingPoint(((int) (value / precision.floatValue())) * precision.floatValue());
    }

    protected void handleMinAttributes(float minWidth, float minHeight, ModifyReason reason) {
        WidgetSize minWidthSize = this.sizes.getSize(SizeType.MIN, WidgetSide.WIDTH);
        WidgetSize minHeightSize = this.sizes.getSize(SizeType.MIN, WidgetSide.HEIGHT);
        handleMinWidth((minWidthSize == null || !minWidthSize.percentage()) ? BoundsType.INNER : BoundsType.OUTER, minWidth, reason);
        handleMinHeight((minHeightSize == null || !minHeightSize.percentage()) ? BoundsType.INNER : BoundsType.OUTER, minHeight, reason);
    }

    private void handleMinWidth(BoundsType type, float minWidth, ModifyReason reason) {
        Bounds bounds = bounds();
        float minWidth2 = minWidth + bounds.getHorizontalOffset(type);
        if (minWidth2 <= 0.0f || bounds.getWidth(type) >= minWidth2) {
            return;
        }
        float difference = minWidth2 - bounds.getWidth(type);
        bounds.setWidth(minWidth2, type, reason);
        float offset = 0.0f;
        switch (alignmentX().get()) {
            case CENTER:
                offset = difference / 2.0f;
                break;
            case RIGHT:
                offset = difference;
                break;
        }
        if (offset != 0.0f) {
            bounds.setX(bounds.getX(type) - transformFloatingPoint(offset), type, reason);
        }
    }

    private void handleMinHeight(BoundsType type, float minHeight, ModifyReason reason) {
        Bounds bounds = bounds();
        float minHeight2 = minHeight + bounds.getVerticalOffset(type);
        if (minHeight2 <= 0.0f || bounds.getHeight(type) >= minHeight2) {
            return;
        }
        float difference = minHeight2 - bounds.getHeight(type);
        bounds.setHeight(minHeight2, type, reason);
        float offset = 0.0f;
        switch (alignmentY().get()) {
            case CENTER:
                offset = difference / 2.0f;
                break;
            case RIGHT:
                offset = difference;
                break;
        }
        if (offset != 0.0f) {
            bounds.setY(bounds.getY(type) - transformFloatingPoint(offset), type, reason);
        }
    }

    protected void handleMaxAttributes(float maxWidth, float maxHeight, ModifyReason reason) {
        boolean realign = false;
        Bounds bounds = bounds();
        if (maxWidth > 0.0f && bounds.getWidth() > maxWidth) {
            float width = bounds.getWidth(BoundsType.OUTER) - maxWidth;
            bounds.setWidth(maxWidth, reason);
            realign = true;
        }
        if (maxHeight > 0.0f && bounds.getHeight() > maxHeight) {
            float height = bounds.getHeight(BoundsType.OUTER) - maxHeight;
            bounds.setHeight(maxHeight, reason);
            realign = true;
        }
        if (realign) {
            handleAlignmentAttributes();
        }
        if (this.widthPrecision.get() != null && this.widthPrecision.get().floatValue() > 0.0f) {
            bounds.setWidth(applyWidthPrecision(bounds.getWidth(BoundsType.INNER)), reason);
        }
        if (this.heightPrecision.get() != null && this.heightPrecision.get().floatValue() > 0.0f) {
            bounds.setHeight(applyHeightPrecision(bounds.getHeight(BoundsType.INNER)), reason);
        }
    }

    protected float transformFloatingPoint(float value) {
        return transformFloatingPoint((AbstractWidget<?>) this, value);
    }

    protected float transformFloatingPoint(AbstractWidget<?> widget, float value) {
        return transformFloatingPoint(widget.useFloatingPointPosition().get().booleanValue(), value);
    }

    protected float transformFloatingPoint(boolean useFloatingPoint, float value) {
        return MathHelper.applyFloatingPointPosition(useFloatingPoint, value);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public Float getMargin(OffsetSide side) {
        switch (side) {
            case LEFT:
                return this.marginLeft.get();
            case TOP:
                return this.marginTop.get();
            case RIGHT:
                return this.marginRight.get();
            case BOTTOM:
                return this.marginBottom.get();
            default:
                return Float.valueOf(0.0f);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public Float getPadding(OffsetSide side) {
        switch (side) {
            case LEFT:
                return this.paddingLeft.get();
            case TOP:
                return this.paddingTop.get();
            case RIGHT:
                return this.paddingRight.get();
            case BOTTOM:
                return this.paddingBottom.get();
            default:
                return Float.valueOf(0.0f);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public Float getBorder(OffsetSide side) {
        if (this.border == null) {
            return Float.valueOf(0.0f);
        }
        switch (side) {
            case LEFT:
                return Float.valueOf(this.border.getLeftWidth());
            case TOP:
                return Float.valueOf(this.border.getTopWidth());
            case RIGHT:
                return Float.valueOf(this.border.getRightWidth());
            case BOTTOM:
                return Float.valueOf(this.border.getBottomWidth());
            default:
                return Float.valueOf(0.0f);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setActionListener(Runnable listener) {
        setActionListener("main", listener);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setActionListener(String id, Runnable listener) {
        this.actionListeners.put(id, listener);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void removeActionListener(String id) {
        this.actionListeners.remove(id);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isFocused() {
        return isAttributeStateEnabled(AttributeState.FOCUS);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setFocused(boolean focused) {
        setAttributeState(AttributeState.FOCUS, focused || this.alwaysFocused.get().booleanValue());
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isVisible() {
        return this.visible.get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void setVisible(boolean visible) {
        this.visible.set(Boolean.valueOf(visible));
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <W extends Widget> W addId(String... ids) {
        boolean added = false;
        for (String id : ids) {
            boolean v = addIdInternal(id);
            if (!added) {
                added = v;
            }
        }
        if (this.initialized && added) {
            reloadStyleSheets();
        }
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <W extends Widget> W addId(String id) {
        if (addIdInternal(id) && this.initialized) {
            reloadStyleSheets();
        }
        return this;
    }

    private boolean addIdInternal(String id) {
        if (this.ids.contains(id)) {
            return false;
        }
        this.ids.add(id);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasId(CharSequence id) {
        return this.ids.contains(id);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void removeId(String id) {
        boolean removed = this.ids.remove(id);
        if (removed && this.initialized) {
            reloadStyleSheets();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <W extends Widget> W replaceId(String str, String str2) {
        if (this.ids.remove(str)) {
            return (W) addId(str2);
        }
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getUniqueId() {
        return this.uniqueId.get();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<CharSequence> getIds() {
        return this.ids;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getTypeName() {
        return this.widgetMetadata.simpleName();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getQualifiedName() {
        return this.widgetMetadata.qualifiedName();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getParent() {
        return this.parent;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Bounds bounds() {
        return this.bounds;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Animation animation() {
        return this.animation;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public AnimationBuilder animate(String propertyName) {
        return new AnimationBuilder(this.animation, propertyName);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public AnimationBuilder animate() {
        return new AnimationBuilder(this.animation);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void playAnimation(String id, Runnable finishHandler) {
        int i;
        if (this.styleSheets.isEmpty()) {
            this.pendingAnimationId = id;
            this.pendingAnimationFinishHandler = finishHandler;
            return;
        }
        Optional<StyleRule> found = Optional.empty();
        Iterator<StyleSheet> it = this.styleSheets.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StyleSheet sheet = it.next();
            StyleRule keyframes = sheet.getRule("keyframes", id);
            if (keyframes != null) {
                found = Optional.of(keyframes);
                break;
            }
        }
        List<StyleBlock> blocks = (List) found.map((v0) -> {
            return v0.getBlocks();
        }).orElse(null);
        if (blocks == null) {
            if (finishHandler != null) {
                this.labyAPI.minecraft().executeNextTick(finishHandler);
                return;
            }
            return;
        }
        Map<String, Map<Long, AttributePatch>> patches = new HashMap<>();
        for (StyleBlock block : blocks) {
            String stage = block.getRawSelector().trim();
            if (!stage.isEmpty()) {
                if (stage.equalsIgnoreCase("from")) {
                    i = 0;
                } else {
                    i = stage.equalsIgnoreCase("to") ? 100 : Integer.parseInt(stage.replace("%", ""));
                }
                int percentage = i;
                long currentMillis = (long) ((((double) percentage) / 100.0d) * ((double) this.animationDuration.get().intValue()));
                block.generateAttributePatches(this, 1).forEach((key, patch) -> {
                    ((Map) patches.computeIfAbsent(key, s -> {
                        return new HashMap();
                    })).put(Long.valueOf(currentMillis), patch.patch());
                });
            }
        }
        if (patches.isEmpty()) {
            if (finishHandler != null) {
                this.labyAPI.minecraft().executeNextTick(finishHandler);
                return;
            }
            return;
        }
        for (Map.Entry<String, Map<Long, AttributePatch>> entry : patches.entrySet()) {
            String key2 = entry.getKey();
            Map<Long, AttributePatch> currentPatches = entry.getValue();
            boolean seen = false;
            Map.Entry<Long, AttributePatch> best = null;
            Comparator<Map.Entry<Long, AttributePatch>> comparator = Comparator.comparingLong((v0) -> {
                return v0.getKey();
            });
            for (Map.Entry<Long, AttributePatch> longAttributePatchEntry : currentPatches.entrySet()) {
                if (!seen || comparator.compare(longAttributePatchEntry, best) < 0) {
                    seen = true;
                    best = longAttributePatchEntry;
                }
            }
            AttributePatch value = (!seen || best == null) ? null : best.getValue();
            AttributePatch min = value;
            AttributeAnimation<AttributePatch> animation = this.animation.createCustom(key2, new AttributePatchAnimationAccessor(key2, this, min));
            for (Map.Entry<Long, AttributePatch> attributePatch : currentPatches.entrySet()) {
                Long timestamp = attributePatch.getKey();
                AttributePatch patch2 = attributePatch.getValue();
                animation.addKeyframe(patch2, timestamp.longValue(), this.animationTimingFunction.get());
            }
        }
        this.styleInstructions.removeIf(i2 -> {
            return i2.selector().buildSelector().equals("#" + getUniqueId());
        });
        this.animation.setCompletionListener(() -> {
            for (String key3 : patches.keySet()) {
                this.animation.remove(key3);
            }
            for (Map<Long, AttributePatch> map : patches.values()) {
                boolean seen2 = false;
                Map.Entry<Long, AttributePatch> best2 = null;
                Comparator<Map.Entry<Long, AttributePatch>> comparator2 = Comparator.comparingLong((v0) -> {
                    return v0.getKey();
                });
                for (Map.Entry<Long, AttributePatch> longAttributePatchEntry2 : map.entrySet()) {
                    if (!seen2 || comparator2.compare(longAttributePatchEntry2, best2) > 0) {
                        seen2 = true;
                        best2 = longAttributePatchEntry2;
                    }
                }
                if (seen2 && best2 != null) {
                    addAttributePatch(this.labyAPI.styleHelper().createSelector("#" + getUniqueId()), best2.getValue(), 0);
                }
            }
            if (finishHandler != null) {
                finishHandler.run();
            }
        });
        int iterations = this.animationIterationCount.get().intValue();
        boolean alternate = this.animationDirectionAlternate.get().booleanValue();
        if (iterations == -1 || iterations > 1) {
            this.animation.loopMode(alternate ? LoopMode.PING_PONG : LoopMode.LOOP);
        }
        this.animation.start();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void playAnimation(String id) {
        playAnimation(id, null);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<ThemeRenderer> renderer() {
        return this.renderer;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement
    public List<T> getChildren() {
        return this.children;
    }

    @Deprecated
    public List<T> getGenericChildren() {
        return this.children;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public final float getContentSize(BoundsType type, WidgetSide side) {
        return side == WidgetSide.WIDTH ? getContentWidth(type) : getContentHeight(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        Float childWidth;
        boolean useRelativeBounds = this.parent == null || !(this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION) || this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH));
        float minX = useRelativeBounds ? this.left.getOrDefault(Float.valueOf(2.1474836E9f)).floatValue() : 2.1474836E9f;
        float maxX = useRelativeBounds ? this.right.getOrDefault(Float.valueOf(-2.1474836E9f)).floatValue() : -2.1474836E9f;
        for (T child : this.children) {
            if (child.isVisible() && isVisibleForContentBounds(child) && (childWidth = getFitWidth(child)) != null) {
                float childLeft = child.bounds().getLeft(BoundsType.OUTER);
                minX = Math.min(minX, childLeft);
                maxX = Math.max(maxX, childLeft + childWidth.floatValue());
            }
        }
        if (minX == 2.1474836E9f) {
            return 0.0f;
        }
        return bounds().getHorizontalOffset(type) + (maxX - minX);
    }

    protected Float getFitWidth(T child) {
        Float childWidth = null;
        WidgetSize width = child.sizes().getSize(SizeType.ACTUAL, WidgetSide.WIDTH);
        Bounds childBounds = child.bounds();
        if ((width != null && width.percentage()) || ((child instanceof AbstractWidget) && ((AbstractWidget) child).left.get() != null && ((AbstractWidget) child).right.get() != null)) {
            WidgetSize minWidth = child.sizes().getSize(SizeType.MIN, WidgetSide.WIDTH);
            if (minWidth != null && !minWidth.percentage()) {
                childWidth = Float.valueOf(minWidth.value() + childBounds.getHorizontalOffset(BoundsType.OUTER));
            }
        } else {
            childWidth = Float.valueOf(childBounds.getWidth(BoundsType.OUTER));
        }
        return childWidth;
    }

    protected float getDefaultContentWidth(BoundsType type) {
        return getSizeOr(SizeType.MIN, WidgetSide.WIDTH, 0.0f);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        Float childHeight;
        boolean useRelativeBounds = this.parent == null || !(this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.POSITION) || this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT));
        float minY = useRelativeBounds ? this.top.getOrDefault(Float.valueOf(2.1474836E9f)).floatValue() : 2.1474836E9f;
        float maxY = useRelativeBounds ? this.bottom.getOrDefault(Float.valueOf(-2.1474836E9f)).floatValue() : -2.1474836E9f;
        for (T child : this.children) {
            if (child.isVisible() && isVisibleForContentBounds(child) && (childHeight = getFitHeight(child)) != null) {
                float childTop = child.bounds().getTop(BoundsType.OUTER);
                minY = Math.min(minY, childTop);
                maxY = Math.max(maxY, childTop + childHeight.floatValue());
            }
        }
        if (minY == 2.1474836E9f) {
            return 0.0f;
        }
        return bounds().getVerticalOffset(type) + (maxY - minY);
    }

    protected Float getFitHeight(T child) {
        Float childHeight = null;
        WidgetSize height = child.sizes().getSize(SizeType.ACTUAL, WidgetSide.HEIGHT);
        Bounds childBounds = child.bounds();
        if ((height != null && height.percentage()) || ((child instanceof AbstractWidget) && ((AbstractWidget) child).top.get() != null && ((AbstractWidget) child).bottom.get() != null)) {
            WidgetSize minHeight = child.sizes().getSize(SizeType.MIN, WidgetSide.HEIGHT);
            if (minHeight != null && !minHeight.percentage()) {
                childHeight = Float.valueOf(minHeight.value() + childBounds.getVerticalOffset(BoundsType.OUTER));
            }
        } else {
            childHeight = Float.valueOf(childBounds.getHeight(BoundsType.OUTER));
        }
        return childHeight;
    }

    protected float getDefaultContentHeight(BoundsType type) {
        return getSizeOr(SizeType.MIN, WidgetSide.HEIGHT, 0.0f);
    }

    @Nullable
    protected Float predictWidth() {
        Bounds bounds = bounds();
        if (this.parent != null && this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.WIDTH)) {
            return Float.valueOf(bounds.getWidth());
        }
        if (hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
            return getSize(SizeType.MAX, WidgetSide.WIDTH);
        }
        return Float.valueOf(bounds.getWidth());
    }

    @Nullable
    protected Float predictHeight() {
        Bounds bounds = bounds();
        if (this.parent != null && this.parent.hasAutoBounds(this.actualWidget, AutoAlignType.HEIGHT)) {
            return Float.valueOf(bounds.getHeight());
        }
        if (hasSize(WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) {
            return getSize(SizeType.MAX, WidgetSide.HEIGHT);
        }
        return Float.valueOf(bounds.getHeight());
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public WidgetSizeList sizes() {
        return this.sizes;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setSize(SizeType type, WidgetSide side, WidgetSize size) {
        this.sizes.setSize(type, side, size);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Float getSize(SizeType type, WidgetSide side) {
        WidgetSize size = this.sizes.getSize(type, side);
        if (size == null) {
            return null;
        }
        float value = size.computeValue(this, side);
        return Float.valueOf(this.useFloatingPointPosition.get().booleanValue() ? value : MathHelper.floor(value));
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasSize(SizeType type, WidgetSide side, WidgetSize.Type sizeType) {
        WidgetSize size = this.sizes.getSize(type, side);
        return size != null && size.type() == sizeType;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasAnySize(WidgetSide side) {
        return this.sizes.hasAnySize(side);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isPercentageSize(SizeType type, WidgetSide side) {
        WidgetSize size = this.sizes.getSize(type, side);
        return size != null && size.percentage();
    }

    protected boolean isVisibleForContentBounds(T child) {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<BoxSizing> boxSizing() {
        return this.boxSizing;
    }

    public String toString() {
        return getTypeName();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getRoot() {
        if (this.parent != null) {
            return this.parent.getRoot();
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isActive() {
        return isAttributeStateEnabled(AttributeState.ACTIVE);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void setActive(boolean active) {
        if (isActive() == active) {
            return;
        }
        setAttributeState(AttributeState.ACTIVE, active);
        this.lastActiveChangedTime = TimeUtil.getMillis();
        updateState(false);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isTicking() {
        return this.ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasHoverComponent() {
        return this.hoverComponent != null;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverComponent(Component component) {
        RenderableComponent renderableComponentOf;
        if (component == null) {
            renderableComponentOf = null;
        } else {
            renderableComponentOf = RenderableComponent.of(component, 200.0f, HorizontalAlignment.LEFT);
        }
        this.hoverComponent = renderableComponentOf;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverComponent(Component component, float maxWidth) {
        this.hoverComponent = component == null ? null : RenderableComponent.of(component, maxWidth, HorizontalAlignment.LEFT);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverRenderableComponent(RenderableComponent renderableComponent) {
        this.hoverComponent = renderableComponent;
    }

    public void openContextMenu() {
        ContextMenu contextMenu = getContextMenu();
        if (contextMenu == null) {
            throw new IllegalStateException("Cannot open context menu, no context menu has been set");
        }
        contextMenu.open(this);
    }

    public void closeContextMenu() {
        this.contextMenu.close();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement
    public void sortChildren() {
        boolean z = false;
        for (T t : getChildren()) {
            t.sortChildren();
            if (t.getSortingValue() != 0) {
                z = true;
            }
        }
        if (z) {
            getChildren().sort(this.comparator);
        }
    }

    public void setComparator(Comparator<? super Widget> comparator) {
        this.comparator = comparator;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getTranslateX() {
        return this.translateX.get().floatValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setTranslateX(float translateX) {
        this.translateX.set(Float.valueOf(translateX));
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getTranslateY() {
        return this.translateY.get().floatValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setTranslateY(float translateY) {
        this.translateY.set(Float.valueOf(translateY));
    }

    public long getLastActionTime() {
        return this.lastActionTime;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getScaleX() {
        return this.scaleX.get().floatValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setScaleX(float scaleX) {
        this.scaleX.set(Float.valueOf(scaleX));
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getScaleY() {
        return this.scaleY.get().floatValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setScaleY(float scaleY) {
        this.scaleY.set(Float.valueOf(scaleY));
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
    }

    public void resetScale() {
        this.scaleX.reset();
        this.scaleY.reset();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getEffectiveWidth() {
        Float maxWidth = getSize(SizeType.MAX, WidgetSide.WIDTH);
        float contentWidth = getContentWidth(BoundsType.OUTER);
        if (maxWidth != null && maxWidth.floatValue() < contentWidth) {
            return maxWidth.floatValue();
        }
        return contentWidth;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getEffectiveHeight() {
        Float maxHeight = getSize(SizeType.MAX, WidgetSide.HEIGHT);
        float contentHeight = getContentHeight(BoundsType.OUTER);
        if (maxHeight != null && maxHeight.floatValue() < contentHeight) {
            return maxHeight.floatValue();
        }
        return contentHeight;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isSelected() {
        return isAttributeStateEnabled(AttributeState.SELECTED);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setSelected(boolean selected) {
        setAttributeState(AttributeState.SELECTED, selected);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isCancelParentHoverComponent() {
        return cancelParentHoverComponent().get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Rectangle childrenRegion() {
        MutableRectangle rectangle = Rectangle.extendable();
        for (T child : this.children) {
            if (child.isVisible()) {
                rectangle.extend(child.bounds());
                rectangle.extend(child.childrenRegion());
            }
        }
        return rectangle;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <K extends Widget> void traverse(Collection<K> output, FilterAction filterAction) {
        for (T child : this.children) {
            if (filterAction.shouldInclude(child)) {
                output.add(child);
            }
            child.traverse(output, filterAction);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<T> getChildrenAt(int x, int y) {
        List<T> children = new ArrayList<>();
        for (T child : this.children) {
            if (child.bounds().isInRectangle(x, y)) {
                children.add(child);
            }
        }
        return children;
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        try {
            for (T child : this.children) {
                child.dispose();
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            for (Widget child2 : (Widget[]) this.children.toArray(new Widget[0])) {
                child2.dispose();
            }
        }
        this.disposed = true;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }

    public void refreshActionTime() {
        this.lastActionTime = TimeUtil.getMillis();
    }

    protected void callActionListeners() {
        callActionListeners(false);
    }

    protected void callActionListeners(boolean children) {
        for (Runnable listener : this.actionListeners.values()) {
            listener.run();
        }
        if (!children) {
            this.lastActionTime = TimeUtil.getMillis();
            return;
        }
        for (T child : this.children) {
            Widget widgetUnwrapWidget = WidgetUtil.unwrapWidget(child);
            if (widgetUnwrapWidget instanceof AbstractWidget) {
                ((AbstractWidget) widgetUnwrapWidget).callActionListeners(children);
            }
        }
        this.lastActionTime = TimeUtil.getMillis();
    }

    public boolean hasBorder() {
        return this.border != null && this.border.isSet();
    }

    public boolean hasBorderRadius() {
        return this.borderRadius != null && this.borderRadius.isSet();
    }

    @Nullable
    public BorderRadius getBorderRadius() {
        return this.borderRadius;
    }

    public void setBorderRadius(@Nullable BorderRadius borderRadius) {
        this.borderRadius = borderRadius;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isLazy() {
        return this.lazy;
    }

    public long getLastInitialTime() {
        return this.lastInitialTime;
    }

    public long getLastDestroyTime() {
        return this.lastDestroyTime;
    }

    public long getLastActiveChangedTime() {
        return this.lastActiveChangedTime;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void destroy() {
        this.children.forEach((v0) -> {
            v0.destroy();
        });
        this.destroyed = true;
        this.lastDestroyTime = TimeUtil.getMillis();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.util.Disposable
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isDestroyed() {
        return this.destroyed;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <K extends Widget> void onAttachedTo(AbstractWidget<K> parent) {
    }

    public LssProperty<Integer> animationDuration() {
        return this.animationDuration;
    }

    public LssProperty<Boolean> draggable() {
        return this.draggable;
    }

    public LssProperty<Boolean> interactable() {
        return this.interactable;
    }

    public LssProperty<Boolean> pressable() {
        return this.pressable;
    }

    public LssProperty<Boolean> visible() {
        return this.visible;
    }

    public LssProperty<Float> left() {
        return this.left;
    }

    public LssProperty<Float> top() {
        return this.top;
    }

    public LssProperty<Float> right() {
        return this.right;
    }

    public LssProperty<Float> bottom() {
        return this.bottom;
    }

    public LssProperty<Float> marginLeft() {
        return this.marginLeft;
    }

    public LssProperty<Float> marginTop() {
        return this.marginTop;
    }

    public LssProperty<Float> marginRight() {
        return this.marginRight;
    }

    public LssProperty<Float> marginBottom() {
        return this.marginBottom;
    }

    public LssProperty<Float> paddingLeft() {
        return this.paddingLeft;
    }

    public LssProperty<Float> paddingTop() {
        return this.paddingTop;
    }

    public LssProperty<Float> paddingRight() {
        return this.paddingRight;
    }

    public LssProperty<Float> paddingBottom() {
        return this.paddingBottom;
    }

    public LssProperty<Float> widthPrecision() {
        return this.widthPrecision;
    }

    public LssProperty<Float> heightPrecision() {
        return this.heightPrecision;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<PriorityLayer> priorityLayer() {
        return this.priorityLayer;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<WidgetAlignment> alignmentX() {
        return this.alignmentX;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<WidgetAlignment> alignmentY() {
        return this.alignmentY;
    }

    public LssProperty<Float> translateX() {
        return this.translateX;
    }

    public LssProperty<Float> translateY() {
        return this.translateY;
    }

    public LssProperty<Float> zIndex() {
        return this.zIndex;
    }

    public LssProperty<Float> scaleX() {
        return this.scaleX;
    }

    public LssProperty<Float> scaleY() {
        return this.scaleY;
    }

    public LssProperty<Float> opacity() {
        return this.opacity;
    }

    public LssProperty<Float> mouseRenderDistance() {
        return this.mouseRenderDistance;
    }

    public void setPressOnRelease(boolean pressOnRelease) {
        this.pressOnRelease = pressOnRelease;
    }

    public LssProperty<Boolean> stencilX() {
        return this.stencilX;
    }

    public LssProperty<Boolean> stencilY() {
        return this.stencilY;
    }

    public void setStencil(boolean stencil) {
        this.stencilX.set(Boolean.valueOf(stencil));
        this.stencilY.set(Boolean.valueOf(stencil));
    }

    public LssProperty<Boolean> stencilTranslation() {
        return this.stencilTranslation;
    }

    public LssProperty<Boolean> writeToStencilBuffer() {
        return this.writeToStencilBuffer;
    }

    public LssProperty<Boolean> useFloatingPointPosition() {
        return this.useFloatingPointPosition;
    }

    public LssProperty<Boolean> alwaysFocused() {
        return this.alwaysFocused;
    }

    public LssProperty<Boolean> interactableOutside() {
        return this.interactableOutside;
    }

    public LssProperty<Long> destroyDelay() {
        return this.destroyDelay;
    }

    public LssProperty<Boolean> fitOuter() {
        return this.fitOuter;
    }

    public LssProperty<Boolean> distinct() {
        return this.distinct;
    }

    public LssProperty<Boolean> cancelParentHoverComponent() {
        return this.cancelParentHoverComponent;
    }

    public LssProperty<Boolean> renderChildren() {
        return this.renderChildren;
    }

    public LssProperty<Integer> backgroundColor() {
        return this.backgroundColor;
    }

    public LssProperty<Long> backgroundColorTransitionDuration() {
        return this.backgroundColorTransitionDuration;
    }

    public LssProperty<Boolean> backgroundAlwaysDirt() {
        return this.backgroundAlwaysDirt;
    }

    public LssProperty<Integer> backgroundDirtShift() {
        return this.backgroundDirtShift;
    }

    public LssProperty<DirtBackgroundType> backgroundDirtType() {
        return this.backgroundDirtType;
    }

    public LssProperty<Float> fontWeight() {
        return this.fontWeight;
    }

    public LssProperty<CubicBezier> animationTimingFunction() {
        return this.animationTimingFunction;
    }

    public LssProperty<Integer> animationIterationCount() {
        return this.animationIterationCount;
    }

    public LssProperty<Boolean> animationDirectionAlternate() {
        return this.animationDirectionAlternate;
    }

    public LssProperty<Filter[]> filter() {
        return this.filter;
    }

    public LssProperty<Integer> hoverBoxDelay() {
        return this.hoverBoxDelay;
    }

    public LssProperty<Boolean> clearDepth() {
        return this.clearDepth;
    }

    public LssProperty<Boolean> forceVanillaFont() {
        return this.forceVanillaFont;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getFontWeight() {
        return this.fontWeight.get().floatValue();
    }

    public boolean isHoverComponentRendered() {
        long timestamp;
        if (this.hoverComponent == null || !isHovered()) {
            return false;
        }
        long hoverTimestamp = getAttributeStateToggleTimestamp(AttributeState.HOVER);
        long draggingTimestamp = 0;
        if (isAttributeStateEnabled(AttributeState.ENABLED)) {
            draggingTimestamp = getAttributeStateToggleTimestamp(AttributeState.DRAGGING);
        }
        if (draggingTimestamp > hoverTimestamp) {
            timestamp = draggingTimestamp;
        } else {
            timestamp = hoverTimestamp;
        }
        return timestamp + ((long) this.hoverBoxDelay.get().intValue()) < TimeUtil.getMillis();
    }

    protected RenderableComponent getHoverComponent() {
        return this.hoverComponent;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isOutOfBounds() {
        return this.outOfBounds;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
        if (outOfBounds) {
            this.attributeStates.remove(AttributeState.HOVER);
        }
        for (T child : this.children) {
            child.setOutOfBounds(outOfBounds);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Collection<AttributeState> getAttributeStates() {
        return this.attributeStates.keySet();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public long getAttributeStateToggleTimestamp(AttributeState state) {
        AttributeStateValue value;
        if (state.isStaticState()) {
            value = this.staticAttributeStates.get(state);
        } else {
            value = this.attributeStates.get(state);
        }
        return value != null ? value.lastUpdate : this.widgetCreateTimestamp;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isAttributeStateEnabled(AttributeState state) {
        AttributeStateValue value;
        if (state.isStaticState()) {
            value = this.staticAttributeStates.get(state);
        } else {
            value = this.attributeStates.get(state);
        }
        return value != null && value.enabled;
    }

    protected void setStaticAttributeState(AttributeState state) {
        if (!state.isStaticState()) {
            throw new IllegalArgumentException("AttributeState \"" + String.valueOf(state) + "\" is not static");
        }
        this.staticAttributeStates.put(state, new AttributeStateValue(true));
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setAttributeState(AttributeState state, boolean enabled) {
        boolean changed;
        if (state.isStaticState()) {
            throw new IllegalArgumentException("setAttributeState() may only be used with states that are not static (state.isStaticState())");
        }
        AttributeStateValue value = this.attributeStates.get(state);
        if (value == null) {
            this.attributeStates.put(state, new AttributeStateValue(enabled));
            changed = enabled;
        } else {
            changed = value.setEnabled(enabled);
        }
        if (changed) {
            onAttributeStateChanged(state, enabled);
        }
    }

    public WidgetReference displayInOverlay(Widget widget) {
        return displayInOverlay(getStyleSheets(), widget);
    }

    public WidgetReference displayInOverlay(List<StyleSheet> styles) {
        return displayInOverlay(styles, this);
    }

    public WidgetReference displayInOverlay(List<StyleSheet> styles, Widget widget) {
        Parent root = getRoot();
        return this.labyAPI.screenOverlayHandler().displayInOverlay(root instanceof LabyScreen ? (LabyScreen) root : null, styles, widget);
    }

    public Theme getTheme() {
        return this.labyAPI.themeService().currentTheme();
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public LssVariableHolder getParentVariableHolder() {
        if (this.parent instanceof LssVariableHolder) {
            return (LssVariableHolder) this.parent;
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public Map<String, LssVariable> getLssVariables() {
        return this.lssVariables;
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void updateLssVariable(LssVariable variable) {
        super.updateLssVariable(variable);
        for (T child : this.children) {
            child.updateLssVariable(variable);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void forceUpdateLssVariable(LssVariable variable) {
        super.forceUpdateLssVariable(variable);
        for (T child : this.children) {
            child.forceUpdateLssVariable(variable);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean shouldHandleEscape() {
        for (T child : this.children) {
            if (child.shouldHandleEscape()) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasContextMenu() {
        return ((this.contextMenu == null || this.contextMenu.entries().isEmpty()) && this.contextMenuFactory == null) ? false : true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    @Nullable
    public DirectPropertyValueAccessor getDirectPropertyValueAccessor() {
        return this.widgetMetadata.propertyAccessor();
    }

    @Nullable
    public ContextMenu getContextMenu() {
        callContextMenuFactory();
        return this.contextMenu;
    }

    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }

    public ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        this.contextMenu = contextMenu;
        return contextMenu;
    }

    public void createContextMenuLazy(Consumer<ContextMenu> factory) {
        this.contextMenuFactory = factory;
    }

    private void callContextMenuFactory() {
        if (this.contextMenu != null || this.contextMenuFactory == null) {
            return;
        }
        this.contextMenuFactory.accept(createContextMenu());
        this.contextMenuFactory = null;
    }

    protected void updateContentBounds() {
        handleSizeAttributes();
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isPendingInitialize() {
        return this.pendingInitialize;
    }

    protected void allowCustomFont(Runnable runnable) {
        Parent root = getRoot();
        LabyScreen screen = root instanceof LabyScreen ? (LabyScreen) root : null;
        ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(screen);
        runnable.run();
        screenCustomFontStack.pop(screen);
    }

    protected boolean shouldCancelHoverComponent(Widget widget) {
        boolean hasHoverComponent;
        for (Widget child : widget.getChildren()) {
            if (shouldCancelHoverComponent(child)) {
                return true;
            }
        }
        if (widget instanceof WrappedWidget) {
            WrappedWidget wrappedWidget = (WrappedWidget) widget;
            widget = wrappedWidget.childWidget();
        }
        if (widget instanceof AbstractWidget) {
            AbstractWidget<?> abstractWidget = (AbstractWidget) widget;
            hasHoverComponent = abstractWidget.isHoverComponentRendered();
        } else {
            hasHoverComponent = widget.isHovered() && widget.hasHoverComponent();
        }
        return hasHoverComponent && widget.isCancelParentHoverComponent();
    }

    public WidgetRendererContext widgetRendererContext() {
        return this.widgetRendererContext;
    }

    public WidgetTransformer widgetTransformer() {
        return this.widgetTransformer;
    }

    @ApiStatus.Experimental
    protected int getLastBoundsUpdateFrame() {
        return this.lastBoundsUpdateFrame;
    }

    @ApiStatus.Experimental
    protected boolean didBoundsChangeThisFrame() {
        return this.lastBoundsUpdateFrame == FRAME_TIMER.getFrame();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/AbstractWidget$AttributeStateValue.class */
    private static class AttributeStateValue {
        private boolean enabled;
        private long lastUpdate = TimeUtil.getMillis();

        public AttributeStateValue(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean setEnabled(boolean enabled) {
            if (this.enabled != enabled) {
                this.enabled = enabled;
                this.lastUpdate = TimeUtil.getMillis();
                return true;
            }
            return false;
        }
    }

    @Deprecated
    public LssProperty<Integer> backgroundDirtBrightness() {
        return this.backgroundDirtBrightness;
    }

    @Deprecated
    @NotNull
    public ContextMenu contextMenu() {
        ContextMenu contextMenu = getContextMenu();
        if (contextMenu == null) {
            this.contextMenu = new ContextMenu();
        }
        return this.contextMenu;
    }

    private void prepareWidgetRendering(ScreenContext context) {
        this.labyAPI.gfxRenderPipeline().overlappingTranslator().translate(this, context.stack());
        if (renderer().get() == null) {
            initializeThemeRenderer();
        }
        if (isLazy()) {
            updateLazy();
        }
        bounds().checkForChanges();
        animation().apply(context.getTickDelta());
    }

    public void setHoverCursor(CursorType type) {
        this.hoverCursorType = type;
    }

    public void setHoverCursor(CursorType type, boolean forceActive) {
        setHoverCursor(type);
        this.hoverCursorForceActive = forceActive;
    }

    private void handleCursor(ScreenContext context) {
        if (isHovered() && this.labyAPI.minecraft().minecraftWindow().allowCursorChange()) {
            context.requestCursor((this.hoverCursorForceActive || isAttributeStateEnabled(AttributeState.ENABLED)) ? this.hoverCursorType : CursorType.DEFAULT);
        }
    }
}
