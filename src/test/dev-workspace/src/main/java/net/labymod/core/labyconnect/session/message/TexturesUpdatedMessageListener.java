package net.labymod.core.labyconnect.session.message;

import net.labymod.api.client.session.model.MojangTexture;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.core.labyconnect.session.ApplyTextureController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/TexturesUpdatedMessageListener.class */
public class TexturesUpdatedMessageListener implements MessageListener {
    private final ApplyTextureController applyTextureController;

    public TexturesUpdatedMessageListener(ApplyTextureController applyTextureController) {
        this.applyTextureController = applyTextureController;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        MojangTextureChangedResponse response = (MojangTextureChangedResponse) GSON.fromJson(message, MojangTextureChangedResponse.class);
        MojangTexture[] skins = response.getSkins();
        if (skins.length > 0) {
            this.applyTextureController.applySkinTexture(response.getUniqueId(), skins[0]);
        }
        MojangTexture[] capes = response.getCapes();
        this.applyTextureController.applyCapeTexture(response.getUniqueId(), capes.length > 0 ? capes[0] : null);
    }
}
