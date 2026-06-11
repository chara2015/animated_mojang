package net.labymod.core.client.os.linux.credentials;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/linux/credentials/LinuxCredentialsAccessor.class */
public class LinuxCredentialsAccessor implements CredentialsAccessor {
    private static final Logging LOGGER = Logging.getLogger();

    public String getValue(String serviceName, String id) {
        try {
            GKLib gklib = GKLib.INSTANCE;
            PointerByReference keyring_ref = new PointerByReference();
            GKResult result = new GKResult(gklib, gklib.gnome_keyring_get_default_keyring_sync(keyring_ref));
            if (result.success()) {
                String keyRing = keyring_ref.getValue().getString(0L);
                PointerByReference pref = new PointerByReference();
                GKResult idResult = new GKResult(gklib, gklib.gnome_keyring_list_item_ids_sync(keyRing, pref));
                if (idResult.success()) {
                    Pointer p = pref.getValue();
                    GList gkal = new GList(p);
                    while (true) {
                        long keyRingId = Pointer.nativeValue(gkal.data);
                        PointerByReference item_info_ref = new PointerByReference();
                        Pointer item_info = null;
                        try {
                            GKResult resultItem = new GKResult(gklib, gklib.gnome_keyring_item_get_info_full_sync(keyRing, (int) keyRingId, 1, item_info_ref));
                            if (resultItem.success()) {
                                item_info = item_info_ref.getValue();
                                String display = gklib.gnome_keyring_item_info_get_display_name(item_info);
                                String secret = gklib.gnome_keyring_item_info_get_secret(item_info);
                                if (display.equals(id)) {
                                    return secret;
                                }
                            } else {
                                resultItem.error();
                            }
                            if (item_info != null) {
                                gklib.gnome_keyring_item_info_free(item_info);
                            }
                            if (gkal.next == Pointer.NULL) {
                                break;
                            }
                            gkal = new GList(gkal.next);
                        } finally {
                            if (0 != 0) {
                                gklib.gnome_keyring_item_info_free(null);
                            }
                        }
                    }
                }
            } else {
                result.error();
            }
            return null;
        } catch (Throwable t) {
            LOGGER.warn("Failed to get credentials from gnome keyring: " + t.getMessage(), new Object[0]);
            return null;
        }
    }

    public void setValue(String serviceName, String id, String secret) throws Exception {
        throw new UnsupportedOperationException();
    }
}
