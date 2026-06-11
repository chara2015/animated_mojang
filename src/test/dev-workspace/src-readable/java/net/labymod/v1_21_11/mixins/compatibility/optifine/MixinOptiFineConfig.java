package net.labymod.v1_21_11.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineConfig.class */
@Pseudo
@DynamicMixin("optifine")
@Mixin(targets = {"net.optifine.Config"}, remap = false)
public class MixinOptiFineConfig {
    @WrapOperation(method = {"initGameSettings"}, at = {@At(value = "FIELD", target = "Lnet/optifine/Config;antialiasingLevel:I")})
    @Dynamic
    private static void labyMod$disableAntiAliasing(int value, Operation<Void> original) {
    }
}
