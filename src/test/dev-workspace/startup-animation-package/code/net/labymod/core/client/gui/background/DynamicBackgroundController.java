package net.labymod.core.client.gui.background;

import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.client.lifecycle.GameFpsLimitEvent;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.SchematicUniformBlock;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.background.position.PositionTransition;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;
import net.labymod.core.client.gui.background.title.TitleScreenLogoListener;
import net.labymod.core.client.render.schematic.Schematic;
import net.labymod.core.client.render.schematic.SchematicRenderer;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.lighting.LightSourceRegistry;
import net.labymod.core.client.render.schematic.lighting.PointLightSource;
import net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.DepthConventionHolder;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.pipeline.target.RenderTargetDescription;
import net.labymod.laby3d.api.pipeline.target.attachment.AttachmentType;
import net.labymod.laby3d.api.pipeline.target.attachment.ClearValue;
import net.labymod.laby3d.api.pipeline.target.attachment.RenderTargetAttachmentDescription;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/DynamicBackgroundController.class */
@Singleton
@Referenceable
public class DynamicBackgroundController {
    private static final String ANIMATION_OPENING = "opening";
    private final LabyAPI labyAPI;
    private final Laby3D laby3D;
    private final DynamicBackgroundConfig config;
    private final RenderTarget schematicTarget;
    private SchematicRenderer renderer;
    private ScreenPositionRegistry screenPositionRegistry;
    private Schematic schematic;
    private Model entityModel;
    private AnimationController entityAnimationController;
    private AnimationLoader entityAnimationLoader;
    private DefaultModelVertexWriter modelVertexWriter;
    private boolean loaded;
    private boolean openingPlayed;
    private boolean wind;
    private float timePassed;
    private long lastTickTime;
    private int prevMouseX;
    private int prevMouseY;
    private long lastTimeMouseMoved;
    private boolean crashed;
    private final float[] weatherEffectXOffsets = new float[1024];
    private final float[] weatherEffectZOffsets = new float[1024];
    private final Random random = new Random();
    private final boolean valentine;
    private static final Logging LOGGER = Logging.create((Class<?>) DynamicBackgroundController.class);
    public static final CubicBezier OPENER_CURVE = new CubicBezier(0.2d, 0.32d, 0.2d, 1.0d);
    public static final Location POS_OPENER_START = new Location(17.0d, 13.0d, 82.0d, 0.0d, 0.0d, -90.0d);
    public static final Location POS_OPENER_TRANSFER = new Location(20.0d, 12.8d, 44.0d, 8.0d, -5.0d, -10.0d);
    public static final Location POS_TITLE_SCREEN = new Location(31.0d, 13.0d, 6.0d, 21.0d, -13.0d, 0.0d);
    public static final Location CUSTOMIZATION_PLAYER_CAMERA = new Location(20.0d, 13.0d, 38.599998474121094d, 20.0d, 0.0d, 0.0d);
    public static final Location SHOP_PLAYER_CAMERA = new Location(21.5d, 13.0d, 39.20000076293945d, 38.0d, 0.0d, 0.0d);
    public static final Location POS_PLAYER_ENTITY = new Location(17.0d, 12.0d, 40.5d, -45.0d, 0.0d, 0.0d);
    public static boolean LIGHTING = true;
    private static final IntList WINTER_MONTHS = IntList.of(12, 1, 2);

