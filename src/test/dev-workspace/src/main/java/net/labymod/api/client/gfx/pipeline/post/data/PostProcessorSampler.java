package net.labymod.api.client.gfx.pipeline.post.data;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler.class */
public final class PostProcessorSampler extends Record {
    private final String name;
    private final int index;

    public PostProcessorSampler(String name, int index) {
        this.name = name;
        this.index = index;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PostProcessorSampler.class), PostProcessorSampler.class, "name;index", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler;->index:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PostProcessorSampler.class), PostProcessorSampler.class, "name;index", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler;->index:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PostProcessorSampler.class, Object.class), PostProcessorSampler.class, "name;index", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorSampler;->index:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public int index() {
        return this.index;
    }
}
