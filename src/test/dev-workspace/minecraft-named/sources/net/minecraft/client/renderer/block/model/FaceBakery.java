package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.math.MatrixUtil;
import com.mojang.math.Quadrant;
import com.mojang.math.Transformation;
import java.util.Objects;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import org.joml.GeometryUtils;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/FaceBakery.class */
public class FaceBakery {
    private static final Vector3fc BLOCK_MIDDLE = new Vector3f(0.5f, 0.5f, 0.5f);

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @VisibleForTesting
    static BlockElementFace.UVs defaultFaceUV(Vector3fc $$0, Vector3fc $$1, Direction $$2) throws MatchException {
        switch ($$2) {
            case DOWN:
                return new BlockElementFace.UVs($$0.x(), 16.0f - $$1.z(), $$1.x(), 16.0f - $$0.z());
            case UP:
                return new BlockElementFace.UVs($$0.x(), $$0.z(), $$1.x(), $$1.z());
            case NORTH:
                return new BlockElementFace.UVs(16.0f - $$1.x(), 16.0f - $$1.y(), 16.0f - $$0.x(), 16.0f - $$0.y());
            case SOUTH:
                return new BlockElementFace.UVs($$0.x(), 16.0f - $$1.y(), $$1.x(), 16.0f - $$0.y());
            case WEST:
                return new BlockElementFace.UVs($$0.z(), 16.0f - $$1.y(), $$1.z(), 16.0f - $$0.y());
            case EAST:
                return new BlockElementFace.UVs(16.0f - $$1.z(), 16.0f - $$1.y(), 16.0f - $$0.z(), 16.0f - $$0.y());
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static BakedQuad bakeQuad(ModelBaker.PartCache $$0, Vector3fc $$1, Vector3fc $$2, BlockElementFace $$3, TextureAtlasSprite $$4, Direction $$5, ModelState $$6, BlockElementRotation $$7, boolean $$8, int $$9) throws MatchException {
        BlockElementFace.UVs $$10 = $$3.uvs();
        if ($$10 == null) {
            $$10 = defaultFaceUV($$1, $$2, $$5);
        }
        Matrix4fc $$11 = $$6.inverseFaceTransformation($$5);
        Vector3fc[] $$12 = new Vector3fc[4];
        long[] $$13 = new long[4];
        FaceInfo $$14 = FaceInfo.fromFacing($$5);
        for (int $$15 = 0; $$15 < 4; $$15++) {
            bakeVertex($$15, $$14, $$10, $$3.rotation(), $$11, $$1, $$2, $$4, $$6.transformation(), $$7, $$12, $$13, $$0);
        }
        Direction $$16 = calculateFacing($$12);
        if ($$7 == null && $$16 != null) {
            recalculateWinding($$12, $$13, $$16);
        }
        return new BakedQuad($$12[0], $$12[1], $$12[2], $$12[3], $$13[0], $$13[1], $$13[2], $$13[3], $$3.tintIndex(), (Direction) Objects.requireNonNullElse($$16, Direction.UP), $$4, $$8, $$9);
    }

    private static void bakeVertex(int $$0, FaceInfo $$1, BlockElementFace.UVs $$2, Quadrant $$3, Matrix4fc $$4, Vector3fc $$5, Vector3fc $$6, TextureAtlasSprite $$7, Transformation $$8, BlockElementRotation $$9, Vector3fc[] $$10, long[] $$11, ModelBaker.PartCache $$12) {
        float $$20;
        float $$21;
        FaceInfo.VertexInfo $$13 = $$1.getVertexInfo($$0);
        Vector3f $$14 = $$13.select($$5, $$6).div(16.0f);
        if ($$9 != null) {
            rotateVertexBy($$14, $$9.origin(), $$9.transform());
        }
        if ($$8 != Transformation.identity()) {
            rotateVertexBy($$14, BLOCK_MIDDLE, $$8.getMatrix());
        }
        float $$15 = BlockElementFace.getU($$2, $$3, $$0);
        float $$16 = BlockElementFace.getV($$2, $$3, $$0);
        if (MatrixUtil.isIdentity($$4)) {
            $$20 = $$15;
            $$21 = $$16;
        } else {
            Vector3f $$19 = $$4.transformPosition(new Vector3f(cornerToCenter($$15), cornerToCenter($$16), 0.0f));
            $$20 = centerToCorner($$19.x);
            $$21 = centerToCorner($$19.y);
        }
        $$10[$$0] = $$12.vector($$14);
        $$11[$$0] = UVPair.pack($$7.getU($$20), $$7.getV($$21));
    }

    private static float cornerToCenter(float $$0) {
        return $$0 - 0.5f;
    }

    private static float centerToCorner(float $$0) {
        return $$0 + 0.5f;
    }

    private static void rotateVertexBy(Vector3f $$0, Vector3fc $$1, Matrix4fc $$2) {
        $$0.sub($$1);
        $$2.transformPosition($$0);
        $$0.add($$1);
    }

    private static Direction calculateFacing(Vector3fc[] $$0) {
        Vector3f $$1 = new Vector3f();
        GeometryUtils.normal($$0[0], $$0[1], $$0[2], $$1);
        return findClosestDirection($$1);
    }

    private static Direction findClosestDirection(Vector3f $$0) {
        if (!$$0.isFinite()) {
            return null;
        }
        Direction $$1 = null;
        float $$2 = 0.0f;
        for (Direction $$3 : Direction.values()) {
            float $$4 = $$0.dot($$3.getUnitVec3f());
            if ($$4 >= 0.0f && $$4 > $$2) {
                $$2 = $$4;
                $$1 = $$3;
            }
        }
        return $$1;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static void recalculateWinding(Vector3fc[] $$0, long[] $$1, Direction $$2) throws MatchException {
        float $$3 = 999.0f;
        float $$4 = 999.0f;
        float $$5 = 999.0f;
        float $$6 = -999.0f;
        float $$7 = -999.0f;
        float $$8 = -999.0f;
        for (int $$9 = 0; $$9 < 4; $$9++) {
            Vector3fc $$10 = $$0[$$9];
            float $$11 = $$10.x();
            float $$12 = $$10.y();
            float $$13 = $$10.z();
            if ($$11 < $$3) {
                $$3 = $$11;
            }
            if ($$12 < $$4) {
                $$4 = $$12;
            }
            if ($$13 < $$5) {
                $$5 = $$13;
            }
            if ($$11 > $$6) {
                $$6 = $$11;
            }
            if ($$12 > $$7) {
                $$7 = $$12;
            }
            if ($$13 > $$8) {
                $$8 = $$13;
            }
        }
        FaceInfo $$14 = FaceInfo.fromFacing($$2);
        for (int $$15 = 0; $$15 < 4; $$15++) {
            FaceInfo.VertexInfo $$16 = $$14.getVertexInfo($$15);
            float $$17 = $$16.xFace().select($$3, $$4, $$5, $$6, $$7, $$8);
            float $$18 = $$16.yFace().select($$3, $$4, $$5, $$6, $$7, $$8);
            float $$19 = $$16.zFace().select($$3, $$4, $$5, $$6, $$7, $$8);
            int $$20 = findVertex($$0, $$15, $$17, $$18, $$19);
            if ($$20 == -1) {
                throw new IllegalStateException("Can't find vertex to swap");
            }
            if ($$20 != $$15) {
                swap($$0, $$20, $$15);
                swap($$1, $$20, $$15);
            }
        }
    }

    private static int findVertex(Vector3fc[] $$0, int $$1, float $$2, float $$3, float $$4) {
        for (int $$5 = $$1; $$5 < 4; $$5++) {
            Vector3fc $$6 = $$0[$$5];
            if ($$2 == $$6.x() && $$3 == $$6.y() && $$4 == $$6.z()) {
                return $$5;
            }
        }
        return -1;
    }

    private static void swap(Vector3fc[] $$0, int $$1, int $$2) {
        Vector3fc $$3 = $$0[$$1];
        $$0[$$1] = $$0[$$2];
        $$0[$$2] = $$3;
    }

    private static void swap(long[] $$0, int $$1, int $$2) {
        long $$3 = $$0[$$1];
        $$0[$$1] = $$0[$$2];
        $$0[$$2] = $$3;
    }
}
