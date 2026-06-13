package net.labymod.api.laby3d.pipeline;

import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlock;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.blend.DefaultBlendFunctions;
import net.labymod.laby3d.api.pipeline.shader.ShaderProgramDescription;
import net.labymod.laby3d.api.pipeline.shader.UniformSamplerDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/ItemRenderStates.class */
public final class ItemRenderStates {
    private static final String MAX_BONES_FLAG = "MAX_BONES";
    private static final String MODEL_VIEW_MATRIX_LIGHTING_FLAG = "MODEL_VIEW_MATRIX_LIGHTING";
    private static final boolean USE_MODEL_VIEW_MATRIX_FOR_LIGHTING = MinecraftVersions.V1_16_5.orOlder();
    public static final ShaderProgramDescription.Snippet COSMETICS_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(9).setLayout(CosmeticsUniformBlock.LAYOUT).setName(CosmeticsUniformBlock.NAME);
    }).buildSnippet();
    public static final ShaderProgramDescription.Snippet COSMETICS_SHADER_PROGRAM_SNIPPET = buildDefaultShaderProgramSnippet();
    private static final String TRANSLUCENT_COSMETICS_NAME = "translucent_cosmetics";
    public static final ShaderProgramDescription.Snippet COSMETICS_TRANSLUCENT_SHADER_PROGRAM_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(TRANSLUCENT_COSMETICS_NAME)).setDefine("ALPHA_CUTOUT", 0.1f).buildSnippet();
    private static final String OPAQUE_COSMETICS_NAME = "opaque_cosmetics";
    public static final RenderState OPAQUE_COSMETICS = RenderState.builder(new RenderState.Snippet[0]).setId(RenderStates.buildStateId(OPAQUE_COSMETICS_NAME)).setDrawingMode(DrawingMode.QUADS).setVertexDescription(VertexDescriptions.COSMETIC).setCull(false).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(OPAQUE_COSMETICS_NAME)).build()).build();
    private static final String RETAINED_OPAQUE_COSMETICS_NAME = "retained_opaque_cosmetics";
    private static final String RETAINED_FLAG = "RETAINED";
    public static final RenderState RETAINED_OPAQUE_COSMETICS = RenderState.builder(new RenderState.Snippet[]{OPAQUE_COSMETICS.toSnippet()}).setId(RenderStates.buildStateId(RETAINED_OPAQUE_COSMETICS_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(RETAINED_OPAQUE_COSMETICS_NAME)).setFlag(RETAINED_FLAG).build()).build();
    public static final RenderState TRANSLUCENT_COSMETICS = RenderState.builder(new RenderState.Snippet[]{OPAQUE_COSMETICS.toSnippet()}).setId(RenderStates.buildStateId(TRANSLUCENT_COSMETICS_NAME)).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_TRANSLUCENT_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(TRANSLUCENT_COSMETICS_NAME)).build()).build();
    private static final String EMISSIVE_TRANSLUCENT_COSMETICS_NAME = "emissive_translucent_cosmetics";
    private static final String EMISSIVE_FLAG = "EMISSIVE";
    public static final RenderState EMISSIVE_TRANSLUCENT_COSMETICS = RenderState.builder(new RenderState.Snippet[]{TRANSLUCENT_COSMETICS.toSnippet()}).setId(RenderStates.buildStateId(EMISSIVE_TRANSLUCENT_COSMETICS_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_TRANSLUCENT_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(EMISSIVE_TRANSLUCENT_COSMETICS_NAME)).setFlag(EMISSIVE_FLAG).build()).build();
    private static final String RETAINED_TRANSLUCENT_COSMETICS_NAME = "retained_translucent_cosmetics";
    public static final RenderState RETAINED_TRANSLUCENT_COSMETICS = RenderState.builder(new RenderState.Snippet[]{TRANSLUCENT_COSMETICS.toSnippet()}).setId(RenderStates.buildStateId(RETAINED_TRANSLUCENT_COSMETICS_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(RETAINED_TRANSLUCENT_COSMETICS_NAME)).setDefine("ALPHA_CUTOUT", 0.1f).setFlag(RETAINED_FLAG).build()).build();
    private static final String OUTLINE_COSMETICS_NAME = "outline_cosmetics";
    public static final RenderState OUTLINE_COSMETICS = RenderState.builder(new RenderState.Snippet[]{TRANSLUCENT_COSMETICS.toSnippet()}).setId(RenderStates.buildStateId(OUTLINE_COSMETICS_NAME)).setCull(true).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_TRANSLUCENT_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(OUTLINE_COSMETICS_NAME)).build()).build();
    private static final String RETAINED_OUTLINE_COSMETICS_NAME = "retained_outline_cosmetics";
    public static final RenderState RETAINED_OUTLINE_COSMETICS = RenderState.builder(new RenderState.Snippet[]{OUTLINE_COSMETICS.toSnippet()}).setId(RenderStates.buildStateId(RETAINED_OUTLINE_COSMETICS_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{COSMETICS_SHADER_PROGRAM_SNIPPET}).setId(RenderStates.buildProgramId(RETAINED_OUTLINE_COSMETICS_NAME)).setDefine("ALPHA_CUTOUT", 0.1f).setFlag(EMISSIVE_FLAG).setFlag(RETAINED_FLAG).build()).build();

    private static ShaderProgramDescription.Snippet buildDefaultShaderProgramSnippet() {
        ShaderProgramDescription.Builder builder = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{RenderStates.DEFAULT_SHADER_SNIPPET, COSMETICS_SHADER_SNIPPET, RenderStates.LIGHTING_SHADER_SNIPPET});
        if (USE_MODEL_VIEW_MATRIX_FOR_LIGHTING) {
            builder.setFlag(MODEL_VIEW_MATRIX_LIGHTING_FLAG);
        }
        return builder.setVertexShader(RenderStates.SHADER_RESOLVER.apply("core/cosmetics.vsh")).setFragmentShader(RenderStates.SHADER_RESOLVER.apply("core/cosmetics.fsh")).setDefine(MAX_BONES_FLAG, 240).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).addSampler(new UniformSamplerDescription("OverlaySampler", 1)).addSampler(new UniformSamplerDescription("LightSampler", 2)).buildSnippet();
    }
}
