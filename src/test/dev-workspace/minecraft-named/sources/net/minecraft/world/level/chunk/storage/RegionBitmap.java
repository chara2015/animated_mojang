package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.BitSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/storage/RegionBitmap.class */
public class RegionBitmap {
    private final BitSet used = new BitSet();

    public void force(int $$0, int $$1) {
        this.used.set($$0, $$0 + $$1);
    }

    public void free(int $$0, int $$1) {
        this.used.clear($$0, $$0 + $$1);
    }

    public int allocate(int $$0) {
        int $$2;
        int i = 0;
        while (true) {
            int $$1 = i;
            $$2 = this.used.nextClearBit($$1);
            int $$3 = this.used.nextSetBit($$2);
            if ($$3 == -1 || $$3 - $$2 >= $$0) {
                break;
            }
            i = $$3;
        }
        force($$2, $$0);
        return $$2;
    }

    @VisibleForTesting
    public IntSet getUsed() {
        return (IntSet) this.used.stream().collect(IntArraySet::new, (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }
}
