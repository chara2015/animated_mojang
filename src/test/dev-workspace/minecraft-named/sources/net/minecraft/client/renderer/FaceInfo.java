package net.minecraft.client.renderer;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/FaceInfo.class */
public enum FaceInfo {
    DOWN(new VertexInfo(Extent.MIN_X, Extent.MIN_Y, Extent.MAX_Z), new VertexInfo(Extent.MIN_X, Extent.MIN_Y, Extent.MIN_Z), new VertexInfo(Extent.MAX_X, Extent.MIN_Y, Extent.MIN_Z), new VertexInfo(Extent.MAX_X, Extent.MIN_Y, Extent.MAX_Z)),
    UP(new VertexInfo(Extent.MIN_X, Extent.MAX_Y, Extent.MIN_Z), new VertexInfo(Extent.MIN_X, Extent.MAX_Y, Extent.MAX_Z), new VertexInfo(Extent.MAX_X, Extent.MAX_Y, Extent.MAX_Z), new VertexInfo(Extent.MAX_X, Extent.MAX_Y, Extent.MIN_Z)),
    NORTH(new VertexInfo(Extent.MAX_X, Extent.MAX_Y, Extent.MIN_Z), new VertexInfo(Extent.MAX_X, Extent.MIN_Y, Extent.MIN_Z), new VertexInfo(Extent.MIN_X, Extent.MIN_Y, Extent.MIN_Z), new VertexInfo(Extent.MIN_X, Extent.MAX_Y, Extent.MIN_Z)),
    SOUTH(new VertexInfo(Extent.MIN_X, Extent.MAX_Y, Extent.MAX_Z), new VertexInfo(Extent.MIN_X, Extent.MIN_Y, Extent.MAX_Z), new VertexInfo(Extent.MAX_X, Extent.MIN_Y, Extent.MAX_Z), new VertexInfo(Extent.MAX_X, Extent.MAX_Y, Extent.MAX_Z)),
    WEST(new VertexInfo(Extent.MIN_X, Extent.MAX_Y, Extent.MIN_Z), new VertexInfo(Extent.MIN_X, Extent.MIN_Y, Extent.MIN_Z), new VertexInfo(Extent.MIN_X, Extent.MIN_Y, Extent.MAX_Z), new VertexInfo(Extent.MIN_X, Extent.MAX_Y, Extent.MAX_Z)),
    EAST(new VertexInfo(Extent.MAX_X, Extent.MAX_Y, Extent.MAX_Z), new VertexInfo(Extent.MAX_X, Extent.MIN_Y, Extent.MAX_Z), new VertexInfo(Extent.MAX_X, Extent.MIN_Y, Extent.MIN_Z), new VertexInfo(Extent.MAX_X, Extent.MAX_Y, Extent.MIN_Z));

    private static final Map<Direction, FaceInfo> BY_FACING = (Map) Util.make(new EnumMap(Direction.class), $$0 -> {
        $$0.put(Direction.DOWN, DOWN);
        $$0.put(Direction.UP, UP);
        $$0.put(Direction.NORTH, NORTH);
        $$0.put(Direction.SOUTH, SOUTH);
        $$0.put(Direction.WEST, WEST);
        $$0.put(Direction.EAST, EAST);
    });
    private final VertexInfo[] infos;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/FaceInfo$Extent.class */
    public enum Extent {
        MIN_X,
        MIN_Y,
        MIN_Z,
        MAX_X,
        MAX_Y,
        MAX_Z;

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public float select(Vector3fc $$0, Vector3fc $$1) throws MatchException {
            switch (this) {
                case MIN_X:
                    return $$0.x();
                case MIN_Y:
                    return $$0.y();
                case MIN_Z:
                    return $$0.z();
                case MAX_X:
                    return $$1.x();
                case MAX_Y:
                    return $$1.y();
                case MAX_Z:
                    return $$1.z();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public float select(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) throws MatchException {
            switch (this) {
                case MIN_X:
                    return $$0;
                case MIN_Y:
                    return $$1;
                case MIN_Z:
                    return $$2;
                case MAX_X:
                    return $$3;
                case MAX_Y:
                    return $$4;
                case MAX_Z:
                    return $$5;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
    }

    public static FaceInfo fromFacing(Direction $$0) {
        return BY_FACING.get($$0);
    }

    FaceInfo(VertexInfo... $$0) {
        this.infos = $$0;
    }

    public VertexInfo getVertexInfo(int $$0) {
        return this.infos[$$0];
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/FaceInfo$VertexInfo.class */
    public static final class VertexInfo extends Record {
        private final Extent xFace;
        private final Extent yFace;
        private final Extent zFace;

        public VertexInfo(Extent $$0, Extent $$1, Extent $$2) {
            this.xFace = $$0;
            this.yFace = $$1;
            this.zFace = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, VertexInfo.class), VertexInfo.class, "xFace;yFace;zFace", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->xFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->yFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->zFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VertexInfo.class), VertexInfo.class, "xFace;yFace;zFace", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->xFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->yFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->zFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VertexInfo.class, Object.class), VertexInfo.class, "xFace;yFace;zFace", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->xFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->yFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;", "FIELD:Lnet/minecraft/client/renderer/FaceInfo$VertexInfo;->zFace:Lnet/minecraft/client/renderer/FaceInfo$Extent;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Extent xFace() {
            return this.xFace;
        }

        public Extent yFace() {
            return this.yFace;
        }

        public Extent zFace() {
            return this.zFace;
        }

        public Vector3f select(Vector3fc $$0, Vector3fc $$1) {
            return new Vector3f(this.xFace.select($$0, $$1), this.yFace.select($$0, $$1), this.zFace.select($$0, $$1));
        }
    }
}
