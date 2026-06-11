package net.labymod.api.client.gui.screen.state;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/LayerSpatialHash.class */
@ApiStatus.Internal
final class LayerSpatialHash {
    private final Context context;
    private final Long2ObjectMap<List<GuiComponent>> cells = new Long2ObjectOpenHashMap();
    private final List<GuiComponent> linearFallback = new ArrayList();

    LayerSpatialHash(Context context) {
        this.context = context;
    }

    void insert(GuiComponent component, Rectangle bounds) {
        if (bounds == null) {
            return;
        }
        if (!this.context.enabled()) {
            this.linearFallback.add(component);
            return;
        }
        int cs = this.context.cellSize();
        int minCx = minCell(bounds.getLeft(), cs);
        int maxCx = maxCell(bounds.getRight(), cs);
        int minCy = minCell(bounds.getTop(), cs);
        int maxCy = maxCell(bounds.getBottom(), cs);
        Stats stats = this.context.stats();
        stats.inserts++;
        for (int y = minCy; y <= maxCy; y++) {
            for (int x = minCx; x <= maxCx; x++) {
                writeToCell(pack(x, y), component, stats);
            }
        }
    }

    boolean hasIntersection(Rectangle bounds) {
        if (!this.context.enabled()) {
            return linearScan(bounds);
        }
        int cs = this.context.cellSize();
        int minCx = minCell(bounds.getLeft(), cs);
        int maxCx = maxCell(bounds.getRight(), cs);
        int minCy = minCell(bounds.getTop(), cs);
        int maxCy = maxCell(bounds.getBottom(), cs);
        Stats stats = this.context.stats();
        stats.queries++;
        for (int y = minCy; y <= maxCy; y++) {
            for (int x = minCx; x <= maxCx; x++) {
                stats.cellReads++;
                List<GuiComponent> bucket = (List) this.cells.get(pack(x, y));
                if (bucket != null && bucketIntersects(bucket, bounds, stats)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void writeToCell(long key, GuiComponent component, Stats stats) {
        List<GuiComponent> bucket = (List) this.cells.get(key);
        if (bucket == null) {
            bucket = new ArrayList<>();
            this.cells.put(key, bucket);
        }
        bucket.add(component);
        stats.cellWrites++;
        if (bucket.size() > stats.maxItemsPerCell) {
            stats.maxItemsPerCell = bucket.size();
        }
    }

    private boolean bucketIntersects(List<GuiComponent> bucket, Rectangle bounds, Stats stats) {
        for (GuiComponent guiComponent : bucket) {
            stats.componentChecks++;
            Rectangle stateBounds = guiComponent.bounds();
            if (stateBounds != null && stateBounds.intersects(bounds)) {
                return true;
            }
        }
        return false;
    }

    private boolean linearScan(Rectangle bounds) {
        Stats stats = this.context.stats();
        stats.queries++;
        return bucketIntersects(this.linearFallback, bounds, stats);
    }

    private static int minCell(float value, int cs) {
        return (int) Math.floor(value / cs);
    }

    private static int maxCell(float exclusive, int cs) {
        return ((int) Math.ceil(exclusive / cs)) - 1;
    }

    private static long pack(int x, int y) {
        return (((long) x) << 32) | (((long) y) & 4294967295L);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/LayerSpatialHash$Context.class */
    @ApiStatus.Internal
    static final class Context {
        private static final int DEFAULT_CELL_SIZE = 128;
        private final Stats stats = new Stats();
        private int cellSize = 128;
        private boolean enabled = true;

        Context() {
        }

        int cellSize() {
            return this.cellSize;
        }

        void setCellSize(int cellSize) {
            this.cellSize = cellSize;
        }

        boolean enabled() {
            return this.enabled;
        }

        void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        Stats stats() {
            return this.stats;
        }

        void resetStats() {
            this.stats.reset();
        }

        String summarize() {
            Stats s = this.stats;
            float avgCellsPerInsert = s.inserts == 0 ? 0.0f : s.cellWrites / s.inserts;
            float avgChecksPerQuery = s.queries == 0 ? 0.0f : s.componentChecks / s.queries;
            return "[LayerSpatialHash cellSize=" + this.cellSize + " enabled=" + this.enabled + " inserts=" + s.inserts + " cellWrites=" + s.cellWrites + " avgCellsPerInsert=" + String.format("%.2f", Float.valueOf(avgCellsPerInsert)) + " queries=" + s.queries + " cellReads=" + s.cellReads + " componentChecks=" + s.componentChecks + " avgChecksPerQuery=" + String.format("%.2f", Float.valueOf(avgChecksPerQuery)) + " maxItemsPerCell=" + s.maxItemsPerCell + " maxChainDepth=" + s.maxChainDepth + "]";
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/LayerSpatialHash$Stats.class */
    @ApiStatus.Internal
    static final class Stats {
        int inserts;
        int cellWrites;
        int queries;
        int cellReads;
        int componentChecks;
        int maxItemsPerCell;
        int maxChainDepth;

        Stats() {
        }

        void recordChainDepth(int depth) {
            if (depth > this.maxChainDepth) {
                this.maxChainDepth = depth;
            }
        }

        void reset() {
            this.inserts = 0;
            this.cellWrites = 0;
            this.queries = 0;
            this.cellReads = 0;
            this.componentChecks = 0;
            this.maxItemsPerCell = 0;
            this.maxChainDepth = 0;
        }
    }
}
