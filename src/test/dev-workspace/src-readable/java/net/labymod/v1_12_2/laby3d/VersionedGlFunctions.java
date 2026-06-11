package net.labymod.v1_12_2.laby3d;

import java.util.Set;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.laby3d.api.opengl.GlFunctions;
import net.labymod.laby3d.api.textures.DeviceTexture;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/laby3d/VersionedGlFunctions.class */
public class VersionedGlFunctions extends GlFunctions {
    private static final int GL_STATE_MANAGER_TEXTURE_LIMIT = 8;

    public VersionedGlFunctions(GLCapabilities capabilities, Set<String> enabledExtensions) {
        super(capabilities, enabledExtensions);
    }

    public void useProgram(int program) {
        cii.d(program);
    }

    public void enableBlend() {
        bus.m();
    }

    public void blendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        bus.a(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public void blendEquationSeparate(int modeRGB, int modeAlpha) {
        bus.d(modeRGB);
        super.blendEquationSeparate(modeAlpha, modeAlpha);
    }

    public void disableBlend() {
        bus.l();
    }

    public void enableDepthTest() {
        bus.k();
    }

    public void depthFunc(int func) {
        bus.c(func);
    }

    public void disableDepthTest() {
        bus.j();
    }

    public void enablePolygonOffset() {
        bus.s();
    }

    public void polygonOffset(float factor, float units) {
        bus.a(factor, units);
    }

    public void disablePolygonOffset() {
        bus.t();
    }

    public void bindTexture(int target, int id) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 8) {
            return;
        }
        if (target == 3553) {
            bus.i(id);
        } else {
            super.bindTexture(target, id);
        }
    }

    public void enableAlphaTest() {
        bus.e();
    }

    public void alphaFunc(int func, float ref) {
        bus.a(func, ref);
    }

    public void disableAlphaTest() {
        bus.d();
    }

    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        bus.a(red, green, blue, alpha);
    }

    public void depthMask(boolean mask) {
        bus.a(mask);
    }

    public void enableCull() {
        bus.q();
    }

    public void disableCull() {
        bus.r();
    }

    public void enableTexture(@NotNull DeviceTexture.Dimension dimension) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 8) {
            return;
        }
        if (dimension == DeviceTexture.Dimension.TEXTURE_2D) {
            bus.y();
        } else {
            super.enableTexture(dimension);
        }
    }

    public void disableTexture(@NotNull DeviceTexture.Dimension dimension) {
        int index = activeTexture() - GlConst.GL_TEXTURE0;
        if (index >= 8) {
            return;
        }
        if (dimension == DeviceTexture.Dimension.TEXTURE_2D) {
            bus.z();
        } else {
            super.disableTexture(dimension);
        }
    }

    public void activeTexture(int texture) {
        bus.g(texture);
    }

    public int activeTexture() {
        return bus.q + GlConst.GL_TEXTURE0;
    }

    public void shadeMode(int shadeMode) {
        bus.j(shadeMode);
    }

    public void viewport(int x, int y, int width, int height) {
        bus.b(x, y, width, height);
    }
}
