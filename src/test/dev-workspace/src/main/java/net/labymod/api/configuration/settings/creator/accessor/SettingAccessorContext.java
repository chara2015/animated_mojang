package net.labymod.api.configuration.settings.creator.accessor;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.configuration.settings.creator.MemberInspector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext.class */
public final class SettingAccessorContext extends Record {
    private final MemberInspector element;
    private final Field field;

    public SettingAccessorContext(MemberInspector element, Field field) {
        this.element = element;
        this.field = field;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SettingAccessorContext.class), SettingAccessorContext.class, "element;field", "FIELD:Lnet/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext;->element:Lnet/labymod/api/configuration/settings/creator/MemberInspector;", "FIELD:Lnet/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext;->field:Ljava/lang/reflect/Field;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SettingAccessorContext.class), SettingAccessorContext.class, "element;field", "FIELD:Lnet/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext;->element:Lnet/labymod/api/configuration/settings/creator/MemberInspector;", "FIELD:Lnet/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext;->field:Ljava/lang/reflect/Field;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SettingAccessorContext.class, Object.class), SettingAccessorContext.class, "element;field", "FIELD:Lnet/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext;->element:Lnet/labymod/api/configuration/settings/creator/MemberInspector;", "FIELD:Lnet/labymod/api/configuration/settings/creator/accessor/SettingAccessorContext;->field:Ljava/lang/reflect/Field;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public MemberInspector element() {
        return this.element;
    }

    public Field field() {
        return this.field;
    }
}
