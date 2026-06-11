package net.minecraft.data.metadata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.DetectedVersion;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.FeatureFlagsMetadataSection;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.flag.FeatureFlagSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/metadata/PackMetadataGenerator.class */
public class PackMetadataGenerator implements DataProvider {
    private final PackOutput output;
    private final Map<String, Supplier<JsonElement>> elements = new HashMap();

    public PackMetadataGenerator(PackOutput $$0) {
        this.output = $$0;
    }

    public <T> PackMetadataGenerator add(MetadataSectionType<T> $$0, T $$1) {
        this.elements.put($$0.name(), () -> {
            return ((JsonElement) $$0.codec().encodeStart(JsonOps.INSTANCE, $$1).getOrThrow(IllegalArgumentException::new)).getAsJsonObject();
        });
        return this;
    }

    @Override // net.minecraft.data.DataProvider
    public CompletableFuture<?> run(CachedOutput $$0) {
        JsonObject $$1 = new JsonObject();
        this.elements.forEach(($$12, $$2) -> {
            $$1.add($$12, (JsonElement) $$2.get());
        });
        return DataProvider.saveStable($$0, $$1, this.output.getOutputFolder().resolve(PackResources.PACK_META));
    }

    @Override // net.minecraft.data.DataProvider
    public final String getName() {
        return "Pack Metadata";
    }

    public static PackMetadataGenerator forFeaturePack(PackOutput $$0, Component $$1) {
        return new PackMetadataGenerator($$0).add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection($$1, DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange()));
    }

    public static PackMetadataGenerator forFeaturePack(PackOutput $$0, Component $$1, FeatureFlagSet $$2) {
        return forFeaturePack($$0, $$1).add(FeatureFlagsMetadataSection.TYPE, new FeatureFlagsMetadataSection($$2));
    }
}
