package net.labymod.v1_16_5.mixins.client;

import net.labymod.api.models.OperatingSystem;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinMain.class */
@Mixin({Main.class})
public class MixinMain {
    @Redirect(method = {"<clinit>"}, at = @At(value = "INVOKE", target = "Ljava/lang/System;setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String labyMod$test(String key, String value) {
        if (OperatingSystem.isOSX()) {
            return System.setProperty(key, "false");
        }
        return System.setProperty(key, value);
    }
}
