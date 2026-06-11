package net.labymod.api.laby3d.pipeline.material;

import java.util.function.BiFunction;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.util.function.FunctionMemoizeStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/LevelMaterials.class */
public final class LevelMaterials {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final BiFunction<ResourceLocation, TextDrawMode, Material> MSDF_FONT = MEMOIZE.memoize((location, drawMode) -> {
        return LevelMaterial.builder(RenderStates.getMsdfFont(drawMode)).setTexture(0, location).useLightmap().build();
    });

    public static Material getMsdfFont(ResourceLocation location, TextDrawMode drawMode) {
        return MSDF_FONT.apply(location, drawMode);
    }
}
