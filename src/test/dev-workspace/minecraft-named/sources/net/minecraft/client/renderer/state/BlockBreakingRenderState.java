package net.minecraft.client.renderer.state;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/state/BlockBreakingRenderState.class */
public class BlockBreakingRenderState extends MovingBlockRenderState {
    public int progress;

    public BlockBreakingRenderState(ClientLevel $$0, BlockPos $$1, int $$2) {
        this.level = $$0;
        this.blockPos = $$1;
        this.blockState = $$0.getBlockState($$1);
        this.progress = $$2;
        this.biome = $$0.getBiome($$1);
    }
}
