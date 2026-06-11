package net.minecraft.server.packs.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.packs.PackResources;
import net.minecraft.util.Util;
import net.minecraft.world.flag.FeatureFlagSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/repository/PackRepository.class */
public class PackRepository {
    private final Set<RepositorySource> sources;
    private Map<String, Pack> available = ImmutableMap.of();
    private List<Pack> selected = ImmutableList.of();

    public PackRepository(RepositorySource... $$0) {
        this.sources = ImmutableSet.copyOf($$0);
    }

    public static String displayPackList(Collection<Pack> $$0) {
        return (String) $$0.stream().map($$02 -> {
            return $$02.getId() + ($$02.getCompatibility().isCompatible() ? "" : " (incompatible)");
        }).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT));
    }

    public void reload() {
        List<String> $$0 = (List) this.selected.stream().map((v0) -> {
            return v0.getId();
        }).collect(ImmutableList.toImmutableList());
        this.available = discoverAvailable();
        this.selected = rebuildSelected($$0);
    }

    private Map<String, Pack> discoverAvailable() {
        Map<String, Pack> $$0 = Maps.newTreeMap();
        for (RepositorySource $$1 : this.sources) {
            $$1.loadPacks($$12 -> {
                $$0.put($$12.getId(), $$12);
            });
        }
        return ImmutableMap.copyOf($$0);
    }

    public boolean isAbleToClearAnyPack() {
        List<Pack> $$0 = rebuildSelected(List.of());
        return !this.selected.equals($$0);
    }

    public void setSelected(Collection<String> $$0) {
        this.selected = rebuildSelected($$0);
    }

    public boolean addPack(String $$0) {
        Pack $$1 = this.available.get($$0);
        if ($$1 != null && !this.selected.contains($$1)) {
            List<Pack> $$2 = Lists.newArrayList(this.selected);
            $$2.add($$1);
            this.selected = $$2;
            return true;
        }
        return false;
    }

    public boolean removePack(String $$0) {
        Pack $$1 = this.available.get($$0);
        if ($$1 != null && this.selected.contains($$1)) {
            List<Pack> $$2 = Lists.newArrayList(this.selected);
            $$2.remove($$1);
            this.selected = $$2;
            return true;
        }
        return false;
    }

    private List<Pack> rebuildSelected(Collection<String> $$0) {
        List<Pack> $$1 = (List) getAvailablePacks($$0).collect(Util.toMutableList());
        for (Pack $$2 : this.available.values()) {
            if ($$2.isRequired() && !$$1.contains($$2)) {
                $$2.getDefaultPosition().insert($$1, $$2, (v0) -> {
                    return v0.selectionConfig();
                }, false);
            }
        }
        return ImmutableList.copyOf($$1);
    }

    private Stream<Pack> getAvailablePacks(Collection<String> $$0) {
        Stream<String> stream = $$0.stream();
        Map<String, Pack> map = this.available;
        Objects.requireNonNull(map);
        return stream.map((v1) -> {
            return r1.get(v1);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        });
    }

    public Collection<String> getAvailableIds() {
        return this.available.keySet();
    }

    public Collection<Pack> getAvailablePacks() {
        return this.available.values();
    }

    public Collection<String> getSelectedIds() {
        return (Collection) this.selected.stream().map((v0) -> {
            return v0.getId();
        }).collect(ImmutableSet.toImmutableSet());
    }

    public FeatureFlagSet getRequestedFeatureFlags() {
        return (FeatureFlagSet) getSelectedPacks().stream().map((v0) -> {
            return v0.getRequestedFeatures();
        }).reduce((v0, v1) -> {
            return v0.join(v1);
        }).orElse(FeatureFlagSet.of());
    }

    public Collection<Pack> getSelectedPacks() {
        return this.selected;
    }

    public Pack getPack(String $$0) {
        return this.available.get($$0);
    }

    public boolean isAvailable(String $$0) {
        return this.available.containsKey($$0);
    }

    public List<PackResources> openAllSelected() {
        return (List) this.selected.stream().map((v0) -> {
            return v0.open();
        }).collect(ImmutableList.toImmutableList());
    }
}
