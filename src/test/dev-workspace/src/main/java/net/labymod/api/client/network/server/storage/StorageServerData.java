package net.labymod.api.client.network.server.storage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.labymod.api.Constants;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.util.GsonUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/storage/StorageServerData.class */
public class StorageServerData extends ConnectableServerData {
    public static final String LAST_SCREENSHOT_KEY = "LastScreenshotFile";
    public static final String BOUND_ACCOUNT_KEY = "LabyMod-BoundAccount";
    public static final String ACCEPTED_CODE_OF_CONDUCT_KEY = "acceptedCodeOfConduct";
    private static final boolean MIGRATE_LABY_IP = true;
    private String iconBase64;
    private Map<String, String> metadata;

    protected StorageServerData(@Nullable String name, @NotNull ServerAddress serverAddress) {
        super(name, serverAddress, ServerType.THIRD_PARTY, null);
    }

    public static StorageServerData of(@Nullable String name, @NotNull ServerAddress serverAddress) {
        return new StorageServerData(name, serverAddress);
    }

    public static StorageServerData of(@Nullable String name, @NotNull String serverAddress) {
        if (serverAddress.equalsIgnoreCase("tcpshield.laby.net:25560")) {
            serverAddress = serverAddress.replace("tcpshield.laby.net:25560", Constants.Domains.LABY_DOMAIN);
        }
        return of(name, ServerAddress.parse(serverAddress));
    }

    public String getIconBase64() {
        return this.iconBase64;
    }

    public void setIconBase64(String iconBase64) {
        this.iconBase64 = iconBase64;
    }

    public void setResourcePackStatus(ServerResourcePackStatus resourcePackStatus) {
        this.resourcePackStatus = resourcePackStatus;
    }

    public void setAcceptedCodeOfConduct(int value) {
        metadata().put(ACCEPTED_CODE_OF_CONDUCT_KEY, String.valueOf(value));
    }

    public int getAcceptedCodeOfConduct() {
        String value = metadata().get(ACCEPTED_CODE_OF_CONDUCT_KEY);
        if (value == null || value.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setServerAddress(ServerAddress serverAddress) {
        this.serverAddress = serverAddress;
        this.actualAddress = null;
    }

    public void setServerAddress(String serverAddress) {
        setServerAddress(ServerAddress.parse(serverAddress));
    }

    @Nullable
    public UUID getBoundUniqueId() {
        String value;
        if (!hasMetadata() || (value = metadata().get(BOUND_ACCOUNT_KEY)) == null) {
            return null;
        }
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> metadata() {
        if (this.metadata == null) {
            this.metadata = new HashMap();
        }
        return this.metadata;
    }

    public boolean hasMetadata() {
        return (this.metadata == null || this.metadata.isEmpty()) ? false : true;
    }

    public <T> T getJsonMeta(String str, Class<T> cls) {
        return (T) GsonUtil.DEFAULT_GSON.fromJson(metadata().get(str), cls);
    }

    public <T> void setJsonMeta(String key, T value) {
        metadata().put(key, GsonUtil.DEFAULT_GSON.toJson(value));
    }

    @Nullable
    public File getLastScreenshotFile() {
        String path;
        if (this.metadata != null && (path = metadata().get(LAST_SCREENSHOT_KEY)) != null) {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
            return null;
        }
        return null;
    }

    @Override // net.labymod.api.client.network.server.ConnectableServerData, net.labymod.api.client.network.server.ServerData
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageServerData) || !super.equals(o)) {
            return false;
        }
        StorageServerData that = (StorageServerData) o;
        return Objects.equals(this.iconBase64, that.iconBase64);
    }

    @Override // net.labymod.api.client.network.server.ConnectableServerData, net.labymod.api.client.network.server.ServerData
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + (this.iconBase64 != null ? this.iconBase64.hashCode() : 0);
    }
}
