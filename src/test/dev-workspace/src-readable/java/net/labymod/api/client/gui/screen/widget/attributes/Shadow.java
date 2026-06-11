package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/Shadow.class */
public class Shadow implements WidgetRenderer<AbstractWidget<?>> {
    private static final int CLASSIC_COLOR_OUTER = ColorFormat.ARGB32.pack(0, 0, 0, 255);
    private static final int CLASSIC_COLOR_INNER = ColorFormat.ARGB32.pack(0, 0, 0, 0);
    private static final UVCoordinates FRESH_SHADOW_UV = UVCoordinates.of(0, 0, 1, 1, 1, 1);
    private boolean inset;
    private FloatVector2 offset;
    private float spread;
    private float blur;
    private int color;
    private boolean classic;
    private boolean classicIsBackground;
    private boolean classicLeft;
    private boolean classicTop;
    private boolean classicRight;
    private boolean classicBottom;

    public Shadow(boolean classic, boolean left, boolean top, boolean right, boolean bottom, boolean classicIsBackground) {
        this.classic = false;
        this.classicIsBackground = false;
        this.classicLeft = false;
        this.classicTop = false;
        this.classicRight = false;
        this.classicBottom = false;
        this.classic = classic;
        this.classicLeft = left;
        this.classicTop = top;
        this.classicRight = right;
        this.classicBottom = bottom;
        this.classicIsBackground = classicIsBackground;
    }

    public Shadow(boolean inset, float offsetX, float offsetY, float spread, float blur, int color) {
        this.classic = false;
        this.classicIsBackground = false;
        this.classicLeft = false;
        this.classicTop = false;
        this.classicRight = false;
        this.classicBottom = false;
        this.inset = inset;
        this.offset = new FloatVector2(offsetX, offsetY);
        this.spread = spread;
        this.blur = blur;
        this.color = color;
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        if (this.classic && this.classicIsBackground) {
            if (isFreshUI()) {
                renderFreshShadow(context, bounds);
                return;
            } else {
                renderClassicShadow(context, bounds);
                return;
            }
        }
        if (!this.classic) {
            float blurStart = this.spread - (this.blur / 2.0f);
            if (!this.inset) {
                ScreenCanvas screenCanvas = context.canvas();
                MutableRectangle rect = bounds.rectangle(BoundsType.MIDDLE).copy().expand(this.offset).expand(blurStart);
                screenCanvas.submitOutlineRect(rect, this.blur, this.color, 0);
                screenCanvas.submitRect(rect, this.color);
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
        Bounds bounds = widget.bounds();
        if (this.classic && !this.classicIsBackground) {
            if (isFreshUI()) {
                renderFreshShadow(context, bounds);
                return;
            } else {
                renderClassicShadow(context, bounds);
                return;
            }
        }
        if (!this.classic) {
            float blurStart = this.spread - (this.blur / 2.0f);
            if (this.inset) {
                ScreenCanvas screenCanvas = context.canvas();
                Rectangle middleRectangle = bounds.rectangle(BoundsType.MIDDLE);
                float middleLeft = middleRectangle.getLeft();
                float middleTop = middleRectangle.getTop();
                float middleRight = middleRectangle.getRight();
                screenCanvas.submitAbsoluteRect(middleLeft, middleTop, middleRight, middleTop + this.offset.getY() + blurStart, this.color);
                screenCanvas.submitAbsoluteRect(middleLeft, middleTop, middleRight, middleTop + this.offset.getY() + blurStart, this.color);
                float middleBottom = middleRectangle.getBottom();
                screenCanvas.submitAbsoluteRect(middleLeft, (middleBottom + this.offset.getY()) - blurStart, middleRight, middleBottom, this.color);
                screenCanvas.submitAbsoluteRect(middleLeft, middleTop, middleLeft + this.offset.getX() + blurStart, middleBottom, this.color);
                screenCanvas.submitAbsoluteRect((middleRight + this.offset.getX()) - blurStart, middleTop, middleRight, middleBottom, this.color);
                screenCanvas.submitAbsoluteOutlineRect(bounds.getLeft(BoundsType.MIDDLE) + Math.max(this.offset.getX() + blurStart + this.blur, this.blur), bounds.getTop(BoundsType.MIDDLE) + Math.max(this.offset.getY() + blurStart + this.blur, this.blur), bounds.getRight(BoundsType.MIDDLE) + Math.min((this.offset.getX() - blurStart) - this.blur, -this.blur), bounds.getBottom(BoundsType.MIDDLE) + Math.min((this.offset.getY() - blurStart) - this.blur, -this.blur), this.blur, 0, this.color);
            }
        }
    }

    private void renderClassicShadow(ScreenContext context, Bounds bounds) {
        ScreenCanvas screenCanvas = context.canvas();
        if (this.classicLeft) {
            screenCanvas.submitAbsoluteGradientRect(GradientDirection.LEFT_TO_RIGHT, bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE), bounds.getLeft(BoundsType.MIDDLE) + 5.0f, bounds.getBottom(BoundsType.MIDDLE), CLASSIC_COLOR_OUTER, CLASSIC_COLOR_INNER);
        }
        if (this.classicTop) {
            screenCanvas.submitAbsoluteGradientRect(GradientDirection.TOP_TO_BOTTOM, bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE), bounds.getRight(BoundsType.MIDDLE), bounds.getY(BoundsType.MIDDLE) + 5.0f, CLASSIC_COLOR_OUTER, CLASSIC_COLOR_INNER);
        }
        if (this.classicRight) {
            screenCanvas.submitAbsoluteGradientRect(GradientDirection.LEFT_TO_RIGHT, bounds.getRight(BoundsType.MIDDLE) - 5.0f, bounds.getTop(BoundsType.MIDDLE), bounds.getRight(BoundsType.MIDDLE), bounds.getBottom(BoundsType.MIDDLE), CLASSIC_COLOR_INNER, CLASSIC_COLOR_OUTER);
        }
        if (this.classicBottom) {
            screenCanvas.submitAbsoluteGradientRect(GradientDirection.TOP_TO_BOTTOM, bounds.getLeft(BoundsType.MIDDLE), bounds.getBottom(BoundsType.MIDDLE) - 5.0f, bounds.getRight(BoundsType.MIDDLE), bounds.getBottom(BoundsType.MIDDLE), CLASSIC_COLOR_INNER, CLASSIC_COLOR_OUTER);
        }
    }

