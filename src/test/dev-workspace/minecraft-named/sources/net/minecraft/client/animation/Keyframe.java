package net.minecraft.client.animation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.animation.AnimationChannel;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/animation/Keyframe.class */
public final class Keyframe extends Record {
    private final float timestamp;
    private final Vector3fc preTarget;
    private final Vector3fc postTarget;
    private final AnimationChannel.Interpolation interpolation;

    public Keyframe(float $$0, Vector3fc $$1, Vector3fc $$2, AnimationChannel.Interpolation $$3) {
        this.timestamp = $$0;
        this.preTarget = $$1;
        this.postTarget = $$2;
        this.interpolation = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Keyframe.class), Keyframe.class, "timestamp;preTarget;postTarget;interpolation", "FIELD:Lnet/minecraft/client/animation/Keyframe;->timestamp:F", "FIELD:Lnet/minecraft/client/animation/Keyframe;->preTarget:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/animation/Keyframe;->postTarget:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/animation/Keyframe;->interpolation:Lnet/minecraft/client/animation/AnimationChannel$Interpolation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Keyframe.class), Keyframe.class, "timestamp;preTarget;postTarget;interpolation", "FIELD:Lnet/minecraft/client/animation/Keyframe;->timestamp:F", "FIELD:Lnet/minecraft/client/animation/Keyframe;->preTarget:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/animation/Keyframe;->postTarget:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/animation/Keyframe;->interpolation:Lnet/minecraft/client/animation/AnimationChannel$Interpolation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Keyframe.class, Object.class), Keyframe.class, "timestamp;preTarget;postTarget;interpolation", "FIELD:Lnet/minecraft/client/animation/Keyframe;->timestamp:F", "FIELD:Lnet/minecraft/client/animation/Keyframe;->preTarget:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/animation/Keyframe;->postTarget:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/animation/Keyframe;->interpolation:Lnet/minecraft/client/animation/AnimationChannel$Interpolation;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float timestamp() {
        return this.timestamp;
    }

    public Vector3fc preTarget() {
        return this.preTarget;
    }

    public Vector3fc postTarget() {
        return this.postTarget;
    }

    public AnimationChannel.Interpolation interpolation() {
        return this.interpolation;
    }

    public Keyframe(float $$0, Vector3fc $$1, AnimationChannel.Interpolation $$2) {
        this($$0, $$1, $$1, $$2);
    }
}
