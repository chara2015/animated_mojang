package net.labymod.v1_21_11.client.resources.pack;

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
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements PackResources {
    private final ResourcePack resourcePack;
    private final PackLocationInfo packLocationInfo;

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
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("description", this.resourcePack.getName());
        jsonObject2.addProperty("pack_format", 11);
        jsonObject.add("pack", jsonObject2);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        try {
            T t = (T) AbstractPackResources.getMetadataFromStream(metadataSectionType, byteArrayInputStream, this.packLocationInfo);
            byteArrayInputStream.close();
            return t;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public PackLocationInfo location() {
        return this.packLocationInfo;
    }

    public String packId() {
        return this.resourcePack.getName();
    }

    public void close() {
    }
}
