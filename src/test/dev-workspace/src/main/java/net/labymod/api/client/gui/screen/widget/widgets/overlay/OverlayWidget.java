package net.labymod.api.client.gui.screen.widget.widgets.overlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.event.Phase;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/overlay/OverlayWidget.class */
public abstract class OverlayWidget extends AbstractWidget<Widget> {
    protected static final ModifyReason OVERLAY_POSITION = ModifyReason.of("overlayPosition");
    private final List<StyleSheet> overlayStyleSheets;
    private final WidgetReference.ClickRemoveStrategy clickRemoveStrategy;
    private final WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy;
    private final String identifier;
    private final boolean forwardStyles;
    private final LssProperty<OverlayPositionStrategy> strategyX;
    private final LssProperty<OverlayPositionStrategy> strategyY;
    private final LssProperty<Boolean> playInteractSound;
    private WidgetReference reference;

    @NotNull
    protected abstract Parent content();

    protected OverlayWidget(@Nullable String identifier, @NotNull WidgetReference.ClickRemoveStrategy clickRemoveStrategy, @NotNull WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy, boolean forwardStyles) {
        this.overlayStyleSheets = new ArrayList();
        this.strategyX = new LssProperty<>(OverlayPositionStrategy.MAXX_MAXX);
        this.strategyY = new LssProperty<>(OverlayPositionStrategy.MAXY_Y);
        this.playInteractSound = new LssProperty<>(true);
        Objects.requireNonNull(clickRemoveStrategy, "clickRemoveStrategy");
        Objects.requireNonNull(keyPressRemoveStrategy, "keyPressRemoveStrategy");
        this.clickRemoveStrategy = clickRemoveStrategy;
        this.keyPressRemoveStrategy = keyPressRemoveStrategy;
        this.identifier = identifier == null ? TextFormat.CAMEL_CASE.toDashCase(getTypeName()) : identifier;
        this.forwardStyles = forwardStyles;
        this.strategyX.addChangeListener((property, prev, next) -> {
            if (next.orientation() != Orientation.HORIZONTAL && next != OverlayPositionStrategy.CUSTOM) {
                property.set(prev);
                throw new IllegalStateException("Strategy " + String.valueOf(next) + " is not horizontal!");
            }
        });
        this.strategyY.addChangeListener((property2, prev2, next2) -> {
            if (next2.orientation() != Orientation.VERTICAL && next2 != OverlayPositionStrategy.CUSTOM) {
                property2.set(prev2);
                throw new IllegalStateException("Strategy " + String.valueOf(next2) + " is not vertical!");
            }
        });
    }

    protected OverlayWidget(@NotNull WidgetReference.ClickRemoveStrategy clickRemoveStrategy, @NotNull WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy, boolean forwardStyles) {
        this(null, clickRemoveStrategy, keyPressRemoveStrategy, forwardStyles);
    }

    protected OverlayWidget(@NotNull WidgetReference.ClickRemoveStrategy clickRemoveStrategy, boolean forwardStyles) {
        this(null, clickRemoveStrategy, WidgetReference.KeyPressRemoveStrategy.ALWAYS, forwardStyles);
    }

    protected OverlayWidget(@NotNull WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy, boolean forwardStyles) {
        this(null, WidgetReference.ClickRemoveStrategy.ALWAYS, keyPressRemoveStrategy, forwardStyles);
    }

    protected OverlayWidget(boolean forwardStyles) {
        this(null, WidgetReference.ClickRemoveStrategy.ALWAYS, WidgetReference.KeyPressRemoveStrategy.ALWAYS, forwardStyles);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        if (this.playInteractSound.get().booleanValue()) {
            this.labyAPI.minecraft().sounds().playButtonPress();
        }
        toggle();
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        if (this.initialized) {
            close();
        }
    }

    public boolean isOpen() {
        return this.reference != null && this.reference.isAlive();
    }

    public void toggle() {
        if (isOpen()) {
            close();
        } else {
            open();
        }
    }

    public void close() {
        if (isOpen()) {
            this.reference.remove();
            this.reference = null;
        }
    }

    public void open() {
        Widget stencilWrapper;
        Laby.references().linkMetaLoader().loadMeta(getClass(), this::addStyleSheet);
        Parent parent = content();
        if (parent instanceof Activity) {
            ScreenRendererWidget screenRendererWidget = new ScreenRendererWidget();
            screenRendererWidget.displayScreen((Activity) parent);
            stencilWrapper = screenRendererWidget;
        } else if (parent instanceof Widget) {
            OverlayContentWrapperWidget divWidget = new OverlayContentWrapperWidget();
            divWidget.addChild((Widget) parent);
            stencilWrapper = divWidget;
        } else {
            throw new IllegalStateException("Supplied value is not a widget or activity");
        }
        stencilWrapper.addId(this.identifier + "-overlay-wrapper");
        onOpen(Phase.PRE);
        this.reference = displayInOverlay(getOverlayStyleSheets(), stencilWrapper).addDestroyHandler(this::onClose).boundsUpdater((reference, bounds) -> {
            applyPosition(reference.widget(), bounds);
        });
        this.reference.clickRemoveStrategy(this.clickRemoveStrategy);
        this.reference.keyPressRemoveStrategy(this.keyPressRemoveStrategy);
        onOpen(Phase.POST);
    }

    public void addStyleSheet(String namespace, String link) {
        StyleSheet styleSheet = new LinkReference(namespace, "lss/" + link).loadStyleSheet();
        if (styleSheet == null) {
            throw new IllegalStateException("Could not load stylesheet " + link);
        }
        addStyleSheet(styleSheet);
    }

