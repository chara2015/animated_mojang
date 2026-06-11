package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.resource.RenderTargetDescriptor;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.PrioritizeChunkUpdates;
import net.minecraft.client.TextureFilteringMethod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.DynamicUniforms;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.chunk.CompiledSectionMesh;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import net.minecraft.client.renderer.chunk.SectionBuffers;
import net.minecraft.client.renderer.chunk.SectionMesh;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.chunk.TranslucencyPointOfView;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.GameTestBlockHighlightRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.BlockBreakingRenderState;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.ParticlesRenderState;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.gizmos.SimpleGizmoCollector;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.util.Brightness;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.joml.Vector4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/LevelRenderer.class */
public class LevelRenderer implements ResourceManagerReloadListener, AutoCloseable {
    private static final Identifier TRANSPARENCY_POST_CHAIN_ID = Identifier.withDefaultNamespace("transparency");
    private static final Identifier ENTITY_OUTLINE_POST_CHAIN_ID = Identifier.withDefaultNamespace("entity_outline");
    public static final int SECTION_SIZE = 16;
    public static final int HALF_SECTION_SIZE = 8;
    public static final int NEARBY_SECTION_DISTANCE_IN_BLOCKS = 32;
    private static final int MINIMUM_TRANSPARENT_SORT_COUNT = 15;
    private static final float CHUNK_VISIBILITY_THRESHOLD = 0.3f;
    private final Minecraft minecraft;
    private final EntityRenderDispatcher entityRenderDispatcher;
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    private final RenderBuffers renderBuffers;
    private SkyRenderer skyRenderer;
    private ClientLevel level;
    private ViewArea viewArea;
    private int ticks;
    private RenderTarget entityOutlineTarget;
    private SectionRenderDispatcher sectionRenderDispatcher;
    private boolean captureFrustum;
    private Frustum capturedFrustum;
    private BlockPos lastTranslucentSortBlockPos;
    private int translucencyResortIterationIndex;
    private final LevelRenderState levelRenderState;
    private final SubmitNodeStorage submitNodeStorage;
    private final FeatureRenderDispatcher featureRenderDispatcher;
    private GpuSampler chunkLayerSampler;
    private final CloudRenderer cloudRenderer = new CloudRenderer();
    private final WorldBorderRenderer worldBorderRenderer = new WorldBorderRenderer();
    private final WeatherEffectRenderer weatherEffectRenderer = new WeatherEffectRenderer();
    private final ParticlesRenderState particlesRenderState = new ParticlesRenderState();
    public final DebugRenderer debugRenderer = new DebugRenderer();
    public final GameTestBlockHighlightRenderer gameTestBlockHighlightRenderer = new GameTestBlockHighlightRenderer();
    private final SectionOcclusionGraph sectionOcclusionGraph = new SectionOcclusionGraph();
    private final ObjectArrayList<SectionRenderDispatcher.RenderSection> visibleSections = new ObjectArrayList<>(10000);
    private final ObjectArrayList<SectionRenderDispatcher.RenderSection> nearbyVisibleSections = new ObjectArrayList<>(50);
    private final Int2ObjectMap<BlockDestructionProgress> destroyingBlocks = new Int2ObjectOpenHashMap();
    private final Long2ObjectMap<SortedSet<BlockDestructionProgress>> destructionProgress = new Long2ObjectOpenHashMap();
    private final LevelTargetBundle targets = new LevelTargetBundle();
    private int lastCameraSectionX = ChunkSkyLightSources.NEGATIVE_INFINITY;
    private int lastCameraSectionY = ChunkSkyLightSources.NEGATIVE_INFINITY;
    private int lastCameraSectionZ = ChunkSkyLightSources.NEGATIVE_INFINITY;
    private double prevCamX = Double.MIN_VALUE;
    private double prevCamY = Double.MIN_VALUE;
    private double prevCamZ = Double.MIN_VALUE;
    private double prevCamRotX = Double.MIN_VALUE;
    private double prevCamRotY = Double.MIN_VALUE;
    private int lastViewDistance = -1;
    private final SimpleGizmoCollector collectedGizmos = new SimpleGizmoCollector();
    private FinalizedGizmos finalizedGizmos = new FinalizedGizmos(new DrawableGizmoPrimitives(), new DrawableGizmoPrimitives());

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/LevelRenderer$BrightnessGetter.class */
    @FunctionalInterface
    public interface BrightnessGetter {
        public static final BrightnessGetter DEFAULT = ($$0, $$1) -> {
            int $$2 = $$0.getBrightness(LightLayer.SKY, $$1);
            int $$3 = $$0.getBrightness(LightLayer.BLOCK, $$1);
            return Brightness.pack($$3, $$2);
        };

        int packedBrightness(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos);
    }

