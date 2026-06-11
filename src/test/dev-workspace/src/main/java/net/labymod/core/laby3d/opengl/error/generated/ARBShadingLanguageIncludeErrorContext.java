package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBShadingLanguageInclude;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBShadingLanguageIncludeErrorContext.class */
public final class ARBShadingLanguageIncludeErrorContext {
    public static void nglNamedStringARB(int p0, int p1, long p2, int p3, long p4) {
        ARBShadingLanguageInclude.nglNamedStringARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglNamedStringARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glNamedStringARB(int p0, ByteBuffer p1, ByteBuffer p2) {
        ARBShadingLanguageInclude.glNamedStringARB(p0, p1, p2);
        LabyDebugContext.glError("glNamedStringARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glNamedStringARB(int p0, CharSequence p1, CharSequence p2) {
        ARBShadingLanguageInclude.glNamedStringARB(p0, p1, p2);
        LabyDebugContext.glError("glNamedStringARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void nglDeleteNamedStringARB(int p0, long p1) {
        ARBShadingLanguageInclude.nglDeleteNamedStringARB(p0, p1);
        LabyDebugContext.glError("nglDeleteNamedStringARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteNamedStringARB(ByteBuffer p0) {
        ARBShadingLanguageInclude.glDeleteNamedStringARB(p0);
        LabyDebugContext.glError("glDeleteNamedStringARB", "p0", p0);
    }

    public static void glDeleteNamedStringARB(CharSequence p0) {
        ARBShadingLanguageInclude.glDeleteNamedStringARB(p0);
        LabyDebugContext.glError("glDeleteNamedStringARB", "p0", p0);
    }

    public static void nglCompileShaderIncludeARB(int p0, int p1, long p2, long p3) {
        ARBShadingLanguageInclude.nglCompileShaderIncludeARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglCompileShaderIncludeARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glCompileShaderIncludeARB(int p0, PointerBuffer p1, IntBuffer p2) {
        ARBShadingLanguageInclude.glCompileShaderIncludeARB(p0, p1, p2);
        LabyDebugContext.glError("glCompileShaderIncludeARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static boolean nglIsNamedStringARB(int p0, long p1) {
        boolean returnType = ARBShadingLanguageInclude.nglIsNamedStringARB(p0, p1);
        LabyDebugContext.glError("nglIsNamedStringARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
        return returnType;
    }

    public static boolean glIsNamedStringARB(ByteBuffer p0) {
        boolean returnType = ARBShadingLanguageInclude.glIsNamedStringARB(p0);
        LabyDebugContext.glError("glIsNamedStringARB", "p0", p0);
        return returnType;
    }

    public static boolean glIsNamedStringARB(CharSequence p0) {
        boolean returnType = ARBShadingLanguageInclude.glIsNamedStringARB(p0);
        LabyDebugContext.glError("glIsNamedStringARB", "p0", p0);
        return returnType;
    }

    public static void nglGetNamedStringARB(int p0, long p1, int p2, long p3, long p4) {
        ARBShadingLanguageInclude.nglGetNamedStringARB(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetNamedStringARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetNamedStringARB(ByteBuffer p0, IntBuffer p1, ByteBuffer p2) {
        ARBShadingLanguageInclude.glGetNamedStringARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringARB", "p0", p0, "p1", p1, "p2", p2);
    }

    public static void glGetNamedStringARB(CharSequence p0, IntBuffer p1, ByteBuffer p2) {
        ARBShadingLanguageInclude.glGetNamedStringARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringARB", "p0", p0, "p1", p1, "p2", p2);
    }

    public static String glGetNamedStringARB(CharSequence p0, int p1) {
        String returnType = ARBShadingLanguageInclude.glGetNamedStringARB(p0, p1);
        LabyDebugContext.glError("glGetNamedStringARB", "p0", p0, "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static String glGetNamedStringARB(CharSequence p0) {
        String returnType = ARBShadingLanguageInclude.glGetNamedStringARB(p0);
        LabyDebugContext.glError("glGetNamedStringARB", "p0", p0);
        return returnType;
    }

    public static void nglGetNamedStringivARB(int p0, long p1, int p2, long p3) {
        ARBShadingLanguageInclude.nglGetNamedStringivARB(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetNamedStringivARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetNamedStringivARB(ByteBuffer p0, int p1, IntBuffer p2) {
        ARBShadingLanguageInclude.glGetNamedStringivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringivARB", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedStringivARB(CharSequence p0, int p1, IntBuffer p2) {
        ARBShadingLanguageInclude.glGetNamedStringivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringivARB", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetNamedStringiARB(CharSequence p0, int p1) {
        int returnType = ARBShadingLanguageInclude.glGetNamedStringiARB(p0, p1);
        LabyDebugContext.glError("glGetNamedStringiARB", "p0", p0, "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glCompileShaderIncludeARB(int p0, PointerBuffer p1, int[] p2) {
        ARBShadingLanguageInclude.glCompileShaderIncludeARB(p0, p1, p2);
        LabyDebugContext.glError("glCompileShaderIncludeARB", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glGetNamedStringARB(ByteBuffer p0, int[] p1, ByteBuffer p2) {
        ARBShadingLanguageInclude.glGetNamedStringARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringARB", "p0", p0, "p1", p1, "p2", p2);
    }

    public static void glGetNamedStringARB(CharSequence p0, int[] p1, ByteBuffer p2) {
        ARBShadingLanguageInclude.glGetNamedStringARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringARB", "p0", p0, "p1", p1, "p2", p2);
    }

    public static void glGetNamedStringivARB(ByteBuffer p0, int p1, int[] p2) {
        ARBShadingLanguageInclude.glGetNamedStringivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringivARB", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetNamedStringivARB(CharSequence p0, int p1, int[] p2) {
        ARBShadingLanguageInclude.glGetNamedStringivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetNamedStringivARB", "p0", p0, "p1", Integer.valueOf(p1), "p2", p2);
    }
}
