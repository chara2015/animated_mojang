package net.labymod.v1_21.mixins.client.window;

import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/window/MixinWindowBorderless.class */
@Mixin({fam.class})
public abstract class MixinWindowBorderless implements FullscreenWindowController {

    @Shadow
    private boolean m;

    @Shadow
    private int h;

    @Shadow
    private int i;

    @Shadow
    private int j;

    @Shadow
    private int k;
    private final MutableRectangle labyMod$previousWindowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    private final MutableRectangle labyMod$windowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    private BorderlessWindow labyMod$borderlessWindow;

    @Shadow
    public abstract long j();

    @Shadow
    @Nullable
    public abstract fah u();

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$init(fan eventHandler, fak screenManager, faa data, String videoModeName, String title, CallbackInfo ci) {
        this.labyMod$windowedRectangle.setPosition(this.h, this.i);
        this.labyMod$windowedRectangle.setSize(this.j, this.k);
        this.labyMod$borderlessWindow = new BorderlessWindow(j(), () -> {
            return this.labyMod$windowedRectangle;
        }, () -> {
            return this.labyMod$previousWindowedRectangle;
        }, handle -> {
            BorderlessWindow.VideoMode videoMode;
            fah bestMonitor = u();
            if (bestMonitor == null) {
                return null;
            }
            fal currentMode = bestMonitor.b();
            int iC = bestMonitor.c();
            int iD = bestMonitor.d();
            if (currentMode == null) {
                videoMode = null;
            } else {
                videoMode = new BorderlessWindow.VideoMode(currentMode.a(), currentMode.b());
            }
            return new BorderlessWindow.Monitor(iC, iD, videoMode);
        }, this);
    }

    @Inject(method = {"onMove"}, at = {@At("TAIL")})
    private void labyMod$setPosition(long windowHandle, int x, int y, CallbackInfo ci) {
        if (!this.m && !this.labyMod$borderlessWindow.isFullscreen()) {
            this.labyMod$windowedRectangle.setPosition(x, y);
        }
    }

    @Inject(method = {"onResize"}, at = {@At("TAIL")})
    private void labyMod$setSize(long windowHandle, int width, int height, CallbackInfo ci) {
        if (!this.m && !this.labyMod$borderlessWindow.isFullscreen()) {
            this.labyMod$windowedRectangle.setSize(width, height);
        }
    }

    @Inject(method = {"toggleFullScreen"}, at = {@At("TAIL")})
    private void labyMod$setBorderlessFullscreen(CallbackInfo ci) {
        setWindowFullscreen(this.m);
    }

    @Inject(method = {"isFullscreen"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$isFullscreen(CallbackInfoReturnable<Boolean> cir) {
        if (GLFWUtil.isBorderlessWindowed()) {
            cir.setReturnValue(Boolean.valueOf(this.labyMod$borderlessWindow.isFullscreen()));
        }
    }

    @Inject(method = {"updateDisplay"}, at = {@At("TAIL")})
    private void labyMod$pollTasks(CallbackInfo ci) {
        this.labyMod$borderlessWindow.poll();
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController
    public boolean isWindowFullscreen() {
        return this.m;
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController
    public void setWindowFullscreen(boolean value) {
        this.m = value;
        if (GLFWUtil.isBorderlessWindowed()) {
            this.m = false;
            this.labyMod$borderlessWindow.toggleFullscreen();
        }
    }
}
