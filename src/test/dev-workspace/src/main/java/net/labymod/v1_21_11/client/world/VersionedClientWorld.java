package net.labymod.v1_21_11.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/world/VersionedClientWorld.class */
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
        hif level = gfj.V().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        amo location = level.aq().a();
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
        hif level = gfj.V().r;
        if (level == null) {
            return 0L;
        }
        return level.al();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        hif level = gfj.V().r;
        if (level == null) {
            return 0L;
        }
        return level.au();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        hif level = gfj.V().r;
        if (level == null) {
            return false;
        }
        return level.a_(new is(x, y, z)).e();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        hif hifVar = gfj.V().r;
        if (hifVar == null) {
            return null;
        }
        return hifVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        hif hifVar = gfj.V().r;
        if (hifVar == null) {
            return null;
        }
        BlockState blockState = hifVar.a_(new is(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        is blockPos = is.a(position.getX(), position.getY(), position.getZ());
        hif level = gfj.V().r;
        return hoj.a(getBrightness(level, dww.b, blockPos), getBrightness(level, dww.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        hnh player = gfj.V().s;
        hif level = gfj.V().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<fug> voxelShapes = level.g(player, (fth) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (fug voxelShape : voxelShapes) {
            for (fth aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        hif level = gfj.V().r;
        if (level == null) {
            return 0;
        }
        return level.K_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        hif level = gfj.V().r;
        if (level == null) {
            return 0;
        }
        return level.aw();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        return 0;
    }

    private int getBrightness(dwo level, dww layer, is blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    private ResourceLocation getCurrentBiome() {
        cgk cameraEntity = gfj.V().au();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dK());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new is(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull is blockPos) {
        hif level = gfj.V().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.K_() || blockPos.v() >= level.aw()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<amt<dxo>> biomeHolder = level.z(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        amo biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
}
