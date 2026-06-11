package net.labymod.v26_1.client.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.world.DefaultClientWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/world/VersionedClientWorld.class */
@Singleton
@Implements(ClientWorld.class)
public class VersionedClientWorld extends DefaultClientWorld {
    private final ResourceLocationFactory resourceLocationFactory;
    private static final GameMathMapper GAME_MATH_MAPPER = MathHelper.mapper();
    private static final ResourceLocation LABYMOD_UNKNOWN = ResourceLocation.create("labymod", "unknown");

    @Inject
    public VersionedClientWorld(ResourceLocationFactory resourceLocationFactory) {
        this.resourceLocationFactory = resourceLocationFactory;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public ResourceLocation dimension() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        Identifier location = level.dimension().identifier();
        return this.resourceLocationFactory.create(location.getNamespace(), location.getPath());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public ResourceLocation biome() {
        return getCurrentBiome();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public ResourceLocation biome(double x, double y, double z) {
        return getBiomeAt(x, y, z);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getDayTime() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return 0L;
        }
        return level.getOverworldClockTime();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return 0L;
        }
        return level.getGameTime();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return false;
        }
        return level.getBlockState(new BlockPos(x, y, z)).isSolid();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return null;
        }
        return clientLevel.getChunk(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return null;
        }
        BlockState blockState = clientLevel.getBlockState(new BlockPos(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        BlockPos blockPos = BlockPos.containing(position.getX(), position.getY(), position.getZ());
        ClientLevel level = Minecraft.getInstance().level;
        return LightCoordsUtil.pack(getBrightness(level, LightLayer.BLOCK, blockPos), getBrightness(level, LightLayer.SKY, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<VoxelShape> voxelShapes = level.getBlockCollisions(player, (AABB) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (VoxelShape voxelShape : voxelShapes) {
            for (AABB aabb : voxelShape.toAabbs()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return 0;
        }
        return level.getMinY();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return 0;
        }
        return level.getMaxY();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        return 0;
    }

    private int getBrightness(Level level, LightLayer layer, BlockPos blockPos) {
        if (level == null) {
            return 15;
        }
        return level.getBrightness(layer, blockPos);
    }

    private ResourceLocation getCurrentBiome() {
        Minecraft minecraft = Minecraft.getInstance();
        GameRenderer renderer = minecraft.gameRenderer;
        if (renderer == null) {
            return LABYMOD_UNKNOWN;
        }
        Entity cameraEntity = minecraft.getCameraEntity();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.blockPosition());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull BlockPos blockPos) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.getY() < level.getMinY() || blockPos.getY() >= level.getMaxY()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<ResourceKey<Biome>> biomeHolder = level.getBiome(blockPos).unwrapKey();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        Identifier biomeResourceLocation = biomeHolder.get().identifier();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.getNamespace(), biomeResourceLocation.getPath());
    }
}
