package net.labymod.v1_21_10.mixins.client.gui.font;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_10.client.gfx.pipeline.renderer.text.FontAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/font/MixinFont_Accessor.class */
@Mixin({gda.class})
public abstract class MixinFont_Accessor implements FontAccessor {
    @Shadow
    abstract gii a(int i, yv yvVar);

    @Shadow
    protected abstract gdb a(yc ycVar);

    @Override // net.labymod.v1_21_10.client.gfx.pipeline.renderer.text.FontAccessor
    public gii labyMod$getGlyph(int codepoint, Style style) {
        return a(codepoint, (yv) CastUtil.cast(style));
    }

    @Override // net.labymod.v1_21_10.client.gfx.pipeline.renderer.text.FontAccessor
    public gdb labyMod$getGlyphSource(yc description) {
        return a(description);
    }
}
