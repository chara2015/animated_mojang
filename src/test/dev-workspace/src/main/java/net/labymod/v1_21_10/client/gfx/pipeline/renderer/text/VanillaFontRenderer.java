package net.labymod.v1_21_10.client.gfx.pipeline.renderer.text;

import java.util.Objects;
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
import net.labymod.v1_21_10.client.StringSplitterAccessor;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gfx/pipeline/renderer/text/VanillaFontRenderer.class */
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
        gda font = getFont();
        font.a(text, x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        gda font = getFont();
        font.a((xx) this.componentMapper.toMinecraftComponent(text), x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class);
        gda font = getFont();
        font.a(vanillaText.getSequence(), x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public TextState prepareText(FormattedTextLayout textLayout, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(textLayout, VanillaFormattedTextLayout.class);
        gda font = getFont();
        TextStateVisitor builder = new TextStateVisitor();
        font.a(vanillaText.getSequence(), x, y, argb, FontFlags.isShadow(flags), backgroundArgb).a(builder);
        return builder.build();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(String text) {
        return getFont().b(text);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(Component text) {
        return getFont().a((xx) this.componentMapper.toMinecraftComponent(text));
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
        gaj splitter = getFont().b();
        if (start == StringStart.LEFT) {
            return splitter.b(text, MathHelper.ceil(maxWidth), yv.a);
        }
        return splitter.c(text, MathHelper.ceil(maxWidth), yv.a);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer
    public float getWidth(int codepoint, Style style) {
        return StringSplitterAccessor.cast(getFont().b()).getWidthProvider().getWidth(codepoint, (yv) CastUtil.cast(style));
    }

    private hfw getBufferSource() {
        return fzz.W().aU().c();
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

    private gda getFont() {
        return fzz.W().g;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gfx/pipeline/renderer/text/VanillaFontRenderer$TextStateVisitor.class */
    private static class TextStateVisitor implements b {
        private final TextStateBuilder builder = new TextStateBuilder();

        public void a(gih textRenderable) {
            this.builder.addGlyph(new VanillaRenderedGlyph(textRenderable));
        }

        public void b(gih textRenderable) {
            this.builder.addEffect(new VanillaRenderedEffect(textRenderable));
        }

        public TextState build() {
            return this.builder.build();
        }
    }
}
