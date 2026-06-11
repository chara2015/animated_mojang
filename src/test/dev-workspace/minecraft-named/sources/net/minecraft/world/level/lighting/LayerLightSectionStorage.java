package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.DataLayerStorageMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/LayerLightSectionStorage.class */
public abstract class LayerLightSectionStorage<M extends DataLayerStorageMap<M>> {
    private final LightLayer layer;
    protected final LightChunkGetter chunkSource;
    protected volatile M visibleSectionData;
    protected final M updatingSectionData;
    protected volatile boolean hasInconsistencies;
    protected final Long2ByteMap sectionStates = new Long2ByteOpenHashMap();
    private final LongSet columnsWithSources = new LongOpenHashSet();
    protected final LongSet changedSections = new LongOpenHashSet();
    protected final LongSet sectionsAffectedByLightUpdates = new LongOpenHashSet();
    protected final Long2ObjectMap<DataLayer> queuedSections = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());
    private final LongSet columnsToRetainQueuedDataFor = new LongOpenHashSet();
    private final LongSet toRemove = new LongOpenHashSet();

    protected abstract int getLightValue(long j);

    protected LayerLightSectionStorage(LightLayer lightLayer, LightChunkGetter lightChunkGetter, M m) {
        this.layer = lightLayer;
        this.chunkSource = lightChunkGetter;
        this.updatingSectionData = m;
        this.visibleSectionData = (M) m.copy();
        this.visibleSectionData.disableCache();
        this.sectionStates.defaultReturnValue((byte) 0);
    }

    protected boolean storingLightForSection(long $$0) {
        return getDataLayer($$0, true) != null;
    }

    protected DataLayer getDataLayer(long $$0, boolean $$1) {
        return getDataLayer($$1 ? this.updatingSectionData : this.visibleSectionData, $$0);
    }

    protected DataLayer getDataLayer(M $$0, long $$1) {
        return $$0.getLayer($$1);
    }

    protected DataLayer getDataLayerToWrite(long $$0) {
        DataLayer $$1 = this.updatingSectionData.getLayer($$0);
        if ($$1 == null) {
            return null;
        }
        if (this.changedSections.add($$0)) {
            $$1 = $$1.copy();
            this.updatingSectionData.setLayer($$0, $$1);
            this.updatingSectionData.clearCache();
        }
        return $$1;
    }

    public DataLayer getDataLayerData(long $$0) {
        DataLayer $$1 = (DataLayer) this.queuedSections.get($$0);
        if ($$1 != null) {
            return $$1;
        }
        return getDataLayer($$0, false);
    }

    protected int getStoredLevel(long $$0) {
        long $$1 = SectionPos.blockToSection($$0);
        DataLayer $$2 = getDataLayer($$1, true);
        return $$2.get(SectionPos.sectionRelative(BlockPos.getX($$0)), SectionPos.sectionRelative(BlockPos.getY($$0)), SectionPos.sectionRelative(BlockPos.getZ($$0)));
    }

    protected void setStoredLevel(long $$0, int $$1) {
        DataLayer $$4;
        long $$2 = SectionPos.blockToSection($$0);
        if (this.changedSections.add($$2)) {
            $$4 = this.updatingSectionData.copyDataLayer($$2);
        } else {
            $$4 = getDataLayer($$2, true);
        }
        $$4.set(SectionPos.sectionRelative(BlockPos.getX($$0)), SectionPos.sectionRelative(BlockPos.getY($$0)), SectionPos.sectionRelative(BlockPos.getZ($$0)), $$1);
        LongSet longSet = this.sectionsAffectedByLightUpdates;
        Objects.requireNonNull(longSet);
        SectionPos.aroundAndAtBlockPos($$0, longSet::add);
    }

    protected void markSectionAndNeighborsAsAffected(long $$0) {
        int $$1 = SectionPos.x($$0);
        int $$2 = SectionPos.y($$0);
        int $$3 = SectionPos.z($$0);
        for (int $$4 = -1; $$4 <= 1; $$4++) {
            for (int $$5 = -1; $$5 <= 1; $$5++) {
                for (int $$6 = -1; $$6 <= 1; $$6++) {
                    this.sectionsAffectedByLightUpdates.add(SectionPos.asLong($$1 + $$5, $$2 + $$6, $$3 + $$4));
                }
            }
        }
    }

    protected DataLayer createDataLayer(long $$0) {
        DataLayer $$1 = (DataLayer) this.queuedSections.get($$0);
        if ($$1 != null) {
            return $$1;
        }
        return new DataLayer();
    }

    protected boolean hasInconsistencies() {
        return this.hasInconsistencies;
    }

    protected void markNewInconsistencies(LightEngine<M, ?> $$0) {
        if (!this.hasInconsistencies) {
            return;
        }
        this.hasInconsistencies = false;
        LongIterator it = this.toRemove.iterator();
        while (it.hasNext()) {
            long $$1 = ((Long) it.next()).longValue();
            DataLayer $$2 = (DataLayer) this.queuedSections.remove($$1);
            DataLayer $$3 = this.updatingSectionData.removeLayer($$1);
            if (this.columnsToRetainQueuedDataFor.contains(SectionPos.getZeroNode($$1))) {
                if ($$2 != null) {
                    this.queuedSections.put($$1, $$2);
                } else if ($$3 != null) {
                    this.queuedSections.put($$1, $$3);
                }
            }
        }
        this.updatingSectionData.clearCache();
        LongIterator it2 = this.toRemove.iterator();
        while (it2.hasNext()) {
            long $$4 = ((Long) it2.next()).longValue();
            onNodeRemoved($$4);
            this.changedSections.add($$4);
        }
        this.toRemove.clear();
        ObjectIterator<Long2ObjectMap.Entry<DataLayer>> $$5 = Long2ObjectMaps.fastIterator(this.queuedSections);
        while ($$5.hasNext()) {
            Long2ObjectMap.Entry<DataLayer> $$6 = (Long2ObjectMap.Entry) $$5.next();
            long $$7 = $$6.getLongKey();
            if (storingLightForSection($$7)) {
                DataLayer $$8 = (DataLayer) $$6.getValue();
                if (this.updatingSectionData.getLayer($$7) != $$8) {
                    this.updatingSectionData.setLayer($$7, $$8);
                    this.changedSections.add($$7);
                }
                $$5.remove();
            }
        }
        this.updatingSectionData.clearCache();
    }

    protected void onNodeAdded(long $$0) {
    }

    protected void onNodeRemoved(long $$0) {
    }

    protected void setLightEnabled(long $$0, boolean $$1) {
        if ($$1) {
            this.columnsWithSources.add($$0);
        } else {
            this.columnsWithSources.remove($$0);
        }
    }

    protected boolean lightOnInSection(long $$0) {
        long $$1 = SectionPos.getZeroNode($$0);
        return this.columnsWithSources.contains($$1);
    }

    protected boolean lightOnInColumn(long $$0) {
        return this.columnsWithSources.contains($$0);
    }

    public void retainData(long $$0, boolean $$1) {
        if ($$1) {
            this.columnsToRetainQueuedDataFor.add($$0);
        } else {
            this.columnsToRetainQueuedDataFor.remove($$0);
        }
    }

    protected void queueSectionData(long $$0, DataLayer $$1) {
        if ($$1 != null) {
            this.queuedSections.put($$0, $$1);
            this.hasInconsistencies = true;
        } else {
            this.queuedSections.remove($$0);
        }
    }

    protected void updateSectionStatus(long $$0, boolean $$1) {
        byte $$2 = this.sectionStates.get($$0);
        byte $$3 = SectionState.hasData($$2, !$$1);
        if ($$2 == $$3) {
            return;
        }
        putSectionState($$0, $$3);
        int $$4 = $$1 ? -1 : 1;
        for (int $$5 = -1; $$5 <= 1; $$5++) {
            for (int $$6 = -1; $$6 <= 1; $$6++) {
                for (int $$7 = -1; $$7 <= 1; $$7++) {
                    if ($$5 != 0 || $$6 != 0 || $$7 != 0) {
                        long $$8 = SectionPos.offset($$0, $$5, $$6, $$7);
                        byte $$9 = this.sectionStates.get($$8);
                        putSectionState($$8, SectionState.neighborCount($$9, SectionState.neighborCount($$9) + $$4));
                    }
                }
            }
        }
    }

    protected void putSectionState(long $$0, byte $$1) {
        if ($$1 != 0) {
            if (this.sectionStates.put($$0, $$1) == 0) {
                initializeSection($$0);
            }
        } else if (this.sectionStates.remove($$0) != 0) {
            removeSection($$0);
        }
    }

    private void initializeSection(long $$0) {
        if (!this.toRemove.remove($$0)) {
            this.updatingSectionData.setLayer($$0, createDataLayer($$0));
            this.changedSections.add($$0);
            onNodeAdded($$0);
            markSectionAndNeighborsAsAffected($$0);
            this.hasInconsistencies = true;
        }
    }

    private void removeSection(long $$0) {
        this.toRemove.add($$0);
        this.hasInconsistencies = true;
    }

    protected void swapSectionMap() {
        if (!this.changedSections.isEmpty()) {
            M m = (M) this.updatingSectionData.copy();
            m.disableCache();
            this.visibleSectionData = m;
            this.changedSections.clear();
        }
        if (!this.sectionsAffectedByLightUpdates.isEmpty()) {
            LongIterator it = this.sectionsAffectedByLightUpdates.iterator();
            while (it.hasNext()) {
                this.chunkSource.onLightUpdate(this.layer, SectionPos.of(it.nextLong()));
            }
            this.sectionsAffectedByLightUpdates.clear();
        }
    }

    public SectionType getDebugSectionType(long $$0) {
        return SectionState.type(this.sectionStates.get($$0));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/LayerLightSectionStorage$SectionState.class */
    protected static class SectionState {
        public static final byte EMPTY = 0;
        private static final int MIN_NEIGHBORS = 0;
        private static final int MAX_NEIGHBORS = 26;
        private static final byte HAS_DATA_BIT = 32;
        private static final byte NEIGHBOR_COUNT_BITS = 31;

        protected SectionState() {
        }

        public static byte hasData(byte $$0, boolean $$1) {
            return (byte) ($$1 ? $$0 | 32 : $$0 & (-33));
        }

        public static byte neighborCount(byte $$0, int $$1) {
            if ($$1 < 0 || $$1 > 26) {
                throw new IllegalArgumentException("Neighbor count was not within range [0; 26]");
            }
            return (byte) (($$0 & (-32)) | ($$1 & 31));
        }

        public static boolean hasData(byte $$0) {
            return ($$0 & 32) != 0;
        }

        public static int neighborCount(byte $$0) {
            return $$0 & 31;
        }

        public static SectionType type(byte $$0) {
            if ($$0 == 0) {
                return SectionType.EMPTY;
            }
            if (hasData($$0)) {
                return SectionType.LIGHT_AND_DATA;
            }
            return SectionType.LIGHT_ONLY;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/LayerLightSectionStorage$SectionType.class */
    public enum SectionType {
        EMPTY("2"),
        LIGHT_ONLY("1"),
        LIGHT_AND_DATA("0");

        private final String display;

        SectionType(String $$0) {
            this.display = $$0;
        }

        public String display() {
            return this.display;
        }
    }
}
