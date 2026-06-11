package net.labymod.core.client.network.server;

import com.google.gson.JsonObject;
import java.io.IOException;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.PingProtectionService;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.util.io.web.UrlBuilder;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.client.network.server.ping.ServerPing;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/LabyNetPingProtectionService.class */
public class LabyNetPingProtectionService implements PingProtectionService {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.network.server.PingProtectionService
    public ServerInfo ping(String name, ServerAddress address, int timeout) throws IOException {
        String url = new UrlBuilder(Constants.Urls.LABYNET_SERVER_PING).addParameter("host", address.getHost()).addParameter("port", Integer.valueOf(address.getPort())).build();
        Result resultExecuteSync = ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(JsonObject.class).url(url, new Object[0])).connectTimeout(timeout)).readTimeout(timeout)).executeSync();
        if (resultExecuteSync.hasException()) {
            throw new IOException("Endpoint ping failed for " + String.valueOf(address), resultExecuteSync.exception());
        }
        JsonObject json = (JsonObject) resultExecuteSync.get();
        if (json == null) {
            throw new IOException("Endpoint returned empty response for " + String.valueOf(address));
        }
        int clientVersion = Laby.labyAPI().minecraft().getProtocolVersion();
        int ping = json.has("latency") ? json.get("latency").getAsInt() : -1;
        return ServerPing.parseServerStatus(name, address, clientVersion, json, ping);
    }

    @Override // net.labymod.api.client.network.server.PingProtectionService
    public boolean supports(ServerAddress address) {
        return !isLocalAddress(address.getHost());
    }
}
