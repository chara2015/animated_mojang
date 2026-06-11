package net.labymod.v1_21_11.client.chat;

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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.ChatScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/chat/VersionedChatExecutor.class */
@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor {
    private final ComponentMapper componentMapper;
    private final ChatScreen dummyChatScreen = new ChatScreen("", false);

    @Inject
    public VersionedChatExecutor(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    public void insertText(String insertion, boolean skipInput) {
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof ChatScreenAccessor) {
            ChatScreenAccessor chatScreenAccessor = (ChatScreenAccessor) screen;
            chatScreenAccessor.insertChatText(insertion, false, skipInput);
        }
    }

    public void suggestCommand(String command) {
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof ChatScreenAccessor) {
            ChatScreenAccessor chatScreenAccessor = (ChatScreenAccessor) screen;
            chatScreenAccessor.insertChatText(command, true);
        }
    }

    public void copyToClipboard(String value) {
        Minecraft.getInstance().keyboardHandler.setClipboard(value);
    }

    public void chat(String message) {
        chat(message, true);
    }

    public void chat(String message, boolean addToHistory) {
        this.dummyChatScreen.init(0, 0);
        this.dummyChatScreen.handleChatInput(message, addToHistory);
    }

    public void displayClientMessage(Component message, boolean actionBar) {
        if (ThreadSafe.isRenderThread()) {
            _displayClientMessage(message, actionBar);
        } else {
            ThreadSafe.executeOnRenderThread(() -> {
                _displayClientMessage(message, actionBar);
            });
        }
    }

    public void displayActionBar(Component message, boolean animate) {
        Gui chatGui = Minecraft.getInstance().gui;
        net.minecraft.network.chat.Component component = (net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(message);
        chatGui.setOverlayMessage(component, animate);
    }

    @Nullable
    public String getChatInputMessage() {
        Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof ChatScreenAccessor) {
            ChatScreenAccessor chatScreenAccessor = (ChatScreenAccessor) screen;
            return chatScreenAccessor.getChatText();
        }
        return null;
    }

    public Title getTitle() {
        Gui gui = Minecraft.getInstance().gui;
        if (gui.titleTime <= 0) {
            return null;
        }
        ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.title), mapper.fromMinecraftComponent(gui.subtitle), gui.titleFadeInTime, gui.titleStayTime, gui.titleFadeOutTime);
    }

    public void showTitle(@NotNull Title title) {
        clearTitle();
        if (title.getTitle() != null) {
            Minecraft.getInstance().gui.setTitle((net.minecraft.network.chat.Component) Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        } else {
            Minecraft.getInstance().gui.setTitle(net.minecraft.network.chat.Component.empty());
        }
        if (title.getSubTitle() != null) {
            Minecraft.getInstance().gui.setSubtitle((net.minecraft.network.chat.Component) Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        Minecraft.getInstance().gui.setTimes(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }

    public void clearTitle() {
        Minecraft.getInstance().gui.clearTitles();
    }

    protected void rescaleChat() {
        Minecraft.getInstance().gui.getChat().rescaleChat();
    }

    private void _displayClientMessage(Component message, boolean actionBar) {
        Gui chatGui = Minecraft.getInstance().gui;
        net.minecraft.network.chat.Component component = (net.minecraft.network.chat.Component) this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.setOverlayMessage(component, false);
        } else {
            chatGui.getChat().addMessage(component);
        }
    }
}
