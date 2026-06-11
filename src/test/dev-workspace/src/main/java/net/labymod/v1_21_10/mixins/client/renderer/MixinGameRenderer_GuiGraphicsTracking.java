package net.labymod.v1_21_10.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/MixinGameRenderer_GuiGraphicsTracking.class */
@Mixin({hfk.class})
public class MixinGameRenderer_GuiGraphicsTracking {
    @WrapOperation(method = {"render"}, at = {@At(value = "NEW", target = "Lnet/minecraft/client/gui/GuiGraphics;")})
    private gdd labyMod$setGuiGraphicsTracking(fzz minecraft, gko guiRenderState, Operation<gdd> original) {
        gdd guiGraphics = (gdd) original.call(new Object[]{minecraft, guiRenderState});
        MinecraftUtil.setCurrentGuiGraphics(guiGraphics);
        return guiGraphics;
    }
}
