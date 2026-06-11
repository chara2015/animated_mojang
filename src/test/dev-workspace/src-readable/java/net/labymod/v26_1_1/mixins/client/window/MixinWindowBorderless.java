package net.labymod.v26_1_1.mixins.client.window;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.mojang.blaze3d.systems.GpuBackend;
import javax.annotation.Nullable;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;
import net.labymod.v26_1_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/window/MixinWindowBorderless.class */
@Mixin({Window.class})
public abstract class MixinWindowBorderless implements FullscreenWindowController {

    @Shadow
    private boolean fullscreen;

    @Shadow
    private int windowedX;

    @Shadow
    private int windowedY;

    @Shadow
    private int windowedWidth;

    @Shadow
    private int windowedHeight;
    private final MutableRectangle labyMod$previousWindowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    private final MutableRectangle labyMod$windowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    private BorderlessWindow labyMod$borderlessWindow;

    @Shadow
    public abstract long handle();

    @Shadow
    @Nullable
    public abstract Monitor findBestMonitor();

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$init(WindowEventHandler eventHandler, DisplayData displayData, String fullscreenVideoModeString, String title, GpuBackend backend, CallbackInfo ci) {
        this.labyMod$windowedRectangle.setPosition(this.windowedX, this.windowedY);
        this.labyMod$windowedRectangle.setSize(this.windowedWidth, this.windowedHeight);
        this.labyMod$borderlessWindow = new BorderlessWindow(handle(), () -> {
            return this.labyMod$windowedRectangle;
        }, () -> {
            return this.labyMod$previousWindowedRectangle;
        }, handle -> {
            BorderlessWindow.VideoMode videoMode;
            Monitor bestMonitor = findBestMonitor();
            if (bestMonitor == null) {
                return null;
            }
            VideoMode currentMode = bestMonitor.getCurrentMode();
            int x = bestMonitor.getX();
            int y = bestMonitor.getY();
            if (currentMode == null) {
                videoMode = null;
            } else {
                videoMode = new BorderlessWindow.VideoMode(currentMode.getWidth(), currentMode.getHeight());
            }
            return new BorderlessWindow.Monitor(x, y, videoMode);
        }, this);
        MinecraftUtil.setBorderlessWindow(this.labyMod$borderlessWindow);
    }

    @Inject(method = {"onMove"}, at = {@At("TAIL")})
    private void labyMod$setPosition(long windowHandle, int x, int y, CallbackInfo ci) {
        if (!this.fullscreen && !this.labyMod$borderlessWindow.isFullscreen()) {
            this.labyMod$windowedRectangle.setPosition(x, y);
        }
    }

    @Inject(method = {"onResize"}, at = {@At("TAIL")})
    private void labyMod$setSize(long windowHandle, int width, int height, CallbackInfo ci) {
        if (!this.fullscreen && !this.labyMod$borderlessWindow.isFullscreen()) {
            this.labyMod$windowedRectangle.setSize(width, height);
        }
    }

    @Inject(method = {"toggleFullScreen"}, at = {@At("TAIL")})
    private void labyMod$setBorderlessFullscreen(CallbackInfo ci) {
        setWindowFullscreen(this.fullscreen);
    }

    @Inject(method = {"isFullscreen"}, at = {@At("TAIL")}, cancellable = true)
    private void labyMod$isFullscreen(CallbackInfoReturnable<Boolean> cir) {
        if (GLFWUtil.isBorderlessWindowed()) {
            cir.setReturnValue(Boolean.valueOf(this.labyMod$borderlessWindow.isFullscreen()));
        }
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController
    public boolean isWindowFullscreen() {
        return this.fullscreen;
    }

    @Override // net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController
    public void setWindowFullscreen(boolean value) {
        this.fullscreen = value;
        if (GLFWUtil.isBorderlessWindowed()) {
            this.fullscreen = false;
            this.labyMod$borderlessWindow.toggleFullscreen();
        }
    }
}
