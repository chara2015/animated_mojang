package net.minecraft.server.packs.resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ResourceProvider.class */
@FunctionalInterface
public interface ResourceProvider {
    public static final ResourceProvider EMPTY = $$0 -> {
        return Optional.empty();
    };

    Optional<Resource> getResource(Identifier identifier);

    default Resource getResourceOrThrow(Identifier $$0) throws FileNotFoundException {
        return getResource($$0).orElseThrow(() -> {
            return new FileNotFoundException($$0.toString());
        });
    }

    default InputStream open(Identifier $$0) throws IOException {
        return getResourceOrThrow($$0).open();
    }

    default BufferedReader openAsReader(Identifier $$0) throws IOException {
        return getResourceOrThrow($$0).openAsReader();
    }

    static ResourceProvider fromMap(Map<Identifier, Resource> $$0) {
        return $$1 -> {
            return Optional.ofNullable((Resource) $$0.get($$1));
        };
    }
}
