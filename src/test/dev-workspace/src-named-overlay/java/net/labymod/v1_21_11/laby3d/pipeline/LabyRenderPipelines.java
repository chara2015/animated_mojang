package net.labymod.v1_21_11.laby3d.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.loader.MinecraftVersions;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/pipeline/LabyRenderPipelines.class */
public final class LabyRenderPipelines {
    private static String versionId;
    public static final RenderPipeline NAMETAG_ICON = RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelines.MATRICES_PROJECTION_SNIPPET}).withLocation(createPipelineId("nametag_icon")).withVertexFormat(LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS).withVertexShader(createShaderId("core/nametag_icon")).withFragmentShader(createShaderId("core/nametag_icon")).withBlend(BlendFunction.TRANSLUCENT).withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST).withShaderDefine("v1_21_11").withCull(true).withSampler("Sampler0").withSampler("Sampler2").build();
    public static final RenderPipeline SEE_THROUGH_NAMETAG_ICON = RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelines.MATRICES_PROJECTION_SNIPPET}).withLocation(createPipelineId("see_through_nametag_icon")).withVertexFormat(LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS).withVertexShader(createShaderId("core/see_through_nametag_icon")).withFragmentShader(createShaderId("core/see_through_nametag_icon")).withBlend(BlendFunction.TRANSLUCENT).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withShaderDefine("v1_21_11").withDepthWrite(false).withCull(true).withSampler("Sampler0").withSampler("Sampler2").build();

    private static Identifier createShaderId(String id) {
        if (versionId == null) {
            versionId = MinecraftVersions.current().getFormattedVersion();
        }
        return Identifier.tryBuild("labymod", id + "_" + versionId);
    }

    private static Identifier createPipelineId(String id) {
        return Identifier.tryBuild("labymod", "pipeline/" + id);
    }
}
