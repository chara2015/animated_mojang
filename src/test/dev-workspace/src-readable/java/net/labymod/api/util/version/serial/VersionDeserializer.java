package net.labymod.api.util.version.serial;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.VersionFactory;
import net.labymod.api.util.version.parser.SemanticVersionParser;
import net.labymod.api.util.version.parser.WeeklyCalendarVersionParser;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/serial/VersionDeserializer.class */
public class VersionDeserializer implements JsonDeserializer<Version> {
    private static final VersionFactory DEFAULT_FACTORY = new VersionFactory(List.of(new SemanticVersionParser(), new WeeklyCalendarVersionParser()));

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public Version m607deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String version = json.getAsString();
        return from(version);
    }

    @NotNull
    public static Version from(@NotNull String version) {
        return from(version, DEFAULT_FACTORY);
    }

    @NotNull
    public static Version from(@NotNull String version, @NotNull VersionFactory factory) {
        return factory.from(version);
    }
}
