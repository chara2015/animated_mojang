package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlRenderPipeline.class */
public final class GlRenderPipeline extends Record implements CompiledRenderPipeline {
    private final RenderPipeline info;
    private final GlProgram program;

    public GlRenderPipeline(RenderPipeline $$0, GlProgram $$1) {
        this.info = $$0;
        this.program = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlRenderPipeline.class), GlRenderPipeline.class, "info;program", "FIELD:Lcom/mojang/blaze3d/opengl/GlRenderPipeline;->info:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lcom/mojang/blaze3d/opengl/GlRenderPipeline;->program:Lcom/mojang/blaze3d/opengl/GlProgram;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlRenderPipeline.class), GlRenderPipeline.class, "info;program", "FIELD:Lcom/mojang/blaze3d/opengl/GlRenderPipeline;->info:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lcom/mojang/blaze3d/opengl/GlRenderPipeline;->program:Lcom/mojang/blaze3d/opengl/GlProgram;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlRenderPipeline.class, Object.class), GlRenderPipeline.class, "info;program", "FIELD:Lcom/mojang/blaze3d/opengl/GlRenderPipeline;->info:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "FIELD:Lcom/mojang/blaze3d/opengl/GlRenderPipeline;->program:Lcom/mojang/blaze3d/opengl/GlProgram;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public RenderPipeline info() {
        return this.info;
    }

    public GlProgram program() {
        return this.program;
    }

    @Override // com.mojang.blaze3d.pipeline.CompiledRenderPipeline
    public boolean isValid() {
        return this.program != GlProgram.INVALID_PROGRAM;
    }
}
