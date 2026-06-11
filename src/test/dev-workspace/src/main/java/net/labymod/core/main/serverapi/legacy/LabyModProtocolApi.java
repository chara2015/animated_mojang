package net.labymod.core.main.serverapi.legacy;

import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.serverapi.LabyProtocolApi;
import net.labymod.core.main.serverapi.LabyModProtocolPlatformLogger;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import net.labymod.serverapi.protocol.api.ProtocolPlatformLogger;
import net.labymod.serverapi.protocol.packet.protocol.ProtocolService;
import net.labymod.serverapi.protocol.payload.PayloadBridge;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/legacy/LabyModProtocolApi.class */
@Singleton
@Implements(LabyProtocolApi.class)
@Deprecated(forRemoval = true, since = "4.2.24")
public class LabyModProtocolApi implements LabyProtocolApi {
    private final PayloadBridge payloadBridge = new LabyModPayloadBridge(this);
    private final ProtocolService protocolService = new ProtocolService();
    private final ProtocolPlatformLogger protocolPlatformLogger = new LabyModProtocolPlatformLogger();

    public LabyModProtocolApi() {
        ProtocolApiBridge.initialize(this);
        this.protocolService.initialize();
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolApi
    public PayloadBridge getPayloadBridge() {
        return this.payloadBridge;
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolApi
    public ProtocolService getProtocolService() {
        return this.protocolService;
    }

    @Override // net.labymod.serverapi.protocol.api.ProtocolApi
    public ProtocolPlatformLogger getPlatformLogger() {
        return this.protocolPlatformLogger;
    }
}
