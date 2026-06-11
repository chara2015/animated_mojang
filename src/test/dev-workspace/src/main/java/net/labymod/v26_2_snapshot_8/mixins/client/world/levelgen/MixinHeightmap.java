package net.labymod.v26_2_snapshot_8.mixins.client.world.levelgen;

import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/world/levelgen/MixinHeightmap.class */
@Mixin({Heightmap.class})
public abstract class MixinHeightmap implements net.labymod.api.client.world.chunk.Heightmap {
    @Shadow
    public abstract int getFirstAvailable(int i, int i2);

    @Override // net.labymod.api.client.world.chunk.Heightmap
    public int getHeight(int x, int z) {
        return getFirstAvailable(x, z);
    }
}
