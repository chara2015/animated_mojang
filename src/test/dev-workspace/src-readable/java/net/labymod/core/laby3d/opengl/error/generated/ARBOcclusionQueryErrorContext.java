package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.IntBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.opengl.ARBOcclusionQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/ARBOcclusionQueryErrorContext.class */
public final class ARBOcclusionQueryErrorContext {
    public static void nglGenQueriesARB(int p0, long p1) {
        ARBOcclusionQuery.nglGenQueriesARB(p0, p1);
        LabyDebugContext.glError("nglGenQueriesARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenQueriesARB(IntBuffer p0) {
        ARBOcclusionQuery.glGenQueriesARB(p0);
        LabyDebugContext.glError("glGenQueriesARB", "p0", p0);
    }

    public static int glGenQueriesARB() {
        int returnType = ARBOcclusionQuery.glGenQueriesARB();
        LabyDebugContext.glError("glGenQueriesARB", new Object[0]);
        return returnType;
    }

    public static void nglDeleteQueriesARB(int p0, long p1) {
        ARBOcclusionQuery.nglDeleteQueriesARB(p0, p1);
        LabyDebugContext.glError("nglDeleteQueriesARB", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteQueriesARB(IntBuffer p0) {
        ARBOcclusionQuery.glDeleteQueriesARB(p0);
        LabyDebugContext.glError("glDeleteQueriesARB", "p0", p0);
    }

    public static void glDeleteQueriesARB(int p0) {
        ARBOcclusionQuery.glDeleteQueriesARB(p0);
        LabyDebugContext.glError("glDeleteQueriesARB", "p0", Integer.valueOf(p0));
    }

    public static boolean glIsQueryARB(int p0) {
        boolean returnType = ARBOcclusionQuery.glIsQueryARB(p0);
        LabyDebugContext.glError("glIsQueryARB", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glBeginQueryARB(int p0, int p1) {
        ARBOcclusionQuery.glBeginQueryARB(p0, p1);
        LabyDebugContext.glError("glBeginQueryARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glEndQueryARB(int p0) {
        ARBOcclusionQuery.glEndQueryARB(p0);
        LabyDebugContext.glError("glEndQueryARB", "p0", Integer.valueOf(p0));
    }

    public static void nglGetQueryivARB(int p0, int p1, long p2) {
        ARBOcclusionQuery.nglGetQueryivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryivARB(int p0, int p1, IntBuffer p2) {
        ARBOcclusionQuery.glGetQueryivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetQueryiARB(int p0, int p1) {
        int returnType = ARBOcclusionQuery.glGetQueryiARB(p0, p1);
        LabyDebugContext.glError("glGetQueryiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectivARB(int p0, int p1, long p2) {
        ARBOcclusionQuery.nglGetQueryObjectivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectivARB(int p0, int p1, IntBuffer p2) {
        ARBOcclusionQuery.glGetQueryObjectivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectivARB(int p0, int p1, long p2) {
        ARBOcclusionQuery.glGetQueryObjectivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static int glGetQueryObjectiARB(int p0, int p1) {
        int returnType = ARBOcclusionQuery.glGetQueryObjectiARB(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetQueryObjectuivARB(int p0, int p1, long p2) {
        ARBOcclusionQuery.nglGetQueryObjectuivARB(p0, p1, p2);
        LabyDebugContext.glError("nglGetQueryObjectuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetQueryObjectuivARB(int p0, int p1, IntBuffer p2) {
        ARBOcclusionQuery.glGetQueryObjectuivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectuivARB(int p0, int p1, long p2) {
        ARBOcclusionQuery.glGetQueryObjectuivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static int glGetQueryObjectuiARB(int p0, int p1) {
        int returnType = ARBOcclusionQuery.glGetQueryObjectuiARB(p0, p1);
        LabyDebugContext.glError("glGetQueryObjectuiARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glGenQueriesARB(int[] p0) {
        ARBOcclusionQuery.glGenQueriesARB(p0);
        LabyDebugContext.glError("glGenQueriesARB", "p0", p0);
    }

    public static void glDeleteQueriesARB(int[] p0) {
        ARBOcclusionQuery.glDeleteQueriesARB(p0);
        LabyDebugContext.glError("glDeleteQueriesARB", "p0", p0);
    }

    public static void glGetQueryivARB(int p0, int p1, int[] p2) {
        ARBOcclusionQuery.glGetQueryivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectivARB(int p0, int p1, int[] p2) {
        ARBOcclusionQuery.glGetQueryObjectivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetQueryObjectuivARB(int p0, int p1, int[] p2) {
        ARBOcclusionQuery.glGetQueryObjectuivARB(p0, p1, p2);
        LabyDebugContext.glError("glGetQueryObjectuivARB", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }
}
