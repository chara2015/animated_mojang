package net.labymod.v1_17_1.mixins.client.world.levelgen;

import net.labymod.api.client.world.chunk.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/world/levelgen/MixinHeightmap.class */
@Mixin({cpt.class})
public abstract class MixinHeightmap implements Heightmap {
    @Shadow
    public abstract int a(int i, int i2);

    @Override // net.labymod.api.client.world.chunk.Heightmap
    public int getHeight(int x, int z) {
        return a(x, z);
    }
}
