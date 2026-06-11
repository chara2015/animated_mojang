package net.labymod.core.test.integration;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/AutoServerJoiner.class */
public final class AutoServerJoiner {
    private static final Logging LOGGER = Logging.create((Class<?>) AutoServerJoiner.class);
    private static final String TEST_SERVER_ADDRESS = "server.labystudio.de";
    private static final String TEST_SERVER_NAME = "Test Server";

    public void joinServer() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            LOGGER.error("Cannot join server: Minecraft instance not available", new Object[0]);
            return;
        }
        ServerController serverController = Laby.labyAPI().serverController();
        if (serverController == null) {
            LOGGER.error("Cannot join server: ServerController not available", new Object[0]);
            return;
        }
        if (serverController.isConnected()) {
            ServerData currentServer = serverController.getCurrentServerData();
            if (currentServer != null && isTestServer(currentServer.address().getHost())) {
                LOGGER.info("Already connected to test server: {}", TEST_SERVER_ADDRESS);
                return;
            } else {
                LOGGER.info("Disconnecting from current server before joining test server...", new Object[0]);
                serverController.leaveServer();
            }
        }
        LOGGER.info("Joining test server: {}", TEST_SERVER_ADDRESS);
        minecraft.executeNextTick(() -> {
            try {
                doJoinServer(serverController);
            } catch (Throwable e) {
                LOGGER.error("Failed to join test server", e);
            }
        });
    }

    private void doJoinServer(ServerController serverController) {
        LOGGER.info("Connecting to {} ({})", TEST_SERVER_NAME, TEST_SERVER_ADDRESS);
        serverController.joinServer(TEST_SERVER_ADDRESS);
    }

    private boolean isTestServer(String host) {
        if (host == null) {
            return false;
        }
        return host.equalsIgnoreCase(TEST_SERVER_ADDRESS) || host.equalsIgnoreCase("labystudio.de") || host.endsWith(".labystudio.de");
    }

    public String getServerAddress() {
        return TEST_SERVER_ADDRESS;
    }

    public String getServerName() {
        return TEST_SERVER_NAME;
    }
}
