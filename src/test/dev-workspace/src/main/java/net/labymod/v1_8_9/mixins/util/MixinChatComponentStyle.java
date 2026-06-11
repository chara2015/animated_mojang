package net.labymod.v1_8_9.mixins.util;

import java.util.List;
import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinChatComponentStyle.class */
@Mixin({es.class})
public abstract class MixinChatComponentStyle {

    @Shadow
    protected List<eu> a;

    @Shadow
    public abstract ez b();

    @Overwrite
    public int hashCode() {
        return Objects.hash(b(), this.a);
    }
}
