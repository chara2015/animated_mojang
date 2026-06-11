package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf;

import java.util.Objects;
import java.util.Random;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;
import net.labymod.api.client.gfx.pipeline.renderer.text.StringSplitter;
import net.labymod.api.client.gfx.pipeline.renderer.text.StringStart;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphUtil;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextStateBuilder;
import net.labymod.api.client.render.font.text.StringIterator;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.buffer.RenderingResourceAllocator;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource.LegacyMSDFResourceProvider;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/LegacyMSDFFontRenderer.class */
public class LegacyMSDFFontRenderer implements FontRenderer {
    private static final float EFFECT_DEPTH = 0.01f;
    private static final float OVER_EFFECT_DEPTH = 0.01f;
    private static final float UNDER_EFFECT_DEPTH = -0.01f;
    private final Laby3D laby3D;
    private final FormattedTextLayout.Factory textLayoutFactory;
    private final LegacyMSDFGlyphStorage glyphStorage = new LegacyMSDFGlyphStorage();
    private final LegacyMSDFResourceProvider resourceProvider = new LegacyMSDFResourceProvider(this.glyphStorage);
    private final GlyphProvider glyphProvider = new LegacyMSDFGlyphProvider(this.glyphStorage);
    private final RenderingResourceAllocator.Impl resourceAllocator = new RenderingResourceAllocator.Impl(new ByteBufferBuilder(786432));
    private final StringSplitter splitter = new StringSplitter((codepoint, style) -> {
        BakedGlyph glyph = this.glyphProvider.glyphs(style.getGlyphSourceDescription()).getGlyph(codepoint);
        if (glyph == null) {
            return 0.0f;
        }
        boolean bold = style.hasDecoration(TextDecoration.BOLD);
        return glyph.description().getAdvance(bold);
    });
    private final GlyphProperties properties = new GlyphProperties();

