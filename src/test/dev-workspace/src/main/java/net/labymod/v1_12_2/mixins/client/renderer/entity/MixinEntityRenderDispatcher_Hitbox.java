package net.labymod.v1_12_2.mixins.client.renderer.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/MixinEntityRenderDispatcher_Hitbox.class */
@Mixin({bzf.class})
public class MixinEntityRenderDispatcher_Hitbox {
    @Inject(method = {"renderDebugBoundingBox"}, at = {@At("HEAD")})
    private void labyMod$setLineWidth(vg lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, float lvt_9_1_, CallbackInfo ci) {
        HitboxConfig hitbox = Laby.labyAPI().config().ingame().hitbox();
        GL11.glLineWidth(hitbox.enabled().get().booleanValue() ? hitbox.lineSize().get().floatValue() : 1.0f);
    }

    @WrapOperation(method = {"renderDebugBoundingBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawBoundingBox(DDDDDDFFFF)V")})
    private static void labyMod$renderHitbox(double lvt_0_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, double lvt_8_1_, double lvt_10_1_, float red, float green, float blue, float alpha, Operation<Void> original) {
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
        original.call(new Object[]{Double.valueOf(lvt_0_1_), Double.valueOf(lvt_2_1_), Double.valueOf(lvt_4_1_), Double.valueOf(lvt_6_1_), Double.valueOf(lvt_8_1_), Double.valueOf(lvt_10_1_), Float.valueOf(red), Float.valueOf(green), Float.valueOf(blue), Float.valueOf(alpha)});
    }

    @Inject(method = {"renderDebugBoundingBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getLook(F)Lnet/minecraft/util/math/Vec3d;", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$renderHitbox(vg lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, float lvt_9_1_, CallbackInfo ci) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldHitbox().get().booleanValue()) {
            ci.cancel();
            bus.y();
            bus.f();
            bus.q();
            bus.l();
            bus.a(true);
        }
    }

    @WrapOperation(method = {"renderDebugBoundingBox"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;color(IIII)Lnet/minecraft/client/renderer/BufferBuilder;")})
    private static buk labyMod$renderHitboxViewVector(buk instance, int red, int green, int blue, int alpha, Operation<buk> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        if (hitbox.enabled().get().booleanValue()) {
            int argb = hitbox.eyeLineColor().get().get();
            red = ColorFormat.ARGB32.red(argb);
            green = ColorFormat.ARGB32.green(argb);
            blue = ColorFormat.ARGB32.blue(argb);
        }
        return (buk) original.call(new Object[]{instance, Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue), Integer.valueOf(alpha)});
    }
}
