package net.labymod.api.addon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.addon.exception.AddonInvalidException;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.addon.lifecycle.AddonEnableEvent;
import net.labymod.api.event.addon.lifecycle.AddonPostEnableEvent;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/LabyAddon.class */
public abstract class LabyAddon<T extends AddonConfig> {
    private final LabyAddon<T>.LabyAddonConfigProvider<? extends T> configProvider;
    private ReferenceStorageAccessor referenceStorageAccessor;
    private LabyAddon<T> instance;
    private InstalledAddonInfo addonInfo;
    private boolean registeredCategory;
    private boolean loadedInRuntime;
    private final Logging logging = Logging.create(getClass());
    private final Map<Class<? extends ConfigAccessor>, LabyAddon<T>.LabyAddonConfigProvider<?>> customConfigProviders = new HashMap();
    private final LabyAPI labyAPI = Laby.labyAPI();

    protected abstract void enable();

    protected abstract Class<? extends T> configurationClass();

    protected LabyAddon() {
        if (configurationClass() == null) {
            addonExceptionMessage("The addons configuration class is null!", new Object[0]);
        }
        preConfigurationLoad();
        this.configProvider = new LabyAddonConfigProvider<>(this, configurationClass());
        this.configProvider.safeLoad(JsonConfigLoader.createDefault());
        if (configuration().enabled() == null) {
            addonExceptionMessage("The addon has to implement the enabled setting inherited by AddonConfig", new Object[0]);
        }
    }

    public final T configuration() {
        return this.configProvider.get();
    }

    public final LabyAPI labyAPI() {
        return this.labyAPI;
    }

    public final Logging logger() {
        return this.logging;
    }

    public final InstalledAddonInfo addonInfo() {
        return this.addonInfo;
    }

    public final boolean wasLoadedInRuntime() {
        return this.loadedInRuntime;
    }

    public final void sendMessage(String message) {
        this.labyAPI.minecraft().chatExecutor().chat(message);
    }

    public final void displayMessage(String message) {
        displayMessage(Component.text(message));
    }

    public final void displayMessage(Component message) {
        this.labyAPI.minecraft().chatExecutor().displayClientMessage(message);
    }

    protected void preConfigurationLoad() {
    }

    protected void load() {
    }

    protected final void registerSettingCategory() {
        if (this.registeredCategory) {
            addonExceptionMessage("Cannot register the same category twice", new Object[0]);
        }
        AddonConfig addonConfigConfiguration = configuration();
        if (addonConfigConfiguration == null) {
            addonExceptionMessage("Cannot register the category because configuration is null", new Object[0]);
        }
        RootSettingRegistry registry = RootSettingRegistry.addon(this, addonConfigConfiguration);
        this.labyAPI.coreSettingRegistry().addSetting(registry);
        this.registeredCategory = true;
    }

    protected final void registerListener(@NotNull Object listener) {
        Objects.requireNonNull(listener, "Listener");
        this.labyAPI.eventBus().registerListener(listener);
    }

    protected final void registerLegacyConverter(@NotNull LegacyConverter<?> legacyConverter) {
        Objects.requireNonNull(legacyConverter, "legacyConverter");
        Laby.references().legacyConfigConverter().register(legacyConverter);
    }

    protected final void registerCommand(@NotNull Command command) {
        Objects.requireNonNull(command, "Command");
        this.labyAPI.commandService().register(command);
    }

    public final void saveConfiguration() throws ConfigurationSaveException {
        this.configProvider.safeSave();
    }

