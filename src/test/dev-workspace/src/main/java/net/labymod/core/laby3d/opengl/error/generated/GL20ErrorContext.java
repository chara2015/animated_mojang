package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL20;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL20ErrorContext.class */
public final class GL20ErrorContext {
    public static int glCreateProgram() {
        int returnType = GL20.glCreateProgram();
        LabyDebugContext.glError("glCreateProgram", new Object[0]);
        return returnType;
    }

    public static void glDeleteProgram(int p0) {
        GL20.glDeleteProgram(p0);
        LabyDebugContext.glError("glDeleteProgram", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsProgram(int p0) {
        boolean returnType = GL20.glIsProgram(p0);
        LabyDebugContext.glError("glIsProgram", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static int glCreateShader(int p0) {
        int returnType = GL20.glCreateShader(p0);
        LabyDebugContext.glError("glCreateShader", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glDeleteShader(int p0) {
        GL20.glDeleteShader(p0);
        LabyDebugContext.glError("glDeleteShader", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsShader(int p0) {
        boolean returnType = GL20.glIsShader(p0);
        LabyDebugContext.glError("glIsShader", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glAttachShader(int p0, int p1) {
        GL20.glAttachShader(p0, p1);
        LabyDebugContext.glError("glAttachShader", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDetachShader(int p0, int p1) {
        GL20.glDetachShader(p0, p1);
        LabyDebugContext.glError("glDetachShader", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglShaderSource(int p0, int p1, long p2, long p3) {
        GL20.nglShaderSource(p0, p1, p2, p3);
        LabyDebugContext.glError("nglShaderSource", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glShaderSource(int p0, PointerBuffer p1, IntBuffer p2) {
        GL20.glShaderSource(p0, p1, p2);
        LabyDebugContext.glError("glShaderSource", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glShaderSource(int p0, CharSequence[] p1) {
        GL20.glShaderSource(p0, p1);
        LabyDebugContext.glError("glShaderSource", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glShaderSource(int p0, CharSequence p1) {
        GL20.glShaderSource(p0, p1);
        LabyDebugContext.glError("glShaderSource", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glCompileShader(int p0) {
        GL20.glCompileShader(p0);
        LabyDebugContext.glError("glCompileShader", "p0", Integer.valueOf(p0));
    }

    public static void glLinkProgram(int p0) {
        GL20.glLinkProgram(p0);
        LabyDebugContext.glError("glLinkProgram", "p0", Integer.valueOf(p0));
    }

    public static void glUseProgram(int p0) {
        GL20.glUseProgram(p0);
        LabyDebugContext.glError("glUseProgram", "p0", Integer.valueOf(p0));
    }

    public static void glValidateProgram(int p0) {
        GL20.glValidateProgram(p0);
        LabyDebugContext.glError("glValidateProgram", "p0", Integer.valueOf(p0));
    }

    public static void glUniform1f(int p0, float p1) {
        GL20.glUniform1f(p0, p1);
        LabyDebugContext.glError("glUniform1f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glUniform2f(int p0, float p1, float p2) {
        GL20.glUniform2f(p0, p1, p2);
        LabyDebugContext.glError("glUniform2f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glUniform3f(int p0, float p1, float p2, float p3) {
        GL20.glUniform3f(p0, p1, p2, p3);
        LabyDebugContext.glError("glUniform3f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glUniform4f(int p0, float p1, float p2, float p3, float p4) {
        GL20.glUniform4f(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glUniform4f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glUniform1i(int p0, int p1) {
        GL20.glUniform1i(p0, p1);
        LabyDebugContext.glError("glUniform1i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glUniform2i(int p0, int p1, int p2) {
        GL20.glUniform2i(p0, p1, p2);
        LabyDebugContext.glError("glUniform2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glUniform3i(int p0, int p1, int p2, int p3) {
        GL20.glUniform3i(p0, p1, p2, p3);
        LabyDebugContext.glError("glUniform3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glUniform4i(int p0, int p1, int p2, int p3, int p4) {
        GL20.glUniform4i(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glUniform4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglUniform1fv(int p0, int p1, long p2) {
        GL20.nglUniform1fv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform1fv(int p0, FloatBuffer p1) {
        GL20.glUniform1fv(p0, p1);
        LabyDebugContext.glError("glUniform1fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform2fv(int p0, int p1, long p2) {
        GL20.nglUniform2fv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform2fv(int p0, FloatBuffer p1) {
        GL20.glUniform2fv(p0, p1);
        LabyDebugContext.glError("glUniform2fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform3fv(int p0, int p1, long p2) {
        GL20.nglUniform3fv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform3fv(int p0, FloatBuffer p1) {
        GL20.glUniform3fv(p0, p1);
        LabyDebugContext.glError("glUniform3fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform4fv(int p0, int p1, long p2) {
        GL20.nglUniform4fv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform4fv(int p0, FloatBuffer p1) {
        GL20.glUniform4fv(p0, p1);
        LabyDebugContext.glError("glUniform4fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform1iv(int p0, int p1, long p2) {
        GL20.nglUniform1iv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform1iv(int p0, IntBuffer p1) {
        GL20.glUniform1iv(p0, p1);
        LabyDebugContext.glError("glUniform1iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform2iv(int p0, int p1, long p2) {
        GL20.nglUniform2iv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform2iv(int p0, IntBuffer p1) {
        GL20.glUniform2iv(p0, p1);
        LabyDebugContext.glError("glUniform2iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform3iv(int p0, int p1, long p2) {
        GL20.nglUniform3iv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform3iv(int p0, IntBuffer p1) {
        GL20.glUniform3iv(p0, p1);
        LabyDebugContext.glError("glUniform3iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform4iv(int p0, int p1, long p2) {
        GL20.nglUniform4iv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform4iv(int p0, IntBuffer p1) {
        GL20.glUniform4iv(p0, p1);
        LabyDebugContext.glError("glUniform4iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniformMatrix2fv(int p0, int p1, boolean p2, long p3) {
        GL20.nglUniformMatrix2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix2fv(int p0, boolean p1, FloatBuffer p2) {
        GL20.glUniformMatrix2fv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix3fv(int p0, int p1, boolean p2, long p3) {
        GL20.nglUniformMatrix3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix3fv(int p0, boolean p1, FloatBuffer p2) {
        GL20.glUniformMatrix3fv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix4fv(int p0, int p1, boolean p2, long p3) {
        GL20.nglUniformMatrix4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix4fv(int p0, boolean p1, FloatBuffer p2) {
        GL20.glUniformMatrix4fv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglGetShaderiv(int p0, int p1, long p2) {
        GL20.nglGetShaderiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetShaderiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetShaderiv(int p0, int p1, IntBuffer p2) {
        GL20.glGetShaderiv(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetShaderi(int p0, int p1) {
        int returnType = GL20.glGetShaderi(p0, p1);
        LabyDebugContext.glError("glGetShaderi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetProgramiv(int p0, int p1, long p2) {
        GL20.nglGetProgramiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramiv(int p0, int p1, IntBuffer p2) {
        GL20.glGetProgramiv(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetProgrami(int p0, int p1) {
        int returnType = GL20.glGetProgrami(p0, p1);
        LabyDebugContext.glError("glGetProgrami", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetShaderInfoLog(int p0, int p1, long p2, long p3) {
        GL20.nglGetShaderInfoLog(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetShaderInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetShaderInfoLog(int p0, IntBuffer p1, ByteBuffer p2) {
        GL20.glGetShaderInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetShaderInfoLog(int p0, int p1) {
        String returnType = GL20.glGetShaderInfoLog(p0, p1);
        LabyDebugContext.glError("glGetShaderInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetShaderInfoLog(int p0) {
        String returnType = GL20.glGetShaderInfoLog(p0);
        LabyDebugContext.glError("glGetShaderInfoLog", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetProgramInfoLog(int p0, int p1, long p2, long p3) {
        GL20.nglGetProgramInfoLog(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetProgramInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetProgramInfoLog(int p0, IntBuffer p1, ByteBuffer p2) {
        GL20.glGetProgramInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetProgramInfoLog(int p0, int p1) {
        String returnType = GL20.glGetProgramInfoLog(p0, p1);
        LabyDebugContext.glError("glGetProgramInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetProgramInfoLog(int p0) {
        String returnType = GL20.glGetProgramInfoLog(p0);
        LabyDebugContext.glError("glGetProgramInfoLog", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetAttachedShaders(int p0, int p1, long p2, long p3) {
        GL20.nglGetAttachedShaders(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetAttachedShaders", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetAttachedShaders(int p0, IntBuffer p1, IntBuffer p2) {
        GL20.glGetAttachedShaders(p0, p1, p2);
        LabyDebugContext.glError("glGetAttachedShaders", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static int nglGetUniformLocation(int p0, long p1) {
        int returnType = GL20.nglGetUniformLocation(p0, p1);
        LabyDebugContext.glError("nglGetUniformLocation", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetUniformLocation(int p0, ByteBuffer p1) {
        int returnType = GL20.glGetUniformLocation(p0, p1);
        LabyDebugContext.glError("glGetUniformLocation", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetUniformLocation(int p0, CharSequence p1) {
        int returnType = GL20.glGetUniformLocation(p0, p1);
        LabyDebugContext.glError("glGetUniformLocation", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveUniform(int p0, int p1, int p2, long p3, long p4, long p5, long p6) {
        GL20.nglGetActiveUniform(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetActiveUniform", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetActiveUniform(int p0, int p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        GL20.glGetActiveUniform(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveUniform", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static String glGetActiveUniform(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        String returnType = GL20.glGetActiveUniform(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveUniform", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static String glGetActiveUniform(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        String returnType = GL20.glGetActiveUniform(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniform", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static void nglGetUniformfv(int p0, int p1, long p2) {
        GL20.nglGetUniformfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformfv(int p0, int p1, FloatBuffer p2) {
        GL20.glGetUniformfv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetUniformf(int p0, int p1) {
        float returnType = GL20.glGetUniformf(p0, p1);
        LabyDebugContext.glError("glGetUniformf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetUniformiv(int p0, int p1, long p2) {
        GL20.nglGetUniformiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformiv(int p0, int p1, IntBuffer p2) {
        GL20.glGetUniformiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetUniformi(int p0, int p1) {
        int returnType = GL20.glGetUniformi(p0, p1);
        LabyDebugContext.glError("glGetUniformi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetShaderSource(int p0, int p1, long p2, long p3) {
        GL20.nglGetShaderSource(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetShaderSource", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetShaderSource(int p0, IntBuffer p1, ByteBuffer p2) {
        GL20.glGetShaderSource(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderSource", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetShaderSource(int p0, int p1) {
        String returnType = GL20.glGetShaderSource(p0, p1);
        LabyDebugContext.glError("glGetShaderSource", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetShaderSource(int p0) {
        String returnType = GL20.glGetShaderSource(p0);
        LabyDebugContext.glError("glGetShaderSource", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glVertexAttrib1f(int p0, float p1) {
        GL20.glVertexAttrib1f(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glVertexAttrib1s(int p0, short p1) {
        GL20.glVertexAttrib1s(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1s", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glVertexAttrib1d(int p0, double p1) {
        GL20.glVertexAttrib1d(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glVertexAttrib2f(int p0, float p1, float p2) {
        GL20.glVertexAttrib2f(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttrib2f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glVertexAttrib2s(int p0, short p1, short p2) {
        GL20.glVertexAttrib2s(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttrib2s", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glVertexAttrib2d(int p0, double p1, double p2) {
        GL20.glVertexAttrib2d(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttrib2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glVertexAttrib3f(int p0, float p1, float p2, float p3) {
        GL20.glVertexAttrib3f(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttrib3f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glVertexAttrib3s(int p0, short p1, short p2, short p3) {
        GL20.glVertexAttrib3s(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttrib3s", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glVertexAttrib3d(int p0, double p1, double p2, double p3) {
        GL20.glVertexAttrib3d(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttrib3d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glVertexAttrib4f(int p0, float p1, float p2, float p3, float p4) {
        GL20.glVertexAttrib4f(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glVertexAttrib4s(int p0, short p1, short p2, short p3, short p4) {
        GL20.glVertexAttrib4s(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4s", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3), "p4", Short.valueOf(p4));
    }

    public static void glVertexAttrib4d(int p0, double p1, double p2, double p3, double p4) {
        GL20.glVertexAttrib4d(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void glVertexAttrib4Nub(int p0, byte p1, byte p2, byte p3, byte p4) {
        GL20.glVertexAttrib4Nub(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4Nub", "p0", Integer.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2), "p3", Byte.valueOf(p3), "p4", Byte.valueOf(p4));
    }

    public static void nglVertexAttrib1fv(int p0, long p1) {
        GL20.nglVertexAttrib1fv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib1fv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib1fv(int p0, FloatBuffer p1) {
        GL20.glVertexAttrib1fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib1sv(int p0, long p1) {
        GL20.nglVertexAttrib1sv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib1sv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib1sv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib1sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib1dv(int p0, long p1) {
        GL20.nglVertexAttrib1dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib1dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib1dv(int p0, DoubleBuffer p1) {
        GL20.glVertexAttrib1dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib2fv(int p0, long p1) {
        GL20.nglVertexAttrib2fv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib2fv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib2fv(int p0, FloatBuffer p1) {
        GL20.glVertexAttrib2fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib2sv(int p0, long p1) {
        GL20.nglVertexAttrib2sv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib2sv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib2sv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib2sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib2dv(int p0, long p1) {
        GL20.nglVertexAttrib2dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib2dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib2dv(int p0, DoubleBuffer p1) {
        GL20.glVertexAttrib2dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib3fv(int p0, long p1) {
        GL20.nglVertexAttrib3fv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib3fv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib3fv(int p0, FloatBuffer p1) {
        GL20.glVertexAttrib3fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib3sv(int p0, long p1) {
        GL20.nglVertexAttrib3sv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib3sv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib3sv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib3sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib3dv(int p0, long p1) {
        GL20.nglVertexAttrib3dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib3dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib3dv(int p0, DoubleBuffer p1) {
        GL20.glVertexAttrib3dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4fv(int p0, long p1) {
        GL20.nglVertexAttrib4fv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4fv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4fv(int p0, FloatBuffer p1) {
        GL20.glVertexAttrib4fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4sv(int p0, long p1) {
        GL20.nglVertexAttrib4sv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4sv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4sv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib4sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4dv(int p0, long p1) {
        GL20.nglVertexAttrib4dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4dv(int p0, DoubleBuffer p1) {
        GL20.glVertexAttrib4dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4iv(int p0, long p1) {
        GL20.nglVertexAttrib4iv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4iv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4iv(int p0, IntBuffer p1) {
        GL20.glVertexAttrib4iv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4bv(int p0, long p1) {
        GL20.nglVertexAttrib4bv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4bv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4bv(int p0, ByteBuffer p1) {
        GL20.glVertexAttrib4bv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4bv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4ubv(int p0, long p1) {
        GL20.nglVertexAttrib4ubv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4ubv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4ubv(int p0, ByteBuffer p1) {
        GL20.glVertexAttrib4ubv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4ubv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4usv(int p0, long p1) {
        GL20.nglVertexAttrib4usv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4usv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4usv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib4usv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4usv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4uiv(int p0, long p1) {
        GL20.nglVertexAttrib4uiv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4uiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4uiv(int p0, IntBuffer p1) {
        GL20.glVertexAttrib4uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4Nbv(int p0, long p1) {
        GL20.nglVertexAttrib4Nbv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4Nbv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4Nbv(int p0, ByteBuffer p1) {
        GL20.glVertexAttrib4Nbv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nbv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4Nsv(int p0, long p1) {
        GL20.nglVertexAttrib4Nsv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4Nsv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4Nsv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib4Nsv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nsv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4Niv(int p0, long p1) {
        GL20.nglVertexAttrib4Niv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4Niv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4Niv(int p0, IntBuffer p1) {
        GL20.glVertexAttrib4Niv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Niv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4Nubv(int p0, long p1) {
        GL20.nglVertexAttrib4Nubv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4Nubv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4Nubv(int p0, ByteBuffer p1) {
        GL20.glVertexAttrib4Nubv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nubv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4Nusv(int p0, long p1) {
        GL20.nglVertexAttrib4Nusv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4Nusv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4Nusv(int p0, ShortBuffer p1) {
        GL20.glVertexAttrib4Nusv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nusv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4Nuiv(int p0, long p1) {
        GL20.nglVertexAttrib4Nuiv(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4Nuiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4Nuiv(int p0, IntBuffer p1) {
        GL20.glVertexAttrib4Nuiv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribPointer(int p0, int p1, int p2, boolean p3, int p4, long p5) {
        GL20.nglVertexAttribPointer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glVertexAttribPointer(int p0, int p1, int p2, boolean p3, int p4, ByteBuffer p5) {
        GL20.glVertexAttribPointer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointer(int p0, int p1, int p2, boolean p3, int p4, long p5) {
        GL20.glVertexAttribPointer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glVertexAttribPointer(int p0, int p1, int p2, boolean p3, int p4, ShortBuffer p5) {
        GL20.glVertexAttribPointer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointer(int p0, int p1, int p2, boolean p3, int p4, IntBuffer p5) {
        GL20.glVertexAttribPointer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointer(int p0, int p1, int p2, boolean p3, int p4, FloatBuffer p5) {
        GL20.glVertexAttribPointer(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glEnableVertexAttribArray(int p0) {
        GL20.glEnableVertexAttribArray(p0);
        LabyDebugContext.glError("glEnableVertexAttribArray", "p0", Integer.valueOf(p0));
    }

    public static void glDisableVertexAttribArray(int p0) {
        GL20.glDisableVertexAttribArray(p0);
        LabyDebugContext.glError("glDisableVertexAttribArray", "p0", Integer.valueOf(p0));
    }

    public static void nglBindAttribLocation(int p0, int p1, long p2) {
        GL20.nglBindAttribLocation(p0, p1, p2);
        LabyDebugContext.glError("nglBindAttribLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glBindAttribLocation(int p0, int p1, ByteBuffer p2) {
        GL20.glBindAttribLocation(p0, p1, p2);
        LabyDebugContext.glError("glBindAttribLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glBindAttribLocation(int p0, int p1, CharSequence p2) {
        GL20.glBindAttribLocation(p0, p1, p2);
        LabyDebugContext.glError("glBindAttribLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetActiveAttrib(int p0, int p1, int p2, long p3, long p4, long p5, long p6) {
        GL20.nglGetActiveAttrib(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetActiveAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetActiveAttrib(int p0, int p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        GL20.glGetActiveAttrib(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static String glGetActiveAttrib(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        String returnType = GL20.glGetActiveAttrib(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static String glGetActiveAttrib(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        String returnType = GL20.glGetActiveAttrib(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static int nglGetAttribLocation(int p0, long p1) {
        int returnType = GL20.nglGetAttribLocation(p0, p1);
        LabyDebugContext.glError("nglGetAttribLocation", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetAttribLocation(int p0, ByteBuffer p1) {
        int returnType = GL20.glGetAttribLocation(p0, p1);
        LabyDebugContext.glError("glGetAttribLocation", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetAttribLocation(int p0, CharSequence p1) {
        int returnType = GL20.glGetAttribLocation(p0, p1);
        LabyDebugContext.glError("glGetAttribLocation", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetVertexAttribiv(int p0, int p1, long p2) {
        GL20.nglGetVertexAttribiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribiv(int p0, int p1, IntBuffer p2) {
        GL20.glGetVertexAttribiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetVertexAttribi(int p0, int p1) {
        int returnType = GL20.glGetVertexAttribi(p0, p1);
        LabyDebugContext.glError("glGetVertexAttribi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetVertexAttribfv(int p0, int p1, long p2) {
        GL20.nglGetVertexAttribfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribfv(int p0, int p1, FloatBuffer p2) {
        GL20.glGetVertexAttribfv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetVertexAttribdv(int p0, int p1, long p2) {
        GL20.nglGetVertexAttribdv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribdv(int p0, int p1, DoubleBuffer p2) {
        GL20.glGetVertexAttribdv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetVertexAttribPointerv(int p0, int p1, long p2) {
        GL20.nglGetVertexAttribPointerv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribPointerv(int p0, int p1, PointerBuffer p2) {
        GL20.glGetVertexAttribPointerv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribPointerv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetVertexAttribPointer(int p0, int p1) {
        long returnType = GL20.glGetVertexAttribPointer(p0, p1);
        LabyDebugContext.glError("glGetVertexAttribPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglDrawBuffers(int p0, long p1) {
        GL20.nglDrawBuffers(p0, p1);
        LabyDebugContext.glError("nglDrawBuffers", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDrawBuffers(IntBuffer p0) {
        GL20.glDrawBuffers(p0);
        LabyDebugContext.glError("glDrawBuffers", "p0", p0);
    }

    public static void glDrawBuffers(int p0) {
        GL20.glDrawBuffers(p0);
        LabyDebugContext.glError("glDrawBuffers", "p0", Integer.valueOf(p0));
    }

    public static void glBlendEquationSeparate(int p0, int p1) {
        GL20.glBlendEquationSeparate(p0, p1);
        LabyDebugContext.glError("glBlendEquationSeparate", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glStencilOpSeparate(int p0, int p1, int p2, int p3) {
        GL20.glStencilOpSeparate(p0, p1, p2, p3);
        LabyDebugContext.glError("glStencilOpSeparate", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glStencilFuncSeparate(int p0, int p1, int p2, int p3) {
        GL20.glStencilFuncSeparate(p0, p1, p2, p3);
        LabyDebugContext.glError("glStencilFuncSeparate", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glStencilMaskSeparate(int p0, int p1) {
        GL20.glStencilMaskSeparate(p0, p1);
        LabyDebugContext.glError("glStencilMaskSeparate", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glShaderSource(int p0, PointerBuffer p1, int[] p2) {
        GL20.glShaderSource(p0, p1, p2);
        LabyDebugContext.glError("glShaderSource", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glUniform1fv(int p0, float[] p1) {
        GL20.glUniform1fv(p0, p1);
        LabyDebugContext.glError("glUniform1fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform2fv(int p0, float[] p1) {
        GL20.glUniform2fv(p0, p1);
        LabyDebugContext.glError("glUniform2fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform3fv(int p0, float[] p1) {
        GL20.glUniform3fv(p0, p1);
        LabyDebugContext.glError("glUniform3fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform4fv(int p0, float[] p1) {
        GL20.glUniform4fv(p0, p1);
        LabyDebugContext.glError("glUniform4fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform1iv(int p0, int[] p1) {
        GL20.glUniform1iv(p0, p1);
        LabyDebugContext.glError("glUniform1iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform2iv(int p0, int[] p1) {
        GL20.glUniform2iv(p0, p1);
        LabyDebugContext.glError("glUniform2iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform3iv(int p0, int[] p1) {
        GL20.glUniform3iv(p0, p1);
        LabyDebugContext.glError("glUniform3iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform4iv(int p0, int[] p1) {
        GL20.glUniform4iv(p0, p1);
        LabyDebugContext.glError("glUniform4iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniformMatrix2fv(int p0, boolean p1, float[] p2) {
        GL20.glUniformMatrix2fv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix3fv(int p0, boolean p1, float[] p2) {
        GL20.glUniformMatrix3fv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix4fv(int p0, boolean p1, float[] p2) {
        GL20.glUniformMatrix4fv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glGetShaderiv(int p0, int p1, int[] p2) {
        GL20.glGetShaderiv(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramiv(int p0, int p1, int[] p2) {
        GL20.glGetProgramiv(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShaderInfoLog(int p0, int[] p1, ByteBuffer p2) {
        GL20.glGetShaderInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetProgramInfoLog(int p0, int[] p1, ByteBuffer p2) {
        GL20.glGetProgramInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetAttachedShaders(int p0, int[] p1, int[] p2) {
        GL20.glGetAttachedShaders(p0, p1, p2);
        LabyDebugContext.glError("glGetAttachedShaders", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetActiveUniform(int p0, int p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        GL20.glGetActiveUniform(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveUniform", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glGetUniformfv(int p0, int p1, float[] p2) {
        GL20.glGetUniformfv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetUniformiv(int p0, int p1, int[] p2) {
        GL20.glGetUniformiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShaderSource(int p0, int[] p1, ByteBuffer p2) {
        GL20.glGetShaderSource(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderSource", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glVertexAttrib1fv(int p0, float[] p1) {
        GL20.glVertexAttrib1fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib1sv(int p0, short[] p1) {
        GL20.glVertexAttrib1sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib1dv(int p0, double[] p1) {
        GL20.glVertexAttrib1dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib2fv(int p0, float[] p1) {
        GL20.glVertexAttrib2fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib2sv(int p0, short[] p1) {
        GL20.glVertexAttrib2sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib2dv(int p0, double[] p1) {
        GL20.glVertexAttrib2dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib3fv(int p0, float[] p1) {
        GL20.glVertexAttrib3fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib3sv(int p0, short[] p1) {
        GL20.glVertexAttrib3sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib3dv(int p0, double[] p1) {
        GL20.glVertexAttrib3dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4fv(int p0, float[] p1) {
        GL20.glVertexAttrib4fv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4fv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4sv(int p0, short[] p1) {
        GL20.glVertexAttrib4sv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4sv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4dv(int p0, double[] p1) {
        GL20.glVertexAttrib4dv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4iv(int p0, int[] p1) {
        GL20.glVertexAttrib4iv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4iv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4usv(int p0, short[] p1) {
        GL20.glVertexAttrib4usv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4usv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4uiv(int p0, int[] p1) {
        GL20.glVertexAttrib4uiv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4uiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4Nsv(int p0, short[] p1) {
        GL20.glVertexAttrib4Nsv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nsv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4Niv(int p0, int[] p1) {
        GL20.glVertexAttrib4Niv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Niv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4Nusv(int p0, short[] p1) {
        GL20.glVertexAttrib4Nusv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nusv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4Nuiv(int p0, int[] p1) {
        GL20.glVertexAttrib4Nuiv(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4Nuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetActiveAttrib(int p0, int p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        GL20.glGetActiveAttrib(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveAttrib", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glGetVertexAttribiv(int p0, int p1, int[] p2) {
        GL20.glGetVertexAttribiv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexAttribfv(int p0, int p1, float[] p2) {
        GL20.glGetVertexAttribfv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexAttribdv(int p0, int p1, double[] p2) {
        GL20.glGetVertexAttribdv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDrawBuffers(int[] p0) {
        GL20.glDrawBuffers(p0);
        LabyDebugContext.glError("glDrawBuffers", "p0", p0);
    }
}
