package net.labymod.v1_21.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.v1_21.laby3d.pipeline.LabyShaderInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/compatibility/optifine/MixinOptiFineShaderInstance_Laby3DApi.class */
@Mixin({gfn.class})
public class MixinOptiFineShaderInstance_Laby3DApi {

    @Shadow
    @Final
    private String E;

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private static akr labyMod$rewriteProgramId(String id, Operation<akr> original, @Local(argsOnly = true) String name) {
        if (id.contains(String.valueOf(':'))) {
            return (akr) LabyShaderInstance.rewriteId(id, name).getMinecraftLocation();
        }
        return (akr) original.call(new Object[]{id});
    }
}
