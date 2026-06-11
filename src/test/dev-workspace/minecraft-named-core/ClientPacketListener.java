package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.hash.HashCode;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.lang.ref.WeakReference;
import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.DebugQueryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.DemoIntroScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.dialog.DialogConnectionAccess;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.inventory.NautilusInventoryScreen;
import net.minecraft.client.gui.screens.inventory.TestInstanceBlockEditScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerReconfigScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.resources.sounds.BeeAggressiveSoundInstance;
import net.minecraft.client.resources.sounds.BeeFlyingSoundInstance;
import net.minecraft.client.resources.sounds.BeeSoundInstance;
import net.minecraft.client.resources.sounds.GuardianAttackSoundInstance;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.client.resources.sounds.SnifferSoundInstance;
import net.minecraft.client.waypoints.ClientWaypointManager;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.Connection;
import net.minecraft.network.HashedPatchMap;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.LastSeenMessagesTracker;
import net.minecraft.network.chat.LocalChatSession;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSignatureCache;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.chat.SignableCommand;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.chat.SignedMessageLink;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ConfigurationProtocols;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugBlockValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugChunkValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEntityValuePacket;
import net.minecraft.network.protocol.game.ClientboundDebugEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugSamplePacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameTestHighlightPosPacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMountScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveMinecartPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerRotationPacket;
import net.minecraft.network.protocol.game.ClientboundProjectilePowerPacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookRemovePacket;
import net.minecraft.network.protocol.game.ClientboundRecipeBookSettingsPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetHeldSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTestInstanceBlockStatus;
import net.minecraft.network.protocol.game.ClientboundTickingStatePacket;
import net.minecraft.network.protocol.game.ClientboundTickingStepPacket;
import net.minecraft.network.protocol.game.ClientboundTrackedWaypointPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandSignedPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundChatSessionUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundChunkBatchReceivedPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerLoadedPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.network.protocol.ping.ClientboundPongResponsePacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.util.Crypt;
import net.minecraft.util.HashOps;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.nautilus.AbstractNautilus;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfileKeyPair;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.NautilusInventoryMenu;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/ClientPacketListener.class */
public class ClientPacketListener extends ClientCommonPacketListenerImpl implements ClientGamePacketListener, TickablePacketListener {
    private static final int PENDING_OFFSET_THRESHOLD = 64;
    public static final int TELEPORT_INTERPOLATION_THRESHOLD = 64;
    private final GameProfile localGameProfile;
    private ClientLevel level;
    private ClientLevel.ClientLevelData levelData;
    private final Map<UUID, PlayerInfo> playerInfoMap;
    private final Set<PlayerInfo> listedPlayers;
    private final ClientAdvancements advancements;
    private final ClientSuggestionProvider suggestionsProvider;
    private final ClientSuggestionProvider restrictedSuggestionsProvider;
    private final DebugQueryHandler debugQueryHandler;
    private int serverChunkRadius;
    private int serverSimulationDistance;
    private final RandomSource random;
    private CommandDispatcher<ClientSuggestionProvider> commands;
    private ClientRecipeContainer recipes;
    private final UUID id;
    private Set<ResourceKey<Level>> levels;
    private final RegistryAccess.Frozen registryAccess;
    private final FeatureFlagSet enabledFeatures;
    private final PotionBrewing potionBrewing;
    private FuelValues fuelValues;
    private final HashedPatchMap.HashGenerator decoratedHashOpsGenerator;
    private OptionalInt removedPlayerVehicleId;
    private LocalChatSession chatSession;
    private SignedMessageChain.Encoder signedMessageEncoder;
    private int nextChatIndex;
    private LastSeenMessagesTracker lastSeenMessages;
    private MessageSignatureCache messageSignatureCache;
    private CompletableFuture<Optional<ProfileKeyPair>> keyPairFuture;
    private ClientInformation remoteClientInformation;
    private final ChunkBatchSizeCalculator chunkBatchSizeCalculator;
    private final PingDebugMonitor pingDebugMonitor;
    private final ClientDebugSubscriber debugSubscriber;
    private LevelLoadTracker levelLoadTracker;
    private boolean serverEnforcesSecureChat;
    private volatile boolean closed;
    private final Scoreboard scoreboard;
    private final ClientWaypointManager waypointManager;
    private final SessionSearchTrees searchTrees;
    private final List<WeakReference<CacheSlot<?, ?>>> cacheSlots;
    private boolean clientLoaded;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Component UNSECURE_SERVER_TOAST_TITLE = Component.translatable("multiplayer.unsecureserver.toast.title");
    private static final Component UNSERURE_SERVER_TOAST = Component.translatable("multiplayer.unsecureserver.toast");
    private static final Component INVALID_PACKET = Component.translatable("multiplayer.disconnect.invalid_packet");
    private static final Component RECONFIGURE_SCREEN_MESSAGE = Component.translatable("connect.reconfiguring");
    private static final Component BAD_CHAT_INDEX = Component.translatable("multiplayer.disconnect.bad_chat_index");
    private static final Component COMMAND_SEND_CONFIRM_TITLE = Component.translatable("multiplayer.confirm_command.title");
    private static final Component BUTTON_RUN_COMMAND = Component.translatable("multiplayer.confirm_command.run_command");
    private static final Component BUTTON_SUGGEST_COMMAND = Component.translatable("multiplayer.confirm_command.suggest_command");
    private static final Permission RESTRICTED_COMMAND = Permission.Atom.create("client/commands/restricted");
    static final PermissionCheck RESTRICTED_COMMAND_CHECK = new PermissionCheck.Require(RESTRICTED_COMMAND);
    private static final PermissionSet ALLOW_RESTRICTED_COMMANDS = $$0 -> {
        return $$0.equals(RESTRICTED_COMMAND);
    };
    private static final ClientboundCommandsPacket.NodeBuilder<ClientSuggestionProvider> COMMAND_NODE_BUILDER = new ClientboundCommandsPacket.NodeBuilder<ClientSuggestionProvider>() { // from class: net.minecraft.client.multiplayer.ClientPacketListener.1
        @Override // net.minecraft.network.protocol.game.ClientboundCommandsPacket.NodeBuilder
        public ArgumentBuilder<ClientSuggestionProvider, ?> createLiteral(String $$0) {
            return LiteralArgumentBuilder.literal($$0);
        }

        @Override // net.minecraft.network.protocol.game.ClientboundCommandsPacket.NodeBuilder
        public ArgumentBuilder<ClientSuggestionProvider, ?> createArgument(String $$0, ArgumentType<?> $$1, Identifier $$2) {
            RequiredArgumentBuilder<ClientSuggestionProvider, ?> $$3 = RequiredArgumentBuilder.argument($$0, $$1);
            if ($$2 != null) {
                $$3.suggests(SuggestionProviders.getProvider($$2));
            }
            return $$3;
        }

        @Override // net.minecraft.network.protocol.game.ClientboundCommandsPacket.NodeBuilder
        public ArgumentBuilder<ClientSuggestionProvider, ?> configure(ArgumentBuilder<ClientSuggestionProvider, ?> $$0, boolean $$1, boolean $$2) {
            if ($$1) {
                $$0.executes($$02 -> {
                    return 0;
                });
            }
            if ($$2) {
                $$0.requires(Commands.hasPermission(ClientPacketListener.RESTRICTED_COMMAND_CHECK));
            }
            return $$0;
        }
    };

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/ClientPacketListener$CommandCheckResult.class */
    enum CommandCheckResult {
        NO_ISSUES,
        PARSE_ERRORS,
        SIGNATURE_REQUIRED,
        PERMISSIONS_REQUIRED
    }

    public ClientPacketListener(Minecraft $$0, Connection $$1, CommonListenerCookie $$2) {
        super($$0, $$1, $$2);
        this.playerInfoMap = Maps.newHashMap();
        this.listedPlayers = new ReferenceOpenHashSet();
        this.debugQueryHandler = new DebugQueryHandler(this);
        this.serverChunkRadius = 3;
        this.serverSimulationDistance = 3;
        this.random = RandomSource.createThreadSafe();
        this.commands = new CommandDispatcher<>();
        this.recipes = new ClientRecipeContainer(Map.of(), SelectableRecipe.SingleInputSet.empty());
        this.id = UUID.randomUUID();
        this.removedPlayerVehicleId = OptionalInt.empty();
        this.signedMessageEncoder = SignedMessageChain.Encoder.UNSIGNED;
        this.lastSeenMessages = new LastSeenMessagesTracker(20);
        this.messageSignatureCache = MessageSignatureCache.createDefault();
        this.chunkBatchSizeCalculator = new ChunkBatchSizeCalculator();
        this.scoreboard = new Scoreboard();
        this.waypointManager = new ClientWaypointManager();
        this.searchTrees = new SessionSearchTrees();
        this.cacheSlots = new ArrayList();
        this.localGameProfile = $$2.localGameProfile();
        this.registryAccess = $$2.receivedRegistries();
        RegistryOps<HashCode> $$3 = this.registryAccess.createSerializationContext(HashOps.CRC32C_INSTANCE);
        this.decoratedHashOpsGenerator = $$12 -> {
            return Integer.valueOf(((HashCode) $$12.encodeValue($$3).getOrThrow($$12 -> {
                return new IllegalArgumentException("Failed to hash " + String.valueOf($$12) + ": " + $$12);
            })).asInt());
        };
        this.enabledFeatures = $$2.enabledFeatures();
        this.advancements = new ClientAdvancements($$0, this.telemetryManager);
        PermissionSet $$4 = $$13 -> {
            LocalPlayer $$22 = $$0.player;
            return $$22 != null && $$22.permissions().hasPermission($$13);
        };
        this.suggestionsProvider = new ClientSuggestionProvider(this, $$0, $$4.union(ALLOW_RESTRICTED_COMMANDS));
        this.restrictedSuggestionsProvider = new ClientSuggestionProvider(this, $$0, PermissionSet.NO_PERMISSIONS);
        this.pingDebugMonitor = new PingDebugMonitor(this, $$0.getDebugOverlay().getPingLogger());
        this.debugSubscriber = new ClientDebugSubscriber(this, $$0.getDebugOverlay());
        if ($$2.chatState() != null) {
            $$0.gui.getChat().restoreState($$2.chatState());
        }
        this.potionBrewing = PotionBrewing.bootstrap(this.enabledFeatures);
        this.fuelValues = FuelValues.vanillaBurnTimes($$2.receivedRegistries(), this.enabledFeatures);
        this.levelLoadTracker = $$2.levelLoadTracker();
    }

    public ClientSuggestionProvider getSuggestionsProvider() {
        return this.suggestionsProvider;
    }

    public void close() {
        this.closed = true;
        clearLevel();
        this.telemetryManager.onDisconnect();
    }

    public void clearLevel() {
        clearCacheSlots();
        this.level = null;
        this.levelLoadTracker = null;
    }

    private void clearCacheSlots() {
        for (WeakReference<CacheSlot<?, ?>> $$0 : this.cacheSlots) {
            CacheSlot<?, ?> $$1 = $$0.get();
            if ($$1 != null) {
                $$1.clear();
            }
        }
        this.cacheSlots.clear();
    }

