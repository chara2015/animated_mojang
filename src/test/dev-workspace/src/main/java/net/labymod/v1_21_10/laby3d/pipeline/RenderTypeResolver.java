package net.labymod.v1_21_10.laby3d.pipeline;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.pipeline.material.TextureBinding;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/laby3d/pipeline/RenderTypeResolver.class */
public final class RenderTypeResolver {
    private static final int SMALL_BUFFER_SIZE = 65536;
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final RenderPipelineLinker LINKER = new RenderPipelineLinker();
    private static final Function<LevelMaterial, hgk> DYNAMIC_TYPES = MEMOIZE.memoize(levelMaterial -> {
        d dVar;
        i iVar;
        String renderTypeName = getRenderTypeName(levelMaterial.id());
        RenderPipeline renderPipeline = LINKER.get(levelMaterial.renderState());
        a aVarA = b.a().a(new j((amj) ((TextureBinding) levelMaterial.getTextures().get(0)).location().getMinecraftLocation(), false));
        if (levelMaterial.isUseLightmap()) {
            dVar = hgj.j;
        } else {
            dVar = hgj.k;
        }
        a aVarA2 = aVarA.a(dVar);
        if (levelMaterial.isUseOverlay()) {
            iVar = hgj.l;
        } else {
            iVar = hgj.m;
        }
        return hgk.a(renderTypeName, 65536, renderPipeline, aVarA2.a(iVar).a(false));
    });

    private static String getRenderTypeName(AssetId id) {
        return id.namespace() + ":" + id.path() + "_render_type";
    }

    public hgk resolve(Material material) {
        if (!(material instanceof LevelMaterial)) {
            throw new IllegalStateException("Material " + material.getClass().getName() + " cannot be resolved to a render type.");
        }
        LevelMaterial levelMaterial = (LevelMaterial) material;
        return DYNAMIC_TYPES.apply(levelMaterial);
    }
}
