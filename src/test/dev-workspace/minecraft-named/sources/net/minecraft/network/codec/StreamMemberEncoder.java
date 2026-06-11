package net.minecraft.network.codec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/StreamMemberEncoder.class */
@FunctionalInterface
public interface StreamMemberEncoder<O, T> {
    void encode(T t, O o);
}
