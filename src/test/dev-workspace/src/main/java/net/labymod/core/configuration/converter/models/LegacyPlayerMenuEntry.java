package net.labymod.core.configuration.converter.models;

import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextServerConfig;
import net.labymod.api.client.gui.screen.key.Key;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/models/LegacyPlayerMenuEntry.class */
public class LegacyPlayerMenuEntry {
    private String displayName;
    private String command;
    private boolean sendInstantly;

    public String getDisplayName() {
        return this.displayName;
    }

    public AutoTextEntry convert() {
        return new AutoTextEntry(this.displayName, this.command, true, this.sendInstantly, new AutoTextServerConfig(false, ""), new Key[0]);
    }
}
