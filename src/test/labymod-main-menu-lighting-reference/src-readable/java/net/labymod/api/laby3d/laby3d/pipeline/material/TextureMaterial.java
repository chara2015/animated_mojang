package net.labymod.api.laby3d.pipeline.material;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/TextureMaterial.class */
public final class TextureMaterial extends Record {
    private final DeviceTextureView textureView;

    public TextureMaterial(DeviceTextureView textureView) {
        this.textureView = textureView;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextureMaterial.class), TextureMaterial.class, "textureView", "FIELD:Lnet/labymod/api/laby3d/pipeline/material/TextureMaterial;->textureView:Lnet/labymod/laby3d/api/textures/DeviceTextureView;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextureMaterial.class), TextureMaterial.class, "textureView", "FIELD:Lnet/labymod/api/laby3d/pipeline/material/TextureMaterial;->textureView:Lnet/labymod/laby3d/api/textures/DeviceTextureView;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextureMaterial.class, Object.class), TextureMaterial.class, "textureView", "FIELD:Lnet/labymod/api/laby3d/pipeline/material/TextureMaterial;->textureView:Lnet/labymod/laby3d/api/textures/DeviceTextureView;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public DeviceTextureView textureView() {
        return this.textureView;
    }
}
