package net.labymod.v1_20_6.mixins.client.resources;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/resources/MixinResourceLocationHashCode.class */
@Mixin({alf.class})
public class MixinResourceLocationHashCode {

    @Shadow
    @Final
    private String h;

    @Shadow
    @Final
    private String g;
    private int labyMod$hashCode;
    private boolean labyMod$calculateHashCode = true;

    @Overwrite
    public int hashCode() {
        if (this.labyMod$calculateHashCode) {
            this.labyMod$hashCode = (31 * this.g.hashCode()) + this.h.hashCode();
            this.labyMod$calculateHashCode = false;
        }
        return this.labyMod$hashCode;
    }
}
