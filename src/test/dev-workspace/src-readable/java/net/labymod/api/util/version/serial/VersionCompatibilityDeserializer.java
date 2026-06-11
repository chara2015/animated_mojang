package net.labymod.api.util.version.serial;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.version.VersionMultiRange;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/serial/VersionCompatibilityDeserializer.class */
public class VersionCompatibilityDeserializer implements JsonDeserializer<VersionCompatibility> {
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public VersionCompatibility m605deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return from(json.getAsString());
    }

    @NotNull
    public static VersionCompatibility from(@NotNull String version) {
        return new VersionMultiRange(version);
    }
}
