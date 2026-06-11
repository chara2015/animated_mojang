package net.labymod.v1_21_1.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_1.client.renderer.GameRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/MixinGameRendererAccessor.class */
@Mixin({ges.class})
public class MixinGameRendererAccessor implements GameRendererAccessor {
    private fhz labyMod$guiGraphics;

    @WrapOperation(method = {"render"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)Lnet/minecraft/client/gui/GuiGraphics;")})
    private fhz labyMod$setGuiGraphics(fgo $$0, a $$1, Operation<fhz> original) {
        fhz graphics = (fhz) original.call(new Object[]{$$0, $$1});
        this.labyMod$guiGraphics = graphics;
        return graphics;
    }

    @Override // net.labymod.v1_21_1.client.renderer.GameRendererAccessor
    public fhz labyMod$getGuiGraphics() {
        return this.labyMod$guiGraphics;
    }
}
