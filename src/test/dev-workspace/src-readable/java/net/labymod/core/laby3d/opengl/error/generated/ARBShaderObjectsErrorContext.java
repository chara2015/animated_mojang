package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBShaderObjects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBShaderObjectsErrorContext.class */
public final class ARBShaderObjectsErrorContext {
    public static void glDeleteObjectARB(int p0) {
        ARBShaderObjects.glDeleteObjectARB(p0);
        LabyDebugContext.glError("glDeleteObjectARB", "p0", Integer.valueOf(p0));
    }

    public static int glGetHandleARB(int p0) {
        int returnType = ARBShaderObjects.glGetHandleARB(p0);
        LabyDebugContext.glError("glGetHandleARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glDetachObjectARB(int p0, int p1) {
        ARBShaderObjects.glDetachObjectARB(p0, p1);
        LabyDebugContext.glError("glDetachObjectARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static int glCreateShaderObjectARB(int p0) {
        int returnType = ARBShaderObjects.glCreateShaderObjectARB(p0);
        LabyDebugContext.glError("glCreateShaderObjectARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglShaderSourceARB(int p0, int p1, long p2, long p3) {
        ARBShaderObjects.nglShaderSourceARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglShaderSourceARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glShaderSourceARB(int p0, PointerBuffer p1, IntBuffer p2) {
        ARBShaderObjects.glShaderSourceARB(p0, p1, p2);
        LabyDebugContext.glError("glShaderSourceARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glShaderSourceARB(int p0, CharSequence[] p1) {
        ARBShaderObjects.glShaderSourceARB(p0, p1);
        LabyDebugContext.glError("glShaderSourceARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glShaderSourceARB(int p0, CharSequence p1) {
        ARBShaderObjects.glShaderSourceARB(p0, p1);
        LabyDebugContext.glError("glShaderSourceARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glCompileShaderARB(int p0) {
        ARBShaderObjects.glCompileShaderARB(p0);
        LabyDebugContext.glError("glCompileShaderARB", "p0", Integer.valueOf(p0));
    }

    public static int glCreateProgramObjectARB() {
        int returnType = ARBShaderObjects.glCreateProgramObjectARB();
        LabyDebugContext.glError("glCreateProgramObjectARB", new Object[0]);
        return returnType;
    }

    public static void glAttachObjectARB(int p0, int p1) {
        ARBShaderObjects.glAttachObjectARB(p0, p1);
        LabyDebugContext.glError("glAttachObjectARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glLinkProgramARB(int p0) {
        ARBShaderObjects.glLinkProgramARB(p0);
        LabyDebugContext.glError("glLinkProgramARB", "p0", Integer.valueOf(p0));
    }

    public static void glUseProgramObjectARB(int p0) {
        ARBShaderObjects.glUseProgramObjectARB(p0);
        LabyDebugContext.glError("glUseProgramObjectARB", "p0", Integer.valueOf(p0));
    }

    public static void glValidateProgramARB(int p0) {
        ARBShaderObjects.glValidateProgramARB(p0);
        LabyDebugContext.glError("glValidateProgramARB", "p0", Integer.valueOf(p0));
    }

    public static void glUniform1fARB(int p0, float p1) {
        ARBShaderObjects.glUniform1fARB(p0, p1);
        LabyDebugContext.glError("glUniform1fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glUniform2fARB(int p0, float p1, float p2) {
        ARBShaderObjects.glUniform2fARB(p0, p1, p2);
        LabyDebugContext.glError("glUniform2fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glUniform3fARB(int p0, float p1, float p2, float p3) {
        ARBShaderObjects.glUniform3fARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glUniform3fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glUniform4fARB(int p0, float p1, float p2, float p3, float p4) {
        ARBShaderObjects.glUniform4fARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glUniform4fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glUniform1iARB(int p0, int p1) {
        ARBShaderObjects.glUniform1iARB(p0, p1);
        LabyDebugContext.glError("glUniform1iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glUniform2iARB(int p0, int p1, int p2) {
        ARBShaderObjects.glUniform2iARB(p0, p1, p2);
        LabyDebugContext.glError("glUniform2iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glUniform3iARB(int p0, int p1, int p2, int p3) {
        ARBShaderObjects.glUniform3iARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glUniform3iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glUniform4iARB(int p0, int p1, int p2, int p3, int p4) {
        ARBShaderObjects.glUniform4iARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glUniform4iARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void nglUniform1fvARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform1fvARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform1fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform1fvARB(int p0, FloatBuffer p1) {
        ARBShaderObjects.glUniform1fvARB(p0, p1);
        LabyDebugContext.glError("glUniform1fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform2fvARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform2fvARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform2fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform2fvARB(int p0, FloatBuffer p1) {
        ARBShaderObjects.glUniform2fvARB(p0, p1);
        LabyDebugContext.glError("glUniform2fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform3fvARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform3fvARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform3fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform3fvARB(int p0, FloatBuffer p1) {
        ARBShaderObjects.glUniform3fvARB(p0, p1);
        LabyDebugContext.glError("glUniform3fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform4fvARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform4fvARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform4fvARB(int p0, FloatBuffer p1) {
        ARBShaderObjects.glUniform4fvARB(p0, p1);
        LabyDebugContext.glError("glUniform4fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform1ivARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform1ivARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform1ivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform1ivARB(int p0, IntBuffer p1) {
        ARBShaderObjects.glUniform1ivARB(p0, p1);
        LabyDebugContext.glError("glUniform1ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform2ivARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform2ivARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform2ivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform2ivARB(int p0, IntBuffer p1) {
        ARBShaderObjects.glUniform2ivARB(p0, p1);
        LabyDebugContext.glError("glUniform2ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform3ivARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform3ivARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform3ivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform3ivARB(int p0, IntBuffer p1) {
        ARBShaderObjects.glUniform3ivARB(p0, p1);
        LabyDebugContext.glError("glUniform3ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniform4ivARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglUniform4ivARB(p0, p1, p2);
        LabyDebugContext.glError("nglUniform4ivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glUniform4ivARB(int p0, IntBuffer p1) {
        ARBShaderObjects.glUniform4ivARB(p0, p1);
        LabyDebugContext.glError("glUniform4ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglUniformMatrix2fvARB(int p0, int p1, boolean p2, long p3) {
        ARBShaderObjects.nglUniformMatrix2fvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix2fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix2fvARB(int p0, boolean p1, FloatBuffer p2) {
        ARBShaderObjects.glUniformMatrix2fvARB(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2fvARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix3fvARB(int p0, int p1, boolean p2, long p3) {
        ARBShaderObjects.nglUniformMatrix3fvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix3fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix3fvARB(int p0, boolean p1, FloatBuffer p2) {
        ARBShaderObjects.glUniformMatrix3fvARB(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3fvARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglUniformMatrix4fvARB(int p0, int p1, boolean p2, long p3) {
        ARBShaderObjects.nglUniformMatrix4fvARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglUniformMatrix4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glUniformMatrix4fvARB(int p0, boolean p1, FloatBuffer p2) {
        ARBShaderObjects.glUniformMatrix4fvARB(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4fvARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void nglGetObjectParameterfvARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglGetObjectParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetObjectParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetObjectParameterfvARB(int p0, int p1, FloatBuffer p2) {
        ARBShaderObjects.glGetObjectParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetObjectParameterivARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglGetObjectParameterivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetObjectParameterivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetObjectParameterivARB(int p0, int p1, IntBuffer p2) {
        ARBShaderObjects.glGetObjectParameterivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectParameterivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetObjectParameteriARB(int p0, int p1) {
        int returnType = ARBShaderObjects.glGetObjectParameteriARB(p0, p1);
        LabyDebugContext.glError("glGetObjectParameteriARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetInfoLogARB(int p0, int p1, long p2, long p3) {
        ARBShaderObjects.nglGetInfoLogARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetInfoLogARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetInfoLogARB(int p0, IntBuffer p1, ByteBuffer p2) {
        ARBShaderObjects.glGetInfoLogARB(p0, p1, p2);
        LabyDebugContext.glError("glGetInfoLogARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetInfoLogARB(int p0, int p1) {
        String returnType = ARBShaderObjects.glGetInfoLogARB(p0, p1);
        LabyDebugContext.glError("glGetInfoLogARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetInfoLogARB(int p0) {
        String returnType = ARBShaderObjects.glGetInfoLogARB(p0);
        LabyDebugContext.glError("glGetInfoLogARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetAttachedObjectsARB(int p0, int p1, long p2, long p3) {
        ARBShaderObjects.nglGetAttachedObjectsARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetAttachedObjectsARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetAttachedObjectsARB(int p0, IntBuffer p1, IntBuffer p2) {
        ARBShaderObjects.glGetAttachedObjectsARB(p0, p1, p2);
        LabyDebugContext.glError("glGetAttachedObjectsARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static int nglGetUniformLocationARB(int p0, long p1) {
        int returnType = ARBShaderObjects.nglGetUniformLocationARB(p0, p1);
        LabyDebugContext.glError("nglGetUniformLocationARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static int glGetUniformLocationARB(int p0, ByteBuffer p1) {
        int returnType = ARBShaderObjects.glGetUniformLocationARB(p0, p1);
        LabyDebugContext.glError("glGetUniformLocationARB", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static int glGetUniformLocationARB(int p0, CharSequence p1) {
        int returnType = ARBShaderObjects.glGetUniformLocationARB(p0, p1);
        LabyDebugContext.glError("glGetUniformLocationARB", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void nglGetActiveUniformARB(int p0, int p1, int p2, long p3, long p4, long p5, long p6) {
        ARBShaderObjects.nglGetActiveUniformARB(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetActiveUniformARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetActiveUniformARB(int p0, int p1, IntBuffer p2, IntBuffer p3, IntBuffer p4, ByteBuffer p5) {
        ARBShaderObjects.glGetActiveUniformARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveUniformARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static String glGetActiveUniformARB(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4) {
        String returnType = ARBShaderObjects.glGetActiveUniformARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetActiveUniformARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4);
        return returnType;
    }

    public static String glGetActiveUniformARB(int p0, int p1, IntBuffer p2, IntBuffer p3) {
        String returnType = ARBShaderObjects.glGetActiveUniformARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetActiveUniformARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3);
        return returnType;
    }

    public static void nglGetUniformfvARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglGetUniformfvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformfvARB(int p0, int p1, FloatBuffer p2) {
        ARBShaderObjects.glGetUniformfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetUniformfARB(int p0, int p1) {
        float returnType = ARBShaderObjects.glGetUniformfARB(p0, p1);
        LabyDebugContext.glError("glGetUniformfARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetUniformivARB(int p0, int p1, long p2) {
        ARBShaderObjects.nglGetUniformivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetUniformivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetUniformivARB(int p0, int p1, IntBuffer p2) {
        ARBShaderObjects.glGetUniformivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetUniformiARB(int p0, int p1) {
        int returnType = ARBShaderObjects.glGetUniformiARB(p0, p1);
        LabyDebugContext.glError("glGetUniformiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetShaderSourceARB(int p0, int p1, long p2, long p3) {
        ARBShaderObjects.nglGetShaderSourceARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetShaderSourceARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetShaderSourceARB(int p0, IntBuffer p1, ByteBuffer p2) {
        ARBShaderObjects.glGetShaderSourceARB(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderSourceARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static String glGetShaderSourceARB(int p0, int p1) {
        String returnType = ARBShaderObjects.glGetShaderSourceARB(p0, p1);
        LabyDebugContext.glError("glGetShaderSourceARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetShaderSourceARB(int p0) {
        String returnType = ARBShaderObjects.glGetShaderSourceARB(p0);
        LabyDebugContext.glError("glGetShaderSourceARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glShaderSourceARB(int p0, PointerBuffer p1, int[] p2) {
        ARBShaderObjects.glShaderSourceARB(p0, p1, p2);
        LabyDebugContext.glError("glShaderSourceARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glUniform1fvARB(int p0, float[] p1) {
        ARBShaderObjects.glUniform1fvARB(p0, p1);
        LabyDebugContext.glError("glUniform1fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform2fvARB(int p0, float[] p1) {
        ARBShaderObjects.glUniform2fvARB(p0, p1);
        LabyDebugContext.glError("glUniform2fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform3fvARB(int p0, float[] p1) {
        ARBShaderObjects.glUniform3fvARB(p0, p1);
        LabyDebugContext.glError("glUniform3fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform4fvARB(int p0, float[] p1) {
        ARBShaderObjects.glUniform4fvARB(p0, p1);
        LabyDebugContext.glError("glUniform4fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform1ivARB(int p0, int[] p1) {
        ARBShaderObjects.glUniform1ivARB(p0, p1);
        LabyDebugContext.glError("glUniform1ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform2ivARB(int p0, int[] p1) {
        ARBShaderObjects.glUniform2ivARB(p0, p1);
        LabyDebugContext.glError("glUniform2ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform3ivARB(int p0, int[] p1) {
        ARBShaderObjects.glUniform3ivARB(p0, p1);
        LabyDebugContext.glError("glUniform3ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniform4ivARB(int p0, int[] p1) {
        ARBShaderObjects.glUniform4ivARB(p0, p1);
        LabyDebugContext.glError("glUniform4ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glUniformMatrix2fvARB(int p0, boolean p1, float[] p2) {
        ARBShaderObjects.glUniformMatrix2fvARB(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix2fvARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix3fvARB(int p0, boolean p1, float[] p2) {
        ARBShaderObjects.glUniformMatrix3fvARB(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix3fvARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glUniformMatrix4fvARB(int p0, boolean p1, float[] p2) {
        ARBShaderObjects.glUniformMatrix4fvARB(p0, p1, p2);
        LabyDebugContext.glError("glUniformMatrix4fvARB", "p0", Integer.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", p2);
    }

    public static void glGetObjectParameterfvARB(int p0, int p1, float[] p2) {
        ARBShaderObjects.glGetObjectParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetObjectParameterivARB(int p0, int p1, int[] p2) {
        ARBShaderObjects.glGetObjectParameterivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetObjectParameterivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetInfoLogARB(int p0, int[] p1, ByteBuffer p2) {
        ARBShaderObjects.glGetInfoLogARB(p0, p1, p2);
        LabyDebugContext.glError("glGetInfoLogARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetAttachedObjectsARB(int p0, int[] p1, int[] p2) {
        ARBShaderObjects.glGetAttachedObjectsARB(p0, p1, p2);
        LabyDebugContext.glError("glGetAttachedObjectsARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetActiveUniformARB(int p0, int p1, int[] p2, int[] p3, int[] p4, ByteBuffer p5) {
        ARBShaderObjects.glGetActiveUniformARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetActiveUniformARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glGetUniformfvARB(int p0, int p1, float[] p2) {
        ARBShaderObjects.glGetUniformfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetUniformivARB(int p0, int p1, int[] p2) {
        ARBShaderObjects.glGetUniformivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetUniformivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetShaderSourceARB(int p0, int[] p1, ByteBuffer p2) {
        ARBShaderObjects.glGetShaderSourceARB(p0, p1, p2);
        LabyDebugContext.glError("glGetShaderSourceARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }
}
