package net.labymod.core.main.user.shop.emote;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.laby.lib.cosmetics.AttachmentPoint;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/EmoteRenderer.class */
@Singleton
@Referenceable
public class EmoteRenderer {
    private static final float MODEL_ROTATION_SHIFT = 0.4f;
    private static final float SNEAK_OFFSET = 0.125f;
    private static final float LEGACY_SNEAK_OFFSET = 0.2f;
    private static final float LEGACY_LEG_SNEAK_OFFSET = 0.1875f;
    private static final float BODY_HEIGHT = 1.5f;
    private static final float TRANSLUCENT_ALPHA = 0.15f;
    private static final String CHEST_PART = "chest";
    private static final Map<String, String> EMOTE_RETARGET_MAP = Map.of(CHEST_PART, HumanoidModel.BODY_NAME, HumanoidModel.HEAD_NAME, HumanoidModel.HEAD_NAME, HumanoidModel.LEFT_ARM_NAME, HumanoidModel.LEFT_ARM_NAME, HumanoidModel.RIGHT_ARM_NAME, HumanoidModel.RIGHT_ARM_NAME, HumanoidModel.LEFT_LEG_NAME, HumanoidModel.LEFT_LEG_NAME, HumanoidModel.RIGHT_LEG_NAME, HumanoidModel.RIGHT_LEG_NAME);
    private final Minecraft minecraft;
    private final RenderConstants renderConstants;
    private final GeometrySubmitter geometrySubmitter;
    private EmoteAnimationStorage animationStorage;
    private Player player;
    private HumanoidModel model;
    private boolean firstPerson;

    @Inject
    public EmoteRenderer(LabyAPI labyAPI, RenderConstants renderConstants, GeometrySubmitter geometrySubmitter) {
        this.minecraft = labyAPI.minecraft();
        this.renderConstants = renderConstants;
        this.geometrySubmitter = geometrySubmitter;
    }

    public EmoteRenderer animationStorage(EmoteAnimationStorage animationStorage) {
        this.animationStorage = animationStorage;
        return this;
    }

    public EmoteRenderer player(Player player) {
        this.player = player;
        return this;
    }

    public EmoteRenderer model(HumanoidModel model) {
        this.model = model;
        return this;
    }

    public EmoteRenderer firstPerson(boolean firstPerson) {
        this.firstPerson = firstPerson;
        return this;
    }

    public void transformBody(Stack stack) {
        AnimationController animationController = this.animationStorage.animationController();
        if (this.player.isCrouching() && ((Boolean) getMeta(EmoteAnimationMeta.BLOCK_SNEAKING, false)).booleanValue()) {
            stack.translate(0.0f, -(PlatformEnvironment.isAncientOpenGL() ? 0.2f : SNEAK_OFFSET), 0.0f);
        }
        animationController.disableTransformation(TransformationType.ROTATION, TransformationType.SCALE).transform(stack, HumanoidModel.BODY_NAME).enableTransformation(TransformationType.ROTATION, TransformationType.SCALE);
        stack.translate(0.0f, 0.4f, 0.0f);
        FloatVector3 rotation = animationController.getCurrentRotation(HumanoidModel.BODY_NAME);
        if (rotation != null) {
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getX(), 1.0f, 0.0f, 0.0f);
        }
        FloatVector3 scale = animationController.getCurrentScale(HumanoidModel.BODY_NAME);
        if (scale != null) {
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        stack.translate(0.0f, -0.4f, 0.0f);
    }

