package net.labymod.core.platform.launcher.communication;

import java.util.HashMap;
import java.util.Map;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherAddonInstalledPacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherIncompatibleAddonPacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherModLoaderChangePacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherIdentificationPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherStopPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherWindowCreatedPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/LauncherPacketRegistry.class */
public class LauncherPacketRegistry {
    private final Map<Integer, Class<? extends LauncherPacket>> packets = new HashMap();

    public LauncherPacketRegistry() {
        this.packets.put(0, LauncherIdentificationPacket.class);
        this.packets.put(1, LauncherWindowCreatedPacket.class);
        this.packets.put(2, LauncherStopPacket.class);
        this.packets.put(10, LauncherAddonInstalledPacket.class);
        this.packets.put(11, LauncherIncompatibleAddonPacket.class);
        this.packets.put(12, LauncherModLoaderChangePacket.class);
    }

    public Class<? extends LauncherPacket> getPacketById(int id) {
        return this.packets.get(Integer.valueOf(id));
    }

    public int getIdByPacket(Class<? extends LauncherPacket> packet) {
        for (Map.Entry<Integer, Class<? extends LauncherPacket>> entry : this.packets.entrySet()) {
            if (entry.getValue().equals(packet)) {
                return entry.getKey().intValue();
            }
        }
        return -1;
    }
}
