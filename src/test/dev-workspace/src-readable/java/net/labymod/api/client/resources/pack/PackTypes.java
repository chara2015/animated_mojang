package net.labymod.api.client.resources.pack;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/PackTypes.class */
public final class PackTypes {
    private static final Map<String, PackType> TYPES = new HashMap();
    public static final PackType CLIENT_RESOURCES = create("assets");
    public static final PackType SERVER_DATA = create("data");

    public static PackType create(String directory) {
        return TYPES.computeIfAbsent(directory, PackType::new);
    }

    public static PackType select(boolean serverData) {
        return serverData ? SERVER_DATA : CLIENT_RESOURCES;
    }
}
