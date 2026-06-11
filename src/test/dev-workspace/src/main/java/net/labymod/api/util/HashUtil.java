package net.labymod.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/HashUtil.class */
public final class HashUtil {
    private static final String SHA_1 = "SHA-1";
    private static final String MD5 = "MD5";

    public static byte[] sha1(byte[] data) {
        return getSha1().digest(data);
    }

    public static byte[] sha1(InputStream data) throws IOException {
        return digest(data, getSha1());
    }

    public static String sha1Hex(byte[] data) {
        return new String(HexUtil.encodeHex(sha1(data)));
    }

    public static String sha1Hex(InputStream data) throws IOException {
        return new String(HexUtil.encodeHex(sha1(data)));
    }

    public static byte[] md5(byte[] data) {
        return getMd5().digest(data);
    }

    public static byte[] md5(InputStream data) throws IOException {
        return digest(data, getMd5());
    }

    public static String md5Hex(InputStream data) throws IOException {
        return new String(HexUtil.encodeHex(md5(data)));
    }

    public static String md5Hex(byte[] data) {
        return new String(HexUtil.encodeHex(md5(data)));
    }

    public static byte[] digest(InputStream inputStream, MessageDigest digest) throws IOException {
        byte[] buffer = new byte[256];
        while (true) {
            int len = inputStream.read(buffer);
            if (len != -1) {
                digest.update(buffer, 0, len);
            } else {
                return digest.digest();
            }
        }
    }

    public static MessageDigest getSha1() {
        return getDigest(SHA_1);
    }

    public static MessageDigest getMd5() {
        return getDigest(MD5);
    }

    public static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
