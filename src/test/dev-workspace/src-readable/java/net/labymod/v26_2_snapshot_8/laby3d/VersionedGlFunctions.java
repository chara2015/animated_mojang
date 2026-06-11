package net.labymod.v26_2_snapshot_8.laby3d;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.Set;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.laby3d.api.opengl.GlFunctions;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/VersionedGlFunctions.class */
public class VersionedGlFunctions extends GlFunctions {
    private static final int GL_STATE_MANAGER_TEXTURE_LIMIT = 12;

    public VersionedGlFunctions(GLCapabilities capabilities, Set<String> enabledExtensions) {
        super(capabilities, enabledExtensions);
    }

    public void enableBlend() {
        GlStateManager._enableBlend(0);
    }

    public void blendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        GlStateManager._blendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public void blendEquationSeparate(int modeRGB, int modeAlpha) {
        super.blendEquationSeparate(modeAlpha, modeAlpha);
    }

    public void disableBlend() {
        GlStateManager._disableBlend(0);
    }

    public void enableDepthTest() {
        GlStateManager._enableDepthTest();
    }

    public void depthFunc(int func) {
        GlStateManager._depthFunc(func);
    }

    public void disableDepthTest() {
        GlStateManager._disableDepthTest();
    }

    public void enablePolygonOffset() {
        GlStateManager._enablePolygonOffset();
    }

    public void polygonOffset(float factor, float units) {
        GlStateManager._polygonOffset(factor, units);
    }

    public void disablePolygonOffset() {
        GlStateManager._disablePolygonOffset();
    }

    public void useProgram(int program) {
        GlStateManager._glUseProgram(program);
    }

    public void bindTexture(int target, int id) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 12) {
            return;
        }
        if (target == 3553) {
            GlStateManager._bindTexture(id);
        } else {
            super.bindTexture(target, id);
        }
    }

    public void bindFramebuffer(int target, int framebuffer) {
        GlStateManager._glBindFramebuffer(target, framebuffer);
    }

    public void enableScissorTest() {
        GlStateManager._enableScissorTest();
    }

    public void scissor(int x, int y, int width, int height) {
        GlStateManager._scissorBox(x, y, width, height);
    }

    public void disableScissorTest() {
        GlStateManager._disableScissorTest();
    }

    public void depthMask(boolean mask) {
        GlStateManager._depthMask(mask);
    }

    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        int mask = 0;
        if (red) {
            mask = 0 | 1;
        }
        if (green) {
            mask |= 2;
        }
        if (blue) {
            mask |= 4;
        }
        if (alpha) {
            mask |= 8;
        }
        GlStateManager._colorMask(mask);
    }

    public void enableCull() {
        GlStateManager._enableCull();
    }

    public void disableCull() {
        GlStateManager._disableCull();
    }

    public int activeTexture() {
        return GlStateManager.activeTexture + GlConst.GL_TEXTURE0;
    }

    public void activeTexture(int texture) {
        GlStateManager._activeTexture(texture);
    }

    public void viewport(int x, int y, int width, int height) {
        GlStateManager._viewport(x, y, width, height);
    }
}
