package net.labymod.api.client.waila;

import java.util.Objects;
import net.labymod.api.client.world.block.BlockState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/WailaBlock.class */
public final class WailaBlock implements Waila<BlockState> {
    private final BlockState block;

    WailaBlock(BlockState block) {
        this.block = block;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.waila.Waila
    public BlockState getValue() {
        return this.block;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WailaBlock that = (WailaBlock) o;
        return Objects.equals(this.block, that.block);
    }

    public int hashCode() {
        return Objects.hashCode(this.block);
    }
}
