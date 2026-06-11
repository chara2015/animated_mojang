package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.laby3d.api.rhi.Color;
import net.labymod.laby3d.api.rhi.bind.BindGroup;
import net.labymod.laby3d.api.rhi.bind.BindGroupEntry;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayoutEntry;
import net.labymod.laby3d.api.rhi.bind.ShaderStage;
import net.labymod.laby3d.api.rhi.buffer.Buffer;
import net.labymod.laby3d.api.rhi.command.RenderPassEncoder;
import net.labymod.laby3d.api.rhi.pipeline.IndexFormat;
import net.labymod.laby3d.api.rhi.pipeline.RenderPipeline;
import net.labymod.laby3d.api.rhi.sync.OcclusionQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanRenderPassEncoder.class */
class VulkanRenderPassEncoder implements RenderPassEncoder {
    private static final int[] EMPTY_DYNAMIC_OFFSETS = new int[0];
    private final RenderPass mcPass;
    private final Map<String, GpuTextureView> pendingTextures = new HashMap();
    private final Map<String, GpuSampler> pendingSamplers = new HashMap();
    private boolean closed;

    VulkanRenderPassEncoder(RenderPass mcPass) {
        this.mcPass = mcPass;
    }

    public void setPipeline(RenderPipeline pipeline) {
        this.mcPass.setPipeline(((VulkanRenderPipeline) pipeline).mcPipeline());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void setBindGroup(int setIndex, BindGroup group) throws MatchException {
        setBindGroup(setIndex, group, EMPTY_DYNAMIC_OFFSETS);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void setBindGroup(int setIndex, BindGroup group, int... dynamicOffsets) throws MatchException {
        VulkanBindGroup vkGroup = (VulkanBindGroup) group;
        VulkanBindGroupLayout layout = vkGroup.layout();
        this.pendingTextures.clear();
        this.pendingSamplers.clear();
        int dynamicIdx = 0;
        for (BindGroupEntry.BufferBinding bufferBinding : vkGroup.entries()) {
            BindGroupLayoutEntry layoutEntry = layout.findEntry(bufferBinding.binding());
            if (layoutEntry == null) {
                throw new IllegalStateException("BindGroup entry at binding " + bufferBinding.binding() + " has no matching layout entry");
            }
            String name = VulkanBindGroupLayout.bindingName(layoutEntry);
            Objects.requireNonNull(bufferBinding);
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, BindGroupEntry.class, Integer.TYPE), BindGroupEntry.BufferBinding.class, BindGroupEntry.TextureBinding.class, BindGroupEntry.SamplerBinding.class).dynamicInvoker().invoke(bufferBinding, 0) /* invoke-custom */) {
                case 0:
                    BindGroupEntry.BufferBinding buf = bufferBinding;
                    long offset = buf.offset();
                    if (layoutEntry.hasDynamicOffset() && dynamicIdx < dynamicOffsets.length) {
                        int i = dynamicIdx;
                        dynamicIdx++;
                        offset += (long) dynamicOffsets[i];
                    }
                    GpuBuffer mcBuf = ((VulkanBuffer) buf.buffer()).mcBuffer();
                    this.mcPass.setUniform(name, mcBuf.slice(offset, buf.size()));
                    break;
                case 1:
                    BindGroupEntry.TextureBinding tex = (BindGroupEntry.TextureBinding) bufferBinding;
                    this.pendingTextures.put(name, ((VulkanTextureView) tex.view()).mcView());
                    break;
                case 2:
                    BindGroupEntry.SamplerBinding samp = (BindGroupEntry.SamplerBinding) bufferBinding;
                    this.pendingSamplers.put(name, ((VulkanSampler) samp.sampler()).mcSampler());
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
        for (Map.Entry<String, GpuTextureView> texEntry : this.pendingTextures.entrySet()) {
            GpuSampler sampler = this.pendingSamplers.get(texEntry.getKey());
            this.mcPass.bindTexture(texEntry.getKey(), texEntry.getValue(), sampler);
        }
    }

    public void setVertexBuffer(int slot, Buffer buffer) {
        setVertexBuffer(slot, buffer, 0L);
    }

    public void setVertexBuffer(int slot, Buffer buffer, long offset) {
        GpuBuffer mcBuf = ((VulkanBuffer) buffer).mcBuffer();
        GpuBufferSlice slice = mcBuf.slice(offset, mcBuf.size() - offset);
        this.mcPass.setVertexBuffer(slot, slice);
    }

    public void setIndexBuffer(Buffer buffer, IndexFormat format, long offset) {
        if (offset != 0) {
            throw new UnsupportedOperationException("Mojang's 26.2 RenderPass.setIndexBuffer takes no offset; non-zero offset unsupported");
        }
        GpuBuffer mcBuf = ((VulkanBuffer) buffer).mcBuffer();
        IndexType mcType = format == IndexFormat.UINT16 ? IndexType.SHORT : IndexType.INT;
        this.mcPass.setIndexBuffer(mcBuf, mcType);
    }

    public void setViewport(float x, float y, float w, float h, float minDepth, float maxDepth) {
    }

    public void setScissor(int x, int y, int w, int h) {
        if (w <= 0 || h <= 0) {
            this.mcPass.disableScissor();
        } else {
            this.mcPass.enableScissor(x, y, w, h);
        }
    }

    public void setBlendConstant(Color color) {
    }

    public void setStencilReference(int reference) {
    }

    public void pushConstants(EnumSet<ShaderStage> stages, int offset, ByteBuffer data) {
        throw new UnsupportedOperationException("VulkanRenderPassEncoder.pushConstants: Mojang's 26.2 RenderPass has no push-constant primitive; use a CPU-side UBO via setUniform instead");
    }

    public void draw(int vertexCount, int instanceCount, int firstVertex, int firstInstance) {
        if (instanceCount == 0 || vertexCount == 0) {
            return;
        }
        if (instanceCount > 1 || firstInstance != 0) {
            throw new UnsupportedOperationException("Mojang's 26.2 RenderPass.draw() doesn't support instancing or base-instance; use drawIndexed for instanced draws (no firstInstance support there either)");
        }
        this.mcPass.draw(firstVertex, vertexCount, firstVertex, firstInstance);
    }

    public void drawIndexed(int indexCount, int instanceCount, int firstIndex, int baseVertex, int firstInstance) {
        if (instanceCount == 0 || indexCount == 0) {
            return;
        }
        if (firstInstance != 0) {
            throw new UnsupportedOperationException("Mojang's 26.2 RenderPass.drawIndexed() doesn't support firstInstance != 0");
        }
        this.mcPass.drawIndexed(indexCount, instanceCount, firstIndex, baseVertex, firstInstance);
    }

    public void drawIndirect(Buffer indirectBuffer, long indirectOffset) {
        throw new UnsupportedOperationException("Mojang's 26.2 RenderPass has no indirect draw support");
    }

    public void drawIndexedIndirect(Buffer indirectBuffer, long indirectOffset) {
        throw new UnsupportedOperationException("Mojang's 26.2 RenderPass has no indirect draw support");
    }

    public void beginOcclusionQuery(OcclusionQuery query) {
        throw new UnsupportedOperationException("Mojang's 26.2 RenderPass exposes only timestamp queries, not occlusion queries");
    }

    public void endOcclusionQuery(OcclusionQuery query) {
        throw new UnsupportedOperationException("Mojang's 26.2 RenderPass exposes only timestamp queries, not occlusion queries");
    }

    public void end() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.mcPass.close();
    }
}
