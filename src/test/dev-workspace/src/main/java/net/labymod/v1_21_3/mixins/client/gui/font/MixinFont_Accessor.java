package net.labymod.v1_21_3.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_3.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({fnq.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean g;

    @Shadow
    abstract fqy a(alz alzVar);

    @Override // net.labymod.v1_21_3.client.gfx.pipeline.renderer.text.FontAccessor
    public fqy labyMod$getFontSet(alz location) {
        return a(location);
    }

    @Override // net.labymod.v1_21_3.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.g;
    }

    @Override // net.labymod.v1_21_3.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        frc frcVarA;
        ys mcStyle = (ys) CastUtil.cast(style);
        fqy fontSet = a(mcStyle.k());
        feq glyphInfo = fontSet.a(codepoint, this.g);
        if (mcStyle.f() && codepoint != 32) {
            frcVarA = fontSet.a(glyphInfo);
        } else {
            frcVarA = fontSet.a(codepoint);
        }
        frc glyph = frcVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
