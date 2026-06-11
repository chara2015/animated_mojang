package net.minecraft.network.codec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/StreamDecoder.class */
@FunctionalInterface
public interface StreamDecoder<I, T> {
    T decode(I i);
}
