package net.labymod.v1_21_8.client.chat;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/chat/VersionedChatExecutor.class */
@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor {
    private final ComponentMapper componentMapper;
    private final gdp dummyChatScreen = new gdp("");

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
        fue.R().p.a(value);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message) {
        chat(message, true);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message, boolean addToHistory) {
        this.dummyChatScreen.b(fue.R(), 0, 0);
        this.dummyChatScreen.b(message, addToHistory);
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
        fxa chatGui = fue.R().m;
        xo component = (xo) this.componentMapper.toMinecraftComponent(message);
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
        fxa gui = fue.R().m;
        if (gui.au <= 0) {
            return null;
        }
        ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.av), mapper.fromMinecraftComponent(gui.aw), gui.ax, gui.ay, gui.az);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void showTitle(@NotNull Title title) {
        clearTitle();
        if (title.getTitle() != null) {
            fue.R().m.c((xo) Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        } else {
            fue.R().m.c(xo.i());
        }
        if (title.getSubTitle() != null) {
            fue.R().m.b((xo) Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        fue.R().m.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void clearTitle() {
        fue.R().m.d();
    }

    @Override // net.labymod.core.client.chat.DefaultChatExecutor
    protected void rescaleChat() {
        fue.R().m.e().b();
    }

    private void _displayClientMessage(Component message, boolean actionBar) {
        fxa chatGui = fue.R().m;
        xo component = (xo) this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        } else {
            chatGui.e().a(component);
        }
    }
}
