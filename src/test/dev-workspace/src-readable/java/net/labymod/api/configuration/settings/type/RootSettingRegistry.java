package net.labymod.api.configuration.settings.type;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.addon.AddonService;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/RootSettingRegistry.class */
public class RootSettingRegistry extends AbstractSettingRegistry {
    private final String namespace;
    private String translationId;
    private Component forcedDisplayName;

    protected RootSettingRegistry(String namespace, String id) {
        super(id, null);
        this.namespace = namespace;
        this.translationId = id;
    }

    public RootSettingRegistry translationId(String translationId) {
        this.translationId = translationId;
        return this;
    }

    public boolean isAddon() {
        return !this.namespace.equals("labymod");
    }

    public String getNamespace() {
        return this.namespace;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public String getTranslationId() {
        return this.translationId;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public Component displayName() {
        if (this.forcedDisplayName != null) {
            return this.forcedDisplayName;
        }
        return super.displayName();
    }

    public static <T extends AddonConfig> RootSettingRegistry addon(Object instance, T config) {
        LabyAPI labyAPI = Laby.labyAPI();
        AddonService addonService = labyAPI.addonService();
        String namespace = labyAPI.getNamespace(instance);
        RootSettingRegistry settingRegistry = new RootSettingRegistry(namespace, namespace).translationId("settings");
        settingRegistry.addSettings(config);
        addonService.getAddon(namespace).ifPresent(addon -> {
            settingRegistry.setForcedDisplayName(addon.info().getDisplayName());
        });
        return settingRegistry;
    }

    public void setForcedDisplayName(String forcedDisplayName) {
        if (forcedDisplayName == null) {
            this.forcedDisplayName = null;
        } else {
            this.forcedDisplayName = Component.text(forcedDisplayName);
        }
    }

    @ApiStatus.Internal
    public static RootSettingRegistry labymod(String id) {
        return new RootSettingRegistry("labymod", id);
    }

    @ApiStatus.Internal
    public static RootSettingRegistry custom(String namespace, String id) {
        return new RootSettingRegistry(namespace, id);
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public String getTranslationKey() {
        return this.namespace + "." + getTranslationId();
    }
}
