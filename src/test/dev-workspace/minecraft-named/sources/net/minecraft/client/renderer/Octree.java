package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Octree.class */
public class Octree {
    private final Branch root;
    final BlockPos cameraSectionCenter;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Octree$Node.class */
    public interface Node {
        void visitNodes(OctreeVisitor octreeVisitor, boolean z, Frustum frustum, int i, int i2, boolean z2);

        SectionRenderDispatcher.RenderSection getSection();

        AABB getAABB();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Octree$OctreeVisitor.class */
    @FunctionalInterface
    public interface OctreeVisitor {
        void visit(Node node, boolean z, int i, boolean z2);
    }

    public Octree(SectionPos $$0, int $$1, int $$2, int $$3) {
        int $$4 = ($$1 * 2) + 1;
        int $$5 = Mth.smallestEncompassingPowerOfTwo($$4);
        int $$6 = $$1 * 16;
        BlockPos $$7 = $$0.origin();
        this.cameraSectionCenter = $$0.center();
        int $$8 = $$7.getX() - $$6;
        int $$9 = ($$8 + ($$5 * 16)) - 1;
        int $$10 = $$5 >= $$2 ? $$3 : $$7.getY() - $$6;
        int $$11 = ($$10 + ($$5 * 16)) - 1;
        int $$12 = $$7.getZ() - $$6;
        int $$13 = ($$12 + ($$5 * 16)) - 1;
        this.root = new Branch(new BoundingBox($$8, $$10, $$12, $$9, $$11, $$13));
    }

    public boolean add(SectionRenderDispatcher.RenderSection $$0) {
        return this.root.add($$0);
    }

    public void visitNodes(OctreeVisitor $$0, Frustum $$1, int $$2) {
        this.root.visitNodes($$0, false, $$1, 0, $$2, true);
    }

