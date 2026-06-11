package net.labymod.core.addon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.accessor.impl.ConfigPropertySettingAccessor;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonDisablePacketHandler;
import net.labymod.serverapi.core.model.moderation.InstalledAddon;
import net.labymod.serverapi.core.packet.serverbound.game.moderation.AddonStateChangedPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/ServerAPIAddonService.class */
public class ServerAPIAddonService {
    private static final Logging LOGGER = Logging.create((Class<?>) ServerAPIAddonService.class);
    private final DefaultAddonService addonService;
    private boolean requestedAddonStateChanges;
    private Set<String> requestedAddons = new HashSet();
    private final Set<String> disabledAddons = new HashSet();

    protected ServerAPIAddonService(DefaultAddonService addonService) {
        this.addonService = addonService;
    }

    public static InstalledAddon addonToInstalledAddon(LoadedAddon loadedAddon, boolean enabled) {
        InstalledAddonInfo info = loadedAddon.info();
        Version version = loadedAddon.getVersion();
        return new InstalledAddon(info.getNamespace(), new InstalledAddon.AddonVersion(version.getMajor(), version.getMinor(), version.getPatch()), enabled, !info.isFlintAddon());
    }

    public boolean forceDisable(String namespace) {
        if (isForceDisabled(namespace)) {
            return false;
        }
        String namespace2 = namespace.toLowerCase(Locale.ENGLISH);
        this.disabledAddons.add(namespace2);
        AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace2);
        if (addonConfig != null) {
            ConfigProperty<Boolean> enabled = addonConfig.enabled();
            enabled.withPseudoValue(false);
            return enabled.getActualValue().booleanValue();
        }
        return false;
    }

    public boolean revertForceDisable(String namespace) {
        if (!isForceDisabled(namespace)) {
            return false;
        }
        String namespace2 = namespace.toLowerCase(Locale.ENGLISH);
        this.disabledAddons.remove(namespace2);
        AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace2);
        if (addonConfig != null) {
            ConfigProperty<Boolean> enabled = addonConfig.enabled();
            enabled.withoutPseudoValue();
            return enabled.get().booleanValue();
        }
        return false;
    }

    public void requestAddons(List<String> addons) {
        if (this.requestedAddonStateChanges) {
            if (this.requestedAddons.isEmpty()) {
                return;
            }
            if (addons.isEmpty()) {
                this.requestedAddons.clear();
                return;
            } else if (!this.requestedAddons.containsAll(addons)) {
                this.requestedAddons.addAll(addons);
                return;
            }
        }
        this.requestedAddonStateChanges = true;
        this.requestedAddons = new HashSet(addons);
    }

    public boolean isForceDisabled(String namespace) {
        return this.disabledAddons.contains(namespace);
    }

    @Subscribe(126)
    public void onServerLeave(ServerDisconnectEvent event) {
        this.requestedAddonStateChanges = false;
        this.requestedAddons = new HashSet();
        List<ConfigProperty<Boolean>> enabledProperties = new ArrayList<>();
        for (String namespace : this.disabledAddons) {
            AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace);
            if (addonConfig != null) {
                enabledProperties.add(addonConfig.enabled());
            }
        }
        this.disabledAddons.clear();
        for (ConfigProperty<Boolean> enabledProperty : enabledProperties) {
            enabledProperty.withoutPseudoValue();
        }
    }

    @Subscribe(126)
    public void onSetting(SettingInitializeEvent event) {
        Setting setting = event.setting();
        if (setting.hasParent()) {
            Setting settingParent = setting.parent();
            if (settingParent instanceof RootSettingRegistry) {
                RootSettingRegistry rootSettingRegistry = (RootSettingRegistry) settingParent;
                if (!setting.isElement()) {
                    return;
                }
                String namespace = rootSettingRegistry.getNamespace();
                Optional<LoadedAddon> optionalAddon = this.addonService.getAddon(namespace);
                if (optionalAddon.isEmpty()) {
                    return;
                }
                AddonConfig addonConfig = this.addonService.getMainConfiguration(namespace);
                ConfigProperty<Boolean> enabledProperty = addonConfig == null ? null : addonConfig.enabled();
                if (enabledProperty == null) {
                    return;
                }
                SettingElement settingElement = setting.asElement();
                SettingAccessor accessor = settingElement.getAccessor();
                if (accessor instanceof ConfigPropertySettingAccessor) {
                    ConfigPropertySettingAccessor accessor2 = (ConfigPropertySettingAccessor) accessor;
                    if (accessor2.property() != enabledProperty) {
                        return;
                    }
                    enabledProperty.addChangeListener(enabled -> {
                        updateEnabledState(namespace, enabledProperty, enabled.booleanValue());
                    });
                    updateEnabledState(namespace, enabledProperty, enabledProperty.get().booleanValue());
                    if (isForceDisabled(namespace)) {
                        AddonDisablePacketHandler.pushDisableNotification(optionalAddon.get().info().getDisplayName());
                    }
                    settingElement.setOverlayInfo(new SettingOverlayInfo(() -> {
                        return isForceDisabled(namespace);
                    }, Component.translatable("labymod.ui.settings.serverFeatures.disabledAddon", NamedTextColor.RED, Component.text(optionalAddon.get().info().getDisplayName(), NamedTextColor.GRAY)), false));
                }
            }
        }
    }

    private void updateEnabledState(String namespace, ConfigProperty<Boolean> property, boolean enabled) {
        if (enabled && isForceDisabled(namespace)) {
            property.withPseudoValue(false);
            LOGGER.warn("Addon {} was disabled by the server.", namespace);
        } else if (requestedAddon(namespace)) {
            this.addonService.getAddon(namespace).ifPresent(addon -> {
                Laby.references().labyModProtocolService().sendLabyModPacket(new AddonStateChangedPacket(addonToInstalledAddon(addon, enabled)));
            });
        }
    }

    private boolean requestedAddon(String namespace) {
        return this.requestedAddonStateChanges && (this.requestedAddons.contains(namespace) || this.requestedAddons.isEmpty());
    }
}
