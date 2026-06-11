package net.labymod.api.configuration.settings.creator;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingListener;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/SettingListenerMethod.class */
public final class SettingListenerMethod extends Record {
    private final String target;
    private final SettingListener.EventType eventType;
    private final Method method;
    private static final Logging LOGGER = Logging.getLogger();

    public SettingListenerMethod(String target, SettingListener.EventType eventType, Method method) {
        this.target = target;
        this.eventType = eventType;
        this.method = method;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SettingListenerMethod.class), SettingListenerMethod.class, "target;eventType;method", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->target:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->eventType:Lnet/labymod/api/configuration/settings/annotation/SettingListener$EventType;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->method:Ljava/lang/reflect/Method;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SettingListenerMethod.class), SettingListenerMethod.class, "target;eventType;method", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->target:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->eventType:Lnet/labymod/api/configuration/settings/annotation/SettingListener$EventType;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->method:Ljava/lang/reflect/Method;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SettingListenerMethod.class, Object.class), SettingListenerMethod.class, "target;eventType;method", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->target:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->eventType:Lnet/labymod/api/configuration/settings/annotation/SettingListener$EventType;", "FIELD:Lnet/labymod/api/configuration/settings/creator/SettingListenerMethod;->method:Ljava/lang/reflect/Method;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String target() {
        return this.target;
    }

    public SettingListener.EventType eventType() {
        return this.eventType;
    }

    public Method method() {
        return this.method;
    }

    public SettingListenerMethod(SettingListener listener, Method method) {
        this(listener.target(), listener.type(), method);
    }

    public void invoke(Object instance, Setting setting) {
        try {
            this.method.invoke(instance, setting);
        } catch (Exception exception) {
            LOGGER.error("Failed to invoke setting listener method", exception);
        }
    }
}
