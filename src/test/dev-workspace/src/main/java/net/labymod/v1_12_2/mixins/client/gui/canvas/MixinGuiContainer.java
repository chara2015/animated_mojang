package net.labymod.v1_12_2.mixins.client.gui.canvas;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.client.gui.screen.canvas.CanvasSnapshotFlusher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/canvas/MixinGuiContainer.class */
@Mixin({bmg.class})
public class MixinGuiContainer {
    @Inject(method = {"drawSlot(Lnet/minecraft/inventory/Slot;)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawSlot(CallbackInfo ci) {
        CanvasSnapshotFlusher.flushPending();
    }

    @WrapOperation(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGuiContainerBackgroundLayer(FII)V")})
    private void labyMod$flushBeforeDrawBackground(bmg instance, float a, int mouseX, int mouseY, Operation<Void> original) {
        CanvasSnapshotFlusher.flushPending();
        original.call(new Object[]{instance, Float.valueOf(a), Integer.valueOf(mouseX), Integer.valueOf(mouseY)});
    }

    @Inject(method = {"drawGuiContainerForegroundLayer(II)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawForeground(CallbackInfo ci) {
        CanvasSnapshotFlusher.flushPending();
    }
}
