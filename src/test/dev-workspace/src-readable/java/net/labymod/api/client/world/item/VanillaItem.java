package net.labymod.api.client.world.item;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.MinecraftVersion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/VanillaItem.class */
public final class VanillaItem {
    private final ResourceLocation identifier;
    private final Map<String, MinecraftVersion> versions = new HashMap();
    private final boolean available;

    private VanillaItem(ResourceLocation identifier, MinecraftVersion[] versions) {
        this.identifier = identifier;
        boolean available = false;
        for (MinecraftVersion version : versions) {
            this.versions.put(version.toString(), version);
            available |= version.isCurrent();
        }
        this.available = available;
    }

    public static VanillaItem of(String identifier, MinecraftVersion[] versions) {
        ResourceLocation itemIdentifier = ResourceLocation.parse(identifier);
        return new VanillaItem(itemIdentifier, versions);
    }

    public ResourceLocation identifier() {
        return this.identifier;
    }

    public Map<String, MinecraftVersion> getVersions() {
        return this.versions;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
