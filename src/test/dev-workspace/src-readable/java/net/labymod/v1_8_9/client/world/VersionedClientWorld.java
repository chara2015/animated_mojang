package net.labymod.v1_8_9.client.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
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
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/world/VersionedClientWorld.class */
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

    public static ResourceLocation getDimensionFromId(int dimensionId) {
        String str;
        switch (dimensionId) {
            case -1:
                str = "the_nether";
                break;
            case 0:
                str = "overworld";
                break;
            case 1:
                str = "the_end";
                break;
            default:
                str = null;
                break;
        }
        String name = str;
        if (name == null) {
            return LABYMOD_UNKNOWN;
        }
        return Laby.references().resourceLocationFactory().createMinecraft(name);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public ResourceLocation dimension() {
        bdb level = ave.A().f;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        return getDimensionFromId(level.t.q());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public ResourceLocation biome() {
        return getCurrentBiome();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public ResourceLocation biome(double x, double y, double z) {
        return getCurrentBiome();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getDayTime() {
        bdb level = ave.A().f;
        if (level == null) {
            return 0L;
        }
        return level.L();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        bdb level = ave.A().f;
        if (level == null) {
            return 0L;
        }
        return level.K();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        bdb level = ave.A().f;
        if (level == null) {
            return false;
        }
        return level.p(new cj(x, y, z)).c().t().a();
    }

    private ResourceLocation getCurrentBiome() {
        pk entity = ave.A().ac();
        if (entity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getCurrentBiome(new cj(entity.s, entity.t, entity.u));
    }

    private ResourceLocation getCurrentBiome(double x, double y, double z) {
        return getCurrentBiome(new cj(x, y, z));
    }

    private ResourceLocation getCurrentBiome(@NotNull cj blockPos) {
        bdb clientWorld = ave.A().f;
        if (clientWorld == null || !clientWorld.e(blockPos)) {
            return LABYMOD_UNKNOWN;
        }
        amy chunk = clientWorld.f(blockPos);
        String biomeName = chunk.a(blockPos, clientWorld.v()).ah;
        String[] split = biomeName.replace(" ", "").split("(?=\\p{Upper})");
        StringBuilder biomeBuilder = new StringBuilder();
        int i = 0;
        while (true) {
            if (i >= split.length) {
                break;
            }
            String biome = split[i].toLowerCase(Locale.US);
            if (i == split.length - 1) {
                biomeBuilder.append(biome);
                break;
            }
            biomeBuilder.append(biome).append("_");
            i++;
        }
        return this.resourceLocationFactory.create(Namespaces.MINECRAFT, biomeBuilder.toString());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Chunk getChunk(int chunkX, int chunkZ) {
        bdb client = ave.A().f;
        if (client == null) {
            return null;
        }
        return client.a(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        bdb client = ave.A().f;
        if (client == null) {
            return null;
        }
        cj pos = new cj(x, y, z);
        return MinecraftUtil.fromMinecraft(client.p(pos), x, y, z);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        cj blockPos = new cj(position.getX(), position.getY(), position.getZ());
        bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        return MinecraftUtil.getPackedLight(level, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        bew player = ave.A().h;
        bdb level = ave.A().f;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        List<aug> voxelShapes = level.a((aug) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (aug voxelShape : voxelShapes) {
            collisions.add(GAME_MATH_MAPPER.fromAABB(voxelShape));
        }
        return collisions;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMinBuildHeight() {
        return 0;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getMaxBuildHeight() {
        bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        return level.U();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        bdb bdbVar = ave.A().f;
        if (bdbVar == null) {
            return 0;
        }
        return bdbVar.a(0.0f);
    }
}
