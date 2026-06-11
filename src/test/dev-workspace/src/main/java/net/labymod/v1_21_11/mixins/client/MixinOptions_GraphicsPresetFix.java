package net.labymod.v1_21_11.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinOptions_GraphicsPresetFix.class */
@Mixin({gfo.class})
public class MixinOptions_GraphicsPresetFix {
    @WrapOperation(method = {"setGraphicsPresetToCustom"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;")})
    private gsb labyMod$setGraphicsPresetToCustom(gfj instance, Operation<gsb> original) {
        LabyScreenAccessor labyScreenAccessor = (gsb) original.call(new Object[]{instance});
        if (!(labyScreenAccessor instanceof LabyScreenAccessor)) {
            return labyScreenAccessor;
        }
        LabyScreenAccessor accessor = labyScreenAccessor;
        LabyScreen labyScreen = accessor.screen();
        Object mostInnerScreen = labyScreen.mostInnerScreen();
        if (!(mostInnerScreen instanceof gsb)) {
            return labyScreenAccessor;
        }
        gsb innerScreen = (gsb) mostInnerScreen;
        return innerScreen;
    }
}
