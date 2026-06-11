package net.labymod.api.client.render.model.animation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelPartAnimation.class */
public class ModelPartAnimation {
    private final ModelAnimation parent;
    private final ModelPartTransformation position;
    private final ModelPartTransformation rotation;
    private final ModelPartTransformation scale;
    private final Map<TransformationType, ModelPartTransformation> transformations;
    private PartResolver resolver;
    private boolean resolved;

    @Nullable
    private ModelPart modelPart;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelPartAnimation$PartResolver.class */
    @FunctionalInterface
    public interface PartResolver {
        ModelPart resolve(Model model, String str, ModelPartAnimation modelPartAnimation);
    }

    public ModelPartAnimation(ModelAnimation parent, ModelPartTransformation position, ModelPartTransformation rotation, ModelPartTransformation scale) {
        this.parent = parent;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.transformations = new HashMap();
        this.transformations.put(TransformationType.POSITION, position);
        this.transformations.put(TransformationType.ROTATION, rotation);
        this.transformations.put(TransformationType.SCALE, scale);
    }

    public ModelPartAnimation(ModelAnimation parent) {
        this(parent, new ModelPartTransformation(TransformationType.POSITION, 0.0f, 0.0f, 0.0f), new ModelPartTransformation(TransformationType.ROTATION, 0.0f, 0.0f, 0.0f), new ModelPartTransformation(TransformationType.SCALE, 1.0f, 1.0f, 1.0f));
    }

    public ModelAnimation parent() {
        return this.parent;
    }

    public ModelPartTransformation position() {
        return this.position;
    }

    public ModelPartTransformation rotation() {
        return this.rotation;
    }

    public ModelPartTransformation scale() {
        return this.scale;
    }

    public ModelPartAnimation addKeyframes(long timestamp, boolean smooth, FloatVector3 position, FloatVector3 rotation, FloatVector3 scale, Collection<TransformationType> disabledTransformations) {
        if (!disabledTransformations.contains(this.position.type())) {
            this.position.addKeyframe(new ModelPartTransformation.Keyframe(timestamp, smooth, position));
        }
        if (!disabledTransformations.contains(this.rotation.type())) {
            this.rotation.addKeyframe(new ModelPartTransformation.Keyframe(timestamp, smooth, rotation));
        }
        if (!disabledTransformations.contains(this.scale.type())) {
            this.scale.addKeyframe(new ModelPartTransformation.Keyframe(timestamp, smooth, scale));
        }
        return this;
    }

    public void filterKeyframes(BiPredicate<ModelPartTransformation, ModelPartTransformation.Keyframe> predicate) {
        this.position.filterKeyframes(keyframe -> {
            return predicate.test(this.position, keyframe);
        });
        this.rotation.filterKeyframes(keyframe2 -> {
            return predicate.test(this.rotation, keyframe2);
        });
        this.scale.filterKeyframes(keyframe3 -> {
            return predicate.test(this.scale, keyframe3);
        });
    }

    public long getLength() {
        return Math.max(Math.max(this.position.getLength(), this.rotation.getLength()), this.scale.getLength());
    }

    public ModelPartTransformation transformationByType(TransformationType type) {
        return this.transformations.get(type);
    }

    public void setResolver(PartResolver resolver) {
        if (this.resolver == null || this.resolver != resolver) {
            this.modelPart = null;
            this.resolved = false;
        }
        this.resolver = resolver;
    }

    @Nullable
    public ModelPart getModelPart() {
        return this.modelPart;
    }

    public void setModelPart(@Nullable ModelPart modelPart) {
        this.modelPart = modelPart;
    }
}
