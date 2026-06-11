package com.mojang.realmsclient.client;

import java.net.Proxy;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsClientConfig.class */
public class RealmsClientConfig {
    private static Proxy proxy;

    public static Proxy getProxy() {
        return proxy;
    }

    public static void setProxy(Proxy $$0) {
        if (proxy == null) {
            proxy = $$0;
        }
    }
}
