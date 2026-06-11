package net.labymod.core.client.chat.gui.listener;

import java.util.Objects;
import net.labymod.api.client.chat.input.ChatInputRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent;
import net.labymod.api.user.permission.ClientPermission;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/gui/listener/ChatButtonPermissionListener.class */
public class ChatButtonPermissionListener {
    private final ChatInputRegistry registry;

    public ChatButtonPermissionListener(ChatInputRegistry registry) {
        this.registry = registry;
    }

    @Subscribe
    public void onPermissionStateChange(PermissionStateChangeEvent event) {
        ClientPermission permission = event.permission();
        this.registry.forEach(widget -> {
            String permissionId = widget.getPermissionId();
            if (permissionId != null && Objects.equals(permission.getIdentifier(), permissionId)) {
                PermissionStateChangeEvent.State state = event.state();
                widget.setEnabled(state == PermissionStateChangeEvent.State.ALLOWED);
            }
        });
    }
}
