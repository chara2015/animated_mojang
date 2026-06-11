package net.labymod.api.configuration.converter;

import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyConfigConverter.class */
@Referenceable
public interface LegacyConfigConverter {
    void register(@NotNull LegacyConverter<?> legacyConverter);

    void registerAddon(@NotNull String str, @NotNull String... strArr);

    void registerAddonResolver(@NotNull LegacyAddonResolver legacyAddonResolver);

    @Nullable
    String getModernAddonNamespace(@NotNull String str);

    @NotNull
    Collection<LegacyConverter<?>> getConverters();

    @NotNull
    Collection<LegacyConverter<?>> getConverters(@NotNull String str);

    boolean hasLegacyInstallation();

    boolean hasStuffToConvert(@NotNull String str);

    boolean wasConversionAsked(@NotNull String str);

    void setConversionAsked(@NotNull String str);

    void useVersion(@NotNull String str, @NotNull LegacyAddonConverter.Version version);

    int convert(@NotNull String str);

    @NotNull
    Collection<LegacyAddon> discoverLegacyAddons();

    @NotNull
    Collection<LegacyAddon> discoverLegacyAddons(@NotNull LegacyAddonConverter.Version version);
}
