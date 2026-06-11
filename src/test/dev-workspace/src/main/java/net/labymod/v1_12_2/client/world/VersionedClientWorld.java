package net.labymod.v1_12_2.client.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;
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
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/world/VersionedClientWorld.class */
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

    @Override // net.labymod.api.client.world.ClientWorld
    public ResourceLocation dimension() {
        bsb level = bib.z().f;
        if (level == null) {
            return LABYMOD_UNKNOWN;
        }
        return this.resourceLocationFactory.createMinecraft(level.s.q().b());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public ResourceLocation biome() {
        return getCurrentBiome();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public ResourceLocation biome(double x, double y, double z) {
        return getCurrentBiome(x, y, z);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getDayTime() {
        bsb level = bib.z().f;
        if (level == null) {
            return 0L;
        }
        return level.S();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public long getGameTime() {
        bsb level = bib.z().f;
        if (level == null) {
            return 0L;
        }
        return level.R();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public boolean hasSolidBlockAt(int x, int y, int z) {
        bsb level = bib.z().f;
        if (level == null) {
            return false;
        }
        awt blockState = level.o(new et(x, y, z));
        return blockState.u().q(blockState).a();
    }

    private ResourceLocation getCurrentBiome() {
        vg entity = bib.z().aa();
        if (entity == null) {
            return LABYMOD_UNKNOWN;
        }
        return getCurrentBiome(new et(entity.p, entity.q, entity.r));
    }

    private ResourceLocation getCurrentBiome(double x, double y, double z) {
        return getCurrentBiome(new et(x, y, z));
    }

    private ResourceLocation getCurrentBiome(@NotNull et blockPos) {
        bsb clientWorld = bib.z().f;
        if (clientWorld == null || !clientWorld.e(blockPos)) {
            return LABYMOD_UNKNOWN;
        }
        axw chunk = clientWorld.f(blockPos);
        String biomeName = chunk.a(blockPos, clientWorld.C()).l();
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
        bsb client = bib.z().f;
        if (client == null) {
            return null;
        }
        return client.a(chunkX, chunkZ);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public BlockState getBlockState(int x, int y, int z) {
        bsb client = bib.z().f;
        if (client == null) {
            return null;
        }
        et blockPos = new et(x, y, z);
        return MinecraftUtil.fromMinecraft(client.o(blockPos), x, y, z);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPackedLight(FloatVector3 position) {
        et blockPos = new et(position.getX(), position.getY(), position.getZ());
        bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        return MinecraftUtil.getPackedLight(level, blockPos);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public List<AxisAlignedBoundingBox> getBlockCollisions(AxisAlignedBoundingBox boundingBox) {
        bud player = bib.z().h;
        bsb level = bib.z().f;
        if (player == null || level == null) {
            return Collections.emptyList();
        }
        List<bhb> voxelShapes = level.a(player, (bhb) GAME_MATH_MAPPER.toAABB(boundingBox));
        List<AxisAlignedBoundingBox> collisions = new ArrayList<>();
        for (bhb voxelShape : voxelShapes) {
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
        bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        return level.aa();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getSkyBrightness() {
        bsb bsbVar = bib.z().f;
        if (bsbVar == null) {
            return 0;
        }
        return bsbVar.a(0.0f);
    }
}
