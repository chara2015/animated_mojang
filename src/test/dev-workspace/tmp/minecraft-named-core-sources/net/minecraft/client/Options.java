package net.minecraft.client;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.input.InputQuirks;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.SoundPreviewHandler;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.levelgen.Density;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/Options.class */
public class Options {
    public static final int RENDER_DISTANCE_SHORT = 4;
    public static final int RENDER_DISTANCE_FAR = 12;
    public static final int RENDER_DISTANCE_REALLY_FAR = 16;
    public static final int RENDER_DISTANCE_EXTREME = 32;
    public static final String DEFAULT_SOUND_DEVICE = "";
    private final OptionInstance<Integer> renderDistance;
    private final OptionInstance<Integer> simulationDistance;
    public static final int UNLIMITED_FRAMERATE_CUTOFF = 260;
    private boolean isApplyingGraphicsPreset;
    private static final int BLURRINESS_DEFAULT_VALUE = 5;
    private final OptionInstance<Boolean> narratorHotkey;
    public String fullscreenVideoModeString;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus;
    private final Set<PlayerModelPart> modelParts;
    private final OptionInstance<HumanoidArm> mainHand;
    public int overrideWidth;
    public int overrideHeight;
    private final OptionInstance<Double> chatScale;
    private final OptionInstance<Double> chatWidth;
    private final OptionInstance<Double> chatHeightUnfocused;
    private final OptionInstance<Double> chatHeightFocused;
    private final OptionInstance<Double> chatDelay;
    private final OptionInstance<Double> notificationDisplayTime;
    private final OptionInstance<Integer> mipmapLevels;
    private final OptionInstance<Integer> maxAnisotropyBit;
    private final OptionInstance<TextureFilteringMethod> textureFiltering;
    private boolean useNativeTransport;
    private final OptionInstance<AttackIndicatorStatus> attackIndicator;
    public TutorialSteps tutorialStep;
    public boolean joinedFirstServer;
    private final OptionInstance<Integer> biomeBlendRadius;
    private final OptionInstance<Double> mouseWheelSensitivity;
    private final OptionInstance<Boolean> rawMouseInput;
    private final OptionInstance<Boolean> allowCursorChanges;
    public int glDebugVerbosity;
    private final OptionInstance<Boolean> autoJump;
    private final OptionInstance<Boolean> rotateWithMinecart;
    private final OptionInstance<Boolean> operatorItemsTab;
    private final OptionInstance<Boolean> autoSuggestions;
    private final OptionInstance<Boolean> chatColors;
    private final OptionInstance<Boolean> chatLinks;
    private final OptionInstance<Boolean> chatLinksPrompt;
    private final OptionInstance<Boolean> enableVsync;
    private final OptionInstance<Boolean> entityShadows;
    private final OptionInstance<Boolean> forceUnicodeFont;
    private final OptionInstance<Boolean> japaneseGlyphVariants;
    private final OptionInstance<Boolean> invertXMouse;
    private final OptionInstance<Boolean> invertYMouse;
    private final OptionInstance<Boolean> discreteMouseScroll;
    private final OptionInstance<Boolean> realmsNotifications;
    private final OptionInstance<Boolean> allowServerListing;
    private final OptionInstance<Boolean> reducedDebugInfo;
    private final Map<SoundSource, OptionInstance<Double>> soundSourceVolumes;
    private final OptionInstance<Boolean> showSubtitles;
    private final OptionInstance<Boolean> directionalAudio;
    private final OptionInstance<Boolean> backgroundForChatOnly;
    private final OptionInstance<Boolean> touchscreen;
    private final OptionInstance<Boolean> fullscreen;
    private final OptionInstance<Boolean> bobView;
    private final OptionInstance<Boolean> toggleCrouch;
    private final OptionInstance<Boolean> toggleSprint;
    private final OptionInstance<Boolean> toggleAttack;
    private final OptionInstance<Boolean> toggleUse;
    private final OptionInstance<Integer> sprintWindow;
    public boolean skipMultiplayerWarning;
    private final OptionInstance<Boolean> hideMatchedNames;
    private final OptionInstance<Boolean> showAutosaveIndicator;
    private final OptionInstance<Boolean> onlyShowSecureChat;
    private final OptionInstance<Boolean> saveChatDrafts;
    public final KeyMapping keyUp;
    public final KeyMapping keyLeft;
    public final KeyMapping keyDown;
    public final KeyMapping keyRight;
    public final KeyMapping keyJump;
    public final KeyMapping keyShift;
    public final KeyMapping keySprint;
    public final KeyMapping keyInventory;
    public final KeyMapping keySwapOffhand;
    public final KeyMapping keyDrop;
    public final KeyMapping keyUse;
    public final KeyMapping keyAttack;
    public final KeyMapping keyPickItem;
    public final KeyMapping keyChat;
    public final KeyMapping keyPlayerList;
    public final KeyMapping keyCommand;
    public final KeyMapping keySocialInteractions;
    public final KeyMapping keyScreenshot;
    public final KeyMapping keyTogglePerspective;
    public final KeyMapping keySmoothCamera;
    public final KeyMapping keyFullscreen;
    public final KeyMapping keyAdvancements;
    public final KeyMapping keyQuickActions;
    public final KeyMapping keyToggleGui;
    public final KeyMapping keyToggleSpectatorShaderEffects;
    public final KeyMapping[] keyHotbarSlots;
    public final KeyMapping keySaveHotbarActivator;
    public final KeyMapping keyLoadHotbarActivator;
    public final KeyMapping keySpectatorOutlines;
    public final KeyMapping keySpectatorHotbar;
    public final KeyMapping keyDebugOverlay;
    public final KeyMapping keyDebugModifier;
    public final KeyMapping keyDebugCrash;
    public final KeyMapping keyDebugReloadChunk;
    public final KeyMapping keyDebugShowHitboxes;
    public final KeyMapping keyDebugClearChat;
    public final KeyMapping keyDebugShowChunkBorders;
    public final KeyMapping keyDebugShowAdvancedTooltips;
    public final KeyMapping keyDebugCopyRecreateCommand;
    public final KeyMapping keyDebugSpectate;
    public final KeyMapping keyDebugSwitchGameMode;
    public final KeyMapping keyDebugDebugOptions;
    public final KeyMapping keyDebugFocusPause;
    public final KeyMapping keyDebugDumpDynamicTextures;
    public final KeyMapping keyDebugReloadResourcePacks;
    public final KeyMapping keyDebugProfiling;
    public final KeyMapping keyDebugCopyLocation;
    public final KeyMapping keyDebugDumpVersion;
    public final KeyMapping keyDebugPofilingChart;
    public final KeyMapping keyDebugFpsCharts;
    public final KeyMapping keyDebugNetworkCharts;
    public final KeyMapping[] debugKeys;
    public final KeyMapping[] keyMappings;
    protected Minecraft minecraft;
    private final File optionsFile;
    public boolean hideGui;
    private CameraType cameraType;
    public String lastMpIp;
    public boolean smoothCamera;
    private final OptionInstance<Integer> fov;
    private final OptionInstance<Boolean> telemetryOptInExtra;
    private final OptionInstance<Double> screenEffectScale;
    private final OptionInstance<Double> fovEffectScale;
    private final OptionInstance<Double> darknessEffectScale;
    private final OptionInstance<Double> glintSpeed;
    private final OptionInstance<Double> glintStrength;
    private final OptionInstance<Double> damageTiltStrength;
    private final OptionInstance<Double> gamma;
    public static final int AUTO_GUI_SCALE = 0;
    private static final int MAX_GUI_SCALE_INCLUSIVE = 2147483646;
    private final OptionInstance<Integer> guiScale;
    private final OptionInstance<ParticleStatus> particles;
    private final OptionInstance<NarratorStatus> narrator;
    public String languageCode;
    private final OptionInstance<String> soundDevice;
    public boolean onboardAccessibility;
    private final OptionInstance<MusicManager.MusicFrequency> musicFrequency;
    private final OptionInstance<MusicToastDisplayState> musicToast;
    public boolean syncWrites;
    public boolean startedCleanly;
    static final Logger LOGGER = LogUtils.getLogger();
    static final Gson GSON = new Gson();
    private static final TypeToken<List<String>> LIST_OF_STRINGS_TYPE = new TypeToken<List<String>>() { // from class: net.minecraft.client.Options.1
    };
    private static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);
    private static final Component ACCESSIBILITY_TOOLTIP_DARK_MOJANG_BACKGROUND = Component.translatable("options.darkMojangStudiosBackgroundColor.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_HIDE_LIGHTNING_FLASHES = Component.translatable("options.hideLightningFlashes.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_HIDE_SPLASH_TEXTS = Component.translatable("options.hideSplashTexts.tooltip");
    private static final Component INACTIVITY_FPS_LIMIT_TOOLTIP_MINIMIZED = Component.translatable("options.inactivityFpsLimit.minimized.tooltip");
    private static final Component INACTIVITY_FPS_LIMIT_TOOLTIP_AFK = Component.translatable("options.inactivityFpsLimit.afk.tooltip");
    private static final Component GRAPHICS_TOOLTIP_WEATHER_RADIUS = Component.translatable("options.weatherRadius.tooltip");
    private static final Component GRAPHICS_TOOLTIP_CUTOUT_LEAVES = Component.translatable("options.cutoutLeaves.tooltip");
    private static final Component GRAPHICS_TOOLTIP_VIGNETTE = Component.translatable("options.vignette.tooltip");
    private static final Component GRAPHICS_TOOLTIP_IMPROVED_TRANSPARENCY = Component.translatable("options.improvedTransparency.tooltip");
    private static final Component GRAPHICS_TOOLTIP_CHUNK_FADE = Component.translatable("options.chunkFade.tooltip");
    private static final Component PRIORITIZE_CHUNK_TOOLTIP_NONE = Component.translatable("options.prioritizeChunkUpdates.none.tooltip");
    private static final Component PRIORITIZE_CHUNK_TOOLTIP_PLAYER_AFFECTED = Component.translatable("options.prioritizeChunkUpdates.byPlayer.tooltip");
    private static final Component PRIORITIZE_CHUNK_TOOLTIP_NEARBY = Component.translatable("options.prioritizeChunkUpdates.nearby.tooltip");
    private static final Component MENU_BACKGROUND_BLURRINESS_TOOLTIP = Component.translatable("options.accessibility.menu_background_blurriness.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_CONTRAST_MODE = Component.translatable("options.accessibility.high_contrast.tooltip");
    private static final Component HIGH_CONTRAST_BLOCK_OUTLINE_TOOLTIP = Component.translatable("options.accessibility.high_contrast_block_outline.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_NOTIFICATION_DISPLAY_TIME = Component.translatable("options.notifications.display_time.tooltip");
    private static final Component GRAPHICS_TOOLTIP_ANISOTROPIC_FILTERING = Component.translatable("options.maxAnisotropy.tooltip");
    private static final Component FILTERING_NONE_TOOLTIP = Component.translatable("options.textureFiltering.none.tooltip");
    private static final Component FILTERING_RGSS_TOOLTIP = Component.translatable("options.textureFiltering.rgss.tooltip");
    private static final Component FILTERING_ANISOTROPIC_TOOLTIP = Component.translatable("options.textureFiltering.anisotropic.tooltip");
    private static final Component ALLOW_CURSOR_CHANGES_TOOLTIP = Component.translatable("options.allowCursorChanges.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_ROTATE_WITH_MINECART = Component.translatable("options.rotateWithMinecart.tooltip");
    private static final Component REALMS_NOTIFICATIONS_TOOLTIP = Component.translatable("options.realmsNotifications.tooltip");
    private static final Component ALLOW_SERVER_LISTING_TOOLTIP = Component.translatable("options.allowServerListing.tooltip");
    private static final Component CLOSED_CAPTIONS_TOOLTIP = Component.translatable("options.showSubtitles.tooltip");
    private static final Component DIRECTIONAL_AUDIO_TOOLTIP_ON = Component.translatable("options.directionalAudio.on.tooltip");
    private static final Component DIRECTIONAL_AUDIO_TOOLTIP_OFF = Component.translatable("options.directionalAudio.off.tooltip");
    private static final Component KEY_TOGGLE = Component.translatable("options.key.toggle");
    private static final Component KEY_HOLD = Component.translatable("options.key.hold");
    private static final Component SPRINT_WINDOW_TOOLTIP = Component.translatable("options.sprintWindow.tooltip");
    private static final Component CHAT_TOOLTIP_HIDE_MATCHED_NAMES = Component.translatable("options.hideMatchedNames.tooltip");
    private static final Component CHAT_TOOLTIP_ONLY_SHOW_SECURE = Component.translatable("options.onlyShowSecureChat.tooltip");
    private static final Component CHAT_TOOLTIP_SAVE_DRAFTS = Component.translatable("options.chat.drafts.tooltip");
    private static final Component TELEMETRY_TOOLTIP = Component.translatable("options.telemetry.button.tooltip", Component.translatable("options.telemetry.state.minimal"), Component.translatable("options.telemetry.state.all"));
    private static final Component ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT = Component.translatable("options.screenEffectScale.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_FOV_EFFECT = Component.translatable("options.fovEffectScale.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_DARKNESS_EFFECT = Component.translatable("options.darknessEffectScale.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_GLINT_SPEED = Component.translatable("options.glintSpeed.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_GLINT_STRENGTH = Component.translatable("options.glintStrength.tooltip");
    private static final Component ACCESSIBILITY_TOOLTIP_DAMAGE_TILT_STRENGTH = Component.translatable("options.damageTiltStrength.tooltip");
    private static final Component MUSIC_FREQUENCY_TOOLTIP = Component.translatable("options.music_frequency.tooltip");
    private final OptionInstance<Boolean> darkMojangStudiosBackground = OptionInstance.createBoolean("options.darkMojangStudiosBackgroundColor", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_DARK_MOJANG_BACKGROUND), false);
    private final OptionInstance<Boolean> hideLightningFlash = OptionInstance.createBoolean("options.hideLightningFlashes", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_HIDE_LIGHTNING_FLASHES), false);
    private final OptionInstance<Boolean> hideSplashTexts = OptionInstance.createBoolean("options.hideSplashTexts", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_HIDE_SPLASH_TEXTS), false);
    private final OptionInstance<Double> sensitivity = new OptionInstance<>("options.sensitivity", OptionInstance.noTooltip(), ($$0, $$1) -> {
        if ($$1.doubleValue() == Density.SURFACE) {
            return genericValueLabel($$0, Component.translatable("options.sensitivity.min"));
        }
        if ($$1.doubleValue() == 1.0d) {
            return genericValueLabel($$0, Component.translatable("options.sensitivity.max"));
        }
        return percentValueLabel($$0, 2.0d * $$1.doubleValue());
    }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5d), $$02 -> {
    });
    private int serverRenderDistance = 0;
    private final OptionInstance<Double> entityDistanceScaling = new OptionInstance<>("options.entityDistanceScaling", OptionInstance.noTooltip(), (v0, v1) -> {
        return percentValueLabel(v0, v1);
    }, new OptionInstance.IntRange(2, 20).xmap($$0 -> {
        return Double.valueOf(((double) $$0) / 4.0d);
    }, $$02 -> {
        return (int) ($$02.doubleValue() * 4.0d);
    }, true), Codec.doubleRange(0.5d, 5.0d), Double.valueOf(1.0d), $$03 -> {
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Integer> framerateLimit = new OptionInstance<>("options.framerateLimit", OptionInstance.noTooltip(), ($$0, $$1) -> {
        if ($$1.intValue() == 260) {
            return genericValueLabel($$0, Component.translatable("options.framerateLimit.max"));
        }
        return genericValueLabel($$0, Component.translatable("options.framerate", $$1));
    }, new OptionInstance.IntRange(1, 26).xmap($$02 -> {
        return Integer.valueOf($$02 * 10);
    }, $$03 -> {
        return $$03.intValue() / 10;
    }, true), Codec.intRange(10, 260), 120, $$04 -> {
        Minecraft.getInstance().getFramerateLimitTracker().setFramerateLimit($$04.intValue());
    });
    private final OptionInstance<GraphicsPreset> graphicsPreset = new OptionInstance<>("options.graphics.preset", OptionInstance.cachedConstantTooltip(Component.translatable("options.graphics.preset.tooltip")), ($$0, $$1) -> {
        return genericValueLabel($$0, Component.translatable($$1.getKey()));
    }, new OptionInstance.SliderableEnum(List.of((Object[]) GraphicsPreset.values()), GraphicsPreset.CODEC), GraphicsPreset.CODEC, GraphicsPreset.FANCY, this::applyGraphicsPreset);
    private final OptionInstance<InactivityFpsLimit> inactivityFpsLimit = new OptionInstance<>("options.inactivityFpsLimit", $$0 -> {
        switch ($$0) {
            case MINIMIZED:
                return Tooltip.create(INACTIVITY_FPS_LIMIT_TOOLTIP_MINIMIZED);
            case AFK:
                return Tooltip.create(INACTIVITY_FPS_LIMIT_TOOLTIP_AFK);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }, ($$02, $$1) -> {
        return $$1.caption();
    }, new OptionInstance.Enum(Arrays.asList(InactivityFpsLimit.values()), InactivityFpsLimit.CODEC), InactivityFpsLimit.AFK, $$03 -> {
    });
    private final OptionInstance<CloudStatus> cloudStatus = new OptionInstance<>("options.renderClouds", OptionInstance.noTooltip(), ($$0, $$1) -> {
        return $$1.caption();
    }, new OptionInstance.Enum(Arrays.asList(CloudStatus.values()), Codec.withAlternative(CloudStatus.CODEC, Codec.BOOL, $$02 -> {
        return $$02.booleanValue() ? CloudStatus.FANCY : CloudStatus.OFF;
    })), CloudStatus.FANCY, $$03 -> {
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Integer> cloudRange = new OptionInstance<>("options.renderCloudsDistance", OptionInstance.noTooltip(), ($$0, $$1) -> {
        return genericValueLabel($$0, Component.translatable("options.chunks", $$1));
    }, new OptionInstance.IntRange(2, 128, true), 128, $$02 -> {
        operateOnLevelRenderer($$02 -> {
            $$02.getCloudRenderer().markForRebuild();
        });
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Integer> weatherRadius = new OptionInstance<>("options.weatherRadius", OptionInstance.cachedConstantTooltip(GRAPHICS_TOOLTIP_WEATHER_RADIUS), ($$0, $$1) -> {
        return genericValueLabel($$0, Component.translatable("options.blocks", $$1));
    }, new OptionInstance.IntRange(3, 10, true), 10, $$02 -> {
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Boolean> cutoutLeaves = OptionInstance.createBoolean("options.cutoutLeaves", OptionInstance.cachedConstantTooltip(GRAPHICS_TOOLTIP_CUTOUT_LEAVES), true, $$0 -> {
        operateOnLevelRenderer((v0) -> {
            v0.allChanged();
        });
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Boolean> vignette = OptionInstance.createBoolean("options.vignette", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(GRAPHICS_TOOLTIP_VIGNETTE), true);
    private final OptionInstance<Boolean> improvedTransparency = OptionInstance.createBoolean("options.improvedTransparency", OptionInstance.cachedConstantTooltip(GRAPHICS_TOOLTIP_IMPROVED_TRANSPARENCY), false, $$0 -> {
        Minecraft $$1 = Minecraft.getInstance();
        GpuWarnlistManager $$2 = $$1.getGpuWarnlistManager();
        if ($$0.booleanValue() && $$2.willShowWarning()) {
            $$2.showWarning();
        } else {
            operateOnLevelRenderer((v0) -> {
                v0.allChanged();
            });
            setGraphicsPresetToCustom();
        }
    });
    private final OptionInstance<Boolean> ambientOcclusion = OptionInstance.createBoolean("options.ao", true, (Consumer<Boolean>) $$0 -> {
        operateOnLevelRenderer((v0) -> {
            v0.allChanged();
        });
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Double> chunkSectionFadeInTime = new OptionInstance<>("options.chunkFade", OptionInstance.cachedConstantTooltip(GRAPHICS_TOOLTIP_CHUNK_FADE), ($$0, $$1) -> {
        if ($$1.doubleValue() <= Density.SURFACE) {
            return Component.translatable("options.chunkFade.none");
        }
        return Component.translatable("options.chunkFade.seconds", String.format(Locale.ROOT, "%.2f", $$1));
    }, new OptionInstance.IntRange(0, 40).xmap($$02 -> {
        return Double.valueOf(((double) $$02) / 20.0d);
    }, $$03 -> {
        return (int) ($$03.doubleValue() * 20.0d);
    }, true), Codec.doubleRange(Density.SURFACE, 2.0d), Double.valueOf(0.75d), $$04 -> {
    });
    private final OptionInstance<PrioritizeChunkUpdates> prioritizeChunkUpdates = new OptionInstance<>("options.prioritizeChunkUpdates", $$0 -> {
        switch ($$0) {
            case NONE:
                return Tooltip.create(PRIORITIZE_CHUNK_TOOLTIP_NONE);
            case PLAYER_AFFECTED:
                return Tooltip.create(PRIORITIZE_CHUNK_TOOLTIP_PLAYER_AFFECTED);
            case NEARBY:
                return Tooltip.create(PRIORITIZE_CHUNK_TOOLTIP_NEARBY);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }, ($$02, $$1) -> {
        return $$1.caption();
    }, new OptionInstance.Enum(Arrays.asList(PrioritizeChunkUpdates.values()), PrioritizeChunkUpdates.LEGACY_CODEC), PrioritizeChunkUpdates.NONE, $$03 -> {
        setGraphicsPresetToCustom();
    });
    public List<String> resourcePacks = Lists.newArrayList();
    public List<String> incompatibleResourcePacks = Lists.newArrayList();
    private final OptionInstance<ChatVisiblity> chatVisibility = new OptionInstance<>("options.chat.visibility", OptionInstance.noTooltip(), ($$0, $$1) -> {
        return $$1.caption();
    }, new OptionInstance.Enum(Arrays.asList(ChatVisiblity.values()), ChatVisiblity.LEGACY_CODEC), ChatVisiblity.FULL, $$02 -> {
    });
    private final OptionInstance<Double> chatOpacity = new OptionInstance<>("options.chat.opacity", OptionInstance.noTooltip(), ($$0, $$1) -> {
        return percentValueLabel($$0, ($$1.doubleValue() * 0.9d) + 0.1d);
    }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$02 -> {
        Minecraft.getInstance().gui.getChat().rescaleChat();
    });
    private final OptionInstance<Double> chatLineSpacing = new OptionInstance<>("options.chat.line_spacing", OptionInstance.noTooltip(), (v0, v1) -> {
        return percentValueLabel(v0, v1);
    }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(Density.SURFACE), $$0 -> {
    });
    private final OptionInstance<Integer> menuBackgroundBlurriness = new OptionInstance<>("options.accessibility.menu_background_blurriness", OptionInstance.cachedConstantTooltip(MENU_BACKGROUND_BLURRINESS_TOOLTIP), (v0, v1) -> {
        return genericValueOrOffLabel(v0, v1);
    }, new OptionInstance.IntRange(0, 10), 5, $$0 -> {
        setGraphicsPresetToCustom();
    });
    private final OptionInstance<Double> textBackgroundOpacity = new OptionInstance<>("options.accessibility.text_background_opacity", OptionInstance.noTooltip(), (v0, v1) -> {
        return percentValueLabel(v0, v1);
    }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5d), $$0 -> {
        Minecraft.getInstance().gui.getChat().rescaleChat();
    });
    private final OptionInstance<Double> panoramaSpeed = new OptionInstance<>("options.accessibility.panorama_speed", OptionInstance.noTooltip(), (v0, v1) -> {
        return percentValueLabel(v0, v1);
    }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$0 -> {
    });
    private final OptionInstance<Boolean> highContrast = OptionInstance.createBoolean("options.accessibility.high_contrast", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_CONTRAST_MODE), false, $$0 -> {
        PackRepository $$1 = Minecraft.getInstance().getResourcePackRepository();
        boolean $$2 = $$1.getSelectedIds().contains(ClientPackSource.HIGH_CONTRAST_PACK);
        if (!$$2 && $$0.booleanValue()) {
            if ($$1.addPack(ClientPackSource.HIGH_CONTRAST_PACK)) {
                updateResourcePacks($$1);
            }
        } else if ($$2 && !$$0.booleanValue() && $$1.removePack(ClientPackSource.HIGH_CONTRAST_PACK)) {
            updateResourcePacks($$1);
        }
    });
    private final OptionInstance<Boolean> highContrastBlockOutline = OptionInstance.createBoolean("options.accessibility.high_contrast_block_outline", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(HIGH_CONTRAST_BLOCK_OUTLINE_TOOLTIP), false);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/Options$FieldAccess.class */
    interface FieldAccess extends OptionAccess {
        int process(String str, int i);

        boolean process(String str, boolean z);

        String process(String str, String str2);

        float process(String str, float f);

        <T> T process(String str, T t, Function<String, T> function, Function<T, String> function2);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/Options$OptionAccess.class */
    interface OptionAccess {
        <T> void process(String str, OptionInstance<T> optionInstance);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void operateOnLevelRenderer(Consumer<LevelRenderer> $$0) {
        LevelRenderer $$1 = Minecraft.getInstance().levelRenderer;
        if ($$1 != null) {
            $$0.accept($$1);
        }
    }

    public OptionInstance<Boolean> darkMojangStudiosBackground() {
        return this.darkMojangStudiosBackground;
    }

    public OptionInstance<Boolean> hideLightningFlash() {
        return this.hideLightningFlash;
    }

    public OptionInstance<Boolean> hideSplashTexts() {
        return this.hideSplashTexts;
    }

    public OptionInstance<Double> sensitivity() {
        return this.sensitivity;
    }

    public OptionInstance<Integer> renderDistance() {
        return this.renderDistance;
    }

    public OptionInstance<Integer> simulationDistance() {
        return this.simulationDistance;
    }

    public OptionInstance<Double> entityDistanceScaling() {
        return this.entityDistanceScaling;
    }

    public OptionInstance<Integer> framerateLimit() {
        return this.framerateLimit;
    }

    public void applyGraphicsPreset(GraphicsPreset $$0) {
        this.isApplyingGraphicsPreset = true;
        $$0.apply(this.minecraft);
        this.isApplyingGraphicsPreset = false;
    }

    public OptionInstance<GraphicsPreset> graphicsPreset() {
        return this.graphicsPreset;
    }

    public OptionInstance<InactivityFpsLimit> inactivityFpsLimit() {
        return this.inactivityFpsLimit;
    }

    public OptionInstance<CloudStatus> cloudStatus() {
        return this.cloudStatus;
    }

    public OptionInstance<Integer> cloudRange() {
        return this.cloudRange;
    }

    public OptionInstance<Integer> weatherRadius() {
        return this.weatherRadius;
    }

    public OptionInstance<Boolean> cutoutLeaves() {
        return this.cutoutLeaves;
    }

    public OptionInstance<Boolean> vignette() {
        return this.vignette;
    }

    public OptionInstance<Boolean> improvedTransparency() {
        return this.improvedTransparency;
    }

    public OptionInstance<Boolean> ambientOcclusion() {
        return this.ambientOcclusion;
    }

    public OptionInstance<Double> chunkSectionFadeInTime() {
        return this.chunkSectionFadeInTime;
    }

    public OptionInstance<PrioritizeChunkUpdates> prioritizeChunkUpdates() {
        return this.prioritizeChunkUpdates;
    }

    public void updateResourcePacks(PackRepository $$0) {
        ImmutableList immutableListCopyOf = ImmutableList.copyOf(this.resourcePacks);
        this.resourcePacks.clear();
        this.incompatibleResourcePacks.clear();
        for (Pack $$2 : $$0.getSelectedPacks()) {
            if (!$$2.isFixedPosition()) {
                this.resourcePacks.add($$2.getId());
                if (!$$2.getCompatibility().isCompatible()) {
                    this.incompatibleResourcePacks.add($$2.getId());
                }
            }
        }
        save();
        if (!ImmutableList.copyOf(this.resourcePacks).equals(immutableListCopyOf)) {
            this.minecraft.reloadResourcePacks();
        }
    }

    public OptionInstance<ChatVisiblity> chatVisibility() {
        return this.chatVisibility;
    }

    public OptionInstance<Double> chatOpacity() {
        return this.chatOpacity;
    }

    public OptionInstance<Double> chatLineSpacing() {
        return this.chatLineSpacing;
    }

    public OptionInstance<Integer> menuBackgroundBlurriness() {
        return this.menuBackgroundBlurriness;
    }

    public int getMenuBackgroundBlurriness() {
        return menuBackgroundBlurriness().get().intValue();
    }

    public OptionInstance<Double> textBackgroundOpacity() {
        return this.textBackgroundOpacity;
    }

    public OptionInstance<Double> panoramaSpeed() {
        return this.panoramaSpeed;
    }

    public OptionInstance<Boolean> highContrast() {
        return this.highContrast;
    }

    public OptionInstance<Boolean> highContrastBlockOutline() {
        return this.highContrastBlockOutline;
    }

    public OptionInstance<Boolean> narratorHotkey() {
        return this.narratorHotkey;
    }

    public OptionInstance<HumanoidArm> mainHand() {
        return this.mainHand;
    }

    public OptionInstance<Double> chatScale() {
        return this.chatScale;
    }

    public OptionInstance<Double> chatWidth() {
        return this.chatWidth;
    }

    public OptionInstance<Double> chatHeightUnfocused() {
        return this.chatHeightUnfocused;
    }

    public OptionInstance<Double> chatHeightFocused() {
        return this.chatHeightFocused;
    }

    public OptionInstance<Double> chatDelay() {
        return this.chatDelay;
    }

    public OptionInstance<Double> notificationDisplayTime() {
        return this.notificationDisplayTime;
    }

    public OptionInstance<Integer> mipmapLevels() {
        return this.mipmapLevels;
    }

    public OptionInstance<Integer> maxAnisotropyBit() {
        return this.maxAnisotropyBit;
    }

    public int maxAnisotropyValue() {
        return Math.min(1 << this.maxAnisotropyBit.get().intValue(), RenderSystem.getDevice().getMaxSupportedAnisotropy());
    }

    public OptionInstance<TextureFilteringMethod> textureFiltering() {
        return this.textureFiltering;
    }

    public OptionInstance<AttackIndicatorStatus> attackIndicator() {
        return this.attackIndicator;
    }

    public OptionInstance<Integer> biomeBlendRadius() {
        return this.biomeBlendRadius;
    }

    private static double logMouse(int $$0) {
        return Math.pow(10.0d, ((double) $$0) / 100.0d);
    }

    private static int unlogMouse(double $$0) {
        return Mth.floor(Math.log10($$0) * 100.0d);
    }

    public OptionInstance<Double> mouseWheelSensitivity() {
        return this.mouseWheelSensitivity;
    }

    public OptionInstance<Boolean> rawMouseInput() {
        return this.rawMouseInput;
    }

    public OptionInstance<Boolean> allowCursorChanges() {
        return this.allowCursorChanges;
    }

    public OptionInstance<Boolean> autoJump() {
        return this.autoJump;
    }

    public OptionInstance<Boolean> rotateWithMinecart() {
        return this.rotateWithMinecart;
    }

    public OptionInstance<Boolean> operatorItemsTab() {
        return this.operatorItemsTab;
    }

    public OptionInstance<Boolean> autoSuggestions() {
        return this.autoSuggestions;
    }

    public OptionInstance<Boolean> chatColors() {
        return this.chatColors;
    }

    public OptionInstance<Boolean> chatLinks() {
        return this.chatLinks;
    }

    public OptionInstance<Boolean> chatLinksPrompt() {
        return this.chatLinksPrompt;
    }

    public OptionInstance<Boolean> enableVsync() {
        return this.enableVsync;
    }

    public OptionInstance<Boolean> entityShadows() {
        return this.entityShadows;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateFontOptions() {
        Minecraft $$0 = Minecraft.getInstance();
        if ($$0.getWindow() != null) {
            $$0.updateFontOptions();
            $$0.resizeDisplay();
        }
    }

    public OptionInstance<Boolean> forceUnicodeFont() {
        return this.forceUnicodeFont;
    }

    private static boolean japaneseGlyphVariantsDefault() {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("ja");
    }

    public OptionInstance<Boolean> japaneseGlyphVariants() {
        return this.japaneseGlyphVariants;
    }

    public OptionInstance<Boolean> invertMouseX() {
        return this.invertXMouse;
    }

    public OptionInstance<Boolean> invertMouseY() {
        return this.invertYMouse;
    }

    public OptionInstance<Boolean> discreteMouseScroll() {
        return this.discreteMouseScroll;
    }

    public OptionInstance<Boolean> realmsNotifications() {
        return this.realmsNotifications;
    }

    public OptionInstance<Boolean> allowServerListing() {
        return this.allowServerListing;
    }

    public OptionInstance<Boolean> reducedDebugInfo() {
        return this.reducedDebugInfo;
    }

    public final float getFinalSoundSourceVolume(SoundSource $$0) {
        if ($$0 == SoundSource.MASTER) {
            return getSoundSourceVolume($$0);
        }
        return getSoundSourceVolume($$0) * getSoundSourceVolume(SoundSource.MASTER);
    }

    public final float getSoundSourceVolume(SoundSource $$0) {
        return getSoundSourceOptionInstance($$0).get().floatValue();
    }

    public final OptionInstance<Double> getSoundSourceOptionInstance(SoundSource $$0) {
        return (OptionInstance) Objects.requireNonNull(this.soundSourceVolumes.get($$0));
    }

    private OptionInstance<Double> createSoundSliderOptionInstance(String $$0, SoundSource $$1) {
        return new OptionInstance<>($$0, OptionInstance.noTooltip(), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$12 -> {
            Minecraft $$2 = Minecraft.getInstance();
            SoundManager $$3 = $$2.getSoundManager();
            if (($$1 == SoundSource.MASTER || $$1 == SoundSource.MUSIC) && getFinalSoundSourceVolume(SoundSource.MUSIC) > 0.0f) {
                $$2.getMusicManager().showNowPlayingToastIfNeeded();
            }
            $$3.refreshCategoryVolume($$1);
            if ($$2.level == null) {
                SoundPreviewHandler.preview($$3, $$1, $$12.floatValue());
            }
        });
    }

    public OptionInstance<Boolean> showSubtitles() {
        return this.showSubtitles;
    }

    public OptionInstance<Boolean> directionalAudio() {
        return this.directionalAudio;
    }

    public OptionInstance<Boolean> backgroundForChatOnly() {
        return this.backgroundForChatOnly;
    }

    public OptionInstance<Boolean> touchscreen() {
        return this.touchscreen;
    }

    public OptionInstance<Boolean> fullscreen() {
        return this.fullscreen;
    }

    public OptionInstance<Boolean> bobView() {
        return this.bobView;
    }

    public OptionInstance<Boolean> toggleCrouch() {
        return this.toggleCrouch;
    }

    public OptionInstance<Boolean> toggleSprint() {
        return this.toggleSprint;
    }

    public OptionInstance<Boolean> toggleAttack() {
        return this.toggleAttack;
    }

    public OptionInstance<Boolean> toggleUse() {
        return this.toggleUse;
    }

    public OptionInstance<Integer> sprintWindow() {
        return this.sprintWindow;
    }

    public OptionInstance<Boolean> hideMatchedNames() {
        return this.hideMatchedNames;
    }

    public OptionInstance<Boolean> showAutosaveIndicator() {
        return this.showAutosaveIndicator;
    }

    public OptionInstance<Boolean> onlyShowSecureChat() {
        return this.onlyShowSecureChat;
    }

    public OptionInstance<Boolean> saveChatDrafts() {
        return this.saveChatDrafts;
    }

    private void setGraphicsPresetToCustom() {
        if (this.isApplyingGraphicsPreset) {
            return;
        }
        this.graphicsPreset.set(GraphicsPreset.CUSTOM);
        Screen screen = this.minecraft.screen;
        if (screen instanceof OptionsSubScreen) {
            OptionsSubScreen $$0 = (OptionsSubScreen) screen;
            $$0.resetOption(this.graphicsPreset);
        }
    }

    public OptionInstance<Integer> fov() {
        return this.fov;
    }

    public OptionInstance<Boolean> telemetryOptInExtra() {
        return this.telemetryOptInExtra;
    }

    public OptionInstance<Double> screenEffectScale() {
        return this.screenEffectScale;
    }

    public OptionInstance<Double> fovEffectScale() {
        return this.fovEffectScale;
    }

    public OptionInstance<Double> darknessEffectScale() {
        return this.darknessEffectScale;
    }

    public OptionInstance<Double> glintSpeed() {
        return this.glintSpeed;
    }

    public OptionInstance<Double> glintStrength() {
        return this.glintStrength;
    }

    public OptionInstance<Double> damageTiltStrength() {
        return this.damageTiltStrength;
    }

    public OptionInstance<Double> gamma() {
        return this.gamma;
    }

    public OptionInstance<Integer> guiScale() {
        return this.guiScale;
    }

    public OptionInstance<ParticleStatus> particles() {
        return this.particles;
    }

    public OptionInstance<NarratorStatus> narrator() {
        return this.narrator;
    }

    public OptionInstance<String> soundDevice() {
        return this.soundDevice;
    }

    public void onboardingAccessibilityFinished() {
        this.onboardAccessibility = false;
        save();
    }

    public OptionInstance<MusicManager.MusicFrequency> musicFrequency() {
        return this.musicFrequency;
    }

    public OptionInstance<MusicToastDisplayState> musicToast() {
        return this.musicToast;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Options(Minecraft $$0, File $$1) {
        MutableComponent mutableComponentTranslatable;
        if (InputQuirks.REPLACE_CTRL_KEY_WITH_CMD_KEY) {
            mutableComponentTranslatable = Component.translatable("options.accessibility.narrator_hotkey.mac.tooltip");
        } else {
            mutableComponentTranslatable = Component.translatable("options.accessibility.narrator_hotkey.tooltip");
        }
        this.narratorHotkey = OptionInstance.createBoolean("options.accessibility.narrator_hotkey", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(mutableComponentTranslatable), true);
        this.pauseOnLostFocus = true;
        this.modelParts = EnumSet.allOf(PlayerModelPart.class);
        this.mainHand = new OptionInstance<>("options.mainHand", OptionInstance.noTooltip(), ($$02, $$12) -> {
            return $$12.caption();
        }, new OptionInstance.Enum(Arrays.asList(HumanoidArm.values()), HumanoidArm.CODEC), HumanoidArm.RIGHT, $$03 -> {
        });
        this.chatScale = new OptionInstance<>("options.chat.scale", OptionInstance.noTooltip(), ($$04, $$13) -> {
            if ($$13.doubleValue() == Density.SURFACE) {
                return CommonComponents.optionStatus($$04, false);
            }
            return percentValueLabel($$04, $$13.doubleValue());
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$05 -> {
            Minecraft.getInstance().gui.getChat().rescaleChat();
        });
        this.chatWidth = new OptionInstance<>("options.chat.width", OptionInstance.noTooltip(), ($$06, $$14) -> {
            return pixelValueLabel($$06, ChatComponent.getWidth($$14.doubleValue()));
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$07 -> {
            Minecraft.getInstance().gui.getChat().rescaleChat();
        });
        this.chatHeightUnfocused = new OptionInstance<>("options.chat.height.unfocused", OptionInstance.noTooltip(), ($$08, $$15) -> {
            return pixelValueLabel($$08, ChatComponent.getHeight($$15.doubleValue()));
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(ChatComponent.defaultUnfocusedPct()), $$09 -> {
            Minecraft.getInstance().gui.getChat().rescaleChat();
        });
        this.chatHeightFocused = new OptionInstance<>("options.chat.height.focused", OptionInstance.noTooltip(), ($$010, $$16) -> {
            return pixelValueLabel($$010, ChatComponent.getHeight($$16.doubleValue()));
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$011 -> {
            Minecraft.getInstance().gui.getChat().rescaleChat();
        });
        this.chatDelay = new OptionInstance<>("options.chat.delay_instant", OptionInstance.noTooltip(), ($$012, $$17) -> {
            if ($$17.doubleValue() <= Density.SURFACE) {
                return Component.translatable("options.chat.delay_none");
            }
            return Component.translatable("options.chat.delay", String.format(Locale.ROOT, "%.1f", $$17));
        }, new OptionInstance.IntRange(0, 60).xmap($$013 -> {
            return Double.valueOf(((double) $$013) / 10.0d);
        }, $$014 -> {
            return (int) ($$014.doubleValue() * 10.0d);
        }, true), Codec.doubleRange(Density.SURFACE, 6.0d), Double.valueOf(Density.SURFACE), $$015 -> {
            Minecraft.getInstance().getChatListener().setMessageDelay($$015.doubleValue());
        });
        this.notificationDisplayTime = new OptionInstance<>("options.notifications.display_time", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_NOTIFICATION_DISPLAY_TIME), ($$016, $$18) -> {
            return genericValueLabel($$016, Component.translatable("options.multiplier", $$18));
        }, new OptionInstance.IntRange(5, 100).xmap($$017 -> {
            return Double.valueOf(((double) $$017) / 10.0d);
        }, $$018 -> {
            return (int) ($$018.doubleValue() * 10.0d);
        }, true), Codec.doubleRange(0.5d, 10.0d), Double.valueOf(1.0d), $$019 -> {
        });
        this.mipmapLevels = new OptionInstance<>("options.mipmapLevels", OptionInstance.noTooltip(), ($$020, $$19) -> {
            if ($$19.intValue() == 0) {
                return CommonComponents.optionStatus($$020, false);
            }
            return genericValueLabel($$020, $$19.intValue());
        }, new OptionInstance.IntRange(0, 4), 4, $$021 -> {
            setGraphicsPresetToCustom();
        });
        this.maxAnisotropyBit = new OptionInstance<>("options.maxAnisotropy", OptionInstance.cachedConstantTooltip(GRAPHICS_TOOLTIP_ANISOTROPIC_FILTERING), ($$022, $$110) -> {
            if ($$110.intValue() == 0) {
                return CommonComponents.optionStatus($$022, false);
            }
            return genericValueLabel($$022, Component.translatable("options.multiplier", Integer.toString(1 << $$110.intValue())));
        }, new OptionInstance.IntRange(1, 3), 2, $$023 -> {
            setGraphicsPresetToCustom();
            operateOnLevelRenderer((v0) -> {
                v0.resetSampler();
            });
        });
        this.textureFiltering = new OptionInstance<>("options.textureFiltering", $$024 -> {
            switch ($$024) {
                case NONE:
                    return Tooltip.create(FILTERING_NONE_TOOLTIP);
                case RGSS:
                    return Tooltip.create(FILTERING_RGSS_TOOLTIP);
                case ANISOTROPIC:
                    return Tooltip.create(FILTERING_ANISOTROPIC_TOOLTIP);
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }, ($$025, $$111) -> {
            return $$111.caption();
        }, new OptionInstance.Enum(Arrays.asList(TextureFilteringMethod.values()), TextureFilteringMethod.LEGACY_CODEC), TextureFilteringMethod.NONE, $$026 -> {
            setGraphicsPresetToCustom();
            operateOnLevelRenderer((v0) -> {
                v0.resetSampler();
            });
        });
        this.useNativeTransport = true;
        this.attackIndicator = new OptionInstance<>("options.attackIndicator", OptionInstance.noTooltip(), ($$027, $$112) -> {
            return $$112.caption();
        }, new OptionInstance.Enum(Arrays.asList(AttackIndicatorStatus.values()), AttackIndicatorStatus.LEGACY_CODEC), AttackIndicatorStatus.CROSSHAIR, $$028 -> {
        });
        this.tutorialStep = TutorialSteps.MOVEMENT;
        this.joinedFirstServer = false;
        this.biomeBlendRadius = new OptionInstance<>("options.biomeBlendRadius", OptionInstance.noTooltip(), ($$029, $$113) -> {
            int $$2 = ($$113.intValue() * 2) + 1;
            return genericValueLabel($$029, Component.translatable("options.biomeBlendRadius." + $$2));
        }, new OptionInstance.IntRange(0, 7, false), 2, $$030 -> {
            operateOnLevelRenderer((v0) -> {
                v0.allChanged();
            });
            setGraphicsPresetToCustom();
        });
        this.mouseWheelSensitivity = new OptionInstance<>("options.mouseWheelSensitivity", OptionInstance.noTooltip(), ($$031, $$114) -> {
            return genericValueLabel($$031, Component.literal(String.format(Locale.ROOT, "%.2f", $$114)));
        }, new OptionInstance.IntRange(-200, 100).xmap(Options::logMouse, (v0) -> {
            return unlogMouse(v0);
        }, false), Codec.doubleRange(logMouse(-200), logMouse(100)), Double.valueOf(logMouse(0)), $$032 -> {
        });
        this.rawMouseInput = OptionInstance.createBoolean("options.rawMouseInput", true, (Consumer<Boolean>) $$033 -> {
            Window $$115 = Minecraft.getInstance().getWindow();
            if ($$115 != null) {
                $$115.updateRawMouseInput($$033.booleanValue());
            }
        });
        this.allowCursorChanges = OptionInstance.createBoolean("options.allowCursorChanges", OptionInstance.cachedConstantTooltip(ALLOW_CURSOR_CHANGES_TOOLTIP), true, $$034 -> {
            Window $$115 = Minecraft.getInstance().getWindow();
            if ($$115 != null) {
                $$115.setAllowCursorChanges($$034.booleanValue());
            }
        });
        this.glDebugVerbosity = 1;
        this.autoJump = OptionInstance.createBoolean("options.autoJump", false);
        this.rotateWithMinecart = OptionInstance.createBoolean("options.rotateWithMinecart", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_ROTATE_WITH_MINECART), false);
        this.operatorItemsTab = OptionInstance.createBoolean("options.operatorItemsTab", false);
        this.autoSuggestions = OptionInstance.createBoolean("options.autoSuggestCommands", true);
        this.chatColors = OptionInstance.createBoolean("options.chat.color", true);
        this.chatLinks = OptionInstance.createBoolean("options.chat.links", true);
        this.chatLinksPrompt = OptionInstance.createBoolean("options.chat.links.prompt", true);
        this.enableVsync = OptionInstance.createBoolean("options.vsync", true, (Consumer<Boolean>) $$035 -> {
            if (Minecraft.getInstance().getWindow() != null) {
                Minecraft.getInstance().getWindow().updateVsync($$035.booleanValue());
            }
        });
        this.entityShadows = OptionInstance.createBoolean("options.entityShadows", OptionInstance.noTooltip(), true, $$036 -> {
            setGraphicsPresetToCustom();
        });
        this.forceUnicodeFont = OptionInstance.createBoolean("options.forceUnicodeFont", false, (Consumer<Boolean>) $$037 -> {
            updateFontOptions();
        });
        this.japaneseGlyphVariants = OptionInstance.createBoolean("options.japaneseGlyphVariants", OptionInstance.cachedConstantTooltip(Component.translatable("options.japaneseGlyphVariants.tooltip")), japaneseGlyphVariantsDefault(), $$038 -> {
            updateFontOptions();
        });
        this.invertXMouse = OptionInstance.createBoolean("options.invertMouseX", false);
        this.invertYMouse = OptionInstance.createBoolean("options.invertMouseY", false);
        this.discreteMouseScroll = OptionInstance.createBoolean("options.discrete_mouse_scroll", false);
        this.realmsNotifications = OptionInstance.createBoolean("options.realmsNotifications", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(REALMS_NOTIFICATIONS_TOOLTIP), true);
        this.allowServerListing = OptionInstance.createBoolean("options.allowServerListing", OptionInstance.cachedConstantTooltip(ALLOW_SERVER_LISTING_TOOLTIP), true, $$039 -> {
        });
        this.reducedDebugInfo = OptionInstance.createBoolean("options.reducedDebugInfo", OptionInstance.noTooltip(), false, $$040 -> {
            Minecraft.getInstance().debugEntries.rebuildCurrentList();
        });
        this.soundSourceVolumes = Util.makeEnumMap(SoundSource.class, $$041 -> {
            return createSoundSliderOptionInstance("soundCategory." + $$041.getName(), $$041);
        });
        this.showSubtitles = OptionInstance.createBoolean("options.showSubtitles", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(CLOSED_CAPTIONS_TOOLTIP), false);
        this.directionalAudio = OptionInstance.createBoolean("options.directionalAudio", $$042 -> {
            return $$042.booleanValue() ? Tooltip.create(DIRECTIONAL_AUDIO_TOOLTIP_ON) : Tooltip.create(DIRECTIONAL_AUDIO_TOOLTIP_OFF);
        }, false, $$043 -> {
            SoundManager $$115 = Minecraft.getInstance().getSoundManager();
            $$115.reload();
            $$115.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        });
        this.backgroundForChatOnly = new OptionInstance<>("options.accessibility.text_background", OptionInstance.noTooltip(), ($$044, $$115) -> {
            return $$115.booleanValue() ? Component.translatable("options.accessibility.text_background.chat") : Component.translatable("options.accessibility.text_background.everywhere");
        }, OptionInstance.BOOLEAN_VALUES, true, $$045 -> {
        });
        this.touchscreen = OptionInstance.createBoolean("options.touchscreen", false);
        this.fullscreen = OptionInstance.createBoolean("options.fullscreen", false, (Consumer<Boolean>) $$046 -> {
            Minecraft $$116 = Minecraft.getInstance();
            if ($$116.getWindow() != null && $$116.getWindow().isFullscreen() != $$046.booleanValue()) {
                $$116.getWindow().toggleFullScreen();
                fullscreen().set(Boolean.valueOf($$116.getWindow().isFullscreen()));
            }
        });
        this.bobView = OptionInstance.createBoolean("options.viewBobbing", true);
        this.toggleCrouch = new OptionInstance<>("key.sneak", OptionInstance.noTooltip(), ($$047, $$116) -> {
            return $$116.booleanValue() ? KEY_TOGGLE : KEY_HOLD;
        }, OptionInstance.BOOLEAN_VALUES, false, $$048 -> {
        });
        this.toggleSprint = new OptionInstance<>("key.sprint", OptionInstance.noTooltip(), ($$049, $$117) -> {
            return $$117.booleanValue() ? KEY_TOGGLE : KEY_HOLD;
        }, OptionInstance.BOOLEAN_VALUES, false, $$050 -> {
        });
        this.toggleAttack = new OptionInstance<>("key.attack", OptionInstance.noTooltip(), ($$051, $$118) -> {
            return $$118.booleanValue() ? KEY_TOGGLE : KEY_HOLD;
        }, OptionInstance.BOOLEAN_VALUES, false, $$052 -> {
        });
        this.toggleUse = new OptionInstance<>("key.use", OptionInstance.noTooltip(), ($$053, $$119) -> {
            return $$119.booleanValue() ? KEY_TOGGLE : KEY_HOLD;
        }, OptionInstance.BOOLEAN_VALUES, false, $$054 -> {
        });
        this.sprintWindow = new OptionInstance<>("options.sprintWindow", OptionInstance.cachedConstantTooltip(SPRINT_WINDOW_TOOLTIP), ($$055, $$120) -> {
            if ($$120.intValue() == 0) {
                return genericValueLabel($$055, Component.translatable("options.off"));
            }
            return genericValueLabel($$055, Component.translatable("options.value", $$120));
        }, new OptionInstance.IntRange(0, 10), 7, $$056 -> {
        });
        this.hideMatchedNames = OptionInstance.createBoolean("options.hideMatchedNames", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(CHAT_TOOLTIP_HIDE_MATCHED_NAMES), true);
        this.showAutosaveIndicator = OptionInstance.createBoolean("options.autosaveIndicator", true);
        this.onlyShowSecureChat = OptionInstance.createBoolean("options.onlyShowSecureChat", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(CHAT_TOOLTIP_ONLY_SHOW_SECURE), false);
        this.saveChatDrafts = OptionInstance.createBoolean("options.chat.drafts", (OptionInstance.TooltipSupplier<Boolean>) OptionInstance.cachedConstantTooltip(CHAT_TOOLTIP_SAVE_DRAFTS), false);
        this.keyUp = new KeyMapping("key.forward", 87, KeyMapping.Category.MOVEMENT);
        this.keyLeft = new KeyMapping("key.left", 65, KeyMapping.Category.MOVEMENT);
        this.keyDown = new KeyMapping("key.back", 83, KeyMapping.Category.MOVEMENT);
        this.keyRight = new KeyMapping("key.right", 68, KeyMapping.Category.MOVEMENT);
        this.keyJump = new KeyMapping("key.jump", 32, KeyMapping.Category.MOVEMENT);
        KeyMapping.Category category = KeyMapping.Category.MOVEMENT;
        OptionInstance<Boolean> optionInstance = this.toggleCrouch;
        Objects.requireNonNull(optionInstance);
        this.keyShift = new ToggleKeyMapping("key.sneak", InputConstants.KEY_LSHIFT, category, optionInstance::get, true);
        KeyMapping.Category category2 = KeyMapping.Category.MOVEMENT;
        OptionInstance<Boolean> optionInstance2 = this.toggleSprint;
        Objects.requireNonNull(optionInstance2);
        this.keySprint = new ToggleKeyMapping("key.sprint", InputConstants.KEY_LCONTROL, category2, optionInstance2::get, true);
        this.keyInventory = new KeyMapping("key.inventory", 69, KeyMapping.Category.INVENTORY);
        this.keySwapOffhand = new KeyMapping("key.swapOffhand", 70, KeyMapping.Category.INVENTORY);
        this.keyDrop = new KeyMapping("key.drop", 81, KeyMapping.Category.INVENTORY);
        InputConstants.Type type = InputConstants.Type.MOUSE;
        KeyMapping.Category category3 = KeyMapping.Category.GAMEPLAY;
        OptionInstance<Boolean> optionInstance3 = this.toggleUse;
        Objects.requireNonNull(optionInstance3);
        this.keyUse = new ToggleKeyMapping("key.use", type, 1, category3, optionInstance3::get, false);
        InputConstants.Type type2 = InputConstants.Type.MOUSE;
        KeyMapping.Category category4 = KeyMapping.Category.GAMEPLAY;
        OptionInstance<Boolean> optionInstance4 = this.toggleAttack;
        Objects.requireNonNull(optionInstance4);
        this.keyAttack = new ToggleKeyMapping("key.attack", type2, 0, category4, optionInstance4::get, true);
        this.keyPickItem = new KeyMapping("key.pickItem", InputConstants.Type.MOUSE, 2, KeyMapping.Category.GAMEPLAY);
        this.keyChat = new KeyMapping("key.chat", 84, KeyMapping.Category.MULTIPLAYER);
        this.keyPlayerList = new KeyMapping("key.playerlist", InputConstants.KEY_TAB, KeyMapping.Category.MULTIPLAYER);
        this.keyCommand = new KeyMapping("key.command", 47, KeyMapping.Category.MULTIPLAYER);
        this.keySocialInteractions = new KeyMapping("key.socialInteractions", 80, KeyMapping.Category.MULTIPLAYER);
        this.keyScreenshot = new KeyMapping("key.screenshot", InputConstants.KEY_F2, KeyMapping.Category.MISC);
        this.keyTogglePerspective = new KeyMapping("key.togglePerspective", InputConstants.KEY_F5, KeyMapping.Category.MISC);
        this.keySmoothCamera = new KeyMapping("key.smoothCamera", InputConstants.UNKNOWN.getValue(), KeyMapping.Category.MISC);
        this.keyFullscreen = new KeyMapping("key.fullscreen", 300, KeyMapping.Category.MISC);
        this.keyAdvancements = new KeyMapping("key.advancements", 76, KeyMapping.Category.MISC);
        this.keyQuickActions = new KeyMapping("key.quickActions", 71, KeyMapping.Category.MISC);
        this.keyToggleGui = new KeyMapping("key.toggleGui", InputConstants.KEY_F1, KeyMapping.Category.MISC);
        this.keyToggleSpectatorShaderEffects = new KeyMapping("key.toggleSpectatorShaderEffects", InputConstants.KEY_F4, KeyMapping.Category.MISC);
        this.keyHotbarSlots = new KeyMapping[]{new KeyMapping("key.hotbar.1", 49, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.2", 50, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.3", 51, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.4", 52, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.5", 53, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.6", 54, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.7", 55, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.8", 56, KeyMapping.Category.INVENTORY), new KeyMapping("key.hotbar.9", 57, KeyMapping.Category.INVENTORY)};
        this.keySaveHotbarActivator = new KeyMapping("key.saveToolbarActivator", 67, KeyMapping.Category.CREATIVE);
        this.keyLoadHotbarActivator = new KeyMapping("key.loadToolbarActivator", 88, KeyMapping.Category.CREATIVE);
        this.keySpectatorOutlines = new KeyMapping("key.spectatorOutlines", InputConstants.UNKNOWN.getValue(), KeyMapping.Category.SPECTATOR);
        this.keySpectatorHotbar = new KeyMapping("key.spectatorHotbar", InputConstants.Type.MOUSE, 2, KeyMapping.Category.SPECTATOR);
        this.keyDebugOverlay = new KeyMapping("key.debug.overlay", InputConstants.Type.KEYSYM, InputConstants.KEY_F3, KeyMapping.Category.DEBUG, -2);
        this.keyDebugModifier = new KeyMapping("key.debug.modifier", InputConstants.Type.KEYSYM, InputConstants.KEY_F3, KeyMapping.Category.DEBUG, -1);
        this.keyDebugCrash = new KeyMapping("key.debug.crash", InputConstants.Type.KEYSYM, 67, KeyMapping.Category.DEBUG);
        this.keyDebugReloadChunk = new KeyMapping("key.debug.reloadChunk", InputConstants.Type.KEYSYM, 65, KeyMapping.Category.DEBUG);
        this.keyDebugShowHitboxes = new KeyMapping("key.debug.showHitboxes", InputConstants.Type.KEYSYM, 66, KeyMapping.Category.DEBUG);
        this.keyDebugClearChat = new KeyMapping("key.debug.clearChat", InputConstants.Type.KEYSYM, 68, KeyMapping.Category.DEBUG);
        this.keyDebugShowChunkBorders = new KeyMapping("key.debug.showChunkBorders", InputConstants.Type.KEYSYM, 71, KeyMapping.Category.DEBUG);
        this.keyDebugShowAdvancedTooltips = new KeyMapping("key.debug.showAdvancedTooltips", InputConstants.Type.KEYSYM, 72, KeyMapping.Category.DEBUG);
        this.keyDebugCopyRecreateCommand = new KeyMapping("key.debug.copyRecreateCommand", InputConstants.Type.KEYSYM, 73, KeyMapping.Category.DEBUG);
        this.keyDebugSpectate = new KeyMapping("key.debug.spectate", InputConstants.Type.KEYSYM, 78, KeyMapping.Category.DEBUG);
        this.keyDebugSwitchGameMode = new KeyMapping("key.debug.switchGameMode", InputConstants.Type.KEYSYM, InputConstants.KEY_F4, KeyMapping.Category.DEBUG);
        this.keyDebugDebugOptions = new KeyMapping("key.debug.debugOptions", InputConstants.Type.KEYSYM, InputConstants.KEY_F6, KeyMapping.Category.DEBUG);
        this.keyDebugFocusPause = new KeyMapping("key.debug.focusPause", InputConstants.Type.KEYSYM, 80, KeyMapping.Category.DEBUG);
        this.keyDebugDumpDynamicTextures = new KeyMapping("key.debug.dumpDynamicTextures", InputConstants.Type.KEYSYM, 83, KeyMapping.Category.DEBUG);
        this.keyDebugReloadResourcePacks = new KeyMapping("key.debug.reloadResourcePacks", InputConstants.Type.KEYSYM, 84, KeyMapping.Category.DEBUG);
        this.keyDebugProfiling = new KeyMapping("key.debug.profiling", InputConstants.Type.KEYSYM, 76, KeyMapping.Category.DEBUG);
        this.keyDebugCopyLocation = new KeyMapping("key.debug.copyLocation", InputConstants.Type.KEYSYM, 67, KeyMapping.Category.DEBUG);
        this.keyDebugDumpVersion = new KeyMapping("key.debug.dumpVersion", InputConstants.Type.KEYSYM, 86, KeyMapping.Category.DEBUG);
        this.keyDebugPofilingChart = new KeyMapping("key.debug.profilingChart", InputConstants.Type.KEYSYM, 49, KeyMapping.Category.DEBUG, 1);
        this.keyDebugFpsCharts = new KeyMapping("key.debug.fpsCharts", InputConstants.Type.KEYSYM, 50, KeyMapping.Category.DEBUG, 2);
        this.keyDebugNetworkCharts = new KeyMapping("key.debug.networkCharts", InputConstants.Type.KEYSYM, 51, KeyMapping.Category.DEBUG, 3);
        this.debugKeys = new KeyMapping[]{this.keyDebugReloadChunk, this.keyDebugShowHitboxes, this.keyDebugClearChat, this.keyDebugCrash, this.keyDebugShowChunkBorders, this.keyDebugShowAdvancedTooltips, this.keyDebugCopyRecreateCommand, this.keyDebugSpectate, this.keyDebugSwitchGameMode, this.keyDebugDebugOptions, this.keyDebugFocusPause, this.keyDebugDumpDynamicTextures, this.keyDebugReloadResourcePacks, this.keyDebugProfiling, this.keyDebugCopyLocation, this.keyDebugDumpVersion, this.keyDebugPofilingChart, this.keyDebugFpsCharts, this.keyDebugNetworkCharts};
        this.keyMappings = (KeyMapping[]) Stream.of((Object[]) new KeyMapping[]{new KeyMapping[]{this.keyAttack, this.keyUse, this.keyUp, this.keyLeft, this.keyDown, this.keyRight, this.keyJump, this.keyShift, this.keySprint, this.keyDrop, this.keyInventory, this.keyChat, this.keyPlayerList, this.keyPickItem, this.keyCommand, this.keySocialInteractions, this.keyToggleGui, this.keyToggleSpectatorShaderEffects, this.keyScreenshot, this.keyTogglePerspective, this.keySmoothCamera, this.keyFullscreen, this.keySpectatorOutlines, this.keySpectatorHotbar, this.keySwapOffhand, this.keySaveHotbarActivator, this.keyLoadHotbarActivator, this.keyAdvancements, this.keyQuickActions, this.keyDebugOverlay, this.keyDebugModifier}, this.keyHotbarSlots, this.debugKeys}).flatMap((v0) -> {
            return Stream.of(v0);
        }).toArray($$057 -> {
            return new KeyMapping[$$057];
        });
        this.cameraType = CameraType.FIRST_PERSON;
        this.lastMpIp = "";
        this.fov = new OptionInstance<>("options.fov", OptionInstance.noTooltip(), ($$058, $$121) -> {
            switch ($$121.intValue()) {
                case 70:
                    return genericValueLabel($$058, Component.translatable("options.fov.min"));
                case 110:
                    return genericValueLabel($$058, Component.translatable("options.fov.max"));
                default:
                    return genericValueLabel($$058, $$121.intValue());
            }
        }, new OptionInstance.IntRange(30, 110), Codec.DOUBLE.xmap($$059 -> {
            return Integer.valueOf((int) (($$059.doubleValue() * 40.0d) + 70.0d));
        }, $$060 -> {
            return Double.valueOf((((double) $$060.intValue()) - 70.0d) / 40.0d);
        }), 70, $$061 -> {
            operateOnLevelRenderer((v0) -> {
                v0.needsUpdate();
            });
        });
        this.telemetryOptInExtra = OptionInstance.createBoolean("options.telemetry.button", OptionInstance.cachedConstantTooltip(TELEMETRY_TOOLTIP), ($$062, $$122) -> {
            Minecraft $$2 = Minecraft.getInstance();
            if (!$$2.allowsTelemetry()) {
                return Component.translatable("options.telemetry.state.none");
            }
            if ($$122.booleanValue() && $$2.extraTelemetryAvailable()) {
                return Component.translatable("options.telemetry.state.all");
            }
            return Component.translatable("options.telemetry.state.minimal");
        }, false, $$063 -> {
        });
        this.screenEffectScale = new OptionInstance<>("options.screenEffectScale", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$064 -> {
        });
        this.fovEffectScale = new OptionInstance<>("options.fovEffectScale", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_FOV_EFFECT), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE.xmap(Mth::square, (v0) -> {
            return Math.sqrt(v0);
        }), Codec.doubleRange(Density.SURFACE, 1.0d), Double.valueOf(1.0d), $$065 -> {
        });
        this.darknessEffectScale = new OptionInstance<>("options.darknessEffectScale", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_DARKNESS_EFFECT), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE.xmap(Mth::square, (v0) -> {
            return Math.sqrt(v0);
        }), Double.valueOf(1.0d), $$066 -> {
        });
        this.glintSpeed = new OptionInstance<>("options.glintSpeed", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_GLINT_SPEED), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5d), $$067 -> {
        });
        this.glintStrength = new OptionInstance<>("options.glintStrength", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_GLINT_STRENGTH), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.75d), $$068 -> {
        });
        this.damageTiltStrength = new OptionInstance<>("options.damageTiltStrength", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_DAMAGE_TILT_STRENGTH), (v0, v1) -> {
            return percentValueOrOffLabel(v0, v1);
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0d), $$069 -> {
        });
        this.gamma = new OptionInstance<>("options.gamma", OptionInstance.noTooltip(), ($$070, $$123) -> {
            int $$2 = (int) ($$123.doubleValue() * 100.0d);
            if ($$2 == 0) {
                return genericValueLabel($$070, Component.translatable("options.gamma.min"));
            }
            if ($$2 == 50) {
                return genericValueLabel($$070, Component.translatable("options.gamma.default"));
            }
            if ($$2 == 100) {
                return genericValueLabel($$070, Component.translatable("options.gamma.max"));
            }
            return genericValueLabel($$070, $$2);
        }, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5d), $$071 -> {
        });
        this.guiScale = new OptionInstance<>("options.guiScale", OptionInstance.noTooltip(), ($$072, $$124) -> {
            return $$124.intValue() == 0 ? Component.translatable("options.guiScale.auto") : Component.literal(Integer.toString($$124.intValue()));
        }, new OptionInstance.ClampingLazyMaxIntRange(0, () -> {
            Minecraft $$073 = Minecraft.getInstance();
            if (!$$073.isRunning()) {
                return MAX_GUI_SCALE_INCLUSIVE;
            }
            return $$073.getWindow().calculateScale(0, $$073.isEnforceUnicode());
        }, MAX_GUI_SCALE_INCLUSIVE), 0, $$073 -> {
            this.minecraft.resizeDisplay();
        });
        this.particles = new OptionInstance<>("options.particles", OptionInstance.noTooltip(), ($$074, $$125) -> {
            return $$125.caption();
        }, new OptionInstance.Enum(Arrays.asList(ParticleStatus.values()), ParticleStatus.LEGACY_CODEC), ParticleStatus.ALL, $$075 -> {
            setGraphicsPresetToCustom();
        });
        this.narrator = new OptionInstance<>("options.narrator", OptionInstance.noTooltip(), ($$076, $$126) -> {
            if (this.minecraft.getNarrator().isActive()) {
                return $$126.getName();
            }
            return Component.translatable("options.narrator.notavailable");
        }, new OptionInstance.Enum(Arrays.asList(NarratorStatus.values()), NarratorStatus.LEGACY_CODEC), NarratorStatus.OFF, $$077 -> {
            this.minecraft.getNarrator().updateNarratorStatus($$077);
        });
        this.languageCode = Language.DEFAULT;
        this.soundDevice = new OptionInstance<>("options.audioDevice", OptionInstance.noTooltip(), ($$078, $$127) -> {
            if ("".equals($$127)) {
                return Component.translatable("options.audioDevice.default");
            }
            if ($$127.startsWith(SoundEngine.OPEN_AL_SOFT_PREFIX)) {
                return Component.literal($$127.substring(SoundEngine.OPEN_AL_SOFT_PREFIX_LENGTH));
            }
            return Component.literal($$127);
        }, new OptionInstance.LazyEnum(() -> {
            return Stream.concat(Stream.of(""), Minecraft.getInstance().getSoundManager().getAvailableSoundDevices().stream()).toList();
        }, $$079 -> {
            if (!Minecraft.getInstance().isRunning() || $$079 == "" || Minecraft.getInstance().getSoundManager().getAvailableSoundDevices().contains($$079)) {
                return Optional.of($$079);
            }
            return Optional.empty();
        }, Codec.STRING), "", $$080 -> {
            SoundManager $$128 = Minecraft.getInstance().getSoundManager();
            $$128.reload();
            $$128.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        });
        this.onboardAccessibility = true;
        this.musicFrequency = new OptionInstance<>("options.music_frequency", OptionInstance.cachedConstantTooltip(MUSIC_FREQUENCY_TOOLTIP), ($$081, $$128) -> {
            return $$128.caption();
        }, new OptionInstance.Enum(Arrays.asList(MusicManager.MusicFrequency.values()), MusicManager.MusicFrequency.CODEC), MusicManager.MusicFrequency.DEFAULT, $$082 -> {
            Minecraft.getInstance().getMusicManager().setMinutesBetweenSongs($$082);
        });
        this.musicToast = new OptionInstance<>("options.musicToast", $$083 -> {
            return Tooltip.create($$083.tooltip());
        }, ($$084, $$129) -> {
            return $$129.text();
        }, new OptionInstance.Enum(Arrays.asList(MusicToastDisplayState.values()), MusicToastDisplayState.CODEC), MusicToastDisplayState.NEVER, $$085 -> {
            this.minecraft.getToastManager().setMusicToastDisplayState($$085);
        });
        this.startedCleanly = true;
        this.minecraft = $$0;
        this.optionsFile = new File($$1, "options.txt");
        boolean $$2 = Runtime.getRuntime().maxMemory() >= 1000000000;
        this.renderDistance = new OptionInstance<>("options.renderDistance", OptionInstance.noTooltip(), ($$086, $$130) -> {
            return genericValueLabel($$086, Component.translatable("options.chunks", $$130));
        }, new OptionInstance.IntRange(2, $$2 ? 32 : 16, false), 12, $$087 -> {
            operateOnLevelRenderer((v0) -> {
                v0.needsUpdate();
            });
            setGraphicsPresetToCustom();
        });
        this.simulationDistance = new OptionInstance<>("options.simulationDistance", OptionInstance.noTooltip(), ($$088, $$131) -> {
            return genericValueLabel($$088, Component.translatable("options.chunks", $$131));
        }, new OptionInstance.IntRange(SharedConstants.DEBUG_ALLOW_LOW_SIM_DISTANCE ? 2 : 5, $$2 ? 32 : 16, false), 12, $$089 -> {
            setGraphicsPresetToCustom();
        });
        this.syncWrites = Util.getPlatform() == Util.OS.WINDOWS;
        load();
    }

    public float getBackgroundOpacity(float $$0) {
        return this.backgroundForChatOnly.get().booleanValue() ? $$0 : textBackgroundOpacity().get().floatValue();
    }

    public int getBackgroundColor(float $$0) {
        return ARGB.colorFromFloat(getBackgroundOpacity($$0), 0.0f, 0.0f, 0.0f);
    }

    public int getBackgroundColor(int $$0) {
        return this.backgroundForChatOnly.get().booleanValue() ? $$0 : ARGB.colorFromFloat(this.textBackgroundOpacity.get().floatValue(), 0.0f, 0.0f, 0.0f);
    }

    private void processDumpedOptions(OptionAccess $$0) {
        $$0.process("ao", this.ambientOcclusion);
        $$0.process("biomeBlendRadius", this.biomeBlendRadius);
        $$0.process("chunkSectionFadeInTime", this.chunkSectionFadeInTime);
        $$0.process("cutoutLeaves", this.cutoutLeaves);
        $$0.process("enableVsync", this.enableVsync);
        $$0.process("entityDistanceScaling", this.entityDistanceScaling);
        $$0.process("entityShadows", this.entityShadows);
        $$0.process("forceUnicodeFont", this.forceUnicodeFont);
        $$0.process("japaneseGlyphVariants", this.japaneseGlyphVariants);
        $$0.process("fov", this.fov);
        $$0.process("fovEffectScale", this.fovEffectScale);
        $$0.process("darknessEffectScale", this.darknessEffectScale);
        $$0.process("glintSpeed", this.glintSpeed);
        $$0.process("glintStrength", this.glintStrength);
        $$0.process("graphicsPreset", this.graphicsPreset);
        $$0.process("prioritizeChunkUpdates", this.prioritizeChunkUpdates);
        $$0.process("fullscreen", this.fullscreen);
        $$0.process("gamma", this.gamma);
        $$0.process("guiScale", this.guiScale);
        $$0.process("maxAnisotropyBit", this.maxAnisotropyBit);
        $$0.process("textureFiltering", this.textureFiltering);
        $$0.process("maxFps", this.framerateLimit);
        $$0.process("improvedTransparency", this.improvedTransparency);
        $$0.process("inactivityFpsLimit", this.inactivityFpsLimit);
        $$0.process("mipmapLevels", this.mipmapLevels);
        $$0.process("narrator", this.narrator);
        $$0.process("particles", this.particles);
        $$0.process("reducedDebugInfo", this.reducedDebugInfo);
        $$0.process("renderClouds", this.cloudStatus);
        $$0.process("cloudRange", this.cloudRange);
        $$0.process("renderDistance", this.renderDistance);
        $$0.process("simulationDistance", this.simulationDistance);
        $$0.process("screenEffectScale", this.screenEffectScale);
        $$0.process("soundDevice", this.soundDevice);
        $$0.process("vignette", this.vignette);
        $$0.process("weatherRadius", this.weatherRadius);
    }

    private void processOptions(FieldAccess $$0) {
        processDumpedOptions($$0);
        $$0.process("autoJump", this.autoJump);
        $$0.process("rotateWithMinecart", this.rotateWithMinecart);
        $$0.process("operatorItemsTab", this.operatorItemsTab);
        $$0.process("autoSuggestions", this.autoSuggestions);
        $$0.process("chatColors", this.chatColors);
        $$0.process("chatLinks", this.chatLinks);
        $$0.process("chatLinksPrompt", this.chatLinksPrompt);
        $$0.process("discrete_mouse_scroll", this.discreteMouseScroll);
        $$0.process("invertXMouse", this.invertXMouse);
        $$0.process("invertYMouse", this.invertYMouse);
        $$0.process("realmsNotifications", this.realmsNotifications);
        $$0.process("showSubtitles", this.showSubtitles);
        $$0.process("directionalAudio", this.directionalAudio);
        $$0.process("touchscreen", this.touchscreen);
        $$0.process("bobView", this.bobView);
        $$0.process("toggleCrouch", this.toggleCrouch);
        $$0.process("toggleSprint", this.toggleSprint);
        $$0.process("toggleAttack", this.toggleAttack);
        $$0.process("toggleUse", this.toggleUse);
        $$0.process("sprintWindow", this.sprintWindow);
        $$0.process("darkMojangStudiosBackground", this.darkMojangStudiosBackground);
        $$0.process("hideLightningFlashes", this.hideLightningFlash);
        $$0.process("hideSplashTexts", this.hideSplashTexts);
        $$0.process("mouseSensitivity", this.sensitivity);
        $$0.process("damageTiltStrength", this.damageTiltStrength);
        $$0.process("highContrast", this.highContrast);
        $$0.process("highContrastBlockOutline", this.highContrastBlockOutline);
        $$0.process("narratorHotkey", this.narratorHotkey);
        List<String> list = this.resourcePacks;
        Function function = Options::readListOfStrings;
        Gson gson = GSON;
        Objects.requireNonNull(gson);
        this.resourcePacks = (List) $$0.process("resourcePacks", list, function, (v1) -> {
            return r5.toJson(v1);
        });
        List<String> list2 = this.incompatibleResourcePacks;
        Function function2 = Options::readListOfStrings;
        Gson gson2 = GSON;
        Objects.requireNonNull(gson2);
        this.incompatibleResourcePacks = (List) $$0.process("incompatibleResourcePacks", list2, function2, (v1) -> {
            return r5.toJson(v1);
        });
        this.lastMpIp = $$0.process("lastServer", this.lastMpIp);
        this.languageCode = $$0.process("lang", this.languageCode);
        $$0.process("chatVisibility", this.chatVisibility);
        $$0.process("chatOpacity", this.chatOpacity);
        $$0.process("chatLineSpacing", this.chatLineSpacing);
        $$0.process("textBackgroundOpacity", this.textBackgroundOpacity);
        $$0.process("backgroundForChatOnly", this.backgroundForChatOnly);
        this.hideServerAddress = $$0.process("hideServerAddress", this.hideServerAddress);
        this.advancedItemTooltips = $$0.process("advancedItemTooltips", this.advancedItemTooltips);
        this.pauseOnLostFocus = $$0.process("pauseOnLostFocus", this.pauseOnLostFocus);
        this.overrideWidth = $$0.process("overrideWidth", this.overrideWidth);
        this.overrideHeight = $$0.process("overrideHeight", this.overrideHeight);
        $$0.process("chatHeightFocused", this.chatHeightFocused);
        $$0.process("chatDelay", this.chatDelay);
        $$0.process("chatHeightUnfocused", this.chatHeightUnfocused);
        $$0.process("chatScale", this.chatScale);
        $$0.process("chatWidth", this.chatWidth);
        $$0.process("notificationDisplayTime", this.notificationDisplayTime);
        this.useNativeTransport = $$0.process("useNativeTransport", this.useNativeTransport);
        $$0.process("mainHand", this.mainHand);
        $$0.process("attackIndicator", this.attackIndicator);
        this.tutorialStep = (TutorialSteps) $$0.process("tutorialStep", this.tutorialStep, TutorialSteps::getByName, (v0) -> {
            return v0.getName();
        });
        $$0.process("mouseWheelSensitivity", this.mouseWheelSensitivity);
        $$0.process("rawMouseInput", this.rawMouseInput);
        $$0.process("allowCursorChanges", this.allowCursorChanges);
        this.glDebugVerbosity = $$0.process("glDebugVerbosity", this.glDebugVerbosity);
        this.skipMultiplayerWarning = $$0.process("skipMultiplayerWarning", this.skipMultiplayerWarning);
        $$0.process("hideMatchedNames", this.hideMatchedNames);
        this.joinedFirstServer = $$0.process("joinedFirstServer", this.joinedFirstServer);
        this.syncWrites = $$0.process("syncChunkWrites", this.syncWrites);
        $$0.process("showAutosaveIndicator", this.showAutosaveIndicator);
        $$0.process("allowServerListing", this.allowServerListing);
        $$0.process("onlyShowSecureChat", this.onlyShowSecureChat);
        $$0.process("saveChatDrafts", this.saveChatDrafts);
        $$0.process("panoramaScrollSpeed", this.panoramaSpeed);
        $$0.process("telemetryOptInExtra", this.telemetryOptInExtra);
        this.onboardAccessibility = $$0.process("onboardAccessibility", this.onboardAccessibility);
        $$0.process("menuBackgroundBlurriness", this.menuBackgroundBlurriness);
        this.startedCleanly = $$0.process("startedCleanly", this.startedCleanly);
        $$0.process("musicToast", this.musicToast);
        $$0.process("musicFrequency", this.musicFrequency);
        for (KeyMapping $$1 : this.keyMappings) {
            String $$2 = $$1.saveString();
            String $$3 = $$0.process("key_" + $$1.getName(), $$2);
            if (!$$2.equals($$3)) {
                $$1.setKey(InputConstants.getKey($$3));
            }
        }
        for (SoundSource $$4 : SoundSource.values()) {
            $$0.process("soundCategory_" + $$4.getName(), this.soundSourceVolumes.get($$4));
        }
        for (PlayerModelPart $$5 : PlayerModelPart.values()) {
            boolean $$6 = this.modelParts.contains($$5);
            boolean $$7 = $$0.process("modelPart_" + $$5.getId(), $$6);
            if ($$7 != $$6) {
                setModelPart($$5, $$7);
            }
        }
    }

    public void load() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            CompoundTag $$0 = new CompoundTag();
            BufferedReader $$1 = Files.newReader(this.optionsFile, StandardCharsets.UTF_8);
            try {
                $$1.lines().forEach($$12 -> {
                    try {
                        Iterator<String> $$2 = OPTION_SPLITTER.split($$12).iterator();
                        $$0.putString($$2.next(), $$2.next());
                    } catch (Exception e) {
                        LOGGER.warn("Skipping bad option: {}", $$12);
                    }
                });
                if ($$1 != null) {
                    $$1.close();
                }
                final CompoundTag $$2 = dataFix($$0);
                processOptions(new FieldAccess(this) { // from class: net.minecraft.client.Options.2
                    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                    private String getValue(String $$02) throws MatchException {
                        Tag $$13 = $$2.get($$02);
                        if ($$13 == null) {
                            return null;
                        }
                        if ($$13 instanceof StringTag) {
                            try {
                                String $$22 = ((StringTag) $$13).value();
                                return $$22;
                            } catch (Throwable th) {
                                throw new MatchException(th.toString(), th);
                            }
                        }
                        throw new IllegalStateException("Cannot read field of wrong type, expected string: " + String.valueOf($$13));
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
                    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                    @Override // net.minecraft.client.Options.OptionAccess
                    public <T> void process(String $$02, OptionInstance<T> $$13) throws JsonSyntaxException, MatchException {
                        String $$22 = getValue($$02);
                        if ($$22 != null) {
                            JsonElement $$3 = LenientJsonParser.parse($$22.isEmpty() ? "\"\"" : $$22);
                            DataResult dataResultIfError = $$13.codec().parse(JsonOps.INSTANCE, $$3).ifError($$23 -> {
                                Options.LOGGER.error("Error parsing option value {} for option {}: {}", new Object[]{$$22, $$13, $$23.message()});
                            });
                            Objects.requireNonNull($$13);
                            dataResultIfError.ifSuccess($$13::set);
                        }
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                    @Override // net.minecraft.client.Options.FieldAccess
                    public int process(String $$02, int $$13) throws MatchException {
                        String $$22 = getValue($$02);
                        if ($$22 != null) {
                            try {
                                return Integer.parseInt($$22);
                            } catch (NumberFormatException $$3) {
                                Options.LOGGER.warn("Invalid integer value for option {} = {}", new Object[]{$$02, $$22, $$3});
                            }
                        }
                        return $$13;
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                    @Override // net.minecraft.client.Options.FieldAccess
                    public boolean process(String $$02, boolean $$13) throws MatchException {
                        String $$22 = getValue($$02);
                        return $$22 != null ? Options.isTrue($$22) : $$13;
                    }

                    @Override // net.minecraft.client.Options.FieldAccess
                    public String process(String $$02, String $$13) {
                        return (String) MoreObjects.firstNonNull(getValue($$02), $$13);
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                    @Override // net.minecraft.client.Options.FieldAccess
                    public float process(String $$02, float $$13) throws MatchException {
                        String $$22 = getValue($$02);
                        if ($$22 != null) {
                            if (Options.isTrue($$22)) {
                                return 1.0f;
                            }
                            if (Options.isFalse($$22)) {
                                return 0.0f;
                            }
                            try {
                                return Float.parseFloat($$22);
                            } catch (NumberFormatException $$3) {
                                Options.LOGGER.warn("Invalid floating point value for option {} = {}", new Object[]{$$02, $$22, $$3});
                            }
                        }
                        return $$13;
                    }

                    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
                    @Override // net.minecraft.client.Options.FieldAccess
                    public <T> T process(String $$02, T $$13, Function<String, T> $$22, Function<T, String> $$3) throws MatchException {
                        String $$4 = getValue($$02);
                        return $$4 == null ? $$13 : $$22.apply($$4);
                    }
                });
                $$2.getString("fullscreenResolution").ifPresent($$02 -> {
                    this.fullscreenVideoModeString = $$02;
                });
                KeyMapping.resetMapping();
            } finally {
            }
        } catch (Exception $$3) {
            LOGGER.error("Failed to load options", $$3);
        }
    }

    static boolean isTrue(String $$0) {
        return SnbtOperations.BUILTIN_TRUE.equals($$0);
    }

    static boolean isFalse(String $$0) {
        return SnbtOperations.BUILTIN_FALSE.equals($$0);
    }

    private CompoundTag dataFix(CompoundTag $$0) {
        int $$1 = 0;
        try {
            $$1 = ((Integer) $$0.getString("version").map(Integer::parseInt).orElse(0)).intValue();
        } catch (RuntimeException e) {
        }
        return DataFixTypes.OPTIONS.updateToCurrentVersion(this.minecraft.getFixerUpper(), $$0, $$1);
    }

    public void save() {
        try {
            final PrintWriter $$0 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));
            try {
                $$0.println("version:" + SharedConstants.getCurrentVersion().dataVersion().version());
                processOptions(new FieldAccess(this) { // from class: net.minecraft.client.Options.3
                    public void writePrefix(String $$02) {
                        $$0.print($$02);
                        $$0.print(':');
                    }

                    @Override // net.minecraft.client.Options.OptionAccess
                    public <T> void process(String $$02, OptionInstance<T> $$1) {
                        DataResult dataResultIfError = $$1.codec().encodeStart(JsonOps.INSTANCE, $$1.get()).ifError($$12 -> {
                            Options.LOGGER.error("Error saving option {}: {}", $$1, $$12.message());
                        });
                        PrintWriter printWriter = $$0;
                        dataResultIfError.ifSuccess($$2 -> {
                            writePrefix($$02);
                            printWriter.println(Options.GSON.toJson($$2));
                        });
                    }

                    @Override // net.minecraft.client.Options.FieldAccess
                    public int process(String $$02, int $$1) {
                        writePrefix($$02);
                        $$0.println($$1);
                        return $$1;
                    }

                    @Override // net.minecraft.client.Options.FieldAccess
                    public boolean process(String $$02, boolean $$1) {
                        writePrefix($$02);
                        $$0.println($$1);
                        return $$1;
                    }

                    @Override // net.minecraft.client.Options.FieldAccess
                    public String process(String $$02, String $$1) {
                        writePrefix($$02);
                        $$0.println($$1);
                        return $$1;
                    }

                    @Override // net.minecraft.client.Options.FieldAccess
                    public float process(String $$02, float $$1) {
                        writePrefix($$02);
                        $$0.println($$1);
                        return $$1;
                    }

                    @Override // net.minecraft.client.Options.FieldAccess
                    public <T> T process(String $$02, T $$1, Function<String, T> $$2, Function<T, String> $$3) {
                        writePrefix($$02);
                        $$0.println($$3.apply($$1));
                        return $$1;
                    }
                });
                String $$1 = getFullscreenVideoModeString();
                if ($$1 != null) {
                    $$0.println("fullscreenResolution:" + $$1);
                }
                $$0.close();
            } finally {
            }
        } catch (Exception $$2) {
            LOGGER.error("Failed to save options", $$2);
        }
        broadcastOptions();
    }

    private String getFullscreenVideoModeString() {
        Window $$0 = this.minecraft.getWindow();
        if ($$0 == null) {
            return this.fullscreenVideoModeString;
        }
        if ($$0.getPreferredFullscreenVideoMode().isPresent()) {
            return $$0.getPreferredFullscreenVideoMode().get().write();
        }
        return null;
    }

    public ClientInformation buildPlayerInformation() {
        int $$0 = 0;
        for (PlayerModelPart $$1 : this.modelParts) {
            $$0 |= $$1.getMask();
        }
        return new ClientInformation(this.languageCode, this.renderDistance.get().intValue(), this.chatVisibility.get(), this.chatColors.get().booleanValue(), $$0, this.mainHand.get(), this.minecraft.isTextFilteringEnabled(), this.allowServerListing.get().booleanValue(), this.particles.get());
    }

    public void broadcastOptions() {
        if (this.minecraft.player != null) {
            this.minecraft.player.connection.broadcastClientInformation(buildPlayerInformation());
        }
    }

    public void setModelPart(PlayerModelPart $$0, boolean $$1) {
        if ($$1) {
            this.modelParts.add($$0);
        } else {
            this.modelParts.remove($$0);
        }
    }

    public boolean isModelPartEnabled(PlayerModelPart $$0) {
        return this.modelParts.contains($$0);
    }

    public CloudStatus getCloudsType() {
        return this.cloudStatus.get();
    }

    public boolean useNativeTransport() {
        return this.useNativeTransport;
    }

    public void loadSelectedResourcePacks(PackRepository $$0) {
        Set<String> $$1 = Sets.newLinkedHashSet();
        Iterator<String> $$2 = this.resourcePacks.iterator();
        while ($$2.hasNext()) {
            String $$3 = $$2.next();
            Pack $$4 = $$0.getPack($$3);
            if ($$4 == null && !$$3.startsWith("file/")) {
                $$4 = $$0.getPack("file/" + $$3);
            }
            if ($$4 == null) {
                LOGGER.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", $$3);
                $$2.remove();
            } else if (!$$4.getCompatibility().isCompatible() && !this.incompatibleResourcePacks.contains($$3)) {
                LOGGER.warn("Removed resource pack {} from options because it is no longer compatible", $$3);
                $$2.remove();
            } else if ($$4.getCompatibility().isCompatible() && this.incompatibleResourcePacks.contains($$3)) {
                LOGGER.info("Removed resource pack {} from incompatibility list because it's now compatible", $$3);
                this.incompatibleResourcePacks.remove($$3);
            } else {
                $$1.add($$4.getId());
            }
        }
        $$0.setSelected($$1);
    }

    public CameraType getCameraType() {
        return this.cameraType;
    }

    public void setCameraType(CameraType $$0) {
        this.cameraType = $$0;
    }

    private static List<String> readListOfStrings(String $$0) {
        List<String> $$1 = (List) GsonHelper.fromNullableJson(GSON, $$0, LIST_OF_STRINGS_TYPE);
        return $$1 != null ? $$1 : Lists.newArrayList();
    }

    public File getFile() {
        return this.optionsFile;
    }

    public String dumpOptionsForReport() {
        final List<Pair<String, Object>> $$0 = new ArrayList<>();
        processDumpedOptions(new OptionAccess(this) { // from class: net.minecraft.client.Options.4
            @Override // net.minecraft.client.Options.OptionAccess
            public <T> void process(String $$02, OptionInstance<T> $$1) {
                $$0.add(Pair.of($$02, $$1.get()));
            }
        });
        $$0.add(Pair.of("fullscreenResolution", String.valueOf(this.fullscreenVideoModeString)));
        $$0.add(Pair.of("glDebugVerbosity", Integer.valueOf(this.glDebugVerbosity)));
        $$0.add(Pair.of("overrideHeight", Integer.valueOf(this.overrideHeight)));
        $$0.add(Pair.of("overrideWidth", Integer.valueOf(this.overrideWidth)));
        $$0.add(Pair.of("syncChunkWrites", Boolean.valueOf(this.syncWrites)));
        $$0.add(Pair.of("useNativeTransport", Boolean.valueOf(this.useNativeTransport)));
        $$0.add(Pair.of("resourcePacks", this.resourcePacks));
        return (String) $$0.stream().sorted(Comparator.comparing((v0) -> {
            return v0.getFirst();
        })).map($$02 -> {
            return ((String) $$02.getFirst()) + ": " + String.valueOf($$02.getSecond());
        }).collect(Collectors.joining(System.lineSeparator()));
    }

    public void setServerRenderDistance(int $$0) {
        this.serverRenderDistance = $$0;
    }

    public int getEffectiveRenderDistance() {
        return this.serverRenderDistance > 0 ? Math.min(this.renderDistance.get().intValue(), this.serverRenderDistance) : this.renderDistance.get().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Component pixelValueLabel(Component $$0, int $$1) {
        return Component.translatable("options.pixel_value", $$0, Integer.valueOf($$1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Component percentValueLabel(Component $$0, double $$1) {
        return Component.translatable("options.percent_value", $$0, Integer.valueOf((int) ($$1 * 100.0d)));
    }

    public static Component genericValueLabel(Component $$0, Component $$1) {
        return Component.translatable("options.generic_value", $$0, $$1);
    }

    public static Component genericValueLabel(Component $$0, int $$1) {
        return genericValueLabel($$0, Component.literal(Integer.toString($$1)));
    }

    public static Component genericValueOrOffLabel(Component $$0, int $$1) {
        if ($$1 == 0) {
            return genericValueLabel($$0, CommonComponents.OPTION_OFF);
        }
        return genericValueLabel($$0, $$1);
    }

    private static Component percentValueOrOffLabel(Component $$0, double $$1) {
        if ($$1 == Density.SURFACE) {
            return genericValueLabel($$0, CommonComponents.OPTION_OFF);
        }
        return percentValueLabel($$0, $$1);
    }
}
