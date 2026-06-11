package net.labymod.v1_17_1.mixins.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/gui/screens/inventory/MixinBookEditScreen.class */
@Mixin({ebs.class})
public class MixinBookEditScreen {
    private dql labyMod$poseStack;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At("HEAD"))
    private void labyMod$getStack(dql stack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }

    @Redirect(method = {"renderHighlight"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dqp labyMod$addStack(dqg instance, double x, double y, double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float) x, (float) y, (float) z);
    }

    @Inject(method = {"renderHighlight"}, at = {@At("TAIL")})
    private void labyMod$setWhiteShaderColor(enn[] $$0, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
