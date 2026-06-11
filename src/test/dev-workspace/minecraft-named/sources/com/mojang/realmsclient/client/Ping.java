package com.mojang.realmsclient.client;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.dto.RegionPingResult;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Comparator;
import java.util.List;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/Ping.class */
public class Ping {
    public static List<RegionPingResult> ping(Region... $$0) {
        for (Region $$1 : $$0) {
            ping($$1.endpoint);
        }
        List<RegionPingResult> $$2 = Lists.newArrayList();
        for (Region $$3 : $$0) {
            $$2.add(new RegionPingResult($$3.name, ping($$3.endpoint)));
        }
        $$2.sort(Comparator.comparingInt((v0) -> {
            return v0.ping();
        }));
        return $$2;
    }

    private static int ping(String $$0) {
        long $$2 = 0;
        Socket $$3 = null;
        for (int $$4 = 0; $$4 < 5; $$4++) {
            try {
                try {
                    SocketAddress $$5 = new InetSocketAddress($$0, 80);
                    $$3 = new Socket();
                    long $$6 = now();
                    $$3.connect($$5, 700);
                    $$2 += now() - $$6;
                    IOUtils.closeQuietly($$3);
                } catch (Exception e) {
                    $$2 += 700;
                    IOUtils.closeQuietly($$3);
                }
            } catch (Throwable th) {
                IOUtils.closeQuietly($$3);
                throw th;
            }
        }
        return (int) ($$2 / 5.0d);
    }

    private static long now() {
        return Util.getMillis();
    }

    public static List<RegionPingResult> pingAllRegions() {
        return ping(Region.values());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/Ping$Region.class */
    enum Region {
        US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
        US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
        US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
        EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
        AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
        AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
        AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
        SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com");

        final String name;
        final String endpoint;

        Region(String $$0, String $$1) {
            this.name = $$0;
            this.endpoint = $$1;
        }
    }
}
