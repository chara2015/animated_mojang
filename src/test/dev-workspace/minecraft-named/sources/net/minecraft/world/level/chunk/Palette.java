package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/Palette.class */
public interface Palette<T> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/Palette$Factory.class */
    public interface Factory {
        <A> Palette<A> create(int i, List<A> list);
    }

    int idFor(T t, PaletteResize<T> paletteResize);

    boolean maybeHas(Predicate<T> predicate);

    T valueFor(int i);

    void read(FriendlyByteBuf friendlyByteBuf, IdMap<T> idMap);

    void write(FriendlyByteBuf friendlyByteBuf, IdMap<T> idMap);

    int getSerializedSize(IdMap<T> idMap);

    int getSize();

    Palette<T> copy();
}
