package net.labymod.api.configuration.settings.creator.availability;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.LabyAPI;
import net.labymod.api.Namespaces;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.creator.version.VersionValidator;
import net.labymod.api.configuration.settings.creator.version.def.AddonVersionValidator;
import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.comparison.VersionMultiRangeComparison;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/availability/VersionCompatibleMemberAvailability.class */
public class VersionCompatibleMemberAvailability implements MemberAvailability {
    private static final Logging LOGGER = Logging.getLogger();
    private final LabyAPI labyAPI;
    private final Map<String, VersionValidator> validators = new HashMap();

    public VersionCompatibleMemberAvailability(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.validators.put("addon", new AddonVersionValidator(labyAPI));
    }

    @Override // net.labymod.api.configuration.settings.creator.availability.MemberAvailability
    public boolean isAvailable(MemberAvailabilityContext context) {
        String version;
        MemberInspector inspector = context.inspector();
        VersionCompatibility annotation = (VersionCompatibility) inspector.getAnnotation(VersionCompatibility.class);
        if (annotation == null || (version = annotation.value()) == null || version.isEmpty()) {
            return true;
        }
        String type = annotation.type();
        if (type == null) {
            return false;
        }
        if (type.equals(Namespaces.MINECRAFT)) {
            try {
                return isMinecraftVersionSupported(version);
            } catch (IllegalArgumentException exception) {
                LOGGER.error("Version range of a configuration from addon {} could not be parsed", context.namespace(), exception);
                return false;
            }
        }
        String[] entries = type.split(":", 2);
        String key = entries[0];
        VersionValidator selected = this.validators.get(key);
        if (selected == null) {
            return false;
        }
        return selected.isSupportedVersion(version, entries[1]);
    }

    private boolean isMinecraftVersionSupported(String version) {
        MinecraftVersion runningVersion = MinecraftVersions.byId(this.labyAPI.labyModLoader().version());
        if (version.contains("<") || version.contains(",")) {
            VersionMultiRangeComparison<MinecraftVersion> comparison = VersionMultiRangeComparison.parse(version, s -> {
                return MinecraftVersions.byId(VersionDeserializer.from(s));
            });
            return comparison.isCompatible(runningVersion);
        }
        MinecraftVersion desiredVersion = MinecraftVersions.byId(VersionDeserializer.from(version));
        return runningVersion.isCompatible(desiredVersion);
    }
}
