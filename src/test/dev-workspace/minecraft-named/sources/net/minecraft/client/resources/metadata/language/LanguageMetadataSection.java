package net.minecraft.client.resources.metadata.language;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.server.packs.metadata.MetadataSectionType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/metadata/language/LanguageMetadataSection.class */
public final class LanguageMetadataSection extends Record {
    private final Map<String, LanguageInfo> languages;
    public static final Codec<String> LANGUAGE_CODE_CODEC = Codec.string(1, 16);
    public static final Codec<LanguageMetadataSection> CODEC = Codec.unboundedMap(LANGUAGE_CODE_CODEC, LanguageInfo.CODEC).xmap(LanguageMetadataSection::new, (v0) -> {
        return v0.languages();
    });
    public static final MetadataSectionType<LanguageMetadataSection> TYPE = new MetadataSectionType<>("language", CODEC);

    public LanguageMetadataSection(Map<String, LanguageInfo> $$0) {
        this.languages = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LanguageMetadataSection.class), LanguageMetadataSection.class, "languages", "FIELD:Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;->languages:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LanguageMetadataSection.class), LanguageMetadataSection.class, "languages", "FIELD:Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;->languages:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LanguageMetadataSection.class, Object.class), LanguageMetadataSection.class, "languages", "FIELD:Lnet/minecraft/client/resources/metadata/language/LanguageMetadataSection;->languages:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<String, LanguageInfo> languages() {
        return this.languages;
    }
}