    protected final <C extends ConfigAccessor> C addCustomConfiguration(@NotNull Class<C> cls) throws ConfigurationLoadException {
        Objects.requireNonNull(cls, "Custom Configuration Class");
        Class<? extends T> clsConfigurationClass = configurationClass();
        if (clsConfigurationClass == cls) {
            addonExceptionMessage("Cannot add the main addon configuration as custom configuration", new Object[0]);
        }
        if (this.customConfigProviders.containsKey(cls)) {
            addonExceptionMessage("The custom configuration %s was already loaded", cls.getName());
        }
        String name = ConfigLoader.getName(clsConfigurationClass);
        if (name.equals(ConfigLoader.getName(cls))) {
            addonExceptionMessage("Configuration %s has the same ConfigName as the main configuration", cls.getName());
        }
        Iterator<Class<? extends ConfigAccessor>> it = this.customConfigProviders.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Class<? extends ConfigAccessor> next = it.next();
            if (name.equals(ConfigLoader.getName(next))) {
                addonExceptionMessage("Configuration %s has the same ConfigName as the custom configuration %s", cls.getName(), next);
                break;
            }
        }
        LabyAddon<T>.LabyAddonConfigProvider<?> labyAddonConfigProvider = new LabyAddonConfigProvider<>(this, cls);
        C c = (C) labyAddonConfigProvider.safeLoad(JsonConfigLoader.createDefault());
        this.customConfigProviders.put(cls, labyAddonConfigProvider);
        return c;
    }

    protected final <C extends ConfigAccessor> void saveCustomConfiguration(@NotNull Class<C> configurationClass) throws ConfigurationSaveException {
        Objects.requireNonNull(configurationClass, "Custom Configuration Class");
        ConfigProvider<?> customConfigProvider = this.customConfigProviders.get(configurationClass);
        if (customConfigProvider == null) {
            addonExceptionMessage("Cannot save the custom configuration %s as it isn't declared as an custom configuration", configurationClass.getName());
        }
        customConfigProvider.safeSave();
    }

    @Subscribe
    public final void onAddonLoad(AddonEnableEvent event) {
        this.loadedInRuntime = this.labyAPI.isFullyInitialized();
        this.addonInfo = event.addon().info();
        this.instance = (LabyAddon) event.getInstance();
        this.referenceStorageAccessor = event.getReferenceStorageAccessor();
        if (configuration() != null) {
            this.labyAPI.addonService().registerMainConfiguration(this.addonInfo.getNamespace(), configuration());
        }
        try {
            load();
        } catch (Exception e) {
            addonException("Failed to load the addon", e);
        }
    }

    @Subscribe
    public final void onAddonInitialize(AddonPostEnableEvent event) {
        try {
            enable();
        } catch (Exception e) {
            addonException("Failed to enable the addon", e);
        }
    }

    @Subscribe
    public final void onAddonSettingsSave(ConfigurationSaveEvent event) {
        saveConfiguration();
    }

    private void addonExceptionMessage(String message, Object... arguments) {
        String message2 = String.format(Locale.ROOT, message, arguments);
        addonException(message2, new AddonInvalidException(message2));
    }

    private void addonException(String message, Exception exception) throws RuntimeException {
        if (this.labyAPI.labyModLoader().isAddonDevelopmentEnvironment() && (this.addonInfo == null || this.addonInfo.getNamespace().equals(this.labyAPI.addonService().getClassPathAddon()))) {
            this.labyAPI.minecraft().crashGame(message, exception);
        } else if (!(exception instanceof RuntimeException)) {
            throw new RuntimeException(exception);
        }
    }

    @Deprecated
    protected final <A extends LabyAddon<T>> A getAddonInstance() {
        if (this.instance == null) {
            return null;
        }
        return this.instance;
    }

    @NotNull
    protected final <A extends LabyAddon<T>> A addonInstance() {
        if (this.instance == null) {
            throw new NullPointerException("Addon instance is not initialized yet!");
        }
        return this.instance;
    }

    @Deprecated
    protected final <R extends ReferenceStorageAccessor> R getReferenceStorageAccessor() {
        if (this.referenceStorageAccessor == null) {
            return null;
        }
        return (R) this.referenceStorageAccessor;
    }

    @NotNull
    protected final <R extends ReferenceStorageAccessor> R referenceStorageAccessor() {
        if (this.referenceStorageAccessor == null) {
            throw new NullPointerException("Reference storage is not initialized yet!");
        }
        return (R) this.referenceStorageAccessor;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/LabyAddon$LabyAddonConfigProvider.class */
    public class LabyAddonConfigProvider<C extends ConfigAccessor> extends ConfigProvider<C> {
        private final Class<C> configurationClass;

        private LabyAddonConfigProvider(LabyAddon this$0, Class<C> configurationClass) {
            this.configurationClass = configurationClass;
        }

        @Override // net.labymod.api.configuration.loader.ConfigProvider
        protected Class<C> getType() {
            return this.configurationClass;
        }
    }
}
