package net.labymod.v1_21_11.client.gfx.pipeline.blaze3d.program;

import java.util.function.Function;
import net.labymod.api.laby3d.textures.TextureBindingSet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gfx/pipeline/blaze3d/program/LabyRenderTypes.class */
public final class LabyRenderTypes {
    private static final Function<TextureBindingSet, ijs> ENTITY_TRANSLUCENT = bhs.b(textureBindingSet -> {
        ijr setup = LabyRenderSetup.labyBuilder(hpa.s).withTextures(textureBindingSet).build();
        return ijs.a("laby_entity_translucent", setup);
    });

    public static ijs entityTranslucent(TextureBindingSet textureBindingSet) {
        return ENTITY_TRANSLUCENT.apply(textureBindingSet);
    }
}
