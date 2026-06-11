package net.labymod.v1_21_8.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/world/VersionedClientWorld.class */
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
        grk level = fue.R().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        ame location = level.aj().a();
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
        grk level = fue.R().s;
        if (level == null) {
            return 0L;
        }
        return level.am();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        grk level = fue.R().s;
        if (level == null) {
            return 0L;
        }
        return level.ae();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        grk level = fue.R().s;
        if (level == null) {
            return false;
        }
        return level.a_(new jb(x, y, z)).e();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        grk grkVar = fue.R().s;
        if (grkVar == null) {
            return null;
        }
        return grkVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        grk grkVar = fue.R().s;
        if (grkVar == null) {
            return null;
        }
        BlockState blockState = grkVar.a_(new jb(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        jb blockPos = jb.a(position.getX(), position.getY(), position.getZ());
        grk level = fue.R().s;
        return gxj.a(getBrightness(level, dnd.b, blockPos), getBrightness(level, dnd.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        gwi player = fue.R().t;
        grk level = fue.R().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<fjm> voxelShapes = level.e(player, (fin) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (fjm voxelShape : voxelShapes) {
            for (fin aabb : voxelShape.e()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        grk level = fue.R().s;
        if (level == null) {
            return 0;
        }
        return level.L_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        grk level = fue.R().s;
        if (level == null) {
            return 0;
        }
        return level.ao();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        return 0;
    }

    private int getBrightness(dmu level, dnd layer, jb blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    private ResourceLocation getCurrentBiome() {
        bzm cameraEntity = fue.R().ap();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.dx());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new jb(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull jb blockPos) {
        grk level = fue.R().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.L_() || blockPos.v() >= level.ao()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<amd<dnx>> biomeHolder = level.v(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        ame biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }
}
