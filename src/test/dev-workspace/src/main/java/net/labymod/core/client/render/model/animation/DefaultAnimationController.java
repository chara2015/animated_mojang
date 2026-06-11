package net.labymod.core.client.render.model.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.function.FloatSupplier;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.render.model.animation.molang.MolangEvaluationContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/DefaultAnimationController.class */
public class DefaultAnimationController implements AnimationController {
    public static final ModelPartAnimation.PartResolver SIMPLE_ANIMATION = (model, name, animation) -> {
        return model.getPart(name);
    };
    public static final ModelPartAnimation.PartResolver SHARED_MODEL_ANIMATION = (model, name, animation) -> {
        ModelPart modelPart = animation.getModelPart();
        if (modelPart == null) {
            modelPart = model.getPart(name);
            animation.setModelPart(modelPart);
        }
        return modelPart;
    };
    private final ModelPartAnimation.PartResolver partResolver;
    private final List<ModelAnimation> animationQueue;
    private Consumer<ModelAnimation> onStop;
    private Function<ModelPart, Boolean> hiddenPart;
    private ModelAnimation playing;
    private float speed;
    private float speedChangedProgress;
    private int maxQueueSize;
    private boolean animateStrict;
    private float startTick;
    private float lengthTicks;
    private FloatSupplier tickProvider;
    private AnimationController.AnimationApplier animationApplier;
    private AnimationController.PartTransformer partTransformer;
    private boolean positionEnabled;
    private boolean rotationEnabled;
    private boolean scaleEnabled;
    private final MolangEvaluationContext evaluationContext;

    public DefaultAnimationController() {
        this(SIMPLE_ANIMATION);
    }

