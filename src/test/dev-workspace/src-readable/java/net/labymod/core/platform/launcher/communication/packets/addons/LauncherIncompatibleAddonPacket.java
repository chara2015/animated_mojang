package net.labymod.core.platform.launcher.communication.packets.addons;

import java.util.ArrayList;
import java.util.List;
import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/packets/addons/LauncherIncompatibleAddonPacket.class */
public class LauncherIncompatibleAddonPacket implements LauncherPacket {
    private List<IncompatibleAddon> incompatibleAddonList;

    public LauncherIncompatibleAddonPacket() {
    }

    public LauncherIncompatibleAddonPacket(List<IncompatibleAddon> incompatibleAddonList) {
        this.incompatibleAddonList = incompatibleAddonList;
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void read(PayloadReader reader) {
        this.incompatibleAddonList = new ArrayList();
        int decisions = reader.readVarInt();
        for (int i = 0; i < decisions; i++) {
            String namespace = reader.readString();
            boolean disableIncompatible = reader.readBoolean();
            this.incompatibleAddonList.add(new IncompatibleAddon(namespace, disableIncompatible));
        }
    }

    @Override // net.labymod.core.platform.launcher.communication.LauncherPacket
    public void write(PayloadWriter writer) {
        writer.writeVarInt(this.incompatibleAddonList.size());
        for (IncompatibleAddon incompatibleAddon : this.incompatibleAddonList) {
            writer.writeString(incompatibleAddon.getNamespace());
            writer.writeVarInt(incompatibleAddon.dependencies.length);
            for (String dependency : incompatibleAddon.dependencies) {
                writer.writeString(dependency);
            }
            writer.writeVarInt(incompatibleAddon.incompatibleNamespaces.length);
            for (String incompatible : incompatibleAddon.incompatibleNamespaces) {
                writer.writeString(incompatible);
            }
        }
    }

    public List<IncompatibleAddon> getIncompatibleAddons() {
        return this.incompatibleAddonList;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/communication/packets/addons/LauncherIncompatibleAddonPacket$IncompatibleAddon.class */
    public static class IncompatibleAddon {
        private final String namespace;
        private final String[] dependencies;
        private final String[] incompatibleNamespaces;
        private boolean disableIncompatible;

        public IncompatibleAddon(String namespace, String[] dependencies, String[] incompatibleNamespaces) {
            this.namespace = namespace;
            this.dependencies = dependencies;
            this.incompatibleNamespaces = incompatibleNamespaces;
            this.disableIncompatible = false;
        }

        public IncompatibleAddon(String namespace, boolean disableIncompatible) {
            this(namespace, null, null);
            this.disableIncompatible = disableIncompatible;
        }

        public String getNamespace() {
            return this.namespace;
        }

        public String[] getIncompatibleNamespaces() {
            return this.incompatibleNamespaces;
        }

        public boolean disableIncompatible() {
            return this.disableIncompatible;
        }
    }
}