    boolean isClose(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, int $$6) {
        int $$7 = this.cameraSectionCenter.getX();
        int $$8 = this.cameraSectionCenter.getY();
        int $$9 = this.cameraSectionCenter.getZ();
        return ((double) $$7) > $$0 - ((double) $$6) && ((double) $$7) < $$3 + ((double) $$6) && ((double) $$8) > $$1 - ((double) $$6) && ((double) $$8) < $$4 + ((double) $$6) && ((double) $$9) > $$2 - ((double) $$6) && ((double) $$9) < $$5 + ((double) $$6);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Octree$Branch.class */
    class Branch implements Node {
        private final Node[] nodes = new Node[8];
        private final BoundingBox boundingBox;
        private final int bbCenterX;
        private final int bbCenterY;
        private final int bbCenterZ;
        private final AxisSorting sorting;
        private final boolean cameraXDiffNegative;
        private final boolean cameraYDiffNegative;
        private final boolean cameraZDiffNegative;

        public Branch(BoundingBox $$0) {
            this.boundingBox = $$0;
            this.bbCenterX = this.boundingBox.minX() + (this.boundingBox.getXSpan() / 2);
            this.bbCenterY = this.boundingBox.minY() + (this.boundingBox.getYSpan() / 2);
            this.bbCenterZ = this.boundingBox.minZ() + (this.boundingBox.getZSpan() / 2);
            int $$1 = Octree.this.cameraSectionCenter.getX() - this.bbCenterX;
            int $$2 = Octree.this.cameraSectionCenter.getY() - this.bbCenterY;
            int $$3 = Octree.this.cameraSectionCenter.getZ() - this.bbCenterZ;
            this.sorting = AxisSorting.getAxisSorting(Math.abs($$1), Math.abs($$2), Math.abs($$3));
            this.cameraXDiffNegative = $$1 < 0;
            this.cameraYDiffNegative = $$2 < 0;
            this.cameraZDiffNegative = $$3 < 0;
        }

        public boolean add(SectionRenderDispatcher.RenderSection $$0) {
            long $$1 = $$0.getSectionNode();
            boolean $$2 = SectionPos.sectionToBlockCoord(SectionPos.x($$1)) - this.bbCenterX < 0;
            boolean $$3 = SectionPos.sectionToBlockCoord(SectionPos.y($$1)) - this.bbCenterY < 0;
            boolean $$4 = SectionPos.sectionToBlockCoord(SectionPos.z($$1)) - this.bbCenterZ < 0;
            boolean $$5 = $$2 != this.cameraXDiffNegative;
            boolean $$6 = $$3 != this.cameraYDiffNegative;
            boolean $$7 = $$4 != this.cameraZDiffNegative;
            int $$8 = getNodeIndex(this.sorting, $$5, $$6, $$7);
            if (areChildrenLeaves()) {
                boolean $$9 = this.nodes[$$8] != null;
                this.nodes[$$8] = Octree.this.new Leaf($$0);
                return !$$9;
            }
            if (this.nodes[$$8] != null) {
                Branch $$10 = (Branch) this.nodes[$$8];
                return $$10.add($$0);
            }
            BoundingBox $$11 = createChildBoundingBox($$2, $$3, $$4);
            Branch $$12 = Octree.this.new Branch($$11);
            this.nodes[$$8] = $$12;
            return $$12.add($$0);
        }

        private static int getNodeIndex(AxisSorting $$0, boolean $$1, boolean $$2, boolean $$3) {
            int $$4 = 0;
            if ($$1) {
                $$4 = 0 + $$0.xShift;
            }
            if ($$2) {
                $$4 += $$0.yShift;
            }
            if ($$3) {
                $$4 += $$0.zShift;
            }
            return $$4;
        }

        private boolean areChildrenLeaves() {
            return this.boundingBox.getXSpan() == 32;
        }

        private BoundingBox createChildBoundingBox(boolean $$0, boolean $$1, boolean $$2) {
            int $$5;
            int $$6;
            int $$9;
            int $$10;
            int $$13;
            int $$14;
            if ($$0) {
                $$5 = this.boundingBox.minX();
                $$6 = this.bbCenterX - 1;
            } else {
                $$5 = this.bbCenterX;
                $$6 = this.boundingBox.maxX();
            }
            if ($$1) {
                $$9 = this.boundingBox.minY();
                $$10 = this.bbCenterY - 1;
            } else {
                $$9 = this.bbCenterY;
                $$10 = this.boundingBox.maxY();
            }
            if ($$2) {
                $$13 = this.boundingBox.minZ();
                $$14 = this.bbCenterZ - 1;
            } else {
                $$13 = this.bbCenterZ;
                $$14 = this.boundingBox.maxZ();
            }
            return new BoundingBox($$5, $$9, $$13, $$6, $$10, $$14);
        }

        @Override // net.minecraft.client.renderer.Octree.Node
        public void visitNodes(OctreeVisitor $$0, boolean $$1, Frustum $$2, int $$3, int $$4, boolean $$5) {
            boolean $$6 = $$1;
            if (!$$1) {
                int $$7 = $$2.cubeInFrustum(this.boundingBox);
                $$1 = $$7 == -2;
                $$6 = $$7 == -2 || $$7 == -1;
            }
            if ($$6) {
                boolean $$52 = $$5 && Octree.this.isClose((double) this.boundingBox.minX(), (double) this.boundingBox.minY(), (double) this.boundingBox.minZ(), (double) this.boundingBox.maxX(), (double) this.boundingBox.maxY(), (double) this.boundingBox.maxZ(), $$4);
                $$0.visit(this, $$1, $$3, $$52);
                for (Node $$8 : this.nodes) {
                    if ($$8 != null) {
                        $$8.visitNodes($$0, $$1, $$2, $$3 + 1, $$4, $$52);
                    }
                }
            }
        }

        @Override // net.minecraft.client.renderer.Octree.Node
        public SectionRenderDispatcher.RenderSection getSection() {
            return null;
        }

        @Override // net.minecraft.client.renderer.Octree.Node
        public AABB getAABB() {
            return new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() + 1, this.boundingBox.maxY() + 1, this.boundingBox.maxZ() + 1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Octree$Leaf.class */
    final class Leaf implements Node {
        private final SectionRenderDispatcher.RenderSection section;

        Leaf(SectionRenderDispatcher.RenderSection $$0) {
            this.section = $$0;
        }

        @Override // net.minecraft.client.renderer.Octree.Node
        public void visitNodes(OctreeVisitor $$0, boolean $$1, Frustum $$2, int $$3, int $$4, boolean $$5) {
            AABB $$6 = this.section.getBoundingBox();
            if ($$1 || $$2.isVisible(getSection().getBoundingBox())) {
                boolean $$52 = $$5 && Octree.this.isClose($$6.minX, $$6.minY, $$6.minZ, $$6.maxX, $$6.maxY, $$6.maxZ, $$4);
                $$0.visit(this, $$1, $$3, $$52);
            }
        }

        @Override // net.minecraft.client.renderer.Octree.Node
        public SectionRenderDispatcher.RenderSection getSection() {
            return this.section;
        }

        @Override // net.minecraft.client.renderer.Octree.Node
        public AABB getAABB() {
            return this.section.getBoundingBox();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Octree$AxisSorting.class */
    enum AxisSorting {
        XYZ(4, 2, 1),
        XZY(4, 1, 2),
        YXZ(2, 4, 1),
        YZX(1, 4, 2),
        ZXY(2, 1, 4),
        ZYX(1, 2, 4);

        final int xShift;
        final int yShift;
        final int zShift;

        AxisSorting(int $$0, int $$1, int $$2) {
            this.xShift = $$0;
            this.yShift = $$1;
            this.zShift = $$2;
        }

        public static AxisSorting getAxisSorting(int $$0, int $$1, int $$2) {
            if ($$0 > $$1 && $$0 > $$2) {
                if ($$1 > $$2) {
                    return XYZ;
                }
                return XZY;
            }
            if ($$1 > $$0 && $$1 > $$2) {
                if ($$0 > $$2) {
                    return YXZ;
                }
                return YZX;
            }
            if ($$0 > $$1) {
                return ZXY;
            }
            return ZYX;
        }
    }
}
