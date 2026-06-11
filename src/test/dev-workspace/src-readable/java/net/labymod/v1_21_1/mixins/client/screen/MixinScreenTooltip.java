package net.labymod.v1_21_1.mixins.client.screen;

import java.util.List;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/screen/MixinScreenTooltip.class */
@Mixin({fod.class})
public abstract class MixinScreenTooltip {

    @Shadow
    protected fhx o;

    @Redirect(method = {"renderWithTooltip"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;II)V"))
    private void labyMod$renderTooltip(fhz graphics, fhx font, List<aya> values, fqo positioner, int mouseX, int mouseY) {
        Laby.gfx().storeAndRestoreBlaze3DStates(() -> {
            graphics.a(font, values, positioner, mouseX, mouseY);
        });
    }
}
