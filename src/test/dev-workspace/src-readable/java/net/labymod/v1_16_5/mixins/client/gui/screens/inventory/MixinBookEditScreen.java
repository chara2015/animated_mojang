package net.labymod.v1_16_5.mixins.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/screens/inventory/MixinBookEditScreen.class */
@Mixin({dpu.class})
public class MixinBookEditScreen {
    private dfm labyMod$poseStack;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At("HEAD"))
    private void labyMod$getStack(dfm stack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }

    @Redirect(method = {"renderHighlight"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dfq labyMod$addStack(dfh instance, double x, double y, double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float) x, (float) y, (float) z);
    }

    @Inject(method = {"renderHighlight"}, at = {@At("TAIL")})
    private void labyMod$setWhiteShaderColor(eal[] $$0, CallbackInfo ci) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
