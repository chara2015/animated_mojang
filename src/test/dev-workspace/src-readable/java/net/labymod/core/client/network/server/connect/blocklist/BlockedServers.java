package net.labymod.core.client.network.server.connect.blocklist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.labymod.api.util.HashUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/connect/blocklist/BlockedServers.class */
public class BlockedServers implements Predicate<String> {
    private final Set<String> blockedServers;
    private static final String SRV_PREFIX = "_minecraft._tcp.";
    public static final Charset HASH_CHARSET = StandardCharsets.ISO_8859_1;
    public static final BlockedServers INSTANCE = create();
    private static final Function<Collection<String>, String> DOT_JOINER = s -> {
        return String.join(".", s);
    };

    public BlockedServers(Collection<String> blockedServers) {
        this.blockedServers = Set.copyOf(blockedServers);
    }

    /* JADX WARN: Incorrect condition in loop: B:38:0x00c5 */
    @Override // java.util.function.Predicate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean test(@org.jetbrains.annotations.Nullable java.lang.String r6) {
        /*
            Method dump skipped, instruction units count: 283
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.client.network.server.connect.blocklist.BlockedServers.test(java.lang.String):boolean");
    }

    private static boolean isIp(List<String> address) {
        if (address.size() != 4) {
            return false;
        }
        for (String segment : address) {
            try {
                int part = Integer.parseInt(segment);
                if (part < 0 || part > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlockedServerHostName(String server) {
        return this.blockedServers.contains(HashUtil.sha1Hex(server.toLowerCase(Locale.ROOT).getBytes(HASH_CHARSET)));
    }

    private static BlockedServers create() {
        try {
            URLConnection connection = new URL("https://sessionserver.mojang.com/blockedservers").openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, HASH_CHARSET));
            BlockedServers blockedServers = new BlockedServers((Collection) reader.lines().collect(Collectors.toList()));
            inputStream.close();
            return blockedServers;
        } catch (Throwable e) {
            e.printStackTrace();
            return new BlockedServers(Collections.emptyList());
        }
    }
}
