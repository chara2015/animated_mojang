package net.labymod.api.configuration.settings.creator.availability;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.configuration.settings.creator.MemberInspector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext.class */
public final class MemberAvailabilityContext extends Record {
    private final String namespace;
    private final MemberInspector inspector;

    public MemberAvailabilityContext(String namespace, MemberInspector inspector) {
        this.namespace = namespace;
        this.inspector = inspector;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MemberAvailabilityContext.class), MemberAvailabilityContext.class, "namespace;inspector", "FIELD:Lnet/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext;->inspector:Lnet/labymod/api/configuration/settings/creator/MemberInspector;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MemberAvailabilityContext.class), MemberAvailabilityContext.class, "namespace;inspector", "FIELD:Lnet/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext;->inspector:Lnet/labymod/api/configuration/settings/creator/MemberInspector;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MemberAvailabilityContext.class, Object.class), MemberAvailabilityContext.class, "namespace;inspector", "FIELD:Lnet/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/availability/MemberAvailabilityContext;->inspector:Lnet/labymod/api/configuration/settings/creator/MemberInspector;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String namespace() {
        return this.namespace;
    }

    public MemberInspector inspector() {
        return this.inspector;
    }
}
