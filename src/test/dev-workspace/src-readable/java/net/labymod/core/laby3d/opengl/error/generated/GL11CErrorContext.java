package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11C;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL11CErrorContext.class */
public final class GL11CErrorContext {
    public static void glEnable(int p0) {
        GL11C.glEnable(p0);
        LabyDebugContext.glError("glEnable", "p0", Integer.valueOf(p0));
    }

    public static void glDisable(int p0) {
        GL11C.glDisable(p0);
        LabyDebugContext.glError("glDisable", "p0", Integer.valueOf(p0));
    }

    public static void glBindTexture(int p0, int p1) {
        GL11C.glBindTexture(p0, p1);
        LabyDebugContext.glError("glBindTexture", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glBlendFunc(int p0, int p1) {
        GL11C.glBlendFunc(p0, p1);
        LabyDebugContext.glError("glBlendFunc", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glClear(int p0) {
        GL11C.glClear(p0);
        LabyDebugContext.glError("glClear", "p0", Integer.valueOf(p0));
    }

    public static void glClearColor(float p0, float p1, float p2, float p3) {
        GL11C.glClearColor(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearColor", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glClearDepth(double p0) {
        GL11C.glClearDepth(p0);
        LabyDebugContext.glError("glClearDepth", "p0", Double.valueOf(p0));
    }

    public static void glClearStencil(int p0) {
        GL11C.glClearStencil(p0);
        LabyDebugContext.glError("glClearStencil", "p0", Integer.valueOf(p0));
    }

    public static void glColorMask(boolean p0, boolean p1, boolean p2, boolean p3) {
        GL11C.glColorMask(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorMask", "p0", Boolean.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Boolean.valueOf(p3));
    }

    public static void glCullFace(int p0) {
        GL11C.glCullFace(p0);
        LabyDebugContext.glError("glCullFace", "p0", Integer.valueOf(p0));
    }

    public static void glDepthFunc(int p0) {
        GL11C.glDepthFunc(p0);
        LabyDebugContext.glError("glDepthFunc", "p0", Integer.valueOf(p0));
    }

    public static void glDepthMask(boolean p0) {
        GL11C.glDepthMask(p0);
        LabyDebugContext.glError("glDepthMask", "p0", Boolean.valueOf(p0));
    }

    public static void glDepthRange(double p0, double p1) {
        GL11C.glDepthRange(p0, p1);
        LabyDebugContext.glError("glDepthRange", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glDrawArrays(int p0, int p1, int p2) {
        GL11C.glDrawArrays(p0, p1, p2);
        LabyDebugContext.glError("glDrawArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDrawBuffer(int p0) {
        GL11C.glDrawBuffer(p0);
        LabyDebugContext.glError("glDrawBuffer", "p0", Integer.valueOf(p0));
    }

    public static void nglDrawElements(int p0, int p1, int p2, long p3) {
        GL11C.nglDrawElements(p0, p1, p2, p3);
        LabyDebugContext.glError("nglDrawElements", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glDrawElements(int p0, int p1, int p2, long p3) {
        GL11C.glDrawElements(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glDrawElements(int p0, int p1, ByteBuffer p2) {
        GL11C.glDrawElements(p0, p1, p2);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDrawElements(int p0, ByteBuffer p1) {
        GL11C.glDrawElements(p0, p1);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawElements(int p0, ShortBuffer p1) {
        GL11C.glDrawElements(p0, p1);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawElements(int p0, IntBuffer p1) {
        GL11C.glDrawElements(p0, p1);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFinish() {
        GL11C.glFinish();
        LabyDebugContext.glError("glFinish", new Object[0]);
    }

    public static void glFlush() {
        GL11C.glFlush();
        LabyDebugContext.glError("glFlush", new Object[0]);
    }

    public static void glFrontFace(int p0) {
        GL11C.glFrontFace(p0);
        LabyDebugContext.glError("glFrontFace", "p0", Integer.valueOf(p0));
    }

    public static void nglGenTextures(int p0, long p1) {
        GL11C.nglGenTextures(p0, p1);
        LabyDebugContext.glError("nglGenTextures", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenTextures(IntBuffer p0) {
        GL11C.glGenTextures(p0);
        LabyDebugContext.glError("glGenTextures", "p0", p0);
    }

    public static int glGenTextures() {
        int returnType = GL11C.glGenTextures();
        LabyDebugContext.glError("glGenTextures", new Object[0]);
        return returnType;
    }

    public static void nglDeleteTextures(int p0, long p1) {
        GL11C.nglDeleteTextures(p0, p1);
        LabyDebugContext.glError("nglDeleteTextures", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteTextures(IntBuffer p0) {
        GL11C.glDeleteTextures(p0);
        LabyDebugContext.glError("glDeleteTextures", "p0", p0);
    }

    public static void glDeleteTextures(int p0) {
        GL11C.glDeleteTextures(p0);
        LabyDebugContext.glError("glDeleteTextures", "p0", Integer.valueOf(p0));
    }

    public static void nglGetBooleanv(int p0, long p1) {
        GL11C.nglGetBooleanv(p0, p1);
        LabyDebugContext.glError("nglGetBooleanv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetBooleanv(int p0, ByteBuffer p1) {
        GL11C.glGetBooleanv(p0, p1);
        LabyDebugContext.glError("glGetBooleanv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static boolean glGetBoolean(int p0) {
        boolean returnType = GL11C.glGetBoolean(p0);
        LabyDebugContext.glError("glGetBoolean", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetFloatv(int p0, long p1) {
        GL11C.nglGetFloatv(p0, p1);
        LabyDebugContext.glError("nglGetFloatv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetFloatv(int p0, FloatBuffer p1) {
        GL11C.glGetFloatv(p0, p1);
        LabyDebugContext.glError("glGetFloatv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static float glGetFloat(int p0) {
        float returnType = GL11C.glGetFloat(p0);
        LabyDebugContext.glError("glGetFloat", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetIntegerv(int p0, long p1) {
        GL11C.nglGetIntegerv(p0, p1);
        LabyDebugContext.glError("nglGetIntegerv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetIntegerv(int p0, IntBuffer p1) {
        GL11C.glGetIntegerv(p0, p1);
        LabyDebugContext.glError("glGetIntegerv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glGetInteger(int p0) {
        int returnType = GL11C.glGetInteger(p0);
        LabyDebugContext.glError("glGetInteger", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetDoublev(int p0, long p1) {
        GL11C.nglGetDoublev(p0, p1);
        LabyDebugContext.glError("nglGetDoublev", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetDoublev(int p0, DoubleBuffer p1) {
        GL11C.glGetDoublev(p0, p1);
        LabyDebugContext.glError("glGetDoublev", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static double glGetDouble(int p0) {
        double returnType = GL11C.glGetDouble(p0);
        LabyDebugContext.glError("glGetDouble", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static int glGetError() {
        int returnType = GL11C.glGetError();
        LabyDebugContext.glError("glGetError", new Object[0]);
        return returnType;
    }

    public static void nglGetPointerv(int p0, long p1) {
        GL11C.nglGetPointerv(p0, p1);
        LabyDebugContext.glError("nglGetPointerv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPointerv(int p0, PointerBuffer p1) {
        GL11C.glGetPointerv(p0, p1);
        LabyDebugContext.glError("glGetPointerv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static long glGetPointer(int p0) {
        long returnType = GL11C.glGetPointer(p0);
        LabyDebugContext.glError("glGetPointer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static long nglGetString(int p0) {
        long returnType = GL11C.nglGetString(p0);
        LabyDebugContext.glError("nglGetString", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static String glGetString(int p0) {
        String returnType = GL11C.glGetString(p0);
        LabyDebugContext.glError("glGetString", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetTexImage(int p0, int p1, int p2, int p3, long p4) {
        GL11C.nglGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, long p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetTexLevelParameteriv(int p0, int p1, int p2, long p3) {
        GL11C.nglGetTexLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTexLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTexLevelParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        GL11C.glGetTexLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetTexLevelParameteri(int p0, int p1, int p2) {
        int returnType = GL11C.glGetTexLevelParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetTexLevelParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTexLevelParameterfv(int p0, int p1, int p2, long p3) {
        GL11C.nglGetTexLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTexLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTexLevelParameterfv(int p0, int p1, int p2, FloatBuffer p3) {
        GL11C.glGetTexLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static float glGetTexLevelParameterf(int p0, int p1, int p2) {
        float returnType = GL11C.glGetTexLevelParameterf(p0, p1, p2);
        LabyDebugContext.glError("glGetTexLevelParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTexParameteriv(int p0, int p1, long p2) {
        GL11C.nglGetTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexParameteriv(int p0, int p1, IntBuffer p2) {
        GL11C.glGetTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTexParameteri(int p0, int p1) {
        int returnType = GL11C.glGetTexParameteri(p0, p1);
        LabyDebugContext.glError("glGetTexParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexParameterfv(int p0, int p1, long p2) {
        GL11C.nglGetTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexParameterfv(int p0, int p1, FloatBuffer p2) {
        GL11C.glGetTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetTexParameterf(int p0, int p1) {
        float returnType = GL11C.glGetTexParameterf(p0, p1);
        LabyDebugContext.glError("glGetTexParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glHint(int p0, int p1) {
        GL11C.glHint(p0, p1);
        LabyDebugContext.glError("glHint", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static boolean glIsEnabled(int p0) {
        boolean returnType = GL11C.glIsEnabled(p0);
        LabyDebugContext.glError("glIsEnabled", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static boolean glIsTexture(int p0) {
        boolean returnType = GL11C.glIsTexture(p0);
        LabyDebugContext.glError("glIsTexture", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glLineWidth(float p0) {
        GL11C.glLineWidth(p0);
        LabyDebugContext.glError("glLineWidth", "p0", Float.valueOf(p0));
    }

    public static void glLogicOp(int p0) {
        GL11C.glLogicOp(p0);
        LabyDebugContext.glError("glLogicOp", "p0", Integer.valueOf(p0));
    }

    public static void glPixelStorei(int p0, int p1) {
        GL11C.glPixelStorei(p0, p1);
        LabyDebugContext.glError("glPixelStorei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glPixelStoref(int p0, float p1) {
        GL11C.glPixelStoref(p0, p1);
        LabyDebugContext.glError("glPixelStoref", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glPointSize(float p0) {
        GL11C.glPointSize(p0);
        LabyDebugContext.glError("glPointSize", "p0", Float.valueOf(p0));
    }

    public static void glPolygonMode(int p0, int p1) {
        GL11C.glPolygonMode(p0, p1);
        LabyDebugContext.glError("glPolygonMode", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glPolygonOffset(float p0, float p1) {
        GL11C.glPolygonOffset(p0, p1);
        LabyDebugContext.glError("glPolygonOffset", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glReadBuffer(int p0) {
        GL11C.glReadBuffer(p0);
        LabyDebugContext.glError("glReadBuffer", "p0", Integer.valueOf(p0));
    }

    public static void nglReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11C.nglReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glScissor(int p0, int p1, int p2, int p3) {
        GL11C.glScissor(p0, p1, p2, p3);
        LabyDebugContext.glError("glScissor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glStencilFunc(int p0, int p1, int p2) {
        GL11C.glStencilFunc(p0, p1, p2);
        LabyDebugContext.glError("glStencilFunc", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glStencilMask(int p0) {
        GL11C.glStencilMask(p0);
        LabyDebugContext.glError("glStencilMask", "p0", Integer.valueOf(p0));
    }

    public static void glStencilOp(int p0, int p1, int p2) {
        GL11C.glStencilOp(p0, p1, p2);
        LabyDebugContext.glError("glStencilOp", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL11C.nglTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ByteBuffer p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ShortBuffer p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, IntBuffer p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, FloatBuffer p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, DoubleBuffer p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11C.nglTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glCopyTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        GL11C.glCopyTexImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCopyTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glCopyTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL11C.glCopyTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCopyTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glCopyTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL11C.glCopyTexSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCopyTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glCopyTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL11C.glCopyTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCopyTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glTexParameteri(int p0, int p1, int p2) {
        GL11C.glTexParameteri(p0, p1, p2);
        LabyDebugContext.glError("glTexParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTexParameteriv(int p0, int p1, long p2) {
        GL11C.nglTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexParameteriv(int p0, int p1, IntBuffer p2) {
        GL11C.glTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterf(int p0, int p1, float p2) {
        GL11C.glTexParameterf(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglTexParameterfv(int p0, int p1, long p2) {
        GL11C.nglTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexParameterfv(int p0, int p1, FloatBuffer p2) {
        GL11C.glTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11C.nglTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, DoubleBuffer p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11C.nglTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glViewport(int p0, int p1, int p2, int p3) {
        GL11C.glViewport(p0, p1, p2, p3);
        LabyDebugContext.glError("glViewport", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glGenTextures(int[] p0) {
        GL11C.glGenTextures(p0);
        LabyDebugContext.glError("glGenTextures", "p0", p0);
    }

    public static void glDeleteTextures(int[] p0) {
        GL11C.glDeleteTextures(p0);
        LabyDebugContext.glError("glDeleteTextures", "p0", p0);
    }

    public static void glGetFloatv(int p0, float[] p1) {
        GL11C.glGetFloatv(p0, p1);
        LabyDebugContext.glError("glGetFloatv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetIntegerv(int p0, int[] p1) {
        GL11C.glGetIntegerv(p0, p1);
        LabyDebugContext.glError("glGetIntegerv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetDoublev(int p0, double[] p1) {
        GL11C.glGetDoublev(p0, p1);
        LabyDebugContext.glError("glGetDoublev", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, short[] p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, int[] p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, float[] p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, double[] p4) {
        GL11C.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexLevelParameteriv(int p0, int p1, int p2, int[] p3) {
        GL11C.glGetTexLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTexLevelParameterfv(int p0, int p1, int p2, float[] p3) {
        GL11C.glGetTexLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTexParameteriv(int p0, int p1, int[] p2) {
        GL11C.glGetTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexParameterfv(int p0, int p1, float[] p2) {
        GL11C.glGetTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        GL11C.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, short[] p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int[] p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, float[] p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, double[] p7) {
        GL11C.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        GL11C.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexParameteriv(int p0, int p1, int[] p2) {
        GL11C.glTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterfv(int p0, int p1, float[] p2) {
        GL11C.glTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, double[] p6) {
        GL11C.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        GL11C.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }
}
