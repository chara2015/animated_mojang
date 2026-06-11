package net.labymod.v1_21_4.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_4.client.renderer.GameRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/MixinGameRendererAccessor.class */
@Mixin({glq.class})
public class MixinGameRendererAccessor implements GameRendererAccessor {
    private fof labyMod$guiGraphics;

    @WrapOperation(method = {"render"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)Lnet/minecraft/client/gui/GuiGraphics;")})
    private fof labyMod$setGuiGraphics(flk $$0, a $$1, Operation<fof> original) {
        fof graphics = (fof) original.call(new Object[]{$$0, $$1});
        this.labyMod$guiGraphics = graphics;
        return graphics;
    }

    @Override // net.labymod.v1_21_4.client.renderer.GameRendererAccessor
    public fof labyMod$getGuiGraphics() {
        return this.labyMod$guiGraphics;
    }
}
