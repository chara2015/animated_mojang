package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.GpuOutOfMemoryException;
import com.mojang.blaze3d.GraphicsWorkarounds;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.shaders.ShaderType;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlDevice.class */
public class GlDevice implements GpuDevice {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected static boolean USE_GL_ARB_vertex_attrib_binding = true;
    protected static boolean USE_GL_KHR_debug = true;
    protected static boolean USE_GL_EXT_debug_label = true;
    protected static boolean USE_GL_ARB_debug_output = true;
    protected static boolean USE_GL_ARB_direct_state_access = true;
    protected static boolean USE_GL_ARB_buffer_storage = true;
    private final CommandEncoder encoder;
    private final GlDebug debugLog;
    private final GlDebugLabel debugLabels;
    private final int maxSupportedTextureSize;
    private final DirectStateAccess directStateAccess;
    private final ShaderSource defaultShaderSource;
    private final VertexArrayCache vertexArrayCache;
    private final BufferStorage bufferStorage;
    private final int uniformOffsetAlignment;
    private final int maxSupportedAnisotropy;
    private final Map<RenderPipeline, GlRenderPipeline> pipelineCache = new IdentityHashMap();
    private final Map<ShaderCompilationKey, GlShaderModule> shaderCache = new HashMap();
    private final Set<String> enabledExtensions = new HashSet();

    public GlDevice(long $$0, int $$1, boolean $$2, ShaderSource $$3, boolean $$4) {
        GLFW.glfwMakeContextCurrent($$0);
        GLCapabilities $$5 = GL.createCapabilities();
        int $$6 = getMaxSupportedTextureSize();
        GLFW.glfwSetWindowSizeLimits($$0, -1, -1, $$6, $$6);
        GraphicsWorkarounds $$7 = GraphicsWorkarounds.get(this);
        this.debugLog = GlDebug.enableDebugCallback($$1, $$2, this.enabledExtensions);
        this.debugLabels = GlDebugLabel.create($$5, $$4, this.enabledExtensions);
        this.vertexArrayCache = VertexArrayCache.create($$5, this.debugLabels, this.enabledExtensions);
        this.bufferStorage = BufferStorage.create($$5, this.enabledExtensions);
        this.directStateAccess = DirectStateAccess.create($$5, this.enabledExtensions, $$7);
        this.maxSupportedTextureSize = $$6;
        this.defaultShaderSource = $$3;
        this.encoder = new GlCommandEncoder(this);
        this.uniformOffsetAlignment = GL11.glGetInteger(35380);
        GL11.glEnable(34895);
        GL11.glEnable(34370);
        if ($$5.GL_EXT_texture_filter_anisotropic) {
            this.maxSupportedAnisotropy = Mth.floor(GL11.glGetFloat(34047));
            this.enabledExtensions.add("GL_EXT_texture_filter_anisotropic");
        } else {
            this.maxSupportedAnisotropy = 1;
        }
    }

