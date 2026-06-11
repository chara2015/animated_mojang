package net.labymod.v26_2_snapshot_8.mixins.client.resources;

import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/resources/MixinResourceLocationHashCode.class */
@Mixin({Identifier.class})
public class MixinResourceLocationHashCode {

    @Shadow
    @Final
    private String path;

    @Shadow
    @Final
    private String namespace;
    private int labyMod$hashCode;
    private boolean labyMod$calculateHashCode = true;

    @Overwrite
    public int hashCode() {
        if (this.labyMod$calculateHashCode) {
            this.labyMod$hashCode = (31 * this.namespace.hashCode()) + this.path.hashCode();
            this.labyMod$calculateHashCode = false;
        }
        return this.labyMod$hashCode;
    }
}
