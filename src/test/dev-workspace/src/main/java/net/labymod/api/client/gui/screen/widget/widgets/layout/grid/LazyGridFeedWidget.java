package net.labymod.api.client.gui.screen.widget.widgets.layout.grid;

import java.util.Iterator;
import java.util.function.IntFunction;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.util.Color;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.ModifyReason;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/grid/LazyGridFeedWidget.class */
@AutoWidget
public class LazyGridFeedWidget<T extends AbstractWidget<?>> extends LazyGridWidget<T> {
    private static final ModifyReason REASON = ModifyReason.of("LazyGridFeedWidget");
    private final LssProperty<Float> refreshRadius;
    private final LssProperty<Float> loadingTextGap;
    private final LssProperty<String> loadingText;
    private final LssProperty<Boolean> removeLoadingText;
    private final LssProperty<Integer> loadingColor;
    private final Feed feed;
    private boolean fetching;
    private boolean done;
    private boolean forceRemoveLoadingText;
    private final LazyGridFeedLoader loader;
    private int loaderCalledWithEntries;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/grid/LazyGridFeedWidget$Feed.class */
    public interface Feed {
        void setFetching();

        void setDone();

        void setTotalEntries(int i);

        void addEntries(int i);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/grid/LazyGridFeedWidget$LazyGridFeedLoader.class */
    public interface LazyGridFeedLoader {
        void initialize(int i, Feed feed);
    }

    public LazyGridFeedWidget(@NotNull ListSession<T> session, int initialEntries, @NotNull LazyGridFeedLoader loader, @Nullable IntFunction<T> widgetSupplier) {
        super(session, initialEntries);
        this.refreshRadius = new LssProperty<>(Float.valueOf(-1.0f));
        this.loadingTextGap = new LssProperty<>(Float.valueOf(10.0f));
        this.loadingText = new LssProperty<>("labymod.misc.loading");
        this.removeLoadingText = new LssProperty<>(false);
        this.loadingColor = new LssProperty<>(Integer.valueOf(Color.GRAY.get()));
        this.loaderCalledWithEntries = -1;
        widgetSupplier((IntFunction) widgetSupplier);
        this.loader = loader;
        this.loaderCalledWithEntries = initialEntries - 1;
        this.feed = new Feed() { // from class: net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget.1
            @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget.Feed
            public void setFetching() {
                LazyGridFeedWidget.this.fetching();
            }

            @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget.Feed
            public void setDone() {
                LazyGridFeedWidget.this.fetching();
            }

            @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget.Feed
            public void setTotalEntries(int totalEntries) {
                LazyGridFeedWidget.this.totalEntries(totalEntries);
            }

            @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget.Feed
            public void addEntries(int entries) {
                LazyGridFeedWidget.this.totalEntries(LazyGridFeedWidget.this.getTotalEntries() + entries);
            }
        };
    }

