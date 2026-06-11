package net.labymod.v1_21_11.client.gfx.pipeline.blaze3d.program;

import java.util.function.Function;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/blaze3d/program/LabyRenderTypes.class */
public final class LabyRenderTypes {
    private static final Function<TextureBindingSet, RenderType> ENTITY_TRANSLUCENT = Util.memoize(textureBindingSet -> {
        RenderSetup setup = LabyRenderSetup.labyBuilder(RenderPipelines.ENTITY_TRANSLUCENT).withTextures(textureBindingSet).build();
        return RenderType.create("laby_entity_translucent", setup);
    });

    public static RenderType entityTranslucent(TextureBindingSet textureBindingSet) {
        return ENTITY_TRANSLUCENT.apply(textureBindingSet);
    }
}
