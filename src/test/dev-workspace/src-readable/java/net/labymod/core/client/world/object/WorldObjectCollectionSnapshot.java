package net.labymod.core.client.world.object;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot;
import net.labymod.api.client.world.object.submit.WorldObjectSubmitter;
import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/object/WorldObjectCollectionSnapshot.class */
public final class WorldObjectCollectionSnapshot extends AbstractLabySnapshot implements Iterable<Entry> {
    private final List<Entry> entries;

    public WorldObjectCollectionSnapshot(@NotNull List<Entry> entries) {
        super(Extras.empty());
        this.entries = entries;
    }

    @NotNull
    public List<Entry> entries() {
        return this.entries;
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<Entry> iterator() {
        return this.entries.iterator();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry.class */
    public static final class Entry extends Record {

        @NotNull
        private final WorldObjectSnapshot snapshot;

        @NotNull
        private final WorldObjectSubmitter<?, ?> submitter;

        @Nullable
        private final AxisAlignedBoundingBox debugCullBox;

        public Entry(@NotNull WorldObjectSnapshot snapshot, @NotNull WorldObjectSubmitter<?, ?> submitter, @Nullable AxisAlignedBoundingBox debugCullBox) {
            this.snapshot = snapshot;
            this.submitter = submitter;
            this.debugCullBox = debugCullBox;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Entry.class), Entry.class, "snapshot;submitter;debugCullBox", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->snapshot:Lnet/labymod/api/client/world/object/snapshot/WorldObjectSnapshot;", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->submitter:Lnet/labymod/api/client/world/object/submit/WorldObjectSubmitter;", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->debugCullBox:Lnet/labymod/api/util/math/AxisAlignedBoundingBox;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Entry.class), Entry.class, "snapshot;submitter;debugCullBox", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->snapshot:Lnet/labymod/api/client/world/object/snapshot/WorldObjectSnapshot;", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->submitter:Lnet/labymod/api/client/world/object/submit/WorldObjectSubmitter;", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->debugCullBox:Lnet/labymod/api/util/math/AxisAlignedBoundingBox;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Entry.class, Object.class), Entry.class, "snapshot;submitter;debugCullBox", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->snapshot:Lnet/labymod/api/client/world/object/snapshot/WorldObjectSnapshot;", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->submitter:Lnet/labymod/api/client/world/object/submit/WorldObjectSubmitter;", "FIELD:Lnet/labymod/core/client/world/object/WorldObjectCollectionSnapshot$Entry;->debugCullBox:Lnet/labymod/api/util/math/AxisAlignedBoundingBox;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @NotNull
        public WorldObjectSnapshot snapshot() {
            return this.snapshot;
        }

        @NotNull
        public WorldObjectSubmitter<?, ?> submitter() {
            return this.submitter;
        }

        @Nullable
        public AxisAlignedBoundingBox debugCullBox() {
            return this.debugCullBox;
        }
    }
}
