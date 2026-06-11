package net.labymod.v1_20_1.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gfx/pipeline/renderer/text/FontTextureExtension.class */
public interface FontTextureExtension {
    void labyMod$setTextureLocation(ResourceLocation resourceLocation);

    static FontTextureExtension cast(Object obj) {
        return (FontTextureExtension) CastUtil.requireInstanceOf(obj, FontTextureExtension.class);
    }
}
