package net.labymod.v1_20_1.client.gfx.pipeline.blaze3d;

import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
import net.caffeinemc.mods.sodium.api.vertex.format.VertexFormatDescription;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gfx/pipeline/blaze3d/SodiumPositionTextureColorVertexConsumer.class */
public class SodiumPositionTextureColorVertexConsumer extends Blaze3DVertexProvider implements ein, VertexBufferWriter {
    private Blaze3DVertex current;

    private SodiumPositionTextureColorVertexConsumer() {
    }

    public static SodiumPositionTextureColorVertexConsumer create() {
        return new SodiumPositionTextureColorVertexConsumer();
    }

    public ein a(double x, double y, double z) {
        this.current = new Blaze3DVertex((float) x, (float) y, (float) z);
        return this;
    }

    public ein a(int red, int green, int blue, int alpha) {
        this.current.setArgb(red, green, blue, alpha);
        return this;
    }

    public ein a(float u, float v) {
        this.current.setUv(u, v);
        return this;
    }

    public ein a(int i, int i1) {
        return this;
    }

    public ein b(int var1, int var2) {
        return this;
    }

    public ein a(float var1, float var2, float var3) {
        return this;
    }

    public void e() {
        addVertex(this.current);
    }

    public void b(int i, int i1, int i2, int i3) {
    }

    public void k() {
    }

    public void push(MemoryStack memoryStack, long ptr, int count, VertexFormatDescription description) {
        for (int index = 0; index < count; index++) {
            long vertexPtr = ptr + (((long) index) * ((long) description.stride()));
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
