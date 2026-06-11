package net.labymod.api.client.gui.screen.widget.overlay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/overlay/WidgetReference.class */
public class WidgetReference {
    private final WidgetScreenOverlay overlay;
    private final LabyScreen sourceScreen;
    private Widget widget;
    private final List<StyleSheet> styleSheets;
    private BiConsumer<WidgetReference, Bounds> boundsUpdater;
    private final Collection<Runnable> destroyHandlers = new ArrayList();
    private ClickRemoveStrategy clickRemoveStrategy = ClickRemoveStrategy.ALWAYS;
    private KeyPressRemoveStrategy keyPressRemoveStrategy = KeyPressRemoveStrategy.ALWAYS;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/overlay/WidgetReference$ClickRemoveStrategy.class */
    public enum ClickRemoveStrategy {
        ALWAYS,
        INSIDE,
        OUTSIDE,
        NEVER
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/overlay/WidgetReference$KeyPressRemoveStrategy.class */
    public enum KeyPressRemoveStrategy {
        ALWAYS,
        ESCAPE,
        NEVER
    }

    public WidgetReference(WidgetScreenOverlay overlay, LabyScreen sourceScreen, List<StyleSheet> styleSheets, Widget widget) {
        this.overlay = overlay;
        this.sourceScreen = sourceScreen;
        this.styleSheets = styleSheets;
        this.widget = widget;
    }

    protected void onPreRender() {
        if (this.boundsUpdater != null) {
            this.boundsUpdater.accept(this, this.widget.bounds());
        }
    }

    public void remove() {
        if (!isAlive()) {
            return;
        }
        this.overlay.destroy(this);
    }

    public WidgetReference boundsUpdater(BiConsumer<WidgetReference, Bounds> consumer) {
        this.boundsUpdater = consumer;
        if (consumer != null) {
            consumer.accept(this, this.widget.bounds());
        }
        return this;
    }

    public WidgetReference addDestroyHandler(Runnable handler) {
        this.destroyHandlers.add(handler);
        return this;
    }

    public WidgetReference clickRemoveStrategy(ClickRemoveStrategy strategy) {
        this.clickRemoveStrategy = strategy;
        return this;
    }

    public ClickRemoveStrategy clickRemoveStrategy() {
        return this.clickRemoveStrategy;
    }

    public WidgetReference keyPressRemoveStrategy(KeyPressRemoveStrategy strategy) {
        this.keyPressRemoveStrategy = strategy;
        return this;
    }

    public KeyPressRemoveStrategy keyPressRemoveStrategy() {
        return this.keyPressRemoveStrategy;
    }

    public boolean isAlive() {
        return this.overlay.getReferences().contains(this);
    }

    @Nullable
    public LabyScreen getSourceScreen() {
        return this.sourceScreen;
    }

    public Widget widget() {
        return this.widget;
    }

    public Collection<Runnable> destroyHandlers() {
        return this.destroyHandlers;
    }

    public List<StyleSheet> getStyleSheets() {
        return this.styleSheets;
    }

    public void updateWidget(Widget widget) {
        ThreadSafe.ensureRenderThread();
        this.overlay.document().removeChildImmediately(this.widget);
        this.overlay.document().addChildInitialized(widget);
        this.widget.dispose();
        this.widget.destroy();
        this.widget = widget;
    }

    @Deprecated
    public WidgetReference removeOnClick(boolean removeOnClick) {
        if (removeOnClick) {
            this.clickRemoveStrategy = ClickRemoveStrategy.ALWAYS;
        } else {
            this.clickRemoveStrategy = ClickRemoveStrategy.NEVER;
        }
        return this;
    }

    @Deprecated
    public WidgetReference removeOnOutsideClick(boolean removeOnOutsideClick) {
        if (removeOnOutsideClick) {
            this.clickRemoveStrategy = ClickRemoveStrategy.OUTSIDE;
        }
        return this;
    }

    @Deprecated
    public boolean isRemoveOnClick() {
        return this.clickRemoveStrategy == ClickRemoveStrategy.ALWAYS;
    }

    @Deprecated
    public boolean isRemoveOnOutsideClick() {
        return this.clickRemoveStrategy == ClickRemoveStrategy.OUTSIDE;
    }
}
