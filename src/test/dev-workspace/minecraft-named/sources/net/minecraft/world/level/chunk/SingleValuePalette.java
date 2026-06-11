package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/SingleValuePalette.class */
public class SingleValuePalette<T> implements Palette<T> {
    private T value;

    public SingleValuePalette(List<T> list) {
        if (!list.isEmpty()) {
            Validate.isTrue(list.size() <= 1, "Can't initialize SingleValuePalette with %d values.", list.size());
            this.value = (T) list.getFirst();
        }
    }

    public static <A> Palette<A> create(int $$0, List<A> $$1) {
        return new SingleValuePalette($$1);
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public int idFor(T $$0, PaletteResize<T> $$1) {
        if (this.value == null || this.value == $$0) {
            this.value = $$0;
            return 0;
        }
        return $$1.onResize(1, $$0);
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public boolean maybeHas(Predicate<T> $$0) {
        if (this.value == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        return $$0.test(this.value);
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public T valueFor(int $$0) {
        if (this.value == null || $$0 != 0) {
            throw new IllegalStateException("Missing Palette entry for id " + $$0 + ".");
        }
        return this.value;
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public void read(FriendlyByteBuf $$0, IdMap<T> $$1) {
        this.value = $$1.byIdOrThrow($$0.readVarInt());
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public void write(FriendlyByteBuf $$0, IdMap<T> $$1) {
        if (this.value == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        $$0.writeVarInt($$1.getId(this.value));
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public int getSerializedSize(IdMap<T> $$0) {
        if (this.value == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        return VarInt.getByteSize($$0.getId(this.value));
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public int getSize() {
        return 1;
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public Palette<T> copy() {
        if (this.value == null) {
            throw new IllegalStateException("Use of an uninitialized palette");
        }
        return this;
    }
}
