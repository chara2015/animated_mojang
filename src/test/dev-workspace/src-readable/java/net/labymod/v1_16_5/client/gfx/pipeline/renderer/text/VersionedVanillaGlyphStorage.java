package net.labymod.v1_16_5.client.gfx.pipeline.renderer.text;

import javax.inject.Singleton;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage;
import net.labymod.api.models.Implements;
import net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage.class */
@Singleton
@Implements(VanillaGlyphStorage.class)
public class VersionedVanillaGlyphStorage extends VanillaGlyphStorage {
    public VersionedVanillaGlyphStorage() {
        super(x$0 -> {
            return new VersionedVanillaGlyph[x$0];
        }, x$02 -> {
            return new VersionedVanillaGlyph[x$02];
        });
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage
    protected BakedGlyph createGlyph(int codepoint) {
        dku font = djz.C().g;
        FontAccessor.MinecraftGlyph bakedGlyph = FontAccessor.self(font).labyMod$getGlyph(codepoint, Style.EMPTY);
        return new VersionedVanillaGlyph(bakedGlyph);
    }
}
