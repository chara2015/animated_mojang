package net.labymod.v1_19_4.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/world/VersionedClientWorld.class */
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
        fdj level = emh.N().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        add location = level.ab().a();
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
        fdj level = emh.N().s;
        if (level == null) {
            return 0L;
        }
        return level.ag();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        fdj level = emh.N().s;
        if (level == null) {
            return 0L;
        }
        return level.U();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        fdj level = emh.N().s;
        if (level == null) {
            return false;
        }
        return level.a_(new gt(x, y, z)).d().b();
    }

    private ResourceLocation getCurrentBiome() {
        bfh cameraEntity = emh.N().al();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dg());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new gt(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull gt blockPos) {
        fdj level = emh.N().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.v_() || blockPos.v() >= level.ai()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<adc<cnf>> biomeHolder = level.v(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        add biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        fdj fdjVar = emh.N().s;
        if (fdjVar == null) {
            return null;
        }
        return fdjVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        fdj fdjVar = emh.N().s;
        if (fdjVar == null) {
            return null;
        }
        BlockState blockState = fdjVar.a_(new gt(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        gt blockPos = gt.a(position.getX(), position.getY(), position.getZ());
        fdj level = emh.N().s;
        return fif.a(getBrightness(level, cmr.b, blockPos), getBrightness(level, cmr.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        fhk player = emh.N().t;
        fdj level = emh.N().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<edx> voxelShapes = level.d(player, (ecz) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (edx voxelShape : voxelShapes) {
            for (ecz aabb : voxelShape.d()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        fdj level = emh.N().s;
        if (level == null) {
            return 0;
        }
        return level.v_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        fdj level = emh.N().s;
        if (level == null) {
            return 0;
        }
        return level.ai();
    }

    private int getBrightness(cmi level, cmr layer, gt blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        fdj level = emh.N().s;
        if (level == null) {
            return 0;
        }
        return level.o_();
    }
}
