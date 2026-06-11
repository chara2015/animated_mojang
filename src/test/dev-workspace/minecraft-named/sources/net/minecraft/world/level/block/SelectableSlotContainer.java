package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SelectableSlotContainer.class */
public interface SelectableSlotContainer {
    int getRows();

    int getColumns();

    default OptionalInt getHitSlot(BlockHitResult $$0, Direction $$1) {
        return (OptionalInt) getRelativeHitCoordinatesForBlockFace($$0, $$1).map($$02 -> {
            int $$12 = getSection(1.0f - $$02.y, getRows());
            int $$2 = getSection($$02.x, getColumns());
            return OptionalInt.of($$2 + ($$12 * getColumns()));
        }).orElseGet(OptionalInt::empty);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult $$0, Direction $$1) throws MatchException {
        Direction $$2 = $$0.getDirection();
        if ($$1 != $$2) {
            return Optional.empty();
        }
        BlockPos $$3 = $$0.getBlockPos().relative($$2);
        Vec3 $$4 = $$0.getLocation().subtract($$3.getX(), $$3.getY(), $$3.getZ());
        double $$5 = $$4.x();
        double $$6 = $$4.y();
        double $$7 = $$4.z();
        switch ($$2) {
            case NORTH:
                return Optional.of(new Vec2((float) (1.0d - $$5), (float) $$6));
            case SOUTH:
                return Optional.of(new Vec2((float) $$5, (float) $$6));
            case WEST:
                return Optional.of(new Vec2((float) $$7, (float) $$6));
            case EAST:
                return Optional.of(new Vec2((float) (1.0d - $$7), (float) $$6));
            case DOWN:
            case UP:
                return Optional.empty();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private static int getSection(float $$0, int $$1) {
        float $$2 = $$0 * 16.0f;
        float $$3 = 16.0f / $$1;
        return Mth.clamp(Mth.floor($$2 / $$3), 0, $$1 - 1);
    }
}
