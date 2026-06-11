package net.labymod.v26_1.client.component.data;

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
import net.labymod.api.util.logging.Logging;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/component/data/VersionedDataComponentContainer.class */
public class VersionedDataComponentContainer implements DataComponentContainer {
    private static final Logging LOGGER = Logging.getLogger();
    private final Supplier<DataComponentMap> dataComponents;

    public VersionedDataComponentContainer(DataComponentMap dataComponents) {
        this((Supplier<DataComponentMap>) () -> {
            return dataComponents;
        });
    }

    public VersionedDataComponentContainer(Supplier<DataComponentMap> dataComponents) {
        this.dataComponents = dataComponents;
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public Set<DataComponentKey> keySet() {
        Set<DataComponentKey> keys = new HashSet<>();
        DataComponentMap wrapped = getWrapped();
        for (DataComponentType<?> dataComponentType : wrapped.keySet()) {
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
        DataComponentType<?> dataComponentTypeFindType;
        TypedDataComponent typed;
        Identifier identifierTryParse = Identifier.tryParse(dataComponentKey.name());
        if (identifierTryParse == null || (dataComponentTypeFindType = findType(identifierTryParse)) == null || (typed = getWrapped().getTyped(dataComponentTypeFindType)) == null) {
            return null;
        }
        return (T) typed.encodeValue(NbtOps.INSTANCE).result().orElse(null);
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
        DataComponentType<?> dataComponentTypeFindType;
        PatchedDataComponentMap wrapped = getWrapped();
        if (!(wrapped instanceof PatchedDataComponentMap)) {
            LOGGER.warn("Key {} is not a patched data component map", key);
            return;
        }
        PatchedDataComponentMap patchedDataComponentMap = wrapped;
        Identifier resourceLocation = Identifier.tryParse(key.name());
        if (resourceLocation == null || (dataComponentTypeFindType = findType(resourceLocation)) == null) {
            return;
        }
        DataResult parsedResult = dataComponentTypeFindType.codecOrThrow().decode(NbtOps.INSTANCE, value);
        Optional result = parsedResult.result();
        if (result.isEmpty()) {
            return;
        }
        Object object = result.get();
        if (!(object instanceof Pair)) {
            return;
        }
        Pair pair = (Pair) object;
        patchedDataComponentMap.set(dataComponentTypeFindType, pair.getFirst());
    }

    @Override // net.labymod.api.component.data.DataComponentContainer
    public void apply(DataComponentPatch patch) {
        for (net.labymod.api.component.data.TypedDataComponent<?> component : patch.components()) {
            set(component.key(), component.value());
        }
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<net.labymod.api.component.data.TypedDataComponent<?>> iterator() {
        List<net.labymod.api.component.data.TypedDataComponent<?>> list = new ArrayList<>();
        DataComponentMap<TypedDataComponent<?>> wrapped = getWrapped();
        for (TypedDataComponent<?> dataComponent : wrapped) {
            ResourceLocation resourceLocationFindId = findId(dataComponent.type());
            if (resourceLocationFindId == null) {
                LOGGER.debug("Key {} is null", dataComponent);
            } else {
                list.add(new net.labymod.api.component.data.TypedDataComponent<>(DataComponentKey.fromId(resourceLocationFindId), dataComponent.value()));
            }
        }
        return list.iterator();
    }

    public DataComponentMap getWrapped() {
        return this.dataComponents.get();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VersionedDataComponentContainer)) {
            return false;
        }
        VersionedDataComponentContainer versionedDataComponentContainer = (VersionedDataComponentContainer) obj;
        DataComponentMap current = getWrapped();
        DataComponentMap other = versionedDataComponentContainer.getWrapped();
        return Objects.equals(current, other);
    }

    public int hashCode() {
        return getWrapped().hashCode();
    }

    @Nullable
    private Identifier findId(DataComponentType<?> type) {
        return BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type);
    }

    @Nullable
    private DataComponentType<?> findType(Identifier location) {
        return (DataComponentType) BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(location);
    }
}
