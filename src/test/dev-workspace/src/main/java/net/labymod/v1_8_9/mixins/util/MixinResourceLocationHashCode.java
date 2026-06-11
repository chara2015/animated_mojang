package net.labymod.v1_8_9.mixins.util;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinResourceLocationHashCode.class */
@Mixin({jy.class})
public class MixinResourceLocationHashCode {

    @Shadow
    @Final
    protected String a;

    @Shadow
    @Final
    protected String b;
    private int labyMod$hashCode;

    @Inject(method = {"<init>(I[Ljava/lang/String;)V"}, at = {@At("TAIL")})
    private void labyMod$createHashCode(int id, String[] lvt_2_1_, CallbackInfo ci) {
        this.labyMod$hashCode = (31 * this.a.hashCode()) + this.b.hashCode();
    }

    @Overwrite
    public int hashCode() {
        return this.labyMod$hashCode;
    }
}
