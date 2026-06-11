package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectTokenEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labyconnect.session.DefaultTokenStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/UpdateTokenMessageListener.class */
public class UpdateTokenMessageListener implements MessageListener {
    private static final Logging LOGGER = Logging.getLogger();
    private final LabyConnect labyConnect;
    private final User self;
    private final DefaultTokenStorage tokenStorage;

    public UpdateTokenMessageListener(LabyConnect labyConnect, User self, DefaultTokenStorage tokenStorage) {
        this.labyConnect = labyConnect;
        this.self = self;
        this.tokenStorage = tokenStorage;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        try {
            JsonObject object = (JsonObject) GSON.fromJson(message, JsonObject.class);
            if (object.has("purpose")) {
                TokenStorage.Purpose purpose = TokenStorage.Purpose.valueOf(object.get("purpose").getAsString());
                String tokenString = object.get("token").getAsString();
                long expiresAt = object.get("expires_at").getAsLong();
                TokenStorage.Token token = new TokenStorage.Token(tokenString, expiresAt);
                UUID uniqueId = this.self.getUniqueId();
                this.tokenStorage.updateToken(purpose, uniqueId, token);
                Laby.fireEvent(new LabyConnectTokenEvent(this.labyConnect, purpose, uniqueId, token));
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to parse token", exception);
        }
    }
}
