package net.labymod.v1_21_8.mixins;

import net.labymod.v1_21_8.client.StringSplitterAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/MixinStringSplitter.class */
@Mixin({fuo.class})
public class MixinStringSplitter implements StringSplitterAccessor {

    @Shadow
    @Final
    f a;

    @Override // net.labymod.v1_21_8.client.StringSplitterAccessor
    public f getWidthProvider() {
        return this.a;
    }
}
