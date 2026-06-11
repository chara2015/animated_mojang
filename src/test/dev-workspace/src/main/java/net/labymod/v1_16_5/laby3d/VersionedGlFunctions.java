package net.labymod.v1_16_5.laby3d;

import java.util.Set;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.laby3d.api.opengl.GlFunctions;
import net.labymod.laby3d.api.textures.DeviceTexture;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/laby3d/VersionedGlFunctions.class */
public class VersionedGlFunctions extends GlFunctions {
    private static final int GL_STATE_MANAGER_TEXTURE_LIMIT = 12;

    public VersionedGlFunctions(GLCapabilities capabilities, Set<String> enabledExtensions) {
        super(capabilities, enabledExtensions);
    }

    public void enableBlend() {
        dem.o();
    }

    public void blendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        dem.b(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public void blendEquationSeparate(int modeRGB, int modeAlpha) {
        dem.c(modeRGB);
        super.blendEquationSeparate(modeAlpha, modeAlpha);
    }

    public void disableBlend() {
        dem.n();
    }

    public void enableDepthTest() {
        dem.m();
    }

    public void depthFunc(int func) {
        dem.b(func);
    }

    public void depthMask(boolean mask) {
        dem.a(mask);
    }

    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        dem.a(red, green, blue, alpha);
    }

    public void enableCull() {
        dem.C();
    }

    public void disableCull() {
        dem.D();
    }

    public void activeTexture(int texture) {
        dem.q(texture);
    }

    public int activeTexture() {
        return dem.p + GlConst.GL_TEXTURE0;
    }

    public void viewport(int x, int y, int width, int height) {
        dem.d(x, y, width, height);
    }

    public void enableScissorTest() {
        dem.k();
    }

    public void scissor(int x, int y, int width, int height) {
        dem.a(x, y, width, height);
    }

    public void disableScissorTest() {
        dem.j();
    }

    public void disableDepthTest() {
        dem.l();
    }

    public void enablePolygonOffset() {
        dem.E();
    }

    public void polygonOffset(float factor, float units) {
        dem.a(factor, units);
    }

    public void disablePolygonOffset() {
        dem.F();
    }

    public void useProgram(int program) {
        dem.g(program);
    }

    public void enableTexture(@NotNull DeviceTexture.Dimension dimension) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 12) {
            return;
        }
        if (dimension == DeviceTexture.Dimension.TEXTURE_2D) {
            dem.K();
        } else {
            super.enableTexture(dimension);
        }
    }

    public void disableTexture(@NotNull DeviceTexture.Dimension dimension) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 12) {
            return;
        }
        if (dimension == DeviceTexture.Dimension.TEXTURE_2D) {
            dem.L();
        } else {
            super.disableTexture(dimension);
        }
    }

    public void bindTexture(int target, int id) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 12) {
            return;
        }
        if (target == 3553) {
            dem.s(id);
        } else {
            super.bindTexture(target, id);
        }
    }

    public void bindFramebuffer(int target, int framebuffer) {
        dem.h(target, framebuffer);
    }
}
