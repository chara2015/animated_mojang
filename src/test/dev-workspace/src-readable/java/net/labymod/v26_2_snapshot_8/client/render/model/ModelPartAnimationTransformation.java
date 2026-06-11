package net.labymod.v26_2_snapshot_8.client.render.model;

import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.model.geom.ModelPart;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/render/model/ModelPartAnimationTransformation.class */
public class ModelPartAnimationTransformation extends Transformation {
    public ModelPartAnimationTransformation(ModelPart modelPart, Transformation modelPartTransform) {
        super(new ModelPartPositionVector(modelPart, modelPartTransform.getTranslation()), new ModelPartRotationVector(modelPart), new ModelPartScaleVector(modelPart));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/render/model/ModelPartAnimationTransformation$ModelPartPositionVector.class */
    private static class ModelPartPositionVector extends FloatVector3 {
        private final ModelPart modelPart;
        private final FloatVector3 modelPartTranslation;

        public ModelPartPositionVector(ModelPart modelPart, FloatVector3 modelPartTranslation) {
            this.modelPart = modelPart;
            this.modelPartTranslation = modelPartTranslation;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelPart.x - this.modelPartTranslation.getX();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelPart.x = this.modelPartTranslation.getX() + x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelPart.y - this.modelPartTranslation.getY();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelPart.y = this.modelPartTranslation.getY() + y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelPart.z - this.modelPartTranslation.getZ();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelPart.z = this.modelPartTranslation.getZ() + z;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/render/model/ModelPartAnimationTransformation$ModelPartRotationVector.class */
    private static class ModelPartRotationVector extends FloatVector3 {
        private final ModelPart modelPart;

        public ModelPartRotationVector(ModelPart modelPart) {
            this.modelPart = modelPart;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(float x, float y, float z) {
            this.modelPart.xRot = x;
            this.modelPart.yRot = y;
            this.modelPart.zRot = z;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(FloatVector3 vector) {
            this.modelPart.xRot = vector.getX();
            this.modelPart.yRot = vector.getY();
            this.modelPart.zRot = vector.getZ();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelPart.xRot;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelPart.xRot = x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelPart.yRot;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelPart.yRot = y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelPart.zRot;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelPart.zRot = z;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/render/model/ModelPartAnimationTransformation$ModelPartScaleVector.class */
    private static class ModelPartScaleVector extends FloatVector3 {
        private final ModelPart modelPart;

        public ModelPartScaleVector(ModelPart modelPart) {
            this.modelPart = modelPart;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(float x, float y, float z) {
            this.modelPart.xScale = x;
            this.modelPart.yScale = y;
            this.modelPart.zScale = z;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelPart.xScale;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelPart.xScale = x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelPart.yScale;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelPart.yScale = y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelPart.zScale;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelPart.zScale = z;
        }
    }
}
