package com.mojang.blaze3d.opengl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.opengl.Uniform;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.client.renderer.ShaderManager;
import org.lwjgl.opengl.GL31;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlProgram.class */
public class GlProgram implements AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Set<String> BUILT_IN_UNIFORMS = Sets.newHashSet(new String[]{"Projection", "Lighting", "Fog", "Globals"});
    public static GlProgram INVALID_PROGRAM = new GlProgram(-1, "invalid");
    private final Map<String, Uniform> uniformsByName = new HashMap();
    private final int programId;
    private final String debugLabel;

    private GlProgram(int $$0, String $$1) {
        this.programId = $$0;
        this.debugLabel = $$1;
    }

    public static GlProgram link(GlShaderModule $$0, GlShaderModule $$1, VertexFormat $$2, String $$3) throws ShaderManager.CompilationException {
        int $$4 = GlStateManager.glCreateProgram();
        if ($$4 <= 0) {
            throw new ShaderManager.CompilationException("Could not create shader program (returned program ID " + $$4 + ")");
        }
        int $$5 = 0;
        for (String $$6 : $$2.getElementAttributeNames()) {
            GlStateManager._glBindAttribLocation($$4, $$5, $$6);
            $$5++;
        }
        GlStateManager.glAttachShader($$4, $$0.getShaderId());
        GlStateManager.glAttachShader($$4, $$1.getShaderId());
        GlStateManager.glLinkProgram($$4);
        int $$7 = GlStateManager.glGetProgrami($$4, GlConst.GL_LINK_STATUS);
        String $$8 = GlStateManager.glGetProgramInfoLog($$4, ShaderManager.MAX_LOG_LENGTH);
        if ($$7 == 0 || $$8.contains("Failed for unknown reason")) {
            throw new ShaderManager.CompilationException("Error encountered when linking program containing VS " + String.valueOf($$0.getId()) + " and FS " + String.valueOf($$1.getId()) + ". Log output: " + $$8);
        }
        if (!$$8.isEmpty()) {
            LOGGER.info("Info log when linking program containing VS {} and FS {}. Log output: {}", new Object[]{$$0.getId(), $$1.getId(), $$8});
        }
        return new GlProgram($$4, $$3);
    }

    public void setupUniforms(List<RenderPipeline.UniformDescription> $$0, List<String> $$1) {
        Uniform utb;
        int $$2 = 0;
        int $$3 = 0;
        for (RenderPipeline.UniformDescription $$4 : $$0) {
            String $$5 = $$4.name();
            switch ($$4.type()) {
                case UNIFORM_BUFFER:
                    int $$6 = GL31.glGetUniformBlockIndex(this.programId, $$5);
                    if ($$6 == -1) {
                        utb = null;
                    } else {
                        int $$7 = $$2;
                        $$2++;
                        GL31.glUniformBlockBinding(this.programId, $$6, $$7);
                        utb = new Uniform.Ubo($$7);
                    }
                    break;
                case TEXEL_BUFFER:
                    int $$8 = GlStateManager._glGetUniformLocation(this.programId, $$5);
                    if ($$8 == -1) {
                        LOGGER.warn("{} shader program does not use utb {} defined in the pipeline. This might be a bug.", this.debugLabel, $$5);
                        utb = null;
                    } else {
                        int $$9 = $$3;
                        $$3++;
                        utb = new Uniform.Utb($$8, $$9, (TextureFormat) Objects.requireNonNull($$4.textureFormat()));
                    }
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            Uniform $$10 = utb;
            if ($$10 != null) {
                this.uniformsByName.put($$5, $$10);
            }
        }
        for (String $$11 : $$1) {
            int $$12 = GlStateManager._glGetUniformLocation(this.programId, $$11);
            if ($$12 == -1) {
                LOGGER.warn("{} shader program does not use sampler {} defined in the pipeline. This might be a bug.", this.debugLabel, $$11);
            } else {
                int $$13 = $$3;
                $$3++;
                this.uniformsByName.put($$11, new Uniform.Sampler($$12, $$13));
            }
        }
        int $$14 = GlStateManager.glGetProgrami(this.programId, 35382);
        for (int $$15 = 0; $$15 < $$14; $$15++) {
            String $$16 = GL31.glGetActiveUniformBlockName(this.programId, $$15);
            if (!this.uniformsByName.containsKey($$16)) {
                if (!$$1.contains($$16) && BUILT_IN_UNIFORMS.contains($$16)) {
                    int $$17 = $$2;
                    $$2++;
                    GL31.glUniformBlockBinding(this.programId, $$15, $$17);
                    this.uniformsByName.put($$16, new Uniform.Ubo($$17));
                } else {
                    LOGGER.warn("Found unknown and unsupported uniform {} in {}", $$16, this.debugLabel);
                }
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.uniformsByName.values().forEach((v0) -> {
            v0.close();
        });
        GlStateManager.glDeleteProgram(this.programId);
    }

    public Uniform getUniform(String $$0) {
        RenderSystem.assertOnRenderThread();
        return this.uniformsByName.get($$0);
    }

    @VisibleForTesting
    public int getProgramId() {
        return this.programId;
    }

    public String toString() {
        return this.debugLabel;
    }

    public String getDebugLabel() {
        return this.debugLabel;
    }

    public Map<String, Uniform> getUniforms() {
        return this.uniformsByName;
    }
}
