package net.labymod.api.util.math;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.util.debug.DebugNameable;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/Transformation.class */
public class Transformation implements DebugNameable {
    private static final float WORLD_UNIT = 16.0f;
    private static final Transformation[] EMPTY_TRANSFORMATION = new Transformation[0];
    private final FloatVector3 translation;
    private final FloatVector3 rotation;
    private final FloatVector3 scale;
    private final Matrix4f cachedMatrix;

    @Nullable
    private String debugName;
    private boolean dirty;

    public Transformation() {
        this(new FloatVector3(), new FloatVector3(), new FloatVector3(1.0f, 1.0f, 1.0f));
    }

    public Transformation(FloatVector3 translation, FloatVector3 rotation, FloatVector3 scale) {
        this.cachedMatrix = new Matrix4f();
        this.dirty = true;
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static Transformation decompose(Matrix4f matrix) {
        Vector3f outTranslation = new Vector3f();
        Vector3f outScale = new Vector3f();
        matrix.getTranslation(outTranslation);
        matrix.getScale(outScale);
        Quaternionf q = matrix.getNormalizedRotation(new Quaternionf());
        Vector3f euler = q.getEulerAnglesZYX(new Vector3f());
        outTranslation.mul(WORLD_UNIT);
        return new Transformation(new FloatVector3(outTranslation.x(), outTranslation.y(), outTranslation.z()), new FloatVector3(euler.x(), euler.y(), euler.z()), new FloatVector3(outScale.x(), outScale.y(), outScale.z()));
    }

    public static Transformation combine(Transformation left, Transformation right) {
        Matrix4f result = JomlMath.extractMatrix(left.toMatrix()).mul(right.toMatrix());
        return decompose(result);
    }

    public void setTranslation(float x, float y, float z) {
        this.translation.set(x, y, z);
        this.dirty = true;
    }

    public void setTranslation(FloatVector3 vector) {
        this.translation.set(vector);
        this.dirty = true;
    }

    public void addTranslation(float x, float y, float z) {
        this.translation.add(x, y, z);
        this.dirty = true;
    }

    public void addTranslation(FloatVector3 vector) {
        this.translation.add(vector);
        this.dirty = true;
    }

    public void setScale(float scale) {
        this.scale.set(scale, scale, scale);
        this.dirty = true;
    }

    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
        this.dirty = true;
    }

    public void setScale(FloatVector3 vector) {
        this.scale.set(vector);
        this.dirty = true;
    }

    public void setRotation(FloatVector3 rotation) {
        this.rotation.set(rotation);
        this.dirty = true;
    }

    public void setRotationX(float x) {
        this.rotation.setX(x);
        this.dirty = true;
    }

    public void setRotationY(float y) {
        this.rotation.setY(y);
        this.dirty = true;
    }

    public void setRotationZ(float z) {
        this.rotation.setZ(z);
        this.dirty = true;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
        this.dirty = true;
    }

    public void addRotation(float x, float y, float z) {
        this.rotation.add(x, y, z);
        this.dirty = true;
    }

    public void addRotation(FloatVector3 rotation) {
        this.rotation.add(rotation);
        this.dirty = true;
    }

    public void set(Transformation transformation) {
        this.translation.set(transformation.translation);
        this.rotation.set(transformation.rotation);
        this.scale.set(transformation.scale);
        this.dirty = true;
    }

    public void transform(Stack stack) {
        transform(stack, EMPTY_TRANSFORMATION);
    }

    public void transform(Stack stack, Transformation transformation) {
        transform(stack, transformation);
    }

    public void transform(Stack stack, Transformation... transformations) {
        translate(stack, this);
        for (Transformation transformation : transformations) {
            translate(stack, transformation);
        }
        rotateZ(stack, this);
        for (Transformation transformation2 : transformations) {
            rotateZ(stack, transformation2);
        }
        rotateY(stack, this);
        for (Transformation transformation3 : transformations) {
            rotateY(stack, transformation3);
        }
        rotateX(stack, this);
        for (Transformation transformation4 : transformations) {
            rotateX(stack, transformation4);
        }
        scale(stack, this);
        for (Transformation transformation5 : transformations) {
            scale(stack, transformation5);
        }
    }

    private void translate(Stack stack, Transformation transformation) {
        FloatVector3 translation = transformation.getTranslation();
        stack.translate(translation.getX() * 0.0625f, translation.getY() * 0.0625f, translation.getZ() * 0.0625f);
    }

    private void rotateZ(Stack stack, Transformation transformation) {
        FloatVector3 rotation = transformation.getRotation();
        float z = rotation.getZ();
        if (z == 0.0f) {
            return;
        }
        stack.rotateRadians(z, 0.0f, 0.0f, 1.0f);
    }

    private void rotateY(Stack stack, Transformation transformation) {
        FloatVector3 rotation = transformation.getRotation();
        float y = rotation.getY();
        if (y == 0.0f) {
            return;
        }
        stack.rotateRadians(y, 0.0f, 1.0f, 0.0f);
    }

    private void rotateX(Stack stack, Transformation transformation) {
        FloatVector3 rotation = transformation.getRotation();
        float x = rotation.getX();
        if (x == 0.0f) {
            return;
        }
        stack.rotateRadians(x, 1.0f, 0.0f, 0.0f);
    }

    private void scale(Stack stack, Transformation transformation) {
        FloatVector3 scale = transformation.getScale();
        stack.scale(scale.getX(), scale.getY(), scale.getZ());
    }

    public FloatVector3 getTranslation() {
        return this.translation;
    }

    public FloatVector3 getScale() {
        return this.scale;
    }

    public FloatVector3 getRotation() {
        return this.rotation;
    }

    public void reset() {
        setTranslation(0.0f, 0.0f, 0.0f);
        setRotation(0.0f, 0.0f, 0.0f);
        setScale(1.0f);
    }

    private void translateAndRotate(Stack stack) {
        FloatVector3 translation = getTranslation();
        stack.translate(translation.getX() * 0.0625f, translation.getY() * 0.0625f, translation.getZ() * 0.0625f);
        FloatVector3 rotation = getRotation();
        if (rotation.getZ() != 0.0f) {
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
        }
        if (rotation.getY() != 0.0f) {
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
        }
        if (rotation.getX() != 0.0f) {
            stack.rotateRadians(rotation.getX(), 1.0f, 0.0f, 0.0f);
        }
        FloatVector3 scale = getScale();
        if (scale.getX() != 1.0f || scale.getY() != 1.0f || scale.getZ() != 1.0f) {
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
    }

    public Matrix4f toMatrix() {
        if (this.dirty) {
            this.cachedMatrix.identity().translate(this.translation.getX() * 0.0625f, this.translation.getY() * 0.0625f, this.translation.getZ() * 0.0625f).rotateZ(this.rotation.getZ()).rotateY(this.rotation.getY()).rotateX(this.rotation.getX()).scale(this.scale.getX(), this.scale.getY(), this.scale.getZ());
            this.dirty = false;
        }
        return this.cachedMatrix;
    }

    @Override // net.labymod.api.util.debug.DebugNameable
    public void setDebugName(String name) {
        this.debugName = name;
    }

    @Override // net.labymod.api.util.debug.DebugNameable
    public String getDebugName() {
        return this.debugName;
    }
}
