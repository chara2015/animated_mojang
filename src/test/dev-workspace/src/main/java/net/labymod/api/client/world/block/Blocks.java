package net.labymod.api.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/Blocks.class */
@Referenceable
public interface Blocks {
    Block air();

    Block getBlock(ResourceLocation resourceLocation);
}
