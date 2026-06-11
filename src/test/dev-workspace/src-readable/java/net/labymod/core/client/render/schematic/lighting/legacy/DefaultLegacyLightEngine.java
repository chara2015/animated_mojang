package net.labymod.core.client.render.schematic.lighting.legacy;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import net.labymod.core.client.render.schematic.block.Block;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/legacy/DefaultLegacyLightEngine.class */
public class DefaultLegacyLightEngine implements LegacyLightEngine {
    private final WorldLightAccessor world;
    private final int width;
    private final int height;
    private final int length;
    private final int skyLight;
    private byte[] light;
    private final Queue<Integer> lightUpdateQueue = new LinkedBlockingQueue();
    private boolean dirty = true;

    public DefaultLegacyLightEngine(WorldLightAccessor world, int width, int height, int length, int skyLight) {
        this.world = world;
        this.width = width;
        this.height = height;
        this.length = length;
        this.skyLight = skyLight;
        this.light = new byte[this.width * this.height * this.length];
        Arrays.fill(this.light, (byte) -1);
    }

    private void updateLight(int x, int y, int z) {
        Block block = this.world.getBlockAt(x, y, z);
        int level = this.world.getLightLevel(this, block);
        if (block.material().isTranslucent()) {
            level = Math.max(level, this.skyLight);
        }
        if (x > 0) {
            level = Math.max(level, getLightAt(x - 1, y, z) - 1);
        }
        if (x < this.width - 1) {
            level = Math.max(level, getLightAt(x + 1, y, z) - 1);
        }
        if (y > 0) {
            level = Math.max(level, getLightAt(x, y - 1, z) - 1);
        }
        if (y < this.height - 1) {
            level = Math.max(level, getLightAt(x, y + 1, z) - 1);
        }
        if (z > 0) {
            level = Math.max(level, getLightAt(x, y, z - 1) - 1);
        }
        if (z < this.length - 1) {
            level = Math.max(level, getLightAt(x, y, z + 1) - 1);
        }
        int previousLevel = getLightAt(x, y, z);
        if (level != previousLevel) {
            setLightAt(x, y, z, (byte) level);
            if (x > 0) {
                queueLightUpdate(x - 1, y, z);
            }
            if (x < this.width - 1) {
                queueLightUpdate(x + 1, y, z);
            }
            if (y > 0) {
                queueLightUpdate(x, y - 1, z);
            }
            if (y < this.height - 1) {
                queueLightUpdate(x, y + 1, z);
            }
            if (z > 0) {
                queueLightUpdate(x, y, z - 1);
            }
            if (z < this.length - 1) {
                queueLightUpdate(x, y, z + 1);
            }
        }
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public void handleLightUpdates() {
        Integer index;
        this.dirty = false;
        for (int i = 0; i <= 1000000 && (index = this.lightUpdateQueue.poll()) != null; i++) {
            int x = index.intValue() % this.width;
            int y = index.intValue() / (this.width * this.length);
            int z = (index.intValue() / this.width) % this.length;
            updateLight(x, y, z);
        }
    }

    public void queueLightUpdate(int x, int y, int z) {
        if (this.lightUpdateQueue.size() < 1000) {
            int index = getIndex(x, y, z);
            this.lightUpdateQueue.add(Integer.valueOf(index));
        }
    }

    private int getIndex(int x, int y, int z) {
        return (((y * this.length) + z) * this.width) + x;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public int getLightAt(int x, int y, int z) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.light.length) {
            return 0;
        }
        byte level = this.light[index];
        if (level == -1) {
            queueLightUpdate(x, y, z);
        }
        return level;
    }

    public void setLightAt(int x, int y, int z, byte level) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.light.length) {
            return;
        }
        this.light[index] = level;
        this.dirty = true;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public void reset() {
        this.lightUpdateQueue.clear();
        Arrays.fill(this.light, (byte) -1);
        this.dirty = true;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public float getAverageLightLevelAt(int x, int y, int z) {
        int totalLightLevel = 0;
        int totalBlocks = 0;
        for (int offsetX = -1; offsetX <= 0; offsetX++) {
            for (int offsetY = -1; offsetY <= 0; offsetY++) {
                for (int offsetZ = -1; offsetZ <= 0; offsetZ++) {
                    int posX = x + offsetX;
                    int posY = y + offsetY;
                    int posZ = z + offsetZ;
                    if (this.world.isTranslucent(posX, posY, posZ) || this.world.isLightSource(posX, posY, posZ)) {
                        totalLightLevel += getLightAt(posX, posY, posZ);
                        totalBlocks++;
                    } else if (offsetY == 0) {
                        totalBlocks++;
                    }
                }
            }
        }
        return totalLightLevel / totalBlocks;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public boolean isInProgress() {
        return !this.lightUpdateQueue.isEmpty() || this.dirty;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public boolean isDirty() {
        return this.dirty;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public byte[] getData() {
        return this.light;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine
    public void setData(byte[] data) {
        if (data.length != this.light.length) {
            throw new IllegalArgumentException("Light data has wrong length");
        }
        this.light = data;
    }
}
