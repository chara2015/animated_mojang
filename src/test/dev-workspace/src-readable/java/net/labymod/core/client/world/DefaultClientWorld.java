package net.labymod.core.client.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/DefaultClientWorld.class */
public abstract class DefaultClientWorld implements ClientWorld {
    private final BossBarRegistry bossBarRegistry = Laby.references().bossBarRegistry();
    private final List<Entity> entities = new ArrayList();
    private final List<Player> players = new ArrayList();
    private BiomeCache biomeCache = new BiomeCache(ResourceLocation.create("labymod", "unknown"));

    protected DefaultClientWorld() {
        buildPrettyName(biome());
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public String getReadableBiomeName() {
        ResourceLocation currentBiome = biome();
        if (this.biomeCache.sameBiome(currentBiome)) {
            return this.biomeCache.getPrettyName();
        }
        return buildPrettyName(currentBiome);
    }

    private String buildPrettyName(ResourceLocation currentBiome) {
        String[] split = currentBiome.getPath().split("_");
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (true) {
            if (i >= split.length) {
                break;
            }
            String biome = split[i];
            String biome2 = biome.substring(0, 1).toUpperCase(Locale.US) + biome.substring(1);
            if (i == split.length - 1) {
                builder.append(biome2);
                break;
            }
            builder.append(biome2).append(" ");
            i++;
        }
        String biomePath = builder.toString();
        this.biomeCache = new BiomeCache(currentBiome);
        this.biomeCache.setPrettyName(biomePath);
        return biomePath;
    }

    @ApiStatus.Internal
    public void resetEntityList() {
        if (ThreadSafe.isNotOnRenderThread()) {
            return;
        }
        this.entities.clear();
        this.players.clear();
    }

    @ApiStatus.Internal
    public void addEntity(@NotNull Entity entity) {
        if (ThreadSafe.isNotOnRenderThread()) {
            return;
        }
        this.entities.add(entity);
        if (entity instanceof Player) {
            this.players.add((Player) entity);
        }
    }

    @ApiStatus.Internal
    public void removeEntity(@NotNull Entity entity) {
        if (ThreadSafe.isNotOnRenderThread()) {
            return;
        }
        this.entities.remove(entity);
        if (entity instanceof Player) {
            this.players.remove((Player) entity);
        }
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Optional<Entity> getEntity(UUID uniqueId) {
        for (Entity entity : this.entities) {
            if (entity.getUniqueId().equals(uniqueId)) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Optional<Player> getPlayer(String username) {
        for (Player player : this.players) {
            if (player.getName().equalsIgnoreCase(username)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Optional<Player> getPlayer(UUID uniqueId) {
        for (Player player : this.players) {
            if (player.getUniqueId().equals(uniqueId)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public Set<Chunk> getChunks() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        int renderDistance = minecraft.options().getRenderDistance();
        int chunkDistance = (renderDistance * 2) + 1;
        MinecraftCamera camera = minecraft.getCamera();
        if (camera == null) {
            return Collections.emptySet();
        }
        DoubleVector3 position = camera.position();
        int x = MathHelper.floor(position.getX() / 16.0d);
        int z = MathHelper.floor(position.getZ() / 16.0d);
        Set<Chunk> chunks = new HashSet<>(chunkDistance * chunkDistance);
        for (int offsetX = -renderDistance; offsetX <= renderDistance; offsetX++) {
            for (int offsetZ = -renderDistance; offsetZ <= renderDistance; offsetZ++) {
                Chunk chunk = getChunk(x + offsetX, z + offsetZ);
                if (chunk != null) {
                    chunks.add(chunk);
                }
            }
        }
        return chunks;
    }

    @Override // net.labymod.api.client.world.ClientWorld
    public int getPlayerCount() {
        return this.players.size();
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    @Override // net.labymod.api.client.world.ClientWorld
    @NotNull
    public BossBarRegistry bossBarRegistry() {
        return this.bossBarRegistry;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/DefaultClientWorld$BiomeCache.class */
    static final class BiomeCache {
        private final ResourceLocation resourceLocation;
        private String prettyName;

        public BiomeCache(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public ResourceLocation getResourceLocation() {
            return this.resourceLocation;
        }

        public String getPrettyName() {
            return this.prettyName;
        }

        public void setPrettyName(String prettyName) {
            this.prettyName = prettyName;
        }

        public boolean sameBiome(@NotNull ResourceLocation resourceLocation) {
            return this.resourceLocation.getPath().equals(resourceLocation.getPath());
        }
    }
}
