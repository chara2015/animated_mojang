package net.labymod.v26_1_2.client.resources.pack;

import com.google.gson.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.PackTypes;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements PackResources {
    private final ResourcePack resourcePack;
    private final PackLocationInfo packLocationInfo;
    private ResourceMetadata resourceMetadata;

    public ModifiedPackResources(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.packLocationInfo = new PackLocationInfo(resourcePack.getName(), Component.literal(resourcePack.getName()), PackSource.BUILT_IN, Optional.empty());
    }

    @Nullable
    public IoSupplier<InputStream> getRootResource(String... var1) {
        return () -> {
            return ModifiedPackResources.class.getResourceAsStream(String.join("/", var1));
        };
    }

    public IoSupplier<InputStream> getResource(PackType type, Identifier location) {
        boolean isServerData = type == PackType.SERVER_DATA;
        if (!this.resourcePack.hasResource(PackTypes.select(isServerData), ResourceLocation.create(location.getNamespace(), location.getPath()))) {
            return null;
        }
        return () -> {
            return this.resourcePack.getResource(PackTypes.select(isServerData), ResourceLocation.create(location.getNamespace(), location.getPath()));
        };
    }

    public void listResources(@NotNull PackType packType, @NotNull String namespace, @NotNull String path, @NotNull PackResources.ResourceOutput output) {
        this.resourcePack.listResources(PackTypes.select(packType == PackType.SERVER_DATA), namespace, path, (location, inputStreamSupplier) -> {
            Identifier identifier = (Identifier) location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept(identifier, inputStreamSupplier::get);
        });
    }

    public Set<String> getNamespaces(PackType type) {
        return this.resourcePack.getNamespaces(PackTypes.select(type == PackType.SERVER_DATA));
    }

    @Nullable
    public <T> T getMetadataSection(@NotNull MetadataSectionType<T> metadataSectionType) throws IOException {
        if (this.resourceMetadata == null) {
            this.resourceMetadata = loadMetadata();
        }
        return (T) this.resourceMetadata.getSection(metadataSectionType).orElse(null);
    }

    public PackLocationInfo location() {
        return this.packLocationInfo;
    }

    public String packId() {
        return this.resourcePack.getName();
    }

    public void close() {
    }

    private ResourceMetadata loadMetadata() throws IOException {
        JsonObject object = new JsonObject();
        JsonObject packObject = new JsonObject();
        packObject.addProperty("description", this.resourcePack.getName());
        packObject.addProperty("pack_format", 11);
        object.add("pack", packObject);
        byte[] buffer = object.toString().getBytes(StandardCharsets.UTF_8);
        InputStream stream = new ByteArrayInputStream(buffer);
        try {
            ResourceMetadata resourceMetadataFromJsonStream = ResourceMetadata.fromJsonStream(stream);
            stream.close();
            return resourceMetadataFromJsonStream;
        } catch (Throwable th) {
            try {
                stream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
