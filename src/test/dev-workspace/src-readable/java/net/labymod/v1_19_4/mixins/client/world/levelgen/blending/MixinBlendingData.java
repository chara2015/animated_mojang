package net.labymod.v1_19_4.mixins.client.world.levelgen.blending;

import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/world/levelgen/blending/MixinBlendingData.class */
@Mixin({dic.class})
public class MixinBlendingData {
    @Inject(method = {"getOrUpdateBlendingData"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$getOrUpdateBlendingData(aiw region, int x, int z, CallbackInfoReturnable<dic> cir) {
        ddn chunk = region.a(x, z);
        dds status = MinecraftUtil.getHighestGeneratedStatus(chunk);
        if (chunk.t() != null && status.b(dds.f)) {
            return;
        }
        cir.setReturnValue((Object) null);
    }
}
