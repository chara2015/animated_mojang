package net.labymod.api.client.render.matrix;

import net.labymod.api.laby3d.math.JomlMath;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/Pose.class */
public class Pose {
    private final Matrix4f pose;
    private final Matrix3f normal;
    private boolean trustedNormals;

    public Pose() {
        this(new Matrix4f(), new Matrix3f());
    }

    public Pose(Matrix4f pose, Matrix3f normal) {
        this.trustedNormals = true;
        this.pose = pose;
        this.normal = normal;
    }

    public Matrix4f pose() {
        return this.pose;
    }

    public Matrix3f normal() {
        return this.normal;
    }

    public Vector3f transformNormal(Vector3fc sourceNormal, Vector3f dest) {
        return transformNormal(sourceNormal.x(), sourceNormal.y(), sourceNormal.z(), dest);
    }

    public Vector3f transformNormal(float x, float y, float z, Vector3f dest) {
        Vector3f position = this.normal.transform(x, y, z, dest);
        return this.trustedNormals ? position : position.normalize();
    }

    public void translate(float x, float y, float z) {
        this.pose.translate(x, y, z);
    }

    public void scale(float x, float y, float z) {
        this.pose.scale(x, y, z);
        if (Math.abs(x) != Math.abs(y) || Math.abs(y) != Math.abs(z)) {
            this.normal.scale(1.0f / x, 1.0f / y, 1.0f / z);
            this.trustedNormals = false;
        } else if (x < 0.0f || y < 0.0f || z < 0.0f) {
            this.normal.scale(Math.signum(x), Math.signum(y), Math.signum(z));
        }
    }

    public Pose rotate(Quaternionfc quaternion) {
        this.pose.rotate(quaternion);
        this.normal.rotate(quaternion);
        return this;
    }

    public Pose rotate(float rad, float x, float y, float z) {
        Vector3f axis = new Vector3f(x, y, z).normalize();
        this.pose.rotate(rad, axis);
        this.normal.rotate(rad, axis);
        return this;
    }

    public Pose rotateAround(Quaternionfc quaternion, float x, float y, float z) {
        this.pose.rotateAround(quaternion, x, y, z);
        this.normal.rotate(quaternion);
        return this;
    }

    public void set(Pose other) {
        this.pose.set(other.pose);
        this.normal.set(other.normal);
        this.trustedNormals = other.trustedNormals;
    }

    public Pose identity() {
        this.pose.identity();
        this.normal.identity();
        this.trustedNormals = true;
        return this;
    }

    public void mul(Matrix4fc matrix) {
        this.pose.mul(matrix);
        if (JomlMath.isPureTranslation(matrix)) {
            return;
        }
        if (JomlMath.isOrthonormal(matrix)) {
            this.normal.mul(new Matrix3f(matrix));
        } else {
            computeNormalMatrix();
        }
    }

    public Pose copy() {
        Pose pose = new Pose();
        pose.set(this);
        return pose;
    }

    private void computeNormalMatrix() {
        this.normal.set(this.pose).invert().transpose();
        this.trustedNormals = false;
    }
}
