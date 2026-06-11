package net.labymod.v1_8_9.client.gui.background;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/background/VersionedPanoramaRenderer.class */
@Singleton
@Implements(PanoramaRenderer.class)
public class VersionedPanoramaRenderer extends AbstractPanoramaRenderer {
    private static final jy[] RESOURCES = {new jy("textures/gui/title/background/panorama_0.png"), new jy("textures/gui/title/background/panorama_1.png"), new jy("textures/gui/title/background/panorama_2.png"), new jy("textures/gui/title/background/panorama_3.png"), new jy("textures/gui/title/background/panorama_4.png"), new jy("textures/gui/title/background/panorama_5.png")};
    private final ave minecraft;
    private final jy backgroundTexture;
    private float panoramaTimer;
    private float width;
    private float height;

    @Inject
    public VersionedPanoramaRenderer(LabyAPI labyAPI) {
        super(labyAPI);
        this.minecraft = ave.A();
        this.backgroundTexture = this.minecraft.P().a("background", new blz(256, 256));
    }

    @Override // net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer, net.labymod.core.client.gui.background.panorama.PanoramaRenderer
    public void render(ScreenContext context, float left, float top, float right, float bottom) {
        super.render(context, left, top, right, bottom);
    }

    private void renderSkybox(int mouseX, int mouseY, float tickDelta) {
        this.minecraft.b().e();
        bfl.b(0, 0, 256, 256);
        drawPanorama(mouseX, mouseY, tickDelta);
        rotateAndBlurSkybox(tickDelta);
        rotateAndBlurSkybox(tickDelta);
        rotateAndBlurSkybox(tickDelta);
        rotateAndBlurSkybox(tickDelta);
        rotateAndBlurSkybox(tickDelta);
        rotateAndBlurSkybox(tickDelta);
        rotateAndBlurSkybox(tickDelta);
        this.minecraft.b().a(true);
        bfl.b(0, 0, this.minecraft.d, this.minecraft.e);
        float scale = this.width > this.height ? 120.0f / this.width : 120.0f / this.height;
        float height = (this.height * scale) / 256.0f;
        float width = (this.width * scale) / 256.0f;
        float width2 = this.width;
        float height2 = this.height;
        bfx tessellator = bfx.a();
        bfd worldrenderer = tessellator.c();
        worldrenderer.a(7, bms.i);
        worldrenderer.b(0.0d, height2, 0.0d).a(0.5f - height, 0.5f + width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        worldrenderer.b(width2, height2, 0.0d).a(0.5f - height, 0.5f - width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        worldrenderer.b(width2, 0.0d, 0.0d).a(0.5f + height, 0.5f - width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        worldrenderer.b(0.0d, 0.0d, 0.0d).a(0.5f + height, 0.5f + width).a(1.0f, 1.0f, 1.0f, 1.0f).d();
        tessellator.b();
    }

    private void drawPanorama(int mouseX, int mouseY, float partialTicks) {
        bfx tessellator = bfx.a();
        bfd renderer = tessellator.c();
        bfl.n(GlConst.GL_PROJECTION);
        bfl.E();
        bfl.D();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        bfl.n(GlConst.GL_MODELVIEW);
        bfl.E();
        bfl.D();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        bfl.b(180.0f, 1.0f, 0.0f, 0.0f);
        bfl.b(90.0f, 0.0f, 0.0f, 1.0f);
        bfl.l();
        bfl.c();
        bfl.p();
        bfl.a(false);
        bfl.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        for (int j = 0; j < 8 * 8; j++) {
            bfl.E();
            float x = (((j % 8) / 8) - 0.5f) / 64.0f;
            float y = (((j / 8) / 8) - 0.5f) / 64.0f;
            bfl.b(x, y, 0.0f);
            bfl.b((ns.a((this.panoramaTimer + partialTicks) / 400.0f) * 25.0f) + 20.0f, 1.0f, 0.0f, 0.0f);
            bfl.b((-(this.panoramaTimer + partialTicks)) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int side = 0; side < 6; side++) {
                bfl.E();
                if (side == 1) {
                    bfl.b(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 2) {
                    bfl.b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 3) {
                    bfl.b(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (side == 4) {
                    bfl.b(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (side == 5) {
                    bfl.b(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.minecraft.P().a(RESOURCES[side]);
                renderer.a(7, bms.i);
                int alpha = 255 / (j + 1);
                renderer.b(-1.0d, -1.0d, 1.0d).a(0.0d, 0.0d).b(255, 255, 255, alpha).d();
                renderer.b(1.0d, -1.0d, 1.0d).a(1.0d, 0.0d).b(255, 255, 255, alpha).d();
                renderer.b(1.0d, 1.0d, 1.0d).a(1.0d, 1.0d).b(255, 255, 255, alpha).d();
                renderer.b(-1.0d, 1.0d, 1.0d).a(0.0d, 1.0d).b(255, 255, 255, alpha).d();
                tessellator.b();
                bfl.F();
            }
            bfl.F();
            bfl.a(true, true, true, false);
        }
        renderer.c(0.0d, 0.0d, 0.0d);
        bfl.a(true, true, true, true);
        bfl.n(GlConst.GL_PROJECTION);
        bfl.F();
        bfl.n(GlConst.GL_MODELVIEW);
        bfl.F();
        bfl.a(true);
        bfl.o();
        bfl.j();
    }

    private void rotateAndBlurSkybox(float tickDelta) {
        this.minecraft.P().a(this.backgroundTexture);
        GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, GlConst.GL_LINEAR);
        GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, GlConst.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GlConst.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        bfl.l();
        bfl.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        bfl.a(true, true, true, false);
        bfx tessellator = bfx.a();
        bfd renderer = tessellator.c();
        renderer.a(7, bms.i);
        bfl.c();
        for (int j = 0; j < 3; j++) {
            float alpha = 1.0f / (j + 1);
            float width = this.width;
            float height = this.height;
            float offset = (j - (3 / 2)) / 256.0f;
            renderer.b(width, height, 0.0d).a(0.0f + offset, 1.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
            renderer.b(width, 0.0d, 0.0d).a(1.0f + offset, 1.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
            renderer.b(0.0d, 0.0d, 0.0d).a(1.0f + offset, 0.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
            renderer.b(0.0d, height, 0.0d).a(0.0f + offset, 0.0d).a(1.0f, 1.0f, 1.0f, alpha).d();
        }
        tessellator.b();
        bfl.d();
        bfl.a(true, true, true, true);
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
        bfl.x();
        bfl.l();
        bfl.c();
        bfl.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        bfl.j(GlConst.GL_SMOOTH);
        bfx tessellator = bfx.a();
        bfd worldrenderer = tessellator.c();
        worldrenderer.a(7, bms.f);
        worldrenderer.b(right, top, 0.0d).a(f1, f2, f3, f).d();
        worldrenderer.b(left, top, 0.0d).a(f1, f2, f3, f).d();
        worldrenderer.b(left, bottom, 0.0d).a(f5, f6, f7, f4).d();
        worldrenderer.b(right, bottom, 0.0d).a(f5, f6, f7, f4).d();
        tessellator.b();
        bfl.j(GlConst.GL_FLAT);
        bfl.k();
        bfl.d();
        bfl.w();
    }
}
