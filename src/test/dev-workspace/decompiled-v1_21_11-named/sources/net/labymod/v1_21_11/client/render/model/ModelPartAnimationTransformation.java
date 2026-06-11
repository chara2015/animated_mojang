package net.labymod.v1_21_11.client.render.model;

import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.model.geom.ModelPart;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/model/ModelPartAnimationTransformation.class */
public class ModelPartAnimationTransformation extends Transformation {
    public ModelPartAnimationTransformation(ModelPart modelPart, Transformation modelPartTransform) {
        super(new ModelPartPositionVector(modelPart, modelPartTransform.getTranslation()), new ModelPartRotationVector(modelPart), new ModelPartScaleVector(modelPart));
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/model/ModelPartAnimationTransformation$ModelPartPositionVector.class */
    private static class ModelPartPositionVector extends FloatVector3 {
        private final ModelPart modelPart;
        private final FloatVector3 modelPartTranslation;

        public ModelPartPositionVector(ModelPart modelPart, FloatVector3 modelPartTranslation) {
            this.modelPart = modelPart;
            this.modelPartTranslation = modelPartTranslation;
        }

        public float getX() {
            return this.modelPart.x - this.modelPartTranslation.getX();
        }

        public void setX(float x) {
            this.modelPart.x = this.modelPartTranslation.getX() + x;
        }

        public float getY() {
            return this.modelPart.y - this.modelPartTranslation.getY();
        }

        public void setY(float y) {
            this.modelPart.y = this.modelPartTranslation.getY() + y;
        }

        public float getZ() {
            return this.modelPart.z - this.modelPartTranslation.getZ();
        }

        public void setZ(float z) {
            this.modelPart.z = this.modelPartTranslation.getZ() + z;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/model/ModelPartAnimationTransformation$ModelPartRotationVector.class */
    private static class ModelPartRotationVector extends FloatVector3 {
        private final ModelPart modelPart;

        public ModelPartRotationVector(ModelPart modelPart) {
            this.modelPart = modelPart;
        }

        public void set(float x, float y, float z) {
            this.modelPart.xRot = x;
            this.modelPart.yRot = y;
            this.modelPart.zRot = z;
        }

        public void set(FloatVector3 vector) {
            this.modelPart.xRot = vector.getX();
            this.modelPart.yRot = vector.getY();
            this.modelPart.zRot = vector.getZ();
        }

        public float getX() {
            return this.modelPart.xRot;
        }

        public void setX(float x) {
            this.modelPart.xRot = x;
        }

        public float getY() {
            return this.modelPart.yRot;
        }

        public void setY(float y) {
            this.modelPart.yRot = y;
        }

        public float getZ() {
            return this.modelPart.zRot;
        }

        public void setZ(float z) {
            this.modelPart.zRot = z;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/model/ModelPartAnimationTransformation$ModelPartScaleVector.class */
    private static class ModelPartScaleVector extends FloatVector3 {
        private final ModelPart modelPart;

        public ModelPartScaleVector(ModelPart modelPart) {
            this.modelPart = modelPart;
        }

        public void set(float x, float y, float z) {
            this.modelPart.xScale = x;
            this.modelPart.yScale = y;
            this.modelPart.zScale = z;
        }

        public float getX() {
            return this.modelPart.xScale;
        }

        public void setX(float x) {
            this.modelPart.xScale = x;
        }

        public float getY() {
            return this.modelPart.yScale;
        }

        public void setY(float y) {
            this.modelPart.yScale = y;
        }

        public float getZ() {
            return this.modelPart.zScale;
        }

        public void setZ(float z) {
            this.modelPart.zScale = z;
        }
    }
}
