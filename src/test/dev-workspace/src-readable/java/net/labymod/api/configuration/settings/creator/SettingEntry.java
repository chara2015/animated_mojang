package net.labymod.api.configuration.settings.creator;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.type.SettingHeader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/SettingEntry.class */
public final class SettingEntry extends Record {
    private final SettingHeader header;
    private final SettingElement setting;

    public SettingEntry(SettingHeader header, SettingElement setting) {
        this.header = header;
        this.setting = setting;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SettingEntry.class), SettingEntry.class, "header;setting", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingEntry;->header:Lnet/labymod/api/configuration/settings/type/SettingHeader;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingEntry;->setting:Lnet/labymod/api/configuration/settings/type/SettingElement;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SettingEntry.class), SettingEntry.class, "header;setting", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingEntry;->header:Lnet/labymod/api/configuration/settings/type/SettingHeader;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingEntry;->setting:Lnet/labymod/api/configuration/settings/type/SettingElement;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SettingEntry.class, Object.class), SettingEntry.class, "header;setting", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingEntry;->header:Lnet/labymod/api/configuration/settings/type/SettingHeader;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingEntry;->setting:Lnet/labymod/api/configuration/settings/type/SettingElement;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public SettingHeader header() {
        return this.header;
    }

    public SettingElement setting() {
        return this.setting;
    }
}
