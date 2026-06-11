package net.labymod.core.util;

import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/HardwareUtils.class */
public class HardwareUtils {
    private static final String[] MAIN_NAMES = {"eth0", "en0", "wlan0"};

    public static String sha1(String string) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string.getBytes(StandardCharsets.UTF_8));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", Byte.valueOf(b));
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String encodeAddress(byte[] address) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < address.length) {
            Locale locale = Locale.ROOT;
            Object[] objArr = new Object[2];
            objArr[0] = Byte.valueOf(address[i]);
            objArr[1] = i < address.length - 1 ? "-" : "";
            builder.append(String.format(locale, "%02X%s", objArr));
            i++;
        }
        return builder.toString();
    }

    public static byte[] getMainNetworkInterfaceAddress() throws Exception {
        List<NetworkInterface> candidates = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface network = networkInterfaces.nextElement();
            if (!network.isLoopback() && !network.isVirtual() && network.isUp() && network.getHardwareAddress() != null) {
                candidates.add(network);
            }
        }
        candidates.sort((a, b) -> {
            String[] arr$ = MAIN_NAMES;
            for (String mainName : arr$) {
                if (a.getName().equals(mainName)) {
                    return -1;
                }
                if (b.getName().equals(mainName)) {
                    return 1;
                }
            }
            return 0;
        });
        if (candidates.isEmpty()) {
            return null;
        }
        return candidates.get(0).getHardwareAddress();
    }
}
