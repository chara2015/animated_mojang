package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.core.labynet.insight.controller.InsightWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotTimelineWidget.class */
@AutoWidget
public class ScreenshotTimelineWidget extends AbstractWidget<Widget> {
    private static final ModifyReason UPDATE_LAYOUT_REASON = ModifyReason.of("updateLayout");
    private final ScreenshotTimelineHolder holder;
    private ComponentWidget titleWidget;
    private ScreenshotContainerWidget container;
    private ScreenshotScrollbarWidget scrollbarWidget;
    private final Filter filter = new Filter();
    private float scrollOffset = 0.0f;
    private int tilesPerRow = 10;
    private boolean postInitialized = false;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotTimelineWidget$ScreenshotTimelineHolder.class */
    public interface ScreenshotTimelineHolder {
        ScreenshotBrowser browser();

        void open(Screenshot screenshot);

        Screenshot getOpenScreenshot();
    }

    public ScreenshotTimelineWidget(ScreenshotTimelineHolder holder) {
        this.holder = holder;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.postInitialized = false;
        this.titleWidget = ComponentWidget.empty();
        this.titleWidget.addId("title");
        addChild(this.titleWidget);
        this.container = new ScreenshotContainerWidget(this);
        this.container.addId("container");
        ScreenshotBrowser browser = this.holder.browser();
        Collection<ScreenshotSection> sections = browser.getSectionMap().values();
        ScreenshotSection[] screenshotSectionArr = (ScreenshotSection[]) sections.toArray(new ScreenshotSection[0]);
        int length = screenshotSectionArr.length;
        for (int i = 0; i < length; i++) {
            ScreenshotSection section = screenshotSectionArr[i];
            if (this.filter.matches(section)) {
                boolean showHeader = browser.getLatestSection() != section;
                this.container.addChild(new ScreenshotSectionWidget(this.container, section, this.filter, showHeader));
            }
        }
        addChild(this.container);
        this.scrollbarWidget = new ScreenshotScrollbarWidget(this);
        this.scrollbarWidget.addId("scrollbar");
        addChild(this.scrollbarWidget);
        updateTitle();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.postInitialized = true;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (KeyHandler.isLeftControlDown()) {
            updateTileCount((int) (((double) this.tilesPerRow) - scrollDelta));
        } else {
            this.scrollOffset -= (float) (scrollDelta * 45.0d);
            updateScrollOffset();
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (KeyHandler.isLeftControlDown()) {
            if (key == Key.SLASH || key == Key.SUBTRACT) {
                int i = this.tilesPerRow + 1;
                this.tilesPerRow = i;
                updateTileCount(i);
                return true;
            }
            if (key == Key.R_BRACKET || key == Key.ADD) {
                int i2 = this.tilesPerRow - 1;
                this.tilesPerRow = i2;
                updateTileCount(i2);
                return true;
            }
            if (key == Key.NUM0 || key == Key.NUMPAD0) {
                updateTileCount(10);
                return true;
            }
        }
        return super.keyPressed(key, type);
    }

    public void updateTileCount(int tilesPerRow) {
        this.tilesPerRow = tilesPerRow;
        float previousScrollOffset = this.scrollOffset - 10.0f;
        ScreenshotSectionWidget widget = this.container.getSectionAtOffset(previousScrollOffset);
        ScreenshotSection section = widget == null ? null : widget.section();
        this.tilesPerRow = MathHelper.clamp(tilesPerRow, 5, 30);
        this.container.purgeCache();
        if (section != null) {
            this.scrollOffset = this.container.getOffsetOfSection(section);
            updateScrollOffset();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.postInitialized) {
            updateScrollOffset();
        }
    }

    private void updateScrollOffset() {
        float maxScrollOffset = getMaxScrollOffset();
        this.scrollOffset = MathHelper.clamp(this.scrollOffset, 0.0f, maxScrollOffset);
        this.container.updateLayout(false);
        this.scrollbarWidget.updateAttention();
        updateTitle();
    }

    private void updateTitle() {
        ScreenshotSectionWidget currentSectionWidget;
        if (this.container != null && (currentSectionWidget = this.container.getCurrentSectionWidget()) != null) {
            this.titleWidget.setComponent(currentSectionWidget.title());
        }
    }

    public void updateAllSections() {
        if (this.container == null) {
            return;
        }
        for (ScreenshotSectionWidget section : this.container.getGenericChildren()) {
            section.updateFilteredEntries();
        }
    }

    public void updateSection(ScreenshotSection section) {
        ScreenshotSectionWidget widget;
        if (this.container != null && (widget = this.container.getSectionWidget(section)) != null) {
            widget.updateFilteredEntries();
        }
    }

    public void addSection(ScreenshotSection section) {
        if (this.container == null || !this.filter.matches(section)) {
            return;
        }
        boolean showHeader = this.holder.browser().getLatestSection() != section;
        this.container.addChildInitialized(new ScreenshotSectionWidget(this.container, section, this.filter, showHeader));
        updateTitle();
    }

    public void removeSection(ScreenshotSection section) {
        if (this.container == null) {
            return;
        }
        ScreenshotSectionWidget widget = this.container.getSectionWidget(section);
        if (widget != null) {
            this.container.removeChild(widget);
        }
        updateScrollOffset();
        updateTitle();
    }

    public void updateQuery(String text) {
        this.filter.updateQuery(text);
        this.scrollOffset = 0.0f;
        reInitialize();
        this.container.updateLayout(true);
    }

    public boolean isEmpty() {
        return getFilteredScreenshots().isEmpty();
    }

    public List<Screenshot> getFilteredScreenshots() {
        List<Screenshot> allScreenshots = this.holder.browser().getScreenshots();
        if (!this.filter.hasQuery()) {
            return allScreenshots;
        }
        List<Screenshot> filteredScreenshots = new ArrayList<>();
        for (Screenshot screenshot : (Screenshot[]) allScreenshots.toArray(new Screenshot[0])) {
            if (this.filter.matches(screenshot)) {
                filteredScreenshots.add(screenshot);
            }
        }
        return filteredScreenshots;
    }

    public float getMaxScrollOffset() {
        return Math.max(0.0f, (bounds().getOffset(BoundsType.OUTER, OffsetSide.TOP) + this.container.getContentHeight(BoundsType.OUTER)) - bounds().getHeight(BoundsType.INNER));
    }

    public Screenshot.QualityType getQuality() {
        return this.tilesPerRow < 8 ? Screenshot.QualityType.MEDIUM : Screenshot.QualityType.LOW;
    }

    public Filter screenshotFilter() {
        return this.filter;
    }

    public ScreenshotTimelineHolder holder() {
        return this.holder;
    }

    public ScreenshotContainerWidget getContainer() {
        return this.container;
    }

    public void setScrollOffset(float offset) {
        this.scrollOffset = offset;
        updateScrollOffset();
        this.container.updateLayout(false);
        updateTitle();
    }

    public float getScrollOffset() {
        return this.scrollOffset;
    }

    public int getTilesPerRow() {
        return this.tilesPerRow;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotTimelineWidget$Filter.class */
    public static class Filter {
        private String query;

        protected Filter() {
        }

        public void updateQuery(String query) {
            this.query = query;
        }

        public String getQuery() {
            return this.query;
        }

        public boolean matches(Screenshot screenshot) {
            if (this.query == null || this.query.isEmpty()) {
                return true;
            }
            String query = this.query.toLowerCase(Locale.ROOT);
            if (screenshot.getMeta().getIdentifier().toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
            String json = screenshot.getMeta().get(InsightWriter.KEY);
            if (json != null) {
                return json.toLowerCase(Locale.ROOT).contains(query);
            }
            String legacyJson = screenshot.getMeta().get(InsightWriter.LEGACY_KEY);
            return legacyJson != null && legacyJson.toLowerCase(Locale.ROOT).contains(query);
        }

        public boolean matches(ScreenshotSection section) {
            for (Screenshot screenshot : (Screenshot[]) section.getScreenshots().toArray(new Screenshot[0])) {
                if (matches(screenshot)) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasQuery() {
            return (this.query == null || this.query.isEmpty()) ? false : true;
        }
    }
}