    public LazyGridFeedWidget(@NotNull ListSession<T> session, int initialEntries, @NotNull LazyGridFeedLoader loader) {
        this(session, initialEntries, loader, null);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget
    public LazyGridFeedWidget<T> widgetSupplier(@Nullable IntFunction<T> widgetSupplier) {
        if (widgetSupplier == null) {
            super.widgetSupplier((IntFunction) null);
            return this;
        }
        super.widgetSupplier((IntFunction) i -> {
            AbstractWidget abstractWidget = (AbstractWidget) widgetSupplier.apply(i);
            if (abstractWidget == null) {
                fetching();
            }
            return abstractWidget;
        });
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget
    protected float applyHeight(float height) {
        if (this.forceRemoveLoadingText || !this.fetching || this.done || this.loadingText.get() == null || this.removeLoadingText.get().booleanValue()) {
            removeChildIf(child -> {
                return child.hasId("grid-feed-fetching-component");
            });
            return super.applyHeight(height);
        }
        Widget loadingTextWidget = null;
        Iterator it = this.children.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Widget child2 = (Widget) it.next();
            if (child2.hasId("grid-feed-fetching-component")) {
                loadingTextWidget = child2;
                break;
            }
        }
        boolean createNew = loadingTextWidget == null;
        if (loadingTextWidget == null) {
            ComponentWidget component = ComponentWidget.i18n(this.loadingText.get());
            component.addId("grid-feed-fetching-component");
            component.textColor().set(this.loadingColor.get());
            component.alignmentX().set(WidgetAlignment.CENTER);
            loadingTextWidget = component;
        }
        float gap = this.loadingTextGap.get().floatValue();
        loadingTextWidget.bounds().setPosition(bounds().getCenterX(), height + (gap * 2.0f), REASON);
        if (createNew) {
            addChildInitialized(loadingTextWidget);
        }
        return super.applyHeight(height + loadingTextWidget.bounds().getHeight(BoundsType.OUTER) + (gap * 2.0f));
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget, net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget
    public void updateVisibility(ListWidget<?> listWidget, Parent parent) {
        if (this.fetching || this.done || !(parent instanceof ScrollWidget)) {
            super.updateVisibility(listWidget, parent);
            return;
        }
        Bounds bounds = bounds();
        Bounds parentBounds = parent.bounds();
        ListSession<?> session = ((ScrollWidget) parent).session();
        float height = bounds.getHeight();
        float radius = this.refreshRadius.isDefaultValue() ? getTileHeight() * 2.0f : this.refreshRadius.get().floatValue();
        if (height <= 0.0f || session.getScrollPositionY() >= (height - parentBounds.getHeight()) - radius) {
            fetching();
            if (this.fetching || this.done) {
                return;
            }
        }
        super.updateVisibility(listWidget, parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget
    public LazyGridFeedWidget<T> totalEntries(int totalEntries) {
        ThreadSafe.ensureRenderThread();
        if (getTotalEntries() == totalEntries) {
            return this;
        }
        if (totalEntries <= this.loaderCalledWithEntries) {
            this.loaderCalledWithEntries = totalEntries - 1;
        }
        fetching(false);
        super.totalEntries(totalEntries);
        return this;
    }

    public LazyGridFeedWidget<T> forceRemoveLoadingText() {
        return forceRemoveLoadingText(true);
    }

    public LazyGridFeedWidget<T> forceRemoveLoadingText(boolean forceRemoveLoadingText) {
        if (this.forceRemoveLoadingText == forceRemoveLoadingText) {
            return this;
        }
        this.forceRemoveLoadingText = forceRemoveLoadingText;
        updateNextRenderCall();
        return this;
    }

    public LazyGridFeedWidget<T> fetching() {
        return fetching(true);
    }

    public LazyGridFeedWidget<T> fetching(boolean fetching) {
        ThreadSafe.ensureRenderThread();
        if (this.fetching == fetching) {
            return this;
        }
        this.fetching = fetching;
        if (this.fetching) {
            callLoader();
        }
        updateNextRenderCall();
        return this;
    }

    private void callLoader() {
        int entry;
        ThreadSafe.ensureRenderThread();
        if (this.done || this.loaderCalledWithEntries >= (entry = getTotalEntries())) {
            return;
        }
        this.loaderCalledWithEntries = entry;
        this.loader.initialize(entry, this.feed);
    }

    public boolean isFetching() {
        return this.fetching;
    }

    public LazyGridFeedWidget<T> done() {
        return done(true);
    }

    public LazyGridFeedWidget<T> done(boolean done) {
        ThreadSafe.ensureRenderThread();
        if (this.done == done) {
            return this;
        }
        this.done = done;
        updateNextRenderCall();
        return this;
    }

    public boolean isDone() {
        return this.done;
    }

    public LssProperty<Float> refreshRadius() {
        return this.refreshRadius;
    }

    public LssProperty<Float> loadingTextGap() {
        return this.loadingTextGap;
    }

    public LssProperty<String> loadingText() {
        return this.loadingText;
    }

    public LssProperty<Integer> loadingColor() {
        return this.loadingColor;
    }

    public LssProperty<Boolean> removeLoadingText() {
        return this.removeLoadingText;
    }
}