    private void renderFreshShadow(ScreenContext context, Bounds bounds) {
        ReasonableMutableRectangle rectangle = bounds.rectangle(BoundsType.MIDDLE);
        MinecraftTextures textures = Laby.labyAPI().minecraft().textures();
        ScreenCanvas screenCanvas = context.canvas();
        if (this.classicTop) {
            ResourceLocation location = textures.screenMenuHeaderSeparatorTexture();
            screenCanvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, location), rectangle.getX(), rectangle.getY(), rectangle.getWidth(), 2.0f, FRESH_SHADOW_UV, -1);
        }
        if (this.classicBottom) {
            ResourceLocation location2 = textures.screenMenuFooterSeparatorTexture();
            screenCanvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, location2), rectangle.getX(), rectangle.getBottom() - 1.0f, rectangle.getWidth(), 2.0f, FRESH_SHADOW_UV, -1);
        }
    }

    public void setInset(boolean inset) {
        this.inset = inset;
    }

    public void setOffset(FloatVector2 offset) {
        this.offset = offset;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    public void setBlur(float blur) {
        this.blur = blur;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isInset() {
        return this.inset;
    }

    public FloatVector2 getOffset() {
        return this.offset;
    }

    public float getSpread() {
        return this.spread;
    }

    public float getBlur() {
        return this.blur;
    }

    public int getColor() {
        return this.color;
    }

    private boolean isFreshUI() {
        VanillaThemeConfigAccessor config = (VanillaThemeConfigAccessor) Laby.labyAPI().themeService().getThemeConfig(VanillaThemeConfigAccessor.class);
        return PlatformEnvironment.isFreshUI() && config != null && config.freshUI().get().booleanValue();
    }
}
