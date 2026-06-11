package net.labymod.v1_16_5.mixins.mojang.blaze3d.platform;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.nio.FloatBuffer;
import net.labymod.api.laby3d.GameLightingStorage;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/mojang/blaze3d/platform/MixinGlStateManagerLightTracker.class */
@Mixin({dem.class})
public class MixinGlStateManagerLightTracker {
    @WrapOperation(method = {"setupLevelDiffuseLighting"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_light(IILjava/nio/FloatBuffer;)V")})
    private static void labyMod$storeLightDirection(int light, int pname, FloatBuffer buffer, Operation<Void> original) {
        original.call(new Object[]{Integer.valueOf(light), Integer.valueOf(pname), buffer});
        if (16384 == light && 4611 == pname) {
            GameLightingStorage storage = GameLightingStorage.INSTANCE;
            GameLightingStorage.LightDirections lightDirections = storage.getLightDirections(GameLightingStorage.Entry.LEVEL);
            storage.setLightDirections(GameLightingStorage.Entry.LEVEL, new Vector3f(buffer.get(0), buffer.get(1), buffer.get(2)), new Vector3f(lightDirections.light1Direction()));
        }
        if (16385 == light && 4611 == pname) {
            GameLightingStorage storage2 = GameLightingStorage.INSTANCE;
            GameLightingStorage.LightDirections lightDirections2 = storage2.getLightDirections(GameLightingStorage.Entry.LEVEL);
            storage2.setLightDirections(GameLightingStorage.Entry.LEVEL, new Vector3f(lightDirections2.light0Direction()), new Vector3f(buffer.get(0), buffer.get(1), buffer.get(2)));
        }
    }
}
