package net.labymod.core.client.gui.hud;

import com.google.gson.JsonObject;
import java.util.Locale;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.debug.DebugFlags;
import net.labymod.api.event.client.gui.hud.HudWidgetRegisterEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.Implements;
import net.labymod.api.revision.Revision;
import net.labymod.api.revision.RevisionRegistry;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.core.client.gui.hud.hudwidget.ActionBarHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.BlockBreakHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.BossBarHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.DangerWarnerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.DirectionHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.EconomyDisplayHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.InventoryTrackerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.ItemCounterHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.PaperDollHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.PotionEffectHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.SaturationHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.ScoreboardHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.TitleHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.ArrowHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.ChestHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.FeetHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.HelmetHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.LegsHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.MainHandHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.OffHandHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.test.TestHeightTextHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.test.TestIconTextHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.AfkTimerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.BiomeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClickTestHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClockHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ComboHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.CoordinateHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.CpuTemperatureHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.DateHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.EntityCountHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.FDirectionHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.FpsHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.LightLevelHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.MemoryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PingHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PlayerCountHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PlaytimeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.RangeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.RotationHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ServerAddressHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SpeedHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SystemBatteryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SystemCpuHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SystemMemoryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.TargetedBlockHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.WorldTimeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceTikTokHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceTwitchHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceYouTubeHudWidget;
import net.labymod.core.client.render.font.text.TextUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/DefaultHudWidgetRegistry.class */
@Singleton
@Implements(HudWidgetRegistry.class)
public class DefaultHudWidgetRegistry extends DefaultRegistry<HudWidget<?>> implements HudWidgetRegistry {
    private static final String DEFAULT_PROFILE = "default";
    private final LabyAPI labyAPI;
    private final HudWidgetCategoryRegistry categoryRegistry;
    private final JsonConfigLoader configLoader = new JsonConfigLoader(Constants.Files.CONFIGS);
    private final RevisionRegistry revisionRegistry;
    private String selectedProfile;
    private DefaultGlobalHudWidgetConfig globalHudWidgetConfig;
    private RootSettingRegistry globalHudWidgetSettingRegistry;

