package net.labymod.v1_21_8.client.resources.pack;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements awb {
    private final ResourcePack resourcePack;
    private final awa packLocationInfo;

    public ModifiedPackResources(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.packLocationInfo = new awa(resourcePack.getName(), xo.b(resourcePack.getName()), axa.c, Optional.empty());
    }

    @Nullable
    public axg<InputStream> a(String... var1) {
        return () -> {
            return ModifiedPackResources.class.getResourceAsStream(String.join("/", var1));
        };
    }

    public axg<InputStream> a(awd type, ame location) {
        boolean isServerData = type == awd.b;
        if (!this.resourcePack.hasResource(PackTypes.select(isServerData), ResourceLocation.create(location.b(), location.a()))) {
            return null;
        }
        return () -> {
            return this.resourcePack.getResource(PackTypes.select(isServerData), ResourceLocation.create(location.b(), location.a()));
        };
    }

    public void a(@NotNull awd packType, @NotNull String namespace, @NotNull String path, @NotNull a output) {
        this.resourcePack.listResources(PackTypes.select(packType == awd.b), namespace, path, (location, inputStreamSupplier) -> {
            ame ameVar = (ame) location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept(ameVar, inputStreamSupplier::get);
        });
    }

    public Set<String> a(awd type) {
        return this.resourcePack.getNamespaces(PackTypes.select(type == awd.b));
    }

    @Nullable
    public <T> T a(@NotNull awo<T> awoVar) throws IOException {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("description", this.resourcePack.getName());
        jsonObject2.addProperty("pack_format", 11);
        jsonObject.add("pack", jsonObject2);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        try {
            T t = (T) avs.a(awoVar, byteArrayInputStream);
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

    public awa a() {
        return this.packLocationInfo;
    }

    public String b() {
        return this.resourcePack.getName();
    }

    public void close() {
    }
}
