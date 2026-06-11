package net.labymod.v1_21_1.client.render.model;

import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/render/model/ModelPartAnimationTransformation.class */
public class ModelPartAnimationTransformation extends Transformation {
    public ModelPartAnimationTransformation(fyk modelPart, Transformation modelPartTransform) {
        super(new ModelPartPositionVector(modelPart, modelPartTransform.getTranslation()), new ModelPartRotationVector(modelPart), new FloatVector3(1.0f, 1.0f, 1.0f));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/render/model/ModelPartAnimationTransformation$ModelPartPositionVector.class */
    private static class ModelPartPositionVector extends FloatVector3 {
        private final fyk modelPart;
        private final FloatVector3 modelPartTranslation;

        public ModelPartPositionVector(fyk modelPart, FloatVector3 modelPartTranslation) {
            this.modelPart = modelPart;
            this.modelPartTranslation = modelPartTranslation;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelPart.b - this.modelPartTranslation.getX();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelPart.b = this.modelPartTranslation.getX() + x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelPart.c - this.modelPartTranslation.getY();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelPart.c = this.modelPartTranslation.getY() + y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelPart.d - this.modelPartTranslation.getZ();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelPart.d = this.modelPartTranslation.getZ() + z;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/render/model/ModelPartAnimationTransformation$ModelPartRotationVector.class */
    private static class ModelPartRotationVector extends FloatVector3 {
        private final fyk modelPart;

        public ModelPartRotationVector(fyk modelPart) {
            this.modelPart = modelPart;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(float x, float y, float z) {
            this.modelPart.e = x;
            this.modelPart.f = y;
            this.modelPart.g = z;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(FloatVector3 vector) {
            this.modelPart.e = vector.getX();
            this.modelPart.f = vector.getY();
            this.modelPart.g = vector.getZ();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelPart.e;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelPart.e = x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelPart.f;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelPart.f = y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelPart.g;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelPart.g = z;
        }
    }
}
