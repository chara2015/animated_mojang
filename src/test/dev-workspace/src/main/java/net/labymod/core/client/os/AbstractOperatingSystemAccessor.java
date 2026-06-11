package net.labymod.core.client.os;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import net.labymod.api.Laby;
import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.os.module.ModuleScanner;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/AbstractOperatingSystemAccessor.class */
public abstract class AbstractOperatingSystemAccessor implements OperatingSystemAccessor {
    private static final Logging LOGGER = Logging.getLogger();
    private final ModuleScanner defaultModuleScanner = new ModuleScanner.Empty();

    public abstract CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException;

    public ModuleScanner moduleScanner() {
        return this.defaultModuleScanner;
    }

    @Override // net.labymod.api.client.os.OperatingSystemAccessor
    public void setWindowIcon(long handle, InputStream stream) {
        try {
            Laby.gfx().setGLFWIcon(handle, stream);
        } catch (IOException exception) {
            LOGGER.error("Failed to set window icon", exception);
        }
    }
}
