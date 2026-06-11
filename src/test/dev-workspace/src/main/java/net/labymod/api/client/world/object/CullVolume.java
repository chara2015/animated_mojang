package net.labymod.api.client.world.object;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.laby3d.util.Frustum;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/CullVolume.class */
public interface CullVolume {
    boolean isVisible(@NotNull Frustum frustum);

    @NotNull
    static CullVolume point(@NotNull FloatVector3 point) {
        return point(point.getX(), point.getY(), point.getZ());
    }

    @NotNull
    static CullVolume point(@NotNull DoubleVector3 point) {
        return point(point.getX(), point.getY(), point.getZ());
    }

    @NotNull
    static CullVolume point(double x, double y, double z) {
        return new Point(x, y, z);
    }

    @NotNull
    static CullVolume box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return box(new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
    }

    @NotNull
    static CullVolume box(@NotNull AxisAlignedBoundingBox boundingBox) {
        return new Box(boundingBox);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/CullVolume$Point.class */
    public static final class Point extends Record implements CullVolume {
        private final double x;
        private final double y;
        private final double z;

        public Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Point.class), Point.class, "x;y;z", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->x:D", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->y:D", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->z:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Point.class), Point.class, "x;y;z", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->x:D", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->y:D", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->z:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Point.class, Object.class), Point.class, "x;y;z", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->x:D", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->y:D", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Point;->z:D").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public double x() {
            return this.x;
        }

        public double y() {
            return this.y;
        }

        public double z() {
            return this.z;
        }

        @Override // net.labymod.api.client.world.object.CullVolume
        public boolean isVisible(@NotNull Frustum frustum) {
            return frustum.isVisible(x(), y(), z());
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/CullVolume$Box.class */
    public static final class Box extends Record implements CullVolume {

        @NotNull
        private final AxisAlignedBoundingBox boundingBox;

        public Box(@NotNull AxisAlignedBoundingBox boundingBox) {
            this.boundingBox = boundingBox;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Box.class), Box.class, "boundingBox", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Box;->boundingBox:Lnet/labymod/api/util/math/AxisAlignedBoundingBox;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Box.class), Box.class, "boundingBox", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Box;->boundingBox:Lnet/labymod/api/util/math/AxisAlignedBoundingBox;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Box.class, Object.class), Box.class, "boundingBox", "FIELD:Lnet/labymod/api/client/world/object/CullVolume$Box;->boundingBox:Lnet/labymod/api/util/math/AxisAlignedBoundingBox;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @NotNull
        public AxisAlignedBoundingBox boundingBox() {
            return this.boundingBox;
        }

        @Override // net.labymod.api.client.world.object.CullVolume
        public boolean isVisible(@NotNull Frustum frustum) {
            return frustum.isVisible(boundingBox());
        }
    }
}
