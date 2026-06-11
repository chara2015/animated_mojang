package net.labymod.api.client.blockentity;

import net.labymod.api.client.world.block.BlockPosition;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/blockentity/BlockEntity.class */
public interface BlockEntity {
    @NotNull
    BlockPosition position();

    boolean isRemoved();
}
