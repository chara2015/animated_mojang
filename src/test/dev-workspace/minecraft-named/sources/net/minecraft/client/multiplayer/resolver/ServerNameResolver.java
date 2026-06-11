package net.minecraft.client.multiplayer.resolver;

import com.google.common.annotations.VisibleForTesting;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/resolver/ServerNameResolver.class */
public class ServerNameResolver {
    public static final ServerNameResolver DEFAULT = new ServerNameResolver(ServerAddressResolver.SYSTEM, ServerRedirectHandler.createDnsSrvRedirectHandler(), AddressCheck.createFromService());
    private final ServerAddressResolver resolver;
    private final ServerRedirectHandler redirectHandler;
    private final AddressCheck addressCheck;

    @VisibleForTesting
    ServerNameResolver(ServerAddressResolver $$0, ServerRedirectHandler $$1, AddressCheck $$2) {
        this.resolver = $$0;
        this.redirectHandler = $$1;
        this.addressCheck = $$2;
    }

    public Optional<ResolvedServerAddress> resolveAddress(ServerAddress $$0) {
        Optional<ResolvedServerAddress> $$1 = this.resolver.resolve($$0);
        if (($$1.isPresent() && !this.addressCheck.isAllowed($$1.get())) || !this.addressCheck.isAllowed($$0)) {
            return Optional.empty();
        }
        Optional<ServerAddress> $$2 = this.redirectHandler.lookupRedirect($$0);
        if ($$2.isPresent()) {
            Optional<ResolvedServerAddress> optionalResolve = this.resolver.resolve($$2.get());
            AddressCheck addressCheck = this.addressCheck;
            Objects.requireNonNull(addressCheck);
            $$1 = optionalResolve.filter(addressCheck::isAllowed);
        }
        return $$1;
    }
}