    public GlDebugLabel debugLabels() {
        return this.debugLabels;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public CommandEncoder createCommandEncoder() {
        return this.encoder;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public int getMaxSupportedAnisotropy() {
        return this.maxSupportedAnisotropy;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuSampler createSampler(AddressMode $$0, AddressMode $$1, FilterMode $$2, FilterMode $$3, int $$4, OptionalDouble $$5) {
        if ($$4 < 1 || $$4 > this.maxSupportedAnisotropy) {
            throw new IllegalArgumentException("maxAnisotropy out of range; must be >= 1 and <= " + getMaxSupportedAnisotropy() + ", but was " + $$4);
        }
        return new GlSampler($$0, $$1, $$2, $$3, $$4, $$5);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuTexture createTexture(Supplier<String> $$0, @GpuTexture.Usage int $$1, TextureFormat $$2, int $$3, int $$4, int $$5, int $$6) {
        return createTexture((!this.debugLabels.exists() || $$0 == null) ? null : $$0.get(), $$1, $$2, $$3, $$4, $$5, $$6);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuTexture createTexture(String $$0, @GpuTexture.Usage int $$1, TextureFormat $$2, int $$3, int $$4, int $$5, int $$6) {
        int $$10;
        if ($$6 < 1) {
            throw new IllegalArgumentException("mipLevels must be at least 1");
        }
        if ($$5 < 1) {
            throw new IllegalArgumentException("depthOrLayers must be at least 1");
        }
        boolean $$7 = ($$1 & 16) != 0;
        if ($$7) {
            if ($$3 != $$4) {
                throw new IllegalArgumentException("Cubemap compatible textures must be square, but size is " + $$3 + "x" + $$4);
            }
            if ($$5 % 6 != 0) {
                throw new IllegalArgumentException("Cubemap compatible textures must have a layer count with a multiple of 6, was " + $$5);
            }
            if ($$5 > 6) {
                throw new UnsupportedOperationException("Array textures are not yet supported");
            }
        } else if ($$5 > 1) {
            throw new UnsupportedOperationException("Array or 3D textures are not yet supported");
        }
        GlStateManager.clearGlErrors();
        int $$8 = GlStateManager._genTexture();
        if ($$0 == null) {
            $$0 = String.valueOf($$8);
        }
        if ($$7) {
            GL11.glBindTexture(34067, $$8);
            $$10 = 34067;
        } else {
            GlStateManager._bindTexture($$8);
            $$10 = 3553;
        }
        GlStateManager._texParameter($$10, 33085, $$6 - 1);
        GlStateManager._texParameter($$10, 33082, 0);
        GlStateManager._texParameter($$10, 33083, $$6 - 1);
        if ($$2.hasDepthAspect()) {
            GlStateManager._texParameter($$10, GlConst.GL_TEXTURE_COMPARE_MODE, 0);
        }
        if ($$7) {
            for (int $$11 : GlConst.CUBEMAP_TARGETS) {
                for (int $$12 = 0; $$12 < $$6; $$12++) {
                    GlStateManager._texImage2D($$11, $$12, GlConst.toGlInternalId($$2), $$3 >> $$12, $$4 >> $$12, 0, GlConst.toGlExternalId($$2), GlConst.toGlType($$2), null);
                }
            }
        } else {
            for (int $$13 = 0; $$13 < $$6; $$13++) {
                GlStateManager._texImage2D($$10, $$13, GlConst.toGlInternalId($$2), $$3 >> $$13, $$4 >> $$13, 0, GlConst.toGlExternalId($$2), GlConst.toGlType($$2), null);
            }
        }
        int $$14 = GlStateManager._getError();
        if ($$14 == 1285) {
            throw new GpuOutOfMemoryException("Could not allocate texture of " + $$3 + "x" + $$4 + " for " + $$0);
        }
        if ($$14 != 0) {
            throw new IllegalStateException("OpenGL error " + $$14);
        }
        GlTexture $$15 = new GlTexture($$1, $$0, $$2, $$3, $$4, $$5, $$6, $$8);
        this.debugLabels.applyLabel($$15);
        return $$15;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuTextureView createTextureView(GpuTexture $$0) {
        return createTextureView($$0, 0, $$0.getMipLevels());
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuTextureView createTextureView(GpuTexture $$0, int $$1, int $$2) {
        if ($$0.isClosed()) {
            throw new IllegalArgumentException("Can't create texture view with closed texture");
        }
        if ($$1 < 0 || $$1 + $$2 > $$0.getMipLevels()) {
            throw new IllegalArgumentException($$2 + " mip levels starting from " + $$1 + " would be out of range for texture with only " + $$0.getMipLevels() + " mip levels");
        }
        return new GlTextureView((GlTexture) $$0, $$1, $$2);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuBuffer createBuffer(Supplier<String> $$0, @GpuBuffer.Usage int $$1, long $$2) {
        if ($$2 <= 0) {
            throw new IllegalArgumentException("Buffer size must be greater than zero");
        }
        GlStateManager.clearGlErrors();
        GlBuffer $$3 = this.bufferStorage.createBuffer(this.directStateAccess, $$0, $$1, $$2);
        int $$4 = GlStateManager._getError();
        if ($$4 == 1285) {
            String.valueOf($$0);
            GpuOutOfMemoryException gpuOutOfMemoryException = new GpuOutOfMemoryException("Could not allocate buffer of " + $$2 + " for " + gpuOutOfMemoryException);
            throw gpuOutOfMemoryException;
        }
        if ($$4 != 0) {
            throw new IllegalStateException("OpenGL error " + $$4);
        }
        this.debugLabels.applyLabel($$3);
        return $$3;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GpuBuffer createBuffer(Supplier<String> $$0, @GpuBuffer.Usage int $$1, ByteBuffer $$2) {
        if (!$$2.hasRemaining()) {
            throw new IllegalArgumentException("Buffer source must not be empty");
        }
        GlStateManager.clearGlErrors();
        long $$3 = $$2.remaining();
        GlBuffer $$4 = this.bufferStorage.createBuffer(this.directStateAccess, $$0, $$1, $$2);
        int $$5 = GlStateManager._getError();
        if ($$5 == 1285) {
            String.valueOf($$0);
            GpuOutOfMemoryException gpuOutOfMemoryException = new GpuOutOfMemoryException("Could not allocate buffer of " + $$3 + " for " + gpuOutOfMemoryException);
            throw gpuOutOfMemoryException;
        }
        if ($$5 != 0) {
            throw new IllegalStateException("OpenGL error " + $$5);
        }
        this.debugLabels.applyLabel($$4);
        return $$4;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public String getImplementationInformation() {
        if (GLFW.glfwGetCurrentContext() == 0) {
            return "NO CONTEXT";
        }
        return GlStateManager._getString(7937) + " GL version " + GlStateManager._getString(7938) + ", " + GlStateManager._getString(7936);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public List<String> getLastDebugMessages() {
        return this.debugLog == null ? Collections.emptyList() : this.debugLog.getLastOpenGlDebugMessages();
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public boolean isDebuggingEnabled() {
        return this.debugLog != null;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public String getRenderer() {
        return GlStateManager._getString(7937);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public String getVendor() {
        return GlStateManager._getString(7936);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public String getBackendName() {
        return "OpenGL";
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public String getVersion() {
        return GlStateManager._getString(7938);
    }

    private static int getMaxSupportedTextureSize() {
        int $$0 = GlStateManager._getInteger(GlConst.GL_MAX_TEXTURE_SIZE);
        int iMax = Math.max(ShaderManager.MAX_LOG_LENGTH, $$0);
        while (true) {
            int $$1 = iMax;
            if ($$1 >= 1024) {
                GlStateManager._texImage2D(GlConst.GL_PROXY_TEXTURE_2D, 0, GlConst.GL_RGBA, $$1, $$1, 0, GlConst.GL_RGBA, GlConst.GL_UNSIGNED_BYTE, null);
                int $$2 = GlStateManager._getTexLevelParameter(GlConst.GL_PROXY_TEXTURE_2D, 0, 4096);
                if ($$2 == 0) {
                    iMax = $$1 >> 1;
                } else {
                    return $$1;
                }
            } else {
                int $$3 = Math.max($$0, 1024);
                LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", Integer.valueOf($$3));
                return $$3;
            }
        }
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public int getMaxTextureSize() {
        return this.maxSupportedTextureSize;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public int getUniformOffsetAlignment() {
        return this.uniformOffsetAlignment;
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public void clearPipelineCache() {
        for (GlRenderPipeline $$0 : this.pipelineCache.values()) {
            if ($$0.program() != GlProgram.INVALID_PROGRAM) {
                $$0.program().close();
            }
        }
        this.pipelineCache.clear();
        for (GlShaderModule $$1 : this.shaderCache.values()) {
            if ($$1 != GlShaderModule.INVALID_SHADER) {
                $$1.close();
            }
        }
        this.shaderCache.clear();
        String $$2 = GlStateManager._getString(7937);
        if ($$2.contains("AMD")) {
            sacrificeShaderToOpenGlAndAmd();
        }
    }

    private static void sacrificeShaderToOpenGlAndAmd() {
        int $$0 = GlStateManager.glCreateShader(GlConst.GL_VERTEX_SHADER);
        int $$1 = GlStateManager.glCreateProgram();
        GlStateManager.glAttachShader($$1, $$0);
        GlStateManager.glDeleteShader($$0);
        GlStateManager.glDeleteProgram($$1);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public List<String> getEnabledExtensions() {
        return new ArrayList(this.enabledExtensions);
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public void close() {
        clearPipelineCache();
    }

    public DirectStateAccess directStateAccess() {
        return this.directStateAccess;
    }

    protected GlRenderPipeline getOrCompilePipeline(RenderPipeline $$0) {
        return this.pipelineCache.computeIfAbsent($$0, $$02 -> {
            return compilePipeline($$02, this.defaultShaderSource);
        });
    }

    protected GlShaderModule getOrCompileShader(Identifier $$0, ShaderType $$1, ShaderDefines $$2, ShaderSource $$3) {
        ShaderCompilationKey $$4 = new ShaderCompilationKey($$0, $$1, $$2);
        return this.shaderCache.computeIfAbsent($$4, $$12 -> {
            return compileShader($$12, $$3);
        });
    }

    @Override // com.mojang.blaze3d.systems.GpuDevice
    public GlRenderPipeline precompilePipeline(RenderPipeline $$0, ShaderSource $$1) {
        ShaderSource $$2 = $$1 == null ? this.defaultShaderSource : $$1;
        return this.pipelineCache.computeIfAbsent($$0, $$12 -> {
            return compilePipeline($$12, $$2);
        });
    }

    private GlShaderModule compileShader(ShaderCompilationKey $$0, ShaderSource $$1) {
        String $$2 = $$1.get($$0.id, $$0.type);
        if ($$2 == null) {
            LOGGER.error("Couldn't find source for {} shader ({})", $$0.type, $$0.id);
            return GlShaderModule.INVALID_SHADER;
        }
        String $$3 = GlslPreprocessor.injectDefines($$2, $$0.defines);
        int $$4 = GlStateManager.glCreateShader(GlConst.toGl($$0.type));
        GlStateManager.glShaderSource($$4, $$3);
        GlStateManager.glCompileShader($$4);
        if (GlStateManager.glGetShaderi($$4, GlConst.GL_COMPILE_STATUS) == 0) {
            String $$5 = StringUtils.trim(GlStateManager.glGetShaderInfoLog($$4, ShaderManager.MAX_LOG_LENGTH));
            LOGGER.error("Couldn't compile {} shader ({}): {}", new Object[]{$$0.type.getName(), $$0.id, $$5});
            return GlShaderModule.INVALID_SHADER;
        }
        GlShaderModule $$6 = new GlShaderModule($$4, $$0.id, $$0.type);
        this.debugLabels.applyLabel($$6);
        return $$6;
    }

    private GlProgram compileProgram(RenderPipeline $$0, ShaderSource $$1) {
        GlShaderModule $$2 = getOrCompileShader($$0.getVertexShader(), ShaderType.VERTEX, $$0.getShaderDefines(), $$1);
        GlShaderModule $$3 = getOrCompileShader($$0.getFragmentShader(), ShaderType.FRAGMENT, $$0.getShaderDefines(), $$1);
        if ($$2 == GlShaderModule.INVALID_SHADER) {
            LOGGER.error("Couldn't compile pipeline {}: vertex shader {} was invalid", $$0.getLocation(), $$0.getVertexShader());
            return GlProgram.INVALID_PROGRAM;
        }
        if ($$3 == GlShaderModule.INVALID_SHADER) {
            LOGGER.error("Couldn't compile pipeline {}: fragment shader {} was invalid", $$0.getLocation(), $$0.getFragmentShader());
            return GlProgram.INVALID_PROGRAM;
        }
        try {
            GlProgram $$4 = GlProgram.link($$2, $$3, $$0.getVertexFormat(), $$0.getLocation().toString());
            $$4.setupUniforms($$0.getUniforms(), $$0.getSamplers());
            this.debugLabels.applyLabel($$4);
            return $$4;
        } catch (ShaderManager.CompilationException $$5) {
            LOGGER.error("Couldn't compile program for pipeline {}: {}", $$0.getLocation(), $$5);
            return GlProgram.INVALID_PROGRAM;
        }
    }

    private GlRenderPipeline compilePipeline(RenderPipeline $$0, ShaderSource $$1) {
        return new GlRenderPipeline($$0, compileProgram($$0, $$1));
    }

    public VertexArrayCache vertexArrayCache() {
        return this.vertexArrayCache;
    }

    public BufferStorage getBufferStorage() {
        return this.bufferStorage;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey.class */
    static final class ShaderCompilationKey extends Record {
        private final Identifier id;
        private final ShaderType type;
        private final ShaderDefines defines;

        ShaderCompilationKey(Identifier $$0, ShaderType $$1, ShaderDefines $$2) {
            this.id = $$0;
            this.type = $$1;
            this.defines = $$2;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ShaderCompilationKey.class), ShaderCompilationKey.class, "id;type;defines", "FIELD:Lcom/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lcom/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey;->type:Lcom/mojang/blaze3d/shaders/ShaderType;", "FIELD:Lcom/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey;->defines:Lnet/minecraft/client/renderer/ShaderDefines;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ShaderCompilationKey.class, Object.class), ShaderCompilationKey.class, "id;type;defines", "FIELD:Lcom/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lcom/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey;->type:Lcom/mojang/blaze3d/shaders/ShaderType;", "FIELD:Lcom/mojang/blaze3d/opengl/GlDevice$ShaderCompilationKey;->defines:Lnet/minecraft/client/renderer/ShaderDefines;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }

        public ShaderType type() {
            return this.type;
        }

        public ShaderDefines defines() {
            return this.defines;
        }

        @Override // java.lang.Record
        public String toString() {
            String $$0 = String.valueOf(this.id) + " (" + String.valueOf(this.type) + ")";
            if (!this.defines.isEmpty()) {
                return $$0 + " with " + String.valueOf(this.defines);
            }
            return $$0;
        }
    }
}
