package net.labymod.core.test.widget;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/widget/LazyGridWidgetTestActivity.class */
@Link("test/lazy-grid-widget.lss")
@AutoActivity
public class LazyGridWidgetTestActivity extends TestActivity {
    private static final ListSession<DummyWidget> GRID_SESSION = new ListSession<>();
    private static final ListSession<DummyWidget> LAZY_GRID_SESSION = new ListSession<>();
    private static int gridSize = 150;
    private static int lazyGridSize = 150;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget contentWidget = new FlexibleContentWidget();
        contentWidget.addId("content-wrapper");
        contentWidget.addFlexibleContent(createWrapper("Lazy Grid Widget", () -> {
            return new LazyGridWidget(GRID_SESSION, gridSize, i -> {
                DummyWidget dummyWidget = new DummyWidget(i + 1);
                return dummyWidget;
            });
        }, (gridWidget, buttonWrapper) -> {
            ButtonWidget loadMoreButton = ButtonWidget.text("Load more");
            loadMoreButton.setPressable(() -> {
                gridSize += 50;
                gridWidget.totalEntries(gridSize);
            });
            buttonWrapper.addEntry(loadMoreButton);
            ButtonWidget resetSizeButton = ButtonWidget.text("Reset Size");
            resetSizeButton.setPressable(() -> {
                gridSize = 150;
                gridWidget.totalEntries(gridSize);
            });
            buttonWrapper.addEntry(resetSizeButton);
        }));
        contentWidget.addFlexibleContent(createWrapper("Lazy Grid Feed Widget", () -> {
            return new LazyGridFeedWidget(LAZY_GRID_SESSION, lazyGridSize, (i, feed) -> {
                Task.builder(() -> {
                    lazyGridSize += 50;
                    feed.setTotalEntries(lazyGridSize);
                }).delay(1L, TimeUnit.SECONDS).build().executeOnRenderThread();
            }, i2 -> {
                if (i2 < lazyGridSize) {
                    DummyWidget dummyWidget = new DummyWidget(i2 + 1);
                    return dummyWidget;
                }
                return null;
            });
        }, (gridWidget2, buttonWrapper2) -> {
            ButtonWidget loadMoreButton = ButtonWidget.text("Load more Sync");
            loadMoreButton.setPressable(() -> {
                lazyGridSize += 50;
                gridWidget2.totalEntries(lazyGridSize);
            });
            buttonWrapper2.addEntry(loadMoreButton);
            ButtonWidget loadMoreAsyncButton = ButtonWidget.text("Load more Async");
            loadMoreAsyncButton.setPressable(() -> {
                gridWidget2.fetching();
                Task.builder(() -> {
                    lazyGridSize += 50;
                    gridWidget2.totalEntries(lazyGridSize);
                }).delay(1L, TimeUnit.SECONDS).build().executeOnRenderThread();
                gridWidget2.totalEntries(lazyGridSize);
            });
            buttonWrapper2.addEntry(loadMoreAsyncButton);
            ButtonWidget doneButton = ButtonWidget.text("Done");
            doneButton.setPressable(() -> {
                gridWidget2.done(!gridWidget2.isDone());
            });
            buttonWrapper2.addEntry(doneButton);
            ButtonWidget resetSizeButton = ButtonWidget.text("Reset Size");
            resetSizeButton.setPressable(() -> {
                lazyGridSize = 0;
                gridWidget2.totalEntries(lazyGridSize);
            });
            buttonWrapper2.addEntry(resetSizeButton);
        }));
        ((Document) this.document).addChild(contentWidget);
    }

    private <T extends LazyGridWidget<?>> Widget createWrapper(String title, Supplier<T> widgetSupplier, BiConsumer<T, HorizontalListWidget> buttonConsumer) {
        FlexibleContentWidget wrapper = new FlexibleContentWidget();
        wrapper.addId("grid-wrapper");
        wrapper.addContent(ComponentWidget.text(title));
        T grid = widgetSupplier.get();
        grid.addId("grid");
        wrapper.addFlexibleContent(new ScrollWidget(grid));
        HorizontalListWidget buttonWrapper = new HorizontalListWidget();
        buttonWrapper.addId("button-wrapper");
        buttonConsumer.accept(grid, buttonWrapper);
        wrapper.addContent(buttonWrapper);
        return wrapper;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/widget/LazyGridWidgetTestActivity$DummyWidget.class */
    @AutoWidget
    public static class DummyWidget extends AbstractWidget<Widget> {
        private final int index;

        public DummyWidget(int index) {
            this.index = index;
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
        public void initialize(Parent parent) {
            super.initialize(parent);
            ComponentWidget componentWidget = ComponentWidget.text(this.index);
            addChild(componentWidget);
        }
    }
}
