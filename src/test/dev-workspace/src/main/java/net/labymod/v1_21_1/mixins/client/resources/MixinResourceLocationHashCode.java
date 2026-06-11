package net.labymod.v1_21_1.mixins.client.resources;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/resources/MixinResourceLocationHashCode.class */
@Mixin({akr.class})
public class MixinResourceLocationHashCode {

    @Shadow
    @Final
    private String i;

    @Shadow
    @Final
    private String h;
    private int labyMod$hashCode;
    private boolean labyMod$calculateHashCode = true;

    @Overwrite
    public int hashCode() {
        if (this.labyMod$calculateHashCode) {
            this.labyMod$hashCode = (31 * this.h.hashCode()) + this.i.hashCode();
            this.labyMod$calculateHashCode = false;
        }
        return this.labyMod$hashCode;
    }
}
