package net.labymod.v1_16_5.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({dku.class})
public abstract class MixinFont_Accessor implements FontAccessor {
    @Shadow
    protected abstract dmw a(vk vkVar);

    @Override // net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontAccessor
    public dmw labyMod$getFontSet(vk location) {
        return a(location);
    }

    @Override // net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontAccessor
    public boolean labyMod$filterFishyGlyphs() {
        return false;
    }

    @Override // net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontAccessor
    public FontAccessor.MinecraftGlyph labyMod$getGlyph(int codepoint, Style style) {
        dmz dmzVarB;
        ob mcStyle = (ob) CastUtil.cast(style);
        dmw fontSet = a(mcStyle.k());
        dea glyphInfo = fontSet.a(codepoint);
        if (mcStyle.f() && codepoint != 32) {
            dmzVarB = fontSet.a(glyphInfo);
        } else {
            dmzVarB = fontSet.b(codepoint);
        }
        dmz glyph = dmzVarB;
        return new FontAccessor.MinecraftGlyph(glyph, glyphInfo);
    }
}
