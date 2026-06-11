package net.labymod.api.client.component.serializer.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/serializer/gson/GsonComponentSerializer.class */
@Singleton
@Referenceable
public class GsonComponentSerializer {
    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder().registerTypeHierarchyAdapter(Component.class, ComponentService.getComponentSerializer());
        if (!MinecraftVersions.V23w40a.orNewer()) {
            builder.registerTypeHierarchyAdapter(Style.class, ComponentService.getStyleSerializer());
        }
        ComponentService.applyTypeAdapters(builder);
        GSON = builder.create();
    }

    public static GsonComponentSerializer gson() {
        return Laby.references().gsonComponentSerializer();
    }

    @Inject
    public GsonComponentSerializer() {
    }

    public Component deserializeFromTree(JsonElement jsonElement) {
        return (Component) GSON.fromJson(jsonElement, Component.class);
    }

    public JsonElement serializeToTree(Component component) {
        return GSON.toJsonTree(component);
    }

    public Component deserialize(String string) {
        return (Component) GSON.fromJson(string, Component.class);
    }

    public String serialize(Component component) {
        return GSON.toJson(component);
    }
}
