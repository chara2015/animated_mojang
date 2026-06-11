package net.labymod.core.configuration.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.configuration.converter.addon.CustomNameTagsAddonResolver;
import net.labymod.core.configuration.converter.addon.DirectoryLegacyAddonResolver;
import net.labymod.core.configuration.converter.addon.OptifineLegacyAddonResolver;
import net.labymod.core.configuration.converter.converter.LegacyAccountConverter;
import net.labymod.core.configuration.converter.converter.LegacyAutoTextConverter;
import net.labymod.core.configuration.converter.converter.LegacyChatFilterConverter;
import net.labymod.core.configuration.converter.converter.LegacyLabyModuleConverter;
import net.labymod.core.configuration.converter.converter.LegacyPlayerMenuConverter;
import net.labymod.core.configuration.converter.converter.LegacySettingConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/DefaultLegacyConfigConverter.class */
@Singleton
@Implements(LegacyConfigConverter.class)
public class DefaultLegacyConfigConverter implements LegacyConfigConverter {
    private static final Logging LOGGER = Logging.create((Class<?>) DefaultLegacyConfigConverter.class);
    private final Map<String, String> legacyToModernAddons = new HashMap();
    private final Map<String, LegacyAddonConverter.Version> addonNamespaceVersions = new HashMap();
    private final Collection<LegacyAddonResolver> addonResolvers = new ArrayList();
    private final List<LegacyConverter<?>> legacyConverters = new ArrayList();
    private final boolean legacyDirectoryExists = IOUtil.exists(LegacyConverter.LEGACY_PATH);

    public DefaultLegacyConfigConverter() {
        registerDefaults();
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public void register(@NotNull LegacyConverter<?> legacyConverter) {
        Objects.requireNonNull(legacyConverter, "legacyConverter");
        this.legacyConverters.add(legacyConverter);
        if (!(legacyConverter instanceof LegacyAddonConverter)) {
            legacyConverter.load();
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public void registerAddon(@NotNull String namespace, @NotNull String... legacyUuids) {
        for (String legacyUuid : legacyUuids) {
            this.legacyToModernAddons.put(legacyUuid, namespace);
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public void registerAddonResolver(@NotNull LegacyAddonResolver resolver) {
        this.addonResolvers.add(resolver);
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    @Nullable
    public String getModernAddonNamespace(@NotNull String legacyUuid) {
        return this.legacyToModernAddons.get(legacyUuid);
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    @NotNull
    public Collection<LegacyConverter<?>> getConverters() {
        return this.legacyConverters;
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    @NotNull
    public Collection<LegacyConverter<?>> getConverters(@NotNull String namespace) {
        return (Collection) this.legacyConverters.stream().filter(converter -> {
            return converter.getNamespace().equals(namespace);
        }).collect(Collectors.toList());
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public boolean hasLegacyInstallation() {
        return this.legacyDirectoryExists;
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public boolean hasStuffToConvert(@NotNull String namespace) {
        if (!hasLegacyInstallation()) {
            return false;
        }
        for (LegacyConverter<?> converter : getConverters(namespace)) {
            prepareConverter(converter);
            try {
            } catch (Exception e) {
                LOGGER.error("An error occurred while testing whether {} has stuff to convert", converter.getClass().getSimpleName(), e);
            }
            if (!converter.isEmpty() && converter.hasStuffToConvert()) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public boolean wasConversionAsked(@NotNull String namespace) {
        ThreadSafe.ensureRenderThread();
        return Laby.labyAPI().config().other().conversionAskedNamespaces().get().contains(namespace);
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public void setConversionAsked(@NotNull String namespace) {
        ThreadSafe.ensureRenderThread();
        Laby.labyAPI().config().other().conversionAskedNamespaces().get().add(namespace);
        Laby.fireEvent(new ConfigurationSaveEvent());
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public void useVersion(@NotNull String addonNamespace, @NotNull LegacyAddonConverter.Version version) {
        this.addonNamespaceVersions.put(addonNamespace, version);
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    public int convert(@NotNull String namespace) {
        Collection<LegacyConverter<?>> converters = getConverters(namespace);
        int executed = 0;
        for (LegacyConverter<?> converter : converters) {
            prepareConverter(converter);
            if (!converter.isEmpty()) {
                try {
                    converter.convert();
                    executed++;
                } catch (Exception e) {
                    LOGGER.error("An error occurred while converting with {}", converter.getClass().getSimpleName(), e);
                }
            }
        }
        return executed;
    }

    private void prepareConverter(LegacyConverter<?> converter) {
        if (!(converter instanceof LegacyAddonConverter) || converter.wasLoaded()) {
            return;
        }
        LegacyAddonConverter<?> addonConverter = (LegacyAddonConverter) converter;
        LegacyAddonConverter.Version version = this.addonNamespaceVersions.get(converter.getNamespace());
        if (version != null) {
            addonConverter.load(version);
            return;
        }
        for (LegacyAddonConverter.Version v : LegacyAddonConverter.Version.values()) {
            addonConverter.load(v);
            if (addonConverter.getValue() != null) {
                return;
            }
        }
    }

    private void registerDefaults() {
        LegacySettingConverter settingConverter = new LegacySettingConverter();
        register(settingConverter);
        register(new LegacyAutoTextConverter());
        register(new LegacyAccountConverter());
        register(new LegacyPlayerMenuConverter());
        register(new LegacyChatFilterConverter(settingConverter));
        register(new LegacyLabyModuleConverter());
        registerAddonResolver(new DirectoryLegacyAddonResolver());
        registerAddonResolver(new CustomNameTagsAddonResolver());
        registerAddonResolver(new OptifineLegacyAddonResolver());
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("legacyAddons.json");
            try {
                if (inputStream == null) {
                    LOGGER.error("Cannot find legacy addons file on classpath", new Object[0]);
                    if (inputStream != null) {
                        inputStream.close();
                        return;
                    }
                    return;
                }
                Reader reader = new InputStreamReader(inputStream);
                try {
                    JsonObject object = (JsonObject) GsonUtil.DEFAULT_GSON.fromJson(reader, JsonObject.class);
                    for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        String namespace = entry.getKey();
                        for (JsonElement legacyUuid : object.getAsJsonArray(namespace)) {
                            registerAddon(namespace, legacyUuid.getAsString());
                        }
                    }
                    reader.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load legacy addons from classpath", e);
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    @NotNull
    public Collection<LegacyAddon> discoverLegacyAddons() {
        Collection<LegacyAddon> addons = new ArrayList<>();
        for (LegacyAddonConverter.Version version : LegacyAddonConverter.Version.values()) {
            discoverLegacyAddons(version, addons);
        }
        return addons;
    }

    @Override // net.labymod.api.configuration.converter.LegacyConfigConverter
    @NotNull
    public Collection<LegacyAddon> discoverLegacyAddons(@NotNull LegacyAddonConverter.Version version) {
        Collection<LegacyAddon> addons = new ArrayList<>();
        discoverLegacyAddons(version, addons);
        return addons;
    }

    private void discoverLegacyAddons(@NotNull LegacyAddonConverter.Version version, @NotNull Collection<LegacyAddon> addons) {
        for (LegacyAddonResolver addonResolver : this.addonResolvers) {
            try {
                addonResolver.resolveAddons(version, addons);
            } catch (IOException e) {
                LOGGER.error("Failed to load legacy addons for version {} via resolver {}", version.getVersion(), addonResolver.getClass().getSimpleName(), e);
            }
        }
    }
}
