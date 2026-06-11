package net.labymod.v1_21_5.laby3d.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.loader.MinecraftVersions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/laby3d/pipeline/LabyRenderPipelines.class */
public final class LabyRenderPipelines {
    private static String versionId;
    public static final RenderPipeline NAMETAG_ICON = RenderPipeline.builder(new RenderPipeline.Snippet[]{grw.aJ}).withLocation(createPipelineId("nametag_icon")).withVertexFormat(LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, VertexFormat.b.h).withVertexShader(createShaderId("core/nametag_icon")).withFragmentShader(createShaderId("core/nametag_icon")).withShaderDefine("v1_21_5").withBlend(BlendFunction.TRANSLUCENT).withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST).withCull(true).withSampler("Sampler0").withSampler("Sampler2").build();
    public static final RenderPipeline SEE_THROUGH_NAMETAG_ICON = RenderPipeline.builder(new RenderPipeline.Snippet[]{grw.aJ}).withLocation(createPipelineId("see_through_nametag_icon")).withVertexFormat(LabyVertexFormat.POSITION_TEX_COLOR_LIGHTMAP, VertexFormat.b.h).withVertexShader(createShaderId("core/see_through_nametag_icon")).withFragmentShader(createShaderId("core/see_through_nametag_icon")).withShaderDefine("v1_21_5").withBlend(BlendFunction.TRANSLUCENT).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withDepthWrite(false).withCull(true).withSampler("Sampler0").withSampler("Sampler2").build();

    private static alr createShaderId(String id) {
        if (versionId == null) {
            versionId = MinecraftVersions.current().getFormattedVersion();
        }
        return alr.b("labymod", id + "_" + versionId);
    }

    private static alr createPipelineId(String id) {
        return alr.b("labymod", "pipeline/" + id);
    }
}
