package net.labymod.api.client.render.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/BoneGroup.class */
public final class BoneGroup extends Record {

    @NotNull
    private final List<ModelPart> rootParts;

    public BoneGroup(@NotNull List<ModelPart> rootParts) {
        this.rootParts = rootParts;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BoneGroup.class), BoneGroup.class, "rootParts", "FIELD:Lnet/labymod/api/client/render/model/BoneGroup;->rootParts:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BoneGroup.class), BoneGroup.class, "rootParts", "FIELD:Lnet/labymod/api/client/render/model/BoneGroup;->rootParts:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BoneGroup.class, Object.class), BoneGroup.class, "rootParts", "FIELD:Lnet/labymod/api/client/render/model/BoneGroup;->rootParts:Ljava/util/List;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @NotNull
    public List<ModelPart> rootParts() {
        return this.rootParts;
    }
}
