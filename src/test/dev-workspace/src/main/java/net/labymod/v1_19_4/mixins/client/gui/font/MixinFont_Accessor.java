package net.labymod.v1_19_4.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_19_4.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({enp.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean g;

    @Shadow
    abstract eqe a(add addVar);

    @Override // net.labymod.v1_19_4.client.gfx.pipeline.renderer.text.FontAccessor
    public eqe labyMod$getFontSet(add location) {
        return a(location);
    }

    @Override // net.labymod.v1_19_4.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.g;
    }

    @Override // net.labymod.v1_19_4.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        eqh eqhVarA;
        uf mcStyle = (uf) CastUtil.cast(style);
        eqe fontSet = a(mcStyle.k());
        efh glyphInfo = fontSet.a(codepoint, this.g);
        if (mcStyle.f() && codepoint != 32) {
            eqhVarA = fontSet.a(glyphInfo);
        } else {
            eqhVarA = fontSet.a(codepoint);
        }
        eqh glyph = eqhVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
