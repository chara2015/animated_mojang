package net.labymod.v1_16_5.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/BakedGlyphExtension.class */
public interface BakedGlyphExtension {
    boolean labyMod$isColored();

    void labyMod$setColored(boolean z);

    ResourceLocation labyMod$getTextureLocation();

    void labyMod$setTextureLocation(ResourceLocation resourceLocation);

    float labyMod$getLeft();

    float labyMod$getRight();

    float labyMod$getUp();

    float labyMod$getDown();

    static BakedGlyphExtension cast(Object obj) {
        return (BakedGlyphExtension) CastUtil.requireInstanceOf(obj, BakedGlyphExtension.class);
    }
}
