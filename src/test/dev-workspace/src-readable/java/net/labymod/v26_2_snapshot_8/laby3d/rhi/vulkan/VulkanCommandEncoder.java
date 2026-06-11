package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.OptionalDouble;
import net.labymod.laby3d.api.rhi.buffer.Buffer;
import net.labymod.laby3d.api.rhi.command.BufferTextureCopyLayout;
import net.labymod.laby3d.api.rhi.command.ColorAttachment;
import net.labymod.laby3d.api.rhi.command.CommandBuffer;
import net.labymod.laby3d.api.rhi.command.CommandEncoder;
import net.labymod.laby3d.api.rhi.command.DepthAttachment;
import net.labymod.laby3d.api.rhi.command.DepthStencilAttachment;
import net.labymod.laby3d.api.rhi.command.Extent3D;
import net.labymod.laby3d.api.rhi.command.LoadOp;
import net.labymod.laby3d.api.rhi.command.Origin3D;
import net.labymod.laby3d.api.rhi.command.RenderPassDescriptor;
import net.labymod.laby3d.api.rhi.command.RenderPassEncoder;
import net.labymod.laby3d.api.rhi.command.TextureCopyView;
import org.joml.Vector4f;
import org.joml.Vector4fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanCommandEncoder.class */
class VulkanCommandEncoder implements CommandEncoder {
    private final com.mojang.blaze3d.systems.CommandEncoder mcEncoder;

    VulkanCommandEncoder(GpuDevice gpuDevice) {
        this.mcEncoder = gpuDevice.createCommandEncoder();
    }

    com.mojang.blaze3d.systems.CommandEncoder mcEncoder() {
        return this.mcEncoder;
    }

    public RenderPassEncoder beginRenderPass(RenderPassDescriptor desc) {
        OptionalDouble optionalDoubleEmpty;
        Optional<Vector4fc> optionalEmpty;
        com.mojang.blaze3d.systems.RenderPassDescriptor mcDesc = com.mojang.blaze3d.systems.RenderPassDescriptor.create(() -> {
            return label(desc);
        });
        for (ColorAttachment attachment : desc.colorAttachments()) {
            GpuTextureView view = ((VulkanTextureView) attachment.view()).mcView();
            if (attachment.loadOp() == LoadOp.CLEAR) {
                optionalEmpty = Optional.of(new Vector4f(attachment.clearValue().red(), attachment.clearValue().green(), attachment.clearValue().blue(), attachment.clearValue().alpha()));
            } else {
                optionalEmpty = Optional.empty();
            }
            Optional<Vector4fc> clear = optionalEmpty;
            mcDesc.withColorAttachment(view, clear);
        }
        DepthStencilAttachment ds = desc.depthStencil();
        if (ds != null) {
            GpuTextureView depthView = ((VulkanTextureView) ds.view()).mcView();
            DepthAttachment depth = ds.depth();
            if (depth != null && depth.loadOp() == LoadOp.CLEAR) {
                optionalDoubleEmpty = OptionalDouble.of(depth.clearValue());
            } else {
                optionalDoubleEmpty = OptionalDouble.empty();
            }
            OptionalDouble clearDepth = optionalDoubleEmpty;
            mcDesc.withDepthAttachment(depthView, clearDepth);
        }
        RenderPass mcPass = this.mcEncoder.createRenderPass(mcDesc);
        return new VulkanRenderPassEncoder(mcPass);
    }

    public void copyBufferToBuffer(Buffer src, long srcOffset, Buffer dst, long dstOffset, long size) {
        GpuBufferSlice srcSlice = ((VulkanBuffer) src).mcBuffer().slice(srcOffset, size);
        GpuBufferSlice dstSlice = ((VulkanBuffer) dst).mcBuffer().slice(dstOffset, size);
        this.mcEncoder.copyToBuffer(srcSlice, dstSlice);
    }

    public void copyBufferToTexture(Buffer src, BufferTextureCopyLayout srcLayout, TextureCopyView dst, Extent3D extent) {
        throw new UnsupportedOperationException("VulkanCommandEncoder.copyBufferToTexture: Mojang's 26.2 abstract CommandEncoder does not expose buffer-to-texture copy; use writeTexture instead");
    }

    public void copyTextureToBuffer(TextureCopyView src, Buffer dst, BufferTextureCopyLayout dstLayout, Extent3D extent) {
        Origin3D origin = src.origin();
        this.mcEncoder.copyTextureToBuffer(((VulkanTexture) src.texture()).mcTexture(), ((VulkanBuffer) dst).mcBuffer(), dstLayout.offset(), () -> {
        }, src.mipLevel(), origin.x(), origin.y(), extent.width(), extent.height());
    }

    public void copyTextureToTexture(TextureCopyView src, TextureCopyView dst, Extent3D extent) {
        Origin3D srcOrigin = src.origin();
        Origin3D dstOrigin = dst.origin();
        this.mcEncoder.copyTextureToTexture(((VulkanTexture) src.texture()).mcTexture(), ((VulkanTexture) dst.texture()).mcTexture(), src.mipLevel(), dstOrigin.x(), dstOrigin.y(), srcOrigin.x(), srcOrigin.y(), extent.width(), extent.height());
    }

    public void writeBuffer(Buffer dst, long offset, ByteBuffer data) {
        GpuBufferSlice slice = ((VulkanBuffer) dst).mcBuffer().slice(offset, data.remaining());
        this.mcEncoder.writeToBuffer(slice, data);
    }

    public void writeTexture(TextureCopyView dst, ByteBuffer data, BufferTextureCopyLayout dataLayout, Extent3D extent) {
        Origin3D origin = dst.origin();
        this.mcEncoder.writeToTexture(((VulkanTexture) dst.texture()).mcTexture(), data, NativeImage.Format.RGBA, dst.mipLevel(), 0, origin.x(), origin.y(), extent.width(), extent.height());
    }

    public CommandBuffer finish() {
        return new VulkanCommandBuffer(this.mcEncoder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String label(RenderPassDescriptor desc) {
        return desc.label() != null ? desc.label() : "rhi-render-pass";
    }
}
