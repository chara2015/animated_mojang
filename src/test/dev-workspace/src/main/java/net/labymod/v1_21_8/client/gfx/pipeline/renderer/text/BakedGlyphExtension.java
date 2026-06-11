package net.labymod.v1_21_8.client.gfx.pipeline.renderer.text;

import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gfx/pipeline/renderer/text/BakedGlyphExtension.class */
public interface BakedGlyphExtension {
    boolean labyMod$isColored();

    void labyMod$setColored(boolean z);

    static BakedGlyphExtension cast(Object obj) {
        return (BakedGlyphExtension) CastUtil.requireInstanceOf(obj, BakedGlyphExtension.class);
    }
}