    public LegacyMSDFFontRenderer() {
        ReferenceStorage references = Laby.references();
        this.laby3D = references.laby3D();
        this.textLayoutFactory = references.formattedTextLayoutFactory();
        ResourcesReloadWatcher resourcesReloadWatcher = references.resourcesReloadWatcher();
        LegacyMSDFResourceProvider legacyMSDFResourceProvider = this.resourceProvider;
        Objects.requireNonNull(legacyMSDFResourceProvider);
        resourcesReloadWatcher.addInitializeListener(legacyMSDFResourceProvider::reload);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, String text, float x, float y, int argb, int lightCoords, int backgroundArgb, int flags) {
        StyledTextProcessor builder = new StyledTextProcessor(this, this.glyphProvider, x, y, argb, backgroundArgb, flags);
        StringIterator.iterateFormatted(text, Style.EMPTY, false, builder);
        TextState textState = builder.build();
        textState.buildVertices(this.resourceAllocator, pose, lightCoords);
        this.resourceAllocator.endBatch();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        FormattedTextLayout layout = this.textLayoutFactory.create(text);
        render(pose, layout, x, y, argb, packedLightCoords, backgroundArgb, flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int lightCoords, int backgroundArgb, int flags) {
        StyledTextProcessor builder = new StyledTextProcessor(this, this.glyphProvider, x, y, argb, backgroundArgb, flags);
        text.format(builder);
        TextState textState = builder.build();
        textState.buildVertices(this.resourceAllocator, pose, lightCoords);
        this.resourceAllocator.endBatch();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public TextState prepareText(FormattedTextLayout textLayout, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        StyledTextProcessor builder = new StyledTextProcessor(this, this.glyphProvider, x, y, argb, backgroundArgb, flags);
        textLayout.format(builder);
        return builder.build();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(String text) {
        return MathHelper.ceil(this.splitter.getTextWidth(text));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(Component text) {
        return MathHelper.ceil(this.splitter.getTextWidth(text));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(FormattedTextLayout text) {
        return MathHelper.ceil(this.splitter.getTextWidth(text));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getLineHeight() {
        return this.resourceProvider.getLineHeight();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public String trimTextToWidth(String text, float maxWidth, StringStart start) {
        if (start == StringStart.LEFT) {
            return this.splitter.plainHeadByWidth(text, maxWidth, Style.EMPTY);
        }
        return this.splitter.plainTailByWidth(text, maxWidth, Style.EMPTY);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(int codepoint, Style style) {
        return this.splitter.getWidthProvider().getWidth(codepoint, style);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    @Nullable
    public <V> V getValue(@NotNull ResourceLocation resourceLocation) {
        return (V) CastUtil.cast(this.properties.getProperty(resourceLocation));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public <V> V getValue(@NotNull ResourceLocation resourceLocation, V v) {
        Object property = this.properties.getProperty(resourceLocation);
        if (property == null) {
            return v;
        }
        return (V) CastUtil.cast(property);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public <V> void setValue(@NotNull ResourceLocation key, @Nullable V value) {
        this.properties.setProperty(key, value);
    }

    protected GlyphProperties createProperties() {
        return GlyphProperties.copyOf(this.properties);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/LegacyMSDFFontRenderer$StyledTextProcessor.class */
    private class StyledTextProcessor implements GlyphProcessor {
        private static final Random RANDOM = new Random();
        private final FontRenderer fontRenderer;
        private final GlyphProvider glyphProvider;
        private final TextStateBuilder builder = new TextStateBuilder();
        private final int argb;
        private final int backgroundArgb;
        private final boolean drawShadow;
        private final float startX;
        private float x;
        private final float y;

        public StyledTextProcessor(FontRenderer fontRenderer, GlyphProvider glyphProvider, float x, float y, int argb, int backgroundArgb, int flags) {
            this.fontRenderer = fontRenderer;
            this.glyphProvider = glyphProvider;
            this.startX = x;
            this.x = x;
            this.y = y;
            this.argb = argb;
            this.backgroundArgb = backgroundArgb;
            this.drawShadow = FontFlags.isShadow(flags);
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor
        public boolean accept(int position, Style style, int codepoint) {
            BakedGlyph glyph = GlyphUtil.getGlyph(this.glyphProvider, style, codepoint);
            if (glyph == null) {
                return true;
            }
            GlyphDescription description = glyph.description();
            boolean bold = style.hasDecoration(TextDecoration.BOLD);
            int textColor = getTextColor(style.getColor());
            int shadowColor = getShadowColor(style, textColor);
            float advance = description.getAdvance(bold);
            float shadowOffset = description.getShadowOffset();
            float boldOffset = bold ? description.getBoldOffset() : 0.0f;
            StyledGlyphRenderable renderable = glyph.createGlyph(this.x, this.y, textColor, shadowColor, style, boldOffset, shadowOffset);
            if (renderable != null) {
                addGlyph(renderable);
            }
            if (style.hasDecoration(TextDecoration.STRIKETHROUGH)) {
                float linePosition = this.y + (this.fontRenderer.getLineHeight() / 2.0f);
                addEffect(this.glyphProvider.effect().createEffect(this.x, linePosition - 1.0f, this.x + advance, linePosition, 0.01f, textColor, shadowColor, shadowOffset));
            }
            if (style.hasDecoration(TextDecoration.UNDERLINED)) {
                float linePosition2 = this.y + this.fontRenderer.getLineHeight();
                addEffect(this.glyphProvider.effect().createEffect(this.x, linePosition2 - 1.0f, this.x + advance, linePosition2, LegacyMSDFFontRenderer.UNDER_EFFECT_DEPTH, textColor, shadowColor, shadowOffset));
            }
            this.x += advance;
            return true;
        }

        private void addGlyph(StyledGlyphRenderable renderable) {
            this.builder.addGlyph(renderable);
        }

        private void addEffect(GlyphRenderable renderable) {
            this.builder.addEffect(renderable);
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
                addEffect(this.glyphProvider.effect().createEffect(this.startX, this.y - 1.0f, this.x, (this.y + this.fontRenderer.getLineHeight()) - 1.0f, LegacyMSDFFontRenderer.UNDER_EFFECT_DEPTH, this.backgroundArgb, 0, 0.0f));
            }
            return this.builder.build(LegacyMSDFFontRenderer.this.createProperties());
        }
    }
}
