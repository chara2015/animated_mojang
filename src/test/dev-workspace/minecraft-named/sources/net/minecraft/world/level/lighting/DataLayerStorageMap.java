package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.DataLayerStorageMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/DataLayerStorageMap.class */
public abstract class DataLayerStorageMap<M extends DataLayerStorageMap<M>> {
    private static final int CACHE_SIZE = 2;
    private final long[] lastSectionKeys = new long[2];
    private final DataLayer[] lastSections = new DataLayer[2];
    private boolean cacheEnabled;
    protected final Long2ObjectOpenHashMap<DataLayer> map;

    public abstract M copy();

    protected DataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> $$0) {
        this.map = $$0;
        clearCache();
        this.cacheEnabled = true;
    }

    public DataLayer copyDataLayer(long $$0) {
        DataLayer $$1 = ((DataLayer) this.map.get($$0)).copy();
        this.map.put($$0, $$1);
        clearCache();
        return $$1;
    }

    public boolean hasLayer(long $$0) {
        return this.map.containsKey($$0);
    }

    public DataLayer getLayer(long $$0) {
        if (this.cacheEnabled) {
            for (int $$1 = 0; $$1 < 2; $$1++) {
                if ($$0 == this.lastSectionKeys[$$1]) {
                    return this.lastSections[$$1];
                }
            }
        }
        DataLayer $$2 = (DataLayer) this.map.get($$0);
        if ($$2 != null) {
            if (this.cacheEnabled) {
                for (int $$3 = 1; $$3 > 0; $$3--) {
                    this.lastSectionKeys[$$3] = this.lastSectionKeys[$$3 - 1];
                    this.lastSections[$$3] = this.lastSections[$$3 - 1];
                }
                this.lastSectionKeys[0] = $$0;
                this.lastSections[0] = $$2;
            }
            return $$2;
        }
        return null;
    }

    public DataLayer removeLayer(long $$0) {
        return (DataLayer) this.map.remove($$0);
    }

    public void setLayer(long $$0, DataLayer $$1) {
        this.map.put($$0, $$1);
    }

    public void clearCache() {
        for (int $$0 = 0; $$0 < 2; $$0++) {
            this.lastSectionKeys[$$0] = Long.MAX_VALUE;
            this.lastSections[$$0] = null;
        }
    }

    public void disableCache() {
        this.cacheEnabled = false;
    }
}
