package net.labymod.api.configuration.converter.addon;

import java.io.IOException;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/addon/LegacyAddonResolver.class */
public interface LegacyAddonResolver {
    void resolveAddons(LegacyAddonConverter.Version version, Collection<LegacyAddon> collection) throws IOException;
}
