package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.SplitContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.hud.HudWidgetRegisterEvent;
import net.labymod.api.event.client.gui.hud.HudWidgetUpdateRequestEvent;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.HudWidgetWindowWidget;
import net.labymod.core.client.render.font.text.TextUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/WidgetsEditorActivity.class */
@Link("activity/hudwidget/hud-widget-editor.lss")
@AutoActivity
public class WidgetsEditorActivity extends Activity {
    private final HudWidgetWindowWidget windowWidget;
    private final HudWidgetInteractionWidget renderer;
    private boolean fullScreen;
    private ScreenWrapper previousRootScreen;
    private boolean widgetsDisabledWarningShown = false;
    private final SplitContentWidget<Widget, Widget> split = new SplitContentWidget<>();

    public WidgetsEditorActivity() {
        this.split.addId("split", "windowed");
        this.windowWidget = new HudWidgetWindowWidget(this);
        this.windowWidget.addId("window");
        this.renderer = new HudWidgetInteractionWidget(this.split.bounds(), this);
        this.renderer.addId("renderer", "bounds");
        this.renderer.setOpenSettingsListener(hudWidget -> {
            if (hudWidget == null) {
                this.windowWidget.displayList();
            } else {
                this.windowWidget.displaySettings(hudWidget);
            }
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ThemeService themeService = this.labyAPI.themeService();
        FancyThemeConfig config = (FancyThemeConfig) themeService.getThemeConfig(FancyThemeConfig.class);
        if (config != null) {
            setVariable("--force-vanilla-font", !config.isIngameFancyFont());
        }
        this.split.setLeft(this.windowWidget);
        DivWidget rendererWrapper = new DivWidget();
        rendererWrapper.addId("renderer-wrapper");
        if (DynamicBackgroundController.isEnabled()) {
            this.renderer.removeId("border");
        } else {
            this.renderer.addId("border");
        }
        rendererWrapper.addChild(this.renderer);
        this.split.setRight(rendererWrapper);
        ((Document) this.document).addChild(this.split);
        showWidgetsDisabledWarning();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen
    protected void postInitialize() {
        TextUtil.pushAndApplyAttributes();
        super.postInitialize();
        TextUtil.popRenderAttributes();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
        this.labyAPI.hudWidgetRegistry().updateHudWidgets("open_editor");
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        this.labyAPI.hudWidgetRegistry().updateHudWidgets("close_editor");
        Laby.references().hudWidgetRegistry().saveConfig();
    }

    @Subscribe
    public void onHudWidgetRegister(HudWidgetRegisterEvent event) {
        if (this.renderer != null) {
            this.renderer.addHudWidget(event.hudWidget());
        }
    }

    @Subscribe
    public void onHudWidgetUpdateRequest(HudWidgetUpdateRequestEvent event) {
        if (this.renderer != null && this.renderer.isInitialized()) {
            this.renderer.reinitializeHudWidget(event.hudWidget(), event.getReason());
        }
    }

    public void toggleFullscreen() {
        setFullScreen(!this.fullScreen);
    }

    public void setFullScreen(boolean fullScreen) {
        if (this.fullScreen == fullScreen) {
            return;
        }
        Window window = this.labyAPI.minecraft().minecraftWindow();
        ScreenWrapper previousRootScreen = window.currentScreen();
        this.fullScreen = fullScreen;
        this.split.removeId(fullScreen ? "windowed" : "full-screen");
        this.split.addId(fullScreen ? "full-screen" : "windowed");
        window.displayScreen(fullScreen ? this : this.previousRootScreen);
        this.previousRootScreen = previousRootScreen;
    }

    public boolean isFullScreen() {
        return this.fullScreen;
    }

    public HudWidgetInteractionWidget renderer() {
        return this.renderer;
    }

    public HudWidgetWindowWidget window() {
        return this.windowWidget;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    public boolean shouldHandleEscape() {
        if (this.windowWidget == null) {
            return super.shouldHandleEscape();
        }
        return this.windowWidget.shouldHandleEscape() || this.renderer.shouldHandleEscape();
    }

    private void showWidgetsDisabledWarning() {
        ConfigProperty<Boolean> widgetsEnabledProperty = this.labyAPI.config().ingame().hudWidgets();
        if (widgetsEnabledProperty.get().booleanValue() || this.widgetsDisabledWarningShown) {
            return;
        }
        this.widgetsDisabledWarningShown = true;
        SimpleAdvancedPopup.builder().title(Component.translatable("labymod.hudWidgetEditor.widgetsDisabledWarning.title", new Component[0])).description(Component.translatable("labymod.hudWidgetEditor.widgetsDisabledWarning.description", new Component[0])).addButtons(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.hudWidgetEditor.widgetsDisabledWarning.enable", new Component[0]), (Consumer<SimpleAdvancedPopup.SimplePopupButton>) simplePopupButton -> {
            widgetsEnabledProperty.set(true);
        })).addButtons(SimpleAdvancedPopup.SimplePopupButton.cancel()).build().displayInOverlay();
    }
}
