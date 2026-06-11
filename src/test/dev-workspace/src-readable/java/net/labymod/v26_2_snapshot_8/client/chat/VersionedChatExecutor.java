package net.labymod.v26_2_snapshot_8.client.chat;

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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.ChatScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/chat/VersionedChatExecutor.class */
@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor {
    private final ComponentMapper componentMapper;
    private final ChatScreen dummyChatScreen = new ChatScreen("", false);

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
        Minecraft.getInstance().keyboardHandler.setClipboard(value);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message) {
        chat(message, true);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void chat(String message, boolean addToHistory) {
        this.dummyChatScreen.init(0, 0);
        this.dummyChatScreen.handleChatInput(message, addToHistory);
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
        Hud hud = Minecraft.getInstance().gui.hud;
        net.minecraft.network.chat.Component component = (net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(message);
        hud.setOverlayMessage(component, animate);
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
        Hud gui = Minecraft.getInstance().gui.hud;
        if (gui.titleTime <= 0) {
            return null;
        }
        ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.title), mapper.fromMinecraftComponent(gui.subtitle), gui.titleFadeInTime, gui.titleStayTime, gui.titleFadeOutTime);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void showTitle(@NotNull Title title) {
        clearTitle();
        Hud hud = Minecraft.getInstance().gui.hud;
        if (title.getTitle() != null) {
            hud.setTitle((net.minecraft.network.chat.Component) Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        } else {
            hud.setTitle(net.minecraft.network.chat.Component.empty());
        }
        if (title.getSubTitle() != null) {
            hud.setSubtitle((net.minecraft.network.chat.Component) Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        hud.setTimes(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void clearTitle() {
        Minecraft.getInstance().gui.hud.clearTitles();
    }

    @Override // net.labymod.core.client.chat.DefaultChatExecutor
    protected void rescaleChat() {
        Minecraft.getInstance().gui.hud.getChat().rescaleChat();
    }

    private void _displayClientMessage(Component message, boolean actionBar) {
        Hud hud = Minecraft.getInstance().gui.hud;
        net.minecraft.network.chat.Component component = (net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            hud.setOverlayMessage(component, false);
        } else {
            hud.getChat().addClientSystemMessage(component);
        }
    }
}
