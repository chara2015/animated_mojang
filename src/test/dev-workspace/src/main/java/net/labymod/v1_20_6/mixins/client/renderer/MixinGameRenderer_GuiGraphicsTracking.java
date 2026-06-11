package net.labymod.v1_20_6.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinGameRenderer_GuiGraphicsTracking.class */
@Mixin({gdj.class})
public class MixinGameRenderer_GuiGraphicsTracking {
    @WrapOperation(method = {"render"}, at = {@At(value = "NEW", target = "Lnet/minecraft/client/gui/GuiGraphics;")})
    private fgt labyMod$setGuiGraphicsTracking(ffh minecraft, a bufferSource, Operation<fgt> original) {
        fgt guiGraphics = (fgt) original.call(new Object[]{minecraft, bufferSource});
        MinecraftUtil.setCurrentGuiGraphics(guiGraphics);
        return guiGraphics;
    }
}
