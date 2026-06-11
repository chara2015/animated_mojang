package net.labymod.core.labyconnect.session.spray;

import java.nio.ByteBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.DefaultLabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/spray/SprayProtocol.class */
public interface SprayProtocol {
    void handle(DefaultLabyConnect defaultLabyConnect, short s, int i, UUID uuid, ByteBuffer byteBuffer);
}
