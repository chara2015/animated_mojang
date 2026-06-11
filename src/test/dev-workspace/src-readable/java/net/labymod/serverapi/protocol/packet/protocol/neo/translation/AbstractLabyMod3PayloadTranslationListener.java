package net.labymod.serverapi.protocol.packet.protocol.neo.translation;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.labymod.serverapi.protocol.packet.protocol.Protocol;
import net.labymod.serverapi.protocol.payload.exception.PayloadException;
import net.labymod.serverapi.protocol.payload.exception.PayloadReaderException;
import net.labymod.serverapi.protocol.payload.io.PayloadReader;
import net.labymod.serverapi.protocol.payload.io.PayloadWriter;
import net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/neo/translation/AbstractLabyMod3PayloadTranslationListener.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class AbstractLabyMod3PayloadTranslationListener extends AbstractPayloadTranslationListener {
    private final String oldMessageKey;

    public abstract byte[] translateIncomingPayload(JsonElement jsonElement);

    public AbstractLabyMod3PayloadTranslationListener(Protocol protocol, String oldMessageKey) {
        super(protocol);
        this.oldMessageKey = oldMessageKey;
    }

    @Override // net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener
    public byte[] translateIncomingPayload(byte[] payload) {
        PayloadReader reader = new PayloadReader(payload);
        try {
            String messageKey = reader.readString();
            if (!messageKey.equals(this.oldMessageKey)) {
                return null;
            }
            try {
                String messageContent = reader.readString();
                return translateIncomingPayload(new JsonParser().parse(messageContent));
            } catch (PayloadReaderException exception) {
                throw new PayloadException("No message content could be read from the buffer.", exception);
            }
        } catch (PayloadReaderException exception2) {
            throw new PayloadException("No message key could be read from the buffer.", exception2);
        }
    }

    @Override // net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener
    public final byte[] modifyOutgoingPayload(byte[] payload) {
        PayloadWriter writer = new PayloadWriter();
        writer.writeString(this.oldMessageKey);
        writer.writeBytes(payload);
        return writer.toByteArray();
    }
}
