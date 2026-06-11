package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBVertexProgram;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBVertexProgramErrorContext.class */
public final class ARBVertexProgramErrorContext {
    public static void glVertexAttrib1sARB(int p0, short p1) {
        ARBVertexProgram.glVertexAttrib1sARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glVertexAttrib1fARB(int p0, float p1) {
        ARBVertexProgram.glVertexAttrib1fARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glVertexAttrib1dARB(int p0, double p1) {
        ARBVertexProgram.glVertexAttrib1dARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glVertexAttrib2sARB(int p0, short p1, short p2) {
        ARBVertexProgram.glVertexAttrib2sARB(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttrib2sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glVertexAttrib2fARB(int p0, float p1, float p2) {
        ARBVertexProgram.glVertexAttrib2fARB(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttrib2fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glVertexAttrib2dARB(int p0, double p1, double p2) {
        ARBVertexProgram.glVertexAttrib2dARB(p0, p1, p2);
        LabyDebugContext.glError("glVertexAttrib2dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glVertexAttrib3sARB(int p0, short p1, short p2, short p3) {
        ARBVertexProgram.glVertexAttrib3sARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttrib3sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glVertexAttrib3fARB(int p0, float p1, float p2, float p3) {
        ARBVertexProgram.glVertexAttrib3fARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttrib3fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glVertexAttrib3dARB(int p0, double p1, double p2, double p3) {
        ARBVertexProgram.glVertexAttrib3dARB(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexAttrib3dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glVertexAttrib4sARB(int p0, short p1, short p2, short p3, short p4) {
        ARBVertexProgram.glVertexAttrib4sARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4sARB", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3), "p4", Short.valueOf(p4));
    }

    public static void glVertexAttrib4fARB(int p0, float p1, float p2, float p3, float p4) {
        ARBVertexProgram.glVertexAttrib4fARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4fARB", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4));
    }

    public static void glVertexAttrib4dARB(int p0, double p1, double p2, double p3, double p4) {
        ARBVertexProgram.glVertexAttrib4dARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4dARB", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4));
    }

    public static void glVertexAttrib4NubARB(int p0, byte p1, byte p2, byte p3, byte p4) {
        ARBVertexProgram.glVertexAttrib4NubARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glVertexAttrib4NubARB", "p0", Integer.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2), "p3", Byte.valueOf(p3), "p4", Byte.valueOf(p4));
    }

    public static void nglVertexAttrib1svARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib1svARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib1svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib1svARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib1svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib1fvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib1fvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib1fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib1fvARB(int p0, FloatBuffer p1) {
        ARBVertexProgram.glVertexAttrib1fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib1dvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib1dvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib1dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib1dvARB(int p0, DoubleBuffer p1) {
        ARBVertexProgram.glVertexAttrib1dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib2svARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib2svARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib2svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib2svARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib2svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib2fvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib2fvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib2fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib2fvARB(int p0, FloatBuffer p1) {
        ARBVertexProgram.glVertexAttrib2fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib2dvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib2dvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib2dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib2dvARB(int p0, DoubleBuffer p1) {
        ARBVertexProgram.glVertexAttrib2dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib3svARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib3svARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib3svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib3svARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib3svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib3fvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib3fvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib3fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib3fvARB(int p0, FloatBuffer p1) {
        ARBVertexProgram.glVertexAttrib3fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib3dvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib3dvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib3dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib3dvARB(int p0, DoubleBuffer p1) {
        ARBVertexProgram.glVertexAttrib3dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4fvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4fvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4fvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4fvARB(int p0, FloatBuffer p1) {
        ARBVertexProgram.glVertexAttrib4fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4bvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4bvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4bvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4bvARB(int p0, ByteBuffer p1) {
        ARBVertexProgram.glVertexAttrib4bvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4bvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4svARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4svARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4svARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4svARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib4svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4ivARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4ivARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4ivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4ivARB(int p0, IntBuffer p1) {
        ARBVertexProgram.glVertexAttrib4ivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4ubvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4ubvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4ubvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4ubvARB(int p0, ByteBuffer p1) {
        ARBVertexProgram.glVertexAttrib4ubvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4ubvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4usvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4usvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4usvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4usvARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib4usvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4usvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4uivARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4uivARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4uivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4uivARB(int p0, IntBuffer p1) {
        ARBVertexProgram.glVertexAttrib4uivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4uivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4dvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4dvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4dvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4dvARB(int p0, DoubleBuffer p1) {
        ARBVertexProgram.glVertexAttrib4dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4NbvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4NbvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4NbvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4NbvARB(int p0, ByteBuffer p1) {
        ARBVertexProgram.glVertexAttrib4NbvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NbvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4NsvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4NsvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4NsvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4NsvARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib4NsvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NsvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4NivARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4NivARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4NivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4NivARB(int p0, IntBuffer p1) {
        ARBVertexProgram.glVertexAttrib4NivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4NubvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4NubvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4NubvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4NubvARB(int p0, ByteBuffer p1) {
        ARBVertexProgram.glVertexAttrib4NubvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NubvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4NusvARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4NusvARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4NusvARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4NusvARB(int p0, ShortBuffer p1) {
        ARBVertexProgram.glVertexAttrib4NusvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NusvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttrib4NuivARB(int p0, long p1) {
        ARBVertexProgram.nglVertexAttrib4NuivARB(p0, p1);
        LabyDebugContext.glError("nglVertexAttrib4NuivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glVertexAttrib4NuivARB(int p0, IntBuffer p1) {
        ARBVertexProgram.glVertexAttrib4NuivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NuivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, long p5) {
        ARBVertexProgram.nglVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, ByteBuffer p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, long p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, ShortBuffer p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, IntBuffer p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, FloatBuffer p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glEnableVertexAttribArrayARB(int p0) {
        ARBVertexProgram.glEnableVertexAttribArrayARB(p0);
        LabyDebugContext.glError("glEnableVertexAttribArrayARB", "p0", Integer.valueOf(p0));
    }

    public static void glDisableVertexAttribArrayARB(int p0) {
        ARBVertexProgram.glDisableVertexAttribArrayARB(p0);
        LabyDebugContext.glError("glDisableVertexAttribArrayARB", "p0", Integer.valueOf(p0));
    }

    public static void nglProgramStringARB(int p0, int p1, int p2, long p3) {
        ARBVertexProgram.nglProgramStringARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglProgramStringARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glProgramStringARB(int p0, int p1, ByteBuffer p2) {
        ARBVertexProgram.glProgramStringARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramStringARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glBindProgramARB(int p0, int p1) {
        ARBVertexProgram.glBindProgramARB(p0, p1);
        LabyDebugContext.glError("glBindProgramARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglDeleteProgramsARB(int p0, long p1) {
        ARBVertexProgram.nglDeleteProgramsARB(p0, p1);
        LabyDebugContext.glError("nglDeleteProgramsARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteProgramsARB(IntBuffer p0) {
        ARBVertexProgram.glDeleteProgramsARB(p0);
        LabyDebugContext.glError("glDeleteProgramsARB", "p0", p0);
    }

    public static void nglGenProgramsARB(int p0, long p1) {
        ARBVertexProgram.nglGenProgramsARB(p0, p1);
        LabyDebugContext.glError("nglGenProgramsARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenProgramsARB(IntBuffer p0) {
        ARBVertexProgram.glGenProgramsARB(p0);
        LabyDebugContext.glError("glGenProgramsARB", "p0", p0);
    }

    public static int glGenProgramsARB() {
        int returnType = ARBVertexProgram.glGenProgramsARB();
        LabyDebugContext.glError("glGenProgramsARB", new Object[0]);
        return returnType;
    }

    public static void glProgramEnvParameter4dARB(int p0, int p1, double p2, double p3, double p4, double p5) {
        ARBVertexProgram.glProgramEnvParameter4dARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramEnvParameter4dARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void nglProgramEnvParameter4dvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglProgramEnvParameter4dvARB(p0, p1, p2);
        LabyDebugContext.glError("nglProgramEnvParameter4dvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glProgramEnvParameter4dvARB(int p0, int p1, DoubleBuffer p2) {
        ARBVertexProgram.glProgramEnvParameter4dvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramEnvParameter4dvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramEnvParameter4fARB(int p0, int p1, float p2, float p3, float p4, float p5) {
        ARBVertexProgram.glProgramEnvParameter4fARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramEnvParameter4fARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5));
    }

    public static void nglProgramEnvParameter4fvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglProgramEnvParameter4fvARB(p0, p1, p2);
        LabyDebugContext.glError("nglProgramEnvParameter4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glProgramEnvParameter4fvARB(int p0, int p1, FloatBuffer p2) {
        ARBVertexProgram.glProgramEnvParameter4fvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramEnvParameter4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramLocalParameter4dARB(int p0, int p1, double p2, double p3, double p4, double p5) {
        ARBVertexProgram.glProgramLocalParameter4dARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramLocalParameter4dARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void nglProgramLocalParameter4dvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglProgramLocalParameter4dvARB(p0, p1, p2);
        LabyDebugContext.glError("nglProgramLocalParameter4dvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glProgramLocalParameter4dvARB(int p0, int p1, DoubleBuffer p2) {
        ARBVertexProgram.glProgramLocalParameter4dvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramLocalParameter4dvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramLocalParameter4fARB(int p0, int p1, float p2, float p3, float p4, float p5) {
        ARBVertexProgram.glProgramLocalParameter4fARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glProgramLocalParameter4fARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5));
    }

    public static void nglProgramLocalParameter4fvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglProgramLocalParameter4fvARB(p0, p1, p2);
        LabyDebugContext.glError("nglProgramLocalParameter4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glProgramLocalParameter4fvARB(int p0, int p1, FloatBuffer p2) {
        ARBVertexProgram.glProgramLocalParameter4fvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramLocalParameter4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetProgramEnvParameterfvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetProgramEnvParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramEnvParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramEnvParameterfvARB(int p0, int p1, FloatBuffer p2) {
        ARBVertexProgram.glGetProgramEnvParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramEnvParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetProgramEnvParameterdvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetProgramEnvParameterdvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramEnvParameterdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramEnvParameterdvARB(int p0, int p1, DoubleBuffer p2) {
        ARBVertexProgram.glGetProgramEnvParameterdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramEnvParameterdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetProgramLocalParameterfvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetProgramLocalParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramLocalParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramLocalParameterfvARB(int p0, int p1, FloatBuffer p2) {
        ARBVertexProgram.glGetProgramLocalParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramLocalParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetProgramLocalParameterdvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetProgramLocalParameterdvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramLocalParameterdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramLocalParameterdvARB(int p0, int p1, DoubleBuffer p2) {
        ARBVertexProgram.glGetProgramLocalParameterdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramLocalParameterdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetProgramivARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetProgramivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramivARB(int p0, int p1, IntBuffer p2) {
        ARBVertexProgram.glGetProgramivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetProgramiARB(int p0, int p1) {
        int returnType = ARBVertexProgram.glGetProgramiARB(p0, p1);
        LabyDebugContext.glError("glGetProgramiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetProgramStringARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetProgramStringARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetProgramStringARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetProgramStringARB(int p0, int p1, ByteBuffer p2) {
        ARBVertexProgram.glGetProgramStringARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramStringARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetVertexAttribfvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetVertexAttribfvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribfvARB(int p0, int p1, FloatBuffer p2) {
        ARBVertexProgram.glGetVertexAttribfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetVertexAttribdvARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetVertexAttribdvARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribdvARB(int p0, int p1, DoubleBuffer p2) {
        ARBVertexProgram.glGetVertexAttribdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetVertexAttribivARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetVertexAttribivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribivARB(int p0, int p1, IntBuffer p2) {
        ARBVertexProgram.glGetVertexAttribivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetVertexAttribiARB(int p0, int p1) {
        int returnType = ARBVertexProgram.glGetVertexAttribiARB(p0, p1);
        LabyDebugContext.glError("glGetVertexAttribiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetVertexAttribPointervARB(int p0, int p1, long p2) {
        ARBVertexProgram.nglGetVertexAttribPointervARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetVertexAttribPointervARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetVertexAttribPointervARB(int p0, int p1, PointerBuffer p2) {
        ARBVertexProgram.glGetVertexAttribPointervARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribPointervARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static long glGetVertexAttribPointerARB(int p0, int p1) {
        long returnType = ARBVertexProgram.glGetVertexAttribPointerARB(p0, p1);
        LabyDebugContext.glError("glGetVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean glIsProgramARB(int p0) {
        boolean returnType = ARBVertexProgram.glIsProgramARB(p0);
        LabyDebugContext.glError("glIsProgramARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glVertexAttrib1svARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib1svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib1fvARB(int p0, float[] p1) {
        ARBVertexProgram.glVertexAttrib1fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib1dvARB(int p0, double[] p1) {
        ARBVertexProgram.glVertexAttrib1dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib1dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib2svARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib2svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib2fvARB(int p0, float[] p1) {
        ARBVertexProgram.glVertexAttrib2fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib2dvARB(int p0, double[] p1) {
        ARBVertexProgram.glVertexAttrib2dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib2dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib3svARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib3svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib3fvARB(int p0, float[] p1) {
        ARBVertexProgram.glVertexAttrib3fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib3dvARB(int p0, double[] p1) {
        ARBVertexProgram.glVertexAttrib3dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib3dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4fvARB(int p0, float[] p1) {
        ARBVertexProgram.glVertexAttrib4fvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4fvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4svARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib4svARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4svARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4ivARB(int p0, int[] p1) {
        ARBVertexProgram.glVertexAttrib4ivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4ivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4usvARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib4usvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4usvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4uivARB(int p0, int[] p1) {
        ARBVertexProgram.glVertexAttrib4uivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4uivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4dvARB(int p0, double[] p1) {
        ARBVertexProgram.glVertexAttrib4dvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4dvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4NsvARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib4NsvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NsvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4NivARB(int p0, int[] p1) {
        ARBVertexProgram.glVertexAttrib4NivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4NusvARB(int p0, short[] p1) {
        ARBVertexProgram.glVertexAttrib4NusvARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NusvARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttrib4NuivARB(int p0, int[] p1) {
        ARBVertexProgram.glVertexAttrib4NuivARB(p0, p1);
        LabyDebugContext.glError("glVertexAttrib4NuivARB", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, short[] p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, int[] p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glVertexAttribPointerARB(int p0, int p1, int p2, boolean p3, int p4, float[] p5) {
        ARBVertexProgram.glVertexAttribPointerARB(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glVertexAttribPointerARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Boolean.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glDeleteProgramsARB(int[] p0) {
        ARBVertexProgram.glDeleteProgramsARB(p0);
        LabyDebugContext.glError("glDeleteProgramsARB", "p0", p0);
    }

    public static void glGenProgramsARB(int[] p0) {
        ARBVertexProgram.glGenProgramsARB(p0);
        LabyDebugContext.glError("glGenProgramsARB", "p0", p0);
    }

    public static void glProgramEnvParameter4dvARB(int p0, int p1, double[] p2) {
        ARBVertexProgram.glProgramEnvParameter4dvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramEnvParameter4dvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramEnvParameter4fvARB(int p0, int p1, float[] p2) {
        ARBVertexProgram.glProgramEnvParameter4fvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramEnvParameter4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramLocalParameter4dvARB(int p0, int p1, double[] p2) {
        ARBVertexProgram.glProgramLocalParameter4dvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramLocalParameter4dvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glProgramLocalParameter4fvARB(int p0, int p1, float[] p2) {
        ARBVertexProgram.glProgramLocalParameter4fvARB(p0, p1, p2);
        LabyDebugContext.glError("glProgramLocalParameter4fvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramEnvParameterfvARB(int p0, int p1, float[] p2) {
        ARBVertexProgram.glGetProgramEnvParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramEnvParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramEnvParameterdvARB(int p0, int p1, double[] p2) {
        ARBVertexProgram.glGetProgramEnvParameterdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramEnvParameterdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramLocalParameterfvARB(int p0, int p1, float[] p2) {
        ARBVertexProgram.glGetProgramLocalParameterfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramLocalParameterfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramLocalParameterdvARB(int p0, int p1, double[] p2) {
        ARBVertexProgram.glGetProgramLocalParameterdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramLocalParameterdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetProgramivARB(int p0, int p1, int[] p2) {
        ARBVertexProgram.glGetProgramivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetProgramivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexAttribfvARB(int p0, int p1, float[] p2) {
        ARBVertexProgram.glGetVertexAttribfvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribfvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexAttribdvARB(int p0, int p1, double[] p2) {
        ARBVertexProgram.glGetVertexAttribdvARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribdvARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetVertexAttribivARB(int p0, int p1, int[] p2) {
        ARBVertexProgram.glGetVertexAttribivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetVertexAttribivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}
