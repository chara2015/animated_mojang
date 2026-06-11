package net.labymod.api.client.gui.screen.widget.widgets.popup;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/popup/AdvancedPopup.class */
public abstract class AdvancedPopup {
    private AdvancedPopupWidget widget;
    private WidgetReference overlayReference;
    private AdvancedPopupActivity activity;

    @Nullable
    public abstract Widget initialize();

    protected void onClose() {
    }

    protected void setup() {
    }

    protected void tick() {
    }

    @NotNull
    public WidgetReference displayInOverlay() {
        AdvancedPopupWidget widget = AdvancedPopupWidget.of(this);
        ScreenOverlayHandler screenOverlayHandler = Laby.references().screenOverlayHandler();
        this.overlayReference = screenOverlayHandler.displayInOverlay(widget);
        this.overlayReference.addDestroyHandler(this::onClose);
        this.overlayReference.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.NEVER);
        this.overlayReference.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
        return this.overlayReference;
    }

    public void displayAsActivity() {
        LabyAPI labyAPI = Laby.labyAPI();
        if (!labyAPI.minecraft().isIngame()) {
            throw new IllegalStateException("Can't display popup as activity while not ingame");
        }
        AdvancedPopupWidget widget = AdvancedPopupWidget.of(this);
        this.activity = new AdvancedPopupActivity(widget);
        labyAPI.minecraft().minecraftWindow().displayScreen(this.activity);
    }

    @NotNull
    protected final AdvancedPopupWidget widget() {
        if (this.widget == null) {
            throw new IllegalStateException("This method can only be called after the object has been handed to a AdvancedPopupWidget");
        }
        return this.widget;
    }

    protected final void close() {
        if (this.overlayReference != null && this.overlayReference.isAlive()) {
            this.overlayReference.remove();
        }
        if (this.activity != null) {
            this.activity.displayPreviousScreen();
            this.activity = null;
            onClose();
        }
    }

    @ApiStatus.Internal
    final void setup(AdvancedPopupWidget widget) {
        if (this.widget != null) {
            throw new IllegalStateException("Please don't reuse AdvancedPopup objects");
        }
        this.widget = widget;
        setup();
    }

    @Nullable
    public final WidgetReference getReference() {
        return this.overlayReference;
    }
}
