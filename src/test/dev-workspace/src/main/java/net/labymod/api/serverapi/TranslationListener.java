package net.labymod.api.serverapi;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/serverapi/TranslationListener.class */
public abstract class TranslationListener {
    public abstract Packet translateIncomingPayload(PayloadReader payloadReader);

    public abstract PayloadWriter translateOutgoingPayload(Packet packet);

    protected TranslationListener() {
    }
}
