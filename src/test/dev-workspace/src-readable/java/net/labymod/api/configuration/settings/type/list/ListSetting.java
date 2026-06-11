package net.labymod.api.configuration.settings.type.list;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/list/ListSetting.class */
public class ListSetting extends SettingElement {
    private final Class<?> type;
    private final List<? extends Config> list;

    public ListSetting(String id, Icon icon, String customTranslation, String[] searchTags, String requiredPermission, SwitchableInfo switchableInfo, byte orderValue, SettingAccessor accessor) {
        this(id, icon, customTranslation, searchTags, new SettingPermissionHolder(requiredPermission), switchableInfo, orderValue, accessor);
    }

    public ListSetting(String id, Icon icon, String customTranslation, String[] searchTags, SettingPermissionHolder permissionHolder, SwitchableInfo switchableInfo, byte orderValue, SettingAccessor accessor) {
        super(id, icon, customTranslation, searchTags, permissionHolder, switchableInfo, orderValue);
        Type parameter = accessor.getGenericType();
        if (!(parameter instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Cannot determine type for config field " + id);
        }
        this.type = (Class) ((ParameterizedType) parameter).getActualTypeArguments()[0];
        this.list = (List) accessor.get();
    }

    public static Component defaultEntryName() {
        return Component.translatable("labymod.ui.settings.list.entry", new Component[0]);
    }

    public static Component defaultNewEntryName() {
        return Component.translatable("labymod.ui.settings.list.newEntry", new Component[0]);
    }

    public ListSettingEntry createNew() {
        ListSettingEntry entry = new ListSettingEntry(this, Component.empty(), this.list.size());
        try {
            ConfigAccessor configAccessor = (Config) this.type.getConstructor(new Class[0]).newInstance(new Object[0]);
            this.list.add(configAccessor);
            if (configAccessor instanceof ListSettingConfig) {
                entry.displayName().append(((ListSettingConfig) configAccessor).newEntryTitle());
            } else {
                entry.displayName().append(defaultNewEntryName());
            }
            entry.addSettings(configAccessor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public void remove(ListSettingEntry entry) {
        this.list.remove(entry.listIndex());
    }

    @Override // net.labymod.api.service.DefaultRegistry, net.labymod.api.service.Registry
    public List<KeyValue<Setting>> getElements() {
        List<KeyValue<Setting>> list = new ArrayList<>();
        int i = 0;
        while (i < this.list.size()) {
            Object object = this.list.get(i);
            if (object instanceof Config) {
                Config config = (Config) object;
                if (isInvalid(config)) {
                    int i2 = i;
                    i--;
                    this.list.remove(i2);
                } else {
                    try {
                        ListSettingEntry entry = new ListSettingEntry(this, displayName(config), i);
                        entry.addSettings(config);
                        list.add(new KeyValue<>(entry.getId(), entry));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            i++;
        }
        return list;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public boolean hasAdvancedButton() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean isInvalid(Config config) {
        if (config instanceof ListSettingConfig) {
            return ((ListSettingConfig) config).isInvalid();
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Component displayName(Config config) {
        if (!(config instanceof ListSettingConfig)) {
            return defaultEntryName();
        }
        ListSettingConfig listSettingConfig = (ListSettingConfig) config;
        Component component = listSettingConfig.entryDisplayName();
        if (component == null) {
            if (Laby.labyAPI().labyModLoader().isAddonDevelopmentEnvironment()) {
                throw new NullPointerException("ListSettingConfig#entryDisplayName() must not return null");
            }
            return defaultEntryName();
        }
        return component;
    }
}
