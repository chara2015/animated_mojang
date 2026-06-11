package net.labymod.v26_1_2.laby3d.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.loader.MinecraftVersions;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/laby3d/pipeline/LabyRenderPipelines.class */
public final class LabyRenderPipelines {
    private static String versionId;
    public static final RenderPipeline NAMETAG_ICON = RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelines.MATRICES_PROJECTION_SNIPPET}).withLocation(createPipelineId("nametag_icon")).withVertexFormat(LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS).withVertexShader(createShaderId("core/nametag_icon")).withFragmentShader(createShaderId("core/nametag_icon")).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true)).withShaderDefine("v26_1_2").withCull(true).withSampler("Sampler0").withSampler("Sampler2").build();
    public static final RenderPipeline SEE_THROUGH_NAMETAG_ICON = RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelines.MATRICES_PROJECTION_SNIPPET}).withLocation(createPipelineId("see_through_nametag_icon")).withVertexFormat(LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS).withVertexShader(createShaderId("core/see_through_nametag_icon")).withFragmentShader(createShaderId("core/see_through_nametag_icon")).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).withDepthStencilState(new DepthStencilState(CompareOp.NEVER_PASS, false)).withShaderDefine("v26_1_2").withCull(true).withSampler("Sampler0").withSampler("Sampler2").build();

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
