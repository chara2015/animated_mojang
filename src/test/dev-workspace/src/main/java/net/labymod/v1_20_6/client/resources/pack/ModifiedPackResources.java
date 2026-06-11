package net.labymod.v1_20_6.client.resources.pack;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements atb {
    private final ResourcePack resourcePack;
    private final ata packLocationInfo;

    public ModifiedPackResources(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
        this.packLocationInfo = new ata(resourcePack.getName(), xp.b(resourcePack.getName()), aub.c, Optional.empty());
    }

    @Nullable
    public auh<InputStream> a(String... var1) {
        return () -> {
            return ModifiedPackResources.class.getResourceAsStream(String.join("/", var1));
        };
    }

    public auh<InputStream> a(atd type, alf location) {
        boolean isServerData = type == atd.b;
        if (!this.resourcePack.hasResource(PackTypes.select(isServerData), ResourceLocation.create(location.b(), location.a()))) {
            return null;
        }
        return () -> {
            return this.resourcePack.getResource(PackTypes.select(isServerData), ResourceLocation.create(location.b(), location.a()));
        };
    }

    public void a(@NotNull atd packType, @NotNull String namespace, @NotNull String path, @NotNull a output) {
        this.resourcePack.listResources(PackTypes.select(packType == atd.b), namespace, path, (location, inputStreamSupplier) -> {
            alf alfVar = (alf) location.getMinecraftLocation();
            Objects.requireNonNull(inputStreamSupplier);
            output.accept(alfVar, inputStreamSupplier::get);
        });
    }

    public Set<String> a(atd type) {
        return this.resourcePack.getNamespaces(PackTypes.select(type == atd.b));
    }

    @Nullable
    public <T> T a(ato<T> atoVar) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("description", this.resourcePack.getName());
        jsonObject2.addProperty("pack_format", 11);
        jsonObject.add("pack", jsonObject2);
        return (T) ass.a(atoVar, new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public ata a() {
        return this.packLocationInfo;
    }

    public String b() {
        return this.resourcePack.getName();
    }

    public void close() {
    }
}
