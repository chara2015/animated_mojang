package net.labymod.api.component.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/component/data/NbtDataComponentContainer.class */
public class NbtDataComponentContainer implements DataComponentContainer {
    private final Supplier<NBTTagCompound> compound;

    public NbtDataComponentContainer(NBTTagCompound compound) {
        this((Supplier<NBTTagCompound>) () -> {
            return compound;
        });
    }

    public NbtDataComponentContainer(Supplier<NBTTagCompound> compound) {
        this.compound = compound;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public Set<DataComponentKey> keySet() {
        Set<DataComponentKey> componentTypes = new HashSet<>();
        NBTTagCompound wrapped = getWrapped();
        for (String name : wrapped.keySet()) {
            DataComponentKey objectDataComponentKey = DataComponentKey.simple(name);
            componentTypes.add(objectDataComponentKey);
        }
        return componentTypes;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public boolean has(DataComponentKey key) {
        return get(key) != null;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public <T> T get(DataComponentKey dataComponentKey) {
        return (T) getWrapped().get(dataComponentKey.name());
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public <T> T getOrDefault(DataComponentKey dataComponentKey, T t) {
        T t2 = (T) get(dataComponentKey);
        return t2 == null ? t : t2;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public <T> void set(DataComponentKey key, T value) {
        if (!(value instanceof NBTTag)) {
            throw new IllegalArgumentException("Value is not a NBTTag");
        }
        NBTTag<?> nbtTag = (NBTTag) value;
        getWrapped().set(key.name(), nbtTag);
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public void apply(DataComponentPatch patch) {
        NBTTagCompound wrapped = getWrapped();
        wrapped.clear();
        for (TypedDataComponent<?> component : patch.components()) {
            DataComponentKey key = component.key();
            Object value = component.value();
            if (value != null) {
                set(key, value);
            }
        }
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<TypedDataComponent<?>> iterator() {
        List<TypedDataComponent<?>> components = new ArrayList<>();
        NBTTagCompound compound = getWrapped();
        for (String name : compound.keySet()) {
            NBTTag<?> nbtTag = compound.get(name);
            if (nbtTag != null) {
                components.add(new TypedDataComponent<>(DataComponentKey.simple(name), nbtTag));
            }
        }
        return components.iterator();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NbtDataComponentContainer)) {
            return false;
        }
        NbtDataComponentContainer nbtDataComponentContainer = (NbtDataComponentContainer) obj;
        NBTTagCompound current = getWrapped();
        NBTTagCompound other = nbtDataComponentContainer.getWrapped();
        return Objects.equals(current, other);
    }

    public String toString() {
        NBTTagCompound wrapped = getWrapped();
        if (wrapped == null) {
            return null;
        }
        return wrapped.toString();
    }

    public int hashCode() {
        return getWrapped().hashCode();
    }

    public NBTTagCompound getWrapped() {
        return this.compound.get();
    }
}
