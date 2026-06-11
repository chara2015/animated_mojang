package net.labymod.v1_20_6.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_20_6.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({fgr.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean g;

    @Shadow
    abstract fjx a(alf alfVar);

    @Override // net.labymod.v1_20_6.client.gfx.pipeline.renderer.text.FontAccessor
    public fjx labyMod$getFontSet(alf location) {
        return a(location);
    }

    @Override // net.labymod.v1_20_6.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.g;
    }

    @Override // net.labymod.v1_20_6.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        fkb fkbVarA;
        ym mcStyle = (ym) CastUtil.cast(style);
        fjx fontSet = a(mcStyle.k());
        eyc glyphInfo = fontSet.a(codepoint, this.g);
        if (mcStyle.f() && codepoint != 32) {
            fkbVarA = fontSet.a(glyphInfo);
        } else {
            fkbVarA = fontSet.a(codepoint);
        }
        fkb glyph = fkbVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
