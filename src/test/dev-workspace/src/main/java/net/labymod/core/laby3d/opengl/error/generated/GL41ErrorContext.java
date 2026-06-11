package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL41;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL41ErrorContext.class */
public final class GL41ErrorContext {
    public static void glReleaseShaderCompiler() {
        GL41.glReleaseShaderCompiler();
        LabyDebugContext.glError("glReleaseShaderCompiler", new Object[0]);
    }

    public static void nglShaderBinary(int p0, long p1, int p2, long p3, int p4) {
        GL41.nglShaderBinary(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglShaderBinary", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glShaderBinary(IntBuffer p0, int p1, ByteBuffer p2) {
        GL41.glShaderBinary(p0, p1, p2);
        LabyDebugContext.glError("glShaderBinary", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetShaderPrecisionFormat(int p0, int p1, long p2, long p3) {
        GL41.nglGetShaderPrecisionFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetShaderPrecisionFormat(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        GL41.glGetShaderPrecisionFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static int glGetShaderPrecisionFormat(int p0, int p1, IntBuffer p2) {
        int returnType = GL41.glGetShaderPrecisionFormat(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void glDepthRangef(float p0, float p1) {
        GL41.glDepthRangef(p0, p1);
        LabyDebugContext.glError("glDepthRangef", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glClearDepthf(float p0) {
        GL41.glClearDepthf(p0);
        LabyDebugContext.glError("glClearDepthf", "p0", Float.valueOf(p0));
    }

    public static void nglGetProgramBinary(int p0, int p1, long p2, long p3, long p4) {
        GL41.nglGetProgramBinary(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetProgramBinary", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetProgramBinary(int p0, IntBuffer p1, IntBuffer p2, ByteBuffer p3) {
        GL41.glGetProgramBinary(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramBinary", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void nglProgramBinary(int p0, int p1, long p2, int p3) {
        GL41.nglProgramBinary(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramBinary", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glProgramBinary(int p0, int p1, ByteBuffer p2) {
        GL41.glProgramBinary(p0, p1, p2);
        LabyDebugContext.glError("glProgramBinary", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramParameteri(int p0, int p1, int p2) {
        GL41.glProgramParameteri(p0, p1, p2);
        LabyDebugContext.glError("glProgramParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glUseProgramStages(int p0, int p1, int p2) {
        GL41.glUseProgramStages(p0, p1, p2);
        LabyDebugContext.glError("glUseProgramStages", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glActiveShaderProgram(int p0, int p1) {
        GL41.glActiveShaderProgram(p0, p1);
        LabyDebugContext.glError("glActiveShaderProgram", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static int nglCreateShaderProgramv(int p0, int p1, long p2) {
        int returnType = GL41.nglCreateShaderProgramv(p0, p1, p2);
        LabyDebugContext.glError("nglCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glCreateShaderProgramv(int p0, PointerBuffer p1) {
        int returnType = GL41.glCreateShaderProgramv(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glCreateShaderProgramv(int p0, CharSequence[] p1) {
        int returnType = GL41.glCreateShaderProgramv(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glCreateShaderProgramv(int p0, CharSequence p1) {
        int returnType = GL41.glCreateShaderProgramv(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void glBindProgramPipeline(int p0) {
        GL41.glBindProgramPipeline(p0);
        LabyDebugContext.glError("glBindProgramPipeline", "p0", Integer.valueOf(p0));
    }

    public static void nglDeleteProgramPipelines(int p0, long p1) {
        GL41.nglDeleteProgramPipelines(p0, p1);
        LabyDebugContext.glError("nglDeleteProgramPipelines", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteProgramPipelines(IntBuffer p0) {
        GL41.glDeleteProgramPipelines(p0);
        LabyDebugContext.glError("glDeleteProgramPipelines", "p0", p0);
    }

    public static void glDeleteProgramPipelines(int p0) {
        GL41.glDeleteProgramPipelines(p0);
        LabyDebugContext.glError("glDeleteProgramPipelines", "p0", Integer.valueOf(p0));
    }

    public static void nglGenProgramPipelines(int p0, long p1) {
        GL41.nglGenProgramPipelines(p0, p1);
        LabyDebugContext.glError("nglGenProgramPipelines", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenProgramPipelines(IntBuffer p0) {
        GL41.glGenProgramPipelines(p0);
        LabyDebugContext.glError("glGenProgramPipelines", "p0", p0);
    }

    public static int glGenProgramPipelines() {
        int returnType = GL41.glGenProgramPipelines();
        LabyDebugContext.glError("glGenProgramPipelines", new Object[0]);
        return returnType;
    }

    public static boolean glIsProgramPipeline(int p0) {
        boolean returnType = GL41.glIsProgramPipeline(p0);
        LabyDebugContext.glError("glIsProgramPipeline", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetProgramPipelineiv(int p0, int p1, long p2) {
        GL41.nglGetProgramPipelineiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramPipelineiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramPipelineiv(int p0, int p1, IntBuffer p2) {
        GL41.glGetProgramPipelineiv(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetProgramPipelinei(int p0, int p1) {
        int returnType = GL41.glGetProgramPipelinei(p0, p1);
        LabyDebugContext.glError("glGetProgramPipelinei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glProgramUniform1i(int p0, int p1, int p2) {
        GL41.glProgramUniform1i(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glProgramUniform2i(int p0, int p1, int p2, int p3) {
        GL41.glProgramUniform2i(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glProgramUniform3i(int p0, int p1, int p2, int p3, int p4) {
        GL41.glProgramUniform3i(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glProgramUniform4i(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL41.glProgramUniform4i(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glProgramUniform1ui(int p0, int p1, int p2) {
        GL41.glProgramUniform1ui(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glProgramUniform2ui(int p0, int p1, int p2, int p3) {
        GL41.glProgramUniform2ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glProgramUniform3ui(int p0, int p1, int p2, int p3, int p4) {
        GL41.glProgramUniform3ui(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glProgramUniform4ui(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL41.glProgramUniform4ui(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glProgramUniform1f(int p0, int p1, float p2) {
        GL41.glProgramUniform1f(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glProgramUniform2f(int p0, int p1, float p2, float p3) {
        GL41.glProgramUniform2f(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glProgramUniform3f(int p0, int p1, float p2, float p3, float p4) {
        GL41.glProgramUniform3f(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glProgramUniform4f(int p0, int p1, float p2, float p3, float p4, float p5) {
        GL41.glProgramUniform4f(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5));
    }

    public static void glProgramUniform1d(int p0, int p1, double p2) {
        GL41.glProgramUniform1d(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glProgramUniform2d(int p0, int p1, double p2, double p3) {
        GL41.glProgramUniform2d(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glProgramUniform3d(int p0, int p1, double p2, double p3, double p4) {
        GL41.glProgramUniform3d(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void glProgramUniform4d(int p0, int p1, double p2, double p3, double p4, double p5) {
        GL41.glProgramUniform4d(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void nglProgramUniform1iv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform1iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1iv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform1iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2iv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform2iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2iv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform2iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3iv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform3iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3iv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform3iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4iv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform4iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4iv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform4iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform1uiv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1uiv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform1uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2uiv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2uiv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform2uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3uiv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3uiv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform3uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4uiv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4uiv(int p0, int p1, IntBuffer p2) {
        GL41.glProgramUniform4uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform1fv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform1fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1fv(int p0, int p1, FloatBuffer p2) {
        GL41.glProgramUniform1fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2fv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2fv(int p0, int p1, FloatBuffer p2) {
        GL41.glProgramUniform2fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3fv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3fv(int p0, int p1, FloatBuffer p2) {
        GL41.glProgramUniform3fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4fv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4fv(int p0, int p1, FloatBuffer p2) {
        GL41.glProgramUniform4fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform1dv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform1dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1dv(int p0, int p1, DoubleBuffer p2) {
        GL41.glProgramUniform1dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2dv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2dv(int p0, int p1, DoubleBuffer p2) {
        GL41.glProgramUniform2dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3dv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3dv(int p0, int p1, DoubleBuffer p2) {
        GL41.glProgramUniform3dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4dv(int p0, int p1, int p2, long p3) {
        GL41.nglProgramUniform4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4dv(int p0, int p1, DoubleBuffer p2) {
        GL41.glProgramUniform4dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniformMatrix2fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix2fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix3fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix4fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix2dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix3dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix4dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x3fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix2x3fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x3fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix2x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x2fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix3x2fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x2fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix3x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x4fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix2x4fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x4fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix2x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x2fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix4x2fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x2fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix4x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x4fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix3x4fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x4fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix3x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x3fv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix4x3fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x3fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        GL41.glProgramUniformMatrix4x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x3dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix2x3dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x3dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix2x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x2dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix3x2dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x2dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix3x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x4dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix2x4dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x4dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix2x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x2dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix4x2dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x2dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix4x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x4dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix3x4dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x4dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix3x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x3dv(int p0, int p1, int p2, boolean p3, long p4) {
        GL41.nglProgramUniformMatrix4x3dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x3dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        GL41.glProgramUniformMatrix4x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glValidateProgramPipeline(int p0) {
        GL41.glValidateProgramPipeline(p0);
        LabyDebugContext.glError("glValidateProgramPipeline", "p0", Integer.valueOf(p0));
    }

    public static void nglGetProgramPipelineInfoLog(int p0, int p1, long p2, long p3) {
        GL41.nglGetProgramPipelineInfoLog(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetProgramPipelineInfoLog(int p0, IntBuffer p1, ByteBuffer p2) {
        GL41.glGetProgramPipelineInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetProgramPipelineInfoLog(int p0, int p1) {
        String returnType = GL41.glGetProgramPipelineInfoLog(p0, p1);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetProgramPipelineInfoLog(int p0) {
        String returnType = GL41.glGetProgramPipelineInfoLog(p0);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glVertexAttribL1d(int p0, double p1) {
        GL41.glVertexAttribL1d(p0, p1);
        LabyDebugContext.glError("glVertexAttribL1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glVertexAttribL2d(int p0, double p1, double p2) {
        GL41.glVertexAttribL2d(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttribL2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glVertexAttribL3d(int p0, double p1, double p2, double p3) {
        GL41.glVertexAttribL3d(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribL3d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glVertexAttribL4d(int p0, double p1, double p2, double p3, double p4) {
        GL41.glVertexAttribL4d(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribL4d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void nglVertexAttribL1dv(int p0, long p1) {
        GL41.nglVertexAttribL1dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL1dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL1dv(int p0, DoubleBuffer p1) {
        GL41.glVertexAttribL1dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribL2dv(int p0, long p1) {
        GL41.nglVertexAttribL2dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL2dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL2dv(int p0, DoubleBuffer p1) {
        GL41.glVertexAttribL2dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribL3dv(int p0, long p1) {
        GL41.nglVertexAttribL3dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL3dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL3dv(int p0, DoubleBuffer p1) {
        GL41.glVertexAttribL3dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribL4dv(int p0, long p1) {
        GL41.nglVertexAttribL4dv(p0, p1);
        LabyDebugContext.glError("nglVertexAttribL4dv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttribL4dv(int p0, DoubleBuffer p1) {
        GL41.glVertexAttribL4dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribLPointer(int p0, int p1, int p2, int p3, long p4) {
        GL41.nglVertexAttribLPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glVertexAttribLPointer(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL41.glVertexAttribLPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glVertexAttribLPointer(int p0, int p1, int p2, int p3, long p4) {
        GL41.glVertexAttribLPointer(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glVertexAttribLPointer(int p0, int p1, int p2, DoubleBuffer p3) {
        GL41.glVertexAttribLPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttribLPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglGetVertexAttribLdv(int p0, int p1, long p2) {
        GL41.nglGetVertexAttribLdv(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribLdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribLdv(int p0, int p1, DoubleBuffer p2) {
        GL41.glGetVertexAttribLdv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribLdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglViewportArrayv(int p0, int p1, long p2) {
        GL41.nglViewportArrayv(p0, p1, p2);
        LabyDebugContext.glError("nglViewportArrayv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glViewportArrayv(int p0, FloatBuffer p1) {
        GL41.glViewportArrayv(p0, p1);
        LabyDebugContext.glError("glViewportArrayv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glViewportIndexedf(int p0, float p1, float p2, float p3, float p4) {
        GL41.glViewportIndexedf(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glViewportIndexedf", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void nglViewportIndexedfv(int p0, long p1) {
        GL41.nglViewportIndexedfv(p0, p1);
        LabyDebugContext.glError("nglViewportIndexedfv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glViewportIndexedfv(int p0, FloatBuffer p1) {
        GL41.glViewportIndexedfv(p0, p1);
        LabyDebugContext.glError("glViewportIndexedfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglScissorArrayv(int p0, int p1, long p2) {
        GL41.nglScissorArrayv(p0, p1, p2);
        LabyDebugContext.glError("nglScissorArrayv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glScissorArrayv(int p0, IntBuffer p1) {
        GL41.glScissorArrayv(p0, p1);
        LabyDebugContext.glError("glScissorArrayv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glScissorIndexed(int p0, int p1, int p2, int p3, int p4) {
        GL41.glScissorIndexed(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glScissorIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglScissorIndexedv(int p0, long p1) {
        GL41.nglScissorIndexedv(p0, p1);
        LabyDebugContext.glError("nglScissorIndexedv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glScissorIndexedv(int p0, IntBuffer p1) {
        GL41.glScissorIndexedv(p0, p1);
        LabyDebugContext.glError("glScissorIndexedv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglDepthRangeArrayv(int p0, int p1, long p2) {
        GL41.nglDepthRangeArrayv(p0, p1, p2);
        LabyDebugContext.glError("nglDepthRangeArrayv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glDepthRangeArrayv(int p0, DoubleBuffer p1) {
        GL41.glDepthRangeArrayv(p0, p1);
        LabyDebugContext.glError("glDepthRangeArrayv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDepthRangeIndexed(int p0, double p1, double p2) {
        GL41.glDepthRangeIndexed(p0, p1, p2);
        LabyDebugContext.glError("glDepthRangeIndexed", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglGetFloati_v(int p0, int p1, long p2) {
        GL41.nglGetFloati_v(p0, p1, p2);
        LabyDebugContext.glError("nglGetFloati_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetFloati_v(int p0, int p1, FloatBuffer p2) {
        GL41.glGetFloati_v(p0, p1, p2);
        LabyDebugContext.glError("glGetFloati_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetFloati(int p0, int p1) {
        float returnType = GL41.glGetFloati(p0, p1);
        LabyDebugContext.glError("glGetFloati", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetDoublei_v(int p0, int p1, long p2) {
        GL41.nglGetDoublei_v(p0, p1, p2);
        LabyDebugContext.glError("nglGetDoublei_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetDoublei_v(int p0, int p1, DoubleBuffer p2) {
        GL41.glGetDoublei_v(p0, p1, p2);
        LabyDebugContext.glError("glGetDoublei_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetDoublei(int p0, int p1) {
        double returnType = GL41.glGetDoublei(p0, p1);
        LabyDebugContext.glError("glGetDoublei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glShaderBinary(int[] p0, int p1, ByteBuffer p2) {
        GL41.glShaderBinary(p0, p1, p2);
        LabyDebugContext.glError("glShaderBinary", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShaderPrecisionFormat(int p0, int p1, int[] p2, int[] p3) {
        GL41.glGetShaderPrecisionFormat(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetShaderPrecisionFormat", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
    }

    public static void glGetProgramBinary(int p0, int[] p1, int[] p2, ByteBuffer p3) {
        GL41.glGetProgramBinary(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramBinary", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2, "p3", p3);
    }

    public static void glDeleteProgramPipelines(int[] p0) {
        GL41.glDeleteProgramPipelines(p0);
        LabyDebugContext.glError("glDeleteProgramPipelines", "p0", p0);
    }

    public static void glGenProgramPipelines(int[] p0) {
        GL41.glGenProgramPipelines(p0);
        LabyDebugContext.glError("glGenProgramPipelines", "p0", p0);
    }

    public static void glGetProgramPipelineiv(int p0, int p1, int[] p2) {
        GL41.glGetProgramPipelineiv(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1iv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform1iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2iv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform2iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3iv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform3iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4iv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform4iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1uiv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform1uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2uiv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform2uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3uiv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform3uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4uiv(int p0, int p1, int[] p2) {
        GL41.glProgramUniform4uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1fv(int p0, int p1, float[] p2) {
        GL41.glProgramUniform1fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2fv(int p0, int p1, float[] p2) {
        GL41.glProgramUniform2fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3fv(int p0, int p1, float[] p2) {
        GL41.glProgramUniform3fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4fv(int p0, int p1, float[] p2) {
        GL41.glProgramUniform4fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1dv(int p0, int p1, double[] p2) {
        GL41.glProgramUniform1dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2dv(int p0, int p1, double[] p2) {
        GL41.glProgramUniform2dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3dv(int p0, int p1, double[] p2) {
        GL41.glProgramUniform3dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4dv(int p0, int p1, double[] p2) {
        GL41.glProgramUniform4dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniformMatrix2fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x3fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix2x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x2fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix3x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x4fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix2x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x2fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix4x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x4fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix3x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x3fv(int p0, int p1, boolean p2, float[] p3) {
        GL41.glProgramUniformMatrix4x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x3dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix2x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x2dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix3x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x4dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix2x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x2dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix4x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x4dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix3x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x3dv(int p0, int p1, boolean p2, double[] p3) {
        GL41.glProgramUniformMatrix4x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glGetProgramPipelineInfoLog(int p0, int[] p1, ByteBuffer p2) {
        GL41.glGetProgramPipelineInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glVertexAttribL1dv(int p0, double[] p1) {
        GL41.glVertexAttribL1dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribL2dv(int p0, double[] p1) {
        GL41.glVertexAttribL2dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribL3dv(int p0, double[] p1) {
        GL41.glVertexAttribL3dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribL4dv(int p0, double[] p1) {
        GL41.glVertexAttribL4dv(p0, p1);
        LabyDebugContext.glError("glVertexAttribL4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetVertexAttribLdv(int p0, int p1, double[] p2) {
        GL41.glGetVertexAttribLdv(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribLdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glViewportArrayv(int p0, float[] p1) {
        GL41.glViewportArrayv(p0, p1);
        LabyDebugContext.glError("glViewportArrayv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glViewportIndexedfv(int p0, float[] p1) {
        GL41.glViewportIndexedfv(p0, p1);
        LabyDebugContext.glError("glViewportIndexedfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glScissorArrayv(int p0, int[] p1) {
        GL41.glScissorArrayv(p0, p1);
        LabyDebugContext.glError("glScissorArrayv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glScissorIndexedv(int p0, int[] p1) {
        GL41.glScissorIndexedv(p0, p1);
        LabyDebugContext.glError("glScissorIndexedv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDepthRangeArrayv(int p0, double[] p1) {
        GL41.glDepthRangeArrayv(p0, p1);
        LabyDebugContext.glError("glDepthRangeArrayv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetFloati_v(int p0, int p1, float[] p2) {
        GL41.glGetFloati_v(p0, p1, p2);
        LabyDebugContext.glError("glGetFloati_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetDoublei_v(int p0, int p1, double[] p2) {
        GL41.glGetDoublei_v(p0, p1, p2);
        LabyDebugContext.glError("glGetDoublei_v", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}
