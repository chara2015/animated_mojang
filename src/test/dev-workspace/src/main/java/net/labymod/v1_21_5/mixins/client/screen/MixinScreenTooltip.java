package net.labymod.v1_21_5.mixins.client.screen;

import java.util.List;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/screen/MixinScreenTooltip.class */
@Mixin({fzq.class})
public abstract class MixinScreenTooltip {

    @Shadow
    protected fti p;

    @Redirect(method = {"renderWithTooltip"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;II)V"))
    private void labyMod$renderTooltip(ftk graphics, fti font, List<azk> values, gcd positioner, int mouseX, int mouseY) {
        Laby.gfx().storeAndRestoreBlaze3DStates(() -> {
            graphics.a(font, values, positioner, mouseX, mouseY);
        });
    }
}
