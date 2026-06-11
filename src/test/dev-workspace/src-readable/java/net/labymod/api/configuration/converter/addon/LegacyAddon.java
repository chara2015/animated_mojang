package net.labymod.api.configuration.converter.addon;

import net.labymod.api.configuration.converter.LegacyAddonConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/addon/LegacyAddon.class */
public interface LegacyAddon {
    @NotNull
    LegacyAddonConverter.Version getVersion();

    @NotNull
    String getName();

    @Nullable
    String getNamespace();
}
