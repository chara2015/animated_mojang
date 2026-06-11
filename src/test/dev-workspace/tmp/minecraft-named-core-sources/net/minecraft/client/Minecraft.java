package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ProfileActionType;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.TracyFrameCapture;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.ClientShutdownWatchdog;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.FramerateLimitTracker;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.TimerQuery;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.datafixers.DataFixer;
import com.mojang.jtracy.DiscontinuousFrame;
import com.mojang.jtracy.TracyClient;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.management.ManagementFactory;
import java.lang.runtime.ObjectMethods;
import java.lang.runtime.SwitchBootstraps;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.Optionull;
import net.minecraft.ReportType;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.ResourceLoadStateTracker;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.entity.ClientMannequin;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.gui.components.debugchart.ProfilerPieChart;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.providers.FreeTypeUtil;
import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screens.BanNoticeScreens;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.LocalPlayerResolver;
import net.minecraft.client.profiling.ClientMetricsSamplersProvider;
import net.minecraft.client.quickplay.QuickPlay;
import net.minecraft.client.quickplay.QuickPlayLog;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.PanoramicScreenshotParameters;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.SkinTextureDownloader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.DryFoliageColorReloadListener;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.MapTextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.client.resources.WaypointStyleManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.server.DownloadedPackSource;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.telemetry.ClientTelemetryManager;
import net.minecraft.client.telemetry.TelemetryProperty;
import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.gizmos.SimpleGizmoCollector;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketProcessor;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.KeybindResolver;
import net.minecraft.network.protocol.game.ServerboundClientTickEndPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.server.dialog.Dialogs;
import net.minecraft.server.level.ChunkLevel;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.level.progress.LoggingLevelLoadListener;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.ProfileResolver;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DialogTags;
import net.minecraft.util.CommonLinks;
import net.minecraft.util.Crypt;
import net.minecraft.util.FileUtil;
import net.minecraft.util.FileZipper;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.ModCheck;
import net.minecraft.util.TimeSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.profiling.ContinuousProfiler;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.Zone;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.PiercingWeapon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.scores.ScoreHolder;
import org.apache.commons.io.FileUtils;
import org.joml.Vector3f;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/Minecraft.class */
public class Minecraft extends ReentrantBlockableEventLoop<Runnable> implements WindowEventHandler {
    static Minecraft instance;
    private static final int MAX_TICKS_PER_UPDATE = 10;
    public static final String UPDATE_DRIVERS_ADVICE = "Please make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).";
    private final long canary;
    private final Path resourcePackDirectory;
    private final CompletableFuture<ProfileResult> profileFuture;
    private final TextureManager textureManager;
    private final ShaderManager shaderManager;
    private final DataFixer fixerUpper;
    private final VirtualScreen virtualScreen;
    private final Window window;
    private final DeltaTracker.Timer deltaTracker;
    private final RenderBuffers renderBuffers;
    public final LevelRenderer levelRenderer;
    private final EntityRenderDispatcher entityRenderDispatcher;
    private final ItemModelResolver itemModelResolver;
    private final ItemRenderer itemRenderer;
    private final MapRenderer mapRenderer;
    public final ParticleEngine particleEngine;
    private final ParticleResources particleResources;
    private final User user;
    public final Font font;
    public final Font fontFilterFishy;
    public final GameRenderer gameRenderer;
    public final Gui gui;
    public final Options options;
    public final DebugScreenEntryList debugEntries;
    private final HotbarManager hotbarManager;
    public final MouseHandler mouseHandler;
    public final KeyboardHandler keyboardHandler;
    private InputType lastInputType;
    public final File gameDirectory;
    private final String launchedVersion;
    private final String versionType;
    private final Proxy proxy;
    private final boolean offlineDeveloperMode;
    private final LevelStorageSource levelSource;
    private final boolean demo;
    private final boolean allowsMultiplayer;
    private final boolean allowsChat;
    private final ReloadableResourceManager resourceManager;
    private final VanillaPackResources vanillaPackResources;
    private final DownloadedPackSource downloadedPackSource;
    private final PackRepository resourcePackRepository;
    private final LanguageManager languageManager;
    private final BlockColors blockColors;
    private final RenderTarget mainRenderTarget;
    private final TracyFrameCapture tracyFrameCapture;
    private final SoundManager soundManager;
    private final MusicManager musicManager;
    private final FontManager fontManager;
    private final SplashManager splashManager;
    private final GpuWarnlistManager gpuWarnlistManager;
    private final PeriodicNotificationManager regionalCompliancies;
    private final UserApiService userApiService;
    private final CompletableFuture<UserApiService.UserProperties> userPropertiesFuture;
    private final SkinManager skinManager;
    private final AtlasManager atlasManager;
    private final ModelManager modelManager;
    private final BlockRenderDispatcher blockRenderer;
    private final MapTextureManager mapTextureManager;
    private final WaypointStyleManager waypointStyles;
    private final ToastManager toastManager;
    private final Tutorial tutorial;
    private final PlayerSocialManager playerSocialManager;
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    private final ClientTelemetryManager telemetryManager;
    private final ProfileKeyPairManager profileKeyPairManager;
    private final RealmsDataFetcher realmsDataFetcher;
    private final QuickPlayLog quickPlayLog;
    private final Services services;
    private final PlayerSkinRenderCache playerSkinRenderCache;
    public MultiPlayerGameMode gameMode;
    public ClientLevel level;
    public LocalPlayer player;
    private IntegratedServer singleplayerServer;
    private Connection pendingConnection;
    private boolean isLocalServer;
    private Entity cameraEntity;
    public Entity crosshairPickEntity;
    public HitResult hitResult;
    private int rightClickDelay;
    protected int missTime;
    private volatile boolean pause;
    private long lastNanoTime;
    private long lastTime;
    private int frames;
    public boolean noRender;
    public Screen screen;
    private Overlay overlay;
    private boolean clientLevelTeardownInProgress;
    Thread gameThread;
    private volatile boolean running;
    private Supplier<CrashReport> delayedCrash;
    private static int fps;
    private long frameTimeNs;
    private final FramerateLimitTracker framerateLimitTracker;
    public boolean wireframe;
    public boolean smartCull;
    private boolean windowActive;
    private CompletableFuture<Void> pendingReload;
    private TutorialToast socialInteractionsToast;
    private int fpsPieRenderTicks;
    private final ContinuousProfiler fpsPieProfiler;
    private MetricsRecorder metricsRecorder;
    private final ResourceLoadStateTracker reloadStateTracker;
    private long savedCpuDuration;
    private double gpuUtilization;
    private TimerQuery.FrameProfile currentFrameProfile;
    private final GameNarrator narrator;
    private final ChatListener chatListener;
    private ReportingContext reportingContext;
    private final CommandHistory commandHistory;
    private final DirectoryValidator directoryValidator;
    private boolean gameLoadFinished;
    private final long clientStartTimeMs;
    private long clientTickCount;
    private final PacketProcessor packetProcessor;
    private final SimpleGizmoCollector perTickGizmos;
    private List<SimpleGizmoCollector.GizmoInstance> drainedLatestTickGizmos;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Identifier DEFAULT_FONT = Identifier.withDefaultNamespace(GameTestEnvironments.DEFAULT);
    public static final Identifier UNIFORM_FONT = Identifier.withDefaultNamespace("uniform");
    public static final Identifier ALT_FONT = Identifier.withDefaultNamespace("alt");
    private static final Identifier REGIONAL_COMPLIANCIES = Identifier.withDefaultNamespace("regional_compliancies.json");
    private static final CompletableFuture<Unit> RESOURCE_RELOAD_INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
    private static final Component SOCIAL_INTERACTIONS_NOT_AVAILABLE = Component.translatable("multiplayer.socialInteractions.not_available");
    private static final Component SAVING_LEVEL = Component.translatable("menu.savingLevel");

