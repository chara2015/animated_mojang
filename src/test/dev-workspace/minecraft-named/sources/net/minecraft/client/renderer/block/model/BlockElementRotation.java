package net.minecraft.client.renderer.block.model;

import com.mojang.math.MatrixUtil;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Direction;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementRotation.class */
public final class BlockElementRotation extends Record {
    private final Vector3fc origin;
    private final RotationValue value;
    private final boolean rescale;
    private final Matrix4fc transform;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementRotation$RotationValue.class */
    public interface RotationValue {
        Matrix4f transformation();
    }

    public BlockElementRotation(Vector3fc $$0, RotationValue $$1, boolean $$2, Matrix4fc $$3) {
        this.origin = $$0;
        this.value = $$1;
        this.rescale = $$2;
        this.transform = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockElementRotation.class), BlockElementRotation.class, "origin;value;rescale;transform", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->origin:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->value:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$RotationValue;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->rescale:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->transform:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockElementRotation.class), BlockElementRotation.class, "origin;value;rescale;transform", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->origin:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->value:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$RotationValue;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->rescale:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->transform:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockElementRotation.class, Object.class), BlockElementRotation.class, "origin;value;rescale;transform", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->origin:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->value:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$RotationValue;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->rescale:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;->transform:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vector3fc origin() {
        return this.origin;
    }

    public RotationValue value() {
        return this.value;
    }

    public boolean rescale() {
        return this.rescale;
    }

    public Matrix4fc transform() {
        return this.transform;
    }

    public BlockElementRotation(Vector3fc $$0, RotationValue $$1, boolean $$2) {
        this($$0, $$1, $$2, computeTransform($$1, $$2));
    }

    private static Matrix4f computeTransform(RotationValue $$0, boolean $$1) {
        Matrix4f $$2 = $$0.transformation();
        if ($$1 && !MatrixUtil.isIdentity($$2)) {
            Vector3fc $$3 = computeRescale($$2);
            $$2.scale($$3);
        }
        return $$2;
    }

    private static Vector3fc computeRescale(Matrix4fc $$0) {
        Vector3f $$1 = new Vector3f();
        float $$2 = scaleFactorForAxis($$0, Direction.Axis.X, $$1);
        float $$3 = scaleFactorForAxis($$0, Direction.Axis.Y, $$1);
        float $$4 = scaleFactorForAxis($$0, Direction.Axis.Z, $$1);
        return $$1.set($$2, $$3, $$4);
    }

    private static float scaleFactorForAxis(Matrix4fc $$0, Direction.Axis $$1, Vector3f $$2) {
        Vector3f $$3 = $$2.set($$1.getPositive().getUnitVec3f());
        Vector3f $$4 = $$0.transformDirection($$3);
        float $$5 = Math.abs($$4.x);
        float $$6 = Math.abs($$4.y);
        float $$7 = Math.abs($$4.z);
        float $$8 = Math.max(Math.max($$5, $$6), $$7);
        return 1.0f / $$8;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation.class */
    public static final class SingleAxisRotation extends Record implements RotationValue {
        private final Direction.Axis axis;
        private final float angle;

        public SingleAxisRotation(Direction.Axis $$0, float $$1) {
            this.axis = $$0;
            this.angle = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SingleAxisRotation.class), SingleAxisRotation.class, "axis;angle", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation;->axis:Lnet/minecraft/core/Direction$Axis;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation;->angle:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SingleAxisRotation.class), SingleAxisRotation.class, "axis;angle", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation;->axis:Lnet/minecraft/core/Direction$Axis;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation;->angle:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SingleAxisRotation.class, Object.class), SingleAxisRotation.class, "axis;angle", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation;->axis:Lnet/minecraft/core/Direction$Axis;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$SingleAxisRotation;->angle:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Direction.Axis axis() {
            return this.axis;
        }

        public float angle() {
            return this.angle;
        }

        @Override // net.minecraft.client.renderer.block.model.BlockElementRotation.RotationValue
        public Matrix4f transformation() {
            Matrix4f $$0 = new Matrix4f();
            if (this.angle == 0.0f) {
                return $$0;
            }
            Vector3fc $$1 = this.axis.getPositive().getUnitVec3f();
            $$0.rotation(this.angle * 0.017453292f, $$1);
            return $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation.class */
    public static final class EulerXYZRotation extends Record implements RotationValue {
        private final float x;
        private final float y;
        private final float z;

        public EulerXYZRotation(float $$0, float $$1, float $$2) {
            this.x = $$0;
            this.y = $$1;
            this.z = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EulerXYZRotation.class), EulerXYZRotation.class, "x;y;z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->x:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->y:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->z:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EulerXYZRotation.class), EulerXYZRotation.class, "x;y;z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->x:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->y:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->z:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EulerXYZRotation.class, Object.class), EulerXYZRotation.class, "x;y;z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->x:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->y:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementRotation$EulerXYZRotation;->z:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float x() {
            return this.x;
        }

        public float y() {
            return this.y;
        }

        public float z() {
            return this.z;
        }

        @Override // net.minecraft.client.renderer.block.model.BlockElementRotation.RotationValue
        public Matrix4f transformation() {
            return new Matrix4f().rotationZYX(this.z * 0.017453292f, this.y * 0.017453292f, this.x * 0.017453292f);
        }
    }
}
