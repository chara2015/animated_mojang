package net.labymod.core.labynet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/ServerGroupRegistry.class */
public class ServerGroupRegistry {
    private static final Logging LOGGER = Logging.create((Class<?>) ServerGroupRegistry.class);
    private final Map<String, ServerGroup> serverGroups = new Object2ObjectOpenHashMap();
    private final Map<String, DummyServerGroup> tempServerGroups = new Object2ObjectOpenHashMap();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/ServerGroupRegistry$ServerGroupIndex.class */
    public static class ServerGroupIndex {

        @SerializedName("server_groups")
        private Map<String, JsonObject> serverGroups;
    }

    public ServerGroup getServerByName(String name) {
        ServerGroup serverGroup = this.serverGroups.get(name);
        if (serverGroup != null) {
            return serverGroup;
        }
        return mapServerGroup(this.tempServerGroups.get(name));
    }

    public ServerGroup getServerByIp(String ip) {
        for (ServerGroup serverGroup : this.serverGroups.values()) {
            if (serverGroup.hasIp(ip)) {
                return serverGroup;
            }
        }
        for (DummyServerGroup temp : this.tempServerGroups.values()) {
            if (temp.matches(ip)) {
                return mapServerGroup(temp);
            }
        }
        return null;
    }

    public void initialize(ServerGroupIndex index) {
        if (index.serverGroups == null) {
            return;
        }
        for (Map.Entry<String, JsonObject> entry : index.serverGroups.entrySet()) {
            String key = entry.getKey();
            JsonObject jsonObject = entry.getValue();
            this.tempServerGroups.put(key, new DummyServerGroup(key, jsonObject));
        }
    }

    private ServerGroup mapServerGroup(DummyServerGroup temp) {
        try {
            if (temp == null) {
                return null;
            }
            try {
                ServerGroup serverGroup = (ServerGroup) GsonUtil.DEFAULT_GSON.fromJson(temp.object, ServerGroup.class);
                if (serverGroup == null) {
                    this.tempServerGroups.remove(temp.key);
                    return null;
                }
                this.serverGroups.put(temp.key, serverGroup);
                this.tempServerGroups.remove(temp.key);
                return serverGroup;
            } catch (Exception e) {
                LOGGER.warn("Failed to map server group " + temp.key, e);
                this.tempServerGroups.remove(temp.key);
                return null;
            }
        } catch (Throwable th) {
            this.tempServerGroups.remove(temp.key);
            throw th;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/ServerGroupRegistry$DummyServerGroup.class */
    private static class DummyServerGroup {
        private final String key;
        private final JsonObject object;
        private String address;
        private String[] wildcards;
        private boolean extractedAddresses;

        public DummyServerGroup(String key, JsonObject jsonObject) {
            this.key = key;
            this.object = jsonObject;
        }

        public boolean matches(String address) {
            try {
                if (!this.extractedAddresses) {
                    try {
                        this.address = this.object.get("direct_ip").getAsString();
                        if (this.object.has("wildcards")) {
                            JsonArray<JsonElement> jsonArray = this.object.get("wildcards").getAsJsonArray();
                            List<String> wildcards = new ArrayList<>();
                            for (JsonElement element : jsonArray) {
                                if (element.isJsonPrimitive()) {
                                    JsonPrimitive primitive = element.getAsJsonPrimitive();
                                    if (primitive.isString()) {
                                        wildcards.add(primitive.getAsString());
                                    }
                                }
                            }
                            this.wildcards = (String[]) wildcards.toArray(new String[0]);
                        }
                        this.extractedAddresses = true;
                    } catch (Exception e) {
                        ServerGroupRegistry.LOGGER.warn("Failed to extract addresses from server group " + this.key, e);
                        this.extractedAddresses = true;
                    }
                }
                if (this.address == null) {
                    return false;
                }
                return ServerGroup.addressMatches(address, this.address, this.wildcards);
            } catch (Throwable th) {
                this.extractedAddresses = true;
                throw th;
            }
        }
    }
}
