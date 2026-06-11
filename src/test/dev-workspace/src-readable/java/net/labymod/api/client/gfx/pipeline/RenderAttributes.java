package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontProperties;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/RenderAttributes.class */
public final class RenderAttributes {
    private static final Lazy<TextRendererProvider> TEXT_RENDERER_PROVIDER = Lazy.of(() -> {
        return Laby.references().textRendererProvider();
    });
    private boolean forceVanillaFont;
    private StencilMode stencilMode;

    @Nullable
    private ClipShape clipShape;
    private float fontWeight;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/RenderAttributes$StencilMode.class */
    @ApiStatus.Experimental
    public enum StencilMode {
        WRITE_STENCIL,
        WRITE_TO_STENCIL,
        DISABLED
    }

    public RenderAttributes() {
        this.stencilMode = StencilMode.DISABLED;
        this.fontWeight = 0.0f;
    }

    public RenderAttributes(RenderAttributes other) {
        this.stencilMode = StencilMode.DISABLED;
        this.fontWeight = 0.0f;
        this.forceVanillaFont = other.forceVanillaFont;
        this.stencilMode = other.stencilMode;
        this.clipShape = other.clipShape;
        this.fontWeight = other.fontWeight;
    }

    public boolean isForceVanillaFont() {
        return this.forceVanillaFont;
    }

    public void setForceVanillaFont(boolean forceVanillaFont) {
        this.forceVanillaFont = forceVanillaFont;
    }

    public StencilMode getStencilMode() {
        return this.stencilMode;
    }

    public void setStencilMode(StencilMode stencilMode) {
        this.stencilMode = stencilMode;
    }

    @Nullable
    public ClipShape getClipShape() {
        return this.clipShape;
    }

    public void setClipShape(@Nullable ClipShape clipShape) {
        this.clipShape = clipShape;
    }

    public float getFontWeight() {
        return this.fontWeight;
    }

    public void setFontWeight(float fontWeight) {
        float weight = (fontWeight / 1000.0f) / 6.0f;
        this.fontWeight = MathHelper.clamp(weight, 0.0f, 0.16666667f);
    }

    public void apply() {
        TextRendererProvider provider = TEXT_RENDERER_PROVIDER.get();
        provider.forceMinecraftRenderer(this.forceVanillaFont);
        provider.getRenderer().setValue(FontProperties.FONT_WEIGHT_KEY, Float.valueOf(this.fontWeight));
    }
}
