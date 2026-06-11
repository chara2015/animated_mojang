package net.labymod.api.client.gfx.pipeline.post;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock;
import net.labymod.laby3d.api.RenderDeviceException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/PostPassData.class */
public final class PostPassData extends Record {
    private final String name;
    private final Map<String, CustomPostProcessorUniformBlock> blocks;

    public PostPassData(String name, Map<String, CustomPostProcessorUniformBlock> blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PostPassData.class), PostPassData.class, "name;blocks", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/PostPassData;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/PostPassData;->blocks:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PostPassData.class), PostPassData.class, "name;blocks", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/PostPassData;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/PostPassData;->blocks:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PostPassData.class, Object.class), PostPassData.class, "name;blocks", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/PostPassData;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/PostPassData;->blocks:Ljava/util/Map;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public Map<String, CustomPostProcessorUniformBlock> blocks() {
        return this.blocks;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    public CustomPostProcessorUniformBlock getBlock(String name) throws RenderDeviceException {
        CustomPostProcessorUniformBlock block = this.blocks.get(name);
        if (block == null) {
            throw new RenderDeviceException("The block '" + name + "' does not exist");
        }
        return block;
    }
}
