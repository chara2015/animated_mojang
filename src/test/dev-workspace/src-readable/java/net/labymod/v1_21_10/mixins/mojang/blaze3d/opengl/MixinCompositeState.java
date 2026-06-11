package net.labymod.v1_21_10.mixins.mojang.blaze3d.opengl;

import com.google.common.collect.ImmutableList;
import net.labymod.v1_21_10.client.renderer.CompositeStateAppender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/mojang/blaze3d/opengl/MixinCompositeState.class */
@Mixin({b.class})
public class MixinCompositeState implements CompositeStateAppender {

    @Mutable
    @Shadow
    @Final
    private ImmutableList<hgj> d;

    @Override // net.labymod.v1_21_10.client.renderer.CompositeStateAppender
    public void append(hgj shard) {
        this.d = ImmutableList.builder().add((hgj[]) this.d.toArray(new hgj[0])).add(shard).build();
    }
}
