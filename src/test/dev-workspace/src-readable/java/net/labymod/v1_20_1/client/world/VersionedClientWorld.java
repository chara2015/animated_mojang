package net.labymod.v1_20_1.client.world;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/world/VersionedClientWorld.class */
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
        few level = enn.N().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        acq location = level.ac().a();
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
        few level = enn.N().s;
        if (level == null) {
            return 0L;
        }
        return level.ah();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        few level = enn.N().s;
        if (level == null) {
            return 0L;
        }
        return level.V();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        few level = enn.N().s;
        if (level == null) {
            return false;
        }
        return level.a_(new gu(x, y, z)).e();
    }

    private ResourceLocation getCurrentBiome() {
        bfj cameraEntity = enn.N().al();
        if (cameraEntity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getBiomeAt(cameraEntity.di());
    }

    private ResourceLocation getBiomeAt(double x, double y, double z) {
        return getBiomeAt(new gu(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    private ResourceLocation getBiomeAt(@NotNull gu blockPos) {
        few level = enn.N().s;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        if (blockPos.v() < level.C_() || blockPos.v() >= level.aj()) {
            return LABYMOD_UNKNOWN;
        }
        Optional<acp<cnk>> biomeHolder = level.s(blockPos).e();
        if (biomeHolder.isEmpty()) {
            return LABYMOD_UNKNOWN;
        }
        acq biomeResourceLocation = biomeHolder.get().a();
        if (biomeResourceLocation == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.create(biomeResourceLocation.b(), biomeResourceLocation.a());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        few fewVar = enn.N().s;
        if (fewVar == null) {
            return null;
        }
        return fewVar.d(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        few fewVar = enn.N().s;
        if (fewVar == null) {
            return null;
        }
        BlockState blockState = fewVar.a_(new gu(x, y, z));
        blockState.setCoordinates(x, y, z);
        return blockState;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        gu blockPos = gu.a(position.getX(), position.getY(), position.getZ());
        few level = enn.N().s;
        return fjw.a(getBrightness(level, cmv.b, blockPos), getBrightness(level, cmv.a, blockPos));
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        fiy player = enn.N().t;
        few level = enn.N().s;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        Iterable<efb> voxelShapes = level.d(player, (eed) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (efb voxelShape : voxelShapes) {
            for (eed aabb : voxelShape.d()) {
                collisions.add(GAME_MATH_MAPPER.fromAABB(aabb));
            }
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.C_();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.aj();
    }

    private int getBrightness(cmm level, cmv layer, gu blockPos) {
        if (level == null) {
            return 15;
        }
        return level.a(layer, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.v_();
    }
}
