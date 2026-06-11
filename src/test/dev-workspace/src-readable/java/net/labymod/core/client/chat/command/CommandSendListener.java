package net.labymod.core.client.chat.command;

import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.util.CollectionHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/command/CommandSendListener.class */
public class CommandSendListener {
    private final DefaultCommandService commandService;

    protected CommandSendListener(DefaultCommandService commandService) {
        this.commandService = commandService;
    }

    @Subscribe(127)
    public void onCommand(ChatMessageSendEvent event) {
        if (!event.isMessageCommand() || KeyHandler.isShiftDown()) {
            return;
        }
        String[] message = event.getMessage().substring(1).split(" ");
        String[] arguments = new String[message.length - 1];
        if (message.length > 1) {
            CollectionHelper.copyOfRange(message, arguments, 1, message.length);
        }
        if (this.commandService.fireCommand(message[0], arguments)) {
            event.setCancelled(true);
        }
    }
}
