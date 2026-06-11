package net.minecraft.core;

import com.google.common.collect.Maps;
import com.mojang.math.MatrixUtil;
import com.mojang.math.Transformation;
import java.util.Map;
import net.minecraft.util.Util;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/BlockMath.class */
public class BlockMath {
    private static final Map<Direction, Transformation> VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL = Maps.newEnumMap(Map.of(Direction.SOUTH, Transformation.identity(), Direction.EAST, new Transformation(null, new Quaternionf().rotateY(1.5707964f), null, null), Direction.WEST, new Transformation(null, new Quaternionf().rotateY(-1.5707964f), null, null), Direction.NORTH, new Transformation(null, new Quaternionf().rotateY(3.1415927f), null, null), Direction.UP, new Transformation(null, new Quaternionf().rotateX(-1.5707964f), null, null), Direction.DOWN, new Transformation(null, new Quaternionf().rotateX(1.5707964f), null, null)));
    private static final Map<Direction, Transformation> VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL = Maps.newEnumMap(Util.mapValues(VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL, (v0) -> {
        return v0.inverse();
    }));

    public static Transformation blockCenterToCorner(Transformation $$0) {
        Matrix4f $$1 = new Matrix4f().translation(0.5f, 0.5f, 0.5f);
        $$1.mul($$0.getMatrix());
        $$1.translate(-0.5f, -0.5f, -0.5f);
        return new Transformation($$1);
    }

    public static Transformation blockCornerToCenter(Transformation $$0) {
        Matrix4f $$1 = new Matrix4f().translation(-0.5f, -0.5f, -0.5f);
        $$1.mul($$0.getMatrix());
        $$1.translate(0.5f, 0.5f, 0.5f);
        return new Transformation($$1);
    }

    public static Transformation getFaceTransformation(Transformation $$0, Direction $$1) {
        if (MatrixUtil.isIdentity($$0.getMatrix())) {
            return $$0;
        }
        Transformation $$2 = $$0.compose(VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL.get($$1));
        Vector3f $$3 = $$2.getMatrix().transformDirection(new Vector3f(0.0f, 0.0f, 1.0f));
        Direction $$4 = Direction.getApproximateNearest($$3.x, $$3.y, $$3.z);
        return VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL.get($$4).compose($$2);
    }
}
