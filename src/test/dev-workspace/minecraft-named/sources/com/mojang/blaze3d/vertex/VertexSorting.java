package com.mojang.blaze3d.vertex;

import com.google.common.primitives.Floats;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Objects;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexSorting.class */
public interface VertexSorting {
    public static final VertexSorting DISTANCE_TO_ORIGIN = byDistance(0.0f, 0.0f, 0.0f);
    public static final VertexSorting ORTHOGRAPHIC_Z = byDistance($$0 -> {
        return -$$0.z();
    });

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/VertexSorting$DistanceFunction.class */
    @FunctionalInterface
    public interface DistanceFunction {
        float apply(Vector3f vector3f);
    }

    int[] sort(CompactVectorArray compactVectorArray);

    static VertexSorting byDistance(float $$0, float $$1, float $$2) {
        return byDistance((Vector3fc) new Vector3f($$0, $$1, $$2));
    }

    static VertexSorting byDistance(Vector3fc $$0) {
        Objects.requireNonNull($$0);
        return byDistance((v1) -> {
            return r0.distanceSquared(v1);
        });
    }

    static VertexSorting byDistance(DistanceFunction $$0) {
        return $$1 -> {
            Vector3f $$2 = new Vector3f();
            float[] $$3 = new float[$$1.size()];
            int[] $$4 = new int[$$1.size()];
            for (int $$5 = 0; $$5 < $$1.size(); $$5++) {
                $$3[$$5] = $$0.apply($$1.get($$5, $$2));
                $$4[$$5] = $$5;
            }
            IntArrays.mergeSort($$4, ($$1, $$22) -> {
                return Floats.compare($$3[$$22], $$3[$$1]);
            });
            return $$4;
        };
    }
}
