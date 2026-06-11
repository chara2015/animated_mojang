package net.labymod.core.flint;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Constants;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/FlintDefaultModifications.class */
public class FlintDefaultModifications {
    private static final String URL = "https://releases.labymod.net/api/v1/flint/default-modifications";
    private static final FlintDefaultModifications INSTANCE = new FlintDefaultModifications();
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) FlintDefaultModifications.class);
    private static final Lazy<DefaultWebResolver> WEB_RESOLVER = Lazy.of(DefaultWebResolver::instance);

    @Deprecated
    private final Path deletionInfoPath = Constants.Files.ADDONS.resolve("deleted-default-modifications.json");
    private final Path installedInfoPath = Constants.Files.ADDONS.resolve("installed-default-modifications.json");
    private final Request<JsonArray> request = Request.ofGson(JsonArray.class).url(URL, new Object[0]);
    private final List<String> defaultModifications = new ArrayList();
    private Response<JsonArray> response;

    /* JADX WARN: Type inference failed for: r1v6, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request<com.google.gson.JsonArray>] */
    private FlintDefaultModifications() {
    }

    public static FlintDefaultModifications instance() {
        return INSTANCE;
    }

    public void loadDefaultAddons() {
        this.response = WEB_RESOLVER.get().resolveConnection(this.request);
        if (!this.response.isPresent()) {
            return;
        }
        this.defaultModifications.clear();
        for (JsonElement jsonElement : this.response.get()) {
            if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isString()) {
                    this.defaultModifications.add(jsonPrimitive.getAsString());
                }
            }
        }
    }

    public boolean hasInstalledBefore(String namespace) {
        JsonArray<JsonElement> installedModifications = getInstalledModifications();
        for (JsonElement installedModification : installedModifications) {
            if (installedModification.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = installedModification.getAsJsonPrimitive();
                if (jsonPrimitive.isString() && jsonPrimitive.getAsString().equals(namespace)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void install(String namespace) {
        if (!isDefaultAddon(namespace)) {
            return;
        }
        JsonArray<JsonElement> deletionInfo = getInstalledModifications();
        for (JsonElement jsonElement : deletionInfo) {
            if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isString() && jsonPrimitive.getAsString().equals(namespace)) {
                    return;
                }
            }
        }
        deletionInfo.add(new JsonPrimitive(namespace));
        saveInstalledModificationInfo(deletionInfo);
    }

    public Response<JsonArray> getResponse() {
        return this.response;
    }

    public List<String> getDefaultModifications() {
        return this.defaultModifications;
    }

    public boolean isDefaultAddon(String namespace) {
        return this.defaultModifications.contains(namespace);
    }

    private JsonArray getInstalledModifications() {
        if (!IOUtil.exists(this.installedInfoPath)) {
            if (IOUtil.exists(this.deletionInfoPath)) {
                JsonArray jsonArray = readJsonArrayFromPath(this.deletionInfoPath);
                saveInstalledModificationInfo(jsonArray);
                try {
                    IOUtil.delete(this.deletionInfoPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return jsonArray;
            }
            return new JsonArray();
        }
        return readJsonArrayFromPath(this.installedInfoPath);
    }

    private JsonArray readJsonArrayFromPath(Path path) {
        try {
            String content = new String(Files.readAllBytes(path));
            return (JsonArray) GsonUtil.DEFAULT_GSON.fromJson(content, JsonArray.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new JsonArray();
        }
    }

    private void saveInstalledModificationInfo(JsonArray jsonArray) {
        try {
            if (!IOUtil.exists(this.installedInfoPath)) {
                IOUtil.createFile(this.installedInfoPath);
            }
            Files.write(this.installedInfoPath, GsonUtil.DEFAULT_GSON.toJson(jsonArray).getBytes(), new OpenOption[0]);
            IOUtil.hideFileInWindowsFileSystem(this.installedInfoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
