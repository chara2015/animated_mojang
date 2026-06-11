package net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.DynamicTexture;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.Color;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/viewer/ScreenshotViewerWidget.class */
@AutoWidget
public class ScreenshotViewerWidget extends SimpleWidget {
    private static final long TRANSITION_DURATION = 200;
    private final ScreenshotViewerHolder holder;
    private final ComponentWidget metaWidget;
    private ButtonWidget nextButtonWidget;
    private ButtonWidget previousButtonWidget;
    private DivWidget canvasWidget;
    private IconWidget iconWidget;
    private ComponentWidget statusWidget;
    private ButtonWidget cancelButtonWidget;
    private ButtonWidget trashButtonWidget;
    private ButtonWidget openButtonWidget;
    private ButtonWidget copyButtonWidget;
    private ButtonWidget shareButtonWidget;
    private ButtonWidget editButtonWidget;
    private SliderWidget paintThicknessSlider;
    private ColorPickerWidget paintColorPicker;
    private Rectangle transitionSourceRectangle;
    private int transitionTicks;
    private Screenshot screenshot;
    private IconWidget paintCanvasWidget;
    private DynamicTexture paintCanvasTexture;
    private boolean open;
    private boolean fileExists;
    private int dragStartX;
    private int dragStartY;
    private int lastMouseX;
    private int lastMouseY;
    private boolean dragging;
    private boolean editing;
    private static final Logging LOGGER = Logging.getLogger();
    private static final ModifyReason ANIMATE_CANVAS = ModifyReason.of("animateCanvas");
    private static final ResourceLocation PAINT_OVERLAY = ResourceLocation.create("labymod", "paint_overlay");
    private TransitionType transitionType = TransitionType.OPEN;
    private float lastPaintX = -1.0f;
    private float lastPaintY = -1.0f;
    private final ComponentWidget titleWidget = ComponentWidget.empty();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/viewer/ScreenshotViewerWidget$ScreenshotViewerHolder.class */
    public interface ScreenshotViewerHolder {
        ScreenshotBrowser browser();

        Rectangle getTileRectangleOf(Screenshot screenshot);

        List<Screenshot> getScreenshots();

        Screenshot.QualityType getQuality();
    }

