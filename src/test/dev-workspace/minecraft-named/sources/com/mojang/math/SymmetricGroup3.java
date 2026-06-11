package com.mojang.math;

import java.util.Arrays;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Vector3f;
import org.joml.Vector3i;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/math/SymmetricGroup3.class */
public enum SymmetricGroup3 {
    P123(0, 1, 2),
    P213(1, 0, 2),
    P132(0, 2, 1),
    P312(2, 0, 1),
    P231(1, 2, 0),
    P321(2, 1, 0);

    private final int p0;
    private final int p1;
    private final int p2;
    private final Matrix3fc transformation = new Matrix3f().zero().set(permute(0), 0, 1.0f).set(permute(1), 1, 1.0f).set(permute(2), 2, 1.0f);
    private static final SymmetricGroup3[][] CAYLEY_TABLE = (SymmetricGroup3[][]) Util.make(() -> {
        SymmetricGroup3[] $$0 = values();
        SymmetricGroup3[][] $$1 = new SymmetricGroup3[$$0.length][$$0.length];
        for (SymmetricGroup3 $$2 : $$0) {
            for (SymmetricGroup3 $$3 : $$0) {
                int $$4 = $$2.permute($$3.p0);
                int $$5 = $$2.permute($$3.p1);
                int $$6 = $$2.permute($$3.p2);
                SymmetricGroup3 $$7 = (SymmetricGroup3) Arrays.stream($$0).filter($$32 -> {
                    return $$32.p0 == $$4 && $$32.p1 == $$5 && $$32.p2 == $$6;
                }).findFirst().get();
                $$1[$$2.ordinal()][$$3.ordinal()] = $$7;
            }
        }
        return $$1;
    });
    private static final SymmetricGroup3[] INVERSE_TABLE = (SymmetricGroup3[]) Util.make(() -> {
        SymmetricGroup3[] $$0 = values();
        return (SymmetricGroup3[]) Arrays.stream($$0).map($$02 -> {
            return (SymmetricGroup3) Arrays.stream(values()).filter($$1 -> {
                return $$02.compose($$1) == P123;
            }).findAny().get();
        }).toArray($$03 -> {
            return new SymmetricGroup3[$$03];
        });
    });

    SymmetricGroup3(int $$0, int $$1, int $$2) {
        this.p0 = $$0;
        this.p1 = $$1;
        this.p2 = $$2;
    }

    public SymmetricGroup3 compose(SymmetricGroup3 $$0) {
        return CAYLEY_TABLE[ordinal()][$$0.ordinal()];
    }

    public SymmetricGroup3 inverse() {
        return INVERSE_TABLE[ordinal()];
    }

    public int permute(int $$0) {
        switch ($$0) {
            case 0:
                return this.p0;
            case 1:
                return this.p1;
            case 2:
                return this.p2;
            default:
                throw new IllegalArgumentException("Must be 0, 1 or 2, but got " + $$0);
        }
    }

    public Direction.Axis permuteAxis(Direction.Axis $$0) {
        return Direction.Axis.VALUES[permute($$0.ordinal())];
    }

    public Vector3f permuteVector(Vector3f $$0) {
        float $$1 = $$0.get(this.p0);
        float $$2 = $$0.get(this.p1);
        float $$3 = $$0.get(this.p2);
        return $$0.set($$1, $$2, $$3);
    }

    public Vector3i permuteVector(Vector3i $$0) {
        int $$1 = $$0.get(this.p0);
        int $$2 = $$0.get(this.p1);
        int $$3 = $$0.get(this.p2);
        return $$0.set($$1, $$2, $$3);
    }

    public Matrix3fc transformation() {
        return this.transformation;
    }
}
