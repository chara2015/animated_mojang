package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.uri.URIParser;
import net.labymod.core.labyconnect.session.ApplyTextureController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/SkinApplyMessageListener.class */
public class SkinApplyMessageListener implements MessageListener {
    private final ApplyTextureController applyTextureController;

    public SkinApplyMessageListener(ApplyTextureController applyTextureController) {
        this.applyTextureController = applyTextureController;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        JsonElement element = (JsonElement) GSON.fromJson(message, JsonElement.class);
        if (!element.isJsonObject()) {
            return;
        }
        JsonObject object = element.getAsJsonObject();
        String variant = object.get("variant").getAsString();
        String downloadUrl = object.get("downloadUrl").getAsString();
        String previewUrl = object.get("previewUrl").getAsString();
        if (!URIParser.isHttpUrl(downloadUrl) || !URIParser.isHttpUrl(previewUrl)) {
            return;
        }
        this.applyTextureController.requestUploadSkin(Icon.url(previewUrl), () -> {
            this.applyTextureController.uploadSkinAsync(MinecraftServices.SkinVariant.of(variant), new MinecraftServices.SkinPayload(downloadUrl));
        });
    }
}
