package net.labymod.v1_21_11.client.component.data;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/component/data/VersionedDataComponentContainer.class */
public class VersionedDataComponentContainer implements DataComponentContainer {
    private static final Logging LOGGER = Logging.getLogger();
    private final Supplier<kf> dataComponents;

    public VersionedDataComponentContainer(kf dataComponents) {
        this((Supplier<kf>) () -> {
            return dataComponents;
        });
    }

    public VersionedDataComponentContainer(Supplier<kf> dataComponents) {
        this.dataComponents = dataComponents;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public Set<DataComponentKey> keySet() {
        Set<DataComponentKey> keys = new HashSet<>();
        kf wrapped = getWrapped();
        for (kh<?> dataComponentType : wrapped.b()) {
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
        kh<?> khVarFindType;
        kk kkVarB;
        amo amoVarC = amo.c(dataComponentKey.name());
        if (amoVarC == null || (khVarFindType = findType(amoVarC)) == null || (kkVarB = getWrapped().b(khVarFindType)) == null) {
            return null;
        }
        return (T) kkVarB.a(vn.a).result().orElse(null);
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
        kh<?> khVarFindType;
        kj wrapped = getWrapped();
        if (!(wrapped instanceof kj)) {
            LOGGER.warn("Key {} is not a patched data component map", key);
            return;
        }
        kj patchedDataComponentMap = wrapped;
        amo resourceLocation = amo.c(key.name());
        if (resourceLocation == null || (khVarFindType = findType(resourceLocation)) == null) {
            return;
        }
        DataResult parsedResult = khVarFindType.c().decode(vn.a, value);
        Optional result = parsedResult.result();
        if (result.isEmpty()) {
            return;
        }
        Object object = result.get();
        if (!(object instanceof Pair)) {
            return;
        }
        Pair pair = (Pair) object;
        patchedDataComponentMap.b(khVarFindType, pair.getFirst());
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
        kf<kk<?>> wrapped = getWrapped();
        for (kk<?> dataComponent : wrapped) {
            ResourceLocation resourceLocationFindId = findId(dataComponent.a());
            if (resourceLocationFindId == null) {
                LOGGER.debug("Key {} is null", dataComponent);
            } else {
                list.add(new TypedDataComponent<>(DataComponentKey.fromId(resourceLocationFindId), dataComponent.b()));
            }
        }
        return list.iterator();
    }

    public kf getWrapped() {
        return this.dataComponents.get();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VersionedDataComponentContainer)) {
            return false;
        }
        VersionedDataComponentContainer versionedDataComponentContainer = (VersionedDataComponentContainer) obj;
        kf current = getWrapped();
        kf other = versionedDataComponentContainer.getWrapped();
        return Objects.equals(current, other);
    }

    public int hashCode() {
        return getWrapped().hashCode();
    }

    @Nullable
    private amo findId(kh<?> type) {
        return mi.am.b(type);
    }

    @Nullable
    private kh<?> findType(amo location) {
        return (kh) mi.am.a(location);
    }
}
