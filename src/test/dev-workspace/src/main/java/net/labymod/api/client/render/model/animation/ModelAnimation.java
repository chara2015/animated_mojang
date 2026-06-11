package net.labymod.api.client.render.model.animation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationCondition;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelAnimation.class */
public class ModelAnimation {
    private final String name;
    private final long length;
    private final List<ModelPartAnimationDefinition> partAnimations;
    private final List<AnimationMetaContainer> meta;

    public ModelAnimation(String name) {
        this(name, -1L);
    }

    public ModelAnimation(String name, long length) {
        this.name = name;
        this.length = length;
        this.partAnimations = new ArrayList();
        this.meta = new ArrayList();
    }

    public String getName() {
        return this.name;
    }

    public void addPartAnimation(String name, ModelPartAnimation partAnimation) {
        this.partAnimations.add(new ModelPartAnimationDefinition(name, partAnimation));
    }

    public ModelPartAnimation getPartAnimation(String name) {
        for (ModelPartAnimationDefinition animation : this.partAnimations) {
            if (Objects.equals(name, animation.name())) {
                return animation.animation();
            }
        }
        ModelPartAnimation animation2 = new ModelPartAnimation(this);
        addPartAnimation(name, animation2);
        return animation2;
    }

    public boolean hasPartAnimation(String name) {
        for (ModelPartAnimationDefinition definition : this.partAnimations) {
            if (Objects.equals(name, definition.name())) {
                return true;
            }
        }
        return false;
    }

    public List<ModelPartAnimationDefinition> getPartAnimations() {
        return this.partAnimations;
    }

    public <T> void addMeta(AnimationMeta<T> meta, T value) {
        this.meta.add(new AnimationMetaContainer(meta, value));
    }

    public <T> void removeMeta(AnimationMeta<T> meta) {
        this.meta.removeIf(container -> {
            return Objects.equals(meta, container.animationMeta());
        });
    }

    public <T> void parseAndAddMeta(AnimationMeta<T> meta, String value) {
        addMeta(meta, meta.getParser().apply(value));
    }

    public <T> T getMeta(AnimationMeta<T> animationMeta) {
        return (T) getMetaDefault(animationMeta, null);
    }

    public <T> T getMetaDefault(AnimationMeta<T> animationMeta, T t) {
        for (AnimationMetaContainer animationMetaContainer : this.meta) {
            if (Objects.equals(animationMeta, animationMetaContainer.animationMeta())) {
                return (T) animationMetaContainer.value();
            }
        }
        return t;
    }

    public boolean meetsConditions(Entity entity) {
        List<AnimationCondition> conditions = (List) getMeta(AnimationMeta.CONDITION);
        if (conditions == null || conditions.isEmpty()) {
            return true;
        }
        if (entity == null) {
            return false;
        }
        for (AnimationCondition condition : conditions) {
            if (condition.apply(entity)) {
                return false;
            }
        }
        return true;
    }

    public List<AnimationTrigger> getTriggers() {
        return (List) getMetaDefault(AnimationMeta.TRIGGER, Collections.emptyList());
    }

    public boolean hasTrigger(AnimationTrigger trigger) {
        return getTriggers().contains(trigger);
    }

    public long getLength() {
        long length = 0;
        for (ModelPartAnimationDefinition definition : this.partAnimations) {
            length = Math.max(length, definition.animation().getLength());
        }
        return Math.max(length, this.length);
    }

    public ModelAnimation copy() {
        return copy((partName, partAnimation) -> {
            return partAnimation;
        });
    }

    public ModelAnimation copy(BiFunction<String, ModelPartAnimation, ModelPartAnimation> transformer) {
        ModelAnimation copy = new ModelAnimation(this.name);
        for (ModelPartAnimationDefinition definition : this.partAnimations) {
            ModelPartAnimation transformed = transformer.apply(definition.name(), new ModelPartAnimation(copy, definition.animation().position().copy(), definition.animation().rotation().copy(), definition.animation().scale().copy()));
            if (transformed != null) {
                copy.addPartAnimation(definition.name(), transformed);
            }
        }
        for (AnimationMetaContainer meta : this.meta) {
            copy.addMeta(meta.animationMeta(), meta.value());
        }
        return copy;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition.class */
    public static final class ModelPartAnimationDefinition extends Record {
        private final String name;
        private final ModelPartAnimation animation;

        public ModelPartAnimationDefinition(String name, ModelPartAnimation animation) {
            this.name = name;
            this.animation = animation;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ModelPartAnimationDefinition.class), ModelPartAnimationDefinition.class, "name;animation", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition;->animation:Lnet/labymod/api/client/render/model/animation/ModelPartAnimation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ModelPartAnimationDefinition.class), ModelPartAnimationDefinition.class, "name;animation", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition;->animation:Lnet/labymod/api/client/render/model/animation/ModelPartAnimation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ModelPartAnimationDefinition.class, Object.class), ModelPartAnimationDefinition.class, "name;animation", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$ModelPartAnimationDefinition;->animation:Lnet/labymod/api/client/render/model/animation/ModelPartAnimation;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public ModelPartAnimation animation() {
            return this.animation;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer.class */
    public static final class AnimationMetaContainer extends Record {
        private final AnimationMeta<?> animationMeta;
        private final Object value;

        public AnimationMetaContainer(AnimationMeta<?> animationMeta, Object value) {
            this.animationMeta = animationMeta;
            this.value = value;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AnimationMetaContainer.class), AnimationMetaContainer.class, "animationMeta;value", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer;->animationMeta:Lnet/labymod/api/client/render/model/animation/meta/AnimationMeta;", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AnimationMetaContainer.class), AnimationMetaContainer.class, "animationMeta;value", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer;->animationMeta:Lnet/labymod/api/client/render/model/animation/meta/AnimationMeta;", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AnimationMetaContainer.class, Object.class), AnimationMetaContainer.class, "animationMeta;value", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer;->animationMeta:Lnet/labymod/api/client/render/model/animation/meta/AnimationMeta;", "FIELD:Lnet/labymod/api/client/render/model/animation/ModelAnimation$AnimationMetaContainer;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public AnimationMeta<?> animationMeta() {
            return this.animationMeta;
        }

        public Object value() {
            return this.value;
        }
    }
}
