package net.minecraft.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.ValueInput;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/ValueInputContextHelper.class */
public class ValueInputContextHelper {
    final HolderLookup.Provider lookup;
    private final DynamicOps<Tag> ops;
    final ValueInput.ValueInputList emptyChildList = new ValueInput.ValueInputList(this) { // from class: net.minecraft.world.level.storage.ValueInputContextHelper.1
        @Override // net.minecraft.world.level.storage.ValueInput.ValueInputList
        public boolean isEmpty() {
            return true;
        }

        @Override // net.minecraft.world.level.storage.ValueInput.ValueInputList
        public Stream<ValueInput> stream() {
            return Stream.empty();
        }

        @Override // java.lang.Iterable
        public Iterator<ValueInput> iterator() {
            return Collections.emptyIterator();
        }
    };
    private final ValueInput.TypedInputList<Object> emptyTypedList = new ValueInput.TypedInputList<Object>(this) { // from class: net.minecraft.world.level.storage.ValueInputContextHelper.2
        @Override // net.minecraft.world.level.storage.ValueInput.TypedInputList
        public boolean isEmpty() {
            return true;
        }

        @Override // net.minecraft.world.level.storage.ValueInput.TypedInputList
        public Stream<Object> stream() {
            return Stream.empty();
        }

        @Override // java.lang.Iterable
        public Iterator<Object> iterator() {
            return Collections.emptyIterator();
        }
    };
    private final ValueInput empty = new ValueInput() { // from class: net.minecraft.world.level.storage.ValueInputContextHelper.3
        @Override // net.minecraft.world.level.storage.ValueInput
        public <T> Optional<T> read(String $$0, Codec<T> $$1) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public <T> Optional<T> read(MapCodec<T> $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public Optional<ValueInput> child(String $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public ValueInput childOrEmpty(String $$0) {
            return this;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public Optional<ValueInput.ValueInputList> childrenList(String $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public ValueInput.ValueInputList childrenListOrEmpty(String $$0) {
            return ValueInputContextHelper.this.emptyChildList;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public <T> Optional<ValueInput.TypedInputList<T>> list(String $$0, Codec<T> $$1) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public <T> ValueInput.TypedInputList<T> listOrEmpty(String $$0, Codec<T> $$1) {
            return ValueInputContextHelper.this.emptyTypedList();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public boolean getBooleanOr(String $$0, boolean $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public byte getByteOr(String $$0, byte $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public int getShortOr(String $$0, short $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public Optional<Integer> getInt(String $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public int getIntOr(String $$0, int $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public long getLongOr(String $$0, long $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public Optional<Long> getLong(String $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public float getFloatOr(String $$0, float $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public double getDoubleOr(String $$0, double $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public Optional<String> getString(String $$0) {
            return Optional.empty();
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public String getStringOr(String $$0, String $$1) {
            return $$1;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public HolderLookup.Provider lookup() {
            return ValueInputContextHelper.this.lookup;
        }

        @Override // net.minecraft.world.level.storage.ValueInput
        public Optional<int[]> getIntArray(String $$0) {
            return Optional.empty();
        }
    };

    public ValueInputContextHelper(HolderLookup.Provider $$0, DynamicOps<Tag> $$1) {
        this.lookup = $$0;
        this.ops = $$0.createSerializationContext($$1);
    }

    public DynamicOps<Tag> ops() {
        return this.ops;
    }

    public HolderLookup.Provider lookup() {
        return this.lookup;
    }

    public ValueInput empty() {
        return this.empty;
    }

    public ValueInput.ValueInputList emptyList() {
        return this.emptyChildList;
    }

    public <T> ValueInput.TypedInputList<T> emptyTypedList() {
        return (ValueInput.TypedInputList<T>) this.emptyTypedList;
    }
}
