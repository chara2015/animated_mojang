package net.minecraft.network.protocol;

import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/CodecModifier.class */
@FunctionalInterface
public interface CodecModifier<B, V, C> {
    StreamCodec<? super B, V> apply(StreamCodec<? super B, V> streamCodec, C c);
}
