package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.uri.URIParser;
import net.labymod.api.util.ThreadSafe;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/WebNotificationMessageListener.class */
public class WebNotificationMessageListener implements MessageListener {
    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String packetMessage) {
        JsonElement element = (JsonElement) GSON.fromJson(packetMessage, JsonElement.class);
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            String title = object.get("title").getAsString();
            String message = object.get("message").getAsString();
            if (title == null || title.isEmpty() || message == null || message.isEmpty()) {
                return;
            }
            Notification.Builder builder = Notification.builder().title(Component.text(title)).text(Component.text(message)).duration(5000L);
            if (object.has("url")) {
                String url = object.get("url").getAsString();
                if (!url.isEmpty()) {
                    builder.onClick(notification -> {
                        Laby.labyAPI().minecraft().chatExecutor().openUrl(url, true);
                    });
                }
            }
            if (object.has("icon")) {
                String iconUrl = object.get("icon").getAsString();
                if (URIParser.isHttpUrl(iconUrl)) {
                    builder.icon(Icon.url(iconUrl));
                }
                if (object.has("secondaryIcon")) {
                    String secondaryIconUrl = object.get("secondaryIcon").getAsString();
                    if (URIParser.isHttpUrl(secondaryIconUrl)) {
                        builder.secondaryIcon(Icon.url(secondaryIconUrl));
                    }
                }
            }
            ThreadSafe.executeOnRenderThread(() -> {
                NotificationController notificationController = Laby.labyAPI().notificationController();
                notificationController.push(builder.build());
            });
        }
    }
}
