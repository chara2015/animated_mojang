package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.api.Laby;
import net.labymod.laby3d.api.opengl.GlFunctions;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import org.lwjgl.opengl.GL20;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/ShaderFixer.class */
public final class ShaderFixer {
    public static void glShaderSource(int shader, ByteBuffer buffer) {
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        glShaderSource(shader, new String(data));
    }

    public static void glShaderSource(int shader, CharSequence source) {
        functions().shaderSource(shader, source);
    }

    public static void glUniform1fv(int location, FloatBuffer buffer) {
        GL20.glUniform1fv(location, buffer);
    }

    public static void glUniform1iv(int location, IntBuffer buffer) {
        GL20.glUniform1iv(location, buffer);
    }

    public static void glUniform2fv(int location, FloatBuffer buffer) {
        GL20.glUniform2fv(location, buffer);
    }

    public static void glUniform2iv(int location, IntBuffer buffer) {
        GL20.glUniform2iv(location, buffer);
    }

    public static void glUniform3fv(int location, FloatBuffer buffer) {
        GL20.glUniform3fv(location, buffer);
    }

    public static void glUniform3iv(int location, IntBuffer buffer) {
        GL20.glUniform3iv(location, buffer);
    }

    public static void glUniform4fv(int location, FloatBuffer buffer) {
        GL20.glUniform4fv(location, buffer);
    }

    public static void glUniform4iv(int location, IntBuffer buffer) {
        GL20.glUniform4iv(location, buffer);
    }

    public static void glUniformMatrix2fv(int location, boolean transpose, FloatBuffer buffer) {
        GL20.glUniformMatrix2fv(location, transpose, buffer);
    }

    public static void glUniformMatrix3fv(int location, boolean transpose, FloatBuffer buffer) {
        GL20.glUniformMatrix3fv(location, transpose, buffer);
    }

    public static void glUniformMatrix4fv(int location, boolean transpose, FloatBuffer buffer) {
        GL20.glUniformMatrix4fv(location, transpose, buffer);
    }

    private static GlFunctions functions() {
        GlRenderDevice glRenderDeviceRenderDevice = Laby.references().laby3D().renderDevice();
        if (!(glRenderDeviceRenderDevice instanceof GlRenderDevice)) {
            throw new IllegalStateException("The render device is not an instance of GlRenderDevice");
        }
        GlRenderDevice glRenderDevice = glRenderDeviceRenderDevice;
        return glRenderDevice.functions();
    }
}
