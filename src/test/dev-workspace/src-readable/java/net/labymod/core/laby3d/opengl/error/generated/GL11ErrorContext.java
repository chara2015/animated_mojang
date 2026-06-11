package net.labymod.core.laby3d.opengl.error.generated;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.labymod.core.laby3d.opengl.error.LabyDebugContext;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/error/generated/GL11ErrorContext.class */
public final class GL11ErrorContext {
    public static void glEnable(int p0) {
        GL11.glEnable(p0);
        LabyDebugContext.glError("glEnable", "p0", Integer.valueOf(p0));
    }

    public static void glDisable(int p0) {
        GL11.glDisable(p0);
        LabyDebugContext.glError("glDisable", "p0", Integer.valueOf(p0));
    }

    public static void glAccum(int p0, float p1) {
        GL11.glAccum(p0, p1);
        LabyDebugContext.glError("glAccum", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glAlphaFunc(int p0, float p1) {
        GL11.glAlphaFunc(p0, p1);
        LabyDebugContext.glError("glAlphaFunc", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static boolean nglAreTexturesResident(int p0, long p1, long p2) {
        boolean returnType = GL11.nglAreTexturesResident(p0, p1, p2);
        LabyDebugContext.glError("nglAreTexturesResident", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
        return returnType;
    }

    public static boolean glAreTexturesResident(IntBuffer p0, ByteBuffer p1) {
        boolean returnType = GL11.glAreTexturesResident(p0, p1);
        LabyDebugContext.glError("glAreTexturesResident", "p0", p0, "p1", p1);
        return returnType;
    }

    public static boolean glAreTexturesResident(int p0, ByteBuffer p1) {
        boolean returnType = GL11.glAreTexturesResident(p0, p1);
        LabyDebugContext.glError("glAreTexturesResident", "p0", Integer.valueOf(p0), "p1", p1);
        return returnType;
    }

    public static void glArrayElement(int p0) {
        GL11.glArrayElement(p0);
        LabyDebugContext.glError("glArrayElement", "p0", Integer.valueOf(p0));
    }

    public static void glBegin(int p0) {
        GL11.glBegin(p0);
        LabyDebugContext.glError("glBegin", "p0", Integer.valueOf(p0));
    }

    public static void glBindTexture(int p0, int p1) {
        GL11.glBindTexture(p0, p1);
        LabyDebugContext.glError("glBindTexture", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglBitmap(int p0, int p1, float p2, float p3, float p4, float p5, long p6) {
        GL11.nglBitmap(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglBitmap", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glBitmap(int p0, int p1, float p2, float p3, float p4, float p5, ByteBuffer p6) {
        GL11.glBitmap(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glBitmap", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", p6);
    }

    public static void glBitmap(int p0, int p1, float p2, float p3, float p4, float p5, long p6) {
        GL11.glBitmap(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glBitmap", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glBlendFunc(int p0, int p1) {
        GL11.glBlendFunc(p0, p1);
        LabyDebugContext.glError("glBlendFunc", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glCallList(int p0) {
        GL11.glCallList(p0);
        LabyDebugContext.glError("glCallList", "p0", Integer.valueOf(p0));
    }

    public static void nglCallLists(int p0, int p1, long p2) {
        GL11.nglCallLists(p0, p1, p2);
        LabyDebugContext.glError("nglCallLists", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glCallLists(int p0, ByteBuffer p1) {
        GL11.glCallLists(p0, p1);
        LabyDebugContext.glError("glCallLists", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glCallLists(ByteBuffer p0) {
        GL11.glCallLists(p0);
        LabyDebugContext.glError("glCallLists", "p0", p0);
    }

    public static void glCallLists(ShortBuffer p0) {
        GL11.glCallLists(p0);
        LabyDebugContext.glError("glCallLists", "p0", p0);
    }

    public static void glCallLists(IntBuffer p0) {
        GL11.glCallLists(p0);
        LabyDebugContext.glError("glCallLists", "p0", p0);
    }

    public static void glClear(int p0) {
        GL11.glClear(p0);
        LabyDebugContext.glError("glClear", "p0", Integer.valueOf(p0));
    }

    public static void glClearAccum(float p0, float p1, float p2, float p3) {
        GL11.glClearAccum(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearAccum", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glClearColor(float p0, float p1, float p2, float p3) {
        GL11.glClearColor(p0, p1, p2, p3);
        LabyDebugContext.glError("glClearColor", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glClearDepth(double p0) {
        GL11.glClearDepth(p0);
        LabyDebugContext.glError("glClearDepth", "p0", Double.valueOf(p0));
    }

    public static void glClearIndex(float p0) {
        GL11.glClearIndex(p0);
        LabyDebugContext.glError("glClearIndex", "p0", Float.valueOf(p0));
    }

    public static void glClearStencil(int p0) {
        GL11.glClearStencil(p0);
        LabyDebugContext.glError("glClearStencil", "p0", Integer.valueOf(p0));
    }

    public static void nglClipPlane(int p0, long p1) {
        GL11.nglClipPlane(p0, p1);
        LabyDebugContext.glError("nglClipPlane", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glClipPlane(int p0, DoubleBuffer p1) {
        GL11.glClipPlane(p0, p1);
        LabyDebugContext.glError("glClipPlane", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glColor3b(byte p0, byte p1, byte p2) {
        GL11.glColor3b(p0, p1, p2);
        LabyDebugContext.glError("glColor3b", "p0", Byte.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2));
    }

    public static void glColor3s(short p0, short p1, short p2) {
        GL11.glColor3s(p0, p1, p2);
        LabyDebugContext.glError("glColor3s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glColor3i(int p0, int p1, int p2) {
        GL11.glColor3i(p0, p1, p2);
        LabyDebugContext.glError("glColor3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glColor3f(float p0, float p1, float p2) {
        GL11.glColor3f(p0, p1, p2);
        LabyDebugContext.glError("glColor3f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glColor3d(double p0, double p1, double p2) {
        GL11.glColor3d(p0, p1, p2);
        LabyDebugContext.glError("glColor3d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glColor3ub(byte p0, byte p1, byte p2) {
        GL11.glColor3ub(p0, p1, p2);
        LabyDebugContext.glError("glColor3ub", "p0", Byte.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2));
    }

    public static void glColor3us(short p0, short p1, short p2) {
        GL11.glColor3us(p0, p1, p2);
        LabyDebugContext.glError("glColor3us", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glColor3ui(int p0, int p1, int p2) {
        GL11.glColor3ui(p0, p1, p2);
        LabyDebugContext.glError("glColor3ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglColor3bv(long p0) {
        GL11.nglColor3bv(p0);
        LabyDebugContext.glError("nglColor3bv", "p0", Long.valueOf(p0));
    }

    public static void glColor3bv(ByteBuffer p0) {
        GL11.glColor3bv(p0);
        LabyDebugContext.glError("glColor3bv", "p0", p0);
    }

    public static void nglColor3sv(long p0) {
        GL11.nglColor3sv(p0);
        LabyDebugContext.glError("nglColor3sv", "p0", Long.valueOf(p0));
    }

    public static void glColor3sv(ShortBuffer p0) {
        GL11.glColor3sv(p0);
        LabyDebugContext.glError("glColor3sv", "p0", p0);
    }

    public static void nglColor3iv(long p0) {
        GL11.nglColor3iv(p0);
        LabyDebugContext.glError("nglColor3iv", "p0", Long.valueOf(p0));
    }

    public static void glColor3iv(IntBuffer p0) {
        GL11.glColor3iv(p0);
        LabyDebugContext.glError("glColor3iv", "p0", p0);
    }

    public static void nglColor3fv(long p0) {
        GL11.nglColor3fv(p0);
        LabyDebugContext.glError("nglColor3fv", "p0", Long.valueOf(p0));
    }

    public static void glColor3fv(FloatBuffer p0) {
        GL11.glColor3fv(p0);
        LabyDebugContext.glError("glColor3fv", "p0", p0);
    }

    public static void nglColor3dv(long p0) {
        GL11.nglColor3dv(p0);
        LabyDebugContext.glError("nglColor3dv", "p0", Long.valueOf(p0));
    }

    public static void glColor3dv(DoubleBuffer p0) {
        GL11.glColor3dv(p0);
        LabyDebugContext.glError("glColor3dv", "p0", p0);
    }

    public static void nglColor3ubv(long p0) {
        GL11.nglColor3ubv(p0);
        LabyDebugContext.glError("nglColor3ubv", "p0", Long.valueOf(p0));
    }

    public static void glColor3ubv(ByteBuffer p0) {
        GL11.glColor3ubv(p0);
        LabyDebugContext.glError("glColor3ubv", "p0", p0);
    }

    public static void nglColor3usv(long p0) {
        GL11.nglColor3usv(p0);
        LabyDebugContext.glError("nglColor3usv", "p0", Long.valueOf(p0));
    }

    public static void glColor3usv(ShortBuffer p0) {
        GL11.glColor3usv(p0);
        LabyDebugContext.glError("glColor3usv", "p0", p0);
    }

    public static void nglColor3uiv(long p0) {
        GL11.nglColor3uiv(p0);
        LabyDebugContext.glError("nglColor3uiv", "p0", Long.valueOf(p0));
    }

    public static void glColor3uiv(IntBuffer p0) {
        GL11.glColor3uiv(p0);
        LabyDebugContext.glError("glColor3uiv", "p0", p0);
    }

    public static void glColor4b(byte p0, byte p1, byte p2, byte p3) {
        GL11.glColor4b(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4b", "p0", Byte.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2), "p3", Byte.valueOf(p3));
    }

    public static void glColor4s(short p0, short p1, short p2, short p3) {
        GL11.glColor4s(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glColor4i(int p0, int p1, int p2, int p3) {
        GL11.glColor4i(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glColor4f(float p0, float p1, float p2, float p3) {
        GL11.glColor4f(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glColor4d(double p0, double p1, double p2, double p3) {
        GL11.glColor4d(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glColor4ub(byte p0, byte p1, byte p2, byte p3) {
        GL11.glColor4ub(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4ub", "p0", Byte.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2), "p3", Byte.valueOf(p3));
    }

    public static void glColor4us(short p0, short p1, short p2, short p3) {
        GL11.glColor4us(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4us", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glColor4ui(int p0, int p1, int p2, int p3) {
        GL11.glColor4ui(p0, p1, p2, p3);
        LabyDebugContext.glError("glColor4ui", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglColor4bv(long p0) {
        GL11.nglColor4bv(p0);
        LabyDebugContext.glError("nglColor4bv", "p0", Long.valueOf(p0));
    }

    public static void glColor4bv(ByteBuffer p0) {
        GL11.glColor4bv(p0);
        LabyDebugContext.glError("glColor4bv", "p0", p0);
    }

    public static void nglColor4sv(long p0) {
        GL11.nglColor4sv(p0);
        LabyDebugContext.glError("nglColor4sv", "p0", Long.valueOf(p0));
    }

    public static void glColor4sv(ShortBuffer p0) {
        GL11.glColor4sv(p0);
        LabyDebugContext.glError("glColor4sv", "p0", p0);
    }

    public static void nglColor4iv(long p0) {
        GL11.nglColor4iv(p0);
        LabyDebugContext.glError("nglColor4iv", "p0", Long.valueOf(p0));
    }

    public static void glColor4iv(IntBuffer p0) {
        GL11.glColor4iv(p0);
        LabyDebugContext.glError("glColor4iv", "p0", p0);
    }

    public static void nglColor4fv(long p0) {
        GL11.nglColor4fv(p0);
        LabyDebugContext.glError("nglColor4fv", "p0", Long.valueOf(p0));
    }

    public static void glColor4fv(FloatBuffer p0) {
        GL11.glColor4fv(p0);
        LabyDebugContext.glError("glColor4fv", "p0", p0);
    }

    public static void nglColor4dv(long p0) {
        GL11.nglColor4dv(p0);
        LabyDebugContext.glError("nglColor4dv", "p0", Long.valueOf(p0));
    }

    public static void glColor4dv(DoubleBuffer p0) {
        GL11.glColor4dv(p0);
        LabyDebugContext.glError("glColor4dv", "p0", p0);
    }

    public static void nglColor4ubv(long p0) {
        GL11.nglColor4ubv(p0);
        LabyDebugContext.glError("nglColor4ubv", "p0", Long.valueOf(p0));
    }

    public static void glColor4ubv(ByteBuffer p0) {
        GL11.glColor4ubv(p0);
        LabyDebugContext.glError("glColor4ubv", "p0", p0);
    }

    public static void nglColor4usv(long p0) {
        GL11.nglColor4usv(p0);
        LabyDebugContext.glError("nglColor4usv", "p0", Long.valueOf(p0));
    }

    public static void glColor4usv(ShortBuffer p0) {
        GL11.glColor4usv(p0);
        LabyDebugContext.glError("glColor4usv", "p0", p0);
    }

    public static void nglColor4uiv(long p0) {
        GL11.nglColor4uiv(p0);
        LabyDebugContext.glError("nglColor4uiv", "p0", Long.valueOf(p0));
    }

    public static void glColor4uiv(IntBuffer p0) {
        GL11.glColor4uiv(p0);
        LabyDebugContext.glError("glColor4uiv", "p0", p0);
    }

    public static void glColorMask(boolean p0, boolean p1, boolean p2, boolean p3) {
        GL11.glColorMask(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorMask", "p0", Boolean.valueOf(p0), "p1", Boolean.valueOf(p1), "p2", Boolean.valueOf(p2), "p3", Boolean.valueOf(p3));
    }

    public static void glColorMaterial(int p0, int p1) {
        GL11.glColorMaterial(p0, p1);
        LabyDebugContext.glError("glColorMaterial", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglColorPointer(int p0, int p1, int p2, long p3) {
        GL11.nglColorPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("nglColorPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glColorPointer(int p0, int p1, int p2, ByteBuffer p3) {
        GL11.glColorPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glColorPointer(int p0, int p1, int p2, long p3) {
        GL11.glColorPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glColorPointer(int p0, int p1, int p2, ShortBuffer p3) {
        GL11.glColorPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glColorPointer(int p0, int p1, int p2, IntBuffer p3) {
        GL11.glColorPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glColorPointer(int p0, int p1, int p2, FloatBuffer p3) {
        GL11.glColorPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glColorPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glCopyPixels(int p0, int p1, int p2, int p3, int p4) {
        GL11.glCopyPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glCopyPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glCullFace(int p0) {
        GL11.glCullFace(p0);
        LabyDebugContext.glError("glCullFace", "p0", Integer.valueOf(p0));
    }

    public static void glDeleteLists(int p0, int p1) {
        GL11.glDeleteLists(p0, p1);
        LabyDebugContext.glError("glDeleteLists", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glDepthFunc(int p0) {
        GL11.glDepthFunc(p0);
        LabyDebugContext.glError("glDepthFunc", "p0", Integer.valueOf(p0));
    }

    public static void glDepthMask(boolean p0) {
        GL11.glDepthMask(p0);
        LabyDebugContext.glError("glDepthMask", "p0", Boolean.valueOf(p0));
    }

    public static void glDepthRange(double p0, double p1) {
        GL11.glDepthRange(p0, p1);
        LabyDebugContext.glError("glDepthRange", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void glDisableClientState(int p0) {
        GL11.glDisableClientState(p0);
        LabyDebugContext.glError("glDisableClientState", "p0", Integer.valueOf(p0));
    }

    public static void glDrawArrays(int p0, int p1, int p2) {
        GL11.glDrawArrays(p0, p1, p2);
        LabyDebugContext.glError("glDrawArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glDrawBuffer(int p0) {
        GL11.glDrawBuffer(p0);
        LabyDebugContext.glError("glDrawBuffer", "p0", Integer.valueOf(p0));
    }

    public static void nglDrawElements(int p0, int p1, int p2, long p3) {
        GL11.nglDrawElements(p0, p1, p2, p3);
        LabyDebugContext.glError("nglDrawElements", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glDrawElements(int p0, int p1, int p2, long p3) {
        GL11.glDrawElements(p0, p1, p2, p3);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glDrawElements(int p0, int p1, ByteBuffer p2) {
        GL11.glDrawElements(p0, p1, p2);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glDrawElements(int p0, ByteBuffer p1) {
        GL11.glDrawElements(p0, p1);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawElements(int p0, ShortBuffer p1) {
        GL11.glDrawElements(p0, p1);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glDrawElements(int p0, IntBuffer p1) {
        GL11.glDrawElements(p0, p1);
        LabyDebugContext.glError("glDrawElements", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglDrawPixels(int p0, int p1, int p2, int p3, long p4) {
        GL11.nglDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, long p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glEdgeFlag(boolean p0) {
        GL11.glEdgeFlag(p0);
        LabyDebugContext.glError("glEdgeFlag", "p0", Boolean.valueOf(p0));
    }

    public static void nglEdgeFlagv(long p0) {
        GL11.nglEdgeFlagv(p0);
        LabyDebugContext.glError("nglEdgeFlagv", "p0", Long.valueOf(p0));
    }

    public static void glEdgeFlagv(ByteBuffer p0) {
        GL11.glEdgeFlagv(p0);
        LabyDebugContext.glError("glEdgeFlagv", "p0", p0);
    }

    public static void nglEdgeFlagPointer(int p0, long p1) {
        GL11.nglEdgeFlagPointer(p0, p1);
        LabyDebugContext.glError("nglEdgeFlagPointer", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glEdgeFlagPointer(int p0, ByteBuffer p1) {
        GL11.glEdgeFlagPointer(p0, p1);
        LabyDebugContext.glError("glEdgeFlagPointer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glEdgeFlagPointer(int p0, long p1) {
        GL11.glEdgeFlagPointer(p0, p1);
        LabyDebugContext.glError("glEdgeFlagPointer", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glEnableClientState(int p0) {
        GL11.glEnableClientState(p0);
        LabyDebugContext.glError("glEnableClientState", "p0", Integer.valueOf(p0));
    }

    public static void glEnd() {
        GL11.glEnd();
        LabyDebugContext.glError("glEnd", new Object[0]);
    }

    public static void glEvalCoord1f(float p0) {
        GL11.glEvalCoord1f(p0);
        LabyDebugContext.glError("glEvalCoord1f", "p0", Float.valueOf(p0));
    }

    public static void nglEvalCoord1fv(long p0) {
        GL11.nglEvalCoord1fv(p0);
        LabyDebugContext.glError("nglEvalCoord1fv", "p0", Long.valueOf(p0));
    }

    public static void glEvalCoord1fv(FloatBuffer p0) {
        GL11.glEvalCoord1fv(p0);
        LabyDebugContext.glError("glEvalCoord1fv", "p0", p0);
    }

    public static void glEvalCoord1d(double p0) {
        GL11.glEvalCoord1d(p0);
        LabyDebugContext.glError("glEvalCoord1d", "p0", Double.valueOf(p0));
    }

    public static void nglEvalCoord1dv(long p0) {
        GL11.nglEvalCoord1dv(p0);
        LabyDebugContext.glError("nglEvalCoord1dv", "p0", Long.valueOf(p0));
    }

    public static void glEvalCoord1dv(DoubleBuffer p0) {
        GL11.glEvalCoord1dv(p0);
        LabyDebugContext.glError("glEvalCoord1dv", "p0", p0);
    }

    public static void glEvalCoord2f(float p0, float p1) {
        GL11.glEvalCoord2f(p0, p1);
        LabyDebugContext.glError("glEvalCoord2f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void nglEvalCoord2fv(long p0) {
        GL11.nglEvalCoord2fv(p0);
        LabyDebugContext.glError("nglEvalCoord2fv", "p0", Long.valueOf(p0));
    }

    public static void glEvalCoord2fv(FloatBuffer p0) {
        GL11.glEvalCoord2fv(p0);
        LabyDebugContext.glError("glEvalCoord2fv", "p0", p0);
    }

    public static void glEvalCoord2d(double p0, double p1) {
        GL11.glEvalCoord2d(p0, p1);
        LabyDebugContext.glError("glEvalCoord2d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void nglEvalCoord2dv(long p0) {
        GL11.nglEvalCoord2dv(p0);
        LabyDebugContext.glError("nglEvalCoord2dv", "p0", Long.valueOf(p0));
    }

    public static void glEvalCoord2dv(DoubleBuffer p0) {
        GL11.glEvalCoord2dv(p0);
        LabyDebugContext.glError("glEvalCoord2dv", "p0", p0);
    }

    public static void glEvalMesh1(int p0, int p1, int p2) {
        GL11.glEvalMesh1(p0, p1, p2);
        LabyDebugContext.glError("glEvalMesh1", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glEvalMesh2(int p0, int p1, int p2, int p3, int p4) {
        GL11.glEvalMesh2(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glEvalMesh2", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4));
    }

    public static void glEvalPoint1(int p0) {
        GL11.glEvalPoint1(p0);
        LabyDebugContext.glError("glEvalPoint1", "p0", Integer.valueOf(p0));
    }

    public static void glEvalPoint2(int p0, int p1) {
        GL11.glEvalPoint2(p0, p1);
        LabyDebugContext.glError("glEvalPoint2", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglFeedbackBuffer(int p0, int p1, long p2) {
        GL11.nglFeedbackBuffer(p0, p1, p2);
        LabyDebugContext.glError("nglFeedbackBuffer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glFeedbackBuffer(int p0, FloatBuffer p1) {
        GL11.glFeedbackBuffer(p0, p1);
        LabyDebugContext.glError("glFeedbackBuffer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFinish() {
        GL11.glFinish();
        LabyDebugContext.glError("glFinish", new Object[0]);
    }

    public static void glFlush() {
        GL11.glFlush();
        LabyDebugContext.glError("glFlush", new Object[0]);
    }

    public static void glFogi(int p0, int p1) {
        GL11.glFogi(p0, p1);
        LabyDebugContext.glError("glFogi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void nglFogiv(int p0, long p1) {
        GL11.nglFogiv(p0, p1);
        LabyDebugContext.glError("nglFogiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glFogiv(int p0, IntBuffer p1) {
        GL11.glFogiv(p0, p1);
        LabyDebugContext.glError("glFogiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFogf(int p0, float p1) {
        GL11.glFogf(p0, p1);
        LabyDebugContext.glError("glFogf", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void nglFogfv(int p0, long p1) {
        GL11.nglFogfv(p0, p1);
        LabyDebugContext.glError("nglFogfv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glFogfv(int p0, FloatBuffer p1) {
        GL11.glFogfv(p0, p1);
        LabyDebugContext.glError("glFogfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFrontFace(int p0) {
        GL11.glFrontFace(p0);
        LabyDebugContext.glError("glFrontFace", "p0", Integer.valueOf(p0));
    }

    public static int glGenLists(int p0) {
        int returnType = GL11.glGenLists(p0);
        LabyDebugContext.glError("glGenLists", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGenTextures(int p0, long p1) {
        GL11.nglGenTextures(p0, p1);
        LabyDebugContext.glError("nglGenTextures", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGenTextures(IntBuffer p0) {
        GL11.glGenTextures(p0);
        LabyDebugContext.glError("glGenTextures", "p0", p0);
    }

    public static int glGenTextures() {
        int returnType = GL11.glGenTextures();
        LabyDebugContext.glError("glGenTextures", new Object[0]);
        return returnType;
    }

    public static void nglDeleteTextures(int p0, long p1) {
        GL11.nglDeleteTextures(p0, p1);
        LabyDebugContext.glError("nglDeleteTextures", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glDeleteTextures(IntBuffer p0) {
        GL11.glDeleteTextures(p0);
        LabyDebugContext.glError("glDeleteTextures", "p0", p0);
    }

    public static void glDeleteTextures(int p0) {
        GL11.glDeleteTextures(p0);
        LabyDebugContext.glError("glDeleteTextures", "p0", Integer.valueOf(p0));
    }

    public static void nglGetClipPlane(int p0, long p1) {
        GL11.nglGetClipPlane(p0, p1);
        LabyDebugContext.glError("nglGetClipPlane", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetClipPlane(int p0, DoubleBuffer p1) {
        GL11.glGetClipPlane(p0, p1);
        LabyDebugContext.glError("glGetClipPlane", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglGetBooleanv(int p0, long p1) {
        GL11.nglGetBooleanv(p0, p1);
        LabyDebugContext.glError("nglGetBooleanv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetBooleanv(int p0, ByteBuffer p1) {
        GL11.glGetBooleanv(p0, p1);
        LabyDebugContext.glError("glGetBooleanv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static boolean glGetBoolean(int p0) {
        boolean returnType = GL11.glGetBoolean(p0);
        LabyDebugContext.glError("glGetBoolean", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetFloatv(int p0, long p1) {
        GL11.nglGetFloatv(p0, p1);
        LabyDebugContext.glError("nglGetFloatv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetFloatv(int p0, FloatBuffer p1) {
        GL11.glGetFloatv(p0, p1);
        LabyDebugContext.glError("glGetFloatv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static float glGetFloat(int p0) {
        float returnType = GL11.glGetFloat(p0);
        LabyDebugContext.glError("glGetFloat", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetIntegerv(int p0, long p1) {
        GL11.nglGetIntegerv(p0, p1);
        LabyDebugContext.glError("nglGetIntegerv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetIntegerv(int p0, IntBuffer p1) {
        GL11.glGetIntegerv(p0, p1);
        LabyDebugContext.glError("glGetIntegerv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static int glGetInteger(int p0) {
        int returnType = GL11.glGetInteger(p0);
        LabyDebugContext.glError("glGetInteger", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetDoublev(int p0, long p1) {
        GL11.nglGetDoublev(p0, p1);
        LabyDebugContext.glError("nglGetDoublev", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetDoublev(int p0, DoubleBuffer p1) {
        GL11.glGetDoublev(p0, p1);
        LabyDebugContext.glError("glGetDoublev", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static double glGetDouble(int p0) {
        double returnType = GL11.glGetDouble(p0);
        LabyDebugContext.glError("glGetDouble", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static int glGetError() {
        int returnType = GL11.glGetError();
        LabyDebugContext.glError("glGetError", new Object[0]);
        return returnType;
    }

    public static void nglGetLightiv(int p0, int p1, long p2) {
        GL11.nglGetLightiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetLightiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetLightiv(int p0, int p1, IntBuffer p2) {
        GL11.glGetLightiv(p0, p1, p2);
        LabyDebugContext.glError("glGetLightiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetLighti(int p0, int p1) {
        int returnType = GL11.glGetLighti(p0, p1);
        LabyDebugContext.glError("glGetLighti", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetLightfv(int p0, int p1, long p2) {
        GL11.nglGetLightfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetLightfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetLightfv(int p0, int p1, FloatBuffer p2) {
        GL11.glGetLightfv(p0, p1, p2);
        LabyDebugContext.glError("glGetLightfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetLightf(int p0, int p1) {
        float returnType = GL11.glGetLightf(p0, p1);
        LabyDebugContext.glError("glGetLightf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetMapiv(int p0, int p1, long p2) {
        GL11.nglGetMapiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMapiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMapiv(int p0, int p1, IntBuffer p2) {
        GL11.glGetMapiv(p0, p1, p2);
        LabyDebugContext.glError("glGetMapiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetMapi(int p0, int p1) {
        int returnType = GL11.glGetMapi(p0, p1);
        LabyDebugContext.glError("glGetMapi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetMapfv(int p0, int p1, long p2) {
        GL11.nglGetMapfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMapfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMapfv(int p0, int p1, FloatBuffer p2) {
        GL11.glGetMapfv(p0, p1, p2);
        LabyDebugContext.glError("glGetMapfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetMapf(int p0, int p1) {
        float returnType = GL11.glGetMapf(p0, p1);
        LabyDebugContext.glError("glGetMapf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetMapdv(int p0, int p1, long p2) {
        GL11.nglGetMapdv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMapdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMapdv(int p0, int p1, DoubleBuffer p2) {
        GL11.glGetMapdv(p0, p1, p2);
        LabyDebugContext.glError("glGetMapdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetMapd(int p0, int p1) {
        double returnType = GL11.glGetMapd(p0, p1);
        LabyDebugContext.glError("glGetMapd", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetMaterialiv(int p0, int p1, long p2) {
        GL11.nglGetMaterialiv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMaterialiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMaterialiv(int p0, int p1, IntBuffer p2) {
        GL11.glGetMaterialiv(p0, p1, p2);
        LabyDebugContext.glError("glGetMaterialiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetMaterialfv(int p0, int p1, long p2) {
        GL11.nglGetMaterialfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetMaterialfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetMaterialfv(int p0, int p1, FloatBuffer p2) {
        GL11.glGetMaterialfv(p0, p1, p2);
        LabyDebugContext.glError("glGetMaterialfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglGetPixelMapfv(int p0, long p1) {
        GL11.nglGetPixelMapfv(p0, p1);
        LabyDebugContext.glError("nglGetPixelMapfv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPixelMapfv(int p0, FloatBuffer p1) {
        GL11.glGetPixelMapfv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPixelMapfv(int p0, long p1) {
        GL11.glGetPixelMapfv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapfv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void nglGetPixelMapusv(int p0, long p1) {
        GL11.nglGetPixelMapusv(p0, p1);
        LabyDebugContext.glError("nglGetPixelMapusv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPixelMapusv(int p0, ShortBuffer p1) {
        GL11.glGetPixelMapusv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapusv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPixelMapusv(int p0, long p1) {
        GL11.glGetPixelMapusv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapusv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void nglGetPixelMapuiv(int p0, long p1) {
        GL11.nglGetPixelMapuiv(p0, p1);
        LabyDebugContext.glError("nglGetPixelMapuiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPixelMapuiv(int p0, IntBuffer p1) {
        GL11.glGetPixelMapuiv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPixelMapuiv(int p0, long p1) {
        GL11.glGetPixelMapuiv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapuiv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void nglGetPointerv(int p0, long p1) {
        GL11.nglGetPointerv(p0, p1);
        LabyDebugContext.glError("nglGetPointerv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glGetPointerv(int p0, PointerBuffer p1) {
        GL11.glGetPointerv(p0, p1);
        LabyDebugContext.glError("glGetPointerv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static long glGetPointer(int p0) {
        long returnType = GL11.glGetPointer(p0);
        LabyDebugContext.glError("glGetPointer", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetPolygonStipple(long p0) {
        GL11.nglGetPolygonStipple(p0);
        LabyDebugContext.glError("nglGetPolygonStipple", "p0", Long.valueOf(p0));
    }

    public static void glGetPolygonStipple(ByteBuffer p0) {
        GL11.glGetPolygonStipple(p0);
        LabyDebugContext.glError("glGetPolygonStipple", "p0", p0);
    }

    public static void glGetPolygonStipple(long p0) {
        GL11.glGetPolygonStipple(p0);
        LabyDebugContext.glError("glGetPolygonStipple", "p0", Long.valueOf(p0));
    }

    public static long nglGetString(int p0) {
        long returnType = GL11.nglGetString(p0);
        LabyDebugContext.glError("nglGetString", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static String glGetString(int p0) {
        String returnType = GL11.glGetString(p0);
        LabyDebugContext.glError("glGetString", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void nglGetTexEnviv(int p0, int p1, long p2) {
        GL11.nglGetTexEnviv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexEnviv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexEnviv(int p0, int p1, IntBuffer p2) {
        GL11.glGetTexEnviv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexEnviv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTexEnvi(int p0, int p1) {
        int returnType = GL11.glGetTexEnvi(p0, p1);
        LabyDebugContext.glError("glGetTexEnvi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexEnvfv(int p0, int p1, long p2) {
        GL11.nglGetTexEnvfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexEnvfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexEnvfv(int p0, int p1, FloatBuffer p2) {
        GL11.glGetTexEnvfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexEnvfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetTexEnvf(int p0, int p1) {
        float returnType = GL11.glGetTexEnvf(p0, p1);
        LabyDebugContext.glError("glGetTexEnvf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexGeniv(int p0, int p1, long p2) {
        GL11.nglGetTexGeniv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexGeniv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexGeniv(int p0, int p1, IntBuffer p2) {
        GL11.glGetTexGeniv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexGeniv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTexGeni(int p0, int p1) {
        int returnType = GL11.glGetTexGeni(p0, p1);
        LabyDebugContext.glError("glGetTexGeni", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexGenfv(int p0, int p1, long p2) {
        GL11.nglGetTexGenfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexGenfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexGenfv(int p0, int p1, FloatBuffer p2) {
        GL11.glGetTexGenfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexGenfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetTexGenf(int p0, int p1) {
        float returnType = GL11.glGetTexGenf(p0, p1);
        LabyDebugContext.glError("glGetTexGenf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexGendv(int p0, int p1, long p2) {
        GL11.nglGetTexGendv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexGendv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexGendv(int p0, int p1, DoubleBuffer p2) {
        GL11.glGetTexGendv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexGendv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static double glGetTexGend(int p0, int p1) {
        double returnType = GL11.glGetTexGend(p0, p1);
        LabyDebugContext.glError("glGetTexGend", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexImage(int p0, int p1, int p2, int p3, long p4) {
        GL11.nglGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("nglGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, ByteBuffer p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, long p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Long.valueOf(p4));
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, ShortBuffer p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, IntBuffer p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, FloatBuffer p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, DoubleBuffer p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void nglGetTexLevelParameteriv(int p0, int p1, int p2, long p3) {
        GL11.nglGetTexLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTexLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTexLevelParameteriv(int p0, int p1, int p2, IntBuffer p3) {
        GL11.glGetTexLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static int glGetTexLevelParameteri(int p0, int p1, int p2) {
        int returnType = GL11.glGetTexLevelParameteri(p0, p1, p2);
        LabyDebugContext.glError("glGetTexLevelParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTexLevelParameterfv(int p0, int p1, int p2, long p3) {
        GL11.nglGetTexLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("nglGetTexLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glGetTexLevelParameterfv(int p0, int p1, int p2, FloatBuffer p3) {
        GL11.glGetTexLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static float glGetTexLevelParameterf(int p0, int p1, int p2) {
        float returnType = GL11.glGetTexLevelParameterf(p0, p1, p2);
        LabyDebugContext.glError("glGetTexLevelParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
        return returnType;
    }

    public static void nglGetTexParameteriv(int p0, int p1, long p2) {
        GL11.nglGetTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexParameteriv(int p0, int p1, IntBuffer p2) {
        GL11.glGetTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static int glGetTexParameteri(int p0, int p1) {
        int returnType = GL11.glGetTexParameteri(p0, p1);
        LabyDebugContext.glError("glGetTexParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void nglGetTexParameterfv(int p0, int p1, long p2) {
        GL11.nglGetTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglGetTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glGetTexParameterfv(int p0, int p1, FloatBuffer p2) {
        GL11.glGetTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static float glGetTexParameterf(int p0, int p1) {
        float returnType = GL11.glGetTexParameterf(p0, p1);
        LabyDebugContext.glError("glGetTexParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
        return returnType;
    }

    public static void glHint(int p0, int p1) {
        GL11.glHint(p0, p1);
        LabyDebugContext.glError("glHint", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glIndexi(int p0) {
        GL11.glIndexi(p0);
        LabyDebugContext.glError("glIndexi", "p0", Integer.valueOf(p0));
    }

    public static void glIndexub(byte p0) {
        GL11.glIndexub(p0);
        LabyDebugContext.glError("glIndexub", "p0", Byte.valueOf(p0));
    }

    public static void glIndexs(short p0) {
        GL11.glIndexs(p0);
        LabyDebugContext.glError("glIndexs", "p0", Short.valueOf(p0));
    }

    public static void glIndexf(float p0) {
        GL11.glIndexf(p0);
        LabyDebugContext.glError("glIndexf", "p0", Float.valueOf(p0));
    }

    public static void glIndexd(double p0) {
        GL11.glIndexd(p0);
        LabyDebugContext.glError("glIndexd", "p0", Double.valueOf(p0));
    }

    public static void nglIndexiv(long p0) {
        GL11.nglIndexiv(p0);
        LabyDebugContext.glError("nglIndexiv", "p0", Long.valueOf(p0));
    }

    public static void glIndexiv(IntBuffer p0) {
        GL11.glIndexiv(p0);
        LabyDebugContext.glError("glIndexiv", "p0", p0);
    }

    public static void nglIndexubv(long p0) {
        GL11.nglIndexubv(p0);
        LabyDebugContext.glError("nglIndexubv", "p0", Long.valueOf(p0));
    }

    public static void glIndexubv(ByteBuffer p0) {
        GL11.glIndexubv(p0);
        LabyDebugContext.glError("glIndexubv", "p0", p0);
    }

    public static void nglIndexsv(long p0) {
        GL11.nglIndexsv(p0);
        LabyDebugContext.glError("nglIndexsv", "p0", Long.valueOf(p0));
    }

    public static void glIndexsv(ShortBuffer p0) {
        GL11.glIndexsv(p0);
        LabyDebugContext.glError("glIndexsv", "p0", p0);
    }

    public static void nglIndexfv(long p0) {
        GL11.nglIndexfv(p0);
        LabyDebugContext.glError("nglIndexfv", "p0", Long.valueOf(p0));
    }

    public static void glIndexfv(FloatBuffer p0) {
        GL11.glIndexfv(p0);
        LabyDebugContext.glError("glIndexfv", "p0", p0);
    }

    public static void nglIndexdv(long p0) {
        GL11.nglIndexdv(p0);
        LabyDebugContext.glError("nglIndexdv", "p0", Long.valueOf(p0));
    }

    public static void glIndexdv(DoubleBuffer p0) {
        GL11.glIndexdv(p0);
        LabyDebugContext.glError("glIndexdv", "p0", p0);
    }

    public static void glIndexMask(int p0) {
        GL11.glIndexMask(p0);
        LabyDebugContext.glError("glIndexMask", "p0", Integer.valueOf(p0));
    }

    public static void nglIndexPointer(int p0, int p1, long p2) {
        GL11.nglIndexPointer(p0, p1, p2);
        LabyDebugContext.glError("nglIndexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glIndexPointer(int p0, int p1, ByteBuffer p2) {
        GL11.glIndexPointer(p0, p1, p2);
        LabyDebugContext.glError("glIndexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glIndexPointer(int p0, int p1, long p2) {
        GL11.glIndexPointer(p0, p1, p2);
        LabyDebugContext.glError("glIndexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glIndexPointer(int p0, ByteBuffer p1) {
        GL11.glIndexPointer(p0, p1);
        LabyDebugContext.glError("glIndexPointer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glIndexPointer(int p0, ShortBuffer p1) {
        GL11.glIndexPointer(p0, p1);
        LabyDebugContext.glError("glIndexPointer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glIndexPointer(int p0, IntBuffer p1) {
        GL11.glIndexPointer(p0, p1);
        LabyDebugContext.glError("glIndexPointer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glInitNames() {
        GL11.glInitNames();
        LabyDebugContext.glError("glInitNames", new Object[0]);
    }

    public static void nglInterleavedArrays(int p0, int p1, long p2) {
        GL11.nglInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("nglInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInterleavedArrays(int p0, int p1, ByteBuffer p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, long p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glInterleavedArrays(int p0, int p1, ShortBuffer p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, IntBuffer p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, FloatBuffer p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, DoubleBuffer p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static boolean glIsEnabled(int p0) {
        boolean returnType = GL11.glIsEnabled(p0);
        LabyDebugContext.glError("glIsEnabled", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static boolean glIsList(int p0) {
        boolean returnType = GL11.glIsList(p0);
        LabyDebugContext.glError("glIsList", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static boolean glIsTexture(int p0) {
        boolean returnType = GL11.glIsTexture(p0);
        LabyDebugContext.glError("glIsTexture", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glLightModeli(int p0, int p1) {
        GL11.glLightModeli(p0, p1);
        LabyDebugContext.glError("glLightModeli", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glLightModelf(int p0, float p1) {
        GL11.glLightModelf(p0, p1);
        LabyDebugContext.glError("glLightModelf", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void nglLightModeliv(int p0, long p1) {
        GL11.nglLightModeliv(p0, p1);
        LabyDebugContext.glError("nglLightModeliv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glLightModeliv(int p0, IntBuffer p1) {
        GL11.glLightModeliv(p0, p1);
        LabyDebugContext.glError("glLightModeliv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglLightModelfv(int p0, long p1) {
        GL11.nglLightModelfv(p0, p1);
        LabyDebugContext.glError("nglLightModelfv", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glLightModelfv(int p0, FloatBuffer p1) {
        GL11.glLightModelfv(p0, p1);
        LabyDebugContext.glError("glLightModelfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glLighti(int p0, int p1, int p2) {
        GL11.glLighti(p0, p1, p2);
        LabyDebugContext.glError("glLighti", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glLightf(int p0, int p1, float p2) {
        GL11.glLightf(p0, p1, p2);
        LabyDebugContext.glError("glLightf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglLightiv(int p0, int p1, long p2) {
        GL11.nglLightiv(p0, p1, p2);
        LabyDebugContext.glError("nglLightiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glLightiv(int p0, int p1, IntBuffer p2) {
        GL11.glLightiv(p0, p1, p2);
        LabyDebugContext.glError("glLightiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglLightfv(int p0, int p1, long p2) {
        GL11.nglLightfv(p0, p1, p2);
        LabyDebugContext.glError("nglLightfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glLightfv(int p0, int p1, FloatBuffer p2) {
        GL11.glLightfv(p0, p1, p2);
        LabyDebugContext.glError("glLightfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glLineStipple(int p0, short p1) {
        GL11.glLineStipple(p0, p1);
        LabyDebugContext.glError("glLineStipple", "p0", Integer.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glLineWidth(float p0) {
        GL11.glLineWidth(p0);
        LabyDebugContext.glError("glLineWidth", "p0", Float.valueOf(p0));
    }

    public static void glListBase(int p0) {
        GL11.glListBase(p0);
        LabyDebugContext.glError("glListBase", "p0", Integer.valueOf(p0));
    }

    public static void nglLoadMatrixf(long p0) {
        GL11.nglLoadMatrixf(p0);
        LabyDebugContext.glError("nglLoadMatrixf", "p0", Long.valueOf(p0));
    }

    public static void glLoadMatrixf(FloatBuffer p0) {
        GL11.glLoadMatrixf(p0);
        LabyDebugContext.glError("glLoadMatrixf", "p0", p0);
    }

    public static void nglLoadMatrixd(long p0) {
        GL11.nglLoadMatrixd(p0);
        LabyDebugContext.glError("nglLoadMatrixd", "p0", Long.valueOf(p0));
    }

    public static void glLoadMatrixd(DoubleBuffer p0) {
        GL11.glLoadMatrixd(p0);
        LabyDebugContext.glError("glLoadMatrixd", "p0", p0);
    }

    public static void glLoadIdentity() {
        GL11.glLoadIdentity();
        LabyDebugContext.glError("glLoadIdentity", new Object[0]);
    }

    public static void glLoadName(int p0) {
        GL11.glLoadName(p0);
        LabyDebugContext.glError("glLoadName", "p0", Integer.valueOf(p0));
    }

    public static void glLogicOp(int p0) {
        GL11.glLogicOp(p0);
        LabyDebugContext.glError("glLogicOp", "p0", Integer.valueOf(p0));
    }

    public static void nglMap1f(int p0, float p1, float p2, int p3, int p4, long p5) {
        GL11.nglMap1f(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglMap1f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glMap1f(int p0, float p1, float p2, int p3, int p4, FloatBuffer p5) {
        GL11.glMap1f(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMap1f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglMap1d(int p0, double p1, double p2, int p3, int p4, long p5) {
        GL11.nglMap1d(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("nglMap1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Long.valueOf(p5));
    }

    public static void glMap1d(int p0, double p1, double p2, int p3, int p4, DoubleBuffer p5) {
        GL11.glMap1d(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMap1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void nglMap2f(int p0, float p1, float p2, int p3, int p4, float p5, float p6, int p7, int p8, long p9) {
        GL11.nglMap2f(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("nglMap2f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Long.valueOf(p9));
    }

    public static void glMap2f(int p0, float p1, float p2, int p3, int p4, float p5, float p6, int p7, int p8, FloatBuffer p9) {
        GL11.glMap2f(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glMap2f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void nglMap2d(int p0, double p1, double p2, int p3, int p4, double p5, double p6, int p7, int p8, long p9) {
        GL11.nglMap2d(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("nglMap2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Double.valueOf(p5), "p6", Double.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", Long.valueOf(p9));
    }

    public static void glMap2d(int p0, double p1, double p2, int p3, int p4, double p5, double p6, int p7, int p8, DoubleBuffer p9) {
        GL11.glMap2d(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glMap2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Double.valueOf(p5), "p6", Double.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void glMapGrid1f(int p0, float p1, float p2) {
        GL11.glMapGrid1f(p0, p1, p2);
        LabyDebugContext.glError("glMapGrid1f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glMapGrid1d(int p0, double p1, double p2) {
        GL11.glMapGrid1d(p0, p1, p2);
        LabyDebugContext.glError("glMapGrid1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glMapGrid2f(int p0, float p1, float p2, int p3, float p4, float p5) {
        GL11.glMapGrid2f(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMapGrid2f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Float.valueOf(p4), "p5", Float.valueOf(p5));
    }

    public static void glMapGrid2d(int p0, double p1, double p2, int p3, double p4, double p5) {
        GL11.glMapGrid2d(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMapGrid2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void glMateriali(int p0, int p1, int p2) {
        GL11.glMateriali(p0, p1, p2);
        LabyDebugContext.glError("glMateriali", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glMaterialf(int p0, int p1, float p2) {
        GL11.glMaterialf(p0, p1, p2);
        LabyDebugContext.glError("glMaterialf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglMaterialiv(int p0, int p1, long p2) {
        GL11.nglMaterialiv(p0, p1, p2);
        LabyDebugContext.glError("nglMaterialiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glMaterialiv(int p0, int p1, IntBuffer p2) {
        GL11.glMaterialiv(p0, p1, p2);
        LabyDebugContext.glError("glMaterialiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglMaterialfv(int p0, int p1, long p2) {
        GL11.nglMaterialfv(p0, p1, p2);
        LabyDebugContext.glError("nglMaterialfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glMaterialfv(int p0, int p1, FloatBuffer p2) {
        GL11.glMaterialfv(p0, p1, p2);
        LabyDebugContext.glError("glMaterialfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMatrixMode(int p0) {
        GL11.glMatrixMode(p0);
        LabyDebugContext.glError("glMatrixMode", "p0", Integer.valueOf(p0));
    }

    public static void nglMultMatrixf(long p0) {
        GL11.nglMultMatrixf(p0);
        LabyDebugContext.glError("nglMultMatrixf", "p0", Long.valueOf(p0));
    }

    public static void glMultMatrixf(FloatBuffer p0) {
        GL11.glMultMatrixf(p0);
        LabyDebugContext.glError("glMultMatrixf", "p0", p0);
    }

    public static void nglMultMatrixd(long p0) {
        GL11.nglMultMatrixd(p0);
        LabyDebugContext.glError("nglMultMatrixd", "p0", Long.valueOf(p0));
    }

    public static void glMultMatrixd(DoubleBuffer p0) {
        GL11.glMultMatrixd(p0);
        LabyDebugContext.glError("glMultMatrixd", "p0", p0);
    }

    public static void glFrustum(double p0, double p1, double p2, double p3, double p4, double p5) {
        GL11.glFrustum(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glFrustum", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void glNewList(int p0, int p1) {
        GL11.glNewList(p0, p1);
        LabyDebugContext.glError("glNewList", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glEndList() {
        GL11.glEndList();
        LabyDebugContext.glError("glEndList", new Object[0]);
    }

    public static void glNormal3f(float p0, float p1, float p2) {
        GL11.glNormal3f(p0, p1, p2);
        LabyDebugContext.glError("glNormal3f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glNormal3b(byte p0, byte p1, byte p2) {
        GL11.glNormal3b(p0, p1, p2);
        LabyDebugContext.glError("glNormal3b", "p0", Byte.valueOf(p0), "p1", Byte.valueOf(p1), "p2", Byte.valueOf(p2));
    }

    public static void glNormal3s(short p0, short p1, short p2) {
        GL11.glNormal3s(p0, p1, p2);
        LabyDebugContext.glError("glNormal3s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glNormal3i(int p0, int p1, int p2) {
        GL11.glNormal3i(p0, p1, p2);
        LabyDebugContext.glError("glNormal3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glNormal3d(double p0, double p1, double p2) {
        GL11.glNormal3d(p0, p1, p2);
        LabyDebugContext.glError("glNormal3d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglNormal3fv(long p0) {
        GL11.nglNormal3fv(p0);
        LabyDebugContext.glError("nglNormal3fv", "p0", Long.valueOf(p0));
    }

    public static void glNormal3fv(FloatBuffer p0) {
        GL11.glNormal3fv(p0);
        LabyDebugContext.glError("glNormal3fv", "p0", p0);
    }

    public static void nglNormal3bv(long p0) {
        GL11.nglNormal3bv(p0);
        LabyDebugContext.glError("nglNormal3bv", "p0", Long.valueOf(p0));
    }

    public static void glNormal3bv(ByteBuffer p0) {
        GL11.glNormal3bv(p0);
        LabyDebugContext.glError("glNormal3bv", "p0", p0);
    }

    public static void nglNormal3sv(long p0) {
        GL11.nglNormal3sv(p0);
        LabyDebugContext.glError("nglNormal3sv", "p0", Long.valueOf(p0));
    }

    public static void glNormal3sv(ShortBuffer p0) {
        GL11.glNormal3sv(p0);
        LabyDebugContext.glError("glNormal3sv", "p0", p0);
    }

    public static void nglNormal3iv(long p0) {
        GL11.nglNormal3iv(p0);
        LabyDebugContext.glError("nglNormal3iv", "p0", Long.valueOf(p0));
    }

    public static void glNormal3iv(IntBuffer p0) {
        GL11.glNormal3iv(p0);
        LabyDebugContext.glError("glNormal3iv", "p0", p0);
    }

    public static void nglNormal3dv(long p0) {
        GL11.nglNormal3dv(p0);
        LabyDebugContext.glError("nglNormal3dv", "p0", Long.valueOf(p0));
    }

    public static void glNormal3dv(DoubleBuffer p0) {
        GL11.glNormal3dv(p0);
        LabyDebugContext.glError("glNormal3dv", "p0", p0);
    }

    public static void nglNormalPointer(int p0, int p1, long p2) {
        GL11.nglNormalPointer(p0, p1, p2);
        LabyDebugContext.glError("nglNormalPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glNormalPointer(int p0, int p1, ByteBuffer p2) {
        GL11.glNormalPointer(p0, p1, p2);
        LabyDebugContext.glError("glNormalPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNormalPointer(int p0, int p1, long p2) {
        GL11.glNormalPointer(p0, p1, p2);
        LabyDebugContext.glError("glNormalPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glNormalPointer(int p0, int p1, ShortBuffer p2) {
        GL11.glNormalPointer(p0, p1, p2);
        LabyDebugContext.glError("glNormalPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNormalPointer(int p0, int p1, IntBuffer p2) {
        GL11.glNormalPointer(p0, p1, p2);
        LabyDebugContext.glError("glNormalPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glNormalPointer(int p0, int p1, FloatBuffer p2) {
        GL11.glNormalPointer(p0, p1, p2);
        LabyDebugContext.glError("glNormalPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glOrtho(double p0, double p1, double p2, double p3, double p4, double p5) {
        GL11.glOrtho(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glOrtho", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3), "p4", Double.valueOf(p4), "p5", Double.valueOf(p5));
    }

    public static void glPassThrough(float p0) {
        GL11.glPassThrough(p0);
        LabyDebugContext.glError("glPassThrough", "p0", Float.valueOf(p0));
    }

    public static void nglPixelMapfv(int p0, int p1, long p2) {
        GL11.nglPixelMapfv(p0, p1, p2);
        LabyDebugContext.glError("nglPixelMapfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelMapfv(int p0, int p1, long p2) {
        GL11.glPixelMapfv(p0, p1, p2);
        LabyDebugContext.glError("glPixelMapfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelMapfv(int p0, FloatBuffer p1) {
        GL11.glPixelMapfv(p0, p1);
        LabyDebugContext.glError("glPixelMapfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglPixelMapusv(int p0, int p1, long p2) {
        GL11.nglPixelMapusv(p0, p1, p2);
        LabyDebugContext.glError("nglPixelMapusv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelMapusv(int p0, int p1, long p2) {
        GL11.glPixelMapusv(p0, p1, p2);
        LabyDebugContext.glError("glPixelMapusv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelMapusv(int p0, ShortBuffer p1) {
        GL11.glPixelMapusv(p0, p1);
        LabyDebugContext.glError("glPixelMapusv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void nglPixelMapuiv(int p0, int p1, long p2) {
        GL11.nglPixelMapuiv(p0, p1, p2);
        LabyDebugContext.glError("nglPixelMapuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelMapuiv(int p0, int p1, long p2) {
        GL11.glPixelMapuiv(p0, p1, p2);
        LabyDebugContext.glError("glPixelMapuiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPixelMapuiv(int p0, IntBuffer p1) {
        GL11.glPixelMapuiv(p0, p1);
        LabyDebugContext.glError("glPixelMapuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPixelStorei(int p0, int p1) {
        GL11.glPixelStorei(p0, p1);
        LabyDebugContext.glError("glPixelStorei", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glPixelStoref(int p0, float p1) {
        GL11.glPixelStoref(p0, p1);
        LabyDebugContext.glError("glPixelStoref", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glPixelTransferi(int p0, int p1) {
        GL11.glPixelTransferi(p0, p1);
        LabyDebugContext.glError("glPixelTransferi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glPixelTransferf(int p0, float p1) {
        GL11.glPixelTransferf(p0, p1);
        LabyDebugContext.glError("glPixelTransferf", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glPixelZoom(float p0, float p1) {
        GL11.glPixelZoom(p0, p1);
        LabyDebugContext.glError("glPixelZoom", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glPointSize(float p0) {
        GL11.glPointSize(p0);
        LabyDebugContext.glError("glPointSize", "p0", Float.valueOf(p0));
    }

    public static void glPolygonMode(int p0, int p1) {
        GL11.glPolygonMode(p0, p1);
        LabyDebugContext.glError("glPolygonMode", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glPolygonOffset(float p0, float p1) {
        GL11.glPolygonOffset(p0, p1);
        LabyDebugContext.glError("glPolygonOffset", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void nglPolygonStipple(long p0) {
        GL11.nglPolygonStipple(p0);
        LabyDebugContext.glError("nglPolygonStipple", "p0", Long.valueOf(p0));
    }

    public static void glPolygonStipple(ByteBuffer p0) {
        GL11.glPolygonStipple(p0);
        LabyDebugContext.glError("glPolygonStipple", "p0", p0);
    }

    public static void glPolygonStipple(long p0) {
        GL11.glPolygonStipple(p0);
        LabyDebugContext.glError("glPolygonStipple", "p0", Long.valueOf(p0));
    }

    public static void glPushAttrib(int p0) {
        GL11.glPushAttrib(p0);
        LabyDebugContext.glError("glPushAttrib", "p0", Integer.valueOf(p0));
    }

    public static void glPushClientAttrib(int p0) {
        GL11.glPushClientAttrib(p0);
        LabyDebugContext.glError("glPushClientAttrib", "p0", Integer.valueOf(p0));
    }

    public static void glPopAttrib() {
        GL11.glPopAttrib();
        LabyDebugContext.glError("glPopAttrib", new Object[0]);
    }

    public static void glPopClientAttrib() {
        GL11.glPopClientAttrib();
        LabyDebugContext.glError("glPopClientAttrib", new Object[0]);
    }

    public static void glPopMatrix() {
        GL11.glPopMatrix();
        LabyDebugContext.glError("glPopMatrix", new Object[0]);
    }

    public static void glPopName() {
        GL11.glPopName();
        LabyDebugContext.glError("glPopName", new Object[0]);
    }

    public static void nglPrioritizeTextures(int p0, long p1, long p2) {
        GL11.nglPrioritizeTextures(p0, p1, p2);
        LabyDebugContext.glError("nglPrioritizeTextures", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glPrioritizeTextures(IntBuffer p0, FloatBuffer p1) {
        GL11.glPrioritizeTextures(p0, p1);
        LabyDebugContext.glError("glPrioritizeTextures", "p0", p0, "p1", p1);
    }

    public static void glPushMatrix() {
        GL11.glPushMatrix();
        LabyDebugContext.glError("glPushMatrix", new Object[0]);
    }

    public static void glPushName(int p0) {
        GL11.glPushName(p0);
        LabyDebugContext.glError("glPushName", "p0", Integer.valueOf(p0));
    }

    public static void glRasterPos2i(int p0, int p1) {
        GL11.glRasterPos2i(p0, p1);
        LabyDebugContext.glError("glRasterPos2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glRasterPos2s(short p0, short p1) {
        GL11.glRasterPos2s(p0, p1);
        LabyDebugContext.glError("glRasterPos2s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glRasterPos2f(float p0, float p1) {
        GL11.glRasterPos2f(p0, p1);
        LabyDebugContext.glError("glRasterPos2f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glRasterPos2d(double p0, double p1) {
        GL11.glRasterPos2d(p0, p1);
        LabyDebugContext.glError("glRasterPos2d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void nglRasterPos2iv(long p0) {
        GL11.nglRasterPos2iv(p0);
        LabyDebugContext.glError("nglRasterPos2iv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos2iv(IntBuffer p0) {
        GL11.glRasterPos2iv(p0);
        LabyDebugContext.glError("glRasterPos2iv", "p0", p0);
    }

    public static void nglRasterPos2sv(long p0) {
        GL11.nglRasterPos2sv(p0);
        LabyDebugContext.glError("nglRasterPos2sv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos2sv(ShortBuffer p0) {
        GL11.glRasterPos2sv(p0);
        LabyDebugContext.glError("glRasterPos2sv", "p0", p0);
    }

    public static void nglRasterPos2fv(long p0) {
        GL11.nglRasterPos2fv(p0);
        LabyDebugContext.glError("nglRasterPos2fv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos2fv(FloatBuffer p0) {
        GL11.glRasterPos2fv(p0);
        LabyDebugContext.glError("glRasterPos2fv", "p0", p0);
    }

    public static void nglRasterPos2dv(long p0) {
        GL11.nglRasterPos2dv(p0);
        LabyDebugContext.glError("nglRasterPos2dv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos2dv(DoubleBuffer p0) {
        GL11.glRasterPos2dv(p0);
        LabyDebugContext.glError("glRasterPos2dv", "p0", p0);
    }

    public static void glRasterPos3i(int p0, int p1, int p2) {
        GL11.glRasterPos3i(p0, p1, p2);
        LabyDebugContext.glError("glRasterPos3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glRasterPos3s(short p0, short p1, short p2) {
        GL11.glRasterPos3s(p0, p1, p2);
        LabyDebugContext.glError("glRasterPos3s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glRasterPos3f(float p0, float p1, float p2) {
        GL11.glRasterPos3f(p0, p1, p2);
        LabyDebugContext.glError("glRasterPos3f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glRasterPos3d(double p0, double p1, double p2) {
        GL11.glRasterPos3d(p0, p1, p2);
        LabyDebugContext.glError("glRasterPos3d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglRasterPos3iv(long p0) {
        GL11.nglRasterPos3iv(p0);
        LabyDebugContext.glError("nglRasterPos3iv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos3iv(IntBuffer p0) {
        GL11.glRasterPos3iv(p0);
        LabyDebugContext.glError("glRasterPos3iv", "p0", p0);
    }

    public static void nglRasterPos3sv(long p0) {
        GL11.nglRasterPos3sv(p0);
        LabyDebugContext.glError("nglRasterPos3sv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos3sv(ShortBuffer p0) {
        GL11.glRasterPos3sv(p0);
        LabyDebugContext.glError("glRasterPos3sv", "p0", p0);
    }

    public static void nglRasterPos3fv(long p0) {
        GL11.nglRasterPos3fv(p0);
        LabyDebugContext.glError("nglRasterPos3fv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos3fv(FloatBuffer p0) {
        GL11.glRasterPos3fv(p0);
        LabyDebugContext.glError("glRasterPos3fv", "p0", p0);
    }

    public static void nglRasterPos3dv(long p0) {
        GL11.nglRasterPos3dv(p0);
        LabyDebugContext.glError("nglRasterPos3dv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos3dv(DoubleBuffer p0) {
        GL11.glRasterPos3dv(p0);
        LabyDebugContext.glError("glRasterPos3dv", "p0", p0);
    }

    public static void glRasterPos4i(int p0, int p1, int p2, int p3) {
        GL11.glRasterPos4i(p0, p1, p2, p3);
        LabyDebugContext.glError("glRasterPos4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glRasterPos4s(short p0, short p1, short p2, short p3) {
        GL11.glRasterPos4s(p0, p1, p2, p3);
        LabyDebugContext.glError("glRasterPos4s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glRasterPos4f(float p0, float p1, float p2, float p3) {
        GL11.glRasterPos4f(p0, p1, p2, p3);
        LabyDebugContext.glError("glRasterPos4f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glRasterPos4d(double p0, double p1, double p2, double p3) {
        GL11.glRasterPos4d(p0, p1, p2, p3);
        LabyDebugContext.glError("glRasterPos4d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void nglRasterPos4iv(long p0) {
        GL11.nglRasterPos4iv(p0);
        LabyDebugContext.glError("nglRasterPos4iv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos4iv(IntBuffer p0) {
        GL11.glRasterPos4iv(p0);
        LabyDebugContext.glError("glRasterPos4iv", "p0", p0);
    }

    public static void nglRasterPos4sv(long p0) {
        GL11.nglRasterPos4sv(p0);
        LabyDebugContext.glError("nglRasterPos4sv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos4sv(ShortBuffer p0) {
        GL11.glRasterPos4sv(p0);
        LabyDebugContext.glError("glRasterPos4sv", "p0", p0);
    }

    public static void nglRasterPos4fv(long p0) {
        GL11.nglRasterPos4fv(p0);
        LabyDebugContext.glError("nglRasterPos4fv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos4fv(FloatBuffer p0) {
        GL11.glRasterPos4fv(p0);
        LabyDebugContext.glError("glRasterPos4fv", "p0", p0);
    }

    public static void nglRasterPos4dv(long p0) {
        GL11.nglRasterPos4dv(p0);
        LabyDebugContext.glError("nglRasterPos4dv", "p0", Long.valueOf(p0));
    }

    public static void glRasterPos4dv(DoubleBuffer p0) {
        GL11.glRasterPos4dv(p0);
        LabyDebugContext.glError("glRasterPos4dv", "p0", p0);
    }

    public static void glReadBuffer(int p0) {
        GL11.glReadBuffer(p0);
        LabyDebugContext.glError("glReadBuffer", "p0", Integer.valueOf(p0));
    }

    public static void nglReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11.nglReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glRecti(int p0, int p1, int p2, int p3) {
        GL11.glRecti(p0, p1, p2, p3);
        LabyDebugContext.glError("glRecti", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glRects(short p0, short p1, short p2, short p3) {
        GL11.glRects(p0, p1, p2, p3);
        LabyDebugContext.glError("glRects", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glRectf(float p0, float p1, float p2, float p3) {
        GL11.glRectf(p0, p1, p2, p3);
        LabyDebugContext.glError("glRectf", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glRectd(double p0, double p1, double p2, double p3) {
        GL11.glRectd(p0, p1, p2, p3);
        LabyDebugContext.glError("glRectd", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void nglRectiv(long p0, long p1) {
        GL11.nglRectiv(p0, p1);
        LabyDebugContext.glError("nglRectiv", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glRectiv(IntBuffer p0, IntBuffer p1) {
        GL11.glRectiv(p0, p1);
        LabyDebugContext.glError("glRectiv", "p0", p0, "p1", p1);
    }

    public static void nglRectsv(long p0, long p1) {
        GL11.nglRectsv(p0, p1);
        LabyDebugContext.glError("nglRectsv", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glRectsv(ShortBuffer p0, ShortBuffer p1) {
        GL11.glRectsv(p0, p1);
        LabyDebugContext.glError("glRectsv", "p0", p0, "p1", p1);
    }

    public static void nglRectfv(long p0, long p1) {
        GL11.nglRectfv(p0, p1);
        LabyDebugContext.glError("nglRectfv", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glRectfv(FloatBuffer p0, FloatBuffer p1) {
        GL11.glRectfv(p0, p1);
        LabyDebugContext.glError("glRectfv", "p0", p0, "p1", p1);
    }

    public static void nglRectdv(long p0, long p1) {
        GL11.nglRectdv(p0, p1);
        LabyDebugContext.glError("nglRectdv", "p0", Long.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glRectdv(DoubleBuffer p0, DoubleBuffer p1) {
        GL11.glRectdv(p0, p1);
        LabyDebugContext.glError("glRectdv", "p0", p0, "p1", p1);
    }

    public static int glRenderMode(int p0) {
        int returnType = GL11.glRenderMode(p0);
        LabyDebugContext.glError("glRenderMode", "p0", Integer.valueOf(p0));
        return returnType;
    }

    public static void glRotatef(float p0, float p1, float p2, float p3) {
        GL11.glRotatef(p0, p1, p2, p3);
        LabyDebugContext.glError("glRotatef", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glRotated(double p0, double p1, double p2, double p3) {
        GL11.glRotated(p0, p1, p2, p3);
        LabyDebugContext.glError("glRotated", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void glScalef(float p0, float p1, float p2) {
        GL11.glScalef(p0, p1, p2);
        LabyDebugContext.glError("glScalef", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glScaled(double p0, double p1, double p2) {
        GL11.glScaled(p0, p1, p2);
        LabyDebugContext.glError("glScaled", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glScissor(int p0, int p1, int p2, int p3) {
        GL11.glScissor(p0, p1, p2, p3);
        LabyDebugContext.glError("glScissor", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void nglSelectBuffer(int p0, long p1) {
        GL11.nglSelectBuffer(p0, p1);
        LabyDebugContext.glError("nglSelectBuffer", "p0", Integer.valueOf(p0), "p1", Long.valueOf(p1));
    }

    public static void glSelectBuffer(IntBuffer p0) {
        GL11.glSelectBuffer(p0);
        LabyDebugContext.glError("glSelectBuffer", "p0", p0);
    }

    public static void glShadeModel(int p0) {
        GL11.glShadeModel(p0);
        LabyDebugContext.glError("glShadeModel", "p0", Integer.valueOf(p0));
    }

    public static void glStencilFunc(int p0, int p1, int p2) {
        GL11.glStencilFunc(p0, p1, p2);
        LabyDebugContext.glError("glStencilFunc", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glStencilMask(int p0) {
        GL11.glStencilMask(p0);
        LabyDebugContext.glError("glStencilMask", "p0", Integer.valueOf(p0));
    }

    public static void glStencilOp(int p0, int p1, int p2) {
        GL11.glStencilOp(p0, p1, p2);
        LabyDebugContext.glError("glStencilOp", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTexCoord1f(float p0) {
        GL11.glTexCoord1f(p0);
        LabyDebugContext.glError("glTexCoord1f", "p0", Float.valueOf(p0));
    }

    public static void glTexCoord1s(short p0) {
        GL11.glTexCoord1s(p0);
        LabyDebugContext.glError("glTexCoord1s", "p0", Short.valueOf(p0));
    }

    public static void glTexCoord1i(int p0) {
        GL11.glTexCoord1i(p0);
        LabyDebugContext.glError("glTexCoord1i", "p0", Integer.valueOf(p0));
    }

    public static void glTexCoord1d(double p0) {
        GL11.glTexCoord1d(p0);
        LabyDebugContext.glError("glTexCoord1d", "p0", Double.valueOf(p0));
    }

    public static void nglTexCoord1fv(long p0) {
        GL11.nglTexCoord1fv(p0);
        LabyDebugContext.glError("nglTexCoord1fv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord1fv(FloatBuffer p0) {
        GL11.glTexCoord1fv(p0);
        LabyDebugContext.glError("glTexCoord1fv", "p0", p0);
    }

    public static void nglTexCoord1sv(long p0) {
        GL11.nglTexCoord1sv(p0);
        LabyDebugContext.glError("nglTexCoord1sv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord1sv(ShortBuffer p0) {
        GL11.glTexCoord1sv(p0);
        LabyDebugContext.glError("glTexCoord1sv", "p0", p0);
    }

    public static void nglTexCoord1iv(long p0) {
        GL11.nglTexCoord1iv(p0);
        LabyDebugContext.glError("nglTexCoord1iv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord1iv(IntBuffer p0) {
        GL11.glTexCoord1iv(p0);
        LabyDebugContext.glError("glTexCoord1iv", "p0", p0);
    }

    public static void nglTexCoord1dv(long p0) {
        GL11.nglTexCoord1dv(p0);
        LabyDebugContext.glError("nglTexCoord1dv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord1dv(DoubleBuffer p0) {
        GL11.glTexCoord1dv(p0);
        LabyDebugContext.glError("glTexCoord1dv", "p0", p0);
    }

    public static void glTexCoord2f(float p0, float p1) {
        GL11.glTexCoord2f(p0, p1);
        LabyDebugContext.glError("glTexCoord2f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glTexCoord2s(short p0, short p1) {
        GL11.glTexCoord2s(p0, p1);
        LabyDebugContext.glError("glTexCoord2s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glTexCoord2i(int p0, int p1) {
        GL11.glTexCoord2i(p0, p1);
        LabyDebugContext.glError("glTexCoord2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glTexCoord2d(double p0, double p1) {
        GL11.glTexCoord2d(p0, p1);
        LabyDebugContext.glError("glTexCoord2d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void nglTexCoord2fv(long p0) {
        GL11.nglTexCoord2fv(p0);
        LabyDebugContext.glError("nglTexCoord2fv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord2fv(FloatBuffer p0) {
        GL11.glTexCoord2fv(p0);
        LabyDebugContext.glError("glTexCoord2fv", "p0", p0);
    }

    public static void nglTexCoord2sv(long p0) {
        GL11.nglTexCoord2sv(p0);
        LabyDebugContext.glError("nglTexCoord2sv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord2sv(ShortBuffer p0) {
        GL11.glTexCoord2sv(p0);
        LabyDebugContext.glError("glTexCoord2sv", "p0", p0);
    }

    public static void nglTexCoord2iv(long p0) {
        GL11.nglTexCoord2iv(p0);
        LabyDebugContext.glError("nglTexCoord2iv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord2iv(IntBuffer p0) {
        GL11.glTexCoord2iv(p0);
        LabyDebugContext.glError("glTexCoord2iv", "p0", p0);
    }

    public static void nglTexCoord2dv(long p0) {
        GL11.nglTexCoord2dv(p0);
        LabyDebugContext.glError("nglTexCoord2dv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord2dv(DoubleBuffer p0) {
        GL11.glTexCoord2dv(p0);
        LabyDebugContext.glError("glTexCoord2dv", "p0", p0);
    }

    public static void glTexCoord3f(float p0, float p1, float p2) {
        GL11.glTexCoord3f(p0, p1, p2);
        LabyDebugContext.glError("glTexCoord3f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glTexCoord3s(short p0, short p1, short p2) {
        GL11.glTexCoord3s(p0, p1, p2);
        LabyDebugContext.glError("glTexCoord3s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glTexCoord3i(int p0, int p1, int p2) {
        GL11.glTexCoord3i(p0, p1, p2);
        LabyDebugContext.glError("glTexCoord3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glTexCoord3d(double p0, double p1, double p2) {
        GL11.glTexCoord3d(p0, p1, p2);
        LabyDebugContext.glError("glTexCoord3d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglTexCoord3fv(long p0) {
        GL11.nglTexCoord3fv(p0);
        LabyDebugContext.glError("nglTexCoord3fv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord3fv(FloatBuffer p0) {
        GL11.glTexCoord3fv(p0);
        LabyDebugContext.glError("glTexCoord3fv", "p0", p0);
    }

    public static void nglTexCoord3sv(long p0) {
        GL11.nglTexCoord3sv(p0);
        LabyDebugContext.glError("nglTexCoord3sv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord3sv(ShortBuffer p0) {
        GL11.glTexCoord3sv(p0);
        LabyDebugContext.glError("glTexCoord3sv", "p0", p0);
    }

    public static void nglTexCoord3iv(long p0) {
        GL11.nglTexCoord3iv(p0);
        LabyDebugContext.glError("nglTexCoord3iv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord3iv(IntBuffer p0) {
        GL11.glTexCoord3iv(p0);
        LabyDebugContext.glError("glTexCoord3iv", "p0", p0);
    }

    public static void nglTexCoord3dv(long p0) {
        GL11.nglTexCoord3dv(p0);
        LabyDebugContext.glError("nglTexCoord3dv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord3dv(DoubleBuffer p0) {
        GL11.glTexCoord3dv(p0);
        LabyDebugContext.glError("glTexCoord3dv", "p0", p0);
    }

    public static void glTexCoord4f(float p0, float p1, float p2, float p3) {
        GL11.glTexCoord4f(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoord4f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glTexCoord4s(short p0, short p1, short p2, short p3) {
        GL11.glTexCoord4s(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoord4s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glTexCoord4i(int p0, int p1, int p2, int p3) {
        GL11.glTexCoord4i(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoord4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glTexCoord4d(double p0, double p1, double p2, double p3) {
        GL11.glTexCoord4d(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoord4d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void nglTexCoord4fv(long p0) {
        GL11.nglTexCoord4fv(p0);
        LabyDebugContext.glError("nglTexCoord4fv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord4fv(FloatBuffer p0) {
        GL11.glTexCoord4fv(p0);
        LabyDebugContext.glError("glTexCoord4fv", "p0", p0);
    }

    public static void nglTexCoord4sv(long p0) {
        GL11.nglTexCoord4sv(p0);
        LabyDebugContext.glError("nglTexCoord4sv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord4sv(ShortBuffer p0) {
        GL11.glTexCoord4sv(p0);
        LabyDebugContext.glError("glTexCoord4sv", "p0", p0);
    }

    public static void nglTexCoord4iv(long p0) {
        GL11.nglTexCoord4iv(p0);
        LabyDebugContext.glError("nglTexCoord4iv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord4iv(IntBuffer p0) {
        GL11.glTexCoord4iv(p0);
        LabyDebugContext.glError("glTexCoord4iv", "p0", p0);
    }

    public static void nglTexCoord4dv(long p0) {
        GL11.nglTexCoord4dv(p0);
        LabyDebugContext.glError("nglTexCoord4dv", "p0", Long.valueOf(p0));
    }

    public static void glTexCoord4dv(DoubleBuffer p0) {
        GL11.glTexCoord4dv(p0);
        LabyDebugContext.glError("glTexCoord4dv", "p0", p0);
    }

    public static void nglTexCoordPointer(int p0, int p1, int p2, long p3) {
        GL11.nglTexCoordPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("nglTexCoordPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glTexCoordPointer(int p0, int p1, int p2, ByteBuffer p3) {
        GL11.glTexCoordPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoordPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glTexCoordPointer(int p0, int p1, int p2, long p3) {
        GL11.glTexCoordPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoordPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glTexCoordPointer(int p0, int p1, int p2, ShortBuffer p3) {
        GL11.glTexCoordPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoordPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glTexCoordPointer(int p0, int p1, int p2, IntBuffer p3) {
        GL11.glTexCoordPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoordPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glTexCoordPointer(int p0, int p1, int p2, FloatBuffer p3) {
        GL11.glTexCoordPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glTexCoordPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glTexEnvi(int p0, int p1, int p2) {
        GL11.glTexEnvi(p0, p1, p2);
        LabyDebugContext.glError("glTexEnvi", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTexEnviv(int p0, int p1, long p2) {
        GL11.nglTexEnviv(p0, p1, p2);
        LabyDebugContext.glError("nglTexEnviv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexEnviv(int p0, int p1, IntBuffer p2) {
        GL11.glTexEnviv(p0, p1, p2);
        LabyDebugContext.glError("glTexEnviv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexEnvf(int p0, int p1, float p2) {
        GL11.glTexEnvf(p0, p1, p2);
        LabyDebugContext.glError("glTexEnvf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglTexEnvfv(int p0, int p1, long p2) {
        GL11.nglTexEnvfv(p0, p1, p2);
        LabyDebugContext.glError("nglTexEnvfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexEnvfv(int p0, int p1, FloatBuffer p2) {
        GL11.glTexEnvfv(p0, p1, p2);
        LabyDebugContext.glError("glTexEnvfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexGeni(int p0, int p1, int p2) {
        GL11.glTexGeni(p0, p1, p2);
        LabyDebugContext.glError("glTexGeni", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTexGeniv(int p0, int p1, long p2) {
        GL11.nglTexGeniv(p0, p1, p2);
        LabyDebugContext.glError("nglTexGeniv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexGeniv(int p0, int p1, IntBuffer p2) {
        GL11.glTexGeniv(p0, p1, p2);
        LabyDebugContext.glError("glTexGeniv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexGenf(int p0, int p1, float p2) {
        GL11.glTexGenf(p0, p1, p2);
        LabyDebugContext.glError("glTexGenf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglTexGenfv(int p0, int p1, long p2) {
        GL11.nglTexGenfv(p0, p1, p2);
        LabyDebugContext.glError("nglTexGenfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexGenfv(int p0, int p1, FloatBuffer p2) {
        GL11.glTexGenfv(p0, p1, p2);
        LabyDebugContext.glError("glTexGenfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexGend(int p0, int p1, double p2) {
        GL11.glTexGend(p0, p1, p2);
        LabyDebugContext.glError("glTexGend", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglTexGendv(int p0, int p1, long p2) {
        GL11.nglTexGendv(p0, p1, p2);
        LabyDebugContext.glError("nglTexGendv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexGendv(int p0, int p1, DoubleBuffer p2) {
        GL11.glTexGendv(p0, p1, p2);
        LabyDebugContext.glError("glTexGendv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL11.nglTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("nglTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ByteBuffer p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, long p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Long.valueOf(p7));
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, ShortBuffer p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, IntBuffer p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, FloatBuffer p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, DoubleBuffer p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void nglTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11.nglTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glCopyTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6) {
        GL11.glCopyTexImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glCopyTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6));
    }

    public static void glCopyTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL11.glCopyTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCopyTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glCopyTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5) {
        GL11.glCopyTexSubImage1D(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glCopyTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5));
    }

    public static void glCopyTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
        GL11.glCopyTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glCopyTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7));
    }

    public static void glTexParameteri(int p0, int p1, int p2) {
        GL11.glTexParameteri(p0, p1, p2);
        LabyDebugContext.glError("glTexParameteri", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void nglTexParameteriv(int p0, int p1, long p2) {
        GL11.nglTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("nglTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexParameteriv(int p0, int p1, IntBuffer p2) {
        GL11.glTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterf(int p0, int p1, float p2) {
        GL11.glTexParameterf(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterf", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void nglTexParameterfv(int p0, int p1, long p2) {
        GL11.nglTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("nglTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Long.valueOf(p2));
    }

    public static void glTexParameterfv(int p0, int p1, FloatBuffer p2) {
        GL11.glTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void nglTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11.nglTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("nglTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ByteBuffer p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, long p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Long.valueOf(p6));
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, ShortBuffer p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, IntBuffer p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, FloatBuffer p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, DoubleBuffer p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void nglTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11.nglTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("nglTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ByteBuffer p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, long p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Long.valueOf(p8));
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, ShortBuffer p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, IntBuffer p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, FloatBuffer p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, DoubleBuffer p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTranslatef(float p0, float p1, float p2) {
        GL11.glTranslatef(p0, p1, p2);
        LabyDebugContext.glError("glTranslatef", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glTranslated(double p0, double p1, double p2) {
        GL11.glTranslated(p0, p1, p2);
        LabyDebugContext.glError("glTranslated", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void glVertex2f(float p0, float p1) {
        GL11.glVertex2f(p0, p1);
        LabyDebugContext.glError("glVertex2f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1));
    }

    public static void glVertex2s(short p0, short p1) {
        GL11.glVertex2s(p0, p1);
        LabyDebugContext.glError("glVertex2s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1));
    }

    public static void glVertex2i(int p0, int p1) {
        GL11.glVertex2i(p0, p1);
        LabyDebugContext.glError("glVertex2i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1));
    }

    public static void glVertex2d(double p0, double p1) {
        GL11.glVertex2d(p0, p1);
        LabyDebugContext.glError("glVertex2d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1));
    }

    public static void nglVertex2fv(long p0) {
        GL11.nglVertex2fv(p0);
        LabyDebugContext.glError("nglVertex2fv", "p0", Long.valueOf(p0));
    }

    public static void glVertex2fv(FloatBuffer p0) {
        GL11.glVertex2fv(p0);
        LabyDebugContext.glError("glVertex2fv", "p0", p0);
    }

    public static void nglVertex2sv(long p0) {
        GL11.nglVertex2sv(p0);
        LabyDebugContext.glError("nglVertex2sv", "p0", Long.valueOf(p0));
    }

    public static void glVertex2sv(ShortBuffer p0) {
        GL11.glVertex2sv(p0);
        LabyDebugContext.glError("glVertex2sv", "p0", p0);
    }

    public static void nglVertex2iv(long p0) {
        GL11.nglVertex2iv(p0);
        LabyDebugContext.glError("nglVertex2iv", "p0", Long.valueOf(p0));
    }

    public static void glVertex2iv(IntBuffer p0) {
        GL11.glVertex2iv(p0);
        LabyDebugContext.glError("glVertex2iv", "p0", p0);
    }

    public static void nglVertex2dv(long p0) {
        GL11.nglVertex2dv(p0);
        LabyDebugContext.glError("nglVertex2dv", "p0", Long.valueOf(p0));
    }

    public static void glVertex2dv(DoubleBuffer p0) {
        GL11.glVertex2dv(p0);
        LabyDebugContext.glError("glVertex2dv", "p0", p0);
    }

    public static void glVertex3f(float p0, float p1, float p2) {
        GL11.glVertex3f(p0, p1, p2);
        LabyDebugContext.glError("glVertex3f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2));
    }

    public static void glVertex3s(short p0, short p1, short p2) {
        GL11.glVertex3s(p0, p1, p2);
        LabyDebugContext.glError("glVertex3s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2));
    }

    public static void glVertex3i(int p0, int p1, int p2) {
        GL11.glVertex3i(p0, p1, p2);
        LabyDebugContext.glError("glVertex3i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2));
    }

    public static void glVertex3d(double p0, double p1, double p2) {
        GL11.glVertex3d(p0, p1, p2);
        LabyDebugContext.glError("glVertex3d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2));
    }

    public static void nglVertex3fv(long p0) {
        GL11.nglVertex3fv(p0);
        LabyDebugContext.glError("nglVertex3fv", "p0", Long.valueOf(p0));
    }

    public static void glVertex3fv(FloatBuffer p0) {
        GL11.glVertex3fv(p0);
        LabyDebugContext.glError("glVertex3fv", "p0", p0);
    }

    public static void nglVertex3sv(long p0) {
        GL11.nglVertex3sv(p0);
        LabyDebugContext.glError("nglVertex3sv", "p0", Long.valueOf(p0));
    }

    public static void glVertex3sv(ShortBuffer p0) {
        GL11.glVertex3sv(p0);
        LabyDebugContext.glError("glVertex3sv", "p0", p0);
    }

    public static void nglVertex3iv(long p0) {
        GL11.nglVertex3iv(p0);
        LabyDebugContext.glError("nglVertex3iv", "p0", Long.valueOf(p0));
    }

    public static void glVertex3iv(IntBuffer p0) {
        GL11.glVertex3iv(p0);
        LabyDebugContext.glError("glVertex3iv", "p0", p0);
    }

    public static void nglVertex3dv(long p0) {
        GL11.nglVertex3dv(p0);
        LabyDebugContext.glError("nglVertex3dv", "p0", Long.valueOf(p0));
    }

    public static void glVertex3dv(DoubleBuffer p0) {
        GL11.glVertex3dv(p0);
        LabyDebugContext.glError("glVertex3dv", "p0", p0);
    }

    public static void glVertex4f(float p0, float p1, float p2, float p3) {
        GL11.glVertex4f(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertex4f", "p0", Float.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Float.valueOf(p3));
    }

    public static void glVertex4s(short p0, short p1, short p2, short p3) {
        GL11.glVertex4s(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertex4s", "p0", Short.valueOf(p0), "p1", Short.valueOf(p1), "p2", Short.valueOf(p2), "p3", Short.valueOf(p3));
    }

    public static void glVertex4i(int p0, int p1, int p2, int p3) {
        GL11.glVertex4i(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertex4i", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static void glVertex4d(double p0, double p1, double p2, double p3) {
        GL11.glVertex4d(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertex4d", "p0", Double.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Double.valueOf(p3));
    }

    public static void nglVertex4fv(long p0) {
        GL11.nglVertex4fv(p0);
        LabyDebugContext.glError("nglVertex4fv", "p0", Long.valueOf(p0));
    }

    public static void glVertex4fv(FloatBuffer p0) {
        GL11.glVertex4fv(p0);
        LabyDebugContext.glError("glVertex4fv", "p0", p0);
    }

    public static void nglVertex4sv(long p0) {
        GL11.nglVertex4sv(p0);
        LabyDebugContext.glError("nglVertex4sv", "p0", Long.valueOf(p0));
    }

    public static void glVertex4sv(ShortBuffer p0) {
        GL11.glVertex4sv(p0);
        LabyDebugContext.glError("glVertex4sv", "p0", p0);
    }

    public static void nglVertex4iv(long p0) {
        GL11.nglVertex4iv(p0);
        LabyDebugContext.glError("nglVertex4iv", "p0", Long.valueOf(p0));
    }

    public static void glVertex4iv(IntBuffer p0) {
        GL11.glVertex4iv(p0);
        LabyDebugContext.glError("glVertex4iv", "p0", p0);
    }

    public static void nglVertex4dv(long p0) {
        GL11.nglVertex4dv(p0);
        LabyDebugContext.glError("nglVertex4dv", "p0", Long.valueOf(p0));
    }

    public static void glVertex4dv(DoubleBuffer p0) {
        GL11.glVertex4dv(p0);
        LabyDebugContext.glError("glVertex4dv", "p0", p0);
    }

    public static void nglVertexPointer(int p0, int p1, int p2, long p3) {
        GL11.nglVertexPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("nglVertexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexPointer(int p0, int p1, int p2, ByteBuffer p3) {
        GL11.glVertexPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glVertexPointer(int p0, int p1, int p2, long p3) {
        GL11.glVertexPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Long.valueOf(p3));
    }

    public static void glVertexPointer(int p0, int p1, int p2, ShortBuffer p3) {
        GL11.glVertexPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glVertexPointer(int p0, int p1, int p2, IntBuffer p3) {
        GL11.glVertexPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glVertexPointer(int p0, int p1, int p2, FloatBuffer p3) {
        GL11.glVertexPointer(p0, p1, p2, p3);
        LabyDebugContext.glError("glVertexPointer", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glViewport(int p0, int p1, int p2, int p3) {
        GL11.glViewport(p0, p1, p2, p3);
        LabyDebugContext.glError("glViewport", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3));
    }

    public static boolean glAreTexturesResident(int[] p0, ByteBuffer p1) {
        boolean returnType = GL11.glAreTexturesResident(p0, p1);
        LabyDebugContext.glError("glAreTexturesResident", "p0", p0, "p1", p1);
        return returnType;
    }

    public static void glClipPlane(int p0, double[] p1) {
        GL11.glClipPlane(p0, p1);
        LabyDebugContext.glError("glClipPlane", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glColor3sv(short[] p0) {
        GL11.glColor3sv(p0);
        LabyDebugContext.glError("glColor3sv", "p0", p0);
    }

    public static void glColor3iv(int[] p0) {
        GL11.glColor3iv(p0);
        LabyDebugContext.glError("glColor3iv", "p0", p0);
    }

    public static void glColor3fv(float[] p0) {
        GL11.glColor3fv(p0);
        LabyDebugContext.glError("glColor3fv", "p0", p0);
    }

    public static void glColor3dv(double[] p0) {
        GL11.glColor3dv(p0);
        LabyDebugContext.glError("glColor3dv", "p0", p0);
    }

    public static void glColor3usv(short[] p0) {
        GL11.glColor3usv(p0);
        LabyDebugContext.glError("glColor3usv", "p0", p0);
    }

    public static void glColor3uiv(int[] p0) {
        GL11.glColor3uiv(p0);
        LabyDebugContext.glError("glColor3uiv", "p0", p0);
    }

    public static void glColor4sv(short[] p0) {
        GL11.glColor4sv(p0);
        LabyDebugContext.glError("glColor4sv", "p0", p0);
    }

    public static void glColor4iv(int[] p0) {
        GL11.glColor4iv(p0);
        LabyDebugContext.glError("glColor4iv", "p0", p0);
    }

    public static void glColor4fv(float[] p0) {
        GL11.glColor4fv(p0);
        LabyDebugContext.glError("glColor4fv", "p0", p0);
    }

    public static void glColor4dv(double[] p0) {
        GL11.glColor4dv(p0);
        LabyDebugContext.glError("glColor4dv", "p0", p0);
    }

    public static void glColor4usv(short[] p0) {
        GL11.glColor4usv(p0);
        LabyDebugContext.glError("glColor4usv", "p0", p0);
    }

    public static void glColor4uiv(int[] p0) {
        GL11.glColor4uiv(p0);
        LabyDebugContext.glError("glColor4uiv", "p0", p0);
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, short[] p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, int[] p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glDrawPixels(int p0, int p1, int p2, int p3, float[] p4) {
        GL11.glDrawPixels(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glDrawPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glEvalCoord1fv(float[] p0) {
        GL11.glEvalCoord1fv(p0);
        LabyDebugContext.glError("glEvalCoord1fv", "p0", p0);
    }

    public static void glEvalCoord1dv(double[] p0) {
        GL11.glEvalCoord1dv(p0);
        LabyDebugContext.glError("glEvalCoord1dv", "p0", p0);
    }

    public static void glEvalCoord2fv(float[] p0) {
        GL11.glEvalCoord2fv(p0);
        LabyDebugContext.glError("glEvalCoord2fv", "p0", p0);
    }

    public static void glEvalCoord2dv(double[] p0) {
        GL11.glEvalCoord2dv(p0);
        LabyDebugContext.glError("glEvalCoord2dv", "p0", p0);
    }

    public static void glFeedbackBuffer(int p0, float[] p1) {
        GL11.glFeedbackBuffer(p0, p1);
        LabyDebugContext.glError("glFeedbackBuffer", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFogiv(int p0, int[] p1) {
        GL11.glFogiv(p0, p1);
        LabyDebugContext.glError("glFogiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glFogfv(int p0, float[] p1) {
        GL11.glFogfv(p0, p1);
        LabyDebugContext.glError("glFogfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGenTextures(int[] p0) {
        GL11.glGenTextures(p0);
        LabyDebugContext.glError("glGenTextures", "p0", p0);
    }

    public static void glDeleteTextures(int[] p0) {
        GL11.glDeleteTextures(p0);
        LabyDebugContext.glError("glDeleteTextures", "p0", p0);
    }

    public static void glGetClipPlane(int p0, double[] p1) {
        GL11.glGetClipPlane(p0, p1);
        LabyDebugContext.glError("glGetClipPlane", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetFloatv(int p0, float[] p1) {
        GL11.glGetFloatv(p0, p1);
        LabyDebugContext.glError("glGetFloatv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetIntegerv(int p0, int[] p1) {
        GL11.glGetIntegerv(p0, p1);
        LabyDebugContext.glError("glGetIntegerv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetDoublev(int p0, double[] p1) {
        GL11.glGetDoublev(p0, p1);
        LabyDebugContext.glError("glGetDoublev", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetLightiv(int p0, int p1, int[] p2) {
        GL11.glGetLightiv(p0, p1, p2);
        LabyDebugContext.glError("glGetLightiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetLightfv(int p0, int p1, float[] p2) {
        GL11.glGetLightfv(p0, p1, p2);
        LabyDebugContext.glError("glGetLightfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMapiv(int p0, int p1, int[] p2) {
        GL11.glGetMapiv(p0, p1, p2);
        LabyDebugContext.glError("glGetMapiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMapfv(int p0, int p1, float[] p2) {
        GL11.glGetMapfv(p0, p1, p2);
        LabyDebugContext.glError("glGetMapfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMapdv(int p0, int p1, double[] p2) {
        GL11.glGetMapdv(p0, p1, p2);
        LabyDebugContext.glError("glGetMapdv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMaterialiv(int p0, int p1, int[] p2) {
        GL11.glGetMaterialiv(p0, p1, p2);
        LabyDebugContext.glError("glGetMaterialiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetMaterialfv(int p0, int p1, float[] p2) {
        GL11.glGetMaterialfv(p0, p1, p2);
        LabyDebugContext.glError("glGetMaterialfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetPixelMapfv(int p0, float[] p1) {
        GL11.glGetPixelMapfv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPixelMapusv(int p0, short[] p1) {
        GL11.glGetPixelMapusv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapusv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetPixelMapuiv(int p0, int[] p1) {
        GL11.glGetPixelMapuiv(p0, p1);
        LabyDebugContext.glError("glGetPixelMapuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glGetTexEnviv(int p0, int p1, int[] p2) {
        GL11.glGetTexEnviv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexEnviv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexEnvfv(int p0, int p1, float[] p2) {
        GL11.glGetTexEnvfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexEnvfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexGeniv(int p0, int p1, int[] p2) {
        GL11.glGetTexGeniv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexGeniv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexGenfv(int p0, int p1, float[] p2) {
        GL11.glGetTexGenfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexGenfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexGendv(int p0, int p1, double[] p2) {
        GL11.glGetTexGendv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexGendv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, short[] p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, int[] p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, float[] p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexImage(int p0, int p1, int p2, int p3, double[] p4) {
        GL11.glGetTexImage(p0, p1, p2, p3, p4);
        LabyDebugContext.glError("glGetTexImage", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", p4);
    }

    public static void glGetTexLevelParameteriv(int p0, int p1, int p2, int[] p3) {
        GL11.glGetTexLevelParameteriv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTexLevelParameterfv(int p0, int p1, int p2, float[] p3) {
        GL11.glGetTexLevelParameterfv(p0, p1, p2, p3);
        LabyDebugContext.glError("glGetTexLevelParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", p3);
    }

    public static void glGetTexParameteriv(int p0, int p1, int[] p2) {
        GL11.glGetTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glGetTexParameterfv(int p0, int p1, float[] p2) {
        GL11.glGetTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glGetTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glIndexiv(int[] p0) {
        GL11.glIndexiv(p0);
        LabyDebugContext.glError("glIndexiv", "p0", p0);
    }

    public static void glIndexsv(short[] p0) {
        GL11.glIndexsv(p0);
        LabyDebugContext.glError("glIndexsv", "p0", p0);
    }

    public static void glIndexfv(float[] p0) {
        GL11.glIndexfv(p0);
        LabyDebugContext.glError("glIndexfv", "p0", p0);
    }

    public static void glIndexdv(double[] p0) {
        GL11.glIndexdv(p0);
        LabyDebugContext.glError("glIndexdv", "p0", p0);
    }

    public static void glInterleavedArrays(int p0, int p1, short[] p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, int[] p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, float[] p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glInterleavedArrays(int p0, int p1, double[] p2) {
        GL11.glInterleavedArrays(p0, p1, p2);
        LabyDebugContext.glError("glInterleavedArrays", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glLightModeliv(int p0, int[] p1) {
        GL11.glLightModeliv(p0, p1);
        LabyDebugContext.glError("glLightModeliv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glLightModelfv(int p0, float[] p1) {
        GL11.glLightModelfv(p0, p1);
        LabyDebugContext.glError("glLightModelfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glLightiv(int p0, int p1, int[] p2) {
        GL11.glLightiv(p0, p1, p2);
        LabyDebugContext.glError("glLightiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glLightfv(int p0, int p1, float[] p2) {
        GL11.glLightfv(p0, p1, p2);
        LabyDebugContext.glError("glLightfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glLoadMatrixf(float[] p0) {
        GL11.glLoadMatrixf(p0);
        LabyDebugContext.glError("glLoadMatrixf", "p0", p0);
    }

    public static void glLoadMatrixd(double[] p0) {
        GL11.glLoadMatrixd(p0);
        LabyDebugContext.glError("glLoadMatrixd", "p0", p0);
    }

    public static void glMap1f(int p0, float p1, float p2, int p3, int p4, float[] p5) {
        GL11.glMap1f(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMap1f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glMap1d(int p0, double p1, double p2, int p3, int p4, double[] p5) {
        GL11.glMap1d(p0, p1, p2, p3, p4, p5);
        LabyDebugContext.glError("glMap1d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", p5);
    }

    public static void glMap2f(int p0, float p1, float p2, int p3, int p4, float p5, float p6, int p7, int p8, float[] p9) {
        GL11.glMap2f(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glMap2f", "p0", Integer.valueOf(p0), "p1", Float.valueOf(p1), "p2", Float.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Float.valueOf(p5), "p6", Float.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void glMap2d(int p0, double p1, double p2, int p3, int p4, double p5, double p6, int p7, int p8, double[] p9) {
        GL11.glMap2d(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        LabyDebugContext.glError("glMap2d", "p0", Integer.valueOf(p0), "p1", Double.valueOf(p1), "p2", Double.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Double.valueOf(p5), "p6", Double.valueOf(p6), "p7", Integer.valueOf(p7), "p8", Integer.valueOf(p8), "p9", p9);
    }

    public static void glMaterialiv(int p0, int p1, int[] p2) {
        GL11.glMaterialiv(p0, p1, p2);
        LabyDebugContext.glError("glMaterialiv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMaterialfv(int p0, int p1, float[] p2) {
        GL11.glMaterialfv(p0, p1, p2);
        LabyDebugContext.glError("glMaterialfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glMultMatrixf(float[] p0) {
        GL11.glMultMatrixf(p0);
        LabyDebugContext.glError("glMultMatrixf", "p0", p0);
    }

    public static void glMultMatrixd(double[] p0) {
        GL11.glMultMatrixd(p0);
        LabyDebugContext.glError("glMultMatrixd", "p0", p0);
    }

    public static void glNormal3fv(float[] p0) {
        GL11.glNormal3fv(p0);
        LabyDebugContext.glError("glNormal3fv", "p0", p0);
    }

    public static void glNormal3sv(short[] p0) {
        GL11.glNormal3sv(p0);
        LabyDebugContext.glError("glNormal3sv", "p0", p0);
    }

    public static void glNormal3iv(int[] p0) {
        GL11.glNormal3iv(p0);
        LabyDebugContext.glError("glNormal3iv", "p0", p0);
    }

    public static void glNormal3dv(double[] p0) {
        GL11.glNormal3dv(p0);
        LabyDebugContext.glError("glNormal3dv", "p0", p0);
    }

    public static void glPixelMapfv(int p0, float[] p1) {
        GL11.glPixelMapfv(p0, p1);
        LabyDebugContext.glError("glPixelMapfv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPixelMapusv(int p0, short[] p1) {
        GL11.glPixelMapusv(p0, p1);
        LabyDebugContext.glError("glPixelMapusv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPixelMapuiv(int p0, int[] p1) {
        GL11.glPixelMapuiv(p0, p1);
        LabyDebugContext.glError("glPixelMapuiv", "p0", Integer.valueOf(p0), "p1", p1);
    }

    public static void glPrioritizeTextures(int[] p0, float[] p1) {
        GL11.glPrioritizeTextures(p0, p1);
        LabyDebugContext.glError("glPrioritizeTextures", "p0", p0, "p1", p1);
    }

    public static void glRasterPos2iv(int[] p0) {
        GL11.glRasterPos2iv(p0);
        LabyDebugContext.glError("glRasterPos2iv", "p0", p0);
    }

    public static void glRasterPos2sv(short[] p0) {
        GL11.glRasterPos2sv(p0);
        LabyDebugContext.glError("glRasterPos2sv", "p0", p0);
    }

    public static void glRasterPos2fv(float[] p0) {
        GL11.glRasterPos2fv(p0);
        LabyDebugContext.glError("glRasterPos2fv", "p0", p0);
    }

    public static void glRasterPos2dv(double[] p0) {
        GL11.glRasterPos2dv(p0);
        LabyDebugContext.glError("glRasterPos2dv", "p0", p0);
    }

    public static void glRasterPos3iv(int[] p0) {
        GL11.glRasterPos3iv(p0);
        LabyDebugContext.glError("glRasterPos3iv", "p0", p0);
    }

    public static void glRasterPos3sv(short[] p0) {
        GL11.glRasterPos3sv(p0);
        LabyDebugContext.glError("glRasterPos3sv", "p0", p0);
    }

    public static void glRasterPos3fv(float[] p0) {
        GL11.glRasterPos3fv(p0);
        LabyDebugContext.glError("glRasterPos3fv", "p0", p0);
    }

    public static void glRasterPos3dv(double[] p0) {
        GL11.glRasterPos3dv(p0);
        LabyDebugContext.glError("glRasterPos3dv", "p0", p0);
    }

    public static void glRasterPos4iv(int[] p0) {
        GL11.glRasterPos4iv(p0);
        LabyDebugContext.glError("glRasterPos4iv", "p0", p0);
    }

    public static void glRasterPos4sv(short[] p0) {
        GL11.glRasterPos4sv(p0);
        LabyDebugContext.glError("glRasterPos4sv", "p0", p0);
    }

    public static void glRasterPos4fv(float[] p0) {
        GL11.glRasterPos4fv(p0);
        LabyDebugContext.glError("glRasterPos4fv", "p0", p0);
    }

    public static void glRasterPos4dv(double[] p0) {
        GL11.glRasterPos4dv(p0);
        LabyDebugContext.glError("glRasterPos4dv", "p0", p0);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glReadPixels(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        GL11.glReadPixels(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glReadPixels", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glRectiv(int[] p0, int[] p1) {
        GL11.glRectiv(p0, p1);
        LabyDebugContext.glError("glRectiv", "p0", p0, "p1", p1);
    }

    public static void glRectsv(short[] p0, short[] p1) {
        GL11.glRectsv(p0, p1);
        LabyDebugContext.glError("glRectsv", "p0", p0, "p1", p1);
    }

    public static void glRectfv(float[] p0, float[] p1) {
        GL11.glRectfv(p0, p1);
        LabyDebugContext.glError("glRectfv", "p0", p0, "p1", p1);
    }

    public static void glRectdv(double[] p0, double[] p1) {
        GL11.glRectdv(p0, p1);
        LabyDebugContext.glError("glRectdv", "p0", p0, "p1", p1);
    }

    public static void glSelectBuffer(int[] p0) {
        GL11.glSelectBuffer(p0);
        LabyDebugContext.glError("glSelectBuffer", "p0", p0);
    }

    public static void glTexCoord1fv(float[] p0) {
        GL11.glTexCoord1fv(p0);
        LabyDebugContext.glError("glTexCoord1fv", "p0", p0);
    }

    public static void glTexCoord1sv(short[] p0) {
        GL11.glTexCoord1sv(p0);
        LabyDebugContext.glError("glTexCoord1sv", "p0", p0);
    }

    public static void glTexCoord1iv(int[] p0) {
        GL11.glTexCoord1iv(p0);
        LabyDebugContext.glError("glTexCoord1iv", "p0", p0);
    }

    public static void glTexCoord1dv(double[] p0) {
        GL11.glTexCoord1dv(p0);
        LabyDebugContext.glError("glTexCoord1dv", "p0", p0);
    }

    public static void glTexCoord2fv(float[] p0) {
        GL11.glTexCoord2fv(p0);
        LabyDebugContext.glError("glTexCoord2fv", "p0", p0);
    }

    public static void glTexCoord2sv(short[] p0) {
        GL11.glTexCoord2sv(p0);
        LabyDebugContext.glError("glTexCoord2sv", "p0", p0);
    }

    public static void glTexCoord2iv(int[] p0) {
        GL11.glTexCoord2iv(p0);
        LabyDebugContext.glError("glTexCoord2iv", "p0", p0);
    }

    public static void glTexCoord2dv(double[] p0) {
        GL11.glTexCoord2dv(p0);
        LabyDebugContext.glError("glTexCoord2dv", "p0", p0);
    }

    public static void glTexCoord3fv(float[] p0) {
        GL11.glTexCoord3fv(p0);
        LabyDebugContext.glError("glTexCoord3fv", "p0", p0);
    }

    public static void glTexCoord3sv(short[] p0) {
        GL11.glTexCoord3sv(p0);
        LabyDebugContext.glError("glTexCoord3sv", "p0", p0);
    }

    public static void glTexCoord3iv(int[] p0) {
        GL11.glTexCoord3iv(p0);
        LabyDebugContext.glError("glTexCoord3iv", "p0", p0);
    }

    public static void glTexCoord3dv(double[] p0) {
        GL11.glTexCoord3dv(p0);
        LabyDebugContext.glError("glTexCoord3dv", "p0", p0);
    }

    public static void glTexCoord4fv(float[] p0) {
        GL11.glTexCoord4fv(p0);
        LabyDebugContext.glError("glTexCoord4fv", "p0", p0);
    }

    public static void glTexCoord4sv(short[] p0) {
        GL11.glTexCoord4sv(p0);
        LabyDebugContext.glError("glTexCoord4sv", "p0", p0);
    }

    public static void glTexCoord4iv(int[] p0) {
        GL11.glTexCoord4iv(p0);
        LabyDebugContext.glError("glTexCoord4iv", "p0", p0);
    }

    public static void glTexCoord4dv(double[] p0) {
        GL11.glTexCoord4dv(p0);
        LabyDebugContext.glError("glTexCoord4dv", "p0", p0);
    }

    public static void glTexEnviv(int p0, int p1, int[] p2) {
        GL11.glTexEnviv(p0, p1, p2);
        LabyDebugContext.glError("glTexEnviv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexEnvfv(int p0, int p1, float[] p2) {
        GL11.glTexEnvfv(p0, p1, p2);
        LabyDebugContext.glError("glTexEnvfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexGeniv(int p0, int p1, int[] p2) {
        GL11.glTexGeniv(p0, p1, p2);
        LabyDebugContext.glError("glTexGeniv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexGenfv(int p0, int p1, float[] p2) {
        GL11.glTexGenfv(p0, p1, p2);
        LabyDebugContext.glError("glTexGenfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexGendv(int p0, int p1, double[] p2) {
        GL11.glTexGendv(p0, p1, p2);
        LabyDebugContext.glError("glTexGendv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, short[] p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int[] p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, float[] p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, double[] p7) {
        GL11.glTexImage1D(p0, p1, p2, p3, p4, p5, p6, p7);
        LabyDebugContext.glError("glTexImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", p7);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        GL11.glTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexParameteriv(int p0, int p1, int[] p2) {
        GL11.glTexParameteriv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameteriv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexParameterfv(int p0, int p1, float[] p2) {
        GL11.glTexParameterfv(p0, p1, p2);
        LabyDebugContext.glError("glTexParameterfv", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", p2);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, short[] p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, int[] p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, float[] p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage1D(int p0, int p1, int p2, int p3, int p4, int p5, double[] p6) {
        GL11.glTexSubImage1D(p0, p1, p2, p3, p4, p5, p6);
        LabyDebugContext.glError("glTexSubImage1D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", p6);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, short[] p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int[] p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, float[] p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glTexSubImage2D(int p0, int p1, int p2, int p3, int p4, int p5, int p6, int p7, double[] p8) {
        GL11.glTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, p8);
        LabyDebugContext.glError("glTexSubImage2D", "p0", Integer.valueOf(p0), "p1", Integer.valueOf(p1), "p2", Integer.valueOf(p2), "p3", Integer.valueOf(p3), "p4", Integer.valueOf(p4), "p5", Integer.valueOf(p5), "p6", Integer.valueOf(p6), "p7", Integer.valueOf(p7), "p8", p8);
    }

    public static void glVertex2fv(float[] p0) {
        GL11.glVertex2fv(p0);
        LabyDebugContext.glError("glVertex2fv", "p0", p0);
    }

    public static void glVertex2sv(short[] p0) {
        GL11.glVertex2sv(p0);
        LabyDebugContext.glError("glVertex2sv", "p0", p0);
    }

    public static void glVertex2iv(int[] p0) {
        GL11.glVertex2iv(p0);
        LabyDebugContext.glError("glVertex2iv", "p0", p0);
    }

    public static void glVertex2dv(double[] p0) {
        GL11.glVertex2dv(p0);
        LabyDebugContext.glError("glVertex2dv", "p0", p0);
    }

    public static void glVertex3fv(float[] p0) {
        GL11.glVertex3fv(p0);
        LabyDebugContext.glError("glVertex3fv", "p0", p0);
    }

    public static void glVertex3sv(short[] p0) {
        GL11.glVertex3sv(p0);
        LabyDebugContext.glError("glVertex3sv", "p0", p0);
    }

    public static void glVertex3iv(int[] p0) {
        GL11.glVertex3iv(p0);
        LabyDebugContext.glError("glVertex3iv", "p0", p0);
    }

    public static void glVertex3dv(double[] p0) {
        GL11.glVertex3dv(p0);
        LabyDebugContext.glError("glVertex3dv", "p0", p0);
    }

    public static void glVertex4fv(float[] p0) {
        GL11.glVertex4fv(p0);
        LabyDebugContext.glError("glVertex4fv", "p0", p0);
    }

    public static void glVertex4sv(short[] p0) {
        GL11.glVertex4sv(p0);
        LabyDebugContext.glError("glVertex4sv", "p0", p0);
    }

    public static void glVertex4iv(int[] p0) {
        GL11.glVertex4iv(p0);
        LabyDebugContext.glError("glVertex4iv", "p0", p0);
    }

    public static void glVertex4dv(double[] p0) {
        GL11.glVertex4dv(p0);
        LabyDebugContext.glError("glVertex4dv", "p0", p0);
    }
}
