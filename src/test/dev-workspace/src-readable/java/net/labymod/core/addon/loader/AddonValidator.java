package net.labymod.core.addon.loader;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import net.labymod.api.BuildData;
import net.labymod.api.Namespaces;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.addon.exception.UnsupportedAddonException;
import net.labymod.api.models.addon.info.InstalledAddonInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/AddonValidator.class */
public class AddonValidator {
    private static final Pattern NAMESPACE_PATTERN = Pattern.compile("[a-z0-9_\\-]*");
    private static final Set<String> RESERVED_NAMESPACES = Set.of(Namespaces.MINECRAFT, "reamls", "labymod");

    public static void validateAddonInfo(InstalledAddonInfo addonInfo) {
        if (addonInfo == null) {
            throw new AddonLoadException("No addon.json found");
        }
        String errorText = null;
        if (addonInfo.getNamespace() == null || addonInfo.getNamespace().isEmpty()) {
            errorText = "namespace";
        } else if (addonInfo.getAuthor() == null) {
            errorText = "author";
        } else if (addonInfo.getDisplayName() == null) {
            errorText = "displayName";
        } else if (addonInfo.getVersion() == null) {
            errorText = "version";
        } else if (addonInfo.getCompatibleMinecraftVersions() == null) {
            errorText = "compatibleMinecraftVersions";
        } else if (addonInfo.meta() == null) {
            errorText = "meta";
        }
        if (errorText != null) {
            throw new UnsupportedAddonException("Not a valid LabyMod Addon (" + errorText + " is missing)");
        }
        if (!isMatchingNamespaceFormat(addonInfo.getNamespace())) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "%s uses an invalid namespace \"%s\" (Only lowercase letters, numbers, underscores and hyphens are allowed)", addonInfo.getFileName(), addonInfo.getNamespace()));
        }
        if (RESERVED_NAMESPACES.contains(addonInfo.getNamespace())) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "%s uses the reserved namespace \"%s\" (Reserved namespaces are: %s)", addonInfo.getFileName(), addonInfo.getNamespace(), String.join(", ", RESERVED_NAMESPACES)));
        }
    }

    public static boolean isMatchingNamespaceFormat(String namespace) {
        return NAMESPACE_PATTERN.matcher(namespace).matches();
    }

    public static boolean isBuildNumberGreater(InstalledAddonInfo addonInfo) {
        return addonInfo.getRequiredLabyModBuild() > BuildData.getBuildNumber();
    }
}
