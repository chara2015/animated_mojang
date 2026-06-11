package net.labymod.v1_8_9.client.chat;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.chat.Title;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.core.client.chat.DefaultChatExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/chat/VersionedChatExecutor.class */
@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor {
    private final ComponentMapper componentMapper;
    private final axu dummy = new axu(this) { // from class: net.labymod.v1_8_9.client.chat.VersionedChatExecutor.1
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
        axu.e(value);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message) {
        chat(message, true);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message, boolean addToHistory) {
        this.dummy.setMinecraft(ave.A());
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
        avo ingameGui = ave.A().q;
        eu mappedComponent = (eu) this.componentMapper.toMinecraftComponent(message);
        ingameGui.a(mappedComponent.d(), animate);
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
        avo gui = ave.A().q;
        if (gui.w <= 0) {
            return null;
        }
        return new Title(LegacyComponentSerializer.legacySection().deserialize(gui.x), LegacyComponentSerializer.legacySection().deserialize(gui.y), gui.z, gui.A, gui.B);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void showTitle(@NotNull Title title) {
        clearTitle();
        Component titleComponent = title.getTitle();
        avo gui = ave.A().q;
        if (titleComponent != null) {
            eu component = (eu) this.componentMapper.toMinecraftComponent(titleComponent);
            gui.a(component.d(), (String) null, 0, 0, 0);
        } else {
            gui.a("", (String) null, 0, 0, 0);
        }
        Component subTitleComponent = title.getSubTitle();
        if (subTitleComponent != null) {
            eu component2 = (eu) this.componentMapper.toMinecraftComponent(subTitleComponent);
            gui.a((String) null, component2.d(), 0, 0, 0);
        }
        gui.a((String) null, (String) null, title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void clearTitle() {
        ave.A().q.a((String) null, (String) null, -1, -1, -1);
    }

    @Override // net.labymod.core.client.chat.DefaultChatExecutor
    protected void rescaleChat() {
        avo gui = ave.A().q;
        if (gui == null) {
            return;
        }
        gui.d().b();
    }

    private void _displayClientMessage(Component message, boolean actionBar) {
        if (actionBar) {
            displayActionBar(message, false);
            return;
        }
        avo ingameGui = ave.A().q;
        eu component = (eu) this.componentMapper.toMinecraftComponent(message);
        ingameGui.d().a(component);
    }
}
