package net.labymod.api.client.gui.hud.hudwidget.background;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget.BackgroundHudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/background/BackgroundHudWidget.class */
public abstract class BackgroundHudWidget<T extends BackgroundHudWidgetConfig> extends SimpleHudWidget<T> implements HudWidgetBackgroundRenderer {
    protected BackgroundHudWidget(String id, Class<T> configClass) {
        super(id, configClass);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        if (phase.canRender()) {
            renderEntireBackground(context, size);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.background.HudWidgetBackgroundRenderer
    public BackgroundConfig config() {
        return ((BackgroundHudWidgetConfig) this.config).background();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/background/BackgroundHudWidget$BackgroundHudWidgetConfig.class */
    public static abstract class BackgroundHudWidgetConfig extends HudWidgetConfig {

        @SettingOrder(10)
        @CustomTranslation("labymod.hudWidget.background")
        @SettingRequires(value = "useGlobal", invert = true)
        private BackgroundConfig background = new BackgroundConfig();

        public BackgroundConfig background() {
            return (BackgroundConfig) config((v0) -> {
                return v0.background();
            }, this.background);
        }
    }
}
