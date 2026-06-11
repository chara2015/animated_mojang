package net.labymod.v1_21_10.client.render.model;

import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/render/model/ModelPartAnimationTransformation.class */
public class ModelPartAnimationTransformation extends Transformation {
    public ModelPartAnimationTransformation(gyo modelPart, Transformation modelPartTransform) {
        super(new ModelPartPositionVector(modelPart, modelPartTransform.getTranslation()), new ModelPartRotationVector(modelPart), new ModelPartScaleVector(modelPart));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/render/model/ModelPartAnimationTransformation$ModelPartPositionVector.class */
    private static class ModelPartPositionVector extends FloatVector3 {
        private final gyo modelPart;
        private final FloatVector3 modelPartTranslation;

        public ModelPartPositionVector(gyo modelPart, FloatVector3 modelPartTranslation) {
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

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/render/model/ModelPartAnimationTransformation$ModelPartRotationVector.class */
    private static class ModelPartRotationVector extends FloatVector3 {
        private final gyo modelPart;

        public ModelPartRotationVector(gyo modelPart) {
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

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/render/model/ModelPartAnimationTransformation$ModelPartScaleVector.class */
    private static class ModelPartScaleVector extends FloatVector3 {
        private final gyo modelPart;

        public ModelPartScaleVector(gyo modelPart) {
            this.modelPart = modelPart;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(float x, float y, float z) {
            this.modelPart.h = x;
            this.modelPart.i = y;
            this.modelPart.j = z;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelPart.h;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelPart.h = x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelPart.i;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelPart.i = y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelPart.j;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelPart.j = z;
        }
    }
}
