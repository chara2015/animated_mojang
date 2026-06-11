package net.labymod.api.laby3d;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.event.laby3d.RenderDeviceInitializedEvent;
import net.labymod.api.event.laby3d.UniformBlockRegistrationEvent;
import net.labymod.api.laby3d.buffer.ImmediateDeviceBufferStorage;
import net.labymod.api.laby3d.buffer.SyncedBufferPool;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTarget;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry;
import net.labymod.api.laby3d.foreign.target.opengl.GlForeignRenderTargetRegistry;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry;
import net.labymod.api.laby3d.foreign.texture.opengl.GlForeignDeviceTextureRegistry;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.BlurDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.CircleDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.ClipDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlock;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlock;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.GlobalsUniformBlock;
import net.labymod.api.laby3d.shaders.block.LightingUniformBlock;
import net.labymod.api.laby3d.shaders.block.LightingUniformBlockData;
import net.labymod.api.laby3d.shaders.block.PostProcessorUniformBlock;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlock;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.api.laby3d.shaders.block.RoundDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.SchematicUniformBlock;
import net.labymod.api.laby3d.shaders.block.SprayDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaDynamicTransformsUniformBlock;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaFogUniformBlock;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaProjectionUniformBlock;
import net.labymod.api.laby3d.shaders.preprocessor.GlPositionAssignmentLineTransformer;
import net.labymod.api.laby3d.util.RenderTargetPool;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.function.BooleanSetter;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.pool.FrameObjectPools;
import net.labymod.api.util.pool.ObjectPool;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.RenderDeviceValidator;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.BufferType;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.BufferResource;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.mesh.SharedMeshIndexBuffer;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.IndexType;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.resource.AssetId;
import net.labymod.laby3d.api.shaders.ShaderConfiguration;
import net.labymod.laby3d.api.shaders.block.UniformBlock;
import net.labymod.laby3d.api.shaders.block.property.UniformProperty;
import net.labymod.laby3d.api.shaders.preprocessor.builtin.ImportDirective;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexDescription;
import net.labymod.util.property.BooleanSystemProperty;
import net.labymod.util.property.SystemProperties;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4i;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/Laby3D.class */
@Referenceable
public abstract class Laby3D implements Closeable {
    public static final int ONE_MEGABYTE = 1048576;
    private static final int ID_SHIFT = 32;
    private static final long ID_MASK = 4294967295L;
    private final GameTransformations gameTransformations;
    private ForeignRenderTargetRegistry foreignRenderTargetRegistry;
    private ForeignDeviceTextureRegistry foreignDeviceTextureRegistry;
    private GraphicsWorkarounds graphicsWorkarounds;
    private BlurDataUniformBlock blurData;
    private CircleDataUniformBlock circleData;
    private ClipDataUniformBlock clipData;
    private DynamicTransformsUniformBlock dynamicTransforms;
    private GlobalsUniformBlock globals;
    private ProjectionUniformBlock projection;
    private RoundDataUniformBlock roundData;
    private SchematicUniformBlock schematic;
    private SprayDataUniformBlock sprayData;
    private PostProcessorUniformBlock postProcessor;
    private CosmeticsUniformBlock cosmetics;
    private LightingUniformBlock lighting;
    private VanillaProjectionUniformBlock vanillaProjection;
    private VanillaDynamicTransformsUniformBlock vanillaDynamicTransforms;
    private VanillaFogUniformBlock vanillaFog;
    private RenderTargetPool renderTargetPool;
    protected static final int DEFAULT_FLAGS = buildDebuggerFlags();
    private static final CachedOrthoProjectionMatrix<Matrix4f> BLITTER_PROJECTION_MATRIX = CachedOrthoProjectionMatrix.simple(0.1f, 1000.0f, false);
    private static final Matrix4f BLITTER_MODEL_VIEW_MATRIX = new Matrix4f().translate(0.0f, 0.0f, -500.0f);
    private static final Logging LOGGER = Logging.getLogger();
    private static final Function<ResourceLocation, AssetId> LOCATION_MAPPER = location -> {
        return AssetId.of(location.getNamespace(), location.getPath());
    };
    private static final ResourceLocation BUILT_IN_DEFAULT = ResourceLocation.create("labymod", "shaders/builtin/default.glsl");
    private static final boolean USE_IDENTITY_MATRIX = MinecraftVersions.V1_12_2.orOlder();
    private static final boolean UBO = MinecraftVersions.V1_21_6.orNewer();
    private static final Matrix4f IDENTITY_MATRIX = new Matrix4f();
    private final ObjectPool<Matrix4f> matrices = FrameObjectPools.instance().register(Matrix4f::new, (v0) -> {
        v0.identity();
    });
    private final ObjectPool<Vector3f> vector3fs = FrameObjectPools.instance().register(Vector3f::new, (v0) -> {
        v0.zero();
    });
    private final List<UniformBlock> uniformBlocks = new ArrayList();
    private final ByteBufferBuilder buffer = new ByteBufferBuilder(1048576);
    private final Lazy<RenderDevice> renderDevice = Lazy.of(this::initializeRenderDevice);
    private final ImmediateDeviceBufferStorage immediateDeviceBufferStorage = new ImmediateDeviceBufferStorage(this);
    private final RenderTargetMesh intelWorkaroundMesh = new RenderTargetMesh(this);
    private final Long2ObjectMap<RenderTargetMesh> blitFramebufferMeshes = new Long2ObjectOpenHashMap();

