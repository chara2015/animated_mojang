package net.labymod.api.laby3d.pipeline.material;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/TextureBinding.class */
public final class TextureBinding extends Record {
    private final ResourceLocation location;

    public TextureBinding(ResourceLocation location) {
        this.location = location;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextureBinding.class), TextureBinding.class, "location", "FIELD:Lnet/labymod/api/laby3d/pipeline/material/TextureBinding;->location:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextureBinding.class), TextureBinding.class, "location", "FIELD:Lnet/labymod/api/laby3d/pipeline/material/TextureBinding;->location:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextureBinding.class, Object.class), TextureBinding.class, "location", "FIELD:Lnet/labymod/api/laby3d/pipeline/material/TextureBinding;->location:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ResourceLocation location() {
        return this.location;
    }
}
