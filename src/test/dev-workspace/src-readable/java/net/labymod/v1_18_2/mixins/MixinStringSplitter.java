package net.labymod.v1_18_2.mixins;

import net.labymod.v1_18_2.client.StringSplitterAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/MixinStringSplitter.class */
@Mixin({dze.class})
public class MixinStringSplitter implements StringSplitterAccessor {

    @Shadow
    @Final
    f a;

    @Override // net.labymod.v1_18_2.client.StringSplitterAccessor
    public f getWidthProvider() {
        return this.a;
    }
}
