package net.labymod.core.client.gui.hud;

import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.core.client.render.font.text.TextUtil;
import net.labymod.core.event.addon.lifecycle.AddonStateChangeEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/HudWidgetListener.class */
public class HudWidgetListener {
    private final DefaultHudWidgetRegistry hudWidgetRegistry;

    public HudWidgetListener(DefaultHudWidgetRegistry hudWidgetRegistry) {
        this.hudWidgetRegistry = hudWidgetRegistry;
    }

    @Subscribe
    public void onSettingUpdate(SettingUpdateEvent event) {
        SettingAccessor accessor;
        if (event.phase() != Phase.POST || (accessor = event.setting().getAccessor()) == null || accessor.config() == null) {
            return;
        }
        Config config = accessor.config();
        boolean isGlobalConfig = config instanceof GlobalHudWidgetConfig;
        if (!isGlobalConfig && !(config instanceof HudWidgetConfig)) {
            return;
        }
        TextUtil.pushAndApplyAttributes();
        for (HudWidget<?> hudWidget : this.hudWidgetRegistry.values()) {
            if (isGlobalConfig || config.equals(hudWidget.getConfig())) {
                if (hudWidget.isEnabled()) {
                    hudWidget.reloadConfig();
                }
            }
        }
        TextUtil.popRenderAttributes();
    }

    @Subscribe
    public void onResourceReload(ResourceReloadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        TextUtil.pushAndApplyAttributes();
        for (HudWidget<?> widget : this.hudWidgetRegistry.values()) {
            widget.onUpdate();
        }
        TextUtil.popRenderAttributes();
    }

    @Subscribe
    public void onThemeUpdate(ThemeUpdateEvent event) {
        if (event.reason().equals(FancyThemeConfig.FONT_UPDATE_REASON)) {
            this.hudWidgetRegistry.updateHudWidgets("theme_update");
        }
    }

    @Subscribe
    public void onThemeChange(ThemeChangeEvent event) {
        if (event.phase() == Phase.POST) {
            this.hudWidgetRegistry.updateHudWidgets("theme_change");
        }
    }

    @Subscribe
    public void onAddonStateChange(AddonStateChangeEvent event) {
        this.hudWidgetRegistry.updateHudWidgets("addon_state_change");
    }
}
