package net.labymod.v1_12_2.mixins.util;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/util/MixinMouseHelper.class */
@Mixin({bic.class})
public class MixinMouseHelper implements MouseHandlerAccessor {
    @Inject(method = {"grabMouseCursor"}, at = {@At("HEAD")})
    private void labyMod$setCursorPosition(CallbackInfo ci) {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
    }

    @Insert(method = {"grabMouseCursor"}, at = @At("TAIL"))
    private void labymod$grabMouse(InsertInfo ci) {
        labyMod$updateMouseGrabbed(true);
    }

    @Insert(method = {"ungrabMouseCursor"}, at = @At("TAIL"))
    private void labymod$releaseMouse(InsertInfo ci) {
        labyMod$updateMouseGrabbed(false);
    }

    private void labyMod$updateMouseGrabbed(boolean mouseGrabbed) {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        int scaledHeight = window.getScaledHeight();
        double x = (((double) Mouse.getX()) * ((double) window.getScaledWidth())) / ((double) window.getRawWidth());
        double y = ((double) scaledHeight) - ((((double) Mouse.getY()) * ((double) scaledHeight)) / ((double) window.getRawHeight()));
        MouseBridge bridge = LabyMod.references().mouseBridge();
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(mouseGrabbed);
        bridge.absoluteMouse().setGrabbed(mouseGrabbed);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void grabMouseNative() {
        Mouse.setGrabbed(true);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void ungrabMouseNative() {
        Mouse.setGrabbed(false);
    }
}
