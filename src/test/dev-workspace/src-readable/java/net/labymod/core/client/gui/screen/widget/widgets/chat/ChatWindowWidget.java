package net.labymod.core.client.gui.screen.widget.widgets.chat;

import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatSettingActivity;
import net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget;
import net.labymod.core.client.gui.screen.widget.widgets.chat.tab.ChatTabWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/chat/ChatWindowWidget.class */
@AutoWidget
public class ChatWindowWidget extends NewWindowWidget implements WindowAccessor {
    private static final int INTERACTION_BACKGROUND_COLOR = ColorFormat.ARGB32.pack(255, 255, 255, 30);
    private static final Lazy<AdvancedChatConfig> SETTINGS = Lazy.of(() -> {
        return Laby.labyAPI().config().ingame().advancedChat();
    });
    private final ChatWindowRendererWidget renderer;
    private final ChatWindow window;
    private AbstractWidget<?> contextWidget;

    public ChatWindowWidget(ChatWindowRendererWidget renderer, ChatWindow window) {
        super(NewWindowWidget.DraggingType.CONTENT, window.isMainWindow() ? NewWindowWidget.RescaleType.TOP_RIGHT_BOTTOM_EDGES : NewWindowWidget.RescaleType.EDGES, BoundsType.OUTER, MouseButton.LEFT, MouseButton.LEFT);
        this.renderer = renderer;
        this.window = window;
        setInterpolateDuration(50.0f);
        displayActiveTab();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget, net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Widget widget = titleBarWidget();
        if (widget != null) {
            ((AbstractWidget) widget).opacity().set(Float.valueOf(this.renderer.chatOverlay().isChatOpen() ? 1.0f : 0.0f));
            widget.setVisible(hasTitleBar());
        }
    }

    public void displayActiveTab() {
        ChatTab activeTab = this.window.getActiveTab();
        displayTab(activeTab);
    }

    public void closeOverlays() {
        if (this.contextWidget != null) {
            this.contextWidget.closeContextMenu();
        }
    }

    public void displayTab(ChatTab tab) {
        tab.resetUnread();
        if (this.window.getActiveTab() != tab) {
            this.window.switchToTab(tab);
        }
        Widget widget = tab.createContentWidget(this);
        displayContent(widget);
        applyDefaultContextMenu(createContextMenu(), tab);
    }

    public void displayTabSettings(ChatTab tab) {
        RootChatTabConfig focusedTab = tab.rootConfig();
        ScreenRendererWidget renderer = new ScreenRendererWidget();
        AbstractSettingRegistry settingRegistry = focusedTab.config().asRegistry("chattab");
        ChatWindowRendererWidget chatWindowRendererWidget = this.renderer;
        Objects.requireNonNull(chatWindowRendererWidget);
        SettingContentActivity activity = new ChatSettingActivity(settingRegistry, chatWindowRendererWidget::save);
        activity.closeHandler(this::displayActiveTab);
        renderer.displayScreen(activity);
        displayContent(renderer);
        applySettingsContextMenu(createContextMenu());
    }

    @Override // net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor
    public void createNewTab() {
        RootChatTabConfig chatTabConfig = this.renderer.createNewTab(this.window.getTabs().size());
        ChatTab chatTab = this.window.initializeTab(chatTabConfig);
        this.window.switchToTab(chatTab);
        displayTabSettings(chatTab);
    }

