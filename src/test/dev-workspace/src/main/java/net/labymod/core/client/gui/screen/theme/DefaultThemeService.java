package net.labymod.core.client.gui.screen.theme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.api.client.gui.screen.theme.ThemeConfigAccessor;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.widget.widgets.input.AdvancedSelectionWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeLoadEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeRegisterEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUnregisterEvent;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/DefaultThemeService.class */
@Singleton
@Implements(ThemeService.class)
public class DefaultThemeService implements ThemeService {
    private static final float THUMBNAIL_ASPECT_RATIO = 1.7777778f;
    private final LabyAPI labyAPI;
    private final ResourcesReloadWatcher resourcesReloadWatcher;
    private final LinkMetaLoader linkMetaLoader;
    private final StyleSheetLoader styleSheetLoader;
    private Theme currentTheme;
    private boolean initialized = false;
    private final Set<Theme> themes = new HashSet();

    @Inject
    public DefaultThemeService(LabyAPI labyAPI, ResourcesReloadWatcher resourcesReloadWatcher, LinkMetaLoader linkMetaLoader, StyleSheetLoader styleSheetLoader) {
        this.labyAPI = labyAPI;
        this.resourcesReloadWatcher = resourcesReloadWatcher;
        this.linkMetaLoader = linkMetaLoader;
        this.styleSheetLoader = styleSheetLoader;
        labyAPI.eventBus().registerListener(this);
    }

