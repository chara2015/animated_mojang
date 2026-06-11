package net.labymod.core.client.render.schematic.lighting.legacy;

import net.labymod.core.client.render.schematic.block.Block;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/legacy/WorldLightAccessor.class */
public interface WorldLightAccessor {
    Block getBlockAt(int i, int i2, int i3);

    boolean isTranslucent(int i, int i2, int i3);

    boolean isFullBlock(int i, int i2, int i3);

    boolean isLightSource(int i, int i2, int i3);

    default int getLightLevel(DefaultLegacyLightEngine engine, Block block) {
        return block.material().getLight(block);
    }
}
