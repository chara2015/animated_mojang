package net.labymod.v1_18_2.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/world/VersionedClientWorld.class */
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
        ems level = dyr.D().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        yt location = level.aa().a();
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
        ems level = dyr.D().r;
        if (level == null) {
            return 0L;
        }
        return level.ae();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        ems level = dyr.D().r;
        if (level == null) {
            return 0L;
        }
        return level.U();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        ems level = dyr.D().r;
        if (level == null) {
            return false;
        }
        return level.a_(new gj(x, y, z)).c().b();
    }

    private ResourceLocation getCurrentBiome() {
        axk cameraEntity = dyr.D().Z();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.cW());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new gj(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull gj blockPos) {
        ems level = dyr.D().r;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.u_() || blockPos.v() >= level.ag()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<? extends hb<cbr>> optionalRegistry = level.s().c(hb.aP);
        if (optionalRegistry.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        yt biomeResourceLocation = ((hb) optionalRegistry.get()).b((cbr) level.v(blockPos).a());
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        ems emsVar = dyr.D().r;
        if (emsVar == null) {
            return null;
        }
        return emsVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        ems emsVar = dyr.D().r;
        if (emsVar == null) {
            return null;
        }
        BlockState blockState = emsVar.a_(new gj(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        epw player = dyr.D().s;
        ems level = dyr.D().r;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<dqh> voxelShapes = level.d(player, (dpj) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (dqh voxelShape : voxelShapes) {
            for (dpj aabb : voxelShape.d()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        gj blockPos = new gj(position.getX(), position.getY(), position.getZ());
        ems level = dyr.D().r;
        return eqr.a(getBrightness(level, cbe.b, blockPos), getBrightness(level, cbe.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.u_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.ag();
    }

    private int getBrightness(cav level, cbe layer, gj blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.o_();
    }
}
