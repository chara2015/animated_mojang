package net.labymod.v1_12_2.mixins.client.gui.canvas;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.client.gui.screen.canvas.CanvasSnapshotFlusher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/canvas/MixinGuiSlot.class */
@Mixin({bjr.class})
public class MixinGuiSlot {
    @Inject(method = {"drawScreen(IIF)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawScreen(CallbackInfo ci) {
        CanvasSnapshotFlusher.flushPending();
    }

    @WrapOperation(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;drawBackground()V")})
    private void labyMod$flushBeforeDrawBackground(bjr instance, Operation<Void> original) {
        original.call(new Object[]{instance});
        CanvasSnapshotFlusher.flushPending();
    }

    @WrapOperation(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;overlayBackground(IIII)V")})
    private void labyMod$flushBeforeDrawOverlay(bjr instance, int x, int y, int width, int height, Operation<Void> original) {
        original.call(new Object[]{instance, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(width), Integer.valueOf(height)});
        CanvasSnapshotFlusher.flushPending();
    }

    @WrapOperation(method = {"drawSelectionBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;drawSlot(IIIIIIF)V")})
    private void labyMod$flushBeforeDrawSlot(bjr instance, int i0, int i1, int i2, int i3, int i4, int i5, float v, Operation<Void> original) {
        original.call(new Object[]{instance, Integer.valueOf(i0), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Float.valueOf(v)});
        CanvasSnapshotFlusher.flushPending();
    }
}