    @NotNull
    protected abstract RenderDevice createRenderDevice(@NotNull ShaderConfiguration shaderConfiguration);

    public abstract void setupOverlayAndLightingTextures();

    public abstract void setupOverlayAndLightingTextures(@NotNull CommandBuffer commandBuffer);

    @NotNull
    public abstract DeviceTextureView overlayTextureView();

    @NotNull
    public abstract DeviceTextureView lightTextureView();

    public Laby3D(@NotNull GameTransformations gameTransformations) {
        this.gameTransformations = gameTransformations;
        RenderDeviceValidator.DISABLE_UNIFORM_EQUALS_CHECKS = true;
        RenderDeviceValidator.debugDirectory = Constants.Files.LABYMOD_DEBUG_DIRECTORY.resolve("laby3d");
        evaluateFeatureStatus(flag -> {
            RenderDeviceValidator.VALIDATION = flag;
        }, SystemProperties.RENDER_DEVICE_VALIDATION, () -> {
            return "Laby3D is running in validation mode.";
        });
        evaluateFeatureStatus(flag2 -> {
            RenderDeviceValidator.DETECT_LEAKED_RESOURCES = flag2;
        }, SystemProperties.RENDER_DEVICE_LEAKED_RESOURCES_DETECTION, () -> {
            return "Laby3D is running in leak detection mode.";
        });
        evaluateFeatureStatus(flag3 -> {
            RenderDeviceValidator.EXPORT_SHADERS = flag3;
        }, SystemProperties.RENDER_DEVICE_EXPORT_SHADERS, () -> {
            return "Laby3D is exporting shaders to disk.";
        });
    }

    @NotNull
    public RenderDevice renderDevice() {
        return this.renderDevice.get();
    }

    @NotNull
    public BufferBuilder begin(@NotNull DrawingMode mode, @NotNull VertexDescription description) {
        return new BufferBuilder(this.buffer, description, mode);
    }

    @NotNull
    public BlurDataUniformBlock blurData() {
        return this.blurData;
    }

    @NotNull
    public CircleDataUniformBlock circleData() {
        return this.circleData;
    }

    @NotNull
    public DynamicTransformsUniformBlock dynamicTransforms() {
        return this.dynamicTransforms;
    }

    @NotNull
    public GlobalsUniformBlock globals() {
        return this.globals;
    }

