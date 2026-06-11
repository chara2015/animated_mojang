package com.mojang.blaze3d;

import com.mojang.blaze3d.vertex.VertexSorting;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/ProjectionType.class */
public enum ProjectionType {
    PERSPECTIVE(VertexSorting.DISTANCE_TO_ORIGIN, ($$0, $$1) -> {
        $$0.scale(1.0f - ($$1 / 4096.0f));
    }),
    ORTHOGRAPHIC(VertexSorting.ORTHOGRAPHIC_Z, ($$02, $$12) -> {
        $$02.translate(0.0f, 0.0f, $$12 / 512.0f);
    });

    private final VertexSorting vertexSorting;
    private final LayeringTransform layeringTransform;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/ProjectionType$LayeringTransform.class */
    @FunctionalInterface
    interface LayeringTransform {
        void apply(Matrix4f matrix4f, float f);
    }

    ProjectionType(VertexSorting $$0, LayeringTransform $$1) {
        this.vertexSorting = $$0;
        this.layeringTransform = $$1;
    }

    public VertexSorting vertexSorting() {
        return this.vertexSorting;
    }

    public void applyLayeringTransform(Matrix4f $$0, float $$1) {
        this.layeringTransform.apply($$0, $$1);
    }
}
