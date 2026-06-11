package net.labymod.v1_21_5.mixins.client.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.v1_21_5.client.gfx.pipeline.blaze3d.program.LabyRenderType;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/entity/MixinEntityRenderDispatcher_Hitbox.class */
@Mixin({gxt.class})
public class MixinEntityRenderDispatcher_Hitbox {
    @WrapOperation(method = {"renderHitboxes"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;lines()Lnet/minecraft/client/renderer/RenderType;")})
    private gry labyMod$getRenderType(Operation<gry> original) {
        HitboxConfig hitbox = Laby.labyAPI().config().ingame().hitbox();
        if (hitbox.enabled().get().booleanValue()) {
            return LabyRenderType.lines(hitbox.lineSize().get().floatValue());
        }
        return (gry) original.call(new Object[0]);
    }

    @WrapOperation(method = {"renderHitbox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShapeRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDDDDFFFF)V")})
    private static void labyMod$renderHitbox(fld $$0, flg $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, float red, float green, float blue, float alpha, Operation<Void> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        boolean mainHitbox = red == 1.0f && green == 1.0f && blue == 1.0f;
        boolean eyeHitbox = red == 1.0f && green == 0.0f && blue == 0.0f;
        if (hitbox.enabled().get().booleanValue()) {
            if (mainHitbox) {
                int argb = hitbox.boxColor().get().get();
                red = ColorFormat.ARGB32.normalizedRed(argb);
                green = ColorFormat.ARGB32.normalizedGreen(argb);
                blue = ColorFormat.ARGB32.normalizedBlue(argb);
            }
            if (eyeHitbox) {
                int argb2 = hitbox.eyeBoxColor().get().get();
                red = ColorFormat.ARGB32.normalizedRed(argb2);
                green = ColorFormat.ARGB32.normalizedGreen(argb2);
                blue = ColorFormat.ARGB32.normalizedBlue(argb2);
            }
        }
        if (eyeHitbox && config.multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            return;
        }
        original.call(new Object[]{$$0, $$1, Double.valueOf($$2), Double.valueOf($$3), Double.valueOf($$4), Double.valueOf($$5), Double.valueOf($$6), Double.valueOf($$7), Float.valueOf(red), Float.valueOf(green), Float.valueOf(blue), Float.valueOf(alpha)});
    }

    @WrapOperation(method = {"renderHitboxesAndViewVector"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShapeRenderer;renderVector(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lorg/joml/Vector3f;Lnet/minecraft/world/phys/Vec3;I)V")})
    private static void labyMod$renderHitboxViewVector(fld $$0, flg $$1, Vector3f $$2, fgc $$3, int $$4, Operation<Void> original) {
        LabyConfig config = Laby.labyAPI().config();
        if (config.multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            return;
        }
        HitboxConfig hitbox = config.ingame().hitbox();
        if (hitbox.enabled().get().booleanValue()) {
            $$4 = ColorFormat.ARGB32.withAlpha(hitbox.eyeLineColor().get().get(), 255);
        }
        original.call(new Object[]{$$0, $$1, $$2, $$3, Integer.valueOf($$4)});
    }
}
