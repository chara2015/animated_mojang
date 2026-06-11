package net.labymod.v1_21_5.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/MixinGameRenderer_GuiGraphicsTracking.class */
@Mixin({grd.class})
public class MixinGameRenderer_GuiGraphicsTracking {
    @WrapOperation(method = {"render"}, at = {@At(value = "NEW", target = "Lnet/minecraft/client/gui/GuiGraphics;")})
    private ftk labyMod$setGuiGraphicsTracking(fqq minecraft, a bufferSource, Operation<ftk> original) {
        ftk guiGraphics = (ftk) original.call(new Object[]{minecraft, bufferSource});
        MinecraftUtil.setCurrentGuiGraphics(guiGraphics);
        return guiGraphics;
    }
}
