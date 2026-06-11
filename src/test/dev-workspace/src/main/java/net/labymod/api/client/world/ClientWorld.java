package net.labymod.api.client.world;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.IntVector3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/ClientWorld.class */
@Referenceable
public interface ClientWorld {
    Optional<Entity> getEntity(UUID uuid);

    Optional<Player> getPlayer(UUID uuid);

    Optional<Player> getPlayer(String str);

    int getPlayerCount();

    @NotNull
    List<Player> getPlayers();

    @NotNull
    List<Entity> getEntities();

    @NotNull
    String getReadableBiomeName();

    ResourceLocation dimension();

    @NotNull
    ResourceLocation biome();

    @NotNull
    ResourceLocation biome(double d, double d2, double d3);

    long getDayTime();

    long getGameTime();

    @NotNull
    BossBarRegistry bossBarRegistry();

    boolean hasSolidBlockAt(int i, int i2, int i3);

    Chunk getChunk(int i, int i2);

    BlockState getBlockState(int i, int i2, int i3);

    int getPackedLight(FloatVector3 floatVector3);

    List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox axisAlignedBoundingBox);

    int getMinBuildHeight();

    int getMaxBuildHeight();

    Set<Chunk> getChunks();

    int getSkyBrightness();

    default boolean isBlockInBetween(FloatVector3 from, FloatVector3 to) {
        int distance = MathHelper.ceil(from.distance(to));
        for (int i = 0; i < distance; i++) {
            float x = from.getX() + (((to.getX() - from.getX()) * i) / distance);
            float y = from.getY() + (((to.getY() - from.getY()) * i) / distance);
            float z = from.getZ() + (((to.getZ() - from.getZ()) * i) / distance);
            if (hasSolidBlockAt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) {
                return true;
            }
        }
        return false;
    }

    default boolean isBlockInBetween(DoubleVector3 from, DoubleVector3 to) {
        int distance = MathHelper.ceil(from.distance(to));
        for (int i = 0; i < distance; i++) {
            double x = from.getX() + (((to.getX() - from.getX()) * ((double) i)) / ((double) distance));
            double y = from.getY() + (((to.getY() - from.getY()) * ((double) i)) / ((double) distance));
            double z = from.getZ() + (((to.getZ() - from.getZ()) * ((double) i)) / ((double) distance));
            if (hasSolidBlockAt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    default FloatVector3 findBlockPositionInBetween(FloatVector3 from, FloatVector3 to) {
        int distance = MathHelper.ceil(from.distance(to));
        FloatVector3 blockPosition = null;
        int index = 0;
        while (true) {
            if (index >= distance) {
                break;
            }
            float x = from.getX() + (((to.getX() - from.getX()) * index) / distance);
            float y = from.getY() + (((to.getY() - from.getY()) * index) / distance);
            float z = from.getZ() + (((to.getZ() - from.getZ()) * index) / distance);
            if (!hasSolidBlockAt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) {
                index++;
            } else {
                blockPosition = new FloatVector3(x, y, z);
                break;
            }
        }
        return blockPosition;
    }

    default BlockState getBlockState(FloatVector3 position) {
        return getBlockState(MathHelper.floor(position.getX()), MathHelper.floor(position.getY()), MathHelper.floor(position.getZ()));
    }

    default BlockState getBlockState(DoubleVector3 position) {
        return getBlockState(MathHelper.floor(position.getX()), MathHelper.floor(position.getY()), MathHelper.floor(position.getZ()));
    }

    default BlockState getBlockState(IntVector3 position) {
        return getBlockState(position.getX(), position.getY(), position.getZ());
    }

    default int getPackedLight(float x, float y, float z) {
        return getPackedLight(new FloatVector3(x, y, z));
    }

    default int getPackedLight(double x, double y, double z) {
        return getPackedLight((float) x, (float) y, (float) z);
    }

    default int getPackedLight(DoubleVector3 position) {
        return getPackedLight(position.getX(), position.getY(), position.getZ());
    }

    default <T extends Entity> T getNearestEntity(List<? extends T> list, FloatVector3 floatVector3) {
        return (T) getNearestEntity(list, entity -> {
            return false;
        }, floatVector3);
    }

    default <T extends Entity> T getNearestEntity(List<? extends T> list, DoubleVector3 doubleVector3) {
        return (T) getNearestEntity(list, entity -> {
            return false;
        }, doubleVector3);
    }

    @Nullable
    default <T extends Entity> T getNearestEntity(List<? extends T> entities, Predicate<T> filter, FloatVector3 eyePosition) {
        float minDistance = -1.0f;
        T nearestEntity = null;
        for (T entity : entities) {
            if (!filter.test(entity)) {
                float distance = (float) entity.getDistanceSquared(eyePosition);
                if (minDistance == -1.0f || distance < minDistance) {
                    minDistance = distance;
                    nearestEntity = entity;
                }
            }
        }
        return nearestEntity;
    }

    @Nullable
    default <T extends Entity> T getNearestEntity(List<? extends T> entities, Predicate<T> filter, DoubleVector3 position) {
        float minDistance = -1.0f;
        T nearestEntity = null;
        for (T entity : entities) {
            if (!filter.test(entity)) {
                float distance = (float) entity.getDistanceSquared(position);
                if (minDistance == -1.0f || distance < minDistance) {
                    minDistance = distance;
                    nearestEntity = entity;
                }
            }
        }
        return nearestEntity;
    }

    default int getHighestBlockYAt(int x, int z) {
        int y = 255;
        while (y > 0 && !hasSolidBlockAt(x, y, z)) {
            y--;
        }
        return y;
    }

    default int getTopBlockYOf(int x, int y, int z) {
        if (hasSolidBlockAt(x, y, z)) {
            for (int i = 0; i < 255 && hasSolidBlockAt(x, y + 1, z); i++) {
                y++;
            }
        } else {
            while (y > 0 && !hasSolidBlockAt(x, y, z)) {
                y--;
            }
        }
        return y;
    }
}
