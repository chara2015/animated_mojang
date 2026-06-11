package net.minecraft.client.multiplayer.resolver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.mojang.blocklist.BlockListSupplier;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Predicate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/resolver/AddressCheck.class */
public interface AddressCheck {
    boolean isAllowed(ResolvedServerAddress resolvedServerAddress);

    boolean isAllowed(ServerAddress serverAddress);

    static AddressCheck createFromService() {
        final ImmutableList<Predicate<String>> $$0 = (ImmutableList) Streams.stream(ServiceLoader.load(BlockListSupplier.class)).map((v0) -> {
            return v0.createBlockList();
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).collect(ImmutableList.toImmutableList());
        return new AddressCheck() { // from class: net.minecraft.client.multiplayer.resolver.AddressCheck.1
            @Override // net.minecraft.client.multiplayer.resolver.AddressCheck
            public boolean isAllowed(ResolvedServerAddress $$02) {
                String $$1 = $$02.getHostName();
                String $$2 = $$02.getHostIp();
                return $$0.stream().noneMatch($$22 -> {
                    return $$22.test($$1) || $$22.test($$2);
                });
            }

            @Override // net.minecraft.client.multiplayer.resolver.AddressCheck
            public boolean isAllowed(ServerAddress $$02) {
                String $$1 = $$02.getHost();
                return $$0.stream().noneMatch($$12 -> {
                    return $$12.test($$1);
                });
            }
        };
    }
}