    @NotNull
    public ProjectionUniformBlock projection() {
        return this.projection;
    }

    @NotNull
    public RoundDataUniformBlock roundData() {
        return this.roundData;
    }

    @NotNull
    public ClipDataUniformBlock clipData() {
        return this.clipData;
    }

    @NotNull
    public SchematicUniformBlock schematic() {
        return this.schematic;
    }

    @NotNull
    public SprayDataUniformBlock sprayData() {
        return this.sprayData;
    }

    @NotNull
    public PostProcessorUniformBlock postProcessor() {
        return this.postProcessor;
    }

    @NotNull
    public CosmeticsUniformBlock cosmetics() {
        return this.cosmetics;
    }

    @NotNull
    public LightingUniformBlock lighting() {
        return this.lighting;
    }

    @NotNull
    public VanillaProjectionUniformBlock vanillaProjection() {
        return this.vanillaProjection;
    }

    @NotNull
    public VanillaDynamicTransformsUniformBlock vanillaDynamicTransforms() {
        return this.vanillaDynamicTransforms;
    }

    @NotNull
    public VanillaFogUniformBlock vanillaFog() {
        return this.vanillaFog;
    }

    @NotNull
    public RenderTargetPool renderTargetPool() {
        return this.renderTargetPool;
    }

    public void blitTo(@NotNull RenderTarget source, @NotNull RenderTarget destination) {
        RenderDevice renderDevice = renderDevice();
        UniformProperty<Matrix4f> projectionMatrixUniform = projection().projectionMatrix();
        Matrix4f prevProjectionMatrix = JomlMath.extractMatrix((Matrix4f) projectionMatrixUniform.get());
        CommandBuffer cmd = renderDevice.createCommandBuffer(1);
        try {
            cmd.beginPass(destination, LoadOp.LOAD, () -> {
                return "blit target";
            });
            blitTo(cmd, source, destination);
            cmd.endPass();
            cmd.submit();
            if (cmd != null) {
                cmd.close();
            }
            projectionMatrixUniform.set(prevProjectionMatrix);
        } catch (Throwable th) {
            if (cmd != null) {
                try {
                    cmd.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void blitTo(@NotNull CommandBuffer cmd, @NotNull RenderTarget source, @NotNull RenderTarget destination) {
        RenderDevice renderDevice = renderDevice();
        long meshId = getId(destination.width(), destination.height());
        RenderTargetMesh framebufferMesh = (RenderTargetMesh) this.blitFramebufferMeshes.computeIfAbsent(meshId, l -> {
            return new RenderTargetMesh(this);
        });
        Mesh mesh = framebufferMesh.compileOrGet(renderDevice, destination);
        cmd.bindPipeline(RenderStates.BLIT_RENDER_TARGET);
        cmd.bindTexture(0, source.findColorTexture(0));
        ProjectionUniformBlockData data = new ProjectionUniformBlockData(BLITTER_PROJECTION_MATRIX.getCached(destination.width(), destination.height()));
        cmd.bindUniformBlockData("Projection", data);
        cmd.bindUniformBlock("Projection", projection());
        cmd.draw(mesh);
    }

    public void writeToTexture(@NotNull DeviceTexture texture, @NotNull GameImage image) {
        ByteBuffer imageBuffer = MemoryUtil.memAlloc(image.getWidth() * image.getHeight() * 4);
        ColorFormat format = ColorFormat.ARGB32;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getARGB(x, y);
                byte red = (byte) format.red(argb);
                byte green = (byte) format.green(argb);
                byte blue = (byte) format.blue(argb);
                byte alpha = (byte) format.alpha(argb);
                imageBuffer.put(red);
                imageBuffer.put(green);
                imageBuffer.put(blue);
                imageBuffer.put(alpha);
            }
        }
        imageBuffer.flip();
        renderDevice().writeToTexture(texture, image.getWidth(), image.getHeight(), DeviceTexture.Format.R8G8B8A8_UNORM, imageBuffer);
        MemoryUtil.memFree(imageBuffer);
    }

    public void setupDefaultUniforms(@NotNull CommandBuffer cmd) {
        setupDefaultUniforms(cmd, getProjectionMatrix(), getModelViewMatrix());
    }

    @NotNull
    public Matrix4f getProjectionMatrix() {
        Matrix4f matrix4fProjectionMatrix;
        if (Laby.references().renderEnvironmentContext().isScreenContext()) {
            matrix4fProjectionMatrix = this.gameTransformations.guiProjectionMatrix();
        } else {
            matrix4fProjectionMatrix = this.gameTransformations.projectionMatrix();
        }
        Matrix4f projectionMatrix = matrix4fProjectionMatrix;
        Matrix4f offscreenProjectionMatrix = RenderUtil.getOffscreenProjectionMatrix();
        if (offscreenProjectionMatrix != null) {
            projectionMatrix = offscreenProjectionMatrix;
        }
        return projectionMatrix;
    }

    @NotNull
    public Matrix4f getModelViewMatrix() {
        Matrix4f matrix4fModelViewMatrix;
        if (USE_IDENTITY_MATRIX) {
            matrix4fModelViewMatrix = IDENTITY_MATRIX;
        } else {
            matrix4fModelViewMatrix = this.gameTransformations.modelViewMatrix();
        }
        Matrix4f modelViewMatrix = matrix4fModelViewMatrix;
        Matrix4f offscreenModelViewMatrix = RenderUtil.getOffscreenModelViewMatrix();
        if (offscreenModelViewMatrix != null) {
            modelViewMatrix = offscreenModelViewMatrix;
        }
        return modelViewMatrix;
    }

    public void setupDefaultUniforms(@NotNull CommandBuffer cmd, @NotNull Matrix4f projectionMatrix, @NotNull Matrix4f modelViewMatrix) {
        ProjectionUniformBlockData projectionData = new ProjectionUniformBlockData();
        projectionData.projectionMatrix().set(JomlMath.extractMatrix(projectionMatrix));
        DynamicTransformsUniformBlockData dynamicTransformsData = new DynamicTransformsUniformBlockData();
        dynamicTransformsData.setLineWidth(1.0f);
        dynamicTransformsData.modelViewMatrix().set(JomlMath.extractMatrix(modelViewMatrix));
        cmd.bindUniformBlock("Projection", projection());
        cmd.bindUniformBlockData("Projection", projectionData);
        cmd.bindUniformBlock("DynamicTransforms", dynamicTransforms());
        cmd.bindUniformBlockData("DynamicTransforms", dynamicTransformsData);
        cmd.bindUniformBlock(GlobalsUniformBlock.NAME, globals());
        CosmeticsUniformBlockData cosmeticsData = RenderUtil.getCosmeticsData();
        if (cosmeticsData != null) {
            cmd.bindUniformBlock(CosmeticsUniformBlock.NAME, cosmetics());
            cmd.bindUniformBlockData(CosmeticsUniformBlock.NAME, cosmeticsData);
        }
        cmd.bindUniformBlock(LightingUniformBlock.NAME, lighting());
        cmd.bindUniformBlockData(LightingUniformBlock.NAME, new LightingUniformBlockData(this.gameTransformations.light0Direction(), this.gameTransformations.light1Direction()));
    }

    public void runWithScissor(@NotNull ScreenContext context, @NotNull Runnable renderer) {
        Scissor scissor = context.canvas().scissor();
        ScissorArea scissorArea = scissor.getScissorArea();
        if (scissorArea == null) {
            renderer.run();
        } else {
            renderDevice().applyScissor(setupScissorArea(scissorArea.bounds()), renderer);
        }
    }

    @NotNull
    public SyncedBufferPool createBufferPool(@NotNull Supplier<String> name, @NotNull BufferType bufferType, int bufferCount, int size) {
        return new SyncedBufferPool(renderDevice(), name, bufferType, bufferCount, size);
    }

    @NotNull
    public <T extends UniformBlock> T registerUniformBlock(@NotNull T uniformBlock) {
        this.uniformBlocks.add(uniformBlock);
        return uniformBlock;
    }

    @NotNull
    public RenderTarget resolveDrawRenderTarget() {
        ForeignRenderTarget drawTarget = this.foreignRenderTargetRegistry.findDrawTarget();
        if (drawTarget == null) {
            return Laby.labyAPI().minecraft().mainTarget();
        }
        return drawTarget;
    }

    @ApiStatus.Internal
    @NotNull
    public ForeignRenderTargetRegistry foreignRenderTargetRegistry() {
        if (this.foreignRenderTargetRegistry == null) {
            this.foreignRenderTargetRegistry = new GlForeignRenderTargetRegistry();
        }
        return this.foreignRenderTargetRegistry;
    }

    @ApiStatus.Internal
    @NotNull
    public ForeignDeviceTextureRegistry foreignDeviceTextureRegistry() {
        if (this.foreignDeviceTextureRegistry == null) {
            this.foreignDeviceTextureRegistry = new GlForeignDeviceTextureRegistry();
        }
        return this.foreignDeviceTextureRegistry;
    }

    @ApiStatus.Internal
    @NotNull
    public ImmediateDeviceBufferStorage immediateDeviceBufferStorage() {
        return this.immediateDeviceBufferStorage;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (ThreadSafe.isRenderThread()) {
            this.uniformBlocks.forEach((v0) -> {
                v0.close();
            });
            this.uniformBlocks.clear();
            this.renderTargetPool.close();
        }
    }

    private RenderDevice initializeRenderDevice() {
        RenderDevice renderDevice = createRenderDevice(buildShaderConfiguration());
        this.renderTargetPool = new RenderTargetPool(this);
        this.blurData = registerUniformBlock(new BlurDataUniformBlock(renderDevice));
        this.circleData = registerUniformBlock(new CircleDataUniformBlock(renderDevice));
        this.dynamicTransforms = registerUniformBlock(new DynamicTransformsUniformBlock(renderDevice));
        this.globals = registerUniformBlock(new GlobalsUniformBlock(renderDevice));
        this.projection = registerUniformBlock(new ProjectionUniformBlock(renderDevice));
        this.roundData = registerUniformBlock(new RoundDataUniformBlock(renderDevice));
        this.clipData = registerUniformBlock(new ClipDataUniformBlock(renderDevice));
        this.schematic = registerUniformBlock(new SchematicUniformBlock(renderDevice));
        this.sprayData = registerUniformBlock(new SprayDataUniformBlock(renderDevice));
        this.postProcessor = registerUniformBlock(new PostProcessorUniformBlock(renderDevice));
        this.cosmetics = registerUniformBlock(new CosmeticsUniformBlock(renderDevice));
        this.lighting = registerUniformBlock(new LightingUniformBlock(renderDevice));
        this.vanillaProjection = registerUniformBlock(new VanillaProjectionUniformBlock(renderDevice));
        this.vanillaDynamicTransforms = registerUniformBlock(new VanillaDynamicTransformsUniformBlock(renderDevice));
        this.vanillaFog = registerUniformBlock(new VanillaFogUniformBlock(renderDevice));
        this.graphicsWorkarounds = GraphicsWorkarounds.get(renderDevice);
        Laby.fireEvent(new RenderDeviceInitializedEvent());
        Laby.fireEvent(new UniformBlockRegistrationEvent(this::registerUniformBlock));
        return renderDevice;
    }

    private ShaderConfiguration buildShaderConfiguration() {
        return ShaderConfiguration.create(buildBuiltinShaders(BUILT_IN_DEFAULT), (shaderCache, preprocessor) -> {
            preprocessor.enableMojangImportBugCompat();
            if (!UBO) {
                preprocessor.registerLineTransformer(new GlPositionAssignmentLineTransformer());
            }
            preprocessor.registerDirective(new ImportDirective(shaderCache, "moj_import", argument -> {
                if (!argument.contains(":")) {
                    return "minecraft:" + argument;
                }
                return argument;
            }));
        });
    }

    private Set<AssetId> buildBuiltinShaders(ResourceLocation... locations) {
        Set<AssetId> shaders = new HashSet<>();
        for (ResourceLocation location : locations) {
            shaders.add(LOCATION_MAPPER.apply(location));
        }
        return shaders;
    }

    private Vector4i setupScissorArea(Rectangle bounds) {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        float height = window.getRawHeight();
        float scale = window.getScale();
        float scissorX = bounds.getLeft() * scale;
        float scissorY = height - (bounds.getBottom() * scale);
        float scissorWidth = bounds.getWidth() * scale;
        float scissorHeight = bounds.getHeight() * scale;
        return new Vector4i((int) scissorX, (int) scissorY, (int) Math.max(0.0f, scissorWidth), (int) Math.max(0.0f, scissorHeight));
    }

    private void evaluateFeatureStatus(BooleanSetter setter, BooleanSystemProperty property, Supplier<String> message) {
        evaluateFeatureStatus(setter, property, false, message);
    }

    private void evaluateFeatureStatus(BooleanSetter setter, BooleanSystemProperty property, boolean enforced, Supplier<String> message) {
        boolean active = enforced || isFeatureActive(property);
        setter.set(active);
        if (active) {
            LOGGER.info(message.get(), new Object[0]);
        }
    }

    private boolean isFeatureActive(BooleanSystemProperty property) {
        return IdeUtil.RUNNING_IN_IDE || property.get().booleanValue();
    }

    private static int buildDebuggerFlags() {
        int flags = 0;
        if (SystemProperties.RENDER_DEVICE_DEBUG_CONTEXT.get().booleanValue()) {
            flags = 0 | 1;
        }
        if (SystemProperties.RENDER_DEVICE_DEBUG_CONTEXT_SYNC.get().booleanValue()) {
            flags |= 2;
        }
        if (SystemProperties.RENDER_DEVICE_USER_DEBUG.get().booleanValue()) {
            flags |= 3;
        }
        return flags;
    }

    public void nextFrame() {
        this.buffer.clear();
    }

    public void storeStates() {
        GlRenderDevice glRenderDeviceRenderDevice = renderDevice();
        if (glRenderDeviceRenderDevice instanceof GlRenderDevice) {
            GlRenderDevice glRenderDevice = glRenderDeviceRenderDevice;
            glRenderDevice.storeState();
        }
    }

    public void restoreStates() {
        GlRenderDevice glRenderDeviceRenderDevice = renderDevice();
        if (glRenderDeviceRenderDevice instanceof GlRenderDevice) {
            GlRenderDevice glRenderDevice = glRenderDeviceRenderDevice;
            glRenderDevice.restoreState();
        }
    }

    public void copyTextureToTexture(@NotNull RenderTarget source, @NotNull RenderTarget destination, boolean workaround) {
        RenderState renderState;
        DeviceTextureView sourceTexture = source.findColorTexture(0);
        DeviceTextureView destinationTexture = destination.findColorTexture(0);
        RenderDevice renderDevice = renderDevice();
        if (this.graphicsWorkarounds.useDrawBlitter()) {
            CommandBuffer cmd = renderDevice.createCommandBuffer(1);
            try {
                cmd.beginPass(destination);
                Mesh mesh = this.intelWorkaroundMesh.compileOrGet(renderDevice, destination);
                if (workaround) {
                    renderState = RenderStates.GUI_TEXTURED_PREMULTIPLIED_ALPHA_WORKAROUND;
                } else {
                    renderState = RenderStates.GUI_TEXTURED_PREMULTIPLIED_ALPHA;
                }
                cmd.bindPipeline(renderState);
                cmd.bindTexture(0, sourceTexture);
                cmd.bindUniformBlock("Projection", projection());
                ProjectionUniformBlockData projectionData = new ProjectionUniformBlockData();
                projectionData.projectionMatrix().set(BLITTER_PROJECTION_MATRIX.getCached(destination.width(), destination.height()));
                cmd.bindUniformBlockData("Projection", projectionData);
                cmd.bindUniformBlock("DynamicTransforms", dynamicTransforms());
                DynamicTransformsUniformBlockData dynamicTransforms = new DynamicTransformsUniformBlockData();
                dynamicTransforms.modelViewMatrix().set(BLITTER_MODEL_VIEW_MATRIX);
                cmd.bindUniformBlockData("DynamicTransforms", dynamicTransforms);
                cmd.draw(mesh);
                cmd.endPass();
                cmd.submit();
                if (cmd != null) {
                    cmd.close();
                    return;
                }
                return;
            } catch (Throwable th) {
                if (cmd != null) {
                    try {
                        cmd.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        renderDevice.copyTextureToTexture(sourceTexture, destinationTexture);
    }

    public ObjectPool<Matrix4f> matrices() {
        return this.matrices;
    }

    public ObjectPool<Vector3f> vector3fs() {
        return this.vector3fs;
    }

    private long getId(int width, int height) {
        return (((long) width) << 32) | (((long) height) & ID_MASK);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/Laby3D$RenderTargetMesh.class */
    private static class RenderTargetMesh {
        private static final int DEFAULT_QUAD_MESH_SIZE = 6;
        private final Laby3D laby3D;
        private Mesh mesh;
        private int width;
        private int height;
        private IndexType indexType;

        public RenderTargetMesh(Laby3D laby3D) {
            this.laby3D = laby3D;
        }

        private Mesh compileOrGet(RenderDevice renderDevice, RenderTarget target) {
            SharedMeshIndexBuffer sharedMeshBuffer = this.laby3D.renderDevice().getSharedMeshBuffer(DrawingMode.QUADS);
            sharedMeshBuffer.getBuffer(6);
            if (this.mesh == null || isResizeRequired(target) || isIndexTypeMismatch(sharedMeshBuffer.getType()) || hasClosedBuffers(this.mesh.bufferResource())) {
                if (this.mesh != null) {
                    this.mesh.close();
                }
                this.mesh = compile(renderDevice, target.width(), target.height());
                this.width = target.width();
                this.height = target.height();
                this.indexType = sharedMeshBuffer.getType();
            }
            return this.mesh;
        }

        private boolean hasClosedBuffers(BufferResource resource) {
            return resource.vertexBuffer().isClosed() || resource.indexBuffer().isClosed();
        }

        private boolean isIndexTypeMismatch(IndexType indexType) {
            return this.indexType != indexType;
        }

        private boolean isResizeRequired(RenderTarget target) {
            return isResizeRequired(target.width(), target.height());
        }

        private boolean isResizeRequired(int width, int height) {
            return (this.width == width && this.height == height) ? false : true;
        }

        private Mesh compile(RenderDevice renderDevice, int width, int height) {
            BufferBuilder builder = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_UV_COLOR);
            builder.addVertex(0.0f, 0.0f, 0.0f).setUv(0.0f, 0.0f).setColor(-1);
            builder.addVertex(width, 0.0f, 0.0f).setUv(1.0f, 0.0f).setColor(-1);
            builder.addVertex(width, height, 0.0f).setUv(1.0f, 1.0f).setColor(-1);
            builder.addVertex(0.0f, height, 0.0f).setUv(0.0f, 1.0f).setColor(-1);
            return Mesh.create(renderDevice, () -> {
                return "Blit Quad";
            }, builder.build());
        }
    }
}
