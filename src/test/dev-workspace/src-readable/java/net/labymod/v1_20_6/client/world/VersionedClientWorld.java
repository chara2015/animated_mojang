package net.labymod.v1_20_6.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/world/VersionedClientWorld.class */
@Singleton
@Implements(ClientWorld.class)
public class VersionedClientWorld extends DefaultClientWorld {
    private static final GameMathMapper GAME_MATH_MAPPER = MathHelper.mapper();
    private static final ResourceLocation LABYMOD_UNKNOWN = ResourceLocation.create("labymod", "unknown");
    private final ResourceLocationFactory resourceLocationFactory;

    @Inject
    public VersionedClientWorld(ResourceLocationFactory resourceLocationFactory) {
        this.resourceLocationFactory = resourceLocationFactory;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public ResourceLocation dimension() {
        fxx level = ffh.Q().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        alf location = level.af().a();
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
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0L;
        }
        return level.ak();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0L;
        }
        return level.Z();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        fxx level = ffh.Q().r;
        if (level == null) {
            return false;
        }
        return level.a_(new iz(x, y, z)).e();
    }

    private ResourceLocation getCurrentBiome() {
        bsw cameraEntity = ffh.Q().an();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dp());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new iz(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull iz blockPos) {
        fxx level = ffh.Q().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.I_() || blockPos.v() >= level.am()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<ale<dcz>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        alf biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        fxx fxxVar = ffh.Q().r;
        if (fxxVar == null) {
            return null;
        }
        return fxxVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        fxx fxxVar = ffh.Q().r;
        if (fxxVar == null) {
            return null;
        }
        BlockState blockState = fxxVar.a_(new iz(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        iz blockPos = iz.a(position.getX(), position.getY(), position.getZ());
        fxx level = ffh.Q().r;
        return gdp.a(getBrightness(level, dcj.b, blockPos), getBrightness(level, dcj.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        gcs player = ffh.Q().s;
        fxx level = ffh.Q().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<ewm> voxelShapes = level.e(player, (evo) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (ewm voxelShape : voxelShapes) {
            for (evo aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.I_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.am();
    }

    private int getBrightness(dca level, dcj layer, iz blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.B_();
    }
}
