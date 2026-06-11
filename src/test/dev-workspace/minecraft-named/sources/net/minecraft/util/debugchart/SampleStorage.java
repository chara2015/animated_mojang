package net.minecraft.util.debugchart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debugchart/SampleStorage.class */
public interface SampleStorage {
    int capacity();

    int size();

    long get(int i);

    long get(int i, int i2);

    void reset();
}
