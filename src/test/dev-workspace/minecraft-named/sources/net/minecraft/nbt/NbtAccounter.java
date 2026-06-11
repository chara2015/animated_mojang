package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/NbtAccounter.class */
public class NbtAccounter {
    public static final int DEFAULT_NBT_QUOTA = 2097152;
    public static final int UNCOMPRESSED_NBT_QUOTA = 104857600;
    private static final int MAX_STACK_DEPTH = 512;
    private final long quota;
    private long usage;
    private final int maxDepth;
    private int depth;

    public NbtAccounter(long $$0, int $$1) {
        this.quota = $$0;
        this.maxDepth = $$1;
    }

    public static NbtAccounter create(long $$0) {
        return new NbtAccounter($$0, 512);
    }

    public static NbtAccounter defaultQuota() {
        return new NbtAccounter(2097152L, 512);
    }

    public static NbtAccounter uncompressedQuota() {
        return new NbtAccounter(104857600L, 512);
    }

    public static NbtAccounter unlimitedHeap() {
        return new NbtAccounter(DynamicGraphMinFixedPoint.SOURCE, 512);
    }

    public void accountBytes(long $$0, long $$1) {
        accountBytes($$0 * $$1);
    }

    public void accountBytes(long $$0) {
        if ($$0 < 0) {
            throw new IllegalArgumentException("Tried to account NBT tag with negative size: " + $$0);
        }
        if (this.usage + $$0 > this.quota) {
            long j = this.usage;
            long j2 = this.quota;
            NbtAccounterException nbtAccounterException = new NbtAccounterException("Tried to read NBT tag that was too big; tried to allocate: " + j + " + " + nbtAccounterException + " bytes where max allowed: " + $$0);
            throw nbtAccounterException;
        }
        this.usage += $$0;
    }

    public void pushDepth() {
        if (this.depth >= this.maxDepth) {
            throw new NbtAccounterException("Tried to read NBT tag with too high complexity, depth > " + this.maxDepth);
        }
        this.depth++;
    }

    public void popDepth() {
        if (this.depth <= 0) {
            throw new NbtAccounterException("NBT-Accounter tried to pop stack-depth at top-level");
        }
        this.depth--;
    }

    @VisibleForTesting
    public long getUsage() {
        return this.usage;
    }

    @VisibleForTesting
    public int getDepth() {
        return this.depth;
    }
}
