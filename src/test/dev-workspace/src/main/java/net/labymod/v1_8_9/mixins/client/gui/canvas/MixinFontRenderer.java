package net.labymod.v1_8_9.mixins.client.gui.canvas;

import net.labymod.core.client.gui.screen.canvas.CanvasSnapshotFlusher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/canvas/MixinFontRenderer.class */
@Mixin({avn.class})
public class MixinFontRenderer {
    @Inject(method = {"renderString(Ljava/lang/String;FFIZ)I"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeRenderString(CallbackInfoReturnable<Integer> ci) {
        CanvasSnapshotFlusher.flushPending();
    }
}
