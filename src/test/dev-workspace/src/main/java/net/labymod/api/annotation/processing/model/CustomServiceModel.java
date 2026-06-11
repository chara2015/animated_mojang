package net.labymod.api.annotation.processing.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/model/CustomServiceModel.class */
public final class CustomServiceModel extends Record {
    private final String serviceClass;
    private final double classVersion;
    private final Map<String, Object> meta;

    public CustomServiceModel(String serviceClass, double classVersion, Map<String, Object> meta) {
        this.serviceClass = serviceClass;
        this.classVersion = classVersion;
        this.meta = meta;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CustomServiceModel.class), CustomServiceModel.class, "serviceClass;classVersion;meta", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->serviceClass:Ljava/lang/String;", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->classVersion:D", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->meta:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CustomServiceModel.class), CustomServiceModel.class, "serviceClass;classVersion;meta", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->serviceClass:Ljava/lang/String;", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->classVersion:D", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->meta:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CustomServiceModel.class, Object.class), CustomServiceModel.class, "serviceClass;classVersion;meta", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->serviceClass:Ljava/lang/String;", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->classVersion:D", "FIELD:Lnet/labymod/api/annotation/processing/model/CustomServiceModel;->meta:Ljava/util/Map;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String serviceClass() {
        return this.serviceClass;
    }

    public double classVersion() {
        return this.classVersion;
    }

    public Map<String, Object> meta() {
        return this.meta;
    }

    public CustomServiceModel(String serviceClass, double classVersion) {
        this(serviceClass, classVersion, new HashMap());
    }
}
