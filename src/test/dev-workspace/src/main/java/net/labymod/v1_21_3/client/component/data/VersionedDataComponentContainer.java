package net.labymod.v1_21_3.client.component.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.component.data.DataComponentKey;
import net.labymod.api.component.data.DataComponentPatch;
import net.labymod.api.component.data.TypedDataComponent;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/component/data/VersionedDataComponentContainer.class */
public class VersionedDataComponentContainer implements DataComponentContainer {
    private static final Logging LOGGER = Logging.getLogger();
    private final Supplier<kq> dataComponents;

    public VersionedDataComponentContainer(kq dataComponents) {
        this((Supplier<kq>) () -> {
            return dataComponents;
        });
    }

    public VersionedDataComponentContainer(Supplier<kq> dataComponents) {
        this.dataComponents = dataComponents;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public Set<DataComponentKey> keySet() {
        Set<DataComponentKey> keys = new HashSet<>();
        kq wrapped = getWrapped();
        for (kt<?> dataComponentType : wrapped.b()) {
            ResourceLocation resourceLocationFindId = findId(dataComponentType);
            if (resourceLocationFindId == null) {
                LOGGER.debug("Key {} is null", dataComponentType);
            } else {
                keys.add(DataComponentKey.fromId(resourceLocationFindId));
            }
        }
        return keys;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public boolean has(DataComponentKey key) {
        return get(key) != null;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public <T> T get(DataComponentKey dataComponentKey) {
        kt<?> ktVarFindType;
        kw kwVarC;
        alz alzVarC = alz.c(dataComponentKey.name());
        if (alzVarC == null || (ktVarFindType = findType(alzVarC)) == null || (kwVarC = getWrapped().c(ktVarFindType)) == null) {
            return null;
        }
        return (T) kwVarC.a(vl.a).result().orElse(null);
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public <T> T getOrDefault(DataComponentKey key, T defaultValue) {
        if (get(key) == null) {
            return null;
        }
        return defaultValue;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public <T> void set(DataComponentKey key, T value) {
        kt<?> ktVarFindType;
        kv wrapped = getWrapped();
        if (!(wrapped instanceof kv)) {
            LOGGER.warn("Key {} is not a patched data component map", key);
            return;
        }
        kv patchedDataComponentMap = wrapped;
        alz resourceLocation = alz.c(key.name());
        if (resourceLocation == null || (ktVarFindType = findType(resourceLocation)) == null) {
            return;
        }
        DataResult parsedResult = ktVarFindType.c().decode(vl.a, value);
        Optional result = parsedResult.result();
        if (result.isEmpty()) {
            return;
        }
        Object object = result.get();
        if (!(object instanceof Pair)) {
            return;
        }
        Pair pair = (Pair) object;
        patchedDataComponentMap.b(ktVarFindType, pair.getFirst());
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public void apply(DataComponentPatch patch) {
        for (TypedDataComponent<?> component : patch.components()) {
            set(component.key(), component.value());
        }
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<TypedDataComponent<?>> iterator() {
        List<TypedDataComponent<?>> list = new ArrayList<>();
        kq<kw<?>> wrapped = getWrapped();
        for (kw<?> dataComponent : wrapped) {
            ResourceLocation resourceLocationFindId = findId(dataComponent.a());
            if (resourceLocationFindId == null) {
                LOGGER.debug("Key {} is null", dataComponent);
            } else {
                list.add(new TypedDataComponent<>(DataComponentKey.fromId(resourceLocationFindId), dataComponent.b()));
            }
        }
        return list.iterator();
    }

    public kq getWrapped() {
        return this.dataComponents.get();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VersionedDataComponentContainer)) {
            return false;
        }
        VersionedDataComponentContainer versionedDataComponentContainer = (VersionedDataComponentContainer) obj;
        kq current = getWrapped();
        kq other = versionedDataComponentContainer.getWrapped();
        return Objects.equals(current, other);
    }

    public int hashCode() {
        return getWrapped().hashCode();
    }

    @Nullable
    private alz findId(kt<?> type) {
        return ma.ao.b(type);
    }

    @Nullable
    private kt<?> findType(alz location) {
        return (kt) ma.ao.a(location);
    }
}
