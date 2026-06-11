package net.labymod.v1_12_2.mixins.util;

import java.util.List;
import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/util/MixinChatComponentStyle.class */
@Mixin({he.class})
public abstract class MixinChatComponentStyle {

    @Shadow
    protected List<hh> a;

    @Shadow
    public abstract hn b();

    @Overwrite
    public int hashCode() {
        return Objects.hash(b(), this.a);
    }
}
