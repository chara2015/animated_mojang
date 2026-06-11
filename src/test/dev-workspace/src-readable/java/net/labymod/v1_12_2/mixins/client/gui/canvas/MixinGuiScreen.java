package net.labymod.v1_12_2.mixins.client.gui.canvas;

import net.labymod.core.client.gui.screen.canvas.CanvasSnapshotFlusher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/canvas/MixinGuiScreen.class */
@Mixin({blk.class})
public class MixinGuiScreen {
    @Inject(method = {"drawScreen(IIF)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawScreen(CallbackInfo ci) {
        CanvasSnapshotFlusher.flushPending();
    }

    @Inject(method = {"drawHoveringText(Ljava/util/List;II)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawTooltip(CallbackInfo ci) {
        CanvasSnapshotFlusher.flushPending();
    }
}
