package com.mojang.blaze3d.vertex;

import com.mojang.math.MatrixUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/PoseStack.class */
public class PoseStack {
    private final List<Pose> poses = new ArrayList(16);
    private int lastIndex;

    public PoseStack() {
        this.poses.add(new Pose());
    }

    public void translate(double $$0, double $$1, double $$2) {
        translate((float) $$0, (float) $$1, (float) $$2);
    }

    public void translate(float $$0, float $$1, float $$2) {
        last().translate($$0, $$1, $$2);
    }

    public void translate(Vec3 $$0) {
        translate($$0.x, $$0.y, $$0.z);
    }

    public void scale(float $$0, float $$1, float $$2) {
        last().scale($$0, $$1, $$2);
    }

    public void mulPose(Quaternionfc $$0) {
        last().rotate($$0);
    }

    public void rotateAround(Quaternionfc $$0, float $$1, float $$2, float $$3) {
        last().rotateAround($$0, $$1, $$2, $$3);
    }

    public void pushPose() {
        Pose $$0 = last();
        this.lastIndex++;
        if (this.lastIndex >= this.poses.size()) {
            this.poses.add($$0.copy());
        } else {
            this.poses.get(this.lastIndex).set($$0);
        }
    }

    public void popPose() {
        if (this.lastIndex == 0) {
            throw new NoSuchElementException();
        }
        this.lastIndex--;
    }

    public Pose last() {
        return this.poses.get(this.lastIndex);
    }

    public boolean isEmpty() {
        return this.lastIndex == 0;
    }

    public void setIdentity() {
        last().setIdentity();
    }

    public void mulPose(Matrix4fc $$0) {
        last().mulPose($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/PoseStack$Pose.class */
    public static final class Pose {
        private final Matrix4f pose = new Matrix4f();
        private final Matrix3f normal = new Matrix3f();
        private boolean trustedNormals = true;

        private void computeNormalMatrix() {
            this.normal.set(this.pose).invert().transpose();
            this.trustedNormals = false;
        }

        public void set(Pose $$0) {
            this.pose.set($$0.pose);
            this.normal.set($$0.normal);
            this.trustedNormals = $$0.trustedNormals;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public Matrix3f normal() {
            return this.normal;
        }

        public Vector3f transformNormal(Vector3fc $$0, Vector3f $$1) {
            return transformNormal($$0.x(), $$0.y(), $$0.z(), $$1);
        }

        public Vector3f transformNormal(float $$0, float $$1, float $$2, Vector3f $$3) {
            Vector3f $$4 = this.normal.transform($$0, $$1, $$2, $$3);
            return this.trustedNormals ? $$4 : $$4.normalize();
        }

        public Matrix4f translate(float $$0, float $$1, float $$2) {
            return this.pose.translate($$0, $$1, $$2);
        }

        public void scale(float $$0, float $$1, float $$2) {
            this.pose.scale($$0, $$1, $$2);
            if (Math.abs($$0) != Math.abs($$1) || Math.abs($$1) != Math.abs($$2)) {
                this.normal.scale(1.0f / $$0, 1.0f / $$1, 1.0f / $$2);
                this.trustedNormals = false;
            } else if ($$0 < 0.0f || $$1 < 0.0f || $$2 < 0.0f) {
                this.normal.scale(Math.signum($$0), Math.signum($$1), Math.signum($$2));
            }
        }

        public void rotate(Quaternionfc $$0) {
            this.pose.rotate($$0);
            this.normal.rotate($$0);
        }

        public void rotateAround(Quaternionfc $$0, float $$1, float $$2, float $$3) {
            this.pose.rotateAround($$0, $$1, $$2, $$3);
            this.normal.rotate($$0);
        }

        public void setIdentity() {
            this.pose.identity();
            this.normal.identity();
            this.trustedNormals = true;
        }

        public void mulPose(Matrix4fc $$0) {
            this.pose.mul($$0);
            if (!MatrixUtil.isPureTranslation($$0)) {
                if (MatrixUtil.isOrthonormal($$0)) {
                    this.normal.mul(new Matrix3f($$0));
                } else {
                    computeNormalMatrix();
                }
            }
        }

        public Pose copy() {
            Pose $$0 = new Pose();
            $$0.set(this);
            return $$0;
        }
    }
}
