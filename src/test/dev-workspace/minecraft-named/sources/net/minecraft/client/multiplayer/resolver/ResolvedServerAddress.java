package net.minecraft.client.multiplayer.resolver;

import java.net.InetSocketAddress;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/resolver/ResolvedServerAddress.class */
public interface ResolvedServerAddress {
    String getHostName();

    String getHostIp();

    int getPort();

    InetSocketAddress asInetSocketAddress();

    static ResolvedServerAddress from(final InetSocketAddress $$0) {
        return new ResolvedServerAddress() { // from class: net.minecraft.client.multiplayer.resolver.ResolvedServerAddress.1
            @Override // net.minecraft.client.multiplayer.resolver.ResolvedServerAddress
            public String getHostName() {
                return $$0.getAddress().getHostName();
            }

            @Override // net.minecraft.client.multiplayer.resolver.ResolvedServerAddress
            public String getHostIp() {
                return $$0.getAddress().getHostAddress();
            }

            @Override // net.minecraft.client.multiplayer.resolver.ResolvedServerAddress
            public int getPort() {
                return $$0.getPort();
            }

            @Override // net.minecraft.client.multiplayer.resolver.ResolvedServerAddress
            public InetSocketAddress asInetSocketAddress() {
                return $$0;
            }
        };
    }
}
