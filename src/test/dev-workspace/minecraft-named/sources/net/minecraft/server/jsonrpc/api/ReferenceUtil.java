package net.minecraft.server.jsonrpc.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/api/ReferenceUtil.class */
public class ReferenceUtil {
    public static final Codec<URI> REFERENCE_CODEC = Codec.STRING.comapFlatMap($$0 -> {
        try {
            return DataResult.success(new URI($$0));
        } catch (URISyntaxException $$1) {
            Objects.requireNonNull($$1);
            return DataResult.error($$1::getMessage);
        }
    }, (v0) -> {
        return v0.toString();
    });

    public static URI createLocalReference(String $$0) {
        return URI.create("#/components/schemas/" + $$0);
    }
}
