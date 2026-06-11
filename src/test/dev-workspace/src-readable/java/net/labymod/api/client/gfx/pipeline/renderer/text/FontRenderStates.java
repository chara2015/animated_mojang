package net.labymod.api.client.gfx.pipeline.renderer.text;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaDynamicTransformsUniformBlock;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaFogUniformBlock;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaProjectionUniformBlock;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.pipeline.ComparisonStrategy;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.blend.DefaultBlendFunctions;
import net.labymod.laby3d.api.pipeline.shader.ShaderProgramDescription;
import net.labymod.laby3d.api.pipeline.shader.UniformDescription;
import net.labymod.laby3d.api.pipeline.shader.UniformSamplerDescription;
import net.labymod.laby3d.api.resource.AssetId;
import net.labymod.laby3d.api.shaders.UniformType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates.class */
public final class FontRenderStates extends RenderStates {
    private static final String VANILLA_TEXT_NAME = "vanilla_text";
    private static final String VANILLA_TEXT_SEE_THROUGH_NAME = "vanilla_text_see_through";
    private static final String VANILLA_INTENSITY_TEXT_NAME = "vanilla_intensity_text";
    private static final MinecraftShader RENDERTYPE_TEXT;
    private static final MinecraftShader RENDERTYPE_TEXT_SEE_THROUGH;
    private static final MinecraftShader RENDERTYPE_TEXT_INTENSITY;
    private static final MinecraftShader RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH;
    private static final MinecraftShader TEXT;
    private static final MinecraftShader TEXT_GUI;
    private static final MinecraftShader TEXT_INTENSITY_GUI;
    private static final MinecraftShader TEXT_SEE_THROUGH;
    private static final MinecraftShader TEXT_INTENSITY;
    private static final MinecraftShader TEXT_INTENSITY_SEE_THROUGH;
    public static final RenderState VANILLA_TEXT;
    public static final RenderState VANILLA_TEXT_GUI;
    public static final RenderState VANILLA_SEE_THROUGH_TEXT;
    public static final RenderState VANILLA_INTENSITY_SEE_THROUGH_TEXT;
    public static final RenderState VANILLA_INTENSITY_TEXT;
    public static final RenderState VANILLA_INTENSITY_TEXT_GUI;
    private static final RenderEnvironmentContext ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
    private static final boolean UBO = MinecraftVersions.V1_21_6.orNewer();
    private static final List<RenderState> RENDER_STATES = new ArrayList();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$Mode.class */
    enum Mode {
        GUI,
        WORLD
    }

