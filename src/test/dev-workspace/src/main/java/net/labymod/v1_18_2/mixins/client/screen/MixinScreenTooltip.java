package net.labymod.v1_18_2.mixins.client.screen;

import java.util.List;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/screen/MixinScreenTooltip.class */
@Mixin({edw.class})
public abstract class MixinScreenTooltip {
    @Shadow
    protected abstract void a(dtm dtmVar, List<egi> list, int i, int i2);

    @Redirect(method = {"renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;Ljava/util/Optional;II)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTooltipInternal(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V"))
    private void labyMod$renderTooltip(edw instance, dtm stack, List<egi> components, int x, int y) {
        labyMod$renderTooltipInternal(stack, components, x, y);
    }

    @Redirect(method = {"renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTooltipInternal(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;II)V"))
    private void labyMod$renderTooltipCharSequence(edw instance, dtm stack, List<egi> components, int x, int y) {
        labyMod$renderTooltipInternal(stack, components, x, y);
    }

    private void labyMod$renderTooltipInternal(dtm stack, List<egi> components, int x, int y) {
        Laby.labyAPI().gfxRenderPipeline().gfx().storeAndRestoreBlaze3DStates(() -> {
            a(stack, components, x, y);
        });
    }
}
