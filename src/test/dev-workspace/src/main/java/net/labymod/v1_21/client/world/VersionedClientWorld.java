package net.labymod.v1_21.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/world/VersionedClientWorld.class */
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
        fzf level = fgo.Q().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        akr location = level.af().a();
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
        fzf level = fgo.Q().r;
        if (level == null) {
            return 0L;
        }
        return level.ak();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        fzf level = fgo.Q().r;
        if (level == null) {
            return 0L;
        }
        return level.Z();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        fzf level = fgo.Q().r;
        if (level == null) {
            return false;
        }
        return level.a_(new jd(x, y, z)).e();
    }

    private ResourceLocation getCurrentBiome() {
        bsr cameraEntity = fgo.Q().an();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.do());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new jd(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull jd blockPos) {
        fzf level = fgo.Q().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.I_() || blockPos.v() >= level.am()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<akq<ddw>> biomeHolder = level.t(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        akr biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        fzf fzfVar = fgo.Q().r;
        if (fzfVar == null) {
            return null;
        }
        return fzfVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        fzf fzfVar = fgo.Q().r;
        if (fzfVar == null) {
            return null;
        }
        BlockState blockState = fzfVar.a_(new jd(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        jd blockPos = jd.a(position.getX(), position.getY(), position.getZ());
        fzf level = fgo.Q().r;
        return gey.a(getBrightness(level, ddf.b, blockPos), getBrightness(level, ddf.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        geb player = fgo.Q().s;
        fzf level = fgo.Q().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<exv> voxelShapes = level.e(player, (ewx) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (exv voxelShape : voxelShapes) {
            for (ewx aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        fzf level = fgo.Q().r;
        if (level == null) {
            return 0;
        }
        return level.I_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        fzf level = fgo.Q().r;
        if (level == null) {
            return 0;
        }
        return level.am();
    }

    private int getBrightness(dcw level, ddf layer, jd blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        fzf level = fgo.Q().r;
        if (level == null) {
            return 0;
        }
        return level.B_();
    }
}
