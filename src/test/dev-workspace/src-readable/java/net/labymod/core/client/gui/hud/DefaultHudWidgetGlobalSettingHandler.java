package net.labymod.core.client.gui.hud;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/DefaultHudWidgetGlobalSettingHandler.class */
public class DefaultHudWidgetGlobalSettingHandler implements SettingHandler {
    private final HudWidgetRegistry hudWidgetRegistry = Laby.references().hudWidgetRegistry();

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public void created(Setting setting) {
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public void initialized(Setting setting) {
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public boolean opensActivity(Setting setting) {
        return true;
    }

    @Override // net.labymod.api.configuration.settings.SettingHandler
    public Activity activity(Setting setting) {
        AbstractSettingRegistry registry = this.hudWidgetRegistry.globalHudWidgetConfig().asRegistry(setting.getId()).translationId("hudWidget.global");
        return new SettingContentActivity(registry, false);
    }
}
