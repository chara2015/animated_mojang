package net.labymod.api.configuration.settings.type.list;

import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.settings.type.SettingElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/list/ListSettingEntry.class */
public class ListSettingEntry extends SettingElement {
    private final Component displayName;
    private final int listIndex;

    public ListSettingEntry(ListSetting holder, int listIndex) {
        this(holder, Component.empty(), (String) null, listIndex);
    }

    public ListSettingEntry(ListSetting holder, String displayName, int listIndex) {
        this(holder, displayName, (String) null, listIndex);
    }

    public ListSettingEntry(ListSetting holder, Component displayName, int listIndex) {
        this(holder, displayName, (String) null, listIndex);
    }

    public ListSettingEntry(ListSetting holder, String displayName, String customTranslation, int listIndex) {
        this(holder, Component.text(displayName), customTranslation, listIndex);
    }

    public ListSettingEntry(ListSetting holder, Component displayName, String customTranslation, int listIndex) {
        super("entry", null, customTranslation, new String[0]);
        this.parent = holder;
        this.displayName = displayName;
        this.listIndex = listIndex;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public boolean hasAdvancedButton() {
        return true;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public Component displayName() {
        return this.displayName;
    }

    public int listIndex() {
        return this.listIndex;
    }

    public void remove() {
        ((ListSetting) this.parent).remove(this);
    }
}
