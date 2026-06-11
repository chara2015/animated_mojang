package net.labymod.v1_21_1.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.v1_21_1.laby3d.pipeline.LabyShaderInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/MixinShaderInstance_Laby3DApi.class */
@Mixin({gfn.class})
public class MixinShaderInstance_Laby3DApi {

    @Shadow
    @Final
    private String E;

    @WrapOperation(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private akr labyMod$rewriteId(String id, Operation<akr> original) {
        if (this instanceof LabyShaderInstance) {
            return (akr) LabyShaderInstance.rewriteId(id, this.E).getMinecraftLocation();
        }
        return (akr) original.call(new Object[]{id});
    }

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private static akr labyMod$rewriteProgramId(String id, Operation<akr> original, @Local(argsOnly = true) String name) {
        if (name.contains(String.valueOf(':'))) {
            return (akr) LabyShaderInstance.rewriteId(id, name).getMinecraftLocation();
        }
        return (akr) original.call(new Object[]{id});
    }

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/FileUtil;getFullResourcePath(Ljava/lang/String;)Ljava/lang/String;")})
    private static String labyMod$rewritePathId(String id, Operation<String> original, @Local(argsOnly = true) String name) {
        if (name.contains(String.valueOf(':'))) {
            return (String) original.call(new Object[]{LabyShaderInstance.rewriteId(id, name).toString()});
        }
        return (String) original.call(new Object[]{id});
    }
}
