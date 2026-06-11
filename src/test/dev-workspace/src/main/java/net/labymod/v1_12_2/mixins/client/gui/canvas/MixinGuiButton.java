package net.labymod.v1_12_2.mixins.client.gui.canvas;

import net.labymod.core.client.gui.screen.canvas.CanvasSnapshotFlusher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/canvas/MixinGuiButton.class */
@Mixin({bja.class})
public class MixinGuiButton {
    @Inject(method = {"drawButton(Lnet/minecraft/client/Minecraft;IIF)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawButton(CallbackInfo ci) {
        CanvasSnapshotFlusher.flushPending();
    }
}
