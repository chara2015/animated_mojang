package net.labymod.v1_20_4.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.v1_20_4.laby3d.pipeline.LabyShaderInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/MixinShaderInstance_Laby3DApi.class */
@Mixin({ftv.class})
public class MixinShaderInstance_Laby3DApi {

    @Shadow
    @Final
    private String F;

    @WrapOperation(method = {"<init>"}, at = {@At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private ahg labyMod$rewriteId(String id, Operation<ahg> original) {
        if (this instanceof LabyShaderInstance) {
            return (ahg) LabyShaderInstance.rewriteId(id, this.F).getMinecraftLocation();
        }
        return (ahg) original.call(new Object[]{id});
    }

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private static ahg labyMod$rewriteProgramId(String id, Operation<ahg> original, @Local(argsOnly = true) String name) {
        if (name.contains(String.valueOf(':'))) {
            return (ahg) LabyShaderInstance.rewriteId(id, name).getMinecraftLocation();
        }
        return (ahg) original.call(new Object[]{id});
    }

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/FileUtil;getFullResourcePath(Ljava/lang/String;)Ljava/lang/String;")})
    private static String labyMod$rewritePathId(String id, Operation<String> original, @Local(argsOnly = true) String name) {
        if (name.contains(String.valueOf(':'))) {
            return (String) original.call(new Object[]{LabyShaderInstance.rewriteId(id, name).toString()});
        }
        return (String) original.call(new Object[]{id});
    }
}
