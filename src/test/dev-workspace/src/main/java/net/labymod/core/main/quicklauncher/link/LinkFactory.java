package net.labymod.core.main.quicklauncher.link;

import net.labymod.api.models.OperatingSystem;
import net.labymod.core.main.quicklauncher.link.implementation.MacOSLink;
import net.labymod.core.main.quicklauncher.link.implementation.UnixLink;
import net.labymod.core.main.quicklauncher.link.implementation.WindowsLink;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/quicklauncher/link/LinkFactory.class */
public class LinkFactory {
    public static boolean isSupported(OperatingSystem operatingSystem) {
        return create(operatingSystem) != null;
    }

    @Nullable
    public static Link create(OperatingSystem operatingSystem) {
        switch (operatingSystem) {
            case WINDOWS:
                return new WindowsLink();
            case MACOS:
                return new MacOSLink();
            case LINUX:
                return new UnixLink();
            default:
                return null;
        }
    }
}
