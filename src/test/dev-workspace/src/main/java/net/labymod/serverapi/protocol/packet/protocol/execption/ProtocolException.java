package net.labymod.serverapi.protocol.packet.protocol.execption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/execption/ProtocolException.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolException extends RuntimeException {
    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}
