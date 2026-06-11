package net.minecraft.client.renderer;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.jtracy.TracyClient;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.Screenshot;
import net.minecraft.client.TextureFilteringMethod;
import net.minecraft.client.entity.ClientAvatarState;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.font.ActiveArea;
import net.minecraft.client.gui.font.EmptyArea;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.pip.GuiBannerResultRenderer;
import net.minecraft.client.gui.render.pip.GuiBookModelRenderer;
import net.minecraft.client.gui.render.pip.GuiEntityRenderer;
import net.minecraft.client.gui.render.pip.GuiProfilerChartRenderer;
import net.minecraft.client.gui.render.pip.GuiSignRenderer;
import net.minecraft.client.gui.render.pip.GuiSkinRenderer;
import net.minecraft.client.gui.render.state.ColoredRectangleRenderState;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.Zone;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.TrackedWaypoint;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/GameRenderer.class */
public class GameRenderer implements TrackedWaypoint.Projector, AutoCloseable {
    public static final int MAX_BLUR_RADIUS = 10;
    public static final float PROJECTION_Z_NEAR = 0.05f;
    public static final float PROJECTION_3D_HUD_Z_FAR = 100.0f;
    private static final float PORTAL_SPINNING_SPEED = 20.0f;
    private static final float NAUSEA_SPINNING_SPEED = 7.0f;
    private final Minecraft minecraft;
    private float renderDistance;
    public final ItemInHandRenderer itemInHandRenderer;
    private final ScreenEffectRenderer screenEffectRenderer;
    private final RenderBuffers renderBuffers;
    private float spinningEffectTime;
    private float spinningEffectSpeed;
    private float fovModifier;
    private float oldFovModifier;
    private float darkenWorldAmount;
    private float darkenWorldAmountO;
    private long lastScreenshotAttempt;
    private boolean hasWorldScreenshot;
    private final LightTexture lightTexture;
    private PanoramicScreenshotParameters panoramicScreenshotParameters;
    private final GuiRenderer guiRenderer;
    private final SubmitNodeStorage submitNodeStorage;
    private final FeatureRenderDispatcher featureRenderDispatcher;
    private Identifier postEffectId;
    private boolean effectActive;
    private static final Identifier BLUR_POST_CHAIN_ID = Identifier.withDefaultNamespace("blur");
    private static final Logger LOGGER = LogUtils.getLogger();
    private final RandomSource random = RandomSource.create();
    private boolean renderBlockOutline = true;
    private long lastActiveTime = Util.getMillis();
    private final OverlayTexture overlayTexture = new OverlayTexture();
    protected final CubeMap cubeMap = new CubeMap(Identifier.withDefaultNamespace("textures/gui/title/background/panorama"));
    protected final PanoramaRenderer panorama = new PanoramaRenderer(this.cubeMap);
    private final CrossFrameResourcePool resourcePool = new CrossFrameResourcePool(3);
    private final FogRenderer fogRenderer = new FogRenderer();
    private final LevelRenderState levelRenderState = new LevelRenderState();
    private final Camera mainCamera = new Camera();
    private final Lighting lighting = new Lighting();
    private final GlobalSettingsUniform globalSettingsUniform = new GlobalSettingsUniform();
    private final PerspectiveProjectionMatrixBuffer levelProjectionMatrixBuffer = new PerspectiveProjectionMatrixBuffer("level");
    private final CachedPerspectiveProjectionMatrixBuffer hud3dProjectionMatrixBuffer = new CachedPerspectiveProjectionMatrixBuffer("3d hud", 0.05f, 100.0f);
    final GuiRenderState guiRenderState = new GuiRenderState();

    public GameRenderer(Minecraft $$0, ItemInHandRenderer $$1, RenderBuffers $$2, BlockRenderDispatcher $$3) {
        this.minecraft = $$0;
        this.itemInHandRenderer = $$1;
        this.lightTexture = new LightTexture(this, $$0);
        this.renderBuffers = $$2;
        MultiBufferSource.BufferSource $$4 = $$2.bufferSource();
        AtlasManager $$5 = $$0.getAtlasManager();
        this.submitNodeStorage = new SubmitNodeStorage();
        this.featureRenderDispatcher = new FeatureRenderDispatcher(this.submitNodeStorage, $$3, $$4, $$5, $$2.outlineBufferSource(), $$2.crumblingBufferSource(), $$0.font);
        this.guiRenderer = new GuiRenderer(this.guiRenderState, $$4, this.submitNodeStorage, this.featureRenderDispatcher, List.of(new GuiEntityRenderer($$4, $$0.getEntityRenderDispatcher()), new GuiSkinRenderer($$4), new GuiBookModelRenderer($$4), new GuiBannerResultRenderer($$4, $$5), new GuiSignRenderer($$4, $$5), new GuiProfilerChartRenderer($$4)));
        this.screenEffectRenderer = new ScreenEffectRenderer($$0, $$5, $$4);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.globalSettingsUniform.close();
        this.lightTexture.close();
        this.overlayTexture.close();
        this.resourcePool.close();
        this.guiRenderer.close();
        this.levelProjectionMatrixBuffer.close();
        this.hud3dProjectionMatrixBuffer.close();
        this.lighting.close();
        this.cubeMap.close();
        this.fogRenderer.close();
        this.featureRenderDispatcher.close();
    }

