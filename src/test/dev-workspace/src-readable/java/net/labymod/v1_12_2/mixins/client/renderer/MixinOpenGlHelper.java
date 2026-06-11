package net.labymod.v1_12_2.mixins.client.renderer;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.api.models.OperatingSystem;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.ShaderFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinOpenGlHelper.class */
@Mixin({cii.class})
public class MixinOpenGlHelper {
    @Overwrite
    public static void a(int shader, ByteBuffer buffer) {
        ShaderFixer.glShaderSource(shader, buffer);
    }

    @Overwrite
    public static void a(int location, IntBuffer buffer) {
        ShaderFixer.glUniform1iv(location, buffer);
    }

    @Overwrite
    public static void a(int location, FloatBuffer buffer) {
        ShaderFixer.glUniform1fv(location, buffer);
    }

    @Overwrite
    public static void b(int location, IntBuffer buffer) {
        ShaderFixer.glUniform2iv(location, buffer);
    }

    @Overwrite
    public static void b(int location, FloatBuffer buffer) {
        ShaderFixer.glUniform2fv(location, buffer);
    }

    @Overwrite
    public static void c(int location, IntBuffer buffer) {
        ShaderFixer.glUniform3iv(location, buffer);
    }

    @Overwrite
    public static void c(int location, FloatBuffer buffer) {
        ShaderFixer.glUniform3fv(location, buffer);
    }

    @Overwrite
    public static void d(int location, IntBuffer buffer) {
        ShaderFixer.glUniform4iv(location, buffer);
    }

    @Overwrite
    public static void d(int location, FloatBuffer buffer) {
        ShaderFixer.glUniform4fv(location, buffer);
    }

    @Overwrite
    public static void a(int location, boolean transpose, FloatBuffer buffer) {
        ShaderFixer.glUniformMatrix2fv(location, transpose, buffer);
    }

    @Overwrite
    public static void b(int location, boolean transpose, FloatBuffer buffer) {
        ShaderFixer.glUniformMatrix3fv(location, transpose, buffer);
    }

    @Overwrite
    public static void c(int location, boolean transpose, FloatBuffer buffer) {
        ShaderFixer.glUniformMatrix4fv(location, transpose, buffer);
    }

    @Overwrite
    public static void a(File file) {
        OperatingSystem.getPlatform().openFile(file);
    }
}
