package net.labymod.core.client.world.color;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/color/BlockColor.class */
public final class BlockColor extends Record {
    private final ResourceLocation blockResources;
    private final int color;

    public BlockColor(ResourceLocation blockResources, int color) {
        this.blockResources = blockResources;
        this.color = color;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockColor.class), BlockColor.class, "blockResources;color", "FIELD:Lnet/labymod/core/client/world/color/BlockColor;->blockResources:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/core/client/world/color/BlockColor;->color:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockColor.class), BlockColor.class, "blockResources;color", "FIELD:Lnet/labymod/core/client/world/color/BlockColor;->blockResources:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/core/client/world/color/BlockColor;->color:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockColor.class, Object.class), BlockColor.class, "blockResources;color", "FIELD:Lnet/labymod/core/client/world/color/BlockColor;->blockResources:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/core/client/world/color/BlockColor;->color:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ResourceLocation blockResources() {
        return this.blockResources;
    }

    public int color() {
        return this.color;
    }
}
