package net.labymod.api.laby3d;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.laby3d.api.resource.AssetId;
import net.labymod.laby3d.api.resource.ResourceProvider;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/LabyModResourceProvider.class */
public class LabyModResourceProvider implements ResourceProvider {
    private final Map<AssetId, AssetId> assetIdMapping = new HashMap();

    public LabyModResourceProvider() {
        this.assetIdMapping.put(AssetId.parse("minecraft:fog.glsl"), AssetId.parse("minecraft:shaders/include/fog.glsl"));
        this.assetIdMapping.put(AssetId.parse("minecraft:light.glsl"), AssetId.parse("minecraft:shaders/include/light.glsl"));
        this.assetIdMapping.put(AssetId.parse("minecraft:matrix.glsl"), AssetId.parse("minecraft:shaders/include/matrix.glsl"));
        this.assetIdMapping.put(AssetId.parse("minecraft:projection.glsl"), AssetId.parse("minecraft:shaders/include/projection.glsl"));
    }

    @NotNull
    public InputStream openStream(@NotNull AssetId assetId) throws IOException {
        return fromAssetId(assetId).openStream();
    }

    public AssetId mapId(AssetId assetId) {
        String path = assetId.path();
        if (path.startsWith("shaders")) {
            return assetId;
        }
        if (this.assetIdMapping.containsKey(assetId)) {
            return this.assetIdMapping.getOrDefault(assetId, assetId);
        }
        AssetId mappedId = AssetId.of(assetId.namespace(), "shaders/include/" + path);
        this.assetIdMapping.put(assetId, mappedId);
        return mappedId;
    }

    @NotNull
    public static ResourceLocation fromAssetId(@NotNull AssetId assetId) {
        return ResourceLocation.create(assetId.namespace(), assetId.path());
    }

    @NotNull
    public static AssetId toAssetId(@NotNull ResourceLocation resourceLocation) {
        return AssetId.of(resourceLocation.getNamespace(), resourceLocation.getPath());
    }
}
