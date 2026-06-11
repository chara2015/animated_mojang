package net.labymod.core.configuration.converter.models;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextServerConfig;
import net.labymod.api.client.gui.screen.key.Key;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/models/LegacyAutoText.class */
public class LegacyAutoText {
    private String message;
    private boolean keyShift;
    private boolean keyCtrl;
    private boolean keyAlt;
    private int keyCode;
    private boolean sendNotInstantly;
    private boolean serverBound;
    private String serverAddress;
    private boolean available;

    public AutoTextEntry convert() {
        if (!this.available) {
            return null;
        }
        Key[] keys = convertKeys();
        if (keys.length == 0) {
            return null;
        }
        return new AutoTextEntry(this.message, this.message, false, !this.sendNotInstantly, new AutoTextServerConfig(this.serverBound, this.serverAddress), keys);
    }

    private Key[] convertKeys() {
        Key key = Key.get(this.keyCode);
        if (key.isUnknown()) {
            return new Key[0];
        }
        List<Key> keys = new ArrayList<>();
        if (this.keyShift) {
            keys.add(Key.L_SHIFT);
        }
        if (this.keyCtrl) {
            keys.add(Key.L_CONTROL);
        }
        if (this.keyAlt) {
            keys.add(Key.L_ALT);
        }
        keys.add(key);
        return (Key[]) keys.toArray(new Key[0]);
    }
}
