package net.labymod.v1_16_5.client.gfx.pipeline.renderer.text;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.MinecraftFontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.StringStart;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextStateBuilder;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_16_5.client.StringSplitterAccessor;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/VanillaFontRenderer.class */
@Singleton
@Implements(MinecraftFontRenderer.class)
public class VanillaFontRenderer implements MinecraftFontRenderer {
    private static final Logging LOGGER = Logging.getLogger();
    private final GameMathMapper mathMapper;
    private final ComponentMapper componentMapper;

    @Inject
    public VanillaFontRenderer(GameMathMapper mathMapper, ComponentMapper componentMapper) {
        this.mathMapper = mathMapper;
        this.componentMapper = componentMapper;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, String text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        dku font = getFont();
        font.a(text, x, y, argb, FontFlags.isShadow(flags), (b) this.mathMapper.toMatrix4f(pose), getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        dku font = getFont();
        font.a((nr) this.componentMapper.toMinecraftComponent(text), x, y, argb, FontFlags.isShadow(flags), (b) this.mathMapper.toMatrix4f(pose), getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class);
        dku font = getFont();
        font.a(vanillaText.getSequence(), x, y, argb, FontFlags.isShadow(flags), (b) this.mathMapper.toMatrix4f(pose), getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public TextState prepareText(FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class);
        PreparedTextBuilder builder = new PreparedTextBuilder(getFont(), x, y, argb, FontFlags.isShadow(flags), backgroundArgb);
        vanillaText.getSequence().accept(builder);
        return builder.build(x);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(String text) {
        return getFont().b(text);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(Component text) {
        return getFont().a((nr) this.componentMapper.toMinecraftComponent(text));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(FormattedTextLayout text) {
        return getFont().a(((VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class)).getSequence());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getLineHeight() {
        Objects.requireNonNull(getFont());
        return 9.0f;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public String trimTextToWidth(String text, float maxWidth, StringStart start) {
        dkj splitter = getFont().b();
        if (start == StringStart.LEFT) {
            return splitter.b(text, MathHelper.ceil(maxWidth), ob.a);
        }
        return splitter.c(text, MathHelper.ceil(maxWidth), ob.a);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(int codepoint, Style style) {
        return StringSplitterAccessor.cast(getFont().b()).getWidthProvider().getWidth(codepoint, (ob) CastUtil.cast(style));
    }

    private eag getBufferSource() {
        return djz.C().aE().b();
    }

    private boolean getDisplayMode(int flags) {
        FontFlags.ensureOneDisplayMode(flags);
        if (FontFlags.hasFlag(flags, 4)) {
            return false;
        }
        if (FontFlags.hasFlag(flags, 8)) {
            return true;
        }
        if (FontFlags.hasFlag(flags, 16)) {
            LOGGER.error("Polygon offset is not supported", new Object[0]);
        }
        if (IdeUtil.RUNNING_IN_IDE) {
            LOGGER.error("No display mode set for text", new Object[0]);
            return false;
        }
        return false;
    }

    private dku getFont() {
        return djz.C().g;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/VanillaFontRenderer$PreparedTextBuilder.class */
    public static class PreparedTextBuilder implements afb {
        private final TextStateBuilder builder = new TextStateBuilder();
        private final FontAccessor accessor;
        private final int color;
        private final boolean drawShadow;
        private final int backgroundColor;
        private float x;
        private final float y;
        private boolean inverseDepth;

        public PreparedTextBuilder(dku font, float x, float y, int color, boolean drawShadow, int backgroundColor) {
            this.accessor = FontAccessor.self(font);
            this.color = color;
            this.drawShadow = drawShadow;
            this.backgroundColor = backgroundColor;
            this.x = x;
            this.y = y;
        }

        public boolean accept(int position, @NotNull ob style, int codepoint) {
            dmz dmzVarB;
            dmw fontSet = this.accessor.labyMod$getFontSet(style.k());
            dea glyphInfo = fontSet.a(codepoint);
            if (style.f() && codepoint != 32) {
                dmzVarB = fontSet.a(glyphInfo);
            } else {
                dmzVarB = fontSet.b(codepoint);
            }
            dmz bakedGlyph = dmzVarB;
            boolean bold = style.b();
            od textColor = style.a();
            int argb = getTextColor(textColor);
            int shadowArgb = this.drawShadow ? ColorFormat.ARGB32.scaleRGB(argb, 0.25f) : 0;
            float advance = glyphInfo.a(bold);
            float $$11 = position == 0 ? this.x - 1.0f : this.x;
            if (!(bakedGlyph instanceof dna)) {
                float boldOffset = bold ? glyphInfo.b() : 0.0f;
                addGlyph(new GlyphInstance(this.x, this.y, argb, shadowArgb, bakedGlyph, (Style) style, boldOffset));
            }
            if (style.d()) {
                addEffect(createEffect($$11, this.y + 4.5f, this.x + advance, (this.y + 4.5f) - 1.0f, getOverTextEffectDepth(), argb));
            }
            if (style.e()) {
                addEffect(createEffect($$11, this.y + 9.0f, this.x + advance, (this.y + 9.0f) - 1.0f, getOverTextEffectDepth(), argb));
            }
            this.x += advance;
            return true;
        }

        public TextState build(float startX) {
            if (this.backgroundColor != 0) {
                addEffect(createEffect(startX - 1.0f, this.y + 9.0f, this.x, this.y - 1.0f, getUnderTextEffectDepth(), this.backgroundColor));
            }
            return this.builder.build();
        }

        private a createEffect(float left, float top, float right, float bottom, float depth, int argb) {
            if (argb == 0) {
                return null;
            }
            ColorFormat format = ColorFormat.ARGB32;
            float red = format.normalizedRed(argb);
            float green = format.normalizedGreen(argb);
            float blue = format.normalizedBlue(argb);
            float alpha = format.normalizedAlpha(argb);
            return new a(left, top, right, bottom, depth, red, green, blue, alpha);
        }

        private void addGlyph(GlyphInstance glyphInstance) {
            this.builder.addGlyph(new VanillaRenderedGlyph(glyphInstance));
        }

        private void addEffect(a effect) {
            dmw fontSet = this.accessor.labyMod$getFontSet(ob.b);
            dmz bakedGlyph = fontSet.a();
            this.builder.addEffect(new VanillaRenderedEffect(bakedGlyph, effect));
        }

        private int getTextColor(@Nullable od textColor) {
            if (textColor != null) {
                int opacity = ColorFormat.ARGB32.alpha(this.color);
                int argb = textColor.a();
                return ColorFormat.ARGB32.pack(argb, opacity);
            }
            return this.color;
        }

        private float getOverTextEffectDepth() {
            return this.inverseDepth ? 0.01f : -0.01f;
        }

        private float getUnderTextEffectDepth() {
            return this.inverseDepth ? -0.01f : 0.01f;
        }
    }
}
