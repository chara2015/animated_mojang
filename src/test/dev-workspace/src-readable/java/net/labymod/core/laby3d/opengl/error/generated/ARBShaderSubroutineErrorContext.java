package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBShaderSubroutine;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBShaderSubroutineErrorContext.class */
public final class ARBShaderSubroutineErrorContext {
    public static int nglGetSubroutineUniformLocation(int p0, int p1, long p2) {
        int returnType = ARBShaderSubroutine.nglGetSubroutineUniformLocation(p0, p1, p2);
        LabyDebugContext.glError("nglGetSubroutineUniformLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetSubroutineUniformLocation(int p0, int p1, ByteBuffer p2) {
        int returnType = ARBShaderSubroutine.glGetSubroutineUniformLocation(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineUniformLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetSubroutineUniformLocation(int p0, int p1, CharSequence p2) {
        int returnType = ARBShaderSubroutine.glGetSubroutineUniformLocation(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineUniformLocation", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int nglGetSubroutineIndex(int p0, int p1, long p2) {
        int returnType = ARBShaderSubroutine.nglGetSubroutineIndex(p0, p1, p2);
        LabyDebugContext.glError("nglGetSubroutineIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static int glGetSubroutineIndex(int p0, int p1, ByteBuffer p2) {
        int returnType = ARBShaderSubroutine.glGetSubroutineIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static int glGetSubroutineIndex(int p0, int p1, CharSequence p2) {
        int returnType = ARBShaderSubroutine.glGetSubroutineIndex(p0, p1, p2);
        LabyDebugContext.glError("glGetSubroutineIndex", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
        return returnType;
    }

    public static void nglGetActiveSubroutineUniformiv(int p0, int p1, int p2, int p3, long p4) {
        ARBShaderSubroutine.nglGetActiveSubroutineUniformiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetActiveSubroutineUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetActiveSubroutineUniformiv(int p0, int p1, int p2, int p3, IntBuffer p4) {
        ARBShaderSubroutine.glGetActiveSubroutineUniformiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static int glGetActiveSubroutineUniformi(int p0, int p1, int p2, int p3) {
        int returnType = ARBShaderSubroutine.glGetActiveSubroutineUniformi(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveSubroutineUniformi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static void nglGetActiveSubroutineUniformName(int p0, int p1, int p2, int p3, long p4, long p5) {
        ARBShaderSubroutine.nglGetActiveSubroutineUniformName(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetActiveSubroutineUniformName(int p0, int p1, int p2, IntBuffer p3, ByteBuffer p4) {
        ARBShaderSubroutine.glGetActiveSubroutineUniformName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static String glGetActiveSubroutineUniformName(int p0, int p1, int p2, int p3) {
        String returnType = ARBShaderSubroutine.glGetActiveSubroutineUniformName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static String glGetActiveSubroutineUniformName(int p0, int p1, int p2) {
        String returnType = ARBShaderSubroutine.glGetActiveSubroutineUniformName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetActiveSubroutineName(int p0, int p1, int p2, int p3, long p4, long p5) {
        ARBShaderSubroutine.nglGetActiveSubroutineName(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glGetActiveSubroutineName(int p0, int p1, int p2, IntBuffer p3, ByteBuffer p4) {
        ARBShaderSubroutine.glGetActiveSubroutineName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static String glGetActiveSubroutineName(int p0, int p1, int p2, int p3) {
        String returnType = ARBShaderSubroutine.glGetActiveSubroutineName(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
        return returnType;
    }

    public static String glGetActiveSubroutineName(int p0, int p1, int p2) {
        String returnType = ARBShaderSubroutine.glGetActiveSubroutineName(p0, p1, p2);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglUniformSubroutinesuiv(int p0, int p1, long p2) {
        ARBShaderSubroutine.nglUniformSubroutinesuiv(p0, p1, p2);
        LabyDebugContext.glError("nglUniformSubroutinesuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniformSubroutinesuiv(int p0, IntBuffer p1) {
        ARBShaderSubroutine.glUniformSubroutinesuiv(p0, p1);
        LabyDebugContext.glError("glUniformSubroutinesuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniformSubroutinesui(int p0, int p1) {
        ARBShaderSubroutine.glUniformSubroutinesui(p0, p1);
        LabyDebugContext.glError("glUniformSubroutinesui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglGetUniformSubroutineuiv(int p0, int p1, long p2) {
        ARBShaderSubroutine.nglGetUniformSubroutineuiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformSubroutineuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformSubroutineuiv(int p0, int p1, IntBuffer p2) {
        ARBShaderSubroutine.glGetUniformSubroutineuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformSubroutineuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetUniformSubroutineui(int p0, int p1) {
        int returnType = ARBShaderSubroutine.glGetUniformSubroutineui(p0, p1);
        LabyDebugContext.glError("glGetUniformSubroutineui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetProgramStageiv(int p0, int p1, int p2, long p3) {
        ARBShaderSubroutine.nglGetProgramStageiv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetProgramStageiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetProgramStageiv(int p0, int p1, int p2, IntBuffer p3) {
        ARBShaderSubroutine.glGetProgramStageiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramStageiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetProgramStagei(int p0, int p1, int p2) {
        int returnType = ARBShaderSubroutine.glGetProgramStagei(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramStagei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void glGetActiveSubroutineUniformiv(int p0, int p1, int p2, int p3, int[] p4) {
        ARBShaderSubroutine.glGetActiveSubroutineUniformiv(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetActiveSubroutineUniformName(int p0, int p1, int p2, int[] p3, ByteBuffer p4) {
        ARBShaderSubroutine.glGetActiveSubroutineUniformName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineUniformName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glGetActiveSubroutineName(int p0, int p1, int p2, int[] p3, ByteBuffer p4) {
        ARBShaderSubroutine.glGetActiveSubroutineName(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveSubroutineName", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
    }

    public static void glUniformSubroutinesuiv(int p0, int[] p1) {
        ARBShaderSubroutine.glUniformSubroutinesuiv(p0, p1);
        LabyDebugContext.glError("glUniformSubroutinesuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetUniformSubroutineuiv(int p0, int p1, int[] p2) {
        ARBShaderSubroutine.glGetUniformSubroutineuiv(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformSubroutineuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramStageiv(int p0, int p1, int p2, int[] p3) {
        ARBShaderSubroutine.glGetProgramStageiv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetProgramStageiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }
}
