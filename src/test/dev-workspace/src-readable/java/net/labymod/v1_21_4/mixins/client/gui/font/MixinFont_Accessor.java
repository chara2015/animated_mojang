package net.labymod.v1_21_4.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({fod.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean h;

    @Shadow
    abstract frm a(akv akvVar);

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.FontAccessor
    public frm labyMod$getFontSet(akv location) {
        return a(location);
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.h;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        frq frqVarA;
        xm mcStyle = (xm) CastUtil.cast(style);
        frm fontSet = a(mcStyle.l());
        fdt glyphInfo = fontSet.a(codepoint, this.h);
        if (mcStyle.g() && codepoint != 32) {
            frqVarA = fontSet.a(glyphInfo);
        } else {
            frqVarA = fontSet.a(codepoint);
        }
        frq glyph = frqVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