    public void addStyleSheet(String link) {
        addStyleSheet(Laby.labyAPI().getNamespace(this), link);
    }

    public void addStyleSheet(StyleSheet styleSheet) {
        if (!this.overlayStyleSheets.contains(styleSheet)) {
            this.overlayStyleSheets.add(styleSheet);
        }
    }

    public LssProperty<OverlayPositionStrategy> strategyX() {
        return this.strategyX;
    }

    public LssProperty<OverlayPositionStrategy> strategyY() {
        return this.strategyY;
    }

    public LssProperty<Boolean> playInteractSound() {
        return this.playInteractSound;
    }

    protected void onOpen(@NotNull Phase phase) {
    }

    protected void onClose() {
        if (!this.playInteractSound.get().booleanValue() || !this.labyAPI.minecraft().mouse().isInside(Bounds.absoluteBounds(this))) {
            return;
        }
        Laby.references().soundService().play(SoundType.BUTTON_CLICK);
    }

    protected boolean onPositionApply(OverlayPositionStrategy strategyX, OverlayPositionStrategy strategyY, float x, float y, Widget stencilWrapper, Rectangle outerOverlayRectangle, Bounds overlayBounds) {
        return true;
    }

    protected Rectangle absoluteBounds() {
        return Bounds.absoluteBounds(this, BoundsType.OUTER);
    }

    protected WidgetReference getOverlayReference() {
        return this.reference;
    }

    protected List<StyleSheet> getOverlayStyleSheets() {
        if (!this.forwardStyles) {
            return this.overlayStyleSheets;
        }
        List<StyleSheet> references = Lists.newArrayList();
        references.addAll(this.overlayStyleSheets);
        List<StyleSheet> widgetStyleSheets = getStyleSheets();
        for (StyleSheet widgetStyleSheet : widgetStyleSheets) {
            if (!references.contains(widgetStyleSheet)) {
                references.add(widgetStyleSheet);
            }
        }
        return references;
    }

    protected void applyPosition(Widget stencilWrapper, Bounds bounds) {
        Rectangle absoluteBounds = absoluteBounds();
        Bounds windowBounds = this.labyAPI.minecraft().minecraftWindow().absoluteBounds();
        Rectangle rectangle = bounds.rectangle(BoundsType.OUTER);
        OverlayPositionStrategy strategyX = this.strategyX.get();
        float x = -1.0f;
        float width = rectangle.getWidth();
        if (strategyX != OverlayPositionStrategy.CUSTOM) {
            float tempX = strategyX.position().get(width, absoluteBounds);
            if (!testPosition(tempX, width, windowBounds, Orientation.HORIZONTAL)) {
                Iterator<OverlayPositionStrategy> it = OverlayPositionStrategy.horizontalValues().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    OverlayPositionStrategy strategy = it.next();
                    if (strategy != strategyX) {
                        float tempX2 = strategy.position().get(width, absoluteBounds);
                        if (testPosition(tempX2, width, windowBounds, Orientation.HORIZONTAL)) {
                            strategyX = strategy;
                            x = tempX2;
                            break;
                        }
                    }
                }
                if (x == -1.0f) {
                    x = strategyX.position().get(width, absoluteBounds);
                }
            } else {
                x = tempX;
            }
        }
        OverlayPositionStrategy strategyY = this.strategyY.get();
        float y = -1.0f;
        float height = rectangle.getHeight();
        if (strategyY != OverlayPositionStrategy.CUSTOM) {
            float tempY = strategyY.position().get(height, absoluteBounds);
            if (!testPosition(tempY, height, windowBounds, Orientation.VERTICAL)) {
                Iterator<OverlayPositionStrategy> it2 = OverlayPositionStrategy.verticalValues().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    OverlayPositionStrategy strategy2 = it2.next();
                    if (strategy2 != strategyY) {
                        float tempY2 = strategy2.position().get(height, absoluteBounds);
                        if (testPosition(tempY2, height, windowBounds, Orientation.VERTICAL)) {
                            strategyY = strategy2;
                            y = tempY2;
                            break;
                        }
                    }
                }
                if (y == -1.0f) {
                    y = strategyY.position().get(height, absoluteBounds);
                }
            } else {
                y = tempY;
            }
        }
        if (onPositionApply(strategyX, strategyY, x, y, stencilWrapper, rectangle, bounds)) {
            bounds.setOuterPosition(x, y, OVERLAY_POSITION);
        }
    }

    private boolean testPosition(float value, float dimension, Rectangle window, Orientation orientation) {
        float min = orientation == Orientation.HORIZONTAL ? window.getX() : window.getY();
        float max = orientation == Orientation.HORIZONTAL ? window.getMaxX() : window.getMaxY();
        return value >= min && value + dimension <= max;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/overlay/OverlayWidget$OverlayContentWrapperWidget.class */
    @AutoWidget
    public static class OverlayContentWrapperWidget extends AbstractWidget<Widget> {
        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public float getContentHeight(BoundsType type) {
            List<Widget> children = getChildren();
            float height = 0.0f;
            for (Widget child : children) {
                height += child.bounds().getHeight(BoundsType.OUTER);
            }
            return height + bounds().getVerticalOffset(type);
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public float getContentWidth(BoundsType type) {
            List<Widget> children = getChildren();
            float height = 0.0f;
            for (Widget child : children) {
                height += child.bounds().getWidth(BoundsType.OUTER);
            }
            return height + bounds().getHorizontalOffset(type);
        }
    }
}
