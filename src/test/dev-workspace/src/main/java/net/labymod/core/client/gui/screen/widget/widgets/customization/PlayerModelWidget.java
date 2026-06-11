package net.labymod.core.client.gui.screen.widget.widgets.customization;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.mojang.texture.SkinPolicy;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.widget.widgets.model.EmoteModelWidget;
import net.labymod.core.client.render.model.DefaultModel;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.core.client.render.schematic.SchematicRenderer;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/customization/PlayerModelWidget.class */
@AutoWidget
public class PlayerModelWidget extends EmoteModelWidget {
    private final Matrix4f modelMatrix;
    private Skin previewedSkin;
    private float maxTranslationX;
    private float maxTranslationY;
    private float maxScale;
    private boolean scrolling;
    private float clickX;
    private long timeLastRendered;

    public PlayerModelWidget() {
        super(ResourceLocation.create("labymod", "player_model"), new DefaultModel(), new DefaultAnimationController(), 16.0f, 32.0f, null, true);
        this.modelMatrix = new Matrix4f();
        this.maxTranslationX = 0.0f;
        this.maxTranslationY = 0.0f;
        this.maxScale = 1.0f;
        this.scrolling = true;
        this.animationController.onStop(animation -> {
            this.model.reset();
        });
        DynamicBackgroundController dynamicBackgroundController = LabyMod.references().dynamicBackgroundController();
        SchematicRenderer renderer = dynamicBackgroundController.getSchematicRenderer();
        if (renderer != null) {
            renderer.registerRenderHook((cmd, stack, left, top, right, bottom, partialTicks) -> {
                if (!isWorldRenderingSupported()) {
                    return;
                }
                renderInWorld(cmd, stack, partialTicks);
            });
        }
    }

    public void setRenderCosmetics(boolean renderCosmetics) {
        this.cosmetics = renderCosmetics;
    }

    public boolean isRenderingCosmetics() {
        return this.cosmetics;
    }

