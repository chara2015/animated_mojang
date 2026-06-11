package com.mojang.math;

import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/math/Quadrant.class */
public enum Quadrant {
    R0(0, OctahedralGroup.IDENTITY, OctahedralGroup.IDENTITY, OctahedralGroup.IDENTITY),
    R90(1, OctahedralGroup.BLOCK_ROT_X_90, OctahedralGroup.BLOCK_ROT_Y_90, OctahedralGroup.BLOCK_ROT_Z_90),
    R180(2, OctahedralGroup.BLOCK_ROT_X_180, OctahedralGroup.BLOCK_ROT_Y_180, OctahedralGroup.BLOCK_ROT_Z_180),
    R270(3, OctahedralGroup.BLOCK_ROT_X_270, OctahedralGroup.BLOCK_ROT_Y_270, OctahedralGroup.BLOCK_ROT_Z_270);

    public static final Codec<Quadrant> CODEC = Codec.INT.comapFlatMap($$0 -> {
        switch (Mth.positiveModulo($$0.intValue(), 360)) {
            case 0:
                return DataResult.success(R0);
            case 90:
                return DataResult.success(R90);
            case 180:
                return DataResult.success(R180);
            case 270:
                return DataResult.success(R270);
            default:
                return DataResult.error(() -> {
                    return "Invalid rotation " + $$0 + " found, only 0/90/180/270 allowed";
                });
        }
    }, $$02 -> {
        switch ($$02) {
            case R0:
                return 0;
            case R90:
                return 90;
            case R180:
                return 180;
            case R270:
                return 270;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    });
    public final int shift;
    public final OctahedralGroup rotationX;
    public final OctahedralGroup rotationY;
    public final OctahedralGroup rotationZ;

    Quadrant(int $$0, OctahedralGroup $$1, OctahedralGroup $$2, OctahedralGroup $$3) {
        this.shift = $$0;
        this.rotationX = $$1;
        this.rotationY = $$2;
        this.rotationZ = $$3;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    @Deprecated
    public static Quadrant parseJson(int $$0) throws JsonParseException {
        switch (Mth.positiveModulo($$0, 360)) {
            case 0:
                return R0;
            case 90:
                return R90;
            case 180:
                return R180;
            case 270:
                return R270;
            default:
                throw new JsonParseException("Invalid rotation " + $$0 + " found, only 0/90/180/270 allowed");
        }
    }

    public static OctahedralGroup fromXYAngles(Quadrant $$0, Quadrant $$1) {
        return $$1.rotationY.compose($$0.rotationX);
    }

    public static OctahedralGroup fromXYZAngles(Quadrant $$0, Quadrant $$1, Quadrant $$2) {
        return $$2.rotationZ.compose($$1.rotationY.compose($$0.rotationX));
    }

    public int rotateVertexIndex(int $$0) {
        return ($$0 + this.shift) % 4;
    }
}
