package net.labymod.v1_8_9.mixins.client.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.util.color.format.ColorFormat;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/MixinEntityRenderDispatcher_Hitbox.class */
@Mixin({biu.class})
public class MixinEntityRenderDispatcher_Hitbox {
    @Inject(method = {"renderDebugBoundingBox"}, at = {@At("HEAD")})
    private void labyMod$setLineWidth(pk lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, float lvt_9_1_, CallbackInfo ci) {
        HitboxConfig hitbox = Laby.labyAPI().config().ingame().hitbox();
        GL11.glLineWidth(hitbox.enabled().get().booleanValue() ? hitbox.lineSize().get().floatValue() : 1.0f);
    }

    @WrapOperation(method = {"renderDebugBoundingBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V")})
    private static void labyMod$renderHitbox(aug aabb, int red, int green, int blue, int alpha, Operation<Void> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        boolean mainHitbox = red == 255 && green == 255 && blue == 255;
        boolean eyeHitbox = red == 255 && green == 0 && blue == 0;
        if (hitbox.enabled().get().booleanValue()) {
            if (mainHitbox) {
                int argb = hitbox.boxColor().get().get();
                red = ColorFormat.ARGB32.red(argb);
                green = ColorFormat.ARGB32.green(argb);
                blue = ColorFormat.ARGB32.blue(argb);
            }
            if (eyeHitbox) {
                int argb2 = hitbox.eyeBoxColor().get().get();
                red = ColorFormat.ARGB32.red(argb2);
                green = ColorFormat.ARGB32.green(argb2);
                blue = ColorFormat.ARGB32.blue(argb2);
            }
        }
        if (eyeHitbox && config.multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            return;
        }
        original.call(new Object[]{aabb, Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(alpha)});
    }

    @Inject(method = {"renderDebugBoundingBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getLook(F)Lnet/minecraft/util/Vec3;", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$renderHitbox(pk lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, float lvt_9_1_, CallbackInfo ci) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            ci.cancel();
            bfl.w();
            bfl.e();
            bfl.o();
            bfl.k();
            bfl.a(true);
        }
    }

    @WrapOperation(method = {"renderDebugBoundingBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;")})
    private static bfd labyMod$renderHitboxViewVector(bfd instance, int red, int green, int blue, int alpha, Operation<bfd> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        if (hitbox.enabled().get().booleanValue()) {
            int argb = hitbox.eyeLineColor().get().get();
            red = ColorFormat.ARGB32.red(argb);
            green = ColorFormat.ARGB32.green(argb);
            blue = ColorFormat.ARGB32.blue(argb);
        }
        return (bfd) original.call(new Object[]{instance, Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(alpha)});
    }
}