    public ScreenshotViewerWidget(ScreenshotViewerHolder holder) {
        this.holder = holder;
        this.titleWidget.addId("title");
        this.metaWidget = ComponentWidget.empty();
        this.metaWidget.addId("meta");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.canvasWidget = new DivWidget();
        this.canvasWidget.addId("canvas");
        this.iconWidget = new IconWidget(null);
        this.iconWidget.addId("screenshot");
        this.canvasWidget.addChild(this.iconWidget);
        this.paintCanvasWidget = new IconWidget(null);
        this.paintCanvasWidget.addId("paint-canvas");
        this.canvasWidget.addChild(this.paintCanvasWidget);
        addChild(this.canvasWidget);
        this.statusWidget = ComponentWidget.empty();
        this.statusWidget.addId("loading");
        addChild(this.statusWidget);
        ButtonWidget closeButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        closeButtonWidget.addId("close");
        closeButtonWidget.setPressable(this::close);
        addChild(closeButtonWidget);
        HorizontalListWidget menuWidget = new HorizontalListWidget();
        menuWidget.addId("menu");
        this.cancelButtonWidget = ButtonWidget.i18n("labymod.ui.button.cancel");
        this.cancelButtonWidget.setPressable(this::abortEditing);
        this.cancelButtonWidget.setVisible(false);
        menuWidget.addEntry(this.cancelButtonWidget);
        this.trashButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.delete", new Component[0]), Textures.SpriteCommon.TRASH);
        this.trashButtonWidget.setPressable(() -> {
            PopupWidget.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.delete.warning", new Component[0]).arguments(Component.text(this.screenshot.getFileName()))).confirmCallback(() -> {
                if (this.screenshot.delete()) {
                    close();
                }
            }).build().displayInOverlay();
        });
        menuWidget.addEntry(this.trashButtonWidget);
        this.openButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.open", new Component[0]), Textures.SpriteCommon.OPEN_FILE);
        this.openButtonWidget.setPressable(() -> {
            this.screenshot.openInSystem();
        });
        menuWidget.addEntry(this.openButtonWidget);
        this.copyButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.copy", new Component[0]), Textures.SpriteCommon.LARGE_COPY);
        this.copyButtonWidget.setPressable(this::copyToClipboard);
        menuWidget.addEntry(this.copyButtonWidget);
        this.shareButtonWidget = ButtonWidget.component(Component.translatable("labymod.activity.screenshotBrowser.context.share", new Component[0]), Textures.SpriteCommon.SHARE);
        this.shareButtonWidget.setPressable(() -> {
            ContextMenu contextMenu = this.shareButtonWidget.getContextMenu();
            if (contextMenu != null) {
                contextMenu.open();
            }
        });
        menuWidget.addEntry(this.shareButtonWidget);
        this.editButtonWidget = ButtonWidget.component(Component.translatable("labymod.ui.button.edit", new Component[0]), Textures.SpriteCommon.PAINT);
        this.editButtonWidget.setPressable(this::startEditing);
        menuWidget.addEntry(this.editButtonWidget);
        this.nextButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.FORWARD_BUTTON);
        this.nextButtonWidget.addId("icon-button", "right");
        this.nextButtonWidget.setPressable(() -> {
            displayScreenshotAtOffset(1);
        });
        menuWidget.addEntry(this.nextButtonWidget);
        this.previousButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        this.previousButtonWidget.addId("icon-button", "right");
        this.previousButtonWidget.setPressable(() -> {
            displayScreenshotAtOffset(-1);
        });
        menuWidget.addEntry(this.previousButtonWidget);
        this.paintColorPicker = ColorPickerWidget.of(Color.RED);
        this.paintColorPicker.addId("paint-color", "right");
        this.paintColorPicker.setVisible(false);
        menuWidget.addEntry(this.paintColorPicker);
        this.paintThicknessSlider = new SliderWidget();
        this.paintThicknessSlider.range(1.0f, 10.0f);
        this.paintThicknessSlider.addId("paint-thickness", "right");
        this.paintThicknessSlider.setValue(3.0d);
        this.paintThicknessSlider.setVisible(false);
        menuWidget.addEntry(this.paintThicknessSlider);
        addChild(menuWidget);
        addChild(this.titleWidget);
        addChild(this.metaWidget);
        if (this.screenshot != null && this.open) {
            displayScreenshot(this.screenshot);
        }
        this.dragging = false;
        if (this.editing) {
            abortEditing();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        float fAbs;
        MutableMouse mouse = context.mouse();
        this.lastMouseX = mouse.getX();
        this.lastMouseY = mouse.getY();
        boolean hasTransition = this.transitionSourceRectangle != null;
        int iConvertDeltaToMilliseconds = (int) (this.transitionTicks + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta()));
        this.transitionTicks = iConvertDeltaToMilliseconds;
        long transitionTimePassed = iConvertDeltaToMilliseconds;
        if ((isOpen() | (transitionTimePassed < TRANSITION_DURATION)) && hasTransition) {
            float progress = getTransitionProgressAt(transitionTimePassed);
            Rectangle rectangle = getRectangle(progress);
            opacity().set(Float.valueOf(this.transitionType.hasFading() ? progress : 1.0f));
            super.render(context);
            Icon icon = this.screenshot == null ? null : this.screenshot.getIcon();
            if (icon != null && this.screenshot.isLoaded()) {
                float padding = this.dragging ? 0.0f : 2.0f * progress;
                float height = rectangle.getHeight() - (padding * 2.0f);
                float width = height * icon.getAspectRatio();
                this.iconWidget.bounds().setPosition(rectangle.getX() + ((rectangle.getWidth() - width) / 2.0f), rectangle.getY() + padding, ANIMATE_CANVAS);
                this.iconWidget.bounds().setSize(width, height, ANIMATE_CANVAS);
                LssProperty<Float> lssPropertyOpacity = this.iconWidget.opacity();
                if (this.transitionType == TransitionType.SWIPE_LEFT || this.transitionType == TransitionType.SWIPE_RIGHT) {
                    fAbs = Math.abs(progress - 0.5f) * 2.0f;
                } else {
                    fAbs = 1.0f;
                }
                lssPropertyOpacity.set(Float.valueOf(fAbs));
                this.statusWidget.setVisible(!this.fileExists);
                this.paintCanvasWidget.bounds().set(this.iconWidget.bounds(), ANIMATE_CANVAS);
            } else {
                this.statusWidget.setVisible(true);
            }
            visible().set(true);
        } else {
            visible().set(false);
        }
        if (this.editing && this.paintCanvasWidget.isHovered()) {
            this.editButtonWidget.icon().get().render(context.stack(), mouse.getX(), mouse.getY() - 16, 16.0f);
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        GameImage image;
        if (isOpen()) {
            if (this.editing) {
                if (key == Key.ESCAPE) {
                    abortEditing();
                }
                if (key == Key.Y && (image = this.paintCanvasTexture.getImage()) != null) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        for (int y = 0; y < image.getHeight(); y++) {
                            image.setARGB(x, y, 0);
                        }
                    }
                    this.paintCanvasTexture.upload();
                    return true;
                }
                return true;
            }
            if (key == Key.ARROW_LEFT) {
                displayScreenshotAtOffset(-1);
                return true;
            }
            if (key == Key.ARROW_RIGHT) {
                displayScreenshotAtOffset(1);
                return true;
            }
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (!isOpen()) {
            return false;
        }
        if (this.canvasWidget.isHovered() && mouseButton == MouseButton.LEFT && !this.editing) {
            this.dragStartX = mouse.getX();
            this.dragStartY = mouse.getY();
            this.dragging = true;
            startTransition(TransitionType.DRAG);
            return true;
        }
        if (this.canvasWidget.isHovered() && this.editing) {
            GameImage image = this.paintCanvasTexture.getImage();
            if (image == null) {
                return true;
            }
            Bounds bounds = this.paintCanvasWidget.bounds();
            float x = ((mouse.getX() - bounds.getX()) / bounds.getWidth()) * image.getWidth();
            float y = ((mouse.getY() - bounds.getY()) / bounds.getHeight()) * image.getHeight();
            this.lastPaintX = x;
            this.lastPaintY = y;
            return true;
        }
        super.mouseClicked(mouse, mouseButton);
        return true;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (this.canvasWidget.isHovered() && this.editing) {
            GameImage image = this.paintCanvasTexture.getImage();
            if (image == null) {
                return true;
            }
            Bounds bounds = this.paintCanvasWidget.bounds();
            float x = ((mouse.getX() - bounds.getX()) / bounds.getWidth()) * image.getWidth();
            float y = ((mouse.getY() - bounds.getY()) / bounds.getHeight()) * image.getHeight();
            if (this.lastPaintX != -1.0f && this.lastPaintY != -1.0f) {
                int thickness = (int) this.paintThicknessSlider.getValue();
                Color color = this.paintColorPicker.value();
                paintLine(image, this.lastPaintX, this.lastPaintY, x, y, thickness, color.get());
                this.paintCanvasTexture.upload();
            }
            this.lastPaintX = x;
            this.lastPaintY = y;
            return true;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (this.dragging && !this.editing) {
            this.dragging = false;
            startTransition(TransitionType.DRAG);
            Bounds bounds = this.canvasWidget.bounds();
            float offsetX = mouse.getX() - this.dragStartX;
            float offsetY = mouse.getY() - this.dragStartY;
            float heightThreshold = bounds.getHeight() / 4.0f;
            if (Math.abs(offsetY) > heightThreshold) {
                close();
                startTransition(TransitionType.DRAG_CLOSE);
                return true;
            }
            if (offsetX < (-20.0f)) {
                boolean success = displayScreenshotAtOffset(1);
                if (success) {
                    startTransition(TransitionType.SWIPE_LEFT);
                    return true;
                }
                return true;
            }
            if (offsetX > 20.0f) {
                boolean success2 = displayScreenshotAtOffset(-1);
                if (success2) {
                    startTransition(TransitionType.SWIPE_RIGHT);
                    return true;
                }
                return true;
            }
        }
        if (this.editing && this.lastPaintX != -1.0f && this.lastPaintY != -1.0f) {
            GameImage image = this.paintCanvasTexture.getImage();
            if (image != null) {
                Bounds bounds2 = this.paintCanvasWidget.bounds();
                float x = ((mouse.getX() - bounds2.getX()) / bounds2.getWidth()) * image.getWidth();
                float y = ((mouse.getY() - bounds2.getY()) / bounds2.getHeight()) * image.getHeight();
                int thickness = (int) this.paintThicknessSlider.getValue();
                Color color = this.paintColorPicker.value();
                paintLine(image, this.lastPaintX, this.lastPaintY, x, y, thickness, color.get());
                this.paintCanvasTexture.upload();
            }
            this.lastPaintX = -1.0f;
            this.lastPaintY = -1.0f;
            return true;
        }
        return super.mouseReleased(mouse, mouseButton);
    }

    private void paintLine(GameImage image, float fromX, float fromY, float toX, float toY, int thickness, int argb) {
        float dx = toX - fromX;
        float dy = toY - fromY;
        float distance = (float) Math.sqrt((dx * dx) + (dy * dy));
        if (distance > 0.0f) {
            float stepX = dx / distance;
            float stepY = dy / distance;
            for (int i = 0; i < distance; i++) {
                float x = fromX + (stepX * i);
                float y = fromY + (stepY * i);
                paintCircle(image, x, y, thickness, (argb & 16777215) | 1610612736);
            }
            return;
        }
        paintCircle(image, fromX, fromY, thickness, argb);
    }

    private void paintCircle(GameImage image, float x, float y, int radius, int argb) {
        for (int offsetX = -radius; offsetX < radius; offsetX++) {
            for (int offsetY = -radius; offsetY < radius; offsetY++) {
                float alpha = 1.0f - (((float) Math.sqrt((offsetX * offsetX) + (offsetY * offsetY))) / radius);
                if (alpha >= 0.0f) {
                    int destX = (int) (x + offsetX);
                    int destY = (int) (y + offsetY);
                    if (destX >= 0 && destX < image.getWidth() && destY >= 0 && destY < image.getHeight()) {
                        int prevColor = image.getARGB(destX, destY);
                        int newColor = (argb & 16777215) | (((int) (alpha * ((argb >> 24) & 255))) << 24);
                        int backgroundAlpha = (prevColor >> 24) & 255;
                        int overlayAlpha = (newColor >> 24) & 255;
                        int backgroundRed = (prevColor >> 16) & 255;
                        int overlayRed = (newColor >> 16) & 255;
                        int backgroundGreen = (prevColor >> 8) & 255;
                        int overlayGreen = (newColor >> 8) & 255;
                        int backgroundBlue = prevColor & 255;
                        int overlayBlue = newColor & 255;
                        float opacity = overlayAlpha / 255.0f;
                        float backgroundContribution = 1.0f - opacity;
                        int mergedColor = ColorFormat.ARGB32.pack((int) Math.min((overlayRed * opacity) + (backgroundRed * backgroundContribution), 255.0f), (int) Math.min((overlayGreen * opacity) + (backgroundGreen * backgroundContribution), 255.0f), (int) Math.min((overlayBlue * opacity) + (backgroundBlue * backgroundContribution), 255.0f), Math.min(overlayAlpha + backgroundAlpha, 255));
                        image.setARGB(destX, destY, mergedColor);
                    }
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (isOpen()) {
            super.mouseScrolled(mouse, scrollDelta);
            return true;
        }
        return false;
    }

    private Rectangle getRectangle(float progress) {
        if (this.transitionType == TransitionType.OPEN || this.transitionType == TransitionType.CLOSE) {
            Rectangle source = this.transitionSourceRectangle;
            MutableRectangle destination = this.canvasWidget.bounds().copy();
            return hasOpenAndCloseTransition() ? source.lerp(destination, progress) : destination;
        }
        if (this.transitionType == TransitionType.DRAG_CLOSE) {
            Rectangle source2 = this.transitionSourceRectangle;
            MutableRectangle destination2 = this.canvasWidget.bounds().copy();
            applyDragOffset(destination2, 1.0f);
            return hasOpenAndCloseTransition() ? source2.lerp(destination2, progress) : destination2;
        }
        if (this.transitionType == TransitionType.DRAG) {
            MutableRectangle destination3 = this.canvasWidget.bounds().copy();
            MutableRectangle source3 = destination3.copy();
            applyDragOffset(source3, 1.0f);
            return this.dragging ? source3 : source3.lerp(destination3, progress);
        }
        if (this.transitionType == TransitionType.SWIPE_LEFT || this.transitionType == TransitionType.SWIPE_RIGHT) {
            MutableRectangle destination4 = this.canvasWidget.bounds().copy();
            MutableRectangle source4 = destination4.copy();
            if (progress < 0.5d) {
                applyDragOffset(source4, 1.0f);
                applyDragOffset(destination4, 2.0f);
            } else {
                applyDragOffset(source4, -2.0f);
            }
            return source4.lerp(destination4, progress);
        }
        return this.canvasWidget.bounds();
    }

    private void applyDragOffset(MutableRectangle rectangle, float multiplier) {
        float sourceHeight = this.transitionSourceRectangle.getHeight();
        float destinationHeight = this.canvasWidget.bounds().getHeight();
        rectangle.shift((this.lastMouseX - this.dragStartX) * multiplier, (this.lastMouseY - this.dragStartY) * multiplier);
        float distance = Math.abs(this.lastMouseY - this.dragStartY) * multiplier;
        float minPercentage = (sourceHeight / destinationHeight) / 2.0f;
        float shrinkPercentage = Math.min((distance / destinationHeight) * 2.0f, 1.0f - minPercentage);
        rectangle.shrink((((float) CubicBezier.EASE_IN.curve(shrinkPercentage)) * rectangle.getHeight()) / 2.0f);
    }

    private float getTransitionProgressAt(long transitionTimePassed) {
        if (this.transitionSourceRectangle == null) {
            return 1.0f;
        }
        float progress = Math.min(transitionTimePassed / 200.0f, 1.0f);
        if (!isOpen()) {
            progress = 1.0f - progress;
        }
        return (float) this.transitionType.easing().curve(progress);
    }

    public void displayScreenshot(@NotNull Screenshot screenshot) {
        String str;
        TextComponent textComponentText;
        TranslatableComponent translatableComponentTranslatable;
        TranslatableComponent translatableComponentTranslatable2;
        Screenshot previousScreenshot = this.screenshot;
        Rectangle rectangle = this.holder.getTileRectangleOf(screenshot);
        if (rectangle == null) {
            rectangle = new DefaultRectangle();
        }
        this.transitionSourceRectangle = rectangle;
        startTransition(TransitionType.OPEN);
        this.screenshot = screenshot;
        this.open = true;
        this.fileExists = screenshot.exists();
        this.titleWidget.setText(screenshot.getFileName());
        this.canvasWidget.setContextMenu(this.screenshot.getContextMenu());
        this.iconWidget.icon().set(screenshot.getIcon());
        ComponentWidget componentWidget = this.statusWidget;
        if (this.fileExists) {
            str = "labymod.misc.loading";
        } else {
            str = "labymod.activity.screenshotBrowser.viewer.notExisting";
        }
        componentWidget.setComponent(Component.translatable(str, new Component[0]));
        updateShareContextMenu(screenshot);
        ScreenshotMeta meta = screenshot.getMeta();
        String address = meta.get("Server Address");
        ComponentWidget componentWidget2 = this.metaWidget;
        if (address == null) {
            textComponentText = Component.empty();
        } else {
            textComponentText = Component.text(address.replace(":25565", ""));
        }
        componentWidget2.setComponent(textComponentText);
        this.trashButtonWidget.setEnabled(this.fileExists);
        this.openButtonWidget.setEnabled(this.fileExists);
        ButtonWidget buttonWidget = this.openButtonWidget;
        if (this.fileExists) {
            translatableComponentTranslatable = null;
        } else {
            translatableComponentTranslatable = Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]);
        }
        buttonWidget.setHoverComponent(translatableComponentTranslatable);
        this.copyButtonWidget.setEnabled(this.fileExists);
        this.copyButtonWidget.setVisible(true);
        ButtonWidget buttonWidget2 = this.copyButtonWidget;
        if (this.fileExists) {
            translatableComponentTranslatable2 = null;
        } else {
            translatableComponentTranslatable2 = Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]);
        }
        buttonWidget2.setHoverComponent(translatableComponentTranslatable2);
        this.previousButtonWidget.setEnabled(getScreenshotAtOffset(-1) != null);
        this.nextButtonWidget.setEnabled(getScreenshotAtOffset(1) != null);
        ScreenshotBrowser browser = this.holder.browser();
        if (this.fileExists) {
            browser.getExecutor().queue(() -> {
                if (this.screenshot.equals(screenshot)) {
                    screenshot.updateQuality(Screenshot.QualityType.RAW);
                    this.iconWidget.icon().set(screenshot.getIcon());
                }
                if (previousScreenshot != null && !screenshot.equals(previousScreenshot) && previousScreenshot.exists()) {
                    previousScreenshot.updateQuality(this.holder.getQuality());
                }
            });
        } else {
            browser.removeScreenshot(screenshot);
        }
    }

    public boolean displayScreenshotAtOffset(int offset) {
        Screenshot screenshotAtOffset = getScreenshotAtOffset(offset);
        if (screenshotAtOffset == null) {
            return false;
        }
        displayScreenshot(screenshotAtOffset);
        startTransition(TransitionType.NONE);
        return true;
    }

    public Screenshot getScreenshot() {
        return this.screenshot;
    }

    public Screenshot getScreenshotAtOffset(int offset) {
        int index;
        List<Screenshot> screenshots = this.holder.getScreenshots();
        int index2 = screenshots.indexOf(this.screenshot);
        if (index2 != -1 && (index = index2 + offset) >= 0 && index < screenshots.size()) {
            return screenshots.get(index);
        }
        return null;
    }

    public void onSectionChanged() {
        if (this.screenshot != null && !this.screenshot.exists()) {
            close();
        }
    }

    public void close() {
        if (!isOpen()) {
            return;
        }
        this.open = false;
        abortEditing();
        startTransition(TransitionType.CLOSE);
        this.holder.browser().getExecutor().queue(() -> {
            if (this.screenshot.exists()) {
                this.screenshot.updateQuality(Screenshot.QualityType.LOW);
            }
        });
    }

    private void copyToClipboard() {
        if (this.editing) {
            try {
                this.labyAPI.operatingSystemAccessor().copyToClipboard(createdPaintedGameImage());
                return;
            } catch (Exception exception) {
                LOGGER.error("Unable to copy screenshot to clipboard", exception);
                LabySentry.capture(exception);
                return;
            }
        }
        this.screenshot.copyToClipboard();
    }

    private GameImage createdPaintedGameImage() throws Exception {
        GameImage paintCanvas;
        GameImage image = this.screenshot.asGameImage();
        if (image == null || (paintCanvas = this.paintCanvasTexture.getImage()) == null) {
            return null;
        }
        GameImage paintCanvas2 = ScreenshotUtil.resize(paintCanvas, image.getWidth(), image.getHeight());
        GameImage paintedImage = GameImage.IMAGE_PROVIDER.createImage(image.getWidth(), image.getHeight());
        for (int x = 0; x < paintCanvas2.getWidth(); x++) {
            for (int y = 0; y < paintCanvas2.getHeight(); y++) {
                int background = image.getARGB(x, y);
                int paintColor = paintCanvas2.getARGB(x, y);
                paintedImage.setARGB(x, y, ColorUtil.blendColors(background, paintColor));
            }
        }
        return paintedImage;
    }

    public boolean isOpen() {
        return this.open;
    }

    private boolean hasOpenAndCloseTransition() {
        return this.transitionSourceRectangle != null;
    }

    private void startTransition(TransitionType type) {
        this.transitionType = type;
        this.transitionTicks = 0;
    }

    private void startEditing() {
        this.editing = true;
        this.cancelButtonWidget.setVisible(true);
        this.trashButtonWidget.setVisible(false);
        this.openButtonWidget.setVisible(false);
        this.shareButtonWidget.setVisible(false);
        this.editButtonWidget.setVisible(false);
        this.previousButtonWidget.setVisible(false);
        this.nextButtonWidget.setVisible(false);
        this.paintCanvasWidget.setVisible(true);
        this.paintThicknessSlider.setVisible(true);
        this.paintColorPicker.setVisible(true);
        try {
            Icon screenshotIcon = this.screenshot.getIcon();
            FloatVector2 dimension = ScreenshotUtil.maxSize(new FloatVector2(screenshotIcon.getResolutionWidth(), screenshotIcon.getResolutionHeight()), 1920, 1080);
            GameImage paintGameImage = GameImage.IMAGE_PROVIDER.createImage((int) dimension.getX(), (int) dimension.getY());
            this.paintCanvasTexture = new DynamicTexture(PAINT_OVERLAY, paintGameImage);
            this.paintCanvasTexture.bindTo();
            Icon paintIcon = Icon.texture(PAINT_OVERLAY);
            this.paintCanvasWidget.icon().set(paintIcon);
        } catch (Exception exception) {
            LOGGER.error("Failed to create paint canvas texture", exception);
            LabySentry.capture(exception);
        }
    }

    private void abortEditing() {
        this.editing = false;
        this.cancelButtonWidget.setVisible(false);
        this.trashButtonWidget.setVisible(true);
        this.openButtonWidget.setVisible(true);
        this.shareButtonWidget.setVisible(true);
        this.editButtonWidget.setVisible(true);
        this.previousButtonWidget.setVisible(true);
        this.nextButtonWidget.setVisible(true);
        this.paintCanvasWidget.setVisible(false);
        this.paintThicknessSlider.setVisible(false);
        this.paintColorPicker.setVisible(false);
        if (this.paintCanvasTexture != null && !this.paintCanvasTexture.deviceTexture().isClosed()) {
            Laby.references().textureRepository().releaseTexture(this.paintCanvasTexture.getTextureLocation());
        }
    }

    private void updateShareContextMenu(Screenshot screenshot) {
        TranslatableComponent translatableComponentTranslatable;
        TranslatableComponent translatableComponentTranslatable2;
        ContextMenu contextMenu = this.shareButtonWidget.createContextMenu();
        boolean isConnected = this.labyAPI.labyConnect().isAuthenticated();
        ScreenshotMeta meta = screenshot.getMeta();
        boolean hasInsight = meta.hasInsight();
        ContextMenuEntry.Builder builderDisabled = ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.upload", new Component[0])).icon(Textures.SpriteCommon.UPLOAD).disabled((hasInsight && isConnected && this.fileExists) ? false : true);
        if (this.fileExists) {
            if (hasInsight) {
                if (isConnected) {
                    translatableComponentTranslatable = null;
                } else {
                    translatableComponentTranslatable = Component.translatable("labymod.activity.screenshotBrowser.viewer.notConnected", new Component[0]);
                }
            } else {
                translatableComponentTranslatable = Component.translatable("labymod.activity.screenshotBrowser.viewer.noInsight", new Component[0]);
            }
        } else {
            translatableComponentTranslatable = Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]);
        }
        contextMenu.addEntry(builderDisabled.hoverComponent(translatableComponentTranslatable).clickHandler(entry -> {
            this.shareButtonWidget.setEnabled(false);
            this.screenshot.upload().thenAccept(url -> {
                if (url == null) {
                    return;
                }
                OperatingSystem.getPlatform().openUrl(url);
                Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(url);
            }).thenRun(() -> {
                this.shareButtonWidget.setEnabled(true);
            });
            return true;
        }).build());
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        boolean hasOnlineFriends = session != null && session.getOnlineFriendCount() > 0;
        ContextMenuEntry.Builder builderDisabled2 = ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.send", new Component[0])).icon(Textures.SpriteCommon.SHARE).disabled((isConnected && this.fileExists && hasOnlineFriends) ? false : true);
        if (this.fileExists) {
            if (isConnected) {
                if (hasOnlineFriends) {
                    translatableComponentTranslatable2 = null;
                } else {
                    translatableComponentTranslatable2 = Component.translatable("labymod.activity.screenshotBrowser.viewer.noOnlineFriends", new Component[0]);
                }
            } else {
                translatableComponentTranslatable2 = Component.translatable("labymod.activity.screenshotBrowser.viewer.notConnected", new Component[0]);
            }
        } else {
            translatableComponentTranslatable2 = Component.translatable("labymod.activity.screenshotBrowser.viewer.notExisting", new Component[0]);
        }
        contextMenu.addEntry(builderDisabled2.hoverComponent(translatableComponentTranslatable2).subMenu(() -> {
            ContextMenu subMenu = new ContextMenu();
            if (session != null) {
                for (Friend friend : session.getFriends()) {
                    if (friend.isOnline()) {
                        subMenu.addEntry(ContextMenuEntry.builder().text(Component.text(friend.getName())).icon(Icon.head(friend.getUniqueId())).clickHandler(entry2 -> {
                            try {
                                Chat chat = friend.chat();
                                chat.sendImage(screenshot.asGameImage());
                                chat.openChat();
                                return true;
                            } catch (Exception exception) {
                                LOGGER.error("Unable to send screenshot to friend", exception);
                                LabySentry.capture(exception);
                                return true;
                            }
                        }).build());
                    }
                }
            }
            return subMenu;
        }).build());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/viewer/ScreenshotViewerWidget$TransitionType.class */
    enum TransitionType {
        OPEN(CubicBezier.EASE_IN_OUT, true),
        CLOSE(CubicBezier.EASE_IN_OUT, true),
        DRAG_CLOSE(CubicBezier.EASE_IN_OUT, true),
        DRAG(CubicBezier.EASE_IN_OUT, false),
        SWIPE_RIGHT(CubicBezier.EASE_OUT, false),
        SWIPE_LEFT(CubicBezier.EASE_OUT, false),
        NONE(CubicBezier.LINEAR, false);

        private final CubicBezier easing;
        private final boolean fading;

        TransitionType(CubicBezier easing, boolean fading) {
            this.easing = easing;
            this.fading = fading;
        }

        public CubicBezier easing() {
            return this.easing;
        }

        public boolean hasFading() {
            return this.fading;
        }
    }
}
