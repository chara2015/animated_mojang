package net.labymod.v1_8_9.client.render.model;

import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/render/model/ModelRendererAnimationTransformation.class */
public class ModelRendererAnimationTransformation extends Transformation {
    public ModelRendererAnimationTransformation(bct modelRenderer, Transformation modelPartTransform) {
        super(new ModelRendererPositionVector(modelRenderer, modelPartTransform.getTranslation()), new ModelRendererRotationVector(modelRenderer), new FloatVector3(1.0f, 1.0f, 1.0f));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/render/model/ModelRendererAnimationTransformation$ModelRendererPositionVector.class */
    private static class ModelRendererPositionVector extends FloatVector3 {
        private final bct modelRenderer;
        private final FloatVector3 modelPartTranslation;

        public ModelRendererPositionVector(bct modelRenderer, FloatVector3 modelPartTranslation) {
            this.modelRenderer = modelRenderer;
            this.modelPartTranslation = modelPartTranslation;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelRenderer.c - this.modelPartTranslation.getX();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelRenderer.c = this.modelPartTranslation.getX() + x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelRenderer.d - this.modelPartTranslation.getY();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelRenderer.d = this.modelPartTranslation.getY() + y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelRenderer.e - this.modelPartTranslation.getZ();
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelRenderer.e = this.modelPartTranslation.getZ() + z;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/render/model/ModelRendererAnimationTransformation$ModelRendererRotationVector.class */
    private static class ModelRendererRotationVector extends FloatVector3 {
        private final bct modelRenderer;

        public ModelRendererRotationVector(bct modelRenderer) {
            this.modelRenderer = modelRenderer;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getX() {
            return this.modelRenderer.f;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setX(float x) {
            this.modelRenderer.f = x;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getY() {
            return this.modelRenderer.g;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setY(float y) {
            this.modelRenderer.g = y;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public float getZ() {
            return this.modelRenderer.h;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void setZ(float z) {
            this.modelRenderer.h = z;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(float x, float y, float z) {
            this.modelRenderer.f = x;
            this.modelRenderer.g = y;
            this.modelRenderer.h = z;
        }

        @Override // net.labymod.api.util.math.vector.FloatVector3
        public void set(FloatVector3 vector) {
            this.modelRenderer.f = vector.getX();
            this.modelRenderer.g = vector.getY();
            this.modelRenderer.h = vector.getZ();
        }
    }
}
