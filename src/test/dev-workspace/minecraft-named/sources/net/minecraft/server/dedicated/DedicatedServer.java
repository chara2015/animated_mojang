package net.minecraft.server.dedicated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import io.netty.handler.ssl.SslContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.ConsoleInput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.ServerLinks;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.jsonrpc.JsonRpcNotificationService;
import net.minecraft.server.jsonrpc.ManagementServer;
import net.minecraft.server.jsonrpc.internalapi.MinecraftApi;
import net.minecraft.server.jsonrpc.security.AuthenticationHandler;
import net.minecraft.server.jsonrpc.security.JsonRpcSslContextProvider;
import net.minecraft.server.jsonrpc.security.SecurityConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.LoggingLevelLoadListener;
import net.minecraft.server.network.ServerTextFilter;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.server.rcon.RconConsoleSource;
import net.minecraft.server.rcon.thread.QueryThreadGs4;
import net.minecraft.server.rcon.thread.RconThread;
import net.minecraft.util.Crypt;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Util;
import net.minecraft.util.debug.DebugSubscriptions;
import net.minecraft.util.debugchart.RemoteDebugSampleType;
import net.minecraft.util.debugchart.RemoteSampleLogger;
import net.minecraft.util.debugchart.SampleLogger;
import net.minecraft.util.debugchart.TpsDebugDimensions;
import net.minecraft.util.monitoring.jmx.MinecraftServerStatistics;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.scores.ScoreHolder;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dedicated/DedicatedServer.class */
public class DedicatedServer extends MinecraftServer implements ServerInterface {
    static final Logger LOGGER = LogUtils.getLogger();
    private static final int CONVERSION_RETRY_DELAY_MS = 5000;
    private static final int CONVERSION_RETRIES = 2;
    private final List<ConsoleInput> consoleInput;
    private QueryThreadGs4 queryThreadGs4;
    private final RconConsoleSource rconConsoleSource;
    private RconThread rconThread;
    private final DedicatedServerSettings settings;
    private MinecraftServerGui gui;
    private final ServerTextFilter serverTextFilter;
    private RemoteSampleLogger tickTimeLogger;
    private boolean isTickTimeLoggingEnabled;
    private final ServerLinks serverLinks;
    private final Map<String, String> codeOfConductTexts;
    private ManagementServer jsonRpcServer;
    private long lastHeartbeat;

    public DedicatedServer(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, DedicatedServerSettings $$4, DataFixer $$5, Services $$6) {
        super($$0, $$1, $$2, $$3, Proxy.NO_PROXY, $$5, $$6, LoggingLevelLoadListener.forDedicatedServer());
        this.consoleInput = Collections.synchronizedList(Lists.newArrayList());
        this.settings = $$4;
        this.rconConsoleSource = new RconConsoleSource(this);
        this.serverTextFilter = ServerTextFilter.createFromConfig($$4.getProperties());
        this.serverLinks = createServerLinks($$4);
        if ($$4.getProperties().codeOfConduct) {
            this.codeOfConductTexts = readCodeOfConducts();
        } else {
            this.codeOfConductTexts = Map.of();
        }
    }

