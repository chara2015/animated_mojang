package net.labymod.core.client.chat.autotext;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/autotext/AutoTextHandler.class */
@Singleton
public class AutoTextHandler {
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final ServerController serverController = Laby.references().serverController();

    @Inject
    public AutoTextHandler() {
    }

    @Subscribe
    public void handle(KeyEvent event) {
        if (!this.labyAPI.permissionRegistry().isPermissionEnabled("chat_autotext") || this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen() != null) {
            return;
        }
        AutoTextConfigAccessor autoTextConfig = Laby.references().chatProvider().autoTextConfigAccessor();
        if (event.state() != KeyEvent.State.PRESS || autoTextConfig.getEntries() == null || autoTextConfig.getEntries().isEmpty()) {
            return;
        }
        Key key = event.key();
        for (AutoTextEntry entry : autoTextConfig.getEntries()) {
            if (!entry.displayInInteractionMenu().get().booleanValue() && entry.requiresKey(key) && entry.isEveryKeyPressed() && entry.isActiveOnCurrentServer()) {
                String autoTextMessage = entry.message().get();
                if (!entry.sendImmediately().get().booleanValue()) {
                    this.labyAPI.minecraft().openChat(autoTextMessage);
                    if (!key.isAction()) {
                        event.setCancelled(true);
                        return;
                    }
                    return;
                }
                this.labyAPI.minecraft().chatExecutor().chat(autoTextMessage, false);
            }
        }
    }
}