    public void stopEmote(EmoteItem condition) {
        if (condition != null && condition != this.emoteItem) {
            return;
        }
        super.stopEmote();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.emoteItem != null && !this.animationController.isPlaying()) {
            this.animationController.playNext(this.emoteItem.getStartAnimation());
        }
    }

    public void updateSkinTextureFrom(UUID uniqueId) {
        MojangTextureService textureService = Laby.references().mojangTextureService();
        MojangModelService modelService = Laby.references().mojangModelService();
        textureService.getTexture(uniqueId, MojangTextureType.SKIN, texture -> {
            MinecraftServices.SkinVariant variant = textureService.getVariant(texture);
            Model model = modelService.getPlayerModel(variant);
            setModelAndTexture(model, texture);
        });
        this.previewedSkin = null;
    }

    public boolean isModified() {
        return this.previewedSkin != null;
    }

    public void updateSkinTexture(Skin skin) {
        this.previewedSkin = skin;
        String imageHash = skin.getImageHash();
        CompletableResourceLocation cachedResourceLocation = PlayerActivity.SKIN_CACHE.get(imageHash);
        if (cachedResourceLocation != null) {
            updateSkinTexture(cachedResourceLocation);
        } else {
            Request.ofInputStream().url(skin.getDownloadUrl(), new Object[0]).async().execute(response -> {
                if (!response.isPresent()) {
                    this.previewedSkin = null;
                    return;
                }
                InputStream inputStream = ((WebInputStream) response.get()).getInputStream();
                try {
                    GameImage gameImage = Laby.references().gameImageProvider().getImage(inputStream);
                    GameImage gameImage2 = SkinPolicy.applyPolicy(gameImage, true);
                    ResourceLocation resourceLocation = ResourceLocation.create("labymod", "skin/" + imageHash);
                    resourceLocation.metadata().set("variant", skin.skinVariant().getId());
                    CompletableResourceLocation completable = new CompletableResourceLocation(resourceLocation);
                    gameImage2.uploadTextureAt(resourceLocation);
                    this.labyAPI.minecraft().executeOnRenderThread(() -> {
                        PlayerActivity.SKIN_CACHE.put(imageHash, completable);
                        updateSkinTexture(completable);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void updateSkinTexture(CompletableResourceLocation resourceLocation) {
        MojangModelService modelService = Laby.references().mojangModelService();
        MojangTextureService textureService = Laby.references().mojangTextureService();
        ResourceLocation completedLocation = resourceLocation.getCompleted();
        setModelAndTexture(modelService.getPlayerModel(textureService.getVariant(completedLocation)), completedLocation);
        if (this.previewedSkin == null) {
            this.previewedSkin = new Skin();
        }
        resourceLocation.addCompletableListener(() -> {
            ResourceLocation location = resourceLocation.getCompleted();
            setModelAndTexture(modelService.getPlayerModel(textureService.getVariant(location)), location);
        });
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.model.EmoteModelWidget, net.labymod.core.client.gui.screen.widget.widgets.model.CosmeticModelWidget, net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    public void setModel(Model model) {
        super.setModel(model);
        update();
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public void setMaxTranslation(float maxTranslationX, float maxTranslationY) {
        this.maxTranslationX = maxTranslationX;
        this.maxTranslationY = maxTranslationY;
    }

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
    }

    public void update() {
        boolean enabled;
        MinecraftOptions options = this.labyAPI.minecraft().options();
        int value = options.getModelParts();
        boolean changedLayer = false;
        for (SkinLayer layer : SkinLayer.layers()) {
            ModelPart part = this.model.getPart(layer.getResourceId());
            if (part != null && (enabled = layer.isEnabled(value)) != part.isVisible()) {
                part.setVisible(enabled);
                changedLayer = true;
            }
        }
        if (changedLayer) {
            rebuildModel();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.ModelWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (isDragging()) {
            this.rotation.set(0.0f, (this.clickX - context.mouse().getX()) / 50.0f, 0.0f);
        }
        if (!DynamicBackgroundController.isEnabled() || !isWorldRenderingSupported()) {
            super.renderWidget(context);
        }
        if (isVisible()) {
            this.timeLastRendered = TimeUtil.getMillis();
        }
    }

    private void renderInWorld(CommandBuffer cmd, Stack stack, float partialTicks) {
        RenderPipeline legacyPipeline = Laby.labyAPI().renderPipeline();
        GFXRenderPipeline pipeline = Laby.labyAPI().gfxRenderPipeline();
        this.modelMatrix.identity();
        RenderEnvironmentContext environmentContext = pipeline.renderEnvironmentContext();
        DynamicBackgroundController controller = LabyMod.references().dynamicBackgroundController();
        Location entityPosition = DynamicBackgroundController.POS_PLAYER_ENTITY;
        long timePassedSinceLastRender = TimeUtil.getMillis() - this.timeLastRendered;
        long timePassedSinceLastInitialize = TimeUtil.getMillis() - getLastInitialTime();
        float focusYaw = (float) (((double) this.rotation.getY()) % 6.283185307179586d);
        float opacity = MathHelper.clamp(timePassedSinceLastInitialize / 300.0f, 0.0f, 1.0f) - MathHelper.clamp((timePassedSinceLastRender - 100) / 300.0f, 0.0f, 1.0f);
        if (opacity <= 0.0f) {
            return;
        }
        this.modelMatrix.rotateZYX((float) MathHelper.toRadiansDouble(entityPosition.getPitch()), ((float) MathHelper.toRadiansDouble(entityPosition.getYaw())) - focusYaw, (float) MathHelper.toRadiansDouble(entityPosition.getRoll()));
        this.modelMatrix.translate((float) entityPosition.getX(), (float) entityPosition.getY(), (float) entityPosition.getZ());
        float scaleOffset = 1.0f - this.scale.getX();
        CinematicCamera camera = controller.getSchematicRenderer().camera();
        Location modifier = camera.positionModifier(1);
        int windowHeight = this.labyAPI.minecraft().minecraftWindow().getScaledHeight();
        float windowScaleOffset = (1.8f - (windowHeight / 250.0f)) / 2.0f;
        modifier.setX(((-scaleOffset) * (-0.6f)) + (this.translation.getX() / 13.0f) + windowScaleOffset);
        modifier.setY((-this.translation.getY()) / (this.translation.getY() < 0.0f ? 20.0f : 13.0f));
        modifier.setZ((-scaleOffset) * 1.4f);
        modifier.setPitch(-this.translation.getY());
        modifier.setYaw(0.0d);
        modifier.setRoll(0.0d);
        this.animationController.applyAnimation(this.model, new String[0]);
        controller.renderModel(cmd, this.model, opacity, false);
        stack.push();
        stack.identity();
        stack.mul(camera.viewMatrix());
        stack.mul(this.modelMatrix);
        stack.scale(-1.0f, -1.0f, 1.0f);
        boolean prevScreenContext = environmentContext.isScreenContext();
        environmentContext.setScreenContext(false);
        legacyPipeline.setAlpha(opacity);
        setForceProjectionMatrix(false);
        renderModelAttachments(cmd, stack, RenderEnvironmentContext.FULL_BRIGHT, partialTicks);
        Laby.fireEvent(new RenderWorldEvent(stack, Phase.POST, camera, partialTicks));
        environmentContext.setScreenContext(prevScreenContext);
        legacyPipeline.setAlpha(1.0f);
        stack.pop();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        this.clickX = (int) (mouse.getXDouble() + ((double) (this.rotation.getY() * 50.0f)));
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (this.scrolling) {
            float delta = ((float) scrollDelta) / 15.0f;
            float scale = MathHelper.clamp(this.scale.getX() + delta, 1.0f, this.maxScale);
            this.scale.set(scale, scale, scale);
            float progress = this.maxScale == 1.0f ? 0.0f : (scale - 1.0f) / (this.maxScale - 1.0f);
            this.translation.setX(this.maxTranslationX * progress);
            this.translation.setY(this.maxTranslationY * progress);
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }

    protected void lookAt(float posX, float posY) {
        Bounds bounds = bounds();
        float centerX = bounds.getCenterX();
        float centerY = bounds.getTop() + 25.0f;
        float x = centerX - posX;
        float y = centerY - posY;
        double rotationX = 3.141592653589793d - Math.abs(((double) MathHelper.wrapRadians(this.rotation.getX())) - 3.141592653589793d);
        double rotationY = 3.141592653589793d - Math.abs(((double) MathHelper.wrapRadians(this.rotation.getY())) - 3.141592653589793d);
        double rotationZ = 3.141592653589793d - Math.abs(((double) MathHelper.wrapRadians(this.rotation.getZ())) - 3.141592653589793d);
        float length = ((float) ((rotationX * rotationX) + (rotationY * rotationY) + (rotationZ * rotationZ))) * 20.0f;
        float x2 = x / (length + 1.0f);
        float y2 = y / (length + 1.0f);
        Transformation body = this.model.getPart(HumanoidModel.BODY_NAME).getAnimationTransformation();
        body.setRotationY(MathHelper.toRadiansFloat((float) (Math.atan(x2 / 40.0f) * 20.0d)));
        body.setRotationX(MathHelper.toRadiansFloat((-((float) Math.atan(y2 / 40.0f))) * 2.0f));
        Transformation head = this.model.getPart(HumanoidModel.HEAD_NAME).getAnimationTransformation();
        head.setRotationX(MathHelper.toRadiansFloat((-((float) Math.atan(y2 / 40.0f))) * 20.0f));
        head.setRotationY(MathHelper.toRadiansFloat(((float) Math.atan(x2 / 40.0f)) * 40.0f));
        float timeAlive = TimeUtil.convertMillisecondsToTicks(TimeUtil.getMillis() - getLastInitialTime());
        Transformation rightArm = this.model.getPart(HumanoidModel.RIGHT_ARM_NAME).getAnimationTransformation();
        rightArm.setRotationX((float) (Math.sin(((double) timeAlive) * 0.067d) * 0.05d));
        rightArm.setRotationZ((float) ((Math.cos(((double) timeAlive) * 0.09d) * 0.05d) + 0.05d));
        Transformation leftArm = this.model.getPart(HumanoidModel.LEFT_ARM_NAME).getAnimationTransformation();
        leftArm.setRotationX((float) ((-Math.sin(((double) timeAlive) * 0.067d)) * 0.05d));
        leftArm.setRotationZ((float) (-((Math.cos(((double) timeAlive) * 0.09d) * 0.05d) + 0.05d)));
    }

    public Skin getPreviewedSkin() {
        return this.previewedSkin;
    }

    public boolean isWorldRenderingSupported() {
        return false;
    }
}
