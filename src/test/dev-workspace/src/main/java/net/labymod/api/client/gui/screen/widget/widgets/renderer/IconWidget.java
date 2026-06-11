package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectFitType;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectPosition;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/renderer/IconWidget.class */
@AutoWidget
public class IconWidget extends SimpleWidget {
    private static final String UPDATE_LISTENER_KEY = IconWidget.class.getName() + "#IconUpdateListener";
    private final LssProperty<Icon> icon = new LssProperty<>(null);
    private final LssProperty<Integer> color = new LssProperty<>(-1);
    private final LssProperty<Long> colorTransitionDuration = new LssProperty<>(0L);
    private final LssProperty<Boolean> clickable = new LssProperty<>(false);
    private final LssProperty<Boolean> cleanupOnDispose = new LssProperty<>(false);
    private final ObjectPosition objectPosition = new ObjectPosition();
    private final LssProperty<ObjectFitType> objectFit = new LssProperty<>(ObjectFitType.FILL);
    private final LssProperty<Float> zoom = new LssProperty<>(Float.valueOf(0.0f));

    @Deprecated
    private final LssProperty<Boolean> blurry = new LssProperty<>(false);
    private final MutableRectangle iconBounds = new DefaultRectangle();

    public IconWidget(Icon icon) {
        this.icon.addChangeListener((type, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.setUpdateListener(UPDATE_LISTENER_KEY, null);
            }
            if (newValue != null) {
                newValue.setUpdateListener(UPDATE_LISTENER_KEY, this::updateIconBounds);
            }
            updateIconBounds();
        });
        this.icon.updateDefaultValue(icon);
        this.icon.set(icon);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        Icon icon;
        ResourceLocation location;
        if (this.cleanupOnDispose != null && this.icon != null && this.cleanupOnDispose.get().booleanValue() && (icon = this.icon.get()) != null && (location = icon.getResourceLocation()) != null) {
            this.labyAPI.renderPipeline().resources().textureRepository().queueTextureRelease(location);
        }
        super.dispose();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void handleAttributes() {
        super.handleAttributes();
        updateIconBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        Icon icon = this.icon.get();
        float aspectRatio = icon != null ? icon.getAspectRatio() : 1.0f;
        if (aspectRatio != 0.0f) {
            return bounds().getHeight(type) * aspectRatio;
        }
        return 0.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        Icon icon = this.icon.get();
        float aspectRatio = icon != null ? icon.getAspectRatio() : 1.0f;
        if (aspectRatio != 0.0f) {
            return bounds().getWidth(type) / aspectRatio;
        }
        return 0.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        float fFloatValue;
        Rectangle clipBounds;
        Icon icon = this.icon.get();
        if (icon == null) {
            return;
        }
        icon.setBorderRadius(getBorderRadius());
        if (this.zoom.get().floatValue() != 0.0f) {
            fFloatValue = this.zoom.get().floatValue();
        } else {
            fFloatValue = (this.clickable.get().booleanValue() && isHovered()) ? 1.0f : 0.0f;
        }
        float zoom = fFloatValue;
        Bounds bounds = bounds();
        float left = bounds.getLeft(BoundsType.INNER) + this.iconBounds.getLeft();
        float top = bounds.getTop(BoundsType.INNER) + this.iconBounds.getTop();
        float right = bounds.getRight(BoundsType.INNER) - this.iconBounds.getRight();
        float bottom = bounds.getBottom(BoundsType.INNER) - this.iconBounds.getBottom();
        boolean useFloatingPointPosition = useFloatingPointPosition().get().booleanValue();
        float width = MathHelper.applyFloatingPointPosition(useFloatingPointPosition, right - left);
        float height = MathHelper.applyFloatingPointPosition(useFloatingPointPosition, bottom - top);
        ObjectFitType objectFitType = this.objectFit.get();
        if (objectFitType != ObjectFitType.FILL) {
            HorizontalAlignment alignment = this.objectPosition.getHorizontalAlignment();
            float offset = this.objectPosition.getHorizontalOffset();
            if (objectFitType == ObjectFitType.COVER || objectFitType == ObjectFitType.NONE) {
                switch (alignment) {
                    case RIGHT:
                        left += offset;
                        break;
                    case CENTER:
                        left -= ((bounds.getWidth(BoundsType.INNER) / 2.0f) - (width / 2.0f)) - offset;
                        break;
                    case LEFT:
                        left -= (bounds.getWidth(BoundsType.INNER) - width) + offset;
                        break;
                }
            } else {
                switch (alignment) {
                    case RIGHT:
                        left += (bounds.getWidth(BoundsType.INNER) - width) - offset;
                        break;
                    case CENTER:
                        left += ((bounds.getWidth(BoundsType.INNER) / 2.0f) - (width / 2.0f)) + offset;
                        break;
                    case LEFT:
                        left += offset;
                        break;
                }
            }
            VerticalAlignment alignment2 = this.objectPosition.getVerticalAlignment();
            float offset2 = this.objectPosition.getVerticalOffset();
            if (objectFitType == ObjectFitType.COVER || objectFitType == ObjectFitType.NONE) {
                switch (alignment2) {
                    case BOTTOM:
                        top += offset2;
                        break;
                    case CENTER:
                        top -= ((bounds.getHeight(BoundsType.INNER) / 2.0f) - (height / 2.0f)) - offset2;
                        break;
                    case TOP:
                        top -= (bounds.getHeight(BoundsType.INNER) - height) + offset2;
                        break;
                }
            } else {
                switch (alignment2) {
                    case BOTTOM:
                        top += (bounds.getHeight(BoundsType.INNER) - height) - offset2;
                        break;
                    case CENTER:
                        top += ((bounds.getHeight(BoundsType.INNER) / 2.0f) - (height / 2.0f)) + offset2;
                        break;
                    case TOP:
                        top += offset2;
                        break;
                }
            }
        }
        if (this.blurry.getOrDefault(false).booleanValue()) {
            icon.makeBlurry();
        }
        boolean floating = useFloatingPointPosition().get().booleanValue();
        float bX = left - zoom;
        float bY = top - zoom;
        float bWidth = width + (zoom * 2.0f);
        float bHeight = height + (zoom * 2.0f);
        if (this.objectFit.get() == ObjectFitType.SCALE_DOWN) {
            clipBounds = Rectangle.absolute(bX, bY, bX + bWidth, bY + bHeight);
        } else {
            clipBounds = bounds.rectangle(BoundsType.INNER);
        }
        if (!floating) {
            bX = (int) bX;
            bY = (int) bY;
            bWidth = (int) bWidth;
            bHeight = (int) bHeight;
            clipBounds = Rectangle.absolute((int) clipBounds.getLeft(), (int) clipBounds.getTop(), (int) clipBounds.getRight(), (int) clipBounds.getBottom());
        }
        ScreenCanvas screenCanvas = context.canvas();
        screenCanvas.submitIcon(icon, bX, bY, bWidth, bHeight, isHovered(), ColorUtil.lerpedColor(this.colorTransitionDuration.get().longValue(), context.getTickDelta(), this.color), clipBounds);
        super.renderWidget(context);
    }

