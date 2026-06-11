package net.labymod.v1_20_1.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/MixinGameRenderer_GuiGraphicsTracking.class */
@Mixin({fjq.class})
public class MixinGameRenderer_GuiGraphicsTracking {
    @WrapOperation(method = {"render"}, at = {@At(value = "NEW", target = "Lnet/minecraft/client/gui/GuiGraphics;")})
    private eox labyMod$setGuiGraphicsTracking(enn minecraft, a bufferSource, Operation<eox> original) {
        eox guiGraphics = (eox) original.call(new Object[]{minecraft, bufferSource});
        MinecraftUtil.setCurrentGuiGraphics(guiGraphics);
        return guiGraphics;
    }
}
