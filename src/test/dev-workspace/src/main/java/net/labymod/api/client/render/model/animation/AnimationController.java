package net.labymod.api.client.render.model.animation;

import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.util.function.FloatSupplier;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/AnimationController.class */
public interface AnimationController {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/AnimationController$AnimationApplier.class */
    public interface AnimationApplier {
        void applyPosition(Model model, ModelPart modelPart, FloatVector3 floatVector3, ModelPartAnimation modelPartAnimation);

        void applyRotation(Model model, ModelPart modelPart, FloatVector3 floatVector3, ModelPartAnimation modelPartAnimation);

        void applyScale(Model model, ModelPart modelPart, FloatVector3 floatVector3, ModelPartAnimation modelPartAnimation);
    }

    AnimationController playNext(@NotNull ModelAnimation modelAnimation);

    AnimationController swap(@Nullable ModelAnimation modelAnimation);

    AnimationController stop();

    boolean isPlaying();

    AnimationController queue(@NotNull ModelAnimation modelAnimation);

    boolean isQueued(@NotNull ModelAnimation modelAnimation);

    @Nullable
    ModelAnimation getPlaying();

    float getSpeed();

    AnimationController speed(float f);

    int getMaxQueueSize();

    void clearQueue();

    AnimationController maxQueueSize(int i);

    boolean isAnimateStrict();

    AnimationController animateStrict(boolean z);

    AnimationController tickProvider(@NotNull FloatSupplier floatSupplier);

    @NotNull
    AnimationApplier animationApplier();

    AnimationController animationApplier(@NotNull AnimationApplier animationApplier);

    @NotNull
    PartTransformer partTransformer();

    AnimationController partTransformer(@NotNull PartTransformer partTransformer);

    AnimationController enableTransformation(TransformationType... transformationTypeArr);

    AnimationController disableTransformation(TransformationType... transformationTypeArr);

    AnimationController applyAnimation(Model model, AnimationRenderState animationRenderState, String... strArr);

    AnimationController transform(Stack stack, String str);

    AnimationController hiddenPart(@NotNull Function<ModelPart, Boolean> function);

    @Nullable
    FloatVector3 getCurrentPosition(@NotNull String str);

    @Nullable
    FloatVector3 getCurrentRotation(@NotNull String str);

    @Nullable
    FloatVector3 getCurrentScale(@NotNull String str);

    float getProgressTicks();

    long getProgress();

    AnimationController onStop(Consumer<ModelAnimation> consumer);

    default AnimationController applyAnimation(Model model, String... excludedParts) {
        return applyAnimation(model, AnimationRenderState.empty(), excludedParts);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/AnimationController$PartTransformer.class */
    public interface PartTransformer {
        default String remapName(Model model, String partName) {
            return partName;
        }

        default void preAnimationApply(Model model, String partName, ModelPart modelPart, ModelPartAnimation animation) {
        }

        default void postAnimationApply(Model model, String partName, ModelPart modelPart, ModelPartAnimation animation) {
        }
    }
}
