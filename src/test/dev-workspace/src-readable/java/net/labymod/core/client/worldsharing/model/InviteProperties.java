package net.labymod.core.client.worldsharing.model;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/InviteProperties.class */
public final class InviteProperties {
    private final Map<String, String> params = new LinkedHashMap();

    public String getVersion() {
        return get("version", "unknown");
    }

    public InviteProperties setVersion(String version) {
        set("version", version);
        return this;
    }

    public String getGameMode() {
        return get("gamemode", "survival");
    }

    public InviteProperties setGameMode(String gameMode) {
        set("gamemode", gameMode);
        return this;
    }

    public boolean isCheatsEnabled() {
        return "1".equals(get("cheats"));
    }

    public InviteProperties setCheatsEnabled(boolean cheatsEnabled) {
        set("cheats", cheatsEnabled ? "1" : "0");
        return this;
    }

    public String getWorldName() {
        return get("name", "New World");
    }

    public InviteProperties setWorldName(String worldName) {
        set("name", worldName);
        return this;
    }

    public String getIcon() {
        return get("icon");
    }

    public InviteProperties setIcon(String icon) {
        set("icon", icon);
        return this;
    }

    @Nullable
    public JoinWorldEvent.Type getType() {
        String typeParam = get(ParameterType.TYPE);
        if (typeParam == null || typeParam.isBlank()) {
            return null;
        }
        return JoinWorldEvent.Type.fromString(typeParam);
    }

    public InviteProperties setType(JoinWorldEvent.Type type) {
        set(ParameterType.TYPE, type.name());
        return this;
    }

    public String get(String key) {
        return get(key, "");
    }

    public String get(String key, String defaultValue) {
        return this.params.getOrDefault(key, defaultValue);
    }

    public void set(String key, String value) {
        if (key != null && value != null) {
            this.params.put(key, value);
        }
    }

    public String toQuery() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            if (!builder.isEmpty()) {
                builder.append("&");
            }
            builder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return builder.toString();
    }

    @NotNull
    public static InviteProperties fromQuery(String query) {
        InviteProperties properties = new InviteProperties();
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) {
                properties.set(parts[0], URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
            }
        }
        return properties;
    }
}
