package net.labymod.v1_21_11.mixins.client.gui.font;

import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.gfx.pipeline.renderer.text.FontAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GlyphSource;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({Font.class})
public abstract class MixinFont_Accessor implements FontAccessor {
    @Shadow
    abstract BakedGlyph a(int i, Style style);

    @Shadow
    protected abstract GlyphSource a(FontDescription fontDescription);

    @Override // net.labymod.v1_21_11.client.gfx.pipeline.renderer.text.FontAccessor
    public BakedGlyph labyMod$getGlyph(int codepoint, net.labymod.api.client.component.format.Style style) {
        return a(codepoint, (Style) CastUtil.cast(style));
    }

    @Override // net.labymod.v1_21_11.client.gfx.pipeline.renderer.text.FontAccessor
    public GlyphSource labyMod$getGlyphSource(FontDescription description) {
        return a(description);
    }
}
