package net.labymod.v1_8_9.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.concurrent.Callable;
import net.labymod.api.client.gfx.GlConst;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinLWJGL3Minecraft.class */
@Mixin({ave.class})
public abstract class MixinLWJGL3Minecraft {

    @Shadow
    public int d;

    @Shadow
    public int e;

    @Shadow
    public abstract void a(b bVar);

    @Shadow
    protected abstract void a(int i, int i2);

    @Redirect(method = {"addGraphicsAndWorldToCrashReport"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/crash/CrashReportCategory;addCrashSectionCallable(Ljava/lang/String;Ljava/util/concurrent/Callable;)V", ordinal = 2))
    private void labyMod$fixEarlyOpenGLCrash(c category, String name, Callable<String> callable) {
        try {
            GL.getCapabilities();
            category.a(name, () -> {
                return GL11.glGetString(GlConst.GL_RENDERER) + " GL version " + GL11.glGetString(GlConst.GL_VERSION) + ", " + GL11.glGetString(GlConst.GL_VENDOR);
            });
        } catch (IllegalStateException e) {
        }
    }

    @Inject(method = {"checkWindowResize"}, at = {@At("TAIL")})
    private void labyMod$applyResizeWorkaround(CallbackInfo ci) {
        if (Display.wasResized()) {
            this.d = Display.getWidth();
            this.e = Display.getHeight();
            a(this.d, this.e);
        }
    }

    @WrapOperation(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V")})
    private void labyMod$renderFramebuffer(bfw framebuffer, int width, int height, Operation<Void> original) {
        original.call(new Object[]{framebuffer, Integer.valueOf(Display.getFramebufferWidth()), Integer.valueOf(Display.getFramebufferHeight())});
    }

    @WrapOperation(method = {"drawSplashScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V")})
    private void labyMod$renderSplashFramebuffer(bfw framebuffer, int width, int height, Operation<Void> original) {
        original.call(new Object[]{framebuffer, Integer.valueOf(Display.getFramebufferWidth()), Integer.valueOf(Display.getFramebufferHeight())});
    }
}
