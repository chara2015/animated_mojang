package net.minecraft.client.renderer.blockentity.state;

import java.util.Objects;
import net.minecraft.CrashReportCategory;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BlockEntityRenderState.class */
public class BlockEntityRenderState {
    public BlockPos blockPos = BlockPos.ZERO;
    public BlockState blockState = Blocks.AIR.defaultBlockState();
    public BlockEntityType<?> blockEntityType = BlockEntityType.TEST_BLOCK;
    public int lightCoords;
    public ModelFeatureRenderer.CrumblingOverlay breakProgress;

    public static void extractBase(BlockEntity $$0, BlockEntityRenderState $$1, ModelFeatureRenderer.CrumblingOverlay $$2) {
        $$1.blockPos = $$0.getBlockPos();
        $$1.blockState = $$0.getBlockState();
        $$1.blockEntityType = $$0.getType();
        $$1.lightCoords = $$0.getLevel() != null ? LevelRenderer.getLightColor($$0.getLevel(), $$0.getBlockPos()) : LightTexture.FULL_BRIGHT;
        $$1.breakProgress = $$2;
    }

    public void fillCrashReportCategory(CrashReportCategory $$0) {
        $$0.setDetail("BlockEntityRenderState", getClass().getCanonicalName());
        $$0.setDetail("Position", this.blockPos);
        BlockState blockState = this.blockState;
        Objects.requireNonNull(blockState);
        $$0.setDetail("Block state", blockState::toString);
    }
}
