package net.labymod.core.labyconnect.protocol.packets;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.util.CryptManager;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketEncryptionResponse.class */
public class PacketEncryptionResponse extends Packet {
    private byte[] sharedSecret;
    private byte[] verifyToken;
    private byte[] pin;

    public PacketEncryptionResponse(SecretKey key, PublicKey publicKey, byte[] hash) {
        this.pin = new byte[0];
        this.sharedSecret = CryptManager.encryptData(publicKey, key.getEncoded());
        this.verifyToken = CryptManager.encryptData(publicKey, hash);
    }

    public PacketEncryptionResponse(SecretKey key, PublicKey publicKey, byte[] hash, String pin) {
        this(key, publicKey, hash);
        this.pin = CryptManager.encryptData(publicKey, pin.getBytes(StandardCharsets.UTF_8));
    }

    public PacketEncryptionResponse() {
        this.pin = new byte[0];
    }

    public byte[] getSharedSecret() {
        return this.sharedSecret;
    }

    public byte[] getVerifyToken() {
        return this.verifyToken;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.sharedSecret = buf.readByteArray();
        this.verifyToken = buf.readByteArray();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeByteArray(new byte[]{42});
        buf.writeByteArray(this.sharedSecret);
        buf.writeByteArray(this.verifyToken);
        buf.writeByteArray(this.pin);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }
}
