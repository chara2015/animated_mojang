package net.labymod.v1_20_6.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.v1_20_6.laby3d.pipeline.LabyShaderInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinShaderInstance_Laby3DApi.class */
@Mixin({gee.class})
public class MixinShaderInstance_Laby3DApi {

    @Shadow
    @Final
    private String E;

    @WrapOperation(method = {"<init>"}, at = {@At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private alf labyMod$rewriteId(String id, Operation<alf> original) {
        if (this instanceof LabyShaderInstance) {
            return (alf) LabyShaderInstance.rewriteId(id, this.E).getMinecraftLocation();
        }
        return (alf) original.call(new Object[]{id});
    }

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")})
    private static alf labyMod$rewriteProgramId(String id, Operation<alf> original, @Local(argsOnly = true) String name) {
        if (name.contains(String.valueOf(':'))) {
            return (alf) LabyShaderInstance.rewriteId(id, name).getMinecraftLocation();
        }
        return (alf) original.call(new Object[]{id});
    }

    @WrapOperation(method = {"getOrCreate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/FileUtil;getFullResourcePath(Ljava/lang/String;)Ljava/lang/String;")})
    private static String labyMod$rewritePathId(String id, Operation<String> original, @Local(argsOnly = true) String name) {
        if (name.contains(String.valueOf(':'))) {
            return (String) original.call(new Object[]{LabyShaderInstance.rewriteId(id, name).toString()});
        }
        return (String) original.call(new Object[]{id});
    }
}
