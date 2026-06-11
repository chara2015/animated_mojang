package net.labymod.v1_21_5.client.gfx.pipeline.renderer.text;

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
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_21_5.client.StringSplitterAccessor;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/renderer/text/VanillaFontRenderer.class */
@Singleton
@Implements(MinecraftFontRenderer.class)
public class VanillaFontRenderer implements MinecraftFontRenderer {
    private static final Logging LOGGER = Logging.getLogger();
    private final ComponentMapper componentMapper;

    @Inject
    public VanillaFontRenderer(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, String text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        fti font = getFont();
        font.a(text, x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        fti font = getFont();
        font.a((xg) this.componentMapper.toMinecraftComponent(text), x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class);
        fti font = getFont();
        font.a(vanillaText.getSequence(), x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
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
        return getFont().a((xg) this.componentMapper.toMinecraftComponent(text));
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
        fra splitter = getFont().b();
        if (start == StringStart.LEFT) {
            return splitter.b(text, MathHelper.ceil(maxWidth), yd.a);
        }
        return splitter.c(text, MathHelper.ceil(maxWidth), yd.a);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(int codepoint, Style style) {
        return StringSplitterAccessor.cast(getFont().b()).getWidthProvider().getWidth(codepoint, (yd) CastUtil.cast(style));
    }

    private grn getBufferSource() {
        return fqq.Q().aR().c();
    }

    private a getDisplayMode(int flags) {
        FontFlags.ensureOneDisplayMode(flags);
        if (FontFlags.hasFlag(flags, 4)) {
            return a.a;
        }
        if (FontFlags.hasFlag(flags, 8)) {
            return a.b;
        }
        if (FontFlags.hasFlag(flags, 16)) {
            return a.c;
        }
        if (IdeUtil.RUNNING_IN_IDE) {
            LOGGER.error("No display mode set for text", new Object[0]);
        }
        return a.a;
    }

    private fti getFont() {
        return fqq.Q().h;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/renderer/text/VanillaFontRenderer$PreparedTextBuilder.class */
    public static class PreparedTextBuilder implements azl {
        private final TextStateBuilder builder = new TextStateBuilder();
        private final FontAccessor accessor;
        private final int color;
        private final boolean drawShadow;
        private final int backgroundColor;
        private float x;
        private float y;
        private boolean inverseDepth;

        public PreparedTextBuilder(fti font, float x, float y, int color, boolean drawShadow, int backgroundColor) {
            this.accessor = FontAccessor.self(font);
            this.color = color;
            this.drawShadow = drawShadow;
            this.backgroundColor = backgroundColor;
            this.x = x;
            this.y = y;
        }

        public boolean accept(int position, @NotNull yd style, int codepoint) {
            fwu fwuVarA;
            fwq fontSet = this.accessor.labyMod$getFontSet(style.l());
            fir glyphInfo = fontSet.a(codepoint, this.accessor.labyMod$filterFishyGlyphs());
            if (style.g() && codepoint != 32) {
                fwuVarA = fontSet.a(glyphInfo);
            } else {
                fwuVarA = fontSet.a(codepoint);
            }
            fwu bakedGlyph = fwuVarA;
            boolean bold = style.c();
            yf textColor = style.a();
            int argb = getTextColor(textColor);
            int shadowArgb = getShadowColor(style, argb);
            float advance = glyphInfo.a(bold);
            float $$11 = position == 0 ? this.x - 1.0f : this.x;
            float shadowOffset = glyphInfo.b();
            if (!(bakedGlyph instanceof fwv)) {
                float boldOffset = bold ? glyphInfo.a() : 0.0f;
                addGlyph(new b(this.x, this.y, argb, shadowArgb, bakedGlyph, style, boldOffset, shadowOffset));
            }
            if (style.e()) {
                addEffect(new a($$11, this.y + 4.5f, this.x + advance, (this.y + 4.5f) - 1.0f, getOverTextEffectDepth(), argb, shadowArgb, shadowOffset));
            }
            if (style.f()) {
                addEffect(new a($$11, this.y + 9.0f, this.x + advance, (this.y + 9.0f) - 1.0f, getOverTextEffectDepth(), argb, shadowArgb, shadowOffset));
            }
            this.x += advance;
            return true;
        }

        public TextState build(float startX) {
            if (this.backgroundColor != 0) {
                addEffect(new a(startX - 1.0f, this.y + 9.0f, this.x, this.y - 1.0f, getUnderTextEffectDepth(), this.backgroundColor));
            }
            return this.builder.build();
        }

        private void addGlyph(b glyphInstance) {
            this.builder.addGlyph(new VanillaRenderedGlyph(glyphInstance));
        }

        private void addEffect(a effect) {
            fwq fontSet = this.accessor.labyMod$getFontSet(yd.b);
            this.builder.addEffect(new VanillaRenderedEffect(fontSet.b(), effect));
        }

        private int getTextColor(@Nullable yf textColor) {
            if (textColor != null) {
                int opacity = ayh.a(this.color);
                int argb = textColor.a();
                return ayh.c(opacity, argb);
            }
            return this.color;
        }

        private int getShadowColor(yd style, int argb) {
            Integer shadowColor = style.b();
            if (shadowColor != null) {
                float argbOpacity = ayh.i(argb);
                float shadowOpacity = ayh.i(shadowColor.intValue());
                if (argbOpacity != 1.0f) {
                    return ayh.c(ayh.b(argbOpacity * shadowOpacity), shadowColor.intValue());
                }
                return shadowColor.intValue();
            }
            if (this.drawShadow) {
                return ayh.a(argb, 0.25f);
            }
            return 0;
        }

        private float getOverTextEffectDepth() {
            return this.inverseDepth ? 0.01f : -0.01f;
        }

        private float getUnderTextEffectDepth() {
            return this.inverseDepth ? -0.01f : 0.01f;
        }
    }
}
