package net.labymod.v1_21_4.client.world;

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
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/world/VersionedClientWorld.class */
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
        gga level = flk.Q().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        akv location = level.ai().a();
        return this.resourceLocationFactory.create(location.b(), location.a());
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
        gga level = flk.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.al();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        gga level = flk.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.ad();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        gga level = flk.Q().s;
        if (level == null) {
            return false;
        }
        return level.a_(new ji(x, y, z)).e();
    }

    private ResourceLocation getCurrentBiome() {
        bum cameraEntity = flk.Q().ao();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dv());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new ji(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull ji blockPos) {
        gga level = flk.Q().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.L_() || blockPos.v() >= level.an()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<aku<dhl>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        akv biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        gga ggaVar = flk.Q().s;
        if (ggaVar == null) {
            return null;
        }
        return ggaVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        gga ggaVar = flk.Q().s;
        if (ggaVar == null) {
            return null;
        }
        BlockState blockState = ggaVar.a_(new ji(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        ji blockPos = ji.a(position.getX(), position.getY(), position.getZ());
        gga level = flk.Q().s;
        return glx.a(getBrightness(level, dgs.b, blockPos), getBrightness(level, dgs.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        gkx player = flk.Q().t;
        gga level = flk.Q().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<fbv> voxelShapes = level.e(player, (faw) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (fbv voxelShape : voxelShapes) {
            for (faw aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.L_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.an();
    }

    private int getBrightness(dgj level, dgs layer, ji blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.E_();
    }
}
