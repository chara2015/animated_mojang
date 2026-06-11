package net.labymod.api.laby3d.pipeline;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.render.draw.BlurRenderer;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.shaders.block.BlurDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.CircleDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.ClipDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlock;
import net.labymod.api.laby3d.shaders.block.GlobalsUniformBlock;
import net.labymod.api.laby3d.shaders.block.LightingUniformBlock;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlock;
import net.labymod.api.laby3d.shaders.block.RoundDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.SchematicUniformBlock;
import net.labymod.api.laby3d.shaders.block.SprayDataUniformBlock;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.laby3d.api.pipeline.ComparisonStrategy;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.blend.DefaultBlendFunctions;
import net.labymod.laby3d.api.pipeline.shader.ShaderProgramDescription;
import net.labymod.laby3d.api.pipeline.shader.UniformSamplerDescription;
import net.labymod.laby3d.api.resource.AssetId;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/RenderStates.class */
public class RenderStates {
    protected static final int LEGACY_TEXTURE_SLOT = 1;
    protected static final int MODERN_TEXTURE_SLOT = 2;
    private static final String CUSTOM_BLIT_TARGET_PREFIX = "blit_render_target_";
    private static final String BLUR_PREFIX = "blur_";
    private static final String VANILLA_INTENSITY_FONT_NAME = "vanilla_intensity_font";
    public static final String LABYMOD_FLAG = "LABYMOD";
    public static final String LABYMOD_GUI_FLAG = "LABYMOD_GUI";
    private static final String VANILLA_INTENSITY_FONT = "VANILLA_INTENSITY_FONT";
    static final String ALPHA_CUTOUT_FLAG = "ALPHA_CUTOUT";
    public static final int PROJECTION_BINDING = 0;
    public static final int ROUND_DATA_BINDING = 1;
    public static final int CIRCLE_DATA_BINDING = 2;
    public static final int GLOBALS_BINDING = 3;
    public static final int SPRAY_DATA_BINDING = 4;
    public static final int SCHEMATIC_BINDING = 5;
    public static final int DYNAMIC_TRANSFORMS_BINDING = 6;
    public static final int BLUR_DATA_BINDING = 7;
    public static final int POST_PROCESS_BINDING = 8;
    public static final int COSMETICS_BINDING = 9;
    public static final int LIGHTING_BINDING = 10;
    public static final int CLIP_DATA_BINDING = 11;
    protected static final boolean USE_LEGACY_TEXTURE_SLOT = MinecraftVersions.V1_21_2.orOlder();
    private static final FunctionMemoizeStorage STORAGE = Laby.references().functionMemoizeStorage();
    public static final Function<String, AssetId> SHADER_RESOLVER = path -> {
        return AssetId.of("labymod", "shaders/" + path);
    };
    public static final Function<ResourceLocation, AssetId> LOCATION_MAPPER = location -> {
        return AssetId.of(location.getNamespace(), location.getPath());
    };
    public static final ShaderProgramDescription.Snippet PROJECTION_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(0).setName("Projection").setLayout(ProjectionUniformBlock.LAYOUT);
    }).buildSnippet();
    public static final ShaderProgramDescription.Snippet DYNAMIC_TRANSFORMS_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(6).setName("DynamicTransforms").setLayout(DynamicTransformsUniformBlock.LAYOUT);
    }).buildSnippet();
    public static final ShaderProgramDescription.Snippet GLOBALS_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(3).setName(GlobalsUniformBlock.NAME).setLayout(GlobalsUniformBlock.LAYOUT);
    }).buildSnippet();
    public static final ShaderProgramDescription.Snippet LIGHTING_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(10).setLayout(LightingUniformBlock.LAYOUT).setName(LightingUniformBlock.NAME);
    }).buildSnippet();
    public static final ShaderProgramDescription.Snippet DEFAULT_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{PROJECTION_SHADER_SNIPPET, DYNAMIC_TRANSFORMS_SNIPPET, GLOBALS_SHADER_SNIPPET}).buildSnippet();
    private static final String ROUNDED_FLAG = "ROUNDED";
    public static final ShaderProgramDescription.Snippet ROUND_DATA_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(1).setName(RoundDataUniformBlock.NAME).setLayout(RoundDataUniformBlock.LAYOUT);
    }).setFlag(ROUNDED_FLAG).buildSnippet();
    public static final ShaderProgramDescription.Snippet CLIP_DATA_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(11).setName(ClipDataUniformBlock.NAME).setLayout(ClipDataUniformBlock.LAYOUT);
    }).buildSnippet();
    public static final ShaderProgramDescription.Snippet CIRCLE_DATA_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).addUniformBlock(builder -> {
        builder.setBinding(2).setName(CircleDataUniformBlock.NAME).setLayout(CircleDataUniformBlock.LAYOUT);
    }).buildSnippet();
    private static final String SPRAY_NAME = "spray";
    public static final ShaderProgramDescription.Snippet SPRAY_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).setId(buildProgramId(SPRAY_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/spray.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/spray.fsh")).addUniformBlock(builder -> {
        builder.setBinding(4).setName(SprayDataUniformBlock.NAME).setLayout(SprayDataUniformBlock.LAYOUT);
    }).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).addSampler(new UniformSamplerDescription("OverlaySampler", 1)).addSampler(new UniformSamplerDescription("LightSampler", 2)).addSampler(new UniformSamplerDescription("WearSampler", 4)).buildSnippet();
    private static final String SCHEMATIC_NAME = "schematic";
    public static final ShaderProgramDescription.Snippet SCHEMATIC_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]).setId(buildProgramId(SCHEMATIC_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/schematic.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/schematic.fsh")).addUniformBlock(builder -> {
        builder.setBinding(5).setName(SchematicUniformBlock.NAME).setLayout(SchematicUniformBlock.LAYOUT);
    }).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).setDefine(SchematicUniformBlock.MAX_LIGHTS_NAME, 48).buildSnippet();
    private static final String GUI_NAME = "gui";
    public static final ShaderProgramDescription.Snippet GUI_PROGRAM_SHADER_SNIPPET = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET, CLIP_DATA_SHADER_SNIPPET}).setId(buildProgramId(GUI_NAME)).setFragmentShader(SHADER_RESOLVER.apply("core/gui.fsh")).setVertexShader(SHADER_RESOLVER.apply("core/gui.vsh")).buildSnippet();
    public static final RenderState GUI = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(GUI_NAME)).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{GUI_PROGRAM_SHADER_SNIPPET}).setId(buildProgramId(GUI_NAME)).build()).build();
    private static final String GUI_ROUNDED_NAME = "gui_rounded";
    public static final RenderState GUI_ROUNDED = RenderState.builder(new RenderState.Snippet[]{GUI.toSnippet()}).setId(buildStateId(GUI_ROUNDED_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{GUI_PROGRAM_SHADER_SNIPPET, ROUND_DATA_SHADER_SNIPPET}).setId(buildProgramId(GUI_ROUNDED_NAME)).build()).build();
    private static final String GUI_TEXTURED_NAME = "gui_textured";
    private static final String TEXTURED_FLAG = "TEXTURED";
    public static final RenderState GUI_TEXTURED = RenderState.builder(new RenderState.Snippet[]{GUI.toSnippet()}).setId(buildStateId(GUI_TEXTURED_NAME)).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{GUI_PROGRAM_SHADER_SNIPPET}).setId(buildProgramId(GUI_TEXTURED_NAME)).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).setFlag(TEXTURED_FLAG).build()).build();
    public static final RenderState VANILLA_TEXT = RenderState.builder(new RenderState.Snippet[]{GUI_TEXTURED.toSnippet()}).setId(buildProgramId("vanilla_text")).setVertexDescription(VertexDescriptions.POSITION_COLOR_UV_LIGHTMAP).build();
    public static final RenderState VANILLA_SEE_THROUGH_TEXT = RenderState.builder(new RenderState.Snippet[]{VANILLA_TEXT.toSnippet()}).setId(buildProgramId("vanilla_see_through_text")).setDepthTestStrategy(ComparisonStrategy.NEVER).setWriteDepth(false).build();
    public static final RenderState VANILLA_INTENSITY_TEXT = RenderState.builder(new RenderState.Snippet[]{VANILLA_TEXT.toSnippet()}).setId(buildProgramId("vanilla_intensity_text")).build();
    public static final RenderState VANILLA_SEE_THROUGH_INTENSITY_TEXT = RenderState.builder(new RenderState.Snippet[]{VANILLA_TEXT.toSnippet()}).setId(buildProgramId("vanilla_intensity_text")).setDepthTestStrategy(ComparisonStrategy.NEVER).setWriteDepth(false).build();
    private static final String GUI_TEXTURED_ROUNDED_NAME = "gui_textured_rounded";
    public static final RenderState GUI_TEXTURED_ROUNDED = RenderState.builder(new RenderState.Snippet[]{GUI_TEXTURED.toSnippet()}).setId(buildStateId(GUI_TEXTURED_ROUNDED_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{GUI_PROGRAM_SHADER_SNIPPET, ROUND_DATA_SHADER_SNIPPET}).setId(buildProgramId(GUI_TEXTURED_ROUNDED_NAME)).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).setFlag(TEXTURED_FLAG).build()).build();
    private static final String GUI_TEXTURED_PREMULTIPLIED_ALPHA_NAME = "gui_textured_premultiplied_alpha";
    public static final RenderState GUI_TEXTURED_PREMULTIPLIED_ALPHA = RenderState.builder(new RenderState.Snippet[]{GUI_TEXTURED.toSnippet()}).setId(buildStateId(GUI_TEXTURED_PREMULTIPLIED_ALPHA_NAME)).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT_PREMULTIPLIED_ALPHA).build();
    private static final String GUI_TEXTURED_PREMULTIPLIED_ALPHA_WORKAROUND_NAME = "gui_textured_premultiplied_alpha_workaround";
    public static RenderState GUI_TEXTURED_PREMULTIPLIED_ALPHA_WORKAROUND = RenderState.builder(new RenderState.Snippet[]{GUI_TEXTURED_PREMULTIPLIED_ALPHA.toSnippet()}).setId(buildStateId(GUI_TEXTURED_PREMULTIPLIED_ALPHA_WORKAROUND_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{GUI_TEXTURED_PREMULTIPLIED_ALPHA.shaderProgramDescription().toSnippet()}).setId(buildProgramId(GUI_TEXTURED_PREMULTIPLIED_ALPHA_WORKAROUND_NAME)).setFlag("FRAMEBUFFER_WORKAROUND").build()).build();
    private static final String GUI_TEXTURED_PREMULTIPLIED_ALPHA_ROUNDED_NAME = "gui_textured_premultiplied_alpha_rounded";
    public static final RenderState GUI_TEXTURED_PREMULTIPLIED_ALPHA_ROUNDED = RenderState.builder(new RenderState.Snippet[]{GUI_TEXTURED_ROUNDED.toSnippet()}).setId(buildStateId(GUI_TEXTURED_PREMULTIPLIED_ALPHA_ROUNDED_NAME)).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT_PREMULTIPLIED_ALPHA).build();
    private static final String PANORAMA_NAME = "panorama";
    public static final RenderState PANORAMA = RenderState.builder(new RenderState.Snippet[]{GUI.toSnippet()}).setId(buildStateId(PANORAMA_NAME)).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setWriteDepth(false).setCull(false).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{GUI_PROGRAM_SHADER_SNIPPET}).setId(buildProgramId(PANORAMA_NAME)).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).setFlag(TEXTURED_FLAG).build()).build();
    private static final String CIRCLE_NAME = "circle";
    public static final RenderState CIRCLE = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(CIRCLE_NAME)).setDrawingMode(DrawingMode.QUADS).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET, CIRCLE_DATA_SHADER_SNIPPET, CLIP_DATA_SHADER_SNIPPET}).setId(buildProgramId(CIRCLE_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/circle.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/circle.fsh")).build()).build();
    private static final String INVERTED_COLOR_CIRCLE_NAME = "inverted_color_circle";
    public static final RenderState INVERTED_COLOR_CIRCLE = RenderState.builder(new RenderState.Snippet[]{CIRCLE.toSnippet()}).setId(buildStateId(INVERTED_COLOR_CIRCLE_NAME)).setBlendFunction(DefaultBlendFunctions.INVERT).build();
    private static final String GUI_LINES_NAME = "gui_lines";
    private static final String GUI_FLAG = "GUI";
    public static final RenderState GUI_LINES = RenderState.builder(new RenderState.Snippet[0]).setDrawingMode(DrawingMode.LINES).setVertexDescription(VertexDescriptions.POSITION_COLOR_NORMAL).setId(buildStateId(GUI_LINES_NAME)).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(GUI_LINES_NAME)).setFlag(GUI_FLAG).setVertexShader(SHADER_RESOLVER.apply("core/lines.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/lines.fsh")).build()).build();
    private static final String SIMPLE_LEVEL_GEOMETRY_NAME = "simple_geometry";
    public static final RenderState SIMPLE_LEVEL_GEOMETRY = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(SIMPLE_LEVEL_GEOMETRY_NAME)).setVertexDescription(VertexDescriptions.POSITION_COLOR_UV_LIGHTMAP).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setDepthTestStrategy(ComparisonStrategy.LEQUAL).setDepthBias(-1.0f, -10.0f).setCull(false).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(SIMPLE_LEVEL_GEOMETRY_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/simple_level_geometry.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/simple_level_geometry.fsh")).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).build()).build();
    private static final String NAMETAG_ICON_NAME = "nametag_icon";
    public static final RenderState NAMETAG_ICON = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(NAMETAG_ICON_NAME)).setVertexDescription(VertexDescriptions.POSITION_COLOR_UV_LIGHTMAP).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setDepthTestStrategy(ComparisonStrategy.LEQUAL).setDepthBias(-1.0f, -10.0f).setCull(true).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(NAMETAG_ICON_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/simple_level_geometry.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/simple_level_geometry.fsh")).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).build()).build();
    private static final String SEE_THROUGH_NAMETAG_ICON_NAME = "see_through_nametag_icon";
    public static final RenderState SEE_THROUGH_NAMETAG_ICON = RenderState.builder(new RenderState.Snippet[]{NAMETAG_ICON.toSnippet()}).setId(buildStateId(SEE_THROUGH_NAMETAG_ICON_NAME)).setDepthTestStrategy(ComparisonStrategy.NEVER).setWriteDepth(false).build();
    private static final String TRANSLUCENT_EMOTES_NAME = "translucent_emotes";
    public static final RenderState TRANSLUCENT_EMOTES = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(TRANSLUCENT_EMOTES_NAME)).setVertexDescription(VertexDescriptions.MODEL).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setDepthTestStrategy(ComparisonStrategy.LEQUAL).setCull(false).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET, LIGHTING_SHADER_SNIPPET}).setId(buildProgramId(TRANSLUCENT_EMOTES_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/model.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/model.fsh")).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).addSampler(new UniformSamplerDescription("OverlaySampler", 1)).addSampler(new UniformSamplerDescription("LightSampler", 2)).build()).build();
    private static final String TRIANGLE_NAME = "triangle";
    public static final RenderState TRIANGLE = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(TRIANGLE_NAME)).setDrawingMode(DrawingMode.TRIANGLES).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setVertexDescription(VertexDescriptions.POSITION_COLOR_NORMAL).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(TRIANGLE_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/triangle.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/triangle.fsh")).build()).build();
    private static final String BLIT_RENDER_TARGET_NAME = "blit_render_target";
    private static final String SINGLE_FLAG = "SINGLE";
    public static final RenderState BLIT_RENDER_TARGET = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(BLIT_RENDER_TARGET_NAME)).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setDepthTestStrategy(ComparisonStrategy.NEVER).setWriteDepth(false).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(BLIT_RENDER_TARGET_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/blit_render_target.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/blit_render_target.fsh")).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).setFlag(SINGLE_FLAG).build()).build();
    public static final RenderState SPRAY = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(SPRAY_NAME)).setVertexDescription(VertexDescriptions.MODEL).setDrawingMode(DrawingMode.QUADS).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET, SPRAY_SHADER_SNIPPET}).setId(buildProgramId(SPRAY_NAME)).build()).setCull(false).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setDepthBias(-1.0f, -10.0f).build();
    private static final String SPRAY_DEBUG_NAME = "spray_debug";
    private static final String DEBUG_FLAG = "DEBUG";
    public static final RenderState SPRAY_DEBUG = RenderState.builder(new RenderState.Snippet[]{SPRAY.toSnippet()}).setId(buildStateId(SPRAY_DEBUG_NAME)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET, SPRAY_SHADER_SNIPPET}).setId(buildProgramId(SPRAY_DEBUG_NAME)).setFlag(DEBUG_FLAG).build()).build();
    private static final String CAPE_PARTICLES_NAME = "cape_particles";

    @Deprecated
    public static final RenderState CAPE_PARTICLES = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(CAPE_PARTICLES_NAME)).setDrawingMode(DrawingMode.QUADS).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(CAPE_PARTICLES_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/cape_particles.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/cape_particles.fsh")).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).build()).build();
    public static final RenderState SCHEMATIC = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(SCHEMATIC_NAME)).setVertexDescription(VertexDescriptions.SCHEMATIC).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{SCHEMATIC_SHADER_SNIPPET}).setId(buildProgramId(SCHEMATIC_NAME)).build()).build();
    private static final String DEBUG_BOXES_NAME = "debug_boxes";
    public static final RenderState DEBUG_BOXES = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(DEBUG_BOXES_NAME)).setVertexDescription(VertexDescriptions.POSITION_COLOR).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(DEBUG_BOXES_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/position_color.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/position_color.fsh")).build()).build();
    private static final String DEBUG_FRUSTUM_NAME = "debug_frustum";
    public static final RenderState DEBUG_FRUSTUM = RenderState.builder(new RenderState.Snippet[]{DEBUG_BOXES.toSnippet()}).setId(buildStateId(DEBUG_FRUSTUM_NAME)).setCull(false).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(DEBUG_FRUSTUM_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/position_color.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/position_color.fsh")).build()).build();
    private static final String POST_PROCESSING_ROUNDED_NAME = "post_processing_rounded";
    public static final RenderState POST_PROCESSING_ROUNDED = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(POST_PROCESSING_ROUNDED_NAME)).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET, ROUND_DATA_SHADER_SNIPPET, CLIP_DATA_SHADER_SNIPPET}).setId(buildProgramId(POST_PROCESSING_ROUNDED_NAME)).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).setVertexShader(SHADER_RESOLVER.apply("core/post_processing_rounded.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/post_processing_rounded.fsh")).build()).build();
    private static final String MSDF_FONT_NAME = "msdf_font";
    private static final RenderState.Snippet MSDF_FONT_SNIPPET = RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId(MSDF_FONT_NAME)).setVertexDescription(VertexDescriptions.MSDF_GLYPH).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setCull(true).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId(MSDF_FONT_NAME)).setVertexShader(SHADER_RESOLVER.apply("core/msdf_font.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/msdf_font.fsh")).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).addSampler(new UniformSamplerDescription("LightSampler", getLightTextureSlot())).setDefine("TEXTURE_WIDTH", GlConst.GL_STENCIL_FAIL).setDefine("TEXTURE_HEIGHT", GlConst.GL_STENCIL_FAIL).build()).buildSnippet();
    private static final RenderState MSDF_FONT = RenderState.builder(new RenderState.Snippet[]{MSDF_FONT_SNIPPET}).setId(buildStateId(MSDF_FONT_NAME)).build();
    private static final String MSDF_FONT_SEE_THROUGH_NAME = "msdf_font_see_through";
    private static final RenderState MSDF_FONT_SEE_THROUGH = RenderState.builder(new RenderState.Snippet[]{MSDF_FONT_SNIPPET}).setId(buildStateId(MSDF_FONT_SEE_THROUGH_NAME)).setWriteDepth(false).setDepthTestStrategy(ComparisonStrategy.NEVER).build();
    private static final String MSDF_FONT_POLYGON_OFFSET_NAME = "msdf_font_polygon_offset";
    private static final RenderState MSDF_FONT_POLYGON_OFFSET = RenderState.builder(new RenderState.Snippet[]{MSDF_FONT_SNIPPET}).setId(buildStateId(MSDF_FONT_POLYGON_OFFSET_NAME)).setDepthBias(-1.0f, -10.0f).build();
    private static final IntFunction<RenderState> CUSTOM_BLIT_TARGET = memoizeInt(samplers -> {
        ShaderProgramDescription.Builder builder = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET});
        for (int index = 0; index < samplers; index++) {
            builder.addSampler(new UniformSamplerDescription("Sampler" + index, index));
            builder.setFlag("SAMPLER" + index);
        }
        return RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId("blit_render_target_" + samplers)).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setWriteDepth(false).setDepthTestStrategy(ComparisonStrategy.NEVER).setShaderProgramDescription(builder.setId(buildProgramId("blit_render_target_" + samplers)).setVertexShader(SHADER_RESOLVER.apply("core/blit_render_target.vsh")).setFragmentShader(SHADER_RESOLVER.apply("core/blit_render_target.fsh")).build()).build();
    });
    private static final Function<BlurRenderer.BlurAlgorithm, RenderState> GUI_BLUR = memoize(algorithm -> {
        return RenderState.builder(new RenderState.Snippet[0]).setId(buildStateId("blur_" + algorithm.name().toLowerCase(Locale.ROOT))).setVertexDescription(VertexDescriptions.POSITION_UV_COLOR).setDrawingMode(DrawingMode.QUADS).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setDepthTestStrategy(ComparisonStrategy.NEVER).setWriteDepth(false).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{DEFAULT_SHADER_SNIPPET}).setId(buildProgramId("blur_" + algorithm.name().toLowerCase(Locale.ROOT))).setVertexShader(LOCATION_MAPPER.apply(algorithm.vertexShaderLocation())).setFragmentShader(LOCATION_MAPPER.apply(algorithm.fragmentShaderLocation())).addUniformBlock(builder -> {
            builder.setName(BlurDataUniformBlock.NAME).setLayout(BlurDataUniformBlock.LAYOUT).setBinding(7);
        }).addSampler(new UniformSamplerDescription("DiffuseSampler", 0)).build()).build();
    });

    @NotNull
    public static RenderState buildCustomBlitTarget(int samplers) {
        return CUSTOM_BLIT_TARGET.apply(samplers);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @NotNull
    public static RenderState getMsdfFont(@NotNull TextDrawMode mode) throws MatchException {
        switch (mode) {
            case NORMAL:
                return MSDF_FONT;
            case SEE_THROUGH:
                return MSDF_FONT_SEE_THROUGH;
            case POLYGON_OFFSET:
                return MSDF_FONT_POLYGON_OFFSET;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @NotNull
    public static RenderState getGuiBlur(@NotNull BlurRenderer.BlurAlgorithm algorithm) {
        return GUI_BLUR.apply(algorithm);
    }

    @NotNull
    protected static AssetId buildStateId(@NotNull String name) {
        return AssetId.of("labymod", "renderstate/" + name);
    }

    @NotNull
    protected static AssetId buildProgramId(@NotNull String name) {
        return AssetId.of("labymod", "shaderprogram/" + name);
    }

    @NotNull
    protected static AssetId buildMinecraftShaderId(@NotNull String name) {
        return AssetId.of(Namespaces.MINECRAFT, "shaders/" + name);
    }

    private static <T> Function<T, RenderState> memoize(Function<T, RenderState> function) {
        return STORAGE.memoize(function);
    }

    private static IntFunction<RenderState> memoizeInt(IntFunction<RenderState> function) {
        return STORAGE.memoizeInt(function);
    }

    public static int getLightTextureSlot() {
        return USE_LEGACY_TEXTURE_SLOT ? 1 : 2;
    }
}
