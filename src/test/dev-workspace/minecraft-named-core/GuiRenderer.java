package net.minecraft.client.gui.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.pip.OversizedItemRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.gui.render.state.BlitRenderState;
import net.minecraft.client.gui.render.state.GlyphRenderState;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.gui.render.state.GuiItemRenderState;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.render.state.pip.OversizedItemRenderState;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/GuiRenderer.class */
public class GuiRenderer implements AutoCloseable {
    private static final float MAX_GUI_Z = 10000.0f;
    public static final float MIN_GUI_Z = 0.0f;
    private static final float GUI_Z_NEAR = 1000.0f;
    public static final int GUI_3D_Z_FAR = 1000;
    public static final int GUI_3D_Z_NEAR = -1000;
    public static final int DEFAULT_ITEM_SIZE = 16;
    private static final int MINIMUM_ITEM_ATLAS_SIZE = 512;
    public static final int CLEAR_COLOR = 0;
    final GuiRenderState renderState;
    private final MultiBufferSource.BufferSource bufferSource;
    private final SubmitNodeCollector submitNodeCollector;
    private final FeatureRenderDispatcher featureRenderDispatcher;
    private final Map<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> pictureInPictureRenderers;
    private GpuTexture itemsAtlas;
    private GpuTextureView itemsAtlasView;
    private GpuTexture itemsAtlasDepth;
    private GpuTextureView itemsAtlasDepthView;
    private int itemAtlasX;
    private int itemAtlasY;
    private int cachedGuiScale;
    private int frameNumber;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int MAXIMUM_ITEM_ATLAS_SIZE = RenderSystem.getDevice().getMaxTextureSize();
    private static final Comparator<ScreenRectangle> SCISSOR_COMPARATOR = Comparator.nullsFirst(Comparator.comparing((v0) -> {
        return v0.top();
    }).thenComparing((v0) -> {
        return v0.bottom();
    }).thenComparing((v0) -> {
        return v0.left();
    }).thenComparing((v0) -> {
        return v0.right();
    }));
    private static final Comparator<TextureSetup> TEXTURE_COMPARATOR = Comparator.nullsFirst(Comparator.comparing((v0) -> {
        return v0.getSortKey();
    }));
    private static final Comparator<GuiElementRenderState> ELEMENT_SORT_COMPARATOR = Comparator.comparing((v0) -> {
        return v0.scissorArea();
    }, SCISSOR_COMPARATOR).thenComparing((v0) -> {
        return v0.pipeline();
    }, Comparator.comparing((v0) -> {
        return v0.getSortKey();
    })).thenComparing((v0) -> {
        return v0.textureSetup();
    }, TEXTURE_COMPARATOR);
    private final Map<Object, AtlasPosition> atlasPositions = new Object2ObjectOpenHashMap();
    private final Map<Object, OversizedItemRenderer> oversizedItemRenderers = new Object2ObjectOpenHashMap();
    private final List<Draw> draws = new ArrayList();
    private final List<MeshToDraw> meshesToDraw = new ArrayList();
    private final ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE);
    private final Map<VertexFormat, MappableRingBuffer> vertexBuffers = new Object2ObjectOpenHashMap();
    private int firstDrawIndexAfterBlur = Integer.MAX_VALUE;
    private final CachedOrthoProjectionMatrixBuffer guiProjectionMatrixBuffer = new CachedOrthoProjectionMatrixBuffer("gui", GUI_Z_NEAR, 11000.0f, true);
    private final CachedOrthoProjectionMatrixBuffer itemsProjectionMatrixBuffer = new CachedOrthoProjectionMatrixBuffer("items", -1000.0f, GUI_Z_NEAR, true);
    private ScreenRectangle previousScissorArea = null;
    private RenderPipeline previousPipeline = null;
    private TextureSetup previousTextureSetup = null;
    private BufferBuilder bufferBuilder = null;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/GuiRenderer$Draw.class */
    static final class Draw extends Record {
        private final GpuBuffer vertexBuffer;
        private final int baseVertex;
        private final VertexFormat.Mode mode;
        private final int indexCount;
        private final RenderPipeline pipeline;
        private final TextureSetup textureSetup;
        private final ScreenRectangle scissorArea;

        Draw(GpuBuffer $$0, int $$1, VertexFormat.Mode $$2, int $$3, RenderPipeline $$4, TextureSetup $$5, ScreenRectangle $$6) {
            this.vertexBuffer = $$0;
            this.baseVertex = $$1;
            this.mode = $$2;
            this.indexCount = $$3;
            this.pipeline = $$4;
            this.textureSetup = $$5;
            this.scissorArea = $$6;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Draw.class), Draw.class, "vertexBuffer;baseVertex;mode;indexCount;pipeline;textureSetup;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->vertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->baseVertex:I", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->mode:Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->indexCount:I", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Draw.class), Draw.class, "vertexBuffer;baseVertex;mode;indexCount;pipeline;textureSetup;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->vertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->baseVertex:I", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->mode:Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->indexCount:I", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Draw.class, Object.class), Draw.class, "vertexBuffer;baseVertex;mode;indexCount;pipeline;textureSetup;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->vertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->baseVertex:I", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->mode:Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->indexCount:I", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$Draw;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public GpuBuffer vertexBuffer() {
            return this.vertexBuffer;
        }

        public int baseVertex() {
            return this.baseVertex;
        }

        public VertexFormat.Mode mode() {
            return this.mode;
        }

        public int indexCount() {
            return this.indexCount;
        }

        public RenderPipeline pipeline() {
            return this.pipeline;
        }

        public TextureSetup textureSetup() {
            return this.textureSetup;
        }

        public ScreenRectangle scissorArea() {
            return this.scissorArea;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/GuiRenderer$MeshToDraw.class */
    static final class MeshToDraw extends Record implements AutoCloseable {
        private final MeshData mesh;
        private final RenderPipeline pipeline;
        private final TextureSetup textureSetup;
        private final ScreenRectangle scissorArea;

        MeshToDraw(MeshData $$0, RenderPipeline $$1, TextureSetup $$2, ScreenRectangle $$3) {
            this.mesh = $$0;
            this.pipeline = $$1;
            this.textureSetup = $$2;
            this.scissorArea = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MeshToDraw.class), MeshToDraw.class, "mesh;pipeline;textureSetup;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->mesh:Lcom/mojang/blaze3d/vertex/MeshData;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MeshToDraw.class), MeshToDraw.class, "mesh;pipeline;textureSetup;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->mesh:Lcom/mojang/blaze3d/vertex/MeshData;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MeshToDraw.class, Object.class), MeshToDraw.class, "mesh;pipeline;textureSetup;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->mesh:Lcom/mojang/blaze3d/vertex/MeshData;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->pipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->textureSetup:Lnet/minecraft/client/gui/render/TextureSetup;", "FIELD:Lnet/minecraft/client/gui/render/GuiRenderer$MeshToDraw;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public MeshData mesh() {
            return this.mesh;
        }

        public RenderPipeline pipeline() {
            return this.pipeline;
        }

        public TextureSetup textureSetup() {
            return this.textureSetup;
        }

        public ScreenRectangle scissorArea() {
            return this.scissorArea;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mesh.close();
        }
    }

    public GuiRenderer(GuiRenderState $$0, MultiBufferSource.BufferSource $$1, SubmitNodeCollector $$2, FeatureRenderDispatcher $$3, List<PictureInPictureRenderer<?>> $$4) {
        this.renderState = $$0;
        this.bufferSource = $$1;
        this.submitNodeCollector = $$2;
        this.featureRenderDispatcher = $$3;
        ImmutableMap.Builder<Class<? extends PictureInPictureRenderState>, PictureInPictureRenderer<?>> $$5 = ImmutableMap.builder();
        for (PictureInPictureRenderer<?> $$6 : $$4) {
            $$5.put($$6.getRenderStateClass(), $$6);
        }
        this.pictureInPictureRenderers = $$5.buildOrThrow();
    }

    public void incrementFrameNumber() {
        this.frameNumber++;
    }

    public void render(GpuBufferSlice $$0) {
        prepare();
        draw($$0);
        for (MappableRingBuffer $$1 : this.vertexBuffers.values()) {
            $$1.rotate();
        }
        this.draws.clear();
        this.meshesToDraw.clear();
        this.renderState.reset();
        this.firstDrawIndexAfterBlur = Integer.MAX_VALUE;
        clearUnusedOversizedItemRenderers();
        if (SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER) {
            RenderPipeline.updateSortKeySeed();
            TextureSetup.updateSortKeySeed();
        }
    }

    private void clearUnusedOversizedItemRenderers() {
        Iterator<Map.Entry<Object, OversizedItemRenderer>> $$0 = this.oversizedItemRenderers.entrySet().iterator();
        while ($$0.hasNext()) {
            Map.Entry<Object, OversizedItemRenderer> $$1 = $$0.next();
            OversizedItemRenderer $$2 = $$1.getValue();
            if (!$$2.usedOnThisFrame()) {
                $$2.close();
                $$0.remove();
            } else {
                $$2.resetUsedOnThisFrame();
            }
        }
    }

    private void prepare() {
        this.bufferSource.endBatch();
        preparePictureInPicture();
        prepareItemElements();
        prepareText();
        this.renderState.sortElements(ELEMENT_SORT_COMPARATOR);
        addElementsToMeshes(GuiRenderState.TraverseRange.BEFORE_BLUR);
        this.firstDrawIndexAfterBlur = this.meshesToDraw.size();
        addElementsToMeshes(GuiRenderState.TraverseRange.AFTER_BLUR);
        recordDraws();
    }

    private void addElementsToMeshes(GuiRenderState.TraverseRange $$0) {
        this.previousScissorArea = null;
        this.previousPipeline = null;
        this.previousTextureSetup = null;
        this.bufferBuilder = null;
        this.renderState.forEachElement(this::addElementToMesh, $$0);
        if (this.bufferBuilder != null) {
            recordMesh(this.bufferBuilder, this.previousPipeline, this.previousTextureSetup, this.previousScissorArea);
        }
    }

    private void draw(GpuBufferSlice $$0) {
        if (this.draws.isEmpty()) {
            return;
        }
        Minecraft $$1 = Minecraft.getInstance();
        Window $$2 = $$1.getWindow();
        RenderSystem.setProjectionMatrix(this.guiProjectionMatrixBuffer.getBuffer($$2.getWidth() / $$2.getGuiScale(), $$2.getHeight() / $$2.getGuiScale()), ProjectionType.ORTHOGRAPHIC);
        RenderTarget $$3 = $$1.getMainRenderTarget();
        int $$4 = 0;
        for (Draw $$5 : this.draws) {
            if ($$5.indexCount > $$4) {
                $$4 = $$5.indexCount;
            }
        }
        RenderSystem.AutoStorageIndexBuffer $$6 = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
        GpuBuffer $$7 = $$6.getBuffer($$4);
        VertexFormat.IndexType $$8 = $$6.type();
        GpuBufferSlice $$9 = RenderSystem.getDynamicUniforms().writeTransform(new Matrix4f().setTranslation(0.0f, 0.0f, -11000.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector3f(), new Matrix4f());
        if (this.firstDrawIndexAfterBlur > 0) {
            executeDrawRange(() -> {
                return "GUI before blur";
            }, $$3, $$0, $$9, $$7, $$8, 0, Math.min(this.firstDrawIndexAfterBlur, this.draws.size()));
        }
        if (this.draws.size() <= this.firstDrawIndexAfterBlur) {
            return;
        }
        RenderSystem.getDevice().createCommandEncoder().clearDepthTexture($$3.getDepthTexture(), 1.0d);
        $$1.gameRenderer.processBlurEffect();
        executeDrawRange(() -> {
            return "GUI after blur";
        }, $$3, $$0, $$9, $$7, $$8, this.firstDrawIndexAfterBlur, this.draws.size());
    }

    private void executeDrawRange(Supplier<String> $$0, RenderTarget $$1, GpuBufferSlice $$2, GpuBufferSlice $$3, GpuBuffer $$4, VertexFormat.IndexType $$5, int $$6, int $$7) {
        RenderPass $$8 = RenderSystem.getDevice().createCommandEncoder().createRenderPass($$0, $$1.getColorTextureView(), OptionalInt.empty(), $$1.useDepth ? $$1.getDepthTextureView() : null, OptionalDouble.empty());
        try {
            RenderSystem.bindDefaultUniforms($$8);
            $$8.setUniform("Fog", $$2);
            $$8.setUniform("DynamicTransforms", $$3);
            for (int $$9 = $$6; $$9 < $$7; $$9++) {
                Draw $$10 = this.draws.get($$9);
                executeDraw($$10, $$8, $$4, $$5);
            }
            if ($$8 != null) {
                $$8.close();
            }
        } catch (Throwable th) {
            if ($$8 != null) {
                try {
                    $$8.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void addElementToMesh(GuiElementRenderState $$0) {
        RenderPipeline $$1 = $$0.pipeline();
        TextureSetup $$2 = $$0.textureSetup();
        ScreenRectangle $$3 = $$0.scissorArea();
        if ($$1 != this.previousPipeline || scissorChanged($$3, this.previousScissorArea) || !$$2.equals(this.previousTextureSetup)) {
            if (this.bufferBuilder != null) {
                recordMesh(this.bufferBuilder, this.previousPipeline, this.previousTextureSetup, this.previousScissorArea);
            }
            this.bufferBuilder = getBufferBuilder($$1);
            this.previousPipeline = $$1;
            this.previousTextureSetup = $$2;
            this.previousScissorArea = $$3;
        }
        $$0.buildVertices(this.bufferBuilder);
    }

    private void prepareText() {
        this.renderState.forEachText($$0 -> {
            final Matrix3x2fc $$1 = $$0.pose;
            final ScreenRectangle $$2 = $$0.scissor;
            $$0.ensurePrepared().visit(new Font.GlyphVisitor() { // from class: net.minecraft.client.gui.render.GuiRenderer.1
                @Override // net.minecraft.client.gui.Font.GlyphVisitor
                public void acceptGlyph(TextRenderable.Styled $$0) {
                    accept($$0);
                }

                @Override // net.minecraft.client.gui.Font.GlyphVisitor
                public void acceptEffect(TextRenderable $$0) {
                    accept($$0);
                }

                private void accept(TextRenderable $$0) {
                    GuiRenderer.this.renderState.submitGlyphToCurrentLayer(new GlyphRenderState($$1, $$0, $$2));
                }
            });
        });
    }

    private void prepareItemElements() {
        if (this.renderState.getItemModelIdentities().isEmpty()) {
            return;
        }
        int $$0 = getGuiScaleInvalidatingItemAtlasIfChanged();
        int $$1 = 16 * $$0;
        int $$2 = calculateAtlasSizeInPixels($$1);
        if (this.itemsAtlas == null) {
            createAtlasTextures($$2);
        }
        RenderSystem.outputColorTextureOverride = this.itemsAtlasView;
        RenderSystem.outputDepthTextureOverride = this.itemsAtlasDepthView;
        RenderSystem.setProjectionMatrix(this.itemsProjectionMatrixBuffer.getBuffer($$2, $$2), ProjectionType.ORTHOGRAPHIC);
        Minecraft.getInstance().gameRenderer.getLighting().setupFor(Lighting.Entry.ITEMS_3D);
        PoseStack $$3 = new PoseStack();
        MutableBoolean $$4 = new MutableBoolean(false);
        MutableBoolean $$5 = new MutableBoolean(false);
        this.renderState.forEachItem($$52 -> {
            if ($$52.oversizedItemBounds() != null) {
                $$5.setTrue();
                return;
            }
            TrackingItemStackRenderState $$6 = $$52.itemStackRenderState();
            AtlasPosition $$7 = this.atlasPositions.get($$6.getModelIdentity());
            if ($$7 != null && (!$$6.isAnimated() || $$7.lastAnimatedOnFrame == this.frameNumber)) {
                submitBlitFromItemAtlas($$52, $$7.u, $$7.v, $$1, $$2);
                return;
            }
            if (this.itemAtlasX + $$1 > $$2) {
                this.itemAtlasX = 0;
                this.itemAtlasY += $$1;
            }
            boolean $$8 = $$6.isAnimated() && $$7 != null;
            if (!$$8 && this.itemAtlasY + $$1 > $$2) {
                if ($$4.isFalse()) {
                    LOGGER.warn("Trying to render too many items in GUI at the same time. Skipping some of them.");
                    $$4.setTrue();
                    return;
                }
                return;
            }
            int $$9 = $$8 ? $$7.x : this.itemAtlasX;
            int $$10 = $$8 ? $$7.y : this.itemAtlasY;
            if ($$8) {
                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.itemsAtlas, 0, this.itemsAtlasDepth, 1.0d, $$9, ($$2 - $$10) - $$1, $$1, $$1);
            }
            renderItemToAtlas($$6, $$3, $$9, $$10, $$1);
            float $$11 = $$9 / $$2;
            float $$12 = ($$2 - $$10) / $$2;
            submitBlitFromItemAtlas($$52, $$11, $$12, $$1, $$2);
            if ($$8) {
                $$7.lastAnimatedOnFrame = this.frameNumber;
            } else {
                this.atlasPositions.put($$52.itemStackRenderState().getModelIdentity(), new AtlasPosition(this.itemAtlasX, this.itemAtlasY, $$11, $$12, this.frameNumber));
                this.itemAtlasX += $$1;
            }
        });
        RenderSystem.outputColorTextureOverride = null;
        RenderSystem.outputDepthTextureOverride = null;
        if ($$5.booleanValue()) {
            this.renderState.forEachItem($$12 -> {
                if ($$12.oversizedItemBounds() != null) {
                    TrackingItemStackRenderState $$22 = $$12.itemStackRenderState();
                    OversizedItemRenderer $$32 = this.oversizedItemRenderers.computeIfAbsent($$22.getModelIdentity(), $$02 -> {
                        return new OversizedItemRenderer(this.bufferSource);
                    });
                    ScreenRectangle $$42 = $$12.oversizedItemBounds();
                    OversizedItemRenderState $$53 = new OversizedItemRenderState($$12, $$42.left(), $$42.top(), $$42.right(), $$42.bottom());
                    $$32.prepare($$53, this.renderState, $$0);
                }
            });
        }
    }

    private void preparePictureInPicture() {
        int $$0 = Minecraft.getInstance().getWindow().getGuiScale();
        this.renderState.forEachPictureInPicture($$1 -> {
            preparePictureInPictureState($$1, $$0);
        });
    }

    private <T extends PictureInPictureRenderState> void preparePictureInPictureState(T $$0, int $$1) {
        PictureInPictureRenderer<?> pictureInPictureRenderer = this.pictureInPictureRenderers.get($$0.getClass());
        if (pictureInPictureRenderer != null) {
            pictureInPictureRenderer.prepare($$0, this.renderState, $$1);
        }
    }

    private void renderItemToAtlas(TrackingItemStackRenderState $$0, PoseStack $$1, int $$2, int $$3, int $$4) {
        $$1.pushPose();
        $$1.translate($$2 + ($$4 / 2.0f), $$3 + ($$4 / 2.0f), 0.0f);
        $$1.scale($$4, -$$4, $$4);
        boolean $$5 = !$$0.usesBlockLight();
        if ($$5) {
            Minecraft.getInstance().gameRenderer.getLighting().setupFor(Lighting.Entry.ITEMS_FLAT);
        } else {
            Minecraft.getInstance().gameRenderer.getLighting().setupFor(Lighting.Entry.ITEMS_3D);
        }
        RenderSystem.enableScissorForRenderTypeDraws($$2, (this.itemsAtlas.getHeight(0) - $$3) - $$4, $$4, $$4);
        $$0.submit($$1, this.submitNodeCollector, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0);
        this.featureRenderDispatcher.renderAllFeatures();
        this.bufferSource.endBatch();
        RenderSystem.disableScissorForRenderTypeDraws();
        $$1.popPose();
    }

    private void submitBlitFromItemAtlas(GuiItemRenderState $$0, float $$1, float $$2, int $$3, int $$4) {
        float $$5 = $$1 + ($$3 / $$4);
        float $$6 = $$2 + ((-$$3) / $$4);
        this.renderState.submitBlitToCurrentLayer(new BlitRenderState(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, TextureSetup.singleTexture(this.itemsAtlasView, RenderSystem.getSamplerCache().getRepeat(FilterMode.NEAREST)), $$0.pose(), $$0.x(), $$0.y(), $$0.x() + 16, $$0.y() + 16, $$1, $$5, $$2, $$6, -1, $$0.scissorArea(), null));
    }

    private void createAtlasTextures(int $$0) {
        GpuDevice $$1 = RenderSystem.getDevice();
        this.itemsAtlas = $$1.createTexture("UI items atlas", 12, TextureFormat.RGBA8, $$0, $$0, 1, 1);
        this.itemsAtlasView = $$1.createTextureView(this.itemsAtlas);
        this.itemsAtlasDepth = $$1.createTexture("UI items atlas depth", 8, TextureFormat.DEPTH32, $$0, $$0, 1, 1);
        this.itemsAtlasDepthView = $$1.createTextureView(this.itemsAtlasDepth);
        $$1.createCommandEncoder().clearColorAndDepthTextures(this.itemsAtlas, 0, this.itemsAtlasDepth, 1.0d);
    }

    private int calculateAtlasSizeInPixels(int $$0) {
        int $$3;
        Set<Object> $$1 = this.renderState.getItemModelIdentities();
        if (this.atlasPositions.isEmpty()) {
            $$3 = $$1.size();
        } else {
            $$3 = this.atlasPositions.size();
            for (Object $$4 : $$1) {
                if (!this.atlasPositions.containsKey($$4)) {
                    $$3++;
                }
            }
        }
        if (this.itemsAtlas != null) {
            int $$5 = this.itemsAtlas.getWidth(0) / $$0;
            int $$6 = $$5 * $$5;
            if ($$3 < $$6) {
                return this.itemsAtlas.getWidth(0);
            }
            invalidateItemAtlas();
        }
        int $$7 = $$1.size();
        int $$8 = Mth.smallestSquareSide($$7 + ($$7 / 2));
        return Math.clamp(Mth.smallestEncompassingPowerOfTwo($$8 * $$0), 512, MAXIMUM_ITEM_ATLAS_SIZE);
    }

    private int getGuiScaleInvalidatingItemAtlasIfChanged() {
        int $$0 = Minecraft.getInstance().getWindow().getGuiScale();
        if ($$0 != this.cachedGuiScale) {
            invalidateItemAtlas();
            for (OversizedItemRenderer $$1 : this.oversizedItemRenderers.values()) {
                $$1.invalidateTexture();
            }
            this.cachedGuiScale = $$0;
        }
        return $$0;
    }

    private void invalidateItemAtlas() {
        this.itemAtlasX = 0;
        this.itemAtlasY = 0;
        this.atlasPositions.clear();
        if (this.itemsAtlas != null) {
            this.itemsAtlas.close();
            this.itemsAtlas = null;
        }
        if (this.itemsAtlasView != null) {
            this.itemsAtlasView.close();
            this.itemsAtlasView = null;
        }
        if (this.itemsAtlasDepth != null) {
            this.itemsAtlasDepth.close();
            this.itemsAtlasDepth = null;
        }
        if (this.itemsAtlasDepthView != null) {
            this.itemsAtlasDepthView.close();
            this.itemsAtlasDepthView = null;
        }
    }

    private void recordMesh(BufferBuilder $$0, RenderPipeline $$1, TextureSetup $$2, ScreenRectangle $$3) {
        MeshData $$4 = $$0.build();
        if ($$4 != null) {
            this.meshesToDraw.add(new MeshToDraw($$4, $$1, $$2, $$3));
        }
    }

    private void recordDraws() {
        ensureVertexBufferSizes();
        CommandEncoder $$0 = RenderSystem.getDevice().createCommandEncoder();
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        for (MeshToDraw $$2 : this.meshesToDraw) {
            MeshData $$3 = $$2.mesh;
            MeshData.DrawState $$4 = $$3.drawState();
            VertexFormat $$5 = $$4.format();
            MappableRingBuffer $$6 = this.vertexBuffers.get($$5);
            if (!object2IntOpenHashMap.containsKey($$5)) {
                object2IntOpenHashMap.put($$5, 0);
            }
            ByteBuffer $$7 = $$3.vertexBuffer();
            int $$8 = $$7.remaining();
            int $$9 = object2IntOpenHashMap.getInt($$5);
            GpuBuffer.MappedView $$10 = $$0.mapBuffer($$6.currentBuffer().slice($$9, $$8), false, true);
            try {
                MemoryUtil.memCopy($$7, $$10.data());
                if ($$10 != null) {
                    $$10.close();
                }
                object2IntOpenHashMap.put($$5, $$9 + $$8);
                this.draws.add(new Draw($$6.currentBuffer(), $$9 / $$5.getVertexSize(), $$4.mode(), $$4.indexCount(), $$2.pipeline, $$2.textureSetup, $$2.scissorArea));
                $$2.close();
            } catch (Throwable th) {
                if ($$10 != null) {
                    try {
                        $$10.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    private void ensureVertexBufferSizes() {
        Object2IntMap<VertexFormat> $$0 = calculatedRequiredVertexBufferSizes();
        ObjectIterator it = $$0.object2IntEntrySet().iterator();
        while (it.hasNext()) {
            Object2IntMap.Entry<VertexFormat> $$1 = (Object2IntMap.Entry) it.next();
            VertexFormat $$2 = (VertexFormat) $$1.getKey();
            int $$3 = $$1.getIntValue();
            MappableRingBuffer $$4 = this.vertexBuffers.get($$2);
            if ($$4 == null || $$4.size() < $$3) {
                if ($$4 != null) {
                    $$4.close();
                }
                this.vertexBuffers.put($$2, new MappableRingBuffer(() -> {
                    return "GUI vertex buffer for " + String.valueOf($$2);
                }, 34, $$3));
            }
        }
    }

    private Object2IntMap<VertexFormat> calculatedRequiredVertexBufferSizes() {
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        for (MeshToDraw $$1 : this.meshesToDraw) {
            MeshData.DrawState $$2 = $$1.mesh.drawState();
            VertexFormat $$3 = $$2.format();
            if (!object2IntOpenHashMap.containsKey($$3)) {
                object2IntOpenHashMap.put($$3, 0);
            }
            object2IntOpenHashMap.put($$3, object2IntOpenHashMap.getInt($$3) + ($$2.vertexCount() * $$3.getVertexSize()));
        }
        return object2IntOpenHashMap;
    }

    private void executeDraw(Draw $$0, RenderPass $$1, GpuBuffer $$2, VertexFormat.IndexType $$3) {
        RenderPipeline $$4 = $$0.pipeline();
        $$1.setPipeline($$4);
        $$1.setVertexBuffer(0, $$0.vertexBuffer);
        ScreenRectangle $$5 = $$0.scissorArea();
        if ($$5 != null) {
            enableScissor($$5, $$1);
        } else {
            $$1.disableScissor();
        }
        if ($$0.textureSetup.texure0() != null) {
            $$1.bindTexture("Sampler0", $$0.textureSetup.texure0(), $$0.textureSetup.sampler0());
        }
        if ($$0.textureSetup.texure1() != null) {
            $$1.bindTexture("Sampler1", $$0.textureSetup.texure1(), $$0.textureSetup.sampler1());
        }
        if ($$0.textureSetup.texure2() != null) {
            $$1.bindTexture("Sampler2", $$0.textureSetup.texure2(), $$0.textureSetup.sampler2());
        }
        $$1.setIndexBuffer($$2, $$3);
        $$1.drawIndexed($$0.baseVertex, 0, $$0.indexCount, 1);
    }

    private BufferBuilder getBufferBuilder(RenderPipeline $$0) {
        return new BufferBuilder(this.byteBufferBuilder, $$0.getVertexFormatMode(), $$0.getVertexFormat());
    }

    private boolean scissorChanged(ScreenRectangle $$0, ScreenRectangle $$1) {
        if ($$0 == $$1) {
            return false;
        }
        return $$0 == null || !$$0.equals($$1);
    }

    private void enableScissor(ScreenRectangle $$0, RenderPass $$1) {
        Window $$2 = Minecraft.getInstance().getWindow();
        int $$3 = $$2.getHeight();
        int $$4 = $$2.getGuiScale();
        double $$5 = $$0.left() * $$4;
        double $$6 = $$3 - ($$0.bottom() * $$4);
        double $$7 = $$0.width() * $$4;
        double $$8 = $$0.height() * $$4;
        $$1.enableScissor((int) $$5, (int) $$6, Math.max(0, (int) $$7), Math.max(0, (int) $$8));
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.byteBufferBuilder.close();
        if (this.itemsAtlas != null) {
            this.itemsAtlas.close();
        }
        if (this.itemsAtlasView != null) {
            this.itemsAtlasView.close();
        }
        if (this.itemsAtlasDepth != null) {
            this.itemsAtlasDepth.close();
        }
        if (this.itemsAtlasDepthView != null) {
            this.itemsAtlasDepthView.close();
        }
        this.pictureInPictureRenderers.values().forEach((v0) -> {
            v0.close();
        });
        this.guiProjectionMatrixBuffer.close();
        this.itemsProjectionMatrixBuffer.close();
        for (MappableRingBuffer $$0 : this.vertexBuffers.values()) {
            $$0.close();
        }
        this.oversizedItemRenderers.values().forEach((v0) -> {
            v0.close();
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/GuiRenderer$AtlasPosition.class */
    static final class AtlasPosition {
        final int x;
        final int y;
        final float u;
        final float v;
        int lastAnimatedOnFrame;

        AtlasPosition(int $$0, int $$1, float $$2, float $$3, int $$4) {
            this.x = $$0;
            this.y = $$1;
            this.u = $$2;
            this.v = $$3;
            this.lastAnimatedOnFrame = $$4;
        }
    }
}
