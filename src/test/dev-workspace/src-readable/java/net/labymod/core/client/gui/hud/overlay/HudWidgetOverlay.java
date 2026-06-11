package net.labymod.core.client.gui.hud.overlay;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.hud.HudWidgetMovedEvent;
import net.labymod.api.event.client.gui.hud.HudWidgetRegisterEvent;
import net.labymod.api.event.client.gui.hud.HudWidgetUpdateRequestEvent;
import net.labymod.api.event.client.misc.VanillaOptionsSaveEvent;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetRendererWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/overlay/HudWidgetOverlay.class */
@AutoActivity
public class HudWidgetOverlay extends IngameOverlayActivity {
    private static final String REASON_MOVED = "moved";
    private static final ModifyReason UPDATE_RENDERER_BOUNDS = ModifyReason.of("updateRendererBounds");
    private HudWidgetRendererWidget rendererWidget;
    private boolean previousItemInOffhand;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Window window = this.labyAPI.minecraft().minecraftWindow();
        this.rendererWidget = new HudWidgetRendererWidget();
        this.rendererWidget.addId("renderer");
        this.rendererWidget.bounds().set(window.absoluteBounds(), UPDATE_RENDERER_BOUNDS);
        ((Document) this.document).addChild(this.rendererWidget);
    }

    @Subscribe
    public void onHudWidgetRegister(HudWidgetRegisterEvent event) {
        if (this.rendererWidget != null) {
            this.rendererWidget.addHudWidget(event.hudWidget());
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
        if (this.rendererWidget != null) {
            this.rendererWidget.updateHudWidgets();
        }
    }

    @Subscribe
    public void onHudWidgetUpdate(HudWidgetMovedEvent event) {
        if (this.rendererWidget != null) {
            this.rendererWidget.reinitializeHudWidget(event.hudWidget(), REASON_MOVED);
        }
    }

    @Subscribe
    public void onConfigurationSave(ConfigurationSaveEvent event) {
        reload();
    }

    @Subscribe
    public void onVanillaOptionsSave(VanillaOptionsSaveEvent event) {
        if (event.getPhase() == Phase.PRE) {
            reload();
        }
    }

    @Subscribe
    public void onHudWidgetUpdateRequest(HudWidgetUpdateRequestEvent event) {
        if (this.rendererWidget != null) {
            this.rendererWidget.reinitializeHudWidget(event.hudWidget(), event.getReason());
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        Player clientPlayer = this.labyAPI.minecraft().getClientPlayer();
        if (clientPlayer != null) {
            ItemStack offhandItemStack = clientPlayer.getOffHandItemStack();
            boolean itemInOffhand = (offhandItemStack == null || offhandItemStack.isAir()) ? false : true;
            if (this.previousItemInOffhand != itemInOffhand) {
                for (HudWidgetDropzone dropzone : NamedHudWidgetDropzones.ITEMS) {
                    HudWidget<?> hudWidget = this.rendererWidget.getHudWidgetInDropzone(dropzone);
                    if (hudWidget != null) {
                        this.rendererWidget.updateHudWidget(hudWidget.firstWidget());
                    }
                }
                this.previousItemInOffhand = itemInOffhand;
            }
        }
        super.tick();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isVisible() {
        return this.labyAPI.config().ingame().hudWidgets().get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isAcceptingInput() {
        Window window = this.labyAPI.minecraft().minecraftWindow();
        ScreenWrapper screen = window.currentScreen();
        return NamedScreen.CHAT_INPUT.isScreen(screen) || NamedScreen.CHAT_INPUT_IN_BED.isScreen(screen);
    }
}