    public void applyDefaultContextMenu(ContextMenu contextMenu, ChatTab tab) {
        RootChatTabConfig focusedTab = tab.rootConfig();
        boolean isMainTab = focusedTab.type().get() == RootChatTabConfig.Type.SERVER;
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.ADD).text(Component.translatable("labymod.activity.chat.context.newTab", new Component[0])).simpleClickHandler(mouse -> {
            createNewTab();
            this.window.save();
        }).build());
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.ADD).text(Component.translatable("labymod.activity.chat.context.newWindow", new Component[0])).simpleClickHandler(mouse2 -> {
            this.renderer.createNewWindow();
        }).build());
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.SETTINGS).text(Component.translatable("labymod.ui.button.settings", new Component[0])).simpleClickHandler(mouse3 -> {
            ChatTab activeTab = this.window.getActiveTab();
            displayTabSettings(activeTab);
        }).build());
        if (!isMainTab) {
            contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.X).text(Component.translatable("labymod.ui.button.delete", new Component[0])).simpleClickHandler(mouse4 -> {
                deleteTab(tab);
            }).build());
        }
    }

    private void deleteTab(ChatTab tab) {
        PopupWidget popup = PopupWidget.builder().title(Component.translatable("labymod.activity.chat.delete.title", new Component[0])).text(Component.translatable("labymod.activity.chat.delete.text", new Component[0])).confirmCallback(() -> {
            this.window.deleteTab(tab);
            if (this.window.getTabs().isEmpty()) {
                this.renderer.deleteWindow(this.window);
                this.renderer.save();
            } else {
                this.window.save();
                displayActiveTab();
            }
        }).build();
        popup.displayInOverlay();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void renderWidgetTheme(ScreenContext context) {
        if (isDragging() || isRescaling()) {
            Bounds bounds = bounds();
            ScreenCanvas screenCanvas = context.canvas();
            screenCanvas.submitRect(bounds, INTERACTION_BACKGROUND_COLOR);
        }
        if (!this.renderer.chatOverlay().isChatOpen()) {
            renderUnreadTabs(context);
        } else {
            updateTitleBar(false);
        }
        super.renderWidgetTheme(context);
    }

    private void renderUnreadTabs(ScreenContext context) {
        updateTitleBar(true);
        Widget widget = titleBarWidget();
        if (widget != null && hasUnread()) {
            Stack stack = context.stack();
            stack.push();
            stack.translate(0.0f, (-widget.bounds().getY()) + bounds().getBottom(), 0.0f);
            widget.renderWidget(context);
            stack.pop();
        }
    }

    private void updateTitleBar(boolean hide) {
        HorizontalListWidget widget = (HorizontalListWidget) titleBarWidget();
        if (widget == null) {
            return;
        }
        ChatTab activeTab = this.window.getActiveTab();
        activeTab.resetUnread();
        for (T entry : widget.getChildren()) {
            Widget child = entry.childWidget();
            if (child instanceof ChatTabWidget) {
                ChatTabWidget tabWidget = (ChatTabWidget) child;
                ChatTab tab = tabWidget.tab();
                int unread = tab.getUnread();
                tabWidget.updateUnread(unread);
                entry.setVisible((hide && unread == 0) ? false : true);
            } else if (hide) {
                entry.setVisible(false);
            }
        }
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    protected float getEdge(NewWindowWidget.Edge edge, NewWindowWidget.Edge side) {
        if ((edge == NewWindowWidget.Edge.LEFT && side == NewWindowWidget.Edge.TOP) || ((edge == NewWindowWidget.Edge.RIGHT && side == NewWindowWidget.Edge.TOP) || (edge == NewWindowWidget.Edge.TOP && (side == NewWindowWidget.Edge.TOP || side == NewWindowWidget.Edge.BOTTOM)))) {
            Rectangle rectangle = bounds().rectangle(this.boundsType);
            Widget titleBarWidget = titleBarWidget();
            boolean hasTitleBar = titleBarWidget != null && hasTitleBar();
            float titleBarHeight = hasTitleBar ? titleBarWidget.getContentHeight(BoundsType.OUTER) : 0.0f;
            Widget contentWidget = contentWidget();
            float contentHeight = contentWidget.getContentHeight(BoundsType.OUTER);
            if (contentHeight == 0.0f) {
                contentHeight = contentWidget.bounds().getHeight();
            }
            if (hasTitleBar || isRescaling()) {
                return rectangle.getTop() + titleBarHeight;
            }
            return Math.max(rectangle.getBottom() - contentHeight, rectangle.getTop() + titleBarHeight);
        }
        return super.getEdge(edge, side);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    protected boolean hasEdgeOffset() {
        Widget titleBarWidget = titleBarWidget();
        return titleBarWidget != null && hasTitleBar();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    protected void renderEdge(ScreenContext context, MutableMouse mouse, NewWindowWidget.Edge edge) {
        if (isRescaling()) {
            float left = getEdge(edge, NewWindowWidget.Edge.LEFT);
            float top = getEdge(edge, NewWindowWidget.Edge.TOP);
            float right = getEdge(edge, NewWindowWidget.Edge.RIGHT);
            float bottom = getEdge(edge, NewWindowWidget.Edge.BOTTOM);
            switch (edge) {
                case LEFT:
                    left -= 1.0f;
                    break;
                case TOP:
                    top -= 1.0f;
                    break;
                case RIGHT:
                    right += 1.0f;
                    break;
                case BOTTOM:
                    bottom += 1.0f;
                    break;
            }
            context.canvas().submitAbsoluteRect(left, top, right, bottom, -1);
        }
        super.renderEdge(context, mouse, edge);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    protected void dragToPosition(float x, float y, Bounds bounds) {
        if (this.window.isMainWindow()) {
            x = bounds.getX();
        }
        super.dragToPosition(x, y, bounds);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    public boolean isDraggingEnabled() {
        return SETTINGS.get().draggable().get().booleanValue();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    public boolean isRescalingEnabled() {
        return SETTINGS.get().resizeable().get().booleanValue() && !this.renderer.isHoveringChatInputTab();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    protected void onWindowPositionChanged() {
        this.window.config().setPosition(this.renderer.borderBounds(), bounds());
        super.onWindowPositionChanged();
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget
    protected Widget createTitleBar() {
        HorizontalListWidget bar = new HorizontalListWidget();
        for (ChatTab tab : this.window.getTabs()) {
            bar.addEntry(new ChatTabWidget(this, tab));
        }
        if (SETTINGS.get().showMenuButton().get().booleanValue()) {
            ButtonWidget contextWidget = ButtonWidget.icon(Textures.SpriteCommon.SMALL_BURGER);
            contextWidget.addId("context");
            applyDefaultContextMenu(contextWidget.createContextMenu(), this.window.getActiveTab());
            Objects.requireNonNull(contextWidget);
            contextWidget.setPressable(contextWidget::openContextMenu);
            bar.addEntry(contextWidget);
            this.contextWidget = contextWidget;
        }
        return bar;
    }

    private void displayContent(Widget widget) {
        setContentWidget(widget);
        if (this.initialized) {
            this.labyAPI.minecraft().executeNextTick(this::reInitialize);
        }
    }

    public ChatWindowConfig getConfig() {
        return this.window.config();
    }

    public ChatWindow window() {
        return this.window;
    }

    @Override // net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor
    public ChatOverlay chat() {
        return this.renderer.chatOverlay();
    }

    @Override // net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor
    public boolean hasTitleBar() {
        List<ChatTab> tabs = this.window.getTabs();
        int amount = tabs.size();
        if (amount == 0) {
            return false;
        }
        return amount > 1 || tabs.get(0).rootConfig().type().get() != RootChatTabConfig.Type.SERVER;
    }

    private boolean hasUnread() {
        for (ChatTab tab : this.window.getTabs()) {
            if (tab.getUnread() != 0) {
                return true;
            }
        }
        return false;
    }

    private void applySettingsContextMenu(ContextMenu contextMenu) {
        contextMenu.addEntry(ContextMenuEntry.builder().icon(Textures.SpriteCommon.X).text(Component.translatable("labymod.ui.button.close", new Component[0])).simpleClickHandler(mouse -> {
            displayActiveTab();
        }).build());
    }

    public void invalidateCache() {
        for (ChatTab tab : this.window.getTabs()) {
            tab.invalidateCache();
        }
    }
}
