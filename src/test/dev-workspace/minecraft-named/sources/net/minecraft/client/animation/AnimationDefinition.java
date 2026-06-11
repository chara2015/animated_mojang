package net.minecraft.client.animation;

import com.google.common.collect.Maps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/animation/AnimationDefinition.class */
public final class AnimationDefinition extends Record {
    private final float lengthInSeconds;
    private final boolean looping;
    private final Map<String, List<AnimationChannel>> boneAnimations;

    public AnimationDefinition(float $$0, boolean $$1, Map<String, List<AnimationChannel>> $$2) {
        this.lengthInSeconds = $$0;
        this.looping = $$1;
        this.boneAnimations = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AnimationDefinition.class), AnimationDefinition.class, "lengthInSeconds;looping;boneAnimations", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->lengthInSeconds:F", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->looping:Z", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->boneAnimations:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AnimationDefinition.class), AnimationDefinition.class, "lengthInSeconds;looping;boneAnimations", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->lengthInSeconds:F", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->looping:Z", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->boneAnimations:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AnimationDefinition.class, Object.class), AnimationDefinition.class, "lengthInSeconds;looping;boneAnimations", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->lengthInSeconds:F", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->looping:Z", "FIELD:Lnet/minecraft/client/animation/AnimationDefinition;->boneAnimations:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float lengthInSeconds() {
        return this.lengthInSeconds;
    }

    public boolean looping() {
        return this.looping;
    }

    public Map<String, List<AnimationChannel>> boneAnimations() {
        return this.boneAnimations;
    }

    public KeyframeAnimation bake(ModelPart $$0) {
        return KeyframeAnimation.bake($$0, this);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/animation/AnimationDefinition$Builder.class */
    public static class Builder {
        private final float length;
        private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
        private boolean looping;

        public static Builder withLength(float $$0) {
            return new Builder($$0);
        }

        private Builder(float $$0) {
            this.length = $$0;
        }

        public Builder looping() {
            this.looping = true;
            return this;
        }

        public Builder addAnimation(String $$0, AnimationChannel $$1) {
            this.animationByBone.computeIfAbsent($$0, $$02 -> {
                return new ArrayList();
            }).add($$1);
            return this;
        }

        public AnimationDefinition build() {
            return new AnimationDefinition(this.length, this.looping, this.animationByBone);
        }
    }
}
