package net.minecraft.world.phys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.HitResult;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/BlockHitResult.class */
public class BlockHitResult extends HitResult {
    private final Direction direction;
    private final BlockPos blockPos;
    private final boolean miss;
    private final boolean inside;
    private final boolean worldBorderHit;

    public static BlockHitResult miss(Vec3 $$0, Direction $$1, BlockPos $$2) {
        return new BlockHitResult(true, $$0, $$1, $$2, false, false);
    }

    public BlockHitResult(Vec3 $$0, Direction $$1, BlockPos $$2, boolean $$3) {
        this(false, $$0, $$1, $$2, $$3, false);
    }

    public BlockHitResult(Vec3 $$0, Direction $$1, BlockPos $$2, boolean $$3, boolean $$4) {
        this(false, $$0, $$1, $$2, $$3, $$4);
    }

    private BlockHitResult(boolean $$0, Vec3 $$1, Direction $$2, BlockPos $$3, boolean $$4, boolean $$5) {
        super($$1);
        this.miss = $$0;
        this.direction = $$2;
        this.blockPos = $$3;
        this.inside = $$4;
        this.worldBorderHit = $$5;
    }

    public BlockHitResult withDirection(Direction $$0) {
        return new BlockHitResult(this.miss, this.location, $$0, this.blockPos, this.inside, this.worldBorderHit);
    }

    public BlockHitResult withPosition(BlockPos $$0) {
        return new BlockHitResult(this.miss, this.location, this.direction, $$0, this.inside, this.worldBorderHit);
    }

    public BlockHitResult hitBorder() {
        return new BlockHitResult(this.miss, this.location, this.direction, this.blockPos, this.inside, true);
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override // net.minecraft.world.phys.HitResult
    public HitResult.Type getType() {
        return this.miss ? HitResult.Type.MISS : HitResult.Type.BLOCK;
    }

    public boolean isInside() {
        return this.inside;
    }

    public boolean isWorldBorderHit() {
        return this.worldBorderHit;
    }
}
