package net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ProgressBarWidget;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTileWidget;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ScreenshotViewerWidget;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.ScreenshotBrowserNotifier;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/screenshots/ScreenshotBrowserActivity.class */
@Link("activity/screenshot/screenshot-browser.lss")
@AutoActivity
public class ScreenshotBrowserActivity extends SimpleActivity implements ScreenshotBrowserNotifier, ScreenshotTimelineWidget.ScreenshotTimelineHolder, ScreenshotViewerWidget.ScreenshotViewerHolder {
    public static final ScreenshotBrowserActivity INSTANCE = new ScreenshotBrowserActivity();
    private final ScreenshotViewerWidget screenshotViewerWidget;
    private final ProgressBarWidget progressBarWidget;
    private final SliderWidget zoomSlider;
    private float indexProgress;
    private ComponentWidget statusWidget;
    private TextFieldWidget searchWidget;
    private int previousScreenshotAmount = 0;
    private final ScreenshotBrowser browser = LabyMod.references().screenshotBrowser();
    private ScreenshotBrowserNotifier.IndexState indexState = this.browser.getState();
    private final ScreenshotTimelineWidget timelineWidget = new ScreenshotTimelineWidget(this);

    public ScreenshotBrowserActivity() {
        this.timelineWidget.addId("timeline");
        this.screenshotViewerWidget = new ScreenshotViewerWidget(this);
        this.screenshotViewerWidget.addId("viewer");
        this.progressBarWidget = new ProgressBarWidget();
        this.progressBarWidget.addId("progress-bar");
        this.zoomSlider = new SliderWidget(1.0f, value -> {
            this.timelineWidget.updateTileCount((int) value);
        });
        this.zoomSlider.range(5.0f, 30.0f);
        this.zoomSlider.addId("zoom-slider");
        ((Document) this.document).addId("browser");
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
        this.browser.subscribe(this);
        int amount = this.browser.getScreenshotCount();
        if (amount != this.previousScreenshotAmount) {
            this.timelineWidget.updateAllSections();
            this.screenshotViewerWidget.onSectionChanged();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        this.browser.unsubscribe(this);
        this.previousScreenshotAmount = this.browser.getScreenshotCount();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.statusWidget = ComponentWidget.empty();
        this.statusWidget.addId("status");
        ((Document) this.document).addChild(this.statusWidget);
        DivWidget headerWidget = new DivWidget();
        headerWidget.addId("header-bar");
        HorizontalListWidget headerGroup = new HorizontalListWidget();
        headerGroup.addId("header-group");
        this.searchWidget = new TextFieldWidget();
        this.searchWidget.addId(FlintUrls.QUERY_SEARCH_PARAM);
        String query = this.timelineWidget.screenshotFilter().getQuery();
        if (query != null) {
            this.searchWidget.setText(query);
        }
        this.searchWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.setActionListener(() -> {
            this.timelineWidget.updateQuery(this.searchWidget.getText());
            updateStatus();
        });
        headerGroup.addEntry(this.searchWidget);
        ButtonWidget refreshButton = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        refreshButton.addId("refresh", "button");
        refreshButton.setHoverComponent(Component.translatable("labymod.ui.button.refresh", new Component[0]));
        refreshButton.setPressable(() -> {
            this.browser.refresh();
            updateStatus();
        });
        headerGroup.addEntry(refreshButton);
        ButtonWidget openFolderWidget = ButtonWidget.icon(Textures.SpriteCommon.EXPORT);
        openFolderWidget.addId("open-folder", "button");
        openFolderWidget.setHoverComponent(Component.translatable("labymod.activity.screenshotBrowser.openFolder", new Component[0]));
        openFolderWidget.setPressable(() -> {
            OperatingSystem.getPlatform().openFile(Constants.Files.SCREENSHOT_DIRECTORY.toFile());
        });
        headerGroup.addEntry(openFolderWidget);
        headerWidget.addChild(headerGroup);
        ((Document) this.document).addChild(headerWidget);
        DivWidget footerWidget = new DivWidget();
        footerWidget.addId("footer-bar");
        ComponentWidget zoomTitle = ComponentWidget.i18n("labymod.activity.screenshotBrowser.zoom");
        zoomTitle.addId("zoom-title");
        footerWidget.addChild(zoomTitle);
        footerWidget.addChild(this.zoomSlider);
        ButtonWidget buttonWidget = ButtonWidget.i18n("labymod.ui.button.done");
        buttonWidget.addId("done");
        buttonWidget.setPressListener(this::displayPreviousScreen);
        footerWidget.addChild(buttonWidget);
        ((Document) this.document).addChild(footerWidget);
        ((Document) this.document).addChild(this.timelineWidget);
        ((Document) this.document).addChild(this.screenshotViewerWidget);
        ((Document) this.document).addChild(this.progressBarWidget);
        updateStatus();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        float opacity = this.screenshotViewerWidget.isOpen() ? 0.0f : 1.0f;
        for (Widget child : ((Document) this.document).getChildren()) {
            if (!(child instanceof ScreenshotViewerWidget)) {
                ((AbstractWidget) child).opacity().set(Float.valueOf(opacity));
            }
        }
        this.zoomSlider.setValue(this.timelineWidget.getTilesPerRow());
        super.render(context);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget.ScreenshotTimelineHolder
    public void open(Screenshot screenshot) {
        this.screenshotViewerWidget.displayScreenshot(screenshot);
    }

    private void updateStatus() {
        boolean isEmpty = this.timelineWidget.isEmpty();
        if (isEmpty) {
            if (this.indexState != ScreenshotBrowserNotifier.IndexState.IDLE) {
                String key = String.format(Locale.ROOT, "labymod.activity.screenshotBrowser.status.state.%s", TextFormat.SNAKE_CASE.toCamelCase(this.indexState.name(), true));
                int progress = (int) (this.indexProgress * 100.0f);
                this.statusWidget.setComponent(Component.translatable(key, new Component[0]).args(Component.text(progress + "%")).color(NamedTextColor.GRAY));
            } else if (this.timelineWidget.screenshotFilter().hasQuery()) {
                this.statusWidget.setComponent(Component.translatable("labymod.activity.screenshotBrowser.status.noResults", new Component[0]).color(NamedTextColor.RED));
            } else {
                this.statusWidget.setComponent(Component.translatable("labymod.activity.screenshotBrowser.status.noScreenshots", new Component[0]).color(NamedTextColor.RED));
            }
        }
        this.statusWidget.setVisible(isEmpty);
        this.progressBarWidget.setVisible(this.indexState != ScreenshotBrowserNotifier.IndexState.IDLE);
        this.progressBarWidget.setProgress(this.indexProgress);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (this.screenshotViewerWidget.isOpen() && key == Key.ESCAPE) {
            this.screenshotViewerWidget.close();
            return true;
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.core.client.screenshot.ScreenshotBrowserNotifier
    public void onIndexProgress(ScreenshotBrowserNotifier.IndexState state, float progress) {
        this.indexState = state;
        this.indexProgress = progress;
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
    }

    @Override // net.labymod.core.client.screenshot.ScreenshotBrowserNotifier
    public void onSectionAdded(ScreenshotSection section) {
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
        this.timelineWidget.addSection(section);
        this.screenshotViewerWidget.onSectionChanged();
    }

    @Override // net.labymod.core.client.screenshot.ScreenshotBrowserNotifier
    public void onSectionChanged(ScreenshotSection section) {
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
        this.timelineWidget.updateSection(section);
        this.screenshotViewerWidget.onSectionChanged();
    }

    @Override // net.labymod.core.client.screenshot.ScreenshotBrowserNotifier
    public void onSectionRemoved(ScreenshotSection section) {
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
        this.timelineWidget.removeSection(section);
        this.screenshotViewerWidget.onSectionChanged();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget.ScreenshotTimelineHolder, net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ScreenshotViewerWidget.ScreenshotViewerHolder
    public ScreenshotBrowser browser() {
        return this.browser;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ScreenshotViewerWidget.ScreenshotViewerHolder
    public Rectangle getTileRectangleOf(Screenshot screenshot) {
        ScreenshotTileWidget tileWidget = this.timelineWidget.getContainer().getTileWidget(screenshot);
        if (tileWidget == null) {
            return null;
        }
        return tileWidget.bounds();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ScreenshotViewerWidget.ScreenshotViewerHolder
    public List<Screenshot> getScreenshots() {
        return this.timelineWidget.getFilteredScreenshots();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ScreenshotViewerWidget.ScreenshotViewerHolder
    public Screenshot.QualityType getQuality() {
        return this.timelineWidget.getQuality();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget.ScreenshotTimelineHolder
    public Screenshot getOpenScreenshot() {
        if (this.screenshotViewerWidget.isOpen()) {
            return this.screenshotViewerWidget.getScreenshot();
        }
        return null;
    }

    public static void openScreenshot(Path path) {
        ScreenshotBrowserActivity activity = INSTANCE;
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
        Screenshot screenshot = activity.browser().getScreenshot(path);
        if (screenshot == null) {
            return;
        }
        activity.open(screenshot);
    }
}
