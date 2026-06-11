package net.labymod.v1_12_2.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/MixinLoadingScreenRenderer.class */
@Mixin({bie.class})
public abstract class MixinLoadingScreenRenderer {

    @Shadow
    @Final
    private bib b;

    @Shadow
    private boolean e;

    @Shadow
    private long d;

    @Shadow
    @Final
    private bvd g;

    @Shadow
    private String c;

    @Shadow
    private String a;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$setFramebufferColor(bib lvt_1_1_, CallbackInfo ci) {
        this.g.a(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Overwrite
    public void a(int loadingProgress) {
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        long systemTime = bib.I();
        if (systemTime - this.d >= 100) {
            this.d = systemTime;
            bit resolution = new bit(this.b);
            resolution.e();
            int scaledWidth = resolution.a();
            int scaledHeight = resolution.b();
            if (cii.j()) {
                this.g.f();
            } else {
                bus.m(256);
            }
            this.g.a(false);
            bus.n(GlConst.GL_PROJECTION);
            bus.F();
            bus.a(0.0d, resolution.c(), resolution.d(), 0.0d, 100.0d, 300.0d);
            bus.n(GlConst.GL_MODELVIEW);
            bus.F();
            bus.c(0.0f, 0.0f, -200.0f);
            if (!cii.j()) {
                bus.m(16640);
            }
            bve tessellator = bve.a();
            buk renderer = tessellator.c();
            Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
            Laby.references().renderEnvironmentContext().screenContext().runInContext(VersionedStackProvider.DEFAULT_STACK, Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
                BackgroundRenderer backgroundRenderer = currentTheme.getBackgroundRenderer();
                if (backgroundRenderer == null || !backgroundRenderer.renderBackground(context, null)) {
                    labyMod$renderBackground(renderer, scaledWidth, scaledHeight);
                }
            });
            if (loadingProgress >= 0) {
                int left = (scaledWidth / 2) - (100 / 2);
                int top = (scaledHeight / 2) + 16;
                bus.z();
                renderer.a(7, cdy.f);
                renderer.b(left, top, 0.0d).b(128, 128, 128, 255).d();
                renderer.b(left, top + 2, 0.0d).b(128, 128, 128, 255).d();
                renderer.b(left + 100, top + 2, 0.0d).b(128, 128, 128, 255).d();
                renderer.b(left + 100, top, 0.0d).b(128, 128, 128, 255).d();
                renderer.b(left, top, 0.0d).b(128, 255, 128, 255).d();
                renderer.b(left, top + 2, 0.0d).b(128, 255, 128, 255).d();
                renderer.b(left + loadingProgress, top + 2, 0.0d).b(128, 255, 128, 255).d();
                renderer.b(left + loadingProgress, top, 0.0d).b(128, 255, 128, 255).d();
                tessellator.b();
                bus.y();
            }
            bus.m();
            bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            this.b.k.a(this.c, (scaledWidth - this.b.k.a(this.c)) / 2, ((scaledHeight / 2) - 4) - 16, 16777215);
            this.b.k.a(this.a, (scaledWidth - this.b.k.a(this.a)) / 2, ((scaledHeight / 2) - 4) + 8, 16777215);
            this.g.e();
            if (cii.j()) {
                this.g.c(Display.getFramebufferWidth(), Display.getFramebufferHeight());
            }
            this.b.i();
            try {
                Thread.yield();
            } catch (Exception e) {
            }
        }
        laby3D.restoreStates();
    }

    @Unique
    private void labyMod$renderBackground(buk bufferBuilder, int scaledWidth, int scaledHeight) {
        this.b.N().a(bir.b);
        bufferBuilder.a(7, cdy.i);
        float u = scaledWidth / 32.0f;
        float v = scaledHeight / 32.0f;
        bufferBuilder.b(0.0d, scaledHeight, 0.0d).a(0.0d, v).b(64, 64, 64, 255).d();
        bufferBuilder.b(scaledWidth, scaledHeight, 0.0d).a(u, v).b(64, 64, 64, 255).d();
        bufferBuilder.b(scaledWidth, 0.0d, 0.0d).a(u, 0.0d).b(64, 64, 64, 255).d();
        bufferBuilder.b(0.0d, 0.0d, 0.0d).a(0.0d, 0.0d).b(64, 64, 64, 255).d();
        bve.a().b();
    }
}
