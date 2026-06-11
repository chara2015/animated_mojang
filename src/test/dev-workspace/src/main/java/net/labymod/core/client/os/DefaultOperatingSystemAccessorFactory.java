package net.labymod.core.client.os;

import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.api.client.os.OperatingSystemAccessorFactory;
import net.labymod.api.models.OperatingSystem;
import net.labymod.core.client.os.linux.LinuxAccessor;
import net.labymod.core.client.os.unix.MacOSAccessor;
import net.labymod.core.client.os.windows.WindowsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/DefaultOperatingSystemAccessorFactory.class */
public class DefaultOperatingSystemAccessorFactory implements OperatingSystemAccessorFactory {
    @Override // net.labymod.api.client.os.OperatingSystemAccessorFactory
    public OperatingSystemAccessor createAccessor() {
        switch (OperatingSystem.getPlatform()) {
            case WINDOWS:
                return new WindowsAccessor();
            case MACOS:
                return new MacOSAccessor();
            case LINUX:
                return new LinuxAccessor();
            default:
                return null;
        }
    }
}
