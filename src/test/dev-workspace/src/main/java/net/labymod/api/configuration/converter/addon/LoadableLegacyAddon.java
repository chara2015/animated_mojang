package net.labymod.api.configuration.converter.addon;

import java.nio.file.Path;
import net.labymod.api.Laby;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/addon/LoadableLegacyAddon.class */
public class LoadableLegacyAddon implements LegacyAddon {
    private final Path path;
    private final LegacyAddonConverter.Version version;
    private final String uuid;
    private final String name;
    private final String mainClass;

    public LoadableLegacyAddon(@NotNull Path path, @NotNull LegacyAddonConverter.Version version, @NotNull String uuid, @NotNull String name, @NotNull String mainClass) {
        this.path = path;
        this.version = version;
        this.uuid = uuid;
        this.name = name;
        this.mainClass = mainClass;
    }

    @NotNull
    public Path getPath() {
        return this.path;
    }

    @Override // net.labymod.api.configuration.converter.addon.LegacyAddon
    @NotNull
    public LegacyAddonConverter.Version getVersion() {
        return this.version;
    }

    @NotNull
    public String getUuid() {
        return this.uuid;
    }

    @Override // net.labymod.api.configuration.converter.addon.LegacyAddon
    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getMainClass() {
        return this.mainClass;
    }

    @Override // net.labymod.api.configuration.converter.addon.LegacyAddon
    @Nullable
    public String getNamespace() {
        return Laby.references().legacyConfigConverter().getModernAddonNamespace(this.uuid);
    }
}
