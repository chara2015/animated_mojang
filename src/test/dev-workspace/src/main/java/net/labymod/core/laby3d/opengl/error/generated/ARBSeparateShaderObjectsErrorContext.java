package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBSeparateShaderObjects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBSeparateShaderObjectsErrorContext.class */
public final class ARBSeparateShaderObjectsErrorContext {
    public static void glUseProgramStages(int p0, int p1, int p2) {
        ARBSeparateShaderObjects.glUseProgramStages(p0, p1, p2);
        LabyDebugContext.glError("glUseProgramStages", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glActiveShaderProgram(int p0, int p1) {
        ARBSeparateShaderObjects.glActiveShaderProgram(p0, p1);
        LabyDebugContext.glError("glActiveShaderProgram", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static int nglCreateShaderProgramv(int p0, int p1, long p2) {
        int returnType = ARBSeparateShaderObjects.nglCreateShaderProgramv(p0, p1, p2);
        LabyDebugContext.glError("nglCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glCreateShaderProgramv(int p0, PointerBuffer p1) {
        int returnType = ARBSeparateShaderObjects.glCreateShaderProgramv(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glCreateShaderProgramv(int p0, CharSequence[] p1) {
        int returnType = ARBSeparateShaderObjects.glCreateShaderProgramv(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glCreateShaderProgramv(int p0, CharSequence p1) {
        int returnType = ARBSeparateShaderObjects.glCreateShaderProgramv(p0, p1);
        LabyDebugContext.glError("glCreateShaderProgramv", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void glBindProgramPipeline(int p0) {
        ARBSeparateShaderObjects.glBindProgramPipeline(p0);
        LabyDebugContext.glError("glBindProgramPipeline", "p0", Integer.valueOf(p0));
    }

    public static void nglDeleteProgramPipelines(int p0, long p1) {
        ARBSeparateShaderObjects.nglDeleteProgramPipelines(p0, p1);
        LabyDebugContext.glError("nglDeleteProgramPipelines", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteProgramPipelines(IntBuffer p0) {
        ARBSeparateShaderObjects.glDeleteProgramPipelines(p0);
        LabyDebugContext.glError("glDeleteProgramPipelines", "p0", p0);
    }

    public static void glDeleteProgramPipelines(int p0) {
        ARBSeparateShaderObjects.glDeleteProgramPipelines(p0);
        LabyDebugContext.glError("glDeleteProgramPipelines", "p0", Integer.valueOf(p0));
    }

    public static void nglGenProgramPipelines(int p0, long p1) {
        ARBSeparateShaderObjects.nglGenProgramPipelines(p0, p1);
        LabyDebugContext.glError("nglGenProgramPipelines", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenProgramPipelines(IntBuffer p0) {
        ARBSeparateShaderObjects.glGenProgramPipelines(p0);
        LabyDebugContext.glError("glGenProgramPipelines", "p0", p0);
    }

    public static int glGenProgramPipelines() {
        int returnType = ARBSeparateShaderObjects.glGenProgramPipelines();
        LabyDebugContext.glError("glGenProgramPipelines", new Object[0]);
        return returnType;
    }

    public static boolean glIsProgramPipeline(int p0) {
        boolean returnType = ARBSeparateShaderObjects.glIsProgramPipeline(p0);
        LabyDebugContext.glError("glIsProgramPipeline", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glProgramParameteri(int p0, int p1, int p2) {
        ARBSeparateShaderObjects.glProgramParameteri(p0, p1, p2);
        LabyDebugContext.glError("glProgramParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglGetProgramPipelineiv(int p0, int p1, long p2) {
        ARBSeparateShaderObjects.nglGetProgramPipelineiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramPipelineiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramPipelineiv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glGetProgramPipelineiv(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetProgramPipelinei(int p0, int p1) {
        int returnType = ARBSeparateShaderObjects.glGetProgramPipelinei(p0, p1);
        LabyDebugContext.glError("glGetProgramPipelinei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glProgramUniform1i(int p0, int p1, int p2) {
        ARBSeparateShaderObjects.glProgramUniform1i(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glProgramUniform2i(int p0, int p1, int p2, int p3) {
        ARBSeparateShaderObjects.glProgramUniform2i(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glProgramUniform3i(int p0, int p1, int p2, int p3, int p4) {
        ARBSeparateShaderObjects.glProgramUniform3i(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glProgramUniform4i(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBSeparateShaderObjects.glProgramUniform4i(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glProgramUniform1ui(int p0, int p1, int p2) {
        ARBSeparateShaderObjects.glProgramUniform1ui(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glProgramUniform2ui(int p0, int p1, int p2, int p3) {
        ARBSeparateShaderObjects.glProgramUniform2ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glProgramUniform3ui(int p0, int p1, int p2, int p3, int p4) {
        ARBSeparateShaderObjects.glProgramUniform3ui(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glProgramUniform4ui(int p0, int p1, int p2, int p3, int p4, int p5) {
        ARBSeparateShaderObjects.glProgramUniform4ui(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glProgramUniform1f(int p0, int p1, float p2) {
        ARBSeparateShaderObjects.glProgramUniform1f(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glProgramUniform2f(int p0, int p1, float p2, float p3) {
        ARBSeparateShaderObjects.glProgramUniform2f(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glProgramUniform3f(int p0, int p1, float p2, float p3, float p4) {
        ARBSeparateShaderObjects.glProgramUniform3f(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glProgramUniform4f(int p0, int p1, float p2, float p3, float p4, float p5) {
        ARBSeparateShaderObjects.glProgramUniform4f(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4f", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5));
    }

    public static void glProgramUniform1d(int p0, int p1, double p2) {
        ARBSeparateShaderObjects.glProgramUniform1d(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glProgramUniform2d(int p0, int p1, double p2, double p3) {
        ARBSeparateShaderObjects.glProgramUniform2d(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniform2d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glProgramUniform3d(int p0, int p1, double p2, double p3, double p4) {
        ARBSeparateShaderObjects.glProgramUniform3d(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramUniform3d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void glProgramUniform4d(int p0, int p1, double p2, double p3, double p4, double p5) {
        ARBSeparateShaderObjects.glProgramUniform4d(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramUniform4d", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void nglProgramUniform1iv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform1iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1iv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform1iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2iv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform2iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2iv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform2iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3iv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform3iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3iv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform3iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4iv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform4iv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4iv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform4iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform1uiv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform1uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1uiv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform1uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2uiv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform2uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2uiv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform2uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3uiv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform3uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3uiv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform3uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4uiv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform4uiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4uiv(int p0, int p1, IntBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform4uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform1fv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform1fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1fv(int p0, int p1, FloatBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform1fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2fv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2fv(int p0, int p1, FloatBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform2fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3fv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3fv(int p0, int p1, FloatBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform3fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4fv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4fv(int p0, int p1, FloatBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform4fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform1dv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform1dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform1dv(int p0, int p1, DoubleBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform1dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform2dv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform2dv(int p0, int p1, DoubleBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform2dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform3dv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform3dv(int p0, int p1, DoubleBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform3dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniform4dv(int p0, int p1, int p2, long p3) {
        ARBSeparateShaderObjects.nglProgramUniform4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramUniform4dv(int p0, int p1, DoubleBuffer p2) {
        ARBSeparateShaderObjects.glProgramUniform4dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglProgramUniformMatrix2fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix2fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix3fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix4fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix2dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix3dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix4dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x3fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix2x3fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x3fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x2fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix3x2fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x2fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x4fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix2x4fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x4fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x2fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix4x2fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x2fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x4fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix3x4fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x4fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x3fv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix4x3fv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x3fv(int p0, int p1, boolean p2, FloatBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x3dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix2x3dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x3dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x2dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix3x2dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x2dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix2x4dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix2x4dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix2x4dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x2dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix4x2dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x2dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix3x4dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix3x4dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix3x4dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void nglProgramUniformMatrix4x3dv(int p0, int p1, int p2, boolean p3, long p4) {
        ARBSeparateShaderObjects.nglProgramUniformMatrix4x3dv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramUniformMatrix4x3dv(int p0, int p1, boolean p2, DoubleBuffer p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glValidateProgramPipeline(int p0) {
        ARBSeparateShaderObjects.glValidateProgramPipeline(p0);
        LabyDebugContext.glError("glValidateProgramPipeline", "p0", Integer.valueOf(p0));
    }

    public static void nglGetProgramPipelineInfoLog(int p0, int p1, long p2, long p3) {
        ARBSeparateShaderObjects.nglGetProgramPipelineInfoLog(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetProgramPipelineInfoLog(int p0, IntBuffer p1, ByteBuffer p2) {
        ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetProgramPipelineInfoLog(int p0, int p1) {
        String returnType = ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(p0, p1);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetProgramPipelineInfoLog(int p0) {
        String returnType = ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(p0);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glDeleteProgramPipelines(int[] p0) {
        ARBSeparateShaderObjects.glDeleteProgramPipelines(p0);
        LabyDebugContext.glError("glDeleteProgramPipelines", "p0", p0);
    }

    public static void glGenProgramPipelines(int[] p0) {
        ARBSeparateShaderObjects.glGenProgramPipelines(p0);
        LabyDebugContext.glError("glGenProgramPipelines", "p0", p0);
    }

    public static void glGetProgramPipelineiv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glGetProgramPipelineiv(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1iv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform1iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2iv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform2iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3iv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform3iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4iv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform4iv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4iv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1uiv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform1uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2uiv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform2uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3uiv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform3uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4uiv(int p0, int p1, int[] p2) {
        ARBSeparateShaderObjects.glProgramUniform4uiv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4uiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1fv(int p0, int p1, float[] p2) {
        ARBSeparateShaderObjects.glProgramUniform1fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2fv(int p0, int p1, float[] p2) {
        ARBSeparateShaderObjects.glProgramUniform2fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3fv(int p0, int p1, float[] p2) {
        ARBSeparateShaderObjects.glProgramUniform3fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4fv(int p0, int p1, float[] p2) {
        ARBSeparateShaderObjects.glProgramUniform4fv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform1dv(int p0, int p1, double[] p2) {
        ARBSeparateShaderObjects.glProgramUniform1dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform2dv(int p0, int p1, double[] p2) {
        ARBSeparateShaderObjects.glProgramUniform2dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform3dv(int p0, int p1, double[] p2) {
        ARBSeparateShaderObjects.glProgramUniform3dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniform4dv(int p0, int p1, double[] p2) {
        ARBSeparateShaderObjects.glProgramUniform4dv(p0, p1, p2);
        LabyDebugContext.glError("glProgramUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramUniformMatrix2fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x3fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x2fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x4fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x2fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x2fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x4fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x4fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x3fv(int p0, int p1, boolean p2, float[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x3fv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3fv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x3dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x2dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix2x4dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix2x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x2dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix3x4dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix3x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glProgramUniformMatrix4x3dv(int p0, int p1, boolean p2, double[] p3) {
        ARBSeparateShaderObjects.glProgramUniformMatrix4x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("glProgramUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", p3);
    }

    public static void glGetProgramPipelineInfoLog(int p0, int[] p1, ByteBuffer p2) {
        ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramPipelineInfoLog", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }
}
