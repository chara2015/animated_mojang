package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotBrowser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/timeline/ScreenshotTileWidget.class */
@AutoWidget
public class ScreenshotTileWidget extends SimpleWidget {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm");
    private static final int OUTLINE_COLOR = ColorFormat.ARGB32.pack(0, 0, 0, 100);
    private static final int SELECTED_COLOR = ColorFormat.ARGB32.pack(255, 255, 255, 30);
    private static final int NAME_BACKGROUND_COLOR = ColorFormat.ARGB32.pack(0, 0, 0, 100);
    private final ScreenshotSectionWidget sectionWidget;
    private final Screenshot screenshot;
    private final ScreenshotTimelineWidget.ScreenshotTimelineHolder holder;
    private final ScreenshotBrowser.QueuedExecutor executor;
    private final RenderableComponent nameComponent;
    private ScreenshotBrowser.QueuedExecutor.Task task;
    private boolean isInFrame = false;

    public ScreenshotTileWidget(ScreenshotSectionWidget sectionWidget, Screenshot screenshot) {
        this.sectionWidget = sectionWidget;
        this.screenshot = screenshot;
        this.holder = this.sectionWidget.containerWidget().timelineWidget().holder();
        this.executor = this.holder.browser().getExecutor();
        long time = screenshot.getMeta().getTimestamp();
        String displayTime = FORMAT.format(new Date(time));
        this.nameComponent = RenderableComponent.of(Component.text(displayTime));
        setContextMenu(screenshot.getContextMenu());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        visible().set(Boolean.valueOf(this.isInFrame));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        updateVisibleState();
    }

    public void updateVisibleState() {
        Bounds window = this.parent.getParent().bounds();
        Bounds bounds = bounds();
        boolean visible = window.isOverlapping(bounds);
        if (visible().get().booleanValue() != visible) {
            LssProperty<Boolean> lssPropertyVisible = visible();
            this.isInFrame = visible;
            lssPropertyVisible.set(Boolean.valueOf(visible));
            if (visible) {
                if (!this.screenshot.isLoaded()) {
                    this.task = this.executor.queue(() -> {
                        try {
                            Screenshot.QualityType quality = this.sectionWidget.containerWidget().timelineWidget().getQuality();
                            Screenshot openScreenshot = this.holder.getOpenScreenshot();
                            if (Objects.equals(openScreenshot, this.screenshot)) {
                                quality = Screenshot.QualityType.RAW;
                            }
                            this.screenshot.load(quality);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return;
                }
                return;
            }
            unload();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        int tilesPerRow = this.sectionWidget.containerWidget().timelineWidget().getTilesPerRow();
        Rectangle rectangle = bounds().copy(BoundsType.INNER).shrink(0.5f);
        Rectangle outline = bounds();
        boolean selected = isHovered() || (getContextMenu() != null && getContextMenu().isOpen());
        ScreenCanvas renderState = context.canvas();
        renderState.submitRect(outline, OUTLINE_COLOR);
        Icon icon = this.screenshot.getIcon();
        if (this.screenshot.isLoaded() && icon != null) {
            renderState.submitIcon(icon, rectangle);
            if (selected) {
                renderState.submitRect(rectangle, SELECTED_COLOR);
            }
        } else {
            float animationOffset = bounds().getX() + bounds().getY();
            int brightness = (int) ((Math.sin(((TimeUtil.getMillis() % 100000000) / 500.0d) - (((double) animationOffset) / 200.0d)) * 20.0d) + 60.0d);
            renderState.submitRect(rectangle, ColorFormat.ARGB32.pack(brightness, brightness, brightness));
        }
        if (tilesPerRow <= 10 && selected) {
            renderState.submitAbsoluteRect(rectangle.getLeft(), rectangle.getBottom() - 8.0f, rectangle.getRight(), rectangle.getBottom(), NAME_BACKGROUND_COLOR);
            renderState.submitRenderableComponent(this.nameComponent, rectangle.getLeft() + 2.0f, rectangle.getBottom() - 6.0f, -1, 0.67f, 4);
        }
        if (tilesPerRow <= 20 && !this.screenshot.getMeta().getAttributes().isEmpty()) {
            renderState.submitIcon(Textures.SpriteCommon.LABYMOD, rectangle.getRight() - 8.0f, rectangle.getBottom() - 8.0f, 8.0f, 8.0f);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            this.sectionWidget.containerWidget().timelineWidget().holder().open(this.screenshot);
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    public void unload() {
        if (this.task != null) {
            this.task.cancel();
        }
        if (this.screenshot.isLoaded()) {
            this.screenshot.unload();
        }
    }

    public Screenshot getScreenshot() {
        return this.screenshot;
    }
}
