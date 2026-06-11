package net.labymod.v1_21_5.client.component.data;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/component/data/VersionedDataComponentContainer.class */
public class VersionedDataComponentContainer implements DataComponentContainer {
    private static final Logging LOGGER = Logging.getLogger();
    private final Supplier<ki> dataComponents;

    public VersionedDataComponentContainer(ki dataComponents) {
        this((Supplier<ki>) () -> {
            return dataComponents;
        });
    }

    public VersionedDataComponentContainer(Supplier<ki> dataComponents) {
        this.dataComponents = dataComponents;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public Set<DataComponentKey> keySet() {
        Set<DataComponentKey> keys = new HashSet<>();
        ki wrapped = getWrapped();
        for (kk<?> dataComponentType : wrapped.b()) {
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
        kk<?> kkVarFindType;
        kn knVarB;
        alr alrVarC = alr.c(dataComponentKey.name());
        if (alrVarC == null || (kkVarFindType = findType(alrVarC)) == null || (knVarB = getWrapped().b(kkVarFindType)) == null) {
            return null;
        }
        return (T) knVarB.a(uo.a).result().orElse(null);
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
        kk<?> kkVarFindType;
        km wrapped = getWrapped();
        if (!(wrapped instanceof km)) {
            LOGGER.warn("Key {} is not a patched data component map", key);
            return;
        }
        km patchedDataComponentMap = wrapped;
        alr resourceLocation = alr.c(key.name());
        if (resourceLocation == null || (kkVarFindType = findType(resourceLocation)) == null) {
            return;
        }
        DataResult parsedResult = kkVarFindType.c().decode(uo.a, value);
        Optional result = parsedResult.result();
        if (result.isEmpty()) {
            return;
        }
        Object object = result.get();
        if (!(object instanceof Pair)) {
            return;
        }
        Pair pair = (Pair) object;
        patchedDataComponentMap.b(kkVarFindType, pair.getFirst());
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
        ki<kn<?>> wrapped = getWrapped();
        for (kn<?> dataComponent : wrapped) {
            ResourceLocation resourceLocationFindId = findId(dataComponent.a());
            if (resourceLocationFindId == null) {
                LOGGER.debug("Key {} is null", dataComponent);
            } else {
                list.add(new TypedDataComponent<>(DataComponentKey.fromId(resourceLocationFindId), dataComponent.b()));
            }
        }
        return list.iterator();
    }

    public ki getWrapped() {
        return this.dataComponents.get();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VersionedDataComponentContainer)) {
            return false;
        }
        VersionedDataComponentContainer versionedDataComponentContainer = (VersionedDataComponentContainer) obj;
        ki current = getWrapped();
        ki other = versionedDataComponentContainer.getWrapped();
        return Objects.equals(current, other);
    }

    public int hashCode() {
        return getWrapped().hashCode();
    }

    @Nullable
    private alr findId(kk<?> type) {
        return mh.am.b(type);
    }

    @Nullable
    private kk<?> findType(alr location) {
        return (kk) mh.am.a(location);
    }
}
