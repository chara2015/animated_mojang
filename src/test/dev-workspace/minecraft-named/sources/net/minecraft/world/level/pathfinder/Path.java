package net.minecraft.world.level.pathfinder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/pathfinder/Path.class */
public final class Path {
    public static final StreamCodec<FriendlyByteBuf, Path> STREAM_CODEC = StreamCodec.of(($$0, $$1) -> {
        $$1.writeToStream($$0);
    }, Path::createFromStream);
    private final List<Node> nodes;
    private DebugData debugData;
    private int nextNodeIndex;
    private final BlockPos target;
    private final float distToTarget;
    private final boolean reached;

    public Path(List<Node> $$0, BlockPos $$1, boolean $$2) {
        this.nodes = $$0;
        this.target = $$1;
        this.distToTarget = $$0.isEmpty() ? Float.MAX_VALUE : this.nodes.get(this.nodes.size() - 1).distanceManhattan(this.target);
        this.reached = $$2;
    }

    public void advance() {
        this.nextNodeIndex++;
    }

    public boolean notStarted() {
        return this.nextNodeIndex <= 0;
    }

    public boolean isDone() {
        return this.nextNodeIndex >= this.nodes.size();
    }

    public Node getEndNode() {
        if (!this.nodes.isEmpty()) {
            return this.nodes.get(this.nodes.size() - 1);
        }
        return null;
    }

    public Node getNode(int $$0) {
        return this.nodes.get($$0);
    }

    public void truncateNodes(int $$0) {
        if (this.nodes.size() > $$0) {
            this.nodes.subList($$0, this.nodes.size()).clear();
        }
    }

    public void replaceNode(int $$0, Node $$1) {
        this.nodes.set($$0, $$1);
    }

    public int getNodeCount() {
        return this.nodes.size();
    }

    public int getNextNodeIndex() {
        return this.nextNodeIndex;
    }

    public void setNextNodeIndex(int $$0) {
        this.nextNodeIndex = $$0;
    }

    public Vec3 getEntityPosAtNode(Entity $$0, int $$1) {
        Node $$2 = this.nodes.get($$1);
        double $$3 = ((double) $$2.x) + (((double) ((int) ($$0.getBbWidth() + 1.0f))) * 0.5d);
        double $$4 = $$2.y;
        double $$5 = ((double) $$2.z) + (((double) ((int) ($$0.getBbWidth() + 1.0f))) * 0.5d);
        return new Vec3($$3, $$4, $$5);
    }

    public BlockPos getNodePos(int $$0) {
        return this.nodes.get($$0).asBlockPos();
    }

    public Vec3 getNextEntityPos(Entity $$0) {
        return getEntityPosAtNode($$0, this.nextNodeIndex);
    }

    public BlockPos getNextNodePos() {
        return this.nodes.get(this.nextNodeIndex).asBlockPos();
    }

    public Node getNextNode() {
        return this.nodes.get(this.nextNodeIndex);
    }

    public Node getPreviousNode() {
        if (this.nextNodeIndex > 0) {
            return this.nodes.get(this.nextNodeIndex - 1);
        }
        return null;
    }

    public boolean sameAs(Path $$0) {
        return $$0 != null && this.nodes.equals($$0.nodes);
    }

    public boolean equals(Object $$0) {
        if (!($$0 instanceof Path)) {
            return false;
        }
        Path $$2 = (Path) $$0;
        return this.nextNodeIndex == $$2.nextNodeIndex && this.debugData == $$2.debugData && this.reached == $$2.reached && this.target.equals($$2.target) && this.nodes.equals($$2.nodes);
    }

    public int hashCode() {
        return this.nextNodeIndex + (this.nodes.hashCode() * 31);
    }

    public boolean canReach() {
        return this.reached;
    }

    @VisibleForDebug
    void setDebug(Node[] $$0, Node[] $$1, Set<Target> $$2) {
        this.debugData = new DebugData($$0, $$1, $$2);
    }

    public DebugData debugData() {
        return this.debugData;
    }

