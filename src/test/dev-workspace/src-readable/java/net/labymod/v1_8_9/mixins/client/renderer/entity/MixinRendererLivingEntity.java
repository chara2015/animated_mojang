package net.labymod.v1_8_9.mixins.client.renderer.entity;

import java.nio.FloatBuffer;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/MixinRendererLivingEntity.class */
@Mixin({bjl.class})
public abstract class MixinRendererLivingEntity {
    @Redirect(method = {"renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isSneaking()Z", ordinal = 1))
    public boolean labyMod$alwaysRedirectNameTagRendering(pr entity) {
        if (!(entity instanceof wn)) {
            RenderUtil.setNameTagType(TagType.MAIN_TAG);
            return false;
        }
        return false;
    }

    @Insert(method = {"canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$showOwnName(pr entity, InsertInfoReturnable<Boolean> cir) {
        if (entity == ave.A().h) {
            cir.setReturnValue(LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }

    @Redirect(method = {"setBrightness"}, at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;"), slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 0), to = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 3)))
    private FloatBuffer labyMod$damageColored(FloatBuffer buffer, float value) {
        DamageOldAnimation animation = (DamageOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(DamageOldAnimation.NAME);
        if (animation == null) {
            return buffer.put(value);
        }
        return animation.updateBuffer(buffer, value);
    }
}
