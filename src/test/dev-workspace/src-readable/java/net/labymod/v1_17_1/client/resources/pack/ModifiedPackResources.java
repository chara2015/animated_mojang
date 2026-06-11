package net.labymod.v1_17_1.client.resources.pack;

import com.google.gson.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.PackTypes;
import net.labymod.api.client.resources.pack.ResourcePack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements acv {
    private final ResourcePack resourcePack;

    public ModifiedPackResources(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
    }

    @Nullable
    public InputStream b(String path) throws IOException {
        return ModifiedPackResources.class.getResourceAsStream("/" + path);
    }

    public InputStream a(acw type, ww location) throws IOException {
        return this.resourcePack.getResource(PackTypes.select(type == acw.b), ResourceLocation.create(location.b(), location.a()));
    }

    public Collection<ww> a(@NotNull acw packType, @NotNull String namespace, @NotNull String path, int var4, @NotNull Predicate<String> filter) {
        List<ww> locations = new ArrayList<>();
        this.resourcePack.listResources(PackTypes.select(packType == acw.b), namespace, path, (location, inputStreamSupplier) -> {
            locations.add((ww) location.getMinecraftLocation());
            inputStreamSupplier.silentClose();
        });
        return locations;
    }

    public boolean b(acw type, ww location) {
        return this.resourcePack.hasResource(PackTypes.select(type == acw.b), ResourceLocation.create(location.b(), location.a()));
    }

    public Set<String> a(acw type) {
        return this.resourcePack.getNamespaces(PackTypes.select(type == acw.b));
    }

    @Nullable
    public <T> T a(acz<T> aczVar) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("description", a());
        jsonObject2.addProperty("pack_format", 7);
        jsonObject.add("pack", jsonObject2);
        return (T) acs.a(aczVar, new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public String a() {
        return this.resourcePack.getName();
    }

    public void close() {
    }
}
