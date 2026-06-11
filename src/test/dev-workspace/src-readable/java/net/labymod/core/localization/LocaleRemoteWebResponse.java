package net.labymod.core.localization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.labymod.api.util.io.web.WebResponse;
import net.labymod.api.util.io.web.exception.WebRequestException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/LocaleRemoteWebResponse.class */
public class LocaleRemoteWebResponse implements WebResponse<JsonElement> {
    private final Path translationPath;
    private JsonObject translationObject;

    public LocaleRemoteWebResponse(Path translationPath) {
        this.translationPath = translationPath;
    }

    @Override // net.labymod.api.util.io.web.WebResponse
    public void success(JsonElement result) {
        if (!result.isJsonObject()) {
            return;
        }
        this.translationObject = result.getAsJsonObject();
        try {
            Files.createFile(this.translationPath, new FileAttribute[0]);
            Files.write(this.translationPath, this.translationObject.toString().getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.util.io.web.WebResponse
    public void failed(WebRequestException exception) {
        try {
            this.translationObject = new JsonParser().parse(new String(Files.readAllBytes(this.translationPath))).getAsJsonObject();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public JsonObject getTranslationObject() {
        return this.translationObject;
    }
}