    public void writeToStream(FriendlyByteBuf $$0) {
        if (this.debugData == null || this.debugData.targetNodes.isEmpty()) {
            throw new IllegalStateException("Missing debug data");
        }
        $$0.m1594writeBoolean(this.reached);
        $$0.m1588writeInt(this.nextNodeIndex);
        $$0.writeBlockPos(this.target);
        $$0.writeCollection(this.nodes, ($$02, $$1) -> {
            $$1.writeToStream($$02);
        });
        this.debugData.write($$0);
    }

    public static Path createFromStream(FriendlyByteBuf $$0) {
        boolean $$1 = $$0.readBoolean();
        int $$2 = $$0.readInt();
        BlockPos $$3 = $$0.readBlockPos();
        List<Node> $$4 = $$0.readList(Node::createFromStream);
        DebugData $$5 = DebugData.read($$0);
        Path $$6 = new Path($$4, $$3, $$1);
        $$6.debugData = $$5;
        $$6.nextNodeIndex = $$2;
        return $$6;
    }

    public String toString() {
        return "Path(length=" + this.nodes.size() + ")";
    }

    public BlockPos getTarget() {
        return this.target;
    }

    public float getDistToTarget() {
        return this.distToTarget;
    }

    static Node[] readNodeArray(FriendlyByteBuf $$0) {
        Node[] $$1 = new Node[$$0.readVarInt()];
        for (int $$2 = 0; $$2 < $$1.length; $$2++) {
            $$1[$$2] = Node.createFromStream($$0);
        }
        return $$1;
    }

    static void writeNodeArray(FriendlyByteBuf $$0, Node[] $$1) {
        $$0.writeVarInt($$1.length);
        for (Node $$2 : $$1) {
            $$2.writeToStream($$0);
        }
    }

    public Path copy() {
        Path $$0 = new Path(this.nodes, this.target, this.reached);
        $$0.debugData = this.debugData;
        $$0.nextNodeIndex = this.nextNodeIndex;
        return $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/pathfinder/Path$DebugData.class */
    public static final class DebugData extends Record {
        private final Node[] openSet;
        private final Node[] closedSet;
        private final Set<Target> targetNodes;

        public DebugData(Node[] $$0, Node[] $$1, Set<Target> $$2) {
            this.openSet = $$0;
            this.closedSet = $$1;
            this.targetNodes = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DebugData.class), DebugData.class, "openSet;closedSet;targetNodes", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->openSet:[Lnet/minecraft/world/level/pathfinder/Node;", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->closedSet:[Lnet/minecraft/world/level/pathfinder/Node;", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->targetNodes:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DebugData.class), DebugData.class, "openSet;closedSet;targetNodes", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->openSet:[Lnet/minecraft/world/level/pathfinder/Node;", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->closedSet:[Lnet/minecraft/world/level/pathfinder/Node;", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->targetNodes:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DebugData.class, Object.class), DebugData.class, "openSet;closedSet;targetNodes", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->openSet:[Lnet/minecraft/world/level/pathfinder/Node;", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->closedSet:[Lnet/minecraft/world/level/pathfinder/Node;", "FIELD:Lnet/minecraft/world/level/pathfinder/Path$DebugData;->targetNodes:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Node[] openSet() {
            return this.openSet;
        }

        public Node[] closedSet() {
            return this.closedSet;
        }

        public Set<Target> targetNodes() {
            return this.targetNodes;
        }

        public void write(FriendlyByteBuf $$0) {
            $$0.writeCollection(this.targetNodes, ($$02, $$1) -> {
                $$1.writeToStream($$02);
            });
            Path.writeNodeArray($$0, this.openSet);
            Path.writeNodeArray($$0, this.closedSet);
        }

        public static DebugData read(FriendlyByteBuf $$0) {
            HashSet<Target> $$1 = (HashSet) $$0.readCollection(HashSet::new, Target::createFromStream);
            Node[] $$2 = Path.readNodeArray($$0);
            Node[] $$3 = Path.readNodeArray($$0);
            return new DebugData($$2, $$3, $$1);
        }
    }
}
