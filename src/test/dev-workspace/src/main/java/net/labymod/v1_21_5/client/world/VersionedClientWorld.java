package net.labymod.v1_21_5.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/world/VersionedClientWorld.class */
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
        glo level = fqq.Q().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        alr location = level.aj().a();
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
        glo level = fqq.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.am();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        glo level = fqq.Q().s;
        if (level == null) {
            return 0L;
        }
        return level.ae();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        glo level = fqq.Q().s;
        if (level == null) {
            return false;
        }
        return level.a_(new iw(x, y, z)).e();
    }

    private ResourceLocation getCurrentBiome() {
        bxe cameraEntity = fqq.Q().ao();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dv());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new iw(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull iw blockPos) {
        glo level = fqq.Q().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.K_() || blockPos.v() >= level.ao()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<alq<dlm>> biomeHolder = level.u(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        alr biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        glo gloVar = fqq.Q().s;
        if (gloVar == null) {
            return null;
        }
        return gloVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        glo gloVar = fqq.Q().s;
        if (gloVar == null) {
            return null;
        }
        BlockState blockState = gloVar.a_(new iw(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        iw blockPos = iw.a(position.getX(), position.getY(), position.getZ());
        glo level = fqq.Q().s;
        return grk.a(getBrightness(level, dks.b, blockPos), getBrightness(level, dks.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        gqm player = fqq.Q().t;
        glo level = fqq.Q().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<fgw> voxelShapes = level.e(player, (ffx) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (fgw voxelShape : voxelShapes) {
            for (ffx aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        glo level = fqq.Q().s;
        if (level == null) {
            return 0;
        }
        return level.K_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        glo level = fqq.Q().s;
        if (level == null) {
            return 0;
        }
        return level.ao();
    }

    private int getBrightness(dkj level, dks layer, iw blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        glo level = fqq.Q().s;
        if (level == null) {
            return 0;
        }
        return level.D_();
    }
}
