package net.labymod.v1_21_11.mixins;

import net.labymod.v1_21_11.client.StringSplitterAccessor;
import net.minecraft.client.StringSplitter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/MixinStringSplitter.class */
@Mixin({StringSplitter.class})
public class MixinStringSplitter implements StringSplitterAccessor {

    @Shadow
    @Final
    StringSplitter.WidthProvider widthProvider;

    @Override // net.labymod.v1_21_11.client.StringSplitterAccessor
    public StringSplitter.WidthProvider getWidthProvider() {
        return this.widthProvider;
    }
}

