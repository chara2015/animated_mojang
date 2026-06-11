package net.labymod.v1_21_11.mixins.client.world.levelgen;

import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/levelgen/MixinHeightmap.class */
@Mixin({Heightmap.class})
public abstract class MixinHeightmap implements net.labymod.api.client.world.chunk.Heightmap {
    @Shadow
    public abstract int a(int i, int i2);

    public int getHeight(int x, int z) {
        return a(x, z);
    }
}
