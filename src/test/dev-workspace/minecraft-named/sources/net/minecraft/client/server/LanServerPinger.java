package net.minecraft.client.server;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/server/LanServerPinger.class */
public class LanServerPinger extends Thread {
    private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MULTICAST_GROUP = "224.0.2.60";
    public static final int PING_PORT = 4445;
    private static final long PING_INTERVAL = 1500;
    private final String motd;
    private final DatagramSocket socket;
    private boolean isRunning;
    private final String serverAddress;

    public LanServerPinger(String $$0, String $$1) throws IOException {
        super("LanServerPinger #" + UNIQUE_THREAD_ID.incrementAndGet());
        this.isRunning = true;
        this.motd = $$0;
        this.serverAddress = $$1;
        setDaemon(true);
        setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        this.socket = new DatagramSocket();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String $$0 = createPingString(this.motd, this.serverAddress);
        byte[] $$1 = $$0.getBytes(StandardCharsets.UTF_8);
        while (!isInterrupted() && this.isRunning) {
            try {
                InetAddress $$2 = InetAddress.getByName(MULTICAST_GROUP);
                DatagramPacket $$3 = new DatagramPacket($$1, $$1.length, $$2, PING_PORT);
                this.socket.send($$3);
                try {
                    sleep(PING_INTERVAL);
                } catch (InterruptedException e) {
                }
            } catch (IOException $$4) {
                LOGGER.warn("LanServerPinger: {}", $$4.getMessage());
                return;
            }
        }
    }

    @Override // java.lang.Thread
    public void interrupt() {
        super.interrupt();
        this.isRunning = false;
    }

    public static String createPingString(String $$0, String $$1) {
        return "[MOTD]" + $$0 + "[/MOTD][AD]" + $$1 + "[/AD]";
    }

    public static String parseMotd(String $$0) {
        int $$2;
        int $$1 = $$0.indexOf("[MOTD]");
        if ($$1 < 0 || ($$2 = $$0.indexOf("[/MOTD]", $$1 + "[MOTD]".length())) < $$1) {
            return "missing no";
        }
        return $$0.substring($$1 + "[MOTD]".length(), $$2);
    }

    public static String parseAddress(String $$0) {
        int $$3;
        int $$4;
        int $$1 = $$0.indexOf("[/MOTD]");
        if ($$1 < 0) {
            return null;
        }
        int $$2 = $$0.indexOf("[/MOTD]", $$1 + "[/MOTD]".length());
        if ($$2 >= 0 || ($$3 = $$0.indexOf("[AD]", $$1 + "[/MOTD]".length())) < 0 || ($$4 = $$0.indexOf("[/AD]", $$3 + "[AD]".length())) < $$3) {
            return null;
        }
        return $$0.substring($$3 + "[AD]".length(), $$4);
    }
}
