package net.labymod.core.main.listener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.type.SettingHeader;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.collection.map.HashMultimap;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.DefaultAddonService;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/HotkeySettingListener.class */
public class HotkeySettingListener {
    private static final Logging LOGGER = Logging.getLogger();
    private static final String ROOT_HOTKEYS_KEY = "settings.hotkeys";
    private final Multimap<String, Setting> hotkeysSettings = new HashMultimap();
    private final Map<String, DynamicSettingHeader> headers = new HashMap();
    private Setting rootHotkeysSettings;

    @Subscribe
    public void onSettingInitialize(SettingInitializeEvent event) {
        Setting setting = event.setting();
        if (!(setting instanceof SettingElement)) {
            return;
        }
        SettingElement settingElement = (SettingElement) setting;
        RootSettingRegistry registry = findSettingRegistry(setting);
        if (registry == null) {
            LOGGER.warn("Could not find root setting registry for setting {}", setting.getPath());
            return;
        }
        SettingAccessor accessor = settingElement.getAccessor();
        if (accessor != null) {
            boolean hasKeybindWidget = hasKeybindWidget(settingElement.getWidgets());
            if (hasKeybindWidget) {
                this.hotkeysSettings.put(registry.getNamespace(), setting);
            }
        }
        String path = setting.getPath();
        if ("labymod".equals(registry.getNamespace()) && ROOT_HOTKEYS_KEY.equals(path)) {
            this.rootHotkeysSettings = settingElement;
        }
        if (this.rootHotkeysSettings != null) {
            registerLabyModSettings();
            this.hotkeysSettings.forEach(this::registerSetting);
        }
    }

    private void registerLabyModSettings() {
        Collection<Setting> settings = this.hotkeysSettings.get("labymod");
        if (settings.isEmpty()) {
            return;
        }
        for (Setting setting : settings) {
            Setting parent = setting.parent();
            if (parent != this.rootHotkeysSettings) {
                this.rootHotkeysSettings.register(setting);
            }
        }
        this.hotkeysSettings.remove("labymod");
    }

    private void registerSetting(Map.Entry<String, Setting> entry) {
        String namespace = entry.getKey();
        if ("labymod".equals(namespace)) {
            return;
        }
        Setting setting = entry.getValue();
        KeyValue<Setting> node = this.rootHotkeysSettings.getElementById(setting.getId());
        if (node == null) {
            if (this.headers.get(namespace) == null) {
                DynamicSettingHeader dynamicSettingHeader = new DynamicSettingHeader(namespace);
                this.rootHotkeysSettings.register(dynamicSettingHeader);
                this.headers.put(namespace, dynamicSettingHeader);
            }
            this.rootHotkeysSettings.register(setting);
        }
    }

    private boolean hasKeybindWidget(Widget[] widgets) {
        if (widgets == null) {
            return false;
        }
        for (Widget widget : widgets) {
            if (widget instanceof KeybindWidget) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private RootSettingRegistry findSettingRegistry(Setting setting) {
        Setting parent = setting;
        while (!(parent instanceof RootSettingRegistry)) {
            parent = parent.parent();
            if (parent == null) {
                break;
            }
        }
        if (!(parent instanceof RootSettingRegistry)) {
            return null;
        }
        RootSettingRegistry rootSettingRegistry = (RootSettingRegistry) parent;
        return rootSettingRegistry;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/HotkeySettingListener$DynamicSettingHeader.class */
    static class DynamicSettingHeader extends SettingHeader {
        private static final String UNKNOWN_NAME = "Unknown";
        private final String namespace;
        private final List<Component> rows;
        private final boolean unknown;

        public DynamicSettingHeader(String namespace) {
            super(namespace, false, namespace, namespace);
            DefaultAddonService addonService = DefaultAddonService.getInstance();
            String displayName = (String) addonService.getAddon(namespace).map(loadedAddon -> {
                return loadedAddon.info().getDisplayName();
            }).orElse(UNKNOWN_NAME);
            this.namespace = namespace;
            this.unknown = UNKNOWN_NAME.equals(displayName);
            this.rows = Collections.singletonList(Component.text(displayName));
        }

        @Override // net.labymod.api.configuration.settings.type.SettingHeader
        public List<Component> getRows() {
            return this.rows;
        }

        @Override // net.labymod.api.configuration.settings.type.SettingHeader
        public Pressable pressable() {
            return () -> {
                Setting setting = Laby.labyAPI().coreSettingRegistry().getById(this.namespace);
                if (setting == null) {
                    return;
                }
                Laby.labyAPI().showSetting(setting);
            };
        }

        public boolean isUnknown() {
            return this.unknown;
        }
    }
}
