package net.labymod.api.configuration.labymod;

import java.util.List;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.configuration.loader.ConfigAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/AutoTextConfigAccessor.class */
public interface AutoTextConfigAccessor extends ConfigAccessor {
    List<AutoTextEntry> getEntries();
}
