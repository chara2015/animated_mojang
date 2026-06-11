package net.labymod.core.labynet;

import java.util.regex.Matcher;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReceiveEvent;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.event.ServerDataEvent;
import net.labymod.api.labynet.models.ServerChat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/ServerChatDataHandler.class */
@Singleton
public class ServerChatDataHandler {
    private final ComponentRenderer componentRenderer = Laby.references().componentRenderer();
    private final LabyNetController labyNetController;

    @Inject
    public ServerChatDataHandler(LabyNetController labyNetController) {
        this.labyNetController = labyNetController;
    }

    @Subscribe
    public void handleChatMessage(ChatReceiveEvent event) {
        if (event instanceof AdvancedChatReceiveEvent) {
            return;
        }
        this.labyNetController.getCurrentServer().ifPresent(serverGroup -> {
            ServerChat chat = serverGroup.getChat();
            if (chat == null || chat.getEventMessages().isEmpty()) {
                return;
            }
            Component message = event.chatMessage().component();
            String rawMessage = this.componentRenderer.plainSerializer().serialize(message);
            chat.findEventMessage(rawMessage).ifPresent(eventMessage -> {
                Matcher matcher = eventMessage.pattern().matcher(rawMessage);
                if (!matcher.find()) {
                    return;
                }
                String[] arguments = new String[matcher.groupCount()];
                for (int i = 0; i < arguments.length; i++) {
                    arguments[i] = matcher.group(i + 1);
                }
                Laby.fireEvent(new ServerDataEvent(eventMessage.type(), arguments));
            });
        });
    }
}
