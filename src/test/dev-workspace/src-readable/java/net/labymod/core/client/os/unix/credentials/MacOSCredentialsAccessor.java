package net.labymod.core.client.os.unix.credentials;

import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import pt.davidafsilva.apple.OSXKeychain;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/unix/credentials/MacOSCredentialsAccessor.class */
public class MacOSCredentialsAccessor implements CredentialsAccessor {
    private static final boolean ENABLED = false;
    private OSXKeychain keychain;

    public String getValue(String serviceName, String id) throws Exception {
        throw new UnsupportedOperationException("Not supported on this platform");
    }

    public void setValue(String serviceName, String id, String value) throws Exception {
        throw new UnsupportedOperationException("Not supported on this platform");
    }
}
