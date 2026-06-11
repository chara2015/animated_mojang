package net.labymod.v26_1.mixins;

import net.labymod.v26_1.client.StringSplitterAccessor;
import net.minecraft.client.StringSplitter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/MixinStringSplitter.class */
@Mixin({StringSplitter.class})
public class MixinStringSplitter implements StringSplitterAccessor {

    @Shadow
    @Final
    StringSplitter.WidthProvider widthProvider;

    @Override // net.labymod.v26_1.client.StringSplitterAccessor
    public StringSplitter.WidthProvider getWidthProvider() {
        return this.widthProvider;
    }
}
