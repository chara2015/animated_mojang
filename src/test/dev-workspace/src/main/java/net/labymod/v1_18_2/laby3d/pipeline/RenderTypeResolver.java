package net.labymod.v1_18_2.laby3d.pipeline;

import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/laby3d/pipeline/RenderTypeResolver.class */
public final class RenderTypeResolver {
    private static final int SMALL_BUFFER_SIZE = 65536;
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final RenderPipelineLinker LINKER = new RenderPipelineLinker();
    private static final Function<LevelMaterial, era> DYNAMIC_TYPES = MEMOIZE.memoize(levelMaterial -> {
        MinecraftRenderState renderState = LINKER.get(levelMaterial.renderState());
        return era.a(getRenderTypeName(levelMaterial.id()), renderState.getVertexFormat(), renderState.getDrawMode(), 65536, renderState.affectsCrumbling(), renderState.sortOnUpload(), renderState.create(levelMaterial, false));
    });

    private static String getRenderTypeName(AssetId id) {
        return id.namespace() + ":" + id.path() + "_render_type";
    }

    public era resolve(Material material) {
        if (!(material instanceof LevelMaterial)) {
            throw new IllegalStateException("Material " + material.getClass().getName() + " cannot be resolved to a render type.");
        }
        LevelMaterial levelMaterial = (LevelMaterial) material;
        return DYNAMIC_TYPES.apply(levelMaterial);
    }
}
