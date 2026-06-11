package net.minecraft.server.rcon;

import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/rcon/PktUtils.class */
public class PktUtils {
    public static final int MAX_PACKET_SIZE = 1460;
    public static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String stringFromByteArray(byte[] $$0, int $$1, int $$2) {
        int $$3 = $$2 - 1;
        int $$4 = $$1 > $$3 ? $$3 : $$1;
        while (0 != $$0[$$4] && $$4 < $$3) {
            $$4++;
        }
        return new String($$0, $$1, $$4 - $$1, StandardCharsets.UTF_8);
    }

    public static int intFromByteArray(byte[] $$0, int $$1) {
        return intFromByteArray($$0, $$1, $$0.length);
    }

    public static int intFromByteArray(byte[] $$0, int $$1, int $$2) {
        if (0 > ($$2 - $$1) - 4) {
            return 0;
        }
        return ($$0[$$1 + 3] << 24) | (($$0[$$1 + 2] & 255) << 16) | (($$0[$$1 + 1] & 255) << 8) | ($$0[$$1] & 255);
    }

    public static int intFromNetworkByteArray(byte[] $$0, int $$1, int $$2) {
        if (0 > ($$2 - $$1) - 4) {
            return 0;
        }
        return ($$0[$$1] << 24) | (($$0[$$1 + 1] & 255) << 16) | (($$0[$$1 + 2] & 255) << 8) | ($$0[$$1 + 3] & 255);
    }

    public static String toHexString(byte $$0) {
        return HEX_CHAR[($$0 & 240) >>> 4] + HEX_CHAR[$$0 & 15];
    }
}