    public void transformCamera(Stack stack, boolean headAnimation) {
        float playerRotationYaw = this.player.getRotationYaw();
        stack.rotate(-playerRotationYaw, 0.0f, 1.0f, 0.0f);
        AnimationController animationController = this.animationStorage.animationController();
        animationController.disableTransformation(TransformationType.ROTATION, TransformationType.SCALE).transform(stack, HumanoidModel.BODY_NAME).enableTransformation(TransformationType.ROTATION, TransformationType.SCALE);
        stack.translate(0.0f, this.renderConstants.cameraMovementScale(), 0.0f);
        FloatVector3 rotation = animationController.getCurrentRotation(HumanoidModel.BODY_NAME);
        if (rotation != null) {
            stack.rotateRadians(rotation.getX(), -1.0f, 0.0f, 0.0f);
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
        }
        stack.translate(0.0f, -this.renderConstants.cameraMovementScale(), 0.0f);
        if (headAnimation && !((Boolean) getMeta(EmoteAnimationMeta.BLOCK_HEAD_ANIMATION, false)).booleanValue()) {
            animationController.disableTransformation(TransformationType.ROTATION, TransformationType.SCALE).transform(stack, HumanoidModel.HEAD_NAME).enableTransformation(TransformationType.ROTATION, TransformationType.SCALE);
            FloatVector3 headRotation = animationController.getCurrentRotation(HumanoidModel.HEAD_NAME);
            if (headRotation != null) {
                stack.rotateRadians(headRotation.getX(), -1.0f, 0.0f, 0.0f);
                stack.rotateRadians(headRotation.getY(), 0.0f, 1.0f, 0.0f);
                stack.rotateRadians(headRotation.getZ(), 0.0f, 0.0f, 1.0f);
            }
        }
        stack.rotate(playerRotationYaw, 0.0f, 1.0f, 0.0f);
        this.minecraft.requestChunkUpdate();
    }

    public void transformModel() {
        Model steveModel;
        AnimationController animationController = this.animationStorage.animationController();
        if (this.firstPerson) {
            animationController.disableTransformation(TransformationType.POSITION);
        }
        Model model = this.model;
        if (this.animationStorage.hasPlayerModel()) {
            if (this.player.isSlim()) {
                steveModel = this.animationStorage.getAlexModel();
            } else {
                steveModel = this.animationStorage.getSteveModel();
            }
            model = steveModel;
        }
        animationController.animateStrict(false).applyAnimation(model, HumanoidModel.BODY_NAME).animateStrict(true).enableTransformation(TransformationType.POSITION);
        if (model != this.model) {
            EmoteRetargeter.retarget(model, this.model, EMOTE_RETARGET_MAP);
        }
        this.model.copyToSecondLayer();
    }

    public void animateProps() {
        Model propsModel = this.animationStorage.getPropsModel();
        this.animationStorage.animationController().applyAnimation(propsModel, new String[0]);
    }

