package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.chat.gui.DefaultChatInputRegistry;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.core.configuration.labymod.chat.DefaultChatConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/input/ChatInputOverlay.class */
@Link("activity/chat/input/chat-input.lss")
@AutoActivity
public class ChatInputOverlay extends AbstractLayerActivity {
    private final List<KeyValue<ChatButtonWidget>> elements;
    private boolean lssLoaded;
    private ScreenRendererWidget subActivity;
    private DivWidget inputUnderlay;

    public ChatInputOverlay(ScreenInstance parentScreen) {
        super(parentScreen);
        this.mode = AbstractLayerActivity.ParentMode.OVERLAY;
        this.elements = this.labyAPI.chatProvider().chatInputService().getElements();
        for (KeyValue<ChatButtonWidget> entry : this.elements) {
            ChatButtonWidget widget = entry.getValue();
            if (widget.isEnabled()) {
                widget.setPressable(() -> {
                    switchTabTo(entry.getKey());
                });
            }
        }
        Bounds lastBounds = this.elements.get(0).getValue().bounds();
        this.lssLoaded = lastBounds.getWidth() > 0.0f;
    }

    private void switchTabTo(String id) {
        KeyValue<ChatButtonWidget> previousTab = getActiveTab();
        if (previousTab != null) {
            previousTab.getValue().setActive(false);
        }
        ChatButtonWidget activeTab = null;
        if (id != null && (previousTab == null || !previousTab.getKey().equals(id))) {
            activeTab = getTabById(id).getValue();
            activeTab.setActive(true);
        }
        if (activeTab == null) {
            this.subActivity.opacity().set(Float.valueOf(0.0f));
        } else {
            this.subActivity.opacity().set(Float.valueOf(1.0f));
            this.subActivity.displayScreen(activeTab.createScreen());
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Iterator<KeyValue<ChatButtonWidget>> it = this.elements.iterator();
        while (it.hasNext()) {
            it.next().getValue().reset();
        }
        super.initialize(parent);
        FlexibleContentWidget flexibleContentWidget = (FlexibleContentWidget) new FlexibleContentWidget().addId("screen");
        this.subActivity = new ScreenRendererWidget();
        this.subActivity.setPreviousScreenHandler(ignored -> {
            return true;
        });
        this.subActivity.addId("chat-input-tab-renderer");
        flexibleContentWidget.addFlexibleContent(this.subActivity);
        FlexibleContentWidget flexibleContentWidget2 = (FlexibleContentWidget) new FlexibleContentWidget().addId("container");
        DivWidget divWidget = new DivWidget();
        this.inputUnderlay = divWidget;
        flexibleContentWidget2.addFlexibleContent(divWidget).addId("chat-input");
        HorizontalListWidget list = new HorizontalListWidget();
        list.addId("chat-input-list");
        for (KeyValue<ChatButtonWidget> button : this.elements) {
            ChatButtonWidget value = button.getValue();
            if (value.getProperty() == null || value.getProperty().get().booleanValue()) {
                list.addEntry(value);
            }
        }
        flexibleContentWidget2.addContent(list);
        KeyValue<ChatButtonWidget> activeEntry = getActiveTab();
        if (activeEntry != null) {
            ChatButtonWidget activeWidget = activeEntry.getValue();
            this.subActivity.displayScreen(activeWidget.createScreen());
        }
        flexibleContentWidget.addContent(flexibleContentWidget2);
        ((Document) this.document).addChild(flexibleContentWidget);
        super.setPreviousScreen(null);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        switchTabTo(null);
        DefaultChatConfig.ChatConfigProvider.INSTANCE.save();
    }

    protected KeyValue<ChatButtonWidget> getTabById(String id) {
        for (KeyValue<ChatButtonWidget> element : this.elements) {
            if (element.getKey().equals(id)) {
                return element;
            }
        }
        return null;
    }

    protected KeyValue<ChatButtonWidget> getActiveTab() {
        for (KeyValue<ChatButtonWidget> element : this.elements) {
            ChatButtonWidget value = element.getValue();
            if (value.isActive()) {
                ConfigProperty<Boolean> property = value.getProperty();
                if (property != null && !property.get().booleanValue()) {
                    this.subActivity.displayScreen((ScreenInstance) null);
                    value.setActive(false);
                } else {
                    return element;
                }
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen
    protected void postInitialize() {
        super.postInitialize();
        if (!this.lssLoaded) {
            this.lssLoaded = true;
            DefaultChatInputRegistry chatInputRegistry = (DefaultChatInputRegistry) this.labyAPI.chatProvider().chatInputService();
            if (chatInputRegistry.getChatWidthRunnable() != null) {
                chatInputRegistry.getChatWidthRunnable().run();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        boolean consumed = super.mouseClicked(mouse, mouseButton);
        return consumed || ((ChatOverlay) Laby.references().chatAccessor()).superMouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        boolean consumed = super.mouseScrolled(mouse, scrollDelta);
        return consumed || ((ChatOverlay) Laby.references().chatAccessor()).superMouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public boolean isPauseScreen() {
        return false;
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (!NamedScreen.CHAT_INPUT_IN_BED.isScreen(this) || this.labyAPI.minecraft().getClientPlayer().isSleeping()) {
            return;
        }
        ChatExecutor chatExecutor = this.labyAPI.minecraft().chatExecutor();
        String chatInputMessage = chatExecutor.getChatInputMessage();
        Window window = this.labyAPI.minecraft().minecraftWindow();
        if (chatInputMessage == null || chatInputMessage.isEmpty()) {
            window.displayScreenRaw(null);
        } else {
            window.displayScreen(NamedScreen.CHAT_INPUT.create());
            chatExecutor.insertText(chatInputMessage);
        }
    }

    public DivWidget getInputUnderlay() {
        return this.inputUnderlay;
    }

    public ScreenRendererWidget getTabRenderer() {
        return this.subActivity;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.LabyScreen
    public boolean allowCustomFont() {
        return false;
    }
}
