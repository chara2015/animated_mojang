package net.labymod.api.client.gui.screen.util.metadata;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/metadata/WidgetMetadata.class */
public final class WidgetMetadata extends Record {
    private final String namespace;
    private final String qualifiedName;
    private final String simpleName;
    private final DirectPropertyValueAccessor propertyAccessor;

    public WidgetMetadata(String namespace, String qualifiedName, String simpleName, DirectPropertyValueAccessor propertyAccessor) {
        this.namespace = namespace;
        this.qualifiedName = qualifiedName;
        this.simpleName = simpleName;
        this.propertyAccessor = propertyAccessor;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WidgetMetadata.class), WidgetMetadata.class, "namespace;qualifiedName;simpleName;propertyAccessor", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->qualifiedName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->simpleName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->propertyAccessor:Lnet/labymod/api/client/gui/lss/property/DirectPropertyValueAccessor;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WidgetMetadata.class), WidgetMetadata.class, "namespace;qualifiedName;simpleName;propertyAccessor", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->qualifiedName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->simpleName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->propertyAccessor:Lnet/labymod/api/client/gui/lss/property/DirectPropertyValueAccessor;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WidgetMetadata.class, Object.class), WidgetMetadata.class, "namespace;qualifiedName;simpleName;propertyAccessor", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->qualifiedName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->simpleName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gui/screen/util/metadata/WidgetMetadata;->propertyAccessor:Lnet/labymod/api/client/gui/lss/property/DirectPropertyValueAccessor;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String namespace() {
        return this.namespace;
    }

    public String qualifiedName() {
        return this.qualifiedName;
    }

    public String simpleName() {
        return this.simpleName;
    }

    public DirectPropertyValueAccessor propertyAccessor() {
        return this.propertyAccessor;
    }
}
