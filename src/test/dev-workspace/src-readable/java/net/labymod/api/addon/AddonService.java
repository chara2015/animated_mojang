package net.labymod.api.addon;

import java.util.Collection;
import java.util.Optional;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/AddonService.class */
public interface AddonService {
    @NotNull
    Optional<LoadedAddon> getAddon(String str);

    Optional<LoadedAddon> getOptionalAddon(String str);

    @NotNull
    Optional<LoadedAddon> getAddon(ClassLoader classLoader);

    Collection<LoadedAddon> getLoadedAddons();

    Collection<LoadedAddon> getVisibleAddons();

    @Nullable
    InstalledAddonInfo getAddonInfo(String str);

    Class<?> loadClassFromAddon(String str) throws ClassNotFoundException;

    LoadedAddon getLastCallerAddon();

    @Nullable
    String getClassPathAddon();

    boolean isEnabled(Object obj);

    boolean isEnabled(Class<?> cls);

    boolean isEnabled(String str);

    boolean isEnabled(InstalledAddonInfo installedAddonInfo, boolean z);

    boolean isForceDisabled(String str);

    @ApiStatus.Internal
    void registerMainConfiguration(String str, AddonConfig addonConfig);

    @ApiStatus.Internal
    boolean hasMainConfiguration(String str);

    @NotNull
    default Optional<LoadedAddon> getAddon(@NotNull Class<?> clazz) {
        return getAddon(clazz.getClassLoader());
    }
}
