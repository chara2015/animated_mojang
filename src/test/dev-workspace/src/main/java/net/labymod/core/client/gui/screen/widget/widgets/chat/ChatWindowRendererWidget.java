package net.labymod.core.client.gui.screen.widget.widgets.chat;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.util.I18n;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.chat.advanced.DefaultAdvancedChatController;
import net.labymod.core.client.chat.advanced.DefaultChatWindow;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/chat/ChatWindowRendererWidget.class */
@AutoWidget
public class ChatWindowRendererWidget extends AbstractWidget<ChatWindowWidget> {
    private static final ModifyReason INITIALIZE_WINDOWS = ModifyReason.of("initializeWindows");
    private static final DefaultAdvancedChatController CONTROLLER = (DefaultAdvancedChatController) LabyMod.references().advancedChatController();
    private final ChatOverlay chatOverlay;

    public ChatWindowRendererWidget(ChatOverlay chatOverlay) {
        this.chatOverlay = chatOverlay;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        for (ChatWindow window : CONTROLLER.getWindows()) {
            ChatWindowWidget widget = new ChatWindowWidget(this, window);
            widget.addId("window");
            addChild(widget);
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (isHoveringChatInputTab()) {
            return false;
        }
        ChatWindowWidget mainWindow = null;
        boolean handled = false;
        for (ChatWindowWidget windowWidget : getGenericChildren()) {
            if (windowWidget.window().isMainWindow()) {
                mainWindow = windowWidget;
            }
            if (windowWidget.isHovered()) {
                handled = true;
                windowWidget.mouseScrolled(mouse, scrollDelta);
            }
        }
        if (!handled && mainWindow != null) {
            mainWindow.mouseScrolled(mouse, scrollDelta);
            handled = true;
        }
        return handled;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isHoveringChatInputTab()) {
            return false;
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    public RootChatTabConfig createNewTab(int index) {
        return new RootChatTabConfig(index, RootChatTabConfig.Type.CUSTOM, new GeneralChatTabConfig(I18n.translate("labymod.activity.chat.context.newTab", new Object[0])));
    }

    public void createNewWindow() {
        RootChatTabConfig tabConfig = createNewTab(0);
        ChatWindowConfig config = new ChatWindowConfig(tabConfig);
        ChatWindow window = new DefaultChatWindow(config);
        ChatTab chatTab = window.initializeTab(tabConfig, null, false);
        CONTROLLER.addWindow(window);
        Bounds bounds = bounds();
        float centerX = bounds.getCenterX();
        float centerY = bounds.getCenterY();
        ChatWindowWidget widget = new ChatWindowWidget(this, window);
        widget.addId("window");
        widget.bounds().setBounds(centerX - (200.0f / 2.0f), centerY - (80.0f / 2.0f), centerX + (200.0f / 2.0f), centerY + (80.0f / 2.0f), INITIALIZE_WINDOWS);
        addChildInitialized(widget);
        widget.displayTabSettings(chatTab);
    }

    public void deleteWindow(ChatWindow window) {
        CONTROLLER.deleteWindow(window);
        removeChildIf(widget -> {
            return widget.getConfig() == window.config();
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        Bounds bounds = borderBounds();
        for (ChatWindowWidget widget : getGenericChildren()) {
            ChatWindowConfig config = widget.getConfig();
            MutableRectangle rectangle = config.getPosition(bounds);
            if (widget.window().isMainWindow()) {
                rectangle.setX(bounds.getX());
            }
            widget.bounds().set(rectangle, INITIALIZE_WINDOWS);
        }
    }

    public void save() {
        CONTROLLER.saveConfig();
    }

    public ChatOverlay chatOverlay() {
        return this.chatOverlay;
    }

    public boolean isHoveringChatInputTab() {
        LabyScreen labyScreen = this.labyAPI.minecraft().minecraftWindow().currentLabyScreen();
        if (!(labyScreen instanceof ChatInputOverlay)) {
            return false;
        }
        ChatInputOverlay chatInputOverlay = (ChatInputOverlay) labyScreen;
        ScreenRendererWidget tabRenderer = chatInputOverlay.getTabRenderer();
        if (tabRenderer == null || !(tabRenderer.getScreen() instanceof ChatInputTabActivity)) {
            return false;
        }
        return ((ChatInputTabActivity) tabRenderer.getScreen()).isHovered();
    }

    public Bounds borderBounds() {
        DivWidget borderWidget = this.chatOverlay.getBorderWidget();
        if (borderWidget == null) {
            return bounds();
        }
        return borderWidget.bounds();
    }

    public void invalidateCache() {
        for (T windowWidget : this.children) {
            windowWidget.invalidateCache();
        }
    }
}
