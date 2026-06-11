package com.mojang.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/math/Transformation.class */
public final class Transformation {
    private final Matrix4fc matrix;
    private boolean decomposed;
    private Vector3fc translation;
    private Quaternionfc leftRotation;
    private Vector3fc scale;
    private Quaternionfc rightRotation;
    public static final Codec<Transformation> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.VECTOR3F.fieldOf("translation").forGetter($$0 -> {
            return $$0.translation;
        }), ExtraCodecs.QUATERNIONF.fieldOf("left_rotation").forGetter($$02 -> {
            return $$02.leftRotation;
        }), ExtraCodecs.VECTOR3F.fieldOf("scale").forGetter($$03 -> {
            return $$03.scale;
        }), ExtraCodecs.QUATERNIONF.fieldOf("right_rotation").forGetter($$04 -> {
            return $$04.rightRotation;
        })).apply($$0, Transformation::new);
    });
    public static final Codec<Transformation> EXTENDED_CODEC = Codec.withAlternative(CODEC, ExtraCodecs.MATRIX4F.xmap(Transformation::new, (v0) -> {
        return v0.getMatrix();
    }));
    private static final Transformation IDENTITY = (Transformation) Util.make(() -> {
        Transformation $$0 = new Transformation(new Matrix4f());
        $$0.translation = new Vector3f();
        $$0.leftRotation = new Quaternionf();
        $$0.scale = new Vector3f(1.0f, 1.0f, 1.0f);
        $$0.rightRotation = new Quaternionf();
        $$0.decomposed = true;
        return $$0;
    });

    public Transformation(Matrix4fc $$0) {
        if ($$0 == null) {
            this.matrix = new Matrix4f();
        } else {
            this.matrix = $$0;
        }
    }

    public Transformation(Vector3fc $$0, Quaternionfc $$1, Vector3fc $$2, Quaternionfc $$3) {
        this.matrix = compose($$0, $$1, $$2, $$3);
        this.translation = $$0 != null ? $$0 : new Vector3f();
        this.leftRotation = $$1 != null ? $$1 : new Quaternionf();
        this.scale = $$2 != null ? $$2 : new Vector3f(1.0f, 1.0f, 1.0f);
        this.rightRotation = $$3 != null ? $$3 : new Quaternionf();
        this.decomposed = true;
    }

    public static Transformation identity() {
        return IDENTITY;
    }

    public Transformation compose(Transformation $$0) {
        Matrix4f $$1 = getMatrixCopy();
        $$1.mul($$0.getMatrix());
        return new Transformation($$1);
    }

    public Transformation inverse() {
        if (this == IDENTITY) {
            return this;
        }
        Matrix4f $$0 = getMatrixCopy().invertAffine();
        if ($$0.isFinite()) {
            return new Transformation($$0);
        }
        return null;
    }

    private void ensureDecomposed() {
        if (!this.decomposed) {
            float $$0 = 1.0f / this.matrix.m33();
            Triple<Quaternionf, Vector3f, Quaternionf> $$1 = MatrixUtil.svdDecompose(new Matrix3f(this.matrix).scale($$0));
            this.translation = this.matrix.getTranslation(new Vector3f()).mul($$0);
            this.leftRotation = new Quaternionf((Quaternionfc) $$1.getLeft());
            this.scale = new Vector3f((Vector3fc) $$1.getMiddle());
            this.rightRotation = new Quaternionf((Quaternionfc) $$1.getRight());
            this.decomposed = true;
        }
    }

    private static Matrix4f compose(Vector3fc $$0, Quaternionfc $$1, Vector3fc $$2, Quaternionfc $$3) {
        Matrix4f $$4 = new Matrix4f();
        if ($$0 != null) {
            $$4.translation($$0);
        }
        if ($$1 != null) {
            $$4.rotate($$1);
        }
        if ($$2 != null) {
            $$4.scale($$2);
        }
        if ($$3 != null) {
            $$4.rotate($$3);
        }
        return $$4;
    }

    public Matrix4fc getMatrix() {
        return this.matrix;
    }

    public Matrix4f getMatrixCopy() {
        return new Matrix4f(this.matrix);
    }

    public Vector3fc getTranslation() {
        ensureDecomposed();
        return this.translation;
    }

    public Quaternionfc getLeftRotation() {
        ensureDecomposed();
        return this.leftRotation;
    }

    public Vector3fc getScale() {
        ensureDecomposed();
        return this.scale;
    }

    public Quaternionfc getRightRotation() {
        ensureDecomposed();
        return this.rightRotation;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        Transformation $$1 = (Transformation) $$0;
        return Objects.equals(this.matrix, $$1.matrix);
    }

    public int hashCode() {
        return Objects.hash(this.matrix);
    }

    public Transformation slerp(Transformation $$0, float $$1) {
        return new Transformation(getTranslation().lerp($$0.getTranslation(), $$1, new Vector3f()), getLeftRotation().slerp($$0.getLeftRotation(), $$1, new Quaternionf()), getScale().lerp($$0.getScale(), $$1, new Vector3f()), getRightRotation().slerp($$0.getRightRotation(), $$1, new Quaternionf()));
    }
}