    public LssProperty<Icon> icon() {
        return this.icon;
    }

    public LssProperty<Integer> color() {
        return this.color;
    }

    public LssProperty<Long> colorTransitionDuration() {
        return this.colorTransitionDuration;
    }

    public LssProperty<Boolean> clickable() {
        return this.clickable;
    }

    public LssProperty<ObjectFitType> objectFit() {
        return this.objectFit;
    }

    @Deprecated
    public LssProperty<Boolean> cleanupOnDispose() {
        return this.cleanupOnDispose;
    }

    public LssProperty<Float> zoom() {
        return this.zoom;
    }

    public LssProperty<Boolean> blurry() {
        return this.blurry;
    }

    public ObjectPosition objectPosition() {
        return this.objectPosition;
    }

    public boolean isCleanupOnDispose() {
        return this.cleanupOnDispose.get().booleanValue();
    }

    public IconWidget setCleanupOnDispose(boolean value) {
        this.cleanupOnDispose.set(Boolean.valueOf(value));
        return this;
    }

    private void updateIconBounds() {
        Icon icon = this.icon.get();
        if (icon == null) {
        }
        handleSizeAttributes();
        Bounds bounds = bounds();
        float width = bounds.getWidth(BoundsType.INNER);
        float height = bounds.getHeight(BoundsType.INNER);
        float aspectRatio = icon.getAspectRatio();
        icon.setBorderRadius(getBorderRadius());
        this.iconBounds.setBounds(0.0f, 0.0f, 0.0f, 0.0f);
        ObjectFitType objectFitType = this.objectFit.get();
        if (aspectRatio > 0.0f) {
            switch (objectFitType) {
                case SCALE_DOWN:
                case CONTAIN:
                    float targetHeight = width / aspectRatio;
                    if (height > targetHeight) {
                        float verticalOffset = height - targetHeight;
                        this.iconBounds.setBottom(verticalOffset);
                    } else {
                        float targetWidth = height * aspectRatio;
                        float horizontalOffset = width - targetWidth;
                        this.iconBounds.setRight(horizontalOffset);
                    }
                    break;
                case NONE:
                case COVER:
                    float aspectHeight = bounds.getWidth(BoundsType.INNER) / aspectRatio;
                    float verticalOverflow = aspectHeight - bounds.getHeight(BoundsType.INNER);
                    if (verticalOverflow > 0.0f) {
                        this.iconBounds.setTop(-verticalOverflow);
                    } else {
                        float aspectWidth = bounds.getHeight(BoundsType.INNER) * aspectRatio;
                        float horizontalOverflow = aspectWidth - bounds.getWidth(BoundsType.INNER);
                        if (horizontalOverflow > 0.0f) {
                            this.iconBounds.setLeft(-horizontalOverflow);
                        }
                    }
                    setStencil(true);
                    break;
            }
        }
    }

    public Rectangle iconBounds() {
        return this.iconBounds;
    }
}
