package net.labymod.api.configuration.settings.type;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.service.DefaultRegistry;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/SettingGroup.class */
public class SettingGroup extends DefaultRegistry<Setting> implements Setting {
    private Icon icon;
    private Component displayName;
    private boolean experimental;
    private boolean filtered;
    private final Component description = Component.empty();
    private final String[] emptySearchTags = new String[0];

    public void addSetting(Setting setting) {
        register(setting);
    }

    public void addSettings(List<Setting> settings) {
        register((List) settings);
    }

    public SettingGroup of(List<Setting> settings) {
        register((List) settings);
        return this;
    }

    public SettingGroup filtered(boolean filtered) {
        this.filtered = filtered;
        return this;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public Icon getIcon() {
        return this.icon;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public Component displayName() {
        return this.displayName;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public Component getDescription() {
        return this.description;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public boolean hasAdvancedButton() {
        return false;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public Setting parent() {
        return null;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public String getPath() {
        return getId();
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public String getTranslationKey() {
        return getId();
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public String[] getSearchTags() {
        return this.emptySearchTags;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    @Nullable
    public String getRequiredPermission() {
        return null;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public boolean isInitialized() {
        return true;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public boolean isExperimental() {
        return this.experimental;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public void setExperimental(boolean experimental) {
        this.experimental = experimental;
    }

    public boolean isFiltered() {
        return this.filtered;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return "group";
    }

    public static SettingGroup named(Component component) {
        SettingGroup group = new SettingGroup();
        group.displayName = component;
        return group;
    }

    public static SettingGroup named(Component component, Icon icon) {
        SettingGroup group = new SettingGroup();
        group.displayName = component;
        group.icon = icon;
        return group;
    }

    public static SettingGroup empty() {
        return new SettingGroup();
    }
}
