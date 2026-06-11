package net.labymod.v1_21_5.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_5.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({fti.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean h;

    @Shadow
    abstract fwq a(alr alrVar);

    @Override // net.labymod.v1_21_5.client.gfx.pipeline.renderer.text.FontAccessor
    public fwq labyMod$getFontSet(alr location) {
        return a(location);
    }

    @Override // net.labymod.v1_21_5.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.h;
    }

    @Override // net.labymod.v1_21_5.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        fwu fwuVarA;
        yd mcStyle = (yd) CastUtil.cast(style);
        fwq fontSet = a(mcStyle.l());
        fir glyphInfo = fontSet.a(codepoint, this.h);
        if (mcStyle.g() && codepoint != 32) {
            fwuVarA = fontSet.a(glyphInfo);
        } else {
            fwuVarA = fontSet.a(codepoint);
        }
        fwu glyph = fwuVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
