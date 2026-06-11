package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.RenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/MixinRenderItemFrame.class */
@Mixin({bjg.class})
public class MixinRenderItemFrame {
    @Inject(method = {"renderName(Lnet/minecraft/entity/item/EntityItemFrame;DDD)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/tileentity/RenderItemFrame;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE, ordinal = 0)})
    public void labyMod$setMainNameTagType(uo entity, double x, double y, double z, CallbackInfo callbackInfo) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }
}
