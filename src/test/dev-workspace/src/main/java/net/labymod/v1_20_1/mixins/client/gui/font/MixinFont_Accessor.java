package net.labymod.v1_20_1.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_20_1.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({eov.class})
public abstract class MixinFont_Accessor implements FontAccessor {

    @Shadow
    @Final
    boolean g;

    @Shadow
    abstract ern a(acq acqVar);

    @Override // net.labymod.v1_20_1.client.gfx.pipeline.renderer.text.FontAccessor
    public ern labyMod$getFontSet(acq location) {
        return a(location);
    }

    @Override // net.labymod.v1_20_1.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return this.g;
    }

    @Override // net.labymod.v1_20_1.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        err errVarA;
        ts mcStyle = (ts) CastUtil.cast(style);
        ern fontSet = a(mcStyle.k());
        egl glyphInfo = fontSet.a(codepoint, this.g);
        if (mcStyle.f() && codepoint != 32) {
            errVarA = fontSet.a(glyphInfo);
        } else {
            errVarA = fontSet.a(codepoint);
        }
        err glyph = errVarA;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
