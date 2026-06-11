package net.labymod.v1_18_2.mixins.client.gui.screens;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/screens/MixinWinScreen.class */
@Mixin({eed.class})
public class MixinWinScreen {
    private dtm labyMod$poseStack;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At("HEAD"))
    private void labyMod$getStack(dtm stack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dtq labyMod$addStack(dth instance, double x, double y, double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float) x, (float) y, (float) z);
    }

    @Redirect(method = {"renderBg"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dtq labyMod$addBackgroundStack(dth instance, double x, double y, double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float) x, (float) y, (float) z);
    }
}
