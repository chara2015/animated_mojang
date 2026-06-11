package net.minecraft.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/ValueOutput.class */
public interface ValueOutput {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/ValueOutput$TypedOutputList.class */
    public interface TypedOutputList<T> {
        void add(T t);

        boolean isEmpty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/ValueOutput$ValueOutputList.class */
    public interface ValueOutputList {
        ValueOutput addChild();

        void discardLast();

        boolean isEmpty();
    }

    <T> void store(String str, Codec<T> codec, T t);

    <T> void storeNullable(String str, Codec<T> codec, T t);

    @Deprecated
    <T> void store(MapCodec<T> mapCodec, T t);

    void putBoolean(String str, boolean z);

    void putByte(String str, byte b);

    void putShort(String str, short s);

    void putInt(String str, int i);

    void putLong(String str, long j);

    void putFloat(String str, float f);

    void putDouble(String str, double d);

    void putString(String str, String str2);

    void putIntArray(String str, int[] iArr);

    ValueOutput child(String str);

    ValueOutputList childrenList(String str);

    <T> TypedOutputList<T> list(String str, Codec<T> codec);

    void discard(String str);

    boolean isEmpty();
}
