package net.labymod.v1_21.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({fhx.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean g;

    @Shadow
    abstract fld a(akr akrVar);

    @Override // net.labymod.v1_21.client.gfx.pipeline.renderer.text.FontAccessor
    public fld labyMod$getFontSet(akr location) {
        return a(location);
    }

    @Override // net.labymod.v1_21.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.g;
    }

    @Override // net.labymod.v1_21.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        flh flhVarA;
        xw mcStyle = (xw) CastUtil.cast(style);
        fld fontSet = a(mcStyle.k());
        ezl glyphInfo = fontSet.a(codepoint, this.g);
        if (mcStyle.f() && codepoint != 32) {
            flhVarA = fontSet.a(glyphInfo);
        } else {
            flhVarA = fontSet.a(codepoint);
        }
        flh glyph = flhVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
