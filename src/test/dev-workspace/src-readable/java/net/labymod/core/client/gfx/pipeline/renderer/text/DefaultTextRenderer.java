package net.labymod.core.client.gfx.pipeline.renderer.text;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.Font;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.Fonts;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.StringStart;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.LegacyMSDFFontRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/DefaultTextRenderer.class */
@Singleton
@Implements(TextRenderer.class)
public class DefaultTextRenderer implements TextRenderer {
    private static final Logging LOGGER = Logging.getLogger();
    private final Map<Font, FontRenderer> renderers = new HashMap();
    private Font currentFont;
    private FontRenderer current;

    public DefaultTextRenderer() {
        registerRenderer(Fonts.MINECRAFT, Laby.references().minecraftFontRenderer());
        registerRenderer(Fonts.LEGACY_MSDF, new LegacyMSDFFontRenderer());
        setCurrentFont(Fonts.LEGACY_MSDF);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, String text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        this.current.render(pose, text, x, y, argb, packedLightCoords, backgroundArgb, flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        this.current.render(pose, text, x, y, argb, packedLightCoords, backgroundArgb, flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        this.current.render(pose, text, x, y, argb, packedLightCoords, backgroundArgb, flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public TextState prepareText(FormattedTextLayout textLayout, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        return this.current.prepareText(textLayout, x, y, argb, packedLightCoords, backgroundArgb, flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(String text) {
        return this.current.getWidth(text);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(Component text) {
        return this.current.getWidth(text);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(FormattedTextLayout text) {
        return this.current.getWidth(text);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getLineHeight() {
        return this.current.getLineHeight();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public String trimTextToWidth(String text, float maxWidth, StringStart start) {
        return this.current.trimTextToWidth(text, maxWidth, start);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(int codepoint, Style style) {
        return this.current.getWidth(codepoint, style);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer
    public Font getCurrentFont() {
        return this.currentFont;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer
    public void setCurrentFont(Font font) {
        if (this.currentFont == font) {
            return;
        }
        this.currentFont = font;
        this.current = this.renderers.get(font);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer
    public FontRenderer getCurrent() {
        return this.current;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    @Nullable
    public <V> V getValue(@NotNull ResourceLocation resourceLocation) {
        return (V) this.current.getValue(resourceLocation);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public <V> V getValue(@NotNull ResourceLocation resourceLocation, V v) {
        return (V) this.current.getValue(resourceLocation, v);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public <V> void setValue(@NotNull ResourceLocation key, @Nullable V value) {
        this.current.setValue(key, value);
    }

    public String toString() {
        return this.currentFont.toString();
    }

    private void registerRenderer(Font font, FontRenderer renderer) {
        this.renderers.put(font, renderer);
        LOGGER.info("Registered font renderer for font '{}'.", font);
    }
}