    public Minecraft(final GameConfig $$0) {
        super("Client");
        this.canary = Double.doubleToLongBits(3.141592653589793d);
        this.deltaTracker = new DeltaTracker.Timer(20.0f, 0L, this::getTickTargetMillis);
        this.lastInputType = InputType.NONE;
        this.regionalCompliancies = new PeriodicNotificationManager(REGIONAL_COMPLIANCIES, Minecraft::countryEqualsISO3);
        this.lastNanoTime = Util.getNanos();
        this.smartCull = true;
        this.metricsRecorder = InactiveMetricsRecorder.INSTANCE;
        this.reloadStateTracker = new ResourceLoadStateTracker();
        this.perTickGizmos = new SimpleGizmoCollector();
        this.drainedLatestTickGizmos = new ArrayList();
        instance = this;
        this.clientStartTimeMs = System.currentTimeMillis();
        this.gameDirectory = $$0.location.gameDirectory;
        File $$1 = $$0.location.assetDirectory;
        this.resourcePackDirectory = $$0.location.resourcePackDirectory.toPath();
        this.launchedVersion = $$0.game.launchVersion;
        this.versionType = $$0.game.versionType;
        Path $$2 = this.gameDirectory.toPath();
        this.directoryValidator = LevelStorageSource.parseValidator($$2.resolve(LevelStorageSource.ALLOWED_SYMLINKS_CONFIG_NAME));
        ClientPackSource $$3 = new ClientPackSource($$0.location.getExternalAssetSource(), this.directoryValidator);
        this.downloadedPackSource = new DownloadedPackSource(this, $$2.resolve("downloads"), $$0.user);
        RepositorySource $$4 = new FolderRepositorySource(this.resourcePackDirectory, PackType.CLIENT_RESOURCES, PackSource.DEFAULT, this.directoryValidator);
        this.resourcePackRepository = new PackRepository($$3, this.downloadedPackSource.createRepositorySource(), $$4);
        this.vanillaPackResources = $$3.getVanillaPack();
        this.proxy = $$0.user.proxy;
        this.offlineDeveloperMode = $$0.game.offlineDeveloperMode;
        YggdrasilAuthenticationService $$5 = this.offlineDeveloperMode ? YggdrasilAuthenticationService.createOffline(this.proxy) : new YggdrasilAuthenticationService(this.proxy);
        this.services = Services.create($$5, this.gameDirectory);
        this.user = $$0.user.user;
        this.profileFuture = this.offlineDeveloperMode ? CompletableFuture.completedFuture(null) : CompletableFuture.supplyAsync(() -> {
            return this.services.sessionService().fetchProfile(this.user.getProfileId(), true);
        }, Util.nonCriticalIoPool());
        this.userApiService = createUserApiService($$5, $$0);
        this.userPropertiesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return this.userApiService.fetchProperties();
            } catch (AuthenticationException $$02) {
                LOGGER.error("Failed to fetch user properties", $$02);
                return UserApiService.OFFLINE_PROPERTIES;
            }
        }, Util.nonCriticalIoPool());
        LOGGER.info("Setting user: {}", this.user.getName());
        LOGGER.debug("(Session ID is {})", this.user.getSessionId());
        this.demo = $$0.game.demo;
        this.allowsMultiplayer = !$$0.game.disableMultiplayer;
        this.allowsChat = !$$0.game.disableChat;
        this.singleplayerServer = null;
        KeybindResolver.setKeyResolver(KeyMapping::createNameSupplier);
        this.fixerUpper = DataFixers.getDataFixer();
        this.gameThread = Thread.currentThread();
        this.options = new Options(this, this.gameDirectory);
        this.debugEntries = new DebugScreenEntryList(this.gameDirectory);
        this.toastManager = new ToastManager(this, this.options);
        boolean $$6 = this.options.startedCleanly;
        this.options.startedCleanly = false;
        this.options.save();
        this.running = true;
        this.tutorial = new Tutorial(this, this.options);
        this.hotbarManager = new HotbarManager($$2, this.fixerUpper);
        LOGGER.info("Backend library: {}", RenderSystem.getBackendDescription());
        DisplayData $$7 = $$0.display;
        if (this.options.overrideHeight > 0 && this.options.overrideWidth > 0) {
            $$7 = $$0.display.withSize(this.options.overrideWidth, this.options.overrideHeight);
        }
        if (!$$6) {
            $$7 = $$7.withFullscreen(false);
            this.options.fullscreenVideoModeString = null;
            LOGGER.warn("Detected unexpected shutdown during last game startup: resetting fullscreen mode");
        }
        Util.timeSource = RenderSystem.initBackendSystem();
        this.virtualScreen = new VirtualScreen(this);
        this.window = this.virtualScreen.newWindow($$7, this.options.fullscreenVideoModeString, createTitle());
        setWindowActive(true);
        this.window.setWindowCloseCallback(new Runnable() { // from class: net.minecraft.client.Minecraft.1
            private boolean threadStarted;

            @Override // java.lang.Runnable
            public void run() {
                if (!this.threadStarted) {
                    this.threadStarted = true;
                    ClientShutdownWatchdog.startShutdownWatchdog($$0.location.gameDirectory, Minecraft.this.gameThread.threadId());
                }
            }
        });
        GameLoadTimesEvent.INSTANCE.endStep(TelemetryProperty.LOAD_TIME_PRE_WINDOW_MS);
        try {
            this.window.setIcon(this.vanillaPackResources, SharedConstants.getCurrentVersion().stable() ? IconSet.RELEASE : IconSet.SNAPSHOT);
        } catch (IOException $$8) {
            LOGGER.error("Couldn't set icon", $$8);
        }
        this.mouseHandler = new MouseHandler(this);
        this.mouseHandler.setup(this.window);
        this.keyboardHandler = new KeyboardHandler(this);
        this.keyboardHandler.setup(this.window);
        RenderSystem.initRenderer(this.window.handle(), this.options.glDebugVerbosity, SharedConstants.DEBUG_SYNCHRONOUS_GL_LOGS, ($$02, $$12) -> {
            return getShaderManager().getShader($$02, $$12);
        }, $$0.game.renderDebugLabels);
        this.options.applyGraphicsPreset(this.options.graphicsPreset().get());
        LOGGER.info("Using optional rendering extensions: {}", String.join(ComponentUtils.DEFAULT_SEPARATOR_TEXT, RenderSystem.getDevice().getEnabledExtensions()));
        this.mainRenderTarget = new MainTarget(this.window.getWidth(), this.window.getHeight());
        this.resourceManager = new ReloadableResourceManager(PackType.CLIENT_RESOURCES);
        this.resourcePackRepository.reload();
        this.options.loadSelectedResourcePacks(this.resourcePackRepository);
        this.languageManager = new LanguageManager(this.options.languageCode, $$03 -> {
            if (this.player != null) {
                this.player.connection.updateSearchTrees();
            }
        });
        this.resourceManager.registerReloadListener(this.languageManager);
        this.textureManager = new TextureManager(this.resourceManager);
        this.resourceManager.registerReloadListener(this.textureManager);
        this.shaderManager = new ShaderManager(this.textureManager, this::triggerResourcePackRecovery);
        this.resourceManager.registerReloadListener(this.shaderManager);
        SkinTextureDownloader $$9 = new SkinTextureDownloader(this.proxy, this.textureManager, this);
        this.skinManager = new SkinManager($$1.toPath().resolve("skins"), this.services, $$9, this);
        this.levelSource = new LevelStorageSource($$2.resolve("saves"), $$2.resolve("backups"), this.directoryValidator, this.fixerUpper);
        this.commandHistory = new CommandHistory($$2);
        this.musicManager = new MusicManager(this);
        this.soundManager = new SoundManager(this.options);
        this.resourceManager.registerReloadListener(this.soundManager);
        this.splashManager = new SplashManager(this.user);
        this.resourceManager.registerReloadListener(this.splashManager);
        this.atlasManager = new AtlasManager(this.textureManager, this.options.mipmapLevels().get().intValue());
        this.resourceManager.registerReloadListener(this.atlasManager);
        ProfileResolver $$10 = new LocalPlayerResolver(this, this.services.profileResolver());
        this.playerSkinRenderCache = new PlayerSkinRenderCache(this.textureManager, this.skinManager, $$10);
        ClientMannequin.registerOverrides(this.playerSkinRenderCache);
        this.fontManager = new FontManager(this.textureManager, this.atlasManager, this.playerSkinRenderCache);
        this.font = this.fontManager.createFont();
        this.fontFilterFishy = this.fontManager.createFontFilterFishy();
        this.resourceManager.registerReloadListener(this.fontManager);
        updateFontOptions();
        this.resourceManager.registerReloadListener(new GrassColorReloadListener());
        this.resourceManager.registerReloadListener(new FoliageColorReloadListener());
        this.resourceManager.registerReloadListener(new DryFoliageColorReloadListener());
        this.window.setErrorSection("Startup");
        RenderSystem.setupDefaultState();
        this.window.setErrorSection("Post startup");
        this.blockColors = BlockColors.createDefault();
        this.modelManager = new ModelManager(this.blockColors, this.atlasManager, this.playerSkinRenderCache);
        this.resourceManager.registerReloadListener(this.modelManager);
        EquipmentAssetManager $$11 = new EquipmentAssetManager();
        this.resourceManager.registerReloadListener($$11);
        this.itemModelResolver = new ItemModelResolver(this.modelManager);
        this.itemRenderer = new ItemRenderer();
        this.mapTextureManager = new MapTextureManager(this.textureManager);
        this.mapRenderer = new MapRenderer(this.atlasManager, this.mapTextureManager);
        try {
            int $$122 = Runtime.getRuntime().availableProcessors();
            Tesselator.init();
            this.renderBuffers = new RenderBuffers($$122);
            this.playerSocialManager = new PlayerSocialManager(this, this.userApiService);
            this.blockRenderer = new BlockRenderDispatcher(this.modelManager.getBlockModelShaper(), this.atlasManager, this.blockColors);
            this.resourceManager.registerReloadListener(this.blockRenderer);
            this.entityRenderDispatcher = new EntityRenderDispatcher(this, this.textureManager, this.itemModelResolver, this.mapRenderer, this.blockRenderer, this.atlasManager, this.font, this.options, this.modelManager.entityModels(), $$11, this.playerSkinRenderCache);
            this.resourceManager.registerReloadListener(this.entityRenderDispatcher);
            this.blockEntityRenderDispatcher = new BlockEntityRenderDispatcher(this.font, this.modelManager.entityModels(), this.blockRenderer, this.itemModelResolver, this.itemRenderer, this.entityRenderDispatcher, this.atlasManager, this.playerSkinRenderCache);
            this.resourceManager.registerReloadListener(this.blockEntityRenderDispatcher);
            this.particleResources = new ParticleResources();
            this.resourceManager.registerReloadListener(this.particleResources);
            this.particleEngine = new ParticleEngine(this.level, this.particleResources);
            ParticleResources particleResources = this.particleResources;
            ParticleEngine particleEngine = this.particleEngine;
            Objects.requireNonNull(particleEngine);
            particleResources.onReload(particleEngine::clearParticles);
            this.waypointStyles = new WaypointStyleManager();
            this.resourceManager.registerReloadListener(this.waypointStyles);
            this.gameRenderer = new GameRenderer(this, this.entityRenderDispatcher.getItemInHandRenderer(), this.renderBuffers, this.blockRenderer);
            this.levelRenderer = new LevelRenderer(this, this.entityRenderDispatcher, this.blockEntityRenderDispatcher, this.renderBuffers, this.gameRenderer.getLevelRenderState(), this.gameRenderer.getFeatureRenderDispatcher());
            this.resourceManager.registerReloadListener(this.levelRenderer);
            this.resourceManager.registerReloadListener(this.levelRenderer.getCloudRenderer());
            this.gpuWarnlistManager = new GpuWarnlistManager();
            this.resourceManager.registerReloadListener(this.gpuWarnlistManager);
            this.resourceManager.registerReloadListener(this.regionalCompliancies);
            this.gui = new Gui(this);
            RealmsClient $$14 = RealmsClient.getOrCreate(this);
            this.realmsDataFetcher = new RealmsDataFetcher($$14);
            RenderSystem.setErrorCallback(this::onFullscreenError);
            if (this.mainRenderTarget.width != this.window.getWidth() || this.mainRenderTarget.height != this.window.getHeight()) {
                StringBuilder $$15 = new StringBuilder("Recovering from unsupported resolution (" + this.window.getWidth() + "x" + this.window.getHeight() + ").\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).");
                try {
                    GpuDevice $$16 = RenderSystem.getDevice();
                    List<String> $$17 = $$16.getLastDebugMessages();
                    if (!$$17.isEmpty()) {
                        $$15.append("\n\nReported GL debug messages:\n").append(String.join(Crypt.MIME_LINE_SEPARATOR, $$17));
                    }
                } catch (Throwable th) {
                }
                this.window.setWindowed(this.mainRenderTarget.width, this.mainRenderTarget.height);
                TinyFileDialogs.tinyfd_messageBox(JfrProfiler.ROOT_CATEGORY, $$15.toString(), "ok", "error", false);
            } else if (this.options.fullscreen().get().booleanValue() && !this.window.isFullscreen()) {
                if ($$6) {
                    this.window.toggleFullScreen();
                    this.options.fullscreen().set(Boolean.valueOf(this.window.isFullscreen()));
                } else {
                    this.options.fullscreen().set(false);
                }
            }
            this.window.updateVsync(this.options.enableVsync().get().booleanValue());
            this.window.updateRawMouseInput(this.options.rawMouseInput().get().booleanValue());
            this.window.setAllowCursorChanges(this.options.allowCursorChanges().get().booleanValue());
            this.window.setDefaultErrorCallback();
            resizeDisplay();
            this.gameRenderer.preloadUiShader(this.vanillaPackResources.asProvider());
            this.telemetryManager = new ClientTelemetryManager(this, this.userApiService, this.user);
            this.profileKeyPairManager = this.offlineDeveloperMode ? ProfileKeyPairManager.EMPTY_KEY_MANAGER : ProfileKeyPairManager.create(this.userApiService, this.user, $$2);
            this.narrator = new GameNarrator(this);
            this.narrator.checkStatus(this.options.narrator().get() != NarratorStatus.OFF);
            this.chatListener = new ChatListener(this);
            this.chatListener.setMessageDelay(this.options.chatDelay().get().doubleValue());
            this.reportingContext = ReportingContext.create(ReportEnvironment.local(), this.userApiService);
            TitleScreen.registerTextures(this.textureManager);
            LoadingOverlay.registerTextures(this.textureManager);
            this.gameRenderer.getPanorama().registerTextures(this.textureManager);
            setScreen(new GenericMessageScreen(Component.translatable("gui.loadingMinecraft")));
            List<PackResources> $$18 = this.resourcePackRepository.openAllSelected();
            this.reloadStateTracker.startReload(ResourceLoadStateTracker.ReloadReason.INITIAL, $$18);
            ReloadInstance $$19 = this.resourceManager.createReload(Util.backgroundExecutor().forName("resourceLoad"), this, RESOURCE_RELOAD_INITIAL_TASK, $$18);
            GameLoadTimesEvent.INSTANCE.beginStep(TelemetryProperty.LOAD_TIME_LOADING_OVERLAY_MS);
            GameLoadCookie $$20 = new GameLoadCookie($$14, $$0.quickPlay);
            setOverlay(new LoadingOverlay(this, $$19, $$13 -> {
                Util.ifElse($$13, $$13 -> {
                    rollbackResourcePacks($$13, $$20);
                }, () -> {
                    if (SharedConstants.IS_RUNNING_IN_IDE) {
                        selfTest();
                    }
                    this.reloadStateTracker.finishReload();
                    onResourceLoadFinished($$20);
                });
            }, false));
            this.quickPlayLog = QuickPlayLog.of($$0.quickPlay.logPath());
            this.framerateLimitTracker = new FramerateLimitTracker(this.options, this);
            TimeSource.NanoTimeSource nanoTimeSource = Util.timeSource;
            IntSupplier intSupplier = () -> {
                return this.fpsPieRenderTicks;
            };
            FramerateLimitTracker framerateLimitTracker = this.framerateLimitTracker;
            Objects.requireNonNull(framerateLimitTracker);
            this.fpsPieProfiler = new ContinuousProfiler(nanoTimeSource, intSupplier, framerateLimitTracker::isHeavilyThrottled);
            if (TracyClient.isAvailable() && $$0.game.captureTracyImages) {
                this.tracyFrameCapture = new TracyFrameCapture();
            } else {
                this.tracyFrameCapture = null;
            }
            this.packetProcessor = new PacketProcessor(this.gameThread);
        } catch (OutOfMemoryError $$132) {
            TinyFileDialogs.tinyfd_messageBox(JfrProfiler.ROOT_CATEGORY, "Oh no! The game was unable to allocate memory off-heap while trying to start. You may try to free some memory by closing other applications on your computer, check that your system meets the minimum requirements, and try again. If the problem persists, please visit: " + String.valueOf(CommonLinks.GENERAL_HELP), "ok", "error", true);
            throw new SilentInitException("Unable to allocate render buffers", $$132);
        }
    }

    public boolean hasShiftDown() {
        Window $$0 = getWindow();
        return InputConstants.isKeyDown($$0, InputConstants.KEY_LSHIFT) || InputConstants.isKeyDown($$0, InputConstants.KEY_RSHIFT);
    }

    public boolean hasControlDown() {
        Window $$0 = getWindow();
        return InputConstants.isKeyDown($$0, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown($$0, InputConstants.KEY_RCONTROL);
    }

    public boolean hasAltDown() {
        Window $$0 = getWindow();
        return InputConstants.isKeyDown($$0, InputConstants.KEY_LALT) || InputConstants.isKeyDown($$0, InputConstants.KEY_RALT);
    }

    private void onResourceLoadFinished(GameLoadCookie $$0) {
        if (!this.gameLoadFinished) {
            this.gameLoadFinished = true;
            onGameLoadFinished($$0);
        }
    }

    private void onGameLoadFinished(GameLoadCookie $$0) {
        Runnable $$1 = buildInitialScreens($$0);
        GameLoadTimesEvent.INSTANCE.endStep(TelemetryProperty.LOAD_TIME_LOADING_OVERLAY_MS);
        GameLoadTimesEvent.INSTANCE.endStep(TelemetryProperty.LOAD_TIME_TOTAL_TIME_MS);
        GameLoadTimesEvent.INSTANCE.send(this.telemetryManager.getOutsideSessionSender());
        $$1.run();
        this.options.startedCleanly = true;
        this.options.save();
    }

    public boolean isGameLoadFinished() {
        return this.gameLoadFinished;
    }

    private Runnable buildInitialScreens(GameLoadCookie $$0) {
        List<Function<Runnable, Screen>> $$1 = new ArrayList<>();
        boolean $$2 = addInitialScreens($$1);
        Runnable $$3 = () -> {
            if ($$0 != null && $$0.quickPlayData.isEnabled()) {
                QuickPlay.connect(this, $$0.quickPlayData.variant(), $$0.realmsClient());
            } else {
                setScreen(new TitleScreen(true, new LogoRenderer($$2)));
            }
        };
        for (Function<Runnable, Screen> $$4 : Lists.reverse($$1)) {
            Screen $$5 = $$4.apply($$3);
            $$3 = () -> {
                setScreen($$5);
            };
        }
        return $$3;
    }

    private boolean addInitialScreens(List<Function<Runnable, Screen>> $$0) {
        boolean $$1 = false;
        if (this.options.onboardAccessibility || SharedConstants.DEBUG_FORCE_ONBOARDING_SCREEN) {
            $$0.add($$02 -> {
                return new AccessibilityOnboardingScreen(this.options, $$02);
            });
            $$1 = true;
        }
        BanDetails $$2 = multiplayerBan();
        if ($$2 != null) {
            $$0.add($$12 -> {
                return BanNoticeScreens.create($$12 -> {
                    if ($$12) {
                        Util.getPlatform().openUri(CommonLinks.SUSPENSION_HELP);
                    }
                    $$12.run();
                }, $$2);
            });
        }
        ProfileResult $$3 = this.profileFuture.join();
        if ($$3 != null) {
            GameProfile $$4 = $$3.profile();
            Set<ProfileActionType> $$5 = $$3.actions();
            if ($$5.contains(ProfileActionType.FORCED_NAME_CHANGE)) {
                $$0.add($$13 -> {
                    return BanNoticeScreens.createNameBan($$4.name(), $$13);
                });
            }
            if ($$5.contains(ProfileActionType.USING_BANNED_SKIN)) {
                $$0.add(BanNoticeScreens::createSkinBan);
            }
        }
        return $$1;
    }

    private static boolean countryEqualsISO3(Object $$0) {
        try {
            return Locale.getDefault().getISO3Country().equals($$0);
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public void updateTitle() {
        this.window.setTitle(createTitle());
    }

    private String createTitle() {
        StringBuilder $$0 = new StringBuilder(JfrProfiler.ROOT_CATEGORY);
        if (checkModStatus().shouldReportAsModified()) {
            $$0.append(ScoreHolder.WILDCARD_NAME);
        }
        $$0.append(" ");
        $$0.append(SharedConstants.getCurrentVersion().name());
        ClientPacketListener $$1 = getConnection();
        if ($$1 != null && $$1.getConnection().isConnected()) {
            $$0.append(" - ");
            ServerData $$2 = getCurrentServer();
            if (this.singleplayerServer != null && !this.singleplayerServer.isPublished()) {
                $$0.append(I18n.get("title.singleplayer", new Object[0]));
            } else if ($$2 != null && $$2.isRealm()) {
                $$0.append(I18n.get("title.multiplayer.realms", new Object[0]));
            } else if (this.singleplayerServer != null || ($$2 != null && $$2.isLan())) {
                $$0.append(I18n.get("title.multiplayer.lan", new Object[0]));
            } else {
                $$0.append(I18n.get("title.multiplayer.other", new Object[0]));
            }
        }
        return $$0.toString();
    }

    private UserApiService createUserApiService(YggdrasilAuthenticationService $$0, GameConfig $$1) {
        if ($$1.game.offlineDeveloperMode) {
            return UserApiService.OFFLINE;
        }
        return $$0.createUserApiService($$1.user.user.getAccessToken());
    }

    public boolean isOfflineDeveloperMode() {
        return this.offlineDeveloperMode;
    }

    public static ModCheck checkModStatus() {
        return ModCheck.identify("vanilla", ClientBrandRetriever::getClientModName, "Client", Minecraft.class);
    }

    private void rollbackResourcePacks(Throwable $$0, GameLoadCookie $$1) {
        if (this.resourcePackRepository.getSelectedIds().size() > 1) {
            clearResourcePacksOnError($$0, null, $$1);
        } else {
            Util.throwAsRuntime($$0);
        }
    }

    public void clearResourcePacksOnError(Throwable $$0, Component $$1, GameLoadCookie $$2) {
        LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", $$0);
        this.reloadStateTracker.startRecovery($$0);
        this.downloadedPackSource.onRecovery();
        this.resourcePackRepository.setSelected(Collections.emptyList());
        this.options.resourcePacks.clear();
        this.options.incompatibleResourcePacks.clear();
        this.options.save();
        reloadResourcePacks(true, $$2).thenRunAsync(() -> {
            addResourcePackLoadFailToast($$1);
        }, (Executor) this);
    }

    private void abortResourcePackRecovery() {
        setOverlay(null);
        if (this.level != null) {
            this.level.disconnect(ClientLevel.DEFAULT_QUIT_MESSAGE);
            disconnectWithProgressScreen();
        }
        setScreen(new TitleScreen());
        addResourcePackLoadFailToast(null);
    }

    private void addResourcePackLoadFailToast(Component $$0) {
        ToastManager $$1 = getToastManager();
        SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.PACK_LOAD_FAILURE, Component.translatable("resourcePack.load_fail"), $$0);
    }

    public void triggerResourcePackRecovery(Exception $$0) {
        if (!this.resourcePackRepository.isAbleToClearAnyPack()) {
            if (this.resourcePackRepository.getSelectedIds().size() <= 1) {
                LOGGER.error(LogUtils.FATAL_MARKER, $$0.getMessage(), $$0);
                emergencySaveAndCrash(new CrashReport($$0.getMessage(), $$0));
                return;
            } else {
                schedule(this::abortResourcePackRecovery);
                return;
            }
        }
        clearResourcePacksOnError($$0, Component.translatable("resourcePack.runtime_failure"), null);
    }

    public void run() {
        SingleTickProfiler $$2;
        boolean $$3;
        Profiler.Scope $$4;
        this.gameThread = Thread.currentThread();
        if (Runtime.getRuntime().availableProcessors() > 4) {
            this.gameThread.setPriority(10);
        }
        DiscontinuousFrame $$0 = TracyClient.createDiscontinuousFrame("Client Tick");
        boolean $$1 = false;
        while (this.running) {
            try {
                handleDelayedCrash();
                try {
                    $$2 = SingleTickProfiler.createTickProfiler("Renderer");
                    $$3 = getDebugOverlay().showProfilerChart();
                    $$4 = Profiler.use(constructProfiler($$3, $$2));
                } catch (OutOfMemoryError $$5) {
                    if ($$1) {
                        throw $$5;
                    }
                    emergencySave();
                    setScreen(new OutOfMemoryScreen());
                    System.gc();
                    LOGGER.error(LogUtils.FATAL_MARKER, "Out of memory", $$5);
                    $$1 = true;
                }
                try {
                    this.metricsRecorder.startTick();
                    $$0.start();
                    runTick(!$$1);
                    $$0.end();
                    this.metricsRecorder.endTick();
                    if ($$4 != null) {
                        $$4.close();
                    }
                    finishProfilers($$3, $$2);
                } catch (Throwable th) {
                    if ($$4 != null) {
                        try {
                            $$4.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (ReportedException $$6) {
                LOGGER.error(LogUtils.FATAL_MARKER, "Reported exception thrown!", $$6);
                emergencySaveAndCrash($$6.getReport());
                return;
            } catch (Throwable $$7) {
                LOGGER.error(LogUtils.FATAL_MARKER, "Unreported exception thrown!", $$7);
                emergencySaveAndCrash(new CrashReport("Unexpected error", $$7));
                return;
            }
        }
    }

    void updateFontOptions() {
        this.fontManager.updateOptions(this.options);
    }

    private void onFullscreenError(int $$0, long $$1) {
        this.options.enableVsync().set(false);
        this.options.save();
    }

    public RenderTarget getMainRenderTarget() {
        return this.mainRenderTarget;
    }

    public String getLaunchedVersion() {
        return this.launchedVersion;
    }

    public String getVersionType() {
        return this.versionType;
    }

    public void delayCrash(CrashReport $$0) {
        this.delayedCrash = () -> {
            return fillReport($$0);
        };
    }

    public void delayCrashRaw(CrashReport $$0) {
        this.delayedCrash = () -> {
            return $$0;
        };
    }

    private void handleDelayedCrash() {
        if (this.delayedCrash != null) {
            crash(this, this.gameDirectory, this.delayedCrash.get());
        }
    }

    public void emergencySaveAndCrash(CrashReport $$0) {
        MemoryReserve.release();
        CrashReport $$1 = fillReport($$0);
        emergencySave();
        crash(this, this.gameDirectory, $$1);
    }

    public static int saveReport(File $$0, CrashReport $$1) {
        Path $$2 = $$0.toPath().resolve("crash-reports");
        Path $$3 = $$2.resolve("crash-" + Util.getFilenameFormattedDateTime() + "-client.txt");
        Bootstrap.realStdoutPrintln($$1.getFriendlyReport(ReportType.CRASH));
        if ($$1.getSaveFile() != null) {
            Bootstrap.realStdoutPrintln("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf($$1.getSaveFile().toAbsolutePath()));
            return -1;
        }
        if ($$1.saveToFile($$3, ReportType.CRASH)) {
            Bootstrap.realStdoutPrintln("#@!@# Game crashed! Crash report saved to: #@!@# " + String.valueOf($$3.toAbsolutePath()));
            return -1;
        }
        Bootstrap.realStdoutPrintln("#@?@# Game crashed! Crash report could not be saved. #@?@#");
        return -2;
    }

    public static void crash(Minecraft $$0, File $$1, CrashReport $$2) {
        int $$3 = saveReport($$1, $$2);
        if ($$0 != null) {
            $$0.soundManager.emergencyShutdown();
        }
        System.exit($$3);
    }

    public boolean isEnforceUnicode() {
        return this.options.forceUnicodeFont().get().booleanValue();
    }

    public CompletableFuture<Void> reloadResourcePacks() {
        return reloadResourcePacks(false, null);
    }

    private CompletableFuture<Void> reloadResourcePacks(boolean $$0, GameLoadCookie $$1) {
        if (this.pendingReload != null) {
            return this.pendingReload;
        }
        CompletableFuture<Void> $$2 = new CompletableFuture<>();
        if (!$$0 && (this.overlay instanceof LoadingOverlay)) {
            this.pendingReload = $$2;
            return $$2;
        }
        this.resourcePackRepository.reload();
        List<PackResources> $$3 = this.resourcePackRepository.openAllSelected();
        if (!$$0) {
            this.reloadStateTracker.startReload(ResourceLoadStateTracker.ReloadReason.MANUAL, $$3);
        }
        setOverlay(new LoadingOverlay(this, this.resourceManager.createReload(Util.backgroundExecutor().forName("resourceLoad"), this, RESOURCE_RELOAD_INITIAL_TASK, $$3), $$32 -> {
            Util.ifElse($$32, $$22 -> {
                if ($$0) {
                    this.downloadedPackSource.onRecoveryFailure();
                    abortResourcePackRecovery();
                } else {
                    rollbackResourcePacks($$22, $$1);
                }
            }, () -> {
                this.levelRenderer.allChanged();
                this.reloadStateTracker.finishReload();
                this.downloadedPackSource.onReloadSuccess();
                $$2.complete(null);
                onResourceLoadFinished($$1);
            });
        }, !$$0));
        return $$2;
    }

    private void selfTest() {
        boolean $$0 = false;
        BlockModelShaper $$1 = getBlockRenderer().getBlockModelShaper();
        BlockStateModel $$2 = $$1.getModelManager().getMissingBlockStateModel();
        for (Block $$3 : BuiltInRegistries.BLOCK) {
            UnmodifiableIterator it = $$3.getStateDefinition().getPossibleStates().iterator();
            while (it.hasNext()) {
                BlockState $$4 = (BlockState) it.next();
                if ($$4.getRenderShape() == RenderShape.MODEL) {
                    BlockStateModel $$5 = $$1.getBlockModel($$4);
                    if ($$5 == $$2) {
                        LOGGER.debug("Missing model for: {}", $$4);
                        $$0 = true;
                    }
                }
            }
        }
        TextureAtlasSprite $$6 = $$2.particleIcon();
        for (Block $$7 : BuiltInRegistries.BLOCK) {
            UnmodifiableIterator it2 = $$7.getStateDefinition().getPossibleStates().iterator();
            while (it2.hasNext()) {
                BlockState $$8 = (BlockState) it2.next();
                TextureAtlasSprite $$9 = $$1.getParticleIcon($$8);
                if (!$$8.isAir() && $$9 == $$6) {
                    LOGGER.debug("Missing particle icon for: {}", $$8);
                }
            }
        }
        BuiltInRegistries.ITEM.listElements().forEach($$02 -> {
            Item $$12 = (Item) $$02.value();
            String $$22 = $$12.getDescriptionId();
            String $$32 = Component.translatable($$22).getString();
            if ($$32.toLowerCase(Locale.ROOT).equals($$12.getDescriptionId())) {
                LOGGER.debug("Missing translation for: {} {} {}", new Object[]{$$02.key().identifier(), $$22, $$12});
            }
        });
        if ($$0 | MenuScreens.selfTest() | EntityRenderers.validateRegistrations()) {
            throw new IllegalStateException("Your game data is foobar, fix the errors above!");
        }
    }

    public LevelStorageSource getLevelSource() {
        return this.levelSource;
    }

    public void openChatScreen(ChatComponent.ChatMethod $$0) {
        ChatStatus $$1 = getChatStatus();
        if (!$$1.isChatAllowed(isLocalServer())) {
            if (this.gui.isShowingChatDisabledByPlayer()) {
                this.gui.setChatDisabledByPlayerShown(false);
                setScreen(new ConfirmLinkScreen($$02 -> {
                    if ($$02) {
                        Util.getPlatform().openUri(CommonLinks.ACCOUNT_SETTINGS);
                    }
                    setScreen(null);
                }, ChatStatus.INFO_DISABLED_BY_PROFILE, CommonLinks.ACCOUNT_SETTINGS, true));
                return;
            } else {
                Component $$2 = $$1.getMessage();
                this.gui.setOverlayMessage($$2, false);
                this.narrator.saySystemNow($$2);
                this.gui.setChatDisabledByPlayerShown($$1 == ChatStatus.DISABLED_BY_PROFILE);
                return;
            }
        }
        this.gui.getChat().openScreen($$0, ChatScreen::new);
    }

    public void setScreen(Screen $$0) {
        if (SharedConstants.IS_RUNNING_IN_IDE && Thread.currentThread() != this.gameThread) {
            LOGGER.error("setScreen called from non-game thread");
        }
        if (this.screen != null) {
            this.screen.removed();
        } else {
            setLastInputType(InputType.NONE);
        }
        if ($$0 == null) {
            if (this.clientLevelTeardownInProgress) {
                throw new IllegalStateException("Trying to return to in-game GUI during disconnection");
            }
            if (this.level == null) {
                $$0 = new TitleScreen();
            } else if (this.player.isDeadOrDying()) {
                if (this.player.shouldShowDeathScreen()) {
                    $$0 = new DeathScreen(null, this.level.getLevelData().isHardcore(), this.player);
                } else {
                    this.player.respawn();
                }
            } else {
                $$0 = this.gui.getChat().restoreChatScreen();
            }
        }
        this.screen = $$0;
        if (this.screen != null) {
            this.screen.added();
        }
        if ($$0 != null) {
            this.mouseHandler.releaseMouse();
            KeyMapping.releaseAll();
            $$0.init(this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
            this.noRender = false;
        } else {
            if (this.level != null) {
                KeyMapping.restoreToggleStatesOnScreenClosed();
            }
            this.soundManager.resume();
            this.mouseHandler.grabMouse();
        }
        updateTitle();
    }

    public void setOverlay(Overlay $$0) {
        this.overlay = $$0;
    }

    public void destroy() {
        try {
            LOGGER.info("Stopping!");
            try {
                this.narrator.destroy();
            } catch (Throwable th) {
            }
            try {
                if (this.level != null) {
                    this.level.disconnect(ClientLevel.DEFAULT_QUIT_MESSAGE);
                }
                disconnectWithProgressScreen();
            } catch (Throwable th2) {
            }
            if (this.screen != null) {
                this.screen.removed();
            }
            close();
            Util.timeSource = System::nanoTime;
            if (this.delayedCrash == null) {
                System.exit(0);
            }
        } catch (Throwable th3) {
            Util.timeSource = System::nanoTime;
            if (this.delayedCrash == null) {
                System.exit(0);
            }
            throw th3;
        }
    }

    @Override // net.minecraft.util.thread.TaskScheduler, java.lang.AutoCloseable
    public void close() {
        if (this.currentFrameProfile != null) {
            this.currentFrameProfile.cancel();
        }
        try {
            try {
                this.telemetryManager.close();
                this.regionalCompliancies.close();
                this.atlasManager.close();
                this.fontManager.close();
                this.gameRenderer.close();
                this.shaderManager.close();
                this.levelRenderer.close();
                this.soundManager.destroy();
                this.mapTextureManager.close();
                this.textureManager.close();
                this.resourceManager.close();
                if (this.tracyFrameCapture != null) {
                    this.tracyFrameCapture.close();
                }
                FreeTypeUtil.destroy();
                Util.shutdownExecutors();
                RenderSystem.getSamplerCache().close();
                RenderSystem.getDevice().close();
                this.virtualScreen.close();
                this.window.close();
            } finally {
            }
        } catch (Throwable th) {
            this.virtualScreen.close();
            this.window.close();
            throw th;
        }
    }

    private void runTick(boolean $$0) {
        boolean $$10;
        this.window.setErrorSection("Pre render");
        if (this.window.shouldClose()) {
            stop();
        }
        if (this.pendingReload != null && !(this.overlay instanceof LoadingOverlay)) {
            CompletableFuture<Void> $$1 = this.pendingReload;
            this.pendingReload = null;
            reloadResourcePacks().thenRun(() -> {
                $$1.complete(null);
            });
        }
        int $$2 = this.deltaTracker.advanceTime(Util.getMillis(), $$0);
        ProfilerFiller $$3 = Profiler.get();
        if ($$0) {
            Gizmos.TemporaryCollection $$6 = collectPerTickGizmos();
            try {
                $$3.push("scheduledPacketProcessing");
                this.packetProcessor.processQueuedPackets();
                $$3.popPush("scheduledExecutables");
                runAllTasks();
                $$3.pop();
                if ($$6 != null) {
                    $$6.close();
                }
                $$3.push("tick");
                if ($$2 > 0 && isLevelRunningNormally()) {
                    $$3.push("textures");
                    this.textureManager.tick();
                    $$3.pop();
                }
                for (int $$5 = 0; $$5 < Math.min(10, $$2); $$5++) {
                    $$3.incrementCounter("clientTick");
                    $$6 = collectPerTickGizmos();
                    try {
                        tick();
                        if ($$6 != null) {
                            $$6.close();
                        }
                    } finally {
                    }
                }
                if ($$2 > 0 && (this.level == null || this.level.tickRateManager().runsNormally())) {
                    this.drainedLatestTickGizmos = this.perTickGizmos.drainGizmos();
                }
                $$3.pop();
            } finally {
            }
        }
        this.window.setErrorSection("Render");
        Gizmos.TemporaryCollection $$7 = this.levelRenderer.collectPerFrameGizmos();
        try {
            $$3.push("gpuAsync");
            RenderSystem.executePendingTasks();
            $$3.popPush("sound");
            this.soundManager.updateSource(this.gameRenderer.getMainCamera());
            $$3.popPush("toasts");
            this.toastManager.update();
            $$3.popPush("mouse");
            this.mouseHandler.handleAccumulatedMovement();
            $$3.popPush("render");
            long $$8 = Util.getNanos();
            if (this.debugEntries.isCurrentlyEnabled(DebugScreenEntries.GPU_UTILIZATION) || this.metricsRecorder.isRecording()) {
                $$10 = (this.currentFrameProfile == null || this.currentFrameProfile.isDone()) && !TimerQuery.getInstance().isRecording();
                if ($$10) {
                    TimerQuery.getInstance().beginProfile();
                }
            } else {
                $$10 = false;
                this.gpuUtilization = Density.SURFACE;
            }
            RenderTarget $$11 = getMainRenderTarget();
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures($$11.getColorTexture(), 0, $$11.getDepthTexture(), 1.0d);
            $$3.push("gameRenderer");
            if (!this.noRender) {
                this.gameRenderer.render(this.deltaTracker, $$0);
            }
            $$3.popPush("blit");
            if (!this.window.isMinimized()) {
                $$11.blitToScreen();
            }
            this.frameTimeNs = Util.getNanos() - $$8;
            if ($$10) {
                this.currentFrameProfile = TimerQuery.getInstance().endProfile();
            }
            $$3.popPush("updateDisplay");
            if (this.tracyFrameCapture != null) {
                this.tracyFrameCapture.upload();
                this.tracyFrameCapture.capture($$11);
            }
            this.window.updateDisplay(this.tracyFrameCapture);
            int $$12 = this.framerateLimitTracker.getFramerateLimit();
            if ($$12 < 260) {
                RenderSystem.limitDisplayFPS($$12);
            }
            $$3.pop();
            $$3.popPush("yield");
            Thread.yield();
            $$3.pop();
            if ($$7 != null) {
                $$7.close();
            }
            this.window.setErrorSection("Post render");
            this.frames++;
            boolean $$14 = this.pause;
            this.pause = hasSingleplayerServer() && ((this.screen != null && this.screen.isPauseScreen()) || (this.overlay != null && this.overlay.isPauseScreen())) && !this.singleplayerServer.isPublished();
            if (!$$14 && this.pause) {
                this.soundManager.pauseAllExcept(SoundSource.MUSIC, SoundSource.UI);
            }
            this.deltaTracker.updatePauseState(this.pause);
            this.deltaTracker.updateFrozenState(!isLevelRunningNormally());
            long $$15 = Util.getNanos();
            long $$16 = $$15 - this.lastNanoTime;
            if ($$10) {
                this.savedCpuDuration = $$16;
            }
            getDebugOverlay().logFrameDuration($$16);
            this.lastNanoTime = $$15;
            $$3.push("fpsUpdate");
            if (this.currentFrameProfile != null && this.currentFrameProfile.isDone()) {
                this.gpuUtilization = (this.currentFrameProfile.get() * 100.0d) / this.savedCpuDuration;
            }
            while (Util.getMillis() >= this.lastTime + 1000) {
                fps = this.frames;
                this.lastTime += 1000;
                this.frames = 0;
            }
            $$3.pop();
        } catch (Throwable th) {
            if ($$7 != null) {
                try {
                    $$7.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private ProfilerFiller constructProfiler(boolean $$0, SingleTickProfiler $$1) {
        ProfilerFiller $$3;
        if (!$$0) {
            this.fpsPieProfiler.disable();
            if (!this.metricsRecorder.isRecording() && $$1 == null) {
                return InactiveProfiler.INSTANCE;
            }
        }
        if ($$0) {
            if (!this.fpsPieProfiler.isEnabled()) {
                this.fpsPieRenderTicks = 0;
                this.fpsPieProfiler.enable();
            }
            this.fpsPieRenderTicks++;
            $$3 = this.fpsPieProfiler.getFiller();
        } else {
            $$3 = InactiveProfiler.INSTANCE;
        }
        if (this.metricsRecorder.isRecording()) {
            $$3 = ProfilerFiller.combine($$3, this.metricsRecorder.getProfiler());
        }
        return SingleTickProfiler.decorateFiller($$3, $$1);
    }

    private void finishProfilers(boolean $$0, SingleTickProfiler $$1) {
        if ($$1 != null) {
            $$1.endTick();
        }
        ProfilerPieChart $$2 = getDebugOverlay().getProfilerPieChart();
        if ($$0) {
            $$2.setPieChartResults(this.fpsPieProfiler.getResults());
        } else {
            $$2.setPieChartResults(null);
        }
    }

    @Override // com.mojang.blaze3d.platform.WindowEventHandler
    public void resizeDisplay() {
        int $$0 = this.window.calculateScale(this.options.guiScale().get().intValue(), isEnforceUnicode());
        this.window.setGuiScale($$0);
        if (this.screen != null) {
            this.screen.resize(this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
        }
        RenderTarget $$1 = getMainRenderTarget();
        $$1.resize(this.window.getWidth(), this.window.getHeight());
        this.gameRenderer.resize(this.window.getWidth(), this.window.getHeight());
        this.mouseHandler.setIgnoreFirstMove();
    }

    @Override // com.mojang.blaze3d.platform.WindowEventHandler
    public void cursorEntered() {
        this.mouseHandler.cursorEntered();
    }

    public int getFps() {
        return fps;
    }

    public long getFrameTimeNs() {
        return this.frameTimeNs;
    }

    private void emergencySave() {
        MemoryReserve.release();
        try {
            if (this.isLocalServer && this.singleplayerServer != null) {
                this.singleplayerServer.halt(true);
            }
            disconnectWithSavingScreen();
        } catch (Throwable th) {
        }
        System.gc();
    }

    public boolean debugClientMetricsStart(Consumer<Component> $$0) {
        Consumer<Path> $$8;
        if (this.metricsRecorder.isRecording()) {
            debugClientMetricsStop();
            return false;
        }
        Consumer<ProfileResults> $$1 = $$12 -> {
            if ($$12 == EmptyProfileResults.EMPTY) {
                return;
            }
            int $$2 = $$12.getTickDuration();
            double $$3 = $$12.getNanoDuration() / TimeUtil.NANOSECONDS_PER_SECOND;
            execute(() -> {
                $$0.accept(Component.translatable("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", Double.valueOf($$3)), Integer.valueOf($$2), String.format(Locale.ROOT, "%.2f", Double.valueOf(((double) $$2) / $$3))));
            });
        };
        Consumer<Path> $$2 = $$13 -> {
            Component $$22 = Component.literal($$13.toString()).withStyle(ChatFormatting.UNDERLINE).withStyle($$13 -> {
                return $$13.withClickEvent(new ClickEvent.OpenFile($$13.getParent()));
            });
            execute(() -> {
                $$0.accept(Component.translatable("debug.profiling.stop", $$22));
            });
        };
        SystemReport $$3 = fillSystemReport(new SystemReport(), this, this.languageManager, this.launchedVersion, this.options);
        Consumer<List<Path>> $$4 = $$22 -> {
            Path $$32 = archiveProfilingReport($$3, $$22);
            $$2.accept($$32);
        };
        if (this.singleplayerServer == null) {
            $$8 = $$14 -> {
                $$4.accept(ImmutableList.of($$14));
            };
        } else {
            this.singleplayerServer.fillSystemReport($$3);
            CompletableFuture<Path> $$6 = new CompletableFuture<>();
            CompletableFuture<Path> $$7 = new CompletableFuture<>();
            CompletableFuture.allOf($$6, $$7).thenRunAsync(() -> {
                $$4.accept(ImmutableList.of((Path) $$6.join(), (Path) $$7.join()));
            }, (Executor) Util.ioPool());
            IntegratedServer integratedServer = this.singleplayerServer;
            Consumer<ProfileResults> consumer = $$02 -> {
            };
            Objects.requireNonNull($$7);
            integratedServer.startRecordingMetrics(consumer, (v1) -> {
                r2.complete(v1);
            });
            Objects.requireNonNull($$6);
            $$8 = (v1) -> {
                r0.complete(v1);
            };
        }
        this.metricsRecorder = ActiveMetricsRecorder.createStarted(new ClientMetricsSamplersProvider(Util.timeSource, this.levelRenderer), Util.timeSource, Util.ioPool(), new MetricsPersister("client"), $$15 -> {
            this.metricsRecorder = InactiveMetricsRecorder.INSTANCE;
            $$1.accept($$15);
        }, $$8);
        return true;
    }

    private void debugClientMetricsStop() {
        this.metricsRecorder.end();
        if (this.singleplayerServer != null) {
            this.singleplayerServer.finishRecordingMetrics();
        }
    }

    private void debugClientMetricsCancel() {
        this.metricsRecorder.cancel();
        if (this.singleplayerServer != null) {
            this.singleplayerServer.cancelRecordingMetrics();
        }
    }

    private Path archiveProfilingReport(SystemReport $$0, List<Path> $$1) {
        String $$4;
        if (isLocalServer()) {
            $$4 = getSingleplayerServer().getWorldData().getLevelName();
        } else {
            ServerData $$3 = getCurrentServer();
            $$4 = $$3 != null ? $$3.name : "unknown";
        }
        try {
            String $$5 = String.format(Locale.ROOT, "%s-%s-%s", Util.getFilenameFormattedDateTime(), $$4, SharedConstants.getCurrentVersion().id());
            String $$6 = FileUtil.findAvailableName(MetricsPersister.PROFILING_RESULTS_DIR, $$5, ".zip");
            Path $$9 = MetricsPersister.PROFILING_RESULTS_DIR.resolve($$6);
            try {
                FileZipper $$10 = new FileZipper($$9);
                try {
                    $$10.add(Paths.get("system.txt", new String[0]), $$0.toLineSeparatedString());
                    $$10.add(Paths.get("client", new String[0]).resolve(this.options.getFile().getName()), this.options.dumpOptionsForReport());
                    Objects.requireNonNull($$10);
                    $$1.forEach($$10::add);
                    $$10.close();
                    return $$9;
                } finally {
                }
            } finally {
                for (Path $$11 : $$1) {
                    try {
                        FileUtils.forceDelete($$11.toFile());
                    } catch (IOException $$12) {
                        LOGGER.warn("Failed to delete temporary profiling result {}", $$11, $$12);
                    }
                }
            }
        } catch (IOException $$8) {
            throw new UncheckedIOException($$8);
        }
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void pauseGame(boolean $$0) {
        if (this.screen != null) {
            return;
        }
        boolean $$1 = hasSingleplayerServer() && !this.singleplayerServer.isPublished();
        if ($$1) {
            setScreen(new PauseScreen(!$$0));
        } else {
            setScreen(new PauseScreen(true));
        }
    }

    private void continueAttack(boolean $$0) {
        if (!$$0) {
            this.missTime = 0;
        }
        if (this.missTime > 0 || this.player.isUsingItem()) {
            return;
        }
        ItemStack $$1 = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        if ($$1.has(DataComponents.PIERCING_WEAPON)) {
            return;
        }
        if ($$0 && this.hitResult != null && this.hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult $$2 = (BlockHitResult) this.hitResult;
            BlockPos $$3 = $$2.getBlockPos();
            if (!this.level.getBlockState($$3).isAir()) {
                Direction $$4 = $$2.getDirection();
                if (this.gameMode.continueDestroyBlock($$3, $$4)) {
                    this.level.addBreakingBlockEffect($$3, $$4);
                    this.player.swing(InteractionHand.MAIN_HAND);
                    return;
                }
                return;
            }
            return;
        }
        this.gameMode.stopDestroyBlock();
    }

    private boolean startAttack() {
        if (this.missTime > 0) {
            return false;
        }
        if (this.hitResult == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.gameMode.hasMissTime()) {
                this.missTime = 10;
                return false;
            }
            return false;
        }
        if (this.player.isHandsBusy()) {
            return false;
        }
        ItemStack $$0 = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!$$0.isItemEnabled(this.level.enabledFeatures()) || this.player.cannotAttackWithItem($$0, 0)) {
            return false;
        }
        boolean $$1 = false;
        PiercingWeapon $$2 = (PiercingWeapon) $$0.get(DataComponents.PIERCING_WEAPON);
        if ($$2 != null && !this.gameMode.isSpectator()) {
            this.gameMode.piercingAttack($$2);
            this.player.swing(InteractionHand.MAIN_HAND);
            return true;
        }
        switch (this.hitResult.getType()) {
            case ENTITY:
                AttackRange $$3 = (AttackRange) $$0.get(DataComponents.ATTACK_RANGE);
                if ($$3 == null || $$3.isInRange(this.player, this.hitResult.getLocation())) {
                    this.gameMode.attack(this.player, ((EntityHitResult) this.hitResult).getEntity());
                }
                break;
            case BLOCK:
                BlockHitResult $$4 = (BlockHitResult) this.hitResult;
                BlockPos $$5 = $$4.getBlockPos();
                if (!this.level.getBlockState($$5).isAir()) {
                    this.gameMode.startDestroyBlock($$5, $$4.getDirection());
                    if (this.level.getBlockState($$5).isAir()) {
                        $$1 = true;
                    }
                    break;
                }
            case MISS:
                if (this.gameMode.hasMissTime()) {
                    this.missTime = 10;
                }
                this.player.resetAttackStrengthTicker();
                break;
        }
        if (!this.player.isSpectator()) {
            this.player.swing(InteractionHand.MAIN_HAND);
        }
        return $$1;
    }

    private void startUseItem() {
        if (this.gameMode.isDestroying()) {
            return;
        }
        this.rightClickDelay = 4;
        if (this.player.isHandsBusy()) {
            return;
        }
        if (this.hitResult == null) {
            LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
        }
        for (InteractionHand $$0 : InteractionHand.values()) {
            ItemStack $$1 = this.player.getItemInHand($$0);
            if (!$$1.isItemEnabled(this.level.enabledFeatures())) {
                return;
            }
            if (this.hitResult != null) {
                switch (this.hitResult.getType()) {
                    case ENTITY:
                        EntityHitResult $$2 = (EntityHitResult) this.hitResult;
                        Entity $$3 = $$2.getEntity();
                        if (!this.level.getWorldBorder().isWithinBounds($$3.blockPosition())) {
                            return;
                        }
                        if (this.player.isWithinEntityInteractionRange($$3, Density.SURFACE)) {
                            InteractionResult $$4 = this.gameMode.interactAt(this.player, $$3, $$2, $$0);
                            if (!$$4.consumesAction()) {
                                $$4 = this.gameMode.interact(this.player, $$3, $$0);
                            }
                            if ($$4 instanceof InteractionResult.Success) {
                                InteractionResult.Success $$5 = (InteractionResult.Success) $$4;
                                if ($$5.swingSource() == InteractionResult.SwingSource.CLIENT) {
                                    this.player.swing($$0);
                                    return;
                                }
                                return;
                            }
                        }
                        break;
                    case BLOCK:
                        BlockHitResult $$6 = (BlockHitResult) this.hitResult;
                        int $$7 = $$1.getCount();
                        InteractionResult $$8 = this.gameMode.useItemOn(this.player, $$0, $$6);
                        if ($$8 instanceof InteractionResult.Success) {
                            InteractionResult.Success $$9 = (InteractionResult.Success) $$8;
                            if ($$9.swingSource() == InteractionResult.SwingSource.CLIENT) {
                                this.player.swing($$0);
                                if ($$1.isEmpty()) {
                                    return;
                                }
                                if ($$1.getCount() != $$7 || this.player.hasInfiniteMaterials()) {
                                    this.gameRenderer.itemInHandRenderer.itemUsed($$0);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if ($$8 instanceof InteractionResult.Fail) {
                            return;
                        }
                        break;
                }
            }
            if (!$$1.isEmpty()) {
                InteractionResult $$10 = this.gameMode.useItem(this.player, $$0);
                if ($$10 instanceof InteractionResult.Success) {
                    InteractionResult.Success $$11 = (InteractionResult.Success) $$10;
                    if ($$11.swingSource() == InteractionResult.SwingSource.CLIENT) {
                        this.player.swing($$0);
                    }
                    this.gameRenderer.itemInHandRenderer.itemUsed($$0);
                    return;
                }
            }
        }
    }

    public MusicManager getMusicManager() {
        return this.musicManager;
    }

    public void tick() {
        this.clientTickCount++;
        if (this.level != null && !this.pause) {
            this.level.tickRateManager().tick();
        }
        if (this.rightClickDelay > 0) {
            this.rightClickDelay--;
        }
        ProfilerFiller $$0 = Profiler.get();
        $$0.push("gui");
        this.chatListener.tick();
        this.gui.tick(this.pause);
        $$0.pop();
        this.gameRenderer.pick(1.0f);
        this.tutorial.onLookAt(this.level, this.hitResult);
        $$0.push("gameMode");
        if (!this.pause && this.level != null) {
            this.gameMode.tick();
        }
        $$0.popPush("screen");
        if (this.screen == null && this.player != null) {
            if (this.player.isDeadOrDying() && !(this.screen instanceof DeathScreen)) {
                setScreen(null);
            } else if (this.player.isSleeping() && this.level != null) {
                this.gui.getChat().openScreen(ChatComponent.ChatMethod.MESSAGE, InBedChatScreen::new);
            }
        } else {
            Screen screen = this.screen;
            if (screen instanceof InBedChatScreen) {
                InBedChatScreen $$1 = (InBedChatScreen) screen;
                if (!this.player.isSleeping()) {
                    $$1.onPlayerWokeUp();
                }
            }
        }
        if (this.screen != null) {
            this.missTime = 10000;
        }
        if (this.screen != null) {
            try {
                this.screen.tick();
            } catch (Throwable $$2) {
                CrashReport $$3 = CrashReport.forThrowable($$2, "Ticking screen");
                this.screen.fillCrashDetails($$3);
                throw new ReportedException($$3);
            }
        }
        if (this.overlay != null) {
            this.overlay.tick();
        }
        if (!getDebugOverlay().showDebugScreen()) {
            this.gui.clearCache();
        }
        if (this.overlay == null && this.screen == null) {
            $$0.popPush("Keybindings");
            handleKeybinds();
            if (this.missTime > 0) {
                this.missTime--;
            }
        }
        if (this.level != null) {
            if (!this.pause) {
                $$0.popPush("gameRenderer");
                this.gameRenderer.tick();
                $$0.popPush(StructureTemplate.ENTITIES_TAG);
                this.level.tickEntities();
                $$0.popPush("blockEntities");
                this.level.tickBlockEntities();
            }
        } else if (this.gameRenderer.currentPostEffect() != null) {
            this.gameRenderer.clearPostEffect();
        }
        this.musicManager.tick();
        this.soundManager.tick(this.pause);
        if (this.level != null) {
            if (!this.pause) {
                $$0.popPush("level");
                if (!this.options.joinedFirstServer && isMultiplayerServer()) {
                    Component $$4 = Component.translatable("tutorial.socialInteractions.title");
                    Component $$5 = Component.translatable("tutorial.socialInteractions.description", Tutorial.key("socialInteractions"));
                    this.socialInteractionsToast = new TutorialToast(this.font, TutorialToast.Icons.SOCIAL_INTERACTIONS, $$4, $$5, true, 8000);
                    this.toastManager.addToast(this.socialInteractionsToast);
                    this.options.joinedFirstServer = true;
                    this.options.save();
                }
                this.tutorial.tick();
                try {
                    this.level.tick(() -> {
                        return true;
                    });
                } catch (Throwable $$6) {
                    CrashReport $$7 = CrashReport.forThrowable($$6, "Exception in world tick");
                    if (this.level == null) {
                        CrashReportCategory $$8 = $$7.addCategory("Affected level");
                        $$8.setDetail("Problem", "Level is null!");
                    } else {
                        this.level.fillReportDetails($$7);
                    }
                    throw new ReportedException($$7);
                }
            }
            $$0.popPush("animateTick");
            if (!this.pause && isLevelRunningNormally()) {
                this.level.animateTick(this.player.getBlockX(), this.player.getBlockY(), this.player.getBlockZ());
            }
            $$0.popPush("particles");
            if (!this.pause && isLevelRunningNormally()) {
                this.particleEngine.tick();
            }
            ClientPacketListener $$9 = getConnection();
            if ($$9 != null && !this.pause) {
                $$9.send(ServerboundClientTickEndPacket.INSTANCE);
            }
        } else if (this.pendingConnection != null) {
            $$0.popPush("pendingConnection");
            this.pendingConnection.tick();
        }
        $$0.popPush("keyboard");
        this.keyboardHandler.tick();
        $$0.pop();
    }

    private boolean isLevelRunningNormally() {
        return this.level == null || this.level.tickRateManager().runsNormally();
    }

    private boolean isMultiplayerServer() {
        return !this.isLocalServer || (this.singleplayerServer != null && this.singleplayerServer.isPublished());
    }

    private void handleKeybinds() {
        while (this.options.keyTogglePerspective.consumeClick()) {
            CameraType $$0 = this.options.getCameraType();
            this.options.setCameraType(this.options.getCameraType().cycle());
            if ($$0.isFirstPerson() != this.options.getCameraType().isFirstPerson()) {
                this.gameRenderer.checkEntityPostEffect(this.options.getCameraType().isFirstPerson() ? getCameraEntity() : null);
            }
            this.levelRenderer.needsUpdate();
        }
        while (this.options.keySmoothCamera.consumeClick()) {
            this.options.smoothCamera = !this.options.smoothCamera;
        }
        for (int $$1 = 0; $$1 < 9; $$1++) {
            boolean $$2 = this.options.keySaveHotbarActivator.isDown();
            boolean $$3 = this.options.keyLoadHotbarActivator.isDown();
            if (this.options.keyHotbarSlots[$$1].consumeClick()) {
                if (this.player.isSpectator()) {
                    this.gui.getSpectatorGui().onHotbarSelected($$1);
                } else if (this.player.hasInfiniteMaterials() && this.screen == null && ($$3 || $$2)) {
                    CreativeModeInventoryScreen.handleHotbarLoadOrSave(this, $$1, $$3, $$2);
                } else {
                    this.player.getInventory().setSelectedSlot($$1);
                }
            }
        }
        while (this.options.keySocialInteractions.consumeClick()) {
            if (!isMultiplayerServer() && !SharedConstants.DEBUG_SOCIAL_INTERACTIONS) {
                this.player.displayClientMessage(SOCIAL_INTERACTIONS_NOT_AVAILABLE, true);
                this.narrator.saySystemNow(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
            } else {
                if (this.socialInteractionsToast != null) {
                    this.socialInteractionsToast.hide();
                    this.socialInteractionsToast = null;
                }
                setScreen(new SocialInteractionsScreen());
            }
        }
        while (this.options.keyInventory.consumeClick()) {
            if (this.gameMode.isServerControlledInventory()) {
                this.player.sendOpenInventory();
            } else {
                this.tutorial.onOpenInventory();
                setScreen(new InventoryScreen(this.player));
            }
        }
        while (this.options.keyAdvancements.consumeClick()) {
            setScreen(new AdvancementsScreen(this.player.connection.getAdvancements()));
        }
        while (this.options.keyQuickActions.consumeClick()) {
            getQuickActionsDialog().ifPresent($$02 -> {
                this.player.connection.showDialog($$02, this.screen);
            });
        }
        while (this.options.keySwapOffhand.consumeClick()) {
            if (!this.player.isSpectator()) {
                getConnection().send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
            }
        }
        while (this.options.keyDrop.consumeClick()) {
            if (!this.player.isSpectator() && this.player.drop(hasControlDown())) {
                this.player.swing(InteractionHand.MAIN_HAND);
            }
        }
        while (this.options.keyChat.consumeClick()) {
            openChatScreen(ChatComponent.ChatMethod.MESSAGE);
        }
        if (this.screen == null && this.overlay == null && this.options.keyCommand.consumeClick()) {
            openChatScreen(ChatComponent.ChatMethod.COMMAND);
        }
        boolean $$4 = false;
        if (this.player.isUsingItem()) {
            if (!this.options.keyUse.isDown()) {
                this.gameMode.releaseUsingItem(this.player);
            }
            while (this.options.keyAttack.consumeClick()) {
            }
            while (this.options.keyUse.consumeClick()) {
            }
            while (this.options.keyPickItem.consumeClick()) {
            }
        } else {
            while (this.options.keyAttack.consumeClick()) {
                $$4 |= startAttack();
            }
            while (this.options.keyUse.consumeClick()) {
                startUseItem();
            }
            while (this.options.keyPickItem.consumeClick()) {
                pickBlock();
            }
            if (this.player.isSpectator()) {
                while (this.options.keySpectatorHotbar.consumeClick()) {
                    this.gui.getSpectatorGui().onHotbarActionKeyPressed();
                }
            }
        }
        if (this.options.keyUse.isDown() && this.rightClickDelay == 0 && !this.player.isUsingItem()) {
            startUseItem();
        }
        continueAttack(this.screen == null && !$$4 && this.options.keyAttack.isDown() && this.mouseHandler.isMouseGrabbed());
    }

    private Optional<Holder<Dialog>> getQuickActionsDialog() {
        Registry<Dialog> $$0 = this.player.connection.registryAccess().lookupOrThrow((ResourceKey) Registries.DIALOG);
        return $$0.get(DialogTags.QUICK_ACTIONS).flatMap($$1 -> {
            if ($$1.size() == 0) {
                return Optional.empty();
            }
            if ($$1.size() == 1) {
                return Optional.of($$1.get(0));
            }
            return $$0.get(Dialogs.QUICK_ACTIONS);
        });
    }

    public ClientTelemetryManager getTelemetryManager() {
        return this.telemetryManager;
    }

    public double getGpuUtilization() {
        return this.gpuUtilization;
    }

    public ProfileKeyPairManager getProfileKeyPairManager() {
        return this.profileKeyPairManager;
    }

    public WorldOpenFlows createWorldOpenFlows() {
        return new WorldOpenFlows(this, this.levelSource);
    }

    public void doWorldLoad(LevelStorageSource.LevelStorageAccess $$0, PackRepository $$1, WorldStem $$2, boolean $$3) {
        disconnectWithProgressScreen();
        Instant $$4 = Instant.now();
        LevelLoadTracker $$5 = new LevelLoadTracker($$3 ? 500L : 0L);
        LevelLoadingScreen $$6 = new LevelLoadingScreen($$5, LevelLoadingScreen.Reason.OTHER);
        setScreen($$6);
        int $$7 = Math.max(5, 3) + ChunkLevel.RADIUS_AROUND_FULL_CHUNK + 1;
        try {
            $$0.saveDataTag($$2.registries().compositeAccess(), $$2.worldData());
            LevelLoadListener $$8 = LevelLoadListener.compose($$5, LoggingLevelLoadListener.forSingleplayer());
            this.singleplayerServer = (IntegratedServer) MinecraftServer.spin($$42 -> {
                return new IntegratedServer($$42, this, $$0, $$1, $$2, this.services, $$8);
            });
            $$5.setServerChunkStatusView(this.singleplayerServer.createChunkLoadStatusView($$7));
            this.isLocalServer = true;
            updateReportEnvironment(ReportEnvironment.local());
            this.quickPlayLog.setWorldData(QuickPlayLog.Type.SINGLEPLAYER, $$0.getLevelId(), $$2.worldData().getLevelName());
            ProfilerFiller $$12 = Profiler.get();
            $$12.push("waitForServer");
            long $$13 = TimeUnit.SECONDS.toNanos(1L) / 60;
            while (true) {
                if (!this.singleplayerServer.isReady() || this.overlay != null) {
                    long $$14 = Util.getNanos() + $$13;
                    $$6.tick();
                    if (this.overlay != null) {
                        this.overlay.tick();
                    }
                    runTick(false);
                    runAllTasks();
                    managedBlock(() -> {
                        return Util.getNanos() > $$14;
                    });
                    handleDelayedCrash();
                } else {
                    $$12.pop();
                    Duration $$15 = Duration.between($$4, Instant.now());
                    SocketAddress $$16 = this.singleplayerServer.getConnection().startMemoryChannel();
                    Connection $$17 = Connection.connectToLocalServer($$16);
                    $$17.initiateServerboundPlayConnection($$16.toString(), 0, new ClientHandshakePacketListenerImpl($$17, this, null, null, $$3, $$15, $$02 -> {
                    }, $$5, null));
                    $$17.send(new ServerboundHelloPacket(getUser().getName(), getUser().getProfileId()));
                    this.pendingConnection = $$17;
                    return;
                }
            }
        } catch (Throwable $$9) {
            CrashReport $$10 = CrashReport.forThrowable($$9, "Starting integrated server");
            CrashReportCategory $$11 = $$10.addCategory("Starting integrated server");
            $$11.setDetail("Level ID", $$0.getLevelId());
            $$11.setDetail("Level Name", () -> {
                return $$2.worldData().getLevelName();
            });
            throw new ReportedException($$10);
        }
    }

    public void setLevel(ClientLevel $$0) {
        this.level = $$0;
        updateLevelInEngines($$0);
    }

    public void disconnectFromWorld(Component $$0) {
        boolean $$1 = isLocalServer();
        ServerData $$2 = getCurrentServer();
        if (this.level != null) {
            this.level.disconnect($$0);
        }
        if ($$1) {
            disconnectWithSavingScreen();
        } else {
            disconnectWithProgressScreen();
        }
        TitleScreen $$3 = new TitleScreen();
        if ($$1) {
            setScreen($$3);
        } else if ($$2 != null && $$2.isRealm()) {
            setScreen(new RealmsMainScreen($$3));
        } else {
            setScreen(new JoinMultiplayerScreen($$3));
        }
    }

    public void disconnectWithSavingScreen() {
        disconnect(new GenericMessageScreen(SAVING_LEVEL), false);
    }

    public void disconnectWithProgressScreen() {
        disconnectWithProgressScreen(true);
    }

    public void disconnectWithProgressScreen(boolean $$0) {
        disconnect(new ProgressScreen(true), false, $$0);
    }

    public void disconnect(Screen $$0, boolean $$1) {
        disconnect($$0, $$1, true);
    }

    public void disconnect(Screen $$0, boolean $$1, boolean $$2) {
        ClientPacketListener $$3 = getConnection();
        if ($$3 != null) {
            dropAllTasks();
            $$3.close();
            if (!$$1) {
                clearDownloadedResourcePacks();
            }
        }
        this.playerSocialManager.stopOnlineMode();
        if (this.metricsRecorder.isRecording()) {
            debugClientMetricsCancel();
        }
        IntegratedServer $$4 = this.singleplayerServer;
        this.singleplayerServer = null;
        this.gameRenderer.resetData();
        this.gameMode = null;
        this.narrator.clear();
        this.clientLevelTeardownInProgress = true;
        try {
            if (this.level != null) {
                this.gui.onDisconnected();
            }
            if ($$4 != null) {
                setScreen(new GenericMessageScreen(SAVING_LEVEL));
                ProfilerFiller $$5 = Profiler.get();
                $$5.push("waitForServer");
                while (!$$4.isShutdown()) {
                    runTick(false);
                }
                $$5.pop();
            }
            setScreenAndShow($$0);
            this.isLocalServer = false;
            this.level = null;
            updateLevelInEngines(null, $$2);
            this.player = null;
            this.clientLevelTeardownInProgress = false;
        } catch (Throwable th) {
            this.clientLevelTeardownInProgress = false;
            throw th;
        }
    }

    public void clearDownloadedResourcePacks() {
        this.downloadedPackSource.cleanupAfterDisconnect();
        runAllTasks();
    }

    public void clearClientLevel(Screen $$0) {
        ClientPacketListener $$1 = getConnection();
        if ($$1 != null) {
            $$1.clearLevel();
        }
        if (this.metricsRecorder.isRecording()) {
            debugClientMetricsCancel();
        }
        this.gameRenderer.resetData();
        this.gameMode = null;
        this.narrator.clear();
        this.clientLevelTeardownInProgress = true;
        try {
            setScreenAndShow($$0);
            this.gui.onDisconnected();
            this.level = null;
            updateLevelInEngines(null);
            this.player = null;
        } finally {
            this.clientLevelTeardownInProgress = false;
        }
    }

    public void setScreenAndShow(Screen $$0) {
        Zone $$1 = Profiler.get().zone("forcedTick");
        try {
            setScreen($$0);
            runTick(false);
            if ($$1 != null) {
                $$1.close();
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

    private void updateLevelInEngines(ClientLevel $$0) {
        updateLevelInEngines($$0, true);
    }

    private void updateLevelInEngines(ClientLevel $$0, boolean $$1) {
        if ($$1) {
            this.soundManager.stop();
        }
        setCameraEntity(null);
        this.pendingConnection = null;
        this.levelRenderer.setLevel($$0);
        this.particleEngine.setLevel($$0);
        this.gameRenderer.setLevel($$0);
        updateTitle();
    }

    private UserApiService.UserProperties userProperties() {
        return this.userPropertiesFuture.join();
    }

    public boolean telemetryOptInExtra() {
        return extraTelemetryAvailable() && this.options.telemetryOptInExtra().get().booleanValue();
    }

    public boolean extraTelemetryAvailable() {
        return allowsTelemetry() && userProperties().flag(UserApiService.UserFlag.OPTIONAL_TELEMETRY_AVAILABLE);
    }

    public boolean allowsTelemetry() {
        if (SharedConstants.IS_RUNNING_IN_IDE && !SharedConstants.DEBUG_FORCE_TELEMETRY) {
            return false;
        }
        return userProperties().flag(UserApiService.UserFlag.TELEMETRY_ENABLED);
    }

    public boolean allowsMultiplayer() {
        return this.allowsMultiplayer && userProperties().flag(UserApiService.UserFlag.SERVERS_ALLOWED) && multiplayerBan() == null && !isNameBanned();
    }

    public boolean allowsRealms() {
        return userProperties().flag(UserApiService.UserFlag.REALMS_ALLOWED) && multiplayerBan() == null;
    }

    public BanDetails multiplayerBan() {
        return (BanDetails) userProperties().bannedScopes().get("MULTIPLAYER");
    }

    public boolean isNameBanned() {
        ProfileResult $$0 = this.profileFuture.getNow(null);
        return $$0 != null && $$0.actions().contains(ProfileActionType.FORCED_NAME_CHANGE);
    }

    public boolean isBlocked(UUID $$0) {
        if (getChatStatus().isChatAllowed(false)) {
            return this.playerSocialManager.shouldHideMessageFrom($$0);
        }
        return (this.player == null || !$$0.equals(this.player.getUUID())) && !$$0.equals(Util.NIL_UUID);
    }

    public ChatStatus getChatStatus() {
        if (this.options.chatVisibility().get() == ChatVisiblity.HIDDEN) {
            return ChatStatus.DISABLED_BY_OPTIONS;
        }
        if (!this.allowsChat) {
            return ChatStatus.DISABLED_BY_LAUNCHER;
        }
        if (!userProperties().flag(UserApiService.UserFlag.CHAT_ALLOWED)) {
            return ChatStatus.DISABLED_BY_PROFILE;
        }
        return ChatStatus.ENABLED;
    }

    public final boolean isDemo() {
        return this.demo;
    }

    public final boolean canSwitchGameMode() {
        return (this.player == null || this.gameMode == null) ? false : true;
    }

    public ClientPacketListener getConnection() {
        if (this.player == null) {
            return null;
        }
        return this.player.connection;
    }

    public static boolean renderNames() {
        return !instance.options.hideGui;
    }

    public static boolean useShaderTransparency() {
        return !instance.gameRenderer.isPanoramicMode() && instance.options.improvedTransparency().get().booleanValue();
    }

    public static boolean useAmbientOcclusion() {
        return instance.options.ambientOcclusion().get().booleanValue();
    }

    private void pickBlock() {
        if (this.hitResult == null || this.hitResult.getType() == HitResult.Type.MISS) {
            return;
        }
        boolean $$0 = hasControlDown();
        HitResult hitResult = this.hitResult;
        Objects.requireNonNull(hitResult);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), BlockHitResult.class, EntityHitResult.class).dynamicInvoker().invoke(hitResult, 0) /* invoke-custom */) {
            case 0:
                BlockHitResult $$1 = (BlockHitResult) hitResult;
                this.gameMode.handlePickItemFromBlock($$1.getBlockPos(), $$0);
                break;
            case 1:
                EntityHitResult $$2 = (EntityHitResult) hitResult;
                this.gameMode.handlePickItemFromEntity($$2.getEntity(), $$0);
                break;
        }
    }

    public CrashReport fillReport(CrashReport $$0) {
        SystemReport $$1 = $$0.getSystemReport();
        try {
            fillSystemReport($$1, this, this.languageManager, this.launchedVersion, this.options);
            fillUptime($$0.addCategory("Uptime"));
            if (this.level != null) {
                this.level.fillReportDetails($$0);
            }
            if (this.singleplayerServer != null) {
                this.singleplayerServer.fillSystemReport($$1);
            }
            this.reloadStateTracker.fillCrashReport($$0);
        } catch (Throwable $$2) {
            LOGGER.error("Failed to collect details", $$2);
        }
        return $$0;
    }

    public static void fillReport(Minecraft $$0, LanguageManager $$1, String $$2, Options $$3, CrashReport $$4) {
        SystemReport $$5 = $$4.getSystemReport();
        fillSystemReport($$5, $$0, $$1, $$2, $$3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatSeconds(double $$0) {
        return String.format(Locale.ROOT, "%.3fs", Double.valueOf($$0));
    }

    private void fillUptime(CrashReportCategory $$0) {
        $$0.setDetail("JVM uptime", () -> {
            return formatSeconds(ManagementFactory.getRuntimeMXBean().getUptime() / 1000.0d);
        });
        $$0.setDetail("Wall uptime", () -> {
            return formatSeconds((System.currentTimeMillis() - this.clientStartTimeMs) / 1000.0d);
        });
        $$0.setDetail("High-res time", () -> {
            return formatSeconds(Util.getMillis() / 1000.0d);
        });
        $$0.setDetail("Client ticks", () -> {
            return String.format(Locale.ROOT, "%d ticks / %.3fs", Long.valueOf(this.clientTickCount), Double.valueOf(this.clientTickCount / 20.0d));
        });
    }

    private static SystemReport fillSystemReport(SystemReport $$0, Minecraft $$1, LanguageManager $$2, String $$3, Options $$4) {
        String $$6;
        $$0.setDetail("Launched Version", () -> {
            return $$3;
        });
        String $$5 = getLauncherBrand();
        if ($$5 != null) {
            $$0.setDetail("Launcher name", $$5);
        }
        $$0.setDetail("Backend library", RenderSystem::getBackendDescription);
        $$0.setDetail("Backend API", RenderSystem::getApiDescription);
        $$0.setDetail("Window size", () -> {
            return $$1 != null ? $$1.window.getWidth() + "x" + $$1.window.getHeight() : "<not initialized>";
        });
        $$0.setDetail("GFLW Platform", Window::getPlatform);
        $$0.setDetail("Render Extensions", () -> {
            return String.join(ComponentUtils.DEFAULT_SEPARATOR_TEXT, RenderSystem.getDevice().getEnabledExtensions());
        });
        $$0.setDetail("GL debug messages", () -> {
            GpuDevice $$02 = RenderSystem.tryGetDevice();
            if ($$02 == null) {
                return "<no renderer available>";
            }
            if ($$02.isDebuggingEnabled()) {
                return String.join(Crypt.MIME_LINE_SEPARATOR, $$02.getLastDebugMessages());
            }
            return "<debugging unavailable>";
        });
        $$0.setDetail("Is Modded", () -> {
            return checkModStatus().fullDescription();
        });
        $$0.setDetail("Universe", () -> {
            return $$1 != null ? Long.toHexString($$1.canary) : "404";
        });
        $$0.setDetail("Type", "Client (map_client.txt)");
        if ($$4 != null) {
            if ($$1 != null && ($$6 = $$1.getGpuWarnlistManager().getAllWarnings()) != null) {
                $$0.setDetail("GPU Warnings", $$6);
            }
            $$0.setDetail("Transparency", $$4.improvedTransparency().get().booleanValue() ? "shader" : "regular");
            $$0.setDetail("Render Distance", $$4.getEffectiveRenderDistance() + "/" + String.valueOf($$4.renderDistance().get()) + " chunks");
        }
        if ($$1 != null) {
            $$0.setDetail("Resource Packs", () -> {
                return PackRepository.displayPackList($$1.getResourcePackRepository().getSelectedPacks());
            });
        }
        if ($$2 != null) {
            $$0.setDetail("Current Language", () -> {
                return $$2.getSelected();
            });
        }
        $$0.setDetail("Locale", String.valueOf(Locale.getDefault()));
        $$0.setDetail("System encoding", () -> {
            return System.getProperty("sun.jnu.encoding", "<not set>");
        });
        $$0.setDetail("File encoding", () -> {
            return System.getProperty("file.encoding", "<not set>");
        });
        $$0.setDetail("CPU", GLX::_getCpuInfo);
        return $$0;
    }

    public static Minecraft getInstance() {
        return instance;
    }

    public CompletableFuture<Void> delayTextureReload() {
        return submit(this::reloadResourcePacks).thenCompose((Function<? super V, ? extends CompletionStage<U>>) $$0 -> {
            return $$0;
        });
    }

    public void updateReportEnvironment(ReportEnvironment $$0) {
        if (!this.reportingContext.matches($$0)) {
            this.reportingContext = ReportingContext.create($$0, this.userApiService);
        }
    }

    public ServerData getCurrentServer() {
        return (ServerData) Optionull.map(getConnection(), (v0) -> {
            return v0.getServerData();
        });
    }

    public boolean isLocalServer() {
        return this.isLocalServer;
    }

    public boolean hasSingleplayerServer() {
        return this.isLocalServer && this.singleplayerServer != null;
    }

    public IntegratedServer getSingleplayerServer() {
        return this.singleplayerServer;
    }

    public boolean isSingleplayer() {
        IntegratedServer $$0 = getSingleplayerServer();
        return ($$0 == null || $$0.isPublished()) ? false : true;
    }

    public boolean isLocalPlayer(UUID $$0) {
        return $$0.equals(getUser().getProfileId());
    }

    public User getUser() {
        return this.user;
    }

    public GameProfile getGameProfile() {
        ProfileResult $$0 = this.profileFuture.join();
        if ($$0 != null) {
            return $$0.profile();
        }
        return new GameProfile(this.user.getProfileId(), this.user.getName());
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public TextureManager getTextureManager() {
        return this.textureManager;
    }

    public ShaderManager getShaderManager() {
        return this.shaderManager;
    }

    public ResourceManager getResourceManager() {
        return this.resourceManager;
    }

    public PackRepository getResourcePackRepository() {
        return this.resourcePackRepository;
    }

    public VanillaPackResources getVanillaPackResources() {
        return this.vanillaPackResources;
    }

    public DownloadedPackSource getDownloadedPackSource() {
        return this.downloadedPackSource;
    }

    public Path getResourcePackDirectory() {
        return this.resourcePackDirectory;
    }

    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    public boolean isPaused() {
        return this.pause;
    }

    public GpuWarnlistManager getGpuWarnlistManager() {
        return this.gpuWarnlistManager;
    }

    public SoundManager getSoundManager() {
        return this.soundManager;
    }

    public Music getSituationalMusic() {
        Music $$0 = (Music) Optionull.map(this.screen, (v0) -> {
            return v0.getBackgroundMusic();
        });
        if ($$0 != null) {
            return $$0;
        }
        Camera $$1 = this.gameRenderer.getMainCamera();
        if (this.player != null && $$1 != null) {
            Level $$2 = this.player.level();
            if ($$2.dimension() == Level.END && this.gui.getBossOverlay().shouldPlayMusic()) {
                return Musics.END_BOSS;
            }
            BackgroundMusic $$3 = (BackgroundMusic) $$1.attributeProbe().getValue(EnvironmentAttributes.BACKGROUND_MUSIC, 1.0f);
            boolean $$4 = this.player.getAbilities().instabuild && this.player.getAbilities().mayfly;
            boolean $$5 = this.player.isUnderWater();
            return $$3.select($$4, $$5).orElse(null);
        }
        return Musics.MENU;
    }

    public float getMusicVolume() {
        Camera $$0;
        if ((this.screen == null || this.screen.getBackgroundMusic() == null) && ($$0 = this.gameRenderer.getMainCamera()) != null) {
            return ((Float) $$0.attributeProbe().getValue(EnvironmentAttributes.MUSIC_VOLUME, 1.0f)).floatValue();
        }
        return 1.0f;
    }

    public Services services() {
        return this.services;
    }

    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    public Entity getCameraEntity() {
        return this.cameraEntity;
    }

    public void setCameraEntity(Entity $$0) {
        this.cameraEntity = $$0;
        this.gameRenderer.checkEntityPostEffect($$0);
    }

    public boolean shouldEntityAppearGlowing(Entity $$0) {
        return $$0.isCurrentlyGlowing() || (this.player != null && this.player.isSpectator() && this.options.keySpectatorOutlines.isDown() && $$0.getType() == EntityType.PLAYER);
    }

    @Override // net.minecraft.util.thread.BlockableEventLoop
    protected Thread getRunningThread() {
        return this.gameThread;
    }

    @Override // net.minecraft.util.thread.TaskScheduler
    public Runnable wrapRunnable(Runnable $$0) {
        return $$0;
    }

    @Override // net.minecraft.util.thread.BlockableEventLoop
    protected boolean shouldRun(Runnable $$0) {
        return true;
    }

    public BlockRenderDispatcher getBlockRenderer() {
        return this.blockRenderer;
    }

    public EntityRenderDispatcher getEntityRenderDispatcher() {
        return this.entityRenderDispatcher;
    }

    public BlockEntityRenderDispatcher getBlockEntityRenderDispatcher() {
        return this.blockEntityRenderDispatcher;
    }

    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }

    public MapRenderer getMapRenderer() {
        return this.mapRenderer;
    }

    public DataFixer getFixerUpper() {
        return this.fixerUpper;
    }

    public DeltaTracker getDeltaTracker() {
        return this.deltaTracker;
    }

    public BlockColors getBlockColors() {
        return this.blockColors;
    }

    public boolean showOnlyReducedInfo() {
        return (this.player != null && this.player.isReducedDebugInfo()) || this.options.reducedDebugInfo().get().booleanValue();
    }

    public ToastManager getToastManager() {
        return this.toastManager;
    }

    public Tutorial getTutorial() {
        return this.tutorial;
    }

    public boolean isWindowActive() {
        return this.windowActive;
    }

    public HotbarManager getHotbarManager() {
        return this.hotbarManager;
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public AtlasManager getAtlasManager() {
        return this.atlasManager;
    }

    public MapTextureManager getMapTextureManager() {
        return this.mapTextureManager;
    }

    public WaypointStyleManager getWaypointStyles() {
        return this.waypointStyles;
    }

    @Override // com.mojang.blaze3d.platform.WindowEventHandler
    public void setWindowActive(boolean $$0) {
        this.windowActive = $$0;
    }

    public Component grabPanoramixScreenshot(File $$0) {
        int $$4 = this.window.getWidth();
        int $$5 = this.window.getHeight();
        RenderTarget $$6 = getMainRenderTarget();
        float $$7 = this.player.getXRot();
        float $$8 = this.player.getYRot();
        float $$9 = this.player.xRotO;
        float $$10 = this.player.yRotO;
        this.gameRenderer.setRenderBlockOutline(false);
        try {
            try {
                this.gameRenderer.setPanoramicScreenshotParameters(new PanoramicScreenshotParameters(new Vector3f(this.gameRenderer.getMainCamera().forwardVector())));
                this.window.setWidth(4096);
                this.window.setHeight(4096);
                $$6.resize(4096, 4096);
                for (int $$11 = 0; $$11 < 6; $$11++) {
                    switch ($$11) {
                        case 0:
                            this.player.setYRot($$8);
                            this.player.setXRot(0.0f);
                            break;
                        case 1:
                            this.player.setYRot(($$8 + 90.0f) % 360.0f);
                            this.player.setXRot(0.0f);
                            break;
                        case 2:
                            this.player.setYRot(($$8 + 180.0f) % 360.0f);
                            this.player.setXRot(0.0f);
                            break;
                        case 3:
                            this.player.setYRot(($$8 - 90.0f) % 360.0f);
                            this.player.setXRot(0.0f);
                            break;
                        case 4:
                            this.player.setYRot($$8);
                            this.player.setXRot(-90.0f);
                            break;
                        case 5:
                        default:
                            this.player.setYRot($$8);
                            this.player.setXRot(90.0f);
                            break;
                    }
                    this.player.yRotO = this.player.getYRot();
                    this.player.xRotO = this.player.getXRot();
                    this.gameRenderer.updateCamera(DeltaTracker.ONE);
                    this.gameRenderer.renderLevel(DeltaTracker.ONE);
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                    }
                    Screenshot.grab($$0, "panorama_" + $$11 + ".png", $$6, 4, $$02 -> {
                    });
                }
                Component $$12 = Component.literal($$0.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle($$1 -> {
                    return $$1.withClickEvent(new ClickEvent.OpenFile($$0.getAbsoluteFile()));
                });
                MutableComponent mutableComponentTranslatable = Component.translatable("screenshot.success", $$12);
                this.player.setXRot($$7);
                this.player.setYRot($$8);
                this.player.xRotO = $$9;
                this.player.yRotO = $$10;
                this.gameRenderer.setRenderBlockOutline(true);
                this.window.setWidth($$4);
                this.window.setHeight($$5);
                $$6.resize($$4, $$5);
                this.gameRenderer.setPanoramicScreenshotParameters(null);
                return mutableComponentTranslatable;
            } catch (Exception $$13) {
                LOGGER.error("Couldn't save image", $$13);
                MutableComponent mutableComponentTranslatable2 = Component.translatable("screenshot.failure", $$13.getMessage());
                this.player.setXRot($$7);
                this.player.setYRot($$8);
                this.player.xRotO = $$9;
                this.player.yRotO = $$10;
                this.gameRenderer.setRenderBlockOutline(true);
                this.window.setWidth($$4);
                this.window.setHeight($$5);
                $$6.resize($$4, $$5);
                this.gameRenderer.setPanoramicScreenshotParameters(null);
                return mutableComponentTranslatable2;
            }
        } catch (Throwable th) {
            this.player.setXRot($$7);
            this.player.setYRot($$8);
            this.player.xRotO = $$9;
            this.player.yRotO = $$10;
            this.gameRenderer.setRenderBlockOutline(true);
            this.window.setWidth($$4);
            this.window.setHeight($$5);
            $$6.resize($$4, $$5);
            this.gameRenderer.setPanoramicScreenshotParameters(null);
            throw th;
        }
    }

    public SplashManager getSplashManager() {
        return this.splashManager;
    }

    public Overlay getOverlay() {
        return this.overlay;
    }

    public PlayerSocialManager getPlayerSocialManager() {
        return this.playerSocialManager;
    }

    public Window getWindow() {
        return this.window;
    }

    public FramerateLimitTracker getFramerateLimitTracker() {
        return this.framerateLimitTracker;
    }

    public DebugScreenOverlay getDebugOverlay() {
        return this.gui.getDebugOverlay();
    }

    public RenderBuffers renderBuffers() {
        return this.renderBuffers;
    }

    public void updateMaxMipLevel(int $$0) {
        this.atlasManager.updateMaxMipLevel($$0);
    }

    public EntityModelSet getEntityModels() {
        return this.modelManager.entityModels().get();
    }

    public boolean isTextFilteringEnabled() {
        return userProperties().flag(UserApiService.UserFlag.PROFANITY_FILTER_ENABLED);
    }

    public void prepareForMultiplayer() {
        this.playerSocialManager.startOnlineMode();
        getProfileKeyPairManager().prepareKeyPair();
    }

    public InputType getLastInputType() {
        return this.lastInputType;
    }

    public void setLastInputType(InputType $$0) {
        this.lastInputType = $$0;
    }

    public GameNarrator getNarrator() {
        return this.narrator;
    }

    public ChatListener getChatListener() {
        return this.chatListener;
    }

    public ReportingContext getReportingContext() {
        return this.reportingContext;
    }

    public RealmsDataFetcher realmsDataFetcher() {
        return this.realmsDataFetcher;
    }

    public QuickPlayLog quickPlayLog() {
        return this.quickPlayLog;
    }

    public CommandHistory commandHistory() {
        return this.commandHistory;
    }

    public DirectoryValidator directoryValidator() {
        return this.directoryValidator;
    }

    public PlayerSkinRenderCache playerSkinRenderCache() {
        return this.playerSkinRenderCache;
    }

    private float getTickTargetMillis(float $$0) {
        if (this.level != null) {
            TickRateManager $$1 = this.level.tickRateManager();
            if ($$1.runsNormally()) {
                return Math.max($$0, $$1.millisecondsPerTick());
            }
        }
        return $$0;
    }

    public ItemModelResolver getItemModelResolver() {
        return this.itemModelResolver;
    }

    public boolean canInterruptScreen() {
        return (this.screen == null || this.screen.canInterruptWithAnotherScreen()) && !this.clientLevelTeardownInProgress;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/Minecraft$ChatStatus.class */
    public enum ChatStatus {
        ENABLED(CommonComponents.EMPTY) { // from class: net.minecraft.client.Minecraft.ChatStatus.1
            @Override // net.minecraft.client.Minecraft.ChatStatus
            public boolean isChatAllowed(boolean $$0) {
                return true;
            }
        },
        DISABLED_BY_OPTIONS(Component.translatable("chat.disabled.options").withStyle(ChatFormatting.RED)) { // from class: net.minecraft.client.Minecraft.ChatStatus.2
            @Override // net.minecraft.client.Minecraft.ChatStatus
            public boolean isChatAllowed(boolean $$0) {
                return false;
            }
        },
        DISABLED_BY_LAUNCHER(Component.translatable("chat.disabled.launcher").withStyle(ChatFormatting.RED)) { // from class: net.minecraft.client.Minecraft.ChatStatus.3
            @Override // net.minecraft.client.Minecraft.ChatStatus
            public boolean isChatAllowed(boolean $$0) {
                return $$0;
            }
        },
        DISABLED_BY_PROFILE(Component.translatable("chat.disabled.profile", Component.keybind(Minecraft.instance.options.keyChat.getName())).withStyle(ChatFormatting.RED)) { // from class: net.minecraft.client.Minecraft.ChatStatus.4
            @Override // net.minecraft.client.Minecraft.ChatStatus
            public boolean isChatAllowed(boolean $$0) {
                return $$0;
            }
        };

        static final Component INFO_DISABLED_BY_PROFILE = Component.translatable("chat.disabled.profile.moreInfo");
        private final Component message;

        public abstract boolean isChatAllowed(boolean z);

        ChatStatus(Component $$0) {
            this.message = $$0;
        }

        public Component getMessage() {
            return this.message;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/Minecraft$GameLoadCookie.class */
    static final class GameLoadCookie extends Record {
        private final RealmsClient realmsClient;
        private final GameConfig.QuickPlayData quickPlayData;

        GameLoadCookie(RealmsClient $$0, GameConfig.QuickPlayData $$1) {
            this.realmsClient = $$0;
            this.quickPlayData = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GameLoadCookie.class), GameLoadCookie.class, "realmsClient;quickPlayData", "FIELD:Lnet/minecraft/client/Minecraft$GameLoadCookie;->realmsClient:Lcom/mojang/realmsclient/client/RealmsClient;", "FIELD:Lnet/minecraft/client/Minecraft$GameLoadCookie;->quickPlayData:Lnet/minecraft/client/main/GameConfig$QuickPlayData;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GameLoadCookie.class), GameLoadCookie.class, "realmsClient;quickPlayData", "FIELD:Lnet/minecraft/client/Minecraft$GameLoadCookie;->realmsClient:Lcom/mojang/realmsclient/client/RealmsClient;", "FIELD:Lnet/minecraft/client/Minecraft$GameLoadCookie;->quickPlayData:Lnet/minecraft/client/main/GameConfig$QuickPlayData;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GameLoadCookie.class, Object.class), GameLoadCookie.class, "realmsClient;quickPlayData", "FIELD:Lnet/minecraft/client/Minecraft$GameLoadCookie;->realmsClient:Lcom/mojang/realmsclient/client/RealmsClient;", "FIELD:Lnet/minecraft/client/Minecraft$GameLoadCookie;->quickPlayData:Lnet/minecraft/client/main/GameConfig$QuickPlayData;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public RealmsClient realmsClient() {
            return this.realmsClient;
        }

        public GameConfig.QuickPlayData quickPlayData() {
            return this.quickPlayData;
        }
    }

    public static String getLauncherBrand() {
        return System.getProperty("minecraft.launcher.brand");
    }

    public PacketProcessor packetProcessor() {
        return this.packetProcessor;
    }

    public Gizmos.TemporaryCollection collectPerTickGizmos() {
        return Gizmos.withCollector(this.perTickGizmos);
    }

    public Collection<SimpleGizmoCollector.GizmoInstance> getPerTickGizmos() {
        return this.drainedLatestTickGizmos;
    }
}
