package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.configuration.loader.annotation.OSCompatibility;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.models.OperatingSystem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/availability/OperatingSystemCompatibleMemberAvailability.class */
public class OperatingSystemCompatibleMemberAvailability implements MemberAvailability {
    @Override // net.labymod.api.configuration.settings.creator.availability.MemberAvailability
    public boolean isAvailable(MemberAvailabilityContext context) {
        MemberInspector inspector = context.inspector();
        OSCompatibility annotation = (OSCompatibility) inspector.getAnnotation(OSCompatibility.class);
        if (annotation == null) {
            return true;
        }
        OperatingSystem operatingSystem = OperatingSystem.getPlatform();
        OperatingSystem[] compatible = annotation.value();
        for (OperatingSystem os : compatible) {
            if (os == operatingSystem) {
                return true;
            }
        }
        return false;
    }
}
