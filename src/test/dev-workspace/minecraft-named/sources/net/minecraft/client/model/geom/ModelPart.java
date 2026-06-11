package net.minecraft.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/ModelPart.class */
public final class ModelPart {
    public static final float DEFAULT_SCALE = 1.0f;
    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;
    public boolean skipDraw;
    private final List<Cube> cubes;
    private final Map<String, ModelPart> children;
    public float xScale = 1.0f;
    public float yScale = 1.0f;
    public float zScale = 1.0f;
    public boolean visible = true;
    private PartPose initialPose = PartPose.ZERO;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/ModelPart$Visitor.class */
    @FunctionalInterface
    public interface Visitor {
        void visit(PoseStack.Pose pose, String str, int i, Cube cube);
    }

    public ModelPart(List<Cube> $$0, Map<String, ModelPart> $$1) {
        this.cubes = $$0;
        this.children = $$1;
    }

    public PartPose storePose() {
        return PartPose.offsetAndRotation(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot);
    }

    public PartPose getInitialPose() {
        return this.initialPose;
    }

    public void setInitialPose(PartPose $$0) {
        this.initialPose = $$0;
    }

    public void resetPose() {
        loadPose(this.initialPose);
    }

    public void loadPose(PartPose $$0) {
        this.x = $$0.x();
        this.y = $$0.y();
        this.z = $$0.z();
        this.xRot = $$0.xRot();
        this.yRot = $$0.yRot();
        this.zRot = $$0.zRot();
        this.xScale = $$0.xScale();
        this.yScale = $$0.yScale();
        this.zScale = $$0.zScale();
    }

    public boolean hasChild(String $$0) {
        return this.children.containsKey($$0);
    }

    public ModelPart getChild(String $$0) {
        ModelPart $$1 = this.children.get($$0);
        if ($$1 == null) {
            throw new NoSuchElementException("Can't find part " + $$0);
        }
        return $$1;
    }

    public void setPos(float $$0, float $$1, float $$2) {
        this.x = $$0;
        this.y = $$1;
        this.z = $$2;
    }

    public void setRotation(float $$0, float $$1, float $$2) {
        this.xRot = $$0;
        this.yRot = $$1;
        this.zRot = $$2;
    }

    public void render(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3) {
        render($$0, $$1, $$2, $$3, -1);
    }

    public void render(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, int $$4) {
        if (!this.visible) {
            return;
        }
        if (this.cubes.isEmpty() && this.children.isEmpty()) {
            return;
        }
        $$0.pushPose();
        translateAndRotate($$0);
        if (!this.skipDraw) {
            compile($$0.last(), $$1, $$2, $$3, $$4);
        }
        for (ModelPart $$5 : this.children.values()) {
            $$5.render($$0, $$1, $$2, $$3, $$4);
        }
        $$0.popPose();
    }

    public void rotateBy(Quaternionf $$0) {
        Matrix3f $$1 = new Matrix3f().rotationZYX(this.zRot, this.yRot, this.xRot);
        Matrix3f $$2 = $$1.rotate($$0);
        Vector3f $$3 = $$2.getEulerAnglesZYX(new Vector3f());
        setRotation($$3.x, $$3.y, $$3.z);
    }

    public void getExtentsForGui(PoseStack $$0, Consumer<Vector3fc> $$1) {
        visit($$0, ($$12, $$2, $$3, $$4) -> {
            for (Polygon $$5 : $$4.polygons) {
                for (Vertex $$6 : $$5.vertices()) {
                    float $$7 = $$6.worldX();
                    float $$8 = $$6.worldY();
                    float $$9 = $$6.worldZ();
                    Vector3f $$10 = $$12.pose().transformPosition($$7, $$8, $$9, new Vector3f());
                    $$1.accept($$10);
                }
            }
        });
    }

