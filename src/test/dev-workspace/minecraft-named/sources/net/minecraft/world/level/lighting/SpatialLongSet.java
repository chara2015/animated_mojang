package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.util.NoSuchElementException;
import net.minecraft.util.Mth;
import net.minecraft.world.waypoints.Waypoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/SpatialLongSet.class */
public class SpatialLongSet extends LongLinkedOpenHashSet {
    private final InternalMap map;

    public SpatialLongSet(int $$0, float $$1) {
        super($$0, $$1);
        this.map = new InternalMap($$0 / 64, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/SpatialLongSet$InternalMap.class */
    protected static class InternalMap extends Long2LongLinkedOpenHashMap {
        private static final int Y_OFFSET = 0;
        private int lastPos;
        private long lastOuterKey;
        private final int minSize;
        private static final int X_BITS = Mth.log2(Waypoint.MAX_RANGE);
        private static final int Z_BITS = Mth.log2(Waypoint.MAX_RANGE);
        private static final int Y_BITS = (64 - X_BITS) - Z_BITS;
        private static final int Z_OFFSET = Y_BITS;
        private static final int X_OFFSET = Y_BITS + Z_BITS;
        private static final long OUTER_MASK = ((3 << X_OFFSET) | 3) | (3 << Z_OFFSET);

        public InternalMap(int $$0, float $$1) {
            super($$0, $$1);
            this.lastPos = -1;
            this.minSize = $$0;
        }

        static long getOuterKey(long $$0) {
            return $$0 & (OUTER_MASK ^ (-1));
        }

        static int getInnerKey(long $$0) {
            int $$1 = (int) (($$0 >>> X_OFFSET) & 3);
            int $$2 = (int) (($$0 >>> 0) & 3);
            int $$3 = (int) (($$0 >>> Z_OFFSET) & 3);
            return ($$1 << 4) | ($$3 << 2) | $$2;
        }

        static long getFullKey(long $$0, int $$1) {
            return $$0 | (((long) (($$1 >>> 4) & 3)) << X_OFFSET) | (((long) (($$1 >>> 2) & 3)) << Z_OFFSET) | (((long) (($$1 >>> 0) & 3)) << 0);
        }

        public boolean addBit(long $$0) {
            int $$6;
            long $$1 = getOuterKey($$0);
            int $$2 = getInnerKey($$0);
            long $$3 = 1 << $$2;
            if ($$1 == 0) {
                if (this.containsNullKey) {
                    return replaceBit(this.n, $$3);
                }
                this.containsNullKey = true;
                $$6 = this.n;
            } else {
                if (this.lastPos != -1 && $$1 == this.lastOuterKey) {
                    return replaceBit(this.lastPos, $$3);
                }
                long[] $$5 = this.key;
                $$6 = ((int) HashCommon.mix($$1)) & this.mask;
                long j = $$5[$$6];
                while (true) {
                    long $$7 = j;
                    if ($$7 == 0) {
                        break;
                    }
                    if ($$7 == $$1) {
                        this.lastPos = $$6;
                        this.lastOuterKey = $$1;
                        return replaceBit($$6, $$3);
                    }
                    $$6 = ($$6 + 1) & this.mask;
                    j = $$5[$$6];
                }
            }
            this.key[$$6] = $$1;
            this.value[$$6] = $$3;
            if (this.size == 0) {
                int i = $$6;
                this.last = i;
                this.first = i;
                this.link[$$6] = -1;
            } else {
                long[] jArr = this.link;
                int i2 = this.last;
                jArr[i2] = jArr[i2] ^ ((this.link[this.last] ^ (((long) $$6) & 4294967295L)) & 4294967295L);
                this.link[$$6] = ((((long) this.last) & 4294967295L) << 32) | 4294967295L;
                this.last = $$6;
            }
            int i3 = this.size;
            this.size = i3 + 1;
            if (i3 >= this.maxFill) {
                rehash(HashCommon.arraySize(this.size + 1, this.f));
                return false;
            }
            return false;
        }

        private boolean replaceBit(int $$0, long $$1) {
            boolean $$2 = (this.value[$$0] & $$1) != 0;
            long[] jArr = this.value;
            jArr[$$0] = jArr[$$0] | $$1;
            return $$2;
        }

        public boolean removeBit(long $$0) {
            long $$1 = getOuterKey($$0);
            int $$2 = getInnerKey($$0);
            long $$3 = 1 << $$2;
            if ($$1 == 0) {
                if (this.containsNullKey) {
                    return removeFromNullEntry($$3);
                }
                return false;
            }
            if (this.lastPos != -1 && $$1 == this.lastOuterKey) {
                return removeFromEntry(this.lastPos, $$3);
            }
            long[] $$4 = this.key;
            int $$5 = ((int) HashCommon.mix($$1)) & this.mask;
            long j = $$4[$$5];
            while (true) {
                long $$6 = j;
                if ($$6 == 0) {
                    return false;
                }
                if ($$1 == $$6) {
                    this.lastPos = $$5;
                    this.lastOuterKey = $$1;
                    return removeFromEntry($$5, $$3);
                }
                $$5 = ($$5 + 1) & this.mask;
                j = $$4[$$5];
            }
        }

        private boolean removeFromNullEntry(long $$0) {
            if ((this.value[this.n] & $$0) == 0) {
                return false;
            }
            long[] jArr = this.value;
            int i = this.n;
            jArr[i] = jArr[i] & ($$0 ^ (-1));
            if (this.value[this.n] != 0) {
                return true;
            }
            this.containsNullKey = false;
            this.size--;
            fixPointers(this.n);
            if (this.size < this.maxFill / 4 && this.n > 16) {
                rehash(this.n / 2);
                return true;
            }
            return true;
        }

        private boolean removeFromEntry(int $$0, long $$1) {
            if ((this.value[$$0] & $$1) == 0) {
                return false;
            }
            long[] jArr = this.value;
            jArr[$$0] = jArr[$$0] & ($$1 ^ (-1));
            if (this.value[$$0] != 0) {
                return true;
            }
            this.lastPos = -1;
            this.size--;
            fixPointers($$0);
            shiftKeys($$0);
            if (this.size < this.maxFill / 4 && this.n > 16) {
                rehash(this.n / 2);
                return true;
            }
            return true;
        }

        public long removeFirstBit() {
            if (this.size == 0) {
                throw new NoSuchElementException();
            }
            int $$0 = this.first;
            long $$1 = this.key[$$0];
            int $$2 = Long.numberOfTrailingZeros(this.value[$$0]);
            long[] jArr = this.value;
            jArr[$$0] = jArr[$$0] & ((1 << $$2) ^ (-1));
            if (this.value[$$0] == 0) {
                removeFirstLong();
                this.lastPos = -1;
            }
            return getFullKey($$1, $$2);
        }

        protected void rehash(int $$0) {
            if ($$0 > this.minSize) {
                super.rehash($$0);
            }
        }
    }

    public boolean add(long $$0) {
        return this.map.addBit($$0);
    }

    public boolean rem(long $$0) {
        return this.map.removeBit($$0);
    }

    public long removeFirstLong() {
        return this.map.removeFirstBit();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }
}
