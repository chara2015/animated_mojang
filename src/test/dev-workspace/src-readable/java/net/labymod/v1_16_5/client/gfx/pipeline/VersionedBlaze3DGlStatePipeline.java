package net.labymod.v1_16_5.client.gfx.pipeline;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.function.Functional;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_16_5.client.render.matrix.OpenGLStackProvider;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/VersionedBlaze3DGlStatePipeline.class */
@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline {
    private ByteBuffer lightBuffer;
    private static final g DIFFUSE_LIGHT_0 = (g) Functional.of(new g(0.0f, 0.0f, 0.2f), (v0) -> {
        v0.d();
    });
    private static final g DIFFUSE_LIGHT_1 = (g) Functional.of(new g(0.2f, 0.0f, 0.0f), (v0) -> {
        v0.d();
    });

    @Inject
    public VersionedBlaze3DGlStatePipeline() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public Stack getModelViewStack() {
        return OpenGLStackProvider.DEFAULT_STACK;
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void bindTexture(DeviceTextureView textureView) {
        int slot = dem.p;
        ShaderTextures.setShaderTexture(slot, textureView);
        GlResource deviceTexture = (GlResource) CastUtil.requireInstanceOf(textureView.texture(), GlResource.class);
        int id = deviceTexture.getId();
        dem.s(id);
    }

    @Override // net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline
    public void setupFlatLighting(Runnable runnable) {
        float[] light0PositionBuffer = getLightData(16384, GlConst.GL_POSITION);
        float[] light0DiffuseBuffer = getLightData(16384, GlConst.GL_DIFFUSE);
        float[] light0AmbientBuffer = getLightData(16384, GlConst.GL_AMBIENT);
        float[] light0SpecularBuffer = getLightData(16384, GlConst.GL_SPECULAR);
        float[] light1PositionBuffer = getLightData(GlConst.GL_LIGHT1, GlConst.GL_POSITION);
        float[] light1DiffuseBuffer = getLightData(GlConst.GL_LIGHT1, GlConst.GL_DIFFUSE);
        float[] light1AmbientBuffer = getLightData(GlConst.GL_LIGHT1, GlConst.GL_AMBIENT);
        float[] light1SpecularBuffer = getLightData(GlConst.GL_LIGHT1, GlConst.GL_SPECULAR);
        dem.Q();
        dem.P();
        dem.a(0);
        dem.a(1);
        dem.a(16384, GlConst.GL_POSITION, getBuffer(DIFFUSE_LIGHT_0.a(), DIFFUSE_LIGHT_0.b(), DIFFUSE_LIGHT_0.c(), 0.0f));
        dem.a(16384, GlConst.GL_DIFFUSE, getBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        dem.a(16384, GlConst.GL_AMBIENT, getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.a(16384, GlConst.GL_SPECULAR, getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_POSITION, getBuffer(DIFFUSE_LIGHT_1.a(), DIFFUSE_LIGHT_1.b(), DIFFUSE_LIGHT_1.c(), 0.0f));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_DIFFUSE, getBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_AMBIENT, getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_SPECULAR, getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.t(GlConst.GL_FLAT);
        dem.a(GlConst.GL_LIGHT_MODEL_AMBIENT, getBuffer(0.4f, 0.4f, 0.4f, 1.0f));
        dem.R();
        runnable.run();
        dem.Q();
        dem.P();
        dem.a(0);
        dem.a(1);
        dem.a(16384, GlConst.GL_POSITION, getBuffer(light0PositionBuffer[0], light0PositionBuffer[1], light0PositionBuffer[2], light0PositionBuffer[3]));
        dem.a(16384, GlConst.GL_DIFFUSE, getBuffer(light0DiffuseBuffer[0], light0DiffuseBuffer[1], light0DiffuseBuffer[2], light0DiffuseBuffer[3]));
        dem.a(16384, GlConst.GL_AMBIENT, getBuffer(light0AmbientBuffer[0], light0AmbientBuffer[1], light0AmbientBuffer[2], light0AmbientBuffer[3]));
        dem.a(16384, GlConst.GL_SPECULAR, getBuffer(light0SpecularBuffer[0], light0SpecularBuffer[1], light0SpecularBuffer[2], light0SpecularBuffer[3]));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_POSITION, getBuffer(light1PositionBuffer[0], light1PositionBuffer[1], light1PositionBuffer[2], light1PositionBuffer[3]));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_DIFFUSE, getBuffer(light1DiffuseBuffer[0], light1DiffuseBuffer[1], light1DiffuseBuffer[2], light1DiffuseBuffer[3]));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_AMBIENT, getBuffer(light1AmbientBuffer[0], light1AmbientBuffer[1], light1AmbientBuffer[2], light1AmbientBuffer[3]));
        dem.a(GlConst.GL_LIGHT1, GlConst.GL_SPECULAR, getBuffer(light1SpecularBuffer[0], light1SpecularBuffer[1], light1SpecularBuffer[2], light1SpecularBuffer[3]));
        dem.t(GlConst.GL_FLAT);
        dem.a(GlConst.GL_LIGHT_MODEL_AMBIENT, getBuffer(0.4f, 0.4f, 0.4f, 1.0f));
        dem.R();
    }

    private FloatBuffer getBuffer(float x, float y, float z, float scale) {
        ByteBuffer lightBuffer = allocateLightBuffer();
        lightBuffer.putFloat(0, x);
        lightBuffer.putFloat(4, y);
        lightBuffer.putFloat(8, z);
        lightBuffer.putFloat(12, scale);
        return lightBuffer.asFloatBuffer();
    }

    private float[] getLightData(int light, int pname) {
        float[] buffer = new float[4];
        GL11.glGetLightfv(light, pname, buffer);
        return buffer;
    }

    private ByteBuffer allocateLightBuffer() {
        if (this.lightBuffer == null) {
            this.lightBuffer = MemoryUtil.memAlloc(16);
        }
        return this.lightBuffer;
    }
}
