package net.labymod.core.localization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.localization.I18nException;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.models.Implements;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.resources.PathResourceLocation;
import net.labymod.core.util.classpath.ClasspathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/DefaultInternationalization.class */
@Singleton
@Implements(Internationalization.class)
public class DefaultInternationalization implements Internationalization {
    private static final String LANGUAGE_PATH = "assets/%s/i18n/%s";
    private static final String FALLBACK_LANGUAGE = "en_us";
    private final Logging logging = Logging.create("I18n");
    private final List<ResourceLocation> hotswapLocations = new ArrayList();
    private final List<ResourceLocation> languageLocations = new ArrayList();
    private final Map<String, String> fallbackTranslations = new HashMap();
    private final Map<String, String> translations = new HashMap();
    private String selectedLanguage;

    @Inject
    public DefaultInternationalization() {
    }

    public void loadTranslations(String namespace) throws IOException {
        loadTranslations(namespace, Laby.labyAPI().minecraft().options().getCurrentLanguage());
    }

    public void loadTranslations(String namespace, String selectedLanguage) throws IOException {
        loadTranslations(namespace, selectedLanguage, this.translations);
        loadTranslations(namespace, FALLBACK_LANGUAGE, this.fallbackTranslations);
    }

    private void loadTranslations(String namespace, String selectedLanguage, Map<String, String> target) throws IOException {
        String selectedLanguage2 = selectedLanguage.toLowerCase(Locale.ENGLISH);
        if (this.selectedLanguage == null || !this.selectedLanguage.equals(selectedLanguage2)) {
            this.selectedLanguage = selectedLanguage2;
        }
        if (existsLanguageDirectory(namespace)) {
            String name = languagePath(namespace, selectedLanguage2);
            ResourceLocation location = ResourceLocation.create(namespace, "i18n/" + StringUtil.toLowercase(selectedLanguage2) + ".json");
            InputStream inputStream = null;
            try {
                inputStream = location instanceof PathResourceLocation ? location.openStream() : ClasspathUtil.getResourceAsInputStream(namespace, name);
            } catch (IOException exception) {
                if (selectedLanguage2.equals(FALLBACK_LANGUAGE)) {
                    throw new I18nException(String.format(Locale.ROOT, "No fallback language found in namespace \"%s\"", namespace), exception);
                }
            }
            if (inputStream == null) {
                return;
            }
            if (!this.languageLocations.contains(location)) {
                this.languageLocations.add(location);
            }
            try {
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                try {
                    try {
                        JsonElement element = (JsonElement) GsonUtil.DEFAULT_GSON.fromJson(reader, JsonElement.class);
                        if (element == null || !element.isJsonObject()) {
                            this.logging.error("Invalid language file: \"{}:{}\"", namespace, name);
                            reader.close();
                            inputStream.close();
                            return;
                        }
                        JsonObject object = element.getAsJsonObject();
                        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                            readJsonTree(target, entry.getKey(), entry.getValue());
                        }
                        reader.close();
                        inputStream.close();
                    } catch (JsonSyntaxException exception2) {
                        this.logging.error("Could not load the translations of {}.", namespace, exception2);
                        reader.close();
                        inputStream.close();
                    }
                } finally {
                }
            } catch (Throwable th) {
                inputStream.close();
                throw th;
            }
        }
    }

    @Override // net.labymod.api.localization.Internationalization
    @NotNull
    public String getRawTranslation(@NotNull String key) {
        String translation = this.translations.get(key);
        if (translation != null) {
            return translation;
        }
        return this.fallbackTranslations.getOrDefault(key, key);
    }

    @Override // net.labymod.api.localization.Internationalization
    @NotNull
    public String translate(@NotNull String key, Object... args) {
        try {
            return String.format(Locale.ROOT, getRawTranslation(key), args);
        } catch (IllegalFormatException e) {
            return key;
        }
    }

    @Override // net.labymod.api.localization.Internationalization
    @Nullable
    public String getTranslation(@NotNull String key, Object... args) {
        String translate = translate(key, args);
        if (translate.equals(key)) {
            return null;
        }
        return translate;
    }

    @Override // net.labymod.api.localization.Internationalization
    public String getSelectedLanguage() {
        return this.selectedLanguage;
    }

    @Override // net.labymod.api.localization.Internationalization
    public boolean has(@NotNull String key) {
        return this.translations.containsKey(key) || this.fallbackTranslations.containsKey(key);
    }

    @Override // net.labymod.api.localization.Internationalization
    public boolean isAssumedTranslatable(@NotNull String key) {
        return !key.contains(" ") && key.contains(".");
    }

    public void onDevTick() {
        for (ResourceLocation languageLocation : this.languageLocations) {
            if (languageLocation instanceof PathResourceLocation) {
                PathResourceLocation pathLocation = (PathResourceLocation) languageLocation;
                if (pathLocation.isModified()) {
                    this.hotswapLocations.add(languageLocation);
                }
            }
        }
        if (this.hotswapLocations.isEmpty()) {
            return;
        }
        for (ResourceLocation hotswapLocation : this.hotswapLocations) {
            this.fallbackTranslations.entrySet().removeIf(entry -> {
                return ((String) entry.getKey()).startsWith(hotswapLocation.getNamespace());
            });
            this.translations.entrySet().removeIf(entry2 -> {
                return ((String) entry2.getKey()).startsWith(hotswapLocation.getNamespace());
            });
        }
        for (ResourceLocation hotswapLocation2 : this.hotswapLocations) {
            try {
                loadTranslations(hotswapLocation2.getNamespace());
                this.logging.info("Translation file " + String.valueOf(hotswapLocation2) + " has been reloaded", new Object[0]);
            } catch (IOException | JsonSyntaxException exception) {
                this.logging.error("Translations could not be loaded (" + exception.getMessage() + ")", new Object[0]);
            }
        }
        this.hotswapLocations.clear();
        ((DefaultThemeService) Laby.labyAPI().themeService()).reload(true);
    }

    public void onResourceReload() {
        this.fallbackTranslations.clear();
        this.translations.clear();
    }

    private boolean existsLanguageDirectory(String namespace) {
        try {
            InputStream inputStream = ClasspathUtil.getResourceAsInputStream(namespace, "assets/" + namespace + "/i18n/");
            inputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String languagePath(String namespace, String selectedLanguage) {
        return String.format(Locale.ROOT, LANGUAGE_PATH, namespace, StringUtil.toLowercase(selectedLanguage) + ".json");
    }

    private void readJsonTree(Map<String, String> target, String key, JsonElement element) {
        String value;
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                readJsonTree(target, key + "." + entry.getKey(), entry.getValue());
            }
            return;
        }
        if (!element.isJsonPrimitive() || (value = element.getAsString()) == null || value.isBlank()) {
            return;
        }
        target.putIfAbsent(key, value);
    }
}
