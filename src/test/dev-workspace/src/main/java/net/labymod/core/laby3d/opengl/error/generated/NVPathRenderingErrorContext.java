package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.NVPathRendering;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/NVPathRenderingErrorContext.class */
public final class NVPathRenderingErrorContext {
    public static void nglPathCommandsNV(int p0, int p1, long p2, int p3, int p4, long p5) {
        NVPathRendering.nglPathCommandsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglPathCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glPathCommandsNV(int p0, ByteBuffer p1, int p2, ByteBuffer p3) {
        NVPathRendering.glPathCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathCommandsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathCommandsNV(int p0, ByteBuffer p1, int p2, ShortBuffer p3) {
        NVPathRendering.glPathCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathCommandsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathCommandsNV(int p0, ByteBuffer p1, int p2, FloatBuffer p3) {
        NVPathRendering.glPathCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathCommandsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglPathCoordsNV(int p0, int p1, int p2, long p3) {
        NVPathRendering.nglPathCoordsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glPathCoordsNV(int p0, int p1, ByteBuffer p2) {
        NVPathRendering.glPathCoordsNV(p0, p1, p2);
        LabyDebugContext.glError("glPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathCoordsNV(int p0, int p1, ShortBuffer p2) {
        NVPathRendering.glPathCoordsNV(p0, p1, p2);
        LabyDebugContext.glError("glPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathCoordsNV(int p0, int p1, FloatBuffer p2) {
        NVPathRendering.glPathCoordsNV(p0, p1, p2);
        LabyDebugContext.glError("glPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglPathSubCommandsNV(int p0, int p1, int p2, int p3, long p4, int p5, int p6, long p7) {
        NVPathRendering.nglPathSubCommandsNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglPathSubCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glPathSubCommandsNV(int p0, int p1, int p2, ByteBuffer p3, int p4, ByteBuffer p5) {
        NVPathRendering.glPathSubCommandsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathSubCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glPathSubCommandsNV(int p0, int p1, int p2, ByteBuffer p3, int p4, ShortBuffer p5) {
        NVPathRendering.glPathSubCommandsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathSubCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glPathSubCommandsNV(int p0, int p1, int p2, ByteBuffer p3, int p4, FloatBuffer p5) {
        NVPathRendering.glPathSubCommandsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathSubCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglPathSubCoordsNV(int p0, int p1, int p2, int p3, long p4) {
        NVPathRendering.nglPathSubCoordsNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglPathSubCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glPathSubCoordsNV(int p0, int p1, int p2, ByteBuffer p3) {
        NVPathRendering.glPathSubCoordsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathSubCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathSubCoordsNV(int p0, int p1, int p2, ShortBuffer p3) {
        NVPathRendering.glPathSubCoordsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathSubCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathSubCoordsNV(int p0, int p1, int p2, FloatBuffer p3) {
        NVPathRendering.glPathSubCoordsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathSubCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglPathStringNV(int p0, int p1, int p2, long p3) {
        NVPathRendering.nglPathStringNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglPathStringNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glPathStringNV(int p0, int p1, ByteBuffer p2) {
        NVPathRendering.glPathStringNV(p0, p1, p2);
        LabyDebugContext.glError("glPathStringNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglPathGlyphsNV(int p0, int p1, long p2, int p3, int p4, int p5, long p6, int p7, int p8, float p9) {
        NVPathRendering.nglPathGlyphsNV(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("nglPathGlyphsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Float.valueOf(p9));
    }

    public static void glPathGlyphsNV(int p0, int p1, ByteBuffer p2, int p3, int p4, ByteBuffer p5, int p6, int p7, float p8) {
        NVPathRendering.glPathGlyphsNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glPathGlyphsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5, "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Float.valueOf(p8));
    }

    public static void nglPathGlyphRangeNV(int p0, int p1, long p2, int p3, int p4, int p5, int p6, int p7, float p8) {
        NVPathRendering.nglPathGlyphRangeNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglPathGlyphRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Float.valueOf(p8));
    }

    public static void glPathGlyphRangeNV(int p0, int p1, ByteBuffer p2, int p3, int p4, int p5, int p6, int p7, float p8) {
        NVPathRendering.glPathGlyphRangeNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glPathGlyphRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Float.valueOf(p8));
    }

    public static int nglPathGlyphIndexArrayNV(int p0, int p1, long p2, int p3, int p4, int p5, int p6, float p7) {
        int returnType = NVPathRendering.nglPathGlyphIndexArrayNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglPathGlyphIndexArrayNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Float.valueOf(p7));
        return returnType;
    }

    public static int glPathGlyphIndexArrayNV(int p0, int p1, ByteBuffer p2, int p3, int p4, int p5, int p6, float p7) {
        int returnType = NVPathRendering.glPathGlyphIndexArrayNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glPathGlyphIndexArrayNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Float.valueOf(p7));
        return returnType;
    }

    public static int nglPathMemoryGlyphIndexArrayNV(int p0, int p1, long p2, long p3, int p4, int p5, int p6, int p7, float p8) {
        int returnType = NVPathRendering.nglPathMemoryGlyphIndexArrayNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglPathMemoryGlyphIndexArrayNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Float.valueOf(p8));
        return returnType;
    }

    public static int glPathMemoryGlyphIndexArrayNV(int p0, int p1, ByteBuffer p2, int p3, int p4, int p5, int p6, float p7) {
        int returnType = NVPathRendering.glPathMemoryGlyphIndexArrayNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glPathMemoryGlyphIndexArrayNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Float.valueOf(p7));
        return returnType;
    }

    public static void glCopyPathNV(int p0, int p1) {
        NVPathRendering.glCopyPathNV(p0, p1);
        LabyDebugContext.glError("glCopyPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglWeightPathsNV(int p0, int p1, long p2, long p3) {
        NVPathRendering.nglWeightPathsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglWeightPathsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glWeightPathsNV(int p0, IntBuffer p1, FloatBuffer p2) {
        NVPathRendering.glWeightPathsNV(p0, p1, p2);
        LabyDebugContext.glError("glWeightPathsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glInterpolatePathsNV(int p0, int p1, int p2, float p3) {
        NVPathRendering.glInterpolatePathsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glInterpolatePathsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void nglTransformPathNV(int p0, int p1, int p2, long p3) {
        NVPathRendering.nglTransformPathNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglTransformPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glTransformPathNV(int p0, int p1, int p2, FloatBuffer p3) {
        NVPathRendering.glTransformPathNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glTransformPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglPathParameterivNV(int p0, int p1, long p2) {
        NVPathRendering.nglPathParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("nglPathParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPathParameterivNV(int p0, int p1, IntBuffer p2) {
        NVPathRendering.glPathParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glPathParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathParameteriNV(int p0, int p1, int p2) {
        NVPathRendering.glPathParameteriNV(p0, p1, p2);
        LabyDebugContext.glError("glPathParameteriNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglPathParameterfvNV(int p0, int p1, long p2) {
        NVPathRendering.nglPathParameterfvNV(p0, p1, p2);
        LabyDebugContext.glError("nglPathParameterfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPathParameterfvNV(int p0, int p1, FloatBuffer p2) {
        NVPathRendering.glPathParameterfvNV(p0, p1, p2);
        LabyDebugContext.glError("glPathParameterfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathParameterfNV(int p0, int p1, float p2) {
        NVPathRendering.glPathParameterfNV(p0, p1, p2);
        LabyDebugContext.glError("glPathParameterfNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglPathDashArrayNV(int p0, int p1, long p2) {
        NVPathRendering.nglPathDashArrayNV(p0, p1, p2);
        LabyDebugContext.glError("nglPathDashArrayNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPathDashArrayNV(int p0, FloatBuffer p1) {
        NVPathRendering.glPathDashArrayNV(p0, p1);
        LabyDebugContext.glError("glPathDashArrayNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glGenPathsNV(int p0) {
        int returnType = NVPathRendering.glGenPathsNV(p0);
        LabyDebugContext.glError("glGenPathsNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glDeletePathsNV(int p0, int p1) {
        NVPathRendering.glDeletePathsNV(p0, p1);
        LabyDebugContext.glError("glDeletePathsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static boolean glIsPathNV(int p0) {
        boolean returnType = NVPathRendering.glIsPathNV(p0);
        LabyDebugContext.glError("glIsPathNV", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glPathStencilFuncNV(int p0, int p1, int p2) {
        NVPathRendering.glPathStencilFuncNV(p0, p1, p2);
        LabyDebugContext.glError("glPathStencilFuncNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glPathStencilDepthOffsetNV(float p0, float p1) {
        NVPathRendering.glPathStencilDepthOffsetNV(p0, p1);
        LabyDebugContext.glError("glPathStencilDepthOffsetNV", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glStencilFillPathNV(int p0, int p1, int p2) {
        NVPathRendering.glStencilFillPathNV(p0, p1, p2);
        LabyDebugContext.glError("glStencilFillPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glStencilStrokePathNV(int p0, int p1, int p2) {
        NVPathRendering.glStencilStrokePathNV(p0, p1, p2);
        LabyDebugContext.glError("glStencilStrokePathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglStencilFillPathInstancedNV(int p0, int p1, long p2, int p3, int p4, int p5, int p6, long p7) {
        NVPathRendering.nglStencilFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglStencilFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glStencilFillPathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        NVPathRendering.glStencilFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glStencilFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglStencilStrokePathInstancedNV(int p0, int p1, long p2, int p3, int p4, int p5, int p6, long p7) {
        NVPathRendering.nglStencilStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglStencilStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glStencilStrokePathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        NVPathRendering.glStencilStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glStencilStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glPathCoverDepthFuncNV(int p0) {
        NVPathRendering.glPathCoverDepthFuncNV(p0);
        LabyDebugContext.glError("glPathCoverDepthFuncNV", "p0", Integer.valueOf(p0));
    }

    public static void nglPathColorGenNV(int p0, int p1, int p2, long p3) {
        NVPathRendering.nglPathColorGenNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglPathColorGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glPathColorGenNV(int p0, int p1, int p2, FloatBuffer p3) {
        NVPathRendering.glPathColorGenNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathColorGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void nglPathTexGenNV(int p0, int p1, int p2, long p3) {
        NVPathRendering.nglPathTexGenNV(p0, p1, p2, p3);
        LabyDebugContext.glError("nglPathTexGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glPathTexGenNV(int p0, int p1, int p2, FloatBuffer p3) {
        NVPathRendering.glPathTexGenNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathTexGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathFogGenNV(int p0) {
        NVPathRendering.glPathFogGenNV(p0);
        LabyDebugContext.glError("glPathFogGenNV", "p0", Integer.valueOf(p0));
    }

    public static void glCoverFillPathNV(int p0, int p1) {
        NVPathRendering.glCoverFillPathNV(p0, p1);
        LabyDebugContext.glError("glCoverFillPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glCoverStrokePathNV(int p0, int p1) {
        NVPathRendering.glCoverStrokePathNV(p0, p1);
        LabyDebugContext.glError("glCoverStrokePathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglCoverFillPathInstancedNV(int p0, int p1, long p2, int p3, int p4, int p5, long p6) {
        NVPathRendering.nglCoverFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglCoverFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCoverFillPathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, FloatBuffer p5) {
        NVPathRendering.glCoverFillPathInstancedNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCoverFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglCoverStrokePathInstancedNV(int p0, int p1, long p2, int p3, int p4, int p5, long p6) {
        NVPathRendering.nglCoverStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglCoverStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glCoverStrokePathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, FloatBuffer p5) {
        NVPathRendering.glCoverStrokePathInstancedNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCoverStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glStencilThenCoverFillPathNV(int p0, int p1, int p2, int p3) {
        NVPathRendering.glStencilThenCoverFillPathNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glStencilThenCoverFillPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glStencilThenCoverStrokePathNV(int p0, int p1, int p2, int p3) {
        NVPathRendering.glStencilThenCoverStrokePathNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glStencilThenCoverStrokePathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglStencilThenCoverFillPathInstancedNV(int p0, int p1, long p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        NVPathRendering.nglStencilThenCoverFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglStencilThenCoverFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glStencilThenCoverFillPathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, int p6, FloatBuffer p7) {
        NVPathRendering.glStencilThenCoverFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glStencilThenCoverFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglStencilThenCoverStrokePathInstancedNV(int p0, int p1, long p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        NVPathRendering.nglStencilThenCoverStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglStencilThenCoverStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glStencilThenCoverStrokePathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, int p6, FloatBuffer p7) {
        NVPathRendering.glStencilThenCoverStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glStencilThenCoverStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static int nglPathGlyphIndexRangeNV(int p0, long p1, int p2, int p3, float p4, long p5) {
        int returnType = NVPathRendering.nglPathGlyphIndexRangeNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglPathGlyphIndexRangeNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Float.valueOf(p4), "p5", Long.valueOf(p5));
        return returnType;
    }

    public static int glPathGlyphIndexRangeNV(int p0, ByteBuffer p1, int p2, int p3, float p4, IntBuffer p5) {
        int returnType = NVPathRendering.glPathGlyphIndexRangeNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathGlyphIndexRangeNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Float.valueOf(p4), "p5", p5);
        return returnType;
    }

    public static void nglProgramPathFragmentInputGenNV(int p0, int p1, int p2, int p3, long p4) {
        NVPathRendering.nglProgramPathFragmentInputGenNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglProgramPathFragmentInputGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glProgramPathFragmentInputGenNV(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        NVPathRendering.glProgramPathFragmentInputGenNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramPathFragmentInputGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetPathParameterivNV(int p0, int p1, long p2) {
        NVPathRendering.nglGetPathParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetPathParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPathParameterivNV(int p0, int p1, IntBuffer p2) {
        NVPathRendering.glGetPathParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetPathParameteriNV(int p0, int p1) {
        int returnType = NVPathRendering.glGetPathParameteriNV(p0, p1);
        LabyDebugContext.glError("glGetPathParameteriNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetPathParameterfvNV(int p0, int p1, long p2) {
        NVPathRendering.nglGetPathParameterfvNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetPathParameterfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPathParameterfvNV(int p0, int p1, FloatBuffer p2) {
        NVPathRendering.glGetPathParameterfvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathParameterfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetPathParameterfNV(int p0, int p1) {
        float returnType = NVPathRendering.glGetPathParameterfNV(p0, p1);
        LabyDebugContext.glError("glGetPathParameterfNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetPathCommandsNV(int p0, long p1) {
        NVPathRendering.nglGetPathCommandsNV(p0, p1);
        LabyDebugContext.glError("nglGetPathCommandsNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPathCommandsNV(int p0, ByteBuffer p1) {
        NVPathRendering.glGetPathCommandsNV(p0, p1);
        LabyDebugContext.glError("glGetPathCommandsNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetPathCoordsNV(int p0, long p1) {
        NVPathRendering.nglGetPathCoordsNV(p0, p1);
        LabyDebugContext.glError("nglGetPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPathCoordsNV(int p0, FloatBuffer p1) {
        NVPathRendering.glGetPathCoordsNV(p0, p1);
        LabyDebugContext.glError("glGetPathCoordsNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetPathDashArrayNV(int p0, long p1) {
        NVPathRendering.nglGetPathDashArrayNV(p0, p1);
        LabyDebugContext.glError("nglGetPathDashArrayNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPathDashArrayNV(int p0, FloatBuffer p1) {
        NVPathRendering.glGetPathDashArrayNV(p0, p1);
        LabyDebugContext.glError("glGetPathDashArrayNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetPathMetricsNV(int p0, int p1, int p2, long p3, int p4, int p5, long p6) {
        NVPathRendering.nglGetPathMetricsNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglGetPathMetricsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glGetPathMetricsNV(int p0, int p1, ByteBuffer p2, int p3, int p4, FloatBuffer p5) {
        NVPathRendering.glGetPathMetricsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetPathMetricsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglGetPathMetricRangeNV(int p0, int p1, int p2, int p3, long p4) {
        NVPathRendering.nglGetPathMetricRangeNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetPathMetricRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetPathMetricRangeNV(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        NVPathRendering.glGetPathMetricRangeNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetPathMetricRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetPathSpacingNV(int p0, int p1, int p2, long p3, int p4, float p5, float p6, int p7, long p8) {
        NVPathRendering.nglGetPathSpacingNV(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglGetPathSpacingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glGetPathSpacingNV(int p0, int p1, ByteBuffer p2, int p3, float p4, float p5, int p6, FloatBuffer p7) {
        NVPathRendering.glGetPathSpacingNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glGetPathSpacingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglGetPathColorGenivNV(int p0, int p1, long p2) {
        NVPathRendering.nglGetPathColorGenivNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetPathColorGenivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPathColorGenivNV(int p0, int p1, IntBuffer p2) {
        NVPathRendering.glGetPathColorGenivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathColorGenivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetPathColorGeniNV(int p0, int p1) {
        int returnType = NVPathRendering.glGetPathColorGeniNV(p0, p1);
        LabyDebugContext.glError("glGetPathColorGeniNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetPathColorGenfvNV(int p0, int p1, long p2) {
        NVPathRendering.nglGetPathColorGenfvNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetPathColorGenfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPathColorGenfvNV(int p0, int p1, FloatBuffer p2) {
        NVPathRendering.glGetPathColorGenfvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathColorGenfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetPathColorGenfNV(int p0, int p1) {
        float returnType = NVPathRendering.glGetPathColorGenfNV(p0, p1);
        LabyDebugContext.glError("glGetPathColorGenfNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetPathTexGenivNV(int p0, int p1, long p2) {
        NVPathRendering.nglGetPathTexGenivNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetPathTexGenivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPathTexGenivNV(int p0, int p1, IntBuffer p2) {
        NVPathRendering.glGetPathTexGenivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathTexGenivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetPathTexGeniNV(int p0, int p1) {
        int returnType = NVPathRendering.glGetPathTexGeniNV(p0, p1);
        LabyDebugContext.glError("glGetPathTexGeniNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetPathTexGenfvNV(int p0, int p1, long p2) {
        NVPathRendering.nglGetPathTexGenfvNV(p0, p1, p2);
        LabyDebugContext.glError("nglGetPathTexGenfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetPathTexGenfvNV(int p0, int p1, FloatBuffer p2) {
        NVPathRendering.glGetPathTexGenfvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathTexGenfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetPathTexGenfNV(int p0, int p1) {
        float returnType = NVPathRendering.glGetPathTexGenfNV(p0, p1);
        LabyDebugContext.glError("glGetPathTexGenfNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static boolean glIsPointInFillPathNV(int p0, int p1, float p2, float p3) {
        boolean returnType = NVPathRendering.glIsPointInFillPathNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glIsPointInFillPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
        return returnType;
    }

    public static boolean glIsPointInStrokePathNV(int p0, float p1, float p2) {
        boolean returnType = NVPathRendering.glIsPointInStrokePathNV(p0, p1, p2);
        LabyDebugContext.glError("glIsPointInStrokePathNV", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
        return returnType;
    }

    public static float glGetPathLengthNV(int p0, int p1, int p2) {
        float returnType = NVPathRendering.glGetPathLengthNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathLengthNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static boolean nglPointAlongPathNV(int p0, int p1, int p2, float p3, long p4, long p5, long p6, long p7) {
        boolean returnType = NVPathRendering.nglPointAlongPathNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglPointAlongPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Float.valueOf(p3), "p4", Long.valueOf(p4), "p5", Long.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
        return returnType;
    }

    public static boolean glPointAlongPathNV(int p0, int p1, int p2, float p3, FloatBuffer p4, FloatBuffer p5, FloatBuffer p6, FloatBuffer p7) {
        boolean returnType = NVPathRendering.glPointAlongPathNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glPointAlongPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Float.valueOf(p3), "p4", p4, "p5", p5, "p6", p6, "p7", p7);
        return returnType;
    }

    public static void nglMatrixLoad3x2fNV(int p0, long p1) {
        NVPathRendering.nglMatrixLoad3x2fNV(p0, p1);
        LabyDebugContext.glError("nglMatrixLoad3x2fNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixLoad3x2fNV(int p0, FloatBuffer p1) {
        NVPathRendering.glMatrixLoad3x2fNV(p0, p1);
        LabyDebugContext.glError("glMatrixLoad3x2fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMatrixLoad3x3fNV(int p0, long p1) {
        NVPathRendering.nglMatrixLoad3x3fNV(p0, p1);
        LabyDebugContext.glError("nglMatrixLoad3x3fNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixLoad3x3fNV(int p0, FloatBuffer p1) {
        NVPathRendering.glMatrixLoad3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixLoad3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMatrixLoadTranspose3x3fNV(int p0, long p1) {
        NVPathRendering.nglMatrixLoadTranspose3x3fNV(p0, p1);
        LabyDebugContext.glError("nglMatrixLoadTranspose3x3fNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixLoadTranspose3x3fNV(int p0, FloatBuffer p1) {
        NVPathRendering.glMatrixLoadTranspose3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixLoadTranspose3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMatrixMult3x2fNV(int p0, long p1) {
        NVPathRendering.nglMatrixMult3x2fNV(p0, p1);
        LabyDebugContext.glError("nglMatrixMult3x2fNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixMult3x2fNV(int p0, FloatBuffer p1) {
        NVPathRendering.glMatrixMult3x2fNV(p0, p1);
        LabyDebugContext.glError("glMatrixMult3x2fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMatrixMult3x3fNV(int p0, long p1) {
        NVPathRendering.nglMatrixMult3x3fNV(p0, p1);
        LabyDebugContext.glError("nglMatrixMult3x3fNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixMult3x3fNV(int p0, FloatBuffer p1) {
        NVPathRendering.glMatrixMult3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixMult3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglMatrixMultTranspose3x3fNV(int p0, long p1) {
        NVPathRendering.nglMatrixMultTranspose3x3fNV(p0, p1);
        LabyDebugContext.glError("nglMatrixMultTranspose3x3fNV", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glMatrixMultTranspose3x3fNV(int p0, FloatBuffer p1) {
        NVPathRendering.glMatrixMultTranspose3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixMultTranspose3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetProgramResourcefvNV(int p0, int p1, int p2, int p3, long p4, int p5, long p6, long p7) {
        NVPathRendering.nglGetProgramResourcefvNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglGetProgramResourcefvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glGetProgramResourcefvNV(int p0, int p1, int p2, IntBuffer p3, IntBuffer p4, FloatBuffer p5) {
        NVPathRendering.glGetProgramResourcefvNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetProgramResourcefvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
    }

    public static void glPathCommandsNV(int p0, ByteBuffer p1, int p2, short[] p3) {
        NVPathRendering.glPathCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathCommandsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathCommandsNV(int p0, ByteBuffer p1, int p2, float[] p3) {
        NVPathRendering.glPathCommandsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathCommandsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathCoordsNV(int p0, int p1, short[] p2) {
        NVPathRendering.glPathCoordsNV(p0, p1, p2);
        LabyDebugContext.glError("glPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathCoordsNV(int p0, int p1, float[] p2) {
        NVPathRendering.glPathCoordsNV(p0, p1, p2);
        LabyDebugContext.glError("glPathCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathSubCommandsNV(int p0, int p1, int p2, ByteBuffer p3, int p4, short[] p5) {
        NVPathRendering.glPathSubCommandsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathSubCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glPathSubCommandsNV(int p0, int p1, int p2, ByteBuffer p3, int p4, float[] p5) {
        NVPathRendering.glPathSubCommandsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathSubCommandsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glPathSubCoordsNV(int p0, int p1, int p2, short[] p3) {
        NVPathRendering.glPathSubCoordsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathSubCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathSubCoordsNV(int p0, int p1, int p2, float[] p3) {
        NVPathRendering.glPathSubCoordsNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathSubCoordsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glWeightPathsNV(int p0, int[] p1, float[] p2) {
        NVPathRendering.glWeightPathsNV(p0, p1, p2);
        LabyDebugContext.glError("glWeightPathsNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", p2);
    }

    public static void glTransformPathNV(int p0, int p1, int p2, float[] p3) {
        NVPathRendering.glTransformPathNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glTransformPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathParameterivNV(int p0, int p1, int[] p2) {
        NVPathRendering.glPathParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glPathParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathParameterfvNV(int p0, int p1, float[] p2) {
        NVPathRendering.glPathParameterfvNV(p0, p1, p2);
        LabyDebugContext.glError("glPathParameterfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glPathDashArrayNV(int p0, float[] p1) {
        NVPathRendering.glPathDashArrayNV(p0, p1);
        LabyDebugContext.glError("glPathDashArrayNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glStencilFillPathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, float[] p6) {
        NVPathRendering.glStencilFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glStencilFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glStencilStrokePathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, float[] p6) {
        NVPathRendering.glStencilStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glStencilStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glPathColorGenNV(int p0, int p1, int p2, float[] p3) {
        NVPathRendering.glPathColorGenNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathColorGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glPathTexGenNV(int p0, int p1, int p2, float[] p3) {
        NVPathRendering.glPathTexGenNV(p0, p1, p2, p3);
        LabyDebugContext.glError("glPathTexGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCoverFillPathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, float[] p5) {
        NVPathRendering.glCoverFillPathInstancedNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCoverFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glCoverStrokePathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, float[] p5) {
        NVPathRendering.glCoverStrokePathInstancedNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCoverStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glStencilThenCoverFillPathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, int p6, float[] p7) {
        NVPathRendering.glStencilThenCoverFillPathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glStencilThenCoverFillPathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glStencilThenCoverStrokePathInstancedNV(int p0, ByteBuffer p1, int p2, int p3, int p4, int p5, int p6, float[] p7) {
        NVPathRendering.glStencilThenCoverStrokePathInstancedNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glStencilThenCoverStrokePathInstancedNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static int glPathGlyphIndexRangeNV(int p0, ByteBuffer p1, int p2, int p3, float p4, int[] p5) {
        int returnType = NVPathRendering.glPathGlyphIndexRangeNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glPathGlyphIndexRangeNV", "p0", Integer.valueOf(p0), "p1", p1, "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Float.valueOf(p4), "p5", p5);
        return returnType;
    }

    public static void glProgramPathFragmentInputGenNV(int p0, int p1, int p2, int p3, float[] p4) {
        NVPathRendering.glProgramPathFragmentInputGenNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glProgramPathFragmentInputGenNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetPathParameterivNV(int p0, int p1, int[] p2) {
        NVPathRendering.glGetPathParameterivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathParameterivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetPathParameterfvNV(int p0, int p1, float[] p2) {
        NVPathRendering.glGetPathParameterfvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathParameterfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetPathCoordsNV(int p0, float[] p1) {
        NVPathRendering.glGetPathCoordsNV(p0, p1);
        LabyDebugContext.glError("glGetPathCoordsNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPathDashArrayNV(int p0, float[] p1) {
        NVPathRendering.glGetPathDashArrayNV(p0, p1);
        LabyDebugContext.glError("glGetPathDashArrayNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPathMetricsNV(int p0, int p1, ByteBuffer p2, int p3, int p4, float[] p5) {
        NVPathRendering.glGetPathMetricsNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetPathMetricsNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glGetPathMetricRangeNV(int p0, int p1, int p2, int p3, float[] p4) {
        NVPathRendering.glGetPathMetricRangeNV(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetPathMetricRangeNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetPathSpacingNV(int p0, int p1, ByteBuffer p2, int p3, float p4, float p5, int p6, float[] p7) {
        NVPathRendering.glGetPathSpacingNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glGetPathSpacingNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2, "p3", Integer.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glGetPathColorGenivNV(int p0, int p1, int[] p2) {
        NVPathRendering.glGetPathColorGenivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathColorGenivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetPathColorGenfvNV(int p0, int p1, float[] p2) {
        NVPathRendering.glGetPathColorGenfvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathColorGenfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetPathTexGenivNV(int p0, int p1, int[] p2) {
        NVPathRendering.glGetPathTexGenivNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathTexGenivNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetPathTexGenfvNV(int p0, int p1, float[] p2) {
        NVPathRendering.glGetPathTexGenfvNV(p0, p1, p2);
        LabyDebugContext.glError("glGetPathTexGenfvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static boolean glPointAlongPathNV(int p0, int p1, int p2, float p3, float[] p4, float[] p5, float[] p6, float[] p7) {
        boolean returnType = NVPathRendering.glPointAlongPathNV(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glPointAlongPathNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Float.valueOf(p3), "p4", p4, "p5", p5, "p6", p6, "p7", p7);
        return returnType;
    }

    public static void glMatrixLoad3x2fNV(int p0, float[] p1) {
        NVPathRendering.glMatrixLoad3x2fNV(p0, p1);
        LabyDebugContext.glError("glMatrixLoad3x2fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMatrixLoad3x3fNV(int p0, float[] p1) {
        NVPathRendering.glMatrixLoad3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixLoad3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMatrixLoadTranspose3x3fNV(int p0, float[] p1) {
        NVPathRendering.glMatrixLoadTranspose3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixLoadTranspose3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMatrixMult3x2fNV(int p0, float[] p1) {
        NVPathRendering.glMatrixMult3x2fNV(p0, p1);
        LabyDebugContext.glError("glMatrixMult3x2fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMatrixMult3x3fNV(int p0, float[] p1) {
        NVPathRendering.glMatrixMult3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixMult3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glMatrixMultTranspose3x3fNV(int p0, float[] p1) {
        NVPathRendering.glMatrixMultTranspose3x3fNV(p0, p1);
        LabyDebugContext.glError("glMatrixMultTranspose3x3fNV", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetProgramResourcefvNV(int p0, int p1, int p2, int[] p3, int[] p4, float[] p5) {
        NVPathRendering.glGetProgramResourcefvNV(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glGetProgramResourcefvNV", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3, "p4", p4, "p5", p5);
    }
}
