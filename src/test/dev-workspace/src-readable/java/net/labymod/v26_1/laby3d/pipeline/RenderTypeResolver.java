package net.labymod.v26_1.laby3d.pipeline;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.pipeline.material.TextureBinding;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.laby3d.api.resource.AssetId;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/laby3d/pipeline/RenderTypeResolver.class */
public final class RenderTypeResolver {
    private static final int SMALL_BUFFER_SIZE = 65536;
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final RenderPipelineLinker LINKER = new RenderPipelineLinker();
    private static final Function<LevelMaterial, RenderType> DYNAMIC_TYPES = MEMOIZE.memoize(levelMaterial -> {
        RenderSetup.RenderSetupBuilder builder = RenderSetup.builder(LINKER.get(levelMaterial.renderState())).bufferSize(65536);
        Int2ObjectMap<TextureBinding> textures = levelMaterial.getTextures();
        ObjectIterator it = textures.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<TextureBinding> entry = (Int2ObjectMap.Entry) it.next();
            builder.withTexture("Sampler" + entry.getIntKey(), (Identifier) ((TextureBinding) entry.getValue()).location().getMinecraftLocation());
        }
        if (levelMaterial.isUseLightmap()) {
            builder.useLightmap();
        }
        if (levelMaterial.isUseOverlay()) {
            builder.useOverlay();
        }
        return RenderType.create(getRenderTypeName(levelMaterial.id()), builder.createRenderSetup());
    });

    private static String getRenderTypeName(AssetId id) {
        return id.namespace() + ":" + id.path() + "_render_type";
    }

    public RenderType resolve(Material material) {
        if (!(material instanceof LevelMaterial)) {
            throw new IllegalStateException("Material " + material.getClass().getName() + " cannot be resolved to a render type.");
        }
        LevelMaterial levelMaterial = (LevelMaterial) material;
        return DYNAMIC_TYPES.apply(levelMaterial);
    }
}