    public void renderProps(Stack stack, int lightCoords) {
        ModelPart rightLeg;
        float f;
        ModelBuffer propsModelBuffer = this.animationStorage.getPropsModelBuffer();
        EmoteItem emote = this.animationStorage.getEmote();
        emote.updateAnimatedTexture(propsModelBuffer);
        stack.push();
        if (this.player.isCrouching()) {
            if (emote.getAttachedTo() == AttachmentPoint.NONE || ((Boolean) getMeta(EmoteAnimationMeta.BLOCK_SNEAKING, false)).booleanValue()) {
                if (!PlatformEnvironment.isAncientOpenGL()) {
                    stack.translate(0.0f, -0.125f, 0.0f);
                }
            } else {
                if (PlatformEnvironment.isAncientOpenGL()) {
                    if (emote.getAttachedTo() == AttachmentPoint.LEG) {
                        f = LEGACY_LEG_SNEAK_OFFSET;
                    } else {
                        f = 0.2f;
                    }
                    stack.translate(0.0f, f, 0.0f);
                }
                switch (AnonymousClass1.$SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[emote.getAttachedTo().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        rightLeg = this.model.getBody();
                        break;
                    case 4:
                        rightLeg = this.model.getHead();
                        break;
                    case 5:
                        rightLeg = this.model.getRightArm();
                        break;
                    case 6:
                        rightLeg = this.model.getRightLeg();
                        break;
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
                ModelPart attachedPart = rightLeg;
                stack.translate(0.0f, attachedPart.getAnimationTransformation().getTranslation().getY() * 0.0625f, 0.0f);
            }
        }
        Laby.references().renderEnvironmentContext().setPackedLight(lightCoords);
        this.geometrySubmitter.submitModel(EmoteRenderState.OPAQUE, new EmoteModelInstance(this.animationStorage.getPropsModel()), stack, RenderStates.TRANSLUCENT_EMOTES, TextureBindingSet.builder().setTexture(0, propsModelBuffer.getResourceLocation()).build(), lightCoords, RenderEnvironmentContext.NO_OVERLAY);
        stack.pop();
    }

    /* JADX INFO: renamed from: net.labymod.core.main.user.shop.emote.EmoteRenderer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/EmoteRenderer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint = new int[AttachmentPoint.values().length];

        static {
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.BODY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.HEAD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.ARM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.LEG.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public void renderPlayerModel(Stack stack, int lightCoords) {
        Model steveModel;
        if (this.player.isSlim()) {
            steveModel = this.animationStorage.getAlexModel();
        } else {
            steveModel = this.animationStorage.getSteveModel();
        }
        Model playerModel = steveModel;
        this.animationStorage.animationController().animateStrict(false).applyAnimation(playerModel, HumanoidModel.BODY_NAME).animateStrict(true);
        stack.push();
        stack.translate(0.0f, BODY_HEIGHT, 0.0f);
        if (PlatformEnvironment.isAncientOpenGL() && this.player.isCrouching()) {
            stack.translate(0.0f, 0.2f, 0.0f);
        }
        boolean translucent = this.player.isInvisible() && !this.player.isInvisibleFor(this.minecraft.getClientPlayer());
        Laby.references().renderEnvironmentContext().setPackedLight(lightCoords);
        this.geometrySubmitter.submitModel(translucent ? new EmoteRenderState(TRANSLUCENT_ALPHA) : EmoteRenderState.OPAQUE, new EmoteModelInstance(playerModel), stack, RenderStates.TRANSLUCENT_EMOTES, TextureBindingSet.builder().setTexture(0, this.player.skinTexture()).build(), lightCoords, RenderEnvironmentContext.NO_OVERLAY);
        stack.pop();
    }

    private <T> T getMeta(AnimationMeta<T> animationMeta, T t) {
        ModelAnimation playing = this.animationStorage.animationController().getPlaying();
        return playing == null ? t : (T) playing.getMetaDefault(animationMeta, t);
    }

    private void copyState(Model playerModel) {
        String key;
        for (Map.Entry<String, ModelPart> entry : this.model.getParts().entrySet()) {
            ModelPart originalPart = entry.getValue();
            if (entry.getKey().equals(HumanoidModel.BODY_NAME)) {
                key = CHEST_PART;
            } else {
                key = entry.getKey();
            }
            ModelPart part = playerModel.getPart(key);
            if (part != null) {
                part.getAnimationTransformation().set(originalPart.getAnimationTransformation());
                part.setVisible(originalPart.isVisible());
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/EmoteRenderer$EmoteModelInstance.class */
    private static class EmoteModelInstance extends ModelInstance<EmoteRenderState> {
        private final RenderPipeline renderPipeline;
        private float prevAlpha;

        public EmoteModelInstance(Model model) {
            super(model);
            this.renderPipeline = Laby.references().renderPipeline();
        }

        @Override // net.labymod.api.laby3d.model.ModelInstance
        public void update(EmoteRenderState emoteRenderState) {
            super.update(emoteRenderState);
            this.prevAlpha = this.renderPipeline.getAlpha();
            this.renderPipeline.setAlpha(emoteRenderState.alpha());
        }

        @Override // net.labymod.api.laby3d.model.ModelInstance
        public void resetPose() {
            this.renderPipeline.setAlpha(this.prevAlpha);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/EmoteRenderer$EmoteRenderState.class */
    public static final class EmoteRenderState extends Record {
        private final float alpha;
        public static final EmoteRenderState OPAQUE = new EmoteRenderState(1.0f);

        public EmoteRenderState(float alpha) {
            this.alpha = alpha;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EmoteRenderState.class), EmoteRenderState.class, "alpha", "FIELD:Lnet/labymod/core/main/user/shop/emote/EmoteRenderer$EmoteRenderState;->alpha:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EmoteRenderState.class), EmoteRenderState.class, "alpha", "FIELD:Lnet/labymod/core/main/user/shop/emote/EmoteRenderer$EmoteRenderState;->alpha:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EmoteRenderState.class, Object.class), EmoteRenderState.class, "alpha", "FIELD:Lnet/labymod/core/main/user/shop/emote/EmoteRenderer$EmoteRenderState;->alpha:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public float alpha() {
            return this.alpha;
        }
    }
}
