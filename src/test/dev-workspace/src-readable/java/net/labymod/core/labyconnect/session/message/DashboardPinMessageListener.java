package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonObject;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/DashboardPinMessageListener.class */
public class DashboardPinMessageListener implements MessageListener {
    private final Consumer<String> dashboardPinCallback;

    public DashboardPinMessageListener(Consumer<String> dashboardPinCallback) {
        this.dashboardPinCallback = dashboardPinCallback;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        JsonObject jsonObject = (JsonObject) GSON.fromJson(message, JsonObject.class);
        if (jsonObject.has("pin")) {
            String pin = jsonObject.get("pin").getAsString();
            if (this.dashboardPinCallback != null) {
                this.dashboardPinCallback.accept(pin);
            }
        }
    }
}