    static {
        MinecraftVersion minecraftVersion = MinecraftVersions.V26_1_2;
        Objects.requireNonNull(minecraftVersion);
        RENDERTYPE_TEXT = new MinecraftShader("core/rendertype_text", minecraftVersion::orOlder);
        MinecraftVersion minecraftVersion2 = MinecraftVersions.V26_1_2;
        Objects.requireNonNull(minecraftVersion2);
        RENDERTYPE_TEXT_SEE_THROUGH = new MinecraftShader("core/rendertype_text_see_through", minecraftVersion2::orOlder);
        MinecraftVersion minecraftVersion3 = MinecraftVersions.V26_1_2;
        Objects.requireNonNull(minecraftVersion3);
        RENDERTYPE_TEXT_INTENSITY = new MinecraftShader("core/rendertype_text_intensity", minecraftVersion3::orOlder);
        MinecraftVersion minecraftVersion4 = MinecraftVersions.V26_1_2;
        Objects.requireNonNull(minecraftVersion4);
        RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH = new MinecraftShader("core/rendertype_text_intensity_see_through", minecraftVersion4::orOlder);
        MinecraftVersion minecraftVersion5 = MinecraftVersions.V26_2_snapshot_6;
        Objects.requireNonNull(minecraftVersion5);
        TEXT = new MinecraftShader("core/text", minecraftVersion5::orNewer);
        Set setOf = Set.of("GUI");
        MinecraftVersion minecraftVersion6 = MinecraftVersions.V26_2_snapshot_6;
        Objects.requireNonNull(minecraftVersion6);
        TEXT_GUI = new MinecraftShader("core/text", setOf, minecraftVersion6::orNewer);
        Set setOf2 = Set.of("GUI", "IS_GRAYSCALE");
        MinecraftVersion minecraftVersion7 = MinecraftVersions.V26_2_snapshot_6;
        Objects.requireNonNull(minecraftVersion7);
        TEXT_INTENSITY_GUI = new MinecraftShader("core/text", setOf2, minecraftVersion7::orNewer);
        Set setOf3 = Set.of("SEE_THROUGH");
        MinecraftVersion minecraftVersion8 = MinecraftVersions.V26_2_snapshot_6;
        Objects.requireNonNull(minecraftVersion8);
        TEXT_SEE_THROUGH = new MinecraftShader("core/text_see_through", setOf3, minecraftVersion8::orNewer);
        Set setOf4 = Set.of("IS_GRAYSCALE");
        MinecraftVersion minecraftVersion9 = MinecraftVersions.V26_2_snapshot_6;
        Objects.requireNonNull(minecraftVersion9);
        TEXT_INTENSITY = new MinecraftShader("core/text_intensity", setOf4, minecraftVersion9::orNewer);
        Set setOf5 = Set.of("SEE_THROUGH", "IS_GRAYSCALE");
        MinecraftVersion minecraftVersion10 = MinecraftVersions.V26_2_snapshot_6;
        Objects.requireNonNull(minecraftVersion10);
        TEXT_INTENSITY_SEE_THROUGH = new MinecraftShader("core/text_intensity_see_through", setOf5, minecraftVersion10::orNewer);
        VANILLA_TEXT = buildRenderState(null, VANILLA_TEXT_NAME, Mode.WORLD, RENDERTYPE_TEXT, TEXT);
        VANILLA_TEXT_GUI = buildRenderState(null, VANILLA_TEXT_NAME, Mode.GUI, RENDERTYPE_TEXT, TEXT_GUI);
        VANILLA_SEE_THROUGH_TEXT = buildRenderState(VANILLA_TEXT, VANILLA_TEXT_SEE_THROUGH_NAME, Mode.WORLD, RENDERTYPE_TEXT_SEE_THROUGH, TEXT_SEE_THROUGH);
        VANILLA_INTENSITY_SEE_THROUGH_TEXT = buildRenderState(VANILLA_TEXT, VANILLA_TEXT_SEE_THROUGH_NAME, Mode.WORLD, RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH, TEXT_INTENSITY_SEE_THROUGH);
        VANILLA_INTENSITY_TEXT = buildRenderState(VANILLA_TEXT, VANILLA_INTENSITY_TEXT_NAME, Mode.WORLD, RENDERTYPE_TEXT_INTENSITY, TEXT_INTENSITY);
        VANILLA_INTENSITY_TEXT_GUI = buildRenderState(VANILLA_TEXT_GUI, VANILLA_INTENSITY_TEXT_NAME, Mode.GUI, RENDERTYPE_TEXT_INTENSITY, TEXT_INTENSITY_GUI);
    }

    public static RenderState resolve(TextDrawMode drawMode, boolean colored) {
        if (drawMode == TextDrawMode.SEE_THROUGH) {
            return colored ? VANILLA_SEE_THROUGH_TEXT : VANILLA_INTENSITY_SEE_THROUGH_TEXT;
        }
        Mode mode = ENVIRONMENT_CONTEXT.isScreenContext() ? Mode.GUI : Mode.WORLD;
        boolean isGui = mode == Mode.GUI;
        return colored ? isGui ? VANILLA_TEXT_GUI : VANILLA_TEXT : isGui ? VANILLA_INTENSITY_TEXT_GUI : VANILLA_INTENSITY_TEXT;
    }

    @ApiStatus.Internal
    public static boolean isVanillaRenderState(RenderState renderState) {
        return renderState == VANILLA_TEXT || renderState == VANILLA_INTENSITY_TEXT || renderState == VANILLA_SEE_THROUGH_TEXT || renderState == VANILLA_TEXT_GUI || renderState == VANILLA_INTENSITY_TEXT_GUI;
    }

    @ApiStatus.Internal
    public static void invalidateShaders(RenderDevice device) {
        for (RenderState renderState : RENDER_STATES) {
            device.invalidateShader(renderState);
        }
    }

    private static RenderState buildRenderState(@Nullable RenderState parent, String name, Mode mode, MinecraftShader... shaders) {
        for (MinecraftShader shader : shaders) {
            if (shader.isAvailable()) {
                RenderState renderState = build(parent, name, mode, shader);
                RENDER_STATES.add(renderState);
                return renderState;
            }
        }
        throw new IllegalStateException("No available shader for " + name);
    }

