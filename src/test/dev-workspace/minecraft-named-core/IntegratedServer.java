package net.minecraft.client.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.gizmos.SimpleGizmoCollector;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.server.players.NameAndId;
import net.minecraft.stats.Stats;
import net.minecraft.util.ModCheck;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.debugchart.LocalSampleLogger;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/server/IntegratedServer.class */
public class IntegratedServer extends MinecraftServer {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int MIN_SIM_DISTANCE = 2;
    public static final int MAX_PLAYERS = 8;
    private final Minecraft minecraft;
    private boolean paused;
    private int publishedPort;
    private GameType publishedGameType;
    private LanServerPinger lanPinger;
    private UUID uuid;
    private int previousSimulationDistance;
    private volatile List<SimpleGizmoCollector.GizmoInstance> latestTicksGizmos;
    private final SimpleGizmoCollector gizmoCollector;

    public IntegratedServer(Thread $$0, Minecraft $$1, LevelStorageSource.LevelStorageAccess $$2, PackRepository $$3, WorldStem $$4, Services $$5, LevelLoadListener $$6) {
        super($$0, $$2, $$3, $$4, $$1.getProxy(), $$1.getFixerUpper(), $$5, $$6);
        this.paused = true;
        this.publishedPort = -1;
        this.previousSimulationDistance = 0;
        this.latestTicksGizmos = new ArrayList();
        this.gizmoCollector = new SimpleGizmoCollector();
        setSingleplayerProfile($$1.getGameProfile());
        setDemo($$1.isDemo());
        setPlayerList(new IntegratedPlayerList(this, registries(), this.playerDataStorage));
        this.minecraft = $$1;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean initServer() {
        LOGGER.info("Starting integrated minecraft server version {}", SharedConstants.getCurrentVersion().name());
        setUsesAuthentication(true);
        initializeKeyPair();
        loadLevel();
        GameProfile $$0 = getSingleplayerProfile();
        String $$1 = getWorldData().getLevelName();
        setMotd($$0 != null ? $$0.name() + " - " + $$1 : $$1);
        return true;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isPaused() {
        return this.paused;
    }

    @Override // net.minecraft.server.MinecraftServer
    public void processPacketsAndTick(boolean $$0) {
        Gizmos.TemporaryCollection $$1 = Gizmos.withCollector(this.gizmoCollector);
        try {
            super.processPacketsAndTick($$0);
            if ($$1 != null) {
                $$1.close();
            }
            if (tickRateManager().runsNormally()) {
                this.latestTicksGizmos = this.gizmoCollector.drainGizmos();
            }
        } catch (Throwable th) {
            if ($$1 != null) {
                try {
                    $$1.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public void tickServer(BooleanSupplier $$0) {
        boolean $$1 = this.paused;
        this.paused = Minecraft.getInstance().isPaused() || getPlayerList().getPlayers().isEmpty();
        ProfilerFiller $$2 = Profiler.get();
        if (!$$1 && this.paused) {
            $$2.push("autoSave");
            LOGGER.info("Saving and pausing game...");
            saveEverything(false, false, false);
            $$2.pop();
        }
        if (this.paused) {
            tickPaused();
            return;
        }
        if ($$1) {
            forceTimeSynchronization();
        }
        super.tickServer($$0);
        int $$3 = Math.max(2, this.minecraft.options.renderDistance().get().intValue());
        if ($$3 != getPlayerList().getViewDistance()) {
            LOGGER.info("Changing view distance to {}, from {}", Integer.valueOf($$3), Integer.valueOf(getPlayerList().getViewDistance()));
            getPlayerList().setViewDistance($$3);
        }
        int $$4 = Math.max(2, this.minecraft.options.simulationDistance().get().intValue());
        if ($$4 != this.previousSimulationDistance) {
            LOGGER.info("Changing simulation distance to {}, from {}", Integer.valueOf($$4), Integer.valueOf(this.previousSimulationDistance));
            getPlayerList().setSimulationDistance($$4);
            this.previousSimulationDistance = $$4;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.MinecraftServer
    public LocalSampleLogger getTickTimeLogger() {
        return this.minecraft.getDebugOverlay().getTickTimeLogger();
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isTickTimeLoggingEnabled() {
        return true;
    }

    private void tickPaused() {
        tickConnection();
        for (ServerPlayer $$0 : getPlayerList().getPlayers()) {
            $$0.awardStat(Stats.TOTAL_WORLD_TIME);
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean shouldRconBroadcast() {
        return true;
    }

    @Override // net.minecraft.server.MinecraftServer, net.minecraft.commands.CommandSource
    public boolean shouldInformAdmins() {
        return true;
    }

    @Override // net.minecraft.server.MinecraftServer
    public Path getServerDirectory() {
        return this.minecraft.gameDirectory.toPath();
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isDedicatedServer() {
        return false;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getRateLimitPacketsPerSecond() {
        return 0;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean useNativeTransport() {
        return this.minecraft.options.useNativeTransport();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void onServerCrash(CrashReport $$0) {
        this.minecraft.delayCrashRaw($$0);
    }

    @Override // net.minecraft.server.MinecraftServer
    public SystemReport fillServerSystemReport(SystemReport $$0) {
        $$0.setDetail("Type", "Integrated Server (map_client.txt)");
        $$0.setDetail("Is Modded", () -> {
            return getModdedStatus().fullDescription();
        });
        Minecraft minecraft = this.minecraft;
        Objects.requireNonNull(minecraft);
        $$0.setDetail("Launched Version", minecraft::getLaunchedVersion);
        return $$0;
    }

    @Override // net.minecraft.server.MinecraftServer
    public ModCheck getModdedStatus() {
        return Minecraft.checkModStatus().merge(super.getModdedStatus());
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean publishServer(GameType $$0, boolean $$1, int $$2) {
        try {
            this.minecraft.prepareForMultiplayer();
            this.minecraft.getConnection().prepareKeyPair();
            getConnection().startTcpServerListener(null, $$2);
            LOGGER.info("Started serving on {}", Integer.valueOf($$2));
            this.publishedPort = $$2;
            this.lanPinger = new LanServerPinger(getMotd(), $$2);
            this.lanPinger.start();
            this.publishedGameType = $$0;
            getPlayerList().setAllowCommandsForAllPlayers($$1);
            PermissionSet $$3 = getProfilePermissions(this.minecraft.player.nameAndId());
            this.minecraft.player.setPermissions($$3);
            for (ServerPlayer $$4 : getPlayerList().getPlayers()) {
                getCommands().sendCommands($$4);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public void stopServer() {
        super.stopServer();
        if (this.lanPinger != null) {
            this.lanPinger.interrupt();
            this.lanPinger = null;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public void halt(boolean $$0) {
        executeBlocking(() -> {
            List<ServerPlayer> $$02 = Lists.newArrayList(getPlayerList().getPlayers());
            for (ServerPlayer $$1 : $$02) {
                if (!$$1.getUUID().equals(this.uuid)) {
                    getPlayerList().remove($$1);
                }
            }
        });
        super.halt($$0);
        if (this.lanPinger != null) {
            this.lanPinger.interrupt();
            this.lanPinger = null;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isPublished() {
        return this.publishedPort > -1;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getPort() {
        return this.publishedPort;
    }

    @Override // net.minecraft.server.MinecraftServer
    public void setDefaultGameType(GameType $$0) {
        super.setDefaultGameType($$0);
        this.publishedGameType = null;
    }

    @Override // net.minecraft.server.MinecraftServer
    public LevelBasedPermissionSet operatorUserPermissions() {
        return LevelBasedPermissionSet.GAMEMASTER;
    }

    @Override // net.minecraft.server.MinecraftServer
    public LevelBasedPermissionSet getFunctionCompilationPermissions() {
        return LevelBasedPermissionSet.GAMEMASTER;
    }

    public void setUUID(UUID $$0) {
        this.uuid = $$0;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isSingleplayerOwner(NameAndId $$0) {
        return getSingleplayerProfile() != null && $$0.name().equalsIgnoreCase(getSingleplayerProfile().name());
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getScaledTrackingDistance(int $$0) {
        return (int) (this.minecraft.options.entityDistanceScaling().get().doubleValue() * ((double) $$0));
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean forceSynchronousWrites() {
        return this.minecraft.options.syncWrites;
    }

    @Override // net.minecraft.server.MinecraftServer
    public GameType getForcedGameType() {
        if (isPublished() && !isHardcore()) {
            return (GameType) MoreObjects.firstNonNull(this.publishedGameType, this.worldData.getGameType());
        }
        return null;
    }

    @Override // net.minecraft.server.MinecraftServer
    public GlobalPos selectLevelLoadFocusPos() {
        CompoundTag $$0 = this.worldData.getLoadedPlayerTag();
        if ($$0 == null) {
            return super.selectLevelLoadFocusPos();
        }
        ProblemReporter.ScopedCollector $$1 = new ProblemReporter.ScopedCollector(LOGGER);
        try {
            ValueInput $$2 = TagValueInput.create($$1, registryAccess(), $$0);
            ServerPlayer.SavedPosition $$3 = (ServerPlayer.SavedPosition) $$2.read(ServerPlayer.SavedPosition.MAP_CODEC).orElse(ServerPlayer.SavedPosition.EMPTY);
            if ($$3.dimension().isPresent() && $$3.position().isPresent()) {
                GlobalPos globalPos = new GlobalPos($$3.dimension().get(), BlockPos.containing($$3.position().get()));
                $$1.close();
                return globalPos;
            }
            $$1.close();
            return super.selectLevelLoadFocusPos();
        } catch (Throwable th) {
            try {
                $$1.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean saveEverything(boolean $$0, boolean $$1, boolean $$2) {
        boolean $$3 = super.saveEverything($$0, $$1, $$2);
        warnOnLowDiskSpace();
        return $$3;
    }

    private void warnOnLowDiskSpace() {
        if (this.storageSource.checkForLowDiskSpace()) {
            this.minecraft.execute(() -> {
                SystemToast.onLowDiskSpace(this.minecraft);
            });
        }
    }

    @Override // net.minecraft.server.MinecraftServer, net.minecraft.world.level.chunk.storage.ChunkIOErrorReporter
    public void reportChunkLoadFailure(Throwable $$0, RegionStorageInfo $$1, ChunkPos $$2) {
        super.reportChunkLoadFailure($$0, $$1, $$2);
        warnOnLowDiskSpace();
        this.minecraft.execute(() -> {
            SystemToast.onChunkLoadFailure(this.minecraft, $$2);
        });
    }

    @Override // net.minecraft.server.MinecraftServer, net.minecraft.world.level.chunk.storage.ChunkIOErrorReporter
    public void reportChunkSaveFailure(Throwable $$0, RegionStorageInfo $$1, ChunkPos $$2) {
        super.reportChunkSaveFailure($$0, $$1, $$2);
        warnOnLowDiskSpace();
        this.minecraft.execute(() -> {
            SystemToast.onChunkSaveFailure(this.minecraft, $$2);
        });
    }

    @Override // net.minecraft.server.ServerInfo
    public int getMaxPlayers() {
        return 8;
    }

    public Collection<SimpleGizmoCollector.GizmoInstance> getPerTickGizmos() {
        return this.latestTicksGizmos;
    }
}
