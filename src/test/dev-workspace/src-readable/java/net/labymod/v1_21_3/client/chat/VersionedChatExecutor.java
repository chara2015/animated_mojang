package net.labymod.v1_21_3.client.chat;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/chat/VersionedChatExecutor.class */
@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor {
    private final ComponentMapper componentMapper;
    private final fsu dummyChatScreen = new fsu("");

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
        fmg.Q().p.a(value);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message) {
        chat(message, true);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message, boolean addToHistory) {
        this.dummyChatScreen.b(fmg.Q(), 0, 0);
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
        fnr chatGui = fmg.Q().m;
        xv component = (xv) this.componentMapper.toMinecraftComponent(message);
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
        fnr gui = fmg.Q().m;
        if (gui.ay <= 0) {
            return null;
        }
        ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.az), mapper.fromMinecraftComponent(gui.aA), gui.aB, gui.aC, gui.aD);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void showTitle(@NotNull Title title) {
        clearTitle();
        if (title.getTitle() != null) {
            fmg.Q().m.c((xv) Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        } else {
            fmg.Q().m.c(xv.i());
        }
        if (title.getSubTitle() != null) {
            fmg.Q().m.b((xv) Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        fmg.Q().m.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void clearTitle() {
        fmg.Q().m.c();
    }

    @Override // net.labymod.core.client.chat.DefaultChatExecutor
    protected void rescaleChat() {
        fmg.Q().m.d().b();
    }

    private void _displayClientMessage(Component message, boolean actionBar) {
        fnr chatGui = fmg.Q().m;
        xv component = (xv) this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        } else {
            chatGui.d().a(component);
        }
    }
}
