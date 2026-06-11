package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.PolygonMode;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.laby3d.api.rhi.PipelineCreationException;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayout;
import net.labymod.laby3d.api.rhi.pipeline.ColorTargetState;
import net.labymod.laby3d.api.rhi.pipeline.CullMode;
import net.labymod.laby3d.api.rhi.pipeline.PipelineLayout;
import net.labymod.laby3d.api.rhi.pipeline.RenderPipeline;
import net.labymod.laby3d.api.rhi.pipeline.RenderPipelineDescriptor;
import net.labymod.laby3d.api.rhi.pipeline.VertexBufferLayout;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanRenderPipeline.class */
class VulkanRenderPipeline implements RenderPipeline {
    private final RenderPipelineDescriptor descriptor;
    private final com.mojang.blaze3d.pipeline.RenderPipeline mcPipeline;
    private final CompiledRenderPipeline compiled;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.rhi.PipelineCreationException */
    VulkanRenderPipeline(GpuDevice gpuDevice, RenderPipelineDescriptor descriptor) throws PipelineCreationException, MatchException {
        this.descriptor = descriptor;
        VulkanShaderModule shaderModule = (VulkanShaderModule) descriptor.shaderModule();
        VulkanPipelineLayout pipelineLayout = (VulkanPipelineLayout) descriptor.layout();
        Identifier shaderId = shaderModule.id();
        RenderPipeline.Builder builder = com.mojang.blaze3d.pipeline.RenderPipeline.builder(new RenderPipeline.Snippet[0]).withLocation(Identifier.parse("laby3d:rhi/pipeline/" + VulkanConverters.sanitizeIdentifierPath(descriptor.label()))).withVertexShader(shaderId).withFragmentShader(shaderId).withPrimitiveTopology(VulkanConverters.primitiveTopology(descriptor.topology())).withCull(toCull(descriptor.cullMode())).withPolygonMode(PolygonMode.FILL);
        for (BindGroupLayout layout : pipelineLayout.descriptor().bindGroupLayouts()) {
            builder.withBindGroupLayout(((VulkanBindGroupLayout) layout).mcLayout());
        }
        ColorTargetState[] colorTargets = descriptor.colorTargets();
        for (int i = 0; i < colorTargets.length; i++) {
            builder.withColorTargetState(i, VulkanConverters.colorTargetState(colorTargets[i]));
        }
        if (descriptor.depthStencil() != null) {
            builder.withDepthStencilState(VulkanConverters.depthStencilState(descriptor.depthStencil()));
        }
        VertexBufferLayout[] vbLayouts = descriptor.vertexBuffers();
        for (int i2 = 0; i2 < vbLayouts.length; i2++) {
            VertexFormat fmt = VulkanConverters.vertexFormat(vbLayouts[i2]);
            builder.withVertexBinding(i2, fmt);
        }
        try {
            this.mcPipeline = builder.build();
            ShaderSource shaderSource = shaderModule.asShaderSource();
            try {
                this.compiled = gpuDevice.precompilePipeline(this.mcPipeline, shaderSource);
            } catch (RuntimeException e) {
                throw new PipelineCreationException("Failed to compile pipeline: " + e.getMessage(), e);
            }
        } catch (RuntimeException e2) {
            throw new PipelineCreationException("Failed to build Mojang RenderPipeline: " + e2.getMessage(), e2);
        }
    }

    RenderPipelineDescriptor descriptor() {
        return this.descriptor;
    }

    com.mojang.blaze3d.pipeline.RenderPipeline mcPipeline() {
        return this.mcPipeline;
    }

    CompiledRenderPipeline compiled() {
        return this.compiled;
    }

    public PipelineLayout layout() {
        return this.descriptor.layout();
    }

    public void close() {
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan.VulkanRenderPipeline$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanRenderPipeline$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CullMode = new int[CullMode.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CullMode[CullMode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CullMode[CullMode.BACK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CullMode[CullMode.FRONT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static boolean toCull(CullMode mode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$pipeline$CullMode[mode.ordinal()]) {
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                throw new UnsupportedOperationException("FRONT face culling not supported by Mojang's 26.2 RenderPipeline (only NONE / BACK)");
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
