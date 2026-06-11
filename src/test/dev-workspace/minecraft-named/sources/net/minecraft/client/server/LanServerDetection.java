package net.minecraft.client.server;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/server/LanServerDetection.class */
public class LanServerDetection {
    static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
    static final Logger LOGGER = LogUtils.getLogger();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/server/LanServerDetection$LanServerList.class */
    public static class LanServerList {
        private final List<LanServer> servers = Lists.newArrayList();
        private boolean isDirty;

        public synchronized List<LanServer> takeDirtyServers() {
            if (this.isDirty) {
                List<LanServer> $$0 = List.copyOf(this.servers);
                this.isDirty = false;
                return $$0;
            }
            return null;
        }

        public synchronized void addServer(String $$0, InetAddress $$1) {
            String $$2 = LanServerPinger.parseMotd($$0);
            String $$3 = LanServerPinger.parseAddress($$0);
            if ($$3 == null) {
                return;
            }
            String $$32 = $$1.getHostAddress() + ":" + $$3;
            boolean $$4 = false;
            Iterator<LanServer> it = this.servers.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                LanServer $$5 = it.next();
                if ($$5.getAddress().equals($$32)) {
                    $$5.updatePingTime();
                    $$4 = true;
                    break;
                }
            }
            if (!$$4) {
                this.servers.add(new LanServer($$2, $$32));
                this.isDirty = true;
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/server/LanServerDetection$LanServerDetector.class */
    public static class LanServerDetector extends Thread {
        private final LanServerList serverList;
        private final InetAddress pingGroup;
        private final MulticastSocket socket;

        public LanServerDetector(LanServerList $$0) throws IOException {
            super("LanServerDetector #" + LanServerDetection.UNIQUE_THREAD_ID.incrementAndGet());
            this.serverList = $$0;
            setDaemon(true);
            setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LanServerDetection.LOGGER));
            this.socket = new MulticastSocket(LanServerPinger.PING_PORT);
            this.pingGroup = InetAddress.getByName(LanServerPinger.MULTICAST_GROUP);
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.pingGroup);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] $$0 = new byte[1024];
            while (!isInterrupted()) {
                DatagramPacket $$1 = new DatagramPacket($$0, $$0.length);
                try {
                    this.socket.receive($$1);
                    String $$4 = new String($$1.getData(), $$1.getOffset(), $$1.getLength(), StandardCharsets.UTF_8);
                    LanServerDetection.LOGGER.debug("{}: {}", $$1.getAddress(), $$4);
                    this.serverList.addServer($$4, $$1.getAddress());
                } catch (SocketTimeoutException e) {
                } catch (IOException $$3) {
                    LanServerDetection.LOGGER.error("Couldn't ping server", $$3);
                }
            }
            try {
                this.socket.leaveGroup(this.pingGroup);
            } catch (IOException e2) {
            }
            this.socket.close();
        }
    }
}
