package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.OptionalDouble;
import net.labymod.laby3d.api.rhi.DeviceCapabilities;
import net.labymod.laby3d.api.rhi.DeviceLostListener;
import net.labymod.laby3d.api.rhi.PipelineCreationException;
import net.labymod.laby3d.api.rhi.Queue;
import net.labymod.laby3d.api.rhi.RenderDevice;
import net.labymod.laby3d.api.rhi.ResourceCreationException;
import net.labymod.laby3d.api.rhi.bind.BindGroup;
import net.labymod.laby3d.api.rhi.bind.BindGroupDescriptor;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayout;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayoutDescriptor;
import net.labymod.laby3d.api.rhi.buffer.Buffer;
import net.labymod.laby3d.api.rhi.buffer.BufferDescriptor;
import net.labymod.laby3d.api.rhi.command.CommandEncoder;
import net.labymod.laby3d.api.rhi.pipeline.PipelineLayout;
import net.labymod.laby3d.api.rhi.pipeline.PipelineLayoutDescriptor;
import net.labymod.laby3d.api.rhi.pipeline.RenderPipeline;
import net.labymod.laby3d.api.rhi.pipeline.RenderPipelineDescriptor;
import net.labymod.laby3d.api.rhi.shader.ShaderBundle;
import net.labymod.laby3d.api.rhi.shader.ShaderModule;
import net.labymod.laby3d.api.rhi.sync.OcclusionQuery;
import net.labymod.laby3d.api.rhi.texture.Sampler;
import net.labymod.laby3d.api.rhi.texture.SamplerDescriptor;
import net.labymod.laby3d.api.rhi.texture.Texture;
import net.labymod.laby3d.api.rhi.texture.TextureDescriptor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanRenderDevice.class */
public class VulkanRenderDevice implements RenderDevice {
    private final GpuDevice gpuDevice = RenderSystem.getDevice();
    private final VulkanQueue queue = new VulkanQueue(this);
    private final VulkanDeviceCapabilities capabilities = new VulkanDeviceCapabilities(this.gpuDevice);
    private DeviceLostListener deviceLostListener;

    GpuDevice gpuDevice() {
        return this.gpuDevice;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Buffer createBuffer(BufferDescriptor desc) throws ResourceCreationException, MatchException {
        int mcUsage = VulkanConverters.bufferUsage(desc.usage(), desc.memory());
        String label = desc.label();
        return new VulkanBuffer(this.gpuDevice.createBuffer(() -> {
            return label;
        }, mcUsage, desc.size()));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Texture createTexture(TextureDescriptor desc) throws ResourceCreationException, MatchException {
        int mcUsage = VulkanConverters.textureUsage(desc.usage());
        return new VulkanTexture(this.gpuDevice, this.gpuDevice.createTexture(desc.label(), mcUsage, VulkanConverters.format(desc.format()), desc.size().width(), desc.size().height(), desc.size().depth(), desc.mipLevels()));
    }

    public Sampler createSampler(SamplerDescriptor desc) throws ResourceCreationException {
        int aniso = Math.max(1, (int) desc.maxAnisotropy());
        return new VulkanSampler(this.gpuDevice.createSampler(VulkanConverters.addressMode(desc.addressU()), VulkanConverters.addressMode(desc.addressV()), VulkanConverters.filterMode(desc.minFilter()), VulkanConverters.filterMode(desc.magFilter()), aniso, desc.lodMax() > 0.0f ? OptionalDouble.of(desc.lodMax()) : OptionalDouble.empty()));
    }

    public ShaderModule createShaderModule(ShaderBundle bundle) throws ResourceCreationException {
        return new VulkanShaderModule(bundle);
    }

    public BindGroupLayout createBindGroupLayout(BindGroupLayoutDescriptor desc) {
        return new VulkanBindGroupLayout(desc);
    }

    public PipelineLayout createPipelineLayout(PipelineLayoutDescriptor desc) {
        return new VulkanPipelineLayout(desc);
    }

    public RenderPipeline createRenderPipeline(RenderPipelineDescriptor desc) throws PipelineCreationException {
        return new VulkanRenderPipeline(this.gpuDevice, desc);
    }

    public BindGroup createBindGroup(BindGroupDescriptor desc) {
        return new VulkanBindGroup(desc);
    }

    public OcclusionQuery createOcclusionQuery() {
        throw new UnsupportedOperationException("Occlusion queries not exposed by Mojang's 26.2 GpuDevice (only timestamp queries)");
    }

    public CommandEncoder createCommandEncoder() {
        return new VulkanCommandEncoder(this.gpuDevice);
    }

    public Queue queue() {
        return this.queue;
    }

    public DeviceCapabilities capabilities() {
        return this.capabilities;
    }

    public void onDeviceLost(DeviceLostListener listener) {
        this.deviceLostListener = listener;
    }

    public void close() {
    }
}