    private static RenderState build(@Nullable RenderState parent, String name, Mode mode, MinecraftShader shader) {
        RenderState.Builder builder = parent == null ? RenderState.builder(new RenderState.Snippet[0]) : RenderState.builder(new RenderState.Snippet[]{parent.toSnippet()});
        if (VANILLA_TEXT_SEE_THROUGH_NAME.equals(name)) {
            builder.setDepthTestStrategy(ComparisonStrategy.NEVER);
            builder.setWriteDepth(false);
        }
        String path = shader.path();
        RenderState renderState = builder.setId(buildStateId(name)).setDrawingMode(DrawingMode.QUADS).setVertexDescription(VertexDescriptions.POSITION_COLOR_UV_LIGHTMAP).setBlendFunction(DefaultBlendFunctions.TRANSLUCENT).setShaderProgramDescription(buildTextShaderProgram(mode, buildProgramId(name), buildMinecraftShaderId(path + ".vsh"), buildMinecraftShaderId(path + ".fsh"))).build();
        RENDER_STATES.add(renderState);
        return renderState;
    }

    private static ShaderProgramDescription buildTextShaderProgram(Mode mode, AssetId id, AssetId vertexShader, AssetId fragmentShader) {
        ShaderProgramDescription.Builder builder = ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[0]);
        if (mode == Mode.GUI) {
            builder.setFlag(RenderStates.LABYMOD_GUI_FLAG);
        }
        builder.setId(id).setVertexShader(vertexShader).setFragmentShader(fragmentShader).setFlag(RenderStates.LABYMOD_FLAG).addSampler(new UniformSamplerDescription("Sampler0", 0)).addSampler(new UniformSamplerDescription("Sampler2", 2));
        if (UBO) {
            return builder.addUniformBlock(ubBuilder -> {
                ubBuilder.setName("Projection").setLayout(VanillaProjectionUniformBlock.LAYOUT).setBinding(0);
            }).addUniformBlock(ubBuilder2 -> {
                ubBuilder2.setName("DynamicTransforms").setLayout(VanillaDynamicTransformsUniformBlock.LAYOUT).setBinding(1);
            }).addUniformBlock(ubBuilder3 -> {
                ubBuilder3.setName(VanillaFogUniformBlock.NAME).setLayout(VanillaFogUniformBlock.LAYOUT).setBinding(2);
            }).build();
        }
        return builder.addUniform(new UniformDescription("ModelViewMat", UniformType.MAT4)).addUniform(new UniformDescription("ProjMat", UniformType.MAT4)).addUniform(new UniformDescription("ColorModulator", UniformType.VEC4)).addUniform(new UniformDescription("FogShape", UniformType.INT)).addUniform(new UniformDescription("FogColor", UniformType.VEC4)).addUniform(new UniformDescription("FogStart", UniformType.FLOAT)).addUniform(new UniformDescription("FogEnd", UniformType.FLOAT)).addUniform(new UniformDescription("GameTime", UniformType.FLOAT)).build();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader.class */
    static final class MinecraftShader extends Record {
        private final String path;
        private final Set<String> defines;
        private final BooleanSupplier available;

        MinecraftShader(String path, Set<String> defines, BooleanSupplier available) {
            this.path = path;
            this.defines = defines;
            this.available = available;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MinecraftShader.class), MinecraftShader.class, "path;defines;available", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->path:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->defines:Ljava/util/Set;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->available:Ljava/util/function/BooleanSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MinecraftShader.class), MinecraftShader.class, "path;defines;available", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->path:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->defines:Ljava/util/Set;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->available:Ljava/util/function/BooleanSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MinecraftShader.class, Object.class), MinecraftShader.class, "path;defines;available", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->path:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->defines:Ljava/util/Set;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/FontRenderStates$MinecraftShader;->available:Ljava/util/function/BooleanSupplier;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String path() {
            return this.path;
        }

        public Set<String> defines() {
            return this.defines;
        }

        public BooleanSupplier available() {
            return this.available;
        }

        public MinecraftShader(String path, BooleanSupplier available) {
            this(path, Set.of(), available);
        }

        public boolean isAvailable() {
            return this.available.getAsBoolean();
        }
    }
}
