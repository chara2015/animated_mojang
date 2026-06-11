package net.minecraft.locale;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.util.StrictJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/locale/DeprecatedTranslationsInfo.class */
public final class DeprecatedTranslationsInfo extends Record {
    private final List<String> removed;
    private final Map<String, String> renamed;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeprecatedTranslationsInfo EMPTY = new DeprecatedTranslationsInfo(List.of(), Map.of());
    public static final Codec<DeprecatedTranslationsInfo> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.STRING.listOf().fieldOf("removed").forGetter((v0) -> {
            return v0.removed();
        }), Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("renamed").forGetter((v0) -> {
            return v0.renamed();
        })).apply($$0, DeprecatedTranslationsInfo::new);
    });

    public DeprecatedTranslationsInfo(List<String> $$0, Map<String, String> $$1) {
        this.removed = $$0;
        this.renamed = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DeprecatedTranslationsInfo.class), DeprecatedTranslationsInfo.class, "removed;renamed", "FIELD:Lnet/minecraft/locale/DeprecatedTranslationsInfo;->removed:Ljava/util/List;", "FIELD:Lnet/minecraft/locale/DeprecatedTranslationsInfo;->renamed:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DeprecatedTranslationsInfo.class), DeprecatedTranslationsInfo.class, "removed;renamed", "FIELD:Lnet/minecraft/locale/DeprecatedTranslationsInfo;->removed:Ljava/util/List;", "FIELD:Lnet/minecraft/locale/DeprecatedTranslationsInfo;->renamed:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DeprecatedTranslationsInfo.class, Object.class), DeprecatedTranslationsInfo.class, "removed;renamed", "FIELD:Lnet/minecraft/locale/DeprecatedTranslationsInfo;->removed:Ljava/util/List;", "FIELD:Lnet/minecraft/locale/DeprecatedTranslationsInfo;->renamed:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<String> removed() {
        return this.removed;
    }

    public Map<String, String> renamed() {
        return this.renamed;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonIOException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
    public static DeprecatedTranslationsInfo loadFromJson(InputStream $$0) throws JsonSyntaxException, JsonIOException {
        JsonElement $$1 = StrictJsonParser.parse(new InputStreamReader($$0, StandardCharsets.UTF_8));
        return (DeprecatedTranslationsInfo) CODEC.parse(JsonOps.INSTANCE, $$1).getOrThrow($$02 -> {
            return new IllegalStateException("Failed to parse deprecated language data: " + $$02);
        });
    }

    public static DeprecatedTranslationsInfo loadFromResource(String $$0) {
        try {
            InputStream $$1 = Language.class.getResourceAsStream($$0);
            if ($$1 != null) {
                try {
                    DeprecatedTranslationsInfo deprecatedTranslationsInfoLoadFromJson = loadFromJson($$1);
                    if ($$1 != null) {
                        $$1.close();
                    }
                    return deprecatedTranslationsInfoLoadFromJson;
                } finally {
                }
            }
            if ($$1 != null) {
                $$1.close();
            }
        } catch (Exception $$2) {
            LOGGER.error("Failed to read {}", $$0, $$2);
        }
        return EMPTY;
    }

    public static DeprecatedTranslationsInfo loadFromDefaultResource() {
        return loadFromResource("/assets/minecraft/lang/deprecated.json");
    }

    public void applyToMap(Map<String, String> $$0) {
        for (String $$1 : this.removed) {
            $$0.remove($$1);
        }
        this.renamed.forEach(($$12, $$2) -> {
            String $$3 = (String) $$0.remove($$12);
            if ($$3 == null) {
                LOGGER.warn("Missing translation key for rename: {}", $$12);
                $$0.remove($$2);
            } else {
                $$0.put($$2, $$3);
            }
        });
    }
}
