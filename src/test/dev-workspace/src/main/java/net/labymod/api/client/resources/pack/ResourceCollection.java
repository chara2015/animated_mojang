package net.labymod.api.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.models.version.Version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourceCollection.class */
public final class ResourceCollection extends Record {
    private final Map<String, List<ResourceLocation>> resources;
    private static final String SHARED_KEY = "shared";

    public ResourceCollection(Map<String, List<ResourceLocation>> resources) {
        this.resources = resources;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ResourceCollection.class), ResourceCollection.class, "resources", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceCollection;->resources:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ResourceCollection.class), ResourceCollection.class, "resources", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceCollection;->resources:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ResourceCollection.class, Object.class), ResourceCollection.class, "resources", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceCollection;->resources:Ljava/util/Map;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Map<String, List<ResourceLocation>> resources() {
        return this.resources;
    }

    public List<ResourceLocation> getResources(MinecraftVersion version) {
        return getResources(version.version());
    }

    public List<ResourceLocation> getResources(Version version) {
        return getResources(version.toString());
    }

    public List<ResourceLocation> getResources(String version) {
        List<ResourceLocation> result;
        if (this.resources.containsKey(SHARED_KEY)) {
            result = new ArrayList(this.resources.get(SHARED_KEY));
            result.addAll(this.resources.getOrDefault(version, Collections.emptyList()));
        } else {
            result = this.resources.getOrDefault(version, Collections.emptyList());
        }
        return result;
    }
}
