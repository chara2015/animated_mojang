package net.labymod.core.loader.version.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.util.GsonUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/version/parser/VersionParser.class */
public final class VersionParser {
    public static void parse(URL url, Consumer<String> versionConsumer) {
        Objects.requireNonNull(versionConsumer);
        try {
            InputStream stream = url.openStream();
            try {
                Reader reader = new InputStreamReader(stream);
                try {
                    JsonElement element = (JsonElement) GsonUtil.DEFAULT_GSON.fromJson(reader, JsonElement.class);
                    if (!element.isJsonObject()) {
                        throw constructException("Is not a json object");
                    }
                    JsonObject object = element.getAsJsonObject();
                    if (!object.has("id")) {
                        throw constructException("Property `id` is missing");
                    }
                    versionConsumer.accept(object.get("id").getAsString());
                    reader.close();
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new VersionManifestException("Failed to read version manifest", exception);
        }
    }

    private static VersionManifestException constructException(String reason) {
        return new VersionManifestException("Mojang has probably changed the version manifest. (Reason: " + reason + ") Please contact our support");
    }
}