    private static Map<String, String> readCodeOfConducts() {
        Path $$0 = Path.of("codeofconduct", new String[0]);
        if (!Files.isDirectory($$0, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalArgumentException("Code of Conduct folder does not exist: " + String.valueOf($$0));
        }
        try {
            ImmutableMap.Builder<String, String> $$1 = ImmutableMap.builder();
            Stream<Path> $$2 = Files.list($$0);
            try {
                for (Path $$3 : $$2.toList()) {
                    String $$4 = $$3.getFileName().toString();
                    if ($$4.endsWith(".txt")) {
                        String $$5 = $$4.substring(0, $$4.length() - 4).toLowerCase(Locale.ROOT);
                        if (!$$3.toRealPath(new LinkOption[0]).getParent().equals($$0.toAbsolutePath())) {
                            throw new IllegalArgumentException("Failed to read Code of Conduct file \"" + $$4 + "\" because it links to a file outside the allowed directory");
                        }
                        try {
                            String $$6 = String.join(Crypt.MIME_LINE_SEPARATOR, Files.readAllLines($$3, StandardCharsets.UTF_8));
                            $$1.put($$5, StringUtil.stripColor($$6));
                        } catch (IOException $$7) {
                            throw new IllegalArgumentException("Failed to read Code of Conduct file " + $$4, $$7);
                        }
                    }
                }
                if ($$2 != null) {
                    $$2.close();
                }
                return $$1.build();
            } finally {
            }
        } catch (IOException $$8) {
            throw new IllegalArgumentException("Failed to read Code of Conduct folder", $$8);
        }
    }

    private SslContext createSslContext() {
        try {
            return JsonRpcSslContextProvider.createFrom(getProperties().managementServerTlsKeystore, getProperties().managementServerTlsKeystorePassword);
        } catch (Exception $$0) {
            JsonRpcSslContextProvider.printInstructions();
            throw new IllegalStateException("Failed to configure TLS for the server management protocol", $$0);
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean initServer() throws IOException {
        int $$0 = getProperties().managementServerPort;
        if (getProperties().managementServerEnabled) {
            String $$1 = this.settings.getProperties().managementServerSecret;
            if (!SecurityConfig.isValid($$1)) {
                throw new IllegalStateException("Invalid management server secret, must be 40 alphanumeric characters");
            }
            String $$2 = getProperties().managementServerHost;
            HostAndPort $$3 = HostAndPort.fromParts($$2, $$0);
            SecurityConfig $$4 = new SecurityConfig($$1);
            String $$5 = getProperties().managementServerAllowedOrigins;
            AuthenticationHandler $$6 = new AuthenticationHandler($$4, $$5);
            LOGGER.info("Starting json RPC server on {}", $$3);
            this.jsonRpcServer = new ManagementServer($$3, $$6);
            MinecraftApi $$7 = MinecraftApi.of(this);
            $$7.notificationManager().registerService(new JsonRpcNotificationService($$7, this.jsonRpcServer));
            if (getProperties().managementServerTlsEnabled) {
                SslContext $$8 = createSslContext();
                this.jsonRpcServer.startWithTls($$7, $$8);
            } else {
                this.jsonRpcServer.startWithoutTls($$7);
            }
        }
        Thread $$9 = new Thread("Server console handler") { // from class: net.minecraft.server.dedicated.DedicatedServer.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String $$12;
                BufferedReader $$02 = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                while (!DedicatedServer.this.isStopped() && DedicatedServer.this.isRunning() && ($$12 = $$02.readLine()) != null) {
                    try {
                        DedicatedServer.this.handleConsoleInput($$12, DedicatedServer.this.createCommandSourceStack());
                    } catch (IOException $$22) {
                        DedicatedServer.LOGGER.error("Exception handling console input", $$22);
                        return;
                    }
                }
            }
        };
        $$9.setDaemon(true);
        $$9.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        $$9.start();
        LOGGER.info("Starting minecraft server version {}", SharedConstants.getCurrentVersion().name());
        if ((Runtime.getRuntime().maxMemory() / 1024) / 1024 < 512) {
            LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        LOGGER.info("Loading properties");
        DedicatedServerProperties $$10 = this.settings.getProperties();
        if (isSingleplayer()) {
            setLocalIp("127.0.0.1");
        } else {
            setUsesAuthentication($$10.onlineMode);
            setPreventProxyConnections($$10.preventProxyConnections);
            setLocalIp($$10.serverIp);
        }
        this.worldData.setGameType($$10.gameMode.get());
        LOGGER.info("Default game type: {}", $$10.gameMode.get());
        InetAddress $$11 = null;
        if (!getLocalIp().isEmpty()) {
            $$11 = InetAddress.getByName(getLocalIp());
        }
        if (getPort() < 0) {
            setPort($$10.serverPort);
        }
        initializeKeyPair();
        LOGGER.info("Starting Minecraft server on {}:{}", getLocalIp().isEmpty() ? ScoreHolder.WILDCARD_NAME : getLocalIp(), Integer.valueOf(getPort()));
        try {
            getConnection().startTcpServerListener($$11, getPort());
            if (!usesAuthentication()) {
                LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
                LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
                LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
                LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
            }
            if (convertOldUsers()) {
                this.services.nameToIdCache().save();
            }
            if (!OldUsersConverter.serverReadyAfterUserconversion(this)) {
                return false;
            }
            setPlayerList(new DedicatedPlayerList(this, registries(), this.playerDataStorage));
            this.tickTimeLogger = new RemoteSampleLogger(TpsDebugDimensions.values().length, debugSubscribers(), RemoteDebugSampleType.TICK_TIME);
            long $$13 = Util.getNanos();
            this.services.nameToIdCache().resolveOfflineUsers(!usesAuthentication());
            LOGGER.info("Preparing level \"{}\"", getLevelIdName());
            loadLevel();
            long $$14 = Util.getNanos() - $$13;
            String $$15 = String.format(Locale.ROOT, "%.3fs", Double.valueOf($$14 / 1.0E9d));
            LOGGER.info("Done ({})! For help, type \"help\"", $$15);
            if ($$10.announcePlayerAchievements != null) {
                this.worldData.getGameRules().set(GameRules.SHOW_ADVANCEMENT_MESSAGES, $$10.announcePlayerAchievements, this);
            }
            if ($$10.enableQuery) {
                LOGGER.info("Starting GS4 status listener");
                this.queryThreadGs4 = QueryThreadGs4.create(this);
            }
            if ($$10.enableRcon) {
                LOGGER.info("Starting remote control listener");
                this.rconThread = RconThread.create(this);
            }
            if (getMaxTickLength() > 0) {
                Thread $$16 = new Thread(new ServerWatchdog(this));
                $$16.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(LOGGER));
                $$16.setName("Server Watchdog");
                $$16.setDaemon(true);
                $$16.start();
            }
            if ($$10.enableJmxMonitoring) {
                MinecraftServerStatistics.registerJmxMonitoring(this);
                LOGGER.info("JMX monitoring enabled");
            }
            notificationManager().serverStarted();
            return true;
        } catch (IOException $$12) {
            LOGGER.warn("**** FAILED TO BIND TO PORT!");
            LOGGER.warn("The exception was: {}", $$12.toString());
            LOGGER.warn("Perhaps a server is already running on that port?");
            return false;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isEnforceWhitelist() {
        return this.settings.getProperties().enforceWhitelist.get().booleanValue();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void setEnforceWhitelist(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.enforceWhitelist.update(registryAccess(), Boolean.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isUsingWhitelist() {
        return this.settings.getProperties().whiteList.get().booleanValue();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void setUsingWhitelist(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.whiteList.update(registryAccess(), Boolean.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public void tickServer(BooleanSupplier $$0) {
        super.tickServer($$0);
        if (this.jsonRpcServer != null) {
            this.jsonRpcServer.tick();
        }
        long $$1 = Util.getMillis();
        int $$2 = statusHeartbeatInterval();
        if ($$2 > 0) {
            long $$3 = ((long) $$2) * TimeUtil.MILLISECONDS_PER_SECOND;
            if ($$1 - this.lastHeartbeat >= $$3) {
                this.lastHeartbeat = $$1;
                notificationManager().statusHeartbeat();
            }
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean saveAllChunks(boolean $$0, boolean $$1, boolean $$2) {
        notificationManager().serverSaveStarted();
        boolean $$3 = super.saveAllChunks($$0, $$1, $$2);
        notificationManager().serverSaveCompleted();
        return $$3;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean allowFlight() {
        return this.settings.getProperties().allowFlight.get().booleanValue();
    }

    public void setAllowFlight(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.allowFlight.update(registryAccess(), Boolean.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.ServerInterface
    public DedicatedServerProperties getProperties() {
        return this.settings.getProperties();
    }

    public void setDifficulty(Difficulty $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.difficulty.update(registryAccess(), $$0);
        });
        forceDifficulty();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void forceDifficulty() {
        setDifficulty(getProperties().difficulty.get(), true);
    }

    public int viewDistance() {
        return this.settings.getProperties().viewDistance.get().intValue();
    }

    public void setViewDistance(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.viewDistance.update(registryAccess(), Integer.valueOf($$0));
        });
        getPlayerList().setViewDistance($$0);
    }

    public int simulationDistance() {
        return this.settings.getProperties().simulationDistance.get().intValue();
    }

    public void setSimulationDistance(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.simulationDistance.update(registryAccess(), Integer.valueOf($$0));
        });
        getPlayerList().setSimulationDistance($$0);
    }

    @Override // net.minecraft.server.MinecraftServer
    public SystemReport fillServerSystemReport(SystemReport $$0) {
        $$0.setDetail("Is Modded", () -> {
            return getModdedStatus().fullDescription();
        });
        $$0.setDetail("Type", () -> {
            return "Dedicated Server (map_server.txt)";
        });
        return $$0;
    }

    @Override // net.minecraft.server.MinecraftServer
    public void dumpServerProperties(Path $$0) throws IOException {
        DedicatedServerProperties $$1 = getProperties();
        Writer $$2 = Files.newBufferedWriter($$0, new OpenOption[0]);
        try {
            $$2.write(String.format(Locale.ROOT, "sync-chunk-writes=%s%n", Boolean.valueOf($$1.syncChunkWrites)));
            $$2.write(String.format(Locale.ROOT, "gamemode=%s%n", $$1.gameMode.get()));
            $$2.write(String.format(Locale.ROOT, "entity-broadcast-range-percentage=%d%n", $$1.entityBroadcastRangePercentage.get()));
            $$2.write(String.format(Locale.ROOT, "max-world-size=%d%n", Integer.valueOf($$1.maxWorldSize)));
            $$2.write(String.format(Locale.ROOT, "view-distance=%d%n", $$1.viewDistance.get()));
            $$2.write(String.format(Locale.ROOT, "simulation-distance=%d%n", $$1.simulationDistance.get()));
            $$2.write(String.format(Locale.ROOT, "generate-structures=%s%n", Boolean.valueOf($$1.worldOptions.generateStructures())));
            $$2.write(String.format(Locale.ROOT, "use-native=%s%n", Boolean.valueOf($$1.useNativeTransport)));
            $$2.write(String.format(Locale.ROOT, "rate-limit=%d%n", Integer.valueOf($$1.rateLimitPacketsPerSecond)));
            if ($$2 != null) {
                $$2.close();
            }
        } catch (Throwable th) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public void onServerExit() {
        if (this.serverTextFilter != null) {
            this.serverTextFilter.close();
        }
        if (this.gui != null) {
            this.gui.close();
        }
        if (this.rconThread != null) {
            this.rconThread.stop();
        }
        if (this.queryThreadGs4 != null) {
            this.queryThreadGs4.stop();
        }
        if (this.jsonRpcServer != null) {
            try {
                this.jsonRpcServer.stop(true);
            } catch (InterruptedException $$0) {
                LOGGER.error("Interrupted while stopping the management server", $$0);
            }
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public void tickConnection() {
        super.tickConnection();
        handleConsoleInputs();
    }

    public void handleConsoleInput(String $$0, CommandSourceStack $$1) {
        this.consoleInput.add(new ConsoleInput($$0, $$1));
    }

    public void handleConsoleInputs() {
        while (!this.consoleInput.isEmpty()) {
            ConsoleInput $$0 = this.consoleInput.remove(0);
            getCommands().performPrefixedCommand($$0.source, $$0.msg);
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isDedicatedServer() {
        return true;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getRateLimitPacketsPerSecond() {
        return getProperties().rateLimitPacketsPerSecond;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean useNativeTransport() {
        return getProperties().useNativeTransport;
    }

    @Override // net.minecraft.server.MinecraftServer
    public DedicatedPlayerList getPlayerList() {
        return (DedicatedPlayerList) super.getPlayerList();
    }

    @Override // net.minecraft.server.ServerInfo
    public int getMaxPlayers() {
        return this.settings.getProperties().maxPlayers.get().intValue();
    }

    public void setMaxPlayers(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.maxPlayers.update(registryAccess(), Integer.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isPublished() {
        return true;
    }

    @Override // net.minecraft.server.ServerInterface
    public String getServerIp() {
        return getLocalIp();
    }

    @Override // net.minecraft.server.ServerInterface
    public int getServerPort() {
        return getPort();
    }

    @Override // net.minecraft.server.ServerInterface
    public String getServerName() {
        return getMotd();
    }

    public void showGui() {
        if (this.gui == null) {
            this.gui = MinecraftServerGui.showFrameFor(this);
        }
    }

    public int spawnProtectionRadius() {
        return getProperties().spawnProtection.get().intValue();
    }

    public void setSpawnProtectionRadius(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.spawnProtection.update(registryAccess(), Integer.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isUnderSpawnProtection(ServerLevel $$0, BlockPos $$1, Player $$2) {
        LevelData.RespawnData $$3 = $$0.getRespawnData();
        if ($$0.dimension() != $$3.dimension() || getPlayerList().getOps().isEmpty() || getPlayerList().isOp($$2.nameAndId()) || spawnProtectionRadius() <= 0) {
            return false;
        }
        BlockPos $$4 = $$3.pos();
        int $$5 = Mth.abs($$1.getX() - $$4.getX());
        int $$6 = Mth.abs($$1.getZ() - $$4.getZ());
        int $$7 = Math.max($$5, $$6);
        return $$7 <= spawnProtectionRadius();
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean repliesToStatus() {
        return getProperties().enableStatus.get().booleanValue();
    }

    public void setRepliesToStatus(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.enableStatus.update(registryAccess(), Boolean.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean hidesOnlinePlayers() {
        return getProperties().hideOnlinePlayers.get().booleanValue();
    }

    public void setHidesOnlinePlayers(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.hideOnlinePlayers.update(registryAccess(), Boolean.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public LevelBasedPermissionSet operatorUserPermissions() {
        return getProperties().opPermissions.get();
    }

    public void setOperatorUserPermissions(LevelBasedPermissionSet $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.opPermissions.update(registryAccess(), $$0);
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public PermissionSet getFunctionCompilationPermissions() {
        return getProperties().functionPermissions;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int playerIdleTimeout() {
        return this.settings.getProperties().playerIdleTimeout.get().intValue();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void setPlayerIdleTimeout(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.playerIdleTimeout.update(registryAccess(), Integer.valueOf($$0));
        });
    }

    public int statusHeartbeatInterval() {
        return this.settings.getProperties().statusHeartbeatInterval.get().intValue();
    }

    public void setStatusHeartbeatInterval(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.statusHeartbeatInterval.update(registryAccess(), Integer.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer, net.minecraft.server.ServerInfo
    public String getMotd() {
        return this.settings.getProperties().motd.get();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void setMotd(String $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.motd.update(registryAccess(), $$0);
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean shouldRconBroadcast() {
        return getProperties().broadcastRconToOps;
    }

    @Override // net.minecraft.server.MinecraftServer, net.minecraft.commands.CommandSource
    public boolean shouldInformAdmins() {
        return getProperties().broadcastConsoleToOps;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getAbsoluteMaxWorldSize() {
        return getProperties().maxWorldSize;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getCompressionThreshold() {
        return getProperties().networkCompressionThreshold;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean enforceSecureProfile() {
        DedicatedServerProperties $$0 = getProperties();
        return $$0.enforceSecureProfile && $$0.onlineMode && this.services.canValidateProfileKeys();
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean logIPs() {
        return getProperties().logIPs;
    }

    protected boolean convertOldUsers() {
        boolean $$0 = false;
        for (int $$1 = 0; !$$0 && $$1 <= 2; $$1++) {
            if ($$1 > 0) {
                LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
                waitForRetry();
            }
            $$0 = OldUsersConverter.convertUserBanlist(this);
        }
        boolean $$2 = false;
        for (int $$12 = 0; !$$2 && $$12 <= 2; $$12++) {
            if ($$12 > 0) {
                LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
                waitForRetry();
            }
            $$2 = OldUsersConverter.convertIpBanlist(this);
        }
        boolean $$3 = false;
        for (int $$13 = 0; !$$3 && $$13 <= 2; $$13++) {
            if ($$13 > 0) {
                LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
                waitForRetry();
            }
            $$3 = OldUsersConverter.convertOpsList(this);
        }
        boolean $$4 = false;
        for (int $$14 = 0; !$$4 && $$14 <= 2; $$14++) {
            if ($$14 > 0) {
                LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
                waitForRetry();
            }
            $$4 = OldUsersConverter.convertWhiteList(this);
        }
        boolean $$5 = false;
        for (int $$15 = 0; !$$5 && $$15 <= 2; $$15++) {
            if ($$15 > 0) {
                LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
                waitForRetry();
            }
            $$5 = OldUsersConverter.convertPlayers(this);
        }
        return $$0 || $$2 || $$3 || $$4 || $$5;
    }

    private void waitForRetry() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
        }
    }

    public long getMaxTickLength() {
        return getProperties().maxTickTime;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getMaxChainedNeighborUpdates() {
        return getProperties().maxChainedNeighborUpdates;
    }

    @Override // net.minecraft.server.ServerInterface
    public String getPluginNames() {
        return "";
    }

    @Override // net.minecraft.server.ServerInterface
    public String runCommand(String $$0) {
        this.rconConsoleSource.prepareForCommand();
        executeBlocking(() -> {
            getCommands().performPrefixedCommand(this.rconConsoleSource.createCommandSourceStack(), $$0);
        });
        return this.rconConsoleSource.getCommandResponse();
    }

    @Override // net.minecraft.server.MinecraftServer
    public void stopServer() {
        notificationManager().serverShuttingDown();
        super.stopServer();
        Util.shutdownExecutors();
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isSingleplayerOwner(NameAndId $$0) {
        return false;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int getScaledTrackingDistance(int $$0) {
        return (entityBroadcastRangePercentage() * $$0) / 100;
    }

    public int entityBroadcastRangePercentage() {
        return getProperties().entityBroadcastRangePercentage.get().intValue();
    }

    public void setEntityBroadcastRangePercentage(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.entityBroadcastRangePercentage.update(registryAccess(), Integer.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.ServerInterface
    public String getLevelIdName() {
        return this.storageSource.getLevelId();
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean forceSynchronousWrites() {
        return this.settings.getProperties().syncChunkWrites;
    }

    @Override // net.minecraft.server.MinecraftServer
    public TextFilter createTextFilterForPlayer(ServerPlayer $$0) {
        if (this.serverTextFilter != null) {
            return this.serverTextFilter.createContext($$0.getGameProfile());
        }
        return TextFilter.DUMMY;
    }

    @Override // net.minecraft.server.MinecraftServer
    public GameType getForcedGameType() {
        if (forceGameMode()) {
            return this.worldData.getGameType();
        }
        return null;
    }

    public boolean forceGameMode() {
        return this.settings.getProperties().forceGameMode.get().booleanValue();
    }

    public void setForceGameMode(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.forceGameMode.update(registryAccess(), Boolean.valueOf($$0));
        });
        enforceGameTypeForPlayers(getForcedGameType());
    }

    public GameType gameMode() {
        return getProperties().gameMode.get();
    }

    public void setGameMode(GameType $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.gameMode.update(registryAccess(), $$0);
        });
        this.worldData.setGameType(gameMode());
        enforceGameTypeForPlayers(getForcedGameType());
    }

    @Override // net.minecraft.server.MinecraftServer
    public Optional<MinecraftServer.ServerResourcePackInfo> getServerResourcePack() {
        return this.settings.getProperties().serverResourcePackInfo;
    }

    @Override // net.minecraft.server.MinecraftServer
    public void endMetricsRecordingTick() {
        super.endMetricsRecordingTick();
        this.isTickTimeLoggingEnabled = debugSubscribers().hasAnySubscriberFor(DebugSubscriptions.DEDICATED_SERVER_TICK_TIME);
    }

    @Override // net.minecraft.server.MinecraftServer
    public SampleLogger getTickTimeLogger() {
        return this.tickTimeLogger;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean isTickTimeLoggingEnabled() {
        return this.isTickTimeLoggingEnabled;
    }

    @Override // net.minecraft.server.MinecraftServer
    public boolean acceptsTransfers() {
        return this.settings.getProperties().acceptsTransfers.get().booleanValue();
    }

    public void setAcceptsTransfers(boolean $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.acceptsTransfers.update(registryAccess(), Boolean.valueOf($$0));
        });
    }

    @Override // net.minecraft.server.MinecraftServer
    public ServerLinks serverLinks() {
        return this.serverLinks;
    }

    @Override // net.minecraft.server.MinecraftServer
    public int pauseWhenEmptySeconds() {
        return this.settings.getProperties().pauseWhenEmptySeconds.get().intValue();
    }

    public void setPauseWhenEmptySeconds(int $$0) {
        this.settings.update($$1 -> {
            return (DedicatedServerProperties) $$1.pauseWhenEmptySeconds.update(registryAccess(), Integer.valueOf($$0));
        });
    }

    private static ServerLinks createServerLinks(DedicatedServerSettings $$0) {
        Optional<URI> $$1 = parseBugReportLink($$0.getProperties());
        return (ServerLinks) $$1.map($$02 -> {
            return new ServerLinks(List.of(ServerLinks.KnownLinkType.BUG_REPORT.create($$02)));
        }).orElse(ServerLinks.EMPTY);
    }

    private static Optional<URI> parseBugReportLink(DedicatedServerProperties $$0) {
        String $$1 = $$0.bugReportLink;
        if ($$1.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Util.parseAndValidateUntrustedUri($$1));
        } catch (Exception $$2) {
            LOGGER.warn("Failed to parse bug link {}", $$1, $$2);
            return Optional.empty();
        }
    }

    @Override // net.minecraft.server.MinecraftServer
    public Map<String, String> getCodeOfConducts() {
        return this.codeOfConductTexts;
    }
}
