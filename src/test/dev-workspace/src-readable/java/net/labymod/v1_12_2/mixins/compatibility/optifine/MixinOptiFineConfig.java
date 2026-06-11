package net.labymod.v1_12_2.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.net.URI;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/compatibility/optifine/MixinOptiFineConfig.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin(targets = {"Config"}, remap = false)
public class MixinOptiFineConfig {

    @Shadow
    @Mutable
    @Final
    private static DisplayMode desktopDisplayMode;

    @Overwrite
    @Dynamic
    public static boolean openWebLink(URI uri) {
        try {
            return OperatingSystem.getPlatform().launchUrlProcess(uri.toURL());
        } catch (Exception e) {
            return false;
        }
    }

    @WrapOperation(method = {"initDisplay"}, at = {@At(value = "FIELD", target = "LConfig;antialiasingLevel:I")})
    private static void labyMod$disableAntiAliasing(int value, Operation<Void> original) {
    }
}
