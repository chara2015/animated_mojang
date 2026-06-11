package net.labymod.v1_20_4.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/world/VersionedClientWorld.class */
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
        fns level = evi.O().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        ahg location = level.ae().a();
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
        fns level = evi.O().r;
        if (level == null) {
            return 0L;
        }
        return level.aj();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        fns level = evi.O().r;
        if (level == null) {
            return 0L;
        }
        return level.X();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        fns level = evi.O().r;
        if (level == null) {
            return false;
        }
        return level.a_(new hx(x, y, z)).e();
    }

    private ResourceLocation getCurrentBiome() {
        blv cameraEntity = evi.O().am();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dm());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new hx(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull hx blockPos) {
        fns level = evi.O().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.J_() || blockPos.v() >= level.al()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<ahf<cuo>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        ahg biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        fns fnsVar = evi.O().r;
        if (fnsVar == null) {
            return null;
        }
        return fnsVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        fns fnsVar = evi.O().r;
        if (fnsVar == null) {
            return null;
        }
        BlockState blockState = fnsVar.a_(new hx(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        hx blockPos = hx.a(position.getX(), position.getY(), position.getZ());
        fns level = evi.O().r;
        return ftg.a(getBrightness(level, cty.b, blockPos), getBrightness(level, cty.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        fsj player = evi.O().s;
        fns level = evi.O().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<emm> voxelShapes = level.e(player, (elo) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (emm voxelShape : voxelShapes) {
            for (elo aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        fns level = evi.O().r;
        if (level == null) {
            return 0;
        }
        return level.J_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        fns level = evi.O().r;
        if (level == null) {
            return 0;
        }
        return level.al();
    }

    private int getBrightness(ctp level, cty layer, hx blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        fns level = evi.O().r;
        if (level == null) {
            return 0;
        }
        return level.C_();
    }
}
