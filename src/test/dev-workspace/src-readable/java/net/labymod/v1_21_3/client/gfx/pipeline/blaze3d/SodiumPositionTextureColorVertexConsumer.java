package net.labymod.v1_21_3.client.gfx.pipeline.blaze3d;

import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gfx/pipeline/blaze3d/SodiumPositionTextureColorVertexConsumer.class */
public class SodiumPositionTextureColorVertexConsumer extends Blaze3DVertexProvider implements fgw, VertexBufferWriter {
    private Blaze3DVertex current;

    private SodiumPositionTextureColorVertexConsumer() {
    }

    public static SodiumPositionTextureColorVertexConsumer create() {
        return new SodiumPositionTextureColorVertexConsumer();
    }

    public fgw a(float x, float y, float z) {
        this.current = new Blaze3DVertex(x, y, z);
        return this;
    }

    public fgw a(int red, int green, int blue, int alpha) {
        this.current.setArgb(red, green, blue, alpha);
        return this;
    }

    public fgw a(float u, float v) {
        this.current.setUv(u, v);
        return this;
    }

    public fgw a(int var1, int var2) {
        return null;
    }

    public fgw b(int var1, int var2) {
        return null;
    }

    public fgw b(float var1, float var2, float var3) {
        return null;
    }

    public void push(MemoryStack memoryStack, long ptr, int count, fgx description) {
        for (int index = 0; index < count; index++) {
            long vertexPtr = ptr + (((long) index) * ((long) description.b()));
            float x = MemoryUtil.memGetFloat(vertexPtr);
            float y = MemoryUtil.memGetFloat(vertexPtr + 4);
            float z = MemoryUtil.memGetFloat(vertexPtr + 8);
            int argb = ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, MemoryUtil.memGetInt(vertexPtr + 12));
            float u = MemoryUtil.memGetFloat(vertexPtr + 16);
            float v = MemoryUtil.memGetFloat(vertexPtr + 20);
            Blaze3DVertex vertex = new Blaze3DVertex(x, y, z);
            vertex.setUv(u, v);
            vertex.setArgb(argb);
            addVertex(vertex);
        }
    }
}
