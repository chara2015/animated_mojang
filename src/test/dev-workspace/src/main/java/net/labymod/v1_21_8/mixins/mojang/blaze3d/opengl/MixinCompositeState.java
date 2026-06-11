package net.labymod.v1_21_8.mixins.mojang.blaze3d.opengl;

import com.google.common.collect.ImmutableList;
import net.labymod.v1_21_8.client.renderer.CompositeStateAppender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/mojang/blaze3d/opengl/MixinCompositeState.class */
@Mixin({b.class})
public class MixinCompositeState implements CompositeStateAppender {

    @Mutable
    @Shadow
    @Final
    private ImmutableList<gxy> d;

    @Override // net.labymod.v1_21_8.client.renderer.CompositeStateAppender
    public void append(gxy shard) {
        this.d = ImmutableList.builder().add((gxy[]) this.d.toArray(new gxy[0])).add(shard).build();
    }
}
