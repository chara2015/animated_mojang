package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.nio.FloatBuffer;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.class */
public final class GLMatrix {
    private GLMatrix() {
    }

    public static void glMatrixMode(int mode) {
        GL11.glMatrixMode(mode);
        MatrixTracker.beginTracking(mode);
    }

    public static void glLoadIdentity() {
        GL11.glLoadIdentity();
        MatrixTracker.loadIdentity();
    }

    public static void glOrtho(double left, double right, double bottom, double top, double zNear, double zFar) {
        GL11.glOrtho(left, right, bottom, top, zNear, zFar);
        MatrixTracker.ortho((float) left, (float) right, (float) bottom, (float) top, (float) zNear, (float) zFar);
    }

    public static void glMultMatrixf(FloatBuffer buffer) {
        GL11.glMultMatrixf(buffer);
        MatrixTracker.multiply(buffer);
    }

    public static void glPushMatrix() {
        GL11.glPushMatrix();
        MatrixTracker.push();
    }

    public static void glPopMatrix() {
        GL11.glPopMatrix();
        MatrixTracker.pop();
    }

    public static void glTranslated(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
        MatrixTracker.translate((float) x, (float) y, (float) z);
    }

    public static void glTranslatef(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
        MatrixTracker.translate(x, y, z);
    }

    public static void glRotated(double angle, double x, double y, double z) {
        GL11.glRotated(angle, x, y, z);
        MatrixTracker.rotate((float) angle, (float) x, (float) y, (float) z);
    }

    public static void glRotatef(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
        MatrixTracker.rotate(angle, x, y, z);
    }

    public static void glScaled(double x, double y, double z) {
        GL11.glScaled(x, y, z);
        MatrixTracker.scale((float) x, (float) y, (float) z);
    }

    public static void glScalef(float x, float y, float z) {
        GL11.glScalef(x, y, z);
        MatrixTracker.scale(x, y, z);
    }
}