    public LevelRenderer(Minecraft $$0, EntityRenderDispatcher $$1, BlockEntityRenderDispatcher $$2, RenderBuffers $$3, LevelRenderState $$4, FeatureRenderDispatcher $$5) {
        this.minecraft = $$0;
        this.entityRenderDispatcher = $$1;
        this.blockEntityRenderDispatcher = $$2;
        this.renderBuffers = $$3;
        this.submitNodeStorage = $$5.getSubmitNodeStorage();
        this.levelRenderState = $$4;
        this.featureRenderDispatcher = $$5;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.entityOutlineTarget != null) {
            this.entityOutlineTarget.destroyBuffers();
        }
        if (this.skyRenderer != null) {
            this.skyRenderer.close();
        }
        if (this.chunkLayerSampler != null) {
            this.chunkLayerSampler.close();
        }
        this.cloudRenderer.close();
    }

    @Override // net.minecraft.server.packs.resources.ResourceManagerReloadListener
    public void onResourceManagerReload(ResourceManager $$0) {
        initOutline();
        if (this.skyRenderer != null) {
            this.skyRenderer.close();
        }
        this.skyRenderer = new SkyRenderer(this.minecraft.getTextureManager(), this.minecraft.getAtlasManager());
    }

    public void initOutline() {
        if (this.entityOutlineTarget != null) {
            this.entityOutlineTarget.destroyBuffers();
        }
        this.entityOutlineTarget = new TextureTarget("Entity Outline", this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), true);
    }

    private PostChain getTransparencyChain() {
        if (!Minecraft.useShaderTransparency()) {
            return null;
        }
        PostChain $$0 = this.minecraft.getShaderManager().getPostChain(TRANSPARENCY_POST_CHAIN_ID, LevelTargetBundle.SORTING_TARGETS);
        if ($$0 == null) {
            this.minecraft.options.improvedTransparency().set(false);
            this.minecraft.options.save();
        }
        return $$0;
    }

    public void doEntityOutline() {
        if (shouldShowEntityOutlines()) {
            this.entityOutlineTarget.blitAndBlendToTexture(this.minecraft.getMainRenderTarget().getColorTextureView());
        }
    }

    protected boolean shouldShowEntityOutlines() {
        return (this.minecraft.gameRenderer.isPanoramicMode() || this.entityOutlineTarget == null || this.minecraft.player == null) ? false : true;
    }

    public void setLevel(ClientLevel $$0) {
        this.lastCameraSectionX = ChunkSkyLightSources.NEGATIVE_INFINITY;
        this.lastCameraSectionY = ChunkSkyLightSources.NEGATIVE_INFINITY;
        this.lastCameraSectionZ = ChunkSkyLightSources.NEGATIVE_INFINITY;
        this.level = $$0;
        if ($$0 != null) {
            allChanged();
        } else {
            this.entityRenderDispatcher.resetCamera();
            if (this.viewArea != null) {
                this.viewArea.releaseAllBuffers();
                this.viewArea = null;
            }
            if (this.sectionRenderDispatcher != null) {
                this.sectionRenderDispatcher.dispose();
            }
            this.sectionRenderDispatcher = null;
            this.sectionOcclusionGraph.waitAndReset(null);
            clearVisibleSections();
        }
        this.gameTestBlockHighlightRenderer.clear();
    }

    private void clearVisibleSections() {
        this.visibleSections.clear();
        this.nearbyVisibleSections.clear();
    }

    public void allChanged() {
        if (this.level == null) {
            return;
        }
        this.level.clearTintCaches();
        if (this.sectionRenderDispatcher == null) {
            this.sectionRenderDispatcher = new SectionRenderDispatcher(this.level, this, Util.backgroundExecutor(), this.renderBuffers, this.minecraft.getBlockRenderer(), this.minecraft.getBlockEntityRenderDispatcher());
        } else {
            this.sectionRenderDispatcher.setLevel(this.level);
        }
        this.cloudRenderer.markForRebuild();
        ItemBlockRenderTypes.setCutoutLeaves(this.minecraft.options.cutoutLeaves().get().booleanValue());
        LeavesBlock.setCutoutLeaves(this.minecraft.options.cutoutLeaves().get().booleanValue());
        this.lastViewDistance = this.minecraft.options.getEffectiveRenderDistance();
        if (this.viewArea != null) {
            this.viewArea.releaseAllBuffers();
        }
        this.sectionRenderDispatcher.clearCompileQueue();
        this.viewArea = new ViewArea(this.sectionRenderDispatcher, this.level, this.minecraft.options.getEffectiveRenderDistance(), this);
        this.sectionOcclusionGraph.waitAndReset(this.viewArea);
        clearVisibleSections();
        Camera $$0 = this.minecraft.gameRenderer.getMainCamera();
        this.viewArea.repositionCamera(SectionPos.of($$0.position()));
    }

    public void resize(int $$0, int $$1) {
        needsUpdate();
        if (this.entityOutlineTarget != null) {
            this.entityOutlineTarget.resize($$0, $$1);
        }
    }

    public String getSectionStatistics() {
        if (this.viewArea == null) {
            return null;
        }
        int $$0 = this.viewArea.sections.length;
        int $$1 = countRenderedSections();
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[5];
        objArr[0] = Integer.valueOf($$1);
        objArr[1] = Integer.valueOf($$0);
        objArr[2] = this.minecraft.smartCull ? "(s) " : "";
        objArr[3] = Integer.valueOf(this.lastViewDistance);
        objArr[4] = this.sectionRenderDispatcher == null ? "null" : this.sectionRenderDispatcher.getStats();
        return String.format(locale, "C: %d/%d %sD: %d, %s", objArr);
    }

    public SectionRenderDispatcher getSectionRenderDispatcher() {
        return this.sectionRenderDispatcher;
    }

    public double getTotalSections() {
        return this.viewArea == null ? Density.SURFACE : this.viewArea.sections.length;
    }

    public double getLastViewDistance() {
        return this.lastViewDistance;
    }

    public int countRenderedSections() {
        int $$0 = 0;
        ObjectListIterator it = this.visibleSections.iterator();
        while (it.hasNext()) {
            SectionRenderDispatcher.RenderSection $$1 = (SectionRenderDispatcher.RenderSection) it.next();
            if ($$1.getSectionMesh().hasRenderableLayers()) {
                $$0++;
            }
        }
        return $$0;
    }

    public void resetSampler() {
        if (this.chunkLayerSampler != null) {
            this.chunkLayerSampler.close();
        }
        this.chunkLayerSampler = null;
    }

    public String getEntityStatistics() {
        if (this.level == null) {
            return null;
        }
        return "E: " + this.levelRenderState.entityRenderStates.size() + "/" + this.level.getEntityCount() + ", SD: " + this.level.getServerSimulationDistance();
    }

    private void cullTerrain(Camera $$0, Frustum $$1, boolean $$2) {
        Vec3 $$3 = $$0.position();
        if (this.minecraft.options.getEffectiveRenderDistance() != this.lastViewDistance) {
            allChanged();
        }
        ProfilerFiller $$4 = Profiler.get();
        $$4.push("repositionCamera");
        int $$5 = SectionPos.posToSectionCoord($$3.x());
        int $$6 = SectionPos.posToSectionCoord($$3.y());
        int $$7 = SectionPos.posToSectionCoord($$3.z());
        if (this.lastCameraSectionX != $$5 || this.lastCameraSectionY != $$6 || this.lastCameraSectionZ != $$7) {
            this.lastCameraSectionX = $$5;
            this.lastCameraSectionY = $$6;
            this.lastCameraSectionZ = $$7;
            this.viewArea.repositionCamera(SectionPos.of($$3));
            this.worldBorderRenderer.invalidate();
        }
        this.sectionRenderDispatcher.setCameraPosition($$3);
        double $$8 = Math.floor($$3.x / 8.0d);
        double $$9 = Math.floor($$3.y / 8.0d);
        double $$10 = Math.floor($$3.z / 8.0d);
        if ($$8 != this.prevCamX || $$9 != this.prevCamY || $$10 != this.prevCamZ) {
            this.sectionOcclusionGraph.invalidate();
        }
        this.prevCamX = $$8;
        this.prevCamY = $$9;
        this.prevCamZ = $$10;
        $$4.pop();
        if (this.capturedFrustum == null) {
            boolean $$11 = this.minecraft.smartCull;
            if ($$2 && this.level.getBlockState($$0.blockPosition()).isSolidRender()) {
                $$11 = false;
            }
            $$4.push("updateSOG");
            this.sectionOcclusionGraph.update($$11, $$0, $$1, this.visibleSections, this.level.getChunkSource().getLoadedEmptySections());
            $$4.pop();
            double $$12 = Math.floor($$0.xRot() / 2.0f);
            double $$13 = Math.floor($$0.yRot() / 2.0f);
            if (this.sectionOcclusionGraph.consumeFrustumUpdate() || $$12 != this.prevCamRotX || $$13 != this.prevCamRotY) {
                $$4.push("applyFrustum");
                applyFrustum(offsetFrustum($$1));
                $$4.pop();
                this.prevCamRotX = $$12;
                this.prevCamRotY = $$13;
            }
        }
    }

    public static Frustum offsetFrustum(Frustum $$0) {
        return new Frustum($$0).offsetToFullyIncludeCameraCube(8);
    }

    private void applyFrustum(Frustum $$0) {
        if (!Minecraft.getInstance().isSameThread()) {
            throw new IllegalStateException("applyFrustum called from wrong thread: " + Thread.currentThread().getName());
        }
        clearVisibleSections();
        this.sectionOcclusionGraph.addSectionsInFrustum($$0, this.visibleSections, this.nearbyVisibleSections);
    }

    public void addRecentlyCompiledSection(SectionRenderDispatcher.RenderSection $$0) {
        this.sectionOcclusionGraph.schedulePropagationFrom($$0);
    }

    private Frustum prepareCullFrustum(Matrix4f $$0, Matrix4f $$1, Vec3 $$2) {
        Frustum $$4;
        if (this.capturedFrustum != null && !this.captureFrustum) {
            $$4 = this.capturedFrustum;
        } else {
            $$4 = new Frustum($$0, $$1);
            $$4.prepare($$2.x(), $$2.y(), $$2.z());
        }
        if (this.captureFrustum) {
            this.capturedFrustum = $$4;
            this.captureFrustum = false;
        }
        return $$4;
    }

    public void renderLevel(GraphicsResourceAllocator $$0, DeltaTracker $$1, boolean $$2, Camera $$3, Matrix4f $$4, Matrix4f $$5, Matrix4f $$6, GpuBufferSlice $$7, Vector4f $$8, boolean $$9) {
        float $$10 = $$1.getGameTimeDeltaPartialTick(false);
        this.levelRenderState.gameTime = this.level.getGameTime();
        this.blockEntityRenderDispatcher.prepare($$3);
        this.entityRenderDispatcher.prepare($$3, this.minecraft.crosshairPickEntity);
        final ProfilerFiller $$11 = Profiler.get();
        $$11.push("populateLightUpdates");
        this.level.pollLightUpdates();
        $$11.popPush("runLightUpdates");
        this.level.getChunkSource().getLightEngine().runLightUpdates();
        $$11.popPush("prepareCullFrustum");
        Vec3 $$12 = $$3.position();
        Frustum $$13 = prepareCullFrustum($$4, $$6, $$12);
        $$11.popPush("cullTerrain");
        cullTerrain($$3, $$13, this.minecraft.player.isSpectator());
        $$11.popPush("compileSections");
        compileSections($$3);
        $$11.popPush("extract");
        $$11.push(StructureTemplate.ENTITIES_TAG);
        extractVisibleEntities($$3, $$13, $$1, this.levelRenderState);
        $$11.popPush("blockEntities");
        extractVisibleBlockEntities($$3, $$10, this.levelRenderState);
        $$11.popPush("blockOutline");
        extractBlockOutline($$3, this.levelRenderState);
        $$11.popPush("blockBreaking");
        extractBlockDestroyAnimation($$3, this.levelRenderState);
        $$11.popPush("weather");
        this.weatherEffectRenderer.extractRenderState(this.level, this.ticks, $$10, $$12, this.levelRenderState.weatherRenderState);
        $$11.popPush("sky");
        this.skyRenderer.extractRenderState(this.level, $$10, $$3, this.levelRenderState.skyRenderState);
        $$11.popPush("border");
        this.worldBorderRenderer.extract(this.level.getWorldBorder(), $$10, $$12, this.minecraft.options.getEffectiveRenderDistance() * 16, this.levelRenderState.worldBorderRenderState);
        $$11.pop();
        $$11.popPush("debug");
        this.debugRenderer.emitGizmos($$13, $$12.x, $$12.y, $$12.z, $$1.getGameTimeDeltaPartialTick(false));
        this.gameTestBlockHighlightRenderer.emitGizmos();
        $$11.popPush("setupFrameGraph");
        Matrix4fStack $$14 = RenderSystem.getModelViewStack();
        $$14.pushMatrix();
        $$14.mul($$4);
        FrameGraphBuilder $$15 = new FrameGraphBuilder();
        this.targets.main = $$15.importExternal("main", this.minecraft.getMainRenderTarget());
        int $$16 = this.minecraft.getMainRenderTarget().width;
        int $$17 = this.minecraft.getMainRenderTarget().height;
        RenderTargetDescriptor $$18 = new RenderTargetDescriptor($$16, $$17, true, 0);
        PostChain $$19 = getTransparencyChain();
        if ($$19 != null) {
            this.targets.translucent = $$15.createInternal("translucent", $$18);
            this.targets.itemEntity = $$15.createInternal("item_entity", $$18);
            this.targets.particles = $$15.createInternal("particles", $$18);
            this.targets.weather = $$15.createInternal("weather", $$18);
            this.targets.clouds = $$15.createInternal("clouds", $$18);
        }
        if (this.entityOutlineTarget != null) {
            this.targets.entityOutline = $$15.importExternal("entity_outline", this.entityOutlineTarget);
        }
        FramePass $$20 = $$15.addPass("clear");
        this.targets.main = $$20.readsAndWrites(this.targets.main);
        $$20.executes(() -> {
            RenderTarget $$110 = this.minecraft.getMainRenderTarget();
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures($$110.getColorTexture(), ARGB.colorFromFloat(0.0f, $$8.x, $$8.y, $$8.z), $$110.getDepthTexture(), 1.0d);
        });
        if ($$9) {
            addSkyPass($$15, $$3, $$7);
        }
        addMainPass($$15, $$13, $$4, $$7, $$2, this.levelRenderState, $$1, $$11);
        PostChain $$21 = this.minecraft.getShaderManager().getPostChain(ENTITY_OUTLINE_POST_CHAIN_ID, LevelTargetBundle.OUTLINE_TARGETS);
        if (this.levelRenderState.haveGlowingEntities && $$21 != null) {
            $$21.addToFrame($$15, $$16, $$17, this.targets);
        }
        this.minecraft.particleEngine.extract(this.particlesRenderState, new Frustum($$13).offset(-3.0f), $$3, $$10);
        addParticlesPass($$15, $$7);
        CloudStatus $$22 = this.minecraft.options.getCloudsType();
        if ($$22 != CloudStatus.OFF) {
            int $$23 = ((Integer) $$3.attributeProbe().getValue(EnvironmentAttributes.CLOUD_COLOR, $$10)).intValue();
            if (ARGB.alpha($$23) > 0) {
                float $$24 = ((Float) $$3.attributeProbe().getValue(EnvironmentAttributes.CLOUD_HEIGHT, $$10)).floatValue();
                addCloudsPass($$15, $$22, this.levelRenderState.cameraRenderState.pos, this.levelRenderState.gameTime, $$10, $$23, $$24);
            }
        }
        addWeatherPass($$15, $$7);
        if ($$19 != null) {
            $$19.addToFrame($$15, $$16, $$17, this.targets);
        }
        addLateDebugPass($$15, this.levelRenderState.cameraRenderState, $$7, $$4);
        $$11.popPush("executeFrameGraph");
        $$15.execute($$0, new FrameGraphBuilder.Inspector(this) { // from class: net.minecraft.client.renderer.LevelRenderer.1
            @Override // com.mojang.blaze3d.framegraph.FrameGraphBuilder.Inspector
            public void beforeExecutePass(String $$02) {
                $$11.push($$02);
            }

            @Override // com.mojang.blaze3d.framegraph.FrameGraphBuilder.Inspector
            public void afterExecutePass(String $$02) {
                $$11.pop();
            }
        });
        this.targets.clear();
        $$14.popMatrix();
        $$11.pop();
        this.levelRenderState.reset();
    }

    private void addMainPass(FrameGraphBuilder $$0, Frustum $$1, Matrix4f $$2, GpuBufferSlice $$3, boolean $$4, LevelRenderState $$5, DeltaTracker $$6, ProfilerFiller $$7) {
        FramePass $$8 = $$0.addPass("main");
        this.targets.main = $$8.readsAndWrites(this.targets.main);
        if (this.targets.translucent != null) {
            this.targets.translucent = $$8.readsAndWrites(this.targets.translucent);
        }
        if (this.targets.itemEntity != null) {
            this.targets.itemEntity = $$8.readsAndWrites(this.targets.itemEntity);
        }
        if (this.targets.weather != null) {
            this.targets.weather = $$8.readsAndWrites(this.targets.weather);
        }
        if ($$5.haveGlowingEntities && this.targets.entityOutline != null) {
            this.targets.entityOutline = $$8.readsAndWrites(this.targets.entityOutline);
        }
        ResourceHandle<RenderTarget> $$9 = this.targets.main;
        ResourceHandle<RenderTarget> $$10 = this.targets.translucent;
        ResourceHandle<RenderTarget> $$11 = this.targets.itemEntity;
        ResourceHandle<RenderTarget> $$12 = this.targets.entityOutline;
        $$8.executes(() -> {
            RenderSystem.setShaderFog($$3);
            Vec3 $$92 = $$5.cameraRenderState.pos;
            double $$102 = $$92.x();
            double $$112 = $$92.y();
            double $$122 = $$92.z();
            $$7.push("terrain");
            if (this.chunkLayerSampler == null) {
                int $$13 = this.minecraft.options.textureFiltering().get() == TextureFilteringMethod.ANISOTROPIC ? this.minecraft.options.maxAnisotropyValue() : 1;
                this.chunkLayerSampler = RenderSystem.getDevice().createSampler(AddressMode.CLAMP_TO_EDGE, AddressMode.CLAMP_TO_EDGE, FilterMode.LINEAR, FilterMode.LINEAR, $$13, OptionalDouble.empty());
            }
            ChunkSectionsToRender $$14 = prepareChunkRenders($$2, $$102, $$112, $$122);
            $$14.renderGroup(ChunkSectionLayerGroup.OPAQUE, this.chunkLayerSampler);
            this.minecraft.gameRenderer.getLighting().setupFor(Lighting.Entry.LEVEL);
            if ($$11 != null) {
                ((RenderTarget) $$11.get()).copyDepthFrom(this.minecraft.getMainRenderTarget());
            }
            if (shouldShowEntityOutlines() && $$12 != null) {
                RenderTarget $$15 = (RenderTarget) $$12.get();
                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures($$15.getColorTexture(), 0, $$15.getDepthTexture(), 1.0d);
            }
            PoseStack $$16 = new PoseStack();
            MultiBufferSource.BufferSource $$17 = this.renderBuffers.bufferSource();
            MultiBufferSource.BufferSource $$18 = this.renderBuffers.crumblingBufferSource();
            $$7.popPush("submitEntities");
            submitEntities($$16, $$5, this.submitNodeStorage);
            $$7.popPush("submitBlockEntities");
            submitBlockEntities($$16, $$5, this.submitNodeStorage);
            $$7.popPush("renderFeatures");
            this.featureRenderDispatcher.renderAllFeatures();
            $$17.endLastBatch();
            checkPoseStack($$16);
            $$17.endBatch(RenderTypes.solidMovingBlock());
            $$17.endBatch(RenderTypes.endPortal());
            $$17.endBatch(RenderTypes.endGateway());
            $$17.endBatch(Sheets.solidBlockSheet());
            $$17.endBatch(Sheets.cutoutBlockSheet());
            $$17.endBatch(Sheets.bedSheet());
            $$17.endBatch(Sheets.shulkerBoxSheet());
            $$17.endBatch(Sheets.signSheet());
            $$17.endBatch(Sheets.hangingSignSheet());
            $$17.endBatch(Sheets.chestSheet());
            this.renderBuffers.outlineBufferSource().endOutlineBatch();
            if ($$4) {
                renderBlockOutline($$17, $$16, false, $$5);
            }
            $$7.pop();
            finalizeGizmoCollection();
            this.finalizedGizmos.standardPrimitives().render($$16, $$17, $$5.cameraRenderState, $$2);
            $$17.endLastBatch();
            checkPoseStack($$16);
            $$17.endBatch(Sheets.translucentItemSheet());
            $$17.endBatch(Sheets.bannerSheet());
            $$17.endBatch(Sheets.shieldSheet());
            $$17.endBatch(RenderTypes.armorEntityGlint());
            $$17.endBatch(RenderTypes.glint());
            $$17.endBatch(RenderTypes.glintTranslucent());
            $$17.endBatch(RenderTypes.entityGlint());
            $$7.push("destroyProgress");
            renderBlockDestroyAnimation($$16, $$18, $$5);
            $$18.endBatch();
            $$7.pop();
            checkPoseStack($$16);
            $$17.endBatch(RenderTypes.waterMask());
            $$17.endBatch();
            if ($$10 != null) {
                ((RenderTarget) $$10.get()).copyDepthFrom((RenderTarget) $$9.get());
            }
            $$7.push("translucent");
            $$14.renderGroup(ChunkSectionLayerGroup.TRANSLUCENT, this.chunkLayerSampler);
            $$7.popPush("string");
            $$14.renderGroup(ChunkSectionLayerGroup.TRIPWIRE, this.chunkLayerSampler);
            if ($$4) {
                renderBlockOutline($$17, $$16, true, $$5);
            }
            $$17.endBatch();
            $$7.pop();
        });
    }

    private void addParticlesPass(FrameGraphBuilder $$0, GpuBufferSlice $$1) {
        FramePass $$2 = $$0.addPass("particles");
        if (this.targets.particles != null) {
            this.targets.particles = $$2.readsAndWrites(this.targets.particles);
            $$2.reads(this.targets.main);
        } else {
            this.targets.main = $$2.readsAndWrites(this.targets.main);
        }
        ResourceHandle<RenderTarget> $$3 = this.targets.main;
        ResourceHandle<RenderTarget> $$4 = this.targets.particles;
        $$2.executes(() -> {
            RenderSystem.setShaderFog($$1);
            if ($$4 != null) {
                ((RenderTarget) $$4.get()).copyDepthFrom((RenderTarget) $$3.get());
            }
            this.particlesRenderState.submit(this.submitNodeStorage, this.levelRenderState.cameraRenderState);
            this.featureRenderDispatcher.renderAllFeatures();
            this.particlesRenderState.reset();
        });
    }

    private void addCloudsPass(FrameGraphBuilder $$0, CloudStatus $$1, Vec3 $$2, long $$3, float $$4, int $$5, float $$6) {
        FramePass $$7 = $$0.addPass("clouds");
        if (this.targets.clouds != null) {
            this.targets.clouds = $$7.readsAndWrites(this.targets.clouds);
        } else {
            this.targets.main = $$7.readsAndWrites(this.targets.main);
        }
        $$7.executes(() -> {
            this.cloudRenderer.render($$5, $$1, $$6, $$2, $$3, $$4);
        });
    }

    private void addWeatherPass(FrameGraphBuilder $$0, GpuBufferSlice $$1) {
        int $$2 = this.minecraft.options.getEffectiveRenderDistance() * 16;
        float $$3 = this.minecraft.gameRenderer.getDepthFar();
        FramePass $$4 = $$0.addPass("weather");
        if (this.targets.weather != null) {
            this.targets.weather = $$4.readsAndWrites(this.targets.weather);
        } else {
            this.targets.main = $$4.readsAndWrites(this.targets.main);
        }
        $$4.executes(() -> {
            RenderSystem.setShaderFog($$1);
            MultiBufferSource.BufferSource $$32 = this.renderBuffers.bufferSource();
            CameraRenderState $$42 = this.levelRenderState.cameraRenderState;
            this.weatherEffectRenderer.render($$32, $$42.pos, this.levelRenderState.weatherRenderState);
            this.worldBorderRenderer.render(this.levelRenderState.worldBorderRenderState, $$42.pos, $$2, $$3);
            $$32.endBatch();
        });
    }

    private void addLateDebugPass(FrameGraphBuilder $$0, CameraRenderState $$1, GpuBufferSlice $$2, Matrix4f $$3) {
        FramePass $$4 = $$0.addPass("late_debug");
        this.targets.main = $$4.readsAndWrites(this.targets.main);
        if (this.targets.itemEntity != null) {
            this.targets.itemEntity = $$4.readsAndWrites(this.targets.itemEntity);
        }
        ResourceHandle<RenderTarget> $$5 = this.targets.main;
        $$4.executes(() -> {
            RenderSystem.setShaderFog($$2);
            PoseStack $$42 = new PoseStack();
            MultiBufferSource.BufferSource $$52 = this.renderBuffers.bufferSource();
            RenderSystem.outputColorTextureOverride = ((RenderTarget) $$5.get()).getColorTextureView();
            RenderSystem.outputDepthTextureOverride = ((RenderTarget) $$5.get()).getDepthTextureView();
            if (!this.finalizedGizmos.alwaysOnTopPrimitives().isEmpty()) {
                RenderTarget $$6 = Minecraft.getInstance().getMainRenderTarget();
                RenderSystem.getDevice().createCommandEncoder().clearDepthTexture($$6.getDepthTexture(), 1.0d);
                this.finalizedGizmos.alwaysOnTopPrimitives().render($$42, $$52, $$1, $$3);
                $$52.endLastBatch();
            }
            RenderSystem.outputColorTextureOverride = null;
            RenderSystem.outputDepthTextureOverride = null;
            checkPoseStack($$42);
        });
    }

    private void extractVisibleEntities(Camera $$0, Frustum $$1, DeltaTracker $$2, LevelRenderState $$3) {
        Vec3 $$4 = $$0.position();
        double $$5 = $$4.x();
        double $$6 = $$4.y();
        double $$7 = $$4.z();
        TickRateManager $$8 = this.minecraft.level.tickRateManager();
        boolean $$9 = shouldShowEntityOutlines();
        Entity.setViewScale(Mth.clamp(((double) this.minecraft.options.getEffectiveRenderDistance()) / 8.0d, 1.0d, 2.5d) * this.minecraft.options.entityDistanceScaling().get().doubleValue());
        for (Entity $$10 : this.level.entitiesForRendering()) {
            if (this.entityRenderDispatcher.shouldRender($$10, $$1, $$5, $$6, $$7) || $$10.hasIndirectPassenger(this.minecraft.player)) {
                BlockPos $$11 = $$10.blockPosition();
                if (this.level.isOutsideBuildHeight($$11.getY()) || isSectionCompiledAndVisible($$11)) {
                    if ($$10 != $$0.entity() || $$0.isDetached() || (($$0.entity() instanceof LivingEntity) && ((LivingEntity) $$0.entity()).isSleeping())) {
                        if (!($$10 instanceof LocalPlayer) || $$0.entity() == $$10) {
                            if ($$10.tickCount == 0) {
                                $$10.xOld = $$10.getX();
                                $$10.yOld = $$10.getY();
                                $$10.zOld = $$10.getZ();
                            }
                            float $$12 = $$2.getGameTimeDeltaPartialTick(!$$8.isEntityFrozen($$10));
                            EntityRenderState $$13 = extractEntity($$10, $$12);
                            $$3.entityRenderStates.add($$13);
                            if ($$13.appearsGlowing() && $$9) {
                                $$3.haveGlowingEntities = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void submitEntities(PoseStack $$0, LevelRenderState $$1, SubmitNodeCollector $$2) {
        Vec3 $$3 = $$1.cameraRenderState.pos;
        double $$4 = $$3.x();
        double $$5 = $$3.y();
        double $$6 = $$3.z();
        for (EntityRenderState $$7 : $$1.entityRenderStates) {
            if (!$$1.haveGlowingEntities) {
                $$7.outlineColor = 0;
            }
            this.entityRenderDispatcher.submit($$7, $$1.cameraRenderState, $$7.x - $$4, $$7.y - $$5, $$7.z - $$6, $$0, $$2);
        }
    }

    private void extractVisibleBlockEntities(Camera $$0, float $$1, LevelRenderState $$2) {
        ModelFeatureRenderer.CrumblingOverlay $$14;
        Vec3 $$3 = $$0.position();
        double $$4 = $$3.x();
        double $$5 = $$3.y();
        double $$6 = $$3.z();
        PoseStack $$7 = new PoseStack();
        ObjectListIterator it = this.visibleSections.iterator();
        while (it.hasNext()) {
            SectionRenderDispatcher.RenderSection $$8 = (SectionRenderDispatcher.RenderSection) it.next();
            List<BlockEntity> $$9 = $$8.getSectionMesh().getRenderableBlockEntities();
            if (!$$9.isEmpty() && $$8.getVisibility(Util.getMillis()) >= 0.3f) {
                for (BlockEntity $$10 : $$9) {
                    BlockPos $$11 = $$10.getBlockPos();
                    SortedSet<BlockDestructionProgress> $$12 = (SortedSet) this.destructionProgress.get($$11.asLong());
                    if ($$12 == null || $$12.isEmpty()) {
                        $$14 = null;
                    } else {
                        $$7.pushPose();
                        $$7.translate(((double) $$11.getX()) - $$4, ((double) $$11.getY()) - $$5, ((double) $$11.getZ()) - $$6);
                        $$14 = new ModelFeatureRenderer.CrumblingOverlay($$12.last().getProgress(), $$7.last());
                        $$7.popPose();
                    }
                    BlockEntityRenderState $$15 = this.blockEntityRenderDispatcher.tryExtractRenderState($$10, $$1, $$14);
                    if ($$15 != null) {
                        $$2.blockEntityRenderStates.add($$15);
                    }
                }
            }
        }
        Iterator<BlockEntity> $$16 = this.level.getGloballyRenderedBlockEntities().iterator();
        while ($$16.hasNext()) {
            BlockEntity $$17 = $$16.next();
            if ($$17.isRemoved()) {
                $$16.remove();
            } else {
                BlockEntityRenderState $$18 = this.blockEntityRenderDispatcher.tryExtractRenderState($$17, $$1, null);
                if ($$18 != null) {
                    $$2.blockEntityRenderStates.add($$18);
                }
            }
        }
    }

    private void submitBlockEntities(PoseStack $$0, LevelRenderState $$1, SubmitNodeStorage $$2) {
        Vec3 $$3 = $$1.cameraRenderState.pos;
        double $$4 = $$3.x();
        double $$5 = $$3.y();
        double $$6 = $$3.z();
        for (BlockEntityRenderState $$7 : $$1.blockEntityRenderStates) {
            BlockPos $$8 = $$7.blockPos;
            $$0.pushPose();
            $$0.translate(((double) $$8.getX()) - $$4, ((double) $$8.getY()) - $$5, ((double) $$8.getZ()) - $$6);
            this.blockEntityRenderDispatcher.submit($$7, $$0, $$2, $$1.cameraRenderState);
            $$0.popPose();
        }
    }

    private void extractBlockDestroyAnimation(Camera $$0, LevelRenderState $$1) {
        SortedSet<BlockDestructionProgress> $$8;
        Vec3 $$2 = $$0.position();
        double $$3 = $$2.x();
        double $$4 = $$2.y();
        double $$5 = $$2.z();
        $$1.blockBreakingRenderStates.clear();
        ObjectIterator it = this.destructionProgress.long2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Long2ObjectMap.Entry<SortedSet<BlockDestructionProgress>> $$6 = (Long2ObjectMap.Entry) it.next();
            BlockPos $$7 = BlockPos.of($$6.getLongKey());
            if ($$7.distToCenterSqr($$3, $$4, $$5) <= 1024.0d && ($$8 = (SortedSet) $$6.getValue()) != null && !$$8.isEmpty()) {
                int $$9 = $$8.last().getProgress();
                $$1.blockBreakingRenderStates.add(new BlockBreakingRenderState(this.level, $$7, $$9));
            }
        }
    }

    private void renderBlockDestroyAnimation(PoseStack $$0, MultiBufferSource.BufferSource $$1, LevelRenderState $$2) {
        Vec3 $$3 = $$2.cameraRenderState.pos;
        double $$4 = $$3.x();
        double $$5 = $$3.y();
        double $$6 = $$3.z();
        for (BlockBreakingRenderState $$7 : $$2.blockBreakingRenderStates) {
            $$0.pushPose();
            BlockPos $$8 = $$7.blockPos;
            $$0.translate(((double) $$8.getX()) - $$4, ((double) $$8.getY()) - $$5, ((double) $$8.getZ()) - $$6);
            PoseStack.Pose $$9 = $$0.last();
            VertexConsumer $$10 = new SheetedDecalTextureGenerator($$1.getBuffer(ModelBakery.DESTROY_TYPES.get($$7.progress)), $$9, 1.0f);
            this.minecraft.getBlockRenderer().renderBreakingTexture($$7.blockState, $$8, $$7, $$0, $$10);
            $$0.popPose();
        }
    }

    private void extractBlockOutline(Camera $$0, LevelRenderState $$1) {
        $$1.blockOutlineRenderState = null;
        HitResult hitResult = this.minecraft.hitResult;
        if (!(hitResult instanceof BlockHitResult)) {
            return;
        }
        BlockHitResult $$3 = (BlockHitResult) hitResult;
        if ($$3.getType() == HitResult.Type.MISS) {
            return;
        }
        BlockPos $$4 = $$3.getBlockPos();
        BlockState $$5 = this.level.getBlockState($$4);
        if (!$$5.isAir() && this.level.getWorldBorder().isWithinBounds($$4)) {
            boolean $$6 = ItemBlockRenderTypes.getChunkRenderType($$5).sortOnUpload();
            boolean $$7 = this.minecraft.options.highContrastBlockOutline().get().booleanValue();
            CollisionContext $$8 = CollisionContext.of($$0.entity());
            VoxelShape $$9 = $$5.getShape(this.level, $$4, $$8);
            if (SharedConstants.DEBUG_SHAPES) {
                VoxelShape $$10 = $$5.getCollisionShape(this.level, $$4, $$8);
                VoxelShape $$11 = $$5.getOcclusionShape();
                VoxelShape $$12 = $$5.getInteractionShape(this.level, $$4);
                $$1.blockOutlineRenderState = new BlockOutlineRenderState($$4, $$6, $$7, $$9, $$10, $$11, $$12);
                return;
            }
            $$1.blockOutlineRenderState = new BlockOutlineRenderState($$4, $$6, $$7, $$9);
        }
    }

    private void renderBlockOutline(MultiBufferSource.BufferSource $$0, PoseStack $$1, boolean $$2, LevelRenderState $$3) {
        BlockOutlineRenderState $$4 = $$3.blockOutlineRenderState;
        if ($$4 == null || $$4.isTranslucent() != $$2) {
            return;
        }
        Vec3 $$5 = $$3.cameraRenderState.pos;
        if ($$4.highContrast()) {
            VertexConsumer $$6 = $$0.getBuffer(RenderTypes.secondaryBlockOutline());
            renderHitOutline($$1, $$6, $$5.x, $$5.y, $$5.z, $$4, -16777216, 7.0f);
        }
        VertexConsumer $$7 = $$0.getBuffer(RenderTypes.lines());
        int $$8 = $$4.highContrast() ? CommonColors.HIGH_CONTRAST_DIAMOND : ARGB.black(ClientboundGameEventPacket.DEMO_PARAM_HINT_2);
        renderHitOutline($$1, $$7, $$5.x, $$5.y, $$5.z, $$4, $$8, this.minecraft.getWindow().getAppropriateLineWidth());
        $$0.endLastBatch();
    }

    private void checkPoseStack(PoseStack $$0) {
        if (!$$0.isEmpty()) {
            throw new IllegalStateException("Pose stack not empty");
        }
    }

    private EntityRenderState extractEntity(Entity $$0, float $$1) {
        return this.entityRenderDispatcher.extractEntity($$0, $$1);
    }

    private void scheduleTranslucentSectionResort(Vec3 $$0) {
        if (this.visibleSections.isEmpty()) {
            return;
        }
        BlockPos $$1 = BlockPos.containing($$0);
        boolean $$2 = !$$1.equals(this.lastTranslucentSortBlockPos);
        TranslucencyPointOfView $$3 = new TranslucencyPointOfView();
        ObjectListIterator it = this.nearbyVisibleSections.iterator();
        while (it.hasNext()) {
            SectionRenderDispatcher.RenderSection $$4 = (SectionRenderDispatcher.RenderSection) it.next();
            scheduleResort($$4, $$3, $$0, $$2, true);
        }
        this.translucencyResortIterationIndex %= this.visibleSections.size();
        int $$5 = Math.max(this.visibleSections.size() / 8, 15);
        while (true) {
            int i = $$5;
            $$5--;
            if (i > 0) {
                int i2 = this.translucencyResortIterationIndex;
                this.translucencyResortIterationIndex = i2 + 1;
                int $$6 = i2 % this.visibleSections.size();
                scheduleResort((SectionRenderDispatcher.RenderSection) this.visibleSections.get($$6), $$3, $$0, $$2, false);
            } else {
                this.lastTranslucentSortBlockPos = $$1;
                return;
            }
        }
    }

    private void scheduleResort(SectionRenderDispatcher.RenderSection $$0, TranslucencyPointOfView $$1, Vec3 $$2, boolean $$3, boolean $$4) {
        $$1.set($$2, $$0.getSectionNode());
        boolean $$5 = $$0.getSectionMesh().isDifferentPointOfView($$1);
        boolean $$6 = $$3 && ($$1.isAxisAligned() || $$4);
        if (($$6 || $$5) && !$$0.transparencyResortingScheduled() && $$0.hasTranslucentGeometry()) {
            $$0.resortTransparency(this.sectionRenderDispatcher);
        }
    }

    private ChunkSectionsToRender prepareChunkRenders(Matrix4fc $$0, double $$1, double $$2, double $$3) {
        GpuBuffer $$21;
        VertexFormat.IndexType $$22;
        ObjectListIterator<SectionRenderDispatcher.RenderSection> $$4 = this.visibleSections.listIterator(0);
        EnumMap<ChunkSectionLayer, List<RenderPass.Draw<GpuBufferSlice[]>>> $$5 = new EnumMap<>(ChunkSectionLayer.class);
        int $$6 = 0;
        for (ChunkSectionLayer $$7 : ChunkSectionLayer.values()) {
            $$5.put($$7, new ArrayList<>());
        }
        List<DynamicUniforms.ChunkSectionInfo> $$8 = new ArrayList<>();
        GpuTextureView $$9 = this.minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).getTextureView();
        int $$10 = $$9.getWidth(0);
        int $$11 = $$9.getHeight(0);
        while ($$4.hasNext()) {
            SectionRenderDispatcher.RenderSection $$12 = (SectionRenderDispatcher.RenderSection) $$4.next();
            SectionMesh $$13 = $$12.getSectionMesh();
            BlockPos $$14 = $$12.getRenderOrigin();
            long $$15 = Util.getMillis();
            int $$16 = -1;
            for (ChunkSectionLayer $$17 : ChunkSectionLayer.values()) {
                SectionBuffers $$18 = $$13.getBuffers($$17);
                if ($$18 != null) {
                    if ($$16 == -1) {
                        $$16 = $$8.size();
                        $$8.add(new DynamicUniforms.ChunkSectionInfo(new Matrix4f($$0), $$14.getX(), $$14.getY(), $$14.getZ(), $$12.getVisibility($$15), $$10, $$11));
                    }
                    if ($$18.getIndexBuffer() == null) {
                        if ($$18.getIndexCount() > $$6) {
                            $$6 = $$18.getIndexCount();
                        }
                        $$21 = null;
                        $$22 = null;
                    } else {
                        $$21 = $$18.getIndexBuffer();
                        $$22 = $$18.getIndexType();
                    }
                    int $$23 = $$16;
                    $$5.get($$17).add(new RenderPass.Draw<>(0, $$18.getVertexBuffer(), $$21, $$22, 0, $$18.getIndexCount(), ($$19, $$24) -> {
                        $$24.upload("ChunkSection", $$19[$$23]);
                    }));
                }
            }
        }
        GpuBufferSlice[] $$242 = RenderSystem.getDynamicUniforms().writeChunkSections((DynamicUniforms.ChunkSectionInfo[]) $$8.toArray(new DynamicUniforms.ChunkSectionInfo[0]));
        return new ChunkSectionsToRender($$9, $$5, $$6, $$242);
    }

    public void endFrame() {
        this.cloudRenderer.endFrame();
    }

    public void captureFrustum() {
        this.captureFrustum = true;
    }

    public void killFrustum() {
        this.capturedFrustum = null;
    }

    public void tick(Camera $$0) {
        if (this.level.tickRateManager().runsNormally()) {
            this.ticks++;
        }
        this.weatherEffectRenderer.tickRainParticles(this.level, $$0, this.ticks, this.minecraft.options.particles().get(), this.minecraft.options.weatherRadius().get().intValue());
        removeBlockBreakingProgress();
    }

    private void removeBlockBreakingProgress() {
        if (this.ticks % 20 != 0) {
            return;
        }
        ObjectIterator it = this.destroyingBlocks.values().iterator();
        while (it.hasNext()) {
            BlockDestructionProgress $$1 = (BlockDestructionProgress) it.next();
            int $$2 = $$1.getUpdatedRenderTick();
            if (this.ticks - $$2 > 400) {
                it.remove();
                removeProgress($$1);
            }
        }
    }

    private void removeProgress(BlockDestructionProgress $$0) {
        long $$1 = $$0.getPos().asLong();
        Set<BlockDestructionProgress> $$2 = (Set) this.destructionProgress.get($$1);
        $$2.remove($$0);
        if ($$2.isEmpty()) {
            this.destructionProgress.remove($$1);
        }
    }

    private void addSkyPass(FrameGraphBuilder $$0, Camera $$1, GpuBufferSlice $$2) {
        SkyRenderer $$5;
        FogType $$3 = $$1.getFluidInCamera();
        if ($$3 == FogType.POWDER_SNOW || $$3 == FogType.LAVA || doesMobEffectBlockSky($$1)) {
            return;
        }
        SkyRenderState $$4 = this.levelRenderState.skyRenderState;
        if ($$4.skybox == DimensionType.Skybox.NONE || ($$5 = this.skyRenderer) == null) {
            return;
        }
        FramePass $$6 = $$0.addPass("sky");
        this.targets.main = $$6.readsAndWrites(this.targets.main);
        $$6.executes(() -> {
            RenderSystem.setShaderFog($$2);
            if ($$4.skybox == DimensionType.Skybox.END) {
                $$5.renderEndSky();
                if ($$4.endFlashIntensity > 1.0E-5f) {
                    PoseStack $$32 = new PoseStack();
                    $$5.renderEndFlash($$32, $$4.endFlashIntensity, $$4.endFlashXAngle, $$4.endFlashYAngle);
                    return;
                }
                return;
            }
            PoseStack $$42 = new PoseStack();
            $$5.renderSkyDisc($$4.skyColor);
            $$5.renderSunriseAndSunset($$42, $$4.sunAngle, $$4.sunriseAndSunsetColor);
            $$5.renderSunMoonAndStars($$42, $$4.sunAngle, $$4.moonAngle, $$4.starAngle, $$4.moonPhase, $$4.rainBrightness, $$4.starBrightness);
            if ($$4.shouldRenderDarkDisc) {
                $$5.renderDarkDisc();
            }
        });
    }

    private boolean doesMobEffectBlockSky(Camera $$0) {
        Entity entity = $$0.entity();
        if (!(entity instanceof LivingEntity)) {
            return false;
        }
        LivingEntity $$1 = (LivingEntity) entity;
        return $$1.hasEffect(MobEffects.BLINDNESS) || $$1.hasEffect(MobEffects.DARKNESS);
    }

    private void compileSections(Camera $$0) {
        ProfilerFiller $$1 = Profiler.get();
        $$1.push("populateSectionsToCompile");
        RenderRegionCache $$2 = new RenderRegionCache();
        BlockPos $$3 = $$0.blockPosition();
        List<SectionRenderDispatcher.RenderSection> $$4 = Lists.newArrayList();
        long $$5 = Mth.floor(this.minecraft.options.chunkSectionFadeInTime().get().doubleValue() * 1000.0d);
        ObjectListIterator it = this.visibleSections.iterator();
        while (it.hasNext()) {
            SectionRenderDispatcher.RenderSection $$6 = (SectionRenderDispatcher.RenderSection) it.next();
            if ($$6.isDirty() && ($$6.getSectionMesh() != CompiledSectionMesh.UNCOMPILED || $$6.hasAllNeighbors())) {
                BlockPos $$7 = SectionPos.of($$6.getSectionNode()).center();
                double $$8 = $$7.distSqr($$3);
                boolean $$9 = $$8 < 768.0d;
                boolean $$10 = false;
                if (this.minecraft.options.prioritizeChunkUpdates().get() == PrioritizeChunkUpdates.NEARBY) {
                    $$10 = $$9 || $$6.isDirtyFromPlayer();
                } else if (this.minecraft.options.prioritizeChunkUpdates().get() == PrioritizeChunkUpdates.PLAYER_AFFECTED) {
                    $$10 = $$6.isDirtyFromPlayer();
                }
                if ($$9 || $$6.wasPreviouslyEmpty()) {
                    $$6.setFadeDuration(0L);
                } else {
                    $$6.setFadeDuration($$5);
                }
                $$6.setWasPreviouslyEmpty(false);
                if ($$10) {
                    $$1.push("compileSectionSynchronously");
                    this.sectionRenderDispatcher.rebuildSectionSync($$6, $$2);
                    $$6.setNotDirty();
                    $$1.pop();
                } else {
                    $$4.add($$6);
                }
            }
        }
        $$1.popPush("uploadSectionMeshes");
        this.sectionRenderDispatcher.uploadAllPendingUploads();
        $$1.popPush("scheduleAsyncCompile");
        for (SectionRenderDispatcher.RenderSection $$11 : $$4) {
            $$11.rebuildSectionAsync($$2);
            $$11.setNotDirty();
        }
        $$1.popPush("scheduleTranslucentResort");
        scheduleTranslucentSectionResort($$0.position());
        $$1.pop();
    }

    private void renderHitOutline(PoseStack $$0, VertexConsumer $$1, double $$2, double $$3, double $$4, BlockOutlineRenderState $$5, int $$6, float $$7) {
        BlockPos $$8 = $$5.pos();
        if (SharedConstants.DEBUG_SHAPES) {
            ShapeRenderer.renderShape($$0, $$1, $$5.shape(), ((double) $$8.getX()) - $$2, ((double) $$8.getY()) - $$3, ((double) $$8.getZ()) - $$4, ARGB.colorFromFloat(1.0f, 1.0f, 1.0f, 1.0f), $$7);
            if ($$5.collisionShape() != null) {
                ShapeRenderer.renderShape($$0, $$1, $$5.collisionShape(), ((double) $$8.getX()) - $$2, ((double) $$8.getY()) - $$3, ((double) $$8.getZ()) - $$4, ARGB.colorFromFloat(0.4f, 0.0f, 0.0f, 0.0f), $$7);
            }
            if ($$5.occlusionShape() != null) {
                ShapeRenderer.renderShape($$0, $$1, $$5.occlusionShape(), ((double) $$8.getX()) - $$2, ((double) $$8.getY()) - $$3, ((double) $$8.getZ()) - $$4, ARGB.colorFromFloat(0.4f, 0.0f, 1.0f, 0.0f), $$7);
            }
            if ($$5.interactionShape() != null) {
                ShapeRenderer.renderShape($$0, $$1, $$5.interactionShape(), ((double) $$8.getX()) - $$2, ((double) $$8.getY()) - $$3, ((double) $$8.getZ()) - $$4, ARGB.colorFromFloat(0.4f, 0.0f, 0.0f, 1.0f), $$7);
                return;
            }
            return;
        }
        ShapeRenderer.renderShape($$0, $$1, $$5.shape(), ((double) $$8.getX()) - $$2, ((double) $$8.getY()) - $$3, ((double) $$8.getZ()) - $$4, $$6, $$7);
    }

    public void blockChanged(BlockGetter $$0, BlockPos $$1, BlockState $$2, BlockState $$3, @Block.UpdateFlags int $$4) {
        setBlockDirty($$1, ($$4 & 8) != 0);
    }

    private void setBlockDirty(BlockPos $$0, boolean $$1) {
        for (int $$2 = $$0.getZ() - 1; $$2 <= $$0.getZ() + 1; $$2++) {
            for (int $$3 = $$0.getX() - 1; $$3 <= $$0.getX() + 1; $$3++) {
                for (int $$4 = $$0.getY() - 1; $$4 <= $$0.getY() + 1; $$4++) {
                    setSectionDirty(SectionPos.blockToSectionCoord($$3), SectionPos.blockToSectionCoord($$4), SectionPos.blockToSectionCoord($$2), $$1);
                }
            }
        }
    }

    public void setBlocksDirty(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        for (int $$6 = $$2 - 1; $$6 <= $$5 + 1; $$6++) {
            for (int $$7 = $$0 - 1; $$7 <= $$3 + 1; $$7++) {
                for (int $$8 = $$1 - 1; $$8 <= $$4 + 1; $$8++) {
                    setSectionDirty(SectionPos.blockToSectionCoord($$7), SectionPos.blockToSectionCoord($$8), SectionPos.blockToSectionCoord($$6));
                }
            }
        }
    }

    public void setBlockDirty(BlockPos $$0, BlockState $$1, BlockState $$2) {
        if (this.minecraft.getModelManager().requiresRender($$1, $$2)) {
            setBlocksDirty($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getX(), $$0.getY(), $$0.getZ());
        }
    }

    public void setSectionDirtyWithNeighbors(int $$0, int $$1, int $$2) {
        setSectionRangeDirty($$0 - 1, $$1 - 1, $$2 - 1, $$0 + 1, $$1 + 1, $$2 + 1);
    }

    public void setSectionRangeDirty(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        for (int $$6 = $$2; $$6 <= $$5; $$6++) {
            for (int $$7 = $$0; $$7 <= $$3; $$7++) {
                for (int $$8 = $$1; $$8 <= $$4; $$8++) {
                    setSectionDirty($$7, $$8, $$6);
                }
            }
        }
    }

    public void setSectionDirty(int $$0, int $$1, int $$2) {
        setSectionDirty($$0, $$1, $$2, false);
    }

    private void setSectionDirty(int $$0, int $$1, int $$2, boolean $$3) {
        this.viewArea.setDirty($$0, $$1, $$2, $$3);
    }

    public void onSectionBecomingNonEmpty(long $$0) {
        SectionRenderDispatcher.RenderSection $$1 = this.viewArea.getRenderSection($$0);
        if ($$1 != null) {
            this.sectionOcclusionGraph.schedulePropagationFrom($$1);
            $$1.setWasPreviouslyEmpty(true);
        }
    }

    public void destroyBlockProgress(int $$0, BlockPos $$1, int $$2) {
        if ($$2 < 0 || $$2 >= 10) {
            BlockDestructionProgress $$3 = (BlockDestructionProgress) this.destroyingBlocks.remove($$0);
            if ($$3 != null) {
                removeProgress($$3);
                return;
            }
            return;
        }
        BlockDestructionProgress $$4 = (BlockDestructionProgress) this.destroyingBlocks.get($$0);
        if ($$4 != null) {
            removeProgress($$4);
        }
        if ($$4 == null || $$4.getPos().getX() != $$1.getX() || $$4.getPos().getY() != $$1.getY() || $$4.getPos().getZ() != $$1.getZ()) {
            $$4 = new BlockDestructionProgress($$0, $$1);
            this.destroyingBlocks.put($$0, $$4);
        }
        $$4.setProgress($$2);
        $$4.updateTick(this.ticks);
        ((SortedSet) this.destructionProgress.computeIfAbsent($$4.getPos().asLong(), $$02 -> {
            return Sets.newTreeSet();
        })).add($$4);
    }

    public boolean hasRenderedAllSections() {
        return this.sectionRenderDispatcher.isQueueEmpty();
    }

    public void onChunkReadyToRender(ChunkPos $$0) {
        this.sectionOcclusionGraph.onChunkReadyToRender($$0);
    }

    public void needsUpdate() {
        this.sectionOcclusionGraph.invalidate();
        this.cloudRenderer.markForRebuild();
    }

    public static int getLightColor(BlockAndTintGetter $$0, BlockPos $$1) {
        return getLightColor(BrightnessGetter.DEFAULT, $$0, $$0.getBlockState($$1), $$1);
    }

    public static int getLightColor(BrightnessGetter $$0, BlockAndTintGetter $$1, BlockState $$2, BlockPos $$3) {
        if ($$2.emissiveRendering($$1, $$3)) {
            return LightTexture.FULL_BRIGHT;
        }
        int $$4 = $$0.packedBrightness($$1, $$3);
        int $$5 = LightTexture.block($$4);
        int $$6 = $$2.getLightEmission();
        if ($$5 < $$6) {
            int $$7 = LightTexture.sky($$4);
            return LightTexture.pack($$6, $$7);
        }
        return $$4;
    }

    public boolean isSectionCompiledAndVisible(BlockPos $$0) {
        SectionRenderDispatcher.RenderSection $$1 = this.viewArea.getRenderSectionAt($$0);
        return ($$1 == null || $$1.sectionMesh.get() == CompiledSectionMesh.UNCOMPILED || $$1.getVisibility(Util.getMillis()) < 0.3f) ? false : true;
    }

    public RenderTarget entityOutlineTarget() {
        if (this.targets.entityOutline != null) {
            return this.targets.entityOutline.get();
        }
        return null;
    }

    public RenderTarget getTranslucentTarget() {
        if (this.targets.translucent != null) {
            return this.targets.translucent.get();
        }
        return null;
    }

    public RenderTarget getItemEntityTarget() {
        if (this.targets.itemEntity != null) {
            return this.targets.itemEntity.get();
        }
        return null;
    }

    public RenderTarget getParticlesTarget() {
        if (this.targets.particles != null) {
            return this.targets.particles.get();
        }
        return null;
    }

    public RenderTarget getWeatherTarget() {
        if (this.targets.weather != null) {
            return this.targets.weather.get();
        }
        return null;
    }

    public RenderTarget getCloudsTarget() {
        if (this.targets.clouds != null) {
            return this.targets.clouds.get();
        }
        return null;
    }

    @VisibleForDebug
    public ObjectArrayList<SectionRenderDispatcher.RenderSection> getVisibleSections() {
        return this.visibleSections;
    }

    @VisibleForDebug
    public SectionOcclusionGraph getSectionOcclusionGraph() {
        return this.sectionOcclusionGraph;
    }

    public Frustum getCapturedFrustum() {
        return this.capturedFrustum;
    }

    public CloudRenderer getCloudRenderer() {
        return this.cloudRenderer;
    }

    public Gizmos.TemporaryCollection collectPerFrameGizmos() {
        return Gizmos.withCollector(this.collectedGizmos);
    }

    private void finalizeGizmoCollection() {
        DrawableGizmoPrimitives $$0 = new DrawableGizmoPrimitives();
        DrawableGizmoPrimitives $$1 = new DrawableGizmoPrimitives();
        this.collectedGizmos.addTemporaryGizmos(this.minecraft.getPerTickGizmos());
        IntegratedServer $$2 = this.minecraft.getSingleplayerServer();
        if ($$2 != null) {
            this.collectedGizmos.addTemporaryGizmos($$2.getPerTickGizmos());
        }
        long $$3 = Util.getMillis();
        for (SimpleGizmoCollector.GizmoInstance $$4 : this.collectedGizmos.drainGizmos()) {
            $$4.gizmo().emit($$4.isAlwaysOnTop() ? $$1 : $$0, $$4.getAlphaMultiplier($$3));
        }
        this.finalizedGizmos = new FinalizedGizmos($$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/LevelRenderer$FinalizedGizmos.class */
    static final class FinalizedGizmos extends Record {
        private final DrawableGizmoPrimitives standardPrimitives;
        private final DrawableGizmoPrimitives alwaysOnTopPrimitives;

        FinalizedGizmos(DrawableGizmoPrimitives $$0, DrawableGizmoPrimitives $$1) {
            this.standardPrimitives = $$0;
            this.alwaysOnTopPrimitives = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FinalizedGizmos.class), FinalizedGizmos.class, "standardPrimitives;alwaysOnTopPrimitives", "FIELD:Lnet/minecraft/client/renderer/LevelRenderer$FinalizedGizmos;->standardPrimitives:Lnet/minecraft/client/renderer/gizmos/DrawableGizmoPrimitives;", "FIELD:Lnet/minecraft/client/renderer/LevelRenderer$FinalizedGizmos;->alwaysOnTopPrimitives:Lnet/minecraft/client/renderer/gizmos/DrawableGizmoPrimitives;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FinalizedGizmos.class), FinalizedGizmos.class, "standardPrimitives;alwaysOnTopPrimitives", "FIELD:Lnet/minecraft/client/renderer/LevelRenderer$FinalizedGizmos;->standardPrimitives:Lnet/minecraft/client/renderer/gizmos/DrawableGizmoPrimitives;", "FIELD:Lnet/minecraft/client/renderer/LevelRenderer$FinalizedGizmos;->alwaysOnTopPrimitives:Lnet/minecraft/client/renderer/gizmos/DrawableGizmoPrimitives;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FinalizedGizmos.class, Object.class), FinalizedGizmos.class, "standardPrimitives;alwaysOnTopPrimitives", "FIELD:Lnet/minecraft/client/renderer/LevelRenderer$FinalizedGizmos;->standardPrimitives:Lnet/minecraft/client/renderer/gizmos/DrawableGizmoPrimitives;", "FIELD:Lnet/minecraft/client/renderer/LevelRenderer$FinalizedGizmos;->alwaysOnTopPrimitives:Lnet/minecraft/client/renderer/gizmos/DrawableGizmoPrimitives;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DrawableGizmoPrimitives standardPrimitives() {
            return this.standardPrimitives;
        }

        public DrawableGizmoPrimitives alwaysOnTopPrimitives() {
            return this.alwaysOnTopPrimitives;
        }
    }
}
