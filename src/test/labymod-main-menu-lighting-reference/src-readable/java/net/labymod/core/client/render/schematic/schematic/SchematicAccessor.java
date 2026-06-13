package net.labymod.core.client.render.schematic;

import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine;
import net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/SchematicAccessor.class */
public interface SchematicAccessor extends WorldLightAccessor {
    short getWidth();

    short getHeight();

    short getLength();

    LegacyLightEngine legacyLightEngine();

    default Block getBlockAt(int x, int y, int z, Face face) {
        return getBlockAt(x + face.getX(), y + face.getY(), z + face.getZ());
    }
}
