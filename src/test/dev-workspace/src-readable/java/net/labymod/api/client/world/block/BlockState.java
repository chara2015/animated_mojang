package net.labymod.api.client.world.block;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityTypes;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.properties.BlockPropertyHolder;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.IntVector3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/BlockState.class */
public interface BlockState {
    @ApiStatus.Internal
    void setCoordinates(int i, int i2, int i3);

    IntVector3 position();

    Block block();

    int getTopColor();

    int getLightLevel();

    int getLightLevel(LightType lightType);

    RenderShape renderShape();

    boolean isCollisionShapeSolid();

    @Nullable
    AxisAlignedBoundingBox bounds();

    boolean hasCollision();

    boolean isFluid();

    boolean isWater();

    boolean isLava();

    float getHardness(Player player);

    BlockPropertyHolder propertyHolder();

    boolean requiresMinToolForDrops();

    boolean isValidSpawnBlock(@NotNull ResourceLocation resourceLocation);

    boolean isRail();

    boolean hasInvisibleCollisionHeight();

    boolean isRedstoneSource();

    boolean isTopCollisionShapeFull();

    @NotNull
    default ResourceLocation biome() {
        IntVector3 pos = position();
        return Laby.labyAPI().minecraft().clientWorld().biome(pos.getX(), pos.getY(), pos.getZ());
    }

    default boolean isValidSpawnBlockForHostiles() {
        return isValidSpawnBlock(EntityTypes.MAGMA_CUBE);
    }
}
