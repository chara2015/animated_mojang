package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotSectionWidget.class */
@AutoWidget
public class ScreenshotSectionWidget extends SimpleWidget {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM yyyy");
    private static final ModifyReason UPDATE_LAYOUT_REASON = ModifyReason.of("updateLayout");
    private final ScreenshotContainerWidget containerWidget;
    private final ScreenshotSection section;
    private final ScreenshotTimelineWidget.Filter filter;
    private final boolean showHeader;
    private final TextComponent titleComponent;
    private final RenderableComponent renderableTitleComponent;
    private DivWidget headerWidget;
    private boolean isInFrame = false;
    private Float cachedHeight = null;
    private final ScreenshotBrowser browser = LabyMod.references().screenshotBrowser();

    public ScreenshotSectionWidget(ScreenshotContainerWidget containerWidget, ScreenshotSection section, ScreenshotTimelineWidget.Filter filter, boolean showHeader) {
        this.containerWidget = containerWidget;
        this.section = section;
        this.filter = filter;
        this.showHeader = showHeader;
        this.titleComponent = Component.text(DATE_FORMAT.format(Long.valueOf(this.section.getTimestamp())));
        this.renderableTitleComponent = RenderableComponent.of(this.titleComponent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        visible().set(Boolean.valueOf(this.isInFrame));
        this.headerWidget = new DivWidget();
        this.headerWidget.addId("header");
        if (this.showHeader) {
            ComponentWidget titleWidget = ComponentWidget.component(title());
            titleWidget.addId("title");
            this.headerWidget.addChild(titleWidget);
        }
        addChild(this.headerWidget);
        if (this.isInFrame) {
            updateFilteredEntries();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
    }

    public void updateVisibleState() {
        Rectangle window = this.parent.bounds();
        Bounds sectionBounds = bounds();
        boolean visible = window.isOverlapping(sectionBounds);
        if (visible().get().booleanValue() != visible) {
            LssProperty<Boolean> lssPropertyVisible = visible();
            this.isInFrame = visible;
            lssPropertyVisible.set(Boolean.valueOf(visible));
            if (visible) {
                updateFilteredEntries();
                return;
            }
            for (T widget : this.children) {
                if (widget instanceof ScreenshotTileWidget) {
                    ((ScreenshotTileWidget) widget).unload();
                }
            }
            removeChildIf(child -> {
                return child instanceof ScreenshotTileWidget;
            });
        }
    }

    public void updateFilteredEntries() {
        List<Screenshot> currentList = new ArrayList<>();
        for (T widget : this.children) {
            if (widget instanceof ScreenshotTileWidget) {
                currentList.add(((ScreenshotTileWidget) widget).getScreenshot());
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Screenshot screenshot : (Screenshot[]) this.section.getScreenshots().toArray(new Screenshot[0])) {
            if (this.filter.matches(screenshot) && !currentList.remove(screenshot)) {
                arrayList.add(new ScreenshotTileWidget(this, screenshot));
            }
        }
        if (!currentList.isEmpty()) {
            removeChildIf(child -> {
                return (child instanceof ScreenshotTileWidget) && currentList.contains(((ScreenshotTileWidget) child).getScreenshot());
            });
        }
        if (this.initialized) {
            if (!arrayList.isEmpty()) {
                addChildrenInitialized(arrayList, true);
            }
        } else {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                addChild((Widget) it.next(), false);
            }
            sortChildren();
        }
        updateLayout(true);
    }

    public void updateLayout(boolean purgeCache) {
        updateVisibleState();
        Rectangle sectionBounds = bounds().rectangle(BoundsType.INNER);
        if (sectionBounds.getWidth() == 0.0f) {
            return;
        }
        float x = sectionBounds.getX();
        float y = sectionBounds.getY() + this.headerWidget.bounds().getHeight(BoundsType.OUTER);
        float maxHeight = 0.0f;
        int tilesPerRow = this.containerWidget.timelineWidget().getTilesPerRow();
        for (T widget : this.children) {
            if (widget instanceof ScreenshotTileWidget) {
                ScreenshotTileWidget screenshotWidget = (ScreenshotTileWidget) widget;
                float width = sectionBounds.getWidth() / tilesPerRow;
                float height = width / screenshotWidget.getScreenshot().getAspectRatio();
                if (x + width > sectionBounds.getRight() + 1.0f) {
                    x = sectionBounds.getX();
                    y += maxHeight;
                    maxHeight = 0.0f;
                }
                Bounds bounds = widget.bounds();
                bounds.setPosition(x, y, UPDATE_LAYOUT_REASON);
                bounds.setSize(width, height, UPDATE_LAYOUT_REASON);
                maxHeight = Math.max(maxHeight, height);
                x += width;
            }
        }
        if (purgeCache) {
            purgeCache();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        if (this.cachedHeight != null) {
            return this.cachedHeight.floatValue();
        }
        Rectangle sectionBounds = bounds().rectangle(BoundsType.INNER);
        if (sectionBounds.getWidth() == 0.0f) {
            return 0.0f;
        }
        float headerHeight = this.headerWidget.bounds().getHeight(BoundsType.OUTER);
        if (headerHeight == 0.0f) {
            return 0.0f;
        }
        float x = sectionBounds.getX();
        float y = sectionBounds.getY() + headerHeight;
        float maxHeight = 0.0f;
        int tilesPerRow = this.containerWidget.timelineWidget().getTilesPerRow();
        for (Screenshot screenshot : (Screenshot[]) this.section.getScreenshots().toArray(new Screenshot[0])) {
            if (this.filter.matches(screenshot)) {
                float width = sectionBounds.getWidth() / tilesPerRow;
                float height = width / screenshot.getAspectRatio();
                if (x + width > sectionBounds.getRight() + 1.0f) {
                    x = sectionBounds.getX();
                    y += maxHeight;
                    maxHeight = 0.0f;
                }
                maxHeight = Math.max(maxHeight, height);
                x += width;
            }
        }
        Float fValueOf = Float.valueOf((y + maxHeight) - sectionBounds.getY());
        this.cachedHeight = fValueOf;
        return fValueOf.floatValue();
    }

    public ScreenshotTileWidget getTileWidget(Screenshot screenshot) {
        for (T widget : this.children) {
            if (widget instanceof ScreenshotTileWidget) {
                ScreenshotTileWidget tileWidget = (ScreenshotTileWidget) widget;
                if (tileWidget.getScreenshot().equals(screenshot)) {
                    return tileWidget;
                }
            }
        }
        return null;
    }

    public void purgeCache() {
        this.cachedHeight = null;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public int getSortingValue() {
        return (int) ((-this.section.getTimestamp()) / 1000);
    }

    public int hashCode() {
        return Long.hashCode(this.section.getTimestamp());
    }

    public boolean equals(Object obj) {
        return (obj instanceof ScreenshotSectionWidget) && ((ScreenshotSectionWidget) obj).section.equals(this.section);
    }

    public Component title() {
        return this.titleComponent;
    }

    public RenderableComponent renderableTitleComponent() {
        return this.renderableTitleComponent;
    }

    public ScreenshotSection section() {
        return this.section;
    }

    public ScreenshotContainerWidget containerWidget() {
        return this.containerWidget;
    }

    public ScreenshotBrowser browser() {
        return this.browser;
    }
}
