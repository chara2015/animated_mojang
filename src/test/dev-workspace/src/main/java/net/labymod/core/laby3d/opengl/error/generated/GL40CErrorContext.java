package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.GL40C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL40CErrorContext.class */
public final class GL40CErrorContext {
    public static void glBlendEquationi(int p0, int p1) {
        GL40C.glBlendEquationi(p0, p1);
        LabyDebugContext.glError("glBlendEquationi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glBlendEquationSeparatei(int p0, int p1, int p2) {
        GL40C.glBlendEquationSeparatei(p0, p1, p2);
        LabyDebugContext.glError("glBlendEquationSeparatei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBlendFunci(int p0, int p1, int p2) {
        GL40C.glBlendFunci(p0, p1, p2);
        LabyDebugContext.glError("glBlendFunci", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBlendFuncSeparatei(int p0, int p1, int p2, int p3, int p4) {
        GL40C.glBlendFuncSeparatei(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glBlendFuncSeparatei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglDrawArraysIndirect(int p0, long p1) {
        GL40C.nglDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("nglDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDrawArraysIndirect(int p0, ByteBuffer p1) {
        GL40C.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawArraysIndirect(int p0, long p1) {
        GL40C.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDrawArraysIndirect(int p0, IntBuffer p1) {
        GL40C.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglDrawElementsIndirect(int p0, int p1, long p2) {
        GL40C.nglDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("nglDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glDrawElementsIndirect(int p0, int p1, ByteBuffer p2) {
        GL40C.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDrawElementsIndirect(int p0, int p1, long p2) {
        GL40C.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glDrawElementsIndirect(int p0, int p1, IntBuffer p2) {
        GL40C.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glUniform1d(int p0, double p1) {
        GL40C.glUniform1d(p0, p1);
        LabyDebugContext.glError("glUniform1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glUniform2d(int p0, double p1, double p2) {
        GL40C.glUniform2d(p0, p1, p2);
        LabyDebugContext.glError("glUniform2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glUniform3d(int p0, double p1, double p2, double p3) {
        GL40C.glUniform3d(p0, p1, p2, p3);
        LabyDebugContext.glError("glUniform3d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glUniform4d(int p0, double p1, double p2, double p3, double p4) {
        GL40C.glUniform4d(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glUniform4d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void nglUniform1dv(int p0, int p1, long p2) {
        GL40C.nglUniform1dv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform1dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform1dv(int p0, DoubleBuffer p1) {
        GL40C.glUniform1dv(p0, p1);
        LabyDebugContext.glError("glUniform1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform2dv(int p0, int p1, long p2) {
        GL40C.nglUniform2dv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform2dv(int p0, DoubleBuffer p1) {
        GL40C.glUniform2dv(p0, p1);
        LabyDebugContext.glError("glUniform2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform3dv(int p0, int p1, long p2) {
        GL40C.nglUniform3dv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform3dv(int p0, DoubleBuffer p1) {
        GL40C.glUniform3dv(p0, p1);
        LabyDebugContext.glError("glUniform3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform4dv(int p0, int p1, long p2) {
        GL40C.nglUniform4dv(p0, p1, p2);
        LabyDebugContext.glError("nglUniform4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform4dv(int p0, DoubleBuffer p1) {
        GL40C.glUniform4dv(p0, p1);
        LabyDebugContext.glError("glUniform4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniformMatrix2dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix2dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix2dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix3dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix3dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix3dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix4dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix4dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix4dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix2x3dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix2x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix2x3dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix2x3dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix2x4dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix2x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix2x4dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix2x4dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix3x2dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix3x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix3x2dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix3x2dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix3x4dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix3x4dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix3x4dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix3x4dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix4x2dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix4x2dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix4x2dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix4x2dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix4x3dv(int p0, int p1, boolean p2, long p3) {
        GL40C.nglUniformMatrix4x3dv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix4x3dv(int p0, boolean p1, DoubleBuffer p2) {
        GL40C.glUniformMatrix4x3dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglGetUniformdv(int p0, int p1, long p2) {
        GL40C.nglGetUniformdv(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformdv(int p0, int p1, DoubleBuffer p2) {
        GL40C.glGetUniformdv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetUniformd(int p0, int p1) {
        double returnType = GL40C.glGetUniformd(p0, p1);
        LabyDebugContext.glError("glGetUniformd", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glMinSampleShading(float p0) {
        GL40C.glMinSampleShading(p0);
        LabyDebugContext.glError("glMinSampleShading", "p0", Float.valueOf(p0));
    }

    public static int nglGetSubroutineUniformLocation(int p0, int p1, long p2) {
        int returnType = GL40C.nglGetSubroutineUniformLocation(p0, p1, p2);
        LabyDebugContext.glError("nglGetSubroutineUniformLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetSubroutineUniformLocation(int p0, int p1, ByteBuffer p2) {
        int returnType = GL40C.glGetSubroutineUniformLocation(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineUniformLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetSubroutineUniformLocation(int p0, int p1, CharSequence p2) {
        int returnType = GL40C.glGetSubroutineUniformLocation(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineUniformLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int nglGetSubroutineIndex(int p0, int p1, long p2) {
        int returnType = GL40C.nglGetSubroutineIndex(p0, p1, p2);
        LabyDebugContext.glError("nglGetSubroutineIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetSubroutineIndex(int p0, int p1, ByteBuffer p2) {
        int returnType = GL40C.glGetSubroutineIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetSubroutineIndex(int p0, int p1, CharSequence p2) {
        int returnType = GL40C.glGetSubroutineIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void nglGetActiveSubroutineUniformiv(int p0, int p1, int p2, int p3, long p4) {
        GL40C.nglGetActiveSubroutineUniformiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveSubroutineUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveSubroutineUniformiv(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL40C.glGetActiveSubroutineUniformiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static int glGetActiveSubroutineUniformi(int p0, int p1, int p2, int p3) {
        int returnType = GL40C.glGetActiveSubroutineUniformi(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveSubroutineUniformi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static void nglGetActiveSubroutineUniformName(int p0, int p1, int p2, int p3, long p4, long p5) {
        GL40C.nglGetActiveSubroutineUniformName(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetActiveSubroutineUniformName(int p0, int p1, int p2, IntBuffer p3, ByteBuffer p4) {
        GL40C.glGetActiveSubroutineUniformName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static String glGetActiveSubroutineUniformName(int p0, int p1, int p2, int p3) {
        String returnType = GL40C.glGetActiveSubroutineUniformName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static String glGetActiveSubroutineUniformName(int p0, int p1, int p2) {
        String returnType = GL40C.glGetActiveSubroutineUniformName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetActiveSubroutineName(int p0, int p1, int p2, int p3, long p4, long p5) {
        GL40C.nglGetActiveSubroutineName(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetActiveSubroutineName(int p0, int p1, int p2, IntBuffer p3, ByteBuffer p4) {
        GL40C.glGetActiveSubroutineName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static String glGetActiveSubroutineName(int p0, int p1, int p2, int p3) {
        String returnType = GL40C.glGetActiveSubroutineName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static String glGetActiveSubroutineName(int p0, int p1, int p2) {
        String returnType = GL40C.glGetActiveSubroutineName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglUniformSubroutinesuiv(int p0, int p1, long p2) {
        GL40C.nglUniformSubroutinesuiv(p0, p1, p2);
        LabyDebugContext.glError("nglUniformSubroutinesuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniformSubroutinesuiv(int p0, IntBuffer p1) {
        GL40C.glUniformSubroutinesuiv(p0, p1);
        LabyDebugContext.glError("glUniformSubroutinesuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniformSubroutinesui(int p0, int p1) {
        GL40C.glUniformSubroutinesui(p0, p1);
        LabyDebugContext.glError("glUniformSubroutinesui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetUniformSubroutineuiv(int p0, int p1, long p2) {
        GL40C.nglGetUniformSubroutineuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformSubroutineuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformSubroutineuiv(int p0, int p1, IntBuffer p2) {
        GL40C.glGetUniformSubroutineuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformSubroutineuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetUniformSubroutineui(int p0, int p1) {
        int returnType = GL40C.glGetUniformSubroutineui(p0, p1);
        LabyDebugContext.glError("glGetUniformSubroutineui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetProgramStageiv(int p0, int p1, int p2, long p3) {
        GL40C.nglGetProgramStageiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetProgramStageiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetProgramStageiv(int p0, int p1, int p2, IntBuffer p3) {
        GL40C.glGetProgramStageiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramStageiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetProgramStagei(int p0, int p1, int p2) {
        int returnType = GL40C.glGetProgramStagei(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramStagei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glPatchParameteri(int p0, int p1) {
        GL40C.glPatchParameteri(p0, p1);
        LabyDebugContext.glError("glPatchParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglPatchParameterfv(int p0, long p1) {
        GL40C.nglPatchParameterfv(p0, p1);
        LabyDebugContext.glError("nglPatchParameterfv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glPatchParameterfv(int p0, FloatBuffer p1) {
        GL40C.glPatchParameterfv(p0, p1);
        LabyDebugContext.glError("glPatchParameterfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glBindTransformFeedback(int p0, int p1) {
        GL40C.glBindTransformFeedback(p0, p1);
        LabyDebugContext.glError("glBindTransformFeedback", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteTransformFeedbacks(int p0, long p1) {
        GL40C.nglDeleteTransformFeedbacks(p0, p1);
        LabyDebugContext.glError("nglDeleteTransformFeedbacks", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteTransformFeedbacks(IntBuffer p0) {
        GL40C.glDeleteTransformFeedbacks(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacks", "p0", p0);
    }

    public static void glDeleteTransformFeedbacks(int p0) {
        GL40C.glDeleteTransformFeedbacks(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacks", "p0", Integer.valueOf(p0));
    }

    public static void nglGenTransformFeedbacks(int p0, long p1) {
        GL40C.nglGenTransformFeedbacks(p0, p1);
        LabyDebugContext.glError("nglGenTransformFeedbacks", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenTransformFeedbacks(IntBuffer p0) {
        GL40C.glGenTransformFeedbacks(p0);
        LabyDebugContext.glError("glGenTransformFeedbacks", "p0", p0);
    }

    public static int glGenTransformFeedbacks() {
        int returnType = GL40C.glGenTransformFeedbacks();
        LabyDebugContext.glError("glGenTransformFeedbacks", new Object[0]);
        return returnType;
    }

    public static boolean glIsTransformFeedback(int p0) {
        boolean returnType = GL40C.glIsTransformFeedback(p0);
        LabyDebugContext.glError("glIsTransformFeedback", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glPauseTransformFeedback() {
        GL40C.glPauseTransformFeedback();
        LabyDebugContext.glError("glPauseTransformFeedback", new Object[0]);
    }

    public static void glResumeTransformFeedback() {
        GL40C.glResumeTransformFeedback();
        LabyDebugContext.glError("glResumeTransformFeedback", new Object[0]);
    }

    public static void glDrawTransformFeedback(int p0, int p1) {
        GL40C.glDrawTransformFeedback(p0, p1);
        LabyDebugContext.glError("glDrawTransformFeedback", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDrawTransformFeedbackStream(int p0, int p1, int p2) {
        GL40C.glDrawTransformFeedbackStream(p0, p1, p2);
        LabyDebugContext.glError("glDrawTransformFeedbackStream", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glBeginQueryIndexed(int p0, int p1, int p2) {
        GL40C.glBeginQueryIndexed(p0, p1, p2);
        LabyDebugContext.glError("glBeginQueryIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glEndQueryIndexed(int p0, int p1) {
        GL40C.glEndQueryIndexed(p0, p1);
        LabyDebugContext.glError("glEndQueryIndexed", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetQueryIndexediv(int p0, int p1, int p2, long p3) {
        GL40C.nglGetQueryIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetQueryIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetQueryIndexediv(int p0, int p1, int p2, IntBuffer p3) {
        GL40C.glGetQueryIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetQueryIndexedi(int p0, int p1, int p2) {
        int returnType = GL40C.glGetQueryIndexedi(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryIndexedi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glDrawArraysIndirect(int p0, int[] p1) {
        GL40C.glDrawArraysIndirect(p0, p1);
        LabyDebugContext.glError("glDrawArraysIndirect", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawElementsIndirect(int p0, int p1, int[] p2) {
        GL40C.glDrawElementsIndirect(p0, p1, p2);
        LabyDebugContext.glError("glDrawElementsIndirect", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glUniform1dv(int p0, double[] p1) {
        GL40C.glUniform1dv(p0, p1);
        LabyDebugContext.glError("glUniform1dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform2dv(int p0, double[] p1) {
        GL40C.glUniform2dv(p0, p1);
        LabyDebugContext.glError("glUniform2dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform3dv(int p0, double[] p1) {
        GL40C.glUniform3dv(p0, p1);
        LabyDebugContext.glError("glUniform3dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform4dv(int p0, double[] p1) {
        GL40C.glUniform4dv(p0, p1);
        LabyDebugContext.glError("glUniform4dv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniformMatrix2dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix2dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix3dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix3dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix4dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix4dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix2x3dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix2x3dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2x3dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix2x4dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix2x4dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2x4dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix3x2dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix3x2dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3x2dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix3x4dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix3x4dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3x4dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix4x2dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix4x2dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4x2dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix4x3dv(int p0, boolean p1, double[] p2) {
        GL40C.glUniformMatrix4x3dv(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4x3dv", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glGetUniformdv(int p0, int p1, double[] p2) {
        GL40C.glGetUniformdv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetActiveSubroutineUniformiv(int p0, int p1, int p2, int p3, int[] p4) {
        GL40C.glGetActiveSubroutineUniformiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetActiveSubroutineUniformName(int p0, int p1, int p2, int[] p3, ByteBuffer p4) {
        GL40C.glGetActiveSubroutineUniformName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glGetActiveSubroutineName(int p0, int p1, int p2, int[] p3, ByteBuffer p4) {
        GL40C.glGetActiveSubroutineName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glUniformSubroutinesuiv(int p0, int[] p1) {
        GL40C.glUniformSubroutinesuiv(p0, p1);
        LabyDebugContext.glError("glUniformSubroutinesuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetUniformSubroutineuiv(int p0, int p1, int[] p2) {
        GL40C.glGetUniformSubroutineuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformSubroutineuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramStageiv(int p0, int p1, int p2, int[] p3) {
        GL40C.glGetProgramStageiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramStageiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPatchParameterfv(int p0, float[] p1) {
        GL40C.glPatchParameterfv(p0, p1);
        LabyDebugContext.glError("glPatchParameterfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDeleteTransformFeedbacks(int[] p0) {
        GL40C.glDeleteTransformFeedbacks(p0);
        LabyDebugContext.glError("glDeleteTransformFeedbacks", "p0", p0);
    }

    public static void glGenTransformFeedbacks(int[] p0) {
        GL40C.glGenTransformFeedbacks(p0);
        LabyDebugContext.glError("glGenTransformFeedbacks", "p0", p0);
    }

    public static void glGetQueryIndexediv(int p0, int p1, int p2, int[] p3) {
        GL40C.glGetQueryIndexediv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetQueryIndexediv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}
