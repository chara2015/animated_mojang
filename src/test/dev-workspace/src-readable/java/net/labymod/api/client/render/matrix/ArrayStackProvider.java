package net.labymod.api.client.render.matrix;

import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/ArrayStackProvider.class */
public class ArrayStackProvider implements StackProvider {
    private final Pose[] matrices;
    private Pose currentEntry;
    private int currentIndex;

    public ArrayStackProvider() {
        this(32);
    }

    public ArrayStackProvider(int size) {
        if (size < 1) {
            throw new IllegalStateException("size must be >= 1");
        }
        this.matrices = new Pose[size + 1];
        for (int index = 0; index < this.matrices.length; index++) {
            this.matrices[index] = new Pose();
        }
        this.currentEntry = last();
        push();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        Pose currentEntry = this.currentEntry;
        currentEntry.translate(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        MathHelper.rotateDegrees(this.currentEntry, angle, x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        MathHelper.rotateRadians(this.currentEntry, radians, x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        Pose currentEntry = this.currentEntry;
        currentEntry.scale(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        this.currentEntry.mul(matrix);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        if (this.currentIndex == this.matrices.length - 2) {
            throw new IllegalStateException("The maximum stack size of " + this.matrices.length + " has been reached");
        }
        Pose[] poseArr = this.matrices;
        int i = this.currentIndex + 1;
        this.currentIndex = i;
        Pose newEntry = poseArr[i];
        newEntry.set(this.currentEntry);
        this.currentEntry = newEntry;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        if (this.currentIndex <= 0) {
            IdeUtil.doPauseOrThrown(() -> {
                return new IllegalStateException("Already at the bottom of the stack");
            });
        }
        Pose[] poseArr = this.matrices;
        int i = this.currentIndex;
        this.currentIndex = i - 1;
        poseArr[i].identity();
        this.currentEntry = last();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        last().identity();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Vector3f transformNormal(Vector3fc sourceNormal, Vector3f dest) {
        return last().transformNormal(sourceNormal, dest);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Vector3f transformNormal(float x, float y, float z, Vector3f dest) {
        return last().transformNormal(x, y, z, dest);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix4f getPose() {
        return this.currentEntry.pose();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix3f getNormal() {
        return this.currentEntry.normal();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    @Nullable
    public Object getPoseStack() {
        return null;
    }

    private Pose last() {
        return this.matrices[this.currentIndex];
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public int index() {
        return this.currentIndex;
    }
}
