package net.minecraft.locale;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringDecomposer;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/locale/Language.class */
public abstract class Language {
    public static final String DEFAULT = "en_us";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();
    private static final Pattern UNSUPPORTED_FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
    private static volatile Language instance = loadDefault();

    public abstract String getOrDefault(String str, String str2);

    public abstract boolean has(String str);

    public abstract boolean isDefaultRightToLeft();

    public abstract FormattedCharSequence getVisualOrder(FormattedText formattedText);

    private static Language loadDefault() {
        DeprecatedTranslationsInfo $$0 = DeprecatedTranslationsInfo.loadFromDefaultResource();
        Map<String, String> $$1 = new HashMap<>();
        Objects.requireNonNull($$1);
        BiConsumer<String, String> $$2 = (v1, v2) -> {
            r0.put(v1, v2);
        };
        parseTranslations($$2, "/assets/minecraft/lang/en_us.json");
        $$0.applyToMap($$1);
        final Map<String, String> $$3 = Map.copyOf($$1);
        return new Language() { // from class: net.minecraft.locale.Language.1
            @Override // net.minecraft.locale.Language
            public String getOrDefault(String $$02, String $$12) {
                return (String) $$3.getOrDefault($$02, $$12);
            }

            @Override // net.minecraft.locale.Language
            public boolean has(String $$02) {
                return $$3.containsKey($$02);
            }

            @Override // net.minecraft.locale.Language
            public boolean isDefaultRightToLeft() {
                return false;
            }

            @Override // net.minecraft.locale.Language
            public FormattedCharSequence getVisualOrder(FormattedText $$02) {
                return $$12 -> {
                    return $$02.visit(($$12, $$22) -> {
                        return StringDecomposer.iterateFormatted($$22, $$12, $$12) ? Optional.empty() : FormattedText.STOP_ITERATION;
                    }, Style.EMPTY).isPresent();
                };
            }
        };
    }

    private static void parseTranslations(BiConsumer<String, String> $$0, String $$1) {
        try {
            InputStream $$2 = Language.class.getResourceAsStream($$1);
            try {
                loadFromJson($$2, $$0);
                if ($$2 != null) {
                    $$2.close();
                }
            } finally {
            }
        } catch (IOException | JsonParseException $$3) {
            LOGGER.error("Couldn't read strings from {}", $$1, $$3);
        }
    }

    public static void loadFromJson(InputStream $$0, BiConsumer<String, String> $$1) {
        JsonObject $$2 = (JsonObject) GSON.fromJson(new InputStreamReader($$0, StandardCharsets.UTF_8), JsonObject.class);
        for (Map.Entry<String, JsonElement> $$3 : $$2.entrySet()) {
            String $$4 = UNSUPPORTED_FORMAT_PATTERN.matcher(GsonHelper.convertToString($$3.getValue(), $$3.getKey())).replaceAll("%$1s");
            $$1.accept($$3.getKey(), $$4);
        }
    }

    public static Language getInstance() {
        return instance;
    }

    public static void inject(Language $$0) {
        instance = $$0;
    }

    public String getOrDefault(String $$0) {
        return getOrDefault($$0, $$0);
    }

    public List<FormattedCharSequence> getVisualOrder(List<FormattedText> $$0) {
        return (List) $$0.stream().map(this::getVisualOrder).collect(ImmutableList.toImmutableList());
    }
}