    public DynamicBackgroundController() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        boolean winter = WINTER_MONTHS.contains(month);
        this.valentine = month == 2 && day == 14;
        boolean showWeatherEffect = this.valentine || winter;
        this.labyAPI = Laby.labyAPI();
        this.laby3D = Laby.references().laby3D();
        this.labyAPI.eventBus().registerListener(this);
        this.labyAPI.eventBus().registerListener(new TitleScreenLogoListener(this));
        this.config = this.labyAPI.config().appearance().dynamicBackground();
        this.config.shader().addChangeListener(this::setRendererDirty);
        this.config.coloredLight().addChangeListener(this::setRendererDirty);
        this.config.brightness().addChangeListener(this::setRendererDirty);
        RenderTargetDescription renderTargetDescription = RenderTargetDescription.builder().setSize(1, 1).addColorAttachment(RenderTargetAttachmentDescription.builder().setName(VertexDescriptions.COLOR_NAME).setType(AttachmentType.COLOR).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setSamplerDescription(builder -> {
            builder.setFilter(SamplerDescription.Filter.LINEAR);
        }).build()).setDepthAttachment(RenderTargetAttachmentDescription.builder().setName("Depth32").setFormat(DeviceTexture.Format.D32_FLOAT).setType(AttachmentType.DEPTH).setClearValue(ClearValue.depth(DepthConventionHolder.get().clearDepth())).setSamplerDescription(builder2 -> {
            builder2.setFilter(SamplerDescription.Filter.LINEAR);
        }).build()).build();
        this.schematicTarget = this.laby3D.renderDevice().createTarget(SchematicUniformBlock.NAME, renderTargetDescription);
        try {
            ModelService modelService = Laby.references().modelService();
            this.entityModel = modelService.loadBlockBenchModel(Constants.Resources.CAVE_ENTITIES);
            this.entityAnimationLoader = new AnimationLoader(Constants.Resources.CAVE_ENTITIES_ANIMATION);
            this.entityAnimationLoader.load();
            this.entityAnimationController = modelService.createAnimationController();
            this.entityAnimationController.tickProvider(() -> {
                return TimeUtil.convertMillisecondsToTicks((long) this.timePassed);
            });
            this.schematic = new Schematic(winter ? Constants.Resources.WINTER_CAVE : Constants.Resources.NORMAL_CAVE);
            this.renderer = new SchematicRenderer(this, this.schematic);
            this.renderer.registerRenderHook((cmd, stack, left, top, right, bottom, partialTicks) -> {
                renderEntities(cmd);
            });
            this.renderer.camera().teleport(POS_OPENER_START);
            if (showWeatherEffect) {
                this.renderer.registerRenderHook((cmd2, stack2, left2, top2, right2, bottom2, partialTicks2) -> {
                    if (this.config.snowflakes().get().booleanValue()) {
                        submitWeatherEffect(cmd2, stack2, partialTicks2);
                    }
                });
            }
            this.screenPositionRegistry = new ScreenPositionRegistry(this.renderer.camera());
            this.modelVertexWriter = new DefaultModelVertexWriter(this.schematic);
            this.screenPositionRegistry.register();
            for (int x = 0; x < 32; x++) {
                for (int z = 0; z < 32; z++) {
                    float strengthX = z - 16;
                    float strengthZ = x - 16;
                    float distance = (float) Math.sqrt((strengthX * strengthX) + (strengthZ * strengthZ));
                    this.weatherEffectXOffsets[(x << 5) | z] = (-strengthZ) / distance;
                    this.weatherEffectZOffsets[(x << 5) | z] = strengthX / distance;
                }
            }
            this.wind = true;
            this.loaded = true;
        } catch (Throwable throwable) {
            onCrash(throwable);
        }
    }

    private void setRendererDirty() {
        if (this.renderer != null) {
            this.renderer.setDirty();
        }
    }

    private void submitWeatherEffect(CommandBuffer cmd, Stack stack, float partialTicks) {
        CinematicCamera camera = this.renderer.camera();
        Location location = camera.location();
        int cameraX = MathHelper.floor(location.getX());
        int cameraY = MathHelper.floor(location.getY());
        int cameraZ = MathHelper.floor(location.getZ());
        double timePassed = ((double) this.timePassed) / 50.0d;
        float distanceToTitlePos = (float) Math.abs(location.getZ() - POS_TITLE_SCREEN.getZ());
        float strength = 1.0f * (1.0f - MathHelper.clamp(distanceToTitlePos / 60.0f, 0.0f, 1.0f));
        RenderState renderState = RenderStates.SCHEMATIC;
        BufferBuilder buffer = this.laby3D.begin(renderState.drawingMode(), renderState.vertexDescription());
        for (int posZ = cameraZ - 10; posZ <= cameraZ + 10; posZ++) {
            for (int posX = cameraX - 10; posX <= cameraX + 10; posX++) {
                int index = (((((posZ - cameraZ) + 16) * 32) + posX) - cameraX) + 16;
                float shiftX = (float) (((double) this.weatherEffectXOffsets[index & 1023]) * 0.5d);
                float shiftZ = (float) (((double) this.weatherEffectZOffsets[index & 1023]) * 0.5d);
                int minY = cameraY - 10;
                int maxY = cameraY + 10;
                this.random.setSeed(((((long) (posX * posX)) * 3121) + (((long) posX) * 45238971)) ^ (((((long) (posZ * posZ)) * 418711) + (((long) posZ) * 13761)) + ((long) 3)));
                double progress = (timePassed % 512.0d) / 512.0d;
                if (this.valentine) {
                    progress = 1.0d - progress;
                }
                double offsetX = this.random.nextDouble() + (timePassed * this.random.nextGaussian() * 0.01d);
                double offsetY = this.random.nextDouble() + (timePassed * this.random.nextGaussian() * 0.001d);
                double distanceX = ((double) (posX + 0.5f)) - ((double) cameraX);
                double distanceZ = ((double) (posZ + 0.5f)) - ((double) cameraZ);
                double distance = Math.sqrt((distanceX * distanceX) + (distanceZ * distanceZ));
                double visibilityFactor = distance / ((double) 10);
                double opacity = (((1.0d - (visibilityFactor * visibilityFactor)) * 0.3d) + 0.5d) * ((double) strength) * MathHelper.clamp(visibilityFactor * visibilityFactor * 3.0d, 0.0d, 1.0d);
                float brightness = (float) Math.min(1.0d, 1.0d - (distance / 12.0d));
                buffer.addVertex((posX - shiftX) + 0.5f, minY, (posZ - shiftZ) + 0.5f).setUv((float) (0.0d + offsetX), (float) ((((double) minY) * 0.25d) + progress + offsetY)).setColor(brightness, brightness, brightness, (float) opacity).setFloat(0.0f).setNormal(0.0f, 0.0f, 0.0f);
                buffer.addVertex(posX + shiftX + 0.5f, minY, posZ + shiftZ + 0.5f).setUv((float) (1.0d + offsetX), (float) ((((double) minY) * 0.25d) + progress + offsetY)).setColor(brightness, brightness, brightness, (float) opacity).setFloat(0.0f).setNormal(0.0f, 0.0f, 0.0f);
                buffer.addVertex(posX + shiftX + 0.5f, maxY, posZ + shiftZ + 0.5f).setUv((float) (1.0d + offsetX), (float) ((((double) maxY) * 0.25d) + progress + offsetY)).setColor(brightness, brightness, brightness, (float) opacity).setFloat(0.0f).setNormal(0.0f, 0.0f, 0.0f);
                buffer.addVertex((posX - shiftX) + 0.5f, maxY, (posZ - shiftZ) + 0.5f).setUv((float) (0.0d + offsetX), (float) ((((double) maxY) * 0.25d) + progress + offsetY)).setColor(brightness, brightness, brightness, (float) opacity).setFloat(0.0f).setNormal(0.0f, 0.0f, 0.0f);
            }
        }
        GeometryData geometryData = buffer.build();
        if (geometryData == null) {
            return;
        }
        ShaderTextures.setShaderTexture(0, this.valentine ? Textures.Splash.HEARTS : Textures.Splash.SNOW);
        Mesh mesh = Mesh.create(this.laby3D.renderDevice(), () -> {
            return "Weather Effect";
        }, geometryData);
        cmd.bindPipeline(renderState);
        cmd.bindTexture(0, ShaderTextures.getShaderTexture(0));
        cmd.bindUniformBlock(SchematicUniformBlock.NAME, this.laby3D.schematic());
        Objects.requireNonNull(mesh);
        cmd.addCleanupAction(mesh::close);
        cmd.draw(mesh);
    }

    public void configureBackground() {
        SchematicUniformBlock schematic = this.laby3D.schematic();
        CinematicCamera camera = this.renderer.camera();
        schematic.projectionMatrix().set(camera.projectionMatrix());
        schematic.viewMatrix().set(camera.viewMatrix());
        schematic.modelMatrix().set(new Matrix4f());
        configureLighting(schematic);
    }

    private void configureLighting(SchematicUniformBlock schematic) {
        schematic.lightSourceEnabled().set(Boolean.valueOf(this.config.shader().get().booleanValue() && LIGHTING));
        LightSourceRegistry registry = LightSourceRegistry.getInstance();
        List<LightSource> lightSources = registry.getLightSources();
        int size = lightSources.size();
        for (int index = 0; index < size; index++) {
            PointLightSource lightSource = (PointLightSource) lightSources.get(index);
            if (index < 48) {
                schematic.lightSourcePosition().set(index, lightSource.getPosition());
                schematic.lightSourceColor().set(index, this.config.coloredLight().get().booleanValue() ? lightSource.getColor() : PointLightSource.WHITE);
                schematic.lightSourceConstant().set(index, Float.valueOf(lightSource.getConstant()));
                schematic.lightSourceLinear().set(index, Float.valueOf(lightSource.getLinear()));
                schematic.lightSourceQuadratic().set(index, Float.valueOf(lightSource.getQuadratic()));
            } else {
                return;
            }
        }
    }

    @Subscribe
    public void onGameFpsLimit(GameFpsLimitEvent event) {
        if (this.labyAPI.minecraft().isIngame() || !this.loaded) {
            return;
        }
        long timePassed = TimeUtil.getMillis() - this.lastTickTime;
        boolean isControllerInUse = timePassed < 300;
        boolean limitFps = this.labyAPI.config().appearance().dynamicBackground().limitFpsWhenUnfocused().get().booleanValue();
        if (isControllerInUse && limitFps) {
            boolean windowFocused = this.labyAPI.minecraft().minecraftWindow().isFocused() || TimeUtil.getMillis() - this.lastTimeMouseMoved < 20000;
            boolean animationInProgress = this.renderer.camera().getProgress() < 1.0f;
            event.setFramerateLimit((windowFocused || animationInProgress) ? 60 : 15);
        }
        MutableMouse mouse = this.labyAPI.minecraft().mouse();
        if (mouse.getX() != this.prevMouseX || mouse.getY() != this.prevMouseY) {
            this.lastTimeMouseMoved = TimeUtil.getMillis();
            this.prevMouseX = mouse.getX();
            this.prevMouseY = mouse.getY();
        }
    }

    @Subscribe
    public void onScreenResize(ScreenResizeEvent event) {
        if (this.schematicTarget != null) {
            this.schematicTarget.resize(event.getWidth(), event.getHeight());
        }
    }

    @Subscribe
    public void onSettingUpdate(SettingUpdateEvent event) {
    }

    public void playOpening() {
        if (!this.loaded) {
            return;
        }
        CinematicCamera camera = this.renderer.camera();
        camera.teleport(POS_OPENER_START);
        camera.moveTo(4000L, OPENER_CURVE, POS_OPENER_TRANSFER, POS_TITLE_SCREEN);
        this.timePassed = 0.0f;
        this.openingPlayed = true;
        this.entityAnimationController.stop();
        ModelAnimation animation = this.entityAnimationLoader.getAnimation(ANIMATION_OPENING);
        this.entityAnimationController.playNext(animation);
    }

    public boolean isOpeningPlaying() {
        ModelAnimation animation = this.entityAnimationController.getPlaying();
        return animation != null && animation.getName().equals(ANIMATION_OPENING);
    }

    public void tick() {
        if (this.loaded && this.openingPlayed) {
            this.renderer.onTick();
        }
        this.lastTickTime = TimeUtil.getMillis();
    }

    public void renderTick(Stack stack, float left, float top, float right, float bottom, float tickDelta) {
        if (!this.loaded || !this.openingPlayed || !this.wind) {
            return;
        }
        float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        CinematicCamera camera = this.renderer.camera();
        Location position = camera.location();
        Location mod = camera.positionModifier(0);
        float time = this.timePassed / 3000.0f;
        float strength = (float) (1.0d - (((double) (1.0f - (time * time))) * Math.exp(((-0.5f) * time) * time)));
        float distance = (float) Math.abs(position.getZ() - POS_TITLE_SCREEN.getZ());
        float strength2 = strength * (1.0f - MathHelper.clamp(distance / 30.0f, 0.0f, 1.0f));
        mod.setRoll((-MathHelper.sin(time)) * strength2);
        mod.setPitch(MathHelper.sin(time) * strength2);
        mod.setYaw(MathHelper.cos(time) * strength2);
        mod.setY(((-(1.0f - strength2)) * strength2) + ((MathHelper.cos(time * 2.0f) * strength2) / 5.0f));
        this.renderer.onRenderTick(stack, left, top, right, bottom, partialTicks);
    }

    public void render(ScreenContext context, float left, float top, float right, float bottom) {
        if (!this.loaded || !this.openingPlayed) {
            return;
        }
        float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        renderFadeOut(context, left, top, right, bottom);
        float resolution = this.labyAPI.config().appearance().dynamicBackground().resolution().get().floatValue();
        try {
            Window window = this.labyAPI.minecraft().minecraftWindow();
            int rawWidth = window.getRawWidth();
            int rawHeight = window.getRawHeight();
            float factor = 1.0f + ((10.0f - resolution) / 9.0f);
            int targetWidth = (int) (rawWidth / factor);
            int targetHeight = (int) (rawHeight / factor);
            if (targetWidth == 0 || targetHeight == 0) {
                return;
            }
            if (this.schematicTarget.width() != targetWidth || this.schematicTarget.height() != targetHeight) {
                this.schematicTarget.resize(targetWidth, targetHeight);
            }
            GFXRenderPipeline renderPipeline = this.labyAPI.gfxRenderPipeline();
            renderPipeline.applyToTarget(this.schematicTarget, renderTarget -> {
                renderWorld(context, left, top, right, bottom, partialTicks);
            });
            RenderTarget destinationTarget = this.labyAPI.minecraft().mainTarget();
            context.canvas().submitBlitRenderTarget(0.0f, 0.0f, destinationTarget.width(), destinationTarget.height(), destinationTarget, this.schematicTarget);
        } catch (Exception exception) {
            onCrash(exception);
        }
        this.timePassed += TimeUtil.convertDeltaToMilliseconds(context.getTickDelta());
    }

    private void renderWorld(ScreenContext context, float left, float top, float right, float bottom, float partialTicks) {
        this.renderer.render(context.stack(), left, top, right, bottom, partialTicks);
    }

    private void renderFadeOut(ScreenContext context, float left, float top, float right, float bottom) {
        float blackFadeOutProgress = getTransitionProgress();
        context.canvas().submitAbsoluteRect(left, top, right, bottom, ColorFormat.ARGB32.pack(0.0f, 0.0f, 0.0f, blackFadeOutProgress));
    }

    private void renderEntities(CommandBuffer cmd) {
        ModelAnimation loop;
        AnimationController controller = this.entityAnimationController;
        controller.applyAnimation(this.entityModel, new String[0]);
        if (!controller.isPlaying() && (loop = this.entityAnimationLoader.getAnimation("loop")) != null) {
            controller.playNext(loop);
        }
        renderModel(cmd, this.entityModel, 1.0f, true);
    }

    public void renderModel(CommandBuffer cmd, Model model, float opacity, boolean lighting) {
        this.modelVertexWriter.setOpacity(opacity);
        this.modelVertexWriter.setLighting(lighting);
        LIGHTING = lighting;
        RenderState renderState = RenderStates.SCHEMATIC;
        GeometryData geometryData = Laby.references().modelUploader().model(model).modelVertexWriter(this.modelVertexWriter).upload(renderState);
        if (geometryData != null) {
            ShaderTextures.setShaderTexture(0, Textures.Splash.CAVE_ENTITIES);
            Mesh mesh = Mesh.create(this.laby3D.renderDevice(), () -> {
                return "Entity Model";
            }, geometryData);
            cmd.bindPipeline(renderState);
            cmd.bindTexture(0, ShaderTextures.getShaderTexture(0));
            cmd.bindUniformBlock(SchematicUniformBlock.NAME, this.laby3D.schematic());
            Objects.requireNonNull(mesh);
            cmd.addCleanupAction(mesh::close);
            cmd.draw(mesh);
        }
        LIGHTING = true;
    }

    public void renderUIInWorld(Stack stack, float left, float top, float right, float bottom, Runnable runnable) {
        SchematicRenderer schematicRenderer = getSchematicRenderer();
        if (schematicRenderer == null) {
            return;
        }
        CinematicCamera camera = schematicRenderer.camera();
        stack.push();
        Matrix4f modelViewMatrix = new Matrix4f();
        modelViewMatrix.translate(0.0f, 0.0f, MinecraftVersions.V1_12_2.orOlder() ? 2000.0f : CinematicCamera.getZLevel());
        modelViewMatrix.mul(camera.viewMatrix());
        stack.mul(modelViewMatrix);
        Location startPos = POS_OPENER_START;
        stack.translate((float) startPos.getX(), (float) startPos.getY(), (float) startPos.getZ());
        stack.rotate((float) (-startPos.getRoll()), 0.0f, 0.0f, 1.0f);
        stack.rotate((float) (-startPos.getPitch()), 1.0f, 0.0f, 0.0f);
        stack.rotate((float) (-startPos.getYaw()), 0.0f, 1.0f, 0.0f);
        float zOffset = bottom * 1.07f;
        float scale = 10.0f / zOffset;
        stack.scale(scale, scale, scale);
        stack.translate((-right) / 2.0f, (-bottom) / 2.0f, zOffset);
        runnable.run();
        stack.pop();
    }

    public void dispose() {
        if (this.renderer != null) {
            this.renderer.dispose();
        }
    }

    public void reset() {
        this.openingPlayed = false;
        this.timePassed = 0.0f;
        if (this.loaded) {
            this.entityAnimationController.stop();
            this.renderer.particleRenderer().clear();
            this.renderer.camera().teleport(POS_OPENER_START);
        }
    }

    @Nullable
    public SchematicRenderer getSchematicRenderer() {
        return this.renderer;
    }

    public boolean isOpeningPlayed() {
        return this.openingPlayed;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean isInTransition() {
        return !this.openingPlayed || getTransitionProgress() < 1.0f;
    }

    public float getTimePassed() {
        return this.timePassed;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public float getTransitionProgress() {
        return (float) MathHelper.clamp((Math.exp((this.timePassed / 1000.0f) - 4.0f) * 10.0d) - 0.20000000298023224d, 0.0d, 1.0d);
    }

    public void executeTransition(ResourceLocation identifier) {
        this.screenPositionRegistry.executeTransition(identifier);
    }

    public void executeTransition(PositionTransition transition) {
        this.screenPositionRegistry.executeTransition(transition);
    }

    private void onCrash(Throwable throwable) {
        this.crashed = true;
        LOGGER.error("Dynamic background crashed", throwable);
    }

    public static boolean isEnabled() {
        LabyAPI api = Laby.labyAPI();
        return (!api.config().appearance().dynamicBackground().enabled().get().booleanValue() || LabyMod.references().dynamicBackgroundController().crashed || api.themeService().currentTheme().getBackgroundRenderer() == null || api.minecraft().isIngame()) ? false : true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/DynamicBackgroundController$DefaultModelVertexWriter.class */
    private static class DefaultModelVertexWriter implements ModelUploader.ModelVertexWriter {
        private final Schematic schematic;
        private boolean lighting;
        private float opacity;

        private DefaultModelVertexWriter(Schematic schematic) {
            this.schematic = schematic;
        }

        @Override // net.labymod.api.client.render.model.ModelUploader.ModelVertexWriter
        public void write(VertexConsumer consumer, float x, float y, float z, float u, float v, int packedColor, float normalX, float normalY, float normalZ, float materialType, boolean glowing, long rainbowCycle) {
            consumer.addVertex(-x, -y, z).setUv(u, v);
            int posX = (int) (-x);
            int posY = (int) (-y);
            int posZ = (int) z;
            if (this.lighting) {
                float red = ((packedColor >> 16) & 255) / 255.0f;
                float green = ((packedColor >> 8) & 255) / 255.0f;
                float blue = (packedColor & 255) / 255.0f;
                LegacyLightEngine lightEngine = this.schematic.legacyLightEngine();
                consumer.setColor(red * lightEngine.getRedStrengthAt(posX, posY, posZ), green * lightEngine.getGreenStrengthAt(posX, posY, posZ), blue * lightEngine.getBlueStrengthAt(posX, posY, posZ), this.opacity);
            } else {
                consumer.setColor(16777215 | (((int) (this.opacity * 255.0f)) << 24));
            }
            consumer.setFloat(DynamicBackgroundController.LIGHTING ? 2.0f : 0.0f);
            consumer.setNormal(normalX, normalY, normalZ);
        }

        public void setLighting(boolean lighting) {
            this.lighting = lighting;
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
        }
    }
}