    private void registerInternalThemes() {
        registerTheme(new VanillaTheme());
        registerTheme(new FancyTheme());
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public void initialize() {
        registerInternalThemes();
        this.currentTheme = getThemeByName(VanillaTheme.ID);
        changeTheme(this.labyAPI.config().appearance().theme().get(), true);
        this.initialized = true;
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public void registerTheme(Theme theme) {
        ThemeRegisterEvent event = new ThemeRegisterEvent(theme);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        this.themes.add(theme);
        theme.getOrLoadThemeConfig();
        updateSettingDropdown();
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public void unregisterTheme(Theme theme) {
        ThemeUnregisterEvent event = new ThemeUnregisterEvent(theme);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        this.themes.remove(theme);
        theme.saveThemeConfig();
        updateSettingDropdown();
    }

    private void updateSettingDropdown() {
        AbstractSettingRegistry registry = this.labyAPI.coreSettingRegistry();
        registry.findSetting((CharSequence) "appearance.theme").ifPresent(setting -> {
            updateSettingDropdown(setting.asElement());
        });
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public Theme changeTheme(String name) {
        return changeTheme(name, false);
    }

    public Theme changeTheme(String name, boolean force) {
        Theme previousTheme = this.currentTheme;
        Theme newTheme = getThemeByName(name);
        if (newTheme == null) {
            throw new IllegalArgumentException("Theme not found: " + name);
        }
        if (previousTheme.equals(newTheme) && !force) {
            return this.currentTheme;
        }
        Theme newTheme2 = ((ThemeChangeEvent) Laby.fireEvent(new ThemeChangeEvent(this.currentTheme, newTheme, Phase.PRE))).newTheme();
        changeThemeInternal(previousTheme, newTheme2, force);
        Laby.fireEvent(new ThemeChangeEvent(previousTheme, newTheme2, Phase.POST));
        return newTheme2;
    }

    private void changeThemeInternal(Theme previousTheme, Theme newTheme, boolean force) {
        previousTheme.onUnload();
        this.currentTheme = newTheme;
        updateSettingDropdown();
        if (!force && newTheme.isResourcesLoaded()) {
            this.labyAPI.minecraft().reloadResources();
        }
        newTheme.onPreLoad();
        Laby.fireEvent(new ThemeLoadEvent(ThemeLoadEvent.Phase.PRE, newTheme));
        this.resourcesReloadWatcher.addOrExecuteInitializeListener(() -> {
            themeLoaded(newTheme);
        });
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
        reloadActivities();
    }

    public void reloadActivities() {
        Window window = this.labyAPI.minecraft().minecraftWindow();
        ScreenWrapper screen = window.currentScreen();
        if (screen != null) {
            renewScreen(window, screen);
        }
        IngameActivityOverlay ingameActivityOverlay = this.labyAPI.ingameOverlay();
        if (ingameActivityOverlay != null) {
            for (IngameOverlayActivity activity : ingameActivityOverlay.getActivities()) {
                activity.reloadMarkup();
                activity.reload();
            }
        }
        ScreenOverlayHandler screenOverlayHandler = this.labyAPI.screenOverlayHandler();
        if (screenOverlayHandler != null) {
            for (ScreenOverlay overlay : screenOverlayHandler.overlays()) {
                overlay.reloadOverlayStyles();
            }
        }
    }

    private void themeLoaded(Theme theme) {
        theme.onPostLoad();
        Laby.fireEvent(new ThemeLoadEvent(ThemeLoadEvent.Phase.POST, theme));
        Laby.references().widgetConverterRegistry().initializeStyle();
        this.linkMetaLoader.loadStyleSheets();
    }

    private void renewScreen(Window window, ScreenWrapper screen) {
        Activity activity;
        Activity newActivity;
        screen.initialize(window);
        if (screen.isActivity() && (newActivity = renewActivity((activity = screen.asActivity()))) != null) {
            activity.redirectScreen(newActivity);
        }
    }

    @Deprecated
    private Activity renewActivity(Activity activity) {
        Object previousScreen = activity.getPreviousScreen();
        if ((previousScreen instanceof Activity) && previousScreen != activity) {
            previousScreen = renewActivity((Activity) previousScreen);
        }
        activity.setPreviousScreen(previousScreen);
        activity.reloadMarkup();
        return activity;
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public void reload() {
        reload(false);
    }

    public void reload(boolean debugReload) {
        Theme currentTheme = currentTheme();
        reload(currentTheme.getId(), debugReload);
    }

    public void reload(String themeName, boolean debugReload) {
        this.styleSheetLoader.invalidate();
        for (Theme theme : (Theme[]) this.themes.toArray(new Theme[0])) {
            unregisterTheme(theme);
        }
        registerInternalThemes();
        changeTheme(themeName, debugReload);
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    @NotNull
    public Theme currentTheme() {
        return this.currentTheme;
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    public Theme getThemeByName(String name) {
        for (Theme theme : this.themes) {
            if (theme.getId().equalsIgnoreCase(name)) {
                return theme;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeService
    @Nullable
    public <T extends ThemeConfigAccessor> T getThemeConfig(Theme theme, Class<T> configClass) {
        if (!configClass.isAssignableFrom(theme.getConfigClass())) {
            throw new IllegalArgumentException("Invalid theme config class supplied");
        }
        ThemeConfig themeConfig = theme.getOrLoadThemeConfig();
        if (themeConfig == null || !configClass.isAssignableFrom(themeConfig.getClass())) {
            return null;
        }
        return themeConfig;
    }

    @Subscribe
    public void onConfigurationSave(ConfigurationSaveEvent event) {
        this.themes.forEach((v0) -> {
            v0.saveThemeConfig();
        });
    }

    @Subscribe
    public void onSettingInitialize(SettingInitializeEvent event) {
        Setting setting = event.setting();
        if (setting.getPath().equals("settings.appearance.theme")) {
            updateSettingDropdown(setting.asElement());
        }
    }

    private void updateSettingDropdown(SettingElement setting) {
        ThemeConfig themeConfig;
        setting.getElements().clear();
        if (this.currentTheme != null && (themeConfig = this.currentTheme.getOrLoadThemeConfig()) != null) {
            setting.addSettings(themeConfig);
            for (Setting value : setting.values()) {
                if (value.isElement()) {
                    value.asElement().setCustomTranslation(String.format(Locale.ROOT, "%s.theme.%s.%s", this.currentTheme.getNamespace(), this.currentTheme.getId(), value.getTranslationId()));
                }
            }
        }
        List<Theme> themes = new ArrayList<>(this.themes);
        themes.sort(Comparator.comparing((v0) -> {
            return v0.getId();
        }));
        for (Widget w : setting.getWidgets()) {
            if (w instanceof AdvancedSelectionWidget) {
                AdvancedSelectionWidget<String> widget = (AdvancedSelectionWidget) w;
                widget.setActionListener("theme", () -> {
                    changeTheme((String) widget.getSelected());
                    reload();
                });
                widget.clear();
                for (Theme theme : themes) {
                    Icon thumbnail = Icon.texture(theme.resource(theme.getNamespace(), "textures/thumbnail.png")).aspectRatio(THUMBNAIL_ASPECT_RATIO);
                    widget.add(theme.getId(), thumbnail);
                }
                widget.setStringParser(id -> {
                    Theme theme2 = getThemeByName(id);
                    return Component.text(theme2 == null ? id : theme2.getDisplayName());
                });
            }
        }
    }
}
