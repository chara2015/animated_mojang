package net.labymod.core.client.network.server.ping;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/ping/ServerPingUtil.class */
public class ServerPingUtil {
    public static void io(boolean value, String message) throws IOException {
        if (value) {
            throw new IOException(message);
        }
    }

    public static void writeLegacyString(DataOutputStream out, String value) throws IOException {
        out.writeShort(value.length());
        out.write(value.getBytes(StandardCharsets.UTF_16BE));
    }

    public static String readLegacyString(DataInputStream input) throws IOException {
        int length = input.readUnsignedShort() << 1;
        byte[] encodedBuffer = input.readNBytes(length);
        return new String(encodedBuffer, StandardCharsets.UTF_16BE);
    }

    public static int readVarInt(DataInputStream in) throws IOException {
        int k;
        int i = 0;
        int j = 0;
        do {
            k = in.readByte();
            int i2 = j;
            j++;
            i |= (k & 127) << (i2 * 7);
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((k & 128) == 128);
        return i;
    }

    public static void writeVarInt(OutputStream out, int value) throws IOException {
        while ((value & (-128)) != 0) {
            out.write((value & 127) | 128);
            value >>>= 7;
        }
        out.write(value);
    }
}
