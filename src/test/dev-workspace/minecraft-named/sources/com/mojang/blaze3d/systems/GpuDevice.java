package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/GpuDevice.class */
@DontObfuscate
public interface GpuDevice {
    CommandEncoder createCommandEncoder();

    GpuSampler createSampler(AddressMode addressMode, AddressMode addressMode2, FilterMode filterMode, FilterMode filterMode2, int i, OptionalDouble optionalDouble);

    GpuTexture createTexture(Supplier<String> supplier, @GpuTexture.Usage int i, TextureFormat textureFormat, int i2, int i3, int i4, int i5);

    GpuTexture createTexture(String str, @GpuTexture.Usage int i, TextureFormat textureFormat, int i2, int i3, int i4, int i5);

    GpuTextureView createTextureView(GpuTexture gpuTexture);

    GpuTextureView createTextureView(GpuTexture gpuTexture, int i, int i2);

    GpuBuffer createBuffer(Supplier<String> supplier, @GpuBuffer.Usage int i, long j);

    GpuBuffer createBuffer(Supplier<String> supplier, @GpuBuffer.Usage int i, ByteBuffer byteBuffer);

    String getImplementationInformation();

    List<String> getLastDebugMessages();

    boolean isDebuggingEnabled();

    String getVendor();

    String getBackendName();

    String getVersion();

    String getRenderer();

    int getMaxTextureSize();

    int getUniformOffsetAlignment();

    CompiledRenderPipeline precompilePipeline(RenderPipeline renderPipeline, ShaderSource shaderSource);

    void clearPipelineCache();

    List<String> getEnabledExtensions();

    int getMaxSupportedAnisotropy();

    void close();

    default CompiledRenderPipeline precompilePipeline(RenderPipeline $$0) {
        return precompilePipeline($$0, null);
    }
}
