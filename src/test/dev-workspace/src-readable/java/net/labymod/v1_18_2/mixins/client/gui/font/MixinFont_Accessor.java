package net.labymod.v1_18_2.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_18_2.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({dzp.class})
public abstract class MixinFont_Accessor implements FontAccessor {
    @Shadow
    abstract ebr a(yt ytVar);

    @Override // net.labymod.v1_18_2.client.gfx.pipeline.renderer.text.FontAccessor
    public ebr labyMod$getFontSet(yt location) {
        return a(location);
    }

    @Override // net.labymod.v1_18_2.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return false;
    }

    @Override // net.labymod.v1_18_2.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        ebu ebuVarB;
        qu mcStyle = (qu) CastUtil.cast(style);
        ebr fontSet = a(mcStyle.k());
        drq glyphInfo = fontSet.a(codepoint);
        if (mcStyle.f() && codepoint != 32) {
            ebuVarB = fontSet.a(glyphInfo);
        } else {
            ebuVarB = fontSet.b(codepoint);
        }
        ebu glyph = ebuVarB;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
