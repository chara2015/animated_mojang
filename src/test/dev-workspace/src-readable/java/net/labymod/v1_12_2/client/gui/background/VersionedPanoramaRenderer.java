package net.labymod.v1_12_2.client.gui.background;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/background/VersionedPanoramaRenderer.class */
@Singleton
@Implements(PanoramaRenderer.class)
public class VersionedPanoramaRenderer extends AbstractPanoramaRenderer {
    private static final nf[] RESOURCES = {new nf("textures/gui/title/background/panorama_0.png"), new nf("textures/gui/title/background/panorama_1.png"), new nf("textures/gui/title/background/panorama_2.png"), new nf("textures/gui/title/background/panorama_3.png"), new nf("textures/gui/title/background/panorama_4.png"), new nf("textures/gui/title/background/panorama_5.png")};
    private final bib minecraft;
    private final nf backgroundTexture;
    private float panoramaTimer;
    private float width;
    private float height;

    @Inject
    public VersionedPanoramaRenderer(LabyAPI labyAPI) {
        super(labyAPI);
        this.minecraft = bib.z();
        this.backgroundTexture = this.minecraft.N().a("background", new cdg(256, 256));
    }

    @Override // net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer, net.labymod.core.client.gui.background.panorama.PanoramaRenderer
    public void render(ScreenContext context, float left, float top, float right, float bottom) {
        super.render(context, left, top, right, bottom);
    }

    private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
        this.minecraft.b().e();
        bus.b(0, 0, 256, 256);
        drawPanorama(mouseX, mouseY, partialTicks);
        rotateAndBlurSkybox(partialTicks);
        rotateAndBlurSkybox(partialTicks);
        rotateAndBlurSkybox(partialTicks);
        rotateAndBlurSkybox(partialTicks);
        rotateAndBlurSkybox(partialTicks);
        rotateAndBlurSkybox(partialTicks);
        rotateAndBlurSkybox(partialTicks);
        this.minecraft.b().a(true);
        bus.b(0, 0, this.minecraft.d, this.minecraft.e);
        float scale = this.width > this.height ? 120.0f / this.width : 120.0f / this.height;
        float height = (this.height * scale) / 256.0f;
        float width = (this.width * scale) / 256.0f;
        float width2 = this.width;
        float height2 = this.height;
        bve tessellator = bve.a();
        buk buffer = tessellator.c();
        buffer.a(7, cdy.i);
        buffer.b(0.0d, height2, 0.0d).a(0.5f - height, 0.5f + width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        buffer.b(width2, height2, 0.0d).a(0.5f - height, 0.5f - width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        buffer.b(width2, 0.0d, 0.0d).a(0.5f + height, 0.5f - width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        buffer.b(0.0d, 0.0d, 0.0d).a(0.5f + height, 0.5f + width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        tessellator.b();
    }

    private void drawPanorama(int mouseX, int mouseY, float partialTicks) {
        bve tessellator = bve.a();
        buk buffer = tessellator.c();
        bus.n(GlConst.GL_PROJECTION);
        bus.G();
        bus.F();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        bus.n(GlConst.GL_MODELVIEW);
        bus.G();
        bus.F();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.b(180.0f, 1.0f, 0.0f, 0.0f);
        bus.b(90.0f, 0.0f, 0.0f, 1.0f);
        bus.m();
        bus.d();
        bus.r();
        bus.a(false);
        bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        for (int j = 0; j < 8 * 8; j++) {
            bus.G();
            float x = (((j % 8) / 8) - 0.5f) / 64.0f;
            float y = (((j / 8) / 8) - 0.5f) / 64.0f;
            bus.c(x, y, 0.0f);
            bus.b((MathHelper.sin((this.panoramaTimer + partialTicks) / 400.0f) * 25.0f) + 20.0f, 1.0f, 0.0f, 0.0f);
            bus.b((-(this.panoramaTimer + partialTicks)) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int side = 0; side < 6; side++) {
                bus.G();
                if (side == 1) {
                    bus.b(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 2) {
                    bus.b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 3) {
                    bus.b(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 4) {
                    bus.b(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (side == 5) {
                    bus.b(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.minecraft.N().a(RESOURCES[side]);
                buffer.a(7, cdy.i);
                int alpha = 255 / (j + 1);
                buffer.b(-1.0d, -1.0d, 1.0d).a(0.0d, 0.0d).b(255, 255, 255, alpha).d();
                buffer.b(1.0d, -1.0d, 1.0d).a(1.0d, 0.0d).b(255, 255, 255, alpha).d();
                buffer.b(1.0d, 1.0d, 1.0d).a(1.0d, 1.0d).b(255, 255, 255, alpha).d();
                buffer.b(-1.0d, 1.0d, 1.0d).a(0.0d, 1.0d).b(255, 255, 255, alpha).d();
                tessellator.b();
                bus.H();
            }
            bus.H();
            bus.a(true, true, true, false);
        }
        buffer.c(0.0d, 0.0d, 0.0d);
        bus.a(true, true, true, true);
        bus.n(GlConst.GL_PROJECTION);
        bus.H();
        bus.n(GlConst.GL_MODELVIEW);
        bus.H();
        bus.a(true);
        bus.q();
        bus.k();
    }

    private void rotateAndBlurSkybox(float partialTicks) {
        this.minecraft.N().a(this.backgroundTexture);
        GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, GlConst.GL_LINEAR);
        GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, GlConst.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GlConst.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        bus.m();
        bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        bus.a(true, true, true, false);
        bve tessellator = bve.a();
        buk buffer = tessellator.c();
        buffer.a(7, cdy.i);
        bus.d();
        for (int j = 0; j < 3; j++) {
            float alpha = 1.0f / (j + 1);
            float width = this.width;
            float height = this.height;
            float offset = (j - (3 / 2)) / 256.0f;
            buffer.b(width, height, 0.0d).a(0.0f + offset, 1.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
            buffer.b(width, 0.0d, 0.0d).a(1.0f + offset, 1.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
            buffer.b(0.0d, 0.0d, 0.0d).a(1.0f + offset, 0.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
            buffer.b(0.0d, height, 0.0d).a(0.0f + offset, 0.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
        }
        tessellator.b();
        bus.e();
        bus.a(true, true, true, true);
    }

    protected void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = ((startColor >> 24) & 255) / 255.0f;
        float f1 = ((startColor >> 16) & 255) / 255.0f;
        float f2 = ((startColor >> 8) & 255) / 255.0f;
        float f3 = (startColor & 255) / 255.0f;
        float f4 = ((endColor >> 24) & 255) / 255.0f;
        float f5 = ((endColor >> 16) & 255) / 255.0f;
        float f6 = ((endColor >> 8) & 255) / 255.0f;
        float f7 = (endColor & 255) / 255.0f;
        bus.z();
        bus.m();
        bus.d();
        bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        bus.j(GlConst.GL_SMOOTH);
        bve tessellator = bve.a();
        buk buffer = tessellator.c();
        buffer.a(7, cdy.f);
        buffer.b(right, top, 0.0d).a(f1, f2, f3, f).d();
        buffer.b(left, top, 0.0d).a(f1, f2, f3, f).d();
        buffer.b(left, bottom, 0.0d).a(f5, f6, f7, f4).d();
        buffer.b(right, bottom, 0.0d).a(f5, f6, f7, f4).d();
        tessellator.b();
        bus.j(GlConst.GL_FLAT);
        bus.l();
        bus.e();
        bus.y();
    }
}
