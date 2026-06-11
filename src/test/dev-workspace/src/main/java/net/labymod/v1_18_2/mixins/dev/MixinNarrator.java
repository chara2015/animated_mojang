package net.labymod.v1_18_2.mixins.dev;

import com.mojang.text2speech.Narrator;
import com.mojang.text2speech.NarratorDummy;
import net.labymod.api.Laby;
import net.labymod.api.models.OperatingSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/dev/MixinNarrator.class */
@Mixin({Narrator.class})
public interface MixinNarrator {
    @Inject(method = {"getNarrator()Lcom/mojang/text2speech/Narrator;"}, at = {@At("HEAD")}, remap = false, cancellable = true)
    private static void getNarrator(CallbackInfoReturnable<Narrator> cir) {
        if (Laby.labyAPI().labyModLoader().isDevelopmentEnvironment() && OperatingSystem.getPlatform() == OperatingSystem.LINUX) {
            cir.setReturnValue(new NarratorDummy());
        }
    }
}
