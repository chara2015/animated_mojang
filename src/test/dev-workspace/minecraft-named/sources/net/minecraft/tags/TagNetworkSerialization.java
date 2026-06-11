package net.minecraft.tags;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.Holder;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RegistryLayer;
import net.minecraft.tags.TagLoader;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/tags/TagNetworkSerialization.class */
public class TagNetworkSerialization {
    public static Map<ResourceKey<? extends Registry<?>>, NetworkPayload> serializeTagsToNetwork(LayeredRegistryAccess<RegistryLayer> $$0) {
        return (Map) RegistrySynchronization.networkSafeRegistries($$0).map($$02 -> {
            return Pair.of($$02.key(), serializeToNetwork($$02.value()));
        }).filter($$03 -> {
            return !((NetworkPayload) $$03.getSecond()).isEmpty();
        }).collect(Collectors.toMap((v0) -> {
            return v0.getFirst();
        }, (v0) -> {
            return v0.getSecond();
        }));
    }

    private static <T> NetworkPayload serializeToNetwork(Registry<T> $$0) {
        Map<Identifier, IntList> $$1 = new HashMap<>();
        $$0.getTags().forEach($$2 -> {
            IntArrayList intArrayList = new IntArrayList($$2.size());
            Iterator<Holder<T>> it = $$2.iterator();
            while (it.hasNext()) {
                Holder holder = (Holder) it.next();
                if (holder.kind() != Holder.Kind.REFERENCE) {
                    throw new IllegalStateException("Can't serialize unregistered value " + String.valueOf(holder));
                }
                intArrayList.add($$0.getId(holder.value()));
            }
            $$1.put($$2.key().location(), intArrayList);
        });
        return new NetworkPayload($$1);
    }

    static <T> TagLoader.LoadResult<T> deserializeTagsFromNetwork(Registry<T> $$0, NetworkPayload $$1) {
        ResourceKey<? extends Registry<T>> $$2 = $$0.key();
        Map<TagKey<T>, List<Holder<T>>> $$3 = new HashMap<>();
        $$1.tags.forEach(($$32, $$4) -> {
            TagKey tagKeyCreate = TagKey.create($$2, $$32);
            IntStream intStream = $$4.intStream();
            Objects.requireNonNull($$0);
            $$3.put(tagKeyCreate, (List) intStream.mapToObj($$0::get).flatMap((v0) -> {
                return v0.stream();
            }).collect(Collectors.toUnmodifiableList()));
        });
        return new TagLoader.LoadResult<>($$2, $$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/tags/TagNetworkSerialization$NetworkPayload.class */
    public static final class NetworkPayload {
        public static final NetworkPayload EMPTY = new NetworkPayload(Map.of());
        final Map<Identifier, IntList> tags;

        NetworkPayload(Map<Identifier, IntList> $$0) {
            this.tags = $$0;
        }

        public void write(FriendlyByteBuf $$0) {
            $$0.writeMap(this.tags, (v0, v1) -> {
                v0.writeIdentifier(v1);
            }, (v0, v1) -> {
                v0.writeIntIdList(v1);
            });
        }

        public static NetworkPayload read(FriendlyByteBuf $$0) {
            return new NetworkPayload($$0.readMap((v0) -> {
                return v0.readIdentifier();
            }, (v0) -> {
                return v0.readIntIdList();
            }));
        }

        public boolean isEmpty() {
            return this.tags.isEmpty();
        }

        public int size() {
            return this.tags.size();
        }

        public <T> TagLoader.LoadResult<T> resolve(Registry<T> $$0) {
            return TagNetworkSerialization.deserializeTagsFromNetwork($$0, this);
        }
    }
}
