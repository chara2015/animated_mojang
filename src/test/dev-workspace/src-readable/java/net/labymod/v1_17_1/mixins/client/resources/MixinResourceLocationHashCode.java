package net.labymod.v1_17_1.mixins.client.resources;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/resources/MixinResourceLocationHashCode.class */
@Mixin({ww.class})
public class MixinResourceLocationHashCode {

    @Shadow
    @Final
    protected String f;

    @Shadow
    @Final
    protected String e;
    private int labyMod$hashCode;

    @Inject(method = {"<init>([Ljava/lang/String;)V"}, at = {@At("TAIL")})
    private void labyMod$createHashCode(String[] $$0, CallbackInfo ci) {
        this.labyMod$hashCode = (31 * this.e.hashCode()) + this.f.hashCode();
    }

    @Overwrite
    public int hashCode() {
        return this.labyMod$hashCode;
    }
}
