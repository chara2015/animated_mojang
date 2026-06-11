package net.labymod.v1_16_5.mixins.client.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.v1_16_5.client.gfx.pipeline.blaze3d.program.LabyRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/entity/MixinEntityRenderDispatcher_Hitbox.class */
@Mixin({eet.class})
public class MixinEntityRenderDispatcher_Hitbox {
    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;lines()Lnet/minecraft/client/renderer/RenderType;")})
    private eao labyMod$getRenderType(Operation<eao> original) {
        HitboxConfig hitbox = Laby.labyAPI().config().ingame().hitbox();
        if (hitbox.enabled().get().booleanValue()) {
            return LabyRenderType.lines(hitbox.lineSize().get().floatValue());
        }
        return (eao) original.call(new Object[0]);
    }

    @WrapOperation(method = {"renderHitbox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDDDDFFFF)V")})
    private static void labyMod$renderHitbox(dfm poseStack, dfq vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha, Operation<Void> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        boolean eyeHitbox = red == 1.0f && green == 0.0f && blue == 0.0f;
        if (hitbox.enabled().get().booleanValue() && eyeHitbox) {
            int argb = hitbox.eyeBoxColor().get().get();
            red = ColorFormat.ARGB32.normalizedRed(argb);
            green = ColorFormat.ARGB32.normalizedGreen(argb);
            blue = ColorFormat.ARGB32.normalizedBlue(argb);
        }
        if (eyeHitbox && config.multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            return;
        }
        original.call(new Object[]{poseStack, vertexConsumer, Double.valueOf(x1), Double.valueOf(y1), Double.valueOf(z1), Double.valueOf(x2), Double.valueOf(y2), Double.valueOf(z2), Float.valueOf(red), Float.valueOf(green), Float.valueOf(blue), Float.valueOf(alpha)});
    }

    @WrapOperation(method = {"renderHitbox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;FFF)V")})
    private static void labyMod$renderHitbox(eet instance, dfm poseStack, dfq vertexConsumer, aqa entity, float red, float green, float blue, Operation<Void> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        boolean mainHitbox = red == 1.0f && green == 1.0f && blue == 1.0f;
        if (hitbox.enabled().get().booleanValue() && mainHitbox) {
            int argb = hitbox.boxColor().get().get();
            red = ColorFormat.ARGB32.normalizedRed(argb);
            green = ColorFormat.ARGB32.normalizedGreen(argb);
            blue = ColorFormat.ARGB32.normalizedBlue(argb);
        }
        original.call(new Object[]{instance, poseStack, vertexConsumer, entity, Float.valueOf(red), Float.valueOf(green), Float.valueOf(blue)});
    }

    @Inject(method = {"renderHitbox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;", shift = At.Shift.AFTER)}, cancellable = true)
    private static void labyMod$renderHitbox(dfm $$0, dfq $$1, aqa $$2, float $$3, CallbackInfo ci) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            ci.cancel();
        }
    }

    @WrapOperation(method = {"renderHitbox"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(IIII)Lcom/mojang/blaze3d/vertex/VertexConsumer;")})
    private static dfq labyMod$renderHitboxViewVector(dfq instance, int red, int green, int blue, int alpha, Operation<dfq> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        if (hitbox.enabled().get().booleanValue()) {
            int argb = hitbox.eyeLineColor().get().get();
            red = ColorFormat.ARGB32.red(argb);
            green = ColorFormat.ARGB32.green(argb);
            blue = ColorFormat.ARGB32.blue(argb);
        }
        return (dfq) original.call(new Object[]{instance, Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(alpha)});
    }
}
