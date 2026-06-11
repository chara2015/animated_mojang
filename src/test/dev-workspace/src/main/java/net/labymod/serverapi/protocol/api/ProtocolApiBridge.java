package net.labymod.serverapi.protocol.api;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/api/ProtocolApiBridge.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolApiBridge {
    private static ProtocolApi protocolApi;

    public static void initialize(ProtocolApi protocolApi2) {
        protocolApi = protocolApi2;
    }

    public static ProtocolApi getProtocolApi() {
        return protocolApi;
    }
}
