package net.labymod.v1_21_10.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/world/VersionedClientWorld.class */
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
        gzn level = fzz.W().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        amj location = level.al().a();
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
        gzn level = fzz.W().r;
        if (level == null) {
            return 0L;
        }
        return level.ap();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        gzn level = fzz.W().r;
        if (level == null) {
            return 0L;
        }
        return level.ag();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        gzn level = fzz.W().r;
        if (level == null) {
            return false;
        }
        return level.a_(new ja(x, y, z)).e();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        gzn gznVar = fzz.W().r;
        if (gznVar == null) {
            return null;
        }
        return gznVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        gzn gznVar = fzz.W().r;
        if (gznVar == null) {
            return null;
        }
        BlockState blockState = gznVar.a_(new ja(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        ja blockPos = ja.a(position.getX(), position.getY(), position.getZ());
        gzn level = fzz.W().r;
        return hfs.a(getBrightness(level, drz.b, blockPos), getBrightness(level, drz.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        hep player = fzz.W().s;
        gzn level = fzz.W().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<fpc> voxelShapes = level.e(player, (foc) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (fpc voxelShape : voxelShapes) {
            for (foc aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        gzn level = fzz.W().r;
        if (level == null) {
            return 0;
        }
        return level.M_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        gzn level = fzz.W().r;
        if (level == null) {
            return 0;
        }
        return level.ar();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        return 0;
    }

    private int getBrightness(drq level, drz layer, ja blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    private ResourceLocation getCurrentBiome() {
        cdv cameraEntity = fzz.W().au();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dF());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new ja(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull ja blockPos) {
        gzn level = fzz.W().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.M_() || blockPos.v() >= level.ar()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<ami<dst>> biomeHolder = level.v(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        amj biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
}
