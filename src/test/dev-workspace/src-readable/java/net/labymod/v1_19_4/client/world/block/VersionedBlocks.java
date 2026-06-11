package net.labymod.v1_19_4.client.world.block;

import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.Blocks;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/world/block/VersionedBlocks.class */
@Singleton
@Implements(Blocks.class)
public class VersionedBlocks implements Blocks {
    @Override // net.labymod.api.client.world.block.Blocks
    public Block air() {
        return cpj.a;
    }

    @Override // net.labymod.api.client.world.block.Blocks
    public Block getBlock(ResourceLocation location) {
        return (Block) ja.f.a((add) location.getMinecraftLocation());
    }
}
