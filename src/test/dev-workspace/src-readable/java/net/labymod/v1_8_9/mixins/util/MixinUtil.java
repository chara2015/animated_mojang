package net.labymod.v1_8_9.mixins.util;

import java.util.concurrent.FutureTask;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinUtil.class */
@Mixin({g.class})
public class MixinUtil {
    @Insert(method = {"runTask"}, at = @At("HEAD"), cancellable = true)
    private static void labyMod$fixNullPointer(FutureTask task, Logger logger, InsertInfoReturnable<?> cir) {
        if (task == null) {
            cir.cancel();
        }
    }
}