    public DefaultAnimationController(ModelPartAnimation.PartResolver partResolver) {
        this.hiddenPart = (v0) -> {
            return v0.isInvisible();
        };
        this.partResolver = partResolver;
        this.animationQueue = new ArrayList();
        this.speed = 1.0f;
        this.maxQueueSize = 10;
        this.animateStrict = true;
        GameTickProvider gameTickProvider = Laby.labyAPI().renderPipeline().gameTickProvider();
        Objects.requireNonNull(gameTickProvider);
        this.tickProvider = gameTickProvider::get;
        this.animationApplier = new DefaultAnimationApplier();
        this.partTransformer = new AnimationController.PartTransformer(this) { // from class: net.labymod.core.client.render.model.animation.DefaultAnimationController.1
        };
        this.positionEnabled = true;
        this.rotationEnabled = true;
        this.scaleEnabled = true;
        this.evaluationContext = new MolangEvaluationContext();
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController playNext(@NotNull ModelAnimation defaultAnimation) {
        this.startTick = this.tickProvider.get();
        swap(this.animationQueue.isEmpty() ? defaultAnimation : (ModelAnimation) this.animationQueue.removeFirst());
        this.speedChangedProgress = 0.0f;
        float speed = 1.0f;
        boolean hasToReachNextInQueue = !this.animationQueue.isEmpty();
        if (hasToReachNextInQueue) {
            speed = ((Float) this.playing.getMetaDefault(AnimationMeta.SPEED, Float.valueOf(1.0f))).floatValue();
        }
        return speed(speed);
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController swap(@Nullable ModelAnimation animation) {
        this.playing = animation;
        this.lengthTicks = animation == null ? 0.0f : TimeUtil.convertMillisecondsToTicks(animation.getLength());
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController stop() {
        this.startTick = 0.0f;
        return swap(null);
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public boolean isPlaying() {
        if (this.playing == null) {
            return false;
        }
        float currentTick = this.tickProvider.get();
        if (this.startTick > currentTick) {
            return false;
        }
        if (getProgressTicks() > this.lengthTicks) {
            if (this.playing != null && this.onStop != null) {
                this.onStop.accept(this.playing);
            }
            stop();
            return false;
        }
        return true;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController queue(@NotNull ModelAnimation animation) {
        if (this.maxQueueSize != -1 && this.animationQueue.size() >= this.maxQueueSize) {
            return this;
        }
        this.animationQueue.add(animation);
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public boolean isQueued(@NotNull ModelAnimation animation) {
        return this.animationQueue.contains(animation);
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    @Nullable
    public ModelAnimation getPlaying() {
        if (isPlaying()) {
            return this.playing;
        }
        return null;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public float getSpeed() {
        return this.speed;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController speed(float speed) {
        this.speedChangedProgress = getProgressTicks();
        this.speed = speed;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public void clearQueue() {
        this.animationQueue.clear();
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController maxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public boolean isAnimateStrict() {
        return this.animateStrict;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController animateStrict(boolean animateStrict) {
        this.animateStrict = animateStrict;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController tickProvider(@NotNull FloatSupplier tickProvider) {
        this.tickProvider = tickProvider;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    @NotNull
    public AnimationController.AnimationApplier animationApplier() {
        return this.animationApplier;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController animationApplier(@NotNull AnimationController.AnimationApplier animationApplier) {
        this.animationApplier = animationApplier;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    @NotNull
    public AnimationController.PartTransformer partTransformer() {
        return this.partTransformer;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController partTransformer(@NotNull AnimationController.PartTransformer partTransformer) {
        this.partTransformer = partTransformer;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController enableTransformation(TransformationType... types) {
        for (TransformationType type : types) {
            switch (type) {
                case POSITION:
                    this.positionEnabled = true;
                    break;
                case ROTATION:
                    this.rotationEnabled = true;
                    break;
                case SCALE:
                    this.scaleEnabled = true;
                    break;
            }
        }
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController disableTransformation(TransformationType... types) {
        for (TransformationType type : types) {
            switch (type) {
                case POSITION:
                    this.positionEnabled = false;
                    break;
                case ROTATION:
                    this.rotationEnabled = false;
                    break;
                case SCALE:
                    this.scaleEnabled = false;
                    break;
            }
        }
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController applyAnimation(Model model, AnimationRenderState renderState, String... excludedParts) {
        ModelPart modelPart;
        if (!isPlaying()) {
            return this;
        }
        for (ModelAnimation.ModelPartAnimationDefinition definition : this.playing.getPartAnimations()) {
            String partName = this.partTransformer.remapName(model, definition.name());
            ModelPartAnimation partAnimation = definition.animation();
            if (!model.isInvalidPart(partName) && !CollectionHelper.contains(excludedParts, definition.name()) && (modelPart = this.partResolver.resolve(model, partName, partAnimation)) != null && !this.hiddenPart.apply(modelPart).booleanValue()) {
                applyPartAnimation(model, modelPart, partName, partAnimation, renderState);
            }
        }
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController transform(Stack stack, String partName) {
        FloatVector3 translation = getCurrentPosition(partName);
        if (this.positionEnabled && translation != null) {
            stack.translate(translation.getX() * 0.0625f, translation.getY() * 0.0625f, translation.getZ() * 0.0625f);
        }
        FloatVector3 rotation = getCurrentRotation(partName);
        if (this.rotationEnabled && rotation != null) {
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getX(), 1.0f, 0.0f, 0.0f);
        }
        FloatVector3 scale = getCurrentScale(partName);
        if (this.scaleEnabled && scale != null) {
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController hiddenPart(@NotNull Function<ModelPart, Boolean> hiddenPart) {
        this.hiddenPart = hiddenPart;
        return this;
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    @Nullable
    public FloatVector3 getCurrentPosition(@NotNull String partName) {
        if (!isPlaying() || !this.playing.hasPartAnimation(partName)) {
            return null;
        }
        return this.playing.getPartAnimation(partName).position().get(getProgress());
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    @Nullable
    public FloatVector3 getCurrentRotation(@NotNull String partName) {
        if (!isPlaying() || !this.playing.hasPartAnimation(partName)) {
            return null;
        }
        return this.playing.getPartAnimation(partName).rotation().get(getProgress());
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    @Nullable
    public FloatVector3 getCurrentScale(@NotNull String partName) {
        if (!isPlaying() || !this.playing.hasPartAnimation(partName)) {
            return null;
        }
        return this.playing.getPartAnimation(partName).scale().get(getProgress());
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public float getProgressTicks() {
        float progress = this.tickProvider.get() - this.startTick;
        if (this.speed == 1.0f) {
            return progress;
        }
        float speedProgress = progress - this.speedChangedProgress;
        return this.speedChangedProgress + (speedProgress * this.speed);
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public long getProgress() {
        return (long) TimeUtil.convertTicksToMilliseconds(getProgressTicks());
    }

    @Override // net.labymod.api.client.render.model.animation.AnimationController
    public AnimationController onStop(Consumer<ModelAnimation> onStop) {
        this.onStop = onStop;
        return this;
    }

    private void applyPartAnimation(Model model, ModelPart part, String partName, ModelPartAnimation animation, AnimationRenderState renderState) {
        this.partTransformer.preAnimationApply(model, partName, part, animation);
        ModelPartTransformation position = animation.position();
        ModelPartTransformation rotation = animation.rotation();
        ModelPartTransformation scale = animation.scale();
        long progress = getProgress();
        boolean needsContext = position.hasDynamicKeyframes() || rotation.hasDynamicKeyframes() || scale.hasDynamicKeyframes();
        if (needsContext) {
            this.evaluationContext.populate(renderState, progress);
        }
        if (this.positionEnabled && (this.animateStrict || position.hasKeyframe(progress))) {
            this.animationApplier.applyPosition(model, part, position.get(progress, this.evaluationContext), animation);
        }
        if (this.rotationEnabled && (this.animateStrict || rotation.hasKeyframe(progress))) {
            this.animationApplier.applyRotation(model, part, rotation.get(progress, this.evaluationContext), animation);
        }
        if (this.scaleEnabled && (this.animateStrict || scale.hasKeyframe(progress))) {
            this.animationApplier.applyScale(model, part, scale.get(progress, this.evaluationContext), animation);
        }
        this.partTransformer.postAnimationApply(model, partName, part, animation);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/DefaultAnimationController$DefaultAnimationApplier.class */
    public static class DefaultAnimationApplier implements AnimationController.AnimationApplier {
        @Override // net.labymod.api.client.render.model.animation.AnimationController.AnimationApplier
        public void applyPosition(Model model, ModelPart modelPart, FloatVector3 position, ModelPartAnimation animation) {
            modelPart.getAnimationTransformation().setTranslation(position);
        }

        @Override // net.labymod.api.client.render.model.animation.AnimationController.AnimationApplier
        public void applyRotation(Model model, ModelPart modelPart, FloatVector3 rotation, ModelPartAnimation animation) {
            modelPart.getAnimationTransformation().setRotation(rotation);
        }

        @Override // net.labymod.api.client.render.model.animation.AnimationController.AnimationApplier
        public void applyScale(Model model, ModelPart modelPart, FloatVector3 scale, ModelPartAnimation animation) {
            modelPart.getAnimationTransformation().setScale(scale);
        }
    }
}