    public RecipeAccess recipes() {
        return this.recipes;
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleLogin(ClientboundLoginPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gameMode = new MultiPlayerGameMode(this.minecraft, this);
        CommonPlayerSpawnInfo $$1 = $$0.commonPlayerSpawnInfo();
        List<ResourceKey<Level>> $$2 = Lists.newArrayList($$0.levels());
        Collections.shuffle($$2);
        this.levels = Sets.newLinkedHashSet($$2);
        ResourceKey<Level> $$3 = $$1.dimension();
        Holder<DimensionType> $$4 = $$1.dimensionType();
        this.serverChunkRadius = $$0.chunkRadius();
        this.serverSimulationDistance = $$0.simulationDistance();
        boolean $$5 = $$1.isDebug();
        boolean $$6 = $$1.isFlat();
        int $$7 = $$1.seaLevel();
        ClientLevel.ClientLevelData $$8 = new ClientLevel.ClientLevelData(Difficulty.NORMAL, $$0.hardcore(), $$6);
        this.levelData = $$8;
        this.level = new ClientLevel(this, $$8, $$3, $$4, this.serverChunkRadius, this.serverSimulationDistance, this.minecraft.levelRenderer, $$5, $$1.seed(), $$7);
        this.minecraft.setLevel(this.level);
        if (this.minecraft.player == null) {
            this.minecraft.player = this.minecraft.gameMode.createPlayer(this.level, new StatsCounter(), new ClientRecipeBook());
            this.minecraft.player.setYRot(-180.0f);
            if (this.minecraft.getSingleplayerServer() != null) {
                this.minecraft.getSingleplayerServer().setUUID(this.minecraft.player.getUUID());
            }
        }
        setClientLoaded(false);
        this.debugSubscriber.clear();
        this.minecraft.levelRenderer.debugRenderer.refreshRendererList();
        this.minecraft.player.resetPos();
        this.minecraft.player.setId($$0.playerId());
        this.level.addEntity(this.minecraft.player);
        this.minecraft.player.input = new KeyboardInput(this.minecraft.options);
        this.minecraft.gameMode.adjustPlayer(this.minecraft.player);
        this.minecraft.setCameraEntity(this.minecraft.player);
        startWaitingForNewLevel(this.minecraft.player, this.level, LevelLoadingScreen.Reason.OTHER);
        this.minecraft.player.setReducedDebugInfo($$0.reducedDebugInfo());
        this.minecraft.player.setShowDeathScreen($$0.showDeathScreen());
        this.minecraft.player.setDoLimitedCrafting($$0.doLimitedCrafting());
        this.minecraft.player.setLastDeathLocation($$1.lastDeathLocation());
        this.minecraft.player.setPortalCooldown($$1.portalCooldown());
        this.minecraft.gameMode.setLocalMode($$1.gameType(), $$1.previousGameType());
        this.minecraft.options.setServerRenderDistance($$0.chunkRadius());
        this.chatSession = null;
        this.signedMessageEncoder = SignedMessageChain.Encoder.UNSIGNED;
        this.nextChatIndex = 0;
        this.lastSeenMessages = new LastSeenMessagesTracker(20);
        this.messageSignatureCache = MessageSignatureCache.createDefault();
        if (this.connection.isEncrypted()) {
            prepareKeyPair();
        }
        this.telemetryManager.onPlayerInfoReceived($$1.gameType(), $$0.hardcore());
        this.minecraft.quickPlayLog().log(this.minecraft);
        this.serverEnforcesSecureChat = $$0.enforcesSecureChat();
        if (this.serverData != null && !this.seenInsecureChatWarning && !enforcesSecureChat()) {
            SystemToast $$9 = SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.UNSECURE_SERVER_WARNING, UNSECURE_SERVER_TOAST_TITLE, UNSERURE_SERVER_TOAST);
            this.minecraft.getToastManager().addToast($$9);
            this.seenInsecureChatWarning = true;
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleAddEntity(ClientboundAddEntityPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if (this.removedPlayerVehicleId.isPresent() && this.removedPlayerVehicleId.getAsInt() == $$0.getId()) {
            this.removedPlayerVehicleId = OptionalInt.empty();
        }
        Entity $$1 = createEntityFromPacket($$0);
        if ($$1 != null) {
            $$1.recreateFromPacket($$0);
            this.level.addEntity($$1);
            postAddEntitySoundInstance($$1);
        } else {
            LOGGER.warn("Skipping Entity with id {}", $$0.getType());
        }
        if ($$1 instanceof Player) {
            Player $$2 = (Player) $$1;
            UUID $$3 = $$2.getUUID();
            PlayerInfo $$4 = this.playerInfoMap.get($$3);
            if ($$4 != null) {
                this.seenPlayers.put($$3, $$4);
            }
        }
    }

    private Entity createEntityFromPacket(ClientboundAddEntityPacket $$0) {
        EntityType<?> $$1 = $$0.getType();
        if ($$1 == EntityType.PLAYER) {
            PlayerInfo $$2 = getPlayerInfo($$0.getUUID());
            if ($$2 == null) {
                LOGGER.warn("Server attempted to add player prior to sending player info (Player id {})", $$0.getUUID());
                return null;
            }
            return new RemotePlayer(this.level, $$2.getProfile());
        }
        return $$1.create(this.level, EntitySpawnReason.LOAD);
    }

    private void postAddEntitySoundInstance(Entity $$0) {
        BeeSoundInstance $$5;
        if ($$0 instanceof AbstractMinecart) {
            AbstractMinecart $$1 = (AbstractMinecart) $$0;
            this.minecraft.getSoundManager().play(new MinecartSoundInstance($$1));
        } else if ($$0 instanceof Bee) {
            Bee $$2 = (Bee) $$0;
            boolean $$3 = $$2.isAngry();
            if ($$3) {
                $$5 = new BeeAggressiveSoundInstance($$2);
            } else {
                $$5 = new BeeFlyingSoundInstance($$2);
            }
            this.minecraft.getSoundManager().queueTickingSound($$5);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetEntityMotion(ClientboundSetEntityMotionPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getId());
        if ($$1 == null) {
            return;
        }
        $$1.lerpMotion($$0.getMovement());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetEntityData(ClientboundSetEntityDataPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.id());
        if ($$1 != null) {
            $$1.getEntityData().assignValues($$0.packedItems());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleEntityPositionSync(ClientboundEntityPositionSyncPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.id());
        if ($$1 == null) {
            return;
        }
        Vec3 $$2 = $$0.values().position();
        $$1.getPositionCodec().setBase($$2);
        if ($$1.isLocalInstanceAuthoritative()) {
            return;
        }
        float $$3 = $$0.values().yRot();
        float $$4 = $$0.values().xRot();
        boolean $$5 = $$1.position().distanceToSqr($$2) > 4096.0d;
        if (this.level.isTickingEntity($$1) && !$$5) {
            $$1.moveOrInterpolateTo($$2, $$3, $$4);
        } else {
            $$1.snapTo($$2, $$3, $$4);
        }
        if (!$$1.isInterpolating() && $$1.hasIndirectPassenger(this.minecraft.player)) {
            $$1.positionRider(this.minecraft.player);
            this.minecraft.player.setOldPosAndRot();
        }
        $$1.setOnGround($$0.onGround());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTeleportEntity(ClientboundTeleportEntityPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.id());
        if ($$1 == null) {
            if (this.removedPlayerVehicleId.isPresent() && this.removedPlayerVehicleId.getAsInt() == $$0.id()) {
                LOGGER.debug("Trying to teleport entity with id {}, that was formerly player vehicle, applying teleport to player instead", Integer.valueOf($$0.id()));
                setValuesFromPositionPacket($$0.change(), $$0.relatives(), this.minecraft.player, false);
                this.connection.send(new ServerboundMovePlayerPacket.PosRot(this.minecraft.player.getX(), this.minecraft.player.getY(), this.minecraft.player.getZ(), this.minecraft.player.getYRot(), this.minecraft.player.getXRot(), false, false));
                return;
            }
            return;
        }
        boolean $$2 = $$0.relatives().contains(Relative.X) || $$0.relatives().contains(Relative.Y) || $$0.relatives().contains(Relative.Z);
        boolean $$3 = this.level.isTickingEntity($$1) || !$$1.isLocalInstanceAuthoritative() || $$2;
        boolean $$4 = setValuesFromPositionPacket($$0.change(), $$0.relatives(), $$1, $$3);
        $$1.setOnGround($$0.onGround());
        if (!$$4 && $$1.hasIndirectPassenger(this.minecraft.player)) {
            $$1.positionRider(this.minecraft.player);
            this.minecraft.player.setOldPosAndRot();
            if ($$1.isLocalInstanceAuthoritative()) {
                this.connection.send(ServerboundMoveVehiclePacket.fromEntity($$1));
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTickingState(ClientboundTickingStatePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if (this.minecraft.level == null) {
            return;
        }
        TickRateManager $$1 = this.minecraft.level.tickRateManager();
        $$1.setTickRate($$0.tickRate());
        $$1.setFrozen($$0.isFrozen());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTickingStep(ClientboundTickingStepPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if (this.minecraft.level == null) {
            return;
        }
        TickRateManager $$1 = this.minecraft.level.tickRateManager();
        $$1.setFrozenTicksToRun($$0.tickSteps());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetHeldSlot(ClientboundSetHeldSlotPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if (Inventory.isHotbarSlot($$0.slot())) {
            this.minecraft.player.getInventory().setSelectedSlot($$0.slot());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMoveEntity(ClientboundMoveEntityPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = $$0.getEntity(this.level);
        if ($$1 == null) {
            return;
        }
        if ($$1.isLocalInstanceAuthoritative()) {
            VecDeltaCodec $$2 = $$1.getPositionCodec();
            Vec3 $$3 = $$2.decode($$0.getXa(), $$0.getYa(), $$0.getZa());
            $$2.setBase($$3);
            return;
        }
        if ($$0.hasPosition()) {
            VecDeltaCodec $$4 = $$1.getPositionCodec();
            Vec3 $$5 = $$4.decode($$0.getXa(), $$0.getYa(), $$0.getZa());
            $$4.setBase($$5);
            if ($$0.hasRotation()) {
                $$1.moveOrInterpolateTo($$5, $$0.getYRot(), $$0.getXRot());
            } else {
                $$1.moveOrInterpolateTo($$5);
            }
        } else if ($$0.hasRotation()) {
            $$1.moveOrInterpolateTo($$0.getYRot(), $$0.getXRot());
        }
        $$1.setOnGround($$0.isOnGround());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMinecartAlongTrack(ClientboundMoveMinecartPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = $$0.getEntity(this.level);
        if (!($$1 instanceof AbstractMinecart)) {
            return;
        }
        AbstractMinecart $$3 = (AbstractMinecart) $$1;
        MinecartBehavior behavior = $$3.getBehavior();
        if (behavior instanceof NewMinecartBehavior) {
            NewMinecartBehavior $$4 = (NewMinecartBehavior) behavior;
            $$4.lerpSteps.addAll($$0.lerpSteps());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRotateMob(ClientboundRotateHeadPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = $$0.getEntity(this.level);
        if ($$1 == null) {
            return;
        }
        $$1.lerpHeadTo($$0.getYHeadRot(), 3);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRemoveEntities(ClientboundRemoveEntitiesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        $$0.getEntityIds().forEach($$02 -> {
            Entity $$1 = this.level.getEntity($$02);
            if ($$1 == null) {
                return;
            }
            if ($$1.hasIndirectPassenger(this.minecraft.player)) {
                LOGGER.debug("Remove entity {}:{} that has player as passenger", $$1.getType(), Integer.valueOf($$02));
                this.removedPlayerVehicleId = OptionalInt.of($$02);
            }
            this.level.removeEntity($$02, Entity.RemovalReason.DISCARDED);
            this.debugSubscriber.dropEntity($$1);
        });
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMovePlayer(ClientboundPlayerPositionPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        if (!$$1.isPassenger()) {
            setValuesFromPositionPacket($$0.change(), $$0.relatives(), $$1, false);
        }
        this.connection.send(new ServerboundAcceptTeleportationPacket($$0.id()));
        this.connection.send(new ServerboundMovePlayerPacket.PosRot($$1.getX(), $$1.getY(), $$1.getZ(), $$1.getYRot(), $$1.getXRot(), false, false));
    }

    private static boolean setValuesFromPositionPacket(PositionMoveRotation $$0, Set<Relative> $$1, Entity $$2, boolean $$3) {
        PositionMoveRotation $$4 = PositionMoveRotation.of($$2);
        PositionMoveRotation $$5 = PositionMoveRotation.calculateAbsolute($$4, $$0, $$1);
        boolean $$6 = $$4.position().distanceToSqr($$5.position()) > 4096.0d;
        if ($$3 && !$$6) {
            $$2.moveOrInterpolateTo($$5.position(), $$5.yRot(), $$5.xRot());
            $$2.setDeltaMovement($$5.deltaMovement());
            return true;
        }
        $$2.setPos($$5.position());
        $$2.setDeltaMovement($$5.deltaMovement());
        $$2.setYRot($$5.yRot());
        $$2.setXRot($$5.xRot());
        PositionMoveRotation $$7 = new PositionMoveRotation($$2.oldPosition(), Vec3.ZERO, $$2.yRotO, $$2.xRotO);
        PositionMoveRotation $$8 = PositionMoveRotation.calculateAbsolute($$7, $$0, $$1);
        $$2.setOldPosAndRot($$8.position(), $$8.yRot(), $$8.xRot());
        return false;
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRotatePlayer(ClientboundPlayerRotationPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        Set<Relative> $$2 = Relative.rotation($$0.relativeY(), $$0.relativeX());
        PositionMoveRotation $$3 = PositionMoveRotation.of($$1);
        PositionMoveRotation $$4 = PositionMoveRotation.calculateAbsolute($$3, $$3.withRotation($$0.yRot(), $$0.xRot()), $$2);
        $$1.setYRot($$4.yRot());
        $$1.setXRot($$4.xRot());
        $$1.setOldRot();
        this.connection.send(new ServerboundMovePlayerPacket.Rot($$1.getYRot(), $$1.getXRot(), false, false));
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        $$0.runUpdates(($$02, $$1) -> {
            this.level.setServerVerifiedBlockState($$02, $$1, 19);
        });
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        int $$1 = $$0.getX();
        int $$2 = $$0.getZ();
        updateLevelChunk($$1, $$2, $$0.getChunkData());
        ClientboundLightUpdatePacketData $$3 = $$0.getLightData();
        this.level.queueLightUpdate(() -> {
            applyLightData($$1, $$2, $$3, false);
            LevelChunk $$32 = this.level.getChunkSource().getChunk($$1, $$2, false);
            if ($$32 != null) {
                enableChunkLight($$32, $$1, $$2);
                this.minecraft.levelRenderer.onChunkReadyToRender($$32.getPos());
            }
        });
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleChunksBiomes(ClientboundChunksBiomesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        for (ClientboundChunksBiomesPacket.ChunkBiomeData $$1 : $$0.chunkBiomeData()) {
            this.level.getChunkSource().replaceBiomes($$1.pos().x, $$1.pos().z, $$1.getReadBuffer());
        }
        for (ClientboundChunksBiomesPacket.ChunkBiomeData $$2 : $$0.chunkBiomeData()) {
            this.level.onChunkLoaded(new ChunkPos($$2.pos().x, $$2.pos().z));
        }
        for (ClientboundChunksBiomesPacket.ChunkBiomeData $$3 : $$0.chunkBiomeData()) {
            for (int $$4 = -1; $$4 <= 1; $$4++) {
                for (int $$5 = -1; $$5 <= 1; $$5++) {
                    for (int $$6 = this.level.getMinSectionY(); $$6 <= this.level.getMaxSectionY(); $$6++) {
                        this.minecraft.levelRenderer.setSectionDirty($$3.pos().x + $$4, $$6, $$3.pos().z + $$5);
                    }
                }
            }
        }
    }

    private void updateLevelChunk(int $$0, int $$1, ClientboundLevelChunkPacketData $$2) {
        this.level.getChunkSource().replaceWithPacketData($$0, $$1, $$2.getReadBuffer(), $$2.getHeightmaps(), $$2.getBlockEntitiesTagsConsumer($$0, $$1));
    }

    private void enableChunkLight(LevelChunk $$0, int $$1, int $$2) {
        LevelLightEngine $$3 = this.level.getChunkSource().getLightEngine();
        LevelChunkSection[] $$4 = $$0.getSections();
        ChunkPos $$5 = $$0.getPos();
        for (int $$6 = 0; $$6 < $$4.length; $$6++) {
            LevelChunkSection $$7 = $$4[$$6];
            int $$8 = this.level.getSectionYFromSectionIndex($$6);
            $$3.updateSectionStatus(SectionPos.of($$5, $$8), $$7.hasOnlyAir());
        }
        this.level.setSectionRangeDirty($$1 - 1, this.level.getMinSectionY(), $$2 - 1, $$1 + 1, this.level.getMaxSectionY(), $$2 + 1);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getChunkSource().drop($$0.pos());
        this.debugSubscriber.dropChunk($$0.pos());
        queueLightRemoval($$0);
    }

    private void queueLightRemoval(ClientboundForgetLevelChunkPacket $$0) {
        ChunkPos $$1 = $$0.pos();
        this.level.queueLightUpdate(() -> {
            LevelLightEngine $$12 = this.level.getLightEngine();
            $$12.setLightEnabled($$1, false);
            for (int $$2 = $$12.getMinLightSection(); $$2 < $$12.getMaxLightSection(); $$2++) {
                SectionPos $$3 = SectionPos.of($$1, $$2);
                $$12.queueSectionData(LightLayer.BLOCK, $$3, null);
                $$12.queueSectionData(LightLayer.SKY, $$3, null);
            }
            for (int $$4 = this.level.getMinSectionY(); $$4 <= this.level.getMaxSectionY(); $$4++) {
                $$12.updateSectionStatus(SectionPos.of($$1, $$4), true);
            }
        });
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBlockUpdate(ClientboundBlockUpdatePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.setServerVerifiedBlockState($$0.getPos(), $$0.getBlockState(), 19);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleConfigurationStart(ClientboundStartConfigurationPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.getChatListener().flushQueue();
        sendChatAcknowledgement();
        ChatComponent.State $$1 = this.minecraft.gui.getChat().storeState();
        this.minecraft.clearClientLevel(new ServerReconfigScreen(RECONFIGURE_SCREEN_MESSAGE, this.connection));
        this.connection.setupInboundProtocol(ConfigurationProtocols.CLIENTBOUND, new ClientConfigurationPacketListenerImpl(this.minecraft, this.connection, new CommonListenerCookie(new LevelLoadTracker(), this.localGameProfile, this.telemetryManager, this.registryAccess, this.enabledFeatures, this.serverBrand, this.serverData, this.postDisconnectScreen, this.serverCookies, $$1, this.customReportDetails, serverLinks(), this.seenPlayers, this.seenInsecureChatWarning)));
        send(ServerboundConfigurationAcknowledgedPacket.INSTANCE);
        this.connection.setupOutboundProtocol(ConfigurationProtocols.SERVERBOUND);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTakeItemEntity(ClientboundTakeItemEntityPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getItemId());
        LivingEntity $$2 = (LivingEntity) this.level.getEntity($$0.getPlayerId());
        if ($$2 == null) {
            $$2 = this.minecraft.player;
        }
        if ($$1 != null) {
            if ($$1 instanceof ExperienceOrb) {
                this.level.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.1f, ((this.random.nextFloat() - this.random.nextFloat()) * 0.35f) + 0.9f, false);
            } else {
                this.level.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f, ((this.random.nextFloat() - this.random.nextFloat()) * 1.4f) + 2.0f, false);
            }
            EntityRenderState $$3 = this.minecraft.getEntityRenderDispatcher().extractEntity($$1, 1.0f);
            this.minecraft.particleEngine.add(new ItemPickupParticle(this.level, $$3, $$2, $$1.getDeltaMovement()));
            if (!($$1 instanceof ItemEntity)) {
                if (!($$1 instanceof ExperienceOrb)) {
                    this.level.removeEntity($$0.getItemId(), Entity.RemovalReason.DISCARDED);
                    return;
                }
                return;
            }
            ItemEntity $$4 = (ItemEntity) $$1;
            ItemStack $$5 = $$4.getItem();
            if (!$$5.isEmpty()) {
                $$5.shrink($$0.getAmount());
            }
            if ($$5.isEmpty()) {
                this.level.removeEntity($$0.getItemId(), Entity.RemovalReason.DISCARDED);
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSystemChat(ClientboundSystemChatPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.getChatListener().handleSystemMessage($$0.content(), $$0.overlay());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerChat(ClientboundPlayerChatPacket $$0) {
        SignedMessageLink $$7;
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        int $$1 = this.nextChatIndex;
        this.nextChatIndex = $$1 + 1;
        if ($$0.globalIndex() != $$1) {
            LOGGER.error("Missing or out-of-order chat message from server, expected index {} but got {}", Integer.valueOf($$1), Integer.valueOf($$0.globalIndex()));
            this.connection.disconnect(BAD_CHAT_INDEX);
            return;
        }
        Optional<SignedMessageBody> $$2 = $$0.body().unpack(this.messageSignatureCache);
        if ($$2.isEmpty()) {
            LOGGER.error("Message from player with ID {} referenced unrecognized signature id", $$0.sender());
            this.connection.disconnect(INVALID_PACKET);
            return;
        }
        this.messageSignatureCache.push($$2.get(), $$0.signature());
        UUID $$3 = $$0.sender();
        PlayerInfo $$4 = getPlayerInfo($$3);
        if ($$4 == null) {
            LOGGER.error("Received player chat packet for unknown player with ID: {}", $$3);
            this.minecraft.getChatListener().handleChatMessageError($$3, $$0.signature(), $$0.chatType());
            return;
        }
        RemoteChatSession $$5 = $$4.getChatSession();
        if ($$5 != null) {
            $$7 = new SignedMessageLink($$0.index(), $$3, $$5.sessionId());
        } else {
            $$7 = SignedMessageLink.unsigned($$3);
        }
        PlayerChatMessage $$8 = $$4.getMessageValidator().updateAndValidate(new PlayerChatMessage($$7, $$0.signature(), $$2.get(), $$0.unsignedContent(), $$0.filterMask()));
        if ($$8 != null) {
            this.minecraft.getChatListener().handlePlayerChatMessage($$8, $$4.getProfile(), $$0.chatType());
        } else {
            this.minecraft.getChatListener().handleChatMessageError($$3, $$0.signature(), $$0.chatType());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDisguisedChat(ClientboundDisguisedChatPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.getChatListener().handleDisguisedChatMessage($$0.message(), $$0.chatType());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDeleteChat(ClientboundDeleteChatPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Optional<MessageSignature> $$1 = $$0.messageSignature().unpack(this.messageSignatureCache);
        if ($$1.isEmpty()) {
            this.connection.disconnect(INVALID_PACKET);
            return;
        }
        this.lastSeenMessages.ignorePending($$1.get());
        if (!this.minecraft.getChatListener().removeFromDelayedMessageQueue($$1.get())) {
            this.minecraft.gui.getChat().deleteMessage($$1.get());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleAnimate(ClientboundAnimatePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getId());
        if ($$1 == null) {
            return;
        }
        if ($$0.getAction() == 0) {
            LivingEntity $$2 = (LivingEntity) $$1;
            $$2.swing(InteractionHand.MAIN_HAND);
            return;
        }
        if ($$0.getAction() == 3) {
            LivingEntity $$3 = (LivingEntity) $$1;
            $$3.swing(InteractionHand.OFF_HAND);
        } else if ($$0.getAction() == 2) {
            Player $$4 = (Player) $$1;
            $$4.stopSleepInBed(false, false);
        } else if ($$0.getAction() == 4) {
            this.minecraft.particleEngine.createTrackingEmitter($$1, ParticleTypes.CRIT);
        } else if ($$0.getAction() == 5) {
            this.minecraft.particleEngine.createTrackingEmitter($$1, ParticleTypes.ENCHANTED_HIT);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleHurtAnimation(ClientboundHurtAnimationPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.id());
        if ($$1 == null) {
            return;
        }
        $$1.animateHurt($$0.yaw());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetTime(ClientboundSetTimePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.setTimeFromServer($$0.gameTime(), $$0.dayTime(), $$0.tickDayTime());
        this.telemetryManager.setTime($$0.gameTime());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.level.setRespawnData($$0.respawnData());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getVehicle());
        if ($$1 == null) {
            LOGGER.warn("Received passengers for unknown entity");
            return;
        }
        boolean $$2 = $$1.hasIndirectPassenger(this.minecraft.player);
        $$1.ejectPassengers();
        for (int $$3 : $$0.getPassengers()) {
            Entity $$4 = this.level.getEntity($$3);
            if ($$4 != null) {
                $$4.startRiding($$1, true, false);
                if ($$4 == this.minecraft.player) {
                    this.removedPlayerVehicleId = OptionalInt.empty();
                    if (!$$2) {
                        if ($$1 instanceof AbstractBoat) {
                            this.minecraft.player.yRotO = $$1.getYRot();
                            this.minecraft.player.setYRot($$1.getYRot());
                            this.minecraft.player.setYHeadRot($$1.getYRot());
                        }
                        Component $$5 = Component.translatable("mount.onboard", this.minecraft.options.keyShift.getTranslatedKeyMessage());
                        this.minecraft.gui.setOverlayMessage($$5, false);
                        this.minecraft.getNarrator().saySystemNow($$5);
                    }
                }
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ScoreHolder entity = this.level.getEntity($$0.getSourceId());
        if (entity instanceof Leashable) {
            Leashable $$2 = (Leashable) entity;
            $$2.setDelayedLeashHolderId($$0.getDestId());
        }
    }

    private static ItemStack findTotem(Player $$0) {
        for (InteractionHand $$1 : InteractionHand.values()) {
            ItemStack $$2 = $$0.getItemInHand($$1);
            if ($$2.has(DataComponents.DEATH_PROTECTION)) {
                return $$2;
            }
        }
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleEntityEvent(ClientboundEntityEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = $$0.getEntity(this.level);
        if ($$1 != null) {
            switch ($$0.getEventId()) {
                case 21:
                    this.minecraft.getSoundManager().play(new GuardianAttackSoundInstance((Guardian) $$1));
                    break;
                case 35:
                    this.minecraft.particleEngine.createTrackingEmitter($$1, ParticleTypes.TOTEM_OF_UNDYING, 30);
                    this.level.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.TOTEM_USE, $$1.getSoundSource(), 1.0f, 1.0f, false);
                    if ($$1 == this.minecraft.player) {
                        this.minecraft.gameRenderer.displayItemActivation(findTotem(this.minecraft.player));
                    }
                    break;
                case 63:
                    this.minecraft.getSoundManager().play(new SnifferSoundInstance((Sniffer) $$1));
                    break;
                default:
                    $$1.handleEntityEvent($$0.getEventId());
                    break;
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDamageEvent(ClientboundDamageEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.entityId());
        if ($$1 == null) {
            return;
        }
        $$1.handleDamageEvent($$0.getSource(this.level));
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetHealth(ClientboundSetHealthPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.player.hurtTo($$0.getHealth());
        this.minecraft.player.getFoodData().setFoodLevel($$0.getFood());
        this.minecraft.player.getFoodData().setSaturation($$0.getSaturation());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetExperience(ClientboundSetExperiencePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.player.setExperienceValues($$0.getExperienceProgress(), $$0.getTotalExperience(), $$0.getExperienceLevel());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRespawn(ClientboundRespawnPacket $$0) {
        LocalPlayer $$14;
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        CommonPlayerSpawnInfo $$1 = $$0.commonPlayerSpawnInfo();
        ResourceKey<Level> $$2 = $$1.dimension();
        Holder<DimensionType> $$3 = $$1.dimensionType();
        LocalPlayer $$4 = this.minecraft.player;
        ResourceKey<Level> $$5 = $$4.level().dimension();
        boolean $$6 = $$2 != $$5;
        LevelLoadingScreen.Reason $$7 = determineLevelLoadingReason($$4.isDeadOrDying(), $$2, $$5);
        if ($$6) {
            Map<MapId, MapItemSavedData> $$8 = this.level.getAllMapData();
            boolean $$9 = $$1.isDebug();
            boolean $$10 = $$1.isFlat();
            int $$11 = $$1.seaLevel();
            ClientLevel.ClientLevelData $$12 = new ClientLevel.ClientLevelData(this.levelData.getDifficulty(), this.levelData.isHardcore(), $$10);
            this.levelData = $$12;
            this.level = new ClientLevel(this, $$12, $$2, $$3, this.serverChunkRadius, this.serverSimulationDistance, this.minecraft.levelRenderer, $$9, $$1.seed(), $$11);
            this.level.addMapData($$8);
            this.minecraft.setLevel(this.level);
            this.debugSubscriber.dropLevel();
        }
        this.minecraft.setCameraEntity(null);
        if ($$4.hasContainerOpen()) {
            $$4.closeContainer();
        }
        if ($$0.shouldKeep((byte) 2)) {
            $$14 = this.minecraft.gameMode.createPlayer(this.level, $$4.getStats(), $$4.getRecipeBook(), $$4.getLastSentInput(), $$4.isSprinting());
        } else {
            $$14 = this.minecraft.gameMode.createPlayer(this.level, $$4.getStats(), $$4.getRecipeBook());
        }
        setClientLoaded(false);
        startWaitingForNewLevel($$14, this.level, $$7);
        $$14.setId($$4.getId());
        this.minecraft.player = $$14;
        if ($$6) {
            this.minecraft.getMusicManager().stopPlaying();
        }
        this.minecraft.setCameraEntity($$14);
        if ($$0.shouldKeep((byte) 2)) {
            List<SynchedEntityData.DataValue<?>> $$15 = $$4.getEntityData().getNonDefaultValues();
            if ($$15 != null) {
                $$14.getEntityData().assignValues($$15);
            }
            $$14.setDeltaMovement($$4.getDeltaMovement());
            $$14.setYRot($$4.getYRot());
            $$14.setXRot($$4.getXRot());
        } else {
            $$14.resetPos();
            $$14.setYRot(-180.0f);
        }
        if ($$0.shouldKeep((byte) 1)) {
            $$14.getAttributes().assignAllValues($$4.getAttributes());
        } else {
            $$14.getAttributes().assignBaseValues($$4.getAttributes());
        }
        this.level.addEntity($$14);
        $$14.input = new KeyboardInput(this.minecraft.options);
        this.minecraft.gameMode.adjustPlayer($$14);
        $$14.setReducedDebugInfo($$4.isReducedDebugInfo());
        $$14.setShowDeathScreen($$4.shouldShowDeathScreen());
        $$14.setLastDeathLocation($$1.lastDeathLocation());
        $$14.setPortalCooldown($$1.portalCooldown());
        $$14.portalEffectIntensity = $$4.portalEffectIntensity;
        $$14.oPortalEffectIntensity = $$4.oPortalEffectIntensity;
        if ((this.minecraft.screen instanceof DeathScreen) || (this.minecraft.screen instanceof DeathScreen.TitleConfirmScreen)) {
            this.minecraft.setScreen(null);
        }
        this.minecraft.gameMode.setLocalMode($$1.gameType(), $$1.previousGameType());
    }

    private LevelLoadingScreen.Reason determineLevelLoadingReason(boolean $$0, ResourceKey<Level> $$1, ResourceKey<Level> $$2) {
        LevelLoadingScreen.Reason $$3 = LevelLoadingScreen.Reason.OTHER;
        if (!$$0) {
            if ($$1 == Level.NETHER || $$2 == Level.NETHER) {
                $$3 = LevelLoadingScreen.Reason.NETHER_PORTAL;
            } else if ($$1 == Level.END || $$2 == Level.END) {
                $$3 = LevelLoadingScreen.Reason.END_PORTAL;
            }
        }
        return $$3;
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleExplosion(ClientboundExplodePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Vec3 $$1 = $$0.center();
        this.minecraft.level.playLocalSound($$1.x(), $$1.y(), $$1.z(), $$0.explosionSound().value(), SoundSource.BLOCKS, 4.0f, (1.0f + ((this.minecraft.level.random.nextFloat() - this.minecraft.level.random.nextFloat()) * 0.2f)) * 0.7f, false);
        this.minecraft.level.addParticle($$0.explosionParticle(), $$1.x(), $$1.y(), $$1.z(), 1.0d, Density.SURFACE, Density.SURFACE);
        this.minecraft.level.trackExplosionEffects($$1, $$0.radius(), $$0.blockCount(), $$0.blockParticles());
        Optional<Vec3> optionalPlayerKnockback = $$0.playerKnockback();
        LocalPlayer localPlayer = this.minecraft.player;
        Objects.requireNonNull(localPlayer);
        optionalPlayerKnockback.ifPresent(localPlayer::addDeltaMovement);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMountScreenOpen(ClientboundMountScreenOpenPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getEntityId());
        LocalPlayer $$2 = this.minecraft.player;
        int $$3 = $$0.getInventoryColumns();
        SimpleContainer $$4 = new SimpleContainer(AbstractMountInventoryMenu.getInventorySize($$3));
        if ($$1 instanceof AbstractHorse) {
            AbstractHorse $$5 = (AbstractHorse) $$1;
            HorseInventoryMenu $$6 = new HorseInventoryMenu($$0.getContainerId(), $$2.getInventory(), $$4, $$5, $$3);
            $$2.containerMenu = $$6;
            this.minecraft.setScreen(new HorseInventoryScreen($$6, $$2.getInventory(), $$5, $$3));
            return;
        }
        if ($$1 instanceof AbstractNautilus) {
            AbstractNautilus $$7 = (AbstractNautilus) $$1;
            NautilusInventoryMenu $$8 = new NautilusInventoryMenu($$0.getContainerId(), $$2.getInventory(), $$4, $$7, $$3);
            $$2.containerMenu = $$8;
            this.minecraft.setScreen(new NautilusInventoryScreen($$8, $$2.getInventory(), $$7, $$3));
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleOpenScreen(ClientboundOpenScreenPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        MenuScreens.create($$0.getType(), this.minecraft, $$0.getContainerId(), $$0.getTitle());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleContainerSetSlot(ClientboundContainerSetSlotPacket $$0) {
        boolean $$6;
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        ItemStack $$2 = $$0.getItem();
        int $$3 = $$0.getSlot();
        this.minecraft.getTutorial().onGetItem($$2);
        Screen screen = this.minecraft.screen;
        if (screen instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen $$4 = (CreativeModeInventoryScreen) screen;
            $$6 = !$$4.isInventoryOpen();
        } else {
            $$6 = false;
        }
        if ($$0.getContainerId() == 0) {
            if (InventoryMenu.isHotbarSlot($$3) && !$$2.isEmpty()) {
                ItemStack $$7 = $$1.inventoryMenu.getSlot($$3).getItem();
                if ($$7.isEmpty() || $$7.getCount() < $$2.getCount()) {
                    $$2.setPopTime(5);
                }
            }
            $$1.inventoryMenu.setItem($$3, $$0.getStateId(), $$2);
        } else if ($$0.getContainerId() == $$1.containerMenu.containerId && ($$0.getContainerId() != 0 || !$$6)) {
            $$1.containerMenu.setItem($$3, $$0.getStateId(), $$2);
        }
        if (this.minecraft.screen instanceof CreativeModeInventoryScreen) {
            $$1.inventoryMenu.setRemoteSlot($$3, $$2);
            $$1.inventoryMenu.broadcastChanges();
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetCursorItem(ClientboundSetCursorItemPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.getTutorial().onGetItem($$0.contents());
        if (!(this.minecraft.screen instanceof CreativeModeInventoryScreen)) {
            this.minecraft.player.containerMenu.setCarried($$0.contents());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetPlayerInventory(ClientboundSetPlayerInventoryPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.getTutorial().onGetItem($$0.contents());
        this.minecraft.player.getInventory().setItem($$0.slot(), $$0.contents());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleContainerContent(ClientboundContainerSetContentPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        if ($$0.containerId() == 0) {
            $$1.inventoryMenu.initializeContents($$0.stateId(), $$0.items(), $$0.carriedItem());
        } else if ($$0.containerId() == $$1.containerMenu.containerId) {
            $$1.containerMenu.initializeContents($$0.stateId(), $$0.items(), $$0.carriedItem());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleOpenSignEditor(ClientboundOpenSignEditorPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        BlockPos $$1 = $$0.getPos();
        BlockEntity blockEntity = this.level.getBlockEntity($$1);
        if (blockEntity instanceof SignBlockEntity) {
            SignBlockEntity $$2 = (SignBlockEntity) blockEntity;
            this.minecraft.player.openTextEdit($$2, $$0.isFrontText());
        } else {
            LOGGER.warn("Ignoring openTextEdit on an invalid entity: {} at pos {}", this.level.getBlockEntity($$1), $$1);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBlockEntityData(ClientboundBlockEntityDataPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        BlockPos $$1 = $$0.getPos();
        this.minecraft.level.getBlockEntity($$1, $$0.getType()).ifPresent($$12 -> {
            ProblemReporter.ScopedCollector $$2 = new ProblemReporter.ScopedCollector($$12.problemPath(), LOGGER);
            try {
                $$12.loadWithComponents(TagValueInput.create($$2, this.registryAccess, $$0.getTag()));
                $$2.close();
                if (($$12 instanceof CommandBlockEntity) && (this.minecraft.screen instanceof CommandBlockEditScreen)) {
                    ((CommandBlockEditScreen) this.minecraft.screen).updateGui();
                }
            } catch (Throwable th) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        });
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleContainerSetData(ClientboundContainerSetDataPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        if ($$1.containerMenu.containerId == $$0.getContainerId()) {
            $$1.containerMenu.setData($$0.getId(), $$0.getValue());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetEquipment(ClientboundSetEquipmentPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getEntity());
        if ($$1 instanceof LivingEntity) {
            LivingEntity $$2 = (LivingEntity) $$1;
            $$0.getSlots().forEach($$12 -> {
                $$2.setItemSlot((EquipmentSlot) $$12.getFirst(), (ItemStack) $$12.getSecond());
            });
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleContainerClose(ClientboundContainerClosePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.player.clientSideCloseContainer();
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBlockEvent(ClientboundBlockEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.level.blockEvent($$0.getPos(), $$0.getBlock(), $$0.getB0(), $$0.getB1());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBlockDestruction(ClientboundBlockDestructionPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.level.destroyBlockProgress($$0.getId(), $$0.getPos(), $$0.getProgress());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleGameEvent(ClientboundGameEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        ClientboundGameEventPacket.Type $$2 = $$0.getEvent();
        float $$3 = $$0.getParam();
        int $$4 = Mth.floor($$3 + 0.5f);
        if ($$2 == ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE) {
            $$1.displayClientMessage(Component.translatable("block.minecraft.spawn.not_valid"), false);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.START_RAINING) {
            this.level.getLevelData().setRaining(true);
            this.level.setRainLevel(0.0f);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.STOP_RAINING) {
            this.level.getLevelData().setRaining(false);
            this.level.setRainLevel(1.0f);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.CHANGE_GAME_MODE) {
            this.minecraft.gameMode.setLocalMode(GameType.byId($$4));
            return;
        }
        if ($$2 == ClientboundGameEventPacket.WIN_GAME) {
            this.minecraft.setScreen(new WinScreen(true, () -> {
                this.minecraft.player.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
                this.minecraft.setScreen(null);
            }));
            return;
        }
        if ($$2 == ClientboundGameEventPacket.DEMO_EVENT) {
            Options $$5 = this.minecraft.options;
            Component $$6 = null;
            if ($$3 == 0.0f) {
                this.minecraft.setScreen(new DemoIntroScreen());
            } else if ($$3 == 101.0f) {
                $$6 = Component.translatable("demo.help.movement", $$5.keyUp.getTranslatedKeyMessage(), $$5.keyLeft.getTranslatedKeyMessage(), $$5.keyDown.getTranslatedKeyMessage(), $$5.keyRight.getTranslatedKeyMessage());
            } else if ($$3 == 102.0f) {
                $$6 = Component.translatable("demo.help.jump", $$5.keyJump.getTranslatedKeyMessage());
            } else if ($$3 == 103.0f) {
                $$6 = Component.translatable("demo.help.inventory", $$5.keyInventory.getTranslatedKeyMessage());
            } else if ($$3 == 104.0f) {
                $$6 = Component.translatable("demo.day.6", $$5.keyScreenshot.getTranslatedKeyMessage());
            }
            if ($$6 != null) {
                this.minecraft.gui.getChat().addMessage($$6);
                this.minecraft.getNarrator().saySystemQueued($$6);
                return;
            }
            return;
        }
        if ($$2 == ClientboundGameEventPacket.PLAY_ARROW_HIT_SOUND) {
            this.level.playSound($$1, $$1.getX(), $$1.getEyeY(), $$1.getZ(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.18f, 0.45f);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.RAIN_LEVEL_CHANGE) {
            this.level.setRainLevel($$3);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE) {
            this.level.setThunderLevel($$3);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.PUFFER_FISH_STING) {
            this.level.playSound($$1, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.PUFFER_FISH_STING, SoundSource.NEUTRAL, 1.0f, 1.0f);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT) {
            this.level.addParticle(ParticleTypes.ELDER_GUARDIAN, $$1.getX(), $$1.getY(), $$1.getZ(), Density.SURFACE, Density.SURFACE, Density.SURFACE);
            if ($$4 == 1) {
                this.level.playSound($$1, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE, 1.0f, 1.0f);
                return;
            }
            return;
        }
        if ($$2 == ClientboundGameEventPacket.IMMEDIATE_RESPAWN) {
            this.minecraft.player.setShowDeathScreen($$3 == 0.0f);
            return;
        }
        if ($$2 == ClientboundGameEventPacket.LIMITED_CRAFTING) {
            this.minecraft.player.setDoLimitedCrafting($$3 == 1.0f);
        } else if ($$2 == ClientboundGameEventPacket.LEVEL_CHUNKS_LOAD_START && this.levelLoadTracker != null) {
            this.levelLoadTracker.loadingPacketsReceived();
        }
    }

    private void startWaitingForNewLevel(LocalPlayer $$0, ClientLevel $$1, LevelLoadingScreen.Reason $$2) {
        if (this.levelLoadTracker == null) {
            this.levelLoadTracker = new LevelLoadTracker();
        }
        this.levelLoadTracker.startClientLoad($$0, $$1, this.minecraft.levelRenderer);
        Screen screen = this.minecraft.screen;
        if (screen instanceof LevelLoadingScreen) {
            LevelLoadingScreen $$3 = (LevelLoadingScreen) screen;
            $$3.update(this.levelLoadTracker, $$2);
        } else {
            this.minecraft.gui.getChat().preserveCurrentChatScreen();
            this.minecraft.setScreenAndShow(new LevelLoadingScreen(this.levelLoadTracker, $$2));
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMapItemData(ClientboundMapItemDataPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        MapId $$1 = $$0.mapId();
        MapItemSavedData $$2 = this.minecraft.level.getMapData($$1);
        if ($$2 == null) {
            $$2 = MapItemSavedData.createForClient($$0.scale(), $$0.locked(), this.minecraft.level.dimension());
            this.minecraft.level.overrideMapData($$1, $$2);
        }
        $$0.applyToMap($$2);
        this.minecraft.getMapTextureManager().update($$1, $$2);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleLevelEvent(ClientboundLevelEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if ($$0.isGlobalEvent()) {
            this.minecraft.level.globalLevelEvent($$0.getType(), $$0.getPos(), $$0.getData());
        } else {
            this.minecraft.level.levelEvent($$0.getType(), $$0.getPos(), $$0.getData());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.advancements.update($$0);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Identifier $$1 = $$0.getTab();
        if ($$1 == null) {
            this.advancements.setSelectedTab(null, false);
        } else {
            AdvancementHolder $$2 = this.advancements.get($$1);
            this.advancements.setSelectedTab($$2, false);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleCommands(ClientboundCommandsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.commands = new CommandDispatcher<>($$0.getRoot(CommandBuildContext.simple(this.registryAccess, this.enabledFeatures), COMMAND_NODE_BUILDER));
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleStopSoundEvent(ClientboundStopSoundPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.getSoundManager().stop($$0.getName(), $$0.getSource());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.suggestionsProvider.completeCustomSuggestions($$0.id(), $$0.toSuggestions());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleUpdateRecipes(ClientboundUpdateRecipesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.recipes = new ClientRecipeContainer($$0.itemSets(), $$0.stonecutterRecipes());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleLookAt(ClientboundPlayerLookAtPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Vec3 $$1 = $$0.getPosition(this.level);
        if ($$1 != null) {
            this.minecraft.player.lookAt($$0.getFromAnchor(), $$1);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTagQueryPacket(ClientboundTagQueryPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if (!this.debugQueryHandler.handleResponse($$0.getTransactionId(), $$0.getTag())) {
            LOGGER.debug("Got unhandled response to tag query {}", Integer.valueOf($$0.getTransactionId()));
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleAwardStats(ClientboundAwardStatsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ObjectIterator it = $$0.stats().object2IntEntrySet().iterator();
        while (it.hasNext()) {
            Object2IntMap.Entry<Stat<?>> $$1 = (Object2IntMap.Entry) it.next();
            Stat<?> $$2 = (Stat) $$1.getKey();
            int $$3 = $$1.getIntValue();
            this.minecraft.player.getStats().setValue(this.minecraft.player, $$2, $$3);
        }
        Screen screen = this.minecraft.screen;
        if (screen instanceof StatsScreen) {
            StatsScreen $$4 = (StatsScreen) screen;
            $$4.onStatsUpdated();
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRecipeBookAdd(ClientboundRecipeBookAddPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ClientRecipeBook $$1 = this.minecraft.player.getRecipeBook();
        if ($$0.replace()) {
            $$1.clear();
        }
        for (ClientboundRecipeBookAddPacket.Entry $$2 : $$0.entries()) {
            $$1.add($$2.contents());
            if ($$2.highlight()) {
                $$1.addHighlight($$2.contents().id());
            }
            if ($$2.notification()) {
                RecipeToast.addOrUpdate(this.minecraft.getToastManager(), $$2.contents().display());
            }
        }
        refreshRecipeBook($$1);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRecipeBookRemove(ClientboundRecipeBookRemovePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ClientRecipeBook $$1 = this.minecraft.player.getRecipeBook();
        for (RecipeDisplayId $$2 : $$0.recipes()) {
            $$1.remove($$2);
        }
        refreshRecipeBook($$1);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRecipeBookSettings(ClientboundRecipeBookSettingsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ClientRecipeBook $$1 = this.minecraft.player.getRecipeBook();
        $$1.setBookSettings($$0.bookSettings());
        refreshRecipeBook($$1);
    }

    private void refreshRecipeBook(ClientRecipeBook $$0) {
        $$0.rebuildCollections();
        this.searchTrees.updateRecipes($$0, this.level);
        GuiEventListener guiEventListener = this.minecraft.screen;
        if (guiEventListener instanceof RecipeUpdateListener) {
            RecipeUpdateListener $$1 = (RecipeUpdateListener) guiEventListener;
            $$1.recipesUpdated();
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getEntityId());
        if (!($$1 instanceof LivingEntity)) {
            return;
        }
        Holder<MobEffect> $$2 = $$0.getEffect();
        MobEffectInstance $$3 = new MobEffectInstance($$2, $$0.getEffectDurationTicks(), $$0.getEffectAmplifier(), $$0.isEffectAmbient(), $$0.isEffectVisible(), $$0.effectShowsIcon(), null);
        if (!$$0.shouldBlend()) {
            $$3.skipBlending();
        }
        ((LivingEntity) $$1).forceAddEffect($$3, null);
    }

    private <T> Registry.PendingTags<T> updateTags(ResourceKey<? extends Registry<? extends T>> $$0, TagNetworkSerialization.NetworkPayload $$1) {
        Registry<T> $$2 = this.registryAccess.lookupOrThrow((ResourceKey) $$0);
        return $$2.prepareTagReload($$1.resolve($$2));
    }

    @Override // net.minecraft.network.protocol.common.ClientCommonPacketListener
    public void handleUpdateTags(ClientboundUpdateTagsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        List<Registry.PendingTags<?>> $$1 = new ArrayList<>($$0.getTags().size());
        boolean $$2 = this.connection.isMemoryConnection();
        $$0.getTags().forEach(($$22, $$3) -> {
            if (!$$2 || RegistrySynchronization.isNetworkable($$22)) {
                $$1.add(updateTags($$22, $$3));
            }
        });
        $$1.forEach((v0) -> {
            v0.apply();
        });
        this.fuelValues = FuelValues.vanillaBurnTimes(this.registryAccess, this.enabledFeatures);
        List<ItemStack> $$32 = List.copyOf(CreativeModeTabs.searchTab().getDisplayItems());
        this.searchTrees.updateCreativeTags($$32);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerCombatEnd(ClientboundPlayerCombatEndPacket $$0) {
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerCombatEnter(ClientboundPlayerCombatEnterPacket $$0) {
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerCombatKill(ClientboundPlayerCombatKillPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.playerId());
        if ($$1 == this.minecraft.player) {
            if (this.minecraft.player.shouldShowDeathScreen()) {
                this.minecraft.setScreen(new DeathScreen($$0.message(), this.level.getLevelData().isHardcore(), this.minecraft.player));
            } else {
                this.minecraft.player.respawn();
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleChangeDifficulty(ClientboundChangeDifficultyPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.levelData.setDifficulty($$0.difficulty());
        this.levelData.setDifficultyLocked($$0.locked());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetCamera(ClientboundSetCameraPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = $$0.getEntity(this.level);
        if ($$1 != null) {
            this.minecraft.setCameraEntity($$1);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleInitializeBorder(ClientboundInitializeBorderPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        WorldBorder $$1 = this.level.getWorldBorder();
        $$1.setCenter($$0.getNewCenterX(), $$0.getNewCenterZ());
        long $$2 = $$0.getLerpTime();
        if ($$2 > 0) {
            $$1.lerpSizeBetween($$0.getOldSize(), $$0.getNewSize(), $$2, this.level.getGameTime());
        } else {
            $$1.setSize($$0.getNewSize());
        }
        $$1.setAbsoluteMaxSize($$0.getNewAbsoluteMaxSize());
        $$1.setWarningBlocks($$0.getWarningBlocks());
        $$1.setWarningTime($$0.getWarningTime());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetBorderCenter(ClientboundSetBorderCenterPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getWorldBorder().setCenter($$0.getNewCenterX(), $$0.getNewCenterZ());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetBorderLerpSize(ClientboundSetBorderLerpSizePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getWorldBorder().lerpSizeBetween($$0.getOldSize(), $$0.getNewSize(), $$0.getLerpTime(), this.level.getGameTime());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetBorderSize(ClientboundSetBorderSizePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getWorldBorder().setSize($$0.getSize());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetBorderWarningDistance(ClientboundSetBorderWarningDistancePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getWorldBorder().setWarningBlocks($$0.getWarningBlocks());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetBorderWarningDelay(ClientboundSetBorderWarningDelayPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getWorldBorder().setWarningTime($$0.getWarningDelay());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTitlesClear(ClientboundClearTitlesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.clearTitles();
        if ($$0.shouldResetTimes()) {
            this.minecraft.gui.resetTitleTimes();
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleServerData(ClientboundServerDataPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if (this.serverData == null) {
            return;
        }
        this.serverData.motd = $$0.motd();
        Optional<U> map = $$0.iconBytes().map(ServerData::validateIcon);
        ServerData serverData = this.serverData;
        Objects.requireNonNull(serverData);
        map.ifPresent(serverData::setIconBytes);
        ServerList.saveSingleServer(this.serverData);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleCustomChatCompletions(ClientboundCustomChatCompletionsPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.suggestionsProvider.modifyCustomCompletions($$0.action(), $$0.entries());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void setActionBarText(ClientboundSetActionBarTextPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.setOverlayMessage($$0.text(), false);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void setTitleText(ClientboundSetTitleTextPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.setTitle($$0.text());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void setSubtitleText(ClientboundSetSubtitleTextPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.setSubtitle($$0.text());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void setTitlesAnimation(ClientboundSetTitlesAnimationPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.setTimes($$0.getFadeIn(), $$0.getStay(), $$0.getFadeOut());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTabListCustomisation(ClientboundTabListPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.getTabList().setHeader($$0.header().getString().isEmpty() ? null : $$0.header());
        this.minecraft.gui.getTabList().setFooter($$0.footer().getString().isEmpty() ? null : $$0.footer());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity entity = $$0.getEntity(this.level);
        if (entity instanceof LivingEntity) {
            LivingEntity $$1 = (LivingEntity) entity;
            $$1.removeEffectNoUpdate($$0.effect());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerInfoRemove(ClientboundPlayerInfoRemovePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        for (UUID $$1 : $$0.profileIds()) {
            this.minecraft.getPlayerSocialManager().removePlayer($$1);
            PlayerInfo $$2 = this.playerInfoMap.remove($$1);
            if ($$2 != null) {
                this.listedPlayers.remove($$2);
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        for (ClientboundPlayerInfoUpdatePacket.Entry $$1 : $$0.newEntries()) {
            PlayerInfo $$2 = new PlayerInfo((GameProfile) Objects.requireNonNull($$1.profile()), enforcesSecureChat());
            if (this.playerInfoMap.putIfAbsent($$1.profileId(), $$2) == null) {
                this.minecraft.getPlayerSocialManager().addPlayer($$2);
            }
        }
        for (ClientboundPlayerInfoUpdatePacket.Entry $$3 : $$0.entries()) {
            PlayerInfo $$4 = this.playerInfoMap.get($$3.profileId());
            if ($$4 == null) {
                LOGGER.warn("Ignoring player info update for unknown player {} ({})", $$3.profileId(), $$0.actions());
            } else {
                for (ClientboundPlayerInfoUpdatePacket.Action $$5 : $$0.actions()) {
                    applyPlayerInfoUpdate($$5, $$3, $$4);
                }
            }
        }
    }

    private void applyPlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket.Action $$0, ClientboundPlayerInfoUpdatePacket.Entry $$1, PlayerInfo $$2) {
        switch ($$0) {
            case INITIALIZE_CHAT:
                initializeChatSession($$1, $$2);
                break;
            case UPDATE_GAME_MODE:
                if ($$2.getGameMode() != $$1.gameMode() && this.minecraft.player != null && this.minecraft.player.getUUID().equals($$1.profileId())) {
                    this.minecraft.player.onGameModeChanged($$1.gameMode());
                }
                $$2.setGameMode($$1.gameMode());
                break;
            case UPDATE_LISTED:
                if ($$1.listed()) {
                    this.listedPlayers.add($$2);
                } else {
                    this.listedPlayers.remove($$2);
                }
                break;
            case UPDATE_LATENCY:
                $$2.setLatency($$1.latency());
                break;
            case UPDATE_DISPLAY_NAME:
                $$2.setTabListDisplayName($$1.displayName());
                break;
            case UPDATE_HAT:
                $$2.setShowHat($$1.showHat());
                break;
            case UPDATE_LIST_ORDER:
                $$2.setTabListOrder($$1.listOrder());
                break;
        }
    }

    private void initializeChatSession(ClientboundPlayerInfoUpdatePacket.Entry $$0, PlayerInfo $$1) {
        GameProfile $$2 = $$1.getProfile();
        SignatureValidator $$3 = this.minecraft.services().profileKeySignatureValidator();
        if ($$3 == null) {
            LOGGER.warn("Ignoring chat session from {} due to missing Services public key", $$2.name());
            $$1.clearChatSession(enforcesSecureChat());
            return;
        }
        RemoteChatSession.Data $$4 = $$0.chatSession();
        if ($$4 != null) {
            try {
                RemoteChatSession $$5 = $$4.validate($$2, $$3);
                $$1.setChatSession($$5);
                return;
            } catch (ProfilePublicKey.ValidationException $$6) {
                LOGGER.error("Failed to validate profile key for player: '{}'", $$2.name(), $$6);
                $$1.clearChatSession(enforcesSecureChat());
                return;
            }
        }
        $$1.clearChatSession(enforcesSecureChat());
    }

    private boolean enforcesSecureChat() {
        return this.minecraft.services().canValidateProfileKeys() && this.serverEnforcesSecureChat;
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlayerAbilities(ClientboundPlayerAbilitiesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Player $$1 = this.minecraft.player;
        $$1.getAbilities().flying = $$0.isFlying();
        $$1.getAbilities().instabuild = $$0.canInstabuild();
        $$1.getAbilities().invulnerable = $$0.isInvulnerable();
        $$1.getAbilities().mayfly = $$0.canFly();
        $$1.getAbilities().setFlyingSpeed($$0.getFlyingSpeed());
        $$1.getAbilities().setWalkingSpeed($$0.getWalkingSpeed());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSoundEvent(ClientboundSoundPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.level.playSeededSound(this.minecraft.player, $$0.getX(), $$0.getY(), $$0.getZ(), $$0.getSound(), $$0.getSource(), $$0.getVolume(), $$0.getPitch(), $$0.getSeed());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSoundEntityEvent(ClientboundSoundEntityPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getId());
        if ($$1 == null) {
            return;
        }
        this.minecraft.level.playSeededSound(this.minecraft.player, $$1, $$0.getSound(), $$0.getSource(), $$0.getVolume(), $$0.getPitch(), $$0.getSeed());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBossUpdate(ClientboundBossEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.gui.getBossOverlay().update($$0);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleItemCooldown(ClientboundCooldownPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if ($$0.duration() == 0) {
            this.minecraft.player.getCooldowns().removeCooldown($$0.cooldownGroup());
        } else {
            this.minecraft.player.getCooldowns().addCooldown($$0.cooldownGroup(), $$0.duration());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMoveVehicle(ClientboundMoveVehiclePacket $$0) {
        Vec3 $$4;
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.minecraft.player.getRootVehicle();
        if ($$1 != this.minecraft.player && $$1.isLocalInstanceAuthoritative()) {
            Vec3 $$2 = $$0.position();
            if ($$1.isInterpolating()) {
                $$4 = $$1.getInterpolation().position();
            } else {
                $$4 = $$1.position();
            }
            if ($$2.distanceTo($$4) > 9.999999747378752E-6d) {
                if ($$1.isInterpolating()) {
                    $$1.getInterpolation().cancel();
                }
                $$1.absSnapTo($$2.x(), $$2.y(), $$2.z(), $$0.yRot(), $$0.xRot());
            }
            this.connection.send(ServerboundMoveVehiclePacket.fromEntity($$1));
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleOpenBook(ClientboundOpenBookPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ItemStack $$1 = this.minecraft.player.getItemInHand($$0.getHand());
        BookViewScreen.BookAccess $$2 = BookViewScreen.BookAccess.fromItem($$1);
        if ($$2 != null) {
            this.minecraft.setScreen(new BookViewScreen($$2));
        }
    }

    @Override // net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl
    public void handleCustomPayload(CustomPacketPayload $$0) {
        handleUnknownCustomPayload($$0);
    }

    private void handleUnknownCustomPayload(CustomPacketPayload $$0) {
        LOGGER.warn("Unknown custom packet payload: {}", $$0.type().id());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleAddObjective(ClientboundSetObjectivePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        String $$1 = $$0.getObjectiveName();
        if ($$0.getMethod() == 0) {
            this.scoreboard.addObjective($$1, ObjectiveCriteria.DUMMY, $$0.getDisplayName(), $$0.getRenderType(), false, $$0.getNumberFormat().orElse(null));
            return;
        }
        Objective $$2 = this.scoreboard.getObjective($$1);
        if ($$2 != null) {
            if ($$0.getMethod() == 1) {
                this.scoreboard.removeObjective($$2);
            } else if ($$0.getMethod() == 2) {
                $$2.setRenderType($$0.getRenderType());
                $$2.setDisplayName($$0.getDisplayName());
                $$2.setNumberFormat($$0.getNumberFormat().orElse(null));
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetScore(ClientboundSetScorePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        String $$1 = $$0.objectiveName();
        ScoreHolder $$2 = ScoreHolder.forNameOnly($$0.owner());
        Objective $$3 = this.scoreboard.getObjective($$1);
        if ($$3 != null) {
            ScoreAccess $$4 = this.scoreboard.getOrCreatePlayerScore($$2, $$3, true);
            $$4.set($$0.score());
            $$4.display($$0.display().orElse(null));
            $$4.numberFormatOverride($$0.numberFormat().orElse(null));
            return;
        }
        LOGGER.warn("Received packet for unknown scoreboard objective: {}", $$1);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleResetScore(ClientboundResetScorePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        String $$1 = $$0.objectiveName();
        ScoreHolder $$2 = ScoreHolder.forNameOnly($$0.owner());
        if ($$1 == null) {
            this.scoreboard.resetAllPlayerScores($$2);
            return;
        }
        Objective $$3 = this.scoreboard.getObjective($$1);
        if ($$3 != null) {
            this.scoreboard.resetSinglePlayerScore($$2, $$3);
        } else {
            LOGGER.warn("Received packet for unknown scoreboard objective: {}", $$1);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        String $$1 = $$0.getObjectiveName();
        Objective $$2 = $$1 == null ? null : this.scoreboard.getObjective($$1);
        this.scoreboard.setDisplayObjective($$0.getSlot(), $$2);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket $$0) {
        PlayerTeam $$3;
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        ClientboundSetPlayerTeamPacket.Action $$1 = $$0.getTeamAction();
        if ($$1 == ClientboundSetPlayerTeamPacket.Action.ADD) {
            $$3 = this.scoreboard.addPlayerTeam($$0.getName());
        } else {
            $$3 = this.scoreboard.getPlayerTeam($$0.getName());
            if ($$3 == null) {
                LOGGER.warn("Received packet for unknown team {}: team action: {}, player action: {}", new Object[]{$$0.getName(), $$0.getTeamAction(), $$0.getPlayerAction()});
                return;
            }
        }
        Optional<ClientboundSetPlayerTeamPacket.Parameters> $$4 = $$0.getParameters();
        PlayerTeam playerTeam = $$3;
        $$4.ifPresent($$12 -> {
            playerTeam.setDisplayName($$12.getDisplayName());
            playerTeam.setColor($$12.getColor());
            playerTeam.unpackOptions($$12.getOptions());
            playerTeam.setNameTagVisibility($$12.getNametagVisibility());
            playerTeam.setCollisionRule($$12.getCollisionRule());
            playerTeam.setPlayerPrefix($$12.getPlayerPrefix());
            playerTeam.setPlayerSuffix($$12.getPlayerSuffix());
        });
        ClientboundSetPlayerTeamPacket.Action $$5 = $$0.getPlayerAction();
        if ($$5 == ClientboundSetPlayerTeamPacket.Action.ADD) {
            for (String $$6 : $$0.getPlayers()) {
                this.scoreboard.addPlayerToTeam($$6, $$3);
            }
        } else if ($$5 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
            for (String $$7 : $$0.getPlayers()) {
                this.scoreboard.removePlayerFromTeam($$7, $$3);
            }
        }
        if ($$1 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
            this.scoreboard.removePlayerTeam($$3);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleParticleEvent(ClientboundLevelParticlesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        if ($$0.getCount() == 0) {
            double $$1 = $$0.getMaxSpeed() * $$0.getXDist();
            double $$2 = $$0.getMaxSpeed() * $$0.getYDist();
            double $$3 = $$0.getMaxSpeed() * $$0.getZDist();
            try {
                this.level.addParticle($$0.getParticle(), $$0.isOverrideLimiter(), $$0.alwaysShow(), $$0.getX(), $$0.getY(), $$0.getZ(), $$1, $$2, $$3);
                return;
            } catch (Throwable th) {
                LOGGER.warn("Could not spawn particle effect {}", $$0.getParticle());
                return;
            }
        }
        for (int $$5 = 0; $$5 < $$0.getCount(); $$5++) {
            double $$6 = this.random.nextGaussian() * ((double) $$0.getXDist());
            double $$7 = this.random.nextGaussian() * ((double) $$0.getYDist());
            double $$8 = this.random.nextGaussian() * ((double) $$0.getZDist());
            double $$9 = this.random.nextGaussian() * ((double) $$0.getMaxSpeed());
            double $$10 = this.random.nextGaussian() * ((double) $$0.getMaxSpeed());
            double $$11 = this.random.nextGaussian() * ((double) $$0.getMaxSpeed());
            try {
                this.level.addParticle($$0.getParticle(), $$0.isOverrideLimiter(), $$0.alwaysShow(), $$0.getX() + $$6, $$0.getY() + $$7, $$0.getZ() + $$8, $$9, $$10, $$11);
            } catch (Throwable th2) {
                LOGGER.warn("Could not spawn particle effect {}", $$0.getParticle());
                return;
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleUpdateAttributes(ClientboundUpdateAttributesPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getEntityId());
        if ($$1 == null) {
            return;
        }
        if (!($$1 instanceof LivingEntity)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + String.valueOf($$1) + ")");
        }
        AttributeMap $$2 = ((LivingEntity) $$1).getAttributes();
        for (ClientboundUpdateAttributesPacket.AttributeSnapshot $$3 : $$0.getValues()) {
            AttributeInstance $$4 = $$2.getInstance($$3.attribute());
            if ($$4 == null) {
                LOGGER.warn("Entity {} does not have attribute {}", $$1, $$3.attribute().getRegisteredName());
            } else {
                $$4.setBaseValue($$3.base());
                $$4.removeModifiers();
                for (AttributeModifier $$5 : $$3.modifiers()) {
                    $$4.addTransientModifier($$5);
                }
            }
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handlePlaceRecipe(ClientboundPlaceGhostRecipePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        AbstractContainerMenu $$1 = this.minecraft.player.containerMenu;
        if ($$1.containerId != $$0.containerId()) {
            return;
        }
        GuiEventListener guiEventListener = this.minecraft.screen;
        if (guiEventListener instanceof RecipeUpdateListener) {
            RecipeUpdateListener $$2 = (RecipeUpdateListener) guiEventListener;
            $$2.fillGhostRecipe($$0.recipeDisplay());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleLightUpdatePacket(ClientboundLightUpdatePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        int $$1 = $$0.getX();
        int $$2 = $$0.getZ();
        ClientboundLightUpdatePacketData $$3 = $$0.getLightData();
        this.level.queueLightUpdate(() -> {
            applyLightData($$1, $$2, $$3, true);
        });
    }

    private void applyLightData(int $$0, int $$1, ClientboundLightUpdatePacketData $$2, boolean $$3) {
        LevelLightEngine $$4 = this.level.getChunkSource().getLightEngine();
        BitSet $$5 = $$2.getSkyYMask();
        BitSet $$6 = $$2.getEmptySkyYMask();
        Iterator<byte[]> $$7 = $$2.getSkyUpdates().iterator();
        readSectionList($$0, $$1, $$4, LightLayer.SKY, $$5, $$6, $$7, $$3);
        BitSet $$8 = $$2.getBlockYMask();
        BitSet $$9 = $$2.getEmptyBlockYMask();
        Iterator<byte[]> $$10 = $$2.getBlockUpdates().iterator();
        readSectionList($$0, $$1, $$4, LightLayer.BLOCK, $$8, $$9, $$10, $$3);
        $$4.setLightEnabled(new ChunkPos($$0, $$1), true);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleMerchantOffers(ClientboundMerchantOffersPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        AbstractContainerMenu $$1 = this.minecraft.player.containerMenu;
        if ($$0.getContainerId() == $$1.containerId && ($$1 instanceof MerchantMenu)) {
            MerchantMenu $$2 = (MerchantMenu) $$1;
            $$2.setOffers($$0.getOffers());
            $$2.setXp($$0.getVillagerXp());
            $$2.setMerchantLevel($$0.getVillagerLevel());
            $$2.setShowProgressBar($$0.showProgress());
            $$2.setCanRestock($$0.canRestock());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetChunkCacheRadius(ClientboundSetChunkCacheRadiusPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.serverChunkRadius = $$0.getRadius();
        this.minecraft.options.setServerRenderDistance(this.serverChunkRadius);
        this.level.getChunkSource().updateViewRadius($$0.getRadius());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetSimulationDistance(ClientboundSetSimulationDistancePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.serverSimulationDistance = $$0.simulationDistance();
        this.level.setServerSimulationDistance(this.serverSimulationDistance);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleSetChunkCacheCenter(ClientboundSetChunkCacheCenterPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.getChunkSource().updateViewCenter($$0.getX(), $$0.getZ());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBlockChangedAck(ClientboundBlockChangedAckPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.level.handleBlockChangedAck($$0.sequence());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleBundlePacket(ClientboundBundlePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        for (Packet<? super ClientGamePacketListener> $$1 : $$0.subPackets()) {
            $$1.handle(this);
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleProjectilePowerPacket(ClientboundProjectilePowerPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.getId());
        if ($$1 instanceof AbstractHurtingProjectile) {
            AbstractHurtingProjectile $$2 = (AbstractHurtingProjectile) $$1;
            $$2.accelerationPower = $$0.getAccelerationPower();
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleChunkBatchStart(ClientboundChunkBatchStartPacket $$0) {
        this.chunkBatchSizeCalculator.onBatchStart();
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleChunkBatchFinished(ClientboundChunkBatchFinishedPacket $$0) {
        this.chunkBatchSizeCalculator.onBatchFinished($$0.batchSize());
        send(new ServerboundChunkBatchReceivedPacket(this.chunkBatchSizeCalculator.getDesiredChunksPerTick()));
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDebugSample(ClientboundDebugSamplePacket $$0) {
        this.minecraft.getDebugOverlay().logRemoteSample($$0.sample(), $$0.debugSampleType());
    }

    @Override // net.minecraft.network.protocol.ping.ClientPongPacketListener
    public void handlePongResponse(ClientboundPongResponsePacket $$0) {
        this.pingDebugMonitor.onPongReceived($$0);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleTestInstanceBlockStatus(ClientboundTestInstanceBlockStatus $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Screen screen = this.minecraft.screen;
        if (screen instanceof TestInstanceBlockEditScreen) {
            TestInstanceBlockEditScreen $$1 = (TestInstanceBlockEditScreen) screen;
            $$1.setStatus($$0.status(), $$0.size());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleWaypoint(ClientboundTrackedWaypointPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        $$0.apply(this.waypointManager);
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDebugChunkValue(ClientboundDebugChunkValuePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.debugSubscriber.updateChunk(this.level.getGameTime(), $$0.chunkPos(), $$0.update());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDebugBlockValue(ClientboundDebugBlockValuePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.debugSubscriber.updateBlock(this.level.getGameTime(), $$0.blockPos(), $$0.update());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDebugEntityValue(ClientboundDebugEntityValuePacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        Entity $$1 = this.level.getEntity($$0.entityId());
        if ($$1 != null) {
            this.debugSubscriber.updateEntity(this.level.getGameTime(), $$1, $$0.update());
        }
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleDebugEvent(ClientboundDebugEventPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.debugSubscriber.pushEvent(this.level.getGameTime(), $$0.event());
    }

    @Override // net.minecraft.network.protocol.game.ClientGamePacketListener
    public void handleGameTestHighlightPos(ClientboundGameTestHighlightPosPacket $$0) {
        PacketUtils.ensureRunningOnSameThread($$0, this, this.minecraft.packetProcessor());
        this.minecraft.levelRenderer.gameTestBlockHighlightRenderer.highlightPos($$0.absolutePos(), $$0.relativePos());
    }

    private void readSectionList(int $$0, int $$1, LevelLightEngine $$2, LightLayer $$3, BitSet $$4, BitSet $$5, Iterator<byte[]> $$6, boolean $$7) {
        for (int $$8 = 0; $$8 < $$2.getLightSectionCount(); $$8++) {
            int $$9 = $$2.getMinLightSection() + $$8;
            boolean $$10 = $$4.get($$8);
            boolean $$11 = $$5.get($$8);
            if ($$10 || $$11) {
                $$2.queueSectionData($$3, SectionPos.of($$0, $$9, $$1), $$10 ? new DataLayer((byte[]) $$6.next().clone()) : new DataLayer());
                if ($$7) {
                    this.level.setSectionDirtyWithNeighbors($$0, $$9, $$1);
                }
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override // net.minecraft.network.PacketListener
    public boolean isAcceptingMessages() {
        return this.connection.isConnected() && !this.closed;
    }

    public Collection<PlayerInfo> getListedOnlinePlayers() {
        return this.listedPlayers;
    }

    public Collection<PlayerInfo> getOnlinePlayers() {
        return this.playerInfoMap.values();
    }

    public Collection<UUID> getOnlinePlayerIds() {
        return this.playerInfoMap.keySet();
    }

    public PlayerInfo getPlayerInfo(UUID $$0) {
        return this.playerInfoMap.get($$0);
    }

    public PlayerInfo getPlayerInfo(String $$0) {
        for (PlayerInfo $$1 : this.playerInfoMap.values()) {
            if ($$1.getProfile().name().equals($$0)) {
                return $$1;
            }
        }
        return null;
    }

    public Map<UUID, PlayerInfo> getSeenPlayers() {
        return this.seenPlayers;
    }

    public PlayerInfo getPlayerInfoIgnoreCase(String $$0) {
        for (PlayerInfo $$1 : this.playerInfoMap.values()) {
            if ($$1.getProfile().name().equalsIgnoreCase($$0)) {
                return $$1;
            }
        }
        return null;
    }

    public GameProfile getLocalGameProfile() {
        return this.localGameProfile;
    }

    public ClientAdvancements getAdvancements() {
        return this.advancements;
    }

    public CommandDispatcher<ClientSuggestionProvider> getCommands() {
        return this.commands;
    }

    public ClientLevel getLevel() {
        return this.level;
    }

    public DebugQueryHandler getDebugQueryHandler() {
        return this.debugQueryHandler;
    }

    public UUID getId() {
        return this.id;
    }

    public Set<ResourceKey<Level>> levels() {
        return this.levels;
    }

    public RegistryAccess.Frozen registryAccess() {
        return this.registryAccess;
    }

    public void markMessageAsProcessed(MessageSignature $$0, boolean $$1) {
        if (this.lastSeenMessages.addPending($$0, $$1) && this.lastSeenMessages.offset() > 64) {
            sendChatAcknowledgement();
        }
    }

    private void sendChatAcknowledgement() {
        int $$0 = this.lastSeenMessages.getAndClearOffset();
        if ($$0 > 0) {
            send(new ServerboundChatAckPacket($$0));
        }
    }

    public void sendChat(String $$0) {
        Instant $$1 = Instant.now();
        long $$2 = Crypt.SaltSupplier.getLong();
        LastSeenMessagesTracker.Update $$3 = this.lastSeenMessages.generateAndApplyUpdate();
        MessageSignature $$4 = this.signedMessageEncoder.pack(new SignedMessageBody($$0, $$1, $$2, $$3.lastSeen()));
        send(new ServerboundChatPacket($$0, $$1, $$2, $$4, $$3.update()));
    }

    public void sendCommand(String $$0) {
        SignableCommand<ClientSuggestionProvider> $$1 = SignableCommand.of(this.commands.parse($$0, this.suggestionsProvider));
        if ($$1.arguments().isEmpty()) {
            send(new ServerboundChatCommandPacket($$0));
            return;
        }
        Instant $$2 = Instant.now();
        long $$3 = Crypt.SaltSupplier.getLong();
        LastSeenMessagesTracker.Update $$4 = this.lastSeenMessages.generateAndApplyUpdate();
        ArgumentSignatures $$5 = ArgumentSignatures.signCommand($$1, $$32 -> {
            SignedMessageBody $$42 = new SignedMessageBody($$32, $$2, $$3, $$4.lastSeen());
            return this.signedMessageEncoder.pack($$42);
        });
        send(new ServerboundChatCommandSignedPacket($$0, $$2, $$3, $$5, $$4.update()));
    }

    public void sendUnattendedCommand(String $$0, Screen $$1) {
        switch (verifyCommand($$0)) {
            case NO_ISSUES:
                send(new ServerboundChatCommandPacket($$0));
                this.minecraft.setScreen($$1);
                break;
            case PARSE_ERRORS:
                openCommandSendConfirmationWindow($$0, "multiplayer.confirm_command.parse_errors", $$1);
                break;
            case SIGNATURE_REQUIRED:
                openSignedCommandSendConfirmationWindow($$0, "multiplayer.confirm_command.signature_required", $$1);
                break;
            case PERMISSIONS_REQUIRED:
                openCommandSendConfirmationWindow($$0, "multiplayer.confirm_command.permissions_required", $$1);
                break;
        }
    }

    private CommandCheckResult verifyCommand(String $$0) {
        ParseResults<ClientSuggestionProvider> $$1 = this.commands.parse($$0, this.suggestionsProvider);
        if (!isValidCommand($$1)) {
            return CommandCheckResult.PARSE_ERRORS;
        }
        if (SignableCommand.hasSignableArguments($$1)) {
            return CommandCheckResult.SIGNATURE_REQUIRED;
        }
        ParseResults<ClientSuggestionProvider> $$2 = this.commands.parse($$0, this.restrictedSuggestionsProvider);
        if (!isValidCommand($$2)) {
            return CommandCheckResult.PERMISSIONS_REQUIRED;
        }
        return CommandCheckResult.NO_ISSUES;
    }

    private static boolean isValidCommand(ParseResults<?> $$0) {
        return ($$0.getReader().canRead() || !$$0.getExceptions().isEmpty() || $$0.getContext().getLastChild().getCommand() == null) ? false : true;
    }

    private void openSendConfirmationWindow(String $$0, String $$1, Component $$2, Runnable $$3) {
        Screen $$4 = this.minecraft.screen;
        this.minecraft.setScreen(new ConfirmScreen($$22 -> {
            if ($$22) {
                $$3.run();
            } else {
                this.minecraft.setScreen($$4);
            }
        }, COMMAND_SEND_CONFIRM_TITLE, Component.translatable($$1, Component.literal($$0).withStyle(ChatFormatting.YELLOW)), $$2, $$4 != null ? CommonComponents.GUI_BACK : CommonComponents.GUI_CANCEL));
    }

    private void openCommandSendConfirmationWindow(String $$0, String $$1, Screen $$2) {
        openSendConfirmationWindow($$0, $$1, BUTTON_RUN_COMMAND, () -> {
            send(new ServerboundChatCommandPacket($$0));
            this.minecraft.setScreen($$2);
        });
    }

    private void openSignedCommandSendConfirmationWindow(String $$0, String $$1, Screen $$2) {
        boolean $$3 = $$2 == null && this.minecraft.getChatStatus().isChatAllowed(this.minecraft.isLocalServer());
        openSendConfirmationWindow($$0, $$1, $$3 ? BUTTON_SUGGEST_COMMAND : CommonComponents.GUI_COPY_TO_CLIPBOARD, () -> {
            if ($$3) {
                this.minecraft.openChatScreen(ChatComponent.ChatMethod.COMMAND);
                Screen $$32 = this.minecraft.screen;
                if ($$32 instanceof ChatScreen) {
                    ChatScreen $$4 = (ChatScreen) $$32;
                    $$4.insertText($$0, false);
                    return;
                }
                return;
            }
            this.minecraft.keyboardHandler.setClipboard("/" + $$0);
            this.minecraft.setScreen($$2);
        });
    }

    public void broadcastClientInformation(ClientInformation $$0) {
        if (!$$0.equals(this.remoteClientInformation)) {
            send(new ServerboundClientInformationPacket($$0));
            this.remoteClientInformation = $$0;
        }
    }

    @Override // net.minecraft.network.TickablePacketListener
    public void tick() {
        if (this.chatSession != null && this.minecraft.getProfileKeyPairManager().shouldRefreshKeyPair()) {
            prepareKeyPair();
        }
        if (this.keyPairFuture != null && this.keyPairFuture.isDone()) {
            this.keyPairFuture.join().ifPresent(this::setKeyPair);
            this.keyPairFuture = null;
        }
        sendDeferredPackets();
        if (this.minecraft.getDebugOverlay().showNetworkCharts()) {
            this.pingDebugMonitor.tick();
        }
        if (this.level != null) {
            this.debugSubscriber.tick(this.level.getGameTime());
        }
        this.telemetryManager.tick();
        if (this.levelLoadTracker != null) {
            this.levelLoadTracker.tickClientLoad();
            if (this.levelLoadTracker.isLevelReady()) {
                notifyPlayerLoaded();
                this.levelLoadTracker = null;
            }
        }
    }

    private void notifyPlayerLoaded() {
        if (!hasClientLoaded()) {
            this.connection.send(new ServerboundPlayerLoadedPacket());
            setClientLoaded(true);
        }
    }

    public void prepareKeyPair() {
        this.keyPairFuture = this.minecraft.getProfileKeyPairManager().prepareKeyPair();
    }

    private void setKeyPair(ProfileKeyPair $$0) {
        if (!this.minecraft.isLocalPlayer(this.localGameProfile.id())) {
            return;
        }
        if (this.chatSession != null && this.chatSession.keyPair().equals($$0)) {
            return;
        }
        this.chatSession = LocalChatSession.create($$0);
        this.signedMessageEncoder = this.chatSession.createMessageEncoder(this.localGameProfile.id());
        send(new ServerboundChatSessionUpdatePacket(this.chatSession.asRemote().asData()));
    }

    @Override // net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl
    protected DialogConnectionAccess createDialogAccess() {
        return new ClientCommonPacketListenerImpl.CommonDialogAccess() { // from class: net.minecraft.client.multiplayer.ClientPacketListener.2
            @Override // net.minecraft.client.gui.screens.dialog.DialogConnectionAccess
            public void runCommand(String $$0, Screen $$1) {
                ClientPacketListener.this.sendUnattendedCommand($$0, $$1);
            }
        };
    }

    public ServerData getServerData() {
        return this.serverData;
    }

    public FeatureFlagSet enabledFeatures() {
        return this.enabledFeatures;
    }

    public boolean isFeatureEnabled(FeatureFlagSet $$0) {
        return $$0.isSubsetOf(enabledFeatures());
    }

    public Scoreboard scoreboard() {
        return this.scoreboard;
    }

    public PotionBrewing potionBrewing() {
        return this.potionBrewing;
    }

    public FuelValues fuelValues() {
        return this.fuelValues;
    }

    public void updateSearchTrees() {
        this.searchTrees.rebuildAfterLanguageChange();
    }

    public SessionSearchTrees searchTrees() {
        return this.searchTrees;
    }

    public void registerForCleaning(CacheSlot<?, ?> $$0) {
        this.cacheSlots.add(new WeakReference<>($$0));
    }

    public HashedPatchMap.HashGenerator decoratedHashOpsGenenerator() {
        return this.decoratedHashOpsGenerator;
    }

    public ClientWaypointManager getWaypointManager() {
        return this.waypointManager;
    }

    public DebugValueAccess createDebugValueAccess() {
        return this.debugSubscriber.createDebugValueAccess(this.level);
    }

    public boolean hasClientLoaded() {
        return this.clientLoaded;
    }

    private void setClientLoaded(boolean $$0) {
        this.clientLoaded = $$0;
    }
}
