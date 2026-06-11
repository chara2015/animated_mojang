package net.minecraft.server.jsonrpc.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/methods/RemoteRpcErrorException.class */
public class RemoteRpcErrorException extends RuntimeException {
    private final JsonElement id;
    private final JsonObject error;

    public RemoteRpcErrorException(JsonElement $$0, JsonObject $$1) {
        this.id = $$0;
        this.error = $$1;
    }

    private JsonObject getError() {
        return this.error;
    }

    private JsonElement getId() {
        return this.id;
    }
}
