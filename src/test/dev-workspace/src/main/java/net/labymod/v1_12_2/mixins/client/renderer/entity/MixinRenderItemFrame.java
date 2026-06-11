package net.labymod.v1_12_2.mixins.client.renderer.entity;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.RenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/MixinRenderItemFrame.class */
@Mixin({bzv.class})
public class MixinRenderItemFrame {
    @Inject(method = {"renderName(Lnet/minecraft/entity/item/EntityItemFrame;DDD)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItemFrame;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE)})
    public void labyMod$setMainNameTagType(acb entity, double x, double y, double z, CallbackInfo callbackInfo) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }
}
