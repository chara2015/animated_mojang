package net.labymod.v1_20_1.mixinplugin;

import net.labymod.api.mixin.ConfigPlugin;
import net.labymod.api.volt.plugin.VoltMixinConfigPlugin;
import net.labymod.core.addon.DefaultAddonService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixinplugin/LabyModMixinConfigPlugin.class */
@ConfigPlugin
public class LabyModMixinConfigPlugin extends VoltMixinConfigPlugin {
    @Override // net.labymod.api.volt.plugin.VoltMixinConfigPlugin
    protected boolean isValid(String name) {
        DefaultAddonService addonService = DefaultAddonService.getInstance();
        return addonService.shouldApplyDynamicMixin(name);
    }
}
