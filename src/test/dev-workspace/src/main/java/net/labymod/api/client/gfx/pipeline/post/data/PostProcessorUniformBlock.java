package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.laby3d.api.shaders.UniformType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock.class */
public final class PostProcessorUniformBlock extends Record {

    @SerializedName("name")
    private final String name;

    @SerializedName("uniforms")
    private final List<Uniform> uniforms;

    public PostProcessorUniformBlock(String name, List<Uniform> uniforms) {
        this.name = name;
        this.uniforms = uniforms;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PostProcessorUniformBlock.class), PostProcessorUniformBlock.class, "name;uniforms", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock;->uniforms:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PostProcessorUniformBlock.class), PostProcessorUniformBlock.class, "name;uniforms", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock;->uniforms:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PostProcessorUniformBlock.class, Object.class), PostProcessorUniformBlock.class, "name;uniforms", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock;->uniforms:Ljava/util/List;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @SerializedName("name")
    public String name() {
        return this.name;
    }

    @SerializedName("uniforms")
    public List<Uniform> uniforms() {
        return this.uniforms;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform.class */
    public static final class Uniform extends Record {

        @SerializedName("name")
        private final String name;

        @SerializedName(ParameterType.TYPE)
        private final UniformType type;

        public Uniform(String name, UniformType type) {
            this.name = name;
            this.type = type;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Uniform.class), Uniform.class, "name;type", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform;->type:Lnet/labymod/laby3d/api/shaders/UniformType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Uniform.class), Uniform.class, "name;type", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform;->type:Lnet/labymod/laby3d/api/shaders/UniformType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Uniform.class, Object.class), Uniform.class, "name;type", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/post/data/PostProcessorUniformBlock$Uniform;->type:Lnet/labymod/laby3d/api/shaders/UniformType;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @SerializedName("name")
        public String name() {
            return this.name;
        }

        @SerializedName(ParameterType.TYPE)
        public UniformType type() {
            return this.type;
        }
    }
}
