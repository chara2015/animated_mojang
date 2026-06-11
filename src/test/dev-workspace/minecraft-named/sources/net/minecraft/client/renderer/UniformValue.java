package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector3ic;
import org.joml.Vector4fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue.class */
public interface UniformValue {
    public static final Codec<UniformValue> CODEC = Type.CODEC.dispatch((v0) -> {
        return v0.type();
    }, $$0 -> {
        return $$0.valueCodec;
    });

    void writeTo(Std140Builder std140Builder);

    void addSize(Std140SizeCalculator std140SizeCalculator);

    Type type();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$Type.class */
    public enum Type implements StringRepresentable {
        INT("int", IntUniform.CODEC),
        IVEC3("ivec3", IVec3Uniform.CODEC),
        FLOAT("float", FloatUniform.CODEC),
        VEC2("vec2", Vec2Uniform.CODEC),
        VEC3("vec3", Vec3Uniform.CODEC),
        VEC4("vec4", Vec4Uniform.CODEC),
        MATRIX4X4("matrix4x4", Matrix4x4Uniform.CODEC);

        public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private final String name;
        final MapCodec<? extends UniformValue> valueCodec;

        Type(String $$0, Codec codec) {
            this.name = $$0;
            this.valueCodec = codec.fieldOf("value");
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$IntUniform.class */
    public static final class IntUniform extends Record implements UniformValue {
        private final int value;
        public static final Codec<IntUniform> CODEC = Codec.INT.xmap((v1) -> {
            return new IntUniform(v1);
        }, (v0) -> {
            return v0.value();
        });

        public IntUniform(int $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IntUniform.class), IntUniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$IntUniform;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IntUniform.class), IntUniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$IntUniform;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IntUniform.class, Object.class), IntUniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$IntUniform;->value:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putInt(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putInt();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.INT;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$IVec3Uniform.class */
    public static final class IVec3Uniform extends Record implements UniformValue {
        private final Vector3ic value;
        public static final Codec<IVec3Uniform> CODEC = ExtraCodecs.VECTOR3I.xmap(IVec3Uniform::new, (v0) -> {
            return v0.value();
        });

        public IVec3Uniform(Vector3ic $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IVec3Uniform.class), IVec3Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$IVec3Uniform;->value:Lorg/joml/Vector3ic;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IVec3Uniform.class), IVec3Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$IVec3Uniform;->value:Lorg/joml/Vector3ic;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IVec3Uniform.class, Object.class), IVec3Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$IVec3Uniform;->value:Lorg/joml/Vector3ic;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Vector3ic value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putIVec3(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putIVec3();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.IVEC3;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$FloatUniform.class */
    public static final class FloatUniform extends Record implements UniformValue {
        private final float value;
        public static final Codec<FloatUniform> CODEC = Codec.FLOAT.xmap((v1) -> {
            return new FloatUniform(v1);
        }, (v0) -> {
            return v0.value();
        });

        public FloatUniform(float $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FloatUniform.class), FloatUniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$FloatUniform;->value:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FloatUniform.class), FloatUniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$FloatUniform;->value:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FloatUniform.class, Object.class), FloatUniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$FloatUniform;->value:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putFloat(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putFloat();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.FLOAT;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$Vec2Uniform.class */
    public static final class Vec2Uniform extends Record implements UniformValue {
        private final Vector2fc value;
        public static final Codec<Vec2Uniform> CODEC = ExtraCodecs.VECTOR2F.xmap(Vec2Uniform::new, (v0) -> {
            return v0.value();
        });

        public Vec2Uniform(Vector2fc $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Vec2Uniform.class), Vec2Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec2Uniform;->value:Lorg/joml/Vector2fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Vec2Uniform.class), Vec2Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec2Uniform;->value:Lorg/joml/Vector2fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Vec2Uniform.class, Object.class), Vec2Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec2Uniform;->value:Lorg/joml/Vector2fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Vector2fc value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putVec2(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putVec2();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.VEC2;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$Vec3Uniform.class */
    public static final class Vec3Uniform extends Record implements UniformValue {
        private final Vector3fc value;
        public static final Codec<Vec3Uniform> CODEC = ExtraCodecs.VECTOR3F.xmap(Vec3Uniform::new, (v0) -> {
            return v0.value();
        });

        public Vec3Uniform(Vector3fc $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Vec3Uniform.class), Vec3Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec3Uniform;->value:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Vec3Uniform.class), Vec3Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec3Uniform;->value:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Vec3Uniform.class, Object.class), Vec3Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec3Uniform;->value:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Vector3fc value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putVec3(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putVec3();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.VEC3;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$Vec4Uniform.class */
    public static final class Vec4Uniform extends Record implements UniformValue {
        private final Vector4fc value;
        public static final Codec<Vec4Uniform> CODEC = ExtraCodecs.VECTOR4F.xmap(Vec4Uniform::new, (v0) -> {
            return v0.value();
        });

        public Vec4Uniform(Vector4fc $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Vec4Uniform.class), Vec4Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec4Uniform;->value:Lorg/joml/Vector4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Vec4Uniform.class), Vec4Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec4Uniform;->value:Lorg/joml/Vector4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Vec4Uniform.class, Object.class), Vec4Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Vec4Uniform;->value:Lorg/joml/Vector4fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Vector4fc value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putVec4(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putVec4();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.VEC4;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/UniformValue$Matrix4x4Uniform.class */
    public static final class Matrix4x4Uniform extends Record implements UniformValue {
        private final Matrix4fc value;
        public static final Codec<Matrix4x4Uniform> CODEC = ExtraCodecs.MATRIX4F.xmap(Matrix4x4Uniform::new, (v0) -> {
            return v0.value();
        });

        public Matrix4x4Uniform(Matrix4fc $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Matrix4x4Uniform.class), Matrix4x4Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Matrix4x4Uniform;->value:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Matrix4x4Uniform.class), Matrix4x4Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Matrix4x4Uniform;->value:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Matrix4x4Uniform.class, Object.class), Matrix4x4Uniform.class, "value", "FIELD:Lnet/minecraft/client/renderer/UniformValue$Matrix4x4Uniform;->value:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4fc value() {
            return this.value;
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void writeTo(Std140Builder $$0) {
            $$0.putMat4f(this.value);
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public void addSize(Std140SizeCalculator $$0) {
            $$0.putMat4f();
        }

        @Override // net.minecraft.client.renderer.UniformValue
        public Type type() {
            return Type.MATRIX4X4;
        }
    }
}