    public SubmitNodeStorage getSubmitNodeStorage() {
        return this.submitNodeStorage;
    }

    public FeatureRenderDispatcher getFeatureRenderDispatcher() {
        return this.featureRenderDispatcher;
    }

    public LevelRenderState getLevelRenderState() {
        return this.levelRenderState;
    }

    public void setRenderBlockOutline(boolean $$0) {
        this.renderBlockOutline = $$0;
    }

    public void setPanoramicScreenshotParameters(PanoramicScreenshotParameters $$0) {
        this.panoramicScreenshotParameters = $$0;
    }

    public PanoramicScreenshotParameters getPanoramicScreenshotParameters() {
        return this.panoramicScreenshotParameters;
    }

    public boolean isPanoramicMode() {
        return this.panoramicScreenshotParameters != null;
    }

    public void clearPostEffect() {
        this.postEffectId = null;
        this.effectActive = false;
    }

    public void togglePostEffect() {
        this.effectActive = !this.effectActive;
    }

    public void checkEntityPostEffect(Entity $$0) {
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), Creeper.class, Spider.class, EnderMan.class).dynamicInvoker().invoke($$0, 0) /* invoke-custom */) {
            case -1:
            default:
                clearPostEffect();
                break;
            case 0:
                setPostEffect(Identifier.withDefaultNamespace("creeper"));
                break;
            case 1:
                setPostEffect(Identifier.withDefaultNamespace("spider"));
                break;
            case 2:
                setPostEffect(Identifier.withDefaultNamespace("invert"));
                break;
        }
    }

    private void setPostEffect(Identifier $$0) {
        this.postEffectId = $$0;
        this.effectActive = true;
    }

    public void processBlurEffect() {
        PostChain $$0 = this.minecraft.getShaderManager().getPostChain(BLUR_POST_CHAIN_ID, LevelTargetBundle.MAIN_TARGETS);
        if ($$0 != null) {
            $$0.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
        }
    }

    public void preloadUiShader(ResourceProvider $$0) {
        GpuDevice $$1 = RenderSystem.getDevice();
        ShaderSource $$2 = ($$12, $$22) -> {
            Identifier $$3 = $$22.idConverter().idToFile($$12);
            try {
                Reader $$4 = $$0.getResourceOrThrow($$3).openAsReader();
                try {
                    String string = IOUtils.toString($$4);
                    if ($$4 != null) {
                        $$4.close();
                    }
                    return string;
                } finally {
                }
            } catch (IOException $$5) {
                LOGGER.error("Coudln't preload {} shader {}: {}", new Object[]{$$22, $$12, $$5});
                return null;
            }
        };
        $$1.precompilePipeline(RenderPipelines.GUI, $$2);
        $$1.precompilePipeline(RenderPipelines.GUI_TEXTURED, $$2);
        if (TracyClient.isAvailable()) {
            $$1.precompilePipeline(RenderPipelines.TRACY_BLIT, $$2);
        }
    }

    public void tick() {
        tickFov();
        this.lightTexture.tick();
        LocalPlayer $$0 = this.minecraft.player;
        if (this.minecraft.getCameraEntity() == null) {
            this.minecraft.setCameraEntity($$0);
        }
        this.mainCamera.tick();
        this.itemInHandRenderer.tick();
        float $$1 = $$0.portalEffectIntensity;
        float $$2 = $$0.getEffectBlendFactor(MobEffects.NAUSEA, 1.0f);
        if ($$1 > 0.0f || $$2 > 0.0f) {
            this.spinningEffectSpeed = (($$1 * 20.0f) + ($$2 * 7.0f)) / ($$1 + $$2);
            this.spinningEffectTime += this.spinningEffectSpeed;
        } else {
            this.spinningEffectSpeed = 0.0f;
        }
        if (!this.minecraft.level.tickRateManager().runsNormally()) {
            return;
        }
        this.darkenWorldAmountO = this.darkenWorldAmount;
        if (this.minecraft.gui.getBossOverlay().shouldDarkenScreen()) {
            this.darkenWorldAmount += 0.05f;
            if (this.darkenWorldAmount > 1.0f) {
                this.darkenWorldAmount = 1.0f;
            }
        } else if (this.darkenWorldAmount > 0.0f) {
            this.darkenWorldAmount -= 0.0125f;
        }
        this.screenEffectRenderer.tick();
        ProfilerFiller $$3 = Profiler.get();
        $$3.push("levelRenderer");
        this.minecraft.levelRenderer.tick(this.mainCamera);
        $$3.pop();
    }

    public Identifier currentPostEffect() {
        return this.postEffectId;
    }

    public void resize(int $$0, int $$1) {
        this.resourcePool.clear();
        this.minecraft.levelRenderer.resize($$0, $$1);
    }

    public void pick(float $$0) {
        Entity entity;
        Entity $$1 = this.minecraft.getCameraEntity();
        if ($$1 == null || this.minecraft.level == null || this.minecraft.player == null) {
            return;
        }
        Profiler.get().push("pick");
        this.minecraft.hitResult = this.minecraft.player.raycastHitResult($$0, $$1);
        Minecraft minecraft = this.minecraft;
        HitResult hitResult = this.minecraft.hitResult;
        if (hitResult instanceof EntityHitResult) {
            EntityHitResult $$2 = (EntityHitResult) hitResult;
            entity = $$2.getEntity();
        } else {
            entity = null;
        }
        minecraft.crosshairPickEntity = entity;
        Profiler.get().pop();
    }

    private void tickFov() {
        float $$5;
        Entity cameraEntity = this.minecraft.getCameraEntity();
        if (cameraEntity instanceof AbstractClientPlayer) {
            AbstractClientPlayer $$0 = (AbstractClientPlayer) cameraEntity;
            Options $$1 = this.minecraft.options;
            boolean $$2 = $$1.getCameraType().isFirstPerson();
            float $$3 = $$1.fovEffectScale().get().floatValue();
            $$5 = $$0.getFieldOfViewModifier($$2, $$3);
        } else {
            $$5 = 1.0f;
        }
        this.oldFovModifier = this.fovModifier;
        this.fovModifier += ($$5 - this.fovModifier) * 0.5f;
        this.fovModifier = Mth.clamp(this.fovModifier, 0.1f, 1.5f);
    }

    private float getFov(Camera $$0, float $$1, boolean $$2) {
        if (isPanoramicMode()) {
            return 90.0f;
        }
        float $$3 = 70.0f;
        if ($$2) {
            float $$32 = this.minecraft.options.fov().get().intValue();
            $$3 = $$32 * Mth.lerp($$1, this.oldFovModifier, this.fovModifier);
        }
        Entity entity = $$0.entity();
        if (entity instanceof LivingEntity) {
            LivingEntity $$4 = (LivingEntity) entity;
            if ($$4.isDeadOrDying()) {
                float $$5 = Math.min($$4.deathTime + $$1, 20.0f);
                $$3 /= ((1.0f - (500.0f / ($$5 + 500.0f))) * 2.0f) + 1.0f;
            }
        }
        FogType $$6 = $$0.getFluidInCamera();
        if ($$6 == FogType.LAVA || $$6 == FogType.WATER) {
            float $$7 = this.minecraft.options.fovEffectScale().get().floatValue();
            $$3 *= Mth.lerp($$7, 1.0f, 0.85714287f);
        }
        return $$3;
    }

    private void bobHurt(PoseStack $$0, float $$1) {
        Entity cameraEntity = this.minecraft.getCameraEntity();
        if (cameraEntity instanceof LivingEntity) {
            LivingEntity $$2 = (LivingEntity) cameraEntity;
            float $$3 = $$2.hurtTime - $$1;
            if ($$2.isDeadOrDying()) {
                float $$4 = Math.min($$2.deathTime + $$1, 20.0f);
                $$0.mulPose((Quaternionfc) Axis.ZP.rotationDegrees(40.0f - (8000.0f / ($$4 + 200.0f))));
            }
            if ($$3 < 0.0f) {
                return;
            }
            float $$32 = $$3 / $$2.hurtDuration;
            float $$33 = Mth.sin($$32 * $$32 * $$32 * $$32 * 3.1415927f);
            float $$5 = $$2.getHurtDir();
            $$0.mulPose((Quaternionfc) Axis.YP.rotationDegrees(-$$5));
            float $$6 = (float) (((double) (-$$33)) * 14.0d * this.minecraft.options.damageTiltStrength().get().doubleValue());
            $$0.mulPose((Quaternionfc) Axis.ZP.rotationDegrees($$6));
            $$0.mulPose((Quaternionfc) Axis.YP.rotationDegrees($$5));
        }
    }

    private void bobView(PoseStack $$0, float $$1) {
        Entity cameraEntity = this.minecraft.getCameraEntity();
        if (!(cameraEntity instanceof AbstractClientPlayer)) {
            return;
        }
        AbstractClientPlayer $$3 = (AbstractClientPlayer) cameraEntity;
        ClientAvatarState $$4 = $$3.avatarState();
        float $$5 = $$4.getBackwardsInterpolatedWalkDistance($$1);
        float $$6 = $$4.getInterpolatedBob($$1);
        $$0.translate(Mth.sin($$5 * 3.1415927f) * $$6 * 0.5f, -Math.abs(Mth.cos($$5 * 3.1415927f) * $$6), 0.0f);
        $$0.mulPose((Quaternionfc) Axis.ZP.rotationDegrees(Mth.sin($$5 * 3.1415927f) * $$6 * 3.0f));
        $$0.mulPose((Quaternionfc) Axis.XP.rotationDegrees(Math.abs(Mth.cos(($$5 * 3.1415927f) - 0.2f) * $$6) * 5.0f));
    }

    private void renderItemInHand(float $$0, boolean $$1, Matrix4f $$2) {
        if (isPanoramicMode()) {
            return;
        }
        this.featureRenderDispatcher.renderAllFeatures();
        this.renderBuffers.bufferSource().endBatch();
        PoseStack $$3 = new PoseStack();
        $$3.pushPose();
        $$3.mulPose((Matrix4fc) $$2.invert(new Matrix4f()));
        Matrix4fStack $$4 = RenderSystem.getModelViewStack();
        $$4.pushMatrix().mul($$2);
        bobHurt($$3, $$0);
        if (this.minecraft.options.bobView().get().booleanValue()) {
            bobView($$3, $$0);
        }
        if (this.minecraft.options.getCameraType().isFirstPerson() && !$$1 && !this.minecraft.options.hideGui && this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            this.itemInHandRenderer.renderHandsWithItems($$0, $$3, this.minecraft.gameRenderer.getSubmitNodeStorage(), this.minecraft.player, this.minecraft.getEntityRenderDispatcher().getPackedLightCoords(this.minecraft.player, $$0));
        }
        $$4.popMatrix();
        $$3.popPose();
    }

    public Matrix4f getProjectionMatrix(float $$0) {
        Matrix4f $$1 = new Matrix4f();
        return $$1.perspective($$0 * 0.017453292f, this.minecraft.getWindow().getWidth() / this.minecraft.getWindow().getHeight(), 0.05f, getDepthFar());
    }

    public float getDepthFar() {
        return Math.max(this.renderDistance * 4.0f, this.minecraft.options.cloudRange().get().intValue() * 16);
    }

    public static float getNightVisionScale(LivingEntity $$0, float $$1) {
        MobEffectInstance $$2 = $$0.getEffect(MobEffects.NIGHT_VISION);
        if (!$$2.endsWithin(200)) {
            return 1.0f;
        }
        return 0.7f + (Mth.sin(($$2.getDuration() - $$1) * 3.1415927f * 0.2f) * 0.3f);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void render(DeltaTracker $$0, boolean $$1) throws MatchException {
        PostChain $$6;
        if (this.minecraft.isWindowActive() || !this.minecraft.options.pauseOnLostFocus || (this.minecraft.options.touchscreen().get().booleanValue() && this.minecraft.mouseHandler.isRightPressed())) {
            this.lastActiveTime = Util.getMillis();
        } else if (Util.getMillis() - this.lastActiveTime > 500) {
            this.minecraft.pauseGame(false);
        }
        if (this.minecraft.noRender) {
            return;
        }
        ProfilerFiller $$2 = Profiler.get();
        $$2.push("camera");
        updateCamera($$0);
        $$2.pop();
        this.globalSettingsUniform.update(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), this.minecraft.options.glintStrength().get().doubleValue(), this.minecraft.level == null ? 0L : this.minecraft.level.getGameTime(), $$0, this.minecraft.options.getMenuBackgroundBlurriness(), this.mainCamera, this.minecraft.options.textureFiltering().get() == TextureFilteringMethod.RGSS);
        boolean $$3 = this.minecraft.isGameLoadFinished();
        int $$4 = (int) this.minecraft.mouseHandler.getScaledXPos(this.minecraft.getWindow());
        int $$5 = (int) this.minecraft.mouseHandler.getScaledYPos(this.minecraft.getWindow());
        if ($$3 && $$1 && this.minecraft.level != null) {
            $$2.push("world");
            renderLevel($$0);
            tryTakeScreenshotIfNeeded();
            this.minecraft.levelRenderer.doEntityOutline();
            if (this.postEffectId != null && this.effectActive && ($$6 = this.minecraft.getShaderManager().getPostChain(this.postEffectId, LevelTargetBundle.MAIN_TARGETS)) != null) {
                $$6.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
            }
            $$2.pop();
        }
        this.fogRenderer.endFrame();
        RenderTarget $$7 = this.minecraft.getMainRenderTarget();
        RenderSystem.getDevice().createCommandEncoder().clearDepthTexture($$7.getDepthTexture(), 1.0d);
        this.minecraft.gameRenderer.getLighting().setupFor(Lighting.Entry.ITEMS_3D);
        this.guiRenderState.reset();
        $$2.push("guiExtraction");
        GuiGraphics $$8 = new GuiGraphics(this.minecraft, this.guiRenderState, $$4, $$5);
        if ($$3 && $$1 && this.minecraft.level != null) {
            this.minecraft.gui.render($$8, $$0);
        }
        if (this.minecraft.getOverlay() != null) {
            try {
                this.minecraft.getOverlay().render($$8, $$4, $$5, $$0.getGameTimeDeltaTicks());
            } catch (Throwable $$9) {
                CrashReport $$10 = CrashReport.forThrowable($$9, "Rendering overlay");
                CrashReportCategory $$11 = $$10.addCategory("Overlay render details");
                $$11.setDetail("Overlay name", () -> {
                    return this.minecraft.getOverlay().getClass().getCanonicalName();
                });
                throw new ReportedException($$10);
            }
        } else if ($$3 && this.minecraft.screen != null) {
            try {
                this.minecraft.screen.renderWithTooltipAndSubtitles($$8, $$4, $$5, $$0.getGameTimeDeltaTicks());
                if (SharedConstants.DEBUG_CURSOR_POS) {
                    this.minecraft.mouseHandler.drawDebugMouseInfo(this.minecraft.font, $$8);
                }
                try {
                    if (this.minecraft.screen != null) {
                        this.minecraft.screen.handleDelayedNarration();
                    }
                } catch (Throwable $$15) {
                    CrashReport $$16 = CrashReport.forThrowable($$15, "Narrating screen");
                    CrashReportCategory $$17 = $$16.addCategory("Screen details");
                    $$17.setDetail("Screen name", () -> {
                        return this.minecraft.screen.getClass().getCanonicalName();
                    });
                    throw new ReportedException($$16);
                }
            } catch (Throwable $$12) {
                CrashReport $$13 = CrashReport.forThrowable($$12, "Rendering screen");
                CrashReportCategory $$14 = $$13.addCategory("Screen render details");
                $$14.setDetail("Screen name", () -> {
                    return this.minecraft.screen.getClass().getCanonicalName();
                });
                this.minecraft.mouseHandler.fillMousePositionDetails($$14, this.minecraft.getWindow());
                throw new ReportedException($$13);
            }
        }
        if ($$3 && $$1 && this.minecraft.level != null) {
            this.minecraft.gui.renderSavingIndicator($$8, $$0);
        }
        if ($$3) {
            Zone $$18 = $$2.zone("toasts");
            try {
                this.minecraft.getToastManager().render($$8);
                if ($$18 != null) {
                    $$18.close();
                }
            } catch (Throwable th) {
                if ($$18 != null) {
                    try {
                        $$18.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (!(this.minecraft.screen instanceof DebugOptionsScreen)) {
            this.minecraft.gui.renderDebugOverlay($$8);
        }
        this.minecraft.gui.renderDeferredSubtitles();
        if (SharedConstants.DEBUG_ACTIVE_TEXT_AREAS) {
            renderActiveTextDebug();
        }
        $$2.popPush("guiRendering");
        this.guiRenderer.render(this.fogRenderer.getBuffer(FogRenderer.FogMode.NONE));
        this.guiRenderer.incrementFrameNumber();
        $$2.pop();
        $$8.applyCursor(this.minecraft.getWindow());
        this.submitNodeStorage.endFrame();
        this.featureRenderDispatcher.endFrame();
        this.resourcePool.endFrame();
    }

    private void renderActiveTextDebug() {
        this.guiRenderState.nextStratum();
        this.guiRenderState.forEachText($$0 -> {
            $$0.ensurePrepared().visit(new Font.GlyphVisitor() { // from class: net.minecraft.client.renderer.GameRenderer.1
                private int index;

                @Override // net.minecraft.client.gui.Font.GlyphVisitor
                public void acceptGlyph(TextRenderable.Styled $$0) {
                    renderDebugMarkers($$0, false);
                }

                @Override // net.minecraft.client.gui.Font.GlyphVisitor
                public void acceptEmptyArea(EmptyArea $$0) {
                    renderDebugMarkers($$0, true);
                }

                private void renderDebugMarkers(ActiveArea $$0, boolean $$1) {
                    int i = $$1 ? 128 : 255;
                    int i2 = this.index;
                    this.index = i2 + 1;
                    int $$2 = i - ((i2 & 1) * 64);
                    Style $$3 = $$0.style();
                    int $$4 = $$3.getClickEvent() != null ? $$2 : 0;
                    int $$5 = $$3.getHoverEvent() != null ? $$2 : 0;
                    int $$6 = ($$4 == 0 || $$5 == 0) ? $$2 : 0;
                    int $$7 = ARGB.color(128, $$4, $$5, $$6);
                    GameRenderer.this.guiRenderState.submitGuiElement(new ColoredRectangleRenderState(RenderPipelines.GUI, TextureSetup.noTexture(), $$0.pose, (int) $$0.activeLeft(), (int) $$0.activeTop(), (int) $$0.activeRight(), (int) $$0.activeBottom(), $$7, $$7, $$0.scissor));
                }
            });
        });
    }

    private void tryTakeScreenshotIfNeeded() {
        if (this.hasWorldScreenshot || !this.minecraft.isLocalServer()) {
            return;
        }
        long $$0 = Util.getMillis();
        if ($$0 - this.lastScreenshotAttempt < 1000) {
            return;
        }
        this.lastScreenshotAttempt = $$0;
        IntegratedServer $$1 = this.minecraft.getSingleplayerServer();
        if ($$1 == null || $$1.isStopped()) {
            return;
        }
        $$1.getWorldScreenshotFile().ifPresent($$02 -> {
            if (Files.isRegularFile($$02, new LinkOption[0])) {
                this.hasWorldScreenshot = true;
            } else {
                takeAutoScreenshot($$02);
            }
        });
    }

    private void takeAutoScreenshot(Path $$0) {
        if (this.minecraft.levelRenderer.countRenderedSections() > 10 && this.minecraft.levelRenderer.hasRenderedAllSections()) {
            Screenshot.takeScreenshot(this.minecraft.getMainRenderTarget(), $$1 -> {
                Util.ioPool().execute(() -> {
                    int $$2 = $$1.getWidth();
                    int $$3 = $$1.getHeight();
                    int $$4 = 0;
                    int $$5 = 0;
                    if ($$2 > $$3) {
                        $$4 = ($$2 - $$3) / 2;
                        $$2 = $$3;
                    } else {
                        $$5 = ($$3 - $$2) / 2;
                        $$3 = $$2;
                    }
                    try {
                        try {
                            NativeImage $$6 = new NativeImage(64, 64, false);
                            try {
                                $$1.resizeSubRectTo($$4, $$5, $$2, $$3, $$6);
                                $$6.writeToFile($$0);
                                $$6.close();
                                $$1.close();
                            } catch (Throwable th) {
                                try {
                                    $$6.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            $$1.close();
                            throw th3;
                        }
                    } catch (IOException $$7) {
                        LOGGER.warn("Couldn't save auto screenshot", $$7);
                        $$1.close();
                    }
                });
            });
        }
    }

    private boolean shouldRenderBlockOutline() {
        if (!this.renderBlockOutline) {
            return false;
        }
        Entity $$0 = this.minecraft.getCameraEntity();
        boolean $$1 = ($$0 instanceof Player) && !this.minecraft.options.hideGui;
        if ($$1 && !((Player) $$0).getAbilities().mayBuild) {
            ItemStack $$2 = ((LivingEntity) $$0).getMainHandItem();
            HitResult $$3 = this.minecraft.hitResult;
            if ($$3 != null && $$3.getType() == HitResult.Type.BLOCK) {
                BlockPos $$4 = ((BlockHitResult) $$3).getBlockPos();
                BlockState $$5 = this.minecraft.level.getBlockState($$4);
                if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
                    $$1 = $$5.getMenuProvider(this.minecraft.level, $$4) != null;
                } else {
                    BlockInWorld $$6 = new BlockInWorld(this.minecraft.level, $$4, false);
                    this.minecraft.level.registryAccess().lookupOrThrow((ResourceKey) Registries.BLOCK);
                    $$1 = !$$2.isEmpty() && ($$2.canBreakBlockInAdventureMode($$6) || $$2.canPlaceOnBlockInAdventureMode($$6));
                }
            }
        }
        return $$1;
    }

    public void updateCamera(DeltaTracker $$0) {
        float $$1 = $$0.getGameTimeDeltaPartialTick(true);
        LocalPlayer $$2 = this.minecraft.player;
        if ($$2 == null || this.minecraft.level == null) {
            return;
        }
        if (this.minecraft.getCameraEntity() == null) {
            this.minecraft.setCameraEntity($$2);
        }
        Entity $$3 = this.minecraft.getCameraEntity() == null ? $$2 : this.minecraft.getCameraEntity();
        float $$4 = this.minecraft.level.tickRateManager().isEntityFrozen($$3) ? 1.0f : $$1;
        this.mainCamera.setup(this.minecraft.level, $$3, !this.minecraft.options.getCameraType().isFirstPerson(), this.minecraft.options.getCameraType().isMirrored(), $$4);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void renderLevel(DeltaTracker $$0) throws MatchException {
        float $$1 = $$0.getGameTimeDeltaPartialTick(true);
        LocalPlayer $$2 = this.minecraft.player;
        this.lightTexture.updateLightTexture(1.0f);
        pick($$1);
        ProfilerFiller $$3 = Profiler.get();
        boolean $$4 = shouldRenderBlockOutline();
        extractCamera($$1);
        this.renderDistance = this.minecraft.options.getEffectiveRenderDistance() * 16;
        $$3.push("matrices");
        float $$5 = getFov(this.mainCamera, $$1, true);
        Matrix4f $$6 = getProjectionMatrix($$5);
        PoseStack $$7 = new PoseStack();
        bobHurt($$7, this.mainCamera.getPartialTickTime());
        if (this.minecraft.options.bobView().get().booleanValue()) {
            bobView($$7, this.mainCamera.getPartialTickTime());
        }
        $$6.mul($$7.last().pose());
        float $$8 = this.minecraft.options.screenEffectScale().get().floatValue();
        float $$9 = Mth.lerp($$1, $$2.oPortalEffectIntensity, $$2.portalEffectIntensity);
        float $$10 = $$2.getEffectBlendFactor(MobEffects.NAUSEA, $$1);
        float $$11 = Math.max($$9, $$10) * $$8 * $$8;
        if ($$11 > 0.0f) {
            float $$12 = (5.0f / (($$11 * $$11) + 5.0f)) - ($$11 * 0.04f);
            float $$122 = $$12 * $$12;
            Vector3f $$13 = new Vector3f(0.0f, Mth.SQRT_OF_TWO / 2.0f, Mth.SQRT_OF_TWO / 2.0f);
            float $$14 = (this.spinningEffectTime + ($$1 * this.spinningEffectSpeed)) * 0.017453292f;
            $$6.rotate($$14, $$13);
            $$6.scale(1.0f / $$122, 1.0f, 1.0f);
            $$6.rotate(-$$14, $$13);
        }
        RenderSystem.setProjectionMatrix(this.levelProjectionMatrixBuffer.getBuffer($$6), ProjectionType.PERSPECTIVE);
        Quaternionf $$15 = this.mainCamera.rotation().conjugate(new Quaternionf());
        Matrix4f $$16 = new Matrix4f().rotation($$15);
        $$3.popPush("fog");
        Vector4f $$17 = this.fogRenderer.setupFog(this.mainCamera, this.minecraft.options.getEffectiveRenderDistance(), $$0, getDarkenWorldAmount($$1), this.minecraft.level);
        GpuBufferSlice $$18 = this.fogRenderer.getBuffer(FogRenderer.FogMode.WORLD);
        $$3.popPush("level");
        boolean $$19 = this.minecraft.gui.getBossOverlay().shouldCreateWorldFog();
        this.minecraft.levelRenderer.renderLevel(this.resourcePool, $$0, $$4, this.mainCamera, $$16, $$6, getProjectionMatrixForCulling($$5), $$18, $$17, !$$19);
        $$3.popPush("hand");
        boolean $$20 = (this.minecraft.getCameraEntity() instanceof LivingEntity) && ((LivingEntity) this.minecraft.getCameraEntity()).isSleeping();
        RenderSystem.setProjectionMatrix(this.hud3dProjectionMatrixBuffer.getBuffer(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), getFov(this.mainCamera, $$1, false)), ProjectionType.PERSPECTIVE);
        RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(this.minecraft.getMainRenderTarget().getDepthTexture(), 1.0d);
        renderItemInHand($$1, $$20, $$16);
        $$3.popPush("screenEffects");
        MultiBufferSource.BufferSource $$21 = this.renderBuffers.bufferSource();
        this.screenEffectRenderer.renderScreenEffect($$20, $$1, this.submitNodeStorage);
        this.featureRenderDispatcher.renderAllFeatures();
        $$21.endBatch();
        $$3.pop();
        RenderSystem.setShaderFog(this.fogRenderer.getBuffer(FogRenderer.FogMode.NONE));
        if (this.minecraft.debugEntries.isCurrentlyEnabled(DebugScreenEntries.THREE_DIMENSIONAL_CROSSHAIR) && this.minecraft.options.getCameraType().isFirstPerson() && !this.minecraft.options.hideGui) {
            this.minecraft.getDebugOverlay().render3dCrosshair(this.mainCamera);
        }
    }

    private void extractCamera(float $$0) {
        CameraRenderState $$1 = this.levelRenderState.cameraRenderState;
        $$1.initialized = this.mainCamera.isInitialized();
        $$1.pos = this.mainCamera.position();
        $$1.blockPos = this.mainCamera.blockPosition();
        $$1.entityPos = this.mainCamera.entity().getPosition($$0);
        $$1.orientation = new Quaternionf(this.mainCamera.rotation());
    }

    private Matrix4f getProjectionMatrixForCulling(float $$0) {
        float $$1 = Math.max($$0, this.minecraft.options.fov().get().intValue());
        return getProjectionMatrix($$1);
    }

    public void resetData() {
        this.screenEffectRenderer.resetItemActivation();
        this.minecraft.getMapTextureManager().resetData();
        this.mainCamera.reset();
        this.hasWorldScreenshot = false;
    }

    public void displayItemActivation(ItemStack $$0) {
        this.screenEffectRenderer.displayItemActivation($$0, this.random);
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }

    public float getDarkenWorldAmount(float $$0) {
        return Mth.lerp($$0, this.darkenWorldAmountO, this.darkenWorldAmount);
    }

    public float getRenderDistance() {
        return this.renderDistance;
    }

    public Camera getMainCamera() {
        return this.mainCamera;
    }

    public LightTexture lightTexture() {
        return this.lightTexture;
    }

    public OverlayTexture overlayTexture() {
        return this.overlayTexture;
    }

    @Override // net.minecraft.world.waypoints.TrackedWaypoint.Projector
    public Vec3 projectPointToScreen(Vec3 $$0) {
        Matrix4f $$1 = getProjectionMatrix(getFov(this.mainCamera, 0.0f, true));
        Quaternionf $$2 = this.mainCamera.rotation().conjugate(new Quaternionf());
        Matrix4f $$3 = new Matrix4f().rotation($$2);
        Matrix4f $$4 = $$1.mul($$3);
        Vec3 $$5 = this.mainCamera.position();
        Vec3 $$6 = $$0.subtract($$5);
        Vector3f $$7 = $$4.transformProject($$6.toVector3f());
        return new Vec3((Vector3fc) $$7);
    }

    @Override // net.minecraft.world.waypoints.TrackedWaypoint.Projector
    public double projectHorizonToScreen() {
        float $$0 = this.mainCamera.xRot();
        if ($$0 <= -90.0f) {
            return Double.NEGATIVE_INFINITY;
        }
        if ($$0 >= 90.0f) {
            return Double.POSITIVE_INFINITY;
        }
        float $$1 = getFov(this.mainCamera, 0.0f, true);
        return Math.tan($$0 * 0.017453292f) / Math.tan(($$1 / 2.0f) * 0.017453292f);
    }

    public GlobalSettingsUniform getGlobalSettingsUniform() {
        return this.globalSettingsUniform;
    }

    public Lighting getLighting() {
        return this.lighting;
    }

    public void setLevel(ClientLevel $$0) {
        if ($$0 != null) {
            this.lighting.updateLevel($$0.dimensionType().cardinalLightType());
        }
    }

    public PanoramaRenderer getPanorama() {
        return this.panorama;
    }
}
