package net.labymod.api.event.labymod.config;

import com.google.gson.JsonObject;
import java.util.Objects;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/ConfigurationVersionUpdateEvent.class */
public class ConfigurationVersionUpdateEvent implements Event {
    private final Class<? extends Config> configClass;
    private final int usedVersion;
    private final int intendedVersion;
    private final JsonObject jsonObject;
    private JsonObject editedJsonObject;

    public ConfigurationVersionUpdateEvent(@NotNull Class<? extends Config> configClass, @NotNull JsonObject jsonObject, int usedVersion, int intendedVersion) {
        Objects.requireNonNull(configClass, "configClass");
        Objects.requireNonNull(jsonObject, "jsonObject");
        this.configClass = configClass;
        this.jsonObject = jsonObject.deepCopy();
        this.usedVersion = usedVersion;
        this.intendedVersion = intendedVersion;
    }

    @NotNull
    public Class<? extends Config> getConfigClass() {
        return this.configClass;
    }

    public int getUsedVersion() {
        return this.usedVersion;
    }

    public int getIntendedVersion() {
        return this.intendedVersion;
    }

    @NotNull
    public JsonObject getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(@NotNull JsonObject jsonObject) {
        Objects.requireNonNull(jsonObject, "jsonObject");
        this.editedJsonObject = jsonObject;
    }

    @Nullable
    public JsonObject getEditedJsonObject() {
        return this.editedJsonObject;
    }
}
