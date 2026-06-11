package net.labymod.core.labyconnect.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/util/GZIPCompression.class */
public class GZIPCompression {
    public static final int DECOMPRESS_MAX_BYTES = 5242880;

    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes(StandardCharsets.UTF_8));
        gzip.flush();
        gzip.close();
        return obj.toByteArray();
    }

    public static String decompress(byte[] compressed) throws IOException {
        if (compressed == null || compressed.length == 0) {
            return "";
        }
        if (!isCompressed(compressed)) {
            return new String(compressed, StandardCharsets.UTF_8);
        }
        return new String(decompressBytes(compressed), StandardCharsets.UTF_8);
    }

    public static boolean isCompressed(byte[] compressed) {
        return compressed[0] == 31 && compressed[1] == -117;
    }

    public static byte[] compressBytes(byte[] input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(input);
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

    public static byte[] decompressBytes(byte[] input) throws IOException {
        if (input == null || input.length == 0) {
            return new byte[0];
        }
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int totalRead = 0;
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(input));
        while (true) {
            try {
                int len = gis.read(buffer);
                if (len > 0) {
                    totalRead += len;
                    if (totalRead > 5242880) {
                        throw new IOException("GZIP decompressed output exceeded allowed size (5242880 bytes)");
                    }
                    out.write(buffer, 0, len);
                } else {
                    gis.close();
                    return out.toByteArray();
                }
            } catch (Throwable th) {
                try {
                    gis.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public static String dc(long... num) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 8 * num.length; i++) {
            byte b = (byte) ((num[i / 8] >> ((i % 8) * 8)) & 255);
            if (b != 0) {
                baos.write(b);
            }
        }
        return baos.toString();
    }
}
