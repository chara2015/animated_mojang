package com.mojang.blaze3d.audio;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/ListenerTransform.class */
public final class ListenerTransform extends Record {
    private final Vec3 position;
    private final Vec3 forward;
    private final Vec3 up;
    public static final ListenerTransform INITIAL = new ListenerTransform(Vec3.ZERO, new Vec3(Density.SURFACE, Density.SURFACE, -1.0d), new Vec3(Density.SURFACE, 1.0d, Density.SURFACE));

    public ListenerTransform(Vec3 $$0, Vec3 $$1, Vec3 $$2) {
        this.position = $$0;
        this.forward = $$1;
        this.up = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ListenerTransform.class), ListenerTransform.class, "position;forward;up", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->position:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->forward:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->up:Lnet/minecraft/world/phys/Vec3;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ListenerTransform.class), ListenerTransform.class, "position;forward;up", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->position:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->forward:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->up:Lnet/minecraft/world/phys/Vec3;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ListenerTransform.class, Object.class), ListenerTransform.class, "position;forward;up", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->position:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->forward:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lcom/mojang/blaze3d/audio/ListenerTransform;->up:Lnet/minecraft/world/phys/Vec3;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vec3 position() {
        return this.position;
    }

    public Vec3 forward() {
        return this.forward;
    }

    public Vec3 up() {
        return this.up;
    }

    public Vec3 right() {
        return this.forward.cross(this.up);
    }
}
