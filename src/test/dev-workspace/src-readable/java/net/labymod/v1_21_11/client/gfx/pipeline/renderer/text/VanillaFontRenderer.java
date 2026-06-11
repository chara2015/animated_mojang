package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
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
import net.labymod.v1_21_11.client.StringSplitterAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Style;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VanillaFontRenderer.class */
@Singleton
@Implements(MinecraftFontRenderer.class)
public class VanillaFontRenderer implements MinecraftFontRenderer {
    private static final Logging LOGGER = Logging.getLogger();
    private final ComponentMapper componentMapper;

    @Inject
    public VanillaFontRenderer(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    public void render(Matrix4f pose, String text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        Font font = getFont();
        font.drawInBatch(text, x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    public void render(Matrix4f pose, Component text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        Font font = getFont();
        font.drawInBatch((net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(text), x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    public void render(Matrix4f pose, FormattedTextLayout text, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class);
        Font font = getFont();
        font.drawInBatch(vanillaText.getSequence(), x, y, argb, FontFlags.isShadow(flags), pose, getBufferSource(), getDisplayMode(flags), backgroundArgb, packedLightCoords);
    }

    public TextState prepareText(FormattedTextLayout textLayout, float x, float y, int argb, int packedLightCoords, int backgroundArgb, int flags) {
        VanillaFormattedTextLayout vanillaText = (VanillaFormattedTextLayout) CastUtil.requireInstanceOf(textLayout, VanillaFormattedTextLayout.class);
        Font font = getFont();
        TextStateVisitor builder = new TextStateVisitor();
        font.prepareText(vanillaText.getSequence(), x, y, argb, FontFlags.isShadow(flags), false, backgroundArgb).visit(builder);
        return builder.build();
    }

    public float getWidth(String text) {
        return getFont().width(text);
    }

    public float getWidth(Component text) {
        return getFont().width((net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(text));
    }

    public float getWidth(FormattedTextLayout text) {
        return getFont().width(((VanillaFormattedTextLayout) CastUtil.requireInstanceOf(text, VanillaFormattedTextLayout.class)).getSequence());
    }

    public float getLineHeight() {
        Objects.requireNonNull(getFont());
        return 9.0f;
    }

    public String trimTextToWidth(String text, float maxWidth, StringStart start) {
        StringSplitter splitter = getFont().getSplitter();
        if (start == StringStart.LEFT) {
            return splitter.plainHeadByWidth(text, MathHelper.ceil(maxWidth), Style.EMPTY);
        }
        return splitter.plainTailByWidth(text, MathHelper.ceil(maxWidth), Style.EMPTY);
    }

    public float getWidth(int codepoint, net.labymod.api.client.component.format.Style style) {
        return StringSplitterAccessor.cast(getFont().getSplitter()).getWidthProvider().getWidth(codepoint, (Style) CastUtil.cast(style));
    }

    private MultiBufferSource getBufferSource() {
        return Minecraft.getInstance().renderBuffers().bufferSource();
    }

    private Font.DisplayMode getDisplayMode(int flags) {
        FontFlags.ensureOneDisplayMode(flags);
        if (FontFlags.hasFlag(flags, 4)) {
            return Font.DisplayMode.NORMAL;
        }
        if (FontFlags.hasFlag(flags, 8)) {
            return Font.DisplayMode.SEE_THROUGH;
        }
        if (FontFlags.hasFlag(flags, 16)) {
            return Font.DisplayMode.POLYGON_OFFSET;
        }
        if (IdeUtil.RUNNING_IN_IDE) {
            LOGGER.error("No display mode set for text", new Object[0]);
        }
        return Font.DisplayMode.NORMAL;
    }

    private Font getFont() {
        return Minecraft.getInstance().font;
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VanillaFontRenderer$TextStateVisitor.class */
    private static class TextStateVisitor implements Font.GlyphVisitor {
        private final TextStateBuilder builder = new TextStateBuilder();

        public void acceptGlyph(TextRenderable.Styled $$0) {
            this.builder.addGlyph(new VanillaRenderedGlyph($$0));
        }

        public void acceptEffect(TextRenderable textRenderable) {
            this.builder.addEffect(new VanillaRenderedEffect(textRenderable));
        }

        public TextState build() {
            return this.builder.build();
        }
    }
}
