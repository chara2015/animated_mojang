package net.labymod.v1_21_3.client.resources.pack;

import com.google.gson.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.PackTypes;
import net.labymod.api.client.resources.pack.ResourcePack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements aug {
    private final ResourcePack resourcePack;
    private final auf packLocationInfo;

    public ModifiedPackResources(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.packLocationInfo = new auf(resourcePack.getName(), xv.b(resourcePack.getName()), avh.c, Optional.empty());
    }

    @Nullable
    public avn<InputStream> a(String... var1) {
        return () -> {
            return ModifiedPackResources.class.getResourceAsStream(String.join("/", var1));
        };
    }

    public avn<InputStream> a(aui type, alz location) {
        boolean isServerData = type == aui.b;
        if (!this.resourcePack.hasResource(PackTypes.select(isServerData), ResourceLocation.create(location.b(), location.a()))) {
            return null;
        }
        return () -> {
            return this.resourcePack.getResource(PackTypes.select(isServerData), ResourceLocation.create(location.b(), location.a()));
        };
    }

    public void a(@NotNull aui packType, @NotNull String namespace, @NotNull String path, @NotNull a output) {
        this.resourcePack.listResources(PackTypes.select(packType == aui.b), namespace, path, (location, inputStreamSupplier) -> {
            alz alzVar = (alz) location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept(alzVar, inputStreamSupplier::get);
        });
    }

    public Set<String> a(aui type) {
        return this.resourcePack.getNamespaces(PackTypes.select(type == aui.b));
    }

    @Nullable
    public <T> T a(aut<T> autVar) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("description", this.resourcePack.getName());
        jsonObject2.addProperty("pack_format", 11);
        jsonObject.add("pack", jsonObject2);
        return (T) atx.a(autVar, new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public auf a() {
        return this.packLocationInfo;
    }

    public String b() {
        return this.resourcePack.getName();
    }

    public void close() {
    }
}
