package net.labymod.v1_12_2.client.gfx.pipeline.renderer.text;

import java.nio.FloatBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;
import net.labymod.api.client.gfx.pipeline.renderer.text.MinecraftFontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.StringStart;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphUtil;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextStateBuilder;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VanillaFontRenderer.class */
@Singleton
@Implements(MinecraftFontRenderer.class)
public class VanillaFontRenderer implements MinecraftFontRenderer {
    private static final float EFFECT_DEPTH = 0.01f;
    private static final float OVER_EFFECT_DEPTH = 0.01f;
    private static final float UNDER_EFFECT_DEPTH = -0.01f;
    private final ComponentMapper componentMapper;
    private final Laby3D laby3D;
    private final GlyphProvider glyphProvider = new GlyphProvider.Simple(Laby.references().vanillaGlyphStorage());

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VanillaFontRenderer$TextWidthProvider.class */
    @FunctionalInterface
    private interface TextWidthProvider<T> {
        int getWidth(bip bipVar, T t);
    }

    @Inject
    public VanillaFontRenderer(ComponentMapper componentMapper, Laby3D laby3D) {
        this.componentMapper = componentMapper;
        this.laby3D = laby3D;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, String text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        bip font = getFont();
        GlRenderDevice glRenderDevice = (GlRenderDevice) this.laby3D.renderDevice();
        pushMatrix(pose);
        drawTextWithBackground(glRenderDevice, font, text, x, y, backgroundArgb, flags, (v0, v1) -> {
            return v0.a(v1);
        });
        font.a(text, x, y, argb, FontFlags.isShadow(flags));
        popMatrix();
        restoreStates(glRenderDevice);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        bip font = getFont();
        hh component = (hh) this.componentMapper.toMinecraftComponent(text);
        String formattedText = component.d();
        GlRenderDevice glRenderDevice = (GlRenderDevice) this.laby3D.renderDevice();
        pushMatrix(pose);
        drawTextWithBackground(glRenderDevice, font, formattedText, x, y, backgroundArgb, flags, (v0, v1) -> {
            return v0.a(v1);
        });
        font.a(formattedText, x, y, argb, FontFlags.isShadow(flags));
        popMatrix();
        restoreStates(glRenderDevice);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class);
        bip font = getFont();
        String formattedText = vanillaText.getFormattedText();
        GlRenderDevice glRenderDevice = (GlRenderDevice) this.laby3D.renderDevice();
        pushMatrix(pose);
        drawTextWithBackground(glRenderDevice, font, formattedText, x, y, backgroundArgb, flags, (v0, v1) -> {
            return v0.a(v1);
        });
        font.a(formattedText, x, y, argb, FontFlags.isShadow(flags));
        popMatrix();
        restoreStates(glRenderDevice);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public TextState prepareText(FormattedTextLayout textLayout, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        StyledTextProcessor builder = new StyledTextProcessor(this, x, y, argb, backgroundArgb, flags);
        textLayout.format(builder);
        return builder.build();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(String text) {
        return getFont().a(text);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(Component text) {
        hh component = (hh) this.componentMapper.toMinecraftComponent(text);
        return getFont().a(component.d());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(FormattedTextLayout text) {
        return getFont().a(((VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class)).getFormattedText());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(int codepoint, Style style) {
        BakedGlyph glyph = this.glyphProvider.glyphs(style.getGlyphSourceDescription()).getGlyph(codepoint);
        if (glyph == null) {
            return 0.0f;
        }
        return glyph.description().getAdvance(style.hasDecoration(TextDecoration.BOLD));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getLineHeight() {
        return getFont().a;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public String trimTextToWidth(String text, float maxWidth, StringStart start) {
        return "";
    }

    private bip getFont() {
        return bib.z().k;
    }

    public void reload(ResourceLocation location) {
        VanillaGlyphStorage vanillaGlyphStorage = Laby.references().vanillaGlyphStorage();
        if (vanillaGlyphStorage instanceof VersionedVanillaGlyphStorage) {
            VersionedVanillaGlyphStorage versionedVanillaGlyphStorage = (VersionedVanillaGlyphStorage) vanillaGlyphStorage;
            versionedVanillaGlyphStorage.reload(location);
        }
    }

    private <T> void drawTextWithBackground(GlRenderDevice renderDevice, bip font, T t, float x, float y, int backgroundArgb, int flags, TextWidthProvider<T> provider) {
        renderDevice.storeState();
        bus.m();
        bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, GlConst.GL_ONE_MINUS_SRC_ALPHA);
        bus.q();
        bus.k();
        bus.c(GlConst.GL_LEQUAL);
        if (ColorFormat.ARGB32.alpha(backgroundArgb) != 0) {
            int width = provider.getWidth(font, t);
            float red = ColorFormat.ARGB32.normalizedRed(backgroundArgb);
            float green = ColorFormat.ARGB32.normalizedGreen(backgroundArgb);
            float blue = ColorFormat.ARGB32.normalizedBlue(backgroundArgb);
            float alpha = ColorFormat.ARGB32.normalizedAlpha(backgroundArgb);
            float startX = x - 1.0f;
            float endX = x + width;
            float topY = y - 1.0f;
            float bottomY = y + font.a;
            bve tessellator = bve.a();
            buk bufferBuilder = tessellator.c();
            bufferBuilder.a(7, cdy.f);
            bufferBuilder.b(startX, topY, UNDER_EFFECT_DEPTH).a(red, green, blue, alpha).d();
            bufferBuilder.b(startX, bottomY, UNDER_EFFECT_DEPTH).a(red, green, blue, alpha).d();
            bufferBuilder.b(endX, bottomY, UNDER_EFFECT_DEPTH).a(red, green, blue, alpha).d();
            bufferBuilder.b(endX, topY, UNDER_EFFECT_DEPTH).a(red, green, blue, alpha).d();
            bus.z();
            bus.a(false);
            bus.j();
            tessellator.b();
            bus.y();
        }
        if ((flags & 8) != 0) {
            bus.a(false);
            bus.j();
        }
        if ((flags & 16) != 0) {
            bus.s();
            bus.a(-1.0f, -10.0f);
        }
    }

    private void restoreStates(GlRenderDevice renderDevice) {
        renderDevice.restoreState();
    }

    private void pushMatrix(Matrix4f matrix) {
        bus.G();
        bus.F();
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        matrix.get(buffer);
        bus.a(buffer);
        MemoryUtil.memFree(buffer);
    }

    private void popMatrix() {
        bus.H();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VanillaFontRenderer$StyledTextProcessor.class */
    private static class StyledTextProcessor implements GlyphProcessor {
        private final VanillaFontRenderer fontRenderer;
        private final TextStateBuilder builder = new TextStateBuilder();
        private final int argb;
        private final int backgroundArgb;
        private final boolean drawShadow;
        private final float startX;
        private float x;
        private final float y;

        public StyledTextProcessor(VanillaFontRenderer fontRenderer, float x, float y, int argb, int backgroundArgb, int flags) {
            this.fontRenderer = fontRenderer;
            this.startX = x;
            this.x = x;
            this.y = y;
            this.argb = argb;
            this.backgroundArgb = backgroundArgb;
            this.drawShadow = FontFlags.isShadow(flags);
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor
        public boolean accept(int position, Style style, int codepoint) {
            BakedGlyph glyph = GlyphUtil.getGlyph(this.fontRenderer.glyphProvider, style, codepoint);
            if (glyph == null) {
                return true;
            }
            GlyphDescription description = glyph.description();
            boolean bold = style.hasDecoration(TextDecoration.BOLD);
            float boldOffset = bold ? description.getBoldOffset() : 0.0f;
            int textColor = getTextColor(style.getColor());
            int shadowColor = getShadowColor(style, textColor);
            float advance = description.getAdvance(bold);
            StyledGlyphRenderable renderable = glyph.createGlyph(this.x, this.y, textColor, shadowColor, style, boldOffset, description.getShadowOffset());
            if (renderable != null) {
                addGlyph(renderable);
            }
            if (style.hasDecoration(TextDecoration.STRIKETHROUGH)) {
                float linePosition = this.y + (this.fontRenderer.getLineHeight() / 2.0f);
                this.builder.addEffect(this.fontRenderer.glyphProvider.effect().createEffect(this.x, linePosition - 1.0f, this.x + advance, linePosition, 0.0f, textColor, shadowColor, description.getShadowOffset()));
            }
            if (style.hasDecoration(TextDecoration.UNDERLINED)) {
                float linePosition2 = this.y + this.fontRenderer.getLineHeight();
                this.builder.addEffect(this.fontRenderer.glyphProvider.effect().createEffect(this.x, linePosition2 - 1.0f, this.x + advance, linePosition2, 0.0f, textColor, shadowColor, description.getShadowOffset()));
            }
            this.x += advance;
            return true;
        }

        private void addGlyph(StyledGlyphRenderable glyph) {
            this.builder.addGlyph(glyph);
        }

        private int getTextColor(TextColor color) {
            if (color == null) {
                return this.argb;
            }
            int alpha = ColorFormat.ARGB32.alpha(this.argb);
            int rgb = color.getValue();
            return ColorFormat.ARGB32.pack(rgb, alpha);
        }

        private int getShadowColor(Style style, int color) {
            Integer shadowColor = style.getShadowColor();
            if (shadowColor == null) {
                if (this.drawShadow) {
                    return ColorFormat.ARGB32.scaleRGB(color, 0.25f);
                }
                return 0;
            }
            float colorAlpha = ColorFormat.ARGB32.normalizedAlpha(color);
            float shadowAlpha = ColorFormat.ARGB32.normalizedAlpha(shadowColor.intValue());
            if (colorAlpha == 1.0f) {
                return shadowColor.intValue();
            }
            return ColorFormat.ARGB32.pack(shadowColor.intValue(), ColorFormat.ARGB32.normalize(colorAlpha * shadowAlpha));
        }

        public TextState build() {
            if (ColorFormat.ARGB32.alpha(this.backgroundArgb) != 0) {
                this.builder.addEffect(this.fontRenderer.glyphProvider.effect().createEffect(this.startX - 1.0f, this.y - 1.0f, this.x, this.y + this.fontRenderer.getLineHeight(), 0.01f, this.backgroundArgb, 0, 0.0f));
            }
            return this.builder.build();
        }
    }
}
