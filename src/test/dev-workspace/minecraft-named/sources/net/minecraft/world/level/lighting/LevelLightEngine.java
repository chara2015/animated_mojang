package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightEventListener;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/LevelLightEngine.class */
public class LevelLightEngine implements LightEventListener {
    public static final int LIGHT_SECTION_PADDING = 1;
    public static final LevelLightEngine EMPTY = new LevelLightEngine();
    protected final LevelHeightAccessor levelHeightAccessor;
    private final LightEngine<?, ?> blockEngine;
    private final LightEngine<?, ?> skyEngine;

    public LevelLightEngine(LightChunkGetter $$0, boolean $$1, boolean $$2) {
        this.levelHeightAccessor = $$0.getLevel();
        this.blockEngine = $$1 ? new BlockLightEngine($$0) : null;
        this.skyEngine = $$2 ? new SkyLightEngine($$0) : null;
    }

    private LevelLightEngine() {
        this.levelHeightAccessor = LevelHeightAccessor.create(0, 0);
        this.blockEngine = null;
        this.skyEngine = null;
    }

    @Override // net.minecraft.world.level.lighting.LightEventListener
    public void checkBlock(BlockPos $$0) {
        if (this.blockEngine != null) {
            this.blockEngine.checkBlock($$0);
        }
        if (this.skyEngine != null) {
            this.skyEngine.checkBlock($$0);
        }
    }

    @Override // net.minecraft.world.level.lighting.LightEventListener
    public boolean hasLightWork() {
        if (this.skyEngine == null || !this.skyEngine.hasLightWork()) {
            return this.blockEngine != null && this.blockEngine.hasLightWork();
        }
        return true;
    }

    @Override // net.minecraft.world.level.lighting.LightEventListener
    public int runLightUpdates() {
        int $$0 = 0;
        if (this.blockEngine != null) {
            $$0 = 0 + this.blockEngine.runLightUpdates();
        }
        if (this.skyEngine != null) {
            $$0 += this.skyEngine.runLightUpdates();
        }
        return $$0;
    }

    @Override // net.minecraft.world.level.lighting.LightEventListener
    public void updateSectionStatus(SectionPos $$0, boolean $$1) {
        if (this.blockEngine != null) {
            this.blockEngine.updateSectionStatus($$0, $$1);
        }
        if (this.skyEngine != null) {
            this.skyEngine.updateSectionStatus($$0, $$1);
        }
    }

    @Override // net.minecraft.world.level.lighting.LightEventListener
    public void setLightEnabled(ChunkPos $$0, boolean $$1) {
        if (this.blockEngine != null) {
            this.blockEngine.setLightEnabled($$0, $$1);
        }
        if (this.skyEngine != null) {
            this.skyEngine.setLightEnabled($$0, $$1);
        }
    }

    @Override // net.minecraft.world.level.lighting.LightEventListener
    public void propagateLightSources(ChunkPos $$0) {
        if (this.blockEngine != null) {
            this.blockEngine.propagateLightSources($$0);
        }
        if (this.skyEngine != null) {
            this.skyEngine.propagateLightSources($$0);
        }
    }

    public LayerLightEventListener getLayerListener(LightLayer $$0) {
        if ($$0 == LightLayer.BLOCK) {
            if (this.blockEngine == null) {
                return LayerLightEventListener.DummyLightLayerEventListener.INSTANCE;
            }
            return this.blockEngine;
        }
        if (this.skyEngine == null) {
            return LayerLightEventListener.DummyLightLayerEventListener.INSTANCE;
        }
        return this.skyEngine;
    }

    public String getDebugData(LightLayer $$0, SectionPos $$1) {
        if ($$0 == LightLayer.BLOCK) {
            if (this.blockEngine != null) {
                return this.blockEngine.getDebugData($$1.asLong());
            }
            return "n/a";
        }
        if (this.skyEngine != null) {
            return this.skyEngine.getDebugData($$1.asLong());
        }
        return "n/a";
    }

    public LayerLightSectionStorage.SectionType getDebugSectionType(LightLayer $$0, SectionPos $$1) {
        if ($$0 == LightLayer.BLOCK) {
            if (this.blockEngine != null) {
                return this.blockEngine.getDebugSectionType($$1.asLong());
            }
        } else if (this.skyEngine != null) {
            return this.skyEngine.getDebugSectionType($$1.asLong());
        }
        return LayerLightSectionStorage.SectionType.EMPTY;
    }

    public void queueSectionData(LightLayer $$0, SectionPos $$1, DataLayer $$2) {
        if ($$0 == LightLayer.BLOCK) {
            if (this.blockEngine != null) {
                this.blockEngine.queueSectionData($$1.asLong(), $$2);
            }
        } else if (this.skyEngine != null) {
            this.skyEngine.queueSectionData($$1.asLong(), $$2);
        }
    }

    public void retainData(ChunkPos $$0, boolean $$1) {
        if (this.blockEngine != null) {
            this.blockEngine.retainData($$0, $$1);
        }
        if (this.skyEngine != null) {
            this.skyEngine.retainData($$0, $$1);
        }
    }

    public int getRawBrightness(BlockPos $$0, int $$1) {
        int $$2 = this.skyEngine == null ? 0 : this.skyEngine.getLightValue($$0) - $$1;
        int $$3 = this.blockEngine == null ? 0 : this.blockEngine.getLightValue($$0);
        return Math.max($$3, $$2);
    }

    public boolean lightOnInColumn(long $$0) {
        return this.blockEngine == null || (this.blockEngine.storage.lightOnInColumn($$0) && (this.skyEngine == null || this.skyEngine.storage.lightOnInColumn($$0)));
    }

    public int getLightSectionCount() {
        return this.levelHeightAccessor.getSectionsCount() + 2;
    }

    public int getMinLightSection() {
        return this.levelHeightAccessor.getMinSectionY() - 1;
    }

    public int getMaxLightSection() {
        return getMinLightSection() + getLightSectionCount();
    }
}
