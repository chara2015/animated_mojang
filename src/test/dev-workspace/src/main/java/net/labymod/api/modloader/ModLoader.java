package net.labymod.api.modloader;

import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.models.version.Version;
import net.labymod.api.modloader.mod.ModInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/modloader/ModLoader.class */
public interface ModLoader {
    @NotNull
    String getId();

    @NotNull
    Version version();

    @NotNull
    String getExpectedMappingNamespace();

    @NotNull
    Collection<Path> getModDirectoryPaths();

    @NotNull
    Collection<Path> getConfigDirectoryPaths();

    @NotNull
    Collection<ModInfo> getModInfos();

    @Nullable
    ModInfo getModInfo(@NotNull String str);

    boolean isModLoaded(@NotNull String str);
}