    public void visit(PoseStack $$0, Visitor $$1) {
        visit($$0, $$1, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void visit(PoseStack $$0, Visitor $$1, String $$2) {
        if (this.cubes.isEmpty() && this.children.isEmpty()) {
            return;
        }
        $$0.pushPose();
        translateAndRotate($$0);
        PoseStack.Pose $$3 = $$0.last();
        for (int $$4 = 0; $$4 < this.cubes.size(); $$4++) {
            $$1.visit($$3, $$2, $$4, this.cubes.get($$4));
        }
        String $$5 = $$2 + "/";
        this.children.forEach(($$32, $$42) -> {
            $$42.visit($$0, $$1, $$5 + $$32);
        });
        $$0.popPose();
    }

    public void translateAndRotate(PoseStack $$0) {
        $$0.translate(this.x / 16.0f, this.y / 16.0f, this.z / 16.0f);
        if (this.xRot != 0.0f || this.yRot != 0.0f || this.zRot != 0.0f) {
            $$0.mulPose((Quaternionfc) new Quaternionf().rotationZYX(this.zRot, this.yRot, this.xRot));
        }
        if (this.xScale != 1.0f || this.yScale != 1.0f || this.zScale != 1.0f) {
            $$0.scale(this.xScale, this.yScale, this.zScale);
        }
    }

    private void compile(PoseStack.Pose $$0, VertexConsumer $$1, int $$2, int $$3, int $$4) {
        for (Cube $$5 : this.cubes) {
            $$5.compile($$0, $$1, $$2, $$3, $$4);
        }
    }

    public Cube getRandomCube(RandomSource $$0) {
        return this.cubes.get($$0.nextInt(this.cubes.size()));
    }

    public boolean isEmpty() {
        return this.cubes.isEmpty();
    }

    public void offsetPos(Vector3f $$0) {
        this.x += $$0.x();
        this.y += $$0.y();
        this.z += $$0.z();
    }

    public void offsetRotation(Vector3f $$0) {
        this.xRot += $$0.x();
        this.yRot += $$0.y();
        this.zRot += $$0.z();
    }

    public void offsetScale(Vector3f $$0) {
        this.xScale += $$0.x();
        this.yScale += $$0.y();
        this.zScale += $$0.z();
    }

    public List<ModelPart> getAllParts() {
        List<ModelPart> $$0 = new ArrayList<>();
        $$0.add(this);
        addAllChildren(($$1, $$2) -> {
            $$0.add($$2);
        });
        return List.copyOf($$0);
    }

    public Function<String, ModelPart> createPartLookup() {
        Map<String, ModelPart> $$0 = new HashMap<>();
        $$0.put("root", this);
        Objects.requireNonNull($$0);
        addAllChildren((v1, v2) -> {
            r1.putIfAbsent(v1, v2);
        });
        Objects.requireNonNull($$0);
        return (v1) -> {
            return r0.get(v1);
        };
    }

    private void addAllChildren(BiConsumer<String, ModelPart> $$0) {
        for (Map.Entry<String, ModelPart> $$1 : this.children.entrySet()) {
            $$0.accept($$1.getKey(), $$1.getValue());
        }
        for (ModelPart $$2 : this.children.values()) {
            $$2.addAllChildren($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/ModelPart$Cube.class */
    public static class Cube {
        public final Polygon[] polygons;
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Cube(int $$0, int $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, boolean $$11, float $$12, float $$13, Set<Direction> $$14) {
            this.minX = $$2;
            this.minY = $$3;
            this.minZ = $$4;
            this.maxX = $$2 + $$5;
            this.maxY = $$3 + $$6;
            this.maxZ = $$4 + $$7;
            this.polygons = new Polygon[$$14.size()];
            float $$15 = $$2 + $$5;
            float $$22 = $$2 - $$8;
            float $$32 = $$3 - $$9;
            float $$42 = $$4 - $$10;
            float $$152 = $$15 + $$8;
            float $$16 = $$3 + $$6 + $$9;
            float $$17 = $$4 + $$7 + $$10;
            if ($$11) {
                $$152 = $$22;
                $$22 = $$152;
            }
            Vertex $$19 = new Vertex($$22, $$32, $$42, 0.0f, 0.0f);
            Vertex $$20 = new Vertex($$152, $$32, $$42, 0.0f, 8.0f);
            Vertex $$21 = new Vertex($$152, $$16, $$42, 8.0f, 8.0f);
            Vertex $$222 = new Vertex($$22, $$16, $$42, 8.0f, 0.0f);
            Vertex $$23 = new Vertex($$22, $$32, $$17, 0.0f, 0.0f);
            Vertex $$24 = new Vertex($$152, $$32, $$17, 0.0f, 8.0f);
            Vertex $$25 = new Vertex($$152, $$16, $$17, 8.0f, 8.0f);
            Vertex $$26 = new Vertex($$22, $$16, $$17, 8.0f, 0.0f);
            float $$27 = $$0;
            float $$28 = $$0 + $$7;
            float $$29 = $$0 + $$7 + $$5;
            float $$30 = $$0 + $$7 + $$5 + $$5;
            float $$31 = $$0 + $$7 + $$5 + $$7;
            float $$322 = $$0 + $$7 + $$5 + $$7 + $$5;
            float $$33 = $$1;
            float $$34 = $$1 + $$7;
            float $$35 = $$1 + $$7 + $$6;
            int $$36 = 0;
            if ($$14.contains(Direction.DOWN)) {
                $$36 = 0 + 1;
                this.polygons[0] = new Polygon(new Vertex[]{$$24, $$23, $$19, $$20}, $$28, $$33, $$29, $$34, $$12, $$13, $$11, Direction.DOWN);
            }
            if ($$14.contains(Direction.UP)) {
                int i = $$36;
                $$36++;
                this.polygons[i] = new Polygon(new Vertex[]{$$21, $$222, $$26, $$25}, $$29, $$34, $$30, $$33, $$12, $$13, $$11, Direction.UP);
            }
            if ($$14.contains(Direction.WEST)) {
                int i2 = $$36;
                $$36++;
                this.polygons[i2] = new Polygon(new Vertex[]{$$19, $$23, $$26, $$222}, $$27, $$34, $$28, $$35, $$12, $$13, $$11, Direction.WEST);
            }
            if ($$14.contains(Direction.NORTH)) {
                int i3 = $$36;
                $$36++;
                this.polygons[i3] = new Polygon(new Vertex[]{$$20, $$19, $$222, $$21}, $$28, $$34, $$29, $$35, $$12, $$13, $$11, Direction.NORTH);
            }
            if ($$14.contains(Direction.EAST)) {
                int i4 = $$36;
                $$36++;
                this.polygons[i4] = new Polygon(new Vertex[]{$$24, $$20, $$21, $$25}, $$29, $$34, $$31, $$35, $$12, $$13, $$11, Direction.EAST);
            }
            if ($$14.contains(Direction.SOUTH)) {
                this.polygons[$$36] = new Polygon(new Vertex[]{$$23, $$24, $$25, $$26}, $$31, $$34, $$322, $$35, $$12, $$13, $$11, Direction.SOUTH);
            }
        }

        public void compile(PoseStack.Pose $$0, VertexConsumer $$1, int $$2, int $$3, int $$4) {
            Matrix4f $$5 = $$0.pose();
            Vector3f $$6 = new Vector3f();
            for (Polygon $$7 : this.polygons) {
                Vector3f $$8 = $$0.transformNormal($$7.normal, $$6);
                float $$9 = $$8.x();
                float $$10 = $$8.y();
                float $$11 = $$8.z();
                for (Vertex $$12 : $$7.vertices) {
                    float $$13 = $$12.worldX();
                    float $$14 = $$12.worldY();
                    float $$15 = $$12.worldZ();
                    Vector3f $$16 = $$5.transformPosition($$13, $$14, $$15, $$6);
                    $$1.addVertex($$16.x(), $$16.y(), $$16.z(), $$4, $$12.u, $$12.v, $$3, $$2, $$9, $$10, $$11);
                }
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/ModelPart$Polygon.class */
    public static final class Polygon extends Record {
        private final Vertex[] vertices;
        private final Vector3fc normal;

        public Polygon(Vertex[] $$0, Vector3fc $$1) {
            this.vertices = $$0;
            this.normal = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Polygon.class), Polygon.class, "vertices;normal", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Polygon;->vertices:[Lnet/minecraft/client/model/geom/ModelPart$Vertex;", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Polygon;->normal:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Polygon.class), Polygon.class, "vertices;normal", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Polygon;->vertices:[Lnet/minecraft/client/model/geom/ModelPart$Vertex;", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Polygon;->normal:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Polygon.class, Object.class), Polygon.class, "vertices;normal", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Polygon;->vertices:[Lnet/minecraft/client/model/geom/ModelPart$Vertex;", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Polygon;->normal:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Vertex[] vertices() {
            return this.vertices;
        }

        public Vector3fc normal() {
            return this.normal;
        }

        public Polygon(Vertex[] $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, boolean $$7, Direction $$8) {
            this($$0, ($$7 ? mirrorFacing($$8) : $$8).getUnitVec3f());
            float $$9 = 0.0f / $$5;
            float $$10 = 0.0f / $$6;
            $$0[0] = $$0[0].remap(($$3 / $$5) - $$9, ($$2 / $$6) + $$10);
            $$0[1] = $$0[1].remap(($$1 / $$5) + $$9, ($$2 / $$6) + $$10);
            $$0[2] = $$0[2].remap(($$1 / $$5) + $$9, ($$4 / $$6) - $$10);
            $$0[3] = $$0[3].remap(($$3 / $$5) - $$9, ($$4 / $$6) - $$10);
            if ($$7) {
                int $$11 = $$0.length;
                for (int $$12 = 0; $$12 < $$11 / 2; $$12++) {
                    Vertex $$13 = $$0[$$12];
                    $$0[$$12] = $$0[($$11 - 1) - $$12];
                    $$0[($$11 - 1) - $$12] = $$13;
                }
            }
        }

        private static Direction mirrorFacing(Direction $$0) {
            return $$0.getAxis() == Direction.Axis.X ? $$0.getOpposite() : $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/ModelPart$Vertex.class */
    public static final class Vertex extends Record {
        private final float x;
        private final float y;
        private final float z;
        private final float u;
        private final float v;
        public static final float SCALE_FACTOR = 16.0f;

        public Vertex(float $$0, float $$1, float $$2, float $$3, float $$4) {
            this.x = $$0;
            this.y = $$1;
            this.z = $$2;
            this.u = $$3;
            this.v = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Vertex.class), Vertex.class, "x;y;z;u;v", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->x:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->y:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->z:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->u:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->v:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Vertex.class), Vertex.class, "x;y;z;u;v", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->x:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->y:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->z:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->u:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->v:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Vertex.class, Object.class), Vertex.class, "x;y;z;u;v", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->x:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->y:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->z:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->u:F", "FIELD:Lnet/minecraft/client/model/geom/ModelPart$Vertex;->v:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float x() {
            return this.x;
        }

        public float y() {
            return this.y;
        }

        public float z() {
            return this.z;
        }

        public float u() {
            return this.u;
        }

        public float v() {
            return this.v;
        }

        public Vertex remap(float $$0, float $$1) {
            return new Vertex(this.x, this.y, this.z, $$0, $$1);
        }

        public float worldX() {
            return this.x / 16.0f;
        }

        public float worldY() {
            return this.y / 16.0f;
        }

        public float worldZ() {
            return this.z / 16.0f;
        }
    }
}
