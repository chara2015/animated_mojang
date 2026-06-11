package net.labymod.core.client.gui.screen.activity.activities.ingame.chat;

import java.util.Iterator;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.ChatAccessor;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReloadEvent;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Lazy;
import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowRendererWidget;
import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/ChatOverlay.class */
@Implements(ChatAccessor.class)
@Link("activity/overlay/chat/chat.lss")
@AutoActivity
@Singleton
public class ChatOverlay extends IngameOverlayActivity implements ChatAccessor {
    private static final Lazy<AdvancedChatConfig> SETTINGS = Lazy.of(() -> {
        return Laby.labyAPI().config().ingame().advancedChat();
    });
    private final ChatProvider provider = Laby.references().chatProvider();
    private ChatWindowRendererWidget renderer;
    private DivWidget borderWidget;
    private float mouseX;
    private float mouseY;
    private boolean open;
    private boolean peekOpen;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.renderer = new ChatWindowRendererWidget(this);
        this.renderer.addId("renderer");
        ((Document) this.document).addChild(this.renderer);
        this.borderWidget = (DivWidget) new DivWidget().addId("border");
        ((Document) this.document).addChild(this.borderWidget);
    }

    @Subscribe
    public void onChatPeek(KeyEvent event) {
        Key chatPeekKey;
        if (this.labyAPI.minecraft().minecraftWindow().isScreenOpened() || !SETTINGS.get().enabled().get().booleanValue() || (chatPeekKey = SETTINGS.get().chatPeekKey().get()) == Key.NONE || event.key() != chatPeekKey) {
            return;
        }
        this.open = event.state() == KeyEvent.State.PRESS || event.state() == KeyEvent.State.HOLDING;
        this.peekOpen = this.open;
        if (event.state() != KeyEvent.State.HOLDING) {
            for (ChatWindowWidget window : this.renderer.getChildren()) {
                window.displayActiveTab();
            }
        }
    }

    @Subscribe
    public void onScreenOpen(ScreenDisplayEvent event) {
        if (!isVisible()) {
            return;
        }
        boolean nowOpen = NamedScreen.CHAT_INPUT.isScreen(event.getScreen()) || NamedScreen.CHAT_INPUT_IN_BED.isScreen(event.getScreen());
        if (this.open && !nowOpen) {
            Mouse mouse = this.labyAPI.minecraft().absoluteMouse();
            this.mouseX = mouse.getX();
            this.mouseY = mouse.getY();
            this.open = false;
            for (ChatWindowWidget window : this.renderer.getChildren()) {
                window.displayActiveTab();
                window.closeOverlays();
            }
            return;
        }
        if (nowOpen) {
            this.open = true;
            Iterator<ChatWindowWidget> it = this.renderer.getChildren().iterator();
            while (it.hasNext()) {
                it.next().displayActiveTab();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return false;
    }

    public boolean superMouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return false;
    }

    public boolean superMouseScrolled(MutableMouse mouse, double scrollDelta) {
        return super.mouseScrolled(mouse, scrollDelta);
    }

    @Subscribe
    public void onThemeUpdate(ThemeUpdateEvent event) {
        this.renderer.invalidateCache();
    }

    @Subscribe
    public void onCrosshairRender(IngameOverlayElementRenderEvent event) {
        if (event.elementType() != IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR || !SETTINGS.get().disableCrosshair().get().booleanValue() || !isVisible() || !isChatOpen()) {
            return;
        }
        event.setCancelled(true);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isAcceptingInput() {
        return isChatOpen() && this.labyAPI.minecraft().minecraftWindow().isScreenOpened();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isVisible() {
        return this.labyAPI.config().ingame().advancedChat().enabled().get().booleanValue() && (!this.labyAPI.minecraft().options().isHideGUI() || this.labyAPI.config().ingame().advancedChat().showChatOnHiddenGui().get().booleanValue());
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isRenderedHidden() {
        return this.peekOpen;
    }

    @Override // net.labymod.api.client.gui.screen.activity.activities.ingame.chat.ChatAccessor
    public boolean isChatOpen() {
        return this.open;
    }

    @Override // net.labymod.api.client.gui.screen.activity.activities.ingame.chat.ChatAccessor
    public ChatProvider getProvider() {
        return this.provider;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public int getPriority() {
        return 75;
    }

    public DivWidget getBorderWidget() {
        return this.borderWidget;
    }

    @Subscribe
    public void onAdvancedChatReload(AdvancedChatReloadEvent event) {
        if (this.renderer == null || !this.renderer.isVisible()) {
            return;
        }
        this.renderer.reInitialize();
    }
}
