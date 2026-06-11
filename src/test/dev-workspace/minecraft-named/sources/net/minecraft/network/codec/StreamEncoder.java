package net.minecraft.network.codec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/StreamEncoder.class */
@FunctionalInterface
public interface StreamEncoder<O, T> {
    void encode(O o, T t);
}