    @Inject
    public DefaultHudWidgetRegistry(LabyAPI labyAPI, HudWidgetCategoryRegistry categoryRegistry, RevisionRegistry revisionRegistry) {
        this.labyAPI = labyAPI;
        this.categoryRegistry = categoryRegistry;
        this.revisionRegistry = revisionRegistry;
        selectProfile(DEFAULT_PROFILE);
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public void registerDefaults() {
        this.labyAPI.eventBus().registerListener(new HudWidgetListener(this));
        this.categoryRegistry.register(HudWidgetCategory.INGAME);
        this.categoryRegistry.register(HudWidgetCategory.ITEM);
        this.categoryRegistry.register(HudWidgetCategory.SYSTEM);
        this.categoryRegistry.register(HudWidgetCategory.SERVICE);
        this.categoryRegistry.register(HudWidgetCategory.MISCELLANEOUS);
        register(FpsHudWidget::new);
        register(CoordinateHudWidget::new);
        register(RotationHudWidget::new);
        register(MemoryHudWidget::new);
        register(AfkTimerHudWidget::new);
        register(BiomeHudWidget::new);
        register(ClickTestHudWidget::new);
        register(ClockHudWidget::new);
        register(ComboHudWidget::new);
        register(DateHudWidget::new);
        register(EntityCountHudWidget::new);
        register(FDirectionHudWidget::new);
        register(PingHudWidget::new);
        register(PlayerCountHudWidget::new);
        register(RangeHudWidget::new);
        register(ServerAddressHudWidget::new);
        register(SpeedHudWidget::new);
        register(SystemBatteryHudWidget::new);
        register(SystemCpuHudWidget::new);
        register(SystemMemoryHudWidget::new);
        register(WorldTimeHudWidget::new);
        register(PlaytimeHudWidget::new);
        register(LightLevelHudWidget::new);
        register(TargetedBlockHudWidget::new);
        if (!MinecraftVersions.V1_16_5.orOlder()) {
            register(CpuTemperatureHudWidget::new);
        }
        register(ServiceTikTokHudWidget::new);
        register(ServiceTwitchHudWidget::new);
        register(ServiceYouTubeHudWidget::new);
        register(ArrowHudWidget::new);
        register(MainHandHudWidget::new);
        if (!PlatformEnvironment.isAncientPvPVersion()) {
            register(OffHandHudWidget::new);
        }
        register(HelmetHudWidget::new);
        register(ChestHudWidget::new);
        register(LegsHudWidget::new);
        register(FeetHudWidget::new);
        register(PotionEffectHudWidget::new);
        register(SaturationHudWidget::new);
        register(ScoreboardHudWidget::new);
        register(PaperDollHudWidget::new);
        register(InventoryTrackerHudWidget::new);
        register(ActionBarHudWidget::new);
        register(BossBarHudWidget::new);
        register(DirectionHudWidget::new);
        register(TitleHudWidget::new);
        register(ItemCounterHudWidget::new);
        register(BlockBreakHudWidget::new);
        register(EconomyDisplayHudWidget::new);
        register(DangerWarnerHudWidget::new);
        register(WailaHudWidget::new);
        if (DebugFlags.TEST_HUD_WIDGETS) {
            registerTextHudWidgets();
        }
    }

    @Override // net.labymod.api.service.Registry
    public void register(HudWidget<?> hudWidget) {
        Class<?> cls = hudWidget.getClass();
        String namespace = this.labyAPI.getNamespace(cls);
        super.register(hudWidget);
        this.labyAPI.eventBus().registerListener(hudWidget);
        try {
            HudWidgetConfig config = loadConfig(hudWidget);
            hudWidget.load(config);
            updateLinkedWidgets(hudWidget);
            Laby.fireEvent(new HudWidgetRegisterEvent(hudWidget));
            SpriteSlot spriteSlot = (SpriteSlot) cls.getAnnotation(SpriteSlot.class);
            if (spriteSlot != null) {
                Theme theme = Laby.labyAPI().themeService().currentTheme();
                Locale locale = Locale.ROOT;
                Object[] objArr = new Object[1];
                objArr[0] = spriteSlot.page() == 0 ? "" : "_" + spriteSlot.page();
                ResourceLocation spriteResource = theme.resource(namespace, String.format(locale, "textures/settings/hud/hud%s.png", objArr));
                Icon icon = Icon.sprite(spriteResource, spriteSlot.x() * spriteSlot.size(), spriteSlot.y() * spriteSlot.size(), spriteSlot.size(), spriteSlot.size(), 128, 128);
                hudWidget.setIcon(icon);
            }
            IntroducedIn newFeature = (IntroducedIn) cls.getAnnotation(IntroducedIn.class);
            if (newFeature != null) {
                Revision revision = this.revisionRegistry.getRevision(namespace, newFeature.value());
                hudWidget.setRevision(revision);
            }
            PermissionRequired permissionRequired = (PermissionRequired) cls.getAnnotation(PermissionRequired.class);
            if (permissionRequired != null) {
                hudWidget.setPermission(new SettingPermissionHolder(permissionRequired));
            }
        } catch (Throwable throwable) {
            LOGGER.error("An error occurred while registering the hud widget '{}'", hudWidget.getId(), throwable);
            LOGGER.error("Hud widget will not be registered!", new Object[0]);
            unregister(hudWidget.getId());
        }
    }

    @Override // net.labymod.api.service.DefaultRegistry, net.labymod.api.service.Registry
    public void unregister(String id) {
        HudWidget<?> hudWidget = getById(id);
        if (hudWidget == null) {
            return;
        }
        super.unregister(id);
        this.labyAPI.eventBus().unregisterListener(hudWidget);
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public void selectProfile(String profile) {
        this.selectedProfile = profile;
        this.configLoader.setVariable("$PROFILE", profile);
        try {
            this.globalHudWidgetConfig = (DefaultGlobalHudWidgetConfig) this.configLoader.load(DefaultGlobalHudWidgetConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            this.globalHudWidgetConfig = new DefaultGlobalHudWidgetConfig();
        }
        for (HudWidget<?> hudWidget : values()) {
            try {
                HudWidgetConfig config = loadConfig(hudWidget);
                hudWidget.load(config);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.globalHudWidgetSettingRegistry = this.globalHudWidgetConfig.asRegistry("global").translationId("hudWidget.global");
        this.globalHudWidgetSettingRegistry.initialize();
        saveConfig();
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public String getSelectedProfile() {
        return this.selectedProfile;
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public void updateLinkedWidgets(HudWidget<?> hudWidget) {
        HudWidget<?> parent = getById(hudWidget.getConfig().getParentId());
        if (parent != null) {
            hudWidget.updateParent(parent);
        }
        for (HudWidget<?> other : values()) {
            String parentId = other.getConfig().getParentId();
            if (Objects.equals(parentId, hudWidget.getId())) {
                hudWidget.updateChild(other);
            }
        }
        for (HudWidget<?> value : values()) {
            if (value.isEnabled() && value.getParent() != null && !value.getParent().isEnabled()) {
                value.updateParent(null);
            }
        }
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public void saveConfig() {
        for (HudWidget<?> hudWidget : values()) {
            this.globalHudWidgetConfig.getConfigs().put(hudWidget.getId(), this.configLoader.getGson().toJsonTree(hudWidget.getConfig()).getAsJsonObject());
        }
        this.configLoader.save(this.globalHudWidgetConfig);
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public GlobalHudWidgetConfig globalHudWidgetConfig() {
        return this.globalHudWidgetConfig;
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public RootSettingRegistry globalHudWidgetSettingRegistry() {
        return this.globalHudWidgetSettingRegistry;
    }

    private HudWidgetConfig loadConfig(HudWidget<?> hudWidget) throws Exception {
        JsonObject configObject = this.globalHudWidgetConfig.getConfigs().get(hudWidget.getId());
        if (configObject != null) {
            return (HudWidgetConfig) this.configLoader.getGson().fromJson(configObject, hudWidget.getConfigClass());
        }
        HudWidgetConfig config = (HudWidgetConfig) hudWidget.getConfigClass().getConstructor(new Class[0]).newInstance(new Object[0]);
        hudWidget.initializePreConfigured(config);
        this.globalHudWidgetConfig.getConfigs().put(hudWidget.getId(), this.configLoader.getGson().toJsonTree(config).getAsJsonObject());
        return config;
    }

    public void reloadConfigOfEnabledHudWidgets() {
        for (HudWidget<?> hudWidget : values()) {
            if (hudWidget.isEnabled()) {
                hudWidget.reloadConfig();
            }
        }
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public HudWidgetCategoryRegistry categoryRegistry() {
        return this.categoryRegistry;
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRegistry
    public void updateHudWidgets(@NotNull String reason) {
        TextUtil.pushAndApplyAttributes();
        for (HudWidget<?> hudWidget : values()) {
            updateLinkedWidgets(hudWidget);
            if (hudWidget.isEnabled()) {
                hudWidget.requestUpdate(reason);
            }
        }
        TextUtil.popRenderAttributes();
    }

    private void registerTextHudWidgets() {
        register(TestIconTextHudWidget::new);
        register(TestHeightTextHudWidget::new);
    }
}
