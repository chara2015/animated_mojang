package net.labymod.v1_21_8.mixins.debug;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Set;
import net.labymod.util.property.SystemProperties;
import org.lwjgl.opengl.GLCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/debug/MixinGlDevice.class */
@Mixin({fme.class})
public class MixinGlDevice {
    @WrapOperation(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlDebugLabel;create(Lorg/lwjgl/opengl/GLCapabilities;ZLjava/util/Set;)Lcom/mojang/blaze3d/opengl/GlDebugLabel;")})
    private fmd labyMod$initializeDebugLabels(GLCapabilities capabilities, boolean useLabels, Set<String> enabledExtensions, Operation<fmd> original) {
        if (!useLabels) {
            useLabels = SystemProperties.RENDER_DEVICE_DEBUG_CONTEXT.get().booleanValue() || SystemProperties.RENDER_DEVICE_USER_DEBUG.get().booleanValue();
        }
        return (fmd) original.call(new Object[]{capabilities, Boolean.valueOf(useLabels), enabledExtensions});
    }
}
