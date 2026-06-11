package net.labymod.v1_17_1.client.chat;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.chat.Title;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.chat.DefaultChatExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/chat/VersionedChatExecutor.class */
@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor {
    private final ComponentMapper componentMapper;
    private final eaq dummy = new eaq(this, (os) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(Component.empty())) { // from class: net.labymod.v1_17_1.client.chat.VersionedChatExecutor.1
    };

    @Inject
    public VersionedChatExecutor(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void insertText(String insertion, boolean skipInput) {
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof ChatScreenAccessor) {
            ChatScreenAccessor chatScreenAccessor = (ChatScreenAccessor) screen;
            chatScreenAccessor.insertChatText(insertion, false, skipInput);
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void suggestCommand(String command) {
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof ChatScreenAccessor) {
            ChatScreenAccessor chatScreenAccessor = (ChatScreenAccessor) screen;
            chatScreenAccessor.insertChatText(command, true);
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void copyToClipboard(String value) {
        dvp.C().n.a(value);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message) {
        chat(message, true);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message, boolean addToHistory) {
        this.dummy.setMinecraft(dvp.C());
        this.dummy.b(message, addToHistory);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void displayClientMessage(Component message, boolean actionBar) {
        if (ThreadSafe.isRenderThread()) {
            _displayClientMessage(message, actionBar);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                _displayClientMessage(message, actionBar);
            });
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void displayActionBar(Component message, boolean animate) {
        dwm chatGui = dvp.C().k;
        os component = (os) this.componentMapper.toMinecraftComponent(message);
        chatGui.a(component, animate);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    @Nullable
    public String getChatInputMessage() {
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof ChatScreenAccessor) {
            ChatScreenAccessor chatScreenAccessor = (ChatScreenAccessor) screen;
            return chatScreenAccessor.getChatText();
        }
        return null;
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public Title getTitle() {
        dwm gui = dvp.C().k;
        if (gui.H <= 0) {
            return null;
        }
        ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.I), mapper.fromMinecraftComponent(gui.J), gui.K, gui.L, gui.M);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void showTitle(@NotNull Title title) {
        clearTitle();
        if (title.getTitle() != null) {
            dvp.C().k.c((os) Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        } else {
            dvp.C().k.c(pf.d);
        }
        if (title.getSubTitle() != null) {
            dvp.C().k.b((os) Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        dvp.C().k.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void clearTitle() {
        dvp.C().k.c();
    }

    @Override // net.labymod.core.client.chat.DefaultChatExecutor
    protected void rescaleChat() {
        dvp.C().k.d().a();
    }

    private void _displayClientMessage(Component message, boolean actionBar) {
        dwm chatGui = dvp.C().k;
        os component = (os) this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        } else {
            chatGui.d().a(component);
        }
    }
}
